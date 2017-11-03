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
package org.govmix.proxy.fatturapa.web.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLotti;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoRequest;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse;
import org.govmix.proxy.fatturapa.web.commons.fatturaattiva.EsitoInvioFattura.ESITO;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.InvioNotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.web.commons.recuperaFatture.RecuperaFatture;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class EndpointEnteImpl implements EndpointEnte {
	
	@Context 
	private MessageContext context;

	private InvioNotificaEsitoCommittente invioNotificaEsitoCommittente;
	private RecuperaFatture recuperaFatture;
	private Logger log;
	private InserimentoLotti inserimento;

	public EndpointEnteImpl() throws Exception {
		this.log = LoggerManager.getEndpointEnteLogger();
		this.log.info("Inizializzazione endpoint Ente...");
		this.invioNotificaEsitoCommittente = new InvioNotificaEsitoCommittente(this.log);
		this.recuperaFatture = new RecuperaFatture(this.log);
		this.log.info("Inizializzazione endpoint Ente completata");
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
			
			IdFattura idFattura = new IdFattura();
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


	@Override
	public Response postRiceviFatturaAttiva(String fileName, String dipartimento, InputStream fatturaStream) {
		try {
			this.log.info("Invoke riceviFatturaAttiva");
			
			if(fileName == null) {
				throw new Exception("Il parametro fileName non puo' essere null");
			}
			
			if(dipartimento == null) {
				throw new Exception("Il parametro dipartimento non puo' essere null");
			}
			
			if(fatturaStream == null) {
				throw new Exception("Il parametro fatturaStream non puo' essere null");
			}
			
			InserimentoLotti inserimento = new InserimentoLotti(this.log);

			inserimento.setDipartimenti(new DipartimentoBD(log).findAll());
			List<InserimentoLottoRequest> requestList = new ArrayList<InserimentoLottoRequest>();
			InserimentoLottoRequest request = new InserimentoLottoRequest();

			request.setDipartimento(dipartimento);
			request.setNomeFile(fileName);
			request.setXml(IOUtils.toByteArray(fatturaStream));
			
			requestList.add(request);
			InserimentoLottoResponse inserisciLotto = inserimento.inserisciLotto(requestList);
			
			if(ESITO.OK.toString().equals(inserisciLotto.getEsito().toString())) {
				this.log.info("riceviFatturaAttiva completata con successo");
				return Response.ok(inserisciLotto.getLstIdentificativoEfatt().get(0).getIdentificativoSdi()).build();
			} else {
				throw inserisciLotto.getEccezione();
			}
		} catch(Exception e) {
			this.log.info("riceviFatturaAttiva completata con errore: "+ e.getMessage(), e);
			return Response.status(500).build();
		}
	}


}
