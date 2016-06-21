/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.mbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.dao.DBLoginDao;
import org.govmix.proxy.fatturapa.web.console.dao.ILoginDao;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.LoginBean;

/**
 * LoginMBean bean di sessione per la gestione della sessione dell'utente.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class LoginMBean extends LoginBean{

	private static Logger log = LoggerManager.getConsoleLogger();

	private Utente loggedUtente = null;

	private List<Dipartimento> listDipartimenti;

	private Ente ente = null;

	
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
		
		String fieldsToUpdate = this.getLanguageForm().getIdForm() + "_formPnl,headerct,headerct_parent,footerct,mainct,menuct";
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
				if(this.getLoginDao().login(this.getUsername(),this.getPassword())){
					//			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
					//			HttpSession session = (HttpSession) ec.getSession(true);
					//			session.setAttribute("logged", true);
					this.setIsLoggedIn(true);
					this.loggedUtente = ((ILoginDao)this.getLoginDao()).getLoggedUtente(this.getUsername(),this.getPassword()); 
					this.ente = ((ILoginDao)this.getLoginDao()).getEnte();
					this.listDipartimenti = ((ILoginDao)this.getLoginDao()).getListaDipartimentiUtente(this.loggedUtente,this.ente);

					return "loginSuccess";
				}else{
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									Utils.getInstance().getMessageFromResourceBundle("login.form.credenzialiError"),null));
				}
			}catch(ServiceException e){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Utils.getInstance().getMessageFromResourceBundle("login.form.genericError"),null));
			}
		}else{
			try{
			this.loggedUtente = ((ILoginDao)this.getLoginDao()).getLoggedUtente(this.getUsername()); 
			if(this.loggedUtente != null){
				this.setIsLoggedIn(true);
				this.ente = ((ILoginDao)this.getLoginDao()).getEnte();
				this.listDipartimenti = ((ILoginDao)this.getLoginDao()).getListaDipartimentiUtente(this.loggedUtente,this.ente);

				return "loginSuccess";
			}
			}catch(Exception e){
				log.error("Si e' verificato un errore durante il login: "+ e.getMessage(), e);
				return "loginError";
			}
		}
		return "login";  
	}

	public String logout(){
		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getExternalContext().getSessionMap().put("loginBean", null);
			HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);
			session.setAttribute("loginBean", null); 
			session.invalidate();
		}catch(Exception e){}

		if(!this.isNoPasswordLogin())
			return "login";
		else 
			return "logoutAS";
	}

	public Utente getLoggedUtente() {
		return loggedUtente;
	}

	public void setLoggedUtente(Utente loggedUtente) {
		this.loggedUtente = loggedUtente;
	}

	public List<Dipartimento> getListDipartimenti() {
		
		if(this.listDipartimenti == null || this.listDipartimenti.isEmpty() )
			this.listDipartimenti = ((ILoginDao)this.getLoginDao()).getListaDipartimentiUtente(this.loggedUtente,this.ente);


		return listDipartimenti;
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
	
	public boolean isShowMenuItemEnti(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN);
	}

	public boolean isShowMenuItemDipartimenti(){
		return this.loggedUtente.getRole().equals(UserRole.ADMIN) || this.loggedUtente.getRole().equals(UserRole.DEPT_ADMIN);
	}

	public Ente getEnte() {
		return ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

	@Override
	public void cambiaLinguaListener(ActionEvent event) {
		super.cambiaLinguaListener(event);
	}

	
	@Override
	public String getCurrentLang() {
		String lingua = super.getCurrentLang();
		
		log.debug("Lingua corrente ["+lingua+"]");
		
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
}
