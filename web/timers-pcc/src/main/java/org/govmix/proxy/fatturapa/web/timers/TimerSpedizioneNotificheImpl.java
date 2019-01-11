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

import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

/**
 * Implementazione dell'interfaccia {@link TimerAccettazioneFattura}.
 * 
 *  
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerSpedizioneNotificheImpl extends AbstractTimerImpl {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6834216547362018351L;

	@Override
	public String getIdModulo() {
		return TimerSpedizioneNotifiche.ID_MODULO;
	}

	@Override
	public AbstractTimerLib getTimerLib() throws Exception {
		return new TimerSpedizioneNotificheLib(this.limit,LoggerManager.getBatchSpedizioneNotificheLogger(),this.logQuery);
	}
//	/**
//	 * serialVersionUID
//	 */
//	private static final long serialVersionUID = 1L;
//
//	/* ******** F I E L D S P R I V A T I ******** */
//
//	/** Contesto */
//	private SessionContext sessionContext;
//	/** Timer associato a questo EJB */
//	private Timer timer;
//	/**
//	 * Timeout che definisce la cadenza di avvio di questo timer. 
//	 */
//	private int timeout;
//	/** Logger utilizzato per debug. */
//	private Logger logTimer = null;
//
//	/** Indicazione se l'istanza in questione e' autoDeployata da JBoss o creata da OpenSPCoop */
//	private boolean deployFromOpenSPCoop = false;
//	/** Indicazione se la gestione e' attualmente in esecuzione */
//	private boolean gestioneAttiva = false;
//	/** Numero di messaggi prelevati sulla singola query */
//	private int limit;
//	/** Indicazione se il logging delle query e' attivo */
//	private boolean logQuery;
//
//	private TimerAccettazioneFatturaLib timerAccettazioneFatturaLib;
//
//
//
//
//
//
//	/* ********  M E T O D I   ******** */
//
//	/**
//	 * Viene chiamato in causa per istanziare il properties reader al primo
//	 * utilizzo del thread che implementa l'EJBean definito in questa classe.
//	 * 
//	 * 
//	 */
//	public void ejbCreate() throws CreateException {
//		WebApiProperties properties = null;
//		try {
//			properties = WebApiProperties.getInstance();
//		} catch (Exception e) {
//			throw new RuntimeException("Errore nell'inizializzazione delle WebApiProperties", e);
//		}
//
//		this.logTimer = LoggerManager.getBatchAccettazioneFatturaLogger();
//
//		this.timeout = properties.getTimerAccettazioneFatturaTimeout();
//		this.limit = properties.getTimerAccettazioneFatturaLimit();
//		this.logQuery = properties.isTimerAccettazioneFatturaLogAbilitato();
//
//	}
//
//	/**
//	 * Metodo necessario per l'implementazione dell'interfaccia
//	 * <code>SessionBean</code>.
//	 * 
//	 * 
//	 */
//	@Override
//	public void ejbRemove() throws EJBException {
//	}
//
//	/**
//	 * Metodo necessario per l'implementazione dell'interfaccia
//	 * <code>SessionBean</code>.
//	 * 
//	 * 
//	 */
//	@Override
//	public void ejbActivate() throws EJBException {
//	}
//
//	/**
//	 * Metodo necessario per l'implementazione dell'interfaccia
//	 * <code>SessionBean</code>.
//	 * 
//	 * 
//	 */
//	@Override
//	public void ejbPassivate() throws EJBException {
//	}
//
//	private static Boolean LOCK = false;
//
//	/**
//	 * Metodo necessario per l'implementazione dell'interfaccia
//	 * <code>TimedObject</code>.
//	 * 
//	 * 
//	 */
//	@Override
//	public void ejbTimeout(Timer timer) throws EJBException {
//
//		WebApiProperties properties;
//		try {
//			properties = WebApiProperties.getInstance();
//		} catch (Exception e) {
//			throw new EJBException("Errore nell'inizializzazione delle WebApiProperties", e);
//		}
//		
//		
//		// Solo il thread avviato da OpenSPCoop deve essere eseguito
//		if( (this.deployFromOpenSPCoop == false)){
//			if(properties.isAutoStartStopTimer()){
//				stop(timer);
//				return;
//			}else{
//				// Viene sempre richiamato ejbCreate() e quindi la variabile deployFromOpenSPCoop è sempre null.
//				// La single instance viene gestiti quindi con un lock
//				synchronized (LOCK) {
//					if(LOCK){
//						this.logTimer.info("Timer "+TimerAccettazioneFattura.ID_MODULO+" gia' avviato.");
//						stop(timer);
//						return;
//					}
//					else{
//						LOCK = true;
//
//						/**
//						 * Aggiungo una sleep di 5 secondi per far provocare il LOCK sopra presente, per le altre istanze di timer
//						 * in modo da avere solamente una istanza in esecuzione
//						 */
//						try{
//							for (int i = 0; i < 10; i++) {
//								Thread.sleep(500);
//							}
//						}catch(Exception eSleep){}
//					}
//				}
//			}
//		}
//
//		try{
//
//			// Controllo che l'inizializzazione corretta delle risorse sia effettuata
//			if(timer == null){
//				String msgErrore = "inizializzazione del Timer non effettuata";
//				this.logTimer.error(msgErrore);
//				stop(timer);
//				return;
//			}
//
//			if(this.gestioneAttiva){
//				this.logTimer.info("Timer "+TimerAccettazioneFattura.ID_MODULO+" gia' avviato.");
//				return;
//			}
//
//			try{
//				// Prendo la gestione
//				this.gestioneAttiva = true;
//				
//				if(this.timerAccettazioneFatturaLib == null) {
//					this.timerAccettazioneFatturaLib = new TimerAccettazioneFatturaLib(this.limit,this.logTimer, this.logQuery);
//				}
//
//				this.timerAccettazioneFatturaLib.execute();
//
//			}catch(Exception e){
//				this.logTimer.error("Errore durante la "+TimerAccettazioneFattura.ID_MODULO+": "+e.getMessage(),e);
//			}finally{
//				// Rilascio la gestione
//				this.gestioneAttiva = false;
//			}
//
//		}finally{
//			synchronized (LOCK) {
//				LOCK = false;
//			}	
//		}
//
//	}
//
//	/**
//	 * Imposta il Contesto del Session Bean. Metodo necessario per
//	 * l'implementazione dell'interfaccia <code>SessionBean</code>.
//	 * 
//	 * @param aContext
//	 *            Contesto del Session Bean.
//	 * 
//	 */
//	@Override
//	public void setSessionContext(SessionContext aContext) throws EJBException {
//		this.sessionContext = aContext;
//	}
//
//	/**
//	 * Inizializza il Timer di gestione
//	 * 
//	 * 
//	 */
//	public boolean start() throws EJBException {
//		if( this.timer != null ){
//
//			this.logTimer.info("Timer "+TimerAccettazioneFattura.ID_MODULO+" avviato");
//			return false;
//
//		} else {
//
//			WebApiProperties properties = null;
//			try {
//				properties = WebApiProperties.getInstance();
//			} catch (Exception e) {
//				throw new EJBException("Errore nell'inizializzazione delle WebApiProperties", e);
//			}
//
//			if(properties.isTimerAccettazioneFatturaAbilitato()){
//				this.logTimer.info("Avvio timer "+TimerAccettazioneFattura.ID_MODULO+"");
//				
//				this.deployFromOpenSPCoop = true;	    
//				
//				try{
//					Thread.sleep(1000); // tempo necessario per un corretto avvio in JBoss 4.0.3...
//				}catch(Exception e){}
//				Date now = DateManager.getDate();
//				long timeout = 1000 * this.timeout;
//				try {
//					// creo il timer
//					TimerService ts = this.sessionContext.getTimerService();
//					this.timer = ts.createTimer(now, timeout, TimerAccettazioneFattura.ID_MODULO);
//
//					this.logTimer.info("Timer "+TimerAccettazioneFattura.ID_MODULO+" avviato");
//				} catch (Exception e) {
//					stop();
//					this.logTimer.error("Errore durante la creazione del timer: "+e.getMessage(),e);
//				}
//				return this.timer != null;
//
//			}else{
//
//				this.logTimer.info("Timer "+TimerAccettazioneFattura.ID_MODULO+" disabilitato");
//				return false;
//
//			}
//		}
//	}
//
//	/**
//	 * Restituisce lo stato del timer di gestione
//	 * 
//	 * 
//	 */
//	public boolean isStarted() throws EJBException {
//		return this.timer != null;
//	}
//
//	/**
//	 * Annulla il timer di gestione
//	 * 
//	 * 
//	 */
//	public void stop(Timer atimer) throws EJBException {
//		if (atimer != null) {
//			atimer.cancel();
//		}
//	}
//
//	/**
//	 * Annulla il timer di gestione interno
//	 * 
//	 * 
//	 */
//	public void stop() throws EJBException {
//
//		if (this.timer != null) {
//			this.timer.cancel();
//			this.timer = null;
//		}
//	}
//
}
