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
import java.io.Serializable;


/** <p>Java class for id-contabilizzazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="id-contabilizzazione">
 * 		&lt;sequence>
 * 			&lt;element name="idImporto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="sistemaRichiedente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idFattura" type="{http://www.govmix.org/proxy/fatturapa/orm}id-fattura" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "id-contabilizzazione", 
  propOrder = {
  	"idImporto",
  	"sistemaRichiedente",
  	"idFattura"
  }
)

@XmlRootElement(name = "id-contabilizzazione")

public class IdContabilizzazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public IdContabilizzazione() {
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

  public java.lang.String getIdImporto() {
    return this.idImporto;
  }

  public void setIdImporto(java.lang.String idImporto) {
    this.idImporto = idImporto;
  }

  public java.lang.String getSistemaRichiedente() {
    return this.sistemaRichiedente;
  }

  public void setSistemaRichiedente(java.lang.String sistemaRichiedente) {
    this.sistemaRichiedente = sistemaRichiedente;
  }

  public IdFattura getIdFattura() {
    return this.idFattura;
  }

  public void setIdFattura(IdFattura idFattura) {
    this.idFattura = idFattura;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idImporto",required=true,nillable=false)
  protected java.lang.String idImporto;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="sistemaRichiedente",required=true,nillable=false)
  protected java.lang.String sistemaRichiedente;

  @XmlElement(name="idFattura",required=true,nillable=false)
  protected IdFattura idFattura;

}
