/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

package org.govmix.proxy.fatturapa.web.timers;

import java.util.ArrayList;
import java.util.List;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.commons.utils.timers.BatchProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.timers.TimerProperties;
import org.openspcoop2.utils.date.DateManager;
import org.openspcoop2.utils.resources.GestoreJNDI;



/**
 * Implementazione del punto di Startup dell'applicazione WEB
 * 
 * @author Giovanni Bussu
 * @author $Author: gbussu $
 * @version 
 */

public class TimerStartup {

	/** Logger utilizzato per segnalazione di errori. */
	private static Logger log = LoggerManager.getBatchStartupLogger();

	private List<TimerObject> timerLst;

	/** indicazione se è un server j2ee */
	protected long startDate ;

	/** Context della Servlet */
	ServletContext servletContext;

	/** OpenSPCoopStartupThread */
	private TimerStartupThread th;

	private static TimerStartup instance;
	
	public static TimerStartup getInstance() {
		if(instance == null) {
			instance = new TimerStartup();
		}
		return instance;
	}

	private TimerStartup() {}

	public void contextInitialized(ServletContextEvent sce) {

		/* ------  Ottiene il servletContext --------*/
		this.servletContext = sce.getServletContext();
		this.initialize();
	}
	
	private void initialize() {

		this.startDate = System.currentTimeMillis();

		this.th = new TimerStartupThread();
		Thread t = new Thread(th);
		t.start();

	}

	class TimerStartupThread implements Runnable {

		private void logError(String msg){
			this.logError(msg,null);
		}
		private void logError(String msg,Exception e){
			if(e==null)
				TimerStartup.log.error(msg);
			else
				TimerStartup.log.error(msg,e);
		}
		

		public TimerStartupThread() {                        
		}

		@Override
		public void run() {
			BatchProperties properties = null;
			try {
				properties = org.govmix.proxy.fatturapa.web.timers.utils.BatchProperties.getInstance();
			} catch(Exception e) {
				TimerStartup.log.error("Errore durante la lettura delle BatchProperties", e);
				return;
			}
			
			try {
				TimerStartup.this.timerLst = this.initTimers(properties);
			} catch (Exception e) {
				TimerStartup.log.error("Errore durante la init dei timer", e);
				return;
			}
			
			for(TimerObject timer : TimerStartup.this.timerLst) {
				timer.start(log);
			}
		}
		
		private List<TimerObject> initTimers(BatchProperties properties) throws Exception {
			ArrayList<TimerObject> lst = new ArrayList<TimerObject>();
			if(properties.isServerJ2EE()) {
				GestoreJNDI jndi = null;
				if(properties.getJndiContextTimerEJB()==null)
					jndi = new GestoreJNDI();
				else
					jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
	
				for(Object timerNameObject : properties.getJndiTimerEJBName().keySet()) {
					String timerName = (String)timerNameObject;
					
					if(this.lookupTimer(timerName, properties, jndi)) {
						lst.add(createEJBTimer(timerName, properties));
					}
				}
			} else {
				for(Object timerNameObject : properties.getJndiTimerEJBName().keySet()) {
					String timerName = (String)timerNameObject;
					
					lst.add(createThreadTimer(timerName, properties));
				}
			}
			
			
			return lst;
		}
		
		private TimerObject createEJBTimer(String timerName, BatchProperties properties) throws Exception {
			
			IEJBTimer timer;
			if(TimerConsegnaFattura.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerConsegnaFattura(properties);
			}else if(TimerConsegnaLotto.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerConsegnaLotto(properties);
			}else if(TimerAssociazioneProtocollo.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerAssociaProtocollo(properties);
			}else if(TimerConsegnaEsito.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerConsegnaEsito(properties);
			}else if(TimerInserimentoFattura.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerInserimentoFattura(properties);
			}else if(TimerAccettazioneFattura.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerAccettazioneFattura(properties);
			} else {
				return null;
			}

			return new EJBTimerObject(timer, properties.getTimers().get(timerName));
			
		}
		
		private TimerObject createThreadTimer(String timerName, BatchProperties properties) throws Exception {

			AbstractTimerThread timer;
			if(TimerConsegnaFattura.ID_MODULO.equals(timerName)){
				timer = new TimerConsegnaFatturaThread();
			}else if(TimerConsegnaLotto.ID_MODULO.equals(timerName)){
				timer = new TimerConsegnaLottoThread();
			}else if(TimerAssociazioneProtocollo.ID_MODULO.equals(timerName)){
				timer = new TimerAssociazioneProtocolloThread();
			}else if(TimerConsegnaEsito.ID_MODULO.equals(timerName)){
				timer = new TimerConsegnaEsitoThread();
			}else if(TimerInserimentoFattura.ID_MODULO.equals(timerName)){
				timer = new TimerInserimentoFatturaThread();
			}else if(TimerAccettazioneFattura.ID_MODULO.equals(timerName)){
				timer = new TimerAccettazioneFatturaThread();
			} else {
				return null;
			}

			return new ThreadTimerObject(timer, properties.getTimers().get(timerName));
			
		}
		
		private boolean lookupTimer(String timerName, BatchProperties properties, GestoreJNDI jndi) {

			boolean isLookup = false;
			long scadenzaWhile = System.currentTimeMillis() + properties.getTimerEJBDeployTimeout();
			
			TimerProperties tp = properties.getTimers().get(timerName);

			while( (System.currentTimeMillis() < scadenzaWhile) && isLookup==false){

				isLookup = false;

				//  check Timer Consegna Fattura
				if(tp.isTimerAbilitato()){
					try{
						String nomeJNDI = properties.getJndiTimerEJBName().get(timerName);
						TimerStartup.log.info("Inizializzo EJB ["+nomeJNDI+"]");
						jndi.lookup(nomeJNDI);
						isLookup = true;
					}catch(Exception e){
						this.logError("Search EJB ["+timerName+"] non trovato: "+e.getMessage());
						try{
							Thread.sleep((new java.util.Random()).nextInt(properties.getTimerEJBDeployCheckInterval())); // random da 0ms a TransactionManagerCheckInterval ms
						}catch(Exception eRandom){}
						continue;
					}
				}else{
					isLookup = true;
				}
			}
			return isLookup;
		}

	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		this.destroy();
	}
	
	private void destroy() {
		
		// Fermo timer
		if(this.timerLst != null && !this.timerLst.isEmpty()) {
			for(TimerObject timer : TimerStartup.this.timerLst) {
				timer.stop(log);
			}
		}

		// DataManger
		DateManager.close();

		// Attendo qualche secondo
		try{
			Thread.sleep(2000);
		}catch(Exception e){}
	}
	
	private static TimerConsegnaFattura createTimerConsegnaFattura(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerConsegnaFattura.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerConsegnaFatturaHome timerHome = 
	        		(TimerConsegnaFatturaHome) PortableRemoteObject.narrow(objref,TimerConsegnaFatturaHome.class);
	        	TimerConsegnaFattura timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
	
	private static TimerInserimentoFattura createTimerInserimentoFattura(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerInserimentoFattura.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerInserimentoFatturaHome timerHome = 
	        		(TimerInserimentoFatturaHome) PortableRemoteObject.narrow(objref,TimerInserimentoFatturaHome.class);
	        	TimerInserimentoFattura timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
	
	private static TimerAccettazioneFattura createTimerAccettazioneFattura(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerAccettazioneFattura.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerAccettazioneFatturaHome timerHome = 
	        		(TimerAccettazioneFatturaHome) PortableRemoteObject.narrow(objref,TimerAccettazioneFatturaHome.class);
	        	TimerAccettazioneFattura timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
	
	private static TimerConsegnaLotto createTimerConsegnaLotto(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerConsegnaLotto.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerConsegnaLottoHome timerHome = 
	        		(TimerConsegnaLottoHome) PortableRemoteObject.narrow(objref,TimerConsegnaLottoHome.class);
	        	TimerConsegnaLotto timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
	
	private static TimerAssociazioneProtocollo createTimerAssociaProtocollo(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerAssociazioneProtocollo.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerAssociazioneProtocolloHome timerHome = 
	        		(TimerAssociazioneProtocolloHome) PortableRemoteObject.narrow(objref,TimerAssociazioneProtocolloHome.class);
	        	TimerAssociazioneProtocollo timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
	
	private static TimerConsegnaEsito createTimerConsegnaEsito(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerConsegnaEsito.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerConsegnaEsitoHome timerHome = 
	        		(TimerConsegnaEsitoHome) PortableRemoteObject.narrow(objref,TimerConsegnaEsitoHome.class);
	        	TimerConsegnaEsito timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}



}
