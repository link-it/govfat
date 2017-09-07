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
package org.govmix.proxy.fatturapa.web.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openspcoop2.utils.LoggerWrapperFactory;

public class LoggerManager {

	private static Logger endpointEnteLogger = null;
	private static Logger endpointPdDLogger = null;
	private static Logger endpointTrasmittenteLogger = null;
	private static Logger endpointGetTracceLogger = null;
	private static Logger endpointProxyPccLogger = null;

	private static Logger batchStartupLogger = null;
	private static Logger batchConsegnaFatturaLogger = null;
	private static Logger batchConsegnaComunicazioneUscitaLogger = null;
	private static Logger batchConsegnaLottoLogger = null;
	private static Logger batchAssociazioneProtocolloLogger = null;
	private static Logger batchConsegnaEsitoLogger  = null;
	private static Logger batchInserimentoFatturaLogger = null;
	private static Logger batchAccettazioneFatturaLogger = null;
	private static Logger batchSpedizioneEsitiLogger = null;
	private static Logger batchSpedizioneNotificheLogger = null;
	private static Logger batchRispedizioneMessaggiLogger = null;

	private static Logger daoLogger = null;
	private static Logger consoleLogger = null;
	private static Logger sondaLogger = null;

	private static boolean initialized = false;

	private static synchronized void init(){
		if(initialized) return;
		try {
			String logPath = "/fatturaPA.log4j.properties"; 

			InputStream is = LoggerManager.class
					.getResourceAsStream(logPath);

			Properties prop = new Properties();
			prop.load(is);

			// inizializzo il logger
			PropertyConfigurator.configure(prop);
		} catch(IOException e) {}

		if(consoleLogger == null)
			consoleLogger = LoggerWrapperFactory.getLogger("console.gui");

		if(batchConsegnaEsitoLogger == null)
			batchConsegnaEsitoLogger = LoggerWrapperFactory.getLogger("batch.consegnaEsito");

		if(batchConsegnaFatturaLogger == null)
			batchConsegnaFatturaLogger = LoggerWrapperFactory.getLogger("batch.consegnaFattura");

		if(batchAccettazioneFatturaLogger == null)
			batchAccettazioneFatturaLogger = LoggerWrapperFactory.getLogger("batch.accettazioneFattura");

		if(batchConsegnaComunicazioneUscitaLogger == null)
			batchConsegnaComunicazioneUscitaLogger = LoggerWrapperFactory.getLogger("batch.consegnaComunicazioneUscita");

		if(batchConsegnaLottoLogger == null)
			batchConsegnaLottoLogger = LoggerWrapperFactory.getLogger("batch.consegnaLotto");

		if(batchAssociazioneProtocolloLogger == null)
			batchAssociazioneProtocolloLogger = LoggerWrapperFactory.getLogger("batch.associazioneProtocollo");

		if(batchInserimentoFatturaLogger == null)
			batchInserimentoFatturaLogger = LoggerWrapperFactory.getLogger("batch.inserimentoFattura");

		if(batchSpedizioneEsitiLogger == null)
			batchSpedizioneEsitiLogger = LoggerWrapperFactory.getLogger("batch.spedizioneEsiti");

		if(batchSpedizioneNotificheLogger == null)
			batchSpedizioneNotificheLogger = LoggerWrapperFactory.getLogger("batch.spedizioneNotifiche");

		if(batchRispedizioneMessaggiLogger == null)
			batchRispedizioneMessaggiLogger = LoggerWrapperFactory.getLogger("batch.rispedizioneMessaggi");

		if(batchStartupLogger == null)
			batchStartupLogger = LoggerWrapperFactory.getLogger("startup");

		if(sondaLogger == null)
			sondaLogger = LoggerWrapperFactory.getLogger("sonda");

		if(daoLogger == null)
			daoLogger = LoggerWrapperFactory.getLogger("dao");

		if(endpointEnteLogger == null)
			endpointEnteLogger = LoggerWrapperFactory.getLogger("endpoint.ente");

		if(endpointPdDLogger == null)
			endpointPdDLogger = LoggerWrapperFactory.getLogger("endpoint.pdd");

		if(endpointTrasmittenteLogger == null)
			endpointTrasmittenteLogger = LoggerWrapperFactory.getLogger("endpoint.trasmittente");

		if(endpointGetTracceLogger == null)
			endpointGetTracceLogger = LoggerWrapperFactory.getLogger("endpoint.getTracce");

		if(endpointProxyPccLogger == null)
			endpointProxyPccLogger = LoggerWrapperFactory.getLogger("endpoint.proxyPcc");

		initialized = true;
	}

	public static Logger getEndpointEnteLogger() {
		init();
		return endpointEnteLogger;
	}
	public static Logger getEndpointPdDLogger() {
		init();
		return endpointPdDLogger;
	}
	public static Logger getEndpointGetTracceLogger() {
		init();
		return endpointGetTracceLogger;
	}
	public static Logger getEndpointProxyPccLogger() {
		init();
		return endpointProxyPccLogger;
	}
	public static Logger getBatchConsegnaFatturaLogger() {
		init();
		return batchConsegnaFatturaLogger;
	}
	public static Logger getBatchConsegnaEsitoLogger() {
		init();
		return batchConsegnaEsitoLogger;

	}
	public static Logger getBatchConsegnaLottoLogger() {
		init();
		return batchConsegnaLottoLogger;

	}
	public static Logger getBatchInserimentoFatturaLogger() {
		init();
		return batchInserimentoFatturaLogger;

	}

	public static Logger getLogger(String logger) {
		init();
		return LoggerWrapperFactory.getLogger(logger);
	}

	public static Logger getBatchAccettazioneFatturaLogger() {
		init();
		return batchAccettazioneFatturaLogger;
	}

	public static Logger getBatchStartupLogger() {
		init();
		return batchStartupLogger;
	}
	public static Logger getConsoleLogger() {
		init();
		return consoleLogger;
	}

	public static Logger getBatchAssociazioneProtocolloLogger() {
		init();
		return batchAssociazioneProtocolloLogger;
	}

	public static Logger getBatchConsegnaComunicazioneUscitaLogger() {
		init();
		return batchConsegnaComunicazioneUscitaLogger;
	}

	public static Logger getEndpointTrasmittenteLogger() {
		init();
		return endpointTrasmittenteLogger;
	}

	public static Logger getBatchSpedizioneEsitiLogger() {
		init();
		return batchSpedizioneEsitiLogger;
	}

	public static Logger getBatchSpedizioneNotificheLogger() {
		init();
		return batchSpedizioneNotificheLogger;
	}

	public static Logger getBatchRispedizioneMessaggiLogger() {
		init();
		return batchRispedizioneMessaggiLogger;
	}

	public static Logger getSondaLogger() {
		init();
		return sondaLogger;
	}

	public static Logger getDaoLogger() {
		init();
		return daoLogger;
	}


}
