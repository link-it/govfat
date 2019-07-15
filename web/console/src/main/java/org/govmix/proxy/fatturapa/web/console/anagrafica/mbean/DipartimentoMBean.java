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

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.DipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel.DipartimentoDM;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IEnteService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.EnteService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.RegistroService;
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

public class DipartimentoMBean extends DataModelListView<DipartimentoBean, Long, DipartimentoSearchForm,DipartimentoForm,DipartimentoDM,Dipartimento,IDipartimentoService>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showForm = false;

	private String azione = null;

	private IRegistroService registroService = null;
	
	private IEnteService enteService = null;
	
	public DipartimentoMBean() {
		super(LoggerManager.getConsoleLogger());
		this.log.debug("Dipartimento MBean");
		this.form = new DipartimentoForm();
		this.form.setRendered(false);
		this.form.setMbean(this); 
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
		this.registroService = new RegistroService();
		this.enteService = new EnteService();
		
		this.initTables();
		this.setOutcomes();
	}
	
	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("dipartimentiListView"); 
			this.table.setEnableDelete(this.isAbilitaCreazioneDipartimenti());
			this.table.setShowAddButton(this.isAbilitaCreazioneDipartimenti());
			this.table.setShowDetailColumn(false);
			this.table.setShowSelectAll(this.isAbilitaCreazioneDipartimenti());
			this.table.setHeaderText("dipartimento.tabellaRisultati.label");
			this.table.setDetailLinkText("dipartimento.dettaglioTitle"); 
			this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 
			
		}catch (Exception e) {
			 
		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("dipartimento");
		this.getNavigationManager().setFiltraOutcome("listaDipartimenti?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaDipartimenti");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome("dipartimento");
		this.getNavigationManager().setResetOutcome("listaDipartimenti?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaDipartimenti");
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(DipartimentoSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedId = null;

		((SelectListImpl)this.search.getEnte()).setElencoSelectItems(this.getListaEnti());
		
		//se sei un DEPT_ADMIN vedi solo i tuoi dipartimenti 
		//[Pintori 2016/06/10] Vincolo rilasciato il DeptAdmin gestisce l'anagrafica di tutti i dipartimenti
//		if(Utils.getLoginBean().isDeptAdmin()){
//			this.search.setListaUtenteDipartimenti(Utils.getLoggedUtente().getUtenteDipartimentoList()); 
//		}
		
		this.search.setmBean(this);
	}

	@Override
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;

		if(this.selectedId != null){

			try {
				//carico dettaglio utente
				DipartimentoBean dettaglioDipartimento = this.service.findById(this.selectedId);
				
				EnteBean ente = this.enteService.findEnteByNome(dettaglioDipartimento.getDTO().getEnte().getNome());
					
				dettaglioDipartimento.setEnteBean(ente); 
				
				this.setSelectedElement(dettaglioDipartimento); 
			} catch (ServiceException e) {
				this.log.error("Si e' verificato un errore durante il caricamento del dettaglio dipartimento: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public void setSelectedElement(DipartimentoBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;
	}

	// Action della pagina di ricerca
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
			this.form.setListaProprietaConsentiteAiDipartimenti(((IDipartimentoService)this.service).getListaOperazioniConsentiteUnitaOrganizzativa());
			this.form.setValues(this.selectedElement);
			this.form.setListaNomiProperties(this.getListaDipartimentoProperties());
			((SelectListImpl)this.form.getEnte()).setElencoSelectItems(this.getListaEnti());
			((SelectListImpl)this.form.getRegistro()).setElencoSelectItems(this.getListaRegistri());
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
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione")+": " + msg);
		}

		try{
			long oldId = -1;
			Dipartimento newDip = this.form.getDipartimento();
			// Add
			if(!this.azione.equals("update")){
				DipartimentoBean bean = new DipartimentoBean();
				bean.setDTO(newDip);
				boolean exists = this.service.exists(bean);

				if(exists){
					String mess = Utils.getInstance().getMessageWithParamsFromResourceBundle("dipartimento.form.dipartimentoEsistente",this.form.getCodice().getValue());
					throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione") +
							": " + mess);
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
			}


			newDip.setId(oldId);


			DipartimentoBean bean = new DipartimentoBean();
			bean.setDTO(newDip);
			bean.setListaNomiProperties(this.getListaDipartimentoProperties());
			bean.setEnteBean(this.getEnteByNome(bean.getDTO().getEnte().getNome())); 
			
			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(newDip.getCodice());
			List<PccDipartimentoOperazione> listaOperazioni = this.form.getOperazioniAbilitate();
			
			((IDipartimentoService)this.service).salvaDipartimento(bean, idDipartimento, listaOperazioni);
			
			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.salvataggioOk"));
			
			Long id = bean.getDTO().getId();
			
			this.setSelectedId(id);

			// reset lista dei dipartimenti dell'utente
			org.govmix.proxy.fatturapa.web.console.util.Utils.getLoginBean().setListDipartimenti(null); 

			return this.getNavigationManager().getInviaOutcome(); //"invia";
		}catch(InviaException e){
			throw e;
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio del dipartimento: " + e.getMessage(), e);
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreGenerico"));
		}
	}


	@Override
	protected String _annulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome("listaDipartimenti");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("annulla");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return super._annulla();
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> descrizioneAutoComplete(Object val,String nomeEnte) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<Dipartimento> lstDipartimenti = ((IDipartimentoService)this.service).getDescrizioneAutoComplete((String)val,nomeEnte);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
								dipartimento.getDescrizione(), dipartimento.getDescrizione()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}
	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> codiceAutoComplete(Object val,String nomeEnte) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<Dipartimento> lstDipartimenti = ((IDipartimentoService)this.service).getCodiceAutoComplete((String)val,nomeEnte);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
								dipartimento.getCodice(),dipartimento.getCodice()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
		this.showForm = true;
		this.azione = "new";
		try {
			this.form.setListaProprietaConsentiteAiDipartimenti(((IDipartimentoService)this.service).getListaOperazioniConsentiteUnitaOrganizzativa());
		} catch (ServiceException e) {
			this.log.error("Si e' verificato un errore durante il caricamento delle properties pcc consentite per le unita' organizzative: " + e.getMessage(), e);
		}
		this.form.setValues(null);
		this.form.setListaNomiProperties(this.getListaDipartimentoProperties());
		((SelectListImpl)this.form.getEnte()).setElencoSelectItems(this.getListaEnti());
		((SelectListImpl)this.form.getRegistro()).setElencoSelectItems(this.getListaRegistri());
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

	//	public DipartimentoForm getForm() {
	//		return form;
	//	}
	//
	//	public void setForm(DipartimentoForm form) {
	//		this.form = form;
	//	}

	public List<DipartimentoProperty> getListaDipartimentoProperties(){
		List<DipartimentoProperty> lista = new ArrayList<DipartimentoProperty>();

		try{
			org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem registroSI = this.form.getRegistro().getValue();
			String nomeRegistro = null;
			IdRegistro idRegistro = null;
			if(registroSI!= null){
				nomeRegistro = registroSI.getValue();
				idRegistro = new IdRegistro();
				idRegistro .setNome(nomeRegistro);
				lista = ((IDipartimentoService)this.service).getListaPropertiesProtocollo(idRegistro);
			}
						
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il caricamento delle properties: " + e.getMessage(), e);
		}


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

	public List<SelectItem> getListaRegistri() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		SelectItem elem0 = new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));


		try {
			RegistroSearchForm registroForm = new RegistroSearchForm();
//			registroForm.getEnte().setValue(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte().getNome());
			List<RegistroBean> findAllRegistri = this.registroService.findAll(registroForm);

			if(findAllRegistri != null && findAllRegistri.size() > 0){
				for (RegistroBean bean : findAllRegistri) {
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
			this.log.debug("["+eliminati+"] Dipartimenti eliminati con successo.");
			String deletemsgok = Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteOk");
			MessageUtils.addInfoMsg(deletemsgok);
		}else {
			this.log.error("Si e' verificato un errore durante l'eliminazione dei dipartimenti: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
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
	
	private List<SelectItem> getListaEnti() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		SelectItem elem0 = new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));


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
	
	public boolean isAbilitaCreazioneDipartimenti(){
		//[Pintori 2016/06/10] Vincolo rilasciato il DeptAdmin gestisce l'anagrafica di tutti i dipartimenti, 
		// quindi e' abilitato anche alla creazione/cancellazione.
//		return Utils.getLoginBean().isAdmin() ;
		
		return Utils.getLoginBean().isAdmin() || Utils.getLoginBean().isDeptAdmin();
	}
}
