/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.orm.constants.ScartoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputField;
import org.openspcoop2.generic_project.web.output.Text;

/**
 * NotificaECBean definisce il bean per la visualizzazione della Notifica esito committente. 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECBean extends BaseBean<NotificaEsitoCommittente, Long> implements IBean<NotificaEsitoCommittente, Long>{

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

	private Text separatore = null;
	private Text statoConsegnaSdi = null;
	
	private DateTime dataProssimaConsegnaSdi =null;

	public NotificaECBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.scarto = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("scarto","notificaEsitoCommittente.scarto");
		this.scartoNote = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("scartoNote","notificaEsitoCommittente.scartoNote");
		this.esito = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("esito","notificaEsitoCommittente.esito");
		this.data = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("data","notificaEsitoCommittente.data",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY);
		this.dataInvio = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataInvio","notificaEsitoCommittente.dataInvio",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY);
		this.descrizione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("descrizione","notificaEsitoCommittente.descrizione");
		this.modalitaBatch = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("modalitaBatch","notificaEsitoCommittente.modalitaBatch");

		this.xml = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("xml","notificaEsitoCommittente.label.xml",null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_XML,"notificaEsitoCommittente.label.xml.iconTitle","notificaEsitoCommittente.label.xml.iconTitle");
		this.pdf = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("pdf","notificaEsitoCommittente.label.pdf",null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_PDF,"notificaEsitoCommittente.label.pdf.iconTitle","notificaEsitoCommittente.label.pdf.iconTitle");
		this.scartoXml = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("scartoXml","notificaEsitoCommittente.scarto.label.xml",null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_XML,"notificaEsitoCommittente.scarto.label.xml.iconTitle","notificaEsitoCommittente.scarto.label.xml.iconTitle");
		this.scartoPdf = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("scartoPdf","notificaEsitoCommittente.scarto.label.pdf",null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_PDF,"notificaEsitoCommittente.scarto.label.pdf.iconTitle","notificaEsitoCommittente.scarto.label.pdf.iconTitle");
		this.utente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("utente","notificaEsitoCommittente.utente");
		
		this.separatore = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("separatore",null);
		this.separatore.setValue(" "); 
		
		this.statoConsegnaSdi = this.getWebGenericProjectFactory().getOutputFieldFactory().createText( "statoConsegnaSdi","notificaEsitoCommittente.statoConsegna");
		this.dataProssimaConsegnaSdi = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataProssimaConsegnaSdi","notificaEsitoCommittente.dataProssimaConsegna","dd/M/yyyy HH:mm" );

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
		this.setField(this.separatore);
		this.setField(this.statoConsegnaSdi);
		this.setField(this.dataProssimaConsegnaSdi);
		
	}
	
	@Override
	public void setField(OutputField<?> field) {
		super.setField(field);
		field.setLabelStyleClass("outputFieldLabel outputFieldLabelPreWrap");
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
		String mod = modalitaPush2 ? org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.modalitaBatch.automatica")
				: org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.modalitaBatch.manuale");
		this.modalitaBatch.setValue(mod);

		this.dataProssimaConsegnaSdi.setValue(this.getDTO().getDataProssimaConsegnaSdi());
		this.dataProssimaConsegnaSdi.setRendered(false);
		
		StatoConsegnaType statoConsegnaE = this.getDTO().getStatoConsegnaSdi(); 
		if(statoConsegnaE != null){
			if(statoConsegnaE.equals(StatoConsegnaType.CONSEGNATA)) {
				this.statoConsegnaSdi.setValue("notificaEsitoCommittente.statoConsegna.consegnata");
			} else if(statoConsegnaE.equals(StatoConsegnaType.IN_RICONSEGNA)) {
				this.statoConsegnaSdi.setValue("notificaEsitoCommittente.statoConsegna.inRiconsegna");
				this.dataProssimaConsegnaSdi.setRendered(true);
			} else if(statoConsegnaE.equals(StatoConsegnaType.ERRORE_CONSEGNA)) {
				this.statoConsegnaSdi.setValue("notificaEsitoCommittente.statoConsegna.erroreConsegna");
			} else {
				this.statoConsegnaSdi.setValue("notificaEsitoCommittente.statoConsegna.nonConsegnata");
			}
		}

		this.prepareUrls();
	}

	public Text getEsito() {
		return this.esito;
	}

	public void setEsito(Text esito) {
		this.esito = esito;
	}

	public DateTime getDataInvio() {
		return this.dataInvio;
	}

	public void setDataInvio(DateTime dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getScarto() {
		return this.scarto;
	}

	public void setScarto(Text scarto) {
		this.scarto = scarto;
	}

	public Text getScartoNote() {
		return this.scartoNote;
	}

	public void setScartoNote(Text scartoNote) {
		this.scartoNote = scartoNote;
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

	public DateTime getData() {
		return this.data;
	}

	public void setData(DateTime data) {
		this.data = data;
	}

	public Button getScartoXml() {
		return this.scartoXml;
	}

	public void setScartoXml(Button scartoXml) {
		this.scartoXml = scartoXml;
	}

	public Button getScartoPdf() {
		return this.scartoPdf;
	}

	public void setScartoPdf(Button scartoPdf) {
		this.scartoPdf = scartoPdf;
	}
	public Text getUtente() {
		return this.utente;
	}

	public void setUtente(Text utente) {
		this.utente = utente;
	}

	public Text getModalitaBatch() {
		return this.modalitaBatch;
	}

	public void setModalitaBatch(Text modalitaBatch) {
		this.modalitaBatch = modalitaBatch;
	}
	
	public Text getSeparatore() {
		return separatore;
	}

	public void setSeparatore(Text separatore) {
		this.separatore = separatore;
	}
	
	
	
	public Text getStatoConsegnaSdi() {
		return statoConsegnaSdi;
	}

	public void setStatoConsegnaSdi(Text statoConsegnaSdi) {
		this.statoConsegnaSdi = statoConsegnaSdi;
	}

	public DateTime getDataProssimaConsegnaSdi() {
		return dataProssimaConsegnaSdi;
	}

	public void setDataProssimaConsegnaSdi(DateTime dataProssimaConsegnaSdi) {
		this.dataProssimaConsegnaSdi = dataProssimaConsegnaSdi;
	}

	public boolean isVisualizzaLinkRiconsegnaNotificaEC(){
		if(this.getDTO().getStatoConsegnaSdi() != null){
			boolean isAdmin = Utils.getLoginBean().isAdmin();
			StatoConsegnaType statoConsegnaType =  this.getDTO().getStatoConsegnaSdi();
			
			if((statoConsegnaType.equals(StatoConsegnaType.ERRORE_CONSEGNA) || 
					statoConsegnaType.equals(StatoConsegnaType.IN_RICONSEGNA)) && isAdmin)
				return true;
		}
		
		return false;
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();



		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_EC;

		this.xml.setHref(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_NOTIFICA_EC;

		this.pdf.setHref( this.getDTO().getXml() != null ? url : null);


		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_SCARTO;


		this.scartoXml.setHref(this.getDTO().getScartoXml() != null ? url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_SCARTO;
		this.scartoPdf.setHref( this.getDTO().getScartoXml() != null ? url : null);
	}

}
