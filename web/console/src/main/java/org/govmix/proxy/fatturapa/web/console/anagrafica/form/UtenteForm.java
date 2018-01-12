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
package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.constants.UserType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.UtenteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.UtenteMBean;
import org.govmix.proxy.fatturapa.web.console.costanti.Costanti;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.govmix.proxy.fatturapa.web.console.util.input.FatturaPAFactory;
import org.govmix.proxy.fatturapa.web.console.util.input.factory.FatturaPAInputFactoryImpl;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.PickListImpl;
import org.openspcoop2.generic_project.web.input.BooleanCheckBox;
import org.openspcoop2.generic_project.web.input.FormField;
import org.openspcoop2.generic_project.web.input.InputSecret;
import org.openspcoop2.generic_project.web.input.PickList;
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
	private SelectList profilo;
	//	private SelectList ente;
	private BooleanCheckBox codicePCC= null;
	private BooleanCheckBox showUOandPCC = null;
	private PickList dipartimento = null; 

	private UtenteMBean mBean = null;

	private BooleanCheckBox utenteEsterno = null;
	private Text sistemaEsterno = null;
	private FormField<String> pagamentoIVA = null;
	private FormField<String> datiFattura = null;
	private FormField<String> inserimentoFattura = null;
	private FormField<String> statoFattura = null;
	private FormField<String> movimentiErarioIVA = null;
	private FormField<String> downloadDocumento = null;
	private FormField<String> consultazioneTracce = null;
	private FormField<String> pagamento = null;
	private FormField<String> stornoPagamento = null;
	private FormField<String> comunicazioneScadenza = null;
	private FormField<String> cancellazioneScadenze = null;
	private FormField<String> contabilizzazione = null;
	private FormField<String> stornoContabilizzazione = null;
	private FormField<String> ricezioneFattura = null;
	private FormField<String> rifiutoFattura = null;

	private List<PccOperazione> listaProprietaPCC;

	public UtenteForm()throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {
		this.setClosable(false);
		String nomeForm = "fUt";
		this.setId(nomeForm);
		this.setNomeForm(null); 

		WebGenericProjectFactory factory = new FatturaPAFactory(LoggerManager.getConsoleLogger()); 
		FatturaPAInputFactoryImpl inputFieldFactory = (FatturaPAInputFactoryImpl)factory.getInputFieldFactory();
		this.password = inputFieldFactory.createInputSecret("password","utente.form.password",null,true);
		this.password.setLabel2(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.password.confirm")); 
		this.password.setConfirm(true); 
		this.password.setRedisplay(true); 
		this.password.setStyleClass(Costanti.INPUT_LONG_STYLE_CLASS); 

		if(Utils.getLoginBean().isNoPasswordLogin()){
			this.password.setRendered(false);
		} else 
			this.password.setRendered(true);

		this.username = inputFieldFactory.createText("username","utente.form.username",null,true);
		this.username.setStyleClass(Costanti.INPUT_LONG_STYLE_CLASS); 
		this.nome = inputFieldFactory.createText("nome","utente.form.nome",null,false);
		this.nome.setStyleClass(Costanti.INPUT_LONG_STYLE_CLASS); 
		this.cognome = inputFieldFactory.createText("cognome","utente.form.cognome",null,false);
		this.cognome.setStyleClass(Costanti.INPUT_LONG_STYLE_CLASS); 

		this.dipartimento = inputFieldFactory.createPickList("dipartimento","utente.form.dipartimento",null,true);
		this.dipartimento.setNote(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("utente.form.dipartimento.note"));
		this.dipartimento.setForm(this);
		this.dipartimento.setAutoComplete(true);
		this.dipartimento.setEnableManualInput(true);
		((PickListImpl)this.dipartimento).setCheckDimensions(true); 
//		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif"
		((PickListImpl)this.dipartimento).setSuggestionBoxStyle("background-image: none !important;    width: 396x; margin-left: 8px;");

		this.ruolo = inputFieldFactory.createSelectList("ruolo","utente.form.ruolo",null,true);
		this.ruolo.setFieldsToUpdate(nomeForm +"_formPnl"); 
		this.ruolo.setForm(this); 

		this.profilo = inputFieldFactory.createSelectList("profilo","utente.form.profilo",null,true);
		this.codicePCC = inputFieldFactory.createBooleanCheckBox("codicePCC","utente.form.codicePCC",null,false);
		this.codicePCC.setDefaultValue(true);
		
		this.showUOandPCC = inputFieldFactory.createBooleanCheckBox("showUOandPCC","utente.form.showUOandPCC",null,false);



		this.utenteEsterno = inputFieldFactory.createBooleanCheckBox("utenteEsterno","utente.pcc.utenteEsterno",null,false);
		this.utenteEsterno.setFieldsToUpdate(nomeForm +"_formPnl"); 
		this.utenteEsterno.setForm(this);
		this.utenteEsterno.setLabelStyleClass(Costanti.LABEL_LONG_STYLE_CLASS);
		this.sistemaEsterno = inputFieldFactory.createText("sistemaEsterno","utente.pcc.sistemaEsterno",null,false);
		this.sistemaEsterno.setStyleClass(Costanti.INPUT_LONG_STYLE_CLASS); 
		this.sistemaEsterno.setLabelStyleClass(Costanti.LABEL_LONG_STYLE_CLASS);


		this.pagamentoIVA = inputFieldFactory.createRadioButtonFatturaPA("pIVA","utente.pcc.pagamentoIVA",null,false);
		this.datiFattura = inputFieldFactory.createRadioButtonFatturaPA("dF","utente.pcc.datiFattura",null,false);
		this.inserimentoFattura = inputFieldFactory.createRadioButtonFatturaPA("iF","utente.pcc.inserimentoFattura",null,false);
		this.statoFattura = inputFieldFactory.createRadioButtonFatturaPA("sF","utente.pcc.statoFattura",null,false);
		this.movimentiErarioIVA = inputFieldFactory.createRadioButtonFatturaPA("mEIVA","utente.pcc.movimentiErarioIVA",null,false);
		this.downloadDocumento = inputFieldFactory.createRadioButtonFatturaPA("dD","utente.pcc.downloadDocumento",null,false);
		this.consultazioneTracce = inputFieldFactory.createRadioButtonFatturaPA("cT","utente.pcc.consultazioneTracce",null,false);
		this.pagamento = inputFieldFactory.createRadioButtonFatturaPA("pag","utente.pcc.pagamento",null,false);
		this.stornoPagamento = inputFieldFactory.createRadioButtonFatturaPA("sP","utente.pcc.stornoPagamento",null,false);
		this.comunicazioneScadenza = inputFieldFactory.createRadioButtonFatturaPA("cS","utente.pcc.comunicazioneScadenza",null,false);
		this.cancellazioneScadenze = inputFieldFactory.createRadioButtonFatturaPA("ccS","utente.pcc.cancellazioneScadenze",null,false);
		this.contabilizzazione = inputFieldFactory.createRadioButtonFatturaPA("co","utente.pcc.contabilizzazione",null,false);
		this.stornoContabilizzazione = inputFieldFactory.createRadioButtonFatturaPA("sC","utente.pcc.stornoContabilizzazione",null,false);
		this.ricezioneFattura = inputFieldFactory.createRadioButtonFatturaPA("rF","utente.pcc.ricezioneFattura",null,false);
		this.rifiutoFattura = inputFieldFactory.createRadioButtonFatturaPA("rrF","utente.pcc.rifiutoFattura",null,false);

		this._setRuolo();
		this._setUtenteEsterno();

		this.setField(this.password);
		this.setField(this.username);
		this.setField(this.nome);
		this.setField(this.cognome);
		this.setField(this.dipartimento);
		this.setField(this.ruolo);
		this.setField(this.profilo);
		//		this.setField(this.ente);

		this.setField(this.utenteEsterno);
		this.setField(this.sistemaEsterno);
		this.setField(this.pagamentoIVA);
		this.setField(this.datiFattura);
		this.setField(this.inserimentoFattura);
		this.setField(this.statoFattura);
		this.setField(this.movimentiErarioIVA);
		this.setField(this.downloadDocumento);
		this.setField(this.consultazioneTracce);
		this.setField(this.pagamento);
		this.setField(this.stornoPagamento);
		this.setField(this.comunicazioneScadenza);
		this.setField(this.cancellazioneScadenze);
		this.setField(this.contabilizzazione);
		this.setField(this.stornoContabilizzazione);
		this.setField(this.ricezioneFattura);
		this.setField(this.rifiutoFattura);
	}

	@Override
	public void reset() {
		this.cognome.reset();
		this.username.reset();
		this.nome.reset();
		this.password.reset();
		this.dipartimento.reset();
		this.ruolo.reset();
		this.profilo.reset();
		//		this.ente.reset();

		this.codicePCC.reset();
		this.showUOandPCC.reset();
		this.utenteEsterno.reset();

		this._setRuolo();
		this._setUtenteEsterno();
		this.sistemaEsterno.reset();


		this.pagamentoIVA.reset();
		this.movimentiErarioIVA.reset();
		this.downloadDocumento.reset();

		this.datiFattura.reset();
		this.inserimentoFattura.reset();
		this.statoFattura.reset();
		this.consultazioneTracce.reset();
		this.pagamento.reset();
		this.stornoPagamento.reset();
		this.comunicazioneScadenza.reset();
		this.cancellazioneScadenze.reset();
		this.contabilizzazione.reset();
		this.stornoContabilizzazione.reset();
		this.ricezioneFattura.reset();
		this.rifiutoFattura.reset();
	}

	/**
	 * Inizializza la form con i valori dell'elemento selezionato.
	 * 
	 * @param bean
	 */
	@Override
	public void setObject(Object arg0) throws Exception {

		List<PccUtenteOperazione> listaProprietaAbilitate = new ArrayList<PccUtenteOperazione>();
		// Aggiornamento
		if(arg0 != null){
			UtenteBean bean = (UtenteBean) arg0;
			this.cognome.setDefaultValue(bean.getDTO().getCognome()); 
			this.nome.setDefaultValue(bean.getDTO().getNome());
			this.username.setDefaultValue(bean.getDTO().getUsername());
			this.username.setDisabled(true);

			this.password.setDefaultValue(bean.getDTO().getPassword());
			this.password.setDefaultValue2(bean.getDTO().getPassword());

			List<SelectItem> listaDipartimentiEnte = new ArrayList<SelectItem>();
			for (DipartimentoBean dbean : bean.getListaDipartimenti()) {
				Dipartimento dipartimento = dbean.getDTO();
				String descrizioneConCodice = dipartimento.getDescrizione()+ " (" + dipartimento.getCodice() + ")";
				org.openspcoop2.generic_project.web.input.SelectItem item = new org.openspcoop2.generic_project.web.input.SelectItem(
						dipartimento.getCodice(),descrizioneConCodice.trim());
				listaDipartimentiEnte.add(item);
			}
			this.dipartimento.setDefaultValue(listaDipartimentiEnte);

			UserRole role = bean.getDTO().getRole();

			boolean showDipEPcc = true;

			if(role!= null){
				if(role.equals(UserRole.ADMIN)){
					this.ruolo.setDefaultValue(new SelectItem(UserRole.ADMIN.getValue(),UserRole.ADMIN.toString()));
					showDipEPcc = false;
				}else if(role.equals(UserRole.DEPT_ADMIN))
					this.ruolo.setDefaultValue(new SelectItem(UserRole.DEPT_ADMIN.getValue(),UserRole.DEPT_ADMIN.toString()));
				else 
					this.ruolo.setDefaultValue(new SelectItem(UserRole.USER.getValue(),UserRole.USER.toString()));
			}else {
				this.ruolo.setDefaultValue(null);
				showDipEPcc = false;
			}

			this.showUOandPCC.setDefaultValue(showDipEPcc); 

			UserType tipo = bean.getDTO().getTipo();

			if(tipo!= null){
				if(tipo.equals(UserType.ESTERNO))
					this.profilo.setDefaultValue(new SelectItem(UserType.ESTERNO.getValue(),("utente.form.profilo.esterno")));
				else 
					this.profilo.setDefaultValue(new SelectItem(UserType.INTERNO.getValue(),("utente.form.profilo.interno")));
			}else 
				this.profilo.setDefaultValue(new SelectItem(UserType.INTERNO.getValue(),("utente.form.profilo.interno")));

			this.utenteEsterno.setDefaultValue(bean.getDTO().isEsterno()); 

			this.sistemaEsterno.setDefaultValue(bean.getDTO().getSistema());  



			// prelevo le properties abilitate
			listaProprietaAbilitate = bean.getListaProprietaAbilitate();
			// proprieta
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.DATI_FATTURA, this.datiFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.INSERIMENTO_FATTURA, this.inserimentoFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.STATO_FATTURA, this.statoFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CP, this.pagamento,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SP, this.stornoPagamento,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CS, this.comunicazioneScadenza,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS, this.cancellazioneScadenze,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CO, this.contabilizzazione,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC, this.stornoContabilizzazione,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RC, this.ricezioneFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RF, this.rifiutoFattura,true);

			this._setRuolo();
			this._setUtenteEsterno();

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
			this.profilo.setDefaultValue(new SelectItem(UserType.INTERNO.getValue(),("utente.form.profilo.interno")));
			//			this.ente.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO)); 
			this.dipartimento.setDefaultValue(new ArrayList<SelectItem>());

			this.showUOandPCC.setDefaultValue(false);

			this.utenteEsterno.setDefaultValue(null); 
			this.sistemaEsterno.setDefaultValue(null); 

			// tutte le properties false
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.DATI_FATTURA, this.datiFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.INSERIMENTO_FATTURA, this.inserimentoFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.STATO_FATTURA, this.statoFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CP, this.pagamento,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SP, this.stornoPagamento,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CS, this.comunicazioneScadenza,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS, this.cancellazioneScadenze,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CO, this.contabilizzazione,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC, this.stornoContabilizzazione,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RC, this.ricezioneFattura,true);
			Utils.impostaValoreProprietaPCCUtenteForm(this.listaProprietaPCC, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RF, this.rifiutoFattura,true);

			this._setRuolo();
			this._setUtenteEsterno();
		}

		this.reset();
	}

	public String valida (){

		String _username = this.username.getValue();
		if(StringUtils.isEmpty(_username))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.username.getLabel());

		if(!Utils.getLoginBean().isNoPasswordLogin()){
			String _password = this.password.getValue();
			if(StringUtils.isEmpty(_password))
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel());

			String _password2 = this.password.getValue2();
			if(StringUtils.isEmpty(_password2))
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.password.getLabel2());

			if(!_password.equals(_password2))
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_CONFERMA_NON_UGUALE, this.password.getLabel(), this.password.getLabel2());
		}

		// Ruolo
		SelectItem _ruolo = this.ruolo.getValue();

		String valueRuolo = null; 

		if(_ruolo != null)
			valueRuolo = _ruolo.getValue();

		if( valueRuolo == null || (valueRuolo != null && valueRuolo.equals(CostantiForm.NON_SELEZIONATO)))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.ruolo.getLabel());

		UserRole userRole = UserRole.toEnumConstant(valueRuolo);

		// Se l'utente non e' superuser deve avere almeno un dipartimento associato.
		if(!userRole.equals(UserRole.ADMIN)){
			List<SelectItem> value = this.dipartimento.getValue();
			if(value == null || (value != null && value.size() <= 0))
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.dipartimento.getLabel());
		}

		// Profilo
		SelectItem _profilo = this.profilo.getValue();

		String valueProfilo = null; 

		if(_profilo != null)
			valueProfilo = _profilo.getValue();

		if( valueProfilo == null || (valueProfilo != null && valueProfilo.equals(CostantiForm.NON_SELEZIONATO)))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.profilo.getLabel());

		// validazione pcc
		if(this.codicePCC.getValue()){
			boolean mod = this.utenteEsterno.getValue() != null ? (this.utenteEsterno.getValue() ? true : false) : false;

			if(mod){
				String _sistema = this.sistemaEsterno.getValue();
				if(StringUtils.isEmpty(_sistema))
					return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.sistemaEsterno.getLabel());
			}
		}

		return null;
	}

	
	@Override
	public Object getObject() throws Exception {
		Utente utente = new Utente();

		utente.setCognome(this.cognome.getValue());
		utente.setNome(this.nome.getValue());
		//utente.setPassword(this.password.getValue());
		utente.setUsername(this.username.getValue());

		SelectItem _ruolo = this.ruolo.getValue();
		String valueRuolo = _ruolo.getValue();
		UserRole userRole = UserRole.toEnumConstant(valueRuolo);

		utente.setRole(userRole);

		SelectItem _profilo = this.profilo.getValue();

		String valueProfilo = _profilo.getValue(); 
		UserType tipo = UserType.toEnumConstant(valueProfilo);

		utente.setTipo(tipo);

		//		IdEnte idEnte = new IdEnte();
		//
		//		SelectItem enteSI = this.ente.getValue();
		//		if(enteSI!= null){
		//			String _ente = enteSI.getValue();
		//			idEnte.setNome(_ente);
		//			utente.setEnte(idEnte);
		//		}

		if(!userRole.equals(UserRole.ADMIN)){
			for (SelectItem item : this.dipartimento.getValue()) {
				String codiceDipartimento = item.getValue();

				IdDipartimento idDipartimento = new IdDipartimento();
				idDipartimento.setCodice(codiceDipartimento);

				UtenteDipartimento utenteDipartimento = new UtenteDipartimento();

				utenteDipartimento.setIdDipartimento(idDipartimento); 
				utente.addUtenteDipartimento(utenteDipartimento );
			}
		}
		
		if(this.codicePCC.getValue()){
			boolean esterno = this.utenteEsterno.getValue() != null ? (this.utenteEsterno.getValue() ? true : false) : false;

			utente.setEsterno(esterno);
			if(esterno){
				String _sistema = this.sistemaEsterno.getValue();
				utente.setSistema(_sistema);
			} else
				utente.setSistema(null);
		}
		return utente;
	}

	public List<PccUtenteOperazione> getOperazioniAbilitate(){
		List<PccUtenteOperazione> list = new ArrayList<PccUtenteOperazione>();

		IdUtente idUtente = new IdUtente();
		idUtente.setUsername(this.getUsername().getValue());

		SelectItem _ruolo = this.ruolo.getValue();
		String valueRuolo = _ruolo.getValue();
		UserRole userRole = UserRole.toEnumConstant(valueRuolo);

		if(userRole.equals(UserRole.ADMIN)){
			this.pagamentoIVA.setValue("commons.label.abilitata");
			this.datiFattura.setValue("commons.label.abilitata");
			this.inserimentoFattura.setValue("commons.label.abilitata");
			this.statoFattura.setValue("commons.label.abilitata");
			this.movimentiErarioIVA.setValue("commons.label.abilitata");
			this.downloadDocumento.setValue("commons.label.abilitata");
			this.consultazioneTracce.setValue("commons.label.abilitata");
			this.pagamento.setValue("commons.label.abilitata");
			this.stornoPagamento.setValue("commons.label.abilitata");
			this.comunicazioneScadenza.setValue("commons.label.abilitata");
			this.cancellazioneScadenze.setValue("commons.label.abilitata");
			this.contabilizzazione.setValue("commons.label.abilitata");
			this.stornoContabilizzazione.setValue("commons.label.abilitata");
			this.ricezioneFattura.setValue("commons.label.abilitata");
			this.rifiutoFattura.setValue("commons.label.abilitata");
		}

		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.DATI_FATTURA, this.datiFattura);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.INSERIMENTO_FATTURA, this.inserimentoFattura);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.STATO_FATTURA, this.statoFattura);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_CP, this.pagamento);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_SP, this.stornoPagamento);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_CS, this.comunicazioneScadenza);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS, this.cancellazioneScadenze);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_CO, this.contabilizzazione);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC, this.stornoContabilizzazione);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_RC, this.ricezioneFattura);
		Utils.addSceltaUtente(list, idUtente, NomePccOperazioneType.OPERAZIONE_CONTABILE_RF, this.rifiutoFattura);



		return list;
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

	public void dipartimentoSelectListener(ActionEvent ae){
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

	public void ruoloSelectListener(ActionEvent ae){
		this._setRuolo();
		this._setUtenteEsterno();
	}

	private void _setRuolo(){

		this.dipartimento.setRendered(false);
		this.pagamentoIVA.setRendered(false);
		this.movimentiErarioIVA.setRendered(false);
		this.downloadDocumento.setRendered(false);
		this.datiFattura.setRendered(false);
		this.inserimentoFattura.setRendered(false);
		this.statoFattura.setRendered(false);
		this.consultazioneTracce.setRendered(false);
		this.pagamento.setRendered(false);
		this.stornoPagamento.setRendered(false);
		this.comunicazioneScadenza.setRendered(false);
		this.cancellazioneScadenze.setRendered(false);
		this.contabilizzazione.setRendered(false);
		this.stornoContabilizzazione.setRendered(false);
		this.ricezioneFattura.setRendered(false);
		this.rifiutoFattura.setRendered(false);
		this.showUOandPCC.setValue(false);

		SelectItem value = this.ruolo.getValue();
		String value2 = null;
		if(value!= null){
			value2 = value.getValue();
		}
		if((value2 == null || (value2!=null && !(value2.equals(CostantiForm.NON_SELEZIONATO) || value2.equals(UserRole.ADMIN.getValue())) ))){
			this.showUOandPCC.setValue(true);
			this.dipartimento.setRendered(true);
			this.pagamentoIVA.setRendered(true);
			this.movimentiErarioIVA.setRendered(true);
			this.downloadDocumento.setRendered(true);
			this.datiFattura.setRendered(true);
			this.inserimentoFattura.setRendered(true);
			this.statoFattura.setRendered(true);
			this.consultazioneTracce.setRendered(true);
			this.pagamento.setRendered(true);
			this.stornoPagamento.setRendered(true);
			this.comunicazioneScadenza.setRendered(true);
			this.cancellazioneScadenze.setRendered(true);
			this.contabilizzazione.setRendered(true);
			this.stornoContabilizzazione.setRendered(true);
			this.ricezioneFattura.setRendered(true);
			this.rifiutoFattura.setRendered(true);
		}

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

	public SelectList getProfilo() {
		return this.profilo;
	}

	public void setProfilo(SelectList profilo) {
		this.profilo = profilo;
	}

	public  FormField<String>  getPagamentoIVA() {
		return this.pagamentoIVA;
	}

	public void setPagamentoIVA(FormField<String>  pagamentoIVA) {
		this.pagamentoIVA = pagamentoIVA;
	}



	public BooleanCheckBox getUtenteEsterno() {
		return utenteEsterno;
	}

	public void setUtenteEsterno(BooleanCheckBox utenteEsterno) {
		this.utenteEsterno = utenteEsterno;
	}

	public Text getSistemaEsterno() {
		this.sistemaEsterno.setRequired(false);
		boolean mod = this.utenteEsterno.getValue() != null ? (this.utenteEsterno.getValue() ? true : false) : false;

		if(mod)
			this.sistemaEsterno.setRequired(true);

		return sistemaEsterno;
	}

	public void setSistemaEsterno(Text sistemaEsterno) {
		this.sistemaEsterno = sistemaEsterno;
	}



	public FormField<String> getDatiFattura() {
		return datiFattura;
	}

	public void setDatiFattura(FormField<String> datiFattura) {
		this.datiFattura = datiFattura;
	}

	public FormField<String> getInserimentoFattura() {
		return inserimentoFattura;
	}

	public void setInserimentoFattura(FormField<String> inserimentoFattura) {
		this.inserimentoFattura = inserimentoFattura;
	}

	public FormField<String> getStatoFattura() {
		return statoFattura;
	}

	public void setStatoFattura(FormField<String> statoFattura) {
		this.statoFattura = statoFattura;
	}

	public FormField<String> getMovimentiErarioIVA() {
		return movimentiErarioIVA;
	}

	public void setMovimentiErarioIVA(FormField<String> movimentiErarioIVA) {
		this.movimentiErarioIVA = movimentiErarioIVA;
	}

	public FormField<String> getDownloadDocumento() {
		return downloadDocumento;
	}

	public void setDownloadDocumento(FormField<String> downloadDocumento) {
		this.downloadDocumento = downloadDocumento;
	}

	public FormField<String> getConsultazioneTracce() {
		return consultazioneTracce;
	}

	public void setConsultazioneTracce(FormField<String> consultazioneTracce) {
		this.consultazioneTracce = consultazioneTracce;
	}

	public FormField<String> getPagamento() {
		return pagamento;
	}

	public void setPagamento(FormField<String> pagamento) {
		this.pagamento = pagamento;
	}

	public FormField<String> getStornoPagamento() {
		return stornoPagamento;
	}

	public void setStornoPagamento(FormField<String> stornoPagamento) {
		this.stornoPagamento = stornoPagamento;
	}

	public FormField<String> getComunicazioneScadenza() {
		return comunicazioneScadenza;
	}

	public void setComunicazioneScadenza(FormField<String> comunicazioneScadenza) {
		this.comunicazioneScadenza = comunicazioneScadenza;
	}

	public FormField<String> getCancellazioneScadenze() {
		return cancellazioneScadenze;
	}

	public void setCancellazioneScadenze(FormField<String> cancellazioneScadenze) {
		this.cancellazioneScadenze = cancellazioneScadenze;
	}

	public FormField<String> getContabilizzazione() {
		return contabilizzazione;
	}

	public void setContabilizzazione(FormField<String> contabilizzazione) {
		this.contabilizzazione = contabilizzazione;
	}

	public FormField<String> getStornoContabilizzazione() {
		return stornoContabilizzazione;
	}

	public void setStornoContabilizzazione(FormField<String> stornoContabilizzazione) {
		this.stornoContabilizzazione = stornoContabilizzazione;
	}

	public FormField<String> getRicezioneFattura() {
		return ricezioneFattura;
	}

	public void setRicezioneFattura(FormField<String> ricezioneFattura) {
		this.ricezioneFattura = ricezioneFattura;
	}

	public FormField<String> getRifiutoFattura() {
		return rifiutoFattura;
	}

	public void setRifiutoFattura(FormField<String> rifiutoFattura) {
		this.rifiutoFattura = rifiutoFattura;
	}

	public List<PccOperazione> getListaProprietaPCC() {
		return listaProprietaPCC;
	}

	public void setListaProprietaPCC(List<PccOperazione> listaProprietaPCC) {
		this.listaProprietaPCC = listaProprietaPCC;
	}

	public BooleanCheckBox getCodicePCC() {
		return codicePCC;
	}

	public void setCodicePCC(BooleanCheckBox codicePCC) {
		this.codicePCC = codicePCC;
	}

	public BooleanCheckBox getShowUOandPCC() {
		return showUOandPCC;
	}

	public void setShowUOandPCC(BooleanCheckBox showUOandPCC) {
		this.showUOandPCC = showUOandPCC;
	}

	public void utenteEsternoOnChangeListener(ActionEvent ae){
		this._setUtenteEsterno();
	}

	private void _setUtenteEsterno() {
		this.sistemaEsterno.setRequired(false);
		this.sistemaEsterno.setRendered(false);

		boolean mod = this.utenteEsterno.getValue() != null ? (this.utenteEsterno.getValue() ? true : false) : false;

		if(mod){
			this.sistemaEsterno.setRequired(true);
			this.sistemaEsterno.setRendered(true);
		}
	}

}
