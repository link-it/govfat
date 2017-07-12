/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.constants.EsitoType;
import org.govmix.proxy.fatturapa.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.AllegatoFatturaBean;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.bean.NotificaDTBean;
import org.govmix.proxy.fatturapa.web.console.bean.NotificaECBean;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.govmix.proxy.fatturapa.web.console.iservice.IAllegatiService;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.iservice.INotificaDTService;
import org.govmix.proxy.fatturapa.web.console.iservice.INotificaECService;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaSearchForm;
import org.govmix.proxy.fatturapa.web.console.search.NotificaECForm;
import org.govmix.proxy.fatturapa.web.console.service.AllegatiService;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.service.NotificaDTService;
import org.govmix.proxy.fatturapa.web.console.service.NotificaECService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf2.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.table.PagedDataTable;

/**
 * FatturaElettronicaMBean ManagedBean per le schermate di visualizzazione delle fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
@ViewScoped @ManagedBean(name="fatturaElettronicaMBean")
public class FatturaElettronicaMBean extends BaseMBean<FatturaElettronicaBean, Long, FatturaElettronicaSearchForm>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Select List Statiche 
	//Notifica Esito Committente
	private List<SelectItem> listaNotificaEC = null;

	// Notifica Decorrenza Termini
	private List<SelectItem> listaNotificaDT = null;

	// Filtro date
	private List<SelectItem> listaPeriodoTemporale = null;

	// Tipo Documento
	private List<SelectItem> listaTipoDocumento = null;

	// Dipartimento
	private List<SelectItem> listaDipartimenti = null;

	private PagedDataTable<List<FatturaElettronicaBean>, FatturaElettronicaSearchForm, NotificaECForm> table;

	// supporto per il caricamento dal db del dettaglio (Allegati, NotificheEC, NotificheDT)
	private INotificaECService notificaECService = null;
	private IAllegatiService allegatiService = null;
	private INotificaDTService notificaDTService = null;

	public FatturaElettronicaMBean(){
		super(LoggerManager.getConsoleLogger());
		this.log.debug("Fattura MBean");
	}

	@PostConstruct
	private void initManagedProperties(){
		setSearch((FatturaElettronicaSearchForm) Utils.findBean("fatturaElettronicaSearchForm"));
		this.service = (FatturaElettronicaService) Utils.findBean("fatturaElettronicaService");
		String reqParameterValue = Utils.getRequestParameter(this.getSelectedIdParamName());
		
		// In teoria risolve il problema della navigazione
		if(reqParameterValue != null){
			setSelectedIdParam(reqParameterValue);
		}
	}
	/*
	 * enableDelete="false"
				headerText="#{msg['fattura.label.ricercaFatture.tabellaRisultati']}" showSelectAll="true"  id="panelFatture"
	 * */
 
	@Override
	public void initSelectedIdParamName() throws Exception {
		setSelectedIdParamName("selectedIdFattura"); 
	}

	public void init() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.getTable().setId("panelFatture"); 
			this.getTable().setEnableDelete(false);
			this.getTable().setShowAddButton(false);
			this.getTable().setShowDetailColumn(false);
			this.getTable().setShowSelectAll(true);
			this.getTable().setHeaderText("fattura.label.ricercaFatture.tabellaRisultati");
			//	this.table.setMBean(this);
			this.getTable().setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	@Override
	public void initNavigationManager() throws Exception {
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome("listaFatture?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaFatture");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome("listaFatture?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaFatture");
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(FatturaElettronicaSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedIdParam = null;
		this.selectedId = null;

		// Popolo le selectList Statiche
		((SelectListImpl)this.search.getTipoDocumento()).setElencoSelectItems(this.getListaTipoDocumento());
		((SelectListImpl)this.search.getDataRicezionePeriodo()).setElencoSelectItems(this.getListaPeriodoTemporale());
		((SelectListImpl)this.search.getNotificaDecorrenzaTermini()).setElencoSelectItems(this.getListaNotificaDT());
		((SelectListImpl)this.search.getNotificaEsitoCommittente()).setElencoSelectItems(this.getListaNotificaEC());
		((SelectListImpl)this.search.getDipartimento()).setElencoSelectItems(this.getDipartimenti());
		this.search.setmBean(this);
	}
 
	public void setSelectedIdParam(String selectedIdFattura) {
		this.selectedIdParam = selectedIdFattura;

		this.log.debug("Selected Id ["+selectedIdFattura+"]");

		if(selectedIdFattura!= null){
			try {
				this.selectedId = new Long(this.selectedIdParam);
				FatturaElettronicaBean findById = this.service.findById(this.selectedId);
				this.setSelectedElement(findById); 
			} catch (ServiceException e) {
				log.error("Errore durante la lettura della fattura con Id ["+this.selectedId+"]: "+ e.getMessage(), e); 
			}
		}
	}

	@Override
	public void setSelectedElement(FatturaElettronicaBean selectedElement) {
		this.selectedElement = selectedElement;

		if(this.selectedElement != null){

			IdFattura idFattura = new IdFattura();
			idFattura.setPosizione(this.selectedElement.getDTO().getPosizione());
			idFattura.setIdentificativoSdi(this.selectedElement.getDTO().getIdentificativoSdi());

			// caricare le informazioni su   notificheEC  
			if(this.notificaECService == null)
				this.notificaECService = new NotificaECService();

			this.notificaECService.setIdFattura(idFattura);
			List<NotificaECBean> listaNotificaEC = new ArrayList<NotificaECBean>();
			try{
				listaNotificaEC = this.notificaECService.findAll();
			}catch(Exception e){
				this.log.debug("Si e' verificato un errore durante il caricamento della lista delle notifiche EC: "+ e.getMessage(), e);

			}
			this.selectedElement.setListaNotificaEC(listaNotificaEC); 

			// caricare le informazioni su allegati 
			if(this.allegatiService == null)
				this.allegatiService = new AllegatiService();

			this.allegatiService.setIdFattura(idFattura);
			List<AllegatoFatturaBean> listaAllegati = new ArrayList<AllegatoFatturaBean>();
			try{
				listaAllegati = this.allegatiService.findAll();

				//				if(listaAllegati != null && listaAllegati.size() > 0){
				//					for (AllegatoFatturaBean allegatoBean : listaAllegati) {
				//						allegatoBean.setIdFattura(this.selectedElement.getDTO().getId());
				//					}
				//				}
			}catch(Exception e){
				this.log.debug("Si e' verificato un errore durante il caricamento degli allegati: "+ e.getMessage(), e);

			}
			this.selectedElement.setAllegati(listaAllegati);

			List<NotificaDTBean> listaNotificaDT = new ArrayList<NotificaDTBean>();
			if(this.selectedElement.getDTO().getIdDecorrenzaTermini() != null){
				// caricare le informazioni su   notificheDT  
				if(this.notificaDTService == null)
					this.notificaDTService = new NotificaDTService();

				this.notificaDTService.setIdNotificaDecorrenzaTermini(this.selectedElement.getDTO().getIdDecorrenzaTermini());

				try{
					listaNotificaDT = this.notificaDTService.findAll();

					if(listaNotificaDT != null && listaNotificaDT.size() > 0){
						for (NotificaDTBean notificaDTBean : listaNotificaDT) {
							notificaDTBean.setIdFattura(this.selectedElement.getDTO().getId());
						}
					}
				}catch(Exception e){
					this.log.debug("Si e' verificato un errore durante il caricamento della lista delle notifiche DT: "+ e.getMessage(), e);

				}
			}
			this.selectedElement.setListaNotificaDT(listaNotificaDT); 


		}
	}

	@Override
	public String azioneMenuAction() throws MenuActionException {
		this.search.setRestoreSearch(true);
		return super.azioneMenuAction();
	}

	// Valori delle select List

	public List<SelectItem> getListaNotificaEC() {
		if (this.listaNotificaEC == null) {
			this.listaNotificaEC = new ArrayList<SelectItem>();

			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem("E",   ("fattura.search.notificaEC.nonPresente"))));
			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(EsitoType.IN_ELABORAZIONE_ACCETTATO.getValue(),   ("fattura.search.notificaEC.inElaborazione.accettato"))));
			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(EsitoType.IN_ELABORAZIONE_RIFIUTATO.getValue(),  ("fattura.search.notificaEC.inElaborazione.rifiutato"))));
			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(EsitoType.INVIATA_ACCETTATO.getValue(),   ("fattura.search.notificaEC.inviata.accettato"))));
			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(EsitoType.INVIATA_RIFIUTATO.getValue() ,  ("fattura.search.notificaEC.inviata.rifiutato"))));
			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(EsitoType.SCARTATA_ACCETTATO.getValue(),   ("fattura.search.notificaEC.scartata.accettato"))));
			this.listaNotificaEC.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(EsitoType.SCARTATA_RIFIUTATO.getValue() ,  ("fattura.search.notificaEC.scartata.rifiutato"))));

		}

		return this.listaNotificaEC;
	}

	public List<SelectItem> getListaNotificaDT() {
		if (this.listaNotificaDT == null) {
			this.listaNotificaDT = new ArrayList<SelectItem>();

			this.listaNotificaDT.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem("*",  ("commons.label.qualsiasi"))));
			this.listaNotificaDT.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem("Y",   ("commons.label.presente"))));
			this.listaNotificaDT.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem("N",   ("commons.label.nonPresente"))));
		}

		return this.listaNotificaDT;
	}

	public List<SelectItem> getListaPeriodoTemporale() {
		if (this.listaPeriodoTemporale == null) {
			this.listaPeriodoTemporale = new ArrayList<SelectItem>();

			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA, ("fattura.search.dataRicezione.ultimaSettimana"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMO_MESE, ("fattura.search.dataRicezione.ultimoMese"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI, ("fattura.search.dataRicezione.ultimiTreMesi"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_PERSONALIZZATO, ("fattura.search.dataRicezione.personalizzato"))));

		}

		return this.listaPeriodoTemporale;
	}

	public List<SelectItem> getListaTipoDocumento() {
		if (this.listaTipoDocumento == null) {
			this.listaTipoDocumento = new ArrayList<SelectItem>();

			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoDocumentoType.TD01.getValue(),  ("fattura.tipoDocumento.TD01"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoDocumentoType.TD02.getValue(),  ("fattura.tipoDocumento.TD02"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoDocumentoType.TD03.getValue(),  ("fattura.tipoDocumento.TD03"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoDocumentoType.TD04.getValue(),  ("fattura.tipoDocumento.TD04"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoDocumentoType.TD05.getValue(),  ("fattura.tipoDocumento.TD05"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(TipoDocumentoType.TD06.getValue(),  ("fattura.tipoDocumento.TD06"))));

		}

		return this.listaTipoDocumento;
	}

	public List<SelectItem> getDipartimenti() {
		this.listaDipartimenti = new ArrayList<SelectItem>();

		this.listaDipartimenti.add(new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem("*",  ("commons.label.qualsiasi"))));

		List<Dipartimento> listaDipartimentiLoggedUtente = org.govmix.proxy.fatturapa.web.console.util.Utils.getListaDipartimentiLoggedUtente();
		if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0)
			for (Dipartimento dipartimento : listaDipartimentiLoggedUtente) {
				this.listaDipartimenti.add(
						new SelectItem(
								new org.openspcoop2.generic_project.web.input.SelectItem(dipartimento.getCodice(),dipartimento.getDescrizione() + " ("+dipartimento.getCodice()+")")));
			}


		return this.listaDipartimenti;
	}

	public String exportSelected() {
		try {

			// recupero lista diagnostici
			List<Long> idFatture = new ArrayList<Long>();

			// se nn sono in select all allore prendo solo quelle selezionate
			if (!this.isSelectedAll()) {
				Iterator<FatturaElettronicaBean> it = this.selectedIds.keySet().iterator();
				while (it.hasNext()) {
					FatturaElettronicaBean fatturaBean = it.next();
					if (this.selectedIds.get(fatturaBean).booleanValue()) {
						idFatture.add(fatturaBean.getDTO().getId());
						it.remove();
					}
				}
			}

			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.sendRedirect(context.getExternalContext()
					.getRequestContextPath()
					+ "/" + FattureExporter.FATTURE_EXPORTER+"?"+FattureExporter.PARAMETRO_IS_ALL+"="
					+ this.isSelectedAll()
					+ "&"+FattureExporter.PARAMETRO_IDS+"="
					+ StringUtils.join(idFatture, ",")
					+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_ZIP_CON_ALLEGATI
					+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA);

			context.responseComplete();

			// End of the method
		} catch (Exception e) {
			FacesContext.getCurrentInstance().responseComplete();
			this.log.error(e, e);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
		}

		return null;
	}

	public List<org.openspcoop2.generic_project.web.input.SelectItem> cedentePrestatoreAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();

		org.openspcoop2.generic_project.web.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();
			else{
				List<String> listaMittenti = ((IFatturaElettronicaService)this.service).getMittenteAutoComplete((String)val);

				if(listaMittenti != null && listaMittenti.size() > 0){
					for (String string : listaMittenti) {
						lst.add(new  org.openspcoop2.generic_project.web.input.SelectItem(string,string));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);



		return lst;
	}

	public List<org.openspcoop2.generic_project.web.input.SelectItem> numeroAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();

		org.openspcoop2.generic_project.web.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();
			else{
				List<String> lstDipartimenti = ((IFatturaElettronicaService)this.service).getNumeroAutoComplete((String)val);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (String string : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.input.SelectItem(string,string));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	public PagedDataTable<List<FatturaElettronicaBean>,  FatturaElettronicaSearchForm,NotificaECForm> getTable() {
		return table;
	}

	public void setTable(PagedDataTable<List<FatturaElettronicaBean>, FatturaElettronicaSearchForm,NotificaECForm> table) {
		this.table = table;
	}

}
