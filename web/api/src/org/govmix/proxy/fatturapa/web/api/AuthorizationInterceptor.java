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
