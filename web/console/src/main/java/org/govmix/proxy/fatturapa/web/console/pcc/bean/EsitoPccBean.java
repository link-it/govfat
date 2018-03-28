/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.IdTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;

public class EsitoPccBean extends BaseBean<PccTracciaTrasmissioneEsito, Long> implements IBean<PccTracciaTrasmissioneEsito, Long>{

	private Text esitoElaborazione = null;
	private Text descrizioneElaborazione = null;
	private Text idTrasmissione = null;
	private Text esitoTrasmissione = null;
	private Text dettaglioErroreTrasmissione = null;
	private Text idEgovRichiesta = null;
	private List<ErroreElaborazionePccBean> listaErroriElaborazione = null;
	private DateTime dataFineElaborazione = null;
	private DateTime gdo = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	private SimpleDateFormat sdf = null;
	
	public EsitoPccBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.esitoElaborazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("esitoElaborazione","esitoPcc.esitoElaborazione");
		this.descrizioneElaborazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("descrizioneElaborazione","esitoPcc.descrizioneElaborazione");
		this.idTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idTrasmissione","esitoPcc.idTrasmissione");
		this.esitoTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("esitoTrasmissione","esitoPcc.esitoTrasmissione");
		this.dettaglioErroreTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("dettaglioErroreTrasmissione","esitoPcc.dettaglioErroreTrasmissione");
		this.dettaglioErroreTrasmissione.setValueStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_WHITE_SPACE_NEW_LINE);
		this.idEgovRichiesta = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idEgovRichiesta","esitoPcc.idEgovRichiesta");
		this.dataFineElaborazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataFineElaborazione","esitoPcc.dataFineElaborazione",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.gdo = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("gdo","esitoPcc.gdo",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS); 
		this.sdf = new SimpleDateFormat(this.gdo.getPattern());
		
		
		this.setField(this.esitoElaborazione);
		this.setField(this.descrizioneElaborazione);
		this.setField(this.idTrasmissione);
		this.setField(this.esitoTrasmissione);
		this.setField(this.dettaglioErroreTrasmissione);
		this.setField(this.idEgovRichiesta);
		this.setField(this.dataFineElaborazione);
		this.setField(this.gdo);

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.gdo);
		this.fieldsDatiGenerali.addField(this.esitoElaborazione);
		this.fieldsDatiGenerali.addField(this.descrizioneElaborazione);
		this.fieldsDatiGenerali.addField(this.idTrasmissione);
		this.fieldsDatiGenerali.addField(this.idEgovRichiesta);
		this.fieldsDatiGenerali.addField(this.dataFineElaborazione);
		this.fieldsDatiGenerali.addField(this.esitoTrasmissione);
		this.fieldsDatiGenerali.addField(this.dettaglioErroreTrasmissione);


		this.fieldsDatiGenerali.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiGenerali.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE);

		this.listaErroriElaborazione = new ArrayList<ErroreElaborazionePccBean>();
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(PccTracciaTrasmissioneEsito dto) {
		super.setDTO(dto);

		this.esitoElaborazione.setValue(this.getDTO().getEsitoElaborazione());
		this.descrizioneElaborazione.setValue(this.getDTO().getDescrizioneElaborazione());
		IdTrasmissione idTrasmissione2 = this.getDTO().getIdTrasmissione();
		if(idTrasmissione2 != null)
			this.idTrasmissione.setValue(idTrasmissione2.getIdTrasmissione() + "");
		EsitoTrasmissioneType esitoTrasmissione2 = this.getDTO().getEsitoTrasmissione();
		if(esitoTrasmissione2 != null){
			String val = "pccEsitoTrasmissione." + esitoTrasmissione2.getValue();
			this.esitoTrasmissione.setValue(val);
		}
		this.dettaglioErroreTrasmissione.setValue(this.getDTO().getDettaglioErroreTrasmissione());
		this.idEgovRichiesta.setValue(this.getDTO().getIdEgovRichiesta());
		this.dataFineElaborazione.setValue(this.getDTO().getDataFineElaborazione());
		this.gdo.setValue(this.getDTO().getGdo());

		this.listaErroriElaborazione = new ArrayList<ErroreElaborazionePccBean>();
		if(this.getDTO().getPccErroreElaborazioneList() != null){
			for (PccErroreElaborazione errore : this.getDTO().getPccErroreElaborazioneList()) {
				ErroreElaborazionePccBean bean = new ErroreElaborazionePccBean(); 
				bean.setDTO(errore);
				this.listaErroriElaborazione.add(bean);
			}
		}
	}

	public Text getEsitoElaborazione() {
		return esitoElaborazione;
	}

	public void setEsitoElaborazione(Text esitoElaborazione) {
		this.esitoElaborazione = esitoElaborazione;
	}

	public Text getDescrizioneElaborazione() {
		return descrizioneElaborazione;
	}

	public void setDescrizioneElaborazione(Text descrizioneElaborazione) {
		this.descrizioneElaborazione = descrizioneElaborazione;
	}

	public Text getIdTrasmissione() {
		return idTrasmissione;
	}

	public void setIdTrasmissione(Text idTrasmissione) {
		this.idTrasmissione = idTrasmissione;
	}

	public Text getEsitoTrasmissione() {
		return esitoTrasmissione;
	}

	public void setEsitoTrasmissione(Text esitoTrasmissione) {
		this.esitoTrasmissione = esitoTrasmissione;
	}

	public Text getDettaglioErroreTrasmissione() {
		return dettaglioErroreTrasmissione;
	}

	public void setDettaglioErroreTrasmissione(Text dettaglioErroreTrasmissione) {
		this.dettaglioErroreTrasmissione = dettaglioErroreTrasmissione;
	}

	public Text getIdEgovRichiesta() {
		return idEgovRichiesta;
	}

	public void setIdEgovRichiesta(Text idEgovRichiesta) {
		this.idEgovRichiesta = idEgovRichiesta;
	}

	public List<ErroreElaborazionePccBean> getListaErroriElaborazione() {
		return listaErroriElaborazione;
	}

	public void setListaErroriElaborazione(List<ErroreElaborazionePccBean> listaErroriElaborazione) {
		this.listaErroriElaborazione = listaErroriElaborazione;
	}

	public DateTime getDataFineElaborazione() {
		return dataFineElaborazione;
	}

	public void setDataFineElaborazione(DateTime dataFineElaborazione) {
		this.dataFineElaborazione = dataFineElaborazione;
	}

	public DateTime getGdo() {
		return gdo;
	}

	public void setGdo(DateTime gdo) {
		this.gdo = gdo;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}

	public String getGdoFormatted(){
		if(this.gdo != null && this.gdo.getValue() != null){
			Date d = this.gdo.getValue();
			return this.sdf.format(d);
		}
		
		return "";
	}

}
