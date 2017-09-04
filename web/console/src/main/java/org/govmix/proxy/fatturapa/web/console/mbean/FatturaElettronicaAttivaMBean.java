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
package org.govmix.proxy.fatturapa.web.console.mbean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.AllegatoFatturaBean;
import org.govmix.proxy.fatturapa.web.console.bean.ConservazioneBean;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaAttivaBean;
import org.govmix.proxy.fatturapa.web.console.bean.TracciaSDIBean;
import org.govmix.proxy.fatturapa.web.console.datamodel.FatturaElettronicaAttivaDM;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.govmix.proxy.fatturapa.web.console.form.FatturaForm;
import org.govmix.proxy.fatturapa.web.console.iservice.IAllegatiService;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaAttivaService;
import org.govmix.proxy.fatturapa.web.console.iservice.ITracciaSDIService;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaAttivaSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.AllegatiService;
import org.govmix.proxy.fatturapa.web.console.service.TracciaSDIService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.DataModelListView;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.FiltraException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;

/**
 * FatturaElettronicaMBean ManagedBean per le schermate di visualizzazione delle fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FatturaElettronicaAttivaMBean extends DataModelListView<FatturaElettronicaAttivaBean, Long,
	FatturaElettronicaAttivaSearchForm, FatturaForm, FatturaElettronicaAttivaDM, FatturaElettronica, 
		IFatturaElettronicaAttivaService>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Select List Statiche 

	// Filtro date
	private List<SelectItem> listaPeriodoTemporale = null;

	// Tipo Documento
	private List<SelectItem> listaTipoDocumento = null;
	
	// Tipo Comunicazione
	private List<SelectItem> listaTipoComunicazione = null;

	// Dipartimento
	private List<SelectItem> listaDipartimenti = null;

	// Stato Consegna
	private List<SelectItem> listaStatoElaborazione = null;
	
	// supporto per il caricamento dal db del dettaglio (Allegati, NotificheEC, NotificheDT)
	private IAllegatiService allegatiService = null;
	private ITracciaSDIService comunicazioneService = null;
	private IdFattura selectedIdFattura = null;

	private String selectedTab = null;

	public FatturaElettronicaAttivaMBean(){
		super(LoggerManager.getConsoleLogger());
		this.initTables();
		this.setOutcomes();
		
		this.form = new FatturaForm();
		this.form.setmBean(this); 
		this.form.setRendered(true);
		((SelectListImpl)this.form.getDipartimento()).setElencoSelectItems(this._getDipartimenti(true));
		this.form.reset();

		this.log.debug("FatturaAttiva MBean");

	}
	/*
	 * enableDelete="false"
				headerText="#{msg['fattura.label.ricercaFatture.tabellaRisultati']}" showSelectAll="true"  id="panelFatture"
	 * */

	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("fatturaElettronicaTable"); 
			this.table.setEnableDelete(false);
			this.table.setShowAddButton(false);
			this.table.setShowDetailColumn(false);
			this.table.setShowSelectAll(true);
			this.table.setHeaderText("fatturaAttiva.label.ricercaFattureAttive.tabellaRisultati");
			this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 

		}catch (Exception e) {
			log.error("Errore durante la init Fattura MBean:" + e.getMessage(),e);  
		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome("listaFattureAttive?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaFattureAttive");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome("listaFattureAttive?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaFattureAttive");
	}
	
	
	@Override
	protected String _filtra() throws FiltraException {
		return super._filtra();
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(FatturaElettronicaAttivaSearchForm search) {
		this.search = search;
		this.selectedElement = null;

		// Popolo le selectList Statiche
		((SelectListImpl)this.search.getTipoDocumento()).setElencoSelectItems(this.getListaTipoDocumento());
		((SelectListImpl)this.search.getTipoComunicazione()).setElencoSelectItems(this.getListaTipoComunicazione());
		((SelectListImpl)this.search.getDataInvioPeriodo()).setElencoSelectItems(this.getListaPeriodoTemporale());
		((SelectListImpl)this.search.getDipartimento()).setElencoSelectItems(this.getDipartimenti());
		((SelectListImpl)this.search.getStatoElaborazione()).setElencoSelectItems(this.getListaStatoElaborazione()); 
		this.search.setmBean(this);
	}


	@Override
	public void setSelectedElement(FatturaElettronicaAttivaBean selectedElement) {
		this.selectedElement = selectedElement;
		this.selectedIdFattura = null;

		if(this.selectedElement != null){

			this.selectedIdFattura = new IdFattura();
			this.selectedIdFattura.setPosizione(this.selectedElement.getDTO().getPosizione());
			this.selectedIdFattura.setIdentificativoSdi(this.selectedElement.getDTO().getIdentificativoSdi());
			this.selectedIdFattura.setId(this.selectedElement.getDTO().getId()); 

			// caricare le informazioni su allegati 
			if(this.allegatiService == null)
				this.allegatiService = new AllegatiService();

			this.allegatiService.setIdFattura(this.selectedIdFattura);
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

			// Lista Comunicazioni
			List<TracciaSDIBean> listaComunicazioni = new ArrayList<TracciaSDIBean>();

			if(this.comunicazioneService == null)
				this.comunicazioneService = new TracciaSDIService();
//
			this.comunicazioneService.setIdFattura(this.selectedIdFattura);
			
			try{
				listaComunicazioni = this.comunicazioneService.findAll();
			}catch(Exception e){
				this.log.debug("Si e' verificato un errore durante il caricamento delle comunicazioni: "+ e.getMessage(), e);

			}
			this.selectedElement.setListaComunicazioni(listaComunicazioni);
		}
	}

	@Override
	protected String _menuAction() throws MenuActionException {
		this.search.setRestoreSearch(true);
		return super._menuAction();
	}

	// Valori delle select List

	public List<SelectItem> getListaPeriodoTemporale() {
		if (this.listaPeriodoTemporale == null) {
			this.listaPeriodoTemporale = new ArrayList<SelectItem>();

			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_ULTIMA_SETTIMANA, ("fattura.search.dataRicezione.ultimaSettimana"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_ULTIMO_MESE, ("fattura.search.dataRicezione.ultimoMese"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_ULTIMI_TRE_MESI, ("fattura.search.dataRicezione.ultimiTreMesi"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_PERSONALIZZATO, ("fattura.search.dataRicezione.personalizzato"))));

		}

		return this.listaPeriodoTemporale;
	}

	public List<SelectItem> getListaTipoDocumento() {
		if (this.listaTipoDocumento == null) {
			this.listaTipoDocumento = new ArrayList<SelectItem>();

			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoDocumentoType.TD01.getValue(),  ("fattura.tipoDocumento.TD01"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoDocumentoType.TD02.getValue(),  ("fattura.tipoDocumento.TD02"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoDocumentoType.TD03.getValue(),  ("fattura.tipoDocumento.TD03"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoDocumentoType.TD04.getValue(),  ("fattura.tipoDocumento.TD04"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoDocumentoType.TD05.getValue(),  ("fattura.tipoDocumento.TD05"))));
			this.listaTipoDocumento.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoDocumentoType.TD06.getValue(),  ("fattura.tipoDocumento.TD06"))));

		}

		return this.listaTipoDocumento;
	}

	public List<SelectItem> getListaStatoElaborazione() {
		if (this.listaStatoElaborazione == null) {
			this.listaStatoElaborazione = new ArrayList<SelectItem>();

			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.SPEDIZIONE_OK.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.SPEDIZIONE_OK.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.SPEDIZIONE_NON_ATTIVA.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.SPEDIZIONE_NON_ATTIVA.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.PROTOCOLLAZIONE_OK.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.PROTOCOLLAZIONE_OK.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.NON_FIRMATO.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.NON_FIRMATO.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.FIRMA_OK.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.FIRMA_OK.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.ERRORE_SPEDIZIONE.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.ERRORE_SPEDIZIONE.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.ERRORE_PROTOCOLLAZIONE.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.ERRORE_PROTOCOLLAZIONE.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.ERRORE_FIRMA.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.ERRORE_FIRMA.getValue()))));
			
		}

		return this.listaStatoElaborazione;
	}

	public List<SelectItem> getDipartimenti() {
		this.listaDipartimenti = new ArrayList<SelectItem>();
		// Se si vogliono tutti i dipartimenti chiamare il metodo con parametro false.
		// passando true vengono restituiti solo i dipartimenti che hanno la fatturazione attiva
		this.listaDipartimenti = this._getDipartimenti(true);
		return this.listaDipartimenti;
	}
	
	
	public List<SelectItem> _getDipartimenti(boolean fatturazioneAttiva) {
		List<SelectItem> listaDipartimenti = new ArrayList<SelectItem>();

		listaDipartimenti.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*",  ("commons.label.qualsiasi"))));

		List<Dipartimento> listaDipartimentiLoggedUtente = org.govmix.proxy.fatturapa.web.console.util.Utils.getListaDipartimentiLoggedUtente();
		if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0)
			for (Dipartimento dipartimento : listaDipartimentiLoggedUtente) {
				boolean add = true;
				
				if(fatturazioneAttiva) {
					add = dipartimento.isFatturazioneAttiva();
				}
				
				if(add)
				listaDipartimenti.add(new SelectItem(
								new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(dipartimento.getCodice(),dipartimento.getDescrizione() + " ("+dipartimento.getCodice()+")")));
			}


		return listaDipartimenti;
	}
	
	
	public Dipartimento getDipartimento(String codice) {
		List<Dipartimento> listaDipartimentiLoggedUtente = org.govmix.proxy.fatturapa.web.console.util.Utils.getListaDipartimentiLoggedUtente();
		if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0)
			for (Dipartimento dipartimento : listaDipartimentiLoggedUtente) {
				if(dipartimento.getCodice().equals(codice))
					return dipartimento;
			}

		return null;
	}
	
	public List<SelectItem> getListaTipoComunicazione() {
		if (this.listaTipoComunicazione == null) {
			this.listaTipoComunicazione = new ArrayList<SelectItem>();

			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("FATTURA_USCITA",  ("fattura.tipoComunicazione.FATTURA_USCITA"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("NOTIFICA_SCARTO",  ("fattura.tipoComunicazione.NOTIFICA_SCARTO"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("RICEVUTA_CONSEGNA",  ("fattura.tipoComunicazione.RICEVUTA_CONSEGNA"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("NOTIFICA_MANCATA_CONSEGNA",  ("fattura.tipoComunicazione.NOTIFICA_MANCATA_CONSEGNA"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("ATTESTAZIONE_TRASMISSIONE_FATTURA",  ("fattura.tipoComunicazione.ATTESTAZIONE_TRASMISSIONE_FATTURA"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("NOTIFICA_ESITO_COMMITTENTE",  ("fattura.tipoComunicazione.NOTIFICA_ESITO_COMMITTENTE"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE",  ("fattura.tipoComunicazione.NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE"))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO",  ("fattura.tipoComunicazione.AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO"))));
		}

		return this.listaTipoComunicazione;
	}

	public String exportSelected() {
		try {

			// recupero lista diagnostici
			List<Long> idFatture = new ArrayList<Long>();

			// se nn sono in select all allore prendo solo quelle selezionate
			if (!this.isSelectedAll()) {
				Iterator<FatturaElettronicaAttivaBean> it = this.selectedIds.keySet().iterator();
				while (it.hasNext()) {
					FatturaElettronicaAttivaBean fatturaBean = it.next();
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
					+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_ZIP_CON_ALLEGATI
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

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> cessionarioCommittenteAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<String> listaDestinatari = ((IFatturaElettronicaAttivaService)this.service).getDestinatarioAutoComplete((String)val);

				if(listaDestinatari != null && listaDestinatari.size() > 0){
					for (String string : listaDestinatari) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(string,string));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> numeroAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<String> lstDipartimenti = ((IFatturaElettronicaAttivaService)this.service).getNumeroAutoComplete((String)val);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (String string : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(string,string));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	public String ritentaConsegna(){
		try{

			FatturaAttivaBD fatturaBD = new FatturaAttivaBD(log);
			FatturaElettronica current = this.selectedElement.getDTO();
			// [TODO]
//			fatturaBD.forzaRispedizioneFattura(current);
			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("fattura.ritentaConsegna.cambioStatoOK"));

			current.setStatoConsegna(StatoConsegnaType.IN_RICONSEGNA);

			this.selectedElement.setDTO(current); 

		}catch(Exception e){
			log.error("Errore durante l'aggiornamento dello stato fattura [Errore Consegna -> In Riconsegna]: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("fattura.ritentaConsegna.erroreGenerico"));
		}
		return null;
	}

	public IdFattura getSelectedIdFattura() {
		if(this.selectedElement == null){
			return  null;
		}

		return selectedIdFattura;
	}

	public void setSelectedIdFattura(IdFattura selectedIdFattura) {
		this.selectedIdFattura = selectedIdFattura;
	}

	public String getSelectedTab() {
		return this.selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}
	
	public void nuovaFatturaListener(ActionEvent ae) {
		this.form.setValues(null); 
		this.form.reset();
		this.listaConservazione = null;
		this.form.getFatturaFile().clear(ae);
		this.checkConservazione = false;
	}
	
	
	public String preparaFormConservazione(){
		this.listaConservazione = null;
		List<String> nomeFile = this.form.getFatturaFile().getNomeFile();
		String codDip = this.form.getDipartimento().getValue().getValue();
		Dipartimento dipartimento =  this.getDipartimento(codDip);
		String registro = dipartimento.getRegistro().getNome(); 
		
		for (String string : nomeFile) {
			ConservazioneBean conservazioneBean = new ConservazioneBean();
			conservazioneBean.setNomeFile(string);
			conservazioneBean.setAnno("");
			conservazioneBean.setProtocollo("");
			conservazioneBean.setRegistro(registro); 
			this.getListaConservazione().add(conservazioneBean );
		}
		
		return null;
	}
	public String salvaFatture(){
		return null;
	}
	
	public String salvaFormCorservazione(){
		this.checkConservazione = false;
		for (ConservazioneBean conservazione : this.listaConservazione) {
			if(StringUtils.isEmpty(conservazione.getProtocollo())){
				MessageUtils.addErrorMsg("Il campo Protocollo per la fattura "+ conservazione.getNomeFile() + " non puo' essere vuoto.");
				return null;
			}
			
			if(StringUtils.isEmpty(conservazione.getAnno())){
				MessageUtils.addErrorMsg("Il campo Anno per la fattura "+ conservazione.getNomeFile() + " non puo' essere vuoto.");
				return null;
			}
			
			if(StringUtils.isEmpty(conservazione.getRegistro())){
				MessageUtils.addErrorMsg("Il campo Registro per la fattura "+ conservazione.getNomeFile() + " non puo' essere vuoto.");
				return null;
			}
		}
		this.checkConservazione = true;
		return null;
	}
	
	public List<ConservazioneBean> getListaConservazione() {
		if(listaConservazione ==null)
			listaConservazione = new ArrayList<ConservazioneBean>();
		
		return listaConservazione;
	}

	public void setListaConservazione(List<ConservazioneBean> listaConservazione) {
		this.listaConservazione = listaConservazione;
	}

	public boolean isCheckConservazione() {
		return checkConservazione;
	}

	public void setCheckConservazione(boolean checkConservazione) {
		this.checkConservazione = checkConservazione;
	}

	private List<ConservazioneBean> listaConservazione = null;
	private boolean checkConservazione = false;
	
}
