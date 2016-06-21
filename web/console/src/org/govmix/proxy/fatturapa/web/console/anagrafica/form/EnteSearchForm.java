package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

public class EnteSearchForm extends SearchForm {
	
	
	private FormField<String> nome = null;
	
	public EnteSearchForm(){
		init();
	}

	@Override
	protected void init() {
		this.nome = new TextField();
		this.nome.setRequired(false);
		this.nome.setLabel(Utils.getInstance().getMessageFromResourceBundle("ente.search.nome"));
		this.nome.setName("nome");
		this.nome.setValue(null);
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.nome.reset();
	}

	public FormField<String> getNome() {
		return nome;
	}

	public void setNome(FormField<String> nome) {
		this.nome = nome;
	}
}
