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
package org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente;

import org.apache.log4j.Logger;

public class InvioNotificaFactory {

	private InvioNotifica invioNotificaSDICoop;
	private InvioNotifica invioNotificaSPCoop;
	private Logger log;
	
	public InvioNotificaFactory(InvioNotifica invioNotificaSPCoop, InvioNotifica invioNotificaSDICoop, Logger log) throws Exception {
		
		if(invioNotificaSDICoop == null) {
			throw new Exception("Canale SdiCoop per l'invio delle notifiche a SdI non definito");
		}
		if(invioNotificaSPCoop == null) {
			throw new Exception("Canale SPCoop per l'invio delle notifiche a SdI non definito");
		}
		this.invioNotificaSDICoop = invioNotificaSDICoop; 
		this.invioNotificaSPCoop = invioNotificaSPCoop;
		this.log = log;
	}
	
	
	public NotificaECResponse invia(NotificaECRequest request, boolean spcoopFirst) throws Exception {
		InvioNotifica invioNotificaPrincipale;
//		InvioNotifica invioNotificaSecondario;
		
		String invioNotificaPrincipaleName;
//		String invioNotificaSecondariaName;
		if(spcoopFirst) {
			invioNotificaPrincipale = this.invioNotificaSPCoop;
//			invioNotificaSecondario = this.invioNotificaSDICoop;
			
			invioNotificaPrincipaleName = "SPCoop";
//			invioNotificaSecondariaName = "SDICoop";

		} else {
			invioNotificaPrincipale = this.invioNotificaSDICoop;
//			invioNotificaSecondario = this.invioNotificaSPCoop;

			invioNotificaPrincipaleName = "SDICoop";
//			invioNotificaSecondariaName = "SPCoop";
		}
		

//		if(invioNotificaPrincipale!=null) {
			
//			try {
				this.log.info("Invio notifica ["+request.getNotifica().getIdentificativoSdi()+"] tramite il canale principale " + invioNotificaPrincipaleName + " in corso...");
				NotificaECResponse resp = invioNotificaPrincipale.invia(request);
				
				this.log.info("Invio notifica ["+request.getNotifica().getIdentificativoSdi()+"] tramite il canale principale " + invioNotificaPrincipaleName + " completato con response code. " + resp.getEsitoChiamata());
//				if(resp.getEsitoChiamata() < 299 || invioNotificaSecondario==null) {
					this.log.info("Restituisco esito notifica ["+request.getNotifica().getIdentificativoSdi()+"] inviata tramite il canale principale " + invioNotificaPrincipaleName);
					return resp;
//				}
//			} catch(Exception e) {
//				if(invioNotificaSecondario==null) {
//					throw e;
//				} else {
//					this.log.warn("Errore durante l'invio notifica tramite il canale principale " + invioNotificaPrincipaleName + ". Invio tramite canale secondario " + invioNotificaSecondariaName + ": " + e.getMessage(), e);
//				}
//			}
//		}
//		
//		if(invioNotificaSecondario!=null) {
//			this.log.info("Invio notifica ["+request.getNotifica().getIdentificativoSdi()+"] tramite il canale secondario " + invioNotificaSecondariaName + " in corso...");
//			NotificaECResponse resp = invioNotificaSecondario.invia(request);
//			
//			this.log.info("Invio notifica ["+request.getNotifica().getIdentificativoSdi()+"] tramite il canale secondario " + invioNotificaSecondariaName + " completato con response code. " + resp.getEsitoChiamata());
//			return resp;
//		} else {
//			this.log.warn("Invio notifica ["+request.getNotifica().getIdentificativoSdi()+"]. Nessun canale configurato");
//			return null;//non accadra' mai in quanto nel costruttore controlliamo che almeno uno dei due canali sia attivo
//		}
		
		
	}

}