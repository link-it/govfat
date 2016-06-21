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
package org.govmix.proxy.fatturapa.web.console.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.Image;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.OutputNumber;
import org.openspcoop2.generic_project.web.output.Text;

/**
 * FatturaElettronicaBean definisce il bean per la visualizzazione della fattura 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FatturaElettronicaBean extends BaseBean<FatturaElettronica, Long> implements IBean<FatturaElettronica, Long>{


	// Field Che visualizzo nella maschera di ricerca
	private Text cedentePrestatore = null;
	private Text dipartimento = null;
	private Text annoNumero = null;
	private DateTime dataRicezione = null;
	private OutputNumber importo = null; 
	private OutputNumber importoRiepilogo = null; 
	private Button notificaEC = null;
	private Image notificaDT = null;
	private Button xml = null;
	private Button pdf = null;
	private Button zip = null;

	// Field da aggiungere nel dettaglio
	private Text identificativoSdi = null;
	private Text posizione = null;
	private Text cedentePrestatoreCF = null;
	private Text cedentePrestatorePaese = null;
	private Text cessionarioCommittente = null;
	private Text cessionarioCommittentePaese = null;
	private Text cessionarioCommittenteCF = null;
	private Text terzoIntermediarioOSoggettoEmittente = null;
	private Text terzoIntermediarioOSoggettoEmittentePaese = null;
	private Text terzoIntermediarioOSoggettoEmittenteCF = null;
	private Text codiceDestinatario = null;
	private Text tipoDocumento = null;
	private Text nomeFile = null;
	private Text messageId = null;
	private Text divisa = null;
	private DateTime data = null;
	private Text numero = null;
	private Text anno = null;
	private Text causale = null;
	private Text statoConsegna = null;
	private Text protocollo = null;
	private DateTime dataConsegna = null;
	private Text formatoTrasmissione = null;
	private DateTime dataProssimaConsegna = null;
	private DateTime dataScadenza = null;
	private Text dataScadenzaAssente = null;

	// Informazioni necessarie per la visualizzazione del dettaglio
	// Metadata Allegati
	private AllegatoFatturaBean metadataAllegato = new AllegatoFatturaBean();
	// Lista degli allegati
	private List<AllegatoFatturaBean> allegati = new ArrayList<AllegatoFatturaBean>();

	//Metadata Notifiche di esito commitente
	private NotificaECBean metadataNotificaEC = new NotificaECBean();
	// Lista notifiche EC
	private List<NotificaECBean> listaNotificaEC = new ArrayList<NotificaECBean>();

	//Metadata  notifica decorrenza termini
	private NotificaDTBean metadataNotificaDT = new NotificaDTBean();
	// lista notifiche DT
	private List<NotificaDTBean> listaNotificaDT = new ArrayList<NotificaDTBean>();

	// Gruppo Informazioni Dati Genareli
	private OutputGroup datiIntestazione = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup datiTrasmissione1 = null;
	//Gruppo informazioni Contenuto Fattura
	private OutputGroup contenutoFattura = null;

	//Gruppo informazioni Contenuto Fattura
	private OutputGroup causaleFattura = null;

	private boolean showPCC = false;

	public FatturaElettronicaBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.cedentePrestatore = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cedentePrestatore","fattura.cedentePrestatoreDenominazione");
		this.cedentePrestatorePaese = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cedentePrestatorePaese","fattura.cedentePrestatorePaese");
		this.dipartimento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("dipartimento","fattura.dipartimento");
		this.annoNumero = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("annoNumero","fattura.annoNumero");
		this.dataRicezione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataRicezione","fattura.dataRicezione","dd/M/yyyy");

		this.importo = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("importo","fattura.importoTotaleDocumento");
		this.importo.setConverterType(Costanti.CONVERT_TYPE_CURRENCY);
		this.importo.setCurrencySymbol(Costanti.CURRENCY_SYMBOL_EURO);
		this.importo.setTableColumnStyleClass("allinatoDX");

		this.importoRiepilogo = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("importoRiepilogo","fattura.importoTotaleRiepilogo");
		this.importoRiepilogo.setValueStyleClass("diag_error");
		this.importoRiepilogo.setConverterType(Costanti.CONVERT_TYPE_CURRENCY);
		this.importoRiepilogo.setCurrencySymbol(Costanti.CURRENCY_SYMBOL_EURO);

		this.notificaEC = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("notificaEC","fattura.notificaEC");
		this.notificaDT = this.getWebGenericProjectFactory().getOutputFieldFactory().createImage("notificaDT","fattura.notificaDT");

		this.xml = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("xml","commons.label.xml",null,"/images/fatturapa/icons/xml.png","commons.label.xml.iconTitle","commons.label.xml.iconTitle");
		this.pdf = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("pdf","commons.label.pdf",null,"/images/fatturapa/icons/pdf.png","commons.label.pdf.iconTitle","commons.label.pdf.iconTitle");
		this.zip = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("zip","commons.button.scaricaTutto",null,"/images/fatturapa/icons/zip.png","commons.button.scaricaTutto.iconTitle","commons.button.scaricaTutto.iconTitle");

		this.identificativoSdi = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("identificativoSdi","fattura.identificativoSdi");
		this.posizione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("posizione","fattura.posizione");
		this.cedentePrestatoreCF = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cedentePrestatoreCF","fattura.cedentePrestatoreCF");
		this.cessionarioCommittente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cessionarioCommittente","fattura.cessionarioCommittente");
		this.cessionarioCommittentePaese = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cessionarioCommittentePaese","fattura.cessionarioCommittentePaese");
		this.cessionarioCommittenteCF = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cessionarioCommittenteCF","fattura.cessionarioCommittenteCF");
		this.terzoIntermediarioOSoggettoEmittente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("terzoIntermediarioOSoggettoEmittente","fattura.terzoIntermediarioOSoggettoEmittente");
		this.terzoIntermediarioOSoggettoEmittentePaese = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("terzoIntermediarioOSoggettoEmittentePaese","fattura.terzoIntermediarioOSoggettoEmittentePaese");
		this.terzoIntermediarioOSoggettoEmittenteCF = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("terzoIntermediarioOSoggettoEmittenteCF","fattura.terzoIntermediarioOSoggettoEmittenteCF");
		this.codiceDestinatario = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceDestinatario","fattura.codiceDestinatario");
		this.tipoDocumento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("tipoDocumento","fattura.tipoDocumento");
		this.nomeFile = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("nomeFile","fattura.nomeFile");
		this.messageId = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("messageId","fattura.messageId");
		this.divisa = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("divisa","fattura.divisa");
		this.data = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("data","fattura.data","dd/M/yyyy");
		this.dataConsegna = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataConsegna","fattura.dataConsegna","dd/M/yyyy");

		this.numero = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("numero","fattura.numero");
		this.anno = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("anno","fattura.anno");
		this.causale = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("causale","fattura.causale","fattura.causale.assente");
		this.causale.setValueStyleClass("whiteSpaceNewLine");
		this.protocollo = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("protocollo","fattura.protocollo","fattura.protocollo.assente");
		this.statoConsegna = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("statoConsegna","fattura.statoConsegna");
		this.formatoTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("formatoTrasmissione","fattura.formatoTrasmissione");

		this.dataProssimaConsegna = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataProssimaConsegna","fattura.dataProssimaConsegna","dd/MM/yyyy HH:mm");
		this.dataScadenza = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataScadenza","fattura.dataScadenza","dd/M/yyyy");
		this.dataScadenzaAssente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("dataScadenzaAssente","fattura.dataScadenzaAssente");
		
		this.setField(this.cedentePrestatore);
		this.setField(this.cedentePrestatorePaese);
		this.setField(this.dipartimento);
		this.setField(this.annoNumero);
		this.setField(this.dataRicezione);
		this.setField(this.importo);
		this.setField(this.importoRiepilogo);
		this.setField(this.notificaEC);
		this.setField(this.notificaDT);
		this.setField(this.xml);
		this.setField(this.pdf);
		this.setField(this.zip);
		this.setField(this.identificativoSdi);
		this.setField(this.posizione);
		this.setField(this.cedentePrestatoreCF);
		this.setField(this.cessionarioCommittente);
		this.setField(this.cessionarioCommittentePaese);
		this.setField(this.cessionarioCommittenteCF);
		this.setField(this.terzoIntermediarioOSoggettoEmittente);
		this.setField(this.terzoIntermediarioOSoggettoEmittentePaese);
		this.setField(this.terzoIntermediarioOSoggettoEmittenteCF);
		this.setField(this.codiceDestinatario);
		this.setField(this.tipoDocumento);
		this.setField(this.nomeFile);
		this.setField(this.messageId);
		this.setField(this.divisa);
		this.setField(this.data);
		this.setField(this.dataConsegna);
		this.setField(this.numero);
		this.setField(this.anno);
		this.setField(this.causale);
		this.setField(this.protocollo);
		this.setField(this.statoConsegna);
		this.setField(this.formatoTrasmissione);
		this.setField(this.dataProssimaConsegna);
		this.setField(this.dataScadenza);
		this.setField(this.dataScadenzaAssente);

		this.datiIntestazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiIntestazione",6);
		this.datiIntestazione.setRendered(true);
		this.datiIntestazione.setStyleClass("datiTrasmissioneTable"); 
		this.datiIntestazione.setColumnClasses("labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx");
		this.datiIntestazione.addField(this.cessionarioCommittente);
		this.datiIntestazione.addField(this.cessionarioCommittenteCF);
		this.datiIntestazione.addField(this.cessionarioCommittentePaese);

		this.datiIntestazione.addField(this.cedentePrestatore);
		this.datiIntestazione.addField(this.cedentePrestatoreCF);
		this.datiIntestazione.addField(this.cedentePrestatorePaese);

		this.datiIntestazione.addField(this.terzoIntermediarioOSoggettoEmittente);
		this.datiIntestazione.addField(this.terzoIntermediarioOSoggettoEmittenteCF);
		this.datiIntestazione.addField(this.terzoIntermediarioOSoggettoEmittentePaese);


		//Dati Trasmissione Riga 1

		this.datiTrasmissione1 = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiTrasmissione1",5);
		this.datiTrasmissione1.setRendered(true);
		this.datiTrasmissione1.setStyleClass("datiTrasmissioneTable"); 
		this.datiTrasmissione1.setColumnClasses("labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx,valueAllineataSx");
		this.datiTrasmissione1.addField(this.identificativoSdi);
		this.datiTrasmissione1.addField(this.posizione);
		this.datiTrasmissione1.addField(this.nomeFile);
		this.datiTrasmissione1.addField(this.formatoTrasmissione);
		this.datiTrasmissione1.addField(this.tipoDocumento);
		this.datiTrasmissione1.addField(this.messageId);
		this.datiTrasmissione1.addField(this.dipartimento);
		this.datiTrasmissione1.addField(this.codiceDestinatario);
		this.datiTrasmissione1.addField(this.dataRicezione);
		this.datiTrasmissione1.addField(this.dataScadenza);
		this.datiTrasmissione1.addField(this.dataConsegna);
		this.datiTrasmissione1.addField(this.dataProssimaConsegna);
		this.datiTrasmissione1.addField(this.statoConsegna);
		this.datiTrasmissione1.addField(this.protocollo);

		this.contenutoFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("contenutoFattura",4);
		this.contenutoFattura.setRendered(true);
		this.contenutoFattura.setStyleClass("datiTrasmissioneTable"); 
		this.contenutoFattura.setColumnClasses("labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx");

		this.contenutoFattura.addField(this.divisa);
		this.contenutoFattura.addField(this.importo);
		this.contenutoFattura.addField(this.data);
		this.contenutoFattura.addField(this.numero);


		this.causaleFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("causaleFattura",2);
		this.causaleFattura.setRendered(true);
		this.causaleFattura.setStyleClass("datiTrasmissioneTable"); 
		this.causaleFattura.setColumnClasses("labelAllineataDx align-top,valueAllineataSx");
		this.causaleFattura.addField(this.causale);
		
	}


	private double truncate(double value) {
		long integerValue = Math.round(value * 100);
		return integerValue / 100.0;
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(FatturaElettronica dto) {
		super.setDTO(dto);

		this.cedentePrestatore.setValue(this.getDTO().getCedentePrestatoreDenominazione());

		List<Dipartimento> listaDipartimentiLoggedUtente = org.govmix.proxy.fatturapa.web.console.util.Utils.getListaDipartimentiLoggedUtente();

		for (Dipartimento dipartimento : listaDipartimentiLoggedUtente) {
			if(dipartimento.getCodice().equals(this.getDTO().getCodiceDestinatario())){
				this.dipartimento.setValue(dipartimento.getDescrizione());
				break;
			}
		}

		//		this.annoNumero.setValue(this.getDTO().getAnno() + "-" + this.getDTO().getNumero());
		this.annoNumero.setValue(this.getDTO().getNumero());
		this.dataRicezione.setValue(this.getDTO().getDataRicezione());
		Double importoTotaleDocumento = this.getDTO().getImportoTotaleDocumento();
		this.importo.setValue(importoTotaleDocumento);
		Double importoTotaleRiepilogo = this.getDTO().getImportoTotaleRiepilogo();
		this.importoRiepilogo.setValue(importoTotaleRiepilogo);

		boolean importiIncoerenti = this.truncate(importoTotaleDocumento.doubleValue()) != this.truncate(importoTotaleRiepilogo.doubleValue());
		if(importoTotaleRiepilogo != null && importoTotaleDocumento != null
				&& importiIncoerenti){
			this.contenutoFattura.getFields().clear();
			this.contenutoFattura.addField(this.divisa);
			this.contenutoFattura.addField(this.importo);
			this.contenutoFattura.addField(this.data);
			this.contenutoFattura.addField(this.importoRiepilogo);
			this.contenutoFattura.addField(this.numero);
			//	this.contenutoFattura.addField(this.causale);
		}else {
			this.contenutoFattura.getFields().clear();
			this.contenutoFattura.addField(this.divisa);
			this.contenutoFattura.addField(this.importo);
			this.contenutoFattura.addField(this.data);
			//			this.contenutoFattura.addField(this.causale);
			this.contenutoFattura.addField(this.numero);
		}

		//valore dell'icona notifica ec
		//Non Esitato
		if(this.dto.getEsito()== null){
			this.notificaEC.setImage("/images/fatturapa/icons/plus-grey.png");
			this.notificaEC.setTitle(("fattura.notificaEC.iconTitle.nonPresente"));
			this.notificaEC.setAlt("fattura.notificaEC.iconTitle.nonPresente");
			if(this.dto.getIdDecorrenzaTermini() == null)
				this.notificaEC.setHref("linkToDownload");
			else 
				this.notificaEC.setHref(null);
		}else {
			// In elaborazione accettata
			if(this.dto.getEsito().equals(EsitoType.IN_ELABORAZIONE_ACCETTATO)){
				this.notificaEC.setImage("/images/fatturapa/icons/accept_circle-yellow.png");
				this.notificaEC.setTitle( ("fattura.notificaEC.iconTitle.inElaborazione.accettato"));
				this.notificaEC.setAlt("fattura.notificaEC.iconTitle.inElaborazione.accettato" );
				this.notificaEC.setHref(null);
				//In elaborazione rifiutata
			}else if(this.dto.getEsito().equals(EsitoType.IN_ELABORAZIONE_RIFIUTATO)){
				this.notificaEC.setImage("/images/fatturapa/icons/no_accept-yellow.png");
				this.notificaEC.setTitle( ("fattura.notificaEC.iconTitle.inElaborazione.rifiutato"));
				this.notificaEC.setAlt( "fattura.notificaEC.iconTitle.inElaborazione.rifiutato");
				this.notificaEC.setHref(null);
				//Accettato
			}	
			else if(this.dto.getEsito().equals(EsitoType.INVIATA_ACCETTATO)){
				this.notificaEC.setImage("/images/fatturapa/icons/accept_circle-green.png");
				this.notificaEC.setTitle( ("fattura.notificaEC.iconTitle.inviata.accettato"));
				this.notificaEC.setAlt("fattura.notificaEC.iconTitle.inviata.accettato" );
				this.notificaEC.setHref(null);
			}else if(this.dto.getEsito().equals(EsitoType.INVIATA_RIFIUTATO)){
				this.notificaEC.setImage("/images/fatturapa/icons/no_accept-green.png");
				this.notificaEC.setTitle( ("fattura.notificaEC.iconTitle.inviata.rifiutato"));
				this.notificaEC.setAlt("fattura.notificaEC.iconTitle.inviata.rifiutato" );
				this.notificaEC.setHref(null);
				//Accettato
			}	
			else if(this.dto.getEsito().equals(EsitoType.SCARTATA_ACCETTATO)){
				this.notificaEC.setImage("/images/fatturapa/icons/accept_circle-red.png");
				this.notificaEC.setTitle( ("fattura.notificaEC.iconTitle.scartata.accettato"));
				this.notificaEC.setAlt( "fattura.notificaEC.iconTitle.scartata.accettato");
				this.notificaEC.setHref(null);
			}else {
				//Rifiutato
				this.notificaEC.setImage("/images/fatturapa/icons/no_accept-red.png");
				this.notificaEC.setTitle( ("fattura.notificaEC.iconTitle.scartata.rifiutato"));
				this.notificaEC.setAlt( "fattura.notificaEC.iconTitle.scartata.rifiutato");
				this.notificaEC.setHref(null);
			}
		}

		if(this.dto.getIdDecorrenzaTermini() != null){
			this.notificaDT.setRendered(true); 
			this.notificaDT.setImage("/images/fatturapa/icons/accept-green.png");
			this.notificaDT.setTitle( ("fattura.notificaDT.iconTitle.presente"));
			this.notificaDT.setAlt("fattura.notificaDT.iconTitle.presente" );
		} else {
			this.notificaDT.setRendered(false); 
			this.notificaDT.setImage("/images/fatturapa/icons/no_accept-red.png");
			this.notificaDT.setTitle( ("fattura.notificaDT.iconTitle.nonPresente"));
			this.notificaDT.setAlt("fattura.notificaDT.iconTitle.nonPresente" );
		}

		this.prepareUrls();

		this.posizione.setValue(this.getDTO().getPosizione() + "");
		this.identificativoSdi.setValue(this.getDTO().getIdentificativoSdi() + "");
		this.cedentePrestatoreCF.setValue(this.getDTO().getCedentePrestatoreCodiceFiscale());
		this.cedentePrestatorePaese.setValue(this.getDTO().getCedentePrestatorePaese());
		this.cessionarioCommittente.setValue(this.getDTO().getCessionarioCommittenteDenominazione());
		this.cessionarioCommittenteCF.setValue(this.getDTO().getCessionarioCommittenteCodiceFiscale());
		this.cessionarioCommittentePaese.setValue(this.getDTO().getCessionarioCommittentePaese());


		String terzoIntermediarioOSoggettoEmittenteDenominazione = this.getDTO().getTerzoIntermediarioOSoggettoEmittenteDenominazione();
		this.terzoIntermediarioOSoggettoEmittente.setValue(terzoIntermediarioOSoggettoEmittenteDenominazione);
		String terzoIntermediarioOSoggettoEmittenteCodiceFiscale = this.getDTO().getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale();
		this.terzoIntermediarioOSoggettoEmittenteCF.setValue(terzoIntermediarioOSoggettoEmittenteCodiceFiscale);
		this.terzoIntermediarioOSoggettoEmittentePaese.setValue(this.getDTO().getTerzoIntermediarioOSoggettoEmittentePaese());

		// Visualizzo la riga solo se almeno uno tra denominazione e CF e' valido.
		if(terzoIntermediarioOSoggettoEmittenteDenominazione == null && terzoIntermediarioOSoggettoEmittenteCodiceFiscale == null){
			this.terzoIntermediarioOSoggettoEmittente.setRendered(false); 
			this.terzoIntermediarioOSoggettoEmittenteCF.setRendered(false); 
			this.terzoIntermediarioOSoggettoEmittentePaese.setRendered(false); 
		}

		this.codiceDestinatario.setValue(this.getDTO().getCodiceDestinatario());
		TipoDocumentoType tipoDocumento2 = this.getDTO().getTipoDocumento();
		if(tipoDocumento2 != null){
			String valueTD = tipoDocumento2.getValue();
			this.tipoDocumento.setValue("fattura.tipoDocumento."+valueTD);
		}
		this.tipoDocumento.getValue();
		this.nomeFile.setValue(this.getDTO().getNomeFile());
		this.messageId.setValue(this.getDTO().getMessageId());
		this.divisa.setValue(this.getDTO().getDivisa());
		this.data.setValue(this.getDTO().getData());
		this.numero.setValue(this.getDTO().getNumero());
		this.anno.setValue(this.getDTO().getAnno() + "");
		this.causale.setValue(this.getDTO().getCausale());

		this.protocollo.setValue(this.getDTO().getProtocollo());

		 
		this.dataConsegna.setValue(this.getDTO().getDataConsegna());
		
		this.dataScadenza.setValue(null);
		if(this.getDTO().getDataScadenza() == null){
			this.dataScadenzaAssente.setValue("--");
		}else {
			if(Utils.isDataScadenzaSconosciuta(this.getDTO().getDataScadenza()))
				this.dataScadenzaAssente.setValue("fattura.dataScadenza.sconosciuta"); 
			else 
				this.dataScadenza.setValue(this.getDTO().getDataScadenza());
		}
		
		this.dataProssimaConsegna.setValue(this.getDTO().getDataProssimaConsegna());   
		this.dataProssimaConsegna.setRendered(false);
		FormatoTrasmissioneType formatoTrasmissione2 = this.getDTO().getFormatoTrasmissione();
		if(formatoTrasmissione2 != null){
			switch (formatoTrasmissione2) {
			case SDI11:
				this.formatoTrasmissione.setValue("fattura.formatoTrasmissione.sdi11");
				break;
			case SDI10:
			default:
				this.formatoTrasmissione.setValue("fattura.formatoTrasmissione.sdi10");
				break;
			}

		}

		StatoConsegnaType statoConsegnaE = this.getDTO().getStatoConsegna(); 

		if(this.getDTO().getProtocollo() != null) {
			this.statoConsegna.setValue("fattura.statoConsegna.protocollata");
		} else {
			if(statoConsegnaE.equals(StatoConsegnaType.CONSEGNATA)) {
				this.statoConsegna.setValue("fattura.statoConsegna.consegnata");
			} else if(statoConsegnaE.equals(StatoConsegnaType.IN_RICONSEGNA)) {
				this.statoConsegna.setValue("fattura.statoConsegna.inRiconsegna");
				this.dataProssimaConsegna.setRendered(true);
			} else if(statoConsegnaE.equals(StatoConsegnaType.ERRORE_CONSEGNA)) {
				this.statoConsegna.setValue("fattura.statoConsegna.erroreConsegna");
			} else {
				this.statoConsegna.setValue("fattura.statoConsegna.nonConsegnata");
			}
		}
	}

	public Text getCedentePrestatore() {
		return this.cedentePrestatore;
	}

	public void setCedentePrestatore(Text cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public Text getAnnoNumero() {
		return this.annoNumero;
	}

	public void setAnnoNumero(Text annoNumero) {
		this.annoNumero = annoNumero;
	}

	public DateTime getDataRicezione() {
		return this.dataRicezione;
	}

	public void setDataRicezione(DateTime dataRicezione) {
		this.dataRicezione = dataRicezione;
	}



	public Text getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(Text dipartimento) {
		this.dipartimento = dipartimento;
	}

	public Button getNotificaEC() {
		return this.notificaEC;
	}

	public void setNotificaEC(Button notificaEC) {
		this.notificaEC = notificaEC;
	}

	public Image getNotificaDT() {
		return this.notificaDT;
	}

	public void setNotificaDT(Image notificaDT) {
		this.notificaDT = notificaDT;
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

	public Text getIdentificativoSdi() {
		return this.identificativoSdi;
	}

	public void setIdentificativoSdi(Text identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}

	public Text getCedentePrestatoreCF() {
		return this.cedentePrestatoreCF;
	}

	public void setCedentePrestatoreCF(Text cedentePrestatoreCF) {
		this.cedentePrestatoreCF = cedentePrestatoreCF;
	}

	public Text getCessionarioCommittente() {
		return this.cessionarioCommittente;
	}

	public void setCessionarioCommittente(Text cessionarioCommittente) {
		this.cessionarioCommittente = cessionarioCommittente;
	}

	public Text getCessionarioCommittenteCF() {
		return this.cessionarioCommittenteCF;
	}

	public void setCessionarioCommittenteCF(
			Text cessionarioCommittenteCF) {
		this.cessionarioCommittenteCF = cessionarioCommittenteCF;
	}

	public Text getTerzoIntermediarioOSoggettoEmittente() {
		return this.terzoIntermediarioOSoggettoEmittente;
	}

	public void setTerzoIntermediarioOSoggettoEmittente(
			Text terzoIntermediarioOSoggettoEmittente) {
		this.terzoIntermediarioOSoggettoEmittente = terzoIntermediarioOSoggettoEmittente;
	}

	public Text getTerzoIntermediarioOSoggettoEmittenteCF() {
		return this.terzoIntermediarioOSoggettoEmittenteCF;
	}

	public void setTerzoIntermediarioOSoggettoEmittenteCF(
			Text terzoIntermediarioOSoggettoEmittenteCF) {
		this.terzoIntermediarioOSoggettoEmittenteCF = terzoIntermediarioOSoggettoEmittenteCF;
	}

	public Text getCodiceDestinatario() {
		return this.codiceDestinatario;
	}

	public void setCodiceDestinatario(Text codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public Text getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(Text tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Text getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(Text nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Text getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Text messageId) {
		this.messageId = messageId;
	}

	public Text getDivisa() {
		return this.divisa;
	}

	public void setDivisa(Text divisa) {
		this.divisa = divisa;
	}

	public DateTime getData() {
		return this.data;
	}

	public void setData(DateTime data) {
		this.data = data;
	}

	public Text getNumero() {
		return this.numero;
	}

	public void setNumero(Text numero) {
		this.numero = numero;
	}

	public Text getAnno() {
		return this.anno;
	}

	public void setAnno(Text anno) {
		this.anno = anno;
	}

	public Text getCausale() {
		return this.causale;
	}

	public void setCausale(Text causale) {
		this.causale = causale;
	}

	public AllegatoFatturaBean getMetadataAllegato() {
		return this.metadataAllegato;
	}

	public void setMetadataAllegato(AllegatoFatturaBean metadataAllegato) {
		this.metadataAllegato = metadataAllegato;
	}

	public List<AllegatoFatturaBean> getAllegati() {
		return this.allegati;
	}

	public void setAllegati(List<AllegatoFatturaBean> allegati) {
		this.allegati = allegati;
	}

	public NotificaECBean getMetadataNotificaEC() {
		return this.metadataNotificaEC;
	}

	public void setMetadataNotificaEC(NotificaECBean metadataNotificaEC) {
		this.metadataNotificaEC = metadataNotificaEC;
	}

	public List<NotificaECBean> getListaNotificaEC() {
		return this.listaNotificaEC;
	}

	public void setListaNotificaEC(List<NotificaECBean> listaNotificaEC) {
		this.listaNotificaEC = listaNotificaEC;
	}

	public NotificaDTBean getMetadataNotificaDT() {
		return this.metadataNotificaDT;
	}

	public void setMetadataNotificaDT(NotificaDTBean metadataNotificaDT) {
		this.metadataNotificaDT = metadataNotificaDT;
	}

	public List<NotificaDTBean> getListaNotificaDT() {
		return this.listaNotificaDT;
	}

	public void setListaNotificaDT(List<NotificaDTBean> listaNotificaDT) {
		this.listaNotificaDT = listaNotificaDT;
	}

	public Button getZip() {
		return this.zip;
	}

	public void setZip(Button zip) {
		this.zip = zip;
	}




	public Text getPosizione() {
		return this.posizione;
	}

	public void setPosizione(Text posizione) {
		this.posizione = posizione;
	}

	public Text getCedentePrestatorePaese() {
		return this.cedentePrestatorePaese;
	}

	public void setCedentePrestatorePaese(Text cedentePrestatorePaese) {
		this.cedentePrestatorePaese = cedentePrestatorePaese;
	}

	public Text getCessionarioCommittentePaese() {
		return this.cessionarioCommittentePaese;
	}

	public void setCessionarioCommittentePaese(
			Text cessionarioCommittentePaese) {
		this.cessionarioCommittentePaese = cessionarioCommittentePaese;
	}

	public Text getTerzoIntermediarioOSoggettoEmittentePaese() {
		return this.terzoIntermediarioOSoggettoEmittentePaese;
	}

	public void setTerzoIntermediarioOSoggettoEmittentePaese(
			Text terzoIntermediarioOSoggettoEmittentePaese) {
		this.terzoIntermediarioOSoggettoEmittentePaese = terzoIntermediarioOSoggettoEmittentePaese;
	}

	public Text getStatoConsegna() {
		return this.statoConsegna;
	}

	public void setStatoConsegna(Text statoConsegna) {
		this.statoConsegna = statoConsegna;
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();



		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;

		this.xml.setHref(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;

		this.pdf.setHref( this.getDTO().getXml() != null ? url : null);


		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_ZIP_CON_ALLEGATI
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;


		this.zip.setHref(this.getDTO().getXml() != null ? url : null);


	}


	public String visualizzaPdf() {
		try {

			// recupero lista diagnostici
			List<Long> idFatture = new ArrayList<Long>();

			idFatture.add(this.getDTO().getId());

			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.sendRedirect(context.getExternalContext()
					.getRequestContextPath()
					+ "/" + FattureExporter.FATTURE_EXPORTER+"?"
					+ FattureExporter.PARAMETRO_IDS+"="
					+ StringUtils.join(idFatture, ",")
					+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
					+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA);

			context.responseComplete();

			// End of the method
		} catch (Exception e) {
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
		}

		return null;
	}

	public String visualizzaXml() {
		try {

			// recupero lista diagnostici
			List<Long> idFatture = new ArrayList<Long>();

			idFatture.add(this.getDTO().getId());

			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.sendRedirect(context.getExternalContext()
					.getRequestContextPath()
					+"/" + FattureExporter.FATTURE_EXPORTER+"?"
					+ FattureExporter.PARAMETRO_IDS+"="
					+ StringUtils.join(idFatture, ",")
					+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
					+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA);

			context.responseComplete();

			// End of the method
		} catch (Exception e) {
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
		}

		return null;
	}

	public Text getProtocollo() {
		return this.protocollo;
	}

	public void setProtocollo(Text protocollo) {
		this.protocollo = protocollo;
	}

	public DateTime getDataConsegna() {
		return this.dataConsegna;
	}

	public void setDataConsegna(DateTime dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public Text getFormatoTrasmissione() {
		return this.formatoTrasmissione;
	}

	public void setFormatoTrasmissione(Text formatoTrasmissione) {
		this.formatoTrasmissione = formatoTrasmissione;
	}

	public OutputGroup getDatiIntestazione() {
		return this.datiIntestazione;
	}

	public void setDatiIntestazione(OutputGroup datiIntestazione) {
		this.datiIntestazione = datiIntestazione;
	}

	public OutputGroup getDatiTrasmissione1() {
		return this.datiTrasmissione1;
	}

	public void setDatiTrasmissione1(OutputGroup datiTrasmissione1) {
		this.datiTrasmissione1 = datiTrasmissione1;
	}

	public OutputGroup getContenutoFattura() {
		return this.contenutoFattura;
	}

	public void setContenutoFattura(OutputGroup contenutoFattura) {
		this.contenutoFattura = contenutoFattura;
	}

	public OutputNumber getImporto() {
		return this.importo;
	}

	public void setImporto(OutputNumber importo) {
		this.importo = importo;
	}

	public OutputNumber getImportoRiepilogo() {
		return this.importoRiepilogo;
	}

	public void setImportoRiepilogo(OutputNumber importoRiepilogo) {
		this.importoRiepilogo = importoRiepilogo;
	}

	public OutputGroup getCausaleFattura() {
		return causaleFattura;
	}

	public void setCausaleFattura(OutputGroup causaleFattura) {
		this.causaleFattura = causaleFattura;
	}

	public DateTime getDataProssimaConsegna() {
		return dataProssimaConsegna;
	}

	public void setDataProssimaConsegna(DateTime dataProssimaConsegna) {
		this.dataProssimaConsegna = dataProssimaConsegna;
	}

	public boolean isVisualizzaColonnaRiconsegnaNotificaEC (){
		boolean isAdmin = Utils.getLoginBean().isAdmin();
		if(isAdmin){
			if(this.getListaNotificaEC() != null &&
					this.getListaNotificaEC().size() > 0) {
				for (NotificaECBean notifica : this.getListaNotificaEC()) {
					NotificaEsitoCommittente dto = notifica.getDTO();
					if(dto.getStatoConsegnaSdi() != null && (dto.getStatoConsegnaSdi().equals(StatoConsegnaType.IN_RICONSEGNA ) || dto.getStatoConsegnaSdi().equals(StatoConsegnaType.ERRORE_CONSEGNA )))
						return true;
				}
			}
		}
		return false;
	}


	public boolean isVisualizzaLinkRiconsegna(){
		if(this.getDTO().getStatoConsegna() != null){
			boolean isAdmin = Utils.getLoginBean().isAdmin();
			StatoConsegnaType statoConsegnaType =  this.getDTO().getStatoConsegna();

			if((statoConsegnaType.equals(StatoConsegnaType.IN_RICONSEGNA) || 
					statoConsegnaType.equals(StatoConsegnaType.ERRORE_CONSEGNA)) && isAdmin)
				return true;
		}

		return false;
	}

	public boolean isShowPCC() {
		return showPCC;
	}

	public void setShowPCC(boolean showPCC) {
		this.showPCC = showPCC;
	}

	public DateTime getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(DateTime dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Text getDataScadenzaAssente() {
		return dataScadenzaAssente;
	}

	public void setDataScadenzaAssente(Text dataScadenzaAssente) {
		this.dataScadenzaAssente = dataScadenzaAssente;
	}
	
}

