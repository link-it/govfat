/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.utils;

import org.apache.log4j.Logger;

public class LoggerManager {

	private static Logger endpointEnteLogger = null;
	private static Logger endpointPdDLogger = null;
	private static Logger batchStartupLogger = null;
	private static Logger batchConsegnaFatturaLogger = null;
	private static Logger batchConsegnaLottoLogger = null;
	private static Logger batchAssociazioneProtocolloLogger = null;
	private static Logger batchConsegnaEsitoLogger  = null;
	private static Logger batchInserimentoFatturaLogger = null;
	private static Logger batchAccettazioneFatturaLogger = null;
	private static Logger consoleLogger = null;
	private static Logger consoleDaoLogger = null;
	
	private static synchronized void init(){
		if(consoleDaoLogger == null)
			consoleDaoLogger = Logger.getLogger("console.dao");
		
		if(consoleLogger == null)
			consoleLogger = Logger.getLogger("console.gui");
		
		if(batchConsegnaEsitoLogger == null)
			batchConsegnaEsitoLogger = Logger.getLogger("batch.consegnaEsito");
		
		if(batchConsegnaFatturaLogger == null)
			batchConsegnaFatturaLogger = Logger.getLogger("batch.consegnaFattura");

		if(batchConsegnaLottoLogger == null)
			batchConsegnaLottoLogger = Logger.getLogger("batch.consegnaLotto");

		if(batchAssociazioneProtocolloLogger == null)
			batchAssociazioneProtocolloLogger = Logger.getLogger("batch.associazioneProtocollo");

		if(batchInserimentoFatturaLogger == null)
			batchInserimentoFatturaLogger = Logger.getLogger("batch.inserimentoFattura");
		
		if(batchAccettazioneFatturaLogger == null)
			batchAccettazioneFatturaLogger = Logger.getLogger("batch.accettazioneFattura");
		
		if(batchStartupLogger == null)
			batchStartupLogger = Logger.getLogger("startup");
		
		if(endpointEnteLogger == null)
			endpointEnteLogger = Logger.getLogger("endpoint.ente");
		
		if(endpointPdDLogger == null)
			endpointPdDLogger = Logger.getLogger("endpoint.pdd");
	}
	
	public static Logger getEndpointEnteLogger() {
		if(endpointEnteLogger == null)
			init();
		
		return endpointEnteLogger;
	}
	public static Logger getEndpointPdDLogger() {
		if(endpointPdDLogger == null)
			init();
		
		return endpointPdDLogger;
	}
	public static Logger getBatchConsegnaFatturaLogger() {
		if(batchConsegnaFatturaLogger == null)
			init();
		
		return batchConsegnaFatturaLogger;
	}
	public static Logger getBatchConsegnaEsitoLogger() {
		if(batchConsegnaEsitoLogger == null)
			init();
		
		return batchConsegnaEsitoLogger;

	}
	public static Logger getBatchConsegnaLottoLogger() {
		if(batchConsegnaLottoLogger == null)
			init();
		
		return batchConsegnaLottoLogger;

	}
	public static Logger getBatchInserimentoFatturaLogger() {
		if(batchInserimentoFatturaLogger == null)
			init();
		
		return batchInserimentoFatturaLogger;

	}


	public static Logger getBatchAccettazioneFatturaLogger() {
		if(batchAccettazioneFatturaLogger == null) {
			init();
		}
		return batchAccettazioneFatturaLogger;
	}

	public static Logger getBatchStartupLogger() {
		if(batchStartupLogger == null)
			init();
		
		return batchStartupLogger;
	}
	public static Logger getConsoleLogger() {
		if(consoleLogger == null)
			init();
		
		return consoleLogger;
	}
	public static Logger getConsoleDaoLogger() {
		if(consoleDaoLogger == null)
			init();
		
		return consoleDaoLogger;
	}

	public static Logger getBatchAssociazioneProtocolloLogger() {
		if(batchAssociazioneProtocolloLogger == null)
			init();
		
		return batchAssociazioneProtocolloLogger;
	}

}
