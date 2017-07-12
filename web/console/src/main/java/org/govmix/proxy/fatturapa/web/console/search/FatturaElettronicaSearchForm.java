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
package org.govmix.proxy.fatturapa.web.console.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.mbean.FatturaElettronicaMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
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
public class FatturaElettronicaSearchForm extends BaseSearchForm implements SearchForm, Cloneable{

	public static final String DATA_RICEZIONE_PERIODO_PERSONALIZZATO = "3";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI = "2";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMO_MESE = "1";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA = "0";
	
	private Text cedentePrestatore = null;
	private List<SelectItem> cedPrestSelList = null;
	private SelectList<SelectItem> dipartimento = null;
	private SelectList<SelectItem> dataRicezionePeriodo = null;
	private DateTime dataRicezione = null;
	private SelectList<SelectItem> tipoDocumento = null;
	private SelectList<SelectItem> notificaEsitoCommittente = null;
	private SelectList<SelectItem> notificaDecorrenzaTermini = null;
	private FatturaElettronicaMBean mBean = null;
	private DateTime dataEsatta = null;
	private Text numero = null;
	private Text identificativoLotto = null;

	private Text identificativoProtocollo = null;
	private SelectList<SelectItem> statoConsegna = null;
	
	private boolean usaDataScadenza = false;
	

	public FatturaElettronicaSearchForm()throws Exception{
		this.init();
	}
	
	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		// Properties del form
		this.setId("formFattura");
		this.setNomeForm("fattura.label.ricercaFatture");
		this.setClosable(false);

		// Init dei FormField
		this.cedentePrestatore = factory.getInputFieldFactory().createText("cedentePrestatore","fattura.search.cedentePrestatoreDenominazione",null,false);
		
		this.cedentePrestatore.setAutoComplete(true);
		this.cedentePrestatore.setEnableManualInput(true);
		this.cedentePrestatore.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.cedentePrestatore.setForm(this);

		this.dipartimento = factory.getInputFieldFactory().createSelectList("dipartimento","fattura.search.dipartimento",null,false);
		((SelectListImpl)this.dipartimento).setCheckItemWidth(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 

//		this.dataRicezionePeriodo = new SelectListField();
//		this.dataRicezionePeriodo.setName("dataRicezionePeriodo");
		//NOTA: Modificato a seguito della CR 80
		this.dataRicezionePeriodo = factory.getInputFieldFactory().createSelectList("dataRicezionePeriodo","fattura.search.dataRicezione",new SelectItem(FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMO_MESE,"fattura.search.dataRicezione.ultimoMese"),false);

		this.dataRicezionePeriodo.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.dataRicezionePeriodo.setForm(this);

		this.dataRicezione = factory.getInputFieldFactory().createDateTimeInterval("dataRicezione","fattura.search.dataRicezione.personalizzato","dd/M/yyyy",null,null,false);
		this.dataEsatta = factory.getInputFieldFactory().createDateTime("dataEsatta","fattura.search.dataEsatta","dd/M/yyyy",null,false);
		
		// imposto i valori di default per le date
		this._setPeriodo();

		this.tipoDocumento = factory.getInputFieldFactory().createSelectList("tipoDocumento","fattura.search.tipoDocumento",null,false);
		this.notificaEsitoCommittente = factory.getInputFieldFactory().createSelectList("notificaEsitoCommittente","fattura.search.notificaEC",null,false);
		this.notificaDecorrenzaTermini = factory.getInputFieldFactory().createSelectList( "notificaDecorrenzaTermini","fattura.search.notificaDT",null,false);
		this.numero = factory.getInputFieldFactory().createText("numero","fattura.search.numero",null,false);
		
		this.numero.setAutoComplete(true);
		this.numero.setEnableManualInput(true);
		this.numero.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.numero.setForm(this);
		
		this.identificativoLotto = factory.getInputFieldFactory().createText("identificativoLotto","fattura.search.identificativoLotto",null,false);

		this.identificativoProtocollo = factory.getInputFieldFactory().createText("identificativoProtocollo",
				"fattura.search.identificativoProtocollo",null,false);

		this.statoConsegna = factory.getInputFieldFactory().createSelectList("statoConsegna","fattura.search.statoConsegna",null,false);
		
		this.setField(this.cedentePrestatore);
		this.setField(this.dipartimento);
		this.setField(this.dataRicezionePeriodo);
		this.setField(this.dataRicezione);
		this.setField(this.dataEsatta);
		
		this.setField(this.tipoDocumento); 
		this.setField(this.notificaEsitoCommittente); 
		this.setField(this.notificaDecorrenzaTermini); 
		this.setField(this.numero); 
		this.setField(this.identificativoLotto);
		this.setField(this.identificativoProtocollo); 
		this.setField(this.statoConsegna); 


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
		this.cedentePrestatore.reset();
		this.setCedPrestSelList(new ArrayList<SelectItem>());
		this.dipartimento.reset();
		this.dataRicezionePeriodo.reset();
		this.dataRicezione.reset();
		this._setPeriodo();
		this.tipoDocumento.reset();
		this.notificaEsitoCommittente.reset();
		this.notificaDecorrenzaTermini.reset();
		
		this.identificativoLotto.reset();
		this.dataEsatta.reset(); 
		this.numero.reset();
		
		this.statoConsegna.reset();
		this.identificativoProtocollo.reset();

		this.usaDataScadenza = false;
	}

	
	
	public DateTime getDataEsatta() {
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

	public FatturaElettronicaMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(FatturaElettronicaMBean mBean) {
		this.mBean = mBean;
	}

	public Text getCedentePrestatore() {
		return this.cedentePrestatore;
	}

	public void setCedentePrestatore(Text cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public SelectList<SelectItem>  getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(SelectList<SelectItem>  dipartimento) {
		this.dipartimento = dipartimento;
	}

	public SelectList<SelectItem>  getDataRicezionePeriodo() {
		return this.dataRicezionePeriodo;
	}

	public void setDataRicezionePeriodo(SelectList<SelectItem> dataRicezionePeriodo) {
		this.dataRicezionePeriodo = dataRicezionePeriodo;
	}

	public DateTime getDataRicezione() {
		boolean rendered = (this.getDataRicezionePeriodo().getValue() != null && this.getDataRicezionePeriodo().getValue().getValue()
				.equals(FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_PERSONALIZZATO)); //Utils.getMessageFromResourceBundle("fattura.search.dataRicezione.personalizzato")));

		this.dataRicezione.setRendered(rendered);

		return this.dataRicezione;
	}

	public void setDataRicezione(DateTime dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public SelectList<SelectItem> getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(SelectList<SelectItem> tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public SelectList<SelectItem> getNotificaEsitoCommittente() {
		return this.notificaEsitoCommittente;
	}

	public void setNotificaEsitoCommittente(
			SelectList<SelectItem> notificaEsitoCommittente) {
		this.notificaEsitoCommittente = notificaEsitoCommittente;
	}

	public SelectList<SelectItem> getNotificaDecorrenzaTermini() {
		return this.notificaDecorrenzaTermini;
	}

	public void setNotificaDecorrenzaTermini(
			SelectList<SelectItem> notificaDecorrenzaTermini) {
		this.notificaDecorrenzaTermini = notificaDecorrenzaTermini;
	}

	public List<SelectItem> cedentePrestatoreAutoComplete(Object val){


		this.cedPrestSelList =  this.mBean.cedentePrestatoreAutoComplete(val);

		List<SelectItem> listToRet = new ArrayList<SelectItem>();

		List<String> app = new ArrayList<String>();
		for (SelectItem selectItem : this.cedPrestSelList) {
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
	
	public void dataRicezionePeriodoSelectListener(ActionEvent ae){
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
		Date dataInizio = this.getDataRicezione().getValue();
		Date dataFine = this.getDataRicezione().getValue2();
		
		String periodo = this.getDataRicezionePeriodo().getValue() != null ? this.getDataRicezionePeriodo().getValue().getValue() : FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA ;

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.clear(Calendar.SECOND);
		today.clear(Calendar.MILLISECOND);

		//ultima settimana
		if (FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA.equals(periodo)) {
			Calendar lastWeek = (Calendar) today.clone();
			Calendar c = Calendar.getInstance();
			dataFine = c.getTime();
			lastWeek.set(Calendar.HOUR_OF_DAY, 0);
			lastWeek.set(Calendar.MINUTE, 0);
			lastWeek.add(Calendar.DATE, -7);
			dataInizio = lastWeek.getTime();
		} else if (FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMO_MESE.equals( periodo)) {
			Calendar lastMonth = (Calendar) today.clone();

			// prendo la data corrente
			dataFine = Calendar.getInstance().getTime();

			// la data inizio rimane uguale sia per giornaliero che per orario
			lastMonth.set(Calendar.HOUR_OF_DAY, 0);
			lastMonth.set(Calendar.MINUTE, 0);
			lastMonth.add(Calendar.DATE, -30);
			dataInizio = lastMonth.getTime();
		} else if (FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI.equals( periodo)) {
			Calendar lastyear = (Calendar) today.clone();

			dataFine = Calendar.getInstance().getTime();

			lastyear.set(Calendar.HOUR_OF_DAY, 0);
			lastyear.set(Calendar.MINUTE, 0);
			lastyear.add(Calendar.DATE, -90);
			dataInizio = lastyear.getTime();
		} else {
		
			// personalizzato
			dataInizio = this.getDataRicezione().getValue();
			dataFine = this.getDataRicezione().getValue2();
		}

		//aggiorno i valori del campo
		this.getDataRicezione().setValue(dataInizio);
		this.getDataRicezione().setValue2(dataFine);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public List<SelectItem> getCedPrestSelList() {
		return cedPrestSelList;
	}

	public void setCedPrestSelList(List<SelectItem> cedPrestSelList) {
		this.cedPrestSelList = cedPrestSelList;
	}


	public Text getIdentificativoProtocollo() {
		return identificativoProtocollo;
	}

	public void setIdentificativoProtocollo(Text identificativoProtocollo) {
		this.identificativoProtocollo = identificativoProtocollo;
	}

	public SelectList<SelectItem> getStatoConsegna() {
		return statoConsegna;
	}

	public void setStatoConsegna(SelectList<SelectItem> statoConsegna) {
		this.statoConsegna = statoConsegna;
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

	public boolean isUsaDataScadenza() {
		return usaDataScadenza;
	}

	public void setUsaDataScadenza(boolean usaDataScadenza) {
		this.usaDataScadenza = usaDataScadenza;
	}	
	
	
}
