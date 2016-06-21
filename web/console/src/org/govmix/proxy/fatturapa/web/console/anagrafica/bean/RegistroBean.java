package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputGroup;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;

public class RegistroBean extends BaseBean<Registro, Long>{

	private OutputField<String> nome = null;
	private OutputField<String> username = null;
	private OutputField<String> password = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = new OutputGroup();


	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsProperties = new OutputGroup();


	public RegistroBean(){
		init();
	}

	private void init(){
		this.nome = new OutputText();
		this.nome.setLabel("registro.nome");
		this.nome.setName("nome");

		this.username = new OutputText();
		this.username.setLabel("registro.username");
		this.username.setName("username");

		this.password = new OutputText();
		this.password.setLabel("registro.password");
		this.password.setName("password");
		this.password.setSecret(true); 

		this.fieldsDatiGenerali = new OutputGroup();
		this.fieldsDatiGenerali.setIdGroup("datiGenerali");
		this.fieldsDatiGenerali.setColumns(2);
		this.fieldsDatiGenerali.setRendered(true);
		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");
		
		this.fieldsDatiGenerali.addField(this.nome);
		this.fieldsDatiGenerali.addField(this.username);
		this.fieldsDatiGenerali.addField(this.password);

		this.fieldsProperties = new OutputGroup();
		this.fieldsProperties.setIdGroup("registroProperties");
		this.fieldsProperties.setColumns(2);
		this.fieldsProperties.setRendered(true);
		this.fieldsProperties.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsProperties.setColumnClasses("labelAllineataDx,valueAllineataSx");
	}
	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}
	
	@Override
	public void setDTO(Registro dto) {
		super.setDTO(dto);
		
		this.nome.setValue(this.getDTO().getNome());
		this.username.setValue(this.getDTO().getUsername());
		this.password.setValue(this.getDTO().getPassword());
		
	}
	
	public void setListaNomiProperties(List<RegistroProperty> listaProperties){
		// svuoto la lista per sicurezza
		this.fieldsProperties.getFields().clear();
		
		if(this.getDTO() != null && listaProperties != null){
			OutputField<String> proprieta = null;
			int i = 0;
			for (RegistroProperty dipartimentoProperty : listaProperties) {
				boolean found = false;
				proprieta  = new OutputText();
				proprieta.setLabel(dipartimentoProperty.getLabel());
				proprieta.setName("prop_" + (i++));
				
				for (RegistroPropertyValue dipartimentoPropertyValue : this.getDTO().getRegistroPropertyValueList()) {
					if(dipartimentoProperty.getNome().equals(dipartimentoPropertyValue.getIdProperty().getNome())){
						proprieta.setValue(dipartimentoPropertyValue.getValore());
						found = true;
						break;
					}
				}
				
				if(!found){
					proprieta.setValue(null);
				}
				
				this.fieldsProperties.addField(proprieta);
				
			}
		}
	}

	public OutputField<String> getNome() {
		return nome;
	}

	public void setNome(OutputField<String> nome) {
		this.nome = nome;
	}

	public OutputField<String> getUsername() {
		return username;
	}

	public void setUsername(OutputField<String> username) {
		this.username = username;
	}

	public OutputField<String> getPassword() {
		return password;
	}

	public void setPassword(OutputField<String> password) {
		this.password = password;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}

	public OutputGroup getFieldsProperties() {
		return fieldsProperties;
	}

	public void setFieldsProperties(OutputGroup fieldsProperties) {
		this.fieldsProperties = fieldsProperties;
	}
	
	
}
