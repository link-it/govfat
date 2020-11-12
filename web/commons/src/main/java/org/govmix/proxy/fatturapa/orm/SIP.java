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
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import java.io.Serializable;


/** <p>Java class for SIP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SIP">
 * 		&lt;sequence>
 * 			&lt;element name="registro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="statoConsegna" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoConsegnaType" minOccurs="1" maxOccurs="1" default="NON_CONSEGNATA"/>
 * 			&lt;element name="erroreTimeout" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataUltimaConsegna" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="rapportoVersamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "SIP", 
  propOrder = {
  	"registro",
  	"anno",
  	"numero",
  	"statoConsegna",
  	"erroreTimeout",
  	"dataUltimaConsegna",
  	"rapportoVersamento"
  }
)

@XmlRootElement(name = "SIP")

public class SIP extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public SIP() {
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

  public java.lang.String getRegistro() {
    return this.registro;
  }

  public void setRegistro(java.lang.String registro) {
    this.registro = registro;
  }

  public int getAnno() {
    return this.anno;
  }

  public void setAnno(int anno) {
    this.anno = anno;
  }

  public java.lang.String getNumero() {
    return this.numero;
  }

  public void setNumero(java.lang.String numero) {
    this.numero = numero;
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

  public boolean isErroreTimeout() {
    return this.erroreTimeout;
  }

  public boolean getErroreTimeout() {
    return this.erroreTimeout;
  }

  public void setErroreTimeout(boolean erroreTimeout) {
    this.erroreTimeout = erroreTimeout;
  }

  public java.util.Date getDataUltimaConsegna() {
    return this.dataUltimaConsegna;
  }

  public void setDataUltimaConsegna(java.util.Date dataUltimaConsegna) {
    this.dataUltimaConsegna = dataUltimaConsegna;
  }

  public java.lang.String getRapportoVersamento() {
    return this.rapportoVersamento;
  }

  public void setRapportoVersamento(java.lang.String rapportoVersamento) {
    this.rapportoVersamento = rapportoVersamento;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.SIPModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.SIP.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.SIP.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.SIPModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.SIPModel model(){
	  if(org.govmix.proxy.fatturapa.orm.SIP.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.SIP.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="registro",required=false,nillable=false)
  protected java.lang.String registro;

  @javax.xml.bind.annotation.XmlSchemaType(name="int")
  @XmlElement(name="anno",required=false,nillable=false)
  protected int anno;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="numero",required=false,nillable=false)
  protected java.lang.String numero;

  @XmlTransient
  protected java.lang.String _value_statoConsegna;

  @XmlElement(name="statoConsegna",required=true,nillable=false,defaultValue="NON_CONSEGNATA")
  protected StatoConsegnaType statoConsegna = (StatoConsegnaType) StatoConsegnaType.toEnumConstantFromString("NON_CONSEGNATA");

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="erroreTimeout",required=true,nillable=false)
  protected boolean erroreTimeout;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataUltimaConsegna",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataUltimaConsegna;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="rapportoVersamento",required=false,nillable=false)
  protected java.lang.String rapportoVersamento;

}
