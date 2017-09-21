/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.RegistroProperty;
import org.govmix.proxy.fatturapa.orm.RegistroPropertyValue;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;

public class RegistroBean extends BaseBean<Registro, Long> implements IBean<Registro, Long> {

	private Text nome = null;
	private Text username = null;
	private Text password = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;


	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsProperties = null;


	public RegistroBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.nome = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("nome","registro.nome");
		this.username = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("username","registro.username");
		this.password = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("password","registro.password");
		this.password.setSecret(true);
		
		this.setField(this.nome);
		this.setField(this.username);
		this.setField(this.password);

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiGenerali.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);

		this.fieldsDatiGenerali.addField(this.nome);
		this.fieldsDatiGenerali.addField(this.username);
		this.fieldsDatiGenerali.addField(this.password);

		this.fieldsProperties = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("registroProperties",2);
		this.fieldsProperties.setRendered(true);
		this.fieldsProperties.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsProperties.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);
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
					proprieta  = this.getWebGenericProjectFactory().getOutputFieldFactory().createText();
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
		return this.nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}

	public Text getUsername() {
		return this.username;
	}

	public void setUsername(Text username) {
		this.username = username;
	}

	public Text getPassword() {
		return this.password;
	}

	public void setPassword(Text password) {
		this.password = password;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return this.fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}

	public OutputGroup getFieldsProperties() {
		return this.fieldsProperties;
	}

	public void setFieldsProperties(OutputGroup fieldsProperties) {
		this.fieldsProperties = fieldsProperties;
	}


}
