package org.govmix.proxy.fatturapa.web.console.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.costanti.Costanti;
import org.govmix.proxy.fatturapa.web.console.mbean.FileUploadBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.richfaces.model.UploadItem;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FatturaElettronicaAttivaUploadServlet extends HttpServlet {

	public static final String FATTURA_ELETTRONICA_ATTIVA_UPLOAD_SERVLET_PATH= "/fatturaAttivaUpload";
	public static final String ID_TO_DELETE_PARAM_NAME= "id";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerManager.getConsoleLogger();

	@Override
	public void init() throws ServletException {
		super.init();
		FatturaElettronicaAttivaUploadServlet.log.debug("Init Servlet FatturaElettronicaAttivaUpload completato.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idRichiesta = (String) req.getAttribute(Costanti.UUID_INFO_PARAMETER_NAME);
		FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] DoGet!");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setStatus(200); 
		resp.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idRichiesta = (String) req.getAttribute(Costanti.UUID_INFO_PARAMETER_NAME);
		FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] DoPost!");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		FileUploadBean fileUploadBean = (FileUploadBean)context.getBean("fileUploadBean");
		Map<String, UploadItem> mapElementiRicevuti = fileUploadBean.getMapElementiRicevuti();
		Map<String, String> mapChiaviElementi = fileUploadBean.getMapChiaviElementi();

		String baseDeleteURL = req.getContextPath() + FATTURA_ELETTRONICA_ATTIVA_UPLOAD_SERVLET_PATH;

		try {
			String[] browserInfo = (String[]) req.getAttribute(Costanti.BROWSER_INFO_PARAMETER_NAME);

			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			String fileName = "";
			Iterator<FileItem> iter = multiparts.iterator();
			List<String> itemResp = new ArrayList<String>();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (!item.isFormField()) {
					if(item.getName().contains("\\")) {
						fileName = item.getName().substring(item.getName().lastIndexOf("\\"));
					} else if(item.getName().contains("/")) {
						fileName = item.getName().substring(item.getName().lastIndexOf("/"));
					} else {
						fileName = item.getName();
					}
					
					
					int dimensione = 0;
					String respBodyItem = "";

					try {
						byte[] contenuto = item.get();
						dimensione = contenuto != null ? contenuto.length : 0;
						String contentType = item.getContentType();
						FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] Ricevuto File ["+fileName+"], content-type ["+contentType+"], dimensione ["+dimensione+"]");
						UploadItem uploadItem = new UploadItem(fileName, dimensione, contentType, contenuto);

						// controllo duplicati?
						if(!mapElementiRicevuti.containsKey(fileName)) {
							String idFileRicevuto = UUID.randomUUID().toString().replaceAll("-", "");
							String deleteUrl = baseDeleteURL + "?"+ID_TO_DELETE_PARAM_NAME+"="+idFileRicevuto;
							mapChiaviElementi.put(idFileRicevuto, fileName); 
							mapElementiRicevuti.put(fileName, uploadItem);

							FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] File ["+fileName+"] non presente, aggiunto alla lista.");
							respBodyItem =  getUploadOkResponseItem(fileName,dimensione,idFileRicevuto,deleteUrl);
						} else {
							// duplicato
							FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] File ["+fileName+"] gia' presente, segnalo duplicato.");
							respBodyItem = getUploadKoResponseItem(fileName, dimensione, "File duplicato");
						}
					}catch(Exception e) {
						FatturaElettronicaAttivaUploadServlet.log.error("["+idRichiesta+"] Errore durante l'elaborazione del file ["+fileName+"]: " +e.getMessage(), e);
						respBodyItem = getUploadKoResponseItem(fileName, dimensione, "ERRORE");
					}

					itemResp.add(respBodyItem);
				}
			}

			String responseBody = getResponse(itemResp);
			FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] Response ["+responseBody+"].");
			ByteArrayInputStream bais = new ByteArrayInputStream(responseBody.getBytes());
			Utils.copy(bais, resp.getOutputStream());
			resp.setContentType(ContentType.APPLICATION_JSON.toString());

			if(browserInfo != null && browserInfo.length == 2) {
				String browsername = browserInfo[0];
				String browserversion = browserInfo[1];
				if(browsername.equalsIgnoreCase("MSIE") || browsername.equalsIgnoreCase("rv")|| browsername.equalsIgnoreCase("Trident")){
					if(browserversion != null) {
						double versione = -1d;
						try{
							versione = Double.parseDouble(browserversion);
						}catch(NumberFormatException e){
							// versione non riconosciuta
							versione = -1d;
						}

						if((int) versione == 8) {
							resp.setContentType(ContentType.TEXT_HTML.toString());
						}
					}
				}
			}


			resp.setStatus(200);	
		}catch(Exception e) {
			FatturaElettronicaAttivaUploadServlet.log.error("["+idRichiesta+"] Errore: " + e.getMessage(), e);
			resp.setStatus(500);
		} finally {
			resp.flushBuffer();	
		}
	}


	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idRichiesta = (String) req.getAttribute(Costanti.UUID_INFO_PARAMETER_NAME);
		FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] DoDelete!");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		List<String> itemResp = new ArrayList<String>();
		try {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			FileUploadBean fileUploadBean = (FileUploadBean)context.getBean("fileUploadBean");
			Map<String, UploadItem> mapElementiRicevuti = fileUploadBean.getMapElementiRicevuti();
			Map<String, String> mapChiaviElementi = fileUploadBean.getMapChiaviElementi();

			String idToDelete = req.getParameter(ID_TO_DELETE_PARAM_NAME);
			FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] Richiesta cancellazione del file id ["+idToDelete+"].");

			boolean statoDelete = true;
			String fileName = "";
			try {
				// rimozione elementi
				fileName = mapChiaviElementi.remove(idToDelete);
				mapElementiRicevuti.remove(fileName); 

			}catch(Throwable e) {
				FatturaElettronicaAttivaUploadServlet.log.error("["+idRichiesta+"] Errore durante la cancellazione del file id ["+idToDelete+"]: " +e.getMessage(), e);
			}

			String respBodyItem = getDeleteOkResponseItem(fileName, idToDelete, statoDelete);
			itemResp.add(respBodyItem);

			String responseBody = getResponse(itemResp);
			FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] Response ["+responseBody+"].");
			ByteArrayInputStream bais = new ByteArrayInputStream(responseBody.getBytes());
			Utils.copy(bais, resp.getOutputStream());
			resp.setContentType(ContentType.APPLICATION_JSON.toString());
			resp.setStatus(200);	
		}catch(Exception e) {
			FatturaElettronicaAttivaUploadServlet.log.error("["+idRichiesta+"] Errore: " + e.getMessage(), e);
			resp.setStatus(500);
		} finally {
			resp.flushBuffer();	
		}
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idRichiesta = (String) req.getAttribute(Costanti.UUID_INFO_PARAMETER_NAME);
		FatturaElettronicaAttivaUploadServlet.log.debug("["+idRichiesta+"] DoOptions!");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Range, Content-Disposition, Content-Description, origin, accept, authorization");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, DELETE, POST");
		resp.setStatus(200); 
		resp.flushBuffer();
	}

	/* 
	Response Ok
		{"files": [
	  {
	    "name": "picture1.jpg",
	    "size": 902604,
	    "url": "http:\/\/example.org\/files\/picture1.jpg",
	    "thumbnailUrl": "http:\/\/example.org\/files\/thumbnail\/picture1.jpg",
	    "deleteUrl": "http:\/\/example.org\/files\/picture1.jpg",
	    "deleteType": "DELETE"
	  },
	  {
	    "name": "picture2.jpg",
	    "size": 841946,
	    "url": "http:\/\/example.org\/files\/picture2.jpg",
	    "thumbnailUrl": "http:\/\/example.org\/files\/thumbnail\/picture2.jpg",
	    "deleteUrl": "http:\/\/example.org\/files\/picture2.jpg",
	    "deleteType": "DELETE"
	  }
	]}
	 */
	public static String getUploadOkResponseItem(String itemName, int dimensione, String idFileRicevuto, String deleteURL) {
		JSONObject j = new JSONObject();
		j.put("name", itemName);
		j.put("size", dimensione);
		j.put("url", "");
		j.put("thumbnailUrl", "");
		j.put("id", idFileRicevuto);
		j.put("deleteUrl", deleteURL);
		j.put("deleteType", "DELETE");
		

		return j.toString();
	}
	
	/* 
Response Error
{"files": [
  {
    "name": "picture1.jpg",
    "size": 902604,
    "error": "Filetype not allowed"
  },
  {
    "name": "picture2.jpg",
    "size": 841946,
    "error": "Filetype not allowed"
  }
]}
	 */
	public static String getUploadKoResponseItem(String itemName, int dimensione, String errorString) {
		
		JSONObject j = new JSONObject();
		j.put("name", itemName);
		j.put("size", dimensione);
		j.put("error", errorString);
		

		return j.toString();

	}

	/* risposta delete
	 * {"files": [
  {
    "picture1.jpg": true
  },
  {
    "picture2.jpg": true
  }
]}
	 * 
	 */
	public static String getDeleteOkResponseItem(String itemName, String idFileRicevuto, boolean stato) {
		
		JSONObject j = new JSONObject();
		j.put(itemName, stato);
		j.put("id", idFileRicevuto);
		

		return j.toString();

	}

	public static String getResponse(List<String> itemResp) {
		
		JSONObject j = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(itemResp);
		j.put("files", jsonArray);
		

		return j.toString();

	}
}
