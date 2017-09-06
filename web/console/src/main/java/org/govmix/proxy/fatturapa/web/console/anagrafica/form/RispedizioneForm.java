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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.constants.TipoErroreType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RispedizioneBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.govmix.proxy.fatturapa.web.console.util.input.FatturaPAFactory;
import org.govmix.proxy.fatturapa.web.console.util.input.factory.FatturaPAInputFactoryImpl;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.FormField;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class RispedizioneForm extends BaseForm implements Form{

	private Text codiceErrore = null;
	private Text descrizioneErrore = null; 
	private Text numeroTentativi = null;  
	private Text intervalloRispedizione = null;
	private SelectList tipoErrore = null;
	private FormField<String> rispedizioneAutomatica= null;
//	private BooleanCheckBox regolaDefault;

	private final String CODICE_ERRORE_PATTERN = "^[A-Z]{1,2}[0-9]{3,5}$";
	private final String NUMBER_PATTERN = "\\d+";
	private Pattern codiceErrorePattern = null;
	private Pattern numberPattern = null;

	public RispedizioneForm()throws Exception {
		this.init();
		
		this.codiceErrorePattern = Pattern.compile(CODICE_ERRORE_PATTERN);
		this.numberPattern = Pattern.compile(NUMBER_PATTERN);
	}

	@Override
	public void init() throws Exception {
		this.setClosable(false);
		this.setId("formRisp");
		this.setNomeForm(null); 

		WebGenericProjectFactory factory = new FatturaPAFactory(LoggerManager.getConsoleLogger()); 

		FatturaPAInputFactoryImpl inputFieldFactory = (FatturaPAInputFactoryImpl)factory.getInputFieldFactory();
		this.codiceErrore = inputFieldFactory.createText("codiceErrore", "rispedizione.form.codiceErrore", null, true);
		this.descrizioneErrore = inputFieldFactory.createText("descrizioneErrore", "rispedizione.form.descrizioneErrore", null, true);
		this.numeroTentativi = inputFieldFactory.createText("numeroTentativi", "rispedizione.form.numeroTentativi", null, true);
//		this.numeroTentativi.setConverterType(Costanti.CONVERT_TYPE_NUMBER);
		this.intervalloRispedizione = inputFieldFactory.createText("intervalloRispedizione", "rispedizione.form.intervalloRispedizione", null, true);
//		this.intervalloRispedizione.setConverterType(Costanti.CONVERT_TYPE_NUMBER);
		this.tipoErrore = inputFieldFactory.createSelectList("tipoErrore","rispedizione.form.tipoErrore",null,true);
		this.rispedizioneAutomatica =  inputFieldFactory.createRadioButtonFatturaPA("rispedizioneAutomatica","rispedizione.form.rispedizioneAutomatica", null, false);
//		this.regolaDefault = inputFieldFactory.createBooleanCheckBox("regolaDefault","rispedizione.form.regolaDefault",null,false);
		

		this.setField(this.codiceErrore);
		this.setField(this.descrizioneErrore);
		this.setField(this.numeroTentativi);
		this.setField(this.intervalloRispedizione);
		this.setField(this.tipoErrore);
		this.setField(this.rispedizioneAutomatica);
//		this.setField(this.regolaDefault);
	}

	@Override
	public void reset() {
		this.codiceErrore.reset();
		this.descrizioneErrore.reset();
		this.numeroTentativi.reset();
		this.intervalloRispedizione.reset();
		this.tipoErrore.reset();
		this.rispedizioneAutomatica.reset();
//		this.regolaDefault.reset();
	}

	
	@Override
	public void setObject(Object arg0) throws Exception {
		if(arg0 != null){
			RispedizioneBean bean = (RispedizioneBean) arg0;
			this.codiceErrore.setDefaultValue(bean.getDTO().getCodiceErrore());
			this.codiceErrore.setDisabled(true);
			this.descrizioneErrore.setDefaultValue(bean.getDTO().getDescrizioneErrore());
			this.numeroTentativi.setDefaultValue(bean.getDTO().getMaxNumeroTentativi() +"");
			this.intervalloRispedizione.setDefaultValue(bean.getDTO().getIntervalloTentativi() +""); 
			if(bean.getDTO().getTipoErrore() != null)
				switch(bean.getDTO().getTipoErrore()){
				case ELABORAZIONE:
					this.tipoErrore.setDefaultValue(new SelectItem(bean.getDTO().getTipoErrore().getValue(), "rispedizione.tipoErrore.elaborazione"));
					break;
				case TRASMISSIONE:
					this.tipoErrore.setDefaultValue(new SelectItem(bean.getDTO().getTipoErrore().getValue(), "rispedizione.tipoErrore.trasmissione"));
					break;
				}
			else 
				this.tipoErrore.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));

			String value = bean.getDTO().isAbilitato() ? "commons.label.abilitata": "commons.label.nonAbilitata";
			this.rispedizioneAutomatica.setDefaultValue(value);
//			this.regolaDefault.setDefaultValue(bean.getDTO().getRispedizioneDefault());
		} else {
			this.codiceErrore.setDisabled(false);
			this.codiceErrore.setDefaultValue(null);
			this.descrizioneErrore.setDefaultValue(null);
			this.numeroTentativi.setDefaultValue(null);
			this.intervalloRispedizione.setDefaultValue(null);
			this.tipoErrore.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
			this.rispedizioneAutomatica.setDefaultValue("commons.label.nonAbilitata");
//			this.regolaDefault.setDefaultValue(null);
		}

		this.reset();
	}

	public String valida (){

		String _codice = this.codiceErrore.getValue();
		if(StringUtils.isEmpty(_codice))
			return  Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.codiceErrore.getLabel());
		
		Matcher matcher = this.codiceErrorePattern.matcher(_codice);

		if(!matcher.matches())
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.formatoCodiceErrato");

		String _descr = this.descrizioneErrore.getValue();
		if(StringUtils.isEmpty(_descr))
			return  Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizioneErrore.getLabel());

		String v = this.numeroTentativi.getValue();
		
		if(StringUtils.isEmpty(v))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_NON_VALIDO, this.numeroTentativi.getLabel());
		
		Matcher m2 = this.numberPattern.matcher(v);
		// non e' un numero
		if(!m2.matches())
			return Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.numeroTentativiFormatoNonValido"); 
		
		int vv = Integer.parseInt(v);
		
		if(vv <= 0)
			return Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.numeroTentativiMinoreZero");
		
		v = this.intervalloRispedizione.getValue();
		
		if(StringUtils.isEmpty(v))
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.INPUT_VALORE_NON_VALIDO, this.intervalloRispedizione.getLabel());
		
		m2 = this.numberPattern.matcher(v);
		// non e' un numero
		if(!m2.matches())
			return Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.intervalloRispedizioneFormatoNonValido"); 
		
		vv = Integer.parseInt(v);
		
		if(vv <= 0)
			return Utils.getInstance().getMessageFromResourceBundle("rispedizione.form.intervalloRispedizioneMinoreZero");

		SelectItem tipoErroreSI = this.tipoErrore.getValue();
		if(tipoErroreSI!= null){
			String _tipoErrore = tipoErroreSI.getValue();

			if(_tipoErrore.equals(CostantiForm.NON_SELEZIONATO))
				return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO, this.tipoErrore.getLabel());
		}
		
		String valueRA = this.rispedizioneAutomatica.getValue();

		if(valueRA == null)
			return Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO, this.rispedizioneAutomatica.getLabel());

		return null;
	}


	@Override
	public Object getObject() throws Exception {
		PccRispedizione rispedizione = new PccRispedizione();

		rispedizione.setCodiceErrore(this.codiceErrore.getValue());
		rispedizione.setDescrizioneErrore(this.descrizioneErrore.getValue());
		rispedizione.setMaxNumeroTentativi( Integer.parseInt(this.numeroTentativi.getValue()));
		rispedizione.setIntervalloTentativi( Integer.parseInt(this.intervalloRispedizione.getValue()));
		rispedizione.setTipoErrore(TipoErroreType.toEnumConstant(this.tipoErrore.getValue().getValue()));
		String valueRA = this.rispedizioneAutomatica.getValue();
		rispedizione.setAbilitato(valueRA.equals("commons.label.abilitata"));
//		boolean rispDef = this.regolaDefault.getValue() != null ? (this.regolaDefault.getValue() ? true : false) : false;
//		
//		rispedizione.setRispedizioneDefault(rispDef); 

		return rispedizione;
	}


	public Text getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(Text codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public Text getDescrizioneErrore() {
		return descrizioneErrore;
	}

	public void setDescrizioneErrore(Text descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	public Text getNumeroTentativi() {
		return numeroTentativi;
	}

	public void setNumeroTentativi(Text numeroTentativi) {
		this.numeroTentativi = numeroTentativi;
	}

	public Text getIntervalloRispedizione() {
		return intervalloRispedizione;
	}

	public void setIntervalloRispedizione(Text intervalloRispedizione) {
		this.intervalloRispedizione = intervalloRispedizione;
	}

	public SelectList getTipoErrore() {
		return tipoErrore;
	}

	public void setTipoErrore(SelectList tipoErrore) {
		this.tipoErrore = tipoErrore;
	}

	public FormField<String> getRispedizioneAutomatica() {
		return rispedizioneAutomatica;
	}

	public void setRispedizioneAutomatica(FormField<String> rispedizioneAutomatica) {
		this.rispedizioneAutomatica = rispedizioneAutomatica;
	}

//	public BooleanCheckBox getRegolaDefault() {
//		return regolaDefault;
//	}
//
//	public void setRegolaDefault(BooleanCheckBox regolaDefault) {
//		this.regolaDefault = regolaDefault;
//	}



}
