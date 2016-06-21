/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.bean;

import java.util.Date;

import javax.faces.context.FacesContext;

import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.constants.ScartoType;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputButton;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputDate;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;

/**
 * NotificaECBean definisce il bean per la visualizzazione della Notifica esito committente. 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECBean extends BaseBean<NotificaEsitoCommittente, Long>{

	private OutputField<String> esito =null;
	private OutputField<Date> dataInvio =null;
	private OutputField<Date> data =null;
	private OutputField<String> descrizione =null;
	private OutputField<String> scarto =null;
	private OutputField<String> scartoNote =null;
	private OutputField<String> xml = null;
	private OutputField<String> pdf = null;

	private OutputField<String> scartoXml = null;
	private OutputField<String> scartoPdf = null;
	
	private OutputField<String> utente = null;
	
	private OutputField<String> modalitaBatch = null;
	
	private OutputField<String> separatore = null;

	public NotificaECBean(){
		initFields();
	}
	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(NotificaEsitoCommittente dto) {
		super.setDTO(dto);

		ScartoType scarto2 = this.getDTO().getScarto();
		if(scarto2 != null){
			if(scarto2.equals(ScartoType.EN00))
				this.scarto.setValue("notificaEsitoCommittente.scarto.EN00");
			else  
				this.scarto.setValue("notificaEsitoCommittente.scarto.EN01");
		}
		
		this.scartoNote.setValue(this.getDTO().getScartoNote());
		this.data.setValue(this.getDTO().getDataInvioSdi());
		this.dataInvio.setValue(this.getDTO().getDataInvioEnte());
		this.descrizione.setValue(this.getDTO().getDescrizione());
		EsitoCommittenteType esitoCommittente = this.getDTO().getEsito();
		if(esitoCommittente != null){	
			if(esitoCommittente.equals(EsitoCommittenteType.EC01))
				this.esito.setValue("notificaEsitoCommittente.esito.accettato");
			else  
				this.esito.setValue("notificaEsitoCommittente.esito.rifiutato");
		}
		
		this.utente.setValue(this.getDTO().getUtente().getUsername());
		
		boolean modalitaPush2 = this.getDTO().getModalitaBatch();
		String mod = modalitaPush2 ? Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.modalitaBatch.automatica")
				: Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.modalitaBatch.manuale");
		this.modalitaBatch.setValue(mod);

		prepareUrls();
	}

	private void initFields(){
		this.scarto = new OutputText();
		this.scarto.setLabel( ("notificaEsitoCommittente.scarto"));
		this.scarto.setName("scarto"); 

		this.scartoNote = new OutputText();
		this.scartoNote.setLabel( ("notificaEsitoCommittente.scartoNote"));
		this.scartoNote.setName("scartoNote"); 

		this.esito = new OutputText();
		this.esito.setLabel( ("notificaEsitoCommittente.esito"));
		this.esito.setName("esito"); 

		this.data = new OutputDate();
		this.data.setLabel( ("notificaEsitoCommittente.data"));
		this.data.setName("data"); 
		this.data.setPattern("dd/M/yyyy");

		this.dataInvio = new OutputDate();
		this.dataInvio.setLabel( ("notificaEsitoCommittente.dataInvio"));
		this.dataInvio.setName("dataInvio");
		this.dataInvio.setPattern("dd/M/yyyy");

		this.descrizione = new OutputText();
		this.descrizione.setLabel( ("notificaEsitoCommittente.descrizione"));
		this.descrizione.setName("descrizione"); 

		this.modalitaBatch = new OutputText();
		this.modalitaBatch.setLabel( ("notificaEsitoCommittente.modalitaBatch"));
		this.modalitaBatch.setName("modalitaBatch"); 

		this.xml = new OutputButton();
		this.xml.setLabel( ("notificaEsitoCommittente.label.xml"));
		this.xml.setName("xml");
		((OutputButton) this.xml).setIcon("xml.png");
		((OutputButton) this.xml).setIconTitle( ("notificaEsitoCommittente.label.xml.iconTitle"));


		this.pdf = new OutputButton();
		this.pdf.setLabel( ("notificaEsitoCommittente.label.pdf"));
		this.pdf.setName("pdf");
		((OutputButton) this.pdf).setIcon("pdf.png");
		((OutputButton) this.pdf).setIconTitle( ("notificaEsitoCommittente.label.pdf.iconTitle"));

		this.scartoXml = new OutputButton();
		this.scartoXml.setLabel( ("notificaEsitoCommittente.scarto.label.xml"));
		this.scartoXml.setName("scartoXml");
		((OutputButton) this.scartoXml).setIcon("xml.png");
		((OutputButton) this.scartoXml).setIconTitle( ("notificaEsitoCommittente.scarto.label.xml.iconTitle"));


		this.scartoPdf = new OutputButton();
		this.scartoPdf.setLabel( ("notificaEsitoCommittente.scarto.label.pdf"));
		this.scartoPdf.setName("scartoPdf");
		((OutputButton) this.scartoPdf).setIcon("pdf.png");
		((OutputButton) this.scartoPdf).setIconTitle( ("notificaEsitoCommittente.scarto.label.pdf.iconTitle"));
		
		this.utente = new OutputText();
		this.utente.setLabel(("notificaEsitoCommittente.utente"));
		this.utente.setName("utente"); 

		
		this.separatore = new OutputText();
		this.separatore.setLabel( null);
		this.separatore.setValue(" "); 
		this.separatore.setName("separatore"); 
	}

	public OutputField<String> getEsito() {
		return esito;
	}

	public void setEsito(OutputField<String> esito) {
		this.esito = esito;
	}

	public OutputField<Date> getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(OutputField<Date> dataInvio) {
		this.dataInvio = dataInvio;
	}

	public OutputField<String> getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(OutputField<String> descrizione) {
		this.descrizione = descrizione;
	}

	public OutputField<String> getScarto() {
		return scarto;
	}

	public void setScarto(OutputField<String> scarto) {
		this.scarto = scarto;
	}

	public OutputField<String> getScartoNote() {
		return scartoNote;
	}

	public void setScartoNote(OutputField<String> scartoNote) {
		this.scartoNote = scartoNote;
	}

	public OutputField<String> getXml() {
		return xml;
	}

	public void setXml(OutputField<String> xml) {
		this.xml = xml;
	}

	public OutputField<String> getPdf() {
		return pdf;
	}

	public void setPdf(OutputField<String> pdf) {
		this.pdf = pdf;
	}

	public OutputField<Date> getData() {
		return data;
	}

	public void setData(OutputField<Date> data) {
		this.data = data;
	}

	public OutputField<String> getScartoXml() {
		return scartoXml;
	}

	public void setScartoXml(OutputField<String> scartoXml) {
		this.scartoXml = scartoXml;
	}

	public OutputField<String> getScartoPdf() {
		return scartoPdf;
	}

	public void setScartoPdf(OutputField<String> scartoPdf) {
		this.scartoPdf = scartoPdf;
	}
	public OutputField<String> getUtente() {
		return utente;
	}

	public void setUtente(OutputField<String> utente) {
		this.utente = utente;
	}

	public OutputField<String> getModalitaBatch() {
		return modalitaBatch;
	}

	public void setModalitaBatch(OutputField<String> modalitaBatch) {
		this.modalitaBatch = modalitaBatch;
	}
	
	public OutputField<String> getSeparatore() {
		return separatore;
	}

	public void setSeparatore(OutputField<String> separatore) {
		this.separatore = separatore;
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();

		

		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_EC;

		((OutputButton) this.xml).setLink(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_EC;

		((OutputButton) this.pdf).setLink( this.getDTO().getXml() != null ? url : null);


		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_SCARTO;


		((OutputButton) this.scartoXml).setLink(this.getDTO().getScartoXml() != null ? url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_SCARTO;
		((OutputButton) this.scartoPdf).setLink( this.getDTO().getScartoXml() != null ? url : null);
	}

}
