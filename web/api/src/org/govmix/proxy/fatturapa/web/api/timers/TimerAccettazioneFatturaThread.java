/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package org.govmix.proxy.fatturapa.web.api.timers;

import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

/**
 * Thread per la gestione della Consegna Fattura
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */
public class TimerAccettazioneFatturaThread  extends AbstractTimerThread {

	@Override
	public String getIdModulo() {
		return TimerAccettazioneFattura.ID_MODULO;
	}

	@Override
	public AbstractTimerLib getTimerLib() throws Exception {
		return new TimerAccettazioneFatturaLib(this.limit,LoggerManager.getBatchAccettazioneFatturaLogger(),this.logQuery);
	}

	
//	/**
//	 * Timeout che definisce la cadenza di avvio di questo timer. 
//	 */
//	private long timeout;
//	
//	/** Logger utilizzato per debug. */
//	private Logger logTimer = null;
//
//	/** Numero di messaggi prelevati sulla singola query */
//	private int limit; 
//	/** Indicazione se il logging delle query e' attivo */
//	private boolean logQuery;
//
//	/** implementazione Timer */
//	private TimerConsegnaFatturaLib consegnaFatturaLib;
//	
//    // VARIABILE PER STOP
//	private boolean stop = false;
//	
//	public boolean isStop() {
//		return this.stop;
//	}
//
//	public void setStop(boolean stop) {
//		this.stop = stop;
//	}
//	
//	
//	
//	/** Costruttore */
//	public TimerConsegnaFatturaThread() {
//		try {
//			WebApiProperties properties = WebApiProperties.getInstance();
//			this.logTimer = LoggerManager.getBatchConsegnaFatturaLogger();
//			this.limit = properties.getTimerConsegnaFatturaLimit();
//			this.logQuery = properties.isTimerConsegnaFatturaLogAbilitato();
//			this.timeout = properties.getTimerConsegnaFatturaTimeout();
//			// Prendo la gestione
//			this.consegnaFatturaLib = 
//				new TimerConsegnaFatturaLib(this.limit,this.logTimer,this.logQuery);
//			
//		} catch (Exception e) {
//			throw new RuntimeException("Errore nell'inizializzazione delle TimerConsegnaFatturaThread", e);
//		}
//		
//
//	}
//	
//	/**
//	 * Metodo che fa partire il Thread. 
//	 *
//	 */
//	@Override
//	public void run(){
//		
//		while(this.stop == false){
//			
//			try{
//				this.consegnaFatturaLib.execute();
//				
//			}catch(Exception e){
//				this.logTimer.error("Errore generale: "+e.getMessage(),e);
//			}finally{
//			}
//			
//					
//			// CheckInterval
//			if(this.stop==false){
//				int i=0;
//				while(i<this.timeout){
//					try{
//						Thread.sleep(1000);		
//					}catch(Exception e){}
//					if(this.stop){
//						break; // thread terminato, non lo devo far piu' dormire
//					}
//					i++;
//				}
//			}
//		} 
//	}
}
