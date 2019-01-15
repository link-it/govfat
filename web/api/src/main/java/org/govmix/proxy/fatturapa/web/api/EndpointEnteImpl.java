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
package org.govmix.proxy.fatturapa.web.api;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.InvioNotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.web.commons.recuperaFatture.RecuperaFatture;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class EndpointEnteImpl implements EndpointEnte {
	
	@Context 
	private MessageContext context;

	private InvioNotificaEsitoCommittente invioNotificaEsitoCommittente;
	protected RecuperaFatture recuperaFatture;
	protected Logger log;

	public EndpointEnteImpl() throws Exception {
		this.log = LoggerManager.getEndpointEnteLogger();
		this.log.info("Inizializzazione endpoint Ente...");
		this.invioNotificaEsitoCommittente = new InvioNotificaEsitoCommittente(this.log);
		this.recuperaFatture = new RecuperaFatture(this.log);
		this.log.info("Inizializzazione endpoint Ente completata");
		
		this.log.info("Info versione: " + CommonsProperties.getInstance(log).getInfoVersione());
	}
	
	
	@Override
	public Response postRiceviNotificaEC(InputStream esitoStream) {
		try{
			this.log.info("Invoke riceviNotificaEC");
			if(esitoStream == null) {
				throw new Exception("L'esito committente ricevuto in ingresso non puo' essere null");
			}
			
			IdUtente idUtente = getIdUtente();
			
			this.invioNotificaEsitoCommittente.invia(esitoStream, idUtente);
		} catch (Exception e) {
			this.log.error("riceviNotificaEC completata con errore: "+ e.getMessage(), e);
			return Response.status(500).build();
		}
		
		this.log.info("riceviNotificaEC completata con successo");
		return Response.ok().build();
	}

	@Override
	public Response cercaFattureNonConsegnate(Integer limit) {
		String lst;
		try {
			this.log.info("Invoke listaFattureNonConsegnate");
			IdUtente idUtente = getIdUtente();
			Integer limitFatture = (limit != null) ? limit: WebApiProperties.getInstance().getLimitGetIdFatture(); 

			lst = this.recuperaFatture.cercaFattureNonConsegnate(idUtente, limitFatture);
		} catch(Exception e) {
			this.log.error("listaFattureNonConsegnate completata con errore: "+e.getMessage(), e);
			return Response.status(500).build();
		}
		this.log.info("listaFattureNonConsegnate completata con successo");
		return Response.ok(lst).build();
		
	}

	@Override
	public Response recuperaFatturaNonConsegnata(Integer idSdI, Integer posizione) {
		
		byte[] fattura;
		try {
			this.log.info("Invoke consegnaFattura");
			
			if(idSdI == null) {
				throw new Exception("Il parametro idSdI non puo' essere null");
			}
			
			if(posizione == null) {
				throw new Exception("Il parametro posizione non puo' essere null");
			}
			
			IdUtente idUtente = getIdUtente();
			
			IdFattura idFattura = recuperaFatture.newIdFattura();
			idFattura.setIdentificativoSdi(idSdI);
			idFattura.setPosizione(posizione);
			
			fattura = this.recuperaFatture.recuperaFatturaNonConsegnata(idUtente, idFattura);
		} catch(Exception e) {
			this.log.info("consegnaFattura completata con errore: "+ e.getMessage(), e);
			return Response.status(500).build();
		}
		this.log.info("consegnaFattura completata con successo");
		return Response.ok(fattura).build();

	}
	

	private IdUtente getIdUtente() throws Exception {
		HttpServletRequest req = context.getHttpServletRequest();
		
		if(req == null) {
			throw new Exception("HttpServletRequest is null");
		}
		
		List<String> principals = context.getHttpHeaders().getRequestHeader("PRINCIPAL_PROXY");
		
		if(principals != null && principals.size() > 0) {
			
			String principal = principals.get(0);
			this.log.info("Principal utente: " + principal);
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(principal);
			
			return idUtente;			
		} else {
			throw new Exception("Principal utente non trovato");
		}

	}



}
