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

import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;

public class TracciaPccEstesaBean extends TracciaPCCBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DateTime dataPrimaTrasmissione;
	private DateTime dataEsito;
	private Text operazioneContabile;
	private Text idEgovTrasmissione;
	private Text idEgovEsito;
	private Text esitoTrasmissione;
	private Text esitoElaborazione;
	private Text idPccTransazione;
	
	private ErroreElaborazionePccBean metadataErrore = null;
	
	private List<ErroreElaborazionePccBean> listaErrori;
	
	
	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiTraccia = null;
	
	public TracciaPccEstesaBean(){
		super();
		try{
			this.metadataErrore = new ErroreElaborazionePccBean();
			this.init2();
		}catch(Exception e){

		}
	}
	
	@Override
	public Long getId() {
		return super.getId();
	}
	
	private void init2() throws FactoryException{
		this.dataPrimaTrasmissione = this.getFactory().getOutputFieldFactory().createDateTime("dataPrimaTrasmissione","tracciaPcc.dataPrimaTrasmissione","dd/M/yyyy HH:mm:ss");
		this.dataEsito = this.getFactory().getOutputFieldFactory().createDateTime("dataEsito","tracciaPcc.dataEsito","dd/M/yyyy HH:mm:ss");
		
		this.operazioneContabile = this.getFactory().getOutputFieldFactory().createText("operazioneContabile","tracciaPcc.operazione");
		this.idEgovTrasmissione = this.getFactory().getOutputFieldFactory().createText("idEgovTrasmissione","tracciaPcc.idEgovTrasmissione");
		this.idEgovEsito = this.getFactory().getOutputFieldFactory().createText("idEgovEsito","tracciaPcc.idEgovEsito");
		this.esitoElaborazione = this.getFactory().getOutputFieldFactory().createText("esitoElaborazione","tracciaPcc.esitoElaborazione");
		this.esitoTrasmissione = this.getFactory().getOutputFieldFactory().createText("esitoTrasmissione","tracciaPcc.esitoTrasmissione");
		this.idPccTransazione = this.getFactory().getOutputFieldFactory().createText("idPccTransazione","trasmissionePcc.idPccTransazione");
		
		//field.getName(), field
		this.getFields().put(this.dataPrimaTrasmissione.getName(), this.dataPrimaTrasmissione);
		this.getFields().put(this.dataEsito.getName(), this.dataEsito);
		this.getFields().put(this.operazioneContabile.getName(), this.operazioneContabile);
		this.getFields().put(this.idEgovTrasmissione.getName(), this.idEgovTrasmissione);
		this.getFields().put(this.idEgovEsito.getName(), this.idEgovEsito);
		this.getFields().put(this.esitoElaborazione.getName(), this.esitoElaborazione);
		this.getFields().put(this.esitoTrasmissione.getName(), this.esitoTrasmissione);
		this.getFields().put(this.idPccTransazione.getName(), this.idPccTransazione);
		
		this.fieldsDatiTraccia = this.getFactory().getOutputFieldFactory()
				.createOutputGroup("datiTraccia",3);
		
		
		this.fieldsDatiTraccia.addField(this.getDataUltimaTrasmissione());
		this.fieldsDatiTraccia.addField(this.dataPrimaTrasmissione);
		this.fieldsDatiTraccia.addField(this.dataEsito);
		this.fieldsDatiTraccia.addField(this.idPccTransazione);
		this.fieldsDatiTraccia.addField(this.getIdPaTransazione());
		this.fieldsDatiTraccia.addField(this.getSistemaRichiedente());
		this.fieldsDatiTraccia.addField(this.getUtenteRichiedente());
		this.fieldsDatiTraccia.addField(this.getCfTrasmittente());
		this.fieldsDatiTraccia.addField(this.operazioneContabile);
		this.fieldsDatiTraccia.addField(this.getStato());
		this.fieldsDatiTraccia.addField(this.idEgovTrasmissione);
		this.fieldsDatiTraccia.addField(this.idEgovEsito);
		this.fieldsDatiTraccia.addField(this.esitoTrasmissione);
		this.fieldsDatiTraccia.addField(this.esitoElaborazione);
		
		this.listaErrori = new ArrayList<ErroreElaborazionePccBean>();
		
	}
	
	@Override
	public void setDTO(PccTraccia dto) {
		super.setDTO(dto);

		
		// combinare per una stringa migliore
		String operazione = Utils.getInstance().getMessageFromResourceBundle("pccOperazione.nome." + dto.getOperazione());
		this.operazioneContabile.setValue(operazione); 
		
		// integrazione dei dati
		List<PccTracciaTrasmissione> listaTrasmissioni = dto.getPccTracciaTrasmissioneList();
		
		if(listaTrasmissioni != null && listaTrasmissioni.size() >0){
			PccTracciaTrasmissione prima = listaTrasmissioni.get(0);
			PccTracciaTrasmissione ultima = listaTrasmissioni.get(0);
			
			if(listaTrasmissioni.size() > 1) {
				for (int i = 0; i < listaTrasmissioni.size(); i++) {
					PccTracciaTrasmissione current = listaTrasmissioni.get(i);
					
					if(current.getGdo().getTime() > ultima.getGdo().getTime())
						ultima = current;
					
					if(current.getGdo().getTime() < ultima.getGdo().getTime())
						prima = current;
				}
			}
			
			// data prima trasmissione
			this.dataPrimaTrasmissione.setValue(prima.getGdo());
			
			// idegovtrasmissione
			this.idEgovTrasmissione.setValue(ultima.getIdEgovRichiesta()); 
			this.idPccTransazione.setValue(ultima.getIdPccTransazione()); 
			
			List<PccTracciaTrasmissioneEsito> esitoList = ultima.getPccTracciaTrasmissioneEsitoList();
			
			if(esitoList != null && esitoList.size() > 0){
				PccTracciaTrasmissioneEsito ultimo = esitoList.get(0); 
				
				if(esitoList.size() > 1) {
					for (int i = 0; i < esitoList.size(); i++) {
						PccTracciaTrasmissioneEsito current = esitoList.get(i);
						
						if(current.getGdo().getTime() > ultima.getGdo().getTime())
							ultimo = current;
					}
				}
				
				// data esito
				this.dataEsito.setValue(ultimo.getGdo());
				// idegovesito
				this.idEgovEsito.setValue(ultimo.getIdEgovRichiesta());
				
				String tipoErr = null;
				// esito elaborazione
				this.esitoElaborazione.setValue(ultimo.getEsitoElaborazione());
				if(ultimo.getEsitoElaborazione() != null && ultimo.getEsitoElaborazione().equalsIgnoreCase("KO")){
					tipoErr = "erroreElaborazione.tipoErrore.elaborazione";
				}
				
				// esito trasmissione
				EsitoTrasmissioneType esitoTrasmissione2 = ultimo.getEsitoTrasmissione();
				if(esitoTrasmissione2 != null){
					String val = "pccEsitoTrasmissione." + esitoTrasmissione2.getValue();
					this.esitoTrasmissione.setValue(val);
					
					if(esitoTrasmissione2.equals(EsitoTrasmissioneType.KO))
						tipoErr = "erroreElaborazione.tipoErrore.trasmissione";
				}
				
				//lista errori
				List<PccErroreElaborazione> lst = ultimo.getPccErroreElaborazioneList();
				
				if(lst != null && lst.size() > 0){
					for (PccErroreElaborazione pccErroreElaborazione : lst) {
						ErroreElaborazionePccBean bean = new ErroreElaborazionePccBean();
						bean.setDTO(pccErroreElaborazione);
						bean.getTipoErrore().setValue(tipoErr);
						this.listaErrori.add(bean);
					}
				}
			}
		}
	}

	public DateTime getDataPrimaTrasmissione() {
		return dataPrimaTrasmissione;
	}

	public void setDataPrimaTrasmissione(DateTime dataPrimaTrasmissione) {
		this.dataPrimaTrasmissione = dataPrimaTrasmissione;
	}

	public DateTime getDataEsito() {
		return dataEsito;
	}

	public void setDataEsito(DateTime dataEsito) {
		this.dataEsito = dataEsito;
	}

	public Text getOperazioneContabile() {
		return operazioneContabile;
	}

	public void setOperazioneContabile(Text operazioneContabile) {
		this.operazioneContabile = operazioneContabile;
	}

	public Text getIdEgovTrasmissione() {
		return idEgovTrasmissione;
	}

	public void setIdEgovTrasmissione(Text idEgovTrasmissione) {
		this.idEgovTrasmissione = idEgovTrasmissione;
	}

	public Text getIdEgovEsito() {
		return idEgovEsito;
	}

	public void setIdEgovEsito(Text idEgovEsito) {
		this.idEgovEsito = idEgovEsito;
	}

	public Text getEsitoTrasmissione() {
		return esitoTrasmissione;
	}

	public void setEsitoTrasmissione(Text esitoTrasmissione) {
		this.esitoTrasmissione = esitoTrasmissione;
	}

	public Text getEsitoElaborazione() {
		return esitoElaborazione;
	}

	public void setEsitoElaborazione(Text esitoElaborazione) {
		this.esitoElaborazione = esitoElaborazione;
	}

	public List<ErroreElaborazionePccBean> getListaErrori() {
		return listaErrori;
	}

	public void setListaErrori(List<ErroreElaborazionePccBean> listaErrori) {
		this.listaErrori = listaErrori;
	}

	public OutputGroup getFieldsDatiTraccia() {
		return fieldsDatiTraccia;
	}

	public void setFieldsDatiTraccia(OutputGroup fieldsDatiTraccia) {
		this.fieldsDatiTraccia = fieldsDatiTraccia;
	}

	public ErroreElaborazionePccBean getMetadataErrore() {
		return metadataErrore;
	}

	public void setMetadataErrore(ErroreElaborazionePccBean metadataErrore) {
		this.metadataErrore = metadataErrore;
	}
	
	@Override
	public FatturaElettronicaBean getFattura() {
		return super.getFattura();
	}
}
