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
package org.govmix.proxy.fatturapa.web.console.mbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.Arrays;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFattureAttiveBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoRequest;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse.ESITO;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoSoloConservazioneRequest;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.AllegatoFatturaBean;
import org.govmix.proxy.fatturapa.web.console.bean.ConservazioneFormBean;
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

	// Notifica Decorrenza Termini
	private List<SelectItem> listaNotificaDT = null;
	
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
	
	// Dominio
	private List<SelectItem> listaDomini = null;

	// supporto per il caricamento dal db del dettaglio (Allegati, NotificheEC, NotificheDT)
	private IAllegatiService allegatiService = null;
	private ITracciaSDIService comunicazioneService = null;
	private IdFattura selectedIdFattura = null;

	private String selectedTab = null;

	public static final String PROTOCOLLO_PATTERN = "^[0-9]*$";

	public static final String ANNO_PATTERN = "^(19|20)\\d{2}$";

	private Pattern protocolloPattern = null;
	private Pattern annoPattern = null;

	public FatturaElettronicaAttivaMBean(){
		super(LoggerManager.getConsoleLogger());
		this.initTables();
		this.setOutcomes();

//		this.form = new FatturaForm();
		

		this.protocolloPattern = Pattern.compile(FatturaElettronicaAttivaMBean.PROTOCOLLO_PATTERN);
		this.annoPattern = Pattern.compile(FatturaElettronicaAttivaMBean.ANNO_PATTERN);

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
		((SelectListImpl)this.search.getNotificaDecorrenzaTermini()).setElencoSelectItems(this.getListaNotificaDT());
		((SelectListImpl)this.search.getDataInvioPeriodo()).setElencoSelectItems(this.getListaPeriodoTemporale());
		((SelectListImpl)this.search.getDipartimento()).setElencoSelectItems(this.getDipartimenti());
		((SelectListImpl)this.search.getStatoElaborazione()).setElencoSelectItems(this.getListaStatoElaborazione());
		((SelectListImpl)this.search.getDominio()).setElencoSelectItems(this.getListaDomini()); 
		this.search.setmBean(this);
		
		if(this.search.getSoloConservazione() != null && this.search.getSoloConservazione().booleanValue()) {
			this.table.setHeaderText("fatturaAttiva.label.ricercaFattureAttiveSoloConservazione.tabellaRisultati");
			this.getNavigationManager().setFiltraOutcome("listaFattureAttiveSoloConservazione?faces-redirect=true");
			this.getNavigationManager().setMenuActionOutcome("listaFattureAttiveSoloConservazione");
			this.getNavigationManager().setResetOutcome("listaFattureAttiveSoloConservazione?faces-redirect=true");
			this.getNavigationManager().setRestoreSearchOutcome("listaFattureAttiveSoloConservazione");
		}
	}

	
	@Override
	public void setForm(FatturaForm form) {
		super.setForm(form);
		if(this.form != null) {
			this.form.setmBean(this); 
			this.form.setRendered(true);
			((SelectListImpl)this.form.getDipartimento()).setElencoSelectItems(this._getDipartimenti(false,true));
			this.form.reset();
		}
	}

	@Override
	public void setSelectedElement(FatturaElettronicaAttivaBean selectedElement) {
		this.selectedElement = selectedElement;
		this.selectedIdFattura = null;

		if(this.selectedElement != null){
			try{
				// forzo il ricaricamento della fattura perche' potrebbe essere passato il batch e averne cambiato lo stato
				this.selectedElement = ((IFatturaElettronicaAttivaService)this.service).findById(this.selectedElement.getId());
			}catch(Exception e){
				this.log.debug("Si e' verificato un errore durante il caricamento della fattura: "+ e.getMessage(), e);
	
			}
		}
		
		if(this.selectedElement != null){

			this.selectedIdFattura = new IdFattura(selectedElement.getDTO().isFatturazioneAttiva());
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
	
	public List<SelectItem> getListaNotificaDT() {
		if (this.listaNotificaDT == null) {
			this.listaNotificaDT = new ArrayList<SelectItem>();

			this.listaNotificaDT.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*",  ("commons.label.qualsiasi"))));
			this.listaNotificaDT.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("Y",   ("commons.label.presente"))));
			this.listaNotificaDT.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("N",   ("commons.label.nonPresente"))));
		}

		return this.listaNotificaDT;
	}

	public List<SelectItem> getListaPeriodoTemporale() {
		if (this.listaPeriodoTemporale == null) {
			this.listaPeriodoTemporale = new ArrayList<SelectItem>();

			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMA_SETTIMANA, ("fattura.search.dataRicezione.ultimaSettimana"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMO_MESE, ("fattura.search.dataRicezione.ultimoMese"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMI_TRE_MESI, ("fattura.search.dataRicezione.ultimiTreMesi"))));
			this.listaPeriodoTemporale.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_PERSONALIZZATO, ("fattura.search.dataRicezione.personalizzato"))));

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
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.ERRORE_DI_FIRMA.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.ERRORE_DI_FIRMA.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.ERRORE_DI_PROTOCOLLO.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.ERRORE_DI_PROTOCOLLO.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.ERRORE_DI_SPEDIZIONE.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.ERRORE_DI_SPEDIZIONE.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.IMPOSSIBILITA_DI_RECAPITO.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.IMPOSSIBILITA_DI_RECAPITO.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.MANCATA_CONSEGNA.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.MANCATA_CONSEGNA.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.RICEVUTA_DALLO_SDI.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.RICEVUTA_DALLO_SDI.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.RICEVUTA_DAL_DESTINATARIO.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.RICEVUTA_DAL_DESTINATARIO.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO.getValue()))));
			this.listaStatoElaborazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoElaborazioneType.RICEVUTO_SCARTO_SDI.getValue(),  ("fattura.statoElaborazione."+StatoElaborazioneType.RICEVUTO_SCARTO_SDI.getValue()))));
		}

		return this.listaStatoElaborazione;
	}
	
	public List<SelectItem> getListaDomini() {
		if (this.listaDomini == null) {
			this.listaDomini = new ArrayList<SelectItem>();
			
			this.listaDomini.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaDomini.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(DominioType.PA.getValue(),  ("fattura.dominio."+DominioType.PA.getValue()))));
			this.listaDomini.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(DominioType.B2B.getValue(),  ("fattura.dominio."+DominioType.B2B.getValue()))));
			
		}
		return listaDomini;
	}

	public List<SelectItem> getDipartimenti() {
		this.listaDipartimenti = new ArrayList<SelectItem>();
		// Se si vogliono tutti i dipartimenti chiamare il metodo con parametro false.
		// passando true vengono restituiti solo i dipartimenti che hanno la fatturazione attiva
		this.listaDipartimenti = this._getDipartimenti(true,true);
		return this.listaDipartimenti;
	}


	public List<SelectItem> _getDipartimenti(boolean addQualsiasi, boolean fatturazioneAttiva) {
		List<SelectItem> listaDipartimenti = new ArrayList<SelectItem>();

		if(addQualsiasi)
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
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.FAT_OUT.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.FAT_OUT.toString()))));
//			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.EC.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.EC.toString()))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.NE.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.NE.toString()))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.DT.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.DT.toString()))));
//			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.SE.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.SE.toString()))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.RC.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.RC.toString()))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.NS.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.NS.toString()))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.MC.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.MC.toString()))));
			this.listaTipoComunicazione.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(TipoComunicazioneType.AT.toString(),  ("fattura.tipoComunicazione."+TipoComunicazioneType.AT.toString()))));
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
							org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
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

			LottoFattureAttiveBD fatturaBD = new LottoFattureAttiveBD(log);
			FatturaElettronica current = this.selectedElement.getDTO();
			IdLotto idLotto = fatturaBD.newIdLotto();
			idLotto.setIdentificativoSdi(current.getIdentificativoSdi());
			StatoElaborazioneType nuovoStato = fatturaBD.ritentaConsegna(idLotto);
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.ritentaConsegna.cambioStatoOK"));

			current.getLottoFatture().setStatoElaborazioneInUscita(nuovoStato);

			this.selectedElement.setDTO(current); 

		}catch(Exception e){
			log.error("Errore durante l'aggiornamento dello stato fattura [Errore Consegna -> In Riconsegna]: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.ritentaConsegna.erroreGenerico"));
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

		// reset form valore dipartimento, se mettiamo null non valida il form e richfaces non valida gli altri campi.
		List<SelectItem> lst = this._getDipartimenti(false, true);
		if(lst != null && lst.size() > 0) {
			SelectItem selectItem = lst.get(0);
			this.form.getDipartimento().setDefaultValue((org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem) selectItem.getValue());
		}

		this.form.reset();
		this.listaConservazione = null;
		this.form.getFatturaFile().clear(ae);
		this.checkConservazione = false;
		this.salvataggioOk = false;
		this.checkFormFattura = false;
		this.checkFormFatturaMessage = null;
		this.checkFormConservazioneMessage = null;
	}


	public String preparaFormConservazione(){

		// prima di mostrare la form della conservazione controllo il form 
		this.checkFormFattura = false;
		this.checkFormConservazioneMessage = null;
		this.checkFormFatturaMessage = this.validaFormFatture();
		if(this.checkFormFatturaMessage != null) {
			this.checkFormFattura = false;
			return null;
		}

		this.checkFormFattura = true;

		this.form.getFatturaFile().checkCaricamenti();
		String codDip = this.form.getDipartimento().getValue().getValue();

		try {
			List<InserimentoLottoSoloConservazioneRequest> requestList = new ArrayList<InserimentoLottoSoloConservazioneRequest>();
			
			Map<String, byte[]> filesMap = new HashMap<String, byte[]>();

			List<String> nomeFiles = this.form.getFatturaFile().getNomeFile();
			for (int i = 0; i < nomeFiles.size(); i++) {
				String nomeFattura = nomeFiles.get(i);
				byte[] xml = this.form.getFatturaFile().getContenuto().get(i);
				
				if(filesMap.containsKey(nomeFattura) && Arrays.areEqual(filesMap.get(nomeFattura), xml)) {
					// NON INSERISCO
					
					this.log.info("Non inserisco il file ["+nomeFattura+"] in quanto duplicato");
				} else {
					filesMap.put(nomeFattura, xml);
					this.log.info("Inserisco il file ["+nomeFattura+"]");
					InserimentoLottoSoloConservazioneRequest dto = new InserimentoLottoSoloConservazioneRequest();
					dto.setNomeFile(nomeFattura);
					dto.setDipartimento(codDip);
					dto.setXml(xml);
					requestList.add(dto);
				}
			}
			
			((IFatturaElettronicaAttivaService)this.service).checkLottoSoloConservazione(requestList);
			
		}catch(InserimentoLottiException e) {
			this.checkFormFattura = false;
			CODICE codEccezione = e.getCodice() != null ? e.getCodice() : CODICE.ERRORE_GENERICO;
			String msgCod = "fattura.salvaFattura." + codEccezione.name();
			Object[] msgParams = e.getParams();
			
			if(msgParams != null && msgParams.length > 0) {
				msgCod +=".parametri";
				this.checkFormFatturaMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle(msgCod, msgParams);
			} else {
				this.checkFormFatturaMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle(msgCod);
			}
			return null;
		} catch(Exception e) {
			this.checkFormFattura = false;
			this.checkFormFatturaMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFattura."+CODICE.ERRORE_GENERICO.name());
			return null;
		}

		this.listaConservazione = null;
		List<String> nomeFile = this.form.getFatturaFile().getNomeFile();
		Dipartimento dipartimento =  this.getDipartimento(codDip);
		IdRegistro idRegistro = dipartimento.getRegistro();
		
		boolean valoreValidoRegistro = (idRegistro != null && StringUtils.isNotEmpty(idRegistro.getNome())); 
		String registro = valoreValidoRegistro ? idRegistro.getNome() : "";
		
		this.labelColonnaRegistro = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.form.registro");
		
		// [BUSSU] Per ora il registro e' opzionale, per far apparire l'asterisco del campo obbligatorio abilitare le seguenti tre righe.
//		this.labelColonnaRegistro = valoreValidoRegistro ? org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.form.registro")
//				: org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.form.registro.obbligatorio");

		for (String string : nomeFile) {
			ConservazioneFormBean conservazioneBean = new ConservazioneFormBean();
			conservazioneBean.setNomeFile(string);
			conservazioneBean.setAnno("");
			conservazioneBean.setProtocollo("");
			conservazioneBean.setRegistro(registro); 
			conservazioneBean.setEditRegistro(!valoreValidoRegistro); 
			this.getListaConservazione().add(conservazioneBean );
		}

		return null;
	}
	public String salvaFatture(){
		this.form.disableButton();
		this.salvataggioOk = false;
		this.checkFormFatturaMessage = null;
		this.checkFormFattura = false;
		try {

			this.checkFormFatturaMessage = this.validaFormFatture();

			if(this.checkFormFatturaMessage != null) {
				this.checkFormFattura = false;
				this.form.enableButton();
				return null;
			}

			this.checkFormFattura = true;

			this.form.getFatturaFile().checkCaricamenti();

			List<String> nomiFileRicevuti = this.form.getFatturaFile().getNomeFile();

			List<InserimentoLottoRequest> toSave = new ArrayList<InserimentoLottoRequest>();
			
			//** Mappa per duplicati **//
			
			Map<String, byte[]> filesMap = new HashMap<String, byte[]>();
			
			// scorro la lista dei files e creo le fatture
			for (int i = 0; i < nomiFileRicevuti.size(); i++) {
				String nomeFattura = nomiFileRicevuti.get(i);
				byte[] xml = this.form.getFatturaFile().getContenuto().get(i);
				
				if(filesMap.containsKey(nomeFattura) && Arrays.areEqual(filesMap.get(nomeFattura), xml)) {
					// NON INSERISCO
					
					this.log.info("Non inserisco il file ["+nomeFattura+"] in quanto duplicato");
				} else {
					filesMap.put(nomeFattura, xml);
					this.log.info("Inserisco il file ["+nomeFattura+"]");
					InserimentoLottoRequest dto = new InserimentoLottoRequest();
					dto.setNomeFile(nomeFattura);
					dto.setXml(xml);
					dto.setDipartimento(this.form.getDipartimento().getValue().getValue());
					toSave.add(dto);
				}
			}

			InserimentoLottoResponse salvaFatture = ((IFatturaElettronicaAttivaService)this.service).salvaFatture(toSave);
			
			if(salvaFatture.getEsito().equals(ESITO.OK)) {
				this.salvataggioOk = true;
				MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFattura.salvataggioOk"));
			} else {
				if(salvaFatture.getEccezione() != null) {
					log.error("Errore durante il salvataggio delle fatture: "+ salvaFatture.getEccezione().getMessage(),salvaFatture.getEccezione());
					
					CODICE codEccezione = salvaFatture.getEccezione().getCodice() != null ? salvaFatture.getEccezione().getCodice() : CODICE.ERRORE_GENERICO;
					String msgCod = "fattura.salvaFattura." + codEccezione.name();
					Object[] msgParams = salvaFatture.getEccezione().getParams();
					
					if(codEccezione.equals(CODICE.ERRORE_GENERICO) && msgParams != null && msgParams.length > 0) {
						msgCod +=".parametri";
						this.checkFormFatturaMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle(msgCod, msgParams);
					} else {
						this.checkFormFatturaMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle(msgCod);
					}
				} else {
					log.error("Errore durante il salvataggio delle fatture [eccezione non presente nella response]");
					this.checkFormFatturaMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFattura."+CODICE.ERRORE_GENERICO.name());
				}
			}
		}catch(Exception e) {
			log.error("Errore durante il salvataggio delle fatture: "+ e.getMessage(),e);
			this.checkFormFatturaMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFattura."+CODICE.ERRORE_GENERICO.name());
		}

		this.form.enableButton();
		return null;
	}

	public String validaFormFatture() {
		String toRet = null;

		int numeroFileRicevuti = this.form.getFatturaFile().getMapChiaviElementi().size();

		if(numeroFileRicevuti < 1) {
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFattura.nessunFileSelezionato");
		}		
		
		// controllo identificativi
		if(this.form.checkIdentificativi() !=null)
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFattura."+CODICE.ERRORE_GENERICO.name());

		// Dipartimento
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem _dipartimento = this.form.getDipartimento().getValue();

		String valueCodiceDipartimento = null; 

		if(_dipartimento != null)
			valueCodiceDipartimento = _dipartimento.getValue();

		if( valueCodiceDipartimento == null || (valueCodiceDipartimento != null && valueCodiceDipartimento.equals(CostantiForm.ALL)))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.form.getDipartimento().getLabel());


		return toRet;
	}

	public String salvaFormCorservazione(){
		this.checkConservazione = false;
		this.checkFormConservazioneMessage = this.validaFormConservazione();

		if(this.checkFormConservazioneMessage != null) {
			return null;
		}

		try {

			// collezionare le informazioni della conservazione e chiamare il salvataggio

			List<String> nomiFileRicevuti = this.form.getFatturaFile().getNomeFile();

			List<InserimentoLottoSoloConservazioneRequest> toSave = new ArrayList<InserimentoLottoSoloConservazioneRequest>();
			// scorro la lista dei files e creo le fatture
			for (int i = 0; i < nomiFileRicevuti.size(); i++) {
				String nomeFattura = nomiFileRicevuti.get(i);
				byte[] xml = this.form.getFatturaFile().getContenuto().get(i);

				InserimentoLottoSoloConservazioneRequest dto = new InserimentoLottoSoloConservazioneRequest();
				dto.setNomeFile(nomeFattura);
				dto.setXml(xml);
				dto.setDipartimento(this.form.getDipartimento().getValue().getValue());

				// conservazione
				ConservazioneFormBean conservazioneBean = this.listaConservazione.get(i);
				dto.setNumeroProtocollo(conservazioneBean.getProtocollo());
				dto.setAnnoProtocollo(conservazioneBean.getAnno());
				dto.setRegistroProtocollo(conservazioneBean.getRegistro()); 

				toSave.add(dto);
			}

			InserimentoLottoResponse salvaFattureSoloConservazione = ((IFatturaElettronicaAttivaService)this.service).salvaFattureSoloConservazione(toSave);

			if(salvaFattureSoloConservazione.getEsito().equals(ESITO.OK)) {
				this.checkConservazione = true;
				MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFatturaSoloConservazione.salvataggioOk"));
			} else {
				if(salvaFattureSoloConservazione.getEccezione() != null) {
					log.error("Errore durante il salvataggio delle fatture: "+ salvaFattureSoloConservazione.getEccezione().getMessage(),salvaFattureSoloConservazione.getEccezione());
					
					CODICE codEccezione = salvaFattureSoloConservazione.getEccezione().getCodice() != null ? salvaFattureSoloConservazione.getEccezione().getCodice() : CODICE.ERRORE_GENERICO;
					String msgCod = "fattura.salvaFatturaSoloConservazione." + codEccezione.name();
					Object[] msgParams = salvaFattureSoloConservazione.getEccezione().getParams();
					
					if(!codEccezione.equals(CODICE.ERRORE_GENERICO) && msgParams != null && msgParams.length > 0) {
						msgCod +=".parametri";
						this.checkFormConservazioneMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle(msgCod, msgParams);
					} else {
						this.checkFormConservazioneMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle(msgCod);
					}
				} else {
					log.error("Errore durante il salvataggio delle fatture [eccezione non presente nella response]");
					this.checkFormConservazioneMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFatturaSoloConservazione."+CODICE.ERRORE_GENERICO.name());
				}
			}
		}catch(Exception e) {
			log.error("Errore durante il salvataggio delle fatture: "+ e.getMessage(),e);
			this.checkFormConservazioneMessage = org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFatturaSoloConservazione."+CODICE.ERRORE_GENERICO.name());
		}
		return null;
	}

	public String validaFormConservazione() {
		for (ConservazioneFormBean conservazione : this.listaConservazione) {

			// [BUSSU] Per ora il registro e' opzionale, per renderlo obbligatorio abilitare le seguenti tre righe.
//			if(StringUtils.isEmpty(conservazione.getRegistro())){
//				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("fattura.conservazione.validazione.registroVuoto",conservazione.getNomeFile());
//			}
			
			if(StringUtils.isEmpty(conservazione.getProtocollo())){
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("fattura.conservazione.validazione.protocolloVuoto",conservazione.getNomeFile());
			}

			Matcher matcher = this.protocolloPattern.matcher(conservazione.getProtocollo());

			if(!matcher.matches()) {
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("fattura.conservazione.validazione.protocolloNonValido",conservazione.getNomeFile());
			}

			if(StringUtils.isEmpty(conservazione.getAnno())){
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("fattura.conservazione.validazione.annoVuoto",conservazione.getNomeFile());
			}

			matcher = this.annoPattern.matcher(conservazione.getAnno());

			if(!matcher.matches()) {
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("fattura.conservazione.validazione.annoNonValido",conservazione.getNomeFile());
			}


		}
		
		return null;
	}

	public List<ConservazioneFormBean> getListaConservazione() {
		if(listaConservazione ==null)
			listaConservazione = new ArrayList<ConservazioneFormBean>();

		return listaConservazione;
	}

	public void setListaConservazione(List<ConservazioneFormBean> listaConservazione) {
		this.listaConservazione = listaConservazione;
	}

	public boolean isCheckConservazione() {
		return checkConservazione;
	}

	public void setCheckConservazione(boolean checkConservazione) {
		this.checkConservazione = checkConservazione;
	}

	private List<ConservazioneFormBean> listaConservazione = null;
	private boolean checkConservazione = false;
	private boolean salvataggioOk = false;
	private boolean checkFormFattura = false;
	private String checkFormFatturaMessage = null;
	private String checkFormConservazioneMessage = null;

	public boolean isSalvataggioOk() {
		return salvataggioOk;
	}

	public void setSalvataggioOk(boolean salvataggioOk) {
		this.salvataggioOk = salvataggioOk;
	}

	public boolean isCheckFormFattura() {
		return checkFormFattura;
	}

	public void setCheckFormFattura(boolean checkFormFattura) {
		this.checkFormFattura = checkFormFattura;
	}

	public String getCheckFormFatturaMessage() {
		if(this.checkFormFatturaMessage == null && this.form != null) {
			this.checkFormFatturaMessage =  this.form.getFatturaFile().getFileUploadErrorMessage();
		}


		return checkFormFatturaMessage;
	}

	public void setCheckFormFatturaMessage(String checkFormFatturaMessage) {
		this.checkFormFatturaMessage = checkFormFatturaMessage;
	}


	public boolean isVisualizzaTastoCaricaFattura() {
		this.visualizzaTastoCaricaFattura = this._getDipartimenti(false, true).size() > 0;

		return visualizzaTastoCaricaFattura;
	}

	public void setVisualizzaTastoCaricaFattura(boolean visualizzaTastoCaricaFattura) {
		this.visualizzaTastoCaricaFattura = visualizzaTastoCaricaFattura;
	}

	public String getCheckFormConservazioneMessage() {
		return checkFormConservazioneMessage;
	}

	public void setCheckFormConservazioneMessage(String checkFormConservazioneMessage) {
		this.checkFormConservazioneMessage = checkFormConservazioneMessage;
	}

	public String getLabelColonnaRegistro() {
		return labelColonnaRegistro;
	}

	public void setLabelColonnaRegistro(String labelColonnaRegistro) {
		this.labelColonnaRegistro = labelColonnaRegistro;
	}

	private boolean visualizzaTastoCaricaFattura = false;
	private String labelColonnaRegistro = null;
	
}
