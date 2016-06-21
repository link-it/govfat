/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package org.govmix.proxy.fatturapa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for DipartimentoProperty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DipartimentoProperty">
 * 		&lt;sequence>
 * 			&lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idEnte" type="{http://www.govmix.org/proxy/fatturapa}id-ente" minOccurs="1" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DipartimentoProperty", 
  propOrder = {
  	"nome",
  	"label",
  	"idEnte"
  }
)

@XmlRootElement(name = "DipartimentoProperty")

public class DipartimentoProperty extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public DipartimentoProperty() {
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

  public java.lang.String getLabel() {
    return this.label;
  }

  public void setLabel(java.lang.String label) {
    this.label = label;
  }

  public IdEnte getIdEnte() {
    return this.idEnte;
  }

  public void setIdEnte(IdEnte idEnte) {
    this.idEnte = idEnte;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.model.DipartimentoPropertyModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.DipartimentoProperty.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.DipartimentoProperty.modelStaticInstance = new org.govmix.proxy.fatturapa.model.DipartimentoPropertyModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.model.DipartimentoPropertyModel model(){
	  if(org.govmix.proxy.fatturapa.DipartimentoProperty.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.DipartimentoProperty.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nome",required=true,nillable=false)
  protected java.lang.String nome;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="label",required=true,nillable=false)
  protected java.lang.String label;

  @XmlElement(name="idEnte",required=true,nillable=false)
  protected IdEnte idEnte;

}
