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
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import java.io.Serializable;


/** <p>Java class for id-comunicazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="id-comunicazione">
 * 		&lt;sequence>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}integer" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tipoComunicazione" type="{http://www.govmix.org/proxy/fatturapa/orm}TipoComunicazioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="progressivo" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "id-comunicazione", 
  propOrder = {
  	"_decimalWrapper_identificativoSdi",
  	"tipoComunicazione",
  	"progressivo"
  }
)

@XmlRootElement(name = "id-comunicazione")

public class IdComunicazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public IdComunicazione() {
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

  public java.lang.Integer getIdentificativoSdi() {
    if(this._decimalWrapper_identificativoSdi!=null){
		return (java.lang.Integer) this._decimalWrapper_identificativoSdi.getObject(java.lang.Integer.class);
	}else{
		return this.identificativoSdi;
	}
  }

  public void setIdentificativoSdi(java.lang.Integer identificativoSdi) {
    if(identificativoSdi!=null){
		this._decimalWrapper_identificativoSdi = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,12,identificativoSdi);
	}
  }

  public void set_value_tipoComunicazione(String value) {
    this.tipoComunicazione = (TipoComunicazioneType) TipoComunicazioneType.toEnumConstantFromString(value);
  }

  public String get_value_tipoComunicazione() {
    if(this.tipoComunicazione == null){
    	return null;
    }else{
    	return this.tipoComunicazione.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType getTipoComunicazione() {
    return this.tipoComunicazione;
  }

  public void setTipoComunicazione(org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType tipoComunicazione) {
    this.tipoComunicazione = tipoComunicazione;
  }

  public java.lang.Integer getProgressivo() {
    return this.progressivo;
  }

  public void setProgressivo(java.lang.Integer progressivo) {
    this.progressivo = progressivo;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="integer")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Integer identificativoSdi;

  @XmlTransient
  protected java.lang.String _value_tipoComunicazione;

  @XmlElement(name="tipoComunicazione",required=true,nillable=false)
  protected TipoComunicazioneType tipoComunicazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="positiveInteger")
  @XmlElement(name="progressivo",required=true,nillable=false)
  protected java.lang.Integer progressivo;

}
