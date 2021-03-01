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
package org.govmix.proxy.fatturapa.web.console.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.mbean.FatturaElettronicaAttivaMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.BooleanCheckBox;
import org.openspcoop2.generic_project.web.input.DateTime;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

/**
 * FatturaElettronicaSearchForm Form di ricerca delle Fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FatturaElettronicaAttivaSearchForm extends BaseSearchForm implements SearchForm, Cloneable{

	private Text cessionarioCommittente = null;
	private List<SelectItem> cessCommSelList = null;
	private SelectList<SelectItem> dipartimento = null;
	private SelectList<SelectItem> dataInvioPeriodo = null;
	private DateTime dataInvio = null;
	private SelectList<SelectItem> tipoDocumento = null;
	private SelectList<SelectItem> tipoComunicazione = null;
	private FatturaElettronicaAttivaMBean mBean = null;
	private DateTime dataEsatta = null;
	private Text numero = null;
	private Text identificativoLotto = null;
	private Text identificativoProtocollo = null;
	private SelectList<SelectItem> statoElaborazione = null;
	private SelectList<SelectItem> notificaDecorrenzaTermini = null;
	private BooleanCheckBox conservazione = null;
	private Boolean soloConservazione = null;
	private SelectList<SelectItem> formatoTrasmissione = null;

	public FatturaElettronicaAttivaSearchForm()throws Exception{
		this.init();
	}
	
	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		// Properties del form
		this.setId("formFattura");
		this.setNomeForm("fatturaAttiva.label.ricercaFattureAttive");
		this.setClosable(false);

		// Init dei FormField
		this.cessionarioCommittente = factory.getInputFieldFactory().createText("cessionarioCommittente","fatturaAttiva.search.cessionarioCommittenteDenominazione",null,false);
		
		this.cessionarioCommittente.setAutoComplete(true);
		this.cessionarioCommittente.setEnableManualInput(true);
		this.cessionarioCommittente.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.cessionarioCommittente.setForm(this);

		this.dipartimento = factory.getInputFieldFactory().createSelectList("dipartimento","fattura.search.uoMittente",null,false);
		((SelectListImpl)this.dipartimento).setCheckItemWidth(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 

//		this.dataInvioPeriodo = new SelectListField();
//		this.dataInvioPeriodo.setName("dataInvioPeriodo");
		//NOTA: Modificato a seguito della CR 80
		this.dataInvioPeriodo = factory.getInputFieldFactory().createSelectList("dataInvioPeriodo","fattura.search.dataInvio",new SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMO_MESE,"fattura.search.dataInvio.ultimoMese"),false);

		this.dataInvioPeriodo.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.dataInvioPeriodo.setForm(this);

		this.dataInvio = factory.getInputFieldFactory().createDateTimeInterval("dataInvio","fattura.search.dataInvio.personalizzato",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY,null,null,false);
		this.dataEsatta = factory.getInputFieldFactory().createDateTime("dataEsatta","fattura.search.dataEsatta",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY,null,false);
		
		// imposto i valori di default per le date
		this._setPeriodo();

		this.tipoDocumento = factory.getInputFieldFactory().createSelectList("tipoDocumento","fattura.search.tipoDocumento",null,false);
		((SelectListImpl)this.tipoDocumento).setCheckItemWidth(true); 
		this.numero = factory.getInputFieldFactory().createText("numero","fattura.search.numero",null,false);
		
		this.numero.setAutoComplete(true);
		this.numero.setEnableManualInput(true);
		this.numero.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.numero.setForm(this);
		
		this.identificativoLotto = factory.getInputFieldFactory().createText("identificativoLotto","fattura.search.identificativoLotto",null,false);

		this.identificativoProtocollo = factory.getInputFieldFactory().createText("identificativoProtocollo",
				"fattura.search.identificativoProtocollo",null,false);

		this.statoElaborazione = factory.getInputFieldFactory().createSelectList("statoElaborazione","fattura.search.statoElaborazione",null,false);
		
		this.tipoComunicazione = factory.getInputFieldFactory().createSelectList("tipoComunicazione","fattura.search.tipoComunicazione",null,false);
		this.tipoComunicazione.setRendered(false); 
		
		this.notificaDecorrenzaTermini = factory.getInputFieldFactory().createSelectList( "notificaDecorrenzaTermini","fattura.search.notificaDT",null,false);
		
		this.conservazione = factory.getInputFieldFactory().createBooleanCheckBox("consSearch","fattura.search.conservazione",false,false);
		
		this.formatoTrasmissione = factory.getInputFieldFactory().createSelectList( "formatoTrasmissione","fattura.search.formatoTrasmissione",null,false);
		
		
		this.setField(this.cessionarioCommittente);
		this.setField(this.dipartimento);
		this.setField(this.dataInvioPeriodo);
		this.setField(this.dataInvio);
		this.setField(this.dataEsatta);
		
		this.setField(this.tipoDocumento); 
		this.setField(this.numero); 
		this.setField(this.identificativoLotto);
		this.setField(this.identificativoProtocollo); 
		this.setField(this.statoElaborazione); 
		this.setField(this.notificaDecorrenzaTermini); 
		this.setField(this.tipoComunicazione); 
		this.setField(this.conservazione);
		
		this.setField(this.formatoTrasmissione);
		
		this.reset();
	}

	public Text getIdentificativoLotto() {
		return this.identificativoLotto;
	}

	public void setIdentificativoLotto(Text identificativoLotto) {
		this.identificativoLotto = identificativoLotto;
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();

		// reset search fields
		this.cessionarioCommittente.reset();
		this.setCessCommSelList(new ArrayList<SelectItem>());
		this.dipartimento.reset();
		this.dataInvioPeriodo.reset();
		this.dataInvio.reset();
		this._setPeriodo();
		this.tipoDocumento.reset();
		this.tipoComunicazione.reset();
		this.conservazione.reset();
		this.identificativoLotto.reset();
		this.dataEsatta.reset(); 
		this.numero.reset();
		this.notificaDecorrenzaTermini.reset();
		this.statoElaborazione.reset();
		this.identificativoProtocollo.reset();
		this.formatoTrasmissione.reset();
	}

	
	
	public DateTime getDataEsatta() {
		if(this.hasSoloConservazione()) {
			boolean rendered = (this.getDataInvioPeriodo().getValue() != null && this.getDataInvioPeriodo().getValue().getValue()
					.equals(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_ESATTA)); //Utils.getMessageFromResourceBundle("fattura.search.dataInvio.personalizzato")));
			this.dataEsatta.setRendered(rendered);
		}
		return this.dataEsatta;
	}

	public void setDataEsatta(DateTime dataEsatta) {
		this.dataEsatta = dataEsatta;
	}

	public Text getNumero() {
		return this.numero;
	}

	public void setNumero(Text numero) {
		this.numero = numero;
	}

	public FatturaElettronicaAttivaMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(FatturaElettronicaAttivaMBean mBean) {
		this.mBean = mBean;
	}

	public Text getCessionarioCommittente() {
		return this.cessionarioCommittente;
	}

	public void setCessionarioCommittente(Text cessionarioCommittente) {
		this.cessionarioCommittente = cessionarioCommittente;
	}

	public SelectList<SelectItem>  getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(SelectList<SelectItem>  dipartimento) {
		this.dipartimento = dipartimento;
	}

	public SelectList<SelectItem>  getDataInvioPeriodo() {
		return this.dataInvioPeriodo;
	}

	public void setDataInvioPeriodo(SelectList<SelectItem> dataInvioPeriodo) {
		this.dataInvioPeriodo = dataInvioPeriodo;
	}

	public DateTime getDataInvio() {
		boolean rendered = (this.getDataInvioPeriodo().getValue() != null && this.getDataInvioPeriodo().getValue().getValue()
				.equals(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_PERSONALIZZATO)); //Utils.getMessageFromResourceBundle("fattura.search.dataInvio.personalizzato")));

		this.dataInvio.setRendered(rendered);

		return this.dataInvio;
	}

	public void setDataInvio(DateTime dataInvio) {
		this.dataInvio = dataInvio;
	}

	public SelectList<SelectItem> getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(SelectList<SelectItem> tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<SelectItem> cessionarioCommittenteAutoComplete(Object val){


		this.cessCommSelList =  this.mBean.cessionarioCommittenteAutoComplete(val);

		List<SelectItem> listToRet = new ArrayList<SelectItem>();

		List<String> app = new ArrayList<String>();
		for (SelectItem selectItem : this.cessCommSelList) {
			String res = selectItem.getValue().trim(); 

			String noSpaceRes = res.replace("  ", " ");
			while(noSpaceRes.contains("  ")) {
				noSpaceRes = noSpaceRes.replace("  ", " ");
			}
			if(!app.contains(noSpaceRes)) 
				app.add(noSpaceRes);
		}

		for (String string : app) {
			listToRet.add(new SelectItem(string, string));
		}

		return listToRet;
	}
	
	public void dataInvioPeriodoSelectListener(ActionEvent ae){
		this._setPeriodo();
	}
	
	public void cedentePrestatoreSelectListener(ActionEvent ae){
		//do something
	}
	
	public List<SelectItem> numeroAutoComplete(Object val){
		return this.mBean.numeroAutoComplete(val);
	}
	
	public void numeroSelectListener(ActionEvent ae){
		//do something
	}
	
	/**
	 * Aggiornamento del valore effettivo delle date quando seleziono un valore nella tendina del periodo
	 * 
	 * 0 ultima settimana
	 * 1 ultimo mese
	 * 2 ultimi 3 mesi 
	 * 3 personalizzato
	 */
	private void _setPeriodo() {
		Date dataInizio = this.getDataInvio().getValue();
		Date dataFine = this.getDataInvio().getValue2();
		
		String periodo = this.getDataInvioPeriodo().getValue() != null ? this.getDataInvioPeriodo().getValue().getValue() : org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMA_SETTIMANA ;

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.clear(Calendar.SECOND);
		today.clear(Calendar.MILLISECOND);
		
		Date dataEsatta = null;
		
		//ultima settimana
		if (org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMA_SETTIMANA.equals(periodo)) {
			Calendar lastWeek = (Calendar) today.clone();
			Calendar c = Calendar.getInstance();
			dataFine = c.getTime();
			lastWeek.set(Calendar.HOUR_OF_DAY, 0);
			lastWeek.set(Calendar.MINUTE, 0);
			lastWeek.add(Calendar.DATE, -7);
			dataInizio = lastWeek.getTime();
		} else if (org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMO_MESE.equals( periodo)) {
			Calendar lastMonth = (Calendar) today.clone();

			// prendo la data corrente
			dataFine = Calendar.getInstance().getTime();

			// la data inizio rimane uguale sia per giornaliero che per orario
			lastMonth.set(Calendar.HOUR_OF_DAY, 0);
			lastMonth.set(Calendar.MINUTE, 0);
			lastMonth.add(Calendar.DATE, -30);
			dataInizio = lastMonth.getTime();
		} else if (org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_PERIODO_ULTIMI_TRE_MESI.equals( periodo)) {
			Calendar lastyear = (Calendar) today.clone();

			dataFine = Calendar.getInstance().getTime();

			lastyear.set(Calendar.HOUR_OF_DAY, 0);
			lastyear.set(Calendar.MINUTE, 0);
			lastyear.add(Calendar.DATE, -90);
			dataInizio = lastyear.getTime();
		} else if (org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_INVIO_ESATTA.equals( periodo)) {
			// se e' data esatta non setto niente lascio la data come quella personalizzata la gestisco nel search form
			dataInizio = this.getDataInvio().getValue();
			dataFine = this.getDataInvio().getValue2();
			dataEsatta = Calendar.getInstance().getTime();
		} else {
			// personalizzato
			dataInizio = this.getDataInvio().getValue();
			dataFine = this.getDataInvio().getValue2();
		}

		//aggiorno i valori del campo
		this.getDataInvio().setValue(dataInizio);
		this.getDataInvio().setValue2(dataFine);
		
		if(this.hasSoloConservazione()) {
			this.dataEsatta.setValue(dataEsatta); 
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public List<SelectItem> getCessCommSelList() {
		return cessCommSelList;
	}

	public void setCessCommSelList(List<SelectItem> cessCommSelList) {
		this.cessCommSelList = cessCommSelList;
	}

	public Text getIdentificativoProtocollo() {
		return identificativoProtocollo;
	}

	public void setIdentificativoProtocollo(Text identificativoProtocollo) {
		this.identificativoProtocollo = identificativoProtocollo;
	}

	public SelectList<SelectItem> getStatoElaborazione() {
		return statoElaborazione;
	}

	public void setStatoElaborazione(SelectList<SelectItem> statoElaborazione) {
		this.statoElaborazione = statoElaborazione;
	}

	public void dataInizioChangeListener(javax.faces.event.ValueChangeEvent event){
		Logger log = LoggerManager.getConsoleLogger();

		Object obj = event.getNewValue();
		log.debug("Nuovo valore data inizio :" + obj); 
	}

	public void dataInizioValidator(javax.faces.context.FacesContext facesContext, javax.faces.component.UIComponent component, java.lang.Object value){
//		Logger log = LoggerManager.getConsoleLogger();
//
//		if(value != null){
//			log.debug("Data INIZIO inserita: "  + value);
//		} else 
//			log.debug("Data INIZIO inserita: NULL");
	}

	public void dataFineValidator(javax.faces.context.FacesContext facesContext, javax.faces.component.UIComponent component, java.lang.Object value){
//		Logger log = LoggerManager.getConsoleLogger();
//
//		if(value != null){
//			log.debug("Data FINE inserita: "  + value);
//		} else 
//			log.debug("Data FINE inserita: NULL");
	}

	public void dataEsattaValidator(javax.faces.context.FacesContext facesContext, javax.faces.component.UIComponent component, java.lang.Object value){
//		Logger log = LoggerManager.getConsoleLogger();
//
//		if(value != null){
//			log.debug("Data Esatta inserita: "  + value);
//		} else 
//			log.debug("Data Esatta inserita: NULL");

	}

	public SelectList<SelectItem> getTipoComunicazione() {
		return tipoComunicazione;
	}

	public void setTipoComunicazione(SelectList<SelectItem> tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}	
	
	public SelectList<SelectItem> getNotificaDecorrenzaTermini() {
		return this.notificaDecorrenzaTermini;
	}

	public void setNotificaDecorrenzaTermini(
			SelectList<SelectItem> notificaDecorrenzaTermini) {
		this.notificaDecorrenzaTermini = notificaDecorrenzaTermini;
	}
	
	public BooleanCheckBox getConservazione() {
		return conservazione;
	}

	public void setConservazione(BooleanCheckBox conservazione) {
		this.conservazione = conservazione;
	}
	
	public boolean hasSoloConservazione() {
		return soloConservazione != null ? soloConservazione.booleanValue() : false;
	}

	public Boolean getSoloConservazione() {
		return soloConservazione;
	}

	public void setSoloConservazione(Boolean soloConservazione) {
		this.soloConservazione = soloConservazione;
		
		if(this.soloConservazione != null && this.soloConservazione.booleanValue()) {
			this.setNomeForm("fatturaAttiva.label.ricercaFattureAttiveSoloConservazione");
			this.getDataInvioPeriodo().setLabel("fattura.search.dataEsatta");
			this.getDataEsatta().setLabel("fattura.search.dataEsatta.valore");
		}
	}

	public SelectList<SelectItem> getFormatoTrasmissione() {
		return formatoTrasmissione;
	}

	public void setFormatoTrasmissione(SelectList<SelectItem> formatoTrasmissione) {
		this.formatoTrasmissione = formatoTrasmissione;
	}
	
}
