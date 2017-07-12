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
package org.govmix.proxy.fatturapa.web.console.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.govmix.proxy.fatturapa.web.console.mbean.FatturaElettronicaMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.TextImpl;
import org.openspcoop2.generic_project.web.input.DateTime;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

/**
 * FatturaElettronicaSearchForm Form di ricerca delle Fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
@SessionScoped @ManagedBean(name="fatturaElettronicaSearchForm")
public class FatturaElettronicaSearchForm extends BaseSearchForm implements SearchForm, Cloneable{

	public static final String DATA_RICEZIONE_PERIODO_PERSONALIZZATO = "3";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI = "2";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMO_MESE = "1";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA = "0";
	
	private Text cedentePrestatore = null;
	private List<SelectItem> cedPrestSelList = null;
	private SelectList dipartimento = null;
	private SelectList dataRicezionePeriodo = null;
	private DateTime dataRicezione = null;
	private SelectList tipoDocumento = null;
	private SelectList notificaEsitoCommittente = null;
	private SelectList notificaDecorrenzaTermini = null;
	private FatturaElettronicaMBean mBean = null;
	private DateTime dataEsatta = null;
	private Text numero = null;
	private Text identificativoLotto = null;
	

	public FatturaElettronicaSearchForm()throws Exception{
		init();
	}
	
	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getFactory();
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
		this.cedentePrestatore.setStyleClass("inputDefaultWidth");
		this.cedentePrestatore.setWidth(412);
		((TextImpl)this.cedentePrestatore).setSelectItemsWidth(412);
		((TextImpl)this.cedentePrestatore).setExecute("@this");

		this.dipartimento = factory.getInputFieldFactory().createSelectList("dipartimento","fattura.search.dipartimento",null,false);
		((SelectListImpl)this.dipartimento).setCheckItemWidth(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		this.dipartimento.setStyleClass("inputDefaultWidth");
		this.dipartimento.setWidth(412);

		//NOTA: Modificato a seguito della CR 80
		this.dataRicezionePeriodo = factory.getInputFieldFactory().createSelectList("dataRicezionePeriodo","fattura.search.dataRicezione",new SelectItem(DATA_RICEZIONE_PERIODO_ULTIMO_MESE,"fattura.search.dataRicezione.ultimoMese"),false);

		this.dataRicezionePeriodo.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.dataRicezionePeriodo.setForm(this);
		this.dataRicezionePeriodo.setStyleClass("inputDefaultWidth");
		this.dataRicezionePeriodo.setWidth(412);
		((SelectListImpl)this.dataRicezionePeriodo).setSelectItemsWidth(412);
		((SelectListImpl)this.dataRicezionePeriodo).setExecute("@this");

		this.dataRicezione = factory.getInputFieldFactory().createDateTimeInterval("dataRicezione","fattura.search.dataRicezione.personalizzato","dd/M/yyyy",null,null,false);
		this.dataEsatta = factory.getInputFieldFactory().createDateTime("dataEsatta","fattura.search.dataEsatta","dd/M/yyyy",null,false);
		
		// imposto i valori di default per le date
		_setPeriodo();

		this.tipoDocumento = factory.getInputFieldFactory().createSelectList("tipoDocumento","fattura.search.tipoDocumento",null,false);
		this.tipoDocumento.setStyleClass("inputDefaultWidth");
		this.tipoDocumento.setWidth(412);
		this.notificaEsitoCommittente = factory.getInputFieldFactory().createSelectList("notificaEsitoCommittente","fattura.search.notificaEC",null,false);
		this.notificaEsitoCommittente.setStyleClass("inputDefaultWidth");
		this.notificaEsitoCommittente.setWidth(412);
		((SelectListImpl)this.notificaEsitoCommittente).setSelectItemsWidth(412);
		this.notificaDecorrenzaTermini = factory.getInputFieldFactory().createSelectList( "notificaDecorrenzaTermini","fattura.search.notificaDT",null,false);
		this.notificaDecorrenzaTermini.setStyleClass("inputDefaultWidth");
		this.notificaDecorrenzaTermini.setWidth(412);
		((SelectListImpl)this.notificaDecorrenzaTermini).setSelectItemsWidth(412);
		this.numero = factory.getInputFieldFactory().createText("numero","fattura.search.numero",null,false);
		
		this.numero.setAutoComplete(true);
		this.numero.setEnableManualInput(true);
		this.numero.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.numero.setForm(this);
		this.numero.setStyleClass("inputDefaultWidth");
		this.numero.setWidth(412);
		
		this.identificativoLotto = factory.getInputFieldFactory().createText("identificativoLotto","fattura.search.identificativoLotto",null,false);
		this.identificativoLotto.setStyleClass("inputDefaultWidth");
		this.identificativoLotto.setWidth(412);
		
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
		

		reset();
	}

	public Text getIdentificativoLotto() {
		return this.identificativoLotto;
	}

	public void setIdentificativoLotto(Text identificativoLotto) {
		this.identificativoLotto = identificativoLotto;
	}

	@Override
	public void reset() {
		resetParametriPaginazione();

		// reset search fields
		this.cedentePrestatore.reset();
		this.setCedPrestSelList(new ArrayList<SelectItem>());
		
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

	public SelectList  getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(SelectList  dipartimento) {
		this.dipartimento = dipartimento;
	}

	public SelectList  getDataRicezionePeriodo() {
		return this.dataRicezionePeriodo;
	}

	public void setDataRicezionePeriodo(SelectList dataRicezionePeriodo) {
		this.dataRicezionePeriodo = dataRicezionePeriodo;
	}

	public DateTime getDataRicezione() {
		return this.dataRicezione;
	}

	public void setDataRicezione(DateTime dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public SelectList getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(SelectList tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public SelectList getNotificaEsitoCommittente() {
		return this.notificaEsitoCommittente;
	}

	public void setNotificaEsitoCommittente(
			SelectList notificaEsitoCommittente) {
		this.notificaEsitoCommittente = notificaEsitoCommittente;
	}

	public SelectList getNotificaDecorrenzaTermini() {
		return this.notificaDecorrenzaTermini;
	}

	public void setNotificaDecorrenzaTermini(
			SelectList notificaDecorrenzaTermini) {
		this.notificaDecorrenzaTermini = notificaDecorrenzaTermini;
	}

	public List<SelectItem> cedentePrestatoreAutoComplete(Object val){
		this.setCedPrestSelList(this.mBean.cedentePrestatoreAutoComplete(val));
		
		return getCedPrestSelList();
	}
	
	public void dataRicezionePeriodoValueChanged(ValueChangeEvent event){
		Object newValueObj = event.getNewValue();

		if(newValueObj instanceof org.openspcoop2.generic_project.web.input.SelectItem ){
			org.openspcoop2.generic_project.web.input.SelectItem newValue = (org.openspcoop2.generic_project.web.input.SelectItem) newValueObj;
			this.getDataRicezionePeriodo().setValue(newValue); 
		}
		_setPeriodo();
	}
	
	public void cedentePrestatoreValueChanged(ValueChangeEvent event){
		//do something
	}
	
	public List<SelectItem> numeroAutoComplete(Object val){
		return this.mBean.numeroAutoComplete(val);
	}
	
	public void numeroValueChanged(ValueChangeEvent event){
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
		
		boolean rendered = (this.getDataRicezionePeriodo().getValue() != null && this.getDataRicezionePeriodo().getValue().getValue()
				.equals(DATA_RICEZIONE_PERIODO_PERSONALIZZATO)); //Utils.getMessageFromResourceBundle("fattura.search.dataRicezione.personalizzato")));

		this.getDataRicezione().setRendered(rendered);

		//aggiorno i valori del campo
		this.getDataRicezione().setValue(dataInizio);
		this.getDataRicezione().setValue2(dataFine);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public List<SelectItem> getCedPrestSelList() {
		return this.cedPrestSelList;
	}

	public void setCedPrestSelList(List<SelectItem> cedPrestSelList) {
		this.cedPrestSelList = cedPrestSelList;
	}

	@Override
	public String valida() throws Exception {
		return null;
	}
}
