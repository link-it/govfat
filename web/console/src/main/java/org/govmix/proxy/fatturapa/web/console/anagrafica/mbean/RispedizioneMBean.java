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
import javax.faces.model.SelectItem;

import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.constants.TipoErroreType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RispedizioneBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel.RispedizioneDM;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RispedizioneForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RispedizioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRispedizioneService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.govmix.proxy.fatturapa.web.console.util.input.RadioButtonImpl;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.DataModelListView;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.ModificaException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;

public class RispedizioneMBean extends DataModelListView<RispedizioneBean, Long, RispedizioneSearchForm,RispedizioneForm,RispedizioneDM,PccRispedizione,IRispedizioneService>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private boolean showForm = false;

	private String azione = null;

	public RispedizioneMBean() throws Exception{
		super(LoggerManager.getConsoleLogger());
		this.showForm = false;
		this.log.debug("Dipartimento MBean");
		this.form = new RispedizioneForm();
		this.form.setRendered(this.showForm);
		this.form.reset();

		((RadioButtonImpl)this.form.getRispedizioneAutomatica()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((SelectListImpl)this.form.getTipoErrore()).setElencoSelectItems(getOpzioniTipoErrore());



		this.azione = null;
		this.initTables();
		this.setOutcomes();

	}

	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("rispedizioniListView"); 
			this.table.setEnableDelete(true);
			this.table.setShowAddButton(true);
			this.table.setShowDetailColumn(false);
			this.table.setShowSelectAll(true);
			this.table.setHeaderText("rispedizione.tabellaRisultati.label");
			this.table.setDetailLinkText("rispedizione.dettaglioTitle"); 
			this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("rispedizionePCC");
		this.getNavigationManager().setFiltraOutcome("listaRispedizioniPCC?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaRispedizioniPCC");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome("rispedizionePCC");
		this.getNavigationManager().setResetOutcome("listaRispedizioniPCC?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaRispedizioniPCC");
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(RispedizioneSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedId = null;
	}

	private List<SelectItem> getOpzioniRadioButtonAbilita() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem siOption = Utils.getSiOption("commons.label.abilitata");
		SelectItem elem0 = new SelectItem(siOption,siOption.getLabel());
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem noOption = Utils.getNoOption("commons.label.nonAbilitata");
		SelectItem elem1 = new SelectItem(noOption,noOption.getLabel()); 


		lista.add(elem0);
		lista.add(elem1);

		return lista;
	}

	private List<SelectItem> getOpzioniTipoErrore() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		SelectItem elem0 = new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));

		lista.add(elem0);


		lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoErroreType.ELABORAZIONE.getValue(), "rispedizione.tipoErrore.elaborazione")));
		lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoErroreType.TRASMISSIONE.getValue(), "rispedizione.tipoErrore.trasmissione")));

		return lista;
	}

	@Override
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;

		if(this.selectedId != null){

			try {
				//carico dettaglio utente
				RispedizioneBean dettaglioRispedizione = this.service.findById(this.selectedId);

				this.setSelectedElement(dettaglioRispedizione); 
			} catch (ServiceException e) {
				this.log.error("Si e' verificato un errore durante il caricamento del dettaglio dipartimento: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public void setSelectedElement(RispedizioneBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.showForm = false;
		this.form.setRendered(this.showForm);
		this.form.reset();
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
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.erroreValidazione")+": " + msg);
		}

		try{
			long oldId = -1;
			PccRispedizione newRisp = this.form.getRispedizione();
			// Add
			if(!this.azione.equals("update")){
				RispedizioneBean bean = new RispedizioneBean();
				bean.setDTO(newRisp);
				boolean exists = this.service.exists(bean);

				if(exists){
					throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.erroreValidazione") +
							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("rispedizione.form.rispedizioneEsistente",this.form.getCodiceErrore().getValue()));
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
			}

			newRisp.setId(oldId);


			RispedizioneBean bean = new RispedizioneBean();
			bean.setDTO(newRisp);

			this.service.store(bean);
			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.salvataggioOk"));

			Long id = bean.getDTO().getId();

			this.setSelectedId(id);

			return this.getNavigationManager().getInviaOutcome(); //"invia";
		}catch(InviaException e){
			throw e;
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio del rispedizione: " + e.getMessage(), e);
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.erroreGenerico"));
		}
	}

	@Override
	protected String _annulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome( "listaRispedizioniPCC");

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
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
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

	@Override
	public String delete() {

		String deleteMsg = null;

		try{
			deleteMsg = super._delete();
		}catch(DeleteException e){
			deleteMsg = e.getMessage();
		}

		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			this.log.debug("["+eliminati+"] Rispedizioni eliminate con successo.");
			String deletemsgok = Utils.getInstance().getMessageFromResourceBundle("rispedizione.deleteOk");
			MessageUtils.addInfoMsg(deletemsgok);
		}else {
			this.log.error("Si e' verificato un errore durante l'eliminazione della rispedizione: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
	}

	public String toggleRispedizioneAutomatica (){
		if(this.selectedElement != null){
			this.form.setValues(this.selectedElement);

			String msg = this.form.valida();

			if(msg!= null){
				MessageUtils.addErrorMsg
				(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.erroreValidazione")+": " + msg);
				return null;
			}

			try{
				PccRispedizione newRisp = this.form.getRispedizione();
				boolean nuovoValoreAbilitato = !newRisp.isAbilitato();
				newRisp.setAbilitato(nuovoValoreAbilitato);
				String v = nuovoValoreAbilitato ? "abilitata" : "disabilitata";

				RispedizioneBean bean = new RispedizioneBean();
				bean.setDTO(newRisp);

				this.service.store(bean);
				MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.rispedizioneAutomatica."+(v)+".Ok"));
			}catch(Exception e){
				this.log.error("Si e' verificato un errore durante il salvataggio del rispedizione: " + e.getMessage(), e);
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.rispedizioneAutomatica.erroreGenerico"));
				return null;
			}
		} else {
			MessageUtils.addErrorMsg("Elemento non selezionato");
		}

		return null;
	}


}
