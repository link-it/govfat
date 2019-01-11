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
package org.govmix.proxy.fatturapa.web.commons.authorization;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.utils.Identity;
//import javax.servlet.http.HttpServletRequest;
//import org.openspcoop2.utils.Identity;

public class SimulationAuthorizationInterceptor extends AbstractPhaseInterceptor<Message> {

	private UtenteBD utenteBD;
	private Logger log;
	
	public static final String HEADER_PRINCIPAL = "X-ProxyFatturaPA-Principal"; 
	
	public SimulationAuthorizationInterceptor() throws Exception {
		super(Phase.UNMARSHAL);
		this.log = LoggerManager.getEndpointProxyPccLogger();
		this.utenteBD = new UtenteBD(this.log);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
		@SuppressWarnings("unchecked")
		Map<String, List<String>> headers = (Map<String, List<String>>)message.get(Message.PROTOCOL_HEADERS);
		
		if (headers == null) {
			headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
			message.put(Message.PROTOCOL_HEADERS, headers);
		}
		HttpServletRequest req = (HttpServletRequest) message.get("HTTP.REQUEST");
		Identity identity = new Identity(req);
		
		try {
			String principal = identity.getUsername();

			this.log.debug("Utente autenticato: " + principal);
			
			if(principal == null) {
				this.log.debug("Nessun utente autenticato, simulo l'autenticazione");

				if(headers.containsKey(HEADER_PRINCIPAL)) {
					principal = headers.get(HEADER_PRINCIPAL).get(0);
					this.log.debug("Utente autenticato (simulato): " + principal);
				} else {
					this.log.debug("Nessun utente autenticato simulato");
				}
			}
			
			if(principal == null) {
				throw new Exception("Utente non autenticato");
			}
			
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(principal);
			
			if(!this.utenteBD.exists(idUtente)) {
				throw new Exception("Utente ["+principal+"] non esistente");
			}

			log.info("User: " + principal);
			headers.put("PRINCIPAL_PROXY", Arrays.asList(principal));
		} catch (Exception e) {
			throw new Fault(e);
		}
		

	}


}
