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
package org.govmix.proxy.fatturapa.web.console.bean;

import javax.faces.context.FacesContext;

import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.view.IViewBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.Text;


/**
 * NotificaDTBean definisce il bean per la visualizzazione della Notifica decorrenza termini. 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaDTBean extends BaseBean<NotificaDecorrenzaTermini, Long> implements IViewBean<NotificaDecorrenzaTermini, Long> { 
	
  	private Text nomeFile =null;
	private Text descrizione =null;
	private Text note =null;
	private DateTime dataRicezione =null;
	private Button xml = null;
	private Button pdf = null;
	
	private Long idFattura = null;
	
	public NotificaDTBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.note = this.getFactory().getOutputFieldFactory().createText("note","notificaDecorrenzaTermini.note");
		this.nomeFile = this.getFactory().getOutputFieldFactory().createText("nomeFile","notificaDecorrenzaTermini.nomeFile");
		this.dataRicezione = this.getFactory().getOutputFieldFactory().createDateTime("dataRicezione","notificaDecorrenzaTermini.dataRicezione","dd/M/yyyy");
		this.descrizione = this.getFactory().getOutputFieldFactory().createText("descrizione","notificaDecorrenzaTermini.descrizione");
		this.xml = this.getFactory().getOutputFieldFactory().createButton("xml","commons.label.xml",null,"/images/fatturapa/icons/xml.png","commons.label.xml.iconTitle","commons.label.xml.iconTitle");
		this.pdf = this.getFactory().getOutputFieldFactory().createButton("pdf","commons.label.pdf",null,"/images/fatturapa/icons/pdf.png","commons.label.pdf.iconTitle","commons.label.pdf.iconTitle");
	
		this.setField(this.note);
		this.setField(this.nomeFile);
		this.setField(this.dataRicezione);
		this.setField(this.descrizione);
		this.setField(this.xml);
		this.setField(this.pdf);
 
	}
	
	@Override
	public void setDTO(NotificaDecorrenzaTermini dto) {
		super.setDTO(dto);
		
		this.note.setValue(this.getDTO().getNote());
		this.nomeFile.setValue(this.getDTO().getNomeFile());
		this.dataRicezione.setValue(this.getDTO().getDataRicezione());
		this.descrizione.setValue(this.getDTO().getDescrizione());
		
		this.prepareUrls();
	}
	
	public void setIdFattura(Long id){
		this.idFattura = id;
		this.prepareUrls();
	}
	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	public Text getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(Text nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getNote() {
		return this.note;
	}

	public void setNote(Text note) {
		this.note = note;
	}

	public DateTime getDataRicezione() {
		return this.dataRicezione;
	}

	public void setDataRicezione(DateTime dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public Button getXml() {
		return this.xml;
	}

	public void setXml(Button xml) {
		this.xml = xml;
	}

	public Button getPdf() {
		return this.pdf;
	}

	public void setPdf(Button pdf) {
		this.pdf = pdf;
	}
	
	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();
		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.idFattura
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_DT;

		this.xml.setHref(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.idFattura
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_DT;

		this.pdf.setHref( this.getDTO().getXml() != null ? url : null);
 
	}

}
