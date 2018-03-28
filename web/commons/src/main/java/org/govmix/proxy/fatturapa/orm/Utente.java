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
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.constants.UserType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for Utente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Utente">
 * 		&lt;sequence>
 * 			&lt;element name="username" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="password" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="role" type="{http://www.govmix.org/proxy/fatturapa/orm}user-role" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tipo" type="{http://www.govmix.org/proxy/fatturapa/orm}user-type" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="esterno" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="sistema" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="UtenteDipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}UtenteDipartimento" minOccurs="1" maxOccurs="unbounded"/>
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
@XmlType(name = "Utente", 
  propOrder = {
  	"username",
  	"password",
  	"nome",
  	"cognome",
  	"role",
  	"tipo",
  	"esterno",
  	"sistema",
  	"utenteDipartimento"
  }
)

@XmlRootElement(name = "Utente")

public class Utente extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Utente() {
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

  public java.lang.String getNome() {
    return this.nome;
  }

  public void setNome(java.lang.String nome) {
    this.nome = nome;
  }

  public java.lang.String getCognome() {
    return this.cognome;
  }

  public void setCognome(java.lang.String cognome) {
    this.cognome = cognome;
  }

  public void set_value_role(String value) {
    this.role = (UserRole) UserRole.toEnumConstantFromString(value);
  }

  public String get_value_role() {
    if(this.role == null){
    	return null;
    }else{
    	return this.role.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.UserRole getRole() {
    return this.role;
  }

  public void setRole(org.govmix.proxy.fatturapa.orm.constants.UserRole role) {
    this.role = role;
  }

  public void set_value_tipo(String value) {
    this.tipo = (UserType) UserType.toEnumConstantFromString(value);
  }

  public String get_value_tipo() {
    if(this.tipo == null){
    	return null;
    }else{
    	return this.tipo.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.UserType getTipo() {
    return this.tipo;
  }

  public void setTipo(org.govmix.proxy.fatturapa.orm.constants.UserType tipo) {
    this.tipo = tipo;
  }

  public boolean isEsterno() {
    return this.esterno;
  }

  public boolean getEsterno() {
    return this.esterno;
  }

  public void setEsterno(boolean esterno) {
    this.esterno = esterno;
  }

  public java.lang.String getSistema() {
    return this.sistema;
  }

  public void setSistema(java.lang.String sistema) {
    this.sistema = sistema;
  }

  public void addUtenteDipartimento(UtenteDipartimento utenteDipartimento) {
    this.utenteDipartimento.add(utenteDipartimento);
  }

  public UtenteDipartimento getUtenteDipartimento(int index) {
    return this.utenteDipartimento.get( index );
  }

  public UtenteDipartimento removeUtenteDipartimento(int index) {
    return this.utenteDipartimento.remove( index );
  }

  public List<UtenteDipartimento> getUtenteDipartimentoList() {
    return this.utenteDipartimento;
  }

  public void setUtenteDipartimentoList(List<UtenteDipartimento> utenteDipartimento) {
    this.utenteDipartimento=utenteDipartimento;
  }

  public int sizeUtenteDipartimentoList() {
    return this.utenteDipartimento.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.UtenteModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.Utente.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.Utente.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.UtenteModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.UtenteModel model(){
	  if(org.govmix.proxy.fatturapa.orm.Utente.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.Utente.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="username",required=true,nillable=false)
  protected java.lang.String username;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="password",required=true,nillable=false)
  protected java.lang.String password;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nome",required=true,nillable=false)
  protected java.lang.String nome;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cognome",required=true,nillable=false)
  protected java.lang.String cognome;

  @XmlTransient
  protected java.lang.String _value_role;

  @XmlElement(name="role",required=true,nillable=false)
  protected UserRole role;

  @XmlTransient
  protected java.lang.String _value_tipo;

  @XmlElement(name="tipo",required=false,nillable=false)
  protected UserType tipo;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="esterno",required=true,nillable=false)
  protected boolean esterno;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="sistema",required=false,nillable=false)
  protected java.lang.String sistema;

  @XmlElement(name="UtenteDipartimento",required=true,nillable=false)
  protected List<UtenteDipartimento> utenteDipartimento = new ArrayList<UtenteDipartimento>();

  /**
   * @deprecated Use method getUtenteDipartimentoList
   * @return List<UtenteDipartimento>
  */
  @Deprecated
  public List<UtenteDipartimento> getUtenteDipartimento() {
  	return this.utenteDipartimento;
  }

  /**
   * @deprecated Use method setUtenteDipartimentoList
   * @param utenteDipartimento List<UtenteDipartimento>
  */
  @Deprecated
  public void setUtenteDipartimento(List<UtenteDipartimento> utenteDipartimento) {
  	this.utenteDipartimento=utenteDipartimento;
  }

  /**
   * @deprecated Use method sizeUtenteDipartimentoList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeUtenteDipartimento() {
  	return this.utenteDipartimento.size();
  }

}
