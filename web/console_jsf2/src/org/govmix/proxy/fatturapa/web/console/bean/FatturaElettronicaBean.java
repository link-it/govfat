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

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.constants.EsitoType;
import org.govmix.proxy.fatturapa.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf2.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.Image;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.OutputNumber;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.view.IViewBean;

/**
 * FatturaElettronicaBean definisce il bean per la visualizzazione della fattura 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FatturaElettronicaBean extends BaseBean<FatturaElettronica, Long> implements IViewBean<FatturaElettronica, Long>{
	

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

	public FatturaElettronicaBean(){
		try{
			init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.cedentePrestatore = this.getFactory().getOutputFieldFactory().createText("cedentePrestatore","fattura.cedentePrestatoreDenominazione");
		this.cedentePrestatorePaese = this.getFactory().getOutputFieldFactory().createText("cedentePrestatorePaese","fattura.cedentePrestatorePaese");
		this.dipartimento = this.getFactory().getOutputFieldFactory().createText("dipartimento","fattura.dipartimento");
		this.annoNumero = this.getFactory().getOutputFieldFactory().createText("annoNumero","fattura.annoNumero");
		this.dataRicezione = this.getFactory().getOutputFieldFactory().createDateTime("dataRicezione","fattura.dataRicezione","dd/M/yyyy");

		this.importo = this.getFactory().getOutputFieldFactory().createNumber("importo","fattura.importoTotaleDocumento");
		this.importo.setConverterType(Costanti.CONVERT_TYPE_CURRENCY);
		this.importo.setCurrencySymbol(Costanti.CURRENCY_SYMBOL_EURO);
		this.importo.setTableColumnStyleClass("allinatoDX");

		this.importoRiepilogo = this.getFactory().getOutputFieldFactory().createNumber("importoRiepilogo","fattura.importoTotaleRiepilogo");
		this.importoRiepilogo.setValueStyleClass("diag_error");
		this.importoRiepilogo.setConverterType(Costanti.CONVERT_TYPE_CURRENCY);
		this.importoRiepilogo.setCurrencySymbol(Costanti.CURRENCY_SYMBOL_EURO);

		this.notificaEC = this.getFactory().getOutputFieldFactory().createButton("notificaEC","fattura.notificaEC");
		this.notificaDT = this.getFactory().getOutputFieldFactory().createImage("notificaDT","fattura.notificaDT");

		this.xml = this.getFactory().getOutputFieldFactory().createButton("xml","commons.label.xml",null,"/images/fatturapa/icons/xml.png","commons.label.xml.iconTitle","commons.label.xml.iconTitle");
		this.pdf = this.getFactory().getOutputFieldFactory().createButton("pdf","commons.label.pdf",null,"/images/fatturapa/icons/pdf.png","commons.label.pdf.iconTitle","commons.label.pdf.iconTitle");
		this.zip = this.getFactory().getOutputFieldFactory().createButton("zip","commons.button.scaricaTutto",null,"/images/fatturapa/icons/zip.png","commons.button.scaricaTutto.iconTitle","commons.button.scaricaTutto.iconTitle");

		this.identificativoSdi = this.getFactory().getOutputFieldFactory().createText("identificativoSdi","fattura.identificativoSdi");
		this.posizione = this.getFactory().getOutputFieldFactory().createText("posizione","fattura.posizione");
		this.cedentePrestatoreCF = this.getFactory().getOutputFieldFactory().createText("cedentePrestatoreCF","fattura.cedentePrestatoreCF");
		this.cessionarioCommittente = this.getFactory().getOutputFieldFactory().createText("cessionarioCommittente","fattura.cessionarioCommittente");
		this.cessionarioCommittentePaese = this.getFactory().getOutputFieldFactory().createText("cessionarioCommittentePaese","fattura.cessionarioCommittentePaese");
		this.cessionarioCommittenteCF = this.getFactory().getOutputFieldFactory().createText("cessionarioCommittenteCF","fattura.cessionarioCommittenteCF");
		this.terzoIntermediarioOSoggettoEmittente = this.getFactory().getOutputFieldFactory().createText("terzoIntermediarioOSoggettoEmittente","fattura.terzoIntermediarioOSoggettoEmittente");
		this.terzoIntermediarioOSoggettoEmittentePaese = this.getFactory().getOutputFieldFactory().createText("terzoIntermediarioOSoggettoEmittentePaese","fattura.terzoIntermediarioOSoggettoEmittentePaese");
		this.terzoIntermediarioOSoggettoEmittenteCF = this.getFactory().getOutputFieldFactory().createText("terzoIntermediarioOSoggettoEmittenteCF","fattura.terzoIntermediarioOSoggettoEmittenteCF");
		this.codiceDestinatario = this.getFactory().getOutputFieldFactory().createText("codiceDestinatario","fattura.codiceDestinatario");
		this.tipoDocumento = this.getFactory().getOutputFieldFactory().createText("tipoDocumento","fattura.tipoDocumento");
		this.nomeFile = this.getFactory().getOutputFieldFactory().createText("nomeFile","fattura.nomeFile");
		this.messageId = this.getFactory().getOutputFieldFactory().createText("messageId","fattura.messageId");
		this.divisa = this.getFactory().getOutputFieldFactory().createText("divisa","fattura.divisa");
		this.data = this.getFactory().getOutputFieldFactory().createDateTime("data","fattura.data","dd/M/yyyy");
		this.dataConsegna = this.getFactory().getOutputFieldFactory().createDateTime("dataConsegna","fattura.dataConsegna","dd/M/yyyy");
		
		this.numero = this.getFactory().getOutputFieldFactory().createText("numero","fattura.numero");
		this.anno = this.getFactory().getOutputFieldFactory().createText("anno","fattura.anno");
		this.causale = this.getFactory().getOutputFieldFactory().createText("causale","fattura.causale","fattura.causale.assente");
		this.protocollo = this.getFactory().getOutputFieldFactory().createText("protocollo","fattura.protocollo","fattura.protocollo.assente");
		this.statoConsegna = this.getFactory().getOutputFieldFactory().createText("statoConsegna","fattura.statoConsegna");
		this.formatoTrasmissione = this.getFactory().getOutputFieldFactory().createText("formatoTrasmissione","fattura.formatoTrasmissione");
		
		
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
		
		this.datiIntestazione = this.getFactory().getOutputFieldFactory().createOutputGroup("datiIntestazione",6);
		this.datiIntestazione.setRendered(true);
		this.datiIntestazione.setStyleClass("outputGroupTable"); 
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

		this.datiTrasmissione1 = this.getFactory().getOutputFieldFactory().createOutputGroup("datiTrasmissione1",4);
		this.datiTrasmissione1.setRendered(true);
		this.datiTrasmissione1.setStyleClass("outputGroupTable"); 
		this.datiTrasmissione1.setColumnClasses("labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx");
		this.datiTrasmissione1.addField(this.identificativoSdi);
		this.datiTrasmissione1.addField(this.posizione);
		this.datiTrasmissione1.addField(this.nomeFile);
		this.datiTrasmissione1.addField(this.formatoTrasmissione);
		this.datiTrasmissione1.addField(this.tipoDocumento);
		this.datiTrasmissione1.addField(this.messageId);
		this.datiTrasmissione1.addField(this.dipartimento);
		this.datiTrasmissione1.addField(this.codiceDestinatario);
		this.datiTrasmissione1.addField(this.dataRicezione);
		this.datiTrasmissione1.addField(this.dataConsegna);
		this.datiTrasmissione1.addField(this.statoConsegna);
		this.datiTrasmissione1.addField(this.protocollo);

		this.contenutoFattura = this.getFactory().getOutputFieldFactory().createOutputGroup("contenutoFattura",4);
		this.contenutoFattura.setRendered(true);
		this.contenutoFattura.setStyleClass("outputGroupTable"); 
		this.contenutoFattura.setColumnClasses("labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx");

		this.contenutoFattura.addField(this.divisa);
		this.contenutoFattura.addField(this.importo);
		this.contenutoFattura.addField(this.data);
		this.contenutoFattura.addField(this.causale);
		this.contenutoFattura.addField(this.numero);


	}
	

	private double truncate(double value) {
		long integerValue = Math.round(value * 100);
		return integerValue / 100.0;
	}
	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
//		if(this.dto != null){
//			IdFattura idFattura = new IdFattura();
//			idFattura.setIdentificativoSdi(this.getDTO().getIdentificativoSdi());
//			idFattura.setPosizione(this.getDTO().getPosizione()); 
//			
//			return idFattura;
//		}
//		
//		
//		return  null;
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
			this.contenutoFattura.addField(this.causale);
		}else {
			this.contenutoFattura.getFields().clear();
			this.contenutoFattura.addField(this.divisa);
			this.contenutoFattura.addField(this.importo);
			this.contenutoFattura.addField(this.data);
			this.contenutoFattura.addField(this.causale);
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

		prepareUrls();

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
		String valueTD = this.getDTO().getTipoDocumento().getValue();
		this.tipoDocumento.setValue("fattura.tipoDocumento."+valueTD);
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
		if(this.getDTO().getIdDecorrenzaTermini() != null) {
			this.statoConsegna.setValue("fattura.statoConsegna.scaduta");
		} else {
			EsitoType esito = this.getDTO().getEsito();
			if(esito!= null) {
				String statoString;

				switch(this.getDTO().getEsito()) {
				case INVIATA_ACCETTATO: statoString = "fattura.statoConsegna.inviata.accettata";
				break;
				case INVIATA_RIFIUTATO: statoString = "fattura.statoConsegna.inviata.rifiutata";
				break;
				case IN_ELABORAZIONE_ACCETTATO: statoString = "fattura.statoConsegna.inElaborazione.accettata";
				break;
				case IN_ELABORAZIONE_RIFIUTATO:  statoString = "fattura.statoConsegna.inElaborazione.rifiutata";
				break;
				case SCARTATA_ACCETTATO:  statoString = "fattura.statoConsegna.scartata.accettata";
				break;
				case SCARTATA_RIFIUTATO: statoString = "fattura.statoConsegna.scartata.rifiutata";
				break;
				default:  statoString = "fattura.statoConsegna.inviata.accettata";
				break;
				}

				this.statoConsegna.setValue(statoString);

			} else {
				if(this.getDTO().getProtocollo() != null) {
					this.statoConsegna.setValue("fattura.statoConsegna.protocollata");
				} else {
					StatoConsegnaType statoConsegnaE = this.getDTO().getStatoConsegna(); 
					if(statoConsegnaE != null){
						if(statoConsegnaE.equals(StatoConsegnaType.CONSEGNATA)) {
							this.statoConsegna.setValue("fattura.statoConsegna.consegnata");
						} else if(statoConsegnaE.equals(StatoConsegnaType.ERRORE_CONSEGNA)) {
							this.statoConsegna.setValue("fattura.statoConsegna.erroreConsegna");
						} else {
							this.statoConsegna.setValue("fattura.statoConsegna.nonConsegnata");
						}
					}
				}					
			}
		}

	}

	public Text getCedentePrestatore() {
		return cedentePrestatore;
	}

	public void setCedentePrestatore(Text cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public Text getAnnoNumero() {
		return annoNumero;
	}

	public void setAnnoNumero(Text annoNumero) {
		this.annoNumero = annoNumero;
	}

	public DateTime getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(DateTime dataRicezione) {
		this.dataRicezione = dataRicezione;
	}



	public Text getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(Text dipartimento) {
		this.dipartimento = dipartimento;
	}

	public Button getNotificaEC() {
		return notificaEC;
	}

	public void setNotificaEC(Button notificaEC) {
		this.notificaEC = notificaEC;
	}

	public Image getNotificaDT() {
		return notificaDT;
	}

	public void setNotificaDT(Image notificaDT) {
		this.notificaDT = notificaDT;
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

	public Text getIdentificativoSdi() {
		return identificativoSdi;
	}

	public void setIdentificativoSdi(Text identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}

	public Text getCedentePrestatoreCF() {
		return cedentePrestatoreCF;
	}

	public void setCedentePrestatoreCF(Text cedentePrestatoreCF) {
		this.cedentePrestatoreCF = cedentePrestatoreCF;
	}

	public Text getCessionarioCommittente() {
		return cessionarioCommittente;
	}

	public void setCessionarioCommittente(Text cessionarioCommittente) {
		this.cessionarioCommittente = cessionarioCommittente;
	}

	public Text getCessionarioCommittenteCF() {
		return cessionarioCommittenteCF;
	}

	public void setCessionarioCommittenteCF(
			Text cessionarioCommittenteCF) {
		this.cessionarioCommittenteCF = cessionarioCommittenteCF;
	}

	public Text getTerzoIntermediarioOSoggettoEmittente() {
		return terzoIntermediarioOSoggettoEmittente;
	}

	public void setTerzoIntermediarioOSoggettoEmittente(
			Text terzoIntermediarioOSoggettoEmittente) {
		this.terzoIntermediarioOSoggettoEmittente = terzoIntermediarioOSoggettoEmittente;
	}

	public Text getTerzoIntermediarioOSoggettoEmittenteCF() {
		return terzoIntermediarioOSoggettoEmittenteCF;
	}

	public void setTerzoIntermediarioOSoggettoEmittenteCF(
			Text terzoIntermediarioOSoggettoEmittenteCF) {
		this.terzoIntermediarioOSoggettoEmittenteCF = terzoIntermediarioOSoggettoEmittenteCF;
	}

	public Text getCodiceDestinatario() {
		return codiceDestinatario;
	}

	public void setCodiceDestinatario(Text codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public Text getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Text tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Text getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(Text nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Text getMessageId() {
		return messageId;
	}

	public void setMessageId(Text messageId) {
		this.messageId = messageId;
	}

	public Text getDivisa() {
		return divisa;
	}

	public void setDivisa(Text divisa) {
		this.divisa = divisa;
	}

	public DateTime getData() {
		return data;
	}

	public void setData(DateTime data) {
		this.data = data;
	}

	public Text getNumero() {
		return numero;
	}

	public void setNumero(Text numero) {
		this.numero = numero;
	}

	public Text getAnno() {
		return anno;
	}

	public void setAnno(Text anno) {
		this.anno = anno;
	}

	public Text getCausale() {
		return causale;
	}

	public void setCausale(Text causale) {
		this.causale = causale;
	}

	public AllegatoFatturaBean getMetadataAllegato() {
		return metadataAllegato;
	}

	public void setMetadataAllegato(AllegatoFatturaBean metadataAllegato) {
		this.metadataAllegato = metadataAllegato;
	}

	public List<AllegatoFatturaBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatoFatturaBean> allegati) {
		this.allegati = allegati;
	}

	public NotificaECBean getMetadataNotificaEC() {
		return metadataNotificaEC;
	}

	public void setMetadataNotificaEC(NotificaECBean metadataNotificaEC) {
		this.metadataNotificaEC = metadataNotificaEC;
	}

	public List<NotificaECBean> getListaNotificaEC() {
		return listaNotificaEC;
	}

	public void setListaNotificaEC(List<NotificaECBean> listaNotificaEC) {
		this.listaNotificaEC = listaNotificaEC;
	}

	public NotificaDTBean getMetadataNotificaDT() {
		return metadataNotificaDT;
	}

	public void setMetadataNotificaDT(NotificaDTBean metadataNotificaDT) {
		this.metadataNotificaDT = metadataNotificaDT;
	}

	public List<NotificaDTBean> getListaNotificaDT() {
		return listaNotificaDT;
	}

	public void setListaNotificaDT(List<NotificaDTBean> listaNotificaDT) {
		this.listaNotificaDT = listaNotificaDT;
	}

	public Button getZip() {
		return zip;
	}

	public void setZip(Button zip) {
		this.zip = zip;
	}




	public Text getPosizione() {
		return posizione;
	}

	public void setPosizione(Text posizione) {
		this.posizione = posizione;
	}

	public Text getCedentePrestatorePaese() {
		return cedentePrestatorePaese;
	}

	public void setCedentePrestatorePaese(Text cedentePrestatorePaese) {
		this.cedentePrestatorePaese = cedentePrestatorePaese;
	}

	public Text getCessionarioCommittentePaese() {
		return cessionarioCommittentePaese;
	}

	public void setCessionarioCommittentePaese(
			Text cessionarioCommittentePaese) {
		this.cessionarioCommittentePaese = cessionarioCommittentePaese;
	}

	public Text getTerzoIntermediarioOSoggettoEmittentePaese() {
		return terzoIntermediarioOSoggettoEmittentePaese;
	}

	public void setTerzoIntermediarioOSoggettoEmittentePaese(
			Text terzoIntermediarioOSoggettoEmittentePaese) {
		this.terzoIntermediarioOSoggettoEmittentePaese = terzoIntermediarioOSoggettoEmittentePaese;
	}

	public Text getStatoConsegna() {
		return statoConsegna;
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

		((Button) this.xml).setHref(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;

		((Button) this.pdf).setHref( this.getDTO().getXml() != null ? url : null);


		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_ZIP_CON_ALLEGATI
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;


		((Button) this.zip).setHref(this.getDTO().getXml() != null ? url : null);


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
							Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
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
							Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
		}

		return null;
	}

	public Text getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(Text protocollo) {
		this.protocollo = protocollo;
	}

	public DateTime getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(DateTime dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public Text getFormatoTrasmissione() {
		return formatoTrasmissione;
	}

	public void setFormatoTrasmissione(Text formatoTrasmissione) {
		this.formatoTrasmissione = formatoTrasmissione;
	}

	public OutputGroup getDatiIntestazione() {
		return datiIntestazione;
	}

	public void setDatiIntestazione(OutputGroup datiIntestazione) {
		this.datiIntestazione = datiIntestazione;
	}

	public OutputGroup getDatiTrasmissione1() {
		return datiTrasmissione1;
	}

	public void setDatiTrasmissione1(OutputGroup datiTrasmissione1) {
		this.datiTrasmissione1 = datiTrasmissione1;
	}

	public OutputGroup getContenutoFattura() {
		return contenutoFattura;
	}

	public void setContenutoFattura(OutputGroup contenutoFattura) {
		this.contenutoFattura = contenutoFattura;
	}

	public OutputNumber getImporto() {
		return importo;
	}

	public void setImporto(OutputNumber importo) {
		this.importo = importo;
	}

	public OutputNumber getImportoRiepilogo() {
		return importoRiepilogo;
	}

	public void setImportoRiepilogo(OutputNumber importoRiepilogo) {
		this.importoRiepilogo = importoRiepilogo;
	}



}

