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
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for TracciaSDI complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TracciaSDI">
 * 		&lt;sequence>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}integer" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="numeroFattura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tipoComunicazione" type="{http://www.govmix.org/proxy/fatturapa/orm}TipoComunicazioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nomeFile" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idEgov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="rawData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="statoProtocollazione" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoProtocollazioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataProtocollazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataProssimaProtocollazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tentativiProtocollazione" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dettaglioProtocollazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="Metadato" type="{http://www.govmix.org/proxy/fatturapa/orm}Metadato" minOccurs="0" maxOccurs="unbounded"/>
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
@XmlType(name = "TracciaSDI", 
  propOrder = {
  	"_decimalWrapper_identificativoSdi",
  	"numeroFattura",
  	"tipoComunicazione",
  	"nomeFile",
  	"data",
  	"idEgov",
  	"contentType",
  	"rawData",
  	"statoProtocollazione",
  	"dataProtocollazione",
  	"dataProssimaProtocollazione",
  	"tentativiProtocollazione",
  	"dettaglioProtocollazione",
  	"metadato"
  }
)

@XmlRootElement(name = "TracciaSDI")

public class TracciaSDI extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public TracciaSDI() {
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

  public java.lang.String getNumeroFattura() {
    return this.numeroFattura;
  }

  public void setNumeroFattura(java.lang.String numeroFattura) {
    this.numeroFattura = numeroFattura;
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

  public java.lang.String getNomeFile() {
    return this.nomeFile;
  }

  public void setNomeFile(java.lang.String nomeFile) {
    this.nomeFile = nomeFile;
  }

  public java.util.Date getData() {
    return this.data;
  }

  public void setData(java.util.Date data) {
    this.data = data;
  }

  public java.lang.String getIdEgov() {
    return this.idEgov;
  }

  public void setIdEgov(java.lang.String idEgov) {
    this.idEgov = idEgov;
  }

  public java.lang.String getContentType() {
    return this.contentType;
  }

  public void setContentType(java.lang.String contentType) {
    this.contentType = contentType;
  }

  public byte[] getRawData() {
    return this.rawData;
  }

  public void setRawData(byte[] rawData) {
    this.rawData = rawData;
  }

  public void set_value_statoProtocollazione(String value) {
    this.statoProtocollazione = (StatoProtocollazioneType) StatoProtocollazioneType.toEnumConstantFromString(value);
  }

  public String get_value_statoProtocollazione() {
    if(this.statoProtocollazione == null){
    	return null;
    }else{
    	return this.statoProtocollazione.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType getStatoProtocollazione() {
    return this.statoProtocollazione;
  }

  public void setStatoProtocollazione(org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType statoProtocollazione) {
    this.statoProtocollazione = statoProtocollazione;
  }

  public java.util.Date getDataProtocollazione() {
    return this.dataProtocollazione;
  }

  public void setDataProtocollazione(java.util.Date dataProtocollazione) {
    this.dataProtocollazione = dataProtocollazione;
  }

  public java.util.Date getDataProssimaProtocollazione() {
    return this.dataProssimaProtocollazione;
  }

  public void setDataProssimaProtocollazione(java.util.Date dataProssimaProtocollazione) {
    this.dataProssimaProtocollazione = dataProssimaProtocollazione;
  }

  public java.lang.Integer getTentativiProtocollazione() {
    return this.tentativiProtocollazione;
  }

  public void setTentativiProtocollazione(java.lang.Integer tentativiProtocollazione) {
    this.tentativiProtocollazione = tentativiProtocollazione;
  }

  public java.lang.String getDettaglioProtocollazione() {
    return this.dettaglioProtocollazione;
  }

  public void setDettaglioProtocollazione(java.lang.String dettaglioProtocollazione) {
    this.dettaglioProtocollazione = dettaglioProtocollazione;
  }

  public void addMetadato(Metadato metadato) {
    this.metadato.add(metadato);
  }

  public Metadato getMetadato(int index) {
    return this.metadato.get( index );
  }

  public Metadato removeMetadato(int index) {
    return this.metadato.remove( index );
  }

  public List<Metadato> getMetadatoList() {
    return this.metadato;
  }

  public void setMetadatoList(List<Metadato> metadato) {
    this.metadato=metadato;
  }

  public int sizeMetadatoList() {
    return this.metadato.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.TracciaSDIModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.TracciaSDI.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.TracciaSDI.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.TracciaSDIModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.TracciaSDIModel model(){
	  if(org.govmix.proxy.fatturapa.orm.TracciaSDI.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.TracciaSDI.modelStaticInstance;
  }


  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="integer")
  @XmlElement(name="identificativoSdi",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_identificativoSdi = null;

  @XmlTransient
  protected java.lang.Integer identificativoSdi;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="numeroFattura",required=false,nillable=false)
  protected java.lang.String numeroFattura;

  @XmlTransient
  protected java.lang.String _value_tipoComunicazione;

  @XmlElement(name="tipoComunicazione",required=true,nillable=false)
  protected TipoComunicazioneType tipoComunicazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nomeFile",required=true,nillable=false)
  protected java.lang.String nomeFile;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="data",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date data;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idEgov",required=true,nillable=false)
  protected java.lang.String idEgov;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="contentType",required=true,nillable=false)
  protected java.lang.String contentType;

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="rawData",required=false,nillable=false)
  protected byte[] rawData;

  @XmlTransient
  protected java.lang.String _value_statoProtocollazione;

  @XmlElement(name="statoProtocollazione",required=true,nillable=false)
  protected StatoProtocollazioneType statoProtocollazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataProtocollazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataProtocollazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataProssimaProtocollazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataProssimaProtocollazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="nonNegativeInteger")
  @XmlElement(name="tentativiProtocollazione",required=true,nillable=false)
  protected java.lang.Integer tentativiProtocollazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="dettaglioProtocollazione",required=false,nillable=false)
  protected java.lang.String dettaglioProtocollazione;

  @XmlElement(name="Metadato",required=true,nillable=false)
  protected List<Metadato> metadato = new ArrayList<Metadato>();

  /**
   * @deprecated Use method getMetadatoList
   * @return List<Metadato>
  */
  @Deprecated
  public List<Metadato> getMetadato() {
  	return this.metadato;
  }

  /**
   * @deprecated Use method setMetadatoList
   * @param metadato List<Metadato>
  */
  @Deprecated
  public void setMetadato(List<Metadato> metadato) {
  	this.metadato=metadato;
  }

  /**
   * @deprecated Use method sizeMetadatoList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeMetadato() {
  	return this.metadato.size();
  }

}
