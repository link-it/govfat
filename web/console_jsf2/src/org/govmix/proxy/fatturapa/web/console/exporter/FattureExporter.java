/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.mbean.LoginMBean;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.expression.IExpression;
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

	public static final String PARAMETRO_ACTION_FATTURA = SingleFileExporter.PARAMETRO_ACTION_FATTURA;
	public static final String PARAMETRO_ACTION_ALLEGATO = SingleFileExporter.PARAMETRO_ACTION_ALLEGATO;
	public static final String PARAMETRO_ACTION_NOTIFICA_EC = SingleFileExporter.PARAMETRO_ACTION_NOTIFICA_EC;
	public static final String PARAMETRO_ACTION_NOTIFICA_DT = SingleFileExporter.PARAMETRO_ACTION_NOTIFICA_DT;
	public static final String PARAMETRO_ACTION_SCARTO = SingleFileExporter.PARAMETRO_ACTION_SCARTO;

	public static final String FATTURE_EXPORTER = "pages/fattureexporter";


	@Override
	public void init() throws ServletException {
		super.init();

		log.debug("Init Servlet FattureExporter completato.");
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
			IExpression expressionFromSearch = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpSession sessione = req.getSession(); 
			String username = null;
			LoginMBean lb = (LoginMBean) sessione.getAttribute("loginBean");

			log.debug("LoginBean trovato in sessione ["+(lb!= null)+"]"); 

			// L'utente per fare l'export delle fatture deve essere loggato
			if(lb != null){
				// utente loggato
				log.debug("Utente Loggato: ["+(lb.getIsLoggedIn())+"]"); 
				if(lb.getIsLoggedIn()){

					String isAllString = req.getParameter(PARAMETRO_IS_ALL);
					Boolean isAll = Boolean.parseBoolean(isAllString);
					String idFatture=req.getParameter(PARAMETRO_IDS);
					String[] ids = StringUtils.split(idFatture, ",");
					String formato=req.getParameter(PARAMETRO_FORMATO);
					String action =req.getParameter(PARAMETRO_ACTION);
					username = lb.getUsername();

					if(ids == null || (ids != null && ids.length == 0))
						throw new ExportException("Si e' verificato un errore durante l'export: Formato parametri errato.");

					if(action != null){
						// controllo del tipo di risorsa richiesta
						if(!action.equals(PARAMETRO_ACTION_FATTURA) && !action.equals(PARAMETRO_ACTION_ALLEGATO) 
								&& !action.equals(PARAMETRO_ACTION_NOTIFICA_EC) && !action.equals(PARAMETRO_ACTION_NOTIFICA_DT) && !action.equals(PARAMETRO_ACTION_SCARTO))
							throw new ExportException("Si e' verificato un errore durante l'export: Tipo di risorsa richiesta non disponibile.");

						SingleFileExporter sfe = new SingleFileExporter(log);
						String fileName = null;

						response.addHeader("Cache-Control", "private");
						response.setStatus(200);
						//						response.setBufferSize(1024);
						if(isAll){
							try{
								ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
								FatturaElettronicaService service = (FatturaElettronicaService)context.getBean("fatturaElettronicaService");

								FatturaElettronicaSearchForm sfInSession = (FatturaElettronicaSearchForm)context.getBean("fatturaElettronicaSearchForm");
								FatturaElettronicaSearchForm form2 = (FatturaElettronicaSearchForm) sfInSession.clone();

								expressionFromSearch = service.getExpressionFromSearch(sfe.getFatturaSearchDAO(), form2);

							}catch(Exception e){
								log.error("Si e' verificato un errore durante l'impostazione dei criteri di ricerca: "+ e.getMessage(),e);
								throw new ExportException("Si e' verificato un errore durante l'export della risorsa selezionata.");
							}
						}

						boolean autorizzato = sfe.checkautorizzazioneExport(username, ids, action, isAll, expressionFromSearch);

						// utente non autorizzato ad accerdere alla risorsa
						if(!autorizzato){
							StringBuilder sb = new StringBuilder();
							sb.append("Autenticazione Richiesta");
							if(username != null)
								sb.append(": Utente [").append(username).append("] non dispone dei permessi necessari per accedere alla risorsa.");
							throw new ExportException(sb.toString());
						}

						// Export Fattura
						if(action.equals(PARAMETRO_ACTION_FATTURA)){
							// formato di export della fattura non valido
							if(!formato.equals(SingleFileExporter.FORMATO_ZIP_CON_ALLEGATI) && !formato.equals(SingleFileExporter.FORMATO_PDF) 
									&& !formato.equals(SingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo fattura.");

							//Export Completo
							if(formato.equals(SingleFileExporter.FORMATO_ZIP_CON_ALLEGATI)){
								response.setContentType("application/zip");
								fileName = SingleFileExporter.BASE_DIR_NAME + ".zip";
								// Tutte le fatture
								if(isAll){
									sfe.exportAsZip(expressionFromSearch,baos);
								}else{
									//
									List<String> idFattura = new ArrayList<String>();
									for (int j = 0; j < ids.length; j++) {
										idFattura.add(ids[j]);					
									}
									sfe.exportAsZipFromListaId(idFattura,baos,true);
								}		
							}

							// Visualizzazione Singola Fattura PDF
							if(formato.equals(SingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = sfe.exportFatturaAsPdf(ids[0],baos);
							}

							// Visualizzazione Singola Fattura XML
							if(formato.equals(SingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = sfe.exportFatturaAsXml(ids[0], baos);
							}

							response.addHeader("Content-Type", "x-download");
							response.addHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						} else if(action.equals(PARAMETRO_ACTION_ALLEGATO)){
							fileName = sfe.exportAllegato(ids[0], baos);
							response.setContentType("application/x-download");
							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						}else if(action.equals(PARAMETRO_ACTION_NOTIFICA_DT)){
							if(!formato.equals(SingleFileExporter.FORMATO_PDF) && !formato.equals(SingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo Notifica DT.");
							// Visualizzazione Notifica DT formato PDF
							if(formato.equals(SingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = sfe.exportNotificaDTAsPdf(ids[0], baos);
							}

							// Visualizzazione NotificaDT formato XML
							if(formato.equals(SingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = sfe.exportNotificaDTAsXml(ids[0], baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						} else if(action.equals(PARAMETRO_ACTION_NOTIFICA_EC)){
							if(!formato.equals(SingleFileExporter.FORMATO_PDF) && !formato.equals(SingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo Notifica EC.");
							// Visualizzazione Notifica EC formato PDF
							if(formato.equals(SingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = sfe.exportNotificaECAsPdf(ids[0], baos);
							}

							// Visualizzazione NotificaEC formato XML
							if(formato.equals(SingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = sfe.exportNotificaECAsXml(ids[0], baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
							//							response.flushBuffer();

						} else if(action.equals(PARAMETRO_ACTION_SCARTO)){
							if(!formato.equals(SingleFileExporter.FORMATO_PDF) && !formato.equals(SingleFileExporter.FORMATO_XML))
								throw new ExportException("Si e' verificato un errore durante l'export: Il formato richiesto non e' disponibile per la risorsa di tipo Scarto Notifica EC.");
							// Visualizzazione Scarto formato PDF
							if(formato.equals(SingleFileExporter.FORMATO_PDF)){
								response.setContentType("application/pdf");
								fileName = sfe.exportScartoNoteECAsPdf(ids[0], baos);
							}

							// Visualizzazione Scarto formato XML
							if(formato.equals(SingleFileExporter.FORMATO_XML)){
								response.setContentType("text/xml");
								fileName = sfe.exportScartoNoteECAsXml(ids[0], baos);
							}

							response.setHeader("Content-Disposition", "attachment; filename="+fileName);
							// committing status and headers
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
}
