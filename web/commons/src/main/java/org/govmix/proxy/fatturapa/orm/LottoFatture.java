/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.SottodominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import java.io.Serializable;


/** <p>Java class for LottoFatture complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LottoFatture">
 * 		&lt;sequence>
 * 			&lt;element name="formatoTrasmissione" type="{http://www.govmix.org/proxy/fatturapa/orm}FormatoTrasmissioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}unsignedLong" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nomeFile" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="formatoArchivioInvioFattura" type="{http://www.govmix.org/proxy/fatturapa/orm}FormatoArchivioInvioFatturaType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="messageId" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatoreDenominazione" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatoreNome" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatoreCognome" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatoreCodice" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatorePaese" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatoreCodiceFiscale" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittenteDenominazione" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittenteNome" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittenteCognome" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittenteCodice" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittentePaese" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittenteCodiceFiscale" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittenteDenominazione" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittenteNome" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittenteCognome" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittenteCodice" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittentePaese" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittenteCodiceFiscale" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codiceDestinatario" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="fatturazioneAttiva" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoElaborazioneInUscita" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoElaborazioneType" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tipiComunicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataUltimaElaborazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dettaglioElaborazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataProssimaElaborazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tentativiConsegna" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataRicezione" type="{http://www.govmix.org/proxy/fatturapa/orm}date" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoInserimento" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoInserimentoType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoConsegna" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoConsegnaType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataConsegna" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dettaglioConsegna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="statoProtocollazione" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoProtocollazioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dominio" type="{http://www.govmix.org/proxy/fatturapa/orm}DominioType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="sottodominio" type="{http://www.govmix.org/proxy/fatturapa/orm}SottodominioType" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="pagoPA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataProtocollazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="protocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idSIP" type="{http://www.govmix.org/proxy/fatturapa/orm}id-sip" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="id-egov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="Dipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}Dipartimento" minOccurs="0" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LottoFatture", 
  propOrder = {
  	"formatoTrasmissione",
  	"_decimalWrapper_identificativoSdi",
  	"nomeFile",
  	"formatoArchivioInvioFattura",
  	"messageId",
  	"cedentePrestatoreDenominazione",
  	"cedentePrestatoreNome",
  	"cedentePrestatoreCognome",
  	"cedentePrestatoreCodice",
  	"cedentePrestatorePaese",
  	"cedentePrestatoreCodiceFiscale",
  	"cessionarioCommittenteDenominazione",
  	"cessionarioCommittenteNome",
  	"cessionarioCommittenteCognome",
  	"cessionarioCommittenteCodice",
  	"cessionarioCommittentePaese",
  	"cessionarioCommittenteCodiceFiscale",
  	"terzoIntermediarioOSoggettoEmittenteDenominazione",
  	"terzoIntermediarioOSoggettoEmittenteNome",
  	"terzoIntermediarioOSoggettoEmittenteCognome",
  	"terzoIntermediarioOSoggettoEmittenteCodice",
  	"terzoIntermediarioOSoggettoEmittentePaese",
  	"terzoIntermediarioOSoggettoEmittenteCodiceFiscale",
  	"codiceDestinatario",
  	"xml",
  	"fatturazioneAttiva",
  	"statoElaborazioneInUscita",
  	"tipiComunicazione",
  	"dataUltimaElaborazione",
  	"dettaglioElaborazione",
  	"dataProssimaElaborazione",
  	"tentativiConsegna",
  	"dataRicezione",
  	"statoInserimento",
  	"statoConsegna",
  	"dataConsegna",
  	"dettaglioConsegna",
  	"statoProtocollazione",
  	"dominio",
  	"sottodominio",
  	"pagoPA",
  	"dataProtocollazione",
  	"protocollo",
  	"idSIP",
  	"idEgov",
  	"dipartimento"
  }
)

@XmlRootElement(name = "LottoFatture")

public class LottoFatture extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public LottoFatture() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return new Long(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=new Long(-1);
  }

  public void set_value_formatoTrasmissione(String value) {
    this.formatoTrasmissione = (FormatoTrasmissioneType) FormatoTrasmissioneType.toEnumConstantFromString(value);
  }

  public String get_value_formatoTrasmissione() {
    if(this.formatoTrasmissione == null){
    	return null;
    }else{
    	return this.formatoTrasmissione.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType getFormatoTrasmissione() {
    return this.formatoTrasmissione;
  }

  public void setFormatoTrasmissione(org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType formatoTrasmissione) {
    this.formatoTrasmissione = formatoTrasmissione;
  }

  public java.lang.Long getIdentificativoSdi() {
    if(this._decimalWrapper_identificativoSdi!=null){
		return (java.lang.Long) this._decimalWrapper_identificativoSdi.getObject(java.lang.Long.class);
	}else{
		return this.identificativoSdi;
	}
  }

  public void setIdentificativoSdi(java.lang.Long identificativoSdi) {
    if(identificativoSdi!=null){
		this._decimalWrapper_identificativoSdi = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,40,identificativoSdi);
	}
  }

  public java.lang.String getNomeFile() {
    return this.nomeFile;
  }

  public void setNomeFile(java.lang.String nomeFile) {
    this.nomeFile = nomeFile;
  }

  public void set_value_formatoArchivioInvioFattura(String value) {
    this.formatoArchivioInvioFattura = (FormatoArchivioInvioFatturaType) FormatoArchivioInvioFatturaType.toEnumConstantFromString(value);
  }

  public String get_value_formatoArchivioInvioFattura() {
    if(this.formatoArchivioInvioFattura == null){
    	return null;
    }else{
    	return this.formatoArchivioInvioFattura.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType getFormatoArchivioInvioFattura() {
    return this.formatoArchivioInvioFattura;
  }

  public void setFormatoArchivioInvioFattura(org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType formatoArchivioInvioFattura) {
    this.formatoArchivioInvioFattura = formatoArchivioInvioFattura;
  }

  public java.lang.String getMessageId() {
    return this.messageId;
  }

  public void setMessageId(java.lang.String messageId) {
    this.messageId = messageId;
  }

  public java.lang.String getCedentePrestatoreDenominazione() {
    return this.cedentePrestatoreDenominazione;
  }

  public void setCedentePrestatoreDenominazione(java.lang.String cedentePrestatoreDenominazione) {
    this.cedentePrestatoreDenominazione = cedentePrestatoreDenominazione;
  }

  public java.lang.String getCedentePrestatoreNome() {
    return this.cedentePrestatoreNome;
  }

  public void setCedentePrestatoreNome(java.lang.String cedentePrestatoreNome) {
    this.cedentePrestatoreNome = cedentePrestatoreNome;
  }

  public java.lang.String getCedentePrestatoreCognome() {
    return this.cedentePrestatoreCognome;
  }

  public void setCedentePrestatoreCognome(java.lang.String cedentePrestatoreCognome) {
    this.cedentePrestatoreCognome = cedentePrestatoreCognome;
  }

  public java.lang.String getCedentePrestatoreCodice() {
    return this.cedentePrestatoreCodice;
  }

  public void setCedentePrestatoreCodice(java.lang.String cedentePrestatoreCodice) {
    this.cedentePrestatoreCodice = cedentePrestatoreCodice;
  }

  public java.lang.String getCedentePrestatorePaese() {
    return this.cedentePrestatorePaese;
  }

  public void setCedentePrestatorePaese(java.lang.String cedentePrestatorePaese) {
    this.cedentePrestatorePaese = cedentePrestatorePaese;
  }

  public java.lang.String getCedentePrestatoreCodiceFiscale() {
    return this.cedentePrestatoreCodiceFiscale;
  }

  public void setCedentePrestatoreCodiceFiscale(java.lang.String cedentePrestatoreCodiceFiscale) {
    this.cedentePrestatoreCodiceFiscale = cedentePrestatoreCodiceFiscale;
  }

  public java.lang.String getCessionarioCommittenteDenominazione() {
    return this.cessionarioCommittenteDenominazione;
  }

  public void setCessionarioCommittenteDenominazione(java.lang.String cessionarioCommittenteDenominazione) {
    this.cessionarioCommittenteDenominazione = cessionarioCommittenteDenominazione;
  }

  public java.lang.String getCessionarioCommittenteNome() {
    return this.cessionarioCommittenteNome;
  }

  public void setCessionarioCommittenteNome(java.lang.String cessionarioCommittenteNome) {
    this.cessionarioCommittenteNome = cessionarioCommittenteNome;
  }

  public java.lang.String getCessionarioCommittenteCognome() {
    return this.cessionarioCommittenteCognome;
  }

  public void setCessionarioCommittenteCognome(java.lang.String cessionarioCommittenteCognome) {
    this.cessionarioCommittenteCognome = cessionarioCommittenteCognome;
  }

  public java.lang.String getCessionarioCommittenteCodice() {
    return this.cessionarioCommittenteCodice;
  }

  public void setCessionarioCommittenteCodice(java.lang.String cessionarioCommittenteCodice) {
    this.cessionarioCommittenteCodice = cessionarioCommittenteCodice;
  }

  public java.lang.String getCessionarioCommittentePaese() {
    return this.cessionarioCommittentePaese;
  }

  public void setCessionarioCommittentePaese(java.lang.String cessionarioCommittentePaese) {
    this.cessionarioCommittentePaese = cessionarioCommittentePaese;
  }

  public java.lang.String getCessionarioCommittenteCodiceFiscale() {
    return this.cessionarioCommittenteCodiceFiscale;
  }

  public void setCessionarioCommittenteCodiceFiscale(java.lang.String cessionarioCommittenteCodiceFiscale) {
    this.cessionarioCommittenteCodiceFiscale = cessionarioCommittenteCodiceFiscale;
  }

  public java.lang.String getTerzoIntermediarioOSoggettoEmittenteDenominazione() {
    return this.terzoIntermediarioOSoggettoEmittenteDenominazione;
  }

  public void setTerzoIntermediarioOSoggettoEmittenteDenominazione(java.lang.String terzoIntermediarioOSoggettoEmittenteDenominazione) {
    this.terzoIntermediarioOSoggettoEmittenteDenominazione = terzoIntermediarioOSoggettoEmittenteDenominazione;
  }

  public java.lang.String getTerzoIntermediarioOSoggettoEmittenteNome() {
    return this.terzoIntermediarioOSoggettoEmittenteNome;
  }

  public void setTerzoIntermediarioOSoggettoEmittenteNome(java.lang.String terzoIntermediarioOSoggettoEmittenteNome) {
    this.terzoIntermediarioOSoggettoEmittenteNome = terzoIntermediarioOSoggettoEmittenteNome;
  }

  public java.lang.String getTerzoIntermediarioOSoggettoEmittenteCognome() {
    return this.terzoIntermediarioOSoggettoEmittenteCognome;
  }

  public void setTerzoIntermediarioOSoggettoEmittenteCognome(java.lang.String terzoIntermediarioOSoggettoEmittenteCognome) {
    this.terzoIntermediarioOSoggettoEmittenteCognome = terzoIntermediarioOSoggettoEmittenteCognome;
  }

  public java.lang.String getTerzoIntermediarioOSoggettoEmittenteCodice() {
    return this.terzoIntermediarioOSoggettoEmittenteCodice;
  }

  public void setTerzoIntermediarioOSoggettoEmittenteCodice(java.lang.String terzoIntermediarioOSoggettoEmittenteCodice) {
    this.terzoIntermediarioOSoggettoEmittenteCodice = terzoIntermediarioOSoggettoEmittenteCodice;
  }

  public java.lang.String getTerzoIntermediarioOSoggettoEmittentePaese() {
    return this.terzoIntermediarioOSoggettoEmittentePaese;
  }

  public void setTerzoIntermediarioOSoggettoEmittentePaese(java.lang.String terzoIntermediarioOSoggettoEmittentePaese) {
    this.terzoIntermediarioOSoggettoEmittentePaese = terzoIntermediarioOSoggettoEmittentePaese;
  }

  public java.lang.String getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale() {
    return this.terzoIntermediarioOSoggettoEmittenteCodiceFiscale;
  }

  public void setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(java.lang.String terzoIntermediarioOSoggettoEmittenteCodiceFiscale) {
    this.terzoIntermediarioOSoggettoEmittenteCodiceFiscale = terzoIntermediarioOSoggettoEmittenteCodiceFiscale;
  }

  public java.lang.String getCodiceDestinatario() {
    return this.codiceDestinatario;
  }

  public void setCodiceDestinatario(java.lang.String codiceDestinatario) {
    this.codiceDestinatario = codiceDestinatario;
  }

  public byte[] getXml() {
    return this.xml;
  }

  public void setXml(byte[] xml) {
    this.xml = xml;
  }

  public boolean isFatturazioneAttiva() {
    return this.fatturazioneAttiva;
  }

  public boolean getFatturazioneAttiva() {
    return this.fatturazioneAttiva;
  }

  public void setFatturazioneAttiva(boolean fatturazioneAttiva) {
    this.fatturazioneAttiva = fatturazioneAttiva;
  }

  public void set_value_statoElaborazioneInUscita(String value) {
    this.statoElaborazioneInUscita = (StatoElaborazioneType) StatoElaborazioneType.toEnumConstantFromString(value);
  }

  public String get_value_statoElaborazioneInUscita() {
    if(this.statoElaborazioneInUscita == null){
    	return null;
    }else{
    	return this.statoElaborazioneInUscita.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType getStatoElaborazioneInUscita() {
    return this.statoElaborazioneInUscita;
  }

  public void setStatoElaborazioneInUscita(org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType statoElaborazioneInUscita) {
    this.statoElaborazioneInUscita = statoElaborazioneInUscita;
  }

  public java.lang.String getTipiComunicazione() {
    return this.tipiComunicazione;
  }

  public void setTipiComunicazione(java.lang.String tipiComunicazione) {
    this.tipiComunicazione = tipiComunicazione;
  }

  public java.util.Date getDataUltimaElaborazione() {
    return this.dataUltimaElaborazione;
  }

  public void setDataUltimaElaborazione(java.util.Date dataUltimaElaborazione) {
    this.dataUltimaElaborazione = dataUltimaElaborazione;
  }

  public java.lang.String getDettaglioElaborazione() {
    return this.dettaglioElaborazione;
  }

  public void setDettaglioElaborazione(java.lang.String dettaglioElaborazione) {
    this.dettaglioElaborazione = dettaglioElaborazione;
  }

  public java.util.Date getDataProssimaElaborazione() {
    return this.dataProssimaElaborazione;
  }

  public void setDataProssimaElaborazione(java.util.Date dataProssimaElaborazione) {
    this.dataProssimaElaborazione = dataProssimaElaborazione;
  }

  public java.lang.Integer getTentativiConsegna() {
    return this.tentativiConsegna;
  }

  public void setTentativiConsegna(java.lang.Integer tentativiConsegna) {
    this.tentativiConsegna = tentativiConsegna;
  }

  public java.util.Date getDataRicezione() {
    return this.dataRicezione;
  }

  public void setDataRicezione(java.util.Date dataRicezione) {
    this.dataRicezione = dataRicezione;
  }

  public void set_value_statoInserimento(String value) {
    this.statoInserimento = (StatoInserimentoType) StatoInserimentoType.toEnumConstantFromString(value);
  }

  public String get_value_statoInserimento() {
    if(this.statoInserimento == null){
    	return null;
    }else{
    	return this.statoInserimento.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType getStatoInserimento() {
    return this.statoInserimento;
  }

  public void setStatoInserimento(org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType statoInserimento) {
    this.statoInserimento = statoInserimento;
  }

  public void set_value_statoConsegna(String value) {
    this.statoConsegna = (StatoConsegnaType) StatoConsegnaType.toEnumConstantFromString(value);
  }

  public String get_value_statoConsegna() {
    if(this.statoConsegna == null){
    	return null;
    }else{
    	return this.statoConsegna.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType getStatoConsegna() {
    return this.statoConsegna;
  }

  public void setStatoConsegna(org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType statoConsegna) {
    this.statoConsegna = statoConsegna;
  }

  public java.util.Date getDataConsegna() {
    return this.dataConsegna;
  }

  public void setDataConsegna(java.util.Date dataConsegna) {
    this.dataConsegna = dataConsegna;
  }

  public java.lang.String getDettaglioConsegna() {
    return this.dettaglioConsegna;
  }

  public void setDettaglioConsegna(java.lang.String dettaglioConsegna) {
    this.dettaglioConsegna = dettaglioConsegna;
  }

  public void set_value_statoProtocollazione(String value) {
    this.statoProtocollazione = (StatoProtocollazioneType) StatoProtocollazioneType.toEnumConstantFromString(value);
  }

  public String get_value_statoProtocollazione() {
    if(this.statoProtocollazione == null){
    	return null;
    }else{
    	return this.statoProtocollazione.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType getStatoProtocollazione() {
    return this.statoProtocollazione;
  }

  public void setStatoProtocollazione(org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType statoProtocollazione) {
    this.statoProtocollazione = statoProtocollazione;
  }

  public void set_value_dominio(String value) {
    this.dominio = (DominioType) DominioType.toEnumConstantFromString(value);
  }

  public String get_value_dominio() {
    if(this.dominio == null){
    	return null;
    }else{
    	return this.dominio.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.DominioType getDominio() {
    return this.dominio;
  }

  public void setDominio(org.govmix.proxy.fatturapa.orm.constants.DominioType dominio) {
    this.dominio = dominio;
  }

  public void set_value_sottodominio(String value) {
    this.sottodominio = (SottodominioType) SottodominioType.toEnumConstantFromString(value);
  }

  public String get_value_sottodominio() {
    if(this.sottodominio == null){
    	return null;
    }else{
    	return this.sottodominio.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.SottodominioType getSottodominio() {
    return this.sottodominio;
  }

  public void setSottodominio(org.govmix.proxy.fatturapa.orm.constants.SottodominioType sottodominio) {
    this.sottodominio = sottodominio;
  }

  public java.lang.String getPagoPA() {
    return this.pagoPA;
  }

  public void setPagoPA(java.lang.String pagoPA) {
    this.pagoPA = pagoPA;
  }

  public java.util.Date getDataProtocollazione() {
    return this.dataProtocollazione;
  }

  public void setDataProtocollazione(java.util.Date dataProtocollazione) {
    this.dataProtocollazione = dataProtocollazione;
  }

  public java.lang.String getProtocollo() {
    return this.protocollo;
  }

  public void setProtocollo(java.lang.String protocollo) {
    this.protocollo = protocollo;
  }

  public IdSip getIdSIP() {
    return this.idSIP;
  }

  public void setIdSIP(IdSip idSIP) {
    this.idSIP = idSIP;
  }

  public java.lang.String getIdEgov() {
    return this.idEgov;
  }

  public void setIdEgov(java.lang.String idEgov) {
    this.idEgov = idEgov;
  }

  public Dipartimento getDipartimento() {
    return this.dipartimento;
  }

  public void setDipartimento(Dipartimento dipartimento) {
    this.dipartimento = dipartimento;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.LottoFattureModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.LottoFatture.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.LottoFatture.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.LottoFattureModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.LottoFattureModel model(){
	  if(org.govmix.proxy.fatturapa.orm.LottoFatture.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.LottoFatture.modelStaticInstance;
  }


  @XmlTransient
  protected java.lang.String _value_formatoTrasmissione;

  @XmlElement(name="formatoTrasmissione",required=true,nillable=false)
  protected FormatoTrasmissioneType formatoTrasmissione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="unsignedLong")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Long identificativoSdi;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nomeFile",required=true,nillable=false)
  protected java.lang.String nomeFile;

  @XmlTransient
  protected java.lang.String _value_formatoArchivioInvioFattura;

  @XmlElement(name="formatoArchivioInvioFattura",required=true,nillable=false)
  protected FormatoArchivioInvioFatturaType formatoArchivioInvioFattura;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="messageId",required=true,nillable=false)
  protected java.lang.String messageId;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cedentePrestatoreDenominazione",required=false,nillable=false)
  protected java.lang.String cedentePrestatoreDenominazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cedentePrestatoreNome",required=false,nillable=false)
  protected java.lang.String cedentePrestatoreNome;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cedentePrestatoreCognome",required=false,nillable=false)
  protected java.lang.String cedentePrestatoreCognome;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cedentePrestatoreCodice",required=false,nillable=false)
  protected java.lang.String cedentePrestatoreCodice;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cedentePrestatorePaese",required=false,nillable=false)
  protected java.lang.String cedentePrestatorePaese;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cedentePrestatoreCodiceFiscale",required=false,nillable=false)
  protected java.lang.String cedentePrestatoreCodiceFiscale;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cessionarioCommittenteDenominazione",required=false,nillable=false)
  protected java.lang.String cessionarioCommittenteDenominazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cessionarioCommittenteNome",required=false,nillable=false)
  protected java.lang.String cessionarioCommittenteNome;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cessionarioCommittenteCognome",required=false,nillable=false)
  protected java.lang.String cessionarioCommittenteCognome;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cessionarioCommittenteCodice",required=false,nillable=false)
  protected java.lang.String cessionarioCommittenteCodice;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cessionarioCommittentePaese",required=false,nillable=false)
  protected java.lang.String cessionarioCommittentePaese;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cessionarioCommittenteCodiceFiscale",required=false,nillable=false)
  protected java.lang.String cessionarioCommittenteCodiceFiscale;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittenteDenominazione",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittenteDenominazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittenteNome",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittenteNome;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittenteCognome",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittenteCognome;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittenteCodice",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittenteCodice;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittentePaese",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittentePaese;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittenteCodiceFiscale",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittenteCodiceFiscale;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codiceDestinatario",required=true,nillable=false)
  protected java.lang.String codiceDestinatario;

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="xml",required=true,nillable=false)
  protected byte[] xml;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="fatturazioneAttiva",required=true,nillable=false)
  protected boolean fatturazioneAttiva;

  @XmlTransient
  protected java.lang.String _value_statoElaborazioneInUscita;

  @XmlElement(name="statoElaborazioneInUscita",required=false,nillable=false)
  protected StatoElaborazioneType statoElaborazioneInUscita;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="tipiComunicazione",required=false,nillable=false)
  protected java.lang.String tipiComunicazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataUltimaElaborazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataUltimaElaborazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="dettaglioElaborazione",required=false,nillable=false)
  protected java.lang.String dettaglioElaborazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataProssimaElaborazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataProssimaElaborazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="nonNegativeInteger")
  @XmlElement(name="tentativiConsegna",required=true,nillable=false)
  protected java.lang.Integer tentativiConsegna;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="dataRicezione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataRicezione;

  @XmlTransient
  protected java.lang.String _value_statoInserimento;

  @XmlElement(name="statoInserimento",required=true,nillable=false)
  protected StatoInserimentoType statoInserimento;

  @XmlTransient
  protected java.lang.String _value_statoConsegna;

  @XmlElement(name="statoConsegna",required=true,nillable=false)
  protected StatoConsegnaType statoConsegna;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataConsegna",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataConsegna;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="dettaglioConsegna",required=false,nillable=false)
  protected java.lang.String dettaglioConsegna;

  @XmlTransient
  protected java.lang.String _value_statoProtocollazione;

  @XmlElement(name="statoProtocollazione",required=true,nillable=false)
  protected StatoProtocollazioneType statoProtocollazione;

  @XmlTransient
  protected java.lang.String _value_dominio;

  @XmlElement(name="dominio",required=true,nillable=false)
  protected DominioType dominio;

  @XmlTransient
  protected java.lang.String _value_sottodominio;

  @XmlElement(name="sottodominio",required=false,nillable=false)
  protected SottodominioType sottodominio;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="pagoPA",required=false,nillable=false)
  protected java.lang.String pagoPA;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataProtocollazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataProtocollazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="protocollo",required=false,nillable=false)
  protected java.lang.String protocollo;

  @XmlElement(name="idSIP",required=false,nillable=false)
  protected IdSip idSIP;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="id-egov",required=false,nillable=false)
  protected java.lang.String idEgov;

  @XmlElement(name="Dipartimento",required=false,nillable=false)
  protected Dipartimento dipartimento;

}
