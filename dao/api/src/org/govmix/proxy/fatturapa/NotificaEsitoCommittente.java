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
package org.govmix.proxy.fatturapa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.govmix.proxy.fatturapa.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.constants.ScartoType;
import java.io.Serializable;


/** <p>Java class for NotificaEsitoCommittente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificaEsitoCommittente">
 * 		&lt;sequence>
 * 			&lt;element name="idFattura" type="{http://www.govmix.org/proxy/fatturapa}id-fattura" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa}integer" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="numeroFattura" type="{http://www.govmix.org/proxy/fatturapa}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="esito" type="{http://www.govmix.org/proxy/fatturapa}EsitoCommittenteType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="messageIdCommittente" type="{http://www.govmix.org/proxy/fatturapa}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="nomeFile" type="{http://www.govmix.org/proxy/fatturapa}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="utente" type="{http://www.govmix.org/proxy/fatturapa}id-utente" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="modalita-batch" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="dataInvioEnte" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataInvioSdi" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="scarto" type="{http://www.govmix.org/proxy/fatturapa}ScartoType" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="scartoNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="scartoXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificaEsitoCommittente", 
  propOrder = {
  	"idFattura",
  	"_decimalWrapper_identificativoSdi",
  	"numeroFattura",
  	"anno",
  	"posizione",
  	"esito",
  	"descrizione",
  	"messageIdCommittente",
  	"nomeFile",
  	"utente",
  	"modalitaBatch",
  	"dataInvioEnte",
  	"dataInvioSdi",
  	"scarto",
  	"scartoNote",
  	"scartoXml",
  	"xml"
  }
)

@XmlRootElement(name = "NotificaEsitoCommittente")

public class NotificaEsitoCommittente extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public NotificaEsitoCommittente() {
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

  public IdFattura getIdFattura() {
    return this.idFattura;
  }

  public void setIdFattura(IdFattura idFattura) {
    this.idFattura = idFattura;
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

  public java.lang.String getNumeroFattura() {
    return this.numeroFattura;
  }

  public void setNumeroFattura(java.lang.String numeroFattura) {
    this.numeroFattura = numeroFattura;
  }

  public java.lang.Integer getAnno() {
    return this.anno;
  }

  public void setAnno(java.lang.Integer anno) {
    this.anno = anno;
  }

  public java.lang.Integer getPosizione() {
    return this.posizione;
  }

  public void setPosizione(java.lang.Integer posizione) {
    this.posizione = posizione;
  }

  public void set_value_esito(String value) {
    this.esito = (EsitoCommittenteType) EsitoCommittenteType.toEnumConstantFromString(value);
  }

  public String get_value_esito() {
    if(this.esito == null){
    	return null;
    }else{
    	return this.esito.toString();
    }
  }

  public org.govmix.proxy.fatturapa.constants.EsitoCommittenteType getEsito() {
    return this.esito;
  }

  public void setEsito(org.govmix.proxy.fatturapa.constants.EsitoCommittenteType esito) {
    this.esito = esito;
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public java.lang.String getMessageIdCommittente() {
    return this.messageIdCommittente;
  }

  public void setMessageIdCommittente(java.lang.String messageIdCommittente) {
    this.messageIdCommittente = messageIdCommittente;
  }

  public java.lang.String getNomeFile() {
    return this.nomeFile;
  }

  public void setNomeFile(java.lang.String nomeFile) {
    this.nomeFile = nomeFile;
  }

  public IdUtente getUtente() {
    return this.utente;
  }

  public void setUtente(IdUtente utente) {
    this.utente = utente;
  }

  public boolean getModalitaBatch() {
    return this.modalitaBatch;
  }

  public void setModalitaBatch(boolean modalitaBatch) {
    this.modalitaBatch = modalitaBatch;
  }

  public java.util.Date getDataInvioEnte() {
    return this.dataInvioEnte;
  }

  public void setDataInvioEnte(java.util.Date dataInvioEnte) {
    this.dataInvioEnte = dataInvioEnte;
  }

  public java.util.Date getDataInvioSdi() {
    return this.dataInvioSdi;
  }

  public void setDataInvioSdi(java.util.Date dataInvioSdi) {
    this.dataInvioSdi = dataInvioSdi;
  }

  public void set_value_scarto(String value) {
    this.scarto = (ScartoType) ScartoType.toEnumConstantFromString(value);
  }

  public String get_value_scarto() {
    if(this.scarto == null){
    	return null;
    }else{
    	return this.scarto.toString();
    }
  }

  public org.govmix.proxy.fatturapa.constants.ScartoType getScarto() {
    return this.scarto;
  }

  public void setScarto(org.govmix.proxy.fatturapa.constants.ScartoType scarto) {
    this.scarto = scarto;
  }

  public java.lang.String getScartoNote() {
    return this.scartoNote;
  }

  public void setScartoNote(java.lang.String scartoNote) {
    this.scartoNote = scartoNote;
  }

  public java.lang.String getScartoXml() {
    return this.scartoXml;
  }

  public void setScartoXml(java.lang.String scartoXml) {
    this.scartoXml = scartoXml;
  }

  public java.lang.String getXml() {
    return this.xml;
  }

  public void setXml(java.lang.String xml) {
    this.xml = xml;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.model.NotificaEsitoCommittenteModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.NotificaEsitoCommittente.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.NotificaEsitoCommittente.modelStaticInstance = new org.govmix.proxy.fatturapa.model.NotificaEsitoCommittenteModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.model.NotificaEsitoCommittenteModel model(){
	  if(org.govmix.proxy.fatturapa.NotificaEsitoCommittente.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.NotificaEsitoCommittente.modelStaticInstance;
  }


  @XmlElement(name="idFattura",required=true,nillable=false)
  protected IdFattura idFattura;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="integer")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Integer identificativoSdi;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="numeroFattura",required=false,nillable=false)
  protected java.lang.String numeroFattura;

  @javax.xml.bind.annotation.XmlSchemaType(name="nonNegativeInteger")
  @XmlElement(name="anno",required=false,nillable=false)
  protected java.lang.Integer anno;

  @javax.xml.bind.annotation.XmlSchemaType(name="positiveInteger")
  @XmlElement(name="posizione",required=false,nillable=false)
  protected java.lang.Integer posizione;

  @XmlTransient
  protected java.lang.String _value_esito;

  @XmlElement(name="esito",required=true,nillable=false)
  protected EsitoCommittenteType esito;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizione",required=false,nillable=false)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="messageIdCommittente",required=false,nillable=false)
  protected java.lang.String messageIdCommittente;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nomeFile",required=true,nillable=false)
  protected java.lang.String nomeFile;

  @XmlElement(name="utente",required=true,nillable=false)
  protected IdUtente utente;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="modalita-batch",required=true,nillable=false,defaultValue="false")
  protected boolean modalitaBatch = false;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataInvioEnte",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataInvioEnte;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataInvioSdi",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataInvioSdi;

  @XmlTransient
  protected java.lang.String _value_scarto;

  @XmlElement(name="scarto",required=false,nillable=false)
  protected ScartoType scarto;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="scartoNote",required=false,nillable=false)
  protected java.lang.String scartoNote;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="scartoXml",required=false,nillable=false)
  protected java.lang.String scartoXml;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="xml",required=false,nillable=false)
  protected java.lang.String xml;

}
