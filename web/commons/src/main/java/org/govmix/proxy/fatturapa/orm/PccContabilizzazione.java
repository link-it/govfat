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
import org.govmix.proxy.fatturapa.orm.constants.CausaleType;
import org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoDebitoType;
import java.io.Serializable;


/** <p>Java class for PccContabilizzazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccContabilizzazione">
 * 		&lt;sequence>
 * 			&lt;element name="importoMovimento" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="naturaSpesa" type="{http://www.govmix.org/proxy/fatturapa/orm}NaturaSpesaType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="capitoliSpesa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="statoDebito" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoDebitoType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="causale" type="{http://www.govmix.org/proxy/fatturapa/orm}CausaleType" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="estremiImpegno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="CodiceCig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="CodiceCup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idImporto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "PccContabilizzazione", 
  propOrder = {
  	"importoMovimento",
  	"naturaSpesa",
  	"capitoliSpesa",
  	"statoDebito",
  	"causale",
  	"descrizione",
  	"estremiImpegno",
  	"codiceCig",
  	"codiceCup",
  	"idImporto",
  	"idFattura",
  	"sistemaRichiedente",
  	"utenteRichiedente",
  	"dataRichiesta",
  	"dataQuery"
  }
)

@XmlRootElement(name = "PccContabilizzazione")

public class PccContabilizzazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccContabilizzazione() {
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

  public double getImportoMovimento() {
    return this.importoMovimento;
  }

  public void setImportoMovimento(double importoMovimento) {
    this.importoMovimento = importoMovimento;
  }

  public void set_value_naturaSpesa(String value) {
    this.naturaSpesa = (NaturaSpesaType) NaturaSpesaType.toEnumConstantFromString(value);
  }

  public String get_value_naturaSpesa() {
    if(this.naturaSpesa == null){
    	return null;
    }else{
    	return this.naturaSpesa.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType getNaturaSpesa() {
    return this.naturaSpesa;
  }

  public void setNaturaSpesa(org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType naturaSpesa) {
    this.naturaSpesa = naturaSpesa;
  }

  public java.lang.String getCapitoliSpesa() {
    return this.capitoliSpesa;
  }

  public void setCapitoliSpesa(java.lang.String capitoliSpesa) {
    this.capitoliSpesa = capitoliSpesa;
  }

  public void set_value_statoDebito(String value) {
    this.statoDebito = (StatoDebitoType) StatoDebitoType.toEnumConstantFromString(value);
  }

  public String get_value_statoDebito() {
    if(this.statoDebito == null){
    	return null;
    }else{
    	return this.statoDebito.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoDebitoType getStatoDebito() {
    return this.statoDebito;
  }

  public void setStatoDebito(org.govmix.proxy.fatturapa.orm.constants.StatoDebitoType statoDebito) {
    this.statoDebito = statoDebito;
  }

  public void set_value_causale(String value) {
    this.causale = (CausaleType) CausaleType.toEnumConstantFromString(value);
  }

  public String get_value_causale() {
    if(this.causale == null){
    	return null;
    }else{
    	return this.causale.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.CausaleType getCausale() {
    return this.causale;
  }

  public void setCausale(org.govmix.proxy.fatturapa.orm.constants.CausaleType causale) {
    this.causale = causale;
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public java.lang.String getEstremiImpegno() {
    return this.estremiImpegno;
  }

  public void setEstremiImpegno(java.lang.String estremiImpegno) {
    this.estremiImpegno = estremiImpegno;
  }

  public java.lang.String getCodiceCig() {
    return this.codiceCig;
  }

  public void setCodiceCig(java.lang.String codiceCig) {
    this.codiceCig = codiceCig;
  }

  public java.lang.String getCodiceCup() {
    return this.codiceCup;
  }

  public void setCodiceCup(java.lang.String codiceCup) {
    this.codiceCup = codiceCup;
  }

  public java.lang.String getIdImporto() {
    return this.idImporto;
  }

  public void setIdImporto(java.lang.String idImporto) {
    this.idImporto = idImporto;
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

  private static org.govmix.proxy.fatturapa.orm.model.PccContabilizzazioneModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccContabilizzazione.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccContabilizzazione.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccContabilizzazioneModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccContabilizzazioneModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccContabilizzazione.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccContabilizzazione.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="double")
  @XmlElement(name="importoMovimento",required=true,nillable=false)
  protected double importoMovimento;

  @XmlTransient
  protected java.lang.String _value_naturaSpesa;

  @XmlElement(name="naturaSpesa",required=true,nillable=false)
  protected NaturaSpesaType naturaSpesa;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="capitoliSpesa",required=false,nillable=false)
  protected java.lang.String capitoliSpesa;

  @XmlTransient
  protected java.lang.String _value_statoDebito;

  @XmlElement(name="statoDebito",required=true,nillable=false)
  protected StatoDebitoType statoDebito;

  @XmlTransient
  protected java.lang.String _value_causale;

  @XmlElement(name="causale",required=false,nillable=false)
  protected CausaleType causale;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizione",required=false,nillable=false)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="estremiImpegno",required=false,nillable=false)
  protected java.lang.String estremiImpegno;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="CodiceCig",required=true,nillable=false)
  protected java.lang.String codiceCig;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="CodiceCup",required=true,nillable=false)
  protected java.lang.String codiceCup;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idImporto",required=true,nillable=false)
  protected java.lang.String idImporto;

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
