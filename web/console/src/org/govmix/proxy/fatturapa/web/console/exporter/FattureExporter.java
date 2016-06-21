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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.util.Utils;

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

	public static final String PARAMETRO_ACTION_FATTURA = "f";
	public static final String PARAMETRO_ACTION_ALLEGATO = "a";
	public static final String PARAMETRO_ACTION_NOTIFICA_EC = "ec";
	public static final String PARAMETRO_ACTION_NOTIFICA_DT = "dt";
	public static final String PARAMETRO_ACTION_SCARTO = "sc";

	public static final String FATTURE_EXPORTER = "fattureexporter";
	

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

			String isAllString = req.getParameter(PARAMETRO_IS_ALL);
			Boolean isAll = Boolean.parseBoolean(isAllString);
			String idFatture=req.getParameter(PARAMETRO_IDS);
			String[] ids = StringUtils.split(idFatture, ",");
			String formato=req.getParameter(PARAMETRO_FORMATO);
			String action =req.getParameter(PARAMETRO_ACTION);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			if(action != null){
				SingleFileExporter sfe = new SingleFileExporter(log);
				String fileName = null;
				response.setContentType("x-download");
				response.addHeader("Cache-Control", "no-cache");
				response.setStatus(200);
				response.setBufferSize(1024);

				// Export Fattura
				if(action.equals(PARAMETRO_ACTION_FATTURA)){
					//Export Completo
					if(formato.equals(SingleFileExporter.FORMATO_ZIP_CON_ALLEGATI)){
						fileName = SingleFileExporter.BASE_DIR_NAME + ".zip";
						// Tutte le fatture
						if(isAll){
							sfe.exportAsZip(baos);
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
						fileName = sfe.exportFatturaAsPdf(ids[0],baos);
					}

					// Visualizzazione Singola Fattura XML
					if(formato.equals(SingleFileExporter.FORMATO_XML)){
						fileName = sfe.exportFatturaAsXml(ids[0], baos);
					}

					response.setHeader("Content-Disposition", "attachment; filename="+fileName);
					// committing status and headers
					response.flushBuffer();

				} else if(action.equals(PARAMETRO_ACTION_ALLEGATO)){
					fileName = sfe.exportAllegato(ids[0], baos);
					response.setHeader("Content-Disposition", "attachment; filename="+fileName);
					// committing status and headers
					response.flushBuffer();

				}else if(action.equals(PARAMETRO_ACTION_NOTIFICA_DT)){
					// Visualizzazione Notifica DT formato PDF
					if(formato.equals(SingleFileExporter.FORMATO_PDF)){
						fileName = sfe.exportNotificaDTAsPdf(ids[0], baos);
					}

					// Visualizzazione NotificaDT formato XML
					if(formato.equals(SingleFileExporter.FORMATO_XML)){
						fileName = sfe.exportNotificaDTAsXml(ids[0], baos);
					}

					response.setHeader("Content-Disposition", "attachment; filename="+fileName);
					// committing status and headers
					response.flushBuffer();

				} else if(action.equals(PARAMETRO_ACTION_NOTIFICA_EC)){
					// Visualizzazione Notifica EC formato PDF
					if(formato.equals(SingleFileExporter.FORMATO_PDF)){
						fileName = sfe.exportNotificaECAsPdf(ids[0], baos);
					}

					// Visualizzazione NotificaEC formato XML
					if(formato.equals(SingleFileExporter.FORMATO_XML)){
						fileName = sfe.exportNotificaECAsXml(ids[0], baos);
					}

					response.setHeader("Content-Disposition", "attachment; filename="+fileName);
					// committing status and headers
					response.flushBuffer();

				} else if(action.equals(PARAMETRO_ACTION_SCARTO)){
					// Visualizzazione Scarto formato PDF
					if(formato.equals(SingleFileExporter.FORMATO_PDF)){
						fileName = sfe.exportScartoNoteECAsPdf(ids[0], baos);
					}

					// Visualizzazione Scarto formato XML
					if(formato.equals(SingleFileExporter.FORMATO_XML)){
						fileName = sfe.exportScartoNoteECAsXml(ids[0], baos);
					}

					response.setHeader("Content-Disposition", "attachment; filename="+fileName);
					// committing status and headers
					response.flushBuffer();

				}else {
					response.setContentType("text/plain");					
					response.addHeader("Cache-Control", "no-cache");
					response.setStatus(500);
					// committing status and headers
					response.flushBuffer();
					baos.write("Action non valida".getBytes());
				}
			}else {
				response.setContentType("text/plain");					
				response.addHeader("Cache-Control", "no-cache");
				response.setStatus(500);
				// committing status and headers
				response.flushBuffer();
				baos.write("Action non presente".getBytes());
			}
			
			byte [] buffer = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
			
			Utils.copy(bais, response.getOutputStream()); 
			
		}catch(IOException se){
			FattureExporter.log.error(se,se);
			throw se;
		} catch(Exception e){
			FattureExporter.log.error(e,e);
			throw new ServletException(e);
		}
	}
}
