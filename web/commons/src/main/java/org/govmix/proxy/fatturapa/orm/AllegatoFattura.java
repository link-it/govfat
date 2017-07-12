/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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


/** <p>Java class for AllegatoFattura complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllegatoFattura">
 * 		&lt;sequence>
 * 			&lt;element name="idFattura" type="{http://www.govmix.org/proxy/fatturapa/orm}id-fattura" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nomeAttachment" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="algoritmoCompressione" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="formatoAttachment" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="descrizioneAttachment" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="attachment" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "AllegatoFattura", 
  propOrder = {
  	"idFattura",
  	"nomeAttachment",
  	"algoritmoCompressione",
  	"formatoAttachment",
  	"descrizioneAttachment",
  	"attachment"
  }
)

@XmlRootElement(name = "AllegatoFattura")

public class AllegatoFattura extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public AllegatoFattura() {
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

  public java.lang.String getNomeAttachment() {
    return this.nomeAttachment;
  }

  public void setNomeAttachment(java.lang.String nomeAttachment) {
    this.nomeAttachment = nomeAttachment;
  }

  public java.lang.String getAlgoritmoCompressione() {
    return this.algoritmoCompressione;
  }

  public void setAlgoritmoCompressione(java.lang.String algoritmoCompressione) {
    this.algoritmoCompressione = algoritmoCompressione;
  }

  public java.lang.String getFormatoAttachment() {
    return this.formatoAttachment;
  }

  public void setFormatoAttachment(java.lang.String formatoAttachment) {
    this.formatoAttachment = formatoAttachment;
  }

  public java.lang.String getDescrizioneAttachment() {
    return this.descrizioneAttachment;
  }

  public void setDescrizioneAttachment(java.lang.String descrizioneAttachment) {
    this.descrizioneAttachment = descrizioneAttachment;
  }

  public byte[] getAttachment() {
    return this.attachment;
  }

  public void setAttachment(byte[] attachment) {
    this.attachment = attachment;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.AllegatoFatturaModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.AllegatoFattura.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.AllegatoFattura.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.AllegatoFatturaModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.AllegatoFatturaModel model(){
	  if(org.govmix.proxy.fatturapa.orm.AllegatoFattura.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.AllegatoFattura.modelStaticInstance;
  }


  @XmlElement(name="idFattura",required=true,nillable=false)
  protected IdFattura idFattura;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="nomeAttachment",required=true,nillable=false)
  protected java.lang.String nomeAttachment;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="algoritmoCompressione",required=false,nillable=false)
  protected java.lang.String algoritmoCompressione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="formatoAttachment",required=false,nillable=false)
  protected java.lang.String formatoAttachment;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="descrizioneAttachment",required=false,nillable=false)
  protected java.lang.String descrizioneAttachment;

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="attachment",required=true,nillable=false)
  protected byte[] attachment;

}
