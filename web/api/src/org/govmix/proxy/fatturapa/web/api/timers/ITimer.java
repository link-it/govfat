package org.govmix.proxy.fatturapa.web.api.timers;

public interface ITimer {

	public abstract String getIdModulo();
	public abstract AbstractTimerLib getTimerLib() throws Exception;

}
