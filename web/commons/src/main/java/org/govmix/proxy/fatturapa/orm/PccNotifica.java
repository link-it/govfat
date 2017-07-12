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
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import java.io.Serializable;


/** <p>Java class for PccNotifica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccNotifica">
 * 		&lt;sequence>
 * 			&lt;element name="idTraccia" type="{http://www.govmix.org/proxy/fatturapa/orm}id-traccia" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idDipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}id-dipartimento" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoConsegna" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoConsegnaType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataCreazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataConsegna" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="PccTraccia" type="{http://www.govmix.org/proxy/fatturapa/orm}PccTraccia" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="Dipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}Dipartimento" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "PccNotifica", 
  propOrder = {
  	"idTraccia",
  	"idDipartimento",
  	"statoConsegna",
  	"dataCreazione",
  	"dataConsegna",
  	"pccTraccia",
  	"dipartimento"
  }
)

@XmlRootElement(name = "PccNotifica")

public class PccNotifica extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccNotifica() {
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

  public IdTraccia getIdTraccia() {
    return this.idTraccia;
  }

  public void setIdTraccia(IdTraccia idTraccia) {
    this.idTraccia = idTraccia;
  }

  public IdDipartimento getIdDipartimento() {
    return this.idDipartimento;
  }

  public void setIdDipartimento(IdDipartimento idDipartimento) {
    this.idDipartimento = idDipartimento;
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

  public java.util.Date getDataCreazione() {
    return this.dataCreazione;
  }

  public void setDataCreazione(java.util.Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public java.util.Date getDataConsegna() {
    return this.dataConsegna;
  }

  public void setDataConsegna(java.util.Date dataConsegna) {
    this.dataConsegna = dataConsegna;
  }

  public PccTraccia getPccTraccia() {
    return this.pccTraccia;
  }

  public void setPccTraccia(PccTraccia pccTraccia) {
    this.pccTraccia = pccTraccia;
  }

  public Dipartimento getDipartimento() {
    return this.dipartimento;
  }

  public void setDipartimento(Dipartimento dipartimento) {
    this.dipartimento = dipartimento;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccNotificaModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccNotifica.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccNotifica.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccNotificaModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccNotificaModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccNotifica.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccNotifica.modelStaticInstance;
  }


  @XmlElement(name="idTraccia",required=true,nillable=false)
  protected IdTraccia idTraccia;

  @XmlElement(name="idDipartimento",required=true,nillable=false)
  protected IdDipartimento idDipartimento;

  @XmlTransient
  protected java.lang.String _value_statoConsegna;

  @XmlElement(name="statoConsegna",required=true,nillable=false)
  protected StatoConsegnaType statoConsegna;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataCreazione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataCreazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataConsegna",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataConsegna;

  @XmlElement(name="PccTraccia",required=false,nillable=false)
  protected PccTraccia pccTraccia;

  @XmlElement(name="Dipartimento",required=false,nillable=false)
  protected Dipartimento dipartimento;

}
