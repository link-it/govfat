package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.form.Form;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.input.FieldType;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

public class RegistroForm extends Form{


	private FormField<String> nome;
	private FormField<String> username;
	private FormField<String> password;

	private List<FormField<String>> properties = null;

	private List<RegistroProperty> listaNomiProperties = null;

	public RegistroForm(){
		init();
	}

	@Override
	protected void init() {
		this.nome = new TextField();
		this.nome.setRequired(true);
		this.nome.setLabel(Utils.getInstance().getMessageFromResourceBundle("registro.form.nome"));
		this.nome.setName("nome");
		this.nome.setValue(null);
		
		this.password = new TextField();
		this.password.setRequired(true);
		this.password.setType(FieldType.SECRET); 
		this.password.setLabel(Utils.getInstance().getMessageFromResourceBundle("registro.form.password"));
		this.password.setName("password");
		this.password.setValue(null);

		this.username = new TextField();
		this.username.setRequired(true);
		this.username.setLabel(Utils.getInstance().getMessageFromResourceBundle("registro.form.username"));
		this.username.setName("username");
		this.username.setValue(null);
		
		this.properties = new ArrayList<FormField<String>>();
	}

	@Override
	public void reset() {
		this.nome.reset();
		this.password.reset();
		this.username.reset();

		for (FormField<String> prop : this.properties) {
			prop.reset();
		}
	}

	public void setValues(RegistroBean bean){
		// Aggiornamento
		if(bean != null){
			this.nome.setDefaultValue(bean.getDTO().getNome());
			this.nome.setDisabled(true);
			this.username.setDefaultValue(bean.getDTO().getUsername());
			this.password.setDefaultValue(bean.getDTO().getPassword());

			this.properties.clear();
			FormField<String> proprieta = null;
			for (RegistroPropertyValue prop : bean.getDTO().getRegistroPropertyValueList()) {
				proprieta = new TextField();
				proprieta.setRequired(true);
				proprieta.setLabel(prop.getIdProperty().getNome());
				proprieta.setName("prop_" + prop.getIdProperty().getNome());
				proprieta.setDefaultValue(prop.getValore());

				this.properties.add(proprieta);
			}
		} else {
			// Nuovo Elemento
			this.nome.setDisabled(false);
			this.nome.setDefaultValue(null);
			this.username.setDefaultValue(null);
			this.password.setDefaultValue(null);
		}

		this.reset();
	}


	public void setListaNomiProperties(List<RegistroProperty> listaProperties){

		this.listaNomiProperties = listaProperties;
		// se e' nuovo creo l'elenco delle properties
		if(this.listaNomiProperties != null && this.listaNomiProperties.size() > 0){
			String namePrefix = "prop_";
			if(this.nome.getDefaultValue() == null){
				FormField<String> proprieta = null;
				for (RegistroProperty registroProperty : this.listaNomiProperties) {
					proprieta = new TextField();
					proprieta.setRequired(true);
					proprieta.setLabel(registroProperty.getLabel());
					proprieta.setName(namePrefix + registroProperty.getNome());
					proprieta.setDefaultValue(null);

					this.properties.add(proprieta);
				}
			} else {
				// Modifica, aggiorno solo la label (in questo momento coincide con il nome, in futuro puo' essere anche diversa)
				for (RegistroProperty registroProperty : this.listaNomiProperties) {
					boolean found = false;
					for (FormField<String> proprieta : this.properties) {
//						if(proprieta.getLabel().equals(dipartimentoProperty.getLabel())){
						if(proprieta.getName().equals(namePrefix+registroProperty.getNome())){
							proprieta.setLabel(registroProperty.getLabel());
							found = true;
							break;
						}
					}
					
					if(!found){
						FormField<String> proprieta = new TextField();
						proprieta.setRequired(true);
						proprieta.setLabel(registroProperty.getLabel());
						proprieta.setName(namePrefix + registroProperty.getNome());
						proprieta.setDefaultValue(null);

						this.properties.add(proprieta);
					}
				}
			}
		}
		this.reset();
	}


	public FormField<String> getUsername() {
		return username;
	}

	public void setUsername(FormField<String> username) {
		this.username = username;
	}

	public FormField<String> getPassword() {
		return password;
	}

	public void setPassword(FormField<String> password) {
		this.password = password;
	}

	public String valida (){

		String _codice = this.nome.getValue();
		if(StringUtils.isEmpty(_codice))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.nome.getLabel());

		String _usr = this.username.getValue();
		if(StringUtils.isEmpty(_usr))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.username.getLabel());

		String _password = this.password.getValue();
		if(StringUtils.isEmpty(_password))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel());


		for (FormField<String> prop : this.properties) {
			String _valore = prop.getValue();
			if(StringUtils.isEmpty(_valore))
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, prop.getLabel());	
		}

		return null;
	}

	public Registro getRegistro(){
		Registro registro = new Registro();

		registro.setNome(this.nome.getValue());
		registro.setUsername(this.username.getValue());
		registro.setPassword(this.password.getValue());

		// Impost i valori delle properties
		if(this.listaNomiProperties != null && this.listaNomiProperties .size() > 0){
			for (FormField<String> prop : this.properties) {
				String valore = prop.getValue();
				String nomeProp = prop.getName();
				nomeProp = nomeProp.substring(nomeProp.lastIndexOf("prop_")+"prop_".length());

				RegistroPropertyValue value = new RegistroPropertyValue();
				value.setValore(valore);
				boolean found = false;
				for (RegistroProperty nomeProprieta : this.listaNomiProperties) {
					if(nomeProprieta.getNome().equals(nomeProp)){
						IdProperty idProperty = new IdProperty();
						idProperty.setIdEnte(nomeProprieta.getIdEnte());
						idProperty.setNome(nomeProprieta.getNome());
						value.setIdProperty(idProperty);
						found = true;
						break;
					}
				}

				if(found)
					registro.getRegistroPropertyValueList().add(value);
			}

		}

		return registro;
	}


	public List<FormField<String>> getProperties() {
		return properties;
	}

	public void setProperties(List<FormField<String>> properties) {
		this.properties = properties;
	}

	public FormField<String> getNome() {
		return nome;
	}

	public void setNome(FormField<String> nome) {
		this.nome = nome;
	}

	public List<RegistroProperty> getListaNomiProperties() {
		return listaNomiProperties;
	}
	
	
}

