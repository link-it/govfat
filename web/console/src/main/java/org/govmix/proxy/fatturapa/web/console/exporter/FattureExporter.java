/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.govmix.proxy.fatturapa.web.console.exporter;

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
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter.FORMAT;
import org.govmix.proxy.fatturapa.web.commons.exporter.AllegatoSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.ExtendedNotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.web.commons.exporter.FatturaSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.NotificaDTSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.NotificaECSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.PccTracciaResponseSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.ScartoECSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.mbean.LoginMBean;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * FattureExporter Servlet per la gestione dell'export delle fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FattureExporter  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1314905657194691373L;
	private static Logger log = LoggerManager.getConsoleLogger();

	public static final String PARAMETRO_ACTION = "action";
	public static final String PARAMETRO_FORMATO = "formato";
	public static final String PARAMETRO_IS_ALL = "isAll";
	public static final String PARAMETRO_IDS = "ids";

	public static final String PARAMETRO_ACTION_FATTURA = AbstractSingleFileExporter.PARAMETRO_ACTION_FATTURA;
	public static final String PARAMETRO_ACTION_ALLEGATO = AbstractSingleFileExporter.PARAMETRO_ACTION_ALLEGATO;
	public static final String PARAMETRO_ACTION_NOTIFICA_EC = AbstractSingleFileExporter.PARAMETRO_ACTION_NOTIFICA_EC;
	public static final String PARAMETRO_ACTION_NOTIFICA_DT = AbstractSingleFileExporter.PARAMETRO_ACTION_NOTIFICA_DT;
	public static final String PARAMETRO_ACTION_SCARTO = AbstractSingleFileExporter.PARAMETRO_ACTION_SCARTO;
	public static final String PARAMETRO_ACTION_PCC_RIALLINEAMENTO = AbstractSingleFileExporter.PARAMETRO_ACTION_PCC_RIALLINEAMENTO;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_FATTURA_USCITA = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_FATTURA_USCITA;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_SCARTO = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_SCARTO;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_RICEVUTA_CONSEGNA = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_RICEVUTA_CONSEGNA;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_MANCATA_CONSEGNA = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_MANCATA_CONSEGNA;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_ATTESTAZIONE_TRASMISSIONE_FATTURA = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_ATTESTAZIONE_TRASMISSIONE_FATTURA;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_ESITO_COMMITTENTE = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_ESITO_COMMITTENTE;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE;
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO = AbstractSingleFileExporter.PARAMETRO_ACTION_COMUNICAZIONE_AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO;

	public static final String FATTURE_EXPORTER = "pages/fattureexporter";


	@Override
	public void init() throws ServletException {
		super.init();

		FattureExporter.log.debug("Init Servlet FattureExporter completato.");
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
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpSession sessione = req.getSession(); 
			String username = null;
			LoginMBean lb = (LoginMBean) sessione.getAttribute("loginBean");

			FattureExporter.log.debug("LoginBean trovato in sessione ["+(lb!= null)+"]"); 

			// L'utente per fare l'export delle fatture deve essere loggato
			if(lb != null){
				// utente loggato
				FattureExporter.log.debug("Utente Loggato: ["+(lb.getIsLoggedIn())+"]"); 
				if(lb.getIsLoggedIn()){

					String isAllString = req.getParameter(FattureExporter.PARAMETRO_IS_ALL);
					Boolean isAll = Boolean.parseBoolean(isAllString);
					String idFatture=req.getParameter(FattureExporter.PARAMETRO_IDS);
					String[] ids = StringUtils.split(idFatture, ",");
					String formato=req.getParameter(FattureExporter.PARAMETRO_FORMATO);
					String action =req.getParameter(FattureExporter.PARAMETRO_ACTION);
					username = lb.getUsername();

					if(ids == null || (ids != null && ids.length == 0))
						throw new ExportException("Si e' verificato un errore durante l'export: Formato parametri errato.");

					if(action != null){
						AbstractSingleFileExporter<?, ?> sfe = getSingleFileExporter(action);
						String fileName = null;

						response.addHeader("Cache-Control", "private");
						response.setStatus(200);

						FatturaBD fatturaSearchDAO = sfe.getFatturaBD();
						if(isAll){
							try{
								ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
								FatturaElettronicaService service = (FatturaElettronicaService)context.getBean("fatturaElettronicaService");

								FatturaElettronicaSearchForm sfInSession = (FatturaElettronicaSearchForm)context.getBean("fatturaElettronicaSearchForm");
								FatturaElettronicaSearchForm form2 = (FatturaElettronicaSearchForm) sfInSession.clone();

								expressionFromSearch = service.getFilterFromSearch(fatturaSearchDAO, form2);

							}catch(Exception e){
								FattureExporter.log.error("Si e' verificato un errore durante l'impostazione dei criteri di ricerca: "+ e.getMessage(),e);
								throw new ExportException("Si e' verificato un errore durante l'export della risorsa selezionata.");
							}
						}

						boolean autorizzato = sfe.checkautorizzazioneExport(username, ids, isAll);

						// utente non autorizzato ad accerdere alla risorsa
						if(!autorizzato){
							StringBuilder sb = new StringBuilder();
							sb.append("Autenticazione Richiesta");
							if(username != null)
								sb.append(": Utente [").append(username).append("] non dispone dei permessi necessari per accedere alla risorsa.");
							throw new ExportException(sb.toString());
						}

						// Export Fattura
						if(action.equals(FattureExporter.PARAMETRO_ACTION_FATTURA)){
							// formato di export della fattura non valido
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_ZIP_CON_ALLEGATI) && !formato.equals(AbstractSingleFileExporter.FORMATO_PDF) 
									&& !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo fattura.");

							FatturaSingleFileExporter fsfe = (FatturaSingleFileExporter) sfe;

							//Export Completo
							if(formato.equals(AbstractSingleFileExporter.FORMATO_ZIP_CON_ALLEGATI)){
								response.setContentType("application/zip");
								fileName = AbstractSingleFileExporter.BASE_DIR_NAME + ".zip";
								// Tutte le fatture
								if(isAll){
									List<IdFattura> lstIdFattura = getLstIdFattura(fatturaSearchDAO, expressionFromSearch);
									fsfe.exportListById(lstIdFattura, baos, FORMAT.ZIP);
									//									sfe.exportAsZip(expressionFromSearch,baos);
								}else{
									List<FatturaElettronica> lstIdFattura = new ArrayList<FatturaElettronica>();
									for (int j = 0; j < ids.length; j++) {

										lstIdFattura.add(fsfe.convertToObject(ids[j]));					
									}
									fsfe.exportList(lstIdFattura, baos, FORMAT.ZIP);

								}		
							} else if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){ // Visualizzazione Singola Fattura PDF
								response.setContentType("application/pdf");
								FatturaElettronica f = fsfe.convertToObject(ids[0]);
								fileName = fsfe.exportAsPdf(f,baos);
							} else if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){ // Visualizzazione Singola Fattura XML
								response.setContentType("text/xml");
								FatturaElettronica f = fsfe.convertToObject(ids[0]);
								fileName = fsfe.exportAsRaw(f, baos);
							}

							response.addHeader("Content-Type", "x-download");
							response.addHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						} else if(action.equals(PARAMETRO_ACTION_ALLEGATO)){
							AllegatoSingleFileExporter asfe = (AllegatoSingleFileExporter) sfe;
							AllegatoFattura a = asfe.convertToObject(ids[0]);
							fileName = asfe.exportAsRaw(a, baos);
							response.setContentType("application/x-download");
							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}else if(action.equals(PARAMETRO_ACTION_NOTIFICA_DT)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo Notifica DT.");

							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						} else if(action.equals(PARAMETRO_ACTION_NOTIFICA_EC)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo Notifica EC.");

							NotificaECSingleFileExporter ecsfe = (NotificaECSingleFileExporter) sfe;
							ExtendedNotificaEsitoCommittente ec = ecsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica EC formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = ecsfe.exportAsPdf(ec, baos);
							}

							// Visualizzazione NotificaEC formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = ecsfe.exportAsRaw(ec, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						} else if(action.equals(PARAMETRO_ACTION_SCARTO)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo Scarto Notifica EC.");

							ScartoECSingleFileExporter ecsfe = (ScartoECSingleFileExporter) sfe;
							ExtendedNotificaEsitoCommittente ec = ecsfe.convertToObject(ids[0]);


							// Visualizzazione Scarto formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = ecsfe.exportAsPdf(ec, baos);
							}

							// Visualizzazione Scarto formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = ecsfe.exportAsRaw(ec, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
						} else if(action.equals(FattureExporter.PARAMETRO_ACTION_PCC_RIALLINEAMENTO)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo Riallineamento PCC.");

							PccTracciaResponseSingleFileExporter riallineamentoSFE = (PccTracciaResponseSingleFileExporter) sfe;
							PccTraccia object = riallineamentoSFE.convertToObject(ids[0]); 
							
							// Visualizzazione Scarto formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = riallineamentoSFE.exportAsPdf(object, baos);
							}
							
							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
						} else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_ATTESTAZIONE_TRASMISSIONE_FATTURA)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Attestazione Trasmissione Fattura.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Avvenuta Trasmissione Impossibilita Recapito.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_FATTURA_USCITA)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Fattura Uscita.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Notifica DT Trasmittente.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_ESITO_COMMITTENTE)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Notifica EC.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_MANCATA_CONSEGNA)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Notifica Mancata Consegna.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_SCARTO)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Notifica Scarto.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_RICEVUTA_CONSEGNA)){
							if(!formato.equals(AbstractSingleFileExporter.FORMATO_PDF) && !formato.equals(AbstractSingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la Comunicazione di tipo Ricevuta Consegna.");

							// [TODO] Bussu
							NotificaDTSingleFileExporter dtsfe = (NotificaDTSingleFileExporter) sfe;
							NotificaDecorrenzaTermini dt = dtsfe.convertToObject(ids[0]);

							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(AbstractSingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = dtsfe.exportAsPdf(dt, baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(AbstractSingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = dtsfe.exportAsRaw(dt, baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}  

						byte [] buffer = baos.toByteArray();
						ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
						response.addHeader("Content-Length", ""+ buffer.length);
						response.flushBuffer();
						Utils.copy(bais, response.getOutputStream()); 
						return ; 
					}

					throw new ExportException("Action non presente");
				}
			} 

			// Utente non autorizzato
			throw new ExportException("Autenticazione Richiesta");
		}
		catch(ExportException e){
			FattureExporter.log.debug(e.getMessage(),e);
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
			FattureExporter.log.error(e,e);
			ByteArrayOutputStream baos=  new ByteArrayOutputStream();

			response.setContentType("text/plain");					
			response.addHeader("Cache-Control", "private");
			response.setStatus(500);
			// committing status and headers
			response.flushBuffer();
			StringBuilder sb = new StringBuilder();
			sb.append("Si e' verificato un errore durante l'export delle risorse selezionate.");
			baos.write(sb.toString().getBytes());
			byte [] buffer = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

			Utils.copy(bais, response.getOutputStream()); 
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

	private AbstractSingleFileExporter<?, ?> getSingleFileExporter(String action) throws Exception {

		if(action.equals(PARAMETRO_ACTION_FATTURA)) {
			return new FatturaSingleFileExporter(log);
		} else if(action.equals(PARAMETRO_ACTION_ALLEGATO)) { 
			return new AllegatoSingleFileExporter(log);
		} else if(action.equals(PARAMETRO_ACTION_NOTIFICA_EC)) {
			return new NotificaECSingleFileExporter(log);
		} else if(action.equals(PARAMETRO_ACTION_SCARTO)) {
			return new ScartoECSingleFileExporter(log);
		} else if(action.equals(PARAMETRO_ACTION_NOTIFICA_DT)) {
			return new NotificaDTSingleFileExporter(log);
		} else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_ATTESTAZIONE_TRASMISSIONE_FATTURA)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_FATTURA_USCITA)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_ESITO_COMMITTENTE)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_MANCATA_CONSEGNA)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_SCARTO)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		}  else if(action.equals(PARAMETRO_ACTION_COMUNICAZIONE_RICEVUTA_CONSEGNA)) {
			// [TODO] Bussu
			throw new ExportException("Non disponibile");
		} else {
			throw new ExportException("Si e' verificato un errore durante l'export: Tipo di risorsa richiesta ["+action+"] non disponibile.");
		}
	}
}
