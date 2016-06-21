/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.search;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.mbean.FatturaElettronicaMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.DateField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectListField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

/**
 * FatturaElettronicaSearchForm Form di ricerca delle Fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FatturaElettronicaSearchForm extends SearchForm {

	public static final String DATA_RICEZIONE_PERIODO_PERSONALIZZATO = "3";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI = "2";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMO_MESE = "1";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA = "0";
	private FormField<String> cedentePrestatore = null;
	private SelectListField dipartimento = null;
	private FormField<SelectItem> dataRicezionePeriodo = null;
	private FormField<Date> dataRicezione = null;
	private FormField<SelectItem> tipoDocumento = null;
	private FormField<SelectItem> notificaEsitoCommittente = null;
	private FormField<SelectItem> notificaDecorrenzaTermini = null;
	private FatturaElettronicaMBean mBean = null;
	private FormField<Date> dataEsatta = null;
	private FormField<String> numero = null;
	private FormField<String> identificativoLotto = null;
	

	public FatturaElettronicaSearchForm(){
		init();
	}

	@Override
	protected void init() {
		// Properties del form
		this.setIdForm("formFattura");
		this.setNomeForm("fattura.label.ricercaFatture");
		this.setClosable(false);

		// Init dei FormField
		this.cedentePrestatore = new TextField();
		this.cedentePrestatore.setType("textWithSuggestion");
		this.cedentePrestatore.setName("cedentePrestatore");
		this.cedentePrestatore.setDefaultValue(null);
		this.cedentePrestatore.setLabel("fattura.search.cedentePrestatoreDenominazione");
		this.cedentePrestatore.setAutoComplete(true);
		this.cedentePrestatore.setEnableManualInput(true);
		this.cedentePrestatore.setFieldsToUpdate(this.getIdForm() + "_formPnl");
		this.cedentePrestatore.setForm(this);

		this.dipartimento = new SelectListField();
		this.dipartimento.setName("dipartimento");
		this.dipartimento.setValue(null);
		this.dipartimento.setLabel("fattura.search.dipartimento");
		this.dipartimento.setCheckItemWidth(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 

		this.dataRicezionePeriodo = new SelectListField();
		this.dataRicezionePeriodo.setName("dataRicezionePeriodo");
		this.dataRicezionePeriodo.setDefaultValue(new SelectItem(DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA,"fattura.search.dataRicezione.ultimaSettimana"));
		this.dataRicezionePeriodo.setLabel("fattura.search.dataRicezione");
		this.dataRicezionePeriodo.setFieldsToUpdate(this.getIdForm() + "_formPnl");
		this.dataRicezionePeriodo.setForm(this);

		this.dataRicezione = new DateField();
		this.dataRicezione.setName("dataRicezione");
		this.dataRicezione.setDefaultValue(null);
		this.dataRicezione.setDefaultValue2(null);
		this.dataRicezione.setInterval(true);
		this.dataRicezione.setLabel("fattura.search.dataRicezione.personalizzato");
		this.dataRicezione.setPattern("dd/M/yyyy");
		
		this.dataEsatta = new DateField();
		this.dataEsatta.setName("dataEsatta");
		this.dataEsatta.setDefaultValue(null);
		this.dataEsatta.setInterval(false);
		this.dataEsatta.setLabel("fattura.search.dataEsatta");
		this.dataEsatta.setPattern("dd/M/yyyy");
		
		// imposto i valori di default per le date
		_setPeriodo();

		this.tipoDocumento = new SelectListField();
		this.tipoDocumento.setName("tipoDocumento");
		this.tipoDocumento.setValue(null);
		this.tipoDocumento.setLabel("fattura.search.tipoDocumento");
		
		this.notificaEsitoCommittente = new SelectListField();
		this.notificaEsitoCommittente.setName("notificaEsitoCommittente");
		this.notificaEsitoCommittente.setValue(null);
		this.notificaEsitoCommittente.setLabel("fattura.search.notificaEC");

		this.notificaDecorrenzaTermini = new SelectListField();
		this.notificaDecorrenzaTermini.setName("notificaDecorrenzaTermini");
		this.notificaDecorrenzaTermini.setValue(null);
		this.notificaDecorrenzaTermini.setLabel("fattura.search.notificaDT");
		
		this.numero = new TextField();
		this.numero.setName("numero");
		this.numero.setValue(null);
		this.numero.setLabel("fattura.search.numero");
		this.numero.setType("textWithSuggestion");
		this.numero.setAutoComplete(true);
		this.numero.setEnableManualInput(true);
		this.numero.setFieldsToUpdate(this.getIdForm() + "_formPnl");
		this.numero.setForm(this);
		
		this.identificativoLotto = new TextField();
		this.identificativoLotto.setName("identificativoLotto");
		this.identificativoLotto.setValue(null);
		this.identificativoLotto.setLabel("fattura.search.identificativoLotto");

		reset();
	}

	public FormField<String> getIdentificativoLotto() {
		return identificativoLotto;
	}

	public void setIdentificativoLotto(FormField<String> identificativoLotto) {
		this.identificativoLotto = identificativoLotto;
	}

	@Override
	public void reset() {
		resetParametriPaginazione();

		// reset search fields
		this.cedentePrestatore.reset();
		this.dipartimento.reset();
		this.dataRicezionePeriodo.reset();
		this.dataRicezione.reset();
		_setPeriodo();
		this.tipoDocumento.reset();
		this.notificaEsitoCommittente.reset();
		this.notificaDecorrenzaTermini.reset();
		
		this.identificativoLotto.reset();
		this.dataEsatta.reset(); 
		this.numero.reset();

	}

	
	
	public FormField<Date> getDataEsatta() {
		return dataEsatta;
	}

	public void setDataEsatta(FormField<Date> dataEsatta) {
		this.dataEsatta = dataEsatta;
	}

	public FormField<String> getNumero() {
		return numero;
	}

	public void setNumero(FormField<String> numero) {
		this.numero = numero;
	}

	public FatturaElettronicaMBean getmBean() {
		return mBean;
	}

	public void setmBean(FatturaElettronicaMBean mBean) {
		this.mBean = mBean;
	}

	public FormField<String> getCedentePrestatore() {
		return cedentePrestatore;
	}

	public void setCedentePrestatore(FormField<String> cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public SelectListField getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(SelectListField dipartimento) {
		this.dipartimento = dipartimento;
	}

	public FormField<SelectItem> getDataRicezionePeriodo() {
		return dataRicezionePeriodo;
	}

	public void setDataRicezionePeriodo(FormField<SelectItem> dataRicezionePeriodo) {
		this.dataRicezionePeriodo = dataRicezionePeriodo;
	}

	public FormField<Date> getDataRicezione() {
		boolean rendered = (this.getDataRicezionePeriodo().getValue() != null && this.getDataRicezionePeriodo().getValue().getValue()
				.equals(DATA_RICEZIONE_PERIODO_PERSONALIZZATO)); //Utils.getMessageFromResourceBundle("fattura.search.dataRicezione.personalizzato")));

		this.dataRicezione.setRendered(rendered);

		return dataRicezione;
	}

	public void setDataRicezione(FormField<Date> dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public FormField<SelectItem> getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(FormField<SelectItem> tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public FormField<SelectItem> getNotificaEsitoCommittente() {
		return notificaEsitoCommittente;
	}

	public void setNotificaEsitoCommittente(
			FormField<SelectItem> notificaEsitoCommittente) {
		this.notificaEsitoCommittente = notificaEsitoCommittente;
	}

	public FormField<SelectItem> getNotificaDecorrenzaTermini() {
		return notificaDecorrenzaTermini;
	}

	public void setNotificaDecorrenzaTermini(
			FormField<SelectItem> notificaDecorrenzaTermini) {
		this.notificaDecorrenzaTermini = notificaDecorrenzaTermini;
	}

	public List<SelectItem> cedentePrestatoreAutoComplete(Object val){
		return this.mBean.cedentePrestatoreAutoComplete(val);
	}
	
	public void dataRicezionePeriodoSelectListener(ActionEvent ae){
		_setPeriodo();
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
		
		String periodo = this.getDataRicezionePeriodo().getValue() != null ? this.getDataRicezionePeriodo().getValue().getValue() : DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA ;

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.clear(Calendar.SECOND);
		today.clear(Calendar.MILLISECOND);

		//ultima settimana
		if (DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA.equals(periodo)) {
			Calendar lastWeek = (Calendar) today.clone();
			Calendar c = Calendar.getInstance();
			dataFine = c.getTime();
			lastWeek.set(Calendar.HOUR_OF_DAY, 0);
			lastWeek.set(Calendar.MINUTE, 0);
			lastWeek.add(Calendar.DATE, -7);
			dataInizio = lastWeek.getTime();
		} else if (DATA_RICEZIONE_PERIODO_ULTIMO_MESE.equals( periodo)) {
			Calendar lastMonth = (Calendar) today.clone();

			// prendo la data corrente
			dataFine = Calendar.getInstance().getTime();

			// la data inizio rimane uguale sia per giornaliero che per orario
			lastMonth.set(Calendar.HOUR_OF_DAY, 0);
			lastMonth.set(Calendar.MINUTE, 0);
			lastMonth.add(Calendar.DATE, -30);
			dataInizio = lastMonth.getTime();
		} else if (DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI.equals( periodo)) {
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



}
