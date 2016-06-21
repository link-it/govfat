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
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}integer" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nomeFile" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="messageId" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="1" maxOccurs="1"/>
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
  	"xml"
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

  public byte[] getXml() {
    return this.xml;
  }

  public void setXml(byte[] xml) {
    this.xml = xml;
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
  @javax.xml.bind.annotation.XmlSchemaType(name="integer")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Integer identificativoSdi;

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

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="xml",required=true,nillable=false)
  protected byte[] xml;

}
