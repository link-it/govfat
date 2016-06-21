package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.IdRegistro;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.BooleanCheckBoxImpl;
import org.openspcoop2.generic_project.web.input.BooleanCheckBox;
import org.openspcoop2.generic_project.web.input.FormField;
import org.openspcoop2.generic_project.web.input.InputSecret;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class DipartimentoForm extends BaseForm implements Form,Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Text codice;
	private Text descrizione;
	private BooleanCheckBox modalitaPush;
	private BooleanCheckBox notificaAutomatica;
	private Text username;
	private InputSecret password;
	private Text endpoint;
	private SelectList registro = null; 

	private List<Text> properties = null;

	private List<DipartimentoProperty> listaNomiProperties = null;

	public static final String DIPARTIMENTO_PATTERN = "[A-Z0-9]{6}";

	private Pattern dipartimentoPattern = null;

	public DipartimentoForm (){
		this.dipartimentoPattern = Pattern.compile(DIPARTIMENTO_PATTERN);				

		this.init();
	}

	@Override
	public void init() {
		this.setClosable(false);
		this.setId("formDipartimento");
		this.setNomeForm(null); 
		try{
			WebGenericProjectFactory factory =  this.getFactory();

			this.codice = factory.getInputFieldFactory().createText("codice","dipartimento.form.codice",null,true);
			this.codice.setStyleClass("inputDefaultWidth");
			this.codice.setWidth(412);
			this.descrizione = factory.getInputFieldFactory().createText("descrizione","dipartimento.form.descrizione",null,true);
			this.descrizione.setStyleClass("inputDefaultWidth");
			this.descrizione.setWidth(412);
			this.modalitaPush = factory.getInputFieldFactory().createBooleanCheckBox("modalitaPush","dipartimento.form.modalitaPush",null,false);
			this.modalitaPush.setFieldsToUpdate("formDipartimento_formPnl"); 
			this.modalitaPush.setForm(this); 
			this.modalitaPush.setStyleClass("controlset");
			((BooleanCheckBoxImpl)this.modalitaPush).setExecute("@this");
//			this.modalitaPush.setWidth(412);

			this.endpoint = factory.getInputFieldFactory().createText("endpoint","dipartimento.form.endpoint",null,true);
			this.endpoint.setStyleClass("inputDefaultWidth");
			this.endpoint.setWidth(412);
			this.password = factory.getInputFieldFactory().createInputSecret("password","dipartimento.form.password",null,false);
			this.password.setStyleClass("inputDefaultWidth");
			this.password.setWidth(412);
			this.username = factory.getInputFieldFactory().createText("username","dipartimento.form.username",null,false);
			this.username.setStyleClass("inputDefaultWidth");
			this.username.setWidth(412);

			this.notificaAutomatica = factory.getInputFieldFactory().createBooleanCheckBox("notificaAutomatica","dipartimento.form.notificaAutomatica",null,false);
			this.notificaAutomatica.setStyleClass("controlset");
//			this.notificaAutomatica.setWidth(412);
			this.registro = factory.getInputFieldFactory().createSelectList("registro","dipartimento.form.registro",null,false);
			this.registro.setStyleClass("inputDefaultWidth");
			this.registro.setWidth(412);
			
			_setModalitaPush();
			
			this.setField(this.codice);
			this.setField(this.descrizione);
			this.setField(this.modalitaPush);
			this.setField(this.endpoint);
			this.setField(this.password);
			this.setField(this.username);
			this.setField(this.notificaAutomatica);
			this.setField(this.registro);
			

			this.properties = new ArrayList<Text>();

		}catch(FactoryException e){

		}
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
		
		_setModalitaPush();

		for (FormField<String> prop : this.properties) {
			prop.reset();
		}
	}
	
	

	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param object
	 */
	@Override
	public void setObject(Object object){
		try{
			WebGenericProjectFactory factory =   this.getFactory();

			// Aggiornamento
			if(object != null){
				DipartimentoBean bean = (DipartimentoBean) object;
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
				Text proprieta = null;
				for (DipartimentoPropertyValue prop : bean.getDTO().getDipartimentoPropertyValueList()) {
					proprieta = factory.getInputFieldFactory().createText();

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

		}catch(Exception e){

		}

		this.reset();
	}

	public void setListaNomiProperties(List<DipartimentoProperty> listaProperties){

		this.listaNomiProperties = listaProperties;

		// se e' nuovo creo l'elenco delle properties
		if(this.listaNomiProperties != null && this.listaNomiProperties.size() > 0){
			try{
				WebGenericProjectFactory factory =  this.getFactory();

				String namePrefix = "prop_";
				if(this.codice.getDefaultValue() == null){
					Text proprieta = null;
					for (DipartimentoProperty dipartimentoProperty : this.listaNomiProperties) {
						proprieta = factory.getInputFieldFactory().createText();
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
							Text proprieta = factory.getInputFieldFactory().createText();
							proprieta.setRequired(true);
							proprieta.setLabel(dipartimentoProperty.getLabel());
							proprieta.setName(namePrefix + dipartimentoProperty.getNome());
							proprieta.setDefaultValue(null);

							this.properties.add(proprieta);
						}
					}
				}

			}catch(Exception e){

			}
		}
		this.reset();
	}

	public Text getCodice() {
		return this.codice;
	}

	public void setCodice(Text codice) {
		this.codice = codice;
	}

	public  Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public BooleanCheckBox getModalitaPush() {
		return this.modalitaPush;
	}

	public void setModalitaPush(BooleanCheckBox modalitaPush) {
		this.modalitaPush = modalitaPush;
	}

	public Text getUsername() {
		this.username.setRendered(false);

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.username.setRendered(true);

		return this.username;
	}

	public void setUsername(Text username) {
		this.username = username;
	}

	public InputSecret getPassword() {
		this.password.setRendered(false);
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.password.setRendered(true);

		return this.password;
	}

	public void setPassword(InputSecret password) {
		this.password = password;
	}

	public Text getEndpoint() {
		this.endpoint.setRendered(false);
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.endpoint.setRendered(true);

		return this.endpoint;
	}

	public void setEndpoint(Text endpoint) {
		this.endpoint = endpoint;
	}


	@Override
	public String valida (){

		String _codice = this.codice.getValue();
		if(StringUtils.isEmpty(_codice))
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.codice.getLabel());

		Matcher matcher = this.dipartimentoPattern.matcher(_codice);

		if(!matcher.matches())
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.formatoCodiceErrato");


		String _descrizione = this.descrizione.getValue();
		if(StringUtils.isEmpty(_descrizione))
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		SelectItem registroSI = this.registro.getValue();
		if(registroSI!= null){
			String _registro = registroSI.getValue();

			if(_registro.equals(CostantiForm.NON_SELEZIONATO) && mod)
				return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO, this.registro.getLabel());
		}



		//
		if(mod){

			for (FormField<String> prop : this.properties) {
				String _valore = prop.getValue();
				if(StringUtils.isEmpty(_valore))
					return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, prop.getLabel());	
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
	
	
	@Override
	public   Dipartimento getObject() throws Exception {
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

	public BooleanCheckBox getNotificaAutomatica() {
		return this.notificaAutomatica;
	}

	public void setNotificaAutomatica(BooleanCheckBox notificaAutomatica) {
		this.notificaAutomatica = notificaAutomatica;
	}

	public List<Text> getProperties() {

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		for (FormField<String> proprieta : this.properties) {
			proprieta.setRequired(mod);
		}


		return this.properties;
	}

	public void setProperties(List<Text> properties) {
		this.properties = properties;
	}

	public SelectList getRegistro() {
		this.registro.setRequired(false);
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		if(mod)
			this.registro.setRequired(true);

		return this.registro;

	}

	public void setRegistro(SelectList registro) {
		this.registro = registro;
	}

	
	public void modalitaPushOnChangeListener(ActionEvent ae){
		_setModalitaPush();
	}

	private void _setModalitaPush() {
		this.registro.setRequired(false);
		this.endpoint.setRendered(false);
		this.password.setRendered(false);
		this.username.setRendered(false);
		
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		
		if(mod){
			this.registro.setRequired(true);
			this.endpoint.setRendered(true);
			this.password.setRendered(true);
			this.username.setRendered(true);
			
			for (FormField<String> proprieta : this.properties) {
				proprieta.setRequired(mod);
			}
		}
		
	}


}
