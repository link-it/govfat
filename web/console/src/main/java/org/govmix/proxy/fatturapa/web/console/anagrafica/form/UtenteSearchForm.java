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

import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.UtenteMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class UtenteSearchForm extends BaseSearchForm implements SearchForm{


	private Text denominazione = null;

	private SelectList<SelectItem> dipartimento = null;

	private SelectList<SelectItem> ente = null;

	private UtenteMBean mBean = null;

	public UtenteSearchForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {
		// Properties del form
		this.setId("fUti");
		this.setNomeForm("utente.label.ricercaUtenti");
		this.setClosable(false);

		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();

		// Init dei FormField
		this.denominazione = factory.getInputFieldFactory().createText("denominazione","utente.search.denominazione",null,false);

		this.denominazione.setAutoComplete(true);
		this.denominazione.setEnableManualInput(true);
		this.denominazione.setFieldsToUpdate(this.getId() + "_searchPnl");
		this.denominazione.setForm(this);

		this.dipartimento = factory.getInputFieldFactory().createSelectList("dipartimento","utente.search.dipartimento",null,false);
		((SelectListImpl)this.dipartimento).setCheckItemWidth(true); 

		this.ente = factory.getInputFieldFactory().createSelectList("ente","utente.search.ente",null,false);
		((SelectListImpl)this.ente).setCheckItemWidth(true);
		this.ente.setFieldsToUpdate(this.getId() + "_searchPnl");
		this.ente.setForm(this);

		this.setField(this.ente);
		this.setField(this.dipartimento);
		this.setField(this.denominazione);

	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.denominazione.reset();
		this.dipartimento.reset();
		this.ente.reset();
	}

	public Text getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(Text denominazione) {
		this.denominazione = denominazione;
	}

	public SelectList<SelectItem> getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(SelectList<SelectItem> dipartimento) {
		this.dipartimento = dipartimento;
	}

	public UtenteMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(UtenteMBean mBean) {
		this.mBean = mBean;
	}

	public List<SelectItem> denominazioneAutoComplete(Object val){
		return this.mBean.denominazioneAutoComplete(val);
	}

	public void denominazioneSelectListener(ActionEvent ae){
		//do something
	}

	public SelectList<SelectItem> getEnte() {
		return ente;
	}

	public void setEnte(SelectList<SelectItem> ente) {
		this.ente = ente;
	}

	public void enteSelectListener(ActionEvent ae){
		SelectItem value = this.ente.getValue();
		String value2 = null;
		if(value!= null){
			value2 = value.getValue();
		}

		((SelectListImpl)this.dipartimento).setElencoSelectItems(this.mBean.getListaDipartimentiEnte(value2));
		
	}

}
