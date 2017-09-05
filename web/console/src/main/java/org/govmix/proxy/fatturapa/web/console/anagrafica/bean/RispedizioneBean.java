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
package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.constants.TipoErroreType;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.view.IViewBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;

public class RispedizioneBean extends BaseBean<PccRispedizione, Long> implements IViewBean<PccRispedizione, Long>{
	
	private Text codiceErrore = null;
	private Text descrizioneErrore = null;
	private Text tipoErrore = null;
	private Text tentativi = null;
	private Text intervallo = null;
	private Text rispedizioneAutomatica = null;
//	private Text regolaDefault = null;
	
	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;
	
	public RispedizioneBean() {
	
		try{
			this.init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.codiceErrore = this.getFactory().getOutputFieldFactory().createText("codiceErrore","rispedizione.codiceErrore");	
		this.descrizioneErrore = this.getFactory().getOutputFieldFactory().createText("descrizioneErrore","rispedizione.descrizioneErrore");
		this.tipoErrore = this.getFactory().getOutputFieldFactory().createText("tipoErrore","rispedizione.tipoErrore");
		this.tentativi = this.getFactory().getOutputFieldFactory().createText("tentativi","rispedizione.tentativi");
		this.intervallo = this.getFactory().getOutputFieldFactory().createText("intervallo","rispedizione.intervallo");
		this.rispedizioneAutomatica = this.getFactory().getOutputFieldFactory().createText("rispedizioneAutomatica","rispedizione.rispedizioneAutomatica");
//		this.regolaDefault = this.getFactory().getOutputFieldFactory().createText("regolaDefault","rispedizione.regolaDefault");
		
		this.setField(this.codiceErrore);
		this.setField(this.descrizioneErrore);
		this.setField(this.tipoErrore);
		this.setField(this.tentativi);
		this.setField(this.rispedizioneAutomatica);
		this.setField(this.intervallo);
//		this.setField(this.regolaDefault);
		
		this.fieldsDatiGenerali = this.getFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.codiceErrore);
		this.fieldsDatiGenerali.addField(this.descrizioneErrore);
		this.fieldsDatiGenerali.addField(this.tipoErrore);
		this.fieldsDatiGenerali.addField(this.tentativi);
		this.fieldsDatiGenerali.addField(this.intervallo);
		this.fieldsDatiGenerali.addField(this.rispedizioneAutomatica);
//		this.fieldsDatiGenerali.addField(this.regolaDefault);
		
		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");
	}
	
	@Override
	public void setDTO(PccRispedizione dto) {
		super.setDTO(dto);

		this.codiceErrore.setValue(this.getDTO().getCodiceErrore());
		this.descrizioneErrore.setValue(this.getDTO().getDescrizioneErrore());
		TipoErroreType tipoErroreType = this.getDTO().getTipoErrore();
		if(tipoErroreType != null ){
			String tipoErr = null;
			switch (tipoErroreType) {
			case ELABORAZIONE:
				tipoErr = "rispedizione.tipoErrore.elaborazione";
				break;
			case TRASMISSIONE:
			default:
				tipoErr = "rispedizione.tipoErrore.trasmissione";
				break;
			}
			
			this.tipoErrore.setValue(tipoErr);
		}
		
		this.tentativi.setValue(this.getDTO().getMaxNumeroTentativi() + "");
		this.intervallo.setValue(this.getDTO().getIntervalloTentativi() + "");

		boolean risp = this.getDTO().isAbilitato();
		this.rispedizioneAutomatica.setValue(Utils.getBooleanAsLabel(risp,"commons.label.abilitata", "commons.label.nonAbilitata"));
		
//		boolean def  = this.getDTO().isRispedizioneDefault();
//		this.regolaDefault.setValue(Utils.getBooleanAsLabel(def,"commons.label.si", "commons.label.no"));
		
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	 
	public Text getTentativi() {
		return tentativi;
	}

	public void setTentativi(Text tentativi) {
		this.tentativi = tentativi;
	}

	public Text getIntervallo() {
		return intervallo;
	}

	public void setIntervallo(Text intervallo) {
		this.intervallo = intervallo;
	}

	public Text getRispedizioneAutomatica() {
		return rispedizioneAutomatica;
	}

	public void setRispedizioneAutomatica(Text rispedizioneAutomatica) {
		this.rispedizioneAutomatica = rispedizioneAutomatica;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
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

	public Text getTipoErrore() {
		return tipoErrore;
	}

	public void setTipoErrore(Text tipoErrore) {
		this.tipoErrore = tipoErrore;
	}

//	public Text getRegolaDefault() {
//		return regolaDefault;
//	}
//
//	public void setRegolaDefault(Text regolaDefault) {
//		this.regolaDefault = regolaDefault;
//	} 
	

	
}
