package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.IdRegistro;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.form.Form;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.BooleanField;
import org.openspcoop2.generic_project.web.input.FieldType;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectListField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

public class DipartimentoForm extends Form implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormField<String> codice;
	private FormField<String> descrizione;
	private FormField<Boolean> modalitaPush;
	private FormField<Boolean> notificaAutomatica;
	private FormField<String> username;
	private FormField<String> password;
	private FormField<String> endpoint;
	private FormField<SelectItem> registro = null;

	private List<FormField<String>> properties = null;

	private List<DipartimentoProperty> listaNomiProperties = null;

	public static final String DIPARTIMENTO_PATTERN = "[A-Z0-9]{6}";

	private Pattern dipartimentoPattern = null;

	public DipartimentoForm (){
		this.dipartimentoPattern = Pattern.compile(DIPARTIMENTO_PATTERN);				

		this.init();
	}

	@Override
	protected void init() {
		this.setClosable(false);
		this.setIdForm("formDipartimento");
		this.setNomeForm(null); 

		this.codice = new TextField();
		this.codice.setRequired(true);
		this.codice.setLabel(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.codice"));
		this.codice.setName("codice");
		this.codice.setValue(null);

		this.descrizione = new TextField();
		this.descrizione.setRequired(true);
		this.descrizione.setLabel(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.descrizione"));
		this.descrizione.setName("descrizione");
		this.descrizione.setValue(null);

		this.modalitaPush = new BooleanField();
		this.modalitaPush.setRequired(false);
		this.modalitaPush.setLabel(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.modalitaPush"));
		this.modalitaPush.setName("modalitaPush");
		this.modalitaPush.setFieldsToUpdate("formDipartimento_formPnl"); 
		this.modalitaPush.setValue(null);

		this.endpoint = new TextField();
		this.endpoint.setRequired(true);
		this.endpoint.setLabel(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.endpoint"));
		this.endpoint.setName("endpoint");
		this.endpoint.setValue(null);

		this.password = new TextField();
		this.password.setRequired(false);
		this.password.setType(FieldType.SECRET); 
		this.password.setLabel(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.password"));
		this.password.setName("password");
		this.password.setValue(null);

		this.username = new TextField();
		this.username.setRequired(false);
		this.username.setLabel(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.username"));
		this.username.setName("username");
		this.username.setValue(null);

		this.notificaAutomatica = new BooleanField();
		this.notificaAutomatica.setRequired(false);
		this.notificaAutomatica.setLabel(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.notificaAutomatica"));
		this.notificaAutomatica.setName("notificaAutomatica");
		//		this.notificaAutomatica.setFieldsToUpdate("formDipartimento_formPnl"); 
		this.notificaAutomatica.setValue(null);
		
		this.registro = new SelectListField();
		this.registro.setName("registro");
		this.registro.setValue(null);
//		this.registro.setRequired(true);
		this.registro.setLabel("dipartimento.form.registro");

		this.properties = new ArrayList<FormField<String>>();
	}

	@Override
	public void reset() {
		this.codice.reset();
		this.descrizione.reset();
		this.modalitaPush.reset();
		this.notificaAutomatica.reset();
		this.endpoint.reset();
		this.password.reset();
		this.username.reset();
		this.registro .reset();
		
		for (FormField<String> prop : this.properties) {
			prop.reset();
		}
	}

	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param bean
	 */
	public void setValues(DipartimentoBean bean){
		// Aggiornamento
		if(bean != null){
			this.codice.setDefaultValue(bean.getDTO().getCodice());
			this.codice.setDisabled(true);
			this.descrizione.setDefaultValue(bean.getDTO().getDescrizione());
			this.modalitaPush.setDefaultValue(bean.getDTO().getModalitaPush());
			//			this.username.setDefaultValue(bean.getDTO().getUsername());
			//			this.password.setDefaultValue(bean.getDTO().getPassword());
			//			if(bean.getDTO().getEndpoint() != null)
			//				this.endpoint.setDefaultValue(bean.getDTO().getEndpoint().toString());
			boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
			this.notificaAutomatica.setDefaultValue(bean.getDTO().getAccettazioneAutomatica());
			
			IdRegistro registro2 = bean.getDTO().getRegistro();
			if(registro2 != null){
				this.registro.setDefaultValue(new SelectItem(registro2.getNome(), registro2.getNome()));
			}else {
				this.registro.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
			}

			this.properties.clear();
			FormField<String> proprieta = null;
			for (DipartimentoPropertyValue prop : bean.getDTO().getDipartimentoPropertyValueList()) {
				proprieta = new TextField();
				
				proprieta.setRequired(mod);
				proprieta.setLabel(prop.getIdProperty().getNome());
				proprieta.setName("prop_" + prop.getIdProperty().getNome());
				proprieta.setDefaultValue(prop.getValore());

				this.properties.add(proprieta);
			}
		} else {
			// Nuovo Elemento
			this.codice.setDisabled(false);
			this.codice.setDefaultValue(null);
			this.descrizione.setDefaultValue(null);
			this.modalitaPush.setDefaultValue(null);
			this.notificaAutomatica.setDefaultValue(null);
			this.registro.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
			//			this.username.setDefaultValue(null);
			//			this.password.setDefaultValue(null);
			//			this.endpoint.setDefaultValue(null);
		}

		this.reset();
	}

	public void setListaNomiProperties(List<DipartimentoProperty> listaProperties){

		this.listaNomiProperties = listaProperties;
		// se e' nuovo creo l'elenco delle properties
		if(this.listaNomiProperties != null && this.listaNomiProperties.size() > 0){
			String namePrefix = "prop_";
			if(this.codice.getDefaultValue() == null){
				FormField<String> proprieta = null;
				for (DipartimentoProperty dipartimentoProperty : this.listaNomiProperties) {
					proprieta = new TextField();
					proprieta.setRequired(false);
					proprieta.setLabel(dipartimentoProperty.getLabel());
					proprieta.setName(namePrefix + dipartimentoProperty.getNome());
					proprieta.setDefaultValue(null);

					this.properties.add(proprieta);
				}
			} else {
				
				// Modifica, aggiorno solo la label (in questo momento coincide con il nome, in futuro puo' essere anche diversa)
				for (DipartimentoProperty dipartimentoProperty : this.listaNomiProperties) {
					boolean found = false;
					for (FormField<String> proprieta : this.properties) {
					//	if(proprieta.getLabel().equals(dipartimentoProperty.getLabel())){
						if(proprieta.getName().equals(namePrefix+dipartimentoProperty.getNome())){
							proprieta.setLabel(dipartimentoProperty.getLabel());
							found = true;
							break;
						}
					}
					
					if(!found){
						FormField<String> proprieta = new TextField();
						proprieta.setRequired(true);
						proprieta.setLabel(dipartimentoProperty.getLabel());
						proprieta.setName(namePrefix + dipartimentoProperty.getNome());
						proprieta.setDefaultValue(null);

						this.properties.add(proprieta);
					}
				}
			}
		}
		this.reset();
	}

	public FormField<String> getCodice() {
		return codice;
	}

	public void setCodice(FormField<String> codice) {
		this.codice = codice;
	}

	public FormField<String> getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(FormField<String> descrizione) {
		this.descrizione = descrizione;
	}

	public FormField<Boolean> getModalitaPush() {
		return modalitaPush;
	}

	public void setModalitaPush(FormField<Boolean> modalitaPush) {
		this.modalitaPush = modalitaPush;
	}

	public FormField<String> getUsername() {
		this.username.setRendered(false);

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.username.setRendered(true);

		return username;
	}

	public void setUsername(FormField<String> username) {
		this.username = username;
	}

	public FormField<String> getPassword() {
		this.password.setRendered(false);
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.password.setRendered(true);

		return password;
	}

	public void setPassword(FormField<String> password) {
		this.password = password;
	}

	public FormField<String> getEndpoint() {
		this.endpoint.setRendered(false);
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.endpoint.setRendered(true);

		return endpoint;
	}

	public void setEndpoint(FormField<String> endpoint) {
		this.endpoint = endpoint;
	}


	public String valida (){

		String _codice = this.codice.getValue();
		if(StringUtils.isEmpty(_codice))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.codice.getLabel());

		Matcher matcher = this.dipartimentoPattern.matcher(_codice);

		if(!matcher.matches())
			return Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.formatoCodiceErrato");


		String _descrizione = this.descrizione.getValue();
		if(StringUtils.isEmpty(_descrizione))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		
		SelectItem registroSI = this.registro.getValue();
		if(registroSI!= null){
			String _registro = registroSI.getValue();

			if(_registro.equals(CostantiForm.NON_SELEZIONATO) && mod)
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO, this.registro.getLabel());
		}


			
		//
				if(mod){
					
					for (FormField<String> prop : this.properties) {
						String _valore = prop.getValue();
						if(StringUtils.isEmpty(_valore))
							return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, prop.getLabel());	
					}
		//			String _endpoint = this.endpoint.getValue();
		//			if(StringUtils.isEmpty(_endpoint))
		//				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.endpoint.getLabel());
		//
		//			try{
		//				new URI(this.endpoint.getValue());
		//			}catch(Exception e){
		//				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_NON_VALIDO, this.endpoint.getLabel());
		//			}
		//
		//			String _usr = this.username.getValue();
		//			if(StringUtils.isEmpty(_usr))
		//				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.username.getLabel());
		//
		//			String _password = this.password.getValue();
		//			if(StringUtils.isEmpty(_password))
		//				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel());
		//
				}

		return null;
	}

	public Dipartimento getDipartimento(){
		Dipartimento dipartimento = new Dipartimento();

		dipartimento.setCodice(this.codice.getValue());
		dipartimento.setDescrizione(this.descrizione.getValue());
		
		IdRegistro idRegistro = new IdRegistro();
		
		SelectItem registroSI = this.registro.getValue();
		if(registroSI!= null){
			String _registro = registroSI.getValue();
			if(!_registro.equals(CostantiForm.NON_SELEZIONATO)){
				idRegistro.setNome(_registro);
				dipartimento.setRegistro(idRegistro );
			} else {
				dipartimento.setRegistro(null);
			}
		}
		
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		dipartimento.setModalitaPush(mod);
		//		dipartimento.setUsername(this.username.getValue());
		//		dipartimento.setPassword(this.password.getValue());
		//		try{
		//			URI epUri = new URI(this.endpoint.getValue());
		//			dipartimento.setEndpoint(epUri);
		//		}catch(Exception e){}

		dipartimento.setAccettazioneAutomatica(this.getNotificaAutomatica().getValue());

		// Impost i valori delle properties
		if(this.listaNomiProperties != null && this.listaNomiProperties .size() > 0){
			for (FormField<String> prop : this.properties) {
				String valore = prop.getValue();
				String nomeProp = prop.getName();
				nomeProp = nomeProp.substring(nomeProp.lastIndexOf("prop_")+"prop_".length());
				
				// Inserisco il valore se e' in modalitaPush (gia' controllato nella validazione), e solo se non e' vuoto;  
				if( !StringUtils.isEmpty(valore)){
				DipartimentoPropertyValue value = new DipartimentoPropertyValue();
				value.setValore(valore);
				boolean found = false;
				for (DipartimentoProperty nomeProprieta : this.listaNomiProperties) {
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
					dipartimento.getDipartimentoPropertyValueList().add(value);
				}
			}
			
		}
		
		return dipartimento;
	}

	public FormField<Boolean> getNotificaAutomatica() {
		return notificaAutomatica;
	}

	public void setNotificaAutomatica(FormField<Boolean> notificaAutomatica) {
		this.notificaAutomatica = notificaAutomatica;
	}

	public List<FormField<String>> getProperties() {
		
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		
		for (FormField<String> proprieta : properties) {
			proprieta.setRequired(mod);
		}
		
		
		return properties;
	}

	public void setProperties(List<FormField<String>> properties) {
		this.properties = properties;
	}

	public FormField<SelectItem> getRegistro() {
		this.registro.setRequired(false);
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.registro.setRequired(true);

		return registro;

	}

	public void setRegistro(FormField<SelectItem> registro) {
		this.registro = registro;
	}

	
}
