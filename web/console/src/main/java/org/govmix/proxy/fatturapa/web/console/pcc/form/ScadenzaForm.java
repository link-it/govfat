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

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ScadenzaPccBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.input.DateTime;
import org.openspcoop2.generic_project.web.input.Text;

public class ScadenzaForm extends BaseForm implements Form {

	private Text importo = null;
	private DateTime data = null;
	private Pattern euroPattern = null;
	private Double pagatoRicontabilizzato  = null;

	private static String TWO_DIGITS_PATTERN = "^(\\-)?[0-9]+(\\,[0-9]{1,2})?$";

	public ScadenzaForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {
		this.euroPattern = Pattern.compile(ScadenzaForm.TWO_DIGITS_PATTERN);
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();

		this.setClosable(false);
		this.setId("addScadForm");
		this.setNomeForm(null); 

		this.data =  factory.getInputFieldFactory()
				.createDateTime("data","scadenza.data",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_MM_YYYY,null,false);
		//		this.data.setRequired(true);

		this.importo = factory.getInputFieldFactory()
				.createText("importo","scadenza.importo",null,false);
		this.importo.setStyle("width: 200px;");
		//		this.importo.setRequired(true);
		//this.importo.setConverterType(Costanti.CONVERT_TYPE_NUMBER);


		this.setField(this.data);
		this.setField(this.importo);

	}

	public void setValues (ScadenzaPccBean bean){
		if(bean != null){
			this.pagatoRicontabilizzato = bean.getDTO().getPagatoRicontabilizzato();
			
			this.data.setDefaultValue(bean.getDTO().getDataScadenza());

			Double importoMovimento = bean.getDTO().getImportoInScadenza();
			if(importoMovimento != null)
				this.importo.setDefaultValue(String.format(Locale.ITALIAN,"%.2f", importoMovimento.doubleValue())); 
		}else {
			this.data.setDefaultValue(null);
			this.importo.setDefaultValue(null); 
			this.pagatoRicontabilizzato = 0D;
		}

		this.reset();
	}

	public PccScadenza getScadenza(){
		PccScadenza scadenza = new PccScadenza();

		scadenza.setDataScadenza(this.getData().getValue());
		String _importo = this.importo.getValue();
		if(StringUtils.isNotEmpty(_importo)){
			Number n =  Utils.getNumber(_importo);
			scadenza.setImportoInScadenza(n.doubleValue());
		}
		scadenza.setSistemaRichiedente(Utils.getSistemaRichiedente());
		scadenza.setUtenteRichiedente(Utils.getLoggedUtente().getUsername());
		
		scadenza.setPagatoRicontabilizzato(this.pagatoRicontabilizzato); 

		return scadenza;
	}

	public String valida(){

		String _importo = this.importo.getValue();
		//		if(StringUtils.isEmpty(_importo))
		//			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.importo.getLabel());

		if(StringUtils.isNotEmpty(_importo)){
			Matcher matcher = this.euroPattern.matcher(_importo);

			if(!matcher.matches())
				return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.importoFormatoNonValido");
		}
		//        if( this.getData().getValue() == null)
		//			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.FIELD_NON_PUO_ESSERE_VUOTO, this.data.getLabel());

		return null;

	}

	@Override
	public void reset() {
		this.data.reset();
		this.importo.reset();
	}

	public Text getImporto() {
		return this.importo;
	}

	public void setImporto(Text importo) {
		this.importo = importo;
	}

	public DateTime getData() {
		return this.data;
	}

	public void setData(DateTime data) {
		this.data = data;
	}

}
