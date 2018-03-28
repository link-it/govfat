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
package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseListView;

public class EnteMBean extends BaseListView<EnteBean, Long, EnteSearchForm,EnteForm,Ente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//
//	private boolean showForm = false;
//
//	private String azione = null;
//
//	private RegistroMBean registroMBean = null;
//
//	public EnteMBean () throws Exception{
//		super(LoggerManager.getConsoleLogger());
//		this.log.debug("Ente MBean");
//
//		this.registroMBean = new RegistroMBean();
//		IRegistroService registroService = new RegistroService();
//		RegistroSearchForm registroSearchForm = new RegistroSearchForm();
//		registroSearchForm.getEnte().setValue(Utils.getEnte().getNome()); 
//		registroService.setForm(registroSearchForm); 
//		this.registroMBean.setService(registroService);
//		this.registroMBean.setEnteMBean(this);
//
//		this.form = new EnteForm();
//		this.form.setRendered(false);
//		this.form.reset();
//
//		this.showForm = false;
//		this.azione = null;
//
//		this.setOutcomes();
//	}
//	
//	private void setOutcomes(){
//		this.getNavigationManager().setAnnullaOutcome(null);
//		this.getNavigationManager().setDeleteOutcome(null);
//		this.getNavigationManager().setDettaglioOutcome(null);
//		this.getNavigationManager().setFiltraOutcome("listaEnti?faces-redirect=true");
//		this.getNavigationManager().setInviaOutcome(null);
//		this.getNavigationManager().setMenuActionOutcome("ente");
//		this.getNavigationManager().setModificaOutcome("modifica");
//		this.getNavigationManager().setNuovoOutcome(null);
//		this.getNavigationManager().setResetOutcome("listaEnti?faces-redirect=true");
//		this.getNavigationManager().setRestoreSearchOutcome("ente");
//	}
//	
//	@Override
//	public void setService(IBaseService<EnteBean, Long, EnteSearchForm> service) {
//		super.setService(service);
//	}
//
//	// Override del set della ricerca, popolo i field di tipo selectList.
//	@Override
//	public void setSearch(EnteSearchForm search) {
//		this.search = search;
//		this.selectedElement = null;
//		this.search.getNome().setValue(Utils.getEnte().getNome()); 
//	}
//
//	@Override
//	public void setSelectedElement(EnteBean selectedElement) {
//		super.setSelectedElement(selectedElement);
//		this.form.setRendered(false);
//		this.form.reset();
//		this.showForm = false;
//		this.azione = null;
//	}
//
//	@Override
//	protected String _menuAction() throws MenuActionException {
//		try{
//			this.search.reset();
//			// In questa versione non c'e' la lista degli enti
//			Ente ente = Utils.getEnte();
//			EnteBean bean = new EnteBean();
//			bean.setDTO(ente);
//
//			this.setSelectedElement(bean);
//
//		}catch(Exception e){
//			throw new MenuActionException(e);
//		}
//		return super._menuAction();
//	}
//
//	@Override
//	protected String _restoreSearch() throws RestoreSearchException {
//		try{
//			this.search.setRestoreSearch(true); 
//
//			// In questa versione non c'e' la lista degli enti
//			Ente ente = Utils.getEnte();
//			EnteBean bean = new EnteBean();
//			bean.setDTO(ente);
//
//			this.setSelectedElement(bean);
//		}catch(Exception e){
//			throw new RestoreSearchException(e);
//		}
//		return this.getNavigationManager().getRestoreSearchOutcome();
//	}
//
//	@Override
//	protected String _modifica() throws ModificaException {
//		try{
//			this.showForm = true;
//			this.azione = "update";
//			this.form.setRendered(this.showForm);
//			this.form.setValues(this.selectedElement);
//			this.form.reset();
//		}catch(Exception e){
//			throw new ModificaException(e);
//		}
//		return super._modifica();
//	}
//
//	@Override
//	protected String _invia() throws InviaException{
//		String msg = this.form.valida();
//
//		if(msg!= null){
//			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
//			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
//			//			return null;
//		}
//
//		try{
//			long oldId = -1;
//			Ente newEnte = this.form.getEnte();
//			//	boolean isAdmin = false;
//			// Add
//			if(!this.azione.equals("update")){
//				EnteBean bean = new EnteBean();
//				bean.setDTO(newEnte);
//				boolean exists = this.service.exists(bean);
//				//				EnteBean oldEnte = ((IEnteService) this.service).findEnteByNome(this.form.getNome().getValue());
//
//				if(exists){
//					throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione") +
//							": " +org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.dipartimentoEsistente",this.form.getNome().getValue()));
//					//					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione") +
//					//							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.dipartimentoEsistente",this.form.getNome().getValue()));
//					//					return null;
//				}
//			} else {
//				oldId = this.selectedElement.getDTO().getId();
//				//isAdmin = this.selectedElement.getDTO().getSuperuser();
//			}
//
//
//			newEnte.setId(oldId);
//
//			EnteBean bean = new EnteBean();
//			bean.setDTO(newEnte);
//
//			this.service.store(bean);
//			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.salvataggioOk"));
//			this.setSelectedElement(bean);
//
//			//aggiorno ente
//			Utils.getLoginBean().setEnte(newEnte); 
//
//			return this.getNavigationManager().getInviaOutcome();//"invia";
//		}catch(Exception e){
//			this.log.error("Si e' verificato un errore durante il salvataggio dell'ente: " + e.getMessage(), e);
//			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
//			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
//			//			return null;
//		}
//	}
//
//	@Override
//	protected String _annulla() throws AnnullaException {
//		this.getNavigationManager().setAnnullaOutcome("listaEnti");
//
//		if(this.azione.equals("update")){
//			this.showForm = false;
//			this.azione = null;
//			this.getNavigationManager().setAnnullaOutcome("annulla");
//		}
//		this.form.setRendered(this.showForm); 
//		this.form.reset();
//
//		return super._annulla();
//	}
//
//	public String annullaRegistro(){
//		String action = this.registroMBean.annulla();
//
//		if(action.equals("ente")){
//			return this.restoreSearch();
//		}
//
//		return action;
//	}
//
//	@Override
//	public void addNewListener(ActionEvent ae) {
//		super.addNewListener(ae);
//		this.selectedElement = null;
//		this.showForm = true;
//		this.azione = "new";
//		this.form.setValues(null);
//		this.form.setRendered(this.showForm); 
//		this.form.reset();
//	}
//
//	public boolean isShowForm() {
//		return this.showForm;
//	}
//
//	public void setShowForm(boolean showForm) {
//		this.showForm = showForm;
//	}
//
//	public String getAzione() {
//		return this.azione;
//	}
//
//	public void setAzione(String azione) {
//		this.azione = azione;
//	}
//
//	public RegistroMBean getRegistroMBean() {
//		return this.registroMBean;
//	}
//
//	public void setRegistroMBean(RegistroMBean registroMBean) {
//		this.registroMBean = registroMBean;
//	}

}
