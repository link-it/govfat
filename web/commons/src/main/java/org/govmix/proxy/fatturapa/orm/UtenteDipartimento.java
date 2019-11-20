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
import java.io.Serializable;


/** <p>Java class for UtenteDipartimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UtenteDipartimento">
 * 		&lt;sequence>
 * 			&lt;element name="idDipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}id-dipartimento" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataUltimaModifica" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idResponsabile" type="{http://www.govmix.org/proxy/fatturapa/orm}id-utente" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "UtenteDipartimento", 
  propOrder = {
  	"idDipartimento",
  	"dataUltimaModifica",
  	"idResponsabile"
  }
)

@XmlRootElement(name = "UtenteDipartimento")

public class UtenteDipartimento extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public UtenteDipartimento() {
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

  public IdDipartimento getIdDipartimento() {
    return this.idDipartimento;
  }

  public void setIdDipartimento(IdDipartimento idDipartimento) {
    this.idDipartimento = idDipartimento;
  }

  public java.util.Date getDataUltimaModifica() {
    return this.dataUltimaModifica;
  }

  public void setDataUltimaModifica(java.util.Date dataUltimaModifica) {
    this.dataUltimaModifica = dataUltimaModifica;
  }

  public IdUtente getIdResponsabile() {
    return this.idResponsabile;
  }

  public void setIdResponsabile(IdUtente idResponsabile) {
    this.idResponsabile = idResponsabile;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @XmlElement(name="idDipartimento",required=true,nillable=false)
  protected IdDipartimento idDipartimento;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataUltimaModifica",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataUltimaModifica;

  @XmlElement(name="idResponsabile",required=true,nillable=false)
  protected IdUtente idResponsabile;

}
