/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.util.ArrayList;
import java.util.List;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
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

			try {
				log.info("Info versione: " + CommonsProperties.getInstance(log).getInfoVersione());
			} catch (Exception e) {
				TimerStartup.log.error("Errore durante la Info versione", e);
				return;
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
			if(TimerRispedizioneMessaggi.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerRispedizioneMessaggi(properties);
			}else if(TimerSpedizioneEsiti.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerSpedizioneEsiti(properties);
			}else if(TimerSpedizioneNotifiche.ID_MODULO.equals(timerName)){
				timer = TimerStartup.createTimerSpedizioneNotifiche(properties);
			} else {
				return null;
			}

			return new EJBTimerObject(timer, properties.getTimers().get(timerName));
			
		}
		
		private TimerObject createThreadTimer(String timerName, BatchProperties properties) throws Exception {

			AbstractTimerThread timer;
			if(TimerRispedizioneMessaggi.ID_MODULO.equals(timerName)){
				timer = new TimerRispedizioneMessaggiThread();
			}else if(TimerSpedizioneEsiti.ID_MODULO.equals(timerName)){
				timer = new TimerSpedizioneEsitiThread();
			}else if(TimerSpedizioneNotifiche.ID_MODULO.equals(timerName)){
				timer = new TimerSpedizioneNotificheThread();
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
	
	private static TimerRispedizioneMessaggi createTimerRispedizioneMessaggi(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerRispedizioneMessaggi.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerRispedizioneMessaggiHome timerHome = 
	        		(TimerRispedizioneMessaggiHome) PortableRemoteObject.narrow(objref,TimerRispedizioneMessaggiHome.class);
	        	TimerRispedizioneMessaggi timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
	
	private static TimerSpedizioneEsiti createTimerSpedizioneEsiti(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerSpedizioneEsiti.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerSpedizioneEsitiHome timerHome = 
	        		(TimerSpedizioneEsitiHome) PortableRemoteObject.narrow(objref,TimerSpedizioneEsitiHome.class);
	        	TimerSpedizioneEsiti timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
	
	private static TimerSpedizioneNotifiche createTimerSpedizioneNotifiche(BatchProperties properties) throws Exception {
	        
	        	GestoreJNDI jndi = null;
	        	if(properties.getJndiContextTimerEJB()==null)
	        		jndi = new GestoreJNDI();
	        	else
	        		jndi = new GestoreJNDI(properties.getJndiContextTimerEJB());
		    
	        	String nomeJNDI = properties.getJndiTimerEJBName().get(TimerSpedizioneNotifiche.ID_MODULO);
	        	Object objref = jndi.lookup(nomeJNDI);
	        	TimerSpedizioneNotificheHome timerHome = 
	        		(TimerSpedizioneNotificheHome) PortableRemoteObject.narrow(objref,TimerSpedizioneNotificheHome.class);
	        	TimerSpedizioneNotifiche timerDiServizio = timerHome.create();	
	        
	            return timerDiServizio;
		    
	        
	}
}
