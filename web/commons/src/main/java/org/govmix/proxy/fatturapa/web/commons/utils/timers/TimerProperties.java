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
package org.govmix.proxy.fatturapa.web.commons.utils.timers;

import org.openspcoop2.utils.resources.PropertiesReader;

public class TimerProperties {

	private boolean timerAbilitato;
	private boolean timerLogAbilitato;
	private int timerLimit;
	private int timerTimeout;
	
	private int timerWarningThreshold;
	private int timerErrorThreshold;
	private String timerName;
	
	public TimerProperties(PropertiesReader externalReader, PropertiesReader reader, String prefix, String name) throws Exception {
		this.timerName = name;
		
		String abilitato = getProperty(prefix+".enable", reader, externalReader, true);
		
		this.timerAbilitato = Boolean.parseBoolean(abilitato.trim());
		
		
		if(this.timerAbilitato) {
			String timeout = getProperty(prefix+".timeout", reader, externalReader, true);
			this.timerTimeout = Integer.parseInt(timeout.trim());
			
			String logAbilitato = reader.getValue(prefix+".logQuery");
			if(logAbilitato == null) {
				throw new Exception("properieta' ["+prefix+".logQuery] non definita");
			}
			
			this.timerLogAbilitato = Boolean.parseBoolean(logAbilitato.trim());
			
			String limit = reader.getValue(prefix+".query.limit");
			if(limit == null) {
				throw new Exception("properieta' ["+prefix+".query.limit] non definita");
			}
			
			this.timerLimit = Integer.parseInt(limit.trim());
			
			String timerWarningThresholdString = getProperty(prefix+".warningThreshold", reader, externalReader, true);
			this.timerWarningThreshold = Integer.parseInt(timerWarningThresholdString.trim());
			
			String timerErrorThresholdString = getProperty(prefix+".errorThreshold", reader, externalReader, true);
			this.timerErrorThreshold = Integer.parseInt(timerErrorThresholdString.trim());
		} else {
			this.timerTimeout = -1;
			this.timerLogAbilitato = false;
			this.timerLimit = 50;
			this.timerWarningThreshold = -1;
			this.timerErrorThreshold = -1;
		}
		
		
	}
	
	private String getProperty(String name, PropertiesReader reader, PropertiesReader externalReader, boolean required) throws Exception {
		String value = null;
		if(externalReader != null) {
			value = externalReader.getValue_convertEnvProperties(name);
		}
		
		if(value != null && !value.trim().equals("")) {
			return value.trim();			
		} else {
			value = reader.getValue_convertEnvProperties(name);
			if(value != null && !value.trim().equals("")) {
				return value.trim();			
			} else {
				if(required){
					throw new Exception("Property ["+name+"] obbligatoria non trovata");
				} else{
					return null;
				}
					
			}
		}
		
	}

	
	public boolean isTimerAbilitato() {
		return this.timerAbilitato;
	}

	public boolean isTimerLogAbilitato() {
		return this.timerLogAbilitato;
	}

	public int getTimerLimit() {
		return this.timerLimit;
	}

	public int getTimerTimeout() {
		return this.timerTimeout;
	}

	public String getTimerName() {
		return this.timerName;
	}

	public int getTimerErrorThreshold() {
		return this.timerErrorThreshold;
	}

	public int getTimerWarningThreshold() {
		return this.timerWarningThreshold;
	}

}
