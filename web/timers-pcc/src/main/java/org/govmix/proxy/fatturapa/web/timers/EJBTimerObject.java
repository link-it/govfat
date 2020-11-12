/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.timers;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.timers.TimerProperties;

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
