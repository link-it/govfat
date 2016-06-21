package org.govmix.proxy.fatturapa.web.timers;

import org.apache.log4j.Logger;

public abstract class AbstractTimerLib {

	protected Logger log;
	protected int limit = 100;
	protected boolean logQuery;

	public AbstractTimerLib(int limit, Logger log, boolean logQuery) throws Exception{
		this.log = log;
		this.limit = limit;
		this.logQuery = logQuery;

	}
	
	public Logger getLogger() {
		return this.log;
	}
	
	public abstract void execute() throws Exception;
}
