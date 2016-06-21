package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.form.Form;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

public class EnteForm extends Form {


	private FormField<String> nome;
	private FormField<String> endpoint;
	private FormField<String> descrizione;

	public EnteForm(){
		init();
	}

	@Override
	protected void init() {
		this.nome = new TextField();
		this.nome.setRequired(false);
		this.nome.setLabel(Utils.getInstance().getMessageFromResourceBundle("ente.form.nome"));
		this.nome.setName("nome");
		this.nome.setValue(null);

		this.descrizione = new TextField();
		this.descrizione.setRequired(true);
		this.descrizione.setLabel(Utils.getInstance().getMessageFromResourceBundle("ente.form.descrizione"));
		this.descrizione.setName("descrizione");
		this.descrizione.setValue(null);

		this.endpoint = new TextField();
		this.endpoint.setRequired(true);
		this.endpoint.setLabel(Utils.getInstance().getMessageFromResourceBundle("ente.form.endpoint"));
		this.endpoint.setName("endpoint");
		this.endpoint.setValue(null);


	}
	@Override
	public void reset() {
		this.nome.reset(); 
		this.descrizione.reset();
		this.endpoint.reset();

	}

	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param bean
	 */
	public void setValues(EnteBean bean){
		// Aggiornamento
		if(bean != null){
			this.nome.setDefaultValue(bean.getDTO().getNome());
			this.nome.setDisabled(true);
			this.descrizione.setDefaultValue(bean.getDTO().getDescrizione());
			if(bean.getDTO().getEndpoint() != null)
				this.endpoint.setDefaultValue(bean.getDTO().getEndpoint().toString());

		} else {
			// Nuovo Elemento
			this.nome.setDefaultValue(null);
			this.nome.setDisabled(false); 
			this.descrizione.setDefaultValue(null);
			this.endpoint.setDefaultValue(null);
		}
		this.reset();
	}

	public String valida (){

		String _nome = this.nome.getValue();
		if(StringUtils.isEmpty(_nome))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.nome.getLabel());


		String _descrizione = this.descrizione.getValue();
		if(StringUtils.isEmpty(_descrizione))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());


		String _endpoint = this.endpoint.getValue();
		if(StringUtils.isEmpty(_endpoint))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.endpoint.getLabel());

		try{
			new URI(this.endpoint.getValue());
		}catch(Exception e){
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_NON_VALIDO, this.endpoint.getLabel());
		}


		return null;
	}


	public Ente getEnte(){
		Ente ente = new Ente();

		ente.setNome(this.nome.getValue());
		ente.setDescrizione(this.descrizione.getValue());


		try{
			URI epUri = new URI(this.endpoint.getValue());
			ente.setEndpoint(epUri);
		}catch(Exception e){}

		return ente;
	}
	public FormField<String> getNome() {
		return nome;
	}
	public void setNome(FormField<String> nome) {
		this.nome = nome;
	}
	public FormField<String> getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(FormField<String> endpoint) {
		this.endpoint = endpoint;
	}
	public FormField<String> getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(FormField<String> descrizione) {
		this.descrizione = descrizione;
	}
	
	
}
