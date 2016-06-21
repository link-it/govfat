package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

public class RegistroSearchForm extends SearchForm {

	private FormField<String> ente = null;
	
	public RegistroSearchForm(){
		init();
	}

	@Override
	protected void init() {
		this.ente = new TextField();
		this.ente.setRequired(false);
		this.ente.setLabel(Utils.getInstance().getMessageFromResourceBundle("ente.search.nome"));
		this.ente.setName("ente");
		this.ente.setValue(null);
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.ente.reset();
	}

	public FormField<String> getEnte() {
		return ente;
	}

	public void setEnte(FormField<String> nome) {
		this.ente = nome;
	}

}
