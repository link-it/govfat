/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.generic_project.exception.DeserializerException;
import org.openspcoop2.generic_project.exception.SerializerException;

import it.gov.fatturapa.sdi.messaggi.v1_0.NotificaEsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.ObjectFactory;
import it.gov.fatturapa.sdi.messaggi.v1_0.RiferimentoFatturaType;
import it.gov.fatturapa.sdi.messaggi.v1_0.ScartoEsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.constants.EsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbDeserializer;
import it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbSerializer;

public class InvioNotifica {

	private static final String NOME_FILE_URL_PARAM = "NomeFile";

	private JaxbSerializer serializer;
	private JaxbDeserializer deserializer;
	private ObjectFactory of;

	private URL url;
	private String username;
	private String password;
	
	public InvioNotifica(URL url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
		
		this.serializer = new JaxbSerializer();
		this.of = new ObjectFactory();
		this.deserializer = new JaxbDeserializer();
	}
	
	
	public NotificaECResponse invia(NotificaECRequest request) throws IOException, SerializerException, DeserializerException {
		
		NotificaEsitoCommittenteType nec = new NotificaEsitoCommittenteType();
		
		NotificaECResponse response = new NotificaECResponse();
		
		EsitoCommittenteType esito;
		switch(request.getNotifica().getEsito()) {
		case EC01: esito = EsitoCommittenteType.EC01;
		break;
		case EC02: esito = EsitoCommittenteType.EC02;
		break;
		default: esito = EsitoCommittenteType.EC02;
		break;

		}
		nec.setEsito(esito);

		nec.setDescrizione(request.getNotifica().getDescrizione());
		nec.setIdentificativoSdI(request.getNotifica().getIdentificativoSdi());

		nec.setMessageIdCommittente(request.getNotifica().getMessageIdCommittente());
		nec.setVersione("1.0");

		if(request.getNotifica().getIdFattura() != null) {
			RiferimentoFatturaType riferimentoFattura = new RiferimentoFatturaType();
			riferimentoFattura.setAnnoFattura(request.getNotifica().getAnno());
			riferimentoFattura.setNumeroFattura(request.getNotifica().getNumeroFattura());
			riferimentoFattura.setPosizioneFattura(request.getNotifica().getIdFattura().getPosizione());
	
			nec.setRiferimentoFattura(riferimentoFattura);
		}

		Date dataSpedizione = new Date();
		byte[] notificaXML = this.serializer.toByteArray(this.of.createNotificaEsitoCommittente(nec));

		URL url = new URL(this.url.toString() + "?" + NOME_FILE_URL_PARAM + "=" + request.getNotifica().getNomeFile());
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
		httpConn.getOutputStream().write(notificaXML);
		httpConn.getOutputStream().close();

		int responseCode = httpConn.getResponseCode();
		response.setEsitoChiamata(responseCode);

		TracciaSDI tracciaNotifica = new TracciaSDI();

		tracciaNotifica.setIdentificativoSdi(request.getNotifica().getIdentificativoSdi());
		if(request.getNotifica().getIdFattura()!=null)
			tracciaNotifica.setPosizione(request.getNotifica().getIdFattura().getPosizione());
		
		tracciaNotifica.setTipoComunicazione(TipoComunicazioneType.EC);
		tracciaNotifica.setData(dataSpedizione);
		tracciaNotifica.setContentType("text/xml");
		tracciaNotifica.setNomeFile(request.getNotifica().getNomeFile());
		tracciaNotifica.setRawData(notificaXML);
		
		tracciaNotifica.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
		tracciaNotifica.setTentativiProtocollazione(0);
		tracciaNotifica.setDataProssimaProtocollazione(new Date());
		
		
		for(String k: httpConn.getHeaderFields().keySet()) {
			LoggerManager.getBatchStartupLogger().debug("RESPONSE HEADER ESITO COMMITTENTE ["+k+"] -> ["+httpConn.getHeaderFields().get(k)+"]");
		}
		
		String idEgov = "--";
		String idEgovHeader;
		try {
			idEgovHeader = CommonsProperties.getInstance(LoggerManager.getBatchStartupLogger()).getIdEgovHeader();
			if(httpConn.getHeaderFields().containsKey(idEgovHeader))
				idEgov = httpConn.getHeaderFields().get(idEgovHeader).get(0);
		} catch (Exception e) {}
		
		tracciaNotifica.setIdEgov(idEgov);

		response.setTracciaNotifica(tracciaNotifica);
		
		if(responseCode == 200) { //leggo lo scarto

			byte[] scartoXML = IOUtils.toByteArray(httpConn.getInputStream());
			ScartoEsitoCommittenteType scarto = deserializer.readScartoEsitoCommittenteType(scartoXML);
			IOUtils.closeQuietly(httpConn.getInputStream());

			TracciaSDI tracciaScarto = new TracciaSDI();

			tracciaScarto.setIdentificativoSdi(request.getNotifica().getIdentificativoSdi());
			
			if(request.getNotifica().getIdFattura()!=null)
				tracciaScarto.setPosizione(request.getNotifica().getIdFattura().getPosizione());
			
			tracciaScarto.setTipoComunicazione(TipoComunicazioneType.SE);
			tracciaScarto.setData(dataSpedizione);
			tracciaScarto.setContentType("text/xml");
			tracciaScarto.setNomeFile(request.getNotifica().getNomeFile() + "-SE.xml"); //TODO scarto?
			tracciaScarto.setRawData(scartoXML);
			
			tracciaScarto.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
			tracciaScarto.setTentativiProtocollazione(0);
			tracciaScarto.setDataProssimaProtocollazione(new Date());
			tracciaScarto.setIdEgov(idEgov);

			response.setTracciaScarto(tracciaScarto);
			response.setScarto(scarto);
		}
		
		return response;

	}

}
