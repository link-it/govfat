package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.input.Text;

@SessionScoped @ManagedBean(name="enteSearchForm")
public class EnteSearchForm extends BaseSearchForm implements SearchForm {
	
	
	private Text nome = null;
	
	public EnteSearchForm() throws Exception{
		init();
	}

	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getFactory();
		
		this.nome = factory.getInputFieldFactory().createText("nome","ente.search.nome",null,false);
		
		this.setField(this.nome); 
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.nome.reset();
	}

	public Text getNome() {
		return this.nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}
	
	@Override
	public String valida() throws Exception {
		return null;
	}
}
