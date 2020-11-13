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
package org.govmix.proxy.fatturapa.orm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for id-notifica-esito-committente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="id-notifica-esito-committente">
 * 		&lt;sequence>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}unsignedLong" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="posizione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="motiviRifiuto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "id-notifica-esito-committente", 
  propOrder = {
  	"_decimalWrapper_identificativoSdi",
  	"posizione",
  	"motiviRifiuto"
  }
)

@XmlRootElement(name = "id-notifica-esito-committente")

public class IdNotificaEsitoCommittente extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public IdNotificaEsitoCommittente() {
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

  public java.lang.Integer getPosizione() {
    return this.posizione;
  }

  public void setPosizione(java.lang.Integer posizione) {
    this.posizione = posizione;
  }

  public java.lang.String getMotiviRifiuto() {
    return this.motiviRifiuto;
  }

  public void setMotiviRifiuto(java.lang.String motiviRifiuto) {
    this.motiviRifiuto = motiviRifiuto;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="unsignedLong")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Long identificativoSdi;

  @javax.xml.bind.annotation.XmlSchemaType(name="positiveInteger")
  @XmlElement(name="posizione",required=true,nillable=false)
  protected java.lang.Integer posizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="motiviRifiuto",required=false,nillable=false)
  protected java.lang.String motiviRifiuto;

}
