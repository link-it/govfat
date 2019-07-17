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
package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.EnteMBean;
import org.govmix.proxy.fatturapa.web.console.util.XPathUtils;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.input.Text;

public class EnteForm extends BaseForm implements Form {


	private Text nome;
	private Text descrizione;
	private Text idPccAmministrazione;
	private Text cfAuth = null;
	private Text enteVersatore = null;
	private Text strutturaVersatore = null;
	private Text nodoCodicePagamento = null;
	private Text prefissoCodicePagamento = null;
	
	private EnteMBean mbean = null;

	public EnteForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception{
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		
		this.nome = factory.getInputFieldFactory().createText("nome","ente.form.nome",null,true);
		this.descrizione = factory.getInputFieldFactory().createText("descrizione","ente.form.descrizione",null,false);
		this.idPccAmministrazione = factory.getInputFieldFactory().createText("idPccAmministrazione","ente.form.idPccAmministrazione",null,false);
		this.cfAuth = factory.getInputFieldFactory().createText("cfAuth","ente.form.cfAuth",null,false);
		this.enteVersatore = factory.getInputFieldFactory().createText("enteVersatore","ente.form.enteVersatore",null,false);
		this.strutturaVersatore = factory.getInputFieldFactory().createText("strutturaVersatore","ente.form.strutturaVersatore",null,false);
		this.nodoCodicePagamento = factory.getInputFieldFactory().createText("nodoCodicePagamento","ente.form.nodoCodicePagamento",null,false);
		this.prefissoCodicePagamento = factory.getInputFieldFactory().createText("prefissoCodicePagamento","ente.form.prefissoCodicePagamento",null,false);
		
		this.setField(this.nome);
		this.setField(this.descrizione);
		this.setField(this.idPccAmministrazione);
		this.setField(this.cfAuth);
		this.setField(this.enteVersatore);
		this.setField(this.strutturaVersatore);
		this.setField(this.nodoCodicePagamento);
		this.setField(this.prefissoCodicePagamento);
		
	}
	@Override
	public void reset() {
		this.nome.reset(); 
		this.descrizione.reset();
		this.idPccAmministrazione.reset();
		this.cfAuth.reset();
		this.enteVersatore.reset();
		this.strutturaVersatore.reset();
		this.nodoCodicePagamento.reset();
		this.prefissoCodicePagamento.reset();
	}

	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param bean
	 */
	public void setValues(EnteBean bean){
		// Aggiornamento
		if(bean != null){
			this.nome.setDefaultValue(bean.getDTO().getNome());
			this.nome.setDisabled(true);
			this.descrizione.setDefaultValue(bean.getDTO().getDescrizione());
			this.idPccAmministrazione.setDefaultValue(bean.getDTO().getIdPccAmministrazione());
			this.cfAuth.setDefaultValue(bean.getDTO().getCfAuth());
			this.enteVersatore.setDefaultValue(bean.getDTO().getEnteVersatore());
			this.strutturaVersatore.setDefaultValue(bean.getDTO().getStrutturaVersatore());
			this.nodoCodicePagamento.setDefaultValue(bean.getDTO().getNodoCodicePagamento());
			this.prefissoCodicePagamento.setDefaultValue(bean.getDTO().getPrefissoCodicePagamento());

		} else {
			// Nuovo Elemento
			this.nome.setDefaultValue(null);
			this.nome.setDisabled(false); 
			this.descrizione.setDefaultValue(null);
			this.idPccAmministrazione.setDefaultValue(null);
			this.cfAuth.setDefaultValue(null);
			this.enteVersatore.setDefaultValue(null);
			this.strutturaVersatore.setDefaultValue(null);
			this.nodoCodicePagamento.setDefaultValue(null);
			this.prefissoCodicePagamento.setDefaultValue(null);
			
		}
		this.reset();
	}

	public String valida (){

		String _nome = this.nome.getValue();
		if(StringUtils.isEmpty(_nome))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.nome.getLabel());

		String msg = this.checkLunghezzaField(this.nome, 255);
		if(msg != null)
			return msg;
		
		msg = this.checkLunghezzaField(this.descrizione,255);
		if(msg != null)
			return msg;
		
		msg = this.checkLunghezzaField(this.idPccAmministrazione,255);
		if(msg != null)
			return msg;
		
		msg = this.checkLunghezzaField(this.cfAuth,255);
		if(msg != null)
			return msg;
		
		msg = this.checkLunghezzaField(this.enteVersatore,255);
		if(msg != null)
			return msg;
		
		msg = this.checkLunghezzaField(this.strutturaVersatore,255);
		if(msg != null)
			return msg;
		
		msg = this.checkLunghezzaField(this.nodoCodicePagamento,255);
		if(msg != null)
			return msg;

		if(StringUtils.isNotBlank(this.nodoCodicePagamento.getValue())) {
			msg = XPathUtils.validaXpath(this.nodoCodicePagamento.getValue());
			if(msg != null)
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.nodoCodicePagamentoErrato", msg);
		}
		
		msg = this.checkLunghezzaField(this.prefissoCodicePagamento,255);
		if(msg != null)
			return msg;
		
		return null;
	}
	
	private String checkLunghezzaField(Text field, int length) {
		if(StringUtils.isNotBlank(field.getValue())) {
			if(field.getValue().length() > length)
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.lunghezza"+length+"Errata", field.getLabel());
		}
		return null;
	}


	public Ente getEnte(){
		Ente ente = new Ente();

		ente.setNome(this.nome.getValue());
		ente.setDescrizione(this.descrizione.getValue());
		ente.setIdPccAmministrazione(this.idPccAmministrazione.getValue());
		ente.setCfAuth(this.cfAuth.getValue());
		ente.setEnteVersatore(this.enteVersatore.getValue());
		ente.setStrutturaVersatore(this.strutturaVersatore.getValue());
		ente.setNodoCodicePagamento(this.nodoCodicePagamento.getValue());
		ente.setPrefissoCodicePagamento(this.prefissoCodicePagamento.getValue());

		return ente;
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

	public EnteMBean getMbean() {
		return mbean;
	}

	public void setMbean(EnteMBean mbean) {
		this.mbean = mbean;
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
	
	
}
