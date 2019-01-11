/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.pcc.mbean;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.TracciaPccEstesaBean;
import org.govmix.proxy.fatturapa.web.console.pcc.form.OperazioneForm;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IOperazioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.OperazioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
//import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.RestoreSearchException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.table.PagedDataTable;

public class OperazioneMBean extends BaseMBean<TracciaPccEstesaBean, Long, OperazioneSearchForm>{

	// Select List Statiche 
	//Tipi Operazioni
	private List<SelectItem> listaTipiOperazioni = null;

	// Filtro date
	private List<SelectItem> listaPeriodoTemporale = null;

	// Esito
	private List<SelectItem> listaEsito = null;	
	
	// Codice Errore PCC
	private List<SelectItem> listaCodiciErrorePCC = null;	

	private IdTraccia selectedIdTraccia = null;

	private FatturaElettronicaBean fattura = null;

	private IFatturaElettronicaService fatturaService = null;

	private String paginaFrom = null; // menu oppure dettaglioFattura

	private PagedDataTable<List<TracciaPccEstesaBean>, OperazioneForm, OperazioneSearchForm> table;
	
	private PccTracciamentoBD tracciamentoBD = null;

	public OperazioneMBean(){
		super(LoggerManager.getConsoleLogger());
		this.initTables();
		this.setOutcomes();

		this.fatturaService = new FatturaElettronicaService();
		
		this.log.debug("Operazioni MBean");
	}

	public void initTables() {
		try{
			this.tracciamentoBD = new PccTracciamentoBD(this.log);
			
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.getTable().setId("panelOperazioni"); 
			this.getTable().setEnableDelete(false);
			this.getTable().setShowAddButton(false);
			this.getTable().setShowDetailColumn(false);
			this.getTable().setShowSelectAll(false);
			this.getTable().setHeaderText("operazione.label.ricercaOperazioni.tabellaRisultati");
			//	this.table.setMBean(this);
			this.getTable().setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome("listaOperazioni?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaOperazioni");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome("listaOperazioni?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaOperazioni");
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(OperazioneSearchForm search) {
		this.search = search;
		this.selectedElement = null;

		// Popolo le selectList Statiche
		((SelectListImpl)this.search.getDataPeriodo()).setElencoSelectItems(this.getListaPeriodoTemporale());
		((SelectListImpl)this.search.getEsito()).setElencoSelectItems(this.getListaEsito());
		((SelectListImpl)this.search.getOperazione()).setElencoSelectItems(this.getListaTipiOperazioni());
		((SelectListImpl)this.search.getCodiceErrore()).setElencoSelectItems(this.getListaCodiciErrorePCC());


		this.search.setmBean(this);
	}
	
	@Override
	public void setSelectedId(Long selectedId) {
		super.setSelectedId(selectedId);
		
		if(this.selectedId != null){
			try {
				TracciaPccEstesaBean findById = this.service.findById(this.selectedId);
				this.setSelectedElement(findById); 
			} catch (ServiceException e) {
				log.error("Errore durante la lettura del dettaglio della traccia: "+ e.getMessage(),e);
			}
		}
	}


	@Override
	public void setSelectedElement(TracciaPccEstesaBean selectedElement) {
		this.selectedElement = selectedElement;
		this.selectedIdTraccia = null;

		if(this.selectedElement != null){
			this.selectedIdTraccia = new IdTraccia();
			this.selectedIdTraccia.setIdTraccia(this.selectedElement.getDTO().getId()); 

			try {

				this.selectedElement = this.service.findById(this.selectedElement.getDTO().getId());
			} catch (ServiceException e) {
				log.error("Errore durante la lettura del dettaglio della traccia: "+ e.getMessage(),e);
			}

//			this.fattura = null;
			// se sono passato dal menu' mi serve la fattura
			//if(this.paginaFrom != null && this.paginaFrom.equals("menu")){
				try{
					this.fattura = this.fatturaService.findById(this.selectedElement.getDTO().getIdFattura());
				} catch (ServiceException e) {
					log.error("Errore durante la lettura del dettaglio della fattura associata alla traccia: "+ e.getMessage(),e);
				}
//			}
		}

	}

	@Override
	protected String _restoreSearch() throws RestoreSearchException {
		return super._restoreSearch();
	}

	@Override
	protected String _menuAction() throws MenuActionException {
		this.search.setRestoreSearch(true);
		this.paginaFrom = "menu";
		return super._menuAction();
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> utenteRichiedenteAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<String> listaMittenti = ((IOperazioneService)this.service).getUtenteRichiedenteAutoComplete((String)val);

				if(listaMittenti != null && listaMittenti.size() > 0){
					for (String string : listaMittenti) {
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

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> sistemaRichiedenteAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<String> listaMittenti = ((IOperazioneService)this.service).getSistemaRichiedenteAutoComplete((String)val);

				if(listaMittenti != null && listaMittenti.size() > 0){
					for (String string : listaMittenti) {
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

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> cedentePrestatoreAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<String> listaMittenti = ((IOperazioneService)this.service).getMittenteAutoComplete((String)val);

				if(listaMittenti != null && listaMittenti.size() > 0){
					for (String string : listaMittenti) {
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
				List<String> lstDipartimenti = ((IOperazioneService)this.service).getNumeroAutoComplete((String)val);

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

	public List<SelectItem> getListaTipiOperazioni() {
		if (this.listaTipiOperazioni == null) {
			this.listaTipiOperazioni = new ArrayList<SelectItem>();

			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("PagamentoIVA", ("pccOperazione.nome.PagamentoIVA"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("DatiFattura", ("pccOperazione.nome.DatiFattura"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("InserimentoFattura", ("pccOperazione.nome.InserimentoFattura"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("StatoFattura", ("pccOperazione.nome.StatoFattura"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("ElencoMovimentiErarioIVA", ("pccOperazione.nome.ElencoMovimentiErarioIVA"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("DownloadDocumento", ("pccOperazione.nome.DownloadDocumento"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("ConsultazioneTracce", ("pccOperazione.nome.ConsultazioneTracce"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_CP", ("pccOperazione.nome.OperazioneContabile_CP"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_SP", ("pccOperazione.nome.OperazioneContabile_SP"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_CS", ("pccOperazione.nome.OperazioneContabile_CS"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_CCS", ("pccOperazione.nome.OperazioneContabile_CCS"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_CSPC", ("pccOperazione.nome.OperazioneContabile_CSPC"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_CO", ("pccOperazione.nome.OperazioneContabile_CO"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_SC", ("pccOperazione.nome.OperazioneContabile_SC"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_CPS", ("pccOperazione.nome.OperazioneContabile_CPS"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_RC", ("pccOperazione.nome.OperazioneContabile_RC"))));
			this.listaTipiOperazioni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("OperazioneContabile_RF", ("pccOperazione.nome.OperazioneContabile_RF"))));
		}

		return this.listaTipiOperazioni;
	}

	public void setListaTipiOperazioni(List<SelectItem> listaTipiOperazioni) {
		this.listaTipiOperazioni = listaTipiOperazioni;
	}

	public List<SelectItem> getListaPeriodoTemporale() {
		if (this.listaPeriodoTemporale == null) {
			this.listaPeriodoTemporale = new ArrayList<SelectItem>();

			this.listaPeriodoTemporale.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMA_SETTIMANA, ("operazione.search.data.ultimaSettimana"))));
			this.listaPeriodoTemporale.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMO_MESE, ("operazione.search.data.ultimoMese"))));
			this.listaPeriodoTemporale.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMI_TRE_MESI, ("operazione.search.data.ultimiTreMesi"))));
			this.listaPeriodoTemporale.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_PERSONALIZZATO, ("operazione.search.data.personalizzato"))));

		}

		return this.listaPeriodoTemporale;
	}

	public void setListaPeriodoTemporale(List<SelectItem> listaPeriodoTemporale) {
		this.listaPeriodoTemporale = listaPeriodoTemporale;
	}

	public List<SelectItem> getListaEsito() {
		if (this.listaEsito == null) {
			this.listaEsito = new ArrayList<SelectItem>(); 

			this.listaEsito.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaEsito.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoType.AS_OK.getValue(),"pccStato." +StatoType.AS_OK.getValue())));
			this.listaEsito.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoType.AS_PRESA_IN_CARICO.getValue(),"pccStato." +StatoType.AS_PRESA_IN_CARICO.getValue())));
			this.listaEsito.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoType.AS_ERRORE.getValue(),"pccStato." +StatoType.AS_ERRORE.getValue())));
			this.listaEsito.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoType.AS_ERRORE_PRESA_IN_CARICO.getValue(),"pccStato." +StatoType.AS_ERRORE_PRESA_IN_CARICO.getValue())));
			this.listaEsito.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoType.S_OK.getValue(),"pccStato." +StatoType.S_OK.getValue())));
			this.listaEsito.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoType.S_ERRORE.getValue(),"pccStato." +StatoType.S_ERRORE.getValue())));


		}
		return this.listaEsito;
	}

	public void setListaEsito(List<SelectItem> listaEsito) {
		this.listaEsito = listaEsito;
	}

	public PagedDataTable<List<TracciaPccEstesaBean>, OperazioneForm, OperazioneSearchForm> getTable() {
		return this.table;
	}

	public void setTable(PagedDataTable<List<TracciaPccEstesaBean>, OperazioneForm, OperazioneSearchForm> table) {
		this.table = table;
	}

	public IdTraccia getSelectedIdTraccia() {
		return selectedIdTraccia;
	}

	public void setSelectedIdTraccia(IdTraccia selectedIdTraccia) {
		this.selectedIdTraccia = selectedIdTraccia;
	}

	public String getPaginaFrom() {
		return paginaFrom;
	}

	public void setPaginaFrom(String paginaFrom) {
		this.paginaFrom = paginaFrom;
	}

	public FatturaElettronicaBean getFattura() {
		return fattura;
	}

	public void setFattura(FatturaElettronicaBean fattura) {
		this.fattura = fattura;
	}

	public String inviaRispedizione(){
		try{
			
			this.tracciamentoBD.forzaRispedizione(this.getSelectedIdTraccia()); 
			
			this.setSelectedId(this.getSelectedIdTraccia().getIdTraccia());
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("tracciaPcc.inviaRispedizione.cambioStatoOK"));


		}catch(Exception e){
			log.error("Errore durante l'invio della rispedizione: "+ e.getMessage(),e);
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("tracciaPcc.inviaRispedizione.erroreGenerico"));
		}
		return null;
	}

	public String refreshDettaglioPcc(){
		return null;
	}

	private boolean visualizzaRefresh = true;

	public boolean isVisualizzaRefresh() {
//		int val = 0;
//
//		try{
//			val =((OperazioneService)this.service).countTracce(StatoType.AS_PRESA_IN_CARICO);
//		}catch(Exception e){
//			log.error("Errore durante la count tracce: "+ e.getMessage(),e);
//		}
//
//		this.visualizzaRefresh = val > 0;

		return visualizzaRefresh;
	}

	public void setVisualizzaRefresh(boolean visualizzaRefresh) {
		this.visualizzaRefresh = visualizzaRefresh;
	}

	public List<SelectItem> getListaCodiciErrorePCC() {
		if (this.listaCodiciErrorePCC == null) {
			this.listaCodiciErrorePCC = new ArrayList<SelectItem>();

			this.listaCodiciErrorePCC.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			try {
				TreeMap<String,String> codiciErrorePCC = ConsoleProperties.getInstance(getLog()).getCodiciErrorePCC();
				
				for (String codice : codiciErrorePCC.keySet()) {
					this.listaCodiciErrorePCC.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(codice, codice)));
				}
			} catch (Exception e) {
				log.error("Errore durante la lettura dei codici di errore PCC: "+ e.getMessage(),e);
			}
		}
		
		return listaCodiciErrorePCC;
	}

	public void setListaCodiciErrorePCC(List<SelectItem> listaCodiciErrorePCC) {
		this.listaCodiciErrorePCC = listaCodiciErrorePCC;
	}


}
