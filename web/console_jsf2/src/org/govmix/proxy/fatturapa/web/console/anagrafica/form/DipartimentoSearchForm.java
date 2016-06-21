package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.DipartimentoMBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.TextImpl;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.Text;

@SessionScoped @ManagedBean(name="dipartimentoSearchForm")
public class DipartimentoSearchForm extends BaseSearchForm implements SearchForm{

	private Text descrizione = null;

	private Text codice = null;

	private DipartimentoMBean mBean = null;

	public DipartimentoSearchForm(){
		init();
	}

	@Override
	public void init() {
		this.setId("formDipartimenti");
		this.setNomeForm(Utils.getInstance().getMessageFromResourceBundle("dipartimento.label.ricercaDipartimenti"));
		this.setClosable(false);

		try{
			WebGenericProjectFactory factory =  this.getFactory();

			// Init dei FormField
			this.descrizione = factory.getInputFieldFactory().createText("descrizione","dipartimento.search.descrizione",null,false);

			this.descrizione.setAutoComplete(true);
			this.descrizione.setEnableManualInput(true);
			this.descrizione.setFieldsToUpdate(this.getId() + "_searchPnl");
			this.descrizione.setForm(this);
			this.descrizione.setStyleClass("inputDefaultWidth");
			this.descrizione.setWidth(412);
			((TextImpl)this.descrizione).setSelectItemsWidth(412);
			((TextImpl)this.descrizione).setExecute("@this");

			this.codice = factory.getInputFieldFactory().createText("codice","dipartimento.search.codice",null,false);

			this.codice.setAutoComplete(true);
			this.codice.setEnableManualInput(true);
			this.codice.setFieldsToUpdate(this.getId() + "_searchPnl");
			this.codice.setForm(this);
			this.codice.setStyleClass("inputDefaultWidth");
			this.codice.setWidth(412);
			((TextImpl)this.codice).setSelectItemsWidth(412);
			((TextImpl)this.codice).setExecute("@this");
			
			this.setField(this.descrizione);
			this.setField(this.codice);
			
		}catch(FactoryException e){

		}
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.descrizione.reset();
		this.codice.reset();
	}

	public Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
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


	public Text getCodice() {
		return this.codice;
	}

	public void setCodice(Text codice) {
		this.codice = codice;
	}

	public void descrizioneValueChanged(ValueChangeEvent event){
		//do something
	}

	public void codiceValueChanged(ValueChangeEvent event){
		//do something
	}

	@Override
	public String valida() throws Exception {
		return null;
	}
}
