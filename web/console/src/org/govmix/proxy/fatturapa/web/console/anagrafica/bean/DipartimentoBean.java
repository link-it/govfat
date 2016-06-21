package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputGroup;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;

public class DipartimentoBean extends BaseBean<Dipartimento, Long>{
	
	
	private OutputField<String> codice = null;
	private OutputField<String> ente = null;
	private OutputField<String> descrizione = null;
	private OutputField<String> modalitaPush = null;
	private OutputField<String> endpoint = null;
	private OutputField<String> username = null;
	private OutputField<String> password = null;
	private OutputField<String> notificaAutomatica = null;
	private OutputField<String> registro = null;
	
	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = new OutputGroup();
	
	
	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsProperties = new OutputGroup();
	
	public DipartimentoBean(){
		init();
	}
	
	private void init(){
		this.codice = new OutputText();
		this.codice.setLabel("dipartimento.codice");
		this.codice.setName("codice");
		
		this.ente = new OutputText();
		this.ente.setLabel("dipartimento.ente");
		this.ente.setName("ente");
		
		this.descrizione = new OutputText();
		this.descrizione.setLabel("dipartimento.descrizione");
		this.descrizione.setName("descrizione");
		
		this.modalitaPush = new OutputText();
		this.modalitaPush.setLabel("dipartimento.modalitaPush");
		this.modalitaPush.setName("modalitaPush");
		
		this.endpoint = new OutputText();
		this.endpoint.setLabel("dipartimento.endpoint");
		this.endpoint.setName("endpoint");
		
		this.username = new OutputText();
		this.username.setLabel("dipartimento.username");
		this.username.setName("username");
		
		this.registro = new OutputText();
		this.registro.setLabel("dipartimento.registro");
		this.registro.setName("registro");
		
		this.password = new OutputText();
		this.password.setLabel("dipartimento.password");
		this.password.setName("password");
		this.password.setSecret(true); 
		
		this.notificaAutomatica = new OutputText();
		this.notificaAutomatica.setLabel("dipartimento.notificaAutomatica");
		this.notificaAutomatica.setName("notificaAutomatica");
		
		
		this.fieldsDatiGenerali = new OutputGroup();
		this.fieldsDatiGenerali.setIdGroup("datiGenerali");
		this.fieldsDatiGenerali.setColumns(2);
		this.fieldsDatiGenerali.setRendered(true);

		this.fieldsDatiGenerali.addField(this.codice);
//		this.fieldsDatiGenerali.addField(this.ente);
		this.fieldsDatiGenerali.addField(this.descrizione);
		this.fieldsDatiGenerali.addField(this.registro);
		this.fieldsDatiGenerali.addField(this.notificaAutomatica);
		
//		this.fieldsDatiGenerali.addField(this.endpoint);
//		this.fieldsDatiGenerali.addField(this.username);
//		this.fieldsDatiGenerali.addField(this.password);
		this.fieldsDatiGenerali.addField(this.modalitaPush);
		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");
		
		this.fieldsProperties = new OutputGroup();
		this.fieldsProperties.setIdGroup("dipartimentoProperties");
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
	public void setDTO(Dipartimento dto) {
		super.setDTO(dto);
		
		this.codice.setValue(this.getDTO().getCodice());
		this.ente.setValue(this.getDTO().getEnte().getNome());
		this.descrizione.setValue(this.getDTO().getDescrizione());
		if(this.getDTO().getRegistro() != null)
			this.registro.setValue( this.getDTO().getRegistro().getNome());
		
		boolean modalitaPush2 = this.getDTO().getModalitaPush();
		String mod = modalitaPush2 ? "commons.label.si": "commons.label.no";
		this.modalitaPush.setValue(mod);
//		URI endpoint2 = this.getDTO().getEndpoint();
//		if(endpoint2 != null)
//			this.endpoint.setValue(endpoint2.toString());
//		this.username.setValue(this.getDTO().getUsername());
//		this.password.setValue(this.getDTO().getPassword());
		boolean notificaAutomatica2 = this.getDTO().getAccettazioneAutomatica();
		String not2 = notificaAutomatica2 ? "commons.label.si": "commons.label.no";
		this.notificaAutomatica.setValue(not2);
		
	}
	
	public void setListaNomiProperties(List<DipartimentoProperty> listaProperties){
		// svuoto la lista per sicurezza
		this.fieldsProperties.getFields().clear();
		
		if(this.getDTO() != null && listaProperties != null){
			OutputField<String> proprieta = null;
			int i = 0;
			for (DipartimentoProperty dipartimentoProperty : listaProperties) {
				boolean found = false;
				proprieta  = new OutputText();
				proprieta.setLabel(dipartimentoProperty.getLabel());
				proprieta.setName("prop_" + (i++));
				
				for (DipartimentoPropertyValue dipartimentoPropertyValue : this.getDTO().getDipartimentoPropertyValueList()) {
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

	public OutputField<String> getNotificaAutomatica() {
		return notificaAutomatica;
	}

	public void setNotificaAutomatica(OutputField<String> notificaAutomatica) {
		this.notificaAutomatica = notificaAutomatica;
	}

	public OutputField<String> getCodice() {
		return codice;
	}

	public void setCodice(OutputField<String> codice) {
		this.codice = codice;
	}

	public OutputField<String> getEnte() {
		return ente;
	}

	public void setEnte(OutputField<String> ente) {
		this.ente = ente;
	}

	public OutputField<String> getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(OutputField<String> descrizione) {
		this.descrizione = descrizione;
	}

	public OutputField<String> getModalitaPush() {
		return modalitaPush;
	}

	public void setModalitaPush(OutputField<String> modalitaPush) {
		this.modalitaPush = modalitaPush;
	}

	public OutputField<String> getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(OutputField<String> endpoint) {
		this.endpoint = endpoint;
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

	public OutputField<String> getRegistro() {
		return registro;
	}

	public void setRegistro(OutputField<String> registro) {
		this.registro = registro;
	}
	
	
	
}
