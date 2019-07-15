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

import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.EnteMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.input.Text;

public class EnteSearchForm extends BaseSearchForm implements SearchForm {
	
	
	private Text nome = null;
	private Text descrizione = null;
	
	private EnteMBean mBean = null;
	
	public EnteSearchForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {
		this.setId("fEnti");
		this.setNomeForm(Utils.getInstance().getMessageFromResourceBundle("ente.label.ricercaEnti"));
		this.setClosable(false);
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		
		this.nome = factory.getInputFieldFactory().createText("nome","ente.search.nome",null,false);
		this.nome.setAutoComplete(true);
		this.nome.setEnableManualInput(true);
		this.nome.setFieldsToUpdate(this.getId() + "_searchPnl");
		this.nome.setForm(this);
		
		this.descrizione = factory.getInputFieldFactory().createText("descrizione","ente.search.descrizione",null,false);
		this.descrizione.setAutoComplete(true);
		this.descrizione.setEnableManualInput(true);
		this.descrizione.setFieldsToUpdate(this.getId() + "_searchPnl");
		this.descrizione.setForm(this);
		
		this.setField(this.nome); 
		this.setField(this.descrizione);
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.nome.reset();
		this.descrizione.reset();
	}

	public Text getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getNome() {
		return this.nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}
	
	public EnteMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(EnteMBean mBean) {
		this.mBean = mBean;
	}

	public List<SelectItem> descrizioneAutoComplete(Object val){
		return this.mBean.descrizioneAutoComplete(val);
	}

	public List<SelectItem> nomeAutoComplete(Object val){
		return this.mBean.nomeAutoComplete(val);
	}
	
	public void descrizioneSelectListener(ActionEvent ae){
		//do something
	}

	public void nomeSelectListener(ActionEvent ae){
		//do something
	}
	
	
}
