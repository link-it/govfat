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
package org.govmix.proxy.fatturapa.web.commons.utils.timers;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.govmix.proxy.pcc.fatture.utils.AbstractProperties;

public class BatchProperties extends AbstractProperties {

	/** tipo di server: j2ee o web */
	private final static String SERVER_J2EE = "j2ee";
	private final static String SERVER_WEB = "web";

	private final static long TIMER_EJB_ATTESA_ATTIVA = 60 * 1000; // 1 minuto
	
	private final static int TIMER_EJB_CHECK_INTERVAL = 200;
	
	private boolean autoStartStopTimer;

	private Properties jndiContextTimerEJB;

	private Hashtable<String, String> jndiTimerEJBName;

	private boolean serverJ2EE;

	private int timerEJBDeployCheckInterval;
	private long timerEJBDeployTimeout;
	private Map<String, TimerProperties> timers;
	
	protected Properties props;
	
	public BatchProperties(String resourceName) throws Exception {
		super(resourceName);
		
		log.info("Lettura delle opzioni dal file di configurazione ["+resourceName+"]");
		String server = this.getProperty("org.govmix.proxy.fatturapa.web.api.server", true);

		if(BatchProperties.SERVER_J2EE.equalsIgnoreCase(server)){
			serverJ2EE = true;
		}else if(BatchProperties.SERVER_WEB.equalsIgnoreCase(server)){
			serverJ2EE = false;
		}else{
			throw new Exception("Valore ["+server+"] non conosciuto per la properieta' [org.govmix.proxy.fatturapa.web.api.server]");
		}

		String autoStart = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.autoStart.stop", true);
		this.autoStartStopTimer = Boolean.parseBoolean(autoStart);

		this.jndiContextTimerEJB = this.readProperties("org.govmix.proxy.fatturapa.web.api.timer.property.");

		
		String timerCheck = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.check", false);
		if(timerCheck!=null){
			this.timerEJBDeployCheckInterval = java.lang.Integer.parseInt(timerCheck);
		}else{
			this.timerEJBDeployCheckInterval =  BatchProperties.TIMER_EJB_CHECK_INTERVAL;
		}

		String timerTimeout = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.timeout", false);

		if(timerTimeout!=null){
			this.timerEJBDeployTimeout = java.lang.Long.parseLong(timerTimeout);
		}else{
			this.timerEJBDeployTimeout = BatchProperties.TIMER_EJB_ATTESA_ATTIVA;
		}
		
		java.util.Hashtable<String,String> table = new java.util.Hashtable<String,String>();
		String timerPrefix = "org.govmix.proxy.fatturapa.web.api.timer.";
		this.timers = new HashMap<String, TimerProperties>();

		
		String[] timerList = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.list", true).split(",");
		
		for(String key: timerList) {
			String value = this.getProperty(timerPrefix+key, true);
			
			if(value!=null) {
					value = value.trim();
			} else {
				throw new Exception("Impossibile trovare le properties relative al batch[ID Modulo ["+key+"]]");
				}
			table.put(key, value);
			TimerProperties timerProperties = new TimerProperties(this.getExternalReader(), this.getReader(), timerPrefix+key, key);
			this.timers.put(key, timerProperties);
		}

		this.jndiTimerEJBName = table;
		log.info("Lettura delle opzioni dal file di configurazione ["+resourceName+"] completata con successo");
		
	}
	public Properties getJndiContextTimerEJB() {
		return jndiContextTimerEJB;
	}
	public Hashtable<String, String> getJndiTimerEJBName() {
		return jndiTimerEJBName;
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
	
}
