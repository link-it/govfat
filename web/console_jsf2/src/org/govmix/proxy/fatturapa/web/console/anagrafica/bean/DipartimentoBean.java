package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf2.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.view.IViewBean;


public class DipartimentoBean extends BaseBean<Dipartimento, Long> implements IViewBean<Dipartimento, Long>{ 


	private Text codice = null;
	private Text ente = null;
	private Text descrizione = null;
	private Text modalitaPush = null;
	private Text endpoint = null;
	private Text username = null;
	private Text password = null;
	private Text notificaAutomatica = null;
	private Text registro = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;


	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsProperties = null;

	public DipartimentoBean(){
		try{
			init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.codice = this.getFactory().getOutputFieldFactory().createText("codice","dipartimento.codice");
		this.ente = this.getFactory().getOutputFieldFactory().createText("ente","dipartimento.ente");
		this.descrizione = this.getFactory().getOutputFieldFactory().createText("descrizione","dipartimento.descrizione");
		this.modalitaPush = this.getFactory().getOutputFieldFactory().createText("modalitaPush","dipartimento.modalitaPush");
		this.endpoint = this.getFactory().getOutputFieldFactory().createText("endpoint","dipartimento.endpoint");
		this.username = this.getFactory().getOutputFieldFactory().createText("username","dipartimento.username");
		this.registro = this.getFactory().getOutputFieldFactory().createText("registro","dipartimento.registro");
		this.password = this.getFactory().getOutputFieldFactory().createText("password","dipartimento.password");
		this.password.setSecret(true); 

		this.notificaAutomatica = this.getFactory().getOutputFieldFactory().createText("notificaAutomatica","dipartimento.notificaAutomatica");

		this.setField(this.codice);
		this.setField(this.descrizione);
		this.setField(this.registro);
		this.setField(this.notificaAutomatica);
		this.setField(this.modalitaPush);
		
		this.fieldsDatiGenerali = this.getFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.codice);
		this.fieldsDatiGenerali.addField(this.descrizione);
		this.fieldsDatiGenerali.addField(this.registro);
		this.fieldsDatiGenerali.addField(this.notificaAutomatica);
		this.fieldsDatiGenerali.addField(this.modalitaPush);
		
		this.fieldsDatiGenerali.setStyleClass("outputGroupTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");

		this.fieldsProperties = this.getFactory().getOutputFieldFactory().createOutputGroup("dipartimentoProperties",2);
		this.fieldsProperties.setStyleClass("outputGroupTable"); 
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
			try{
				Text proprieta = null;
				int i = 0;
				for (DipartimentoProperty dipartimentoProperty : listaProperties) {
					boolean found = false;
					proprieta  = this.getFactory().getOutputFieldFactory().createText(("prop_" + (i++)),dipartimentoProperty.getLabel());

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
			}catch(Exception e){

			}
		}
	}

	public Text getNotificaAutomatica() {
		return notificaAutomatica;
	}

	public void setNotificaAutomatica(Text notificaAutomatica) {
		this.notificaAutomatica = notificaAutomatica;
	}

	public Text getCodice() {
		return codice;
	}

	public void setCodice(Text codice) {
		this.codice = codice;
	}

	public Text getEnte() {
		return ente;
	}

	public void setEnte(Text ente) {
		this.ente = ente;
	}

	public Text getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getModalitaPush() {
		return modalitaPush;
	}

	public void setModalitaPush(Text modalitaPush) {
		this.modalitaPush = modalitaPush;
	}

	public Text getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Text endpoint) {
		this.endpoint = endpoint;
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

	public Text getRegistro() {
		return registro;
	}

	public void setRegistro(Text registro) {
		this.registro = registro;
	}



}
