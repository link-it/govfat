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
package org.govmix.proxy.fatturapa.web.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerManager {

	private static Logger endpointEnteLogger = null;
	private static Logger endpointPdDLogger = null;
	private static Logger endpointTrasmittenteLogger = null;
	private static Logger endpointGetTracceLogger = null;
	private static Logger endpointProxyPccLogger = null;
	private static Logger endpointFattureAttiveLogger = null;

	private static Logger batchStartupLogger = null;
	private static Logger batchConsegnaFatturaLogger = null;
	private static Logger batchConsegnaComunicazioneUscitaLogger = null;
	private static Logger batchConsegnaLottoLogger = null;
	private static Logger batchAssociazioneProtocolloLogger = null;
	private static Logger batchConsegnaEsitoLogger  = null;
	private static Logger batchInserimentoFatturaLogger = null;
	private static Logger batchAccettazioneFatturaLogger = null;
	private static Logger batchWorkFlowFatturaLogger = null;
	private static Logger batchProtocollazioneRicevutaLogger = null;
	private static Logger batchSpedizioneFatturaAttivaLogger = null;
	private static Logger batchInvioConservazioneLogger = null;
	private static Logger batchSchedulingConservazioneLogger = null;
	private static Logger batchSpedizioneEsitiLogger = null;
	private static Logger batchSpedizioneNotificheLogger = null;
	private static Logger batchRispedizioneMessaggiLogger = null;

	private static Logger dumpLogger = null;
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
			consoleLogger = Logger.getLogger("console.gui");

		if(batchConsegnaEsitoLogger == null)
			batchConsegnaEsitoLogger = Logger.getLogger("batch.consegnaEsito");

		if(batchConsegnaFatturaLogger == null)
			batchConsegnaFatturaLogger = Logger.getLogger("batch.consegnaFattura");

		if(batchAccettazioneFatturaLogger == null)
			batchAccettazioneFatturaLogger = Logger.getLogger("batch.accettazioneFattura");

		if(batchWorkFlowFatturaLogger == null)
			batchWorkFlowFatturaLogger = Logger.getLogger("batch.workFlowFattura");

		if(batchSpedizioneFatturaAttivaLogger == null)
			batchSpedizioneFatturaAttivaLogger = Logger.getLogger("batch.spedizioneFatturaAttiva");

		if(batchSchedulingConservazioneLogger == null)
			batchSchedulingConservazioneLogger = Logger.getLogger("batch.schedulingConservazione");

		if(batchInvioConservazioneLogger == null)
			batchInvioConservazioneLogger = Logger.getLogger("batch.invioConservazione");

		if(batchProtocollazioneRicevutaLogger == null)
			batchProtocollazioneRicevutaLogger = Logger.getLogger("batch.protocollazioneRicevuta");

		if(batchConsegnaComunicazioneUscitaLogger == null)
			batchConsegnaComunicazioneUscitaLogger = Logger.getLogger("batch.consegnaComunicazioneUscita");

		if(batchConsegnaLottoLogger == null)
			batchConsegnaLottoLogger = Logger.getLogger("batch.consegnaLotto");

		if(batchAssociazioneProtocolloLogger == null)
			batchAssociazioneProtocolloLogger = Logger.getLogger("batch.associazioneProtocollo");

		if(batchInserimentoFatturaLogger == null)
			batchInserimentoFatturaLogger = Logger.getLogger("batch.inserimentoFattura");

		if(batchSpedizioneEsitiLogger == null)
			batchSpedizioneEsitiLogger = Logger.getLogger("batch.spedizioneEsiti");

		if(batchSpedizioneNotificheLogger == null)
			batchSpedizioneNotificheLogger = Logger.getLogger("batch.spedizioneNotifiche");

		if(batchRispedizioneMessaggiLogger == null)
			batchRispedizioneMessaggiLogger = Logger.getLogger("batch.rispedizioneMessaggi");

		if(batchStartupLogger == null)
			batchStartupLogger = Logger.getLogger("startup");

		if(sondaLogger == null)
			sondaLogger = Logger.getLogger("sonda");

		if(daoLogger == null)
			daoLogger = Logger.getLogger("dao");

		if(dumpLogger == null)
			dumpLogger = Logger.getLogger("dump");

		if(endpointEnteLogger == null)
			endpointEnteLogger = Logger.getLogger("endpoint.ente");

		if(endpointPdDLogger == null)
			endpointPdDLogger = Logger.getLogger("endpoint.pdd");

		if(endpointTrasmittenteLogger == null)
			endpointTrasmittenteLogger = Logger.getLogger("endpoint.trasmittente");

		if(endpointGetTracceLogger == null)
			endpointGetTracceLogger = Logger.getLogger("endpoint.getTracce");

		if(endpointProxyPccLogger == null)
			endpointProxyPccLogger = Logger.getLogger("endpoint.proxyPcc");

		if(endpointFattureAttiveLogger == null)
			endpointFattureAttiveLogger = Logger.getLogger("endpoint.fattureAttive");

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
		return Logger.getLogger(logger);
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

	public static Logger getBatchWorkFlowFatturaLogger() {
		init();
		return batchWorkFlowFatturaLogger;
	}

	public static Logger getBatchSpedizioneFatturaAttivaLogger() {
		init();
		return batchSpedizioneFatturaAttivaLogger;
	}

	public static Logger getBatchProtocollazioneRicevutaLogger() {
		init();
		return batchProtocollazioneRicevutaLogger;
	}

	public static Logger getBatchSchedulingConservazioneLogger() {
		init();
		return batchSchedulingConservazioneLogger;
	}

	public static Logger getBatchInvioConservazioneLogger() {
		init();
		return batchInvioConservazioneLogger;
	}

	public static Logger getDumpLogger() {
		init();
		return dumpLogger;
	}

	public static Logger getEndpointFattureAttiveLogger() {
		return endpointFattureAttiveLogger;
	}

}
