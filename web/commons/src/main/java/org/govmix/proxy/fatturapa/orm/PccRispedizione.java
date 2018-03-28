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
import org.govmix.proxy.fatturapa.orm.constants.TipoErroreType;
import java.io.Serializable;


/** <p>Java class for PccRispedizione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccRispedizione">
 * 		&lt;sequence>
 * 			&lt;element name="maxNumeroTentativi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="intervalloTentativi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="abilitato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tipoErrore" type="{http://www.govmix.org/proxy/fatturapa/orm}TipoErroreType" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "PccRispedizione", 
  propOrder = {
  	"maxNumeroTentativi",
  	"intervalloTentativi",
  	"codiceErrore",
  	"abilitato",
  	"descrizioneErrore",
  	"tipoErrore"
  }
)

@XmlRootElement(name = "PccRispedizione")

public class PccRispedizione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccRispedizione() {
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

  public int getMaxNumeroTentativi() {
    return this.maxNumeroTentativi;
  }

  public void setMaxNumeroTentativi(int maxNumeroTentativi) {
    this.maxNumeroTentativi = maxNumeroTentativi;
  }

  public int getIntervalloTentativi() {
    return this.intervalloTentativi;
  }

  public void setIntervalloTentativi(int intervalloTentativi) {
    this.intervalloTentativi = intervalloTentativi;
  }

  public java.lang.String getCodiceErrore() {
    return this.codiceErrore;
  }

  public void setCodiceErrore(java.lang.String codiceErrore) {
    this.codiceErrore = codiceErrore;
  }

  public boolean isAbilitato() {
    return this.abilitato;
  }

  public boolean getAbilitato() {
    return this.abilitato;
  }

  public void setAbilitato(boolean abilitato) {
    this.abilitato = abilitato;
  }

  public java.lang.String getDescrizioneErrore() {
    return this.descrizioneErrore;
  }

  public void setDescrizioneErrore(java.lang.String descrizioneErrore) {
    this.descrizioneErrore = descrizioneErrore;
  }

  public void set_value_tipoErrore(String value) {
    this.tipoErrore = (TipoErroreType) TipoErroreType.toEnumConstantFromString(value);
  }

  public String get_value_tipoErrore() {
    if(this.tipoErrore == null){
    	return null;
    }else{
    	return this.tipoErrore.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.TipoErroreType getTipoErrore() {
    return this.tipoErrore;
  }

  public void setTipoErrore(org.govmix.proxy.fatturapa.orm.constants.TipoErroreType tipoErrore) {
    this.tipoErrore = tipoErrore;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccRispedizioneModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccRispedizione.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccRispedizione.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccRispedizioneModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccRispedizioneModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccRispedizione.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccRispedizione.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="int")
  @XmlElement(name="maxNumeroTentativi",required=true,nillable=false)
  protected int maxNumeroTentativi;

  @javax.xml.bind.annotation.XmlSchemaType(name="int")
  @XmlElement(name="intervalloTentativi",required=true,nillable=false)
  protected int intervalloTentativi;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codiceErrore",required=true,nillable=false)
  protected java.lang.String codiceErrore;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="abilitato",required=true,nillable=false)
  protected boolean abilitato;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizioneErrore",required=true,nillable=false)
  protected java.lang.String descrizioneErrore;

  @XmlTransient
  protected java.lang.String _value_tipoErrore;

  @XmlElement(name="tipoErrore",required=true,nillable=false)
  protected TipoErroreType tipoErrore;

}
