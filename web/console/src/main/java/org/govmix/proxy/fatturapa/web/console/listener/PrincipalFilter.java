/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.mbean.LoginMBean;
import org.openspcoop2.utils.Identity;

/****
* PrincipalFilter Filtro base per il controllo della login via Container basata sulla presenza del principal.
* 
* @author Pintori Giuliano (pintori@link.it)
* @author $Author: mergefairy $
* @version $Rev: 10491 $, $Date: 2015-01-13 10:33:50 +0100 (Tue, 13 Jan 2015) $
 *
 */
public class PrincipalFilter implements Filter {
	
	public static final String PRINCIPAL_ERROR_MSG = "principalErrorMsg"; 

	private Logger log = LoggerManager.getConsoleLogger();

	private String loginPage = "public/login.jsf";
	private String timeoutPage = "public/timeoutPage.jsf";

	private List<String> excludedPages = null;

	public static final String USE_PRINCIPAL = "usePrincipal";

	private boolean usePrincipal =false;

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.excludedPages = new ArrayList<String>();
		this.excludedPages.add("public");

		String usePrincipalProp = config.getInitParameter(PrincipalFilter.USE_PRINCIPAL);

		if(usePrincipalProp != null){
			try{
				this.usePrincipal = Boolean.parseBoolean(usePrincipalProp);
			}catch(Exception e){
				this.usePrincipal = false;
			}
		}
		
		this.log.debug("Usa il principal per il controllo autorizzazione utente ["+this.usePrincipal+"]"); 

		// popolo la white list degli oggetti che possono essere visti anche se non authenticati, in particolare css, immagini, js, ecc...
		//if(this.usePrincipal){
			this.excludedPages.add("a4j");
			this.excludedPages.add("images");
			this.excludedPages.add("css");
			this.excludedPages.add("scripts");
		//}
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		boolean sessionControlRequiredForThisResource = false;
		String requestPath = null;
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
			httpServletRequest = (HttpServletRequest) request;
			httpServletResponse = (HttpServletResponse) response;
//			httpServletResponse = new PrincipalHttpResponseWrapper((HttpServletResponse) response);
			
			requestPath = httpServletRequest.getRequestURI();
			
			// Autenticazione gestita dall'applicazione 
			sessionControlRequiredForThisResource = isSessionControlRequiredForThisResource(httpServletRequest);
			if(sessionControlRequiredForThisResource)
				this.log.debug("Richiesta risorsa ["+requestPath+"], protetta ["+sessionControlRequiredForThisResource+"]");
			
			if(!this.usePrincipal){
				// is session expire control required for this request?
				if (this.isSessionControlRequiredForThisResource(httpServletRequest)) {

					// is session invalid?
					if (isSessionInvalid(httpServletRequest)) {					
						String redirPageUrl = httpServletRequest.getContextPath() + "/";
						//se la pagina richiesta e' quella di login allora redirigo direttamente a quella, altrimenti a quella di timeout
						//redirPageUrl += StringUtils.contains(httpServletRequest.getRequestURI(), getLoginPage()) ? getLoginPage() : getTimeoutPage();
						redirPageUrl += this.getRedirPage(httpServletRequest);
						//					log.info("session is invalid! redirecting to page : " + redirPageUrl);
						httpServletResponse.sendRedirect(redirPageUrl);
						return;
					}
				}
			}else {
				
				
				if (sessionControlRequiredForThisResource) {
					HttpSession sessione = httpServletRequest.getSession();

//					this.log.debug("Richiesta risorsa privata ["+httpServletRequest.getRequestURI()+"]"); 
					// Ho richiesto una risorsa protetta cerco il login bean
					// Cerco il login bean nella sessione, se non c'e' provo a cercarlo nella sessione di JSF
					LoginMBean lb = (LoginMBean) sessione.getAttribute("loginBean");
					
					this.log.debug("LoginBean trovato in sessione ["+(lb!= null)+"]"); 

					if(lb == null){
						try{
							FacesContext currentInstance = FacesContext.getCurrentInstance();
							this.log.debug("FacesContext not null ["+(currentInstance!= null)+"]"); 
							if(currentInstance != null){
								ExternalContext ec = currentInstance.getExternalContext();
								this.log.debug("ExternalContext not null ["+(ec!= null)+"]");
								if(ec != null){
									lb = (LoginMBean) ec.getSessionMap().get("loginBean");
									this.log.debug("LoginBean trovato in nella SessionMap JSF ["+(lb!= null)+"]"); 
								}
							}
						}catch(Exception e){
							lb = null;
						}
					}
					
					// check lingua
					
					this.log.debug("Controllo Locale in corso ...");
					Locale loc = null;
					try{
						 loc = FacesContext.getCurrentInstance().getViewRoot().getLocale();
					}catch(Exception e){
						this.log.debug("Errore durante controllo Locale: "+ e.getMessage());
						loc = Locale.getDefault();
					}
					
					log.debug("Locale trovato ["+loc.toString()+"]");
					
					
					//this.log.debug("Login Bean trovato in sessione ["+(lb != null)+"]");

					// Se login bean == null lo creo
					if(lb == null){
						// prelevo la lingua della richiesta http
						Locale localeRequest = request.getLocale();
						log.debug("Locale trovato nella Request["+localeRequest.toString()+"]");
						
						lb = new LoginMBean(true); 
						lb.setIsLoggedIn(false);
						lb.impostaLocale(localeRequest);
					}
					
					this.log.debug("Lingua impostata nel Login Bean: ["+lb.getCurrentLang()+"]");

//					this.log.debug("Login Bean Tipo: ["+lb.getClass().getName()+"]");
					this.log.debug("Login Bean, Utente Loggato: ["+lb.getIsLoggedIn()+"]"); 
					// se non e' loggato lo loggo
					if(!lb.getIsLoggedIn()){
						// Controllo principal
						Identity identity = new Identity(httpServletRequest);
						String username = identity.getPrincipal();
						
						this.log.debug("Username trovato nel principal [identity.getPrincipal()]: ["+username+"]");
						
						this.log.debug("Username trovato nel principal [identity.getUsername()]: ["+identity.getUsername()+"]"); 

						// Se l'username che mi arriva e' settato vuol dire che sono autorizzato dal Container
						if(username != null){
							// Creo il login bean ed effettuo il login
							lb.setNoPasswordLogin(true);
							lb.setUsername(username); 
							String loginResult = lb.login();
							if(loginResult.equals("login")){
								this.log.debug("Utente ["+username+"] non autorizzato.");
								lb.logout();
								String redirPageUrl = httpServletRequest.getContextPath() + "/public/error.jsf";//+"index.jsp" ;
								
								// Messaggio di errore
								sessione.setAttribute(PRINCIPAL_ERROR_MSG, org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("login.form.principalFilter.credenzialiError", username)); 
								
								httpServletResponse.sendRedirect(redirPageUrl);
								return;
							}else if(loginResult.equals("loginError")){
								this.log.debug("Errore durante il login per l'utente ["+username+"].");
								lb.logout();
								String redirPageUrl = httpServletRequest.getContextPath() + "/public/error.jsf" ;
								
								// Messaggio di errore
								sessione.setAttribute(PRINCIPAL_ERROR_MSG, org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("login.form.genericError", username)); 
								
								httpServletResponse.sendRedirect(redirPageUrl);
								return;
							}else{
								this.log.debug("Utente ["+username+"] autorizzato.");
								sessione.setAttribute("loginBean", lb);
								String redirPageUrl = httpServletRequest.getContextPath() + "/"+"index.jsp" ;
								httpServletResponse.sendRedirect(redirPageUrl);
								return;
							}
						}else{
							
							// ERRORE
							sessione.setAttribute("loginBean", null);
							String redirPageUrl = httpServletRequest.getContextPath() + "/" + "pages/listaFatture.jsf";
							//se la pagina richiesta e' quella di login allora redirigo direttamente a quella, altrimenti a quella di timeout
							//redirPageUrl += StringUtils.contains(httpServletRequest.getRequestURI(), getLoginPage()) ? getLoginPage() : getTimeoutPage();
//							redirPageUrl += getRedirPage(httpServletRequest);
							this.log.debug("Username NULL redirect ["+redirPageUrl+"]");
							//					log.info("session is invalid! redirecting to page : " + redirPageUrl);
							httpServletResponse.sendRedirect(redirPageUrl);
							return;
						}
					}	else {
						this.log.debug("Utente ["+lb.getUsername()+"] loggato controllo validita' sessione..."); 
						// controllo se la sessione e' valida
						boolean isSessionInvalid = this.isSessionInvalid(httpServletRequest);

						// Se non sono loggato mi autentico e poi faccio redirect verso la pagina di welcome
						if(isSessionInvalid){
							this.log.debug("Utente ["+lb.getUsername()+"] Loggato, ma sessione non valida.");
							lb.logout();
							String redirPageUrl = httpServletRequest.getContextPath() + "/"+"index.jsp" ;
							httpServletResponse.sendRedirect(redirPageUrl);
							return;
						} 
					}
				}
			}
		}  

//		filterChain.doFilter(request, response);
		
		try {
		//	this.log.debug("filterChain.dofilter..."); 
			filterChain.doFilter(request, response);
		} catch (ServletException e) {
//			this.log.debug(" ServletException ["+e.getRootCause()+"]"); 
			Throwable rootCause = e.getRootCause();
			if(rootCause != null){
				if (rootCause instanceof ViewExpiredException) { // This is true for any FacesException.
					
					this.log.debug("Rilevata ViewExpiredException: ["+rootCause.getMessage()+"]"); 
					if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
						httpServletRequest = (HttpServletRequest) request;
						httpServletResponse = (HttpServletResponse) response;
//						httpServletResponse = new PrincipalHttpResponseWrapper((HttpServletResponse) response);
						
						String redirPageUrl = httpServletRequest.getContextPath() + "/"+ "public/timeoutPage.jsf";
						httpServletResponse.sendRedirect(redirPageUrl);
						return;
					}
					
					throw (ViewExpiredException) rootCause; // Throw wrapped ViewExpiredException instead of ServletException.
				} else if (rootCause instanceof RuntimeException) { // This is true for any FacesException.
					throw (RuntimeException) rootCause; // Throw wrapped RuntimeException instead of ServletException.
				} else {
					throw e;
				}
			} else {
				throw e;
			}
		} 
	}

	/**
	 * 
	 * session shouldn't be checked for some pages. For example: for timeout page..
	 * Since we're redirecting to timeout page from this filter,
	 * if we don't disable session control for it, filter will again redirect to it
	 * and this will be result with an infinite loop... 
	 */
	private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
		String requestPath = httpServletRequest.getRequestURI();

		boolean controlRequired = false;
		//		if(StringUtils.contains(requestPath, this.timeoutPage) || 
		//				StringUtils.contains(requestPath, this.loginPage)){
		//			controlRequired = false;
		//		}else{
		controlRequired = true;
		if(this.excludedPages.size() > 0)
			for (String page : this.excludedPages) {
				if(StringUtils.contains(requestPath, page)){
					controlRequired = false;
					break;
				}
			}
		else
			controlRequired = true;
		//		}

		return controlRequired;
	}

	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null)
				&& !httpServletRequest.isRequestedSessionIdValid();
		return sessionInValid;
	}
	
	
//	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
//		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() == null) || !httpServletRequest.isRequestedSessionIdValid();
//		return sessionInValid;
//	}

	private String getRedirPage(HttpServletRequest req){
		String ctx = req.getContextPath();
		String reqUri = req.getRequestURI();

		String reqPage = StringUtils.remove(reqUri, ctx);

		String res = "";
		if("".equals(reqPage) || "/".equals(reqPage) || StringUtils.contains(reqPage, this.loginPage))
			res = this.loginPage;
		else
			res = this.timeoutPage;

		return res;
	}

}

/*
 * 
 	Identity identity = new Identity(httpServletRequest);
				String username = identity.getUsername();
				HttpSession sessione = httpServletRequest.getSession();

				// Se l'username che mi arriva e' settato vuol dire che sono autorizzato dal Container
				if(username != null){
					// Cerco il login bean nella sessione, se non c'e' provo a cercarlo nella sessione di JSF
					LoginBean lb = (LoginBean) sessione.getAttribute("loginBean");

					if(lb == null){
						try{
							FacesContext currentInstance = FacesContext.getCurrentInstance();
							if(currentInstance != null){
								ExternalContext ec = currentInstance.getExternalContext();
								if(ec != null){
									lb = (LoginBean) ec.getSessionMap().get("loginBean");
								}
							}
						}catch(Exception e){}
					} 

					if(lb != null){
						// Controllo se sono ancora loggato
						boolean isLogged = lb == null ? false : lb.getIsLoggedIn();

						// Se non sono loggato mi autentico e poi faccio redirect verso la pagina di welcome
						if(!isLogged){
							lb.setIsLoggedIn(true);
							String redirPageUrl = httpServletRequest.getContextPath() + "/"+"index.jsp" ;
							httpServletResponse.sendRedirect(redirPageUrl);
							return;
						} 
						// se sono loggato non faccio nulla...

						// Se il loginBean ancora non esiste (primo accesso) allora lo creo
					}else {
						lb = new LoginBean(true); 
						lb.setUsername(username); 
						lb.setIsLoggedIn(true);
						String redirPageUrl = httpServletRequest.getContextPath() + "/"+"index.jsp" ;
						httpServletResponse.sendRedirect(redirPageUrl);
						return;
					}

				}else {
					//Se non trovo l'username invalido tutto.
					if (isSessionControlRequiredForThisResource(httpServletRequest)) {

						// is session invalid?
						if (isSessionInvalid(httpServletRequest)) {		
							sessione.setAttribute("loginBean", null);
							String redirPageUrl = httpServletRequest.getContextPath() + "/";
							//se la pagina richiesta e' quella di login allora redirigo direttamente a quella, altrimenti a quella di timeout
							//redirPageUrl += StringUtils.contains(httpServletRequest.getRequestURI(), getLoginPage()) ? getLoginPage() : getTimeoutPage();
							redirPageUrl += getRedirPage(httpServletRequest);
							//					log.info("session is invalid! redirecting to page : " + redirPageUrl);
							httpServletResponse.sendRedirect(redirPageUrl);
							return;
						}
					}
				}
 */
