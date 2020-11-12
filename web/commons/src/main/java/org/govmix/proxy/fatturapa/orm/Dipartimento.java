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
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for Dipartimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dipartimento">
 * 		&lt;sequence>
 * 			&lt;element name="codice" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="ente" type="{http://www.govmix.org/proxy/fatturapa/orm}id-ente" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="fatturazioneAttiva" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="idProcedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idProcedimentoB2B" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="firmaAutomatica" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="accettazioneAutomatica" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="modalitaPush" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="true"/>
 * 			&lt;element name="registro" type="{http://www.govmix.org/proxy/fatturapa/orm}id-registro" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="listaEmailNotifiche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="DipartimentoPropertyValue" type="{http://www.govmix.org/proxy/fatturapa/orm}DipartimentoPropertyValue" minOccurs="0" maxOccurs="unbounded"/>
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
@XmlType(name = "Dipartimento", 
  propOrder = {
  	"codice",
  	"ente",
  	"descrizione",
  	"fatturazioneAttiva",
  	"idProcedimento",
  	"idProcedimentoB2B",
  	"firmaAutomatica",
  	"accettazioneAutomatica",
  	"modalitaPush",
  	"registro",
  	"listaEmailNotifiche",
  	"dipartimentoPropertyValue"
  }
)

@XmlRootElement(name = "Dipartimento")

public class Dipartimento extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Dipartimento() {
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

  public java.lang.String getCodice() {
    return this.codice;
  }

  public void setCodice(java.lang.String codice) {
    this.codice = codice;
  }

  public IdEnte getEnte() {
    return this.ente;
  }

  public void setEnte(IdEnte ente) {
    this.ente = ente;
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public boolean isFatturazioneAttiva() {
    return this.fatturazioneAttiva;
  }

  public boolean getFatturazioneAttiva() {
    return this.fatturazioneAttiva;
  }

  public void setFatturazioneAttiva(boolean fatturazioneAttiva) {
    this.fatturazioneAttiva = fatturazioneAttiva;
  }

  public java.lang.String getIdProcedimento() {
    return this.idProcedimento;
  }

  public void setIdProcedimento(java.lang.String idProcedimento) {
    this.idProcedimento = idProcedimento;
  }

  public java.lang.String getIdProcedimentoB2B() {
    return this.idProcedimentoB2B;
  }

  public void setIdProcedimentoB2B(java.lang.String idProcedimentoB2B) {
    this.idProcedimentoB2B = idProcedimentoB2B;
  }

  public boolean isFirmaAutomatica() {
    return this.firmaAutomatica;
  }

  public boolean getFirmaAutomatica() {
    return this.firmaAutomatica;
  }

  public void setFirmaAutomatica(boolean firmaAutomatica) {
    this.firmaAutomatica = firmaAutomatica;
  }

  public boolean isAccettazioneAutomatica() {
    return this.accettazioneAutomatica;
  }

  public boolean getAccettazioneAutomatica() {
    return this.accettazioneAutomatica;
  }

  public void setAccettazioneAutomatica(boolean accettazioneAutomatica) {
    this.accettazioneAutomatica = accettazioneAutomatica;
  }

  public boolean isModalitaPush() {
    return this.modalitaPush;
  }

  public boolean getModalitaPush() {
    return this.modalitaPush;
  }

  public void setModalitaPush(boolean modalitaPush) {
    this.modalitaPush = modalitaPush;
  }

  public IdRegistro getRegistro() {
    return this.registro;
  }

  public void setRegistro(IdRegistro registro) {
    this.registro = registro;
  }

  public java.lang.String getListaEmailNotifiche() {
    return this.listaEmailNotifiche;
  }

  public void setListaEmailNotifiche(java.lang.String listaEmailNotifiche) {
    this.listaEmailNotifiche = listaEmailNotifiche;
  }

  public void addDipartimentoPropertyValue(DipartimentoPropertyValue dipartimentoPropertyValue) {
    this.dipartimentoPropertyValue.add(dipartimentoPropertyValue);
  }

  public DipartimentoPropertyValue getDipartimentoPropertyValue(int index) {
    return this.dipartimentoPropertyValue.get( index );
  }

  public DipartimentoPropertyValue removeDipartimentoPropertyValue(int index) {
    return this.dipartimentoPropertyValue.remove( index );
  }

  public List<DipartimentoPropertyValue> getDipartimentoPropertyValueList() {
    return this.dipartimentoPropertyValue;
  }

  public void setDipartimentoPropertyValueList(List<DipartimentoPropertyValue> dipartimentoPropertyValue) {
    this.dipartimentoPropertyValue=dipartimentoPropertyValue;
  }

  public int sizeDipartimentoPropertyValueList() {
    return this.dipartimentoPropertyValue.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.DipartimentoModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.Dipartimento.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.Dipartimento.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.DipartimentoModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.DipartimentoModel model(){
	  if(org.govmix.proxy.fatturapa.orm.Dipartimento.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.Dipartimento.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codice",required=true,nillable=false)
  protected java.lang.String codice;

  @XmlElement(name="ente",required=true,nillable=false)
  protected IdEnte ente;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizione",required=true,nillable=false)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="fatturazioneAttiva",required=true,nillable=false,defaultValue="false")
  protected boolean fatturazioneAttiva = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idProcedimento",required=false,nillable=false)
  protected java.lang.String idProcedimento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idProcedimentoB2B",required=false,nillable=false)
  protected java.lang.String idProcedimentoB2B;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="firmaAutomatica",required=true,nillable=false,defaultValue="false")
  protected boolean firmaAutomatica = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="accettazioneAutomatica",required=true,nillable=false,defaultValue="false")
  protected boolean accettazioneAutomatica = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="modalitaPush",required=true,nillable=false,defaultValue="true")
  protected boolean modalitaPush = true;

  @XmlElement(name="registro",required=false,nillable=false)
  protected IdRegistro registro;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="listaEmailNotifiche",required=false,nillable=false)
  protected java.lang.String listaEmailNotifiche;

  @XmlElement(name="DipartimentoPropertyValue",required=true,nillable=false)
  protected List<DipartimentoPropertyValue> dipartimentoPropertyValue = new ArrayList<DipartimentoPropertyValue>();

  /**
   * @deprecated Use method getDipartimentoPropertyValueList
   * @return List<DipartimentoPropertyValue>
  */
  @Deprecated
  public List<DipartimentoPropertyValue> getDipartimentoPropertyValue() {
  	return this.dipartimentoPropertyValue;
  }

  /**
   * @deprecated Use method setDipartimentoPropertyValueList
   * @param dipartimentoPropertyValue List<DipartimentoPropertyValue>
  */
  @Deprecated
  public void setDipartimentoPropertyValue(List<DipartimentoPropertyValue> dipartimentoPropertyValue) {
  	this.dipartimentoPropertyValue=dipartimentoPropertyValue;
  }

  /**
   * @deprecated Use method sizeDipartimentoPropertyValueList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeDipartimentoPropertyValue() {
  	return this.dipartimentoPropertyValue.size();
  }

}
