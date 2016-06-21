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
package org.govmix.proxy.fatturapa.web.timers.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.timers.TimerAccettazioneFattura;
import org.govmix.proxy.fatturapa.web.timers.TimerAssociazioneProtocollo;
import org.govmix.proxy.fatturapa.web.timers.TimerConsegnaEsito;
import org.govmix.proxy.fatturapa.web.timers.TimerConsegnaFattura;
import org.govmix.proxy.fatturapa.web.timers.TimerConsegnaLotto;
import org.govmix.proxy.fatturapa.web.timers.TimerInserimentoFattura;
import org.openspcoop2.utils.resources.PropertiesReader;

public class BatchProperties {

	private static BatchProperties props;
	/** tipo di server: j2ee o web */
	private final static String SERVER_J2EE = "j2ee";
	private final static String SERVER_WEB = "web";

	private final static long TIMER_EJB_ATTESA_ATTIVA = 60 * 1000; // 1 minuto
	
	private final static int TIMER_EJB_CHECK_INTERVAL = 200;
	
	public static BatchProperties getInstance() throws Exception {
		if(props == null) {
			props = new BatchProperties();
		}
		
		return props;
		
	}
	private static String getProperty(String name, Properties props, boolean required) throws Exception {
		String value = props.getProperty(name);
		if(value == null) {
			if(required)
				throw new Exception("Property ["+name+"] non trovata");
			else return null;
		}
		
		return value.trim();
	}
	
	private boolean autoStartStopTimer;

	private Properties jndiContextTimerEJB;

	private Hashtable<String, String> jndiTimerEJBName;

	private String ricezioneEsitoPassword;
	
	private URL ricezioneEsitoURL;

	private String ricezioneEsitoUsername;

	private boolean serverJ2EE;
	
	private boolean consegnaFatturaContestuale;
	
	private boolean validazioneDAOAbilitata;

	private boolean rifiutoAutomaticoAbilitato;

	private int timerEJBDeployCheckInterval;

	private long timerEJBDeployTimeout;

	private Map<String, TimerProperties> timers;
	
	private BatchProperties() throws Exception {
		initProperties();
	}
	
	public void reloadProperties() throws Exception {
		initProperties();
	}
	
	private void initProperties() throws Exception {
		InputStream is = BatchProperties.class.getResourceAsStream("/batch.properties");
		Properties props = new Properties();
		props.load(is);

		
		

		String propertiesPath = getProperty("org.govmix.proxy.fatturapa.web.api.properties.path", props, true);

		/** Properties lette dal file di configurazione ESTERNO */

		Properties externalProps = new Properties();
		File propertiesFile = new File(propertiesPath + "/proxyFatturaPA.properties");
		
		if(!propertiesFile.exists()) {
			throw new Exception("il file ["+propertiesFile.getAbsolutePath()+"] non esiste. Impossibile avviare l'applicazione");
		}
		
		LoggerManager.getBatchStartupLogger().info("Leggo le opzioni dal file di configurazione ["+propertiesFile.getAbsolutePath()+"]");
		FileInputStream fis = new FileInputStream(propertiesFile);
		externalProps.load(fis);
		
		PropertiesReader externalReader = new PropertiesReader(externalProps, false);

		
		
		this.ricezioneEsitoURL = new URL(getProperty("org.govmix.proxy.fatturapa.web.api.pdd.ricezioneEsito.url", externalProps, true));
		this.ricezioneEsitoUsername = getProperty("org.govmix.proxy.fatturapa.web.api.pdd.ricezioneEsito.username", externalProps, true);
		this.ricezioneEsitoPassword = getProperty("org.govmix.proxy.fatturapa.web.api.pdd.ricezioneEsito.password", externalProps, true);
		
		/** Abilitazione del timer **/

		String consegnaFatturaAbilitato = externalReader.getValue("org.govmix.proxy.fatturapa.web.api.timer.consegnaFattura.enable");
		if(consegnaFatturaAbilitato == null) {
			throw new Exception("properieta' [org.govmix.proxy.fatturapa.web.api.timer.consegnaFattura.enable] non definita");
		}
		
		/** Properties lette dal file di configurazione INTERNO */

		PropertiesReader reader = new PropertiesReader(props, false);

		String server = getProperty("org.govmix.proxy.fatturapa.web.api.server", props, true);

		if(BatchProperties.SERVER_J2EE.equalsIgnoreCase(server)){
			serverJ2EE = true;
		}else if(BatchProperties.SERVER_WEB.equalsIgnoreCase(server)){
			serverJ2EE = false;
		}else{
			throw new Exception("Valore ["+server+"] non conosciuto per la properieta' [org.govmix.proxy.fatturapa.web.api.server]");
		}

		String consegnaFatturaContestualeString = getProperty("org.govmix.proxy.fatturapa.web.api.consegnaFatturaAssociata", props, true);
		this.consegnaFatturaContestuale = Boolean.parseBoolean(consegnaFatturaContestualeString);

		String validazioneDAOAbilitataString = getProperty("org.govmix.proxy.fatturapa.web.api.validazioneDAOAbilitata", props, false);
		
		if(validazioneDAOAbilitataString != null) {
			this.validazioneDAOAbilitata = Boolean.parseBoolean(validazioneDAOAbilitataString);
		} else {
			this.validazioneDAOAbilitata = true;
		}

		String rifiutoAutomaticoAbilitatoString = getProperty("org.govmix.proxy.fatturapa.web.api.rifiutoAutomaticoAbilitato", props, false);
		
		if(rifiutoAutomaticoAbilitatoString != null) {
			this.rifiutoAutomaticoAbilitato = Boolean.parseBoolean(rifiutoAutomaticoAbilitatoString);
		} else {
			this.rifiutoAutomaticoAbilitato = false;
		}

		String autoStart = getProperty("org.govmix.proxy.fatturapa.web.api.timer.autoStart.stop", props, true);
		this.autoStartStopTimer = Boolean.parseBoolean(autoStart);

		this.jndiContextTimerEJB = reader.readProperties_convertEnvProperties("org.govmix.proxy.fatturapa.web.api.timer.property.");

		
		String timerCheck = getProperty("org.govmix.proxy.fatturapa.web.api.timer.check", props, false);
		if(timerCheck!=null){
			this.timerEJBDeployCheckInterval = java.lang.Integer.parseInt(timerCheck);
		}else{
			this.timerEJBDeployCheckInterval =  BatchProperties.TIMER_EJB_CHECK_INTERVAL;
		}

		String timerTimeout = getProperty("org.govmix.proxy.fatturapa.web.api.timer.timeout", props, false);

		if(timerTimeout!=null){
			this.timerEJBDeployTimeout = java.lang.Long.parseLong(timerTimeout);
		}else{
			this.timerEJBDeployTimeout = BatchProperties.TIMER_EJB_ATTESA_ATTIVA;
		}
		
		java.util.Hashtable<String,String> table = new java.util.Hashtable<String,String>();
		java.util.Enumeration<?> en = reader.propertyNames();
		String timerPrefix = "org.govmix.proxy.fatturapa.web.api.timer.";
		this.timers = new HashMap<String, TimerProperties>();

		for (; en.hasMoreElements() ;) {
			String property = (String) en.nextElement();
			if(property.startsWith(timerPrefix)){
				String key = (property.substring(timerPrefix.length()));
				if(key != null)
					key = key.trim();
				String value = reader.getValue_convertEnvProperties(property);
				if(value!=null)
					value = value.trim();
				if("consegnaFattura".equals(key) && value!=null){
					table.put(TimerConsegnaFattura.ID_MODULO, value);
					TimerProperties timerProperties = new TimerProperties(externalReader, reader, timerPrefix+"consegnaFattura", TimerConsegnaFattura.ID_MODULO);
					this.timers.put(TimerConsegnaFattura.ID_MODULO, timerProperties);
				}else if("consegnaLotto".equals(key) && value!=null){
					table.put(TimerConsegnaLotto.ID_MODULO, value);
					TimerProperties timerProperties = new TimerProperties(externalReader, reader, timerPrefix+"consegnaLotto", TimerConsegnaLotto.ID_MODULO);
					this.timers.put(TimerConsegnaLotto.ID_MODULO, timerProperties);
				}else if("associazioneProtocollo".equals(key) && value!=null){
					table.put(TimerAssociazioneProtocollo.ID_MODULO, value);
					TimerProperties timerProperties = new TimerProperties(externalReader, reader, timerPrefix+"associazioneProtocollo", TimerAssociazioneProtocollo.ID_MODULO);
					this.timers.put(TimerAssociazioneProtocollo.ID_MODULO, timerProperties);
				}else if("consegnaEsito".equals(key) && value!=null){
					table.put(TimerConsegnaEsito.ID_MODULO, value);
					TimerProperties timerProperties = new TimerProperties(externalReader, reader, timerPrefix+"consegnaEsito", TimerConsegnaEsito.ID_MODULO);
					this.timers.put(TimerConsegnaEsito.ID_MODULO, timerProperties);
				}else if("inserimentoFattura".equals(key) && value!=null){
					table.put(TimerInserimentoFattura.ID_MODULO, value);
					TimerProperties timerProperties = new TimerProperties(externalReader, reader, timerPrefix+"inserimentoFattura", TimerInserimentoFattura.ID_MODULO);
					this.timers.put(TimerInserimentoFattura.ID_MODULO, timerProperties);
				}else if("accettazioneFattura".equals(key) && value!=null){
					table.put(TimerAccettazioneFattura.ID_MODULO, value);
					TimerProperties timerProperties = new TimerProperties(externalReader, reader, timerPrefix+"accettazioneFattura", TimerAccettazioneFattura.ID_MODULO);
					this.timers.put(TimerAccettazioneFattura.ID_MODULO, timerProperties);
				}
			}
		}

		this.jndiTimerEJBName = table;
		
	}
	public Properties getJndiContextTimerEJB() {
		return jndiContextTimerEJB;
	}
	public Hashtable<String, String> getJndiTimerEJBName() {
		return jndiTimerEJBName;
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

	public int getTimerEJBDeployCheckInterval() {
		return timerEJBDeployCheckInterval;
	}
	public long getTimerEJBDeployTimeout() {
		return timerEJBDeployTimeout;
	}
	public Map<String, TimerProperties> getTimers() {
		return timers;
	} 
	public boolean isAutoStartStopTimer() {
		return autoStartStopTimer;
	}

	public boolean isServerJ2EE() {
		return serverJ2EE;
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
	
}
