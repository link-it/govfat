package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.input.Text;



@SessionScoped @ManagedBean(name="registroSearchForm")
public class RegistroSearchForm extends BaseSearchForm implements SearchForm{

	private Text ente = null;
	
	public RegistroSearchForm() throws Exception{
		init();
	}

	@Override
	public void init() throws Exception {

		WebGenericProjectFactory factory = this.getFactory();
		
		this.ente = factory.getInputFieldFactory().createText("ente","ente.search.nome",null,false);
		
		this.setField(this.ente);
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.ente.reset();
	}

	public Text getEnte() {
		return this.ente;
	}

	public void setEnte(Text nome) {
		this.ente = nome;
	}
	@Override
	public String valida() throws Exception {
		return null;
	}
}
