/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.constants.ScartoType;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf2.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.view.IViewBean;

/**
 * NotificaECBean definisce il bean per la visualizzazione della Notifica esito committente. 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECBean extends BaseBean<NotificaEsitoCommittente, Long> implements IViewBean<NotificaEsitoCommittente, Long>{

	private Text esito =null;
	private DateTime  dataInvio =null;
	private DateTime data =null;
	private Text descrizione =null;
	private Text scarto =null;
	private Text scartoNote =null;
	private Button xml = null;
	private Button pdf = null;
	private Button scartoXml = null;
	private Button scartoPdf = null;
	private Text utente = null;
	private Text modalitaBatch = null;

	public NotificaECBean(){
		try{
			init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.scarto = this.getFactory().getOutputFieldFactory().createText("scarto","notificaEsitoCommittente.scarto");
		this.scartoNote = this.getFactory().getOutputFieldFactory().createText("scartoNote","notificaEsitoCommittente.scartoNote");
		this.esito = this.getFactory().getOutputFieldFactory().createText("esito","notificaEsitoCommittente.esito");
		this.data = this.getFactory().getOutputFieldFactory().createDateTime("data","notificaEsitoCommittente.data","dd/M/yyyy");
		this.dataInvio = this.getFactory().getOutputFieldFactory().createDateTime("dataInvio","notificaEsitoCommittente.dataInvio","dd/M/yyyy");
		this.descrizione = this.getFactory().getOutputFieldFactory().createText("descrizione","notificaEsitoCommittente.descrizione");
		this.modalitaBatch = this.getFactory().getOutputFieldFactory().createText("modalitaBatch","notificaEsitoCommittente.modalitaBatch");

		this.xml = this.getFactory().getOutputFieldFactory().createButton("xml","notificaEsitoCommittente.label.xml",null,"/images/fatturapa/icons/xml.png","notificaEsitoCommittente.label.xml.iconTitle","notificaEsitoCommittente.label.xml.iconTitle");
		this.pdf = this.getFactory().getOutputFieldFactory().createButton("pdf","notificaEsitoCommittente.label.pdf",null,"/images/fatturapa/icons/pdf.png","notificaEsitoCommittente.label.pdf.iconTitle","notificaEsitoCommittente.label.pdf.iconTitle");
		this.scartoXml = this.getFactory().getOutputFieldFactory().createButton("scartoXml","notificaEsitoCommittente.scarto.label.xml",null,"/images/fatturapa/icons/xml.png","notificaEsitoCommittente.scarto.label.xml.iconTitle","notificaEsitoCommittente.scarto.label.xml.iconTitle");
		this.scartoPdf = this.getFactory().getOutputFieldFactory().createButton("scartoPdf","notificaEsitoCommittente.scarto.label.pdf",null,"/images/fatturapa/icons/pdf.png","notificaEsitoCommittente.scarto.label.pdf.iconTitle","notificaEsitoCommittente.scarto.label.pdf.iconTitle");
		this.utente = this.getFactory().getOutputFieldFactory().createText("utente","notificaEsitoCommittente.utente");
		
		this.setField(this.scarto);
		this.setField(this.scartoNote);
		this.setField(this.esito);
		this.setField(this.data);
		this.setField(this.dataInvio);
		this.setField(this.descrizione);
		this.setField(this.modalitaBatch);
		this.setField(this.utente);
		this.setField(this.xml);
		this.setField(this.pdf);
		this.setField(this.scartoPdf);
		this.setField(this.scartoXml);
		
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

	public Text getEsito() {
		return esito;
	}

	public void setEsito(Text esito) {
		this.esito = esito;
	}

	public DateTime getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(DateTime dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Text getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getScarto() {
		return scarto;
	}

	public void setScarto(Text scarto) {
		this.scarto = scarto;
	}

	public Text getScartoNote() {
		return scartoNote;
	}

	public void setScartoNote(Text scartoNote) {
		this.scartoNote = scartoNote;
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

	public DateTime getData() {
		return data;
	}

	public void setData(DateTime data) {
		this.data = data;
	}

	public Button getScartoXml() {
		return scartoXml;
	}

	public void setScartoXml(Button scartoXml) {
		this.scartoXml = scartoXml;
	}

	public Button getScartoPdf() {
		return scartoPdf;
	}

	public void setScartoPdf(Button scartoPdf) {
		this.scartoPdf = scartoPdf;
	}
	public Text getUtente() {
		return utente;
	}

	public void setUtente(Text utente) {
		this.utente = utente;
	}

	public Text getModalitaBatch() {
		return modalitaBatch;
	}

	public void setModalitaBatch(Text modalitaBatch) {
		this.modalitaBatch = modalitaBatch;
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();



		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_EC;

		this.xml.setHref(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_EC;

		this.pdf.setHref( this.getDTO().getXml() != null ? url : null);


		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_SCARTO;


		this.scartoXml.setHref(this.getDTO().getScartoXml() != null ? url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_SCARTO;
		this.scartoPdf.setHref( this.getDTO().getScartoXml() != null ? url : null);
	}

}
