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
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for Registro complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Registro">
 * 		&lt;sequence>
 * 			&lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="username" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="password" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idProtocollo" type="{http://www.govmix.org/proxy/fatturapa/orm}id-protocollo" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="RegistroPropertyValue" type="{http://www.govmix.org/proxy/fatturapa/orm}RegistroPropertyValue" minOccurs="0" maxOccurs="unbounded"/>
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
@XmlType(name = "Registro", 
  propOrder = {
  	"nome",
  	"username",
  	"password",
  	"idProtocollo",
  	"registroPropertyValue"
  }
)

@XmlRootElement(name = "Registro")

public class Registro extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Registro() {
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

  public java.lang.String getUsername() {
    return this.username;
  }

  public void setUsername(java.lang.String username) {
    this.username = username;
  }

  public java.lang.String getPassword() {
    return this.password;
  }

  public void setPassword(java.lang.String password) {
    this.password = password;
  }

  public IdProtocollo getIdProtocollo() {
    return this.idProtocollo;
  }

  public void setIdProtocollo(IdProtocollo idProtocollo) {
    this.idProtocollo = idProtocollo;
  }

  public void addRegistroPropertyValue(RegistroPropertyValue registroPropertyValue) {
    this.registroPropertyValue.add(registroPropertyValue);
  }

  public RegistroPropertyValue getRegistroPropertyValue(int index) {
    return this.registroPropertyValue.get( index );
  }

  public RegistroPropertyValue removeRegistroPropertyValue(int index) {
    return this.registroPropertyValue.remove( index );
  }

  public List<RegistroPropertyValue> getRegistroPropertyValueList() {
    return this.registroPropertyValue;
  }

  public void setRegistroPropertyValueList(List<RegistroPropertyValue> registroPropertyValue) {
    this.registroPropertyValue=registroPropertyValue;
  }

  public int sizeRegistroPropertyValueList() {
    return this.registroPropertyValue.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.RegistroModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.Registro.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.Registro.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.RegistroModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.RegistroModel model(){
	  if(org.govmix.proxy.fatturapa.orm.Registro.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.Registro.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nome",required=true,nillable=false)
  protected java.lang.String nome;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="username",required=true,nillable=false)
  protected java.lang.String username;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="password",required=true,nillable=false)
  protected java.lang.String password;

  @XmlElement(name="idProtocollo",required=true,nillable=false)
  protected IdProtocollo idProtocollo;

  @XmlElement(name="RegistroPropertyValue",required=true,nillable=false)
  protected List<RegistroPropertyValue> registroPropertyValue = new ArrayList<RegistroPropertyValue>();

  /**
   * @deprecated Use method getRegistroPropertyValueList
   * @return List<RegistroPropertyValue>
  */
  @Deprecated
  public List<RegistroPropertyValue> getRegistroPropertyValue() {
  	return this.registroPropertyValue;
  }

  /**
   * @deprecated Use method setRegistroPropertyValueList
   * @param registroPropertyValue List<RegistroPropertyValue>
  */
  @Deprecated
  public void setRegistroPropertyValue(List<RegistroPropertyValue> registroPropertyValue) {
  	this.registroPropertyValue=registroPropertyValue;
  }

  /**
   * @deprecated Use method sizeRegistroPropertyValueList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeRegistroPropertyValue() {
  	return this.registroPropertyValue.size();
  }

}
