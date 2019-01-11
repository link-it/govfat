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
package org.govmix.proxy.fatturapa.web.timers.utils;

import java.net.URL;


public class BatchProperties extends org.govmix.proxy.fatturapa.web.commons.utils.timers.BatchProperties {

	
	private String ricezioneEsitoPassword;
	private URL ricezioneEsitoURL;
	private String ricezioneEsitoUsername;
	private int maxTentativiRispedizione;
	private int fattoreRispedizione;
	private int maxTentativiRispedizioneWFM;
	private int fattoreRispedizioneWFM;
	private int maxTentativiRispedizioneSdI;
	private int fattoreRispedizioneSdI;
	private boolean consegnaFatturaContestuale;
	private boolean validazioneDAOAbilitata;
	private boolean rifiutoAutomaticoAbilitato;
	private String msgNotificaGiaPervenuta;
	private String msgAvvenutaRicezioneNonNotificata;
	
	private static BatchProperties props;
	
	public static void initInstance() throws Exception {
		props = new BatchProperties("/batch.properties");
	}
	
	public static BatchProperties getInstance() throws Exception {
		if(props == null) {
			initInstance();
		}
		
		return props;
		
	}

	public BatchProperties(String path) throws Exception {
		super(path);
		
		try {
			this.ricezioneEsitoURL = new URL(this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.ricezioneEsito.url", true));
			this.ricezioneEsitoUsername = this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.ricezioneEsito.username", true);
			this.ricezioneEsitoPassword = this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.ricezioneEsito.password", true);
			
			this.maxTentativiRispedizione = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.consegnaFattura.maxTentativiRispedizione", true));
			this.fattoreRispedizione = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.consegnaFattura.fattoreRispedizione", true));
			this.maxTentativiRispedizioneWFM = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.consegnaFattura.maxTentativiRispedizioneWFM", true));
			this.fattoreRispedizioneWFM = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.consegnaFattura.fattoreRispedizioneWFM", true));
			this.maxTentativiRispedizioneSdI = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.consegnaFattura.maxTentativiRispedizioneSdI", true));
			this.fattoreRispedizioneSdI = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.api.pdd.consegnaFattura.fattoreRispedizioneSdI", true));
			String consegnaFatturaContestualeString = this.getProperty("org.govmix.proxy.fatturapa.web.api.consegnaFatturaAssociata", true);
			this.consegnaFatturaContestuale = Boolean.parseBoolean(consegnaFatturaContestualeString);

			String msgAvvenutaRicezioneNonNotificataString = this.getProperty("org.govmix.proxy.fatturapa.consegnaEsito.msgAvvenutaRicezioneNonNotificata", true);
			this.msgAvvenutaRicezioneNonNotificata = msgAvvenutaRicezioneNonNotificataString.trim();

			
			String msgNotificaGiaPervenutaString = this.getProperty("org.govmix.proxy.fatturapa.consegnaEsito.msgNotificaGiaPervenuta", true);
			this.msgNotificaGiaPervenuta = msgNotificaGiaPervenutaString.trim();
			
			log.debug("Notifica gia' pervenuta ["+this.msgNotificaGiaPervenuta+"]");

			String validazioneDAOAbilitataString = this.getProperty("org.govmix.proxy.fatturapa.web.api.validazioneDAOAbilitata", false);
			
			if(validazioneDAOAbilitataString != null) {
				this.validazioneDAOAbilitata = Boolean.parseBoolean(validazioneDAOAbilitataString);
			} else {
				this.validazioneDAOAbilitata = true;
			}

			String rifiutoAutomaticoAbilitatoString = this.getProperty("org.govmix.proxy.fatturapa.web.api.rifiutoAutomaticoAbilitato", false);
			
			if(rifiutoAutomaticoAbilitatoString != null) {
				this.rifiutoAutomaticoAbilitato = Boolean.parseBoolean(rifiutoAutomaticoAbilitatoString);
			} else {
				this.rifiutoAutomaticoAbilitato = false;
			}

		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getRicezioneEsitoPassword() {
		return ricezioneEsitoPassword;
	}
	public URL getRicezioneEsitoURL() {
		return ricezioneEsitoURL;
	}
	
	public String getRicezioneEsitoUsername() {
		return ricezioneEsitoUsername;
	}
	public String getMsgNotificaGiaPervenuta() {
		return msgNotificaGiaPervenuta;
	}
	public String getMsgAvvenutaRicezioneNonNotificata() {
		return msgAvvenutaRicezioneNonNotificata;
	}
	public boolean isConsegnaFatturaContestuale() {
		return consegnaFatturaContestuale;
	}
	public boolean isValidazioneDAOAbilitata() {
		return validazioneDAOAbilitata;
	}
	public boolean isRifiutoAutomaticoAbilitato() {
		return rifiutoAutomaticoAbilitato;
	}
	public int getMaxTentativiRispedizione() {
		return maxTentativiRispedizione;
	}
	public int getFattoreRispedizione() {
		return fattoreRispedizione;
	}

	public int getMaxTentativiRispedizioneSdI() {
		return maxTentativiRispedizioneSdI;
	}

	public void setMaxTentativiRispedizioneSdI(int maxTentativiRispedizioneSdI) {
		this.maxTentativiRispedizioneSdI = maxTentativiRispedizioneSdI;
	}

	public int getFattoreRispedizioneSdI() {
		return fattoreRispedizioneSdI;
	}

	public void setFattoreRispedizioneSdI(int fattoreRispedizioneSdI) {
		this.fattoreRispedizioneSdI = fattoreRispedizioneSdI;
	}

	public int getMaxTentativiRispedizioneWFM() {
		return maxTentativiRispedizioneWFM;
	}

	public void setMaxTentativiRispedizioneWFM(int maxTentativiRispedizioneWFM) {
		this.maxTentativiRispedizioneWFM = maxTentativiRispedizioneWFM;
	}

	public int getFattoreRispedizioneWFM() {
		return fattoreRispedizioneWFM;
	}

	public void setFattoreRispedizioneWFM(int fattoreRispedizioneWFM) {
		this.fattoreRispedizioneWFM = fattoreRispedizioneWFM;
	}

}
