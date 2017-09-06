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
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.mbean.exception.ModificaException;

public class RispedizioneMBean extends DataModelListView<PccRispedizione, Long, RispedizioneBean,  RispedizioneSearchForm,RispedizioneForm,RispedizioneDM,IRispedizioneService>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private boolean showForm = false;

	private String azione = null;

	public RispedizioneMBean() throws Exception{
		super(LoggerManager.getConsoleLogger());
		this.log.debug("Dipartimento MBean");
	}


	@Override
	public void init() throws Exception {
		try{
			this.showForm = false;
			this.form = new RispedizioneForm();
			this.form.setRendered(this.showForm);
			this.form.reset();

			((RadioButtonImpl)this.form.getRispedizioneAutomatica()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
			((SelectListImpl)this.form.getTipoErrore()).setElencoSelectItems(getOpzioniTipoErrore());

			this.azione = null;

			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("rispedizioniListView"); 
			this.table.setEnableDelete(true);
			this.table.setShowAddButton(true);
			this.table.setShowDetailColumn(false);
			this.table.setShowSelectAll(true);
			this.table.setHeaderText("rispedizione.tabellaRisultati.label");
			this.table.setDetailLinkText("rispedizione.dettaglioTitle"); 
			//			this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	@Override
	public void initNavigationManager() throws Exception {
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

		org.openspcoop2.generic_project.web.input.SelectItem siOption = Utils.getSiOption("commons.label.abilitata");
		SelectItem elem0 = new SelectItem(siOption,siOption.getLabel());
		org.openspcoop2.generic_project.web.input.SelectItem noOption = Utils.getNoOption("commons.label.nonAbilitata");
		SelectItem elem1 = new SelectItem(noOption,noOption.getLabel()); 


		lista.add(elem0);
		lista.add(elem1);

		return lista;
	}

	private List<SelectItem> getOpzioniTipoErrore() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		SelectItem elem0 = new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));

		lista.add(elem0);


		lista.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoErroreType.ELABORAZIONE.getValue(), "rispedizione.tipoErrore.elaborazione")));
		lista.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoErroreType.TRASMISSIONE.getValue(), "rispedizione.tipoErrore.trasmissione")));

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
	public String azioneMenuAction() throws MenuActionException {
		this.search.setRestoreSearch(true);
		return super.azioneMenuAction();
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
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.erroreValidazione")+": " + msg);
		}

		try{
			long oldId = -1;
			PccRispedizione newRisp = (PccRispedizione) this.form.getObject();
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
	public String azioneAnnulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome( "listaRispedizioniPCC");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("annulla");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return super.azioneAnnulla();
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
		this.showForm = true;
		this.azione = "new";
		try {
			this.form.setObject(null);
		} catch (Exception e) {
			this.log.error("Si e' verificato un errore durante l'inizializzazione del form di creazione rispedizione: " + e.getMessage(), e);
		}
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
			deleteMsg = super.azioneDelete();
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
			try{
				this.form.setObject(this.selectedElement);

				String msg = this.form.valida();

				if(msg!= null){
					MessageUtils.addErrorMsg
					(Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.erroreValidazione")+": " + msg);
					return null;
				}

				PccRispedizione newRisp = (PccRispedizione) this.form.getObject();
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
