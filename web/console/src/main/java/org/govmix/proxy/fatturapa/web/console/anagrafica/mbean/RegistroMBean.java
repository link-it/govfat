/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.RegistroProperty;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.ProtocolloBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseListView;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.ModificaException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public class RegistroMBean extends BaseListView<RegistroBean, Long, RegistroSearchForm,RegistroForm, Registro>{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private boolean showForm = false;

	private String azione = null;

	private ProtocolloMBean protocolloMBean = null;

	private ProtocolloBean selectedProtocollo;

	public RegistroMBean () throws Exception{
		super(LoggerManager.getConsoleLogger());
		this.log.debug("RegistroMBean");

		this.form = new RegistroForm();
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;


		// Protocollo fisso dalle properties
		Protocollo protocollo =  Utils.getProtocollo();
		selectedProtocollo = new ProtocolloBean();
		selectedProtocollo.setDTO(protocollo);


		this.setOutcomes();
	}


	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("registro");
		this.getNavigationManager().setFiltraOutcome("listaProtocolli?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome(null);
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome("registro");
		this.getNavigationManager().setResetOutcome("listaProtocolli?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome(null);
	}

	@Override
	public void setService(IBaseService<RegistroBean, Long, RegistroSearchForm> service) {
		super.setService(service);
	}

	@Override
	public void setSearch(RegistroSearchForm search) {
		super.setSearch(search);
		this.search.getProtocollo().setValue(Utils.getProtocollo().getNome());  
	}

	@Override
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;

		if(this.selectedId != null){

			try {
				//carico dettaglio utente
				RegistroBean dettaglioRegistro = this.service.findById(this.selectedId);
				this.setSelectedElement(dettaglioRegistro); 
			} catch (ServiceException e) {
				this.log.error("Si e' verificato un errore durante il caricamento del dettaglio registro: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public void setSelectedElement(RegistroBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;
	}

	@Override
	protected String _modifica() throws ModificaException {
		try{
			this.showForm = true;
			this.azione = "update";
			this.form.setRendered(this.showForm);
			this.form.setValues(this.selectedElement);
			this.form.setListaNomiProperties(this.getListaRegistroProperties());
			this.form.reset();
		}catch(Exception e){
			throw new ModificaException(e);
		}
		return super._modifica();
	}

	@Override
	protected String _invia() throws InviaException{
		String msg = this.form.valida();

		if(msg!= null){
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione")+": " + msg);
		}

		try{
			long oldId = -1;
			Registro newRegistro = this.form.getRegistro();
			newRegistro.setIdProtocollo(Utils.getIdProtocollo());
			//	boolean isAdmin = false;
			// Add
			if(!this.azione.equals("update")){
				RegistroBean bean = new RegistroBean();
				bean.setDTO(newRegistro);
				boolean exists = this.service.exists(bean);
				//				RegistroBean oldEnte = this.enteService.findRegistroByNome(this.form.getNome().getValue());

				if(exists){
					throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione") +
							": " +org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("registro.form.registroEsistente",this.form.getNome().getValue()));
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
			}

			newRegistro.setId(oldId);


			RegistroBean bean = new RegistroBean();
			bean.setDTO(newRegistro);
			bean.setListaNomiProperties(this.getListaRegistroProperties());


			this.service.store(bean);
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.salvataggioOk"));
			this.setSelectedElement(bean);

			return this.getNavigationManager().getInviaOutcome();//"invia";
		}catch(InviaException e){
			throw e;
		}
		
		catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio del registro: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreGenerico"));
			//			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreGenerico"));
			//			return null;
		}
	}

	@Override
	protected String _annulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome("protocollo");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("registro");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		if(this.getNavigationManager().getAnnullaOutcome().equals("protocollo"))
			return this.getProtocolloMBean().restoreSearch();
		else 
			return super._annulla();
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
		this.showForm = true;
		this.azione = "new";
		this.form.setValues(null);
		this.form.setListaNomiProperties(this.getListaRegistroProperties());
		this.form.setRendered(this.showForm); 
		this.form.reset();
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

	public List<RegistroProperty> getListaRegistroProperties(){
		List<RegistroProperty> lista = new ArrayList<RegistroProperty>();

		try{
			lista = ((IRegistroService)  this.service).getListaRegistroPropertiesProtocollo(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdProtocollo());
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il caricamento delle properties: " + e.getMessage(), e);
		}


		return lista;
	}

	@Override
	public String delete(){
		this.toRemove = new ArrayList<RegistroBean>();
		Iterator<RegistroBean> it = this.selectedIds.keySet().iterator();
		while (it.hasNext()) {
			RegistroBean elem = it.next();
			if(this.selectedIds.get(elem).booleanValue()){
				this.toRemove.add(elem);
				it.remove();
			}
		}

		String deleteMsg = null;
		for (RegistroBean elem : this.toRemove) {
			try{
				this.service.delete(elem);
			}catch (Exception e) {
				deleteMsg = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromCommonsResourceBundle("DELETE_ERROR") + ": " + e.getMessage();
			}
		}

		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			this.log.debug("["+eliminati+"] Registri eliminati con successo.");
			MessageUtils.addInfoMsg(
					org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("registro.deleteOk"));
		}else {
			this.log.error("Si e' verificato un errore durante l'eliminazione dei registri: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
	}


	public ProtocolloMBean getProtocolloMBean() {
		return protocolloMBean;
	}


	public void setProtocolloMBean(ProtocolloMBean protocolloMBean) {
		this.protocolloMBean = protocolloMBean;
	}

	public ProtocolloBean getSelectedProtocollo() {
		return selectedProtocollo;
	}


	public void setSelectedProtocollo(ProtocolloBean selectedProtocollo) {
		this.selectedProtocollo = selectedProtocollo;
	}
}
