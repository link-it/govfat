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
		
		String abilitato = externalReader.getValue(prefix+".enable");
		if(abilitato == null) {
			throw new Exception("properieta' ["+prefix+".enable] non definita");
		}
		
		this.timerAbilitato = Boolean.parseBoolean(abilitato.trim());
		
		String timeout = externalReader.getValue(prefix+".timeout");
		if(timeout == null) {
			throw new Exception("properieta' ["+prefix+".timeout] non definita");
		}
		
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
		
		String timerWarningThresholdString = externalReader.getValue(prefix+".warningThreshold");
		if(timerWarningThresholdString == null) {
			throw new Exception("properieta' ["+prefix+".warningThreshold] non definita");
		}
		
		this.timerWarningThreshold = Integer.parseInt(timerWarningThresholdString.trim());
		
		String timerErrorThresholdString = externalReader.getValue(prefix+".errorThreshold");
		if(timerErrorThresholdString == null) {
			throw new Exception("properieta' ["+prefix+".errorThreshold] non definita");
		}
		
		this.timerErrorThreshold = Integer.parseInt(timerErrorThresholdString.trim());
		
		
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
