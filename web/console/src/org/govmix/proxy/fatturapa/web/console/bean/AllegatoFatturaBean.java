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

import javax.faces.context.FacesContext;

import org.govmix.proxy.fatturapa.AllegatoFattura;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputButton;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;

/**
 * AllegatoFatturaBean definisce il bean per la visualizzazione dell'allegato fattura 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class AllegatoFatturaBean extends BaseBean<AllegatoFattura, Long>{

	private 	OutputField<String> nomeAttachment =null;
	private 	OutputField<String> algoritmoCompressione =null;
	private 	OutputField<String> formatoAttachment =null;
	private 	OutputField<String> descrizioneAttachment =null;
	private 	OutputField<String> attachment =null;

	public AllegatoFatturaBean(){
		initFields();
	}


	@Override
	public void setDTO(AllegatoFattura dto) {
		super.setDTO(dto);

		// Valorizzare
		this.nomeAttachment.setValue(this.getDTO().getNomeAttachment());
		this.descrizioneAttachment.setValue(this.getDTO().getDescrizioneAttachment());
		this.algoritmoCompressione.setValue(this.getDTO().getAlgoritmoCompressione());
		this.formatoAttachment.setValue(this.getDTO().getFormatoAttachment());

		prepareUrls();
	}
	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}


	private void initFields(){
		this.nomeAttachment = new OutputText();
		this.nomeAttachment.setLabel("allegatoFattura.nomeAttachment");
		this.nomeAttachment.setName("nomeAttachment"); 

		this.algoritmoCompressione = new OutputText();
		this.algoritmoCompressione.setLabel("allegatoFattura.algoritmoCompressione");
		this.algoritmoCompressione.setName("algoritmoCompressione"); 

		this.formatoAttachment = new OutputText();
		this.formatoAttachment.setLabel("allegatoFattura.formatoAttachment");
		this.formatoAttachment.setName("formatoAttachment"); 

		this.descrizioneAttachment = new OutputText();
		this.descrizioneAttachment.setLabel(("allegatoFattura.descrizioneAttachment"));
		this.descrizioneAttachment.setName("descrizioneAttachment"); 

		this.attachment = new OutputButton();
		this.attachment.setLabel(("allegatoFattura.attachment.visualizza"));
		this.attachment.setName("attachment");
		((OutputButton) this.attachment).setIcon("document.png");
		((OutputButton) this.attachment).setIconTitle(("allegatoFattura.attachment.visualizza.iconTitle"));
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();



		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_ALLEGATO ;

		((OutputButton) this.attachment).setLink(this.getDTO().getAttachment() != null ?  url : null);

	}

	public OutputField<String> getNomeAttachment() {
		return nomeAttachment;
	}


	public void setNomeAttachment(OutputField<String> nomeAttachment) {
		this.nomeAttachment = nomeAttachment;
	}


	public OutputField<String> getAlgoritmoCompressione() {
		return algoritmoCompressione;
	}


	public void setAlgoritmoCompressione(OutputField<String> algoritmoCompressione) {
		this.algoritmoCompressione = algoritmoCompressione;
	}


	public OutputField<String> getFormatoAttachment() {
		return formatoAttachment;
	}


	public void setFormatoAttachment(OutputField<String> formatoAttachment) {
		this.formatoAttachment = formatoAttachment;
	}


	public OutputField<String> getDescrizioneAttachment() {
		return descrizioneAttachment;
	}


	public void setDescrizioneAttachment(OutputField<String> descrizioneAttachment) {
		this.descrizioneAttachment = descrizioneAttachment;
	}


	public OutputField<String> getAttachment() {
		return attachment;
	}


	public void setAttachment(OutputField<String> attachment) {
		this.attachment = attachment;
	}


}
