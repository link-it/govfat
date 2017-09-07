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

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.view.IViewBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.Text;

/**
 * AllegatoFatturaBean definisce il bean per la visualizzazione dell'allegato fattura 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class AllegatoFatturaBean extends BaseBean<AllegatoFattura, Long> implements IViewBean<AllegatoFattura, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Text nomeAttachment =null;
	private Text algoritmoCompressione =null;
	private Text formatoAttachment =null;
	private Text descrizioneAttachment =null;
	private Button attachment =null;

	public AllegatoFatturaBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.nomeAttachment = this.getFactory().getOutputFieldFactory().createText("nomeAttachment","allegatoFattura.nomeAttachment");
		this.algoritmoCompressione =   this.getFactory().getOutputFieldFactory().createText("algoritmoCompressione","allegatoFattura.algoritmoCompressione");
		this.formatoAttachment =  this.getFactory().getOutputFieldFactory().createText("formatoAttachment","allegatoFattura.formatoAttachment");
		this.descrizioneAttachment =  this.getFactory().getOutputFieldFactory().createText("descrizioneAttachment","allegatoFattura.descrizioneAttachment");
		this.attachment =  this.getFactory().getOutputFieldFactory().createButton("attachment","allegatoFattura.attachment.visualizza",null,"/images/fatturapa/icons/document.png","allegatoFattura.attachment.visualizza.iconTitle","allegatoFattura.attachment.visualizza.iconTitle");
		
		this.setField(this.nomeAttachment);
		this.setField(this.algoritmoCompressione);
		this.setField(this.formatoAttachment);
		this.setField(this.descrizioneAttachment);
		this.setField(this.attachment);
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();
		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_ALLEGATO ;

		this.attachment.setHref(this.getDTO().getAttachment() != null ?  url : null);
	}
	
	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(AllegatoFattura dto) {
		super.setDTO(dto);

		// Valorizzare
		this.nomeAttachment.setValue(this.getDTO().getNomeAttachment());
		this.descrizioneAttachment.setValue(this.getDTO().getDescrizioneAttachment());
		this.algoritmoCompressione.setValue(this.getDTO().getAlgoritmoCompressione());
		this.formatoAttachment.setValue(this.getDTO().getFormatoAttachment());

		this.prepareUrls();
	}

	public Text getNomeAttachment() {
		return this.nomeAttachment;
	}


	public void setNomeAttachment(Text nomeAttachment) {
		this.nomeAttachment = nomeAttachment;
	}


	public Text getAlgoritmoCompressione() {
		return this.algoritmoCompressione;
	}


	public void setAlgoritmoCompressione(Text algoritmoCompressione) {
		this.algoritmoCompressione = algoritmoCompressione;
	}


	public Text getFormatoAttachment() {
		return this.formatoAttachment;
	}


	public void setFormatoAttachment(Text formatoAttachment) {
		this.formatoAttachment = formatoAttachment;
	}


	public Text getDescrizioneAttachment() {
		return this.descrizioneAttachment;
	}


	public void setDescrizioneAttachment(Text descrizioneAttachment) {
		this.descrizioneAttachment = descrizioneAttachment;
	}


	public Button getAttachment() {
		return this.attachment;
	}


	public void setAttachment(Button attachment) {
		this.attachment = attachment;
	}


}
