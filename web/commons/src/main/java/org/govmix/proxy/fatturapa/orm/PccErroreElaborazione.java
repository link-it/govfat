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
import org.govmix.proxy.fatturapa.orm.constants.TipoOperazioneType;
import java.io.Serializable;


/** <p>Java class for PccErroreElaborazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccErroreElaborazione">
 * 		&lt;sequence>
 * 			&lt;element name="idEsito" type="{http://www.govmix.org/proxy/fatturapa/orm}id-trasmissione-esito" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tipoOperazione" type="{http://www.govmix.org/proxy/fatturapa/orm}TipoOperazioneType" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="progressivoOperazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codiceEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizioneEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "PccErroreElaborazione", 
  propOrder = {
  	"idEsito",
  	"tipoOperazione",
  	"progressivoOperazione",
  	"codiceEsito",
  	"descrizioneEsito"
  }
)

@XmlRootElement(name = "PccErroreElaborazione")

public class PccErroreElaborazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccErroreElaborazione() {
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

  public IdTrasmissioneEsito getIdEsito() {
    return this.idEsito;
  }

  public void setIdEsito(IdTrasmissioneEsito idEsito) {
    this.idEsito = idEsito;
  }

  public void set_value_tipoOperazione(String value) {
    this.tipoOperazione = (TipoOperazioneType) TipoOperazioneType.toEnumConstantFromString(value);
  }

  public String get_value_tipoOperazione() {
    if(this.tipoOperazione == null){
    	return null;
    }else{
    	return this.tipoOperazione.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.TipoOperazioneType getTipoOperazione() {
    return this.tipoOperazione;
  }

  public void setTipoOperazione(org.govmix.proxy.fatturapa.orm.constants.TipoOperazioneType tipoOperazione) {
    this.tipoOperazione = tipoOperazione;
  }

  public int getProgressivoOperazione() {
    return this.progressivoOperazione;
  }

  public void setProgressivoOperazione(int progressivoOperazione) {
    this.progressivoOperazione = progressivoOperazione;
  }

  public java.lang.String getCodiceEsito() {
    return this.codiceEsito;
  }

  public void setCodiceEsito(java.lang.String codiceEsito) {
    this.codiceEsito = codiceEsito;
  }

  public java.lang.String getDescrizioneEsito() {
    return this.descrizioneEsito;
  }

  public void setDescrizioneEsito(java.lang.String descrizioneEsito) {
    this.descrizioneEsito = descrizioneEsito;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccErroreElaborazioneModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccErroreElaborazione.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccErroreElaborazione.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccErroreElaborazioneModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccErroreElaborazioneModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccErroreElaborazione.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccErroreElaborazione.modelStaticInstance;
  }


  @XmlElement(name="idEsito",required=true,nillable=false)
  protected IdTrasmissioneEsito idEsito;

  @XmlTransient
  protected java.lang.String _value_tipoOperazione;

  @XmlElement(name="tipoOperazione",required=false,nillable=false)
  protected TipoOperazioneType tipoOperazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="int")
  @XmlElement(name="progressivoOperazione",required=false,nillable=false)
  protected int progressivoOperazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codiceEsito",required=true,nillable=false)
  protected java.lang.String codiceEsito;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizioneEsito",required=false,nillable=false)
  protected java.lang.String descrizioneEsito;

}
