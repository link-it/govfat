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
package org.govmix.proxy.fatturapa.orm.model;

import org.govmix.proxy.fatturapa.orm.LottoFatture;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model LottoFatture 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class LottoFattureModel extends AbstractModel<LottoFatture> {

	public LottoFattureModel(){
	
		super();
	
		this.FORMATO_TRASMISSIONE = new Field("formatoTrasmissione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Long.class,"LottoFatture",LottoFatture.class);
		this.NOME_FILE = new Field("nomeFile",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.FORMATO_ARCHIVIO_INVIO_FATTURA = new Field("formatoArchivioInvioFattura",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.MESSAGE_ID = new Field("messageId",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_DENOMINAZIONE = new Field("cedentePrestatoreDenominazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_NOME = new Field("cedentePrestatoreNome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_COGNOME = new Field("cedentePrestatoreCognome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_CODICE = new Field("cedentePrestatoreCodice",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_PAESE = new Field("cedentePrestatorePaese",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_CODICE_FISCALE = new Field("cedentePrestatoreCodiceFiscale",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_DENOMINAZIONE = new Field("cessionarioCommittenteDenominazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_NOME = new Field("cessionarioCommittenteNome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_COGNOME = new Field("cessionarioCommittenteCognome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_CODICE = new Field("cessionarioCommittenteCodice",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_PAESE = new Field("cessionarioCommittentePaese",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_CODICE_FISCALE = new Field("cessionarioCommittenteCodiceFiscale",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE = new Field("terzoIntermediarioOSoggettoEmittenteDenominazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME = new Field("terzoIntermediarioOSoggettoEmittenteNome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME = new Field("terzoIntermediarioOSoggettoEmittenteCognome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE = new Field("terzoIntermediarioOSoggettoEmittenteCodice",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE = new Field("terzoIntermediarioOSoggettoEmittentePaese",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE = new Field("terzoIntermediarioOSoggettoEmittenteCodiceFiscale",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CODICE_DESTINATARIO = new Field("codiceDestinatario",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.XML = new Field("xml",byte[].class,"LottoFatture",LottoFatture.class);
		this.FATTURAZIONE_ATTIVA = new Field("fatturazioneAttiva",boolean.class,"LottoFatture",LottoFatture.class);
		this.STATO_ELABORAZIONE_IN_USCITA = new Field("statoElaborazioneInUscita",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TIPI_COMUNICAZIONE = new Field("tipiComunicazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_ULTIMA_ELABORAZIONE = new Field("dataUltimaElaborazione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.DETTAGLIO_ELABORAZIONE = new Field("dettaglioElaborazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_PROSSIMA_ELABORAZIONE = new Field("dataProssimaElaborazione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.TENTATIVI_CONSEGNA = new Field("tentativiConsegna",java.lang.Integer.class,"LottoFatture",LottoFatture.class);
		this.DATA_RICEZIONE = new Field("dataRicezione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.STATO_INSERIMENTO = new Field("statoInserimento",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.STATO_CONSEGNA = new Field("statoConsegna",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_CONSEGNA = new Field("dataConsegna",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.DETTAGLIO_CONSEGNA = new Field("dettaglioConsegna",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.STATO_PROTOCOLLAZIONE = new Field("statoProtocollazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DOMINIO = new Field("dominio",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.SOTTODOMINIO = new Field("sottodominio",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.PAGO_PA = new Field("pagoPA",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_PROTOCOLLAZIONE = new Field("dataProtocollazione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.PROTOCOLLO = new Field("protocollo",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.ID_SIP = new org.govmix.proxy.fatturapa.orm.model.IdSipModel(new Field("idSIP",org.govmix.proxy.fatturapa.orm.IdSip.class,"LottoFatture",LottoFatture.class));
		this.ID_EGOV = new Field("id-egov",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.DipartimentoModel(new Field("Dipartimento",org.govmix.proxy.fatturapa.orm.Dipartimento.class,"LottoFatture",LottoFatture.class));
	
	}
	
	public LottoFattureModel(IField father){
	
		super(father);
	
		this.FORMATO_TRASMISSIONE = new ComplexField(father,"formatoTrasmissione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Long.class,"LottoFatture",LottoFatture.class);
		this.NOME_FILE = new ComplexField(father,"nomeFile",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.FORMATO_ARCHIVIO_INVIO_FATTURA = new ComplexField(father,"formatoArchivioInvioFattura",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.MESSAGE_ID = new ComplexField(father,"messageId",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_DENOMINAZIONE = new ComplexField(father,"cedentePrestatoreDenominazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_NOME = new ComplexField(father,"cedentePrestatoreNome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_COGNOME = new ComplexField(father,"cedentePrestatoreCognome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_CODICE = new ComplexField(father,"cedentePrestatoreCodice",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_PAESE = new ComplexField(father,"cedentePrestatorePaese",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CEDENTE_PRESTATORE_CODICE_FISCALE = new ComplexField(father,"cedentePrestatoreCodiceFiscale",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_DENOMINAZIONE = new ComplexField(father,"cessionarioCommittenteDenominazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_NOME = new ComplexField(father,"cessionarioCommittenteNome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_COGNOME = new ComplexField(father,"cessionarioCommittenteCognome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_CODICE = new ComplexField(father,"cessionarioCommittenteCodice",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_PAESE = new ComplexField(father,"cessionarioCommittentePaese",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CESSIONARIO_COMMITTENTE_CODICE_FISCALE = new ComplexField(father,"cessionarioCommittenteCodiceFiscale",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE = new ComplexField(father,"terzoIntermediarioOSoggettoEmittenteDenominazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME = new ComplexField(father,"terzoIntermediarioOSoggettoEmittenteNome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME = new ComplexField(father,"terzoIntermediarioOSoggettoEmittenteCognome",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE = new ComplexField(father,"terzoIntermediarioOSoggettoEmittenteCodice",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE = new ComplexField(father,"terzoIntermediarioOSoggettoEmittentePaese",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE = new ComplexField(father,"terzoIntermediarioOSoggettoEmittenteCodiceFiscale",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.CODICE_DESTINATARIO = new ComplexField(father,"codiceDestinatario",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.XML = new ComplexField(father,"xml",byte[].class,"LottoFatture",LottoFatture.class);
		this.FATTURAZIONE_ATTIVA = new ComplexField(father,"fatturazioneAttiva",boolean.class,"LottoFatture",LottoFatture.class);
		this.STATO_ELABORAZIONE_IN_USCITA = new ComplexField(father,"statoElaborazioneInUscita",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.TIPI_COMUNICAZIONE = new ComplexField(father,"tipiComunicazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_ULTIMA_ELABORAZIONE = new ComplexField(father,"dataUltimaElaborazione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.DETTAGLIO_ELABORAZIONE = new ComplexField(father,"dettaglioElaborazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_PROSSIMA_ELABORAZIONE = new ComplexField(father,"dataProssimaElaborazione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.TENTATIVI_CONSEGNA = new ComplexField(father,"tentativiConsegna",java.lang.Integer.class,"LottoFatture",LottoFatture.class);
		this.DATA_RICEZIONE = new ComplexField(father,"dataRicezione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.STATO_INSERIMENTO = new ComplexField(father,"statoInserimento",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.STATO_CONSEGNA = new ComplexField(father,"statoConsegna",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_CONSEGNA = new ComplexField(father,"dataConsegna",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.DETTAGLIO_CONSEGNA = new ComplexField(father,"dettaglioConsegna",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.STATO_PROTOCOLLAZIONE = new ComplexField(father,"statoProtocollazione",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DOMINIO = new ComplexField(father,"dominio",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.SOTTODOMINIO = new ComplexField(father,"sottodominio",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.PAGO_PA = new ComplexField(father,"pagoPA",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DATA_PROTOCOLLAZIONE = new ComplexField(father,"dataProtocollazione",java.util.Date.class,"LottoFatture",LottoFatture.class);
		this.PROTOCOLLO = new ComplexField(father,"protocollo",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.ID_SIP = new org.govmix.proxy.fatturapa.orm.model.IdSipModel(new ComplexField(father,"idSIP",org.govmix.proxy.fatturapa.orm.IdSip.class,"LottoFatture",LottoFatture.class));
		this.ID_EGOV = new ComplexField(father,"id-egov",java.lang.String.class,"LottoFatture",LottoFatture.class);
		this.DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.DipartimentoModel(new ComplexField(father,"Dipartimento",org.govmix.proxy.fatturapa.orm.Dipartimento.class,"LottoFatture",LottoFatture.class));
	
	}
	
	

	public IField FORMATO_TRASMISSIONE = null;
	 
	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField NOME_FILE = null;
	 
	public IField FORMATO_ARCHIVIO_INVIO_FATTURA = null;
	 
	public IField MESSAGE_ID = null;
	 
	public IField CEDENTE_PRESTATORE_DENOMINAZIONE = null;
	 
	public IField CEDENTE_PRESTATORE_NOME = null;
	 
	public IField CEDENTE_PRESTATORE_COGNOME = null;
	 
	public IField CEDENTE_PRESTATORE_CODICE = null;
	 
	public IField CEDENTE_PRESTATORE_PAESE = null;
	 
	public IField CEDENTE_PRESTATORE_CODICE_FISCALE = null;
	 
	public IField CESSIONARIO_COMMITTENTE_DENOMINAZIONE = null;
	 
	public IField CESSIONARIO_COMMITTENTE_NOME = null;
	 
	public IField CESSIONARIO_COMMITTENTE_COGNOME = null;
	 
	public IField CESSIONARIO_COMMITTENTE_CODICE = null;
	 
	public IField CESSIONARIO_COMMITTENTE_PAESE = null;
	 
	public IField CESSIONARIO_COMMITTENTE_CODICE_FISCALE = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE = null;
	 
	public IField CODICE_DESTINATARIO = null;
	 
	public IField XML = null;
	 
	public IField FATTURAZIONE_ATTIVA = null;
	 
	public IField STATO_ELABORAZIONE_IN_USCITA = null;
	 
	public IField TIPI_COMUNICAZIONE = null;
	 
	public IField DATA_ULTIMA_ELABORAZIONE = null;
	 
	public IField DETTAGLIO_ELABORAZIONE = null;
	 
	public IField DATA_PROSSIMA_ELABORAZIONE = null;
	 
	public IField TENTATIVI_CONSEGNA = null;
	 
	public IField DATA_RICEZIONE = null;
	 
	public IField STATO_INSERIMENTO = null;
	 
	public IField STATO_CONSEGNA = null;
	 
	public IField DATA_CONSEGNA = null;
	 
	public IField DETTAGLIO_CONSEGNA = null;
	 
	public IField STATO_PROTOCOLLAZIONE = null;
	 
	public IField DOMINIO = null;
	 
	public IField SOTTODOMINIO = null;
	 
	public IField PAGO_PA = null;
	 
	public IField DATA_PROTOCOLLAZIONE = null;
	 
	public IField PROTOCOLLO = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdSipModel ID_SIP = null;
	 
	public IField ID_EGOV = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.DipartimentoModel DIPARTIMENTO = null;
	 

	@Override
	public Class<LottoFatture> getModeledClass(){
		return LottoFatture.class;
	}
	
	@Override
	public String toString(){
		if(this.getModeledClass()!=null){
			return this.getModeledClass().getName();
		}else{
			return "N.D.";
		}
	}

}