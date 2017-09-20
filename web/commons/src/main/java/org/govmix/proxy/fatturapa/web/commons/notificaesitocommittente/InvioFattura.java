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
package org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.EsitoInvioFattura.ESITO;
import org.openspcoop2.generic_project.exception.DeserializerException;
import org.openspcoop2.generic_project.exception.SerializerException;

public class InvioFattura {

	private URL url;
	private String username;
	private String password;
	private Logger log;
	
	public InvioFattura(URL url, String username, String password, Logger log) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.log = log;
	}
	
	
	public EsitoInvioFattura invia(LottoFatture lotto) throws IOException, SerializerException, DeserializerException {

		URL url = new URL(this.url.toString() + "?TipoFile="+lotto.getFormatoArchivioInvioFattura()+"&Versione="+lotto.getFormatoTrasmissione()+"&IdPaese="+lotto.getCedentePrestatorePaese()+"&IdCodice="+lotto.getCedentePrestatoreCodice());
		URLConnection conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;

		if(this.username != null && !"".equals(this.username)
				&&
			this.password != null && !"".equals(this.password)) {
			String auth =  this.username + ":" +  this.password; 
			String authentication = "Basic " + Base64.encode(auth.getBytes());
			httpConn.setRequestProperty("Authorization", authentication);
		}

		String ct = null;

		ct = getContentType(lotto);
		
		httpConn.setRequestProperty("Content-Type", ct);

		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		httpConn.setRequestMethod("POST");
		httpConn.getOutputStream().write(lotto.getXml());
		httpConn.getOutputStream().close();

		int responseCode = httpConn.getResponseCode();

		EsitoInvioFattura esito = new EsitoInvioFattura();
		
		this.log.info("Invio della fattura ["+lotto.getIdentificativoSdi()+"] completato con responseCode ["+responseCode+"]");
		if(responseCode < 299) {
			esito.setEsito(ESITO.OK);
		} else {
			esito.setEsito(ESITO.KO);
		}
		
		esito.setMetadati(httpConn.getHeaderFields());
		
		return esito;

	}


	public static String getContentType(LottoFatture lotto) {
		switch(lotto.getFormatoArchivioInvioFattura()) {
		case P7M: return "application/pkcs7-mime";
		case XML: return "text/xml";
		default:
			break;
		}
		return null;
	}

}
