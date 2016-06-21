/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.constants.UserType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.UtenteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel.UtenteDM;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.UtenteForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.UtenteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IEnteService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IUtenteService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.DipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.EnteService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.govmix.proxy.fatturapa.web.console.util.input.RadioButtonImpl;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.PickListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.DataModelListView;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.ModificaException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.utils.crypt.Password;

public class UtenteMBean extends DataModelListView<UtenteBean, Long, UtenteSearchForm,UtenteForm,UtenteDM,Utente,IUtenteService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IDipartimentoService dipartimentoService = null;

	private List<SelectItem> listaDipartimentiEnte = null;
	
	private IEnteService enteService = null;

	private List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaDipartimentiEnte2 = null;

	private boolean showForm = false;

	private String azione = null;
	
	private DipartimentoSearchForm dipartimentoSearch = null;
	
	private int numeroDipartimenti = 0;


	public UtenteMBean () throws Exception{
		super(  LoggerManager.getConsoleLogger());
		this.log.debug("Utente MBean");

		this.form = new UtenteForm();
		this.form.setmBean(this); 
		this.form.setRendered(false);
		this.form.reset();

		((RadioButtonImpl)this.form.getPagamentoIVA()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getMovimentiErarioIVA()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getDownloadDocumento()).setElencoSelectItems(getOpzioniRadioButtonAbilita());

		((RadioButtonImpl)this.form.getDatiFattura()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getInserimentoFattura()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getStatoFattura()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getConsultazioneTracce()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getPagamento()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getStornoPagamento()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getComunicazioneScadenza()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getCancellazioneScadenze()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getContabilizzazione()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getStornoContabilizzazione()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getRicezioneFattura()).setElencoSelectItems(getOpzioniRadioButtonAbilita());
		((RadioButtonImpl)this.form.getRifiutoFattura()).setElencoSelectItems(getOpzioniRadioButtonAbilita());

		this.showForm = false;
		this.azione = null;

		this.initTables();
		this.setOutcomes();

		this.enteService = new EnteService();
		this.dipartimentoService = new DipartimentoService();
	}

	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("utentiListView"); 
			this.table.setEnableDelete(true);
			this.table.setShowAddButton(true);
			this.table.setShowDetailColumn(false);
			this.table.setShowSelectAll(true);
			this.table.setHeaderText("utente.tabellaRisultati.label");
			this.table.setDetailLinkText("utente.dettaglioTitle"); 
			this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("utente");
		this.getNavigationManager().setFiltraOutcome("listaUtenti?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaUtenti");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome("utente");
		this.getNavigationManager().setResetOutcome("listaUtenti?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaUtenti");
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(UtenteSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedId = null;
		this.numeroDipartimenti = 0;

		((SelectListImpl)this.search.getEnte()).setElencoSelectItems(this.getListaEnti()); 
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem enteSI = this.search.getEnte().getValue();
		String nomeEnte = null;
		if(enteSI!= null){
			nomeEnte = enteSI.getValue();
		}
		((SelectListImpl)this.search.getDipartimento()).setElencoSelectItems(this.getListaDipartimentiEnte(nomeEnte));
		this.search.setmBean(this);
	}

	@Override
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;
		
		if(this.selectedId != null){
			try {
				//carico dettaglio utente
				UtenteBean dettaglioUtente = this.service.findById(this.selectedId);
				
				this.setSelectedElement(dettaglioUtente); 
			} catch (ServiceException e) {
				this.log.error("Si e' verificato un errore durante il caricamento del dettaglio utente: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public void setSelectedElement(UtenteBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();
		this.numeroDipartimenti = 0;
		
		if(this.selectedElement != null){
//			try {
				this.numeroDipartimenti = this.selectedElement.getDTO().getUtenteDipartimentoList().size();
				this.dipartimentoSearch.setListaUtenteDipartimenti(this.selectedElement.getDTO().getUtenteDipartimentoList());
//				List<DipartimentoBean> listaDipartimentiUtente = this.dipartimentoService.getListaDipartimentiUtente(selectedElement.getDTO() , true);
//				this.selectedElement.setListaDipartimenti(listaDipartimentiUtente); 
//
//			} catch (ServiceException e) {
//				this.log.error("Si e' verificato un errore durante la lettura dei dipartimenti utente: " + e.getMessage(), e);
//			}
		}
		this.showForm = false;
		this.azione = null;
	}

	// Metodo Menu Action

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

			List<DipartimentoBean> listaDipartimentiUtente =
					this.dipartimentoService.getListaDipartimentiUtente(this.selectedElement.getDTO() , true);
			this.selectedElement.setListaDipartimenti(listaDipartimentiUtente); 
			
			this.form.setListaProprietaPCC((((IUtenteService)this.service).getListaOperazioni()));

			this.form.setValues(this.selectedElement);
			((SelectListImpl)this.form.getRuolo()).setElencoSelectItems(this.getListaRuoli());
			((SelectListImpl)this.form.getProfilo()).setElencoSelectItems(getListaProfili());
			this.listaDipartimentiEnte2 = null;
			this.form.getDipartimento().setOptions(this.getListaDipartimentiEnte2(null));


			List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> itemCorrenti = this.form.getDipartimento().getDefaultValue();

			List<Integer> listaId = new ArrayList<Integer>();
			for (int i = 0 ; i < this.form.getDipartimento().getOptions().size() ; i ++) {
				org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = this.form.getDipartimento().getOptions().get(i);
				for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem oldItem : itemCorrenti) {
					if(item.getValue().equals(oldItem.getValue())){
						listaId.add(0,i);
					}
				}
			}

			for (Integer idToRemove : listaId) {
				this.form.getDipartimento().getOptions().remove(idToRemove.intValue());
			}

			this.form.reset();
			((PickListImpl)this.form.getDipartimento()).checkDimensions(); 
		}catch(Exception e){
			throw new ModificaException(e);
		}
		return super._modifica();
	}

	@Override
	protected String _invia() throws InviaException{
		String msg = this.form.valida();

		if(msg!= null){
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.erroreValidazione")+": " + msg);
		}

		try{
			long oldId = -1;
			Utente newUtente = this.form.getUtente();
			// Add
			if(!this.azione.equals("update")){
				UtenteBean bean = new UtenteBean();
				bean.setDTO(newUtente);
				boolean exists = this.service.exists(bean);
				if(exists){
					throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.erroreValidazione") +
							": " +org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("utente.form.utenteEsistente",this.form.getUsername().getValue()));
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
			}


			newUtente.setId(oldId);
			Password password = new Password();
			String passwordInserita = this.form.getPassword().getValue();
			// Cifro la pwd solo se e' un nuovo utente o se l'ho modificata
			if(!Utils.getLoginBean().isNoPasswordLogin()){

				String cryptPw = password.cryptPw(passwordInserita);

				// nuovo
				if(oldId == -1){
					newUtente.setPassword(cryptPw);
				}
				else {
					// Se ho aggiornato la password
					if(passwordInserita.equals(this.form.getPassword().getDefaultValue())) {
						newUtente.setPassword(this.form.getPassword().getDefaultValue());
					}else {
						newUtente.setPassword(cryptPw);
					}
				}
			} else {
				newUtente.setPassword(passwordInserita);
			}

			UtenteBean bean = new UtenteBean();
			bean.setDTO(newUtente);

			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(newUtente.getUsername());
			
			List<PccUtenteOperazione> listaOperazioni = this.form.getOperazioniAbilitate();
			
			((IUtenteService)this.service).salvaUtente(bean, idUtente, listaOperazioni);
			
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.salvataggioOk"));
			
			Long id = bean.getDTO().getId();
			
			this.setSelectedId(id);

			// reset lista dei dipartimenti dell'utente
			org.govmix.proxy.fatturapa.web.console.util.Utils.getLoginBean().setListDipartimenti(null);

			return this.getNavigationManager().getInviaOutcome();//"invia";
		}
		catch(InviaException e){
			throw e;
		}
		catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio dell'utente: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.erroreGenerico"));
		}
	}

	@Override
	protected String _annulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome( "listaUtenti");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("annulla");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return super._annulla();
	}


	public List<SelectItem> getListaDipartimentiEnte(String nomeEnte) {
		this.listaDipartimentiEnte = new ArrayList<SelectItem>();

		this.log.debug("Caricamento Lista Dipartimenti in corso...");
		try{

			List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaDipartimentiEnte2 = this.getListaDipartimentiEnte2(nomeEnte);

			if(listaDipartimentiEnte2 != null && listaDipartimentiEnte2.size() > 0){
				for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item : listaDipartimentiEnte2) {
					this.listaDipartimentiEnte.add(new SelectItem(item));
				}
			}
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il caricamento della lista dipartimenti: "+ e.getMessage(), e);
		}

		this.listaDipartimentiEnte.add(0,new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,
						("commons.label.qualsiasi"))));

		this.log.debug("Caricamento Lista Dipartimenti completata.");

		return this.listaDipartimentiEnte;
	}

	public void setListaDipartimentiEnte2(List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaDipartimentiEnte) {
		this.listaDipartimentiEnte2 = listaDipartimentiEnte;
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> getListaDipartimentiEnte2(String nomeEnte) {
		if(this.listaDipartimentiEnte2 == null){

			this.listaDipartimentiEnte2 = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

			this.log.debug("Caricamento Lista Dipartimenti in corso...");
			try{
				DipartimentoSearchForm ricerca = new DipartimentoSearchForm();
				org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem enteSI =  null;
				if(nomeEnte == null)
					enteSI = new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO);
				else 
					enteSI = new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(nomeEnte, nomeEnte); 
				ricerca.getEnte().setValue(enteSI);
				List<DipartimentoBean> lstDip = this.dipartimentoService.findAll(ricerca);

				if(lstDip != null && lstDip.size() > 0){
					for (DipartimentoBean dipartimentoBean : lstDip) {
						Dipartimento d = dipartimentoBean.getDTO();

						String descrizioneConCodice = d.getDescrizione()+ " (" + d.getCodice() + ")";
						//						log.debug("Descr ["+descrizioneConCodice+"] L["+descrizioneConCodice.length()+"]");
						org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
								d.getCodice(),descrizioneConCodice.trim());
						this.listaDipartimentiEnte2.add(item);

					}
				}
			}catch(Exception e){
				this.log.error("Si e' verificato un errore durante il caricamento della lista dipartimenti: "+ e.getMessage(), e);
			}
		}
		this.log.debug("Caricamento Lista Dipartimenti completata.");

		//		this.listaDipartimentiEnte.add(0,new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO)));

		return this.listaDipartimentiEnte2;
	}

	public void setListaDipartimentiEnte(List<SelectItem> listaDipartimentiEnte) {
		this.listaDipartimentiEnte = listaDipartimentiEnte;
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> denominazioneAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<String> lstNomiCognomi = ((IUtenteService)this.service).getDenominazioneAutoComplete((String)val);

				if(lstNomiCognomi != null && lstNomiCognomi.size() > 0){
					for (String string : lstNomiCognomi) {
						//						String label = string.replace(UtenteService.SEPARATORE, " ");

						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(string,string));
					}
				}
			}

		}catch(Exception e ){
			this.log.error("Si e' verificato un errore durante la lettura dei nomi utente: "+ e.getMessage(), e);
		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> descrizioneDipartimentoAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
		//		log.debug("descrizione dipartimento autocomplete["+val+"]");
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				String nomeEnte = null;
				List<Dipartimento> lstDipartimenti = this.getDipartimentiDisponibili((String)val,nomeEnte, this.form.getDipartimento().getValue());

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						String descrizioneConCodice = dipartimento.getDescrizione() + " ("+ dipartimento.getCodice() + ")";
						//						log.debug("Descr ["+descrizioneConCodice+"] L["+descrizioneConCodice.length()+"]");
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(dipartimento.getCodice(),descrizioneConCodice.trim()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	public void selezionaDipartimento(String codiceDipartimento){

		try{
			//1 Aggiungi alla lista di dx

			this.log.debug("Selezionato Dipartimento Codice["+codiceDipartimento+"]");

			DipartimentoBean findDipartimentoByCodice = this.dipartimentoService.findDipartimentoByCodice(codiceDipartimento);

			//			List<Dipartimento> lstDipartimenti =this.getDipartimentiDisponibili(codiceDipartimento, this.form.getDipartimento().getValue());

			//	this.dipartimentoService.getDescrizioneAutoComplete((String)codiceDipartimento);
			Dipartimento d = null;
			if(findDipartimentoByCodice != null){
				d = findDipartimentoByCodice.getDTO(); //lstDipartimenti.get(0);


				String descrizioneConCodice = d.getDescrizione() + " ("+ d.getCodice() + ")";
				//				log.debug("Descr ["+descrizioneConCodice+"] L["+descrizioneConCodice.length()+"]");
				org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem newItem = new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						d.getCodice(),
						descrizioneConCodice.trim());

				this.form.getDipartimento().getValue().add(newItem);

			}

			// Aggiorno lista sx
			String nomeEnte = null;
			this.form.getDipartimento().setOptions(this.getListaDipartimentiEnte2(nomeEnte));
			List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> itemCorrenti = this.form.getDipartimento().getValue();
			List<Integer> listaId = new ArrayList<Integer>();
			for (int i = 0 ; i < this.form.getDipartimento().getOptions().size() ; i ++) {
				org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = this.form.getDipartimento().getOptions().get(i);
				for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem oldItem : itemCorrenti) {
					if(item.getValue().equals(oldItem.getValue())){
						listaId.add(0,i);
					}
				}
			}

			for (Integer idToRemove : listaId) {
				this.form.getDipartimento().getOptions().remove(idToRemove.intValue());
			}

			((PickListImpl)this.form.getDipartimento()).checkDimensions();

		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante la selezione del dipartimento: "+ e.getMessage(), e);
		}
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
		this.numeroDipartimenti = 0;
		this.showForm = true;
		this.azione = "new";
		this.dipartimentoSearch.setListaUtenteDipartimenti(null); 

		try {
			this.form.setListaProprietaPCC((((IUtenteService)this.service).getListaOperazioni()));
		} catch (ServiceException e) {
			this.log.error("Si e' verificato un errore durante il caricamento delle properties pcc: " + e.getMessage(), e);
		}
		this.form.setValues(null);
		this.form.setRendered(this.showForm); 

		((SelectListImpl)this.form.getRuolo()).setElencoSelectItems(this.getListaRuoli());
		((SelectListImpl)this.form.getProfilo()).setElencoSelectItems(getListaProfili());
		
		this.listaDipartimentiEnte2 = null;
		String nomeEnte = null;
		this.form.getDipartimento().setOptions(this.getListaDipartimentiEnte2(nomeEnte));


		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> itemCorrenti = this.form.getDipartimento().getDefaultValue();

		List<Integer> listaId = new ArrayList<Integer>();
		for (int i = 0 ; i < this.form.getDipartimento().getOptions().size() ; i ++) {
			org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = this.form.getDipartimento().getOptions().get(i);
			for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem oldItem : itemCorrenti) {
				if(item.getValue().equals(oldItem.getValue())){
					listaId.add(0,i);
				}
			}
		}

		for (Integer idToRemove : listaId) {
			this.form.getDipartimento().getOptions().remove(idToRemove.intValue());
		}

		this.form.reset();
		((PickListImpl)this.form.getDipartimento()).checkDimensions();
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

	public List<SelectItem> getListaRuoli() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO)));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserRole.ADMIN.getValue(),UserRole.ADMIN.toString())));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserRole.DEPT_ADMIN.getValue(),UserRole.DEPT_ADMIN.toString())));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserRole.USER.getValue(),UserRole.USER.toString())));

		return lista;
	}

	public List<SelectItem> getListaProfili() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO)));

		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserType.INTERNO.getValue(),("utente.form.profilo.interno"))));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserType.ESTERNO.getValue(),("utente.form.profilo.esterno"))));

		return lista;
	}

	private List<Dipartimento> getDipartimentiDisponibili(String val,String nomeEnte, List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaSelezionati){
		List<Dipartimento> lista = new ArrayList<Dipartimento>();

		try{

			this.log.debug("Ricerca Dipartimenti disponibili. Input Utente["+val+"] in corso..."); 

			List<Dipartimento> lstDipartimenti = this.dipartimentoService.getCodiceDescrizioneAutoComplete(val,nomeEnte);

			if(lstDipartimenti != null && lstDipartimenti.size() > 0){
				for (Dipartimento dipartimento : lstDipartimenti) {
					boolean found = false;
					for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item : listaSelezionati) {
						String codiceDipartimento = item.getValue();
						if(codiceDipartimento.equals(dipartimento.getCodice())){
							found = true;
							break;
						}
					}

					if(!found)
						lista.add(dipartimento);
				}
			}
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante la selezione del dipartimento: "+ e.getMessage(), e);
		}
		this.log.debug("Ricerca Dipartimenti disponibili. Input Utente["+val+"] completata.");

		return lista;
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
			this.log.debug("["+eliminati+"] Utenti eliminati con successo.");
			MessageUtils.addInfoMsg(
					org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("utente.deleteOk"));
		}else {
			this.log.error("Si e' verificato un errore durante l'eliminazione degli utenti: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
	}

	private List<SelectItem> getOpzioniRadioButtonAbilita() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem siOption = Utils.getSelectItem("commons.label.abilitata","commons.label.abilitata");
		SelectItem elem0 = new SelectItem(siOption,siOption.getLabel());
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem noOption = Utils.getSelectItem("commons.label.nonAbilitata","commons.label.nonAbilitata");
		SelectItem elem1 = new SelectItem(noOption,noOption.getLabel()); 


		lista.add(elem0);
		lista.add(elem1);

		return lista;
	}

	private List<SelectItem> getListaEnti() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		SelectItem elem0 = new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,
						("commons.label.qualsiasi"))
//				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO)
				);


		try {

			EnteSearchForm enteSearchForm = new EnteSearchForm();
			List<EnteBean> findAllEnti = this.enteService.findAll(enteSearchForm);

			if(findAllEnti != null && findAllEnti.size() > 0){
				for (EnteBean bean : findAllEnti) {
					SelectItem item = new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
							bean.getNome().getValue(),bean.getNome().getValue()));
					lista.add(item);
				}

			}
		} catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei registri: " + e.getMessage(), e);
		}

		lista.add(0, elem0);

		return lista;
	}
	public boolean isEntePCC(String idEnte){

		try {
			EnteBean ente  = getEnteByNome(idEnte);
			return ente.getIdPccAmministrazione().getValue() != null;
		} catch (ServiceException e) {
			this.log.error("Si e' verificato un errore durante la lettura dell'ente: " + e.getMessage(), e);
		}

		return false;
	}

	private EnteBean getEnteByNome(String idEnte) throws ServiceException {
		EnteBean ente =  null;
		try {
			ente = this.enteService.findEnteByNome(idEnte);
		} catch (ServiceException e) {
			this.log.error("Si e' verificato un errore durante la lettura dell'ente: " + e.getMessage(), e);
		}

		return ente;
	}

	public DipartimentoSearchForm getDipartimentoSearch() {
		return dipartimentoSearch;
	}

	public void setDipartimentoSearch(DipartimentoSearchForm dipartimentoSearch) {
		this.dipartimentoSearch = dipartimentoSearch;
	}
	
	public int getNumeroDipartimenti() {
		return numeroDipartimenti;
	}

	public void setNumeroDipartimenti(int numeroDipartimenti) {
		this.numeroDipartimenti = numeroDipartimenti;
	}
}
