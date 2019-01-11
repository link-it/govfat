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
package org.govmix.proxy.fatturapa.web.commons.sonde;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.sonde.RisultatoSonda.STATO;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public abstract class AbstractServletSonda extends HttpServlet {

	protected static final long serialVersionUID = 1L;
	
	protected Logger log;
	protected FatturaBD fatturaElettronicaBD;
	private List<InvocationUrlInfo> urls;

	public AbstractServletSonda() throws Exception {
		this(null);
	}

	public AbstractServletSonda(List<InvocationUrlInfo> urls) throws Exception {
		this.urls = urls;
		this.fatturaElettronicaBD = new FatturaBD();
		this.log = LoggerManager.getSondaLogger();
	}

	protected RisultatoSonda gather(List<RisultatoSonda> lst) {
		RisultatoSonda ok = null;
		RisultatoSonda warn = null;
		RisultatoSonda ko = null;
		for(RisultatoSonda risultato: lst) {
			switch(risultato.getStato()) {
			case OK: ok = risultato;
				break;
			case WARN: warn = risultato;
				break;
			case KO: ko = risultato;
				break;
			}
		}
		
		if(ko != null)
			return ko;
		if(warn != null)
			return warn;
		if(ok != null) {
			ok.setDettaglio("Funzionalita' verificata alle ore ["+new Date()+"]");
			return ok;
		}
		
		return null;
		
	}
	

	protected void checkURL(InvocationUrlInfo urlInfo) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) (new URL(urlInfo.getUrl()).openConnection());
		
		if(urlInfo.getUsername() !=null && !urlInfo.getUsername().isEmpty() && 
				urlInfo.getPassword() !=null && !urlInfo.getPassword().isEmpty() ) {
			String auth =  urlInfo.getUsername() + ":" +  urlInfo.getPassword(); 
			String authentication = "Basic " + Base64.encode(auth.getBytes());
			conn.setRequestProperty("Authorization", authentication);
		}
		
		conn.setDoInput(true);
		int rc = conn.getResponseCode();
		if(rc > 299) {
			throw new SondaException("URL["+urlInfo.getUrl()+"] non disponibile. Response Code["+rc+"]");
		}
			
		return;
	}
	
	protected RisultatoSonda sonda(HttpServletRequest req) {
		RisultatoSonda risultato = new RisultatoSonda();
		try {
			if(!this.fatturaElettronicaBD.existFatture()) {
				throw new SondaException("Fatture non trovate");
			}
			
			if(this.urls != null && !this.urls.isEmpty()) {
				for(InvocationUrlInfo url: this.urls)
					checkURL(url);
			}
			
			risultato.setStato(STATO.OK);
			risultato.setDettaglio("Funzionalita' verificata alle ore ["+new Date()+"]");
		} catch(SondaException e) {
			this.log.error("Errore durante l'esecuzione della sonda: " +e.getMessage(),e);
			risultato.setStato(STATO.KO);
			risultato.setDettaglio(e.getMessage());
		} catch(Exception e) {
			this.log.error("Errore generico durante l'esecuzione della sonda: " +e.getMessage(),e);
			risultato.setStato(STATO.KO);
			risultato.setDettaglio("Sistema non disponibile");
		}
		return risultato;
	}

	

}
