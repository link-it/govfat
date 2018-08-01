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
package org.govmix.proxy.fatturapa.web.commons.sonde;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.web.commons.sonde.RisultatoSonda.STATO;

public class ClientSondaInterna {

	private String url;
	private String username;
	private String password;
	private String name;
	private Logger log;

	public ClientSondaInterna(String url, String username, String password, String name, Logger log) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.name = name;
		this.log = log;
	}

	public RisultatoSonda invoke(HttpServletRequest req) {
		try {
			this.log.info("Invocazione sonda per il servizio ["+this.name+"]");
			HttpURLConnection conn = (HttpURLConnection) (new URL(this.url).openConnection());
			if(this.username !=null && !this.username.isEmpty() && 
					this.password !=null && !this.password.isEmpty() ) {
				String auth =  this.username + ":" +  this.password; 
				String authentication = "Basic " + Base64.encode(auth.getBytes());
				conn.setRequestProperty("Authorization", authentication);
			}

			conn.setDoInput(true);
			int rc = conn.getResponseCode();
			this.log.info("Invocazione sonda per il servizio ["+this.name+"]. ResponseCode ["+rc+"]");
			if(rc > 299) {
				return new RisultatoSonda(STATO.KO, "Servizio ["+this.name+"] non disponibile. Response Code["+rc+"]");
			}
				

			String response = IOUtils.toString(conn.getInputStream());

			this.log.info("Invocazione sonda per il servizio ["+this.name+"]. Response["+response+"]");
			if(response == null)
				return new RisultatoSonda(STATO.KO, "Servizio ["+this.name+"] non disponibile. Risposta nulla");

			return RisultatoSonda.parse(response);

		} catch(Exception e) {
			this.log.error("Errore durante l'invocazione della sonda per il servizio ["+this.name+"]:"+e.getMessage(), e);
			return new RisultatoSonda(STATO.KO, "Sistema non disponibile");
		}
	}

}
