package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.UtenteMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.TextImpl;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;


@SessionScoped @ManagedBean(name="utenteSearchForm")
public class UtenteSearchForm extends BaseSearchForm implements SearchForm{
	
	
	private Text denominazione = null;
	
	private SelectList dipartimento = null;
	
	private UtenteMBean mBean = null;

	public UtenteSearchForm() throws Exception{
		init();
	}
	
	@Override
	public void init() throws Exception {
		// Properties del form
		this.setId("formUtente");
		this.setNomeForm("utente.label.ricercaUtenti");
		this.setClosable(false);
		
		WebGenericProjectFactory factory = this.getFactory();

		// Init dei FormField
		this.denominazione = factory.getInputFieldFactory().createText("denominazione","utente.search.denominazione",null,false);

		this.denominazione.setAutoComplete(true);
		this.denominazione.setEnableManualInput(true);
		this.denominazione.setFieldsToUpdate(this.getId() + "_searchPnl");
		this.denominazione.setForm(this);
		this.denominazione.setStyleClass("inputDefaultWidth");
		this.denominazione.setWidth(412);
		((TextImpl)this.denominazione).setSelectItemsWidth(412);
		((TextImpl)this.denominazione).setExecute("@this");

		this.dipartimento = factory.getInputFieldFactory().createSelectList("dipartimento","utente.search.dipartimento",null,false);
		((SelectListImpl)this.dipartimento).setCheckItemWidth(true); 
		this.dipartimento.setStyleClass("inputDefaultWidth");
		this.dipartimento.setWidth(412);
		((SelectListImpl)this.dipartimento).setSelectItemsWidth(412);
		((SelectListImpl)this.dipartimento).setExecute("@this");
		
		this.setField(this.denominazione);
		this.setField(this.dipartimento);
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.denominazione.reset();
		this.dipartimento.reset();
	}
	
	public Text getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(Text denominazione) {
		this.denominazione = denominazione;
	}

	public SelectList getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(SelectList dipartimento) {
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
	
	public void denominazioneValueChanged(ValueChangeEvent event){
		//do something
	}
	
	@Override
	public String valida() throws Exception {
		return null;
	}
}
