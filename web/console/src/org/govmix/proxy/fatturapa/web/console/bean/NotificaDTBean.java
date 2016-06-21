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

import org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputButton;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputDate;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;


/**
 * NotificaDTBean definisce il bean per la visualizzazione della Notifica decorrenza termini. 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaDTBean extends BaseBean<NotificaDecorrenzaTermini, Long> { 
	
  	private OutputField<String> nomeFile =null;
	private OutputField<String> descrizione =null;
	private OutputField<String> note =null;
	private OutputField<Date> dataRicezione =null;
	private OutputField<String> xml = null;
	private OutputField<String> pdf = null;
	
	private Long idFattura = null;
	
	public NotificaDTBean(){
		initFields();
	}
	
	@Override
	public void setDTO(NotificaDecorrenzaTermini dto) {
		super.setDTO(dto);
		
		this.note.setValue(this.getDTO().getNote());
		this.nomeFile.setValue(this.getDTO().getNomeFile());
		this.dataRicezione.setValue(this.getDTO().getDataRicezione());
		this.descrizione.setValue(this.getDTO().getDescrizione());
		
		prepareUrls();
	}
	
	public void setIdFattura(Long id){
		this.idFattura = id;
		prepareUrls();
	}
	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}
	
	private void initFields(){
		this.note = new OutputText();
		this.note.setLabel( ("notificaDecorrenzaTermini.note"));
		this.note.setName("nomeAttachment"); 
		
		this.nomeFile = new OutputText();
		this.nomeFile.setLabel( ("notificaDecorrenzaTermini.nomeFile"));
		this.nomeFile.setName("algoritmoCompressione"); 
		
		this.dataRicezione = new OutputDate();
		this.dataRicezione.setLabel( ("notificaDecorrenzaTermini.dataRicezione"));
		this.dataRicezione.setName("dataRicezione");
		this.dataRicezione.setPattern("dd/M/yyyy");
		
		this.descrizione = new OutputText();
		this.descrizione.setLabel( ("notificaDecorrenzaTermini.descrizione"));
		this.descrizione.setName("descrizione"); 
		
		this.xml = new OutputButton();
		this.xml.setLabel( ("commons.label.xml"));
		this.xml.setName("xml");
		((OutputButton) this.xml).setIcon("xml.png");
		((OutputButton) this.xml).setIconTitle( ("commons.label.xml.iconTitle"));

		this.pdf = new OutputButton();
		this.pdf.setLabel( ("commons.label.pdf"));
		this.pdf.setName("pdf");
		((OutputButton) this.pdf).setIcon("pdf.png");
		((OutputButton) this.pdf).setIconTitle( ("commons.label.pdf.iconTitle"));
 
	}

	public OutputField<String> getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(OutputField<String> nomeFile) {
		this.nomeFile = nomeFile;
	}

	public OutputField<String> getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(OutputField<String> descrizione) {
		this.descrizione = descrizione;
	}

	public OutputField<String> getNote() {
		return note;
	}

	public void setNote(OutputField<String> note) {
		this.note = note;
	}

	public OutputField<Date> getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(OutputField<Date> dataRicezione) {
		this.dataRicezione = dataRicezione;
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
	
	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();

		

		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.idFattura
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_DT;

		((OutputButton) this.xml).setLink(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.idFattura
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_DT;

		((OutputButton) this.pdf).setLink( this.getDTO().getXml() != null ? url : null);
 
	}

}
