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

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel.EnteDM;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IEnteService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.DataModelListView;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.ModificaException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public class EnteMBean extends DataModelListView<EnteBean, Long, EnteSearchForm,EnteForm,EnteDM,Ente,IEnteService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showForm = false;

	private String azione = null;

	public EnteMBean () throws Exception{
		super(LoggerManager.getConsoleLogger());
		this.log.debug("Ente MBean");
		this.form = new EnteForm();
		this.form.setRendered(false);
		this.form.setMbean(this); 
		this.form.reset();

		this.showForm = false;
		this.azione = null;

		this.initTables();
		this.setOutcomes();
	}

	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("entiListView"); 
			this.table.setEnableDelete(this.isAbilitaCreazioneDipartimenti());
			this.table.setShowAddButton(this.isAbilitaCreazioneDipartimenti());
			this.table.setShowDetailColumn(false);
			this.table.setShowSelectAll(this.isAbilitaCreazioneDipartimenti());
			this.table.setHeaderText("ente.tabellaRisultati.label");
			this.table.setDetailLinkText("ente.dettaglioTitle"); 
			this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("ente");
		this.getNavigationManager().setFiltraOutcome("listaEnti?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaEnti");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome("ente");
		this.getNavigationManager().setResetOutcome("listaEnti?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaEnti");
	}

	@Override
	public void setService(IBaseService<EnteBean, Long, EnteSearchForm> service) {
		super.setService(service);
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(EnteSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedId = null;
		this.search.setmBean(this);
	}
	
	@Override
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;

		if(this.selectedId != null){

			try {
				//carico dettaglio utente
				EnteBean dettaglioEnte = this.service.findById(this.selectedId);
				this.setSelectedElement(dettaglioEnte); 
			} catch (ServiceException e) {
				this.log.error("Si e' verificato un errore durante il caricamento del dettaglio ente: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public void setSelectedElement(EnteBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();
		this.showForm = false;
		this.azione = null;
	}

	@Override
	protected String _menuAction() throws MenuActionException {
		this.search.setRestoreSearch(true);
		return super._menuAction();
	}

	@Override
	protected String _modifica() throws ModificaException {
		try{
			this.showForm = true;
			this.azione = "update";
			this.form.setRendered(this.showForm);
			this.form.setValues(this.selectedElement);
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
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
			//			return null;
		}

		try{
			long oldId = -1;
			Ente newEnte = this.form.getEnte();
			//	boolean isAdmin = false;
			// Add
			if(!this.azione.equals("update")){
				EnteBean bean = new EnteBean();
				bean.setDTO(newEnte);
				boolean exists = this.service.exists(bean);
				//				EnteBean oldEnte = ((IEnteService) this.service).findEnteByNome(this.form.getNome().getValue());

				if(exists){
					throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.enteEsistente", this.form.getNome().getValue()));
					//	org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione") +
					// MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione") +
					//							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.dipartimentoEsistente",this.form.getNome().getValue()));
					//					return null;
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
				//isAdmin = this.selectedElement.getDTO().getSuperuser();
			}


			newEnte.setId(oldId);

			EnteBean bean = new EnteBean();
			bean.setDTO(newEnte);

			this.service.store(bean);
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.salvataggioOk"));
			this.setSelectedElement(bean);

			//aggiorno ente
			//			Utils.getLoginBean().setEnte(newEnte); 

			return this.getNavigationManager().getInviaOutcome();//"invia";
		}catch(InviaException e){
			throw e;
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio dell'ente: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
			//			return null;
		}
	}

	@Override
	protected String _annulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome("listaEnti");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("annulla");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return super._annulla();
	}

	@Override
	protected String _delete() throws DeleteException {
		String deleteMsg = null;

		try{
			deleteMsg = super._delete();
		}catch(DeleteException e){
			deleteMsg = e.getMessage();
		}

		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			this.log.debug("["+eliminati+"] Enti eliminati con successo.");
			String deletemsgok = Utils.getInstance().getMessageFromResourceBundle("ente.deleteOk");
			MessageUtils.addInfoMsg(deletemsgok);
		}else {
			this.log.error("Si e' verificato un errore durante l'eliminazione degli enti: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
	} 

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.showForm = true;
		this.azione = "new";
		this.form.setValues(null);
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
	
	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> descrizioneAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<Ente> lstEnti = ((IEnteService)this.service).getDescrizioneAutoComplete((String)val);

				if(lstEnti != null && lstEnti.size() > 0){
					for (Ente ente : lstEnti) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(ente.getDescrizione(), ente.getDescrizione()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}
	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> nomeAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<Ente> lstEnti = ((IEnteService)this.service).getNomeAutoComplete((String)val);

				if(lstEnti != null && lstEnti.size() > 0){
					for (Ente enti : lstEnti) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(enti.getNome(),enti.getNome()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	//	public RegistroMBean getRegistroMBean() {
	//		return this.registroMBean;
	//	}
	//
	//	public void setRegistroMBean(RegistroMBean registroMBean) {
	//		this.registroMBean = registroMBean;
	//	}

	public boolean isAbilitaCreazioneDipartimenti(){
		//[Pintori 2016/06/10] Vincolo rilasciato il DeptAdmin gestisce l'anagrafica di tutti i dipartimenti, 
		// quindi e' abilitato anche alla creazione/cancellazione.
		//		return Utils.getLoginBean().isAdmin() ;

		return Utils.getLoginBean().isAdmin() || Utils.getLoginBean().isDeptAdmin();
	}
}
