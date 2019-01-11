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
package org.govmix.proxy.fatturapa.web.console.pcc.bean;

import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.OutputNumber;
import org.openspcoop2.generic_project.web.output.Text;

public class ScadenzaPccBean  extends BaseBean<PccScadenza, Long> implements IBean<PccScadenza, Long>{

	private OutputNumber importo = null;
	private DateTime dataScadenza = null;
	private EsitoPccBean esito;
//	private Text stato;
	private DateTime dataRichiesta;
	private DateTime dataQuery;
	private Text sistemaRichiedente;
	private Text utenteRichiedente;
	private boolean editabile = false;

	
		// Gruppo Informazioni Dati Genareli
		private OutputGroup fieldsDatiGenerali = null;



	public ScadenzaPccBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{

		this.importo = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("importo","scadenza.importo");
		this.importo.setConverterType(Costanti.CONVERT_TYPE_CURRENCY);
		this.importo.setCurrencySymbol(Costanti.CURRENCY_SYMBOL_EURO);
		this.importo.setTableColumnStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_ALLINATO_DX);

		this.dataScadenza = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataScadenza","scadenza.dataScadenza",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY);
		this.dataRichiesta = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataRichiesta","scadenza.dataRichiesta",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.dataQuery = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataQuery","scadenza.dataQuery",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
//		this.stato = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("stato","scadenza.stato");
		this.sistemaRichiedente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("sistemaRichiedente","scadenza.sistemaRichiedente");
		this.utenteRichiedente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("utenteRichiedente","scadenza.utenteRichiedente");
		
		this.setField(this.importo);
		this.setField(this.dataScadenza);
//		this.setField(this.stato);
		this.setField(this.dataRichiesta);
		this.setField(this.dataQuery);
		this.setField(this.sistemaRichiedente);
		this.setField(this.utenteRichiedente);

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.importo);
		this.fieldsDatiGenerali.addField(this.dataScadenza);
//		this.fieldsDatiGenerali.addField(this.stato);
		this.fieldsDatiGenerali.addField(this.dataRichiesta);
		this.fieldsDatiGenerali.addField(this.dataQuery);

		this.fieldsDatiGenerali.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiGenerali.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);
		
		this.fieldsDatiGenerali.setField(this.importo);
		this.fieldsDatiGenerali.setField(this.dataScadenza);
//		this.fieldsDatiGenerali.setField(this.stato);
		this.fieldsDatiGenerali.setField(this.dataRichiesta);
		this.fieldsDatiGenerali.setField(this.dataQuery);
		this.fieldsDatiGenerali.addField(this.sistemaRichiedente);
		this.fieldsDatiGenerali.addField(this.utenteRichiedente);
	}
	@Override
	public Long getId() {
		return this.getDTO().getId();
	}

	@Override
	public void setDTO(PccScadenza dto) {
		super.setDTO(dto);
		
		this.importo.setValue(this.getDTO().getImportoInScadenza());
		this.dataScadenza.setValue(this.getDTO().getDataScadenza());
		this.dataRichiesta.setValue(this.getDTO().getDataRichiesta());
		this.dataQuery.setValue(this.getDTO().getDataQuery());
		this.sistemaRichiedente.setValue(this.getDTO().getSistemaRichiedente());
		this.utenteRichiedente.setValue(this.getDTO().getUtenteRichiedente());
		
		// se pagatoRicontabilizzato > 0 la scadenza non e' editabile
		if(this.getDTO().getPagatoRicontabilizzato() != null && this.getDTO().getPagatoRicontabilizzato() > 0)
			this.editabile = false;
		else 
			this.editabile = true;
	}

	public DateTime getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(DateTime dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
//
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

	public OutputNumber getImporto() {
		return this.importo;
	}

	public void setImporto(OutputNumber importo) {
		this.importo = importo;
	}

	public EsitoPccBean getEsito() {
		return esito;
	}

	public void setEsito(EsitoPccBean esito) {
		this.esito = esito;
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

	public boolean isEditabile() {
		return editabile;
	}

	public void setEditabile(boolean editabile) {
		this.editabile = editabile;
	}
	

}
