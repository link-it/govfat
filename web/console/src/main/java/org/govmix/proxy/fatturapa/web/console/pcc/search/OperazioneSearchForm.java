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
package org.govmix.proxy.fatturapa.web.console.pcc.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.pcc.mbean.OperazioneMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.DateTime;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class OperazioneSearchForm extends BaseSearchForm implements SearchForm, Cloneable{

	private SelectList<SelectItem> operazione = null;
	private Text sistemaRichiedente = null;
	private List<SelectItem> sistemaRichiedenteSelList = null;
	private Text utenteRichiedente = null;
	private List<SelectItem> utenteRichiedenteSelList = null;

	private SelectList<SelectItem> dataPeriodo = null;
	private DateTime data = null;

	private SelectList<SelectItem> esito = null;
	private Text cedentePrestatore = null;
	private List<SelectItem> cedPrestSelList = null;
	private Text numero = null;
	private Text identificativoLotto = null;
	private Text idPaTransazione = null;
	private SelectList<SelectItem> codiceErrore = null;

	private OperazioneMBean mBean = null;


	public OperazioneSearchForm()throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {

		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		// Properties del form
		this.setId("rOp");
		this.setNomeForm("operazione.label.ricercaOperazioni");
		this.setClosable(false);

		// Init dei FormField
		this.cedentePrestatore = factory.getInputFieldFactory().createText("cedPres","operazione.search.cedentePrestatoreDenominazione",null,false);

		this.cedentePrestatore.setAutoComplete(true);
		this.cedentePrestatore.setEnableManualInput(true);
		this.cedentePrestatore.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.cedentePrestatore.setForm(this);

		this.utenteRichiedente = factory.getInputFieldFactory()
				.createText("utRic","operazione.search.utenteRichiedente",null,false);

		this.utenteRichiedente.setAutoComplete(true);
		this.utenteRichiedente.setEnableManualInput(true);
		this.utenteRichiedente.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.utenteRichiedente.setForm(this);

		this.operazione = factory.getInputFieldFactory().createSelectList("oper","operazione.search.operazione",null,false);
		((SelectListImpl)this.operazione).setCheckItemWidth(true); 
		this.operazione.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		this.operazione.setDefaultValue(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi")));

		this.sistemaRichiedente = factory.getInputFieldFactory()
				.createText("sisRic","operazione.search.sistemaRichiedente",null,false);
		this.sistemaRichiedente.setAutoComplete(true);
		this.sistemaRichiedente.setEnableManualInput(true);
		this.sistemaRichiedente.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.sistemaRichiedente.setForm(this);

		this.dataPeriodo = factory.getInputFieldFactory().createSelectList("dataPeriodo","tracciaPcc.dataCreazione",new SelectItem(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMO_MESE,"operazione.search.data.ultimoMese"),false);

		this.dataPeriodo.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.dataPeriodo.setForm(this);

		this.data = factory.getInputFieldFactory().createDateTimeInterval("data","operazione.search.data.personalizzato",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY,null,null,false);

		// imposto i valori di default per le date
		this._setPeriodo();

		this.esito = factory.getInputFieldFactory().createSelectList( "esito","tracciaPcc.stato",null,false);
		this.esito.setDefaultValue(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi")));
		this.numero = factory.getInputFieldFactory().createText("numero","operazione.search.numero",null,false);

		this.numero.setAutoComplete(true);
		this.numero.setEnableManualInput(true);
		this.numero.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.numero.setForm(this);

		this.identificativoLotto = factory.getInputFieldFactory().createText("idLotto","operazione.search.identificativoLotto",null,false);

		this.idPaTransazione = factory.getInputFieldFactory().createText("idPaTrans","operazione.search.idPaTransazione",null,false);
		
		this.codiceErrore = factory.getInputFieldFactory().createSelectList("codiceErrore","operazione.search.codiceErrore",null,false);
		this.codiceErrore.setDefaultValue(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi")));

		this.setField(this.cedentePrestatore);
		this.setField(this.utenteRichiedente);
		this.setField(this.dataPeriodo);
		this.setField(this.data);
		this.setField(this.sistemaRichiedente);
		this.setField(this.esito);
		this.setField(this.operazione);
		this.setField(this.numero); 
		this.setField(this.identificativoLotto); 
		this.setField(this.idPaTransazione);
		this.setField(this.codiceErrore);


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
		this.operazione.reset();
		this.dataPeriodo.reset();
		this.data.reset();
		this._setPeriodo();
		this.utenteRichiedente.reset();
		this.sistemaRichiedente.reset();
		this.esito.reset();
		this.identificativoLotto.reset();
		this.numero.reset();
		this.idPaTransazione.reset();
		this.codiceErrore.reset();

	}

	public Text getNumero() {
		return this.numero;
	}

	public void setNumero(Text numero) {
		this.numero = numero;
	}

	public OperazioneMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(OperazioneMBean mBean) {
		this.mBean = mBean;
	}

	public Text getCedentePrestatore() {
		return this.cedentePrestatore;
	}

	public void setCedentePrestatore(Text cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public SelectList<SelectItem>  getDataPeriodo() {
		return this.dataPeriodo;
	}

	public void setDataPeriodo(SelectList<SelectItem> dataRicezionePeriodo) {
		this.dataPeriodo = dataRicezionePeriodo;
	}

	public DateTime getData() {
		boolean rendered = (this.getDataPeriodo().getValue() != null && this.getDataPeriodo().getValue().getValue()
				.equals(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_PERSONALIZZATO)); //Utils.getMessageFromResourceBundle("operazione.search.dataRicezione.personalizzato")));

		this.data.setRendered(rendered);

		return this.data;
	}

	public void setData(DateTime dataRicezione) {
		this.data = dataRicezione;
	}



	public SelectList<SelectItem> getOperazione() {
		return operazione;
	}

	public void setOperazione(SelectList<SelectItem> operazione) {
		this.operazione = operazione;
	}

	public Text getSistemaRichiedente() {
		return sistemaRichiedente;
	}

	public void setSistemaRichiedente(Text sistemaRichiedente) {
		this.sistemaRichiedente = sistemaRichiedente;
	}

	public Text getUtenteRichiedente() {
		return utenteRichiedente;
	}

	public void setUtenteRichiedente(Text utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}

	public SelectList<SelectItem> getEsito() {
		return esito;
	}

	public void setEsito(SelectList<SelectItem> esito) {
		this.esito = esito;
	}

	public List<SelectItem> cedPresAutoComplete(Object val){


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

	public List<SelectItem> utRicAutoComplete(Object val){
		this.utenteRichiedenteSelList =  this.mBean.utenteRichiedenteAutoComplete(val);

		List<SelectItem> listToRet = new ArrayList<SelectItem>();

		List<String> app = new ArrayList<String>();
		for (SelectItem selectItem : this.utenteRichiedenteSelList) {
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

	public List<SelectItem> sisRicAutoComplete(Object val){
		this.sistemaRichiedenteSelList =  this.mBean.sistemaRichiedenteAutoComplete(val);

		List<SelectItem> listToRet = new ArrayList<SelectItem>();

		List<String> app = new ArrayList<String>();
		for (SelectItem selectItem : this.sistemaRichiedenteSelList) {
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

	public void dataPeriodoSelectListener(ActionEvent ae){
		this._setPeriodo();
	}

	public void cedPreSelectListener(ActionEvent ae){
		//do something
	}

	public List<SelectItem> numeroAutoComplete(Object val){
		return this.mBean.numeroAutoComplete(val);
	}

	public void numeroSelectListener(ActionEvent ae){
		//do something
	}

	public void sisRicSelectListener(ActionEvent ae){
		//do something
	}

	public void utRicSelectListener(ActionEvent ae){
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
		Date dataInizio = this.getData().getValue();
		Date dataFine = this.getData().getValue2();

		String periodo = this.getDataPeriodo().getValue() != null ? this.getDataPeriodo().getValue().getValue() : 
			org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMA_SETTIMANA ;

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.clear(Calendar.SECOND);
		today.clear(Calendar.MILLISECOND);

		//ultima settimana
		if (org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMA_SETTIMANA.equals(periodo)) {
			Calendar lastWeek = (Calendar) today.clone();
			Calendar c = Calendar.getInstance();
			dataFine = c.getTime();
			lastWeek.set(Calendar.HOUR_OF_DAY, 0);
			lastWeek.set(Calendar.MINUTE, 0);
			lastWeek.add(Calendar.DATE, -7);
			dataInizio = lastWeek.getTime();
		} else if (org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMO_MESE.equals( periodo)) {
			Calendar lastMonth = (Calendar) today.clone();

			// prendo la data corrente
			dataFine = Calendar.getInstance().getTime();

			// la data inizio rimane uguale sia per giornaliero che per orario
			lastMonth.set(Calendar.HOUR_OF_DAY, 0);
			lastMonth.set(Calendar.MINUTE, 0);
			lastMonth.add(Calendar.DATE, -30);
			dataInizio = lastMonth.getTime();
		} else if (org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_PERIODO_ULTIMI_TRE_MESI.equals( periodo)) {
			Calendar lastyear = (Calendar) today.clone();

			dataFine = Calendar.getInstance().getTime();

			lastyear.set(Calendar.HOUR_OF_DAY, 0);
			lastyear.set(Calendar.MINUTE, 0);
			lastyear.add(Calendar.DATE, -90);
			dataInizio = lastyear.getTime();
		} else {

			// personalizzato
			dataInizio = this.getData().getValue();
			dataFine = this.getData().getValue2();
		}

		//aggiorno i valori del campo
		this.getData().setValue(dataInizio);
		this.getData().setValue2(dataFine);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


	public void initSearchListener(ActionEvent ae){
		this.setPageSize(25);
		this.setLimit(25); 
		//this.reset();
	}

	public List<SelectItem> getSistemaRichiedenteSelList() {
		return sistemaRichiedenteSelList;
	}

	public void setSistemaRichiedenteSelList(List<SelectItem> sistemaRichiedenteSelList) {
		this.sistemaRichiedenteSelList = sistemaRichiedenteSelList;
	}

	public List<SelectItem> getUtenteRichiedenteSelList() {
		return utenteRichiedenteSelList;
	}

	public void setUtenteRichiedenteSelList(List<SelectItem> utenteRichiedenteSelList) {
		this.utenteRichiedenteSelList = utenteRichiedenteSelList;
	}

	public List<SelectItem> getCedPrestSelList() {
		return cedPrestSelList;
	}

	public void setCedPrestSelList(List<SelectItem> cedPrestSelList) {
		this.cedPrestSelList = cedPrestSelList;
	}

	public Text getIdPaTransazione() {
		return idPaTransazione;
	}

	public void setIdPaTransazione(Text idPaTransazione) {
		this.idPaTransazione = idPaTransazione;
	}

	public SelectList<SelectItem> getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(SelectList<SelectItem> codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
}
