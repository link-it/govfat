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

import java.util.ArrayList;
import java.util.Date;
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
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputButton;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputDate;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputGroup;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputIcon;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputNumber;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;

/**
 * FatturaElettronicaBean definisce il bean per la visualizzazione della fattura 
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FatturaElettronicaBean extends BaseBean<FatturaElettronica, Long>{

	// Field Che visualizzo nella maschera di ricerca
	private OutputField<String> cedentePrestatore = null;
	private OutputField<String> dipartimento = null;
	private OutputField<String> annoNumero = null;
	private OutputField<Date> dataRicezione = null;
	private OutputNumber<Double> importo = null; 
	private OutputNumber<Double> importoRiepilogo = null; 
	private OutputButton notificaEC = null;
	private OutputIcon notificaDT = null;
	private OutputButton xml = null;
	private OutputButton pdf = null;
	private OutputButton zip = null;

	// Field da aggiungere nel dettaglio
	private OutputField<String> identificativoSdi = null;
	private OutputField<String> posizione = null;
	private OutputField<String> cedentePrestatoreCF = null;
	private OutputField<String> cedentePrestatorePaese = null;
	private OutputField<String> cessionarioCommittente = null;
	private OutputField<String> cessionarioCommittentePaese = null;
	private OutputField<String> cessionarioCommittenteCF = null;
	private OutputField<String> terzoIntermediarioOSoggettoEmittente = null;
	private OutputField<String> terzoIntermediarioOSoggettoEmittentePaese = null;
	private OutputField<String> terzoIntermediarioOSoggettoEmittenteCF = null;
	private OutputField<String> codiceDestinatario = null;
	private OutputField<String> tipoDocumento = null;
	private OutputField<String> nomeFile = null;
	private OutputField<String> messageId = null;
	private OutputField<String> divisa = null;
	private OutputField<Date> data = null;
	private OutputField<String> numero = null;
	private OutputField<String> anno = null;
	private OutputField<String> causale = null;
	private OutputField<String> statoConsegna = null;
	private OutputField<String> protocollo = null;
	private OutputField<Date> dataConsegna = null;
	private OutputField<String> formatoTrasmissione = null;


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
	private OutputGroup datiIntestazione = new OutputGroup(); 

	// Gruppo Informazioni Dati Genareli
	private OutputGroup datiTrasmissione1 = new OutputGroup();
	//Gruppo informazioni Contenuto Fattura
	private OutputGroup contenutoFattura = new OutputGroup();

	public FatturaElettronicaBean(){
		initFields();
	}

	private double truncate(double value) {
		int integerValue =(int)  value * 100;
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
			this.notificaEC.setIcon("plus-grey.png");
			this.notificaEC.setIconTitle(("fattura.notificaEC.iconTitle.nonPresente"));
			this.notificaEC.setAlt("fattura.notificaEC.iconTitle.nonPresente");
			if(this.dto.getIdDecorrenzaTermini() == null)
				this.notificaEC.setLink("linkToDownload");
			else 
				this.notificaEC.setLink(null);
		}else {
			// In elaborazione accettata
			if(this.dto.getEsito().equals(EsitoType.IN_ELABORAZIONE_ACCETTATO)){
				this.notificaEC.setIcon("accept_circle-yellow.png");
				this.notificaEC.setIconTitle( ("fattura.notificaEC.iconTitle.inElaborazione.accettato"));
				this.notificaEC.setAlt("fattura.notificaEC.iconTitle.inElaborazione.accettato" );
				this.notificaEC.setLink(null);
				//In elaborazione rifiutata
			}else if(this.dto.getEsito().equals(EsitoType.IN_ELABORAZIONE_RIFIUTATO)){
				this.notificaEC.setIcon("no_accept-yellow.png");
				this.notificaEC.setIconTitle( ("fattura.notificaEC.iconTitle.inElaborazione.rifiutato"));
				this.notificaEC.setAlt( "fattura.notificaEC.iconTitle.inElaborazione.rifiutato");
				this.notificaEC.setLink(null);
				//Accettato
			}	
			else if(this.dto.getEsito().equals(EsitoType.INVIATA_ACCETTATO)){
				this.notificaEC.setIcon("accept_circle-green.png");
				this.notificaEC.setIconTitle( ("fattura.notificaEC.iconTitle.inviata.accettato"));
				this.notificaEC.setAlt("fattura.notificaEC.iconTitle.inviata.accettato" );
				this.notificaEC.setLink(null);
			}else if(this.dto.getEsito().equals(EsitoType.INVIATA_RIFIUTATO)){
				this.notificaEC.setIcon("no_accept-green.png");
				this.notificaEC.setIconTitle( ("fattura.notificaEC.iconTitle.inviata.rifiutato"));
				this.notificaEC.setAlt("fattura.notificaEC.iconTitle.inviata.rifiutato" );
				this.notificaEC.setLink(null);
				//Accettato
			}	
			else if(this.dto.getEsito().equals(EsitoType.SCARTATA_ACCETTATO)){
				this.notificaEC.setIcon("accept_circle-red.png");
				this.notificaEC.setIconTitle( ("fattura.notificaEC.iconTitle.scartata.accettato"));
				this.notificaEC.setAlt( "fattura.notificaEC.iconTitle.scartata.accettato");
				this.notificaEC.setLink(null);
			}else {
				//Rifiutato
				this.notificaEC.setIcon("no_accept-red.png");
				this.notificaEC.setIconTitle( ("fattura.notificaEC.iconTitle.scartata.rifiutato"));
				this.notificaEC.setAlt( "fattura.notificaEC.iconTitle.scartata.rifiutato");
				this.notificaEC.setLink(null);
			}
		}

		if(this.dto.getIdDecorrenzaTermini() != null){
			this.notificaDT.setRendered(true); 
			this.notificaDT.setIcon("accept-green.png");
			this.notificaDT.setIconTitle( ("fattura.notificaDT.iconTitle.presente"));
			this.notificaDT.setAlt("fattura.notificaDT.iconTitle.presente" );
		} else {
			this.notificaDT.setRendered(false); 
			this.notificaDT.setIcon("no_accept-red.png");
			this.notificaDT.setIconTitle( ("fattura.notificaDT.iconTitle.nonPresente"));
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

	private void initFields(){

		this.cedentePrestatore = new OutputText();
		this.cedentePrestatore.setLabel( ("fattura.cedentePrestatoreDenominazione"));
		this.cedentePrestatore.setName("cedentePrestatore");

		this.cedentePrestatorePaese = new OutputText();
		this.cedentePrestatorePaese.setLabel( ("fattura.cedentePrestatorePaese"));
		this.cedentePrestatorePaese.setName("cedentePrestatorePaese");

		this.dipartimento = new OutputText();
		this.dipartimento.setLabel( ("fattura.dipartimento"));
		this.dipartimento.setName("dipartimento");

		this.annoNumero = new OutputText();
		this.annoNumero.setLabel( ("fattura.numero"));
		this.annoNumero.setName("annoNumero");

		this.dataRicezione = new OutputDate();
		this.dataRicezione.setLabel( ("fattura.dataRicezione"));
		this.dataRicezione.setName("dataRicezione");
		this.dataRicezione.setPattern("dd/M/yyyy");

		this.importo = new OutputNumber<Double>();
		this.importo.setType("valuta"); 
		this.importo.setLabel( ("fattura.importoTotaleDocumento"));
		this.importo.setName("importo");

		this.importo.setConverterType(OutputNumber.CONVERT_TYPE_CURRENCY);
		this.importo.setCurrencySymbol(OutputNumber.CURRENCY_SYMBOL_EURO);
		this.importo.setTableColumnStyleClass("allinatoDX");

		this.importoRiepilogo = new OutputNumber<Double>();
		this.importoRiepilogo.setType("valuta"); 
		this.importoRiepilogo.setLabel("fattura.importoTotaleRiepilogo");
		this.importoRiepilogo.setName("importoRiepilogo");
		this.importoRiepilogo.setValueStyleClass("diag_error");
		this.importoRiepilogo.setConverterType(OutputNumber.CONVERT_TYPE_CURRENCY);
		this.importoRiepilogo.setCurrencySymbol(OutputNumber.CURRENCY_SYMBOL_EURO);

		this.notificaEC = new OutputButton();
		this.notificaEC.setLabel( ("fattura.notificaEC"));
		this.notificaEC.setName("notificaEC");

		this.notificaDT = new OutputIcon();
		this.notificaDT.setLabel( ("fattura.notificaDT"));
		this.notificaDT.setName("notificaDT");

		this.xml = new OutputButton();
		this.xml.setLabel( ("commons.label.xml"));
		this.xml.setName("xml");
		this.xml.setIcon("xml.png");
		this.xml.setIconTitle( ("commons.label.xml.iconTitle"));
		this.xml.setAlt( ("commons.label.xml.iconTitle"));

		this.pdf = new OutputButton();
		this.pdf.setLabel( ("commons.label.pdf"));
		this.pdf.setName("pdf");
		this.pdf.setIcon("pdf.png");
		this.pdf.setIconTitle( ("commons.label.pdf.iconTitle"));
		this.pdf.setAlt( ("commons.label.pdf.iconTitle"));

		this.zip = new OutputButton();
		this.zip.setLabel( ("commons.button.scaricaTutto"));
		this.zip.setName("zip");
		this.zip.setIcon("zip.png");
		this.zip.setIconTitle( ("commons.button.scaricaTutto.iconTitle"));
		this.zip.setAlt( ("commons.button.scaricaTutto.iconTitle"));

		this.identificativoSdi = new OutputText();
		this.identificativoSdi.setLabel( ("fattura.identificativoSdi"));
		this.identificativoSdi.setName("identificativoSdi");

		this.posizione = new OutputText();
		this.posizione.setLabel( ("fattura.posizione"));
		this.posizione.setName("posizione");

		this.cedentePrestatoreCF = new OutputText();
		this.cedentePrestatoreCF.setLabel( ("fattura.cedentePrestatoreCF"));
		this.cedentePrestatoreCF.setName("cedentePrestatoreCF");

		this.cessionarioCommittente = new OutputText();
		this.cessionarioCommittente.setLabel( ("fattura.cessionarioCommittente"));
		this.cessionarioCommittente.setName("cessionarioCommittente");

		this.cessionarioCommittentePaese = new OutputText();
		this.cessionarioCommittentePaese.setLabel( ("fattura.cessionarioCommittentePaese"));
		this.cessionarioCommittentePaese.setName("cessionarioCommittentePaese");

		this.cessionarioCommittenteCF = new OutputText();
		this.cessionarioCommittenteCF.setLabel( ("fattura.cessionarioCommittenteCF"));
		this.cessionarioCommittenteCF.setName("cessionarioCommittenteCF");

		this.terzoIntermediarioOSoggettoEmittente = new OutputText();
		this.terzoIntermediarioOSoggettoEmittente.setLabel( ("fattura.terzoIntermediarioOSoggettoEmittente"));
		this.terzoIntermediarioOSoggettoEmittente.setName("terzoIntermediarioOSoggettoEmittente");

		this.terzoIntermediarioOSoggettoEmittentePaese = new OutputText();
		this.terzoIntermediarioOSoggettoEmittentePaese.setLabel( ("fattura.terzoIntermediarioOSoggettoEmittentePaese"));
		this.terzoIntermediarioOSoggettoEmittentePaese.setName("terzoIntermediarioOSoggettoEmittentePaese");

		this.terzoIntermediarioOSoggettoEmittenteCF = new OutputText();
		this.terzoIntermediarioOSoggettoEmittenteCF.setLabel( ("fattura.terzoIntermediarioOSoggettoEmittenteCF"));
		this.terzoIntermediarioOSoggettoEmittenteCF.setName("terzoIntermediarioOSoggettoEmittenteCF");


		this.codiceDestinatario = new OutputText();
		this.codiceDestinatario.setLabel( ("fattura.codiceDestinatario"));
		this.codiceDestinatario.setName("codiceDestinatario");

		this.tipoDocumento = new OutputText();
		this.tipoDocumento.setLabel( ("fattura.tipoDocumento"));
		this.tipoDocumento.setName("tipoDocumento");

		this.nomeFile = new OutputText();
		this.nomeFile.setLabel( ("fattura.nomeFile"));
		this.nomeFile.setName("nomeFile");

		this.messageId = new OutputText();
		this.messageId.setLabel( ("fattura.messageId"));
		this.messageId.setName("messageId");

		this.divisa = new OutputText();
		this.divisa.setLabel( ("fattura.divisa"));
		this.divisa.setName("divisa");

		this.data = new OutputDate();
		this.data.setLabel( ("fattura.data"));
		this.data.setName("data");
		this.data.setPattern("dd/M/yyyy");

		this.dataConsegna = new OutputDate();
		this.dataConsegna.setLabel( ("fattura.dataConsegna"));
		this.dataConsegna.setName("dataConsegna");
		this.dataConsegna.setPattern("dd/M/yyyy");


		this.numero = new OutputText();
		this.numero.setLabel( ("fattura.numero"));
		this.numero.setName("numero");

		this.anno = new OutputText();
		this.anno.setLabel( ("fattura.anno"));
		this.anno.setName("anno");

		this.causale = new OutputText();
		this.causale.setLabel( ("fattura.causale"));
		this.causale.setName("causale");
		this.causale.setDefaultValue("fattura.causale.assente");

		this.protocollo = new OutputText();
		this.protocollo.setLabel( ("fattura.protocollo"));
		this.protocollo.setName("protocollo");
		this.protocollo.setDefaultValue("fattura.protocollo.assente");

		this.statoConsegna = new OutputText();
		this.statoConsegna.setLabel( ("fattura.statoConsegna"));
		this.statoConsegna.setName("statoConsegna");

		this.formatoTrasmissione = new OutputText();
		this.formatoTrasmissione.setLabel( ("fattura.formatoTrasmissione"));
		this.formatoTrasmissione.setName("formatoTrasmissione");

		this.datiIntestazione = new OutputGroup();
		this.datiIntestazione.setIdGroup("datiIntestazione");
		this.datiIntestazione.setColumns(6);
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

		this.datiTrasmissione1 = new OutputGroup();
		this.datiTrasmissione1.setIdGroup("datiTrasmissione1");
		this.datiTrasmissione1.setColumns(4);
		this.datiTrasmissione1.setRendered(true);
		this.datiTrasmissione1.setStyleClass("datiTrasmissioneTable"); 
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

		this.contenutoFattura = new OutputGroup();
		this.contenutoFattura.setIdGroup("contenutoFattura");
		this.contenutoFattura.setColumns(4);
		this.contenutoFattura.setRendered(true);
		this.contenutoFattura.setStyleClass("datiTrasmissioneTable"); 
		this.contenutoFattura.setColumnClasses("labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx");

		this.contenutoFattura.addField(this.divisa);
		this.contenutoFattura.addField(this.importo);
		this.contenutoFattura.addField(this.data);
		this.contenutoFattura.addField(this.causale);
		this.contenutoFattura.addField(this.numero);


	}

	public OutputField<String> getCedentePrestatore() {
		return cedentePrestatore;
	}

	public void setCedentePrestatore(OutputField<String> cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public OutputField<String> getAnnoNumero() {
		return annoNumero;
	}

	public void setAnnoNumero(OutputField<String> annoNumero) {
		this.annoNumero = annoNumero;
	}

	public OutputField<Date> getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(OutputField<Date> dataRicezione) {
		this.dataRicezione = dataRicezione;
	}



	public OutputField<String> getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(OutputField<String> dipartimento) {
		this.dipartimento = dipartimento;
	}

	public OutputButton getNotificaEC() {
		return notificaEC;
	}

	public void setNotificaEC(OutputButton notificaEC) {
		this.notificaEC = notificaEC;
	}

	public OutputIcon getNotificaDT() {
		return notificaDT;
	}

	public void setNotificaDT(OutputIcon notificaDT) {
		this.notificaDT = notificaDT;
	}

	public OutputButton getXml() {
		return xml;
	}

	public void setXml(OutputButton xml) {
		this.xml = xml;
	}

	public OutputButton getPdf() {
		return pdf;
	}

	public void setPdf(OutputButton pdf) {
		this.pdf = pdf;
	}

	public OutputField<String> getIdentificativoSdi() {
		return identificativoSdi;
	}

	public void setIdentificativoSdi(OutputField<String> identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}

	public OutputField<String> getCedentePrestatoreCF() {
		return cedentePrestatoreCF;
	}

	public void setCedentePrestatoreCF(OutputField<String> cedentePrestatoreCF) {
		this.cedentePrestatoreCF = cedentePrestatoreCF;
	}

	public OutputField<String> getCessionarioCommittente() {
		return cessionarioCommittente;
	}

	public void setCessionarioCommittente(OutputField<String> cessionarioCommittente) {
		this.cessionarioCommittente = cessionarioCommittente;
	}

	public OutputField<String> getCessionarioCommittenteCF() {
		return cessionarioCommittenteCF;
	}

	public void setCessionarioCommittenteCF(
			OutputField<String> cessionarioCommittenteCF) {
		this.cessionarioCommittenteCF = cessionarioCommittenteCF;
	}

	public OutputField<String> getTerzoIntermediarioOSoggettoEmittente() {
		return terzoIntermediarioOSoggettoEmittente;
	}

	public void setTerzoIntermediarioOSoggettoEmittente(
			OutputField<String> terzoIntermediarioOSoggettoEmittente) {
		this.terzoIntermediarioOSoggettoEmittente = terzoIntermediarioOSoggettoEmittente;
	}

	public OutputField<String> getTerzoIntermediarioOSoggettoEmittenteCF() {
		return terzoIntermediarioOSoggettoEmittenteCF;
	}

	public void setTerzoIntermediarioOSoggettoEmittenteCF(
			OutputField<String> terzoIntermediarioOSoggettoEmittenteCF) {
		this.terzoIntermediarioOSoggettoEmittenteCF = terzoIntermediarioOSoggettoEmittenteCF;
	}

	public OutputField<String> getCodiceDestinatario() {
		return codiceDestinatario;
	}

	public void setCodiceDestinatario(OutputField<String> codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public OutputField<String> getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(OutputField<String> tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public OutputField<String> getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(OutputField<String> nomeFile) {
		this.nomeFile = nomeFile;
	}

	public OutputField<String> getMessageId() {
		return messageId;
	}

	public void setMessageId(OutputField<String> messageId) {
		this.messageId = messageId;
	}

	public OutputField<String> getDivisa() {
		return divisa;
	}

	public void setDivisa(OutputField<String> divisa) {
		this.divisa = divisa;
	}

	public OutputField<Date> getData() {
		return data;
	}

	public void setData(OutputField<Date> data) {
		this.data = data;
	}

	public OutputField<String> getNumero() {
		return numero;
	}

	public void setNumero(OutputField<String> numero) {
		this.numero = numero;
	}

	public OutputField<String> getAnno() {
		return anno;
	}

	public void setAnno(OutputField<String> anno) {
		this.anno = anno;
	}

	public OutputField<String> getCausale() {
		return causale;
	}

	public void setCausale(OutputField<String> causale) {
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

	public OutputButton getZip() {
		return zip;
	}

	public void setZip(OutputButton zip) {
		this.zip = zip;
	}




	public OutputField<String> getPosizione() {
		return posizione;
	}

	public void setPosizione(OutputField<String> posizione) {
		this.posizione = posizione;
	}

	public OutputField<String> getCedentePrestatorePaese() {
		return cedentePrestatorePaese;
	}

	public void setCedentePrestatorePaese(OutputField<String> cedentePrestatorePaese) {
		this.cedentePrestatorePaese = cedentePrestatorePaese;
	}

	public OutputField<String> getCessionarioCommittentePaese() {
		return cessionarioCommittentePaese;
	}

	public void setCessionarioCommittentePaese(
			OutputField<String> cessionarioCommittentePaese) {
		this.cessionarioCommittentePaese = cessionarioCommittentePaese;
	}

	public OutputField<String> getTerzoIntermediarioOSoggettoEmittentePaese() {
		return terzoIntermediarioOSoggettoEmittentePaese;
	}

	public void setTerzoIntermediarioOSoggettoEmittentePaese(
			OutputField<String> terzoIntermediarioOSoggettoEmittentePaese) {
		this.terzoIntermediarioOSoggettoEmittentePaese = terzoIntermediarioOSoggettoEmittentePaese;
	}

	public OutputField<String> getStatoConsegna() {
		return statoConsegna;
	}

	public void setStatoConsegna(OutputField<String> statoConsegna) {
		this.statoConsegna = statoConsegna;
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();



		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;

		((OutputButton) this.xml).setLink(this.getDTO().getXml() != null ?  url : null);

		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_PDF
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;

		((OutputButton) this.pdf).setLink( this.getDTO().getXml() != null ? url : null);


		url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ SingleFileExporter.FORMATO_ZIP_CON_ALLEGATI
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_FATTURA;


		((OutputButton) this.zip).setLink(this.getDTO().getXml() != null ? url : null);


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
					+ "/fattureexporter?"+ FattureExporter.PARAMETRO_IDS+"="
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
					+ "/fattureexporter?"+ FattureExporter.PARAMETRO_IDS+"="
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

	public OutputField<String> getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(OutputField<String> protocollo) {
		this.protocollo = protocollo;
	}

	public OutputField<Date> getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(OutputField<Date> dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public OutputField<String> getFormatoTrasmissione() {
		return formatoTrasmissione;
	}

	public void setFormatoTrasmissione(OutputField<String> formatoTrasmissione) {
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

	public OutputNumber<Double> getImporto() {
		return importo;
	}

	public void setImporto(OutputNumber<Double> importo) {
		this.importo = importo;
	}

	public OutputNumber<Double> getImportoRiepilogo() {
		return importoRiepilogo;
	}

	public void setImportoRiepilogo(OutputNumber<Double> importoRiepilogo) {
		this.importoRiepilogo = importoRiepilogo;
	}



}

