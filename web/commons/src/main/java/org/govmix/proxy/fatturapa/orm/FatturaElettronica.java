/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;
import java.io.Serializable;


/** <p>Java class for FatturaElettronica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FatturaElettronica">
 * 		&lt;sequence>
 * 			&lt;element name="formatoTrasmissione" type="{http://www.govmix.org/proxy/fatturapa/orm}FormatoTrasmissioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}integer" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nomeFile" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="messageId" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatoreDenominazione" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatorePaese" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cedentePrestatoreCodiceFiscale" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittenteDenominazione" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittentePaese" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cessionarioCommittenteCodiceFiscale" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittenteDenominazione" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittentePaese" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="terzoIntermediarioOSoggettoEmittenteCodiceFiscale" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="1" maxOccurs="1" default="1"/>
 * 			&lt;element name="codiceDestinatario" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tipoDocumento" type="{http://www.govmix.org/proxy/fatturapa/orm}TipoDocumentoType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="divisa" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="data" type="{http://www.govmix.org/proxy/fatturapa/orm}date" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="numero" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="esito" type="{http://www.govmix.org/proxy/fatturapa/orm}EsitoType" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="daPagare" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="importoTotaleDocumento" type="{http://www.govmix.org/proxy/fatturapa/orm}decimal" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="importoTotaleRiepilogo" type="{http://www.govmix.org/proxy/fatturapa/orm}decimal" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="causale" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="statoConsegna" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoConsegnaType" minOccurs="1" maxOccurs="1" default="NON_CONSEGNATA"/>
 * 			&lt;element name="dataConsegna" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataProssimaConsegna" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tentativiConsegna" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dettaglioConsegna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="statoProtocollazione" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoProtocollazioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataProtocollazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="protocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idDecorrenzaTermini" type="{http://www.govmix.org/proxy/fatturapa/orm}id-notifica-decorrenza-termini" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idEsitoContabilizzazione" type="{http://www.govmix.org/proxy/fatturapa/orm}id-trasmissione-esito" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idEsitoScadenza" type="{http://www.govmix.org/proxy/fatturapa/orm}id-trasmissione-esito" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="Dipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}Dipartimento" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="LottoFatture" type="{http://www.govmix.org/proxy/fatturapa/orm}LottoFatture" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "FatturaElettronica", 
  propOrder = {
  	"formatoTrasmissione",
  	"_decimalWrapper_identificativoSdi",
  	"dataRicezione",
  	"nomeFile",
  	"messageId",
  	"cedentePrestatoreDenominazione",
  	"cedentePrestatorePaese",
  	"cedentePrestatoreCodiceFiscale",
  	"cessionarioCommittenteDenominazione",
  	"cessionarioCommittentePaese",
  	"cessionarioCommittenteCodiceFiscale",
  	"terzoIntermediarioOSoggettoEmittenteDenominazione",
  	"terzoIntermediarioOSoggettoEmittentePaese",
  	"terzoIntermediarioOSoggettoEmittenteCodiceFiscale",
  	"posizione",
  	"codiceDestinatario",
  	"tipoDocumento",
  	"divisa",
  	"data",
  	"anno",
  	"numero",
  	"esito",
  	"daPagare",
  	"_decimalWrapper_importoTotaleDocumento",
  	"_decimalWrapper_importoTotaleRiepilogo",
  	"causale",
  	"statoConsegna",
  	"dataConsegna",
  	"dataProssimaConsegna",
  	"tentativiConsegna",
  	"dettaglioConsegna",
  	"statoProtocollazione",
  	"dataScadenza",
  	"dataProtocollazione",
  	"protocollo",
  	"xml",
  	"idDecorrenzaTermini",
  	"idEsitoContabilizzazione",
  	"idEsitoScadenza",
  	"dipartimento",
  	"lottoFatture"
  }
)

@XmlRootElement(name = "FatturaElettronica")

public class FatturaElettronica extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public FatturaElettronica() {
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

  public java.lang.Integer getIdentificativoSdi() {
    if(this._decimalWrapper_identificativoSdi!=null){
		return (java.lang.Integer) this._decimalWrapper_identificativoSdi.getObject(java.lang.Integer.class);
	}else{
		return this.identificativoSdi;
	}
  }

  public void setIdentificativoSdi(java.lang.Integer identificativoSdi) {
    if(identificativoSdi!=null){
		this._decimalWrapper_identificativoSdi = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,12,identificativoSdi);
	}
  }

  public java.util.Date getDataRicezione() {
    return this.dataRicezione;
  }

  public void setDataRicezione(java.util.Date dataRicezione) {
    this.dataRicezione = dataRicezione;
  }

  public java.lang.String getNomeFile() {
    return this.nomeFile;
  }

  public void setNomeFile(java.lang.String nomeFile) {
    this.nomeFile = nomeFile;
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

  public java.lang.Integer getPosizione() {
    return this.posizione;
  }

  public void setPosizione(java.lang.Integer posizione) {
    this.posizione = posizione;
  }

  public java.lang.String getCodiceDestinatario() {
    return this.codiceDestinatario;
  }

  public void setCodiceDestinatario(java.lang.String codiceDestinatario) {
    this.codiceDestinatario = codiceDestinatario;
  }

  public void set_value_tipoDocumento(String value) {
    this.tipoDocumento = (TipoDocumentoType) TipoDocumentoType.toEnumConstantFromString(value);
  }

  public String get_value_tipoDocumento() {
    if(this.tipoDocumento == null){
    	return null;
    }else{
    	return this.tipoDocumento.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType getTipoDocumento() {
    return this.tipoDocumento;
  }

  public void setTipoDocumento(org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public java.lang.String getDivisa() {
    return this.divisa;
  }

  public void setDivisa(java.lang.String divisa) {
    this.divisa = divisa;
  }

  public java.util.Date getData() {
    return this.data;
  }

  public void setData(java.util.Date data) {
    this.data = data;
  }

  public java.lang.Integer getAnno() {
    return this.anno;
  }

  public void setAnno(java.lang.Integer anno) {
    this.anno = anno;
  }

  public java.lang.String getNumero() {
    return this.numero;
  }

  public void setNumero(java.lang.String numero) {
    this.numero = numero;
  }

  public void set_value_esito(String value) {
    this.esito = (EsitoType) EsitoType.toEnumConstantFromString(value);
  }

  public String get_value_esito() {
    if(this.esito == null){
    	return null;
    }else{
    	return this.esito.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.EsitoType getEsito() {
    return this.esito;
  }

  public void setEsito(org.govmix.proxy.fatturapa.orm.constants.EsitoType esito) {
    this.esito = esito;
  }

  public boolean isDaPagare() {
    return this.daPagare;
  }

  public boolean getDaPagare() {
    return this.daPagare;
  }

  public void setDaPagare(boolean daPagare) {
    this.daPagare = daPagare;
  }

  public java.lang.Double getImportoTotaleDocumento() {
    if(this._decimalWrapper_importoTotaleDocumento!=null){
		return (java.lang.Double) this._decimalWrapper_importoTotaleDocumento.getObject(java.lang.Double.class);
	}else{
		return this.importoTotaleDocumento;
	}
  }

  public void setImportoTotaleDocumento(java.lang.Double importoTotaleDocumento) {
    if(importoTotaleDocumento!=null){
		this._decimalWrapper_importoTotaleDocumento = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,11,2,2,importoTotaleDocumento);
	}
  }

  public java.lang.Double getImportoTotaleRiepilogo() {
    if(this._decimalWrapper_importoTotaleRiepilogo!=null){
		return (java.lang.Double) this._decimalWrapper_importoTotaleRiepilogo.getObject(java.lang.Double.class);
	}else{
		return this.importoTotaleRiepilogo;
	}
  }

  public void setImportoTotaleRiepilogo(java.lang.Double importoTotaleRiepilogo) {
    if(importoTotaleRiepilogo!=null){
		this._decimalWrapper_importoTotaleRiepilogo = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,11,2,2,importoTotaleRiepilogo);
	}
  }

  public java.lang.String getCausale() {
    return this.causale;
  }

  public void setCausale(java.lang.String causale) {
    this.causale = causale;
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

  public java.util.Date getDataProssimaConsegna() {
    return this.dataProssimaConsegna;
  }

  public void setDataProssimaConsegna(java.util.Date dataProssimaConsegna) {
    this.dataProssimaConsegna = dataProssimaConsegna;
  }

  public java.lang.Integer getTentativiConsegna() {
    return this.tentativiConsegna;
  }

  public void setTentativiConsegna(java.lang.Integer tentativiConsegna) {
    this.tentativiConsegna = tentativiConsegna;
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

  public java.util.Date getDataScadenza() {
    return this.dataScadenza;
  }

  public void setDataScadenza(java.util.Date dataScadenza) {
    this.dataScadenza = dataScadenza;
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

  public byte[] getXml() {
    return this.xml;
  }

  public void setXml(byte[] xml) {
    this.xml = xml;
  }

  public IdNotificaDecorrenzaTermini getIdDecorrenzaTermini() {
    return this.idDecorrenzaTermini;
  }

  public void setIdDecorrenzaTermini(IdNotificaDecorrenzaTermini idDecorrenzaTermini) {
    this.idDecorrenzaTermini = idDecorrenzaTermini;
  }

  public IdTrasmissioneEsito getIdEsitoContabilizzazione() {
    return this.idEsitoContabilizzazione;
  }

  public void setIdEsitoContabilizzazione(IdTrasmissioneEsito idEsitoContabilizzazione) {
    this.idEsitoContabilizzazione = idEsitoContabilizzazione;
  }

  public IdTrasmissioneEsito getIdEsitoScadenza() {
    return this.idEsitoScadenza;
  }

  public void setIdEsitoScadenza(IdTrasmissioneEsito idEsitoScadenza) {
    this.idEsitoScadenza = idEsitoScadenza;
  }

  public Dipartimento getDipartimento() {
    return this.dipartimento;
  }

  public void setDipartimento(Dipartimento dipartimento) {
    this.dipartimento = dipartimento;
  }

  public LottoFatture getLottoFatture() {
    return this.lottoFatture;
  }

  public void setLottoFatture(LottoFatture lottoFatture) {
    this.lottoFatture = lottoFatture;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.FatturaElettronicaModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.FatturaElettronica.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.FatturaElettronica.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.FatturaElettronicaModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.FatturaElettronicaModel model(){
	  if(org.govmix.proxy.fatturapa.orm.FatturaElettronica.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.FatturaElettronica.modelStaticInstance;
  }


  @XmlTransient
  protected java.lang.String _value_formatoTrasmissione;

  @XmlElement(name="formatoTrasmissione",required=true,nillable=false)
  protected FormatoTrasmissioneType formatoTrasmissione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="integer")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Integer identificativoSdi;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataRicezione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataRicezione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nomeFile",required=true,nillable=false)
  protected java.lang.String nomeFile;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="messageId",required=true,nillable=false)
  protected java.lang.String messageId;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cedentePrestatoreDenominazione",required=true,nillable=false)
  protected java.lang.String cedentePrestatoreDenominazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cedentePrestatorePaese",required=true,nillable=false)
  protected java.lang.String cedentePrestatorePaese;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cedentePrestatoreCodiceFiscale",required=true,nillable=false)
  protected java.lang.String cedentePrestatoreCodiceFiscale;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="cessionarioCommittenteDenominazione",required=true,nillable=false)
  protected java.lang.String cessionarioCommittenteDenominazione;

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

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittentePaese",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittentePaese;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="terzoIntermediarioOSoggettoEmittenteCodiceFiscale",required=false,nillable=false)
  protected java.lang.String terzoIntermediarioOSoggettoEmittenteCodiceFiscale;

  @javax.xml.bind.annotation.XmlSchemaType(name="positiveInteger")
  @XmlElement(name="posizione",required=true,nillable=false,defaultValue="1")
  protected java.lang.Integer posizione = new java.lang.Integer("1");

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codiceDestinatario",required=true,nillable=false)
  protected java.lang.String codiceDestinatario;

  @XmlTransient
  protected java.lang.String _value_tipoDocumento;

  @XmlElement(name="tipoDocumento",required=true,nillable=false)
  protected TipoDocumentoType tipoDocumento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="divisa",required=true,nillable=false)
  protected java.lang.String divisa;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="data",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date data;

  @javax.xml.bind.annotation.XmlSchemaType(name="nonNegativeInteger")
  @XmlElement(name="anno",required=true,nillable=false)
  protected java.lang.Integer anno;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="numero",required=true,nillable=false)
  protected java.lang.String numero;

  @XmlTransient
  protected java.lang.String _value_esito;

  @XmlElement(name="esito",required=false,nillable=false)
  protected EsitoType esito;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="daPagare",required=true,nillable=false)
  protected boolean daPagare;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="importoTotaleDocumento",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_importoTotaleDocumento = null;

  @XmlTransient
  protected java.lang.Double importoTotaleDocumento;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="importoTotaleRiepilogo",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_importoTotaleRiepilogo = null;

  @XmlTransient
  protected java.lang.Double importoTotaleRiepilogo;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="causale",required=false,nillable=false)
  protected java.lang.String causale;

  @XmlTransient
  protected java.lang.String _value_statoConsegna;

  @XmlElement(name="statoConsegna",required=true,nillable=false,defaultValue="NON_CONSEGNATA")
  protected StatoConsegnaType statoConsegna = (StatoConsegnaType) StatoConsegnaType.toEnumConstantFromString("NON_CONSEGNATA");

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataConsegna",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataConsegna;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataProssimaConsegna",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataProssimaConsegna;

  @javax.xml.bind.annotation.XmlSchemaType(name="nonNegativeInteger")
  @XmlElement(name="tentativiConsegna",required=true,nillable=false)
  protected java.lang.Integer tentativiConsegna;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="dettaglioConsegna",required=false,nillable=false)
  protected java.lang.String dettaglioConsegna;

  @XmlTransient
  protected java.lang.String _value_statoProtocollazione;

  @XmlElement(name="statoProtocollazione",required=true,nillable=false)
  protected StatoProtocollazioneType statoProtocollazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataScadenza",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataScadenza;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataProtocollazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataProtocollazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="protocollo",required=false,nillable=false)
  protected java.lang.String protocollo;

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="xml",required=true,nillable=false)
  protected byte[] xml;

  @XmlElement(name="idDecorrenzaTermini",required=false,nillable=false)
  protected IdNotificaDecorrenzaTermini idDecorrenzaTermini;

  @XmlElement(name="idEsitoContabilizzazione",required=false,nillable=false)
  protected IdTrasmissioneEsito idEsitoContabilizzazione;

  @XmlElement(name="idEsitoScadenza",required=false,nillable=false)
  protected IdTrasmissioneEsito idEsitoScadenza;

  @XmlElement(name="Dipartimento",required=false,nillable=false)
  protected Dipartimento dipartimento;

  @XmlElement(name="LottoFatture",required=false,nillable=false)
  protected LottoFatture lottoFatture;

}
