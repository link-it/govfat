/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.IdRegistroProperty;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.RegistroProperty;
import org.govmix.proxy.fatturapa.orm.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.input.InputSecret;
import org.openspcoop2.generic_project.web.input.Text;

public class RegistroForm extends BaseForm implements Form{


	private Text nome;
	private Text username;
	private InputSecret password;

	private List<Text> properties = null;

	private List<RegistroProperty> listaNomiProperties = null;
	private List<String> nomiPropertiesObbligatorie = null;

	public RegistroForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {

		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();

		this.nome = factory.getInputFieldFactory().createText("nome","registro.form.nome",null,true);
		this.password = factory.getInputFieldFactory().createInputSecret("password","registro.form.password",null,true);
		this.password.setRedisplay(true); 
		this.username = factory.getInputFieldFactory().createText("username","registro.form.username",null,true);

		this.setField(this.nome);
		this.setField(this.password);
		this.setField(this.username);
		
		this.properties = new ArrayList<Text>();
		this.nomiPropertiesObbligatorie = ConsoleProperties.getInstance(LoggerManager.getConsoleLogger()).getListaNomiPropertiesRegistroObbligatorie();

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

	public void setValues(RegistroBean bean){
		try{
			// Aggiornamento
			if(bean != null){
				this.nome.setDefaultValue(bean.getDTO().getNome());
				this.nome.setDisabled(true);
				this.username.setDefaultValue(bean.getDTO().getUsername());
				this.password.setDefaultValue(bean.getDTO().getPassword());

				this.properties.clear();
				Text proprieta = null;
				WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
				for (RegistroPropertyValue prop : bean.getDTO().getRegistroPropertyValueList()) {
					proprieta = factory.getInputFieldFactory().createText();
					proprieta.setRequired(this.nomiPropertiesObbligatorie.contains(prop.getIdProperty().getNome()));
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
			WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
			// se e' nuovo creo l'elenco delle properties
			if(this.listaNomiProperties != null && this.listaNomiProperties.size() > 0){
				String namePrefix = "prop_";
				if(this.nome.getDefaultValue() == null){
					Text proprieta = null;
					
					for (RegistroProperty registroProperty : this.listaNomiProperties) {
						proprieta = factory.getInputFieldFactory().createText();
						proprieta.setRequired(this.nomiPropertiesObbligatorie.contains(registroProperty.getNome()));
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
							proprieta.setRequired(this.nomiPropertiesObbligatorie.contains(registroProperty.getNome()));
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
			if(prop.isRequired() &&  StringUtils.isEmpty(_valore))
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
			for (Text prop : this.properties) {
				String valore = prop.getValue();
				String nomeProp = prop.getName();
				nomeProp = nomeProp.substring(nomeProp.lastIndexOf("prop_")+"prop_".length());

				RegistroPropertyValue value = new RegistroPropertyValue();
				value.setValore(valore);
				boolean found = false;
				if(StringUtils.isNotEmpty(valore)) {
					for (RegistroProperty nomeProprieta : this.listaNomiProperties) {
						if(nomeProprieta.getNome().equals(nomeProp)){
							IdRegistroProperty idProperty = new IdRegistroProperty();
							idProperty.setIdProtocollo(nomeProprieta.getIdProtocollo());
							idProperty.setNome(nomeProprieta.getNome());
							value.setIdProperty(idProperty);
							found = true;
							break;
						}
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

