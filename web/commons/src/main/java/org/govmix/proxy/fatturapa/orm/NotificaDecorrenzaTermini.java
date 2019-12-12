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
import java.io.Serializable;


/** <p>Java class for NotificaDecorrenzaTermini complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificaDecorrenzaTermini">
 * 		&lt;sequence>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}unsignedLong" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nomeFile" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="messageId" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idTraccia" type="{http://www.govmix.org/proxy/fatturapa/orm}id-traccia-sdi" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "NotificaDecorrenzaTermini", 
  propOrder = {
  	"_decimalWrapper_identificativoSdi",
  	"nomeFile",
  	"descrizione",
  	"messageId",
  	"note",
  	"dataRicezione",
  	"idTraccia"
  }
)

@XmlRootElement(name = "NotificaDecorrenzaTermini")

public class NotificaDecorrenzaTermini extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public NotificaDecorrenzaTermini() {
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

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public java.lang.String getMessageId() {
    return this.messageId;
  }

  public void setMessageId(java.lang.String messageId) {
    this.messageId = messageId;
  }

  public java.lang.String getNote() {
    return this.note;
  }

  public void setNote(java.lang.String note) {
    this.note = note;
  }

  public java.util.Date getDataRicezione() {
    return this.dataRicezione;
  }

  public void setDataRicezione(java.util.Date dataRicezione) {
    this.dataRicezione = dataRicezione;
  }

  public IdTracciaSdi getIdTraccia() {
    return this.idTraccia;
  }

  public void setIdTraccia(IdTracciaSdi idTraccia) {
    this.idTraccia = idTraccia;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.NotificaDecorrenzaTerminiModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.NotificaDecorrenzaTerminiModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.NotificaDecorrenzaTerminiModel model(){
	  if(org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini.modelStaticInstance;
  }


  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="unsignedLong")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Long identificativoSdi;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nomeFile",required=true,nillable=false)
  protected java.lang.String nomeFile;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizione",required=false,nillable=false)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="messageId",required=true,nillable=false)
  protected java.lang.String messageId;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="note",required=false,nillable=false)
  protected java.lang.String note;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataRicezione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataRicezione;

  @XmlElement(name="idTraccia",required=true,nillable=false)
  protected IdTracciaSdi idTraccia;

}
