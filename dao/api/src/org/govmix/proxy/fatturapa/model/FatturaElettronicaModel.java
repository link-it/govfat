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
package org.govmix.proxy.fatturapa.model;

import org.govmix.proxy.fatturapa.FatturaElettronica;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model FatturaElettronica 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class FatturaElettronicaModel extends AbstractModel<FatturaElettronica> {

	public FatturaElettronicaModel(){
	
		super();
	
		this.FORMATO_TRASMISSIONE = new Field("formatoTrasmissione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Integer.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA_RICEZIONE = new Field("dataRicezione",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.NOME_FILE = new Field("nomeFile",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.MESSAGE_ID = new Field("messageId",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CEDENTE_PRESTATORE_DENOMINAZIONE = new Field("cedentePrestatoreDenominazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CEDENTE_PRESTATORE_PAESE = new Field("cedentePrestatorePaese",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CEDENTE_PRESTATORE_CODICE_FISCALE = new Field("cedentePrestatoreCodiceFiscale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CESSIONARIO_COMMITTENTE_DENOMINAZIONE = new Field("cessionarioCommittenteDenominazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CESSIONARIO_COMMITTENTE_PAESE = new Field("cessionarioCommittentePaese",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CESSIONARIO_COMMITTENTE_CODICE_FISCALE = new Field("cessionarioCommittenteCodiceFiscale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE = new Field("terzoIntermediarioOSoggettoEmittenteDenominazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE = new Field("terzoIntermediarioOSoggettoEmittentePaese",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE = new Field("terzoIntermediarioOSoggettoEmittenteCodiceFiscale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.POSIZIONE = new Field("posizione",java.lang.Integer.class,"FatturaElettronica",FatturaElettronica.class);
		this.CODICE_DESTINATARIO = new Field("codiceDestinatario",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TIPO_DOCUMENTO = new Field("tipoDocumento",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DIVISA = new Field("divisa",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA = new Field("data",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.ANNO = new Field("anno",java.lang.Integer.class,"FatturaElettronica",FatturaElettronica.class);
		this.NUMERO = new Field("numero",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.ESITO = new Field("esito",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.IMPORTO_TOTALE_DOCUMENTO = new Field("importoTotaleDocumento",java.lang.Double.class,"FatturaElettronica",FatturaElettronica.class);
		this.IMPORTO_TOTALE_RIEPILOGO = new Field("importoTotaleRiepilogo",java.lang.Double.class,"FatturaElettronica",FatturaElettronica.class);
		this.CAUSALE = new Field("causale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.STATO_CONSEGNA = new Field("statoConsegna",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA_CONSEGNA = new Field("dataConsegna",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.DETTAGLIO_CONSEGNA = new Field("dettaglioConsegna",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.STATO_PROTOCOLLAZIONE = new Field("statoProtocollazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA_PROTOCOLLAZIONE = new Field("dataProtocollazione",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.PROTOCOLLO = new Field("protocollo",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.XML = new Field("xml",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.ID_DECORRENZA_TERMINI = new org.govmix.proxy.fatturapa.model.IdNotificaDecorrenzaTerminiModel(new Field("idDecorrenzaTermini",org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini.class,"FatturaElettronica",FatturaElettronica.class));
	
	}
	
	public FatturaElettronicaModel(IField father){
	
		super(father);
	
		this.FORMATO_TRASMISSIONE = new ComplexField(father,"formatoTrasmissione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Integer.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA_RICEZIONE = new ComplexField(father,"dataRicezione",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.NOME_FILE = new ComplexField(father,"nomeFile",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.MESSAGE_ID = new ComplexField(father,"messageId",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CEDENTE_PRESTATORE_DENOMINAZIONE = new ComplexField(father,"cedentePrestatoreDenominazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CEDENTE_PRESTATORE_PAESE = new ComplexField(father,"cedentePrestatorePaese",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CEDENTE_PRESTATORE_CODICE_FISCALE = new ComplexField(father,"cedentePrestatoreCodiceFiscale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CESSIONARIO_COMMITTENTE_DENOMINAZIONE = new ComplexField(father,"cessionarioCommittenteDenominazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CESSIONARIO_COMMITTENTE_PAESE = new ComplexField(father,"cessionarioCommittentePaese",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.CESSIONARIO_COMMITTENTE_CODICE_FISCALE = new ComplexField(father,"cessionarioCommittenteCodiceFiscale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE = new ComplexField(father,"terzoIntermediarioOSoggettoEmittenteDenominazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE = new ComplexField(father,"terzoIntermediarioOSoggettoEmittentePaese",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE = new ComplexField(father,"terzoIntermediarioOSoggettoEmittenteCodiceFiscale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.POSIZIONE = new ComplexField(father,"posizione",java.lang.Integer.class,"FatturaElettronica",FatturaElettronica.class);
		this.CODICE_DESTINATARIO = new ComplexField(father,"codiceDestinatario",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.TIPO_DOCUMENTO = new ComplexField(father,"tipoDocumento",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DIVISA = new ComplexField(father,"divisa",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA = new ComplexField(father,"data",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.ANNO = new ComplexField(father,"anno",java.lang.Integer.class,"FatturaElettronica",FatturaElettronica.class);
		this.NUMERO = new ComplexField(father,"numero",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.ESITO = new ComplexField(father,"esito",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.IMPORTO_TOTALE_DOCUMENTO = new ComplexField(father,"importoTotaleDocumento",java.lang.Double.class,"FatturaElettronica",FatturaElettronica.class);
		this.IMPORTO_TOTALE_RIEPILOGO = new ComplexField(father,"importoTotaleRiepilogo",java.lang.Double.class,"FatturaElettronica",FatturaElettronica.class);
		this.CAUSALE = new ComplexField(father,"causale",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.STATO_CONSEGNA = new ComplexField(father,"statoConsegna",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA_CONSEGNA = new ComplexField(father,"dataConsegna",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.DETTAGLIO_CONSEGNA = new ComplexField(father,"dettaglioConsegna",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.STATO_PROTOCOLLAZIONE = new ComplexField(father,"statoProtocollazione",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.DATA_PROTOCOLLAZIONE = new ComplexField(father,"dataProtocollazione",java.util.Date.class,"FatturaElettronica",FatturaElettronica.class);
		this.PROTOCOLLO = new ComplexField(father,"protocollo",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.XML = new ComplexField(father,"xml",java.lang.String.class,"FatturaElettronica",FatturaElettronica.class);
		this.ID_DECORRENZA_TERMINI = new org.govmix.proxy.fatturapa.model.IdNotificaDecorrenzaTerminiModel(new ComplexField(father,"idDecorrenzaTermini",org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini.class,"FatturaElettronica",FatturaElettronica.class));
	
	}
	
	

	public IField FORMATO_TRASMISSIONE = null;
	 
	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField DATA_RICEZIONE = null;
	 
	public IField NOME_FILE = null;
	 
	public IField MESSAGE_ID = null;
	 
	public IField CEDENTE_PRESTATORE_DENOMINAZIONE = null;
	 
	public IField CEDENTE_PRESTATORE_PAESE = null;
	 
	public IField CEDENTE_PRESTATORE_CODICE_FISCALE = null;
	 
	public IField CESSIONARIO_COMMITTENTE_DENOMINAZIONE = null;
	 
	public IField CESSIONARIO_COMMITTENTE_PAESE = null;
	 
	public IField CESSIONARIO_COMMITTENTE_CODICE_FISCALE = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE = null;
	 
	public IField TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE = null;
	 
	public IField POSIZIONE = null;
	 
	public IField CODICE_DESTINATARIO = null;
	 
	public IField TIPO_DOCUMENTO = null;
	 
	public IField DIVISA = null;
	 
	public IField DATA = null;
	 
	public IField ANNO = null;
	 
	public IField NUMERO = null;
	 
	public IField ESITO = null;
	 
	public IField IMPORTO_TOTALE_DOCUMENTO = null;
	 
	public IField IMPORTO_TOTALE_RIEPILOGO = null;
	 
	public IField CAUSALE = null;
	 
	public IField STATO_CONSEGNA = null;
	 
	public IField DATA_CONSEGNA = null;
	 
	public IField DETTAGLIO_CONSEGNA = null;
	 
	public IField STATO_PROTOCOLLAZIONE = null;
	 
	public IField DATA_PROTOCOLLAZIONE = null;
	 
	public IField PROTOCOLLO = null;
	 
	public IField XML = null;
	 
	public org.govmix.proxy.fatturapa.model.IdNotificaDecorrenzaTerminiModel ID_DECORRENZA_TERMINI = null;
	 

	@Override
	public Class<FatturaElettronica> getModeledClass(){
		return FatturaElettronica.class;
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