/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoEsitoTrasmissioneType;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;

public class TracciaTrasmissionePCCBean extends BaseBean<PccTracciaTrasmissione, Long> implements IBean<PccTracciaTrasmissione, Long> {


	private DateTime tsTrasmissione;
	private Text idPccTransazione;
	private Text esitoTrasmissione;
	private Text statoEsito;
	private DateTime gdo;
	private DateTime dataFineElaborazione;
	private Text dettaglioErroreTrasmissione;
	private Text idEgovRichiesta;
	private SimpleDateFormat sdf = null;

	private List<EsitoPccBean> listaEsiti= null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	public TracciaTrasmissionePCCBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.listaEsiti = new ArrayList<EsitoPccBean>();

		this.tsTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("tsTrasmissione","trasmissionePcc.tsTrasmissione","dd/M/yyyy HH:mm:ss");
		this.sdf = new SimpleDateFormat(this.tsTrasmissione.getPattern());
		this.idPccTransazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idPccTransazione","trasmissionePcc.idPccTransazione");
		this.esitoTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("esitoTrasmissione","trasmissionePcc.esitoTrasmissione");
		this.statoEsito = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("statoEsito","trasmissionePcc.statoEsito");
		this.gdo = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("gdo","trasmissionePcc.gdo","dd/M/yyyy HH:mm:ss"); 
		this.dataFineElaborazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataFineElaborazione","trasmissionePcc.dataFineElaborazione","dd/M/yyyy HH:mm:ss");
		this.dettaglioErroreTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("dettaglioErroreTrasmissione","trasmissionePcc.dettaglioErroreTrasmissione");
		this.dettaglioErroreTrasmissione.setValueStyleClass("whiteSpaceNewLine");
		this.idEgovRichiesta = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idEgovRichiesta","trasmissionePcc.idEgovRichiesta");


		this.setField(this.tsTrasmissione);
		this.setField(this.idPccTransazione);
		this.setField(this.esitoTrasmissione);
		this.setField(this.statoEsito);
		this.setField(this.idEgovRichiesta);
		this.setField(this.gdo);
		this.setField(this.dataFineElaborazione);
		this.setField(this.dettaglioErroreTrasmissione);
	


		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.tsTrasmissione);
		this.fieldsDatiGenerali.addField(this.idPccTransazione);
		this.fieldsDatiGenerali.addField(this.esitoTrasmissione);
		this.fieldsDatiGenerali.addField(this.statoEsito);
		this.fieldsDatiGenerali.addField(this.gdo);
		this.fieldsDatiGenerali.addField(this.dataFineElaborazione);
		this.fieldsDatiGenerali.addField(this.dettaglioErroreTrasmissione);
		this.fieldsDatiGenerali.addField(this.idEgovRichiesta);

		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx"); 
	}

	@Override
	public Long getId() {
		return this.getDTO().getId();
	}

	@Override
	public void setDTO(PccTracciaTrasmissione dto) {
		super.setDTO(dto);

		this.tsTrasmissione.setValue(this.getDTO().getTsTrasmissione());
		this.idPccTransazione.setValue(this.getDTO().getIdPccTransazione());
		EsitoTrasmissioneType esitoTrasmissione2 = this.getDTO().getEsitoTrasmissione();
		if(esitoTrasmissione2 != null){
			String val = "pccEsitoTrasmissione." + esitoTrasmissione2.getValue();
			this.esitoTrasmissione.setValue(val);
		}
		StatoEsitoTrasmissioneType statoEsito2 = this.getDTO().getStatoEsito();
		if(statoEsito2 != null){
			String val = "pccStatoEsitoTrasmissione." + statoEsito2.getValue();
			this.statoEsito.setValue(val);
		}
		this.gdo.setValue(this.getDTO().getGdo());
		this.dataFineElaborazione.setValue(this.getDTO().getDataFineElaborazione());
		this.dettaglioErroreTrasmissione.setValue(this.getDTO().getDettaglioErroreTrasmissione());
		this.idEgovRichiesta.setValue(this.getDTO().getIdEgovRichiesta()); 

		this.listaEsiti = new ArrayList<EsitoPccBean>();
		if(this.getDTO().getPccTracciaTrasmissioneEsitoList() != null && this.getDTO().getPccTracciaTrasmissioneEsitoList().size() > 0){
			for (PccTracciaTrasmissioneEsito esito : this.getDTO().getPccTracciaTrasmissioneEsitoList()) {
				EsitoPccBean esitoBean = new EsitoPccBean();
				esitoBean.setDTO(esito);
				this.listaEsiti.add(esitoBean);
			}
		}
	}

	public DateTime getTsTrasmissione() {
		return tsTrasmissione;
	}

	public void setTsTrasmissione(DateTime tsTrasmissione) {
		this.tsTrasmissione = tsTrasmissione;
	}

	public Text getIdPccTransazione() {
		return idPccTransazione;
	}

	public void setIdPccTransazione(Text idPccTransazione) {
		this.idPccTransazione = idPccTransazione;
	}

	public Text getEsitoTrasmissione() {
		return esitoTrasmissione;
	}

	public void setEsitoTrasmissione(Text esitoTrasmissione) {
		this.esitoTrasmissione = esitoTrasmissione;
	}

	public Text getStatoEsito() {
		return statoEsito;
	}

	public void setStatoEsito(Text statoEsito) {
		this.statoEsito = statoEsito;
	}

	public DateTime getGdo() {
		return gdo;
	}

	public void setGdo(DateTime gdo) {
		this.gdo = gdo;
	}

	public DateTime getDataFineElaborazione() {
		return dataFineElaborazione;
	}

	public void setDataFineElaborazione(DateTime dataFineElaborazione) {
		this.dataFineElaborazione = dataFineElaborazione;
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

	public List<EsitoPccBean> getListaEsiti() {
		return listaEsiti;
	}

	public void setListaEsiti(List<EsitoPccBean> listaEsiti) {
		this.listaEsiti = listaEsiti;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}


	public String getTsTrasmissioneFormatted(){
		if(this.tsTrasmissione != null && this.tsTrasmissione.getValue() != null){
			Date d = this.tsTrasmissione.getValue();
			return this.sdf.format(d);
		}
		
		return "";
	}

}
