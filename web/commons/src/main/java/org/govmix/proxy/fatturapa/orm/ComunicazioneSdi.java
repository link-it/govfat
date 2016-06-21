/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for ComunicazioneSdi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComunicazioneSdi">
 * 		&lt;sequence>
 * 			&lt;element name="identificativoSdi" type="{http://www.govmix.org/proxy/fatturapa/orm}integer" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tipoComunicazione" type="{http://www.govmix.org/proxy/fatturapa/orm}TipoComunicazioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="progressivo" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataRicezione" type="{http://www.govmix.org/proxy/fatturapa/orm}date" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="nomeFile" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="rawData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoConsegna" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoConsegnaType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataConsegna" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dettaglioConsegna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "ComunicazioneSdi", 
  propOrder = {
  	"_decimalWrapper_identificativoSdi",
  	"tipoComunicazione",
  	"progressivo",
  	"dataRicezione",
  	"nomeFile",
  	"contentType",
  	"rawData",
  	"statoConsegna",
  	"dataConsegna",
  	"dettaglioConsegna",
  	"metadato"
  }
)

@XmlRootElement(name = "ComunicazioneSdi")

public class ComunicazioneSdi extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public ComunicazioneSdi() {
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

  public java.util.Date getDataRicezione() {
    return this.dataRicezione;
  }

  public void setDataRicezione(java.util.Date dataRicezione) {
    this.dataRicezione = dataRicezione;
  }

  public java.lang.String getNomeFile() {
    return this.nomeFile;
  }

  public void setNomeFile(java.lang.String nomeFile) {
    this.nomeFile = nomeFile;
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

  public void set_value_statoConsegna(String value) {
    this.statoConsegna = (StatoConsegnaType) StatoConsegnaType.toEnumConstantFromString(value);
  }

  public String get_value_statoConsegna() {
    if(this.statoConsegna == null){
    	return null;
    }else{
    	return this.statoConsegna.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType getStatoConsegna() {
    return this.statoConsegna;
  }

  public void setStatoConsegna(org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType statoConsegna) {
    this.statoConsegna = statoConsegna;
  }

  public java.util.Date getDataConsegna() {
    return this.dataConsegna;
  }

  public void setDataConsegna(java.util.Date dataConsegna) {
    this.dataConsegna = dataConsegna;
  }

  public java.lang.String getDettaglioConsegna() {
    return this.dettaglioConsegna;
  }

  public void setDettaglioConsegna(java.lang.String dettaglioConsegna) {
    this.dettaglioConsegna = dettaglioConsegna;
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

  private static org.govmix.proxy.fatturapa.orm.model.ComunicazioneSdiModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.ComunicazioneSdi.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.ComunicazioneSdi.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.ComunicazioneSdiModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.ComunicazioneSdiModel model(){
	  if(org.govmix.proxy.fatturapa.orm.ComunicazioneSdi.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.ComunicazioneSdi.modelStaticInstance;
  }


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

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="dataRicezione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataRicezione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="nomeFile",required=false,nillable=false)
  protected java.lang.String nomeFile;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="contentType",required=true,nillable=false)
  protected java.lang.String contentType;

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="rawData",required=true,nillable=false)
  protected byte[] rawData;

  @XmlTransient
  protected java.lang.String _value_statoConsegna;

  @XmlElement(name="statoConsegna",required=true,nillable=false)
  protected StatoConsegnaType statoConsegna;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataConsegna",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataConsegna;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="dettaglioConsegna",required=false,nillable=false)
  protected java.lang.String dettaglioConsegna;

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
