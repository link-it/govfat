package org.govmix.proxy.fatturapa.web.api.utils;

import org.openspcoop2.utils.resources.PropertiesReader;

public class TimerProperties {

	private boolean timerAbilitato;
	private boolean timerLogAbilitato;
	private int timerLimit;
	private int timerTimeout;
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

}
