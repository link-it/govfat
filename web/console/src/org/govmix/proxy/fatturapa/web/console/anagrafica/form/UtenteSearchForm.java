package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.UtenteMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectListField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;
import org.openspcoop2.generic_project.web.input.FieldType;

public class UtenteSearchForm extends SearchForm{
	
	
	private FormField<String> denominazione = null;
	
	private SelectListField dipartimento = null;
	
	private UtenteMBean mBean = null;

	public UtenteSearchForm(){
		init();
	}
	
	@Override
	protected void init() {
		// Properties del form
		this.setIdForm("formUtente");
		this.setNomeForm("utente.label.ricercaUtenti");
		this.setClosable(false);

		// Init dei FormField
		this.denominazione = new TextField();
		this.denominazione.setType(FieldType.TEXT_WITH_SUGGESTION);
		this.denominazione.setName("denominazione");
		this.denominazione.setDefaultValue(null);
		this.denominazione.setLabel("utente.search.denominazione");
		this.denominazione.setAutoComplete(true);
		this.denominazione.setEnableManualInput(true);
		this.denominazione.setFieldsToUpdate(this.getIdForm() + "_formPnl");
		this.denominazione.setForm(this);

		this.dipartimento = new SelectListField();
		this.dipartimento.setName("dipartimento");
		this.dipartimento.setValue(null);
		this.dipartimento.setLabel("utente.search.dipartimento");
		this.dipartimento.setCheckItemWidth(true); 
		
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.denominazione.reset();
		this.dipartimento.reset();
	}
	
	public FormField<String> getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(FormField<String> denominazione) {
		this.denominazione = denominazione;
	}

	public SelectListField getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(SelectListField dipartimento) {
		this.dipartimento = dipartimento;
	}

	public UtenteMBean getmBean() {
		return mBean;
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
}
