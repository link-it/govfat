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

import java.util.Date;

import javax.ws.rs.core.Response;
import org.govmix.proxy.fatturapa.web.commons.utils.ProtocollazioneUtils;

public class EndpointEnteForzaInvioImpl extends EndpointEnteImpl implements EndpointEnteForzaInvio {
	
	private ProtocollazioneUtils utils;
	
	public EndpointEnteForzaInvioImpl() throws Exception {
		super();
		this.utils = new ProtocollazioneUtils(this.log);
		
	}
	@Override
	public Response elencoLottiRicevuti(String codiceUfficio,
			Date dataIniziale, Date dataFinale) {
		String lst;
		try {
			this.log.info("Invoke elencoFattureRicevute");
			lst = this.recuperaFatture.cercaLottiRicevuti(codiceUfficio, dataIniziale, dataFinale); //Percodice ufficio, intervallo date
		} catch(Exception e) {
			this.log.error("elencoFattureRicevute completata con errore: "+e.getMessage(), e);
			return Response.status(500).build();
		}
		this.log.info("elencoFattureRicevute completata con successo");
		return Response.ok(lst).build();
	}


	@Override
	public Response forzaInvioLotto(Long idSdI, String consegnaDocumentiAccessori) {
		//forzaInvioLotto
		try {
			boolean consegna;
			if(consegnaDocumentiAccessori != null) {
				consegna = Boolean.parseBoolean(consegnaDocumentiAccessori);
			} else {
				consegna = false;
			}

			if(this.utils.forzaInvioLotto(idSdI, consegna)){
				return Response.status(200).build();
			} else {
				return Response.status(500).build();				
			}
		} catch(Exception e) {
			return Response.status(500).build();
		}
	}


	@Override
	public Response elencoFattureRicevute(Long idSdI) {
		String lst;
		try {
			this.log.info("Invoke elencoFattureRicevute");
			lst = this.recuperaFatture.cercaFattureRicevute(idSdI);
		} catch(Exception e) {
			this.log.error("elencoFattureRicevute completata con errore: "+e.getMessage(), e);
			return Response.status(500).build();
		}
		this.log.info("elencoFattureRicevute completata con successo");
		return Response.ok(lst).build();

	}


	@Override
	public Response forzaInvioFattura(Long idSdI, Integer posizione, String consegnaDocumentiAccessori) {
		//forzaInvioFattura
		this.log.info("Invoke forzaInvioFattura");
		try {
			boolean consegna;
			if(consegnaDocumentiAccessori != null) {
				consegna = Boolean.parseBoolean(consegnaDocumentiAccessori);
			} else {
				consegna = false;
			}
			
			if(this.utils.forzaInvioFattura(idSdI, posizione, consegna)){
				this.log.info("Invoke forzaInvioFattura completata con successo");
				return Response.status(200).build();
			} else {
				this.log.info("Invoke forzaInvioFattura completata con errore");
				return Response.status(500).build();				
			}
		} catch(Exception e) {
			this.log.info("Invoke forzaInvioFattura completata con errore:" + e.getMessage(), e);
			return Response.status(500).build();
		}
	}

}
