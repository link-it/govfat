package org.govmix.proxy.fatturapa.web.timers;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.timers.utils.TimerProperties;

public class ThreadTimerObject extends TimerObject {

	private AbstractTimerThread timer;

	public ThreadTimerObject(AbstractTimerThread timer, TimerProperties properties) {
		super(properties);
		this.timer = timer;
	}
	
	@Override
	protected void start(Logger log) {
		if(this.properties.isTimerAbilitato()) {
			try{
				this.timer.start();
			}catch(Exception e){
				log.error("Avvio timer (thread) '"+this.properties.getTimerName()+"'", e);
			}
		}

	}

	@Override
	protected void stop(Logger log) {
		if(this.properties.isTimerAbilitato()){
			try{
				this.timer.setStop(true);
			} catch (Exception e) {
				log.error("Stop timer (thread) '"+this.properties.getTimerName()+"'", e);
			}
		}
	}

}
