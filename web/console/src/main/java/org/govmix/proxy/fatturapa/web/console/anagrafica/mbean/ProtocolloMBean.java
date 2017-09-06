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
package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.ProtocolloBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.ProtocolloForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.ProtocolloSearchForm;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseListView;
import org.openspcoop2.generic_project.web.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.mbean.exception.ModificaException;
import org.openspcoop2.generic_project.web.mbean.exception.RestoreSearchException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public class ProtocolloMBean extends BaseListView<ProtocolloBean, Long, ProtocolloSearchForm,ProtocolloForm,Protocollo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showForm = false;

	private String azione = null;

	private RegistroMBean registroMBean = null;

	public ProtocolloMBean () throws Exception{
		super(LoggerManager.getConsoleLogger());
		this.log.debug("Ente MBean");
	}
	
	
	@Override
	public void init() throws Exception {
		this.form = new ProtocolloForm();
		this.form.setRendered(false);
		this.form.reset();
		this.showForm = false;
		this.azione = null;
	}
	
	@Override
	public void initNavigationManager() throws Exception {
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome("listaProtocolli?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("protocollo");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome("listaProtocolli?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("protocollo");
	}

	@Override
	public void setService(IBaseService<ProtocolloBean, Long, ProtocolloSearchForm> service) {
		super.setService(service);
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(ProtocolloSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.search.getNome().setValue(Utils.getProtocollo().getNome()); 
	}

	@Override
	public void setSelectedElement(ProtocolloBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();
		this.showForm = false;
		this.azione = null;
	}

	
	@Override
	public String azioneMenuAction() throws MenuActionException {
		try{
			this.search.reset();
			// Protocollo fisso dalle properties
			Protocollo protocollo =  Utils.getProtocollo();
			ProtocolloBean bean = new ProtocolloBean();
			bean.setDTO(protocollo);

			this.setSelectedElement(bean);

		}catch(Exception e){
			throw new MenuActionException(e);
		}
		return super.azioneMenuAction();
	}
	
	@Override
	public String azioneRestoreSearch() throws RestoreSearchException {
		try{
			this.search.setRestoreSearch(true); 

			// Protocollo fisso dalle properties
			Protocollo protocollo =  Utils.getProtocollo();
			ProtocolloBean bean = new ProtocolloBean();
			bean.setDTO(protocollo);

			this.setSelectedElement(bean);
		}catch(Exception e){
			throw new RestoreSearchException(e);
		}
		return this.getNavigationManager().getRestoreSearchOutcome();
	}

	@Override
	public String azioneModifica() throws ModificaException {
		try{
			this.showForm = true;
			this.azione = "update";
			this.form.setRendered(this.showForm);
			this.form.setObject(this.selectedElement);
			this.form.reset();
		}catch(Exception e){
			throw new ModificaException(e);
		}
		return super.azioneModifica();
	}

	@Override
	public String azioneInvia() throws InviaException {
		String msg = this.form.valida();

		if(msg!= null){
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
			//			return null;
		}

		try{
			Protocollo protocollo = (Protocollo) this.form.getObject();
			long oldId = this.selectedElement.getDTO().getId();

			protocollo.setId(oldId);

			ProtocolloBean bean = new ProtocolloBean();
			bean.setDTO(protocollo);

			this.service.store(bean);
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.salvataggioOk"));

			//aggiorno protocollo
			Utils.getLoginBean().updateProtocollo(); 
			
			bean.setDTO(Utils.getProtocollo());

			this.setSelectedElement(bean);
			return this.getNavigationManager().getInviaOutcome();//"invia";
		}catch(InviaException e){
			throw e;
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio dell'ente: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
		}
	}

	@Override
	public String azioneAnnulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome("listaProtocolli");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("annulla");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return super.azioneAnnulla();
	}

	public String annullaRegistro(){
		String action = this.registroMBean.annulla();

		if(action.equals("protocollo")){
			return this.restoreSearch();
		}

		return action;
	}

	@Override
	public void addNewListener(ActionEvent ae) {
//		super.addNewListener(ae);
//		this.selectedElement = null;
//		this.showForm = true;
//		this.azione = "new";
//		this.form.setValues(null);
//		this.form.setRendered(this.showForm); 
//		this.form.reset();
	}

	public boolean isShowForm() {
		return this.showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public String getAzione() {
		return this.azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

	public RegistroMBean getRegistroMBean() {
		return this.registroMBean;
	}

	public void setRegistroMBean(RegistroMBean registroMBean) {
		this.registroMBean = registroMBean;
		
//		this.registroMBean = new RegistroMBean();
		this.registroMBean.setProtocolloMBean(this);
	}

}
