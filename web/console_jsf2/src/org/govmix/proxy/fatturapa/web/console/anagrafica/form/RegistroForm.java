package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils;
import org.openspcoop2.generic_project.web.input.InputSecret;
import org.openspcoop2.generic_project.web.input.Text;

public class RegistroForm extends BaseForm implements Form{


	private Text nome;
	private Text username;
	private InputSecret password;

	private List<Text> properties = null;

	private List<RegistroProperty> listaNomiProperties = null;

	public RegistroForm() throws Exception{
		init();
	}

	@Override
	public void init() throws Exception {

		WebGenericProjectFactory factory = this.getFactory();

		this.nome = factory.getInputFieldFactory().createText("nome","registro.form.nome",null,true);
		this.nome.setStyleClass("inputDefaultWidth");
		this.nome.setWidth(412);
		this.password = factory.getInputFieldFactory().createInputSecret("password","registro.form.password",null,true);
		this.password.setStyleClass("inputDefaultWidth");
		this.password.setWidth(412);
		this.username = factory.getInputFieldFactory().createText("username","registro.form.username",null,true);
		this.username.setStyleClass("inputDefaultWidth");
		this.username.setWidth(412);

		this.setField(this.nome);
		this.setField(this.password);
		this.setField(this.username);

		this.properties = new ArrayList<Text>();
	}

	@Override
	public void reset() {
		this.nome.reset();
		this.password.reset();
		this.username.reset();

		for (Text prop : this.properties) {
			prop.reset();
		}
	}

	@Override
	public void setObject(Object object) throws Exception {

		try{
			// Aggiornamento
			if(object != null){
				RegistroBean bean = (RegistroBean) object;
				this.nome.setDefaultValue(bean.getDTO().getNome());
				this.nome.setDisabled(true);
				this.username.setDefaultValue(bean.getDTO().getUsername());
				this.password.setDefaultValue(bean.getDTO().getPassword());

				this.properties.clear();
				Text proprieta = null;
				WebGenericProjectFactory factory = this.getFactory();
				for (RegistroPropertyValue prop : bean.getDTO().getRegistroPropertyValueList()) {
					proprieta = factory.getInputFieldFactory().createText();
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

		}catch(Exception e){

		}
		this.reset();
	}


	public void setListaNomiProperties(List<RegistroProperty> listaProperties){
		try{
			this.listaNomiProperties = listaProperties;
			WebGenericProjectFactory factory = this.getFactory();
			// se e' nuovo creo l'elenco delle properties
			if(this.listaNomiProperties != null && this.listaNomiProperties.size() > 0){
				String namePrefix = "prop_";
				if(this.nome.getDefaultValue() == null){
					Text proprieta = null;

					for (RegistroProperty registroProperty : this.listaNomiProperties) {
						proprieta = factory.getInputFieldFactory().createText();
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
						for (Text proprieta : this.properties) {
							//						if(proprieta.getLabel().equals(dipartimentoProperty.getLabel())){
							if(proprieta.getName().equals(namePrefix+registroProperty.getNome())){
								proprieta.setLabel(registroProperty.getLabel());
								found = true;
								break;
							}
						}

						if(!found){
							Text proprieta = factory.getInputFieldFactory().createText();
							proprieta.setRequired(true);
							proprieta.setLabel(registroProperty.getLabel());
							proprieta.setName(namePrefix + registroProperty.getNome());
							proprieta.setDefaultValue(null);

							this.properties.add(proprieta);
						}
					}
				}
			}

		}catch(Exception e){

		}
		this.reset();
	}


	public Text getUsername() {
		return this.username;
	}

	public void setUsername(Text username) {
		this.username = username;
	}

	public InputSecret getPassword() {
		return this.password;
	}

	public void setPassword(InputSecret password) {
		this.password = password;
	}

	@Override
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


		for (Text prop : this.properties) {
			String _valore = prop.getValue();
			if(StringUtils.isEmpty(_valore))
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, prop.getLabel());	
		}

		return null;
	}

	@Override
	public Registro getObject(){
		Registro registro = new Registro();

		registro.setNome(this.nome.getValue());
		registro.setUsername(this.username.getValue());
		registro.setPassword(this.password.getValue());

		// Impost i valori delle properties
		if(this.listaNomiProperties != null && this.listaNomiProperties .size() > 0){
			for (Text prop : this.properties) {
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


	public List<Text> getProperties() {
		return this.properties;
	}

	public void setProperties(List<Text> properties) {
		this.properties = properties;
	}

	public Text getNome() {
		return this.nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}

	public List<RegistroProperty> getListaNomiProperties() {
		return this.listaNomiProperties;
	}


}

