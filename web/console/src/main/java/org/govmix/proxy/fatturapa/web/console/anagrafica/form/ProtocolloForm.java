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
package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.ProtocolloBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.input.Text;

public class ProtocolloForm extends BaseForm implements Form {


	private Text nome;
	private Text endpoint;
	private Text descrizione;

	public ProtocolloForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception{
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		
		this.nome = factory.getInputFieldFactory().createText("nome","protocollo.form.nome",null,true);
		this.descrizione = factory.getInputFieldFactory().createText("descrizione","protocollo.form.descrizione",null,false);
		this.endpoint = factory.getInputFieldFactory().createText("endpoint","protocollo.form.endpoint",null,true);
		
		this.setField(this.nome);
		this.setField(this.descrizione);
		this.setField(this.endpoint);

	}
	@Override
	public void reset() {
		this.nome.reset(); 
		this.descrizione.reset();
		this.endpoint.reset();

	}

	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param bean
	 */
	public void setValues(ProtocolloBean bean){
		// Aggiornamento
		if(bean != null){
			this.nome.setDefaultValue(bean.getDTO().getNome());
			this.nome.setDisabled(true);
			this.descrizione.setDefaultValue(bean.getDTO().getDescrizione());
			if(bean.getDTO().getEndpoint() != null)
				this.endpoint.setDefaultValue(bean.getDTO().getEndpoint().toString());

		} else {
			// Nuovo Elemento
			this.nome.setDefaultValue(null);
			this.nome.setDisabled(false); 
			this.descrizione.setDefaultValue(null);
			this.endpoint.setDefaultValue(null);
		}
		this.reset();
	}

	public String valida (){

		String _nome = this.nome.getValue();
		if(StringUtils.isEmpty(_nome))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.nome.getLabel());


//		String _descrizione = this.descrizione.getValue();
//		if(StringUtils.isEmpty(_descrizione))
//			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());


		String _endpoint = this.endpoint.getValue();
		if(StringUtils.isEmpty(_endpoint))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.endpoint.getLabel());

		try{
			new URI(this.endpoint.getValue());
		}catch(Exception e){
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_NON_VALIDO, this.endpoint.getLabel());
		}


		return null;
	}


	public Protocollo getProtocollo(){
		Protocollo protocollo = new Protocollo();

		protocollo.setNome(this.nome.getValue());
		protocollo.setDescrizione(this.descrizione.getValue());


		try{
			URI epUri = new URI(this.endpoint.getValue());
			protocollo.setEndpoint(epUri);
		}catch(Exception e){}

		return protocollo;
	}
	public Text getNome() {
		return this.nome;
	}
	public void setNome(Text nome) {
		this.nome = nome;
	}
	public Text getEndpoint() {
		return this.endpoint;
	}
	public void setEndpoint(Text endpoint) {
		this.endpoint = endpoint;
	}
	public Text getDescrizione() {
		return this.descrizione;
	}
	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}
	

}
