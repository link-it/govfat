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

import java.util.List;

import org.govmix.proxy.pcc.fatture.utils.AbstractProperties;
import org.openspcoop2.utils.resources.PropertiesReader;

public class TimerProperties extends AbstractProperties {

	private boolean timerAbilitato;
	private boolean timerLogAbilitato;
	private int timerLimit;
	private int timerTimeout;
	
	private int timerWarningThreshold;
	private int timerErrorThreshold;
	private String timerName;
	
	public TimerProperties(List<PropertiesReader> r, String prefix, String name) throws Exception {
		super(r);

		this.timerName = name;
		
		this.timerAbilitato = this.getBooleanProperty(prefix+".enable", true);
		this.timerTimeout = this.getIntegerProperty(prefix+".timeout", true);
		this.timerLogAbilitato = this.getBooleanProperty(prefix+".logQuery", true);
		this.timerLimit = this.getIntegerProperty(prefix+".query.limit", true);
		this.timerWarningThreshold = this.getIntegerProperty(prefix+".warningThreshold", true);
		this.timerErrorThreshold = this.getIntegerProperty(prefix+".errorThreshold", true);
		
		
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
