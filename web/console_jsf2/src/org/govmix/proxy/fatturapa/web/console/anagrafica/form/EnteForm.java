package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseForm;
import org.openspcoop2.generic_project.web.input.Text;

public class EnteForm extends BaseForm implements Form {


	private Text nome;
	private Text endpoint;
	private Text descrizione;

	public EnteForm() throws Exception{
		init();
	}

	@Override
	public void init() throws Exception{
		
		WebGenericProjectFactory factory = this.getFactory();
		
		this.nome = factory.getInputFieldFactory().createText("nome","ente.form.nome",null,false);
		this.nome.setStyleClass("inputDefaultWidth");
		this.nome.setWidth(412);
		this.descrizione = factory.getInputFieldFactory().createText("descrizione","ente.form.descrizione",null,false);
		this.descrizione.setStyleClass("inputDefaultWidth");
		this.descrizione.setWidth(412);
		this.endpoint = factory.getInputFieldFactory().createText("endpoint","ente.form.endpoint",null,false);
		this.endpoint.setStyleClass("inputDefaultWidth");
		this.endpoint.setWidth(412);
		
		this.setField(this.nome);
		this.setField(this.descrizione);
		this.setField(this.endpoint);

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
	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param object
	 */
	@Override
	public void setObject(Object object){
		// Aggiornamento
		if(object != null){
			
			EnteBean bean = (EnteBean) object;
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

	@Override
	public String valida (){

		String _nome = this.nome.getValue();
		if(StringUtils.isEmpty(_nome))
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.nome.getLabel());


		String _descrizione = this.descrizione.getValue();
		if(StringUtils.isEmpty(_descrizione))
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());


		String _endpoint = this.endpoint.getValue();
		if(StringUtils.isEmpty(_endpoint))
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.endpoint.getLabel());

		try{
			new URI(this.endpoint.getValue());
		}catch(Exception e){
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_NON_VALIDO, this.endpoint.getLabel());
		}


		return null;
	}


	@Override
	public Ente getObject(){
		Ente ente = new Ente();

		ente.setNome(this.nome.getValue());
		ente.setDescrizione(this.descrizione.getValue());


		try{
			URI epUri = new URI(this.endpoint.getValue());
			ente.setEndpoint(epUri);
		}catch(Exception e){}

		return ente;
	}
	public Text getNome() {
		return this.nome;
	}
	public void setNome(Text nome) {
		this.nome = nome;
	}
	public Text getEndpoint() {
		return this.endpoint;
	}
	public void setEndpoint(Text endpoint) {
		this.endpoint = endpoint;
	}
	public Text getDescrizione() {
		return this.descrizione;
	}
	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}
	
	
}
