package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

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
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf2.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.PickListImpl;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.InputSecret;
import org.openspcoop2.generic_project.web.input.PickList;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class UtenteForm extends BaseForm implements Form, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Text username;
	private InputSecret password;
	private Text nome;
	private Text cognome;
	private SelectList ruolo; 
	private PickList dipartimento = null; 

	private UtenteMBean mBean = null;

	//	private static Logger log = Logger.getLogger("console.gui");

	public UtenteForm()throws Exception{
		init();
	}

	@Override
	public void init() throws Exception {

		WebGenericProjectFactory factory = this.getFactory();

		this.password = factory.getInputFieldFactory().createInputSecret("password","utente.form.password",null,true);
		this.password.setLabel2(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.password.confirm")); 
		this.password.setConfirm(true); 
		this.password.setRedisplay(true); 
		this.password.setStyleClass("inputDefaultWidth");
		this.password.setWidth(412);

		if(Utils.getLoginBean().isNoPasswordLogin()){
			this.password.setRendered(false);
		} else 
			this.password.setRendered(true);

		this.username = factory.getInputFieldFactory().createText("username","utente.form.username",null,true);
		this.username.setStyleClass("inputDefaultWidth");
		this.username.setWidth(412);
		this.nome = factory.getInputFieldFactory().createText("nome","utente.form.nome",null,false);
		this.nome.setStyleClass("inputDefaultWidth");
		this.nome.setWidth(412);
		this.cognome = factory.getInputFieldFactory().createText("cognome","utente.form.cognome",null,false);
		this.cognome.setStyleClass("inputDefaultWidth");
		this.cognome.setWidth(412);

		this.dipartimento = factory.getInputFieldFactory().createPickList("dipartimento","utente.form.dipartimento",null,true);
		this.dipartimento.setNote(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.dipartimento.note"));
		this.dipartimento.setForm(this);
		this.dipartimento.setAutoComplete(true);
		this.dipartimento.setEnableManualInput(true);
		((PickListImpl)this.dipartimento).setCheckDimensions(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		this.dipartimento.setStyleClass("inputDefaultWidth");
		this.dipartimento.setWidth(412);
		((PickListImpl)this.dipartimento).setSelectItemsWidth(412);
		((PickListImpl)this.dipartimento).setExecute("@this");

		this.ruolo = factory.getInputFieldFactory().createSelectList("ruolo","utente.form.ruolo",null,true);
		this.ruolo.setStyleClass("inputDefaultWidth");
		this.ruolo.setWidth(412);
		((SelectListImpl)this.ruolo).setSelectItemsWidth(412);

		this.setField(this.password);
		this.setField(this.username);
		this.setField(this.nome);
		this.setField(this.cognome);
		this.setField(this.dipartimento);
		this.setField(this.ruolo);

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

	@Override
	public void setObject(Object object) throws Exception {
		// Aggiornamento
		if(object != null){
			UtenteBean bean = (UtenteBean) object;
			this.cognome.setDefaultValue(bean.getDTO().getCognome()); 
			this.nome.setDefaultValue(bean.getDTO().getNome());
			this.username.setDefaultValue(bean.getDTO().getUsername());
			this.username.setDisabled(true);

			this.password.setDefaultValue(bean.getDTO().getPassword());
			this.password.setDefaultValue2(bean.getDTO().getPassword());

			List<SelectItem> listaDipartimentiEnte = new ArrayList<SelectItem>();
			if(bean.getListaDipartimenti() != null)
			for (DipartimentoBean dbean : bean.getListaDipartimenti()) {
				Dipartimento dipartimento = dbean.getDTO();
				org.openspcoop2.generic_project.web.input.SelectItem item = new org.openspcoop2.generic_project.web.input.SelectItem(
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

	@Override
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
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.username.getLabel());

		if(!Utils.getLoginBean().isNoPasswordLogin()){
			String _password = this.password.getValue();
			if(StringUtils.isEmpty(_password))
				return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel());

			String _password2 = this.password.getValue2();
			if(StringUtils.isEmpty(_password2))
				return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel2());

			if(!_password.equals(_password2))
				return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_CONFERMA_NON_UGUALE, this.password.getLabel(), this.password.getLabel2());
		}

		// Rulo
		SelectItem _ruolo = this.ruolo.getValue();

		String valueRuolo = null; 

		if(_ruolo != null)
			valueRuolo = _ruolo.getValue();

		if( valueRuolo == null || (valueRuolo != null && valueRuolo.equals(CostantiForm.NON_SELEZIONATO)))
			return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.ruolo.getLabel());

		UserRole userRole = UserRole.toEnumConstant(valueRuolo);

		// Se l'utente non e' superuser deve avere almeno un dipartimento associato.
		if(!userRole.equals(UserRole.ADMIN)){
			List<SelectItem> value = this.dipartimento.getValue();
			if(value == null || (value != null && value.size() <= 0))
				return org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.dipartimento.getLabel());
		}

		return null;
	}

	@Override
	public Utente getObject(){
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

	public Text getNome() {
		return this.nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}

	public Text getCognome() {
		return this.cognome;
	}

	public void setCognome(Text cognome) {
		this.cognome = cognome;
	}

	public PickList getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(PickList dipartimento) {
		this.dipartimento = dipartimento;
	}

	public List<SelectItem> dipartimentoAutoComplete(Object val){
		return this.mBean.descrizioneDipartimentoAutoComplete(val);
	}

	public void dipartimentoValueChanged(ValueChangeEvent event){
		Object newValueObj = event.getNewValue();

		if(newValueObj instanceof org.openspcoop2.generic_project.web.input.SelectItem ){
			org.openspcoop2.generic_project.web.input.SelectItem newValue = (org.openspcoop2.generic_project.web.input.SelectItem) newValueObj;
			 ((PickListImpl)this.getDipartimento()).setSuggestionValue(newValue); 
		}
		
		//		log.debug("Dipartimento Selected Action Listener");
		SelectItem selit = ((PickListImpl) this.dipartimento).getSuggestionValue();

		//		log.debug("Dipartimento Scelto ["+selit+"]");
		if(selit != null){
			String codiceDipartimentoScelto = selit.getValue();

			this.mBean.selezionaDipartimento(codiceDipartimentoScelto);
		}
		//1Reset suggestion value
		((PickListImpl)this.dipartimento).setSuggestionValue(null);
	}

	public UtenteMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(UtenteMBean mBean) {
		this.mBean = mBean;
	}

	public SelectList getRuolo() {
		return this.ruolo;
	}

	public void setRuolo(SelectList ruolo) {
		this.ruolo = ruolo;
	}





}
