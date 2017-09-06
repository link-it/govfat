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
package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.input.Text;

public class EnteForm extends BaseForm implements Form {


	private Text nome;
	private Text idPccAmministrazione;
	private Text descrizione;

	public EnteForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception{
		
		WebGenericProjectFactory factory = this.getFactory();
		
		this.nome = factory.getInputFieldFactory().createText("nome","ente.form.nome",null,false);
		this.descrizione = factory.getInputFieldFactory().createText("descrizione","ente.form.descrizione",null,false);
		this.idPccAmministrazione = factory.getInputFieldFactory().createText("idPccAmministrazione","ente.form.idPccAmministrazione",null,false);
		
		this.setField(this.nome);
		this.setField(this.descrizione);
		this.setField(this.idPccAmministrazione);

	}
	@Override
	public void reset() {
		this.nome.reset(); 
		this.descrizione.reset();
		this.idPccAmministrazione.reset();

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

		} else {
			// Nuovo Elemento
			this.nome.setDefaultValue(null);
			this.nome.setDisabled(false); 
			this.descrizione.setDefaultValue(null);
			this.idPccAmministrazione.setDefaultValue(null);
		}
		this.reset();
	}

	public String valida (){

		String _nome = this.nome.getValue();
		if(StringUtils.isEmpty(_nome))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.nome.getLabel());


		String _descrizione = this.descrizione.getValue();
		if(StringUtils.isEmpty(_descrizione))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());


//		String _endpoint = this.idPccAmministrazione.getValue();
//		if(StringUtils.isEmpty(_endpoint))
//			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.idPccAmministrazione.getLabel());

		return null;
	}


	public Ente getEnte(){
		Ente ente = new Ente();

		ente.setNome(this.nome.getValue());
		ente.setDescrizione(this.descrizione.getValue());
		ente.setIdPccAmministrazione(this.idPccAmministrazione.getValue());

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
	
	
}
