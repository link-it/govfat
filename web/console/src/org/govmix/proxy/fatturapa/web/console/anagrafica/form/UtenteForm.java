package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.UtenteDipartimento;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.UtenteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.UtenteMBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.MultipleChoiceField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectListField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;
import org.openspcoop2.generic_project.web.input.FieldType;

public class UtenteForm extends Form implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormField<String> username;
	private FormField<String> password;
	private FormField<String> nome;
	private FormField<String> cognome;
	private SelectListField ruolo;

	private MultipleChoiceField dipartimento = null;

	private UtenteMBean mBean = null;
	
//	private static Logger log = Logger.getLogger("console.gui");

	public UtenteForm(){
		init();
	}

	@Override
	protected void init() {
		this.password = new TextField();
		this.password.setRequired(true);
		this.password.setLabel(Utils.getInstance().getMessageFromResourceBundle("utente.form.password"));
		this.password.setName("password");
		this.password.setType(FieldType.SECRET);
		this.password.setValue(null);
		this.password.setLabel2(Utils.getInstance().getMessageFromResourceBundle("utente.form.password.confirm")); 
		this.password.setConfirm(true); 
		this.password.setRedisplay(true); 

		if(Utils.getLoginBean().isNoPasswordLogin()){
			this.password.setRendered(false);
		} else 
			this.password.setRendered(true);

		this.username = new TextField();
		this.username.setRequired(true);
		this.username.setLabel(Utils.getInstance().getMessageFromResourceBundle("utente.form.username"));
		this.username.setName("username");
		this.username.setValue(null);

		this.nome = new TextField();
		this.nome.setRequired(false);
		this.nome.setLabel(Utils.getInstance().getMessageFromResourceBundle("utente.form.nome"));
		this.nome.setName("nome");
		this.nome.setValue(null);

		this.cognome = new TextField();
		this.cognome.setRequired(false);
		this.cognome.setLabel(Utils.getInstance().getMessageFromResourceBundle("utente.form.cognome"));
		this.cognome.setName("cognome");
		this.cognome.setValue(null);

		this.dipartimento = new MultipleChoiceField();
		this.dipartimento.setName("dipartimento");
		this.dipartimento.setValue(null);
		this.dipartimento.setRequired(true);
		this.dipartimento.setLabel(Utils.getInstance().getMessageFromResourceBundle("utente.form.dipartimento"));
		this.dipartimento.setNote(Utils.getInstance().getMessageFromResourceBundle("utente.form.dipartimento.note"));
		this.dipartimento.setForm(this);
		this.dipartimento.setAutoComplete(true);
		this.dipartimento.setEnableManualInput(true);
		this.dipartimento.setCheckDimensions(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 


		this.ruolo = new SelectListField();
		this.ruolo.setName("ruolo");
		this.ruolo.setValue(null);
		this.ruolo.setLabel("utente.form.ruolo");
		this.ruolo.setRequired(true);

	}

	@Override
	public void reset() {
		this.cognome.reset();
		this.username.reset();
		this.nome.reset();
		this.password.reset();
		this.dipartimento.reset();
		this.ruolo.reset();
	}

	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param bean
	 */
	public void setValues(UtenteBean bean){
		// Aggiornamento
		if(bean != null){
			this.cognome.setDefaultValue(bean.getDTO().getCognome()); 
			this.nome.setDefaultValue(bean.getDTO().getNome());
			this.username.setDefaultValue(bean.getDTO().getUsername());
			this.username.setDisabled(true);

			this.password.setDefaultValue(bean.getDTO().getPassword());
			this.password.setDefaultValue2(bean.getDTO().getPassword());

			List<SelectItem> listaDipartimentiEnte = new ArrayList<SelectItem>();
			for (DipartimentoBean dbean : bean.getListaDipartimenti()) {
				Dipartimento dipartimento = dbean.getDTO();
				org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						dipartimento.getCodice(),dipartimento.getDescrizione());
				listaDipartimentiEnte.add(item);
			}
			this.dipartimento.setDefaultValue(listaDipartimentiEnte);

			UserRole role = bean.getDTO().getRole();

			if(role!= null){
				if(role.equals(UserRole.ADMIN))
					this.ruolo.setDefaultValue(new SelectItem(UserRole.ADMIN.getValue(),UserRole.ADMIN.toString()));
				else if(role.equals(UserRole.DEPT_ADMIN))
					this.ruolo.setDefaultValue(new SelectItem(UserRole.DEPT_ADMIN.getValue(),UserRole.DEPT_ADMIN.toString()));
				else 
					this.ruolo.setDefaultValue(new SelectItem(UserRole.USER.getValue(),UserRole.USER.toString()));
			}else 
				this.ruolo.setDefaultValue(null);

		} else {
			// Nuovo Elemento
			this.cognome.setDefaultValue(null); 
			this.nome.setDefaultValue(null);
			this.username.setDefaultValue(null);
			this.username.setDisabled(false);
			if(Utils.getLoginBean().isNoPasswordLogin()){
				this.password.setDefaultValue("*");
				this.password.setDefaultValue2("*");
			} else {
				this.password.setDefaultValue(null);
				this.password.setDefaultValue2(null);
			}

			this.ruolo.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO)); 
			this.dipartimento.setDefaultValue(new ArrayList<SelectItem>());
		}

		this.reset();
	}

	public String valida (){

		//		String _cognome = this.cognome.getValue();
		//		if(StringUtils.isEmpty(_cognome))
		//			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.cognome.getLabel());
		//
		//		String _nome = this.nome.getValue();
		//		if(StringUtils.isEmpty(_nome))
		//			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.nome.getLabel());

		String _username = this.username.getValue();
		if(StringUtils.isEmpty(_username))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.username.getLabel());

		if(!Utils.getLoginBean().isNoPasswordLogin()){
			String _password = this.password.getValue();
			if(StringUtils.isEmpty(_password))
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel());

			String _password2 = this.password.getValue2();
			if(StringUtils.isEmpty(_password2))
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel2());

			if(!_password.equals(_password2))
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_CONFERMA_NON_UGUALE, this.password.getLabel(), this.password.getLabel2());
		}

		// Rulo
		SelectItem _ruolo = this.ruolo.getValue();

		String valueRuolo = null; 

		if(_ruolo != null)
			valueRuolo = _ruolo.getValue();

		if( valueRuolo == null || (valueRuolo != null && valueRuolo.equals(CostantiForm.NON_SELEZIONATO)))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.ruolo.getLabel());

		UserRole userRole = UserRole.toEnumConstant(valueRuolo);

		// Se l'utente non e' superuser deve avere almeno un dipartimento associato.
		if(!userRole.equals(UserRole.ADMIN)){
			List<SelectItem> value = this.dipartimento.getValue();
			if(value == null || (value != null && value.size() <= 0))
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.dipartimento.getLabel());
		}

		return null;
	}

	public Utente getUtente(){
		Utente utente = new Utente();

		utente.setCognome(this.cognome.getValue());
		utente.setNome(this.nome.getValue());
		//utente.setPassword(this.password.getValue());
		utente.setUsername(this.username.getValue());

		SelectItem _ruolo = this.ruolo.getValue();

		String valueRuolo = _ruolo.getValue();
		UserRole userRole = UserRole.toEnumConstant(valueRuolo);

		utente.setRole(userRole);

		for (SelectItem item : this.dipartimento.getValue()) {
			String codiceDipartimento = item.getValue();

			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(codiceDipartimento);

			UtenteDipartimento utenteDipartimento = new UtenteDipartimento();

			utenteDipartimento.setIdDipartimento(idDipartimento); 
			utente.addUtenteDipartimento(utenteDipartimento );
		}

		return utente;
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

	public FormField<String> getNome() {
		return nome;
	}

	public void setNome(FormField<String> nome) {
		this.nome = nome;
	}

	public FormField<String> getCognome() {
		return cognome;
	}

	public void setCognome(FormField<String> cognome) {
		this.cognome = cognome;
	}

	public MultipleChoiceField getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(MultipleChoiceField dipartimento) {
		this.dipartimento = dipartimento;
	}

	public List<SelectItem> dipartimentoAutoComplete(Object val){
		return this.mBean.descrizioneDipartimentoAutoComplete(val);
	}

	public void dipartimentoSelectListener(ActionEvent ae){
//		log.debug("Dipartimento Selected Action Listener");
		SelectItem selit = this.dipartimento.getSuggestionValue();
		
//		log.debug("Dipartimento Scelto ["+selit+"]");
		if(selit != null){
			String codiceDipartimentoScelto = selit.getValue();

			this.mBean.selezionaDipartimento(codiceDipartimentoScelto);
		}
		//1Reset suggestion value
		((MultipleChoiceField)this.dipartimento).setSuggestionValue(null);
	}

	public UtenteMBean getmBean() {
		return mBean;
	}

	public void setmBean(UtenteMBean mBean) {
		this.mBean = mBean;
	}

	public SelectListField getRuolo() {
		return ruolo;
	}

	public void setRuolo(SelectListField ruolo) {
		this.ruolo = ruolo;
	}





}
