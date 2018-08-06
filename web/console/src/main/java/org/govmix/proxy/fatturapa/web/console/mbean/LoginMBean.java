/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.mbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.Evento;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.constants.UserType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.dao.DBLoginDao;
import org.govmix.proxy.fatturapa.web.console.dao.ILoginDao;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.LoginBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;

import net.sourceforge.spnego.SpnegoAuthenticator;

/**
 * LoginMBean bean di sessione per la gestione della sessione dell'utente.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class LoginMBean extends LoginBean{

    public static final String S_AUTHENTICATOR_KEY = "S_AUTHENTICATOR";

	private static Logger log = LoggerManager.getConsoleLogger();

	private Utente loggedUtente = null;

	private List<Dipartimento> listDipartimenti;
	
	private Map<String,Ente> mapEnti;

	//	private Ente ente = null;

	private Protocollo protocollo = null;


	public LoginMBean(boolean initDao){
		super(initDao);
	}

	public LoginMBean(){
		super();
	}

	@Override
	protected void init() {
		if(this.isInitDao()){
			this.setLoginDao(new DBLoginDao());
		}

		String fieldsToUpdate = this.getLanguageForm().getId() + "_formPnl,headerct,headerct_parent,footerct,mainct,menuct";
		// Fix per il form della lingua nella grafica rossa
		this.getLanguageForm().getLingua().setFieldsToUpdate(fieldsToUpdate );
	}

	@Override
	public String login() {
		if(!this.isNoPasswordLogin()){

			if(null == this.getUsername() && this.getPassword() == null){		
				return "login";
			}

			try{
				LoginMBean.log.info("Verifico le credenziali per l'utente ["+this.getUsername()+"]");

				if(this.getLoginDao().login(this.getUsername(),this.getPassword())){
					//			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
					//			HttpSession session = (HttpSession) ec.getSession(true);
					//			session.setAttribute("logged", true);
					this.setIsLoggedIn(true);
					this.loggedUtente = ((ILoginDao)this.getLoginDao()).getLoggedUtente(this.getUsername(),this.getPassword()); 
					this.protocollo = ((ILoginDao)this.getLoginDao()).getProtocollo(ConsoleProperties.getInstance(LoginMBean.log).getProtocollo());
					this.listDipartimenti = ((ILoginDao)this.getLoginDao()).getListaDipartimentiUtente(this.loggedUtente);
					LoginMBean.log.info("Utente ["+this.getUsername()+"] autenticato con successo");

					if(ConsoleProperties.getInstance(LoginMBean.log).isUtilizzaProfiloUtente()){
						if(this.loggedUtente.getTipo() != null && this.loggedUtente.getTipo().equals(UserType.ESTERNO)){
							Evento eventoLogin = getEventoLogin();
							((ILoginDao)this.getLoginDao()).registraEvento(eventoLogin);
						}
					}

					return "loginSuccess";
				}else{
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									Utils.getInstance().getMessageWithParamsFromResourceBundle("login.form.credenzialiError",this.getUsername()),null));
				}
			}catch(ServiceException e){
				LoginMBean.log.error("Si e' verificato un errore durante il login: "+ e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Utils.getInstance().getMessageWithParamsFromResourceBundle("login.form.genericError",this.getUsername()),null));
			}catch(Exception e){
				LoginMBean.log.error("Si e' verificato un errore durante il login: "+ e.getMessage(), e);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Utils.getInstance().getMessageWithParamsFromResourceBundle("login.form.genericError",this.getUsername()),null));
				return "loginError";
			}
		}else{
			LoginMBean.log.info("Verifico il ticket per l'utente ["+this.getUsername()+"]");
			try{
				this.loggedUtente = ((ILoginDao)this.getLoginDao()).getLoggedUtente(this.getUsername()); 
				if(this.loggedUtente != null){
					this.setIsLoggedIn(true);
					this.protocollo = ((ILoginDao)this.getLoginDao()).getProtocollo(ConsoleProperties.getInstance(LoginMBean.log).getProtocollo());
					this.listDipartimenti = ((ILoginDao)this.getLoginDao()).getListaDipartimentiUtente(this.loggedUtente);
					LoginMBean.log.info("Utente ["+this.getUsername()+"] autenticato con successo");

					if(ConsoleProperties.getInstance(LoginMBean.log).isUtilizzaProfiloUtente()){
						if(this.loggedUtente.getTipo() != null && this.loggedUtente.getTipo().equals(UserType.ESTERNO)){
							Evento eventoLogin = getEventoLogin();
							((ILoginDao)this.getLoginDao()).registraEvento(eventoLogin);
						}
					}				
					return "loginSuccess";
				}
			}catch(ServiceException e){
				LoginMBean.log.error("Si e' verificato un errore durante il login: "+ e.getMessage(), e);
				return "loginError";
			}catch(Exception e){
				LoginMBean.log.error("Si e' verificato un errore durante il login: "+ e.getMessage(), e);
				return "loginError";
			}
		}
		return "login";  
	}

	@Override
	public String logout(){
		LoginMBean.log.info("Logout utente ["+this.getUsername()+"] in corso...");
		SpnegoAuthenticator authenticator = null;
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			if(fc!= null){
				ExternalContext externalContext = fc.getExternalContext();
				if(externalContext != null){
					HttpSession session = (HttpSession)externalContext.getSession(false);

					// Logout Spnego
					if(this.isNoPasswordLogin()){
						try{
							LoginMBean.log.info("Logout Spnego in corso...");
							Object obj =  session.getAttribute(LoginMBean.S_AUTHENTICATOR_KEY);
							if(obj!=null){
								authenticator = (SpnegoAuthenticator) obj;
								LoginMBean.log.info("Authenticator Spnego trovato.");
								authenticator.dispose();
							}
							LoginMBean.log.info("Logout Spnego completato.");
						}catch(Exception e){
							LoginMBean.log.error("Errore durante l'esecuzione del metodo dispose di SPNEGO: "+e.getMessage(), e); 
						}
					}

					externalContext.getSessionMap().put("loginBean", null);
					session.setAttribute("loginBean", null); 
					session.invalidate();
					LoginMBean.log.info("Logout utente ["+this.getUsername()+"] Invalidata Sessione.");
					if(ConsoleProperties.getInstance(LoginMBean.log).isUtilizzaProfiloUtente()){
						if(this.loggedUtente.getTipo() != null && this.loggedUtente.getTipo().equals(UserType.ESTERNO)){
							Evento eventoLogin = getEventoLogout();
							((ILoginDao)this.getLoginDao()).registraEvento(eventoLogin);
						}
					}

				}
			}
			LoginMBean.log.info("Logout utente ["+this.getUsername()+"] completato.");
		}catch(Exception e){
			LoginMBean.log.error("Errore durante il logout: "+e.getMessage(), e); 
		}

		if(!this.isNoPasswordLogin())
			return "login";
		else 
			return "logoutAS";
	}

	public Utente getLoggedUtente() {
		return this.loggedUtente;
	}

	public void setLoggedUtente(Utente loggedUtente) {
		this.loggedUtente = loggedUtente;
	}

	public List<Dipartimento> getListDipartimenti() {

		if(this.listDipartimenti == null || this.listDipartimenti.isEmpty() )
			this.listDipartimenti = ((ILoginDao)this.getLoginDao()).getListaDipartimentiUtente(this.loggedUtente);


		return this.listDipartimenti;
	}
	
	public Map<String,Ente> getMapEnti(){
		if(this.mapEnti == null || this.mapEnti.isEmpty() ) {
			this.mapEnti = new HashMap<String, Ente>();
			List<Dipartimento> listaDipartimenti = this.getListDipartimenti();
			if(listaDipartimenti != null && listaDipartimenti.size() > 0)
				for (Dipartimento dipartimento : listaDipartimenti) {
					IdEnte ente = dipartimento.getEnte();
					if(!this.mapEnti.containsKey(ente.getNome())){
						this.mapEnti.put(ente.getNome(),((ILoginDao)this.getLoginDao()).getEnte(ente.getNome()));
					}
				}
		}
			
		return this.mapEnti;
	}

	public void setListDipartimenti(List<Dipartimento> listDipartimenti) {
		this.listDipartimenti = listDipartimenti;
	}

	public boolean isShowMenuAnagrafica(){
		return !this.loggedUtente.getRole().equals(UserRole.USER);
	}

	public boolean isShowMenuItemUtenti(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN);
	}

	public boolean isShowMenuItemProtocolli(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN);
	}

	public boolean isShowMenuItemDipartimenti(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN) || this.loggedUtente.getRole().equals(UserRole.DEPT_ADMIN);
	}

	public boolean isShowMenuItemOperazioniPCC(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN);
	}

	public boolean isShowMenuItemRispedizioni(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN);
	}
	
	public boolean isShowMenuItemConservazione(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN);
	}

	public void updateProtocollo() throws Exception{
		try{
			this.protocollo = ((ILoginDao)this.getLoginDao()).getProtocollo(ConsoleProperties.getInstance(LoginMBean.log).getProtocollo());
		}catch(Exception e){
			LoginMBean.log.error("Si e' verificato un errore durante la lettura del protocollo: "+ e.getMessage(), e);
			throw e;
		}
	}

	public Protocollo getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(Protocollo protocollo) {
		this.protocollo = protocollo;
	}

	@Override
	public void cambiaLinguaListener(ActionEvent event) {
		super.cambiaLinguaListener(event);
	}


	@Override
	public String getCurrentLang() {
		String lingua = super.getCurrentLang();

		LoginMBean.log.debug("Lingua corrente ["+lingua+"]");

		return lingua;
	}

	@Override
	public List<Locale> caricaListaLingueSupportate() {
		List<Locale> lst = new ArrayList<Locale>();

		lst.add(Locale.ITALIAN);
		//		lst.add(Locale.ENGLISH);
		lst.add(Locale.GERMAN);

		this.setLingueSupportate(lst);

		return this.getLingueSupportate();

	}

	public boolean isAdmin(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN);
	}
	
	public boolean isDeptAdmin(){
		return this.loggedUtente.getRole().equals(UserRole.DEPT_ADMIN);
	}

	public boolean isInterno(){
		UserType tipo = this.loggedUtente.getTipo();

		if(tipo == null)
			return true;

		return tipo.equals(UserType.INTERNO); 
	}

	public boolean isEsterno(){return !isInterno();}

	public String getUrlChangePassword(){
		if(isEsterno()){
			try{
				String url = ConsoleProperties.getInstance(LoginMBean.log).getUrlModificaPassword();

				if(StringUtils.isNotEmpty(url) && ConsoleProperties.getInstance(LoginMBean.log).isVisualizzaUrlModificaPassword())
					return url;
			}catch(Exception e){ LoginMBean.log.error("Errore durante la lettura della url di modifica password: "+e.getMessage(),e);}

			return null;
		}

		return null;
	}

	private Evento getEventoLogin(){
		Evento evento = new Evento();

		evento.setTipo("ACCESSO_UTENTE"); 
		evento.setCodice("LOGIN");
		evento.setDescrizione(this.getLoggedUtente().getUsername()); 
		evento.setOraRegistrazione(new Date()); 
		evento.setSeverita(3);

		return evento;
	}

	private Evento getEventoLogout(){
		Evento evento = new Evento();

		evento.setTipo("ACCESSO_UTENTE");
		evento.setCodice("LOGOUT");
		evento.setDescrizione(this.getLoggedUtente().getUsername());
		evento.setOraRegistrazione(new Date());
		evento.setSeverita(3);

		return evento;

	}
}
