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
package org.govmix.proxy.fatturapa.web.console.pcc.bean;

import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.constants.TipoOperazioneType;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.OutputNumber;
import org.openspcoop2.generic_project.web.output.Text;

public class ErroreElaborazionePccBean extends BaseBean<PccErroreElaborazione, Long> implements IBean<PccErroreElaborazione, Long>{

	private Text tipoOperazione = null;
	private OutputNumber progressivoOperazione = null;
	private Text codiceEsito = null;
	private Text descrizioneEsito = null;
	private Text tipoErrore = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	public ErroreElaborazionePccBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.tipoOperazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("tipoOperazione","erroreElaborazione.tipoOperazione");
		this.progressivoOperazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("progressivoOperazione","erroreElaborazione.progressivoOperazione");
		this.progressivoOperazione.setConverterType(Costanti.CONVERT_TYPE_NUMBER); 
		this.codiceEsito = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceEsito","erroreElaborazione.codiceEsito");
		this.descrizioneEsito = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("descrizioneEsito","erroreElaborazione.descrizioneEsito");
		this.descrizioneEsito.setValueStyleClass("whiteSpaceNewLine");
		this.tipoErrore = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("tipoErrore","erroreElaborazione.tipoErrore");
		
		
		
		this.setField(this.tipoOperazione);
		this.setField(this.progressivoOperazione);
		this.setField(this.codiceEsito);
		this.setField(this.descrizioneEsito);
		this.setField(this.tipoErrore);

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.tipoOperazione);
		this.fieldsDatiGenerali.addField(this.progressivoOperazione);
		this.fieldsDatiGenerali.addField(this.codiceEsito);
		this.fieldsDatiGenerali.addField(this.descrizioneEsito);
		this.fieldsDatiGenerali.addField(this.tipoErrore);

		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(PccErroreElaborazione dto) {
		super.setDTO(dto);

		TipoOperazioneType tipoOperazione2 = this.getDTO().getTipoOperazione();

		if(tipoOperazione2 != null){
			String val = null;
			switch (tipoOperazione2) {
			case CCS: val = "pccOperazione.nome.OperazioneContabile_CCS"; break;
			case CO: val = "pccOperazione.nome.OperazioneContabile_CO"; break;
			case CP: val = "pccOperazione.nome.OperazioneContabile_CP"; break;
			case CS: val = "pccOperazione.nome.OperazioneContabile_CS"; break;
			case RC: val = "pccOperazione.nome.OperazioneContabile_RC"; break;
			case RF: val = "pccOperazione.nome.OperazioneContabile_RF"; break;
			case SC: val = "pccOperazione.nome.OperazioneContabile_SC"; break; 
			case SP: 
			default:
				val = "pccOperazione.nome.OperazioneContabile_SP"; 
				break;
			}
			this.tipoOperazione.setValue(val);
		}

		this.progressivoOperazione.setValue(this.getDTO().getProgressivoOperazione());
		this.codiceEsito.setValue(this.getDTO().getCodiceEsito());
		this.descrizioneEsito.setValue(this.getDTO().getDescrizioneEsito());
	}

	public Text getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(Text tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public OutputNumber getProgressivoOperazione() {
		return progressivoOperazione;
	}

	public void setProgressivoOperazione(OutputNumber progressivoOperazione) {
		this.progressivoOperazione = progressivoOperazione;
	}

	public Text getCodiceEsito() {
		return codiceEsito;
	}

	public void setCodiceEsito(Text codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public Text getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public void setDescrizioneEsito(Text descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}

	public Text getTipoErrore() {
		return tipoErrore;
	}

	public void setTipoErrore(Text tipoErrore) {
		this.tipoErrore = tipoErrore;
	}
}
