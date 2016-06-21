package org.govmix.proxy.fatturapa.web.api.timers;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.api.utils.TimerProperties;

public abstract class TimerObject {
	
	protected TimerProperties properties;
	
	public TimerObject(TimerProperties properties) {
		this.properties = properties;
	}
	
	protected abstract void start(Logger log);
	protected abstract void stop(Logger log);
}
