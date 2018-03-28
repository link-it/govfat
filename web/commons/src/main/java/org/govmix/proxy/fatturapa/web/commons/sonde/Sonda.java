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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.sonde.RisultatoSonda.STATO;

public class Sonda {

	private Map<String, ParametriSonda> statoBatch;

	private static Sonda instance;
	
	public static Sonda getInstance() {
		if(instance == null) {
			instance = new Sonda();
		}
		
		return instance;
	}
	
	private Sonda() {
		this.statoBatch = new HashMap<String, ParametriSonda>();
	}
	
	public List<RisultatoSonda> getStatoBatch(Logger log) throws Exception {
		List<RisultatoSonda> risultatiSonda = new ArrayList<RisultatoSonda>();
		for(String batch: this.statoBatch.keySet()) {
			RisultatoSonda risultatoSonda = getStatoBatch(batch, log);
			log.debug("Stato batch ["+batch+"] -> ["+risultatoSonda.getStato()+"]");
			risultatiSonda.add(risultatoSonda);
		}
		return risultatiSonda;
	}
	
	public RisultatoSonda getStatoBatch(String batch, Logger log) throws Exception {
		ParametriSonda parametriSonda = getParametriSonda(batch);
		
		log.debug("Parametri batch ["+batch+"] DataInizioOp["+parametriSonda.getDataInizioOp()+"]");
		log.debug("Parametri batch ["+batch+"] TempoError["+parametriSonda.getTempoError()+"]");
		log.debug("Parametri batch ["+batch+"] TempoWarning["+parametriSonda.getTempoWarning()+"]");

		RisultatoSonda risultato = new RisultatoSonda();
		
		if(org.govmix.proxy.fatturapa.web.commons.sonde.ParametriSonda.STATO.KO.equals(parametriSonda.getStato())) {
			risultato.setStato(STATO.KO);
			risultato.setDettaglio(parametriSonda.getDettaglio());
			return risultato;
		}
		
		Date dataInizioOp = parametriSonda.getDataInizioOp();
		Date now = new Date();
		Date dataWarning = new Date(now.getTime() - (parametriSonda.getTempoWarning()*1000)); 
		Date dataError = new Date(now.getTime() - (parametriSonda.getTempoError()*1000));

		log.debug("Diagnostica batch ["+batch+"] dataWarning["+dataWarning+"]");
		log.debug("Diagnostica batch ["+batch+"] dataError["+dataError+"]");
		
		
		if(parametriSonda.getTempoError() > 0 && dataInizioOp.before(dataError)) {
			String detail = "Batch ["+batch+"] eseguito per l'ultima volta in data ["+dataInizioOp+"]. Stato KO. Data limite per KO ["+dataError+"]";
			log.debug(detail);
			risultato.setStato(STATO.KO);
			risultato.setDettaglio(detail);
		} else if(parametriSonda.getTempoWarning() > 0 && dataInizioOp.before(dataWarning)) {
			String detail = "Batch ["+batch+"] eseguito per l'ultima volta in data ["+dataInizioOp+"]. Stato WARN. Data limite per WARN ["+dataWarning+"]";
			log.debug(detail);
			risultato.setStato(STATO.WARN);
			risultato.setDettaglio(detail);
		} else {
			String detail = "Batch ["+batch+"] eseguito per l'ultima volta in data ["+dataInizioOp+"]. Stato OK";
			log.debug(detail);
			risultato.setStato(STATO.OK);
			risultato.setDettaglio(detail);
		}
		
		return risultato;
	}
	
	public void initStatoServizio(String batch, long tempoWarning, long tempoError) {
		ParametriSonda param = new ParametriSonda();
		param.setTempoWarning(tempoWarning);
		param.setTempoError(tempoError);
		param.setDataInizioOp(new Date());
		putParametriSonda(batch, param);	
	}
	
	public void registraChiamataServizioOK(String batch) throws Exception {
		ParametriSonda param = getParametriSonda(batch);
		param.setStato(org.govmix.proxy.fatturapa.web.commons.sonde.ParametriSonda.STATO.OK);
		param.setDataInizioOp(new Date());
		putParametriSonda(batch, param);
	}

	public void registraChiamataServizioKO(String batch, String dettaglio) throws Exception {
		ParametriSonda param = getParametriSonda(batch);
		param.setStato(org.govmix.proxy.fatturapa.web.commons.sonde.ParametriSonda.STATO.KO);
		param.setDettaglio(dettaglio);
		param.setDataInizioOp(new Date());
		putParametriSonda(batch, param);
	}

	private synchronized void putParametriSonda(String batch, ParametriSonda param) {
		this.statoBatch.put(batch, param);
	}

	private synchronized ParametriSonda getParametriSonda(String batch) throws Exception {
		ParametriSonda param = null;
		
		if(this.statoBatch.containsKey(batch)) {
			param = this.statoBatch.get(batch);
		} else {
			throw new Exception("Batch ["+batch+"] non monitorato");
		}
		return param;
	}
	
}
