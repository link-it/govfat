package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.DipartimentoMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.form.SearchForm;
import org.openspcoop2.generic_project.web.input.FieldType;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

public class DipartimentoSearchForm extends SearchForm{
	
	private FormField<String> descrizione = null;
	
	private FormField<String> codice = null;

	private DipartimentoMBean mBean = null;
	
	public DipartimentoSearchForm(){
		init();
	}
	
	@Override
	protected void init() {
		this.setIdForm("formDipartimenti");
		this.setNomeForm(Utils.getInstance().getMessageFromResourceBundle("dipartimento.label.ricercaDipartimenti"));
		this.setClosable(false);
		// Init dei FormField
		this.descrizione = new TextField();
		this.descrizione.setType(FieldType.TEXT_WITH_SUGGESTION);
		this.descrizione.setName("descrizione");
		this.descrizione.setDefaultValue(null);
		this.descrizione.setLabel("dipartimento.search.descrizione");
		this.descrizione.setAutoComplete(true);
		this.descrizione.setEnableManualInput(true);
		this.descrizione.setFieldsToUpdate(this.getIdForm() + "_formPnl");
		this.descrizione.setForm(this);
		
		this.codice = new TextField();
		this.codice.setType(FieldType.TEXT_WITH_SUGGESTION);
		this.codice.setName("codice");
		this.codice.setDefaultValue(null);
		this.codice.setLabel("dipartimento.search.codice");
		this.codice.setAutoComplete(true);
		this.codice.setEnableManualInput(true);
		this.codice.setFieldsToUpdate(this.getIdForm() + "_formPnl");
		this.codice.setForm(this);
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.descrizione.reset();
		this.codice.reset();
	}

	public FormField<String> getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(FormField<String> descrizione) {
		this.descrizione = descrizione;
	}
	
	public DipartimentoMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(DipartimentoMBean mBean) {
		this.mBean = mBean;
	}

	public List<SelectItem> descrizioneAutoComplete(Object val){
		return this.mBean.descrizioneAutoComplete(val);
	}
	
	public List<SelectItem> codiceAutoComplete(Object val){
		return this.mBean.codiceAutoComplete(val);
	}
	
	
	public FormField<String> getCodice() {
		return this.codice;
	}

	public void setCodice(FormField<String> codice) {
		this.codice = codice;
	}

	public void descrizioneSelectListener(ActionEvent ae){
		//do something
	}
	
	public void codiceSelectListener(ActionEvent ae){
		//do something
	}
}
