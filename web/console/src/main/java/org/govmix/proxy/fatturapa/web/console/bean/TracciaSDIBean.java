/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.bean;

import javax.faces.context.FacesContext;

import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.Text;

public class TracciaSDIBean extends BaseBean<TracciaSDI, Long> implements IBean<TracciaSDI, Long> {

	
	private Text identificativoSdi = null;
	private Text tipoComunicazione = null;
	private Text nomeFile = null;
	private Text idEgov = null;
	private Text contentType = null;
	private Text statoProtocollazione = null;
	private DateTime dataProtocollazione = null;
	private DateTime dataProssimaProtocollazione = null;
	private Text tentativiProtocollazione = null;
	private Text dettaglioProtocollazione = null;
	private DateTime data = null;
	private Button xml = null;
	private Button pdf = null;
	
	private IdFattura idFattura = null;
	private DominioType dominioType =null;
	
	public TracciaSDIBean() {
		try{
			this.init();
		}catch(Exception e){

		}
	}
	
	private void init() throws FactoryException{
		this.identificativoSdi = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("identificativoSdi","tracciaSDI.identificativoSdi");
		this.tipoComunicazione =   this.getWebGenericProjectFactory().getOutputFieldFactory().createText("tipoComunicazione","tracciaSDI.tipoComunicazione");
		this.nomeFile =  this.getWebGenericProjectFactory().getOutputFieldFactory().createText("nomeFile","tracciaSDI.nomeFile");
		this.idEgov = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idEgov","tracciaSDI.idEgov");
		this.contentType = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("contentType","tracciaSDI.contentType");
		this.statoProtocollazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("statoProtocollazione","tracciaSDI.statoProtocollazione");
		this.dataProtocollazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataProtocollazione","tracciaSDI.dataProtocollazione",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.dataProssimaProtocollazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataProssimaProtocollazione","tracciaSDI.dataProssimaProtocollazione",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.tentativiProtocollazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("tentativiProtocollazione","tracciaSDI.tentativiProtocollazione");
		this.dettaglioProtocollazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("dettaglioProtocollazione","tracciaSDI.dettaglioProtocollazione");
		this.data = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("data","tracciaSDI.data",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_MM_YYYY);
		this.xml = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("xml","tracciaSDI.label.xml",null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_XML,"tracciaSDI.label.xml.iconTitle","tracciaSDI.label.xml.iconTitle");
		this.pdf = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("pdf","tracciaSDI.label.pdf",null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_PDF,"tracciaSDI.label.pdf.iconTitle","tracciaSDI.label.pdf.iconTitle");
		
		this.setField(this.identificativoSdi);
		this.setField(this.tipoComunicazione);
		this.setField(this.nomeFile);
		this.setField(this.idEgov);
		this.setField(this.contentType);
		this.setField(this.statoProtocollazione);
		this.setField(this.dataProtocollazione);
		this.setField(this.dataProssimaProtocollazione);
		this.setField(this.tentativiProtocollazione);
		this.setField(this.dettaglioProtocollazione);
		this.setField(this.data);
		this.setField(this.xml);
		this.setField(this.pdf);
	}
	
	@Override
	public void setDTO(TracciaSDI dto) {
		super.setDTO(dto);
		
		this.identificativoSdi.setValue(this.getDTO().getIdentificativoSdi() + "");
		TipoComunicazioneType tipoComunicazione2 = this.getDTO().getTipoComunicazione();
		if(tipoComunicazione2 != null){
			String valueTC = tipoComunicazione2.getValue();
			this.tipoComunicazione.setValue("fattura.tipoComunicazione."+valueTC);
		}
		this.nomeFile.setValue(this.getDTO().getNomeFile());
		this.idEgov.setValue(this.getDTO().getIdEgov());
		this.contentType.setValue(this.getDTO().getContentType());
		StatoProtocollazioneType statoProtocollazione2 = this.getDTO().getStatoProtocollazione();
		if(statoProtocollazione2 != null) {
			this.statoProtocollazione.setValue("tracciaSDI.statoProtocollazione."+ statoProtocollazione2.getValue());
			
			switch(statoProtocollazione2) {
			case ERRORE_PROTOCOLLAZIONE:
				break;
			case IN_RICONSEGNA: this.dataProssimaProtocollazione.setValue(this.getDTO().getDataProssimaProtocollazione());
				break;
			case NON_PROTOCOLLATA:
				break;
			case PROTOCOLLATA: this.dataProtocollazione.setValue(this.getDTO().getDataProtocollazione());
				break;
			case PROTOCOLLATA_IN_ELABORAZIONE:
				break;
			default:
				break;}
			
		}
			
		
		String tentativiProtocollazione2 = this.getDTO().getTentativiProtocollazione() != null ? this.getDTO().getTentativiProtocollazione() + "" : "--";
		this.tentativiProtocollazione.setValue(tentativiProtocollazione2);
		this.dettaglioProtocollazione.setValue(this.getDTO().getDettaglioProtocollazione());
		this.data.setValue(this.getDTO().getData());
		
		
	}
	
	
	@Override
	public Long getId() {
		return this.getDTO() != null ? this.getDTO().getId() : -1L;
	}
	
	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		String actionValue = "com";
		
		Long idLongComunicazione = this.getDTO().getId();
		
		if(this.getDTO().getTipoComunicazione() != null) {
			actionValue = "com_" + this.getDTO().getTipoComunicazione().toString();
			
			if(this.getDTO().getTipoComunicazione().equals(TipoComunicazioneType.FAT_OUT) && this.idFattura != null){
				idLongComunicazione = this.idFattura.getId();
				actionValue = FattureExporter.PARAMETRO_ACTION_FATTURA;
			}
		}

		
		
		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + idLongComunicazione
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ actionValue;

		this.xml.setHref(this.getDTO().getRawData() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + idLongComunicazione
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ actionValue;
		
		boolean visualizzaPdf  = this.getDTO().getRawData() != null;
		
		// comunicazione fattura in uscita di una fattura B2B non deve essere visualizzato
		if(this.dominioType != null && this.dominioType.equals(DominioType.B2B)) {
			if(this.getDTO().getTipoComunicazione().equals(TipoComunicazioneType.FAT_OUT)) {
				visualizzaPdf = false;
			}
		}

		this.pdf.setHref( visualizzaPdf ? url : null);
	}

	public Text getIdentificativoSdi() {
		return identificativoSdi;
	}

	public void setIdentificativoSdi(Text identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}

	public Text getTipoComunicazione() {
		return tipoComunicazione;
	}

	public void setTipoComunicazione(Text tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}

	public Text getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(Text nomeFile) {
		this.nomeFile = nomeFile;
	}

	public DateTime getData() {
		return data;
	}

	public void setData(DateTime data) {
		this.data = data;
	}

	public Button getXml() {
		return xml;
	}

	public void setXml(Button xml) {
		this.xml = xml;
	}

	public Button getPdf() {
		return pdf;
	}

	public void setPdf(Button pdf) {
		this.pdf = pdf;
	}

	public Text getIdEgov() {
		return idEgov;
	}

	public void setIdEgov(Text idEgov) {
		this.idEgov = idEgov;
	}

	public Text getContentType() {
		return contentType;
	}

	public void setContentType(Text contentType) {
		this.contentType = contentType;
	}

	public Text getStatoProtocollazione() {
		return statoProtocollazione;
	}

	public void setStatoProtocollazione(Text statoProtocollazione) {
		this.statoProtocollazione = statoProtocollazione;
	}

	public DateTime getDataProtocollazione() {
		return dataProtocollazione;
	}

	public void setDataProtocollazione(DateTime dataProtocollazione) {
		this.dataProtocollazione = dataProtocollazione;
	}

	public DateTime getDataProssimaProtocollazione() {
		return dataProssimaProtocollazione;
	}

	public void setDataProssimaProtocollazione(DateTime dataProssimaProtocollazione) {
		this.dataProssimaProtocollazione = dataProssimaProtocollazione;
	}

	public Text getTentativiProtocollazione() {
		return tentativiProtocollazione;
	}

	public void setTentativiProtocollazione(Text tentativiProtocollazione) {
		this.tentativiProtocollazione = tentativiProtocollazione;
	}

	public Text getDettaglioProtocollazione() {
		return dettaglioProtocollazione;
	}

	public void setDettaglioProtocollazione(Text dettaglioProtocollazione) {
		this.dettaglioProtocollazione = dettaglioProtocollazione;
	}

	public IdFattura getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
		this.prepareUrls();
	}

	public DominioType getDominioType() {
		return dominioType;
	}

	public void setDominioType(DominioType dominioType) {
		this.dominioType = dominioType;
		this.prepareUrls();
	}
	
	public boolean isVisualizzaLinkRiconsegnaTraccia(){
		if(this.getDTO().getStatoProtocollazione() != null){
			boolean isAdmin = Utils.getLoginBean().isAdmin();
			StatoProtocollazioneType statoConsegnaType =  this.getDTO().getStatoProtocollazione();
			
			
			if((statoConsegnaType.equals(StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE) || statoConsegnaType.equals(StatoProtocollazioneType.IN_RICONSEGNA)) && isAdmin) {
				return true;
			}
				
		}
		
		return false;
	}
}
