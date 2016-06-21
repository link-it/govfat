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
package org.govmix.proxy.fatturapa.web.api;

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
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.utils.Identity;

public class AuthorizationInterceptor extends AbstractPhaseInterceptor<Message> {

	private UtenteBD utenteBD;
	private Logger log = LoggerManager.getEndpointEnteLogger();
	
	public AuthorizationInterceptor() throws Exception {
		super(Phase.UNMARSHAL);
		this.utenteBD = new UtenteBD();
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
			String principal = null;
			if(WebApiProperties.getInstance().isAuthenticationManaged()) {
				log.info("Auth managed: setto il nome utente dal principal");
				IdUtente idUtente = new IdUtente();
				idUtente.setUsername(identity.getPrincipal());
				
				if(!this.utenteBD.exists(idUtente)) {
					throw new Exception("Utente ["+identity.getPrincipal()+"] non esistente");
				}
				
				principal = identity.getPrincipal();
			} else {
				log.info("Auth user: cerco il nome utente nel db");
				if(!this.utenteBD.checkPw(identity.getUsername(), identity.getPassword())) {
					throw new Exception("Password errata per l'utente ["+identity.getUsername()+"]");
				}
				
				
				principal = identity.getUsername();
			}
			
			log.info("User: " + principal);
			headers.put("PRINCIPAL_PROXY", Arrays.asList(principal));
		} catch (Exception e) {
			throw new Fault(e);
		}
		

	}


}
