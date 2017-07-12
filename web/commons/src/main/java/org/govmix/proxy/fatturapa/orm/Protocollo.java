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


/** <p>Java class for Protocollo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Protocollo">
 * 		&lt;sequence>
 * 			&lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="endpoint" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="endpointConsegnaLotto" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="endpointRichiediProtocollo" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "Protocollo", 
  propOrder = {
  	"nome",
  	"descrizione",
  	"endpoint",
  	"endpointConsegnaLotto",
  	"endpointRichiediProtocollo"
  }
)

@XmlRootElement(name = "Protocollo")

public class Protocollo extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Protocollo() {
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

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public java.net.URI getEndpoint() {
    return this.endpoint;
  }

  public void setEndpoint(java.net.URI endpoint) {
    this.endpoint = endpoint;
  }

  public java.net.URI getEndpointConsegnaLotto() {
    return this.endpointConsegnaLotto;
  }

  public void setEndpointConsegnaLotto(java.net.URI endpointConsegnaLotto) {
    this.endpointConsegnaLotto = endpointConsegnaLotto;
  }

  public java.net.URI getEndpointRichiediProtocollo() {
    return this.endpointRichiediProtocollo;
  }

  public void setEndpointRichiediProtocollo(java.net.URI endpointRichiediProtocollo) {
    this.endpointRichiediProtocollo = endpointRichiediProtocollo;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.ProtocolloModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.Protocollo.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.Protocollo.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.ProtocolloModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.ProtocolloModel model(){
	  if(org.govmix.proxy.fatturapa.orm.Protocollo.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.Protocollo.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nome",required=true,nillable=false)
  protected java.lang.String nome;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizione",required=false,nillable=false)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="anyURI")
  @XmlElement(name="endpoint",required=true,nillable=false)
  protected java.net.URI endpoint;

  @javax.xml.bind.annotation.XmlSchemaType(name="anyURI")
  @XmlElement(name="endpointConsegnaLotto",required=false,nillable=false)
  protected java.net.URI endpointConsegnaLotto;

  @javax.xml.bind.annotation.XmlSchemaType(name="anyURI")
  @XmlElement(name="endpointRichiediProtocollo",required=false,nillable=false)
  protected java.net.URI endpointRichiediProtocollo;

}
