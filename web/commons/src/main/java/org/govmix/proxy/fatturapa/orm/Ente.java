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


/** <p>Java class for Ente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ente">
 * 		&lt;sequence>
 * 			&lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idPccAmministrazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="cfAuth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="enteVersatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="strutturaVersatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="nodoCodicePagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="prefissoCodicePagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "Ente", 
  propOrder = {
  	"nome",
  	"idPccAmministrazione",
  	"cfAuth",
  	"descrizione",
  	"enteVersatore",
  	"strutturaVersatore",
  	"nodoCodicePagamento",
  	"prefissoCodicePagamento"
  }
)

@XmlRootElement(name = "Ente")

public class Ente extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Ente() {
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

  public java.lang.String getNome() {
    return this.nome;
  }

  public void setNome(java.lang.String nome) {
    this.nome = nome;
  }

  public java.lang.String getIdPccAmministrazione() {
    return this.idPccAmministrazione;
  }

  public void setIdPccAmministrazione(java.lang.String idPccAmministrazione) {
    this.idPccAmministrazione = idPccAmministrazione;
  }

  public java.lang.String getCfAuth() {
    return this.cfAuth;
  }

  public void setCfAuth(java.lang.String cfAuth) {
    this.cfAuth = cfAuth;
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public java.lang.String getEnteVersatore() {
    return this.enteVersatore;
  }

  public void setEnteVersatore(java.lang.String enteVersatore) {
    this.enteVersatore = enteVersatore;
  }

  public java.lang.String getStrutturaVersatore() {
    return this.strutturaVersatore;
  }

  public void setStrutturaVersatore(java.lang.String strutturaVersatore) {
    this.strutturaVersatore = strutturaVersatore;
  }

  public java.lang.String getNodoCodicePagamento() {
    return this.nodoCodicePagamento;
  }

  public void setNodoCodicePagamento(java.lang.String nodoCodicePagamento) {
    this.nodoCodicePagamento = nodoCodicePagamento;
  }

  public java.lang.String getPrefissoCodicePagamento() {
    return this.prefissoCodicePagamento;
  }

  public void setPrefissoCodicePagamento(java.lang.String prefissoCodicePagamento) {
    this.prefissoCodicePagamento = prefissoCodicePagamento;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.EnteModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.Ente.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.Ente.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.EnteModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.EnteModel model(){
	  if(org.govmix.proxy.fatturapa.orm.Ente.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.Ente.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nome",required=true,nillable=false)
  protected java.lang.String nome;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idPccAmministrazione",required=false,nillable=false)
  protected java.lang.String idPccAmministrazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cfAuth",required=false,nillable=false)
  protected java.lang.String cfAuth;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizione",required=false,nillable=false)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="enteVersatore",required=false,nillable=false)
  protected java.lang.String enteVersatore;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="strutturaVersatore",required=false,nillable=false)
  protected java.lang.String strutturaVersatore;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nodoCodicePagamento",required=false,nillable=false)
  protected java.lang.String nodoCodicePagamento;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="prefissoCodicePagamento",required=false,nillable=false)
  protected java.lang.String prefissoCodicePagamento;

}
