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


/** <p>Java class for PccScadenza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccScadenza">
 * 		&lt;sequence>
 * 			&lt;element name="importoInScadenza" type="{http://www.govmix.org/proxy/fatturapa/orm}decimal" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="importoIniziale" type="{http://www.govmix.org/proxy/fatturapa/orm}decimal" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="pagatoRicontabilizzato" type="{http://www.govmix.org/proxy/fatturapa/orm}decimal" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idFattura" type="{http://www.govmix.org/proxy/fatturapa/orm}id-fattura" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="sistemaRichiedente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="utenteRichiedente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataRichiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataQuery" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "PccScadenza", 
  propOrder = {
  	"_decimalWrapper_importoInScadenza",
  	"_decimalWrapper_importoIniziale",
  	"_decimalWrapper_pagatoRicontabilizzato",
  	"dataScadenza",
  	"idFattura",
  	"sistemaRichiedente",
  	"utenteRichiedente",
  	"dataRichiesta",
  	"dataQuery"
  }
)

@XmlRootElement(name = "PccScadenza")

public class PccScadenza extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccScadenza() {
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

  public java.lang.Double getImportoInScadenza() {
    if(this._decimalWrapper_importoInScadenza!=null){
		return (java.lang.Double) this._decimalWrapper_importoInScadenza.getObject(java.lang.Double.class);
	}else{
		return this.importoInScadenza;
	}
  }

  public void setImportoInScadenza(java.lang.Double importoInScadenza) {
    if(importoInScadenza!=null){
		this._decimalWrapper_importoInScadenza = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,11,2,2,importoInScadenza);
	}
  }

  public java.lang.Double getImportoIniziale() {
    if(this._decimalWrapper_importoIniziale!=null){
		return (java.lang.Double) this._decimalWrapper_importoIniziale.getObject(java.lang.Double.class);
	}else{
		return this.importoIniziale;
	}
  }

  public void setImportoIniziale(java.lang.Double importoIniziale) {
    if(importoIniziale!=null){
		this._decimalWrapper_importoIniziale = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,11,2,2,importoIniziale);
	}
  }

  public java.lang.Double getPagatoRicontabilizzato() {
    if(this._decimalWrapper_pagatoRicontabilizzato!=null){
		return (java.lang.Double) this._decimalWrapper_pagatoRicontabilizzato.getObject(java.lang.Double.class);
	}else{
		return this.pagatoRicontabilizzato;
	}
  }

  public void setPagatoRicontabilizzato(java.lang.Double pagatoRicontabilizzato) {
    if(pagatoRicontabilizzato!=null){
		this._decimalWrapper_pagatoRicontabilizzato = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,11,2,2,pagatoRicontabilizzato);
	}
  }

  public java.util.Date getDataScadenza() {
    return this.dataScadenza;
  }

  public void setDataScadenza(java.util.Date dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public IdFattura getIdFattura() {
    return this.idFattura;
  }

  public void setIdFattura(IdFattura idFattura) {
    this.idFattura = idFattura;
  }

  public java.lang.String getSistemaRichiedente() {
    return this.sistemaRichiedente;
  }

  public void setSistemaRichiedente(java.lang.String sistemaRichiedente) {
    this.sistemaRichiedente = sistemaRichiedente;
  }

  public java.lang.String getUtenteRichiedente() {
    return this.utenteRichiedente;
  }

  public void setUtenteRichiedente(java.lang.String utenteRichiedente) {
    this.utenteRichiedente = utenteRichiedente;
  }

  public java.util.Date getDataRichiesta() {
    return this.dataRichiesta;
  }

  public void setDataRichiesta(java.util.Date dataRichiesta) {
    this.dataRichiesta = dataRichiesta;
  }

  public java.util.Date getDataQuery() {
    return this.dataQuery;
  }

  public void setDataQuery(java.util.Date dataQuery) {
    this.dataQuery = dataQuery;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccScadenzaModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccScadenza.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccScadenza.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccScadenzaModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccScadenzaModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccScadenza.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccScadenza.modelStaticInstance;
  }


  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="importoInScadenza",required=false,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_importoInScadenza = null;

  @XmlTransient
  protected java.lang.Double importoInScadenza;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="importoIniziale",required=false,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_importoIniziale = null;

  @XmlTransient
  protected java.lang.Double importoIniziale;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="pagatoRicontabilizzato",required=false,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_pagatoRicontabilizzato = null;

  @XmlTransient
  protected java.lang.Double pagatoRicontabilizzato;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="dataScadenza",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataScadenza;

  @XmlElement(name="idFattura",required=true,nillable=false)
  protected IdFattura idFattura;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="sistemaRichiedente",required=true,nillable=false)
  protected java.lang.String sistemaRichiedente;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="utenteRichiedente",required=true,nillable=false)
  protected java.lang.String utenteRichiedente;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataRichiesta",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataRichiesta;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataQuery",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataQuery;

}
