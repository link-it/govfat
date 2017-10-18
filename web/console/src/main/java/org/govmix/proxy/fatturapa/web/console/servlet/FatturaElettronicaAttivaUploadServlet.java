package org.govmix.proxy.fatturapa.web.console.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import org.govmix.proxy.fatturapa.web.console.mbean.FileUploadBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.richfaces.model.UploadItem;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class FatturaElettronicaAttivaUploadServlet extends HttpServlet {

	public static final String FATTURA_ELETTRONICA_ATTIVA_UPLOAD_SERVLET_PATH= "/fatturaAttivaUpload";

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
		FatturaElettronicaAttivaUploadServlet.log.debug("DoGet!");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setStatus(200); 
		resp.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FatturaElettronicaAttivaUploadServlet.log.debug("DoPost!");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		FileUploadBean fileUploadBean = (FileUploadBean)context.getBean("fileUploadBean");
		Map<String, UploadItem> mapElementiRicevuti = fileUploadBean.getMapElementiRicevuti();
		Map<String, String> mapChiaviElementi = fileUploadBean.getMapChiaviElementi();

		String deleteURL = req.getContextPath();

		try {
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			String fileName = "";
			Iterator<FileItem> iter = multiparts.iterator();
			List<String> itemResp = new ArrayList<String>();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (!item.isFormField()) {
					fileName = item.getName();
					int dimensione = 0;
					String respBodyItem = "";

					try {
						byte[] contenuto = item.get();
						dimensione = contenuto != null ? contenuto.length : 0;
						String contentType = item.getContentType();
						FatturaElettronicaAttivaUploadServlet.log.debug("Ricevuto File ["+fileName+"], content-type ["+contentType+"], dimensione ["+dimensione+"]");
						UploadItem uploadItem = new UploadItem(fileName, dimensione, contentType, contenuto);

						// controllo duplicati?
						if(!mapElementiRicevuti.containsKey(fileName)) {
							String idFileRicevuto = UUID.randomUUID().toString().replaceAll("-", ""); 
							mapChiaviElementi.put(idFileRicevuto, fileName); 
							mapElementiRicevuti.put(fileName, uploadItem);

							respBodyItem =  getOkResponseItem(fileName,dimensione,deleteURL);
						} else {
							// duplicato
							respBodyItem = getKoResponseItem(fileName, dimensione, "File duplicato");
						}
					}catch(Exception e) {
						FatturaElettronicaAttivaUploadServlet.log.error("Errore durante l'elaborazione del file ["+fileName+"]: " +e.getMessage(), e);
						respBodyItem = getKoResponseItem(fileName, dimensione, "ERRORE");
					}

					itemResp.add(respBodyItem);
				}
			}

			ByteArrayInputStream bais = new ByteArrayInputStream(getResponse(itemResp).getBytes());
			Utils.copy(bais, resp.getOutputStream());
			resp.setContentType(ContentType.APPLICATION_JSON.toString());
			resp.setStatus(200);	
		}catch(Exception e) {
			FatturaElettronicaAttivaUploadServlet.log.error(e.getMessage(), e);
			resp.setStatus(500);
		} finally {
			resp.flushBuffer();	
		}
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

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FatturaElettronicaAttivaUploadServlet.log.debug("DoDelete!");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		try {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			FileUploadBean fileUploadBean = (FileUploadBean)context.getBean("fileUploadBean");
			Map<String, UploadItem> mapElementiRicevuti = fileUploadBean.getMapElementiRicevuti();
			Map<String, String> mapChiaviElementi = fileUploadBean.getMapChiaviElementi();



			resp.setStatus(200);	
		}catch(Exception e) {
			FatturaElettronicaAttivaUploadServlet.log.error(e.getMessage(), e);
			resp.setStatus(500);
		} finally {
			resp.flushBuffer();	
		}
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
	public static String getOkResponseItem(String itemName, int dimensione, String deleteURL) {
		StringBuffer sb = new StringBuffer();
		sb.append("{ 'name' : '");
		sb.append(itemName);
		sb.append("' , 'size' : ");
		sb.append(dimensione);
		sb.append(", 'url' : ''");
		sb.append(", 'thumbnailUrl' : ''");
		sb.append(", 'deleteUrl' : '");
		sb.append(deleteURL);
		sb.append("' , 'deleteType' : 'DELETE'");
		sb.append("}");

		return sb.toString();
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
	public static String getKoResponseItem(String itemName, int dimensione, String errorString) {
		StringBuffer sb = new StringBuffer();
		sb.append("{ 'name' : '");
		sb.append(itemName);
		sb.append("' , 'size' : ");
		sb.append(dimensione);
		sb.append(", 'error' : '");
		sb.append(errorString);
		sb.append("' }");

		return sb.toString();
	}


	public static String getResponse(List<String> itemResp) {
		StringBuffer sb = new StringBuffer();
		sb.append("{'files' : ["); 
		boolean addComma = false; 
		for (String item : itemResp) {
			if(addComma)
				sb.append(" , "); 

			sb.append(item);
			addComma = true;
		}

		sb.append("]}");

		return sb.toString();
	}
}
