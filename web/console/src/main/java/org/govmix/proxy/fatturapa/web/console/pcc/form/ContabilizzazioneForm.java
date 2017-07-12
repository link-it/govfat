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
package org.govmix.proxy.fatturapa.web.console.pcc.form;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.constants.CausaleType;
import org.govmix.proxy.fatturapa.orm.constants.StatoDebitoType;
import org.govmix.proxy.fatturapa.web.commons.utils.TransformUtils;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ContabilizzazionePccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.mbean.ContabilizzazioneMBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class ContabilizzazioneForm extends BaseForm implements Form {

	private SelectList<SelectItem> causale = null;
	private SelectList<SelectItem>  stato = null;
	private Text descrizione = null;
	private Text importo = null;
	private String idImporto = null;
	private Pattern euroPattern = null;
	
	private ContabilizzazioneMBean mBean = null;
	private static String TWO_DIGITS_PATTERN = "^(\\-)?[0-9]+(\\,[0-9]{1,2})?$";
//	private static String EURO_PATTERN = "^\\s*-?((\\d{1,3}(\\.(\\d){3})*)|\\d*)(,\\d{1,2})?\\s?(\\u20AC)?\\s*$";
	
//	private static String NUMBER_PATTER

	public ContabilizzazioneForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {
		
		this.euroPattern = Pattern.compile(TWO_DIGITS_PATTERN);
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();

		this.setClosable(false);
		this.setId("addContForm");
		this.setNomeForm(null); 

		this.descrizione = factory.getInputFieldFactory().createText("descrizione","contabilizzazione.descrizione",null,false);
		this.descrizione.setStyle("width: 200px;");
		this.descrizione.setRequired(false); 
		this.causale = factory.getInputFieldFactory().createSelectList("causale","contabilizzazione.causale",
				null,false);
		this.causale.setStyle("width: 200px;");
		this.causale.setWidth(200);
		this.causale.setRequired(false);
		this.stato = factory.getInputFieldFactory().createSelectList("stato","contabilizzazione.stato",
				null,false);
		this.stato.setWidth(200);
		this.stato.setStyle("width: 200px;");
		this.stato.setRequired(true);
		this.stato.setFieldsToUpdate(this.getId()+"_formPnl");
		this.stato.setForm(this);
		
		//this._setStato();
		
		this.importo = factory.getInputFieldFactory()
				.createText("cont_importo","contabilizzazione.importo",null,false);
		this.importo.setStyle("width: 200px;");
		this.importo.setRequired(true);              
//		this.importo.setConverterType(Costanti.CONVERT_TYPE_NUMBER); 




		this.setField(this.descrizione);
		this.setField(this.importo);
		this.setField(this.causale);
		this.setField(this.stato);
	}

	@Override
	public void reset() {
		this.descrizione.reset();
		this.stato.reset();
		this.importo.reset();
		this.causale.reset();

	}

	public void setValues (ContabilizzazionePccBean bean){
		if(bean != null){
			
			
			
			String descr = bean.getDTO().getDescrizione();
			
			descr = TransformUtils.toStringDescrizioneImportoContabilizzazione(descr);  
			
			this.descrizione.setDefaultValue(descr);
			double importoMovimento = bean.getDTO().getImportoMovimento();
			
			this.importo.setDefaultValue(String.format(Locale.ITALIAN,"%.2f", importoMovimento)); 
			
			StatoDebitoType statoDebito2 = bean.getDTO().getStatoDebito();
			if(statoDebito2 !=  null){
				this.stato.setDefaultValue(new SelectItem(statoDebito2.getValue(), "pccStatoDebito."+statoDebito2.getValue())); 
			}
			CausaleType causale2 = bean.getDTO().getCausale();
			if(causale2 !=  null){
				this.causale.setDefaultValue(new SelectItem(causale2.getValue(),"pccCausale." + causale2.getValue()));
			} else 
				this.causale.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO)); 
			
			this.idImporto = bean.getDTO().getIdImporto();
		}else {
			this.descrizione.setDefaultValue(null);
			this.importo.setDefaultValue(null);

			this.stato.setDefaultValue(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoDebitoType.NOLIQ.getValue(), "pccStatoDebito."+StatoDebitoType.NOLIQ.getValue()));
			this.causale.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
			//this.causale.setDefaultValue(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.CONT.getValue(), "pccCausale."+CausaleType.CONT.getValue())); 
			this.idImporto = null;
		}

		this.reset();
	}


	public PccContabilizzazione getContabilizzazione(){
		PccContabilizzazione c = new PccContabilizzazione();

		if(StringUtils.isNotEmpty(this.getDescrizione().getValue()))
			c.setDescrizione(this.getDescrizione().getValue()); 

		String value = this.getStato().getValue().getValue();
		c.setStatoDebito(StatoDebitoType.toEnumConstant(value)); 
		
		
		SelectItem causaleSI = this.causale.getValue();

		String valueCausale = null; 
		if(causaleSI != null)
			valueCausale= causaleSI.getValue();
		
		if(StringUtils.isNotEmpty(valueCausale) && !valueCausale.equals(CostantiForm.NON_SELEZIONATO))
			c.setCausale(CausaleType.toEnumConstant(valueCausale));  
		else 
			c.setCausale(null); 

		Number n =  Utils.getNumber(this.getImporto().getValue());
		c .setImportoMovimento(n.doubleValue());
		
		c.setSistemaRichiedente(Utils.getSistemaRichiedente());
		c.setUtenteRichiedente(Utils.getLoggedUtente().getUsername());
		
//		if(this.idImporto == null)
//			c.setIdImporto(Utils.generaIdentificativoImporto());
//		else 
			c.setIdImporto(this.idImporto);
		
		return c; 
	}
	
	public String valida(){

		String _importo = this.importo.getValue();
		if(StringUtils.isEmpty(_importo))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.importo.getLabel());
		
		Matcher matcher = this.euroPattern.matcher(_importo);

		if(!matcher.matches())
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.importoFormatoNonValido");

//		if(StringUtils.isEmpty(this.getDescrizione().getValue()))
//			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.descrizione.getLabel());

		
		SelectItem _ruolo = this.stato.getValue();

		String valueRuolo = null; 

		if(_ruolo != null)
			valueRuolo = _ruolo.getValue();

		if( valueRuolo == null || (valueRuolo != null && valueRuolo.equals(CostantiForm.NON_SELEZIONATO)))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.stato.getLabel());

//		_ruolo = this.causale.getValue();
//
//		valueRuolo = null; 
//
//		if(_ruolo != null)
//			valueRuolo = _ruolo.getValue();
//
//		if( valueRuolo == null || (valueRuolo != null && valueRuolo.equals(CostantiForm.NON_SELEZIONATO)))
//			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.causale.getLabel());

		return null;

	}
	
	public void statoSelectListener(ActionEvent ae){
		 SelectItem statoSI = this.stato.getValue();
			
		String valueStato = null; 

		if(statoSI != null)
			valueStato = statoSI.getValue();
		
		((SelectListImpl)this.causale).setElencoSelectItems(this.mBean.getListaCausali(valueStato));
		this.causale.setDefaultValue(new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
	}
	
	public SelectList<SelectItem> getCausale() {
		return this.causale;
	}

	public void setCausale(SelectList<SelectItem> causale) {
		this.causale = causale;
	}

	public SelectList<SelectItem> getStato() {
		return this.stato;
	}

	public void setStato(SelectList<SelectItem> stato) {
		this.stato = stato;
	}

	public Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getImporto() {
		return this.importo;
	}

	public void setImporto(Text importo) {
		this.importo = importo;
	}

	public ContabilizzazioneMBean getmBean() {
		return mBean;
	}

	public void setmBean(ContabilizzazioneMBean mBean) {
		this.mBean = mBean;
	}
	
	
}
