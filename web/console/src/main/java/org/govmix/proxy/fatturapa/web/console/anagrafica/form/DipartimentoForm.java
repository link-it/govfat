/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.DipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.DipartimentoMBean;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.govmix.proxy.fatturapa.web.console.util.input.FatturaPAFactory;
import org.govmix.proxy.fatturapa.web.console.util.input.factory.FatturaPAInputFactoryImpl;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.input.BooleanCheckBox;
import org.openspcoop2.generic_project.web.input.FormField;
import org.openspcoop2.generic_project.web.input.InputSecret;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;
import org.openspcoop2.generic_project.web.input.TextArea;

public class DipartimentoForm extends BaseForm implements Form,Serializable{


	public static final String BOOL_PROP_TYPE_PREFIX = "bool_";
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
	private SelectList<SelectItem> ente = null; 
	private BooleanCheckBox codicePCC= null;
	private boolean showProperties = false;

	private SelectList<SelectItem> registro = null; 

	private List<FormField<?>> properties = null;

	private TextArea indirizziNotifica = null;

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
	
	private BooleanCheckBox fatturazioneAttiva= null;
	private BooleanCheckBox firmaAutomatica= null;
	private FormField<String> codiceProcedimento = null;
	private FormField<String> codiceProcedimentoB2B = null;

	private List<DipartimentoProperty> listaNomiProperties = null;

	public static final String DIPARTIMENTO_PATTERN = "[A-Z0-9]{6,7}";
	
	public static final String CF_TRASMITTENTE_PATTERN = "[0-9]{11}";
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	private Pattern dipartimentoPattern = null;
	private Pattern emailPattern = null;
	private List<PccOperazione> listaProprietaConsentiteAiDipartimenti;

	private DipartimentoMBean mbean = null;
	
	private List<String> nomiPropertiesObbligatorie = null;

	public DipartimentoForm (){
		this.dipartimentoPattern = Pattern.compile(DipartimentoForm.DIPARTIMENTO_PATTERN);
		this.emailPattern = Pattern.compile(EMAIL_PATTERN);
		this.init();
	}

	@Override
	public void init() {
		this.setClosable(false);
		this.setId("formDipartimento");
		this.setNomeForm(null); 
		try{
			WebGenericProjectFactory factory = new FatturaPAFactory(LoggerManager.getConsoleLogger()); 

			FatturaPAInputFactoryImpl inputFieldFactory = (FatturaPAInputFactoryImpl)factory.getInputFieldFactory();

			this.codice = inputFieldFactory.createText("codice","dipartimento.form.codice",null,true);
			this.descrizione = inputFieldFactory.createText("descrizione","dipartimento.form.descrizione",null,true);
			this.modalitaPush = inputFieldFactory.createBooleanCheckBox("modalitaPush","dipartimento.form.modalitaPush",null,false);
			this.modalitaPush.setFieldsToUpdate(this.getId() + "_formPnl"); 
			this.modalitaPush.setForm(this); 

			this.endpoint = inputFieldFactory.createText("endpoint","dipartimento.form.endpoint",null,true);
			this.password = inputFieldFactory.createInputSecret("password","dipartimento.form.password",null,false);
			this.username = inputFieldFactory.createText("username","dipartimento.form.username",null,false);

			this.notificaAutomatica = inputFieldFactory.createBooleanCheckBox("notificaAutomatica","dipartimento.form.notificaAutomatica",null,false);
			this.ente = inputFieldFactory.createSelectList("ente","dipartimento.form.ente",null,true);
			this.ente.setFieldsToUpdate(this.getId() + "_formPnl"); 
			this.ente.setForm(this); 

			this.codicePCC = inputFieldFactory.createBooleanCheckBox("codicePCC","dipartimento.form.codicePCC",null,false);

			this.registro = inputFieldFactory.createSelectList("registro","dipartimento.form.registro",null,false);
			this.registro.setFieldsToUpdate(this.getId() + "_formPnl"); 
			this.registro.setForm(this); 

			


			this.indirizziNotifica = inputFieldFactory.createTextArea("indirizziNotifica","dipartimento.pcc.indirizziNotifica",null,true);
			this.indirizziNotifica.setNote("dipartimento.pcc.indirizziNotifica.note");
			
			this.pagamentoIVA = inputFieldFactory.createRadioButtonFatturaPA("pagamentoIVA","dipartimento.pcc.pagamentoIVA",null,false);
			this.datiFattura = inputFieldFactory.createRadioButtonFatturaPA("datiFattura","dipartimento.pcc.datiFattura",null,false);
			this.inserimentoFattura = inputFieldFactory.createRadioButtonFatturaPA("inserimentoFattura","dipartimento.pcc.inserimentoFattura",null,false);
			this.statoFattura = inputFieldFactory.createRadioButtonFatturaPA("statoFattura","dipartimento.pcc.statoFattura",null,false);
			this.movimentiErarioIVA = inputFieldFactory.createRadioButtonFatturaPA("movimentiErarioIVA","dipartimento.pcc.movimentiErarioIVA",null,false);
			this.downloadDocumento = inputFieldFactory.createRadioButtonFatturaPA("downloadDocumento","dipartimento.pcc.downloadDocumento",null,false);
			this.consultazioneTracce = inputFieldFactory.createRadioButtonFatturaPA("consultazioneTracce","dipartimento.pcc.consultazioneTracce",null,false);
			
			this.pagamento = inputFieldFactory.createRadioButtonFatturaPA("pagamento","dipartimento.pcc.pagamento", null, false);
			this.stornoPagamento = inputFieldFactory.createRadioButtonFatturaPA("stornoPagamento","dipartimento.pcc.stornoPagamento", null, false);
			this.comunicazioneScadenza = inputFieldFactory.createRadioButtonFatturaPA("comunicazioneScadenza","dipartimento.pcc.comunicazioneScadenza", null, false);
			this.cancellazioneScadenze = inputFieldFactory.createRadioButtonFatturaPA("cancellazioneScadenze","dipartimento.pcc.cancellazioneScadenze", null, false);
			this.contabilizzazione = inputFieldFactory.createRadioButtonFatturaPA("contabilizzazione","dipartimento.pcc.contabilizzazione", null, false);
			this.stornoContabilizzazione = inputFieldFactory.createRadioButtonFatturaPA("stornoContabilizzazione","dipartimento.pcc.stornoContabilizzazione", null, false);
			this.ricezioneFattura = inputFieldFactory.createRadioButtonFatturaPA("ricezioneFattura","dipartimento.pcc.ricezioneFattura", null, false);
			this.rifiutoFattura =  inputFieldFactory.createRadioButtonFatturaPA("rifiutoFattura","dipartimento.pcc.rifiutoFattura", null, false);
			
			this.fatturazioneAttiva = inputFieldFactory.createBooleanCheckBox("fatturazioneAttiva","dipartimento.form.fatturazioneAttiva",null,false);
			this.fatturazioneAttiva.setFieldsToUpdate(this.getId() + "_formPnl"); 
			this.fatturazioneAttiva.setForm(this); 
			this.firmaAutomatica = inputFieldFactory.createBooleanCheckBox("firmaAutomatica","dipartimento.form.firmaAutomatica",null,false);
			this.codiceProcedimento = inputFieldFactory.createText("codiceProcedimento","dipartimento.form.codiceProcedimento",null,false);
			this.codiceProcedimentoB2B = inputFieldFactory.createText("codiceProcedimentoB2B","dipartimento.form.codiceProcedimentoB2B",null,false);

			
			this._setModalitaPush();
			this._setFatturazioneAttiva();

			this.setField(this.codice);
			this.setField(this.descrizione);
			this.setField(this.modalitaPush);
			this.setField(this.endpoint);
			this.setField(this.password);
			this.setField(this.username);
			this.setField(this.notificaAutomatica);
			this.setField(this.registro);
			this.setField(this.ente);
			this.setField(this.codicePCC);

			this.setField(this.indirizziNotifica);
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
			
			this.setField(this.fatturazioneAttiva);
			this.setField(this.firmaAutomatica);
			this.setField(this.codiceProcedimento);
			this.setField(this.codiceProcedimentoB2B);
			
			this.properties = new ArrayList<FormField<?>>();
			
			this.nomiPropertiesObbligatorie = ConsoleProperties.getInstance(LoggerManager.getConsoleLogger()).getListaNomiPropertiesDipartimentoObbligatorie();

		}catch(Exception e){

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
		this.ente .reset();
		this.registro .reset();
		this.codicePCC.reset();
		
		this._setModalitaPush();
		
		this.fatturazioneAttiva.reset();
		this.firmaAutomatica.reset();
		this.codiceProcedimento.reset();
		this.codiceProcedimentoB2B.reset();
		
		this._setFatturazioneAttiva();
		this._abilitaRegistro();
		
		for (FormField<?> prop : this.properties) {
			prop.reset();
		}

		this.resetSezionePCC();
	}

	private void resetSezionePCC() {

		this.pagamentoIVA.reset();
		this.movimentiErarioIVA.reset();
		this.downloadDocumento.reset();
		this.indirizziNotifica.reset();
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
	public void setValues(DipartimentoBean bean){
		try{
			WebGenericProjectFactory factory =   this.getWebGenericProjectFactory();
			List<PccDipartimentoOperazione> listaProprietaAbilitate = new ArrayList<PccDipartimentoOperazione>();
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

				IdEnte idEnte = bean.getDTO().getEnte();
				if(idEnte != null){
					this.ente.setDefaultValue(new SelectItem(idEnte.getNome(), idEnte.getNome()));
				} else {
					this.ente.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
				}

				EnteBean enteBean = bean.getEnteBean();

				this.codicePCC.setDefaultValue(enteBean.getIdPccAmministrazione().getValue() != null);

				IdRegistro registro2 = bean.getDTO().getRegistro();
				if(registro2 != null){
					this.registro.setDefaultValue(new SelectItem(registro2.getNome(), registro2.getNome()));
				}else {
					this.registro.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
				}

				this.properties.clear();
				FormField<?> proprieta = null;
				for (DipartimentoPropertyValue prop : bean.getDTO().getDipartimentoPropertyValueList()) {
					if(prop.getIdProperty().getNome().startsWith(BOOL_PROP_TYPE_PREFIX)) {
						proprieta = factory.getInputFieldFactory().createBooleanCheckBox();
					} else {
						proprieta = factory.getInputFieldFactory().createText();
					}

					proprieta.setRequired(mod && this.nomiPropertiesObbligatorie.contains(prop.getIdProperty().getNome())); 
					proprieta.setLabel(prop.getIdProperty().getNome());
					proprieta.setName("prop_" + prop.getIdProperty().getNome());
					
					if(prop.getIdProperty().getNome().startsWith(BOOL_PROP_TYPE_PREFIX)) {
						((BooleanCheckBox)proprieta).setDefaultValue(prop.getValore() != null && prop.getValore().equals("TRUE"));
					} else {
						((Text)proprieta).setDefaultValue(prop.getValore());
					}

					this.properties.add(proprieta);
				}


				this.indirizziNotifica.setDefaultValue(bean.getDTO().getListaEmailNotifiche());
				
				this.fatturazioneAttiva.setDefaultValue(bean.getDTO().isFatturazioneAttiva());
				this.firmaAutomatica.setDefaultValue(bean.getDTO().isFirmaAutomatica());
				this.codiceProcedimento.setDefaultValue(bean.getDTO().getIdProcedimento()); 
				this.codiceProcedimentoB2B.setDefaultValue(bean.getDTO().getIdProcedimentoB2B()); 

				// prelevo le properties abilitate
				listaProprietaAbilitate = bean.getListaProprietaAbilitate();
				// proprieta
				Utils.impostaValoreProprietaPCCDipartimentoForm(this.listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.DATI_FATTURA, this.datiFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.INSERIMENTO_FATTURA,this.inserimentoFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.STATO_FATTURA,this.statoFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(this.listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(this.listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CP,this.pagamento,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SP,this.stornoPagamento,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CS,this.comunicazioneScadenza,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS,this.cancellazioneScadenze,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CO,this.contabilizzazione,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC,this.stornoContabilizzazione,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RC,this.ricezioneFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RF,this.rifiutoFattura,true);

			} else {
				// Nuovo Elemento
				this.codice.setDisabled(false);
				this.codice.setDefaultValue(null);
				this.descrizione.setDefaultValue(null);
				this.modalitaPush.setDefaultValue(null);
				this.notificaAutomatica.setDefaultValue(null);
				this.ente.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
				this.registro.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));

				this.codicePCC.setDefaultValue(false);

				this.indirizziNotifica.setDefaultValue(null); 
				
				this.fatturazioneAttiva.setDefaultValue(false);
				this.firmaAutomatica.setDefaultValue(false);
				this.codiceProcedimento.setDefaultValue(null); 
				this.codiceProcedimentoB2B.setDefaultValue(null); 

				// tutte le properties false
				Utils.impostaValoreProprietaPCCDipartimentoForm(this.listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.DATI_FATTURA, this.datiFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.INSERIMENTO_FATTURA,this.inserimentoFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.STATO_FATTURA,this.statoFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(this.listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(this.listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CP,this.pagamento,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SP,this.stornoPagamento,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CS,this.comunicazioneScadenza,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS,this.cancellazioneScadenze,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CO,this.contabilizzazione,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC,this.stornoContabilizzazione,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RC,this.ricezioneFattura,true);
				Utils.impostaValoreProprietaPCCDipartimentoForm(listaProprietaConsentiteAiDipartimenti, listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RF,this.rifiutoFattura,true);

			}
		}catch(Exception e){

		}

		this.reset();
	}
	
	public void setListaNomiProperties(List<DipartimentoProperty> listaProperties){
		this.setListaNomiProperties(listaProperties, true); 
	}
	public void setListaNomiProperties(List<DipartimentoProperty> listaProperties, boolean reset){

		this.listaNomiProperties = listaProperties;

		// se e' nuovo creo l'elenco delle properties
		if(this.listaNomiProperties != null && this.listaNomiProperties.size() > 0){
			try{
				WebGenericProjectFactory factory =  this.getWebGenericProjectFactory();

				String namePrefix = "prop_";
				if(this.codice.getDefaultValue() == null){
					FormField<?> proprieta = null;
					for (DipartimentoProperty dipartimentoProperty : this.listaNomiProperties) {
						if(dipartimentoProperty.getNome().startsWith(BOOL_PROP_TYPE_PREFIX)) {
							proprieta = factory.getInputFieldFactory().createBooleanCheckBox();
						} else {
							proprieta = factory.getInputFieldFactory().createText();
						}
						proprieta.setRequired(false);
						proprieta.setLabel(dipartimentoProperty.getLabel());
						proprieta.setName(namePrefix + dipartimentoProperty.getNome());

						this.properties.add(proprieta);
					}
				} else {

					// Modifica, aggiorno solo la label (in questo momento coincide con il nome, in futuro puo' essere anche diversa)
					for (DipartimentoProperty dipartimentoProperty : this.listaNomiProperties) {
						boolean found = false;
						for (FormField<?> proprieta : this.properties) {
							//	if(proprieta.getLabel().equals(dipartimentoProperty.getLabel())){
							if(proprieta.getName().equals(namePrefix+dipartimentoProperty.getNome())){
								proprieta.setLabel(dipartimentoProperty.getLabel());
								found = true;
								break;
							}
						}

						if(!found){
							FormField<?> proprieta = null;
							if(dipartimentoProperty.getNome().startsWith(BOOL_PROP_TYPE_PREFIX)) {
								proprieta = factory.getInputFieldFactory().createBooleanCheckBox();
							} else {
								proprieta = factory.getInputFieldFactory().createText();
							}
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
		if(reset)
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


	public String valida (){

		String _codice = this.codice.getValue();
		if(StringUtils.isEmpty(_codice))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.codice.getLabel());

		Matcher matcher = this.dipartimentoPattern.matcher(_codice);

		if(!matcher.matches())
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.formatoCodiceErrato");


		String _descrizione = this.descrizione.getValue();
		if(StringUtils.isEmpty(_descrizione))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());



		SelectItem enteSI = this.ente.getValue();
		if(enteSI!= null){
			String _ente = enteSI.getValue();

			if(_ente.equals(CostantiForm.NON_SELEZIONATO))
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO, this.ente.getLabel());
		}

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		boolean fattAttiva = this.fatturazioneAttiva.getValue() != null ? (this.getFatturazioneAttiva().getValue() ? true : false) : false;

		SelectItem registroSI = this.registro.getValue();
		if(registroSI!= null){
			String _registro = registroSI.getValue();

			// [TODO] attivare
			if(_registro.equals(CostantiForm.NON_SELEZIONATO) && (mod)) // && (mod || fattAttiva))
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO, this.registro.getLabel());
		}

		//
		if(mod){

			for (FormField<?> prop : this.properties) {
				if(prop instanceof Text) {
					String _valore = ((Text)prop).getValue();
					if(prop.isRequired() && StringUtils.isEmpty(_valore))
						return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, prop.getLabel());
//				} else if(prop instanceof BooleanCheckBox) {
//					Boolean _valore = ((BooleanCheckBox)prop).getValue();
//					if(prop.isRequired() && StringUtils.isEmpty(_valore))
//						return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, prop.getLabel());
				}
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

		// validazione pcc

		if(this.codicePCC.getValue()){
			//validazione degli indirizzi
			String _ind = this.indirizziNotifica.getValue();
			if(StringUtils.isEmpty(_ind))
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.indirizziNotifica.getLabel());
			
			String[] split = _ind.split(",");
			
			if(split != null){
				if(split.length <= 0)
					return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.indirizziNotifica.getLabel());
				
				for (String indirizzo : split) {
					if(StringUtils.isEmpty(indirizzo))
						return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.formatoEmailErratoValoreVuoto");
					
					Matcher matcherIndirizzo = this.emailPattern.matcher(indirizzo);

					if(!matcherIndirizzo.matches())
						return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("dipartimento.form.formatoEmailErrato",indirizzo);
					
					
				}
			}
			
		}
		
		// fatturazione attiva
		
		// se fatturazione attiva e modalita' push allora devo controllare il contenuto dei codici procedimento.
		if(fattAttiva && mod)  {
			String _codiceProcedimentoB2B = this.codiceProcedimentoB2B.getValue();
			String _codiceProcedimento = this.codiceProcedimento.getValue();
			
			// almeno uno tra codiceProcedimento e codiceProcedimentoB2B deve essere indicato
			if(StringUtils.isEmpty(_codiceProcedimento) && StringUtils.isEmpty(_codiceProcedimentoB2B)) {
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.codiceProcedimentoVuoto");
			}
		}
		

		return null;
	}

	public Dipartimento getDipartimento(){
		Dipartimento dipartimento = new Dipartimento();

		dipartimento.setCodice(this.codice.getValue());
		dipartimento.setDescrizione(this.descrizione.getValue());

		IdEnte idEnte = new IdEnte();

		SelectItem enteSI = this.ente.getValue();
		if(enteSI!= null){
			String _ente = enteSI.getValue();
			idEnte.setNome(_ente);
			dipartimento.setEnte(idEnte);
		}

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
			for (FormField<?> prop : this.properties) {
				String valore = null;
				if(prop instanceof Text) {
					valore = ((Text)prop).getValue();
				} else if (prop instanceof BooleanCheckBox) {
					boolean value = ((BooleanCheckBox)prop).getValue() != null ? (((BooleanCheckBox)prop).getValue() ? true : false) : false;
					valore = value ? "TRUE": "FALSE";
				}
				String nomeProp = prop.getName();
				nomeProp = nomeProp.substring(nomeProp.lastIndexOf("prop_")+"prop_".length());

				// Inserisco il valore se e' in modalitaPush (gia' controllato nella validazione), e solo se non e' vuoto;  
				if( !StringUtils.isEmpty(valore)){
					DipartimentoPropertyValue value = new DipartimentoPropertyValue();
					value.setValore(valore);
					boolean found = false;
					for (DipartimentoProperty nomeProprieta : this.listaNomiProperties) {
						if(nomeProprieta.getNome().equals(nomeProp)){
							IdDipartimentoProperty idProperty = new IdDipartimentoProperty();
							idProperty.setIdProtocollo(nomeProprieta.getIdProtocollo());
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


		if(this.codicePCC.getValue()){
			dipartimento.setListaEmailNotifiche(this.indirizziNotifica.getValue()); 
		}
		
		
		// fatturazione attiva
		boolean _fatturazioneAttiva = this.fatturazioneAttiva.getValue() != null ? (this.fatturazioneAttiva.getValue() ? true : false) : false;
		boolean _firmaAutomatica = this.firmaAutomatica.getValue() != null ? (this.firmaAutomatica.getValue() ? true : false) : false;
		String _codiceProcedimento = this.codiceProcedimento.getValue();
		String _codiceProcedimentoB2B = this.codiceProcedimentoB2B.getValue();
		
		dipartimento.setFatturazioneAttiva(_fatturazioneAttiva);
		dipartimento.setFirmaAutomatica(_firmaAutomatica);
		dipartimento.setIdProcedimento(_codiceProcedimento); 
		dipartimento.setIdProcedimentoB2B(_codiceProcedimentoB2B); 

		return dipartimento;
	}

	public List<PccDipartimentoOperazione> getOperazioniAbilitate(){
		List<PccDipartimentoOperazione> list = new ArrayList<PccDipartimentoOperazione>();

		// salvo le properties solo se e' un ente PCC
		if(this.codicePCC.getValue()){
			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(this.getCodice().getValue());
			
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.DATI_FATTURA, this.datiFattura);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.INSERIMENTO_FATTURA,this.inserimentoFattura);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.STATO_FATTURA,this.statoFattura);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_CP,this.pagamento);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_SP,this.stornoPagamento);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_CS,this.comunicazioneScadenza);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS,this.cancellazioneScadenze);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_CO,this.contabilizzazione);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC,this.stornoContabilizzazione);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_RC,this.ricezioneFattura);
			Utils.addSceltaUnitaOrganizzativa(list, idDipartimento, NomePccOperazioneType.OPERAZIONE_CONTABILE_RF,this.rifiutoFattura);
		}
		return list;
	}

	public BooleanCheckBox getNotificaAutomatica() {
		return this.notificaAutomatica;
	}

	public void setNotificaAutomatica(BooleanCheckBox notificaAutomatica) {
		this.notificaAutomatica = notificaAutomatica;
	}

	public List<FormField<?>> getProperties() {

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;

		for (FormField<?> proprieta : this.properties) {
			String nomeProp = proprieta.getName();
			nomeProp = nomeProp.substring(nomeProp.lastIndexOf("prop_")+"prop_".length());
			boolean req = mod && this.nomiPropertiesObbligatorie.contains(nomeProp);
			proprieta.setRequired(req);
		}

		return this.properties;
	}

	public void setProperties(List<FormField<?>> properties) {
		this.properties = properties;
	}

	public SelectList<SelectItem> getRegistro() {
		this.registro.setRequired(false);
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		// [TODO] agganciare
		boolean fatAt = false;//this.fatturazioneAttiva.getValue() != null ? (this.getFatturazioneAttiva().getValue() ? true : false) : false;

		if(mod || fatAt)
			this.registro.setRequired(true);

		return this.registro;

	}

	public void enteSelectListener(ActionEvent ae){
		SelectItem value = this.ente.getValue();
		String value2 = null;
		if(value!= null){
			value2 = value.getValue();
		}

		// aggiorno lista pcc
		if((value2 == null || (value2!=null && !value2.equals(CostantiForm.NON_SELEZIONATO)))){
			this.codicePCC.setValue(this.mbean.isEntePCC(value2));
		} else {
			this.codicePCC.setValue(false);
		}

		// reset le scelte pcc
		this.resetSezionePCC();
	}

	public void registroSelectListener(ActionEvent ae){
		// reset valori delle properties in base al registro scelto.
		this.properties = new ArrayList<FormField<?>>();
		this.setListaNomiProperties(this.mbean.getListaDipartimentoProperties(),false); 
	}

	public void setRegistro(SelectList<SelectItem> registro) {
		this.registro = registro;
	}


	public void modalitaPushOnChangeListener(ActionEvent ae){
		this._setModalitaPush();
		this._abilitaRegistro();
	}

	private void _setModalitaPush() {
		this.endpoint.setRendered(false);
		this.password.setRendered(false);
		this.username.setRendered(false);
		
		if(this.properties != null) {
			for (FormField<?> proprieta : this.properties) {
				proprieta.setRequired(false);
			}
		}

		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		
		if(mod){
			this.endpoint.setRendered(true);
			this.password.setRendered(true);
			this.username.setRendered(true);

			for (FormField<?> proprieta : this.properties) {
				String nomeProp = proprieta.getName();
				nomeProp = nomeProp.substring(nomeProp.lastIndexOf("prop_")+"prop_".length());
				boolean req = mod && this.nomiPropertiesObbligatorie.contains(nomeProp);
				proprieta.setRequired(req);
			}
		}

	}
	
	private void _abilitaRegistro() {
		this.registro.setRequired(false);
		
		boolean mod = this.modalitaPush.getValue() != null ? (this.getModalitaPush().getValue() ? true : false) : false;
		// [TODO] agganciare
		boolean fatAt = false; //this.fatturazioneAttiva.getValue() != null ? (this.getFatturazioneAttiva().getValue() ? true : false) : false;

		if(mod || fatAt){
			this.registro.setRequired(true);
		}
	}

	public TextArea getIndirizziNotifica() {
		return this.indirizziNotifica;
	}

	public void setIndirizziNotifica(TextArea indirizziNotifica) {
		this.indirizziNotifica = indirizziNotifica;
	}

	public FormField<String> getDatiFattura() {
		return this.datiFattura;
	}

	public void setDatiFattura(FormField<String> datiFattura) {
		this.datiFattura = datiFattura;
	}

	public FormField<String> getInserimentoFattura() {
		return this.inserimentoFattura;
	}

	public void setInserimentoFattura(FormField<String> inserimentoFattura) {
		this.inserimentoFattura = inserimentoFattura;
	}

	public FormField<String> getStatoFattura() {
		return this.statoFattura;
	}

	public void setStatoFattura(FormField<String> statoFattura) {
		this.statoFattura = statoFattura;
	}

	public FormField<String> getConsultazioneTracce() {
		return this.consultazioneTracce;
	}

	public void setConsultazioneTracce(FormField<String> consultazioneTracce) {
		this.consultazioneTracce = consultazioneTracce;
	}

	public Pattern getDipartimentoPattern() {
		return this.dipartimentoPattern;
	}

	public void setDipartimentoPattern(Pattern dipartimentoPattern) {
		this.dipartimentoPattern = dipartimentoPattern;
	}

	public List<DipartimentoProperty> getListaNomiProperties() {
		return this.listaNomiProperties;
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

	public List<PccOperazione> getListaProprietaConsentiteAiDipartimenti() {
		return listaProprietaConsentiteAiDipartimenti;
	}

	public void setListaProprietaConsentiteAiDipartimenti(List<PccOperazione> listaProprietaConsentiteAiDipartimenti) {
		this.listaProprietaConsentiteAiDipartimenti = listaProprietaConsentiteAiDipartimenti;
	}

	public SelectList<SelectItem> getEnte() {
		return ente;
	}

	public void setEnte(SelectList<SelectItem> ente) {
		this.ente = ente;
	}

	public DipartimentoMBean getMbean() {
		return mbean;
	}

	public void setMbean(DipartimentoMBean mbean) {
		this.mbean = mbean;
	}

	public BooleanCheckBox getCodicePCC() {
		return codicePCC;
	}

	public void setCodicePCC(BooleanCheckBox codicePCC) {
		this.codicePCC = codicePCC;
	}

	public boolean isShowProperties() {
		this.showProperties = false;
		
		if(this.properties != null && this.properties.size() > 0)
			this.showProperties = true;
		
		return showProperties;
	}

	public void setShowProperties(boolean showProperties) {
		this.showProperties = showProperties;
	}

	public FormField<String> getPagamentoIVA() {
		return pagamentoIVA;
	}

	public void setPagamentoIVA(FormField<String> pagamentoIVA) {
		this.pagamentoIVA = pagamentoIVA;
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

	public BooleanCheckBox getFatturazioneAttiva() {
		return fatturazioneAttiva;
	}

	public void setFatturazioneAttiva(BooleanCheckBox fatturazioneAttiva) {
		this.fatturazioneAttiva = fatturazioneAttiva;
	}

	public BooleanCheckBox getFirmaAutomatica() {
		this.firmaAutomatica.setRendered(false);
		boolean mod = this.fatturazioneAttiva.getValue() != null ? (this.fatturazioneAttiva.getValue() ? true : false) : false;

		if(mod)
			this.firmaAutomatica.setRendered(true);

		return this.firmaAutomatica;
	}

	public void setFirmaAutomatica(BooleanCheckBox firmaAutomatica) {
		this.firmaAutomatica = firmaAutomatica;
	}

	public FormField<String> getCodiceProcedimento() {
		this.codiceProcedimento.setRendered(false);
		boolean mod = this.fatturazioneAttiva.getValue() != null ? (this.fatturazioneAttiva.getValue() ? true : false) : false;

		if(mod)
			this.codiceProcedimento.setRendered(true);

		return this.codiceProcedimento;
	}

	public void setCodiceProcedimento(FormField<String> codiceProcedimento) {
		this.codiceProcedimento = codiceProcedimento;
	}
	
	public FormField<String> getCodiceProcedimentoB2B() {
		this.codiceProcedimentoB2B.setRendered(false);
		boolean mod = this.fatturazioneAttiva.getValue() != null ? (this.fatturazioneAttiva.getValue() ? true : false) : false;

		if(mod)
			this.codiceProcedimentoB2B.setRendered(true);

		return this.codiceProcedimentoB2B;
	}

	public void setCodiceProcedimentoB2B(FormField<String> codiceProcedimentoB2B) {
		this.codiceProcedimentoB2B = codiceProcedimentoB2B;
	}

	public void fatturazioneAttivaOnChangeListener(ActionEvent ae){
		this._setFatturazioneAttiva();
		this._abilitaRegistro();
	}

	private void _setFatturazioneAttiva() {
		this.codiceProcedimento.setRendered(false);
		this.firmaAutomatica.setRendered(false);
		this.codiceProcedimentoB2B.setRendered(false);

		boolean mod = this.fatturazioneAttiva.getValue() != null ? (this.fatturazioneAttiva.getValue() ? true : false) : false;

		if(mod){
			this.codiceProcedimento.setRendered(true);
			this.firmaAutomatica.setRendered(true);
			this.codiceProcedimentoB2B.setRendered(true);
		}

	}
}
