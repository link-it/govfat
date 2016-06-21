package org.govmix.proxy.fatturapa.web.timers;

import java.rmi.RemoteException;

public interface IEJBTimer extends javax.ejb.EJBObject {


    /**
     * Inizializza il Timer di gestione 
     *
     * 
     */
    boolean start() throws RemoteException;
    
    /**
     * Restituisce lo stato del timer di gestione
     *
     * 
     */
    boolean isStarted() throws RemoteException;
    
    /**
     * Ferma il Timer di gestione
     *
     * 
     */
    void stop() throws RemoteException;

}
