/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente;

import it.gov.fatturapa.sdi.messaggi.v1_0.NotificaEsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.ObjectFactory;
import it.gov.fatturapa.sdi.messaggi.v1_0.ScartoEsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbDeserializer;
import it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbSerializer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.openspcoop2.generic_project.exception.DeserializerException;
import org.openspcoop2.generic_project.exception.SerializerException;

import org.apache.soap.encoding.soapenc.Base64;

public class InvioNotifica {

	private static final String NOME_FILE_URL_PARAM = "NomeFile";
	private static final String IDENTIFICATIVO_SDI_URL_PARAM = "IdentificativoSdI";

	private JaxbSerializer serializer;
	private JaxbDeserializer deserializer;
	private ObjectFactory of;

	private URL url;
	private String username;
	private String password;
	
	private int esitoChiamata;
	private byte[] notificaXML;
	private byte[] scartoXML;
	private ScartoEsitoCommittenteType scarto;
	
	
	public InvioNotifica(URL url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
		
		this.serializer = new JaxbSerializer();
		this.of = new ObjectFactory();
		this.deserializer = new JaxbDeserializer();
	}
	
	
	public void invia(NotificaEsitoCommittenteType nec, String nomeFile) throws IOException, SerializerException, DeserializerException {
		this.notificaXML = this.serializer.toByteArray(this.of.createNotificaEsitoCommittente(nec));

		URL url = new URL(this.url.toString() + "?" + NOME_FILE_URL_PARAM + "=" + nomeFile + "&" + IDENTIFICATIVO_SDI_URL_PARAM + "=" + nec.getIdentificativoSdI());
		URLConnection conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;

		if(this.username != null && !"".equals(this.username)
				&&
			this.password != null && !"".equals(this.password)) {
			String auth =  this.username + ":" +  this.password; 
			String authentication = "Basic " + Base64.encode(auth.getBytes());
			httpConn.setRequestProperty("Authorization", authentication);
		}

		httpConn.setRequestProperty("Content-Type", "text/xml");

		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		httpConn.setRequestMethod("POST");
		httpConn.getOutputStream().write(this.notificaXML);
		httpConn.getOutputStream().close();

		this.esitoChiamata = httpConn.getResponseCode();

		if(this.esitoChiamata == 200) { //leggo lo scarto
			
			this.scartoXML = IOUtils.toByteArray(httpConn.getInputStream());
			IOUtils.closeQuietly(httpConn.getInputStream());
			this.scarto = deserializer.readScartoEsitoCommittenteType(scartoXML);
		}

	}


	public int getEsitoChiamata() {
		return this.esitoChiamata;
	}


	public byte[] getScartoXML() {
		return this.scartoXML;
	}

	public byte[] getNotificaXML() {
		return this.notificaXML;
	}

	public ScartoEsitoCommittenteType getScarto() {
		return this.scarto;
	}

}
