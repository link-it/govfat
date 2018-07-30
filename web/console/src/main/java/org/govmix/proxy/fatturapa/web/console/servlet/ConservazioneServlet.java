package org.govmix.proxy.fatturapa.web.console.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter.FORMAT;
import org.govmix.proxy.fatturapa.web.commons.exporter.FatturaSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.mbean.LoginMBean;
import org.govmix.proxy.fatturapa.web.console.search.ConservazioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.ConservazioneService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ConservazioneServlet extends HttpServlet {

	public static final String FATTURA_ELETTRONICA_CONSERVAZIONE_SERVLET_PATH= "/pages/conservazioneservlet";
	public static final String ID_FATTURA_PARAM_NAME= "id";
	
	public static final String PARAMETRO_IS_ALL = "isAll";
	public static final String PARAMETRO_IDS = "ids";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerManager.getConsoleLogger();

	@Override
	public void init() throws ServletException {
		super.init();
		ConservazioneServlet.log.debug("Init ConservazioneServlet completato.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.processRequest(req,resp);		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.processRequest(req,resp);		
	}
	
	
	private void processRequest(HttpServletRequest req, HttpServletResponse response) throws ServletException,IOException{
		try{
			//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			//			IEventiService service = (IEventiService)context.getBean("eventiService");

			// Then we have to get the Response where to write our file
			//			HttpServletResponse response = resp;
			FatturaFilter expressionFromSearch = null;
			HttpSession sessione = req.getSession(); 
			String username = null;
			Utente utente = null;
			LoginMBean lb = (LoginMBean) sessione.getAttribute("loginBean");

			ConservazioneServlet.log.debug("LoginBean trovato in sessione ["+(lb!= null)+"]"); 

			// L'utente per fare l'export delle fatture deve essere loggato
			if(lb != null){
				// utente loggato
				ConservazioneServlet.log.debug("Utente Loggato: ["+(lb.getIsLoggedIn())+"]"); 
				if(lb.getIsLoggedIn()){
					String isAllString = req.getParameter(ConservazioneServlet.PARAMETRO_IS_ALL);
					Boolean isAll = Boolean.parseBoolean(isAllString);
					String idFatture=req.getParameter(ConservazioneServlet.PARAMETRO_IDS);
					String[] ids = StringUtils.split(idFatture, ",");
					
					username = lb.getUsername();
					utente = lb.getLoggedUtente();

					if(ids == null || (ids != null && ids.length == 0))
						throw new ExportException("Si e' verificato un errore durante l'invio in conservazione: Formato parametri errato.");
					
					response.addHeader("Cache-Control", "private");
					response.setStatus(200);

					FatturaBD fatturaSearchDAO = new FatturaBD(ConservazioneServlet.log);
					if(isAll){
						try{
							ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
							ConservazioneService service = (ConservazioneService) context.getBean("conservazioneService");

							ConservazioneSearchForm sfInSession = (ConservazioneSearchForm)context.getBean("conservazioneSearchForm");
							ConservazioneSearchForm form2 = (ConservazioneSearchForm) sfInSession.clone();

							expressionFromSearch = service.getFilterFromSearch(fatturaSearchDAO, form2);

						}catch(Exception e){
							ConservazioneServlet.log.error("Si e' verificato un errore durante l'impostazione dei criteri di ricerca: "+ e.getMessage(),e);
							throw new ExportException("Si e' verificato un errore durante l'export della risorsa selezionata.");
						}
					}

					boolean autorizzato = this.checkautorizzazioneExport(username, ids, isAll, fatturaSearchDAO, utente);

					// utente non autorizzato ad accerdere alla risorsa
					if(!autorizzato){
						StringBuilder sb = new StringBuilder();
						sb.append("Autenticazione Richiesta");
						if(username != null)
							sb.append(": Utente [").append(username).append("] non dispone dei permessi necessari per accedere alla risorsa.");
						throw new ExportException(sb.toString());
					}
					
					List<IdFattura> lstIdFattura = null;
					//Invio in conservazione di tutte le fatture corrispondenti al filtro  
					if(isAll){
						lstIdFattura = getLstIdFattura(fatturaSearchDAO, expressionFromSearch);
					}else{
						lstIdFattura = findIdFattura(ids, isAll, fatturaSearchDAO);
					}
						
					this.inviaConservazione(lstIdFattura);

					// committing status and headers
					//							response.flushBuffer();
					response.addHeader("Content-Length", ""+ "OK".length());
					response.flushBuffer();
					response.getWriter().println("OK");
					return ; 
				}
			}
			// Utente non autorizzato
			throw new ExportException("Autenticazione Richiesta");
		}catch(ExportException e){
			ConservazioneServlet.log.debug(e.getMessage(),e);
			ByteArrayOutputStream baos=  new ByteArrayOutputStream();

			response.setContentType("text/plain");					
			response.addHeader("Cache-Control", "private");
			response.setStatus(500);
			// committing status and headers
			response.flushBuffer();
			StringBuilder sb = new StringBuilder();
			sb.append(e.getMessage());
			baos.write(sb.toString().getBytes());
			byte [] buffer = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

			Utils.copy(bais, response.getOutputStream()); 
		}
		catch(Exception e){
			ConservazioneServlet.log.error(e,e);
			ByteArrayOutputStream baos=  new ByteArrayOutputStream();

			response.setContentType("text/plain");					
			response.addHeader("Cache-Control", "private");
			response.setStatus(500);
			// committing status and headers
			response.flushBuffer();
			StringBuilder sb = new StringBuilder();
			sb.append("Si e' verificato un errore durante l'invio in conservazione delle fatture selezionate.");
			baos.write(sb.toString().getBytes());
			byte [] buffer = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

			Utils.copy(bais, response.getOutputStream()); 
		}
	}

	private void inviaConservazione(List<IdFattura> lstIdFattura) {
		// TODO inserire la logica dell'invio
		
	}

	private List<IdFattura> findIdFattura(String []ids, boolean isAll, FatturaBD fatturaBD)
			throws ServiceException, NotFoundException {
		List<IdFattura> lstIdFattura;
		lstIdFattura = new ArrayList<IdFattura>();
		for (int j = 0; j < ids.length; j++) {
			lstIdFattura.add(fatturaBD.findId(Long.parseLong(ids[j])));					
		}
		return lstIdFattura;
	}
	
	public boolean checkautorizzazioneExport(String username, String []ids, boolean isAll, FatturaBD fatturaBD, Utente utente) throws ExportException {

		try {
			log.debug("Controllo autorizzazione per l'utente ["+username+"] in corso...");

			UserRole role = utente.getRole();
			log.debug("Controllo autorizzazione per l'utente ["+username+"] Ruolo Trovato["+role.toString()+"].");
			if(role.equals(UserRole.ADMIN)){
				log.debug("Controllo autorizzazione per l'utente ["+username+"] completato, l'utente con ruolo ADMIN e' autorizzato.");
				return true;
			}

			List<IdFattura> idFatturaRichiesti = null;
			try {
				idFatturaRichiesti = findIdFattura(ids, isAll,fatturaBD);
			}catch(NotFoundException e){
				log.debug("Impossibile trovare la risorsa richiesta:"+ e.getMessage(), e);
				throw new ExportException("Impossibile trovare la risorsa richiesta.");
			}

			// passo alla bd l'id utente che mi restituisce una lista di idFattura 
			List<IdFattura> idFattureByUtente = fatturaBD.getIdFattureByUtente(utente);


			for (IdFattura idFattura : idFatturaRichiesti) {
				boolean found = false;
				for (IdFattura idFatturaAutorizzata : idFattureByUtente) {
					if(idFattura.getIdentificativoSdi().intValue() == idFatturaAutorizzata.getIdentificativoSdi().intValue() && 
							idFattura.getPosizione().intValue() == idFatturaAutorizzata.getPosizione().intValue()){
						found = true;
						break;
					}
				}
				if(!found)
					return false;
			}

			return true;


		}catch(Exception e){
			log.error("Si e' verificato un errore durante la verifica delle autorizzazione conservazione per l'utente ["+username+"]: "+ e.getMessage() , e);
			throw new ExportException("Si e' verificato un errore durante la verifica delle autorizzazione conservazione per l'utente ["+username+"]");
		}

	}
	
	private List<IdFattura> getLstIdFattura(FatturaBD fatturaBD, FatturaFilter expressionFromSearch) throws Exception {

		FatturaFilter filter = null;
		int start = 0;
		int limit = 1000;
		if(expressionFromSearch != null){
			filter = expressionFromSearch;
			List<FilterSortWrapper> filterSortList = new ArrayList<FilterSortWrapper>();
			FilterSortWrapper wrap = new FilterSortWrapper();
			wrap.setField(FatturaElettronica.model().DATA_RICEZIONE);
			wrap.setSortOrder(SortOrder.DESC);
			filterSortList.add(wrap);
			filter.setFilterSortList(filterSortList);
		} else {
			filter = fatturaBD.newFilter();
		} 

		filter.setOffset(start);
		filter.setLimit(limit);
		
		List<IdFattura> listFattura = fatturaBD.findAllIds(filter);

		int size = listFattura.size();
		while(size>0){

			start+=listFattura.size();
			filter.setOffset(start);
			filter.setLimit(limit);

			List<IdFattura> findAllIds =  fatturaBD.findAllIds(filter);
			listFattura.addAll(findAllIds);
			size = findAllIds.size();
		}
		return listFattura;

	}
}
