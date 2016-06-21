package org.govmix.proxy.fatturapa.web.api.timers;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.api.utils.TimerProperties;

public class EJBTimerObject extends TimerObject {

	private IEJBTimer timer;

	public EJBTimerObject(IEJBTimer timer, TimerProperties properties) {
		super(properties);
		this.timer = timer;
	}

	@Override
	protected void start(Logger log) {
		if(this.properties.isTimerAbilitato()){
			try{
				this.timer.start();
			} catch (RemoteException e) {
				log.error("Avvio timer '"+this.properties.getTimerName()+"'", e);
			}
		}
	}

	@Override
	protected void stop(Logger log) {
		if(this.properties.isTimerAbilitato()){
			try{
				this.timer.stop();
			} catch (RemoteException e) {
				log.error("Stop timer '"+this.properties.getTimerName()+"'", e);
			}
		}
	}

}
