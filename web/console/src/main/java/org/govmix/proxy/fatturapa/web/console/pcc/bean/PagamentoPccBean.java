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
package org.govmix.proxy.fatturapa.web.console.pcc.bean;

import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.OutputNumber;
import org.openspcoop2.generic_project.web.output.Text;

public class PagamentoPccBean extends BaseBean<PccPagamento, Long> implements IBean<PccPagamento, Long>{ 

	private OutputNumber importoPagato;
	private Text  naturaSpesa;
	private Text capitoliSpesa;
	private Text estremiImpegno;
	private Text numeroMandato;
	private DateTime dataMandato;
	private Text idFiscaleIvaBeneficiario;
	private Text codiceCig;
	private Text codiceCup;
	private Text descrizione;
	private DateTime dataRichiesta;
	private EsitoPccBean esito;
	private DateTime dataQuery;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	public PagamentoPccBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.importoPagato = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("importoPagato","pagamentoPcc.importoPagato");
		this.importoPagato.setConverterType(Costanti.CONVERT_TYPE_CURRENCY);
		this.importoPagato.setCurrencySymbol(Costanti.CURRENCY_SYMBOL_EURO);
		this.importoPagato.setTableColumnStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_ALLINATO_DX);
		
		this.naturaSpesa = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("naturaSpesa","pagamentoPcc.naturaSpesa");
		this.capitoliSpesa = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("capitoliSpesa","pagamentoPcc.capitoliSpesa");
		this.estremiImpegno = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("estremiImpegno","pagamentoPcc.estremiImpegno");
		this.numeroMandato = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("numeroMandato","pagamentoPcc.numeroMandato");
		this.dataMandato = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataMandato","pagamentoPcc.dataMandato",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.idFiscaleIvaBeneficiario = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idFiscaleIvaBeneficiario","pagamentoPcc.idFiscaleIvaBeneficiario");
		this.codiceCig = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceCig","pagamentoPcc.codiceCig"); 
		this.codiceCup = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceCup","pagamentoPcc.codiceCup");
		this.descrizione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("descrizione","pagamentoPcc.descrizione");
//		this.stato = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("stato","pagamentoPcc.stato");
		this.dataRichiesta = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataRichiesta","pagamentoPcc.dataRichiesta",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.dataQuery = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataQuery","pagamentoPcc.dataQuery",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS); 

		this.setField(this.importoPagato);
		this.setField(this.naturaSpesa);
		this.setField(this.capitoliSpesa);
		this.setField(this.estremiImpegno);
		this.setField(this.numeroMandato);
		this.setField(this.dataMandato);
		this.setField(this.idFiscaleIvaBeneficiario);
		this.setField(this.codiceCig);
		this.setField(this.codiceCup);
		this.setField(this.descrizione);
//		this.setField(this.stato);
		this.setField(this.dataRichiesta);
		this.setField(this.dataQuery);

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.importoPagato);
		this.fieldsDatiGenerali.addField(this.naturaSpesa);
		this.fieldsDatiGenerali.addField(this.capitoliSpesa);
		this.fieldsDatiGenerali.addField(this.estremiImpegno);
		this.fieldsDatiGenerali.addField(this.numeroMandato);
		this.fieldsDatiGenerali.addField(this.dataMandato);
		this.fieldsDatiGenerali.addField(this.idFiscaleIvaBeneficiario);
		this.fieldsDatiGenerali.addField(this.codiceCig);
		this.fieldsDatiGenerali.addField(this.codiceCup);
		this.fieldsDatiGenerali.addField(this.descrizione);
//		this.fieldsDatiGenerali.addField(this.stato);
		this.fieldsDatiGenerali.addField(this.dataRichiesta);
		this.fieldsDatiGenerali.addField(this.dataQuery);

		this.fieldsDatiGenerali.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiGenerali.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(PccPagamento dto) {
		super.setDTO(dto);

		this.importoPagato.setValue(this.getDTO().getImportoPagato());
		NaturaSpesaType naturaSpesa2 = this.getDTO().getNaturaSpesa();
		if(naturaSpesa2 != null){
			String val = "pccNaturaSpesa." + naturaSpesa2.getValue();
			this.naturaSpesa.setValue(val);
		}
		this.capitoliSpesa.setValue(this.getDTO().getCapitoliSpesa());
		this.estremiImpegno.setValue(this.getDTO().getEstremiImpegno());
		this.numeroMandato.setValue(this.getDTO().getNumeroMandato());
		this.dataMandato.setValue(this.getDTO().getDataMandato());
		this.idFiscaleIvaBeneficiario.setValue(this.getDTO().getIdFiscaleIvaBeneficiario());
		this.codiceCig.setValue(this.getDTO().getCodiceCig());
		this.codiceCup.setValue(this.getDTO().getCodiceCup());
		this.descrizione.setValue(this.getDTO().getDescrizione());

		this.dataRichiesta.setValue(this.getDTO().getDataRichiesta());
		this.dataQuery.setValue(this.getDTO().getDataQuery());

	}

	public OutputNumber getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(OutputNumber importoPagato) {
		this.importoPagato = importoPagato;
	}

	public Text getNaturaSpesa() {
		return naturaSpesa;
	}

	public void setNaturaSpesa(Text naturaSpesa) {
		this.naturaSpesa = naturaSpesa;
	}

	public Text getCapitoliSpesa() {
		return capitoliSpesa;
	}

	public void setCapitoliSpesa(Text capitoliSpesa) {
		this.capitoliSpesa = capitoliSpesa;
	}

	public Text getEstremiImpegno() {
		return estremiImpegno;
	}

	public void setEstremiImpegno(Text estremiImpegno) {
		this.estremiImpegno = estremiImpegno;
	}

	public Text getNumeroMandato() {
		return numeroMandato;
	}

	public void setNumeroMandato(Text numeroMandato) {
		this.numeroMandato = numeroMandato;
	}

	public DateTime getDataMandato() {
		return dataMandato;
	}

	public void setDataMandato(DateTime dataMandato) {
		this.dataMandato = dataMandato;
	}

	public Text getIdFiscaleIvaBeneficiario() {
		return idFiscaleIvaBeneficiario;
	}

	public void setIdFiscaleIvaBeneficiario(Text idFiscaleIvaBeneficiario) {
		this.idFiscaleIvaBeneficiario = idFiscaleIvaBeneficiario;
	}

	public Text getCodiceCig() {
		return codiceCig;
	}

	public void setCodiceCig(Text codiceCig) {
		this.codiceCig = codiceCig;
	}

	public Text getCodiceCup() {
		return codiceCup;
	}

	public void setCodiceCup(Text codiceCup) {
		this.codiceCup = codiceCup;
	}

	public Text getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

//	public Text getStato() {
//		return stato;
//	}
//
//	public void setStato(Text stato) {
//		this.stato = stato;
//	}

	public DateTime getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(DateTime dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public EsitoPccBean getEsito() {
		return esito;
	}

	public void setEsito(EsitoPccBean esito) {
		this.esito = esito;
	}

	public DateTime getDataQuery() {
		return dataQuery;
	}

	public void setDataQuery(DateTime dataQuery) {
		this.dataQuery = dataQuery;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}


}
