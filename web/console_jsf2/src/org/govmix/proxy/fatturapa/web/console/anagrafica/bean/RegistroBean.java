package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf2.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.view.IViewBean;

public class RegistroBean extends BaseBean<Registro, Long> implements IViewBean<Registro, Long> {

	private Text nome = null;
	private Text username = null;
	private Text password = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;


	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsProperties = null;


	public RegistroBean(){
		try{
			init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.nome = this.getFactory().getOutputFieldFactory().createText("nome","registro.nome");
		this.username = this.getFactory().getOutputFieldFactory().createText("username","registro.username");
		this.password = this.getFactory().getOutputFieldFactory().createText("password","registro.password");
		this.password.setSecret(true);
		
		this.setField(this.nome);
		this.setField(this.username);
		this.setField(this.password);

		this.fieldsDatiGenerali = this.getFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.setStyleClass("outputGroupTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");

		this.fieldsDatiGenerali.addField(this.nome);
		this.fieldsDatiGenerali.addField(this.username);
		this.fieldsDatiGenerali.addField(this.password);

		this.fieldsProperties = this.getFactory().getOutputFieldFactory().createOutputGroup("registroProperties",2);
		this.fieldsProperties.setRendered(true);
		this.fieldsProperties.setStyleClass("outputGroupTable"); 
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
			try{
				Text proprieta = null;
				int i = 0;
				for (RegistroProperty dipartimentoProperty : listaProperties) {
					boolean found = false;
					proprieta  = this.getFactory().getOutputFieldFactory().createText();
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
			}catch(Exception e){

			}
		}
	}

	public Text getNome() {
		return nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}

	public Text getUsername() {
		return username;
	}

	public void setUsername(Text username) {
		this.username = username;
	}

	public Text getPassword() {
		return password;
	}

	public void setPassword(Text password) {
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
