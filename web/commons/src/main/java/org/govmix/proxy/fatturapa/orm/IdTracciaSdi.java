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
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import java.io.Serializable;


/** <p>Java class for id-traccia-sdi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="id-traccia-sdi">
 * 		&lt;sequence>
 * 			&lt;element name="idTraccia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoProtocollazione" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoProtocollazioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataProssimaProtocollazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "id-traccia-sdi", 
  propOrder = {
  	"idTraccia",
  	"statoProtocollazione",
  	"dataProssimaProtocollazione"
  }
)

@XmlRootElement(name = "id-traccia-sdi")

public class IdTracciaSdi extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public IdTracciaSdi() {
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

  public long getIdTraccia() {
    return this.idTraccia;
  }

  public void setIdTraccia(long idTraccia) {
    this.idTraccia = idTraccia;
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

  public java.util.Date getDataProssimaProtocollazione() {
    return this.dataProssimaProtocollazione;
  }

  public void setDataProssimaProtocollazione(java.util.Date dataProssimaProtocollazione) {
    this.dataProssimaProtocollazione = dataProssimaProtocollazione;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @javax.xml.bind.annotation.XmlSchemaType(name="long")
  @XmlElement(name="idTraccia",required=true,nillable=false)
  protected long idTraccia;

  @XmlTransient
  protected java.lang.String _value_statoProtocollazione;

  @XmlElement(name="statoProtocollazione",required=true,nillable=false)
  protected StatoProtocollazioneType statoProtocollazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataProssimaProtocollazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataProssimaProtocollazione;

}
