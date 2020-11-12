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
package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import org.govmix.proxy.fatturapa.orm.Ente;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;

public class EnteBean extends BaseBean<Ente, Long> implements IBean<Ente, Long>{

	private Text nome = null;
	private Text descrizione = null;
	private Text idPccAmministrazione = null;
	private Text cfAuth = null;
	private Text enteVersatore = null;
	private Text strutturaVersatore = null;
	private Text nodoCodicePagamento = null;
	private Text prefissoCodicePagamento = null;

	private OutputGroup fieldsDatiGenerali = null;
	private OutputGroup fieldsDatiPcc = null;
	private OutputGroup fieldsDatiParer = null;
	private OutputGroup fieldsDatiPagoPA = null;

	public EnteBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.nome = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("nome","ente.nome");
		this.descrizione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("descrizione","ente.descrizione");
		this.idPccAmministrazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idPccAmministrazione","ente.idPccAmministrazione");
		this.cfAuth = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cfAuth","ente.cfAuth");
		this.enteVersatore = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("enteVersatore","ente.enteVersatore");
		this.strutturaVersatore = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("strutturaVersatore","ente.strutturaVersatore");
		this.nodoCodicePagamento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("nodoCodicePagamento","ente.nodoCodicePagamento");
		this.prefissoCodicePagamento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("prefissoCodicePagamento","ente.prefissoCodicePagamento");
		
		this.setField(this.nome);
		this.setField(this.descrizione);
		this.setField(this.idPccAmministrazione);
		this.setField(this.cfAuth);
		this.setField(this.enteVersatore);
		this.setField(this.strutturaVersatore);
		this.setField(this.prefissoCodicePagamento);
		this.setField(this.nodoCodicePagamento);
		
		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiGenerali.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);

		this.fieldsDatiGenerali.addField(this.nome);
		this.fieldsDatiGenerali.addField(this.descrizione);
		
		this.fieldsDatiPcc = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiPcc",2);
		this.fieldsDatiPcc.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiPcc.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);

		this.fieldsDatiPcc.addField(this.idPccAmministrazione);
		this.fieldsDatiPcc.addField(this.cfAuth);
		
		this.fieldsDatiParer = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("fieldsDatiParer",2);
		this.fieldsDatiParer.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiParer.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);

		this.fieldsDatiParer.addField(this.enteVersatore);
		this.fieldsDatiParer.addField(this.strutturaVersatore);
		
		this.fieldsDatiPagoPA = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("fieldsDatiPagoPA",2);
		this.fieldsDatiPagoPA.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiPagoPA.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);

		this.fieldsDatiPagoPA.addField(this.prefissoCodicePagamento);
		this.fieldsDatiPagoPA.addField(this.nodoCodicePagamento);
		
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(Ente dto) {
		super.setDTO(dto);

		this.nome.setValue(this.getDTO().getNome());
		this.descrizione.setValue(this.getDTO().getDescrizione());
		this.idPccAmministrazione.setValue(this.getDTO().getIdPccAmministrazione());
		this.cfAuth.setValue(this.getDTO().getCfAuth());
		this.enteVersatore.setValue(this.getDTO().getEnteVersatore());
		this.strutturaVersatore.setValue(this.getDTO().getStrutturaVersatore());
		this.nodoCodicePagamento.setValue(this.getDTO().getNodoCodicePagamento());
		this.prefissoCodicePagamento.setValue(this.getDTO().getPrefissoCodicePagamento());
	}

	public Text getNome() {
		return this.nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}

	public Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getIdPccAmministrazione() {
		return idPccAmministrazione;
	}

	public void setIdPccAmministrazione(Text idPccAmministrazione) {
		this.idPccAmministrazione = idPccAmministrazione;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return this.fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}

	public Text getCfAuth() {
		return cfAuth;
	}

	public void setCfAuth(Text cfAuth) {
		this.cfAuth = cfAuth;
	}

	public Text getEnteVersatore() {
		return enteVersatore;
	}

	public void setEnteVersatore(Text enteVersatore) {
		this.enteVersatore = enteVersatore;
	}

	public Text getStrutturaVersatore() {
		return strutturaVersatore;
	}

	public void setStrutturaVersatore(Text strutturaVersatore) {
		this.strutturaVersatore = strutturaVersatore;
	}

	public Text getNodoCodicePagamento() {
		return nodoCodicePagamento;
	}

	public void setNodoCodicePagamento(Text nodoCodicePagamento) {
		this.nodoCodicePagamento = nodoCodicePagamento;
	}

	public Text getPrefissoCodicePagamento() {
		return prefissoCodicePagamento;
	}

	public void setPrefissoCodicePagamento(Text prefissoCodicePagamento) {
		this.prefissoCodicePagamento = prefissoCodicePagamento;
	}

	public OutputGroup getFieldsDatiPcc() {
		return fieldsDatiPcc;
	}

	public void setFieldsDatiPcc(OutputGroup fieldsDatiPcc) {
		this.fieldsDatiPcc = fieldsDatiPcc;
	}

	public OutputGroup getFieldsDatiParer() {
		return fieldsDatiParer;
	}

	public void setFieldsDatiParer(OutputGroup fieldsDatiParer) {
		this.fieldsDatiParer = fieldsDatiParer;
	}

	public OutputGroup getFieldsDatiPagoPA() {
		return fieldsDatiPagoPA;
	}

	public void setFieldsDatiPagoPA(OutputGroup fieldsDatiPagoPA) {
		this.fieldsDatiPagoPA = fieldsDatiPagoPA;
	}
	
	

}
