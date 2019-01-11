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
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import java.io.Serializable;


/** <p>Java class for id-sip complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="id-sip">
 * 		&lt;sequence>
 * 			&lt;element name="idSip" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoConsegna" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoConsegnaType" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataUltimaConsegna" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "id-sip", 
  propOrder = {
  	"idSip",
  	"statoConsegna",
  	"dataUltimaConsegna"
  }
)

@XmlRootElement(name = "id-sip")

public class IdSip extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public IdSip() {
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

  public long getIdSip() {
    return this.idSip;
  }

  public void setIdSip(long idSip) {
    this.idSip = idSip;
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

  public java.util.Date getDataUltimaConsegna() {
    return this.dataUltimaConsegna;
  }

  public void setDataUltimaConsegna(java.util.Date dataUltimaConsegna) {
    this.dataUltimaConsegna = dataUltimaConsegna;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @javax.xml.bind.annotation.XmlSchemaType(name="long")
  @XmlElement(name="idSip",required=true,nillable=false)
  protected long idSip;

  @XmlTransient
  protected java.lang.String _value_statoConsegna;

  @XmlElement(name="statoConsegna",required=false,nillable=false)
  protected StatoConsegnaType statoConsegna;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataUltimaConsegna",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataUltimaConsegna;

}
