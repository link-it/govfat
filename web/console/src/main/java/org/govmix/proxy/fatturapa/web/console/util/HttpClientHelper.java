/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;


/**
 * HttpClientHelper client per l'invocazione dei servizi.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class HttpClientHelper {


	private CloseableHttpClient httpclient = null;
	private String url = null;

	private Logger log = null;   


	public static HttpClientHelper getInstance(Logger log, String url){
		return new HttpClientHelper(log,url);
	}

	public HttpClientHelper(Logger log,String url){
		try{
			this.log = log;
			this.url = url;
			this .httpclient = HttpClientBuilder.create().build();
		}catch(Exception e){
			log.error("Errore durante la creazione del cliente http",e); 
		}
	}
	
	public <T> void sendNoResponse(T obj, Class<T> objClass, ContentType contentType, String username, String password) throws Exception{
		URL urlObj = new URL(this.url);
		HttpHost target = new HttpHost(urlObj.getHost(), urlObj.getPort(), urlObj.getProtocol());
		
		HttpPost richiestaPost = new HttpPost();
		
		richiestaPost.setURI(urlObj.toURI());
		
		byte[] buffer = HttpClientHelper.serializzaOggetto(obj, objClass);
		
		HttpEntity bodyEntity = new InputStreamEntity(new ByteArrayInputStream(buffer),contentType);
		richiestaPost.setEntity(bodyEntity);
		richiestaPost.setHeader("Content-Type", contentType.getMimeType());
		
		String authentication = null;
		if(username != null && password != null){
			String auth =  username + ":" + password; 
			  authentication = "Basic " + Base64.encode(auth.getBytes());
		}
		
		if(authentication != null)
			richiestaPost.setHeader("Authorization", authentication);
		
		HttpResponse responseGET = this.httpclient.execute(target, richiestaPost);
		
		int responseCode = responseGET.getStatusLine().getStatusCode();
		
		this.log.debug("Invio completato ricevuto response code ["+responseCode+"]");
		
		if(responseCode >= 300)
			throw new Exception("Invio completato con codice di errore ["+responseCode+"]");
	 
	}
	
	public <T, RT> RT send(T obj, Class<T> objClass, Class<RT> returnObjClass,ContentType contentType, String username, String password) throws Exception{
		RT returnObj = null;
		URL urlObj = new URL(this.url);
		HttpHost target = new HttpHost(urlObj.getHost(), urlObj.getPort(), urlObj.getProtocol());
		
		HttpPost richiestaPost = new HttpPost();
		
		richiestaPost.setURI(urlObj.toURI());
		
		byte[] buffer = HttpClientHelper.serializzaOggetto(obj, objClass);
		
		HttpEntity bodyEntity = new InputStreamEntity(new ByteArrayInputStream(buffer),contentType);
		richiestaPost.setEntity(bodyEntity);
		richiestaPost.setHeader("Content-Type",contentType.getMimeType());
		
		String authentication = null;
		if(username != null && password != null){
			String auth =  username + ":" + password; 
			  authentication = "Basic " + Base64.encode(auth.getBytes());
		}
		
		if(authentication != null)
			richiestaPost.setHeader("Authorization", authentication);
		
		HttpResponse responseGET = this.httpclient.execute(target, richiestaPost);
		
		int responseCode = responseGET.getStatusLine().getStatusCode();
		
		InputStream is = responseGET.getEntity().getContent();
		
		this.log.debug("Invio completato ricevuto response code ["+responseCode+"]");
		
//		if(responseCode >= 300)
//			throw new Exception("Invio completato con codice di errore ["+responseCode+"]");
		
		returnObj = HttpClientHelper.deserializzaOggetto(returnObjClass, is, this.log);
	 
		return returnObj;
	}
	
	
	public static <RT> RT deserializzaOggetto(Class<RT> objectClass ,InputStream is, Logger log){
		RT toReturn = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(objectClass);
			Unmarshaller u = jc.createUnmarshaller();
			
			Source xmlSource = new StreamSource(is) ;
			
			JAXBElement<RT> el = u.unmarshal(xmlSource,objectClass);
			toReturn = el.getValue();

		} catch (JAXBException e) {
			 log.error(e, e);
		}

		return toReturn;
	}

	public static <T> byte[] serializzaOggetto(T obj, Class<T> objClass) {
		byte[] toRet = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			JAXBContext context = JAXBContext.newInstance(objClass);

				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				
				m.marshal(obj,baos);
				
				baos.flush();
				toRet = baos.toByteArray();

		} catch (Exception e) {
		} finally{
			if(baos!= null){
				try{
				baos.close();
				}catch(Exception e ){}
			}
		}

		return toRet;
	}
	
	
}
