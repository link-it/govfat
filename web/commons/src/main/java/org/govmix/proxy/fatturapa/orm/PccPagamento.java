/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType;
import java.io.Serializable;


/** <p>Java class for PccPagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccPagamento">
 * 		&lt;sequence>
 * 			&lt;element name="importoPagato" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="naturaSpesa" type="{http://www.govmix.org/proxy/fatturapa/orm}NaturaSpesaType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="capitoliSpesa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="estremiImpegno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="numeroMandato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataMandato" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idFiscaleIvaBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="CodiceCig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="CodiceCup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idFattura" type="{http://www.govmix.org/proxy/fatturapa/orm}id-fattura" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "PccPagamento", 
  propOrder = {
  	"importoPagato",
  	"naturaSpesa",
  	"capitoliSpesa",
  	"estremiImpegno",
  	"numeroMandato",
  	"dataMandato",
  	"idFiscaleIvaBeneficiario",
  	"codiceCig",
  	"codiceCup",
  	"descrizione",
  	"idFattura",
  	"dataRichiesta",
  	"dataQuery"
  }
)

@XmlRootElement(name = "PccPagamento")

public class PccPagamento extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccPagamento() {
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

  public double getImportoPagato() {
    return this.importoPagato;
  }

  public void setImportoPagato(double importoPagato) {
    this.importoPagato = importoPagato;
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

  public java.lang.String getEstremiImpegno() {
    return this.estremiImpegno;
  }

  public void setEstremiImpegno(java.lang.String estremiImpegno) {
    this.estremiImpegno = estremiImpegno;
  }

  public java.lang.String getNumeroMandato() {
    return this.numeroMandato;
  }

  public void setNumeroMandato(java.lang.String numeroMandato) {
    this.numeroMandato = numeroMandato;
  }

  public java.util.Date getDataMandato() {
    return this.dataMandato;
  }

  public void setDataMandato(java.util.Date dataMandato) {
    this.dataMandato = dataMandato;
  }

  public java.lang.String getIdFiscaleIvaBeneficiario() {
    return this.idFiscaleIvaBeneficiario;
  }

  public void setIdFiscaleIvaBeneficiario(java.lang.String idFiscaleIvaBeneficiario) {
    this.idFiscaleIvaBeneficiario = idFiscaleIvaBeneficiario;
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

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public IdFattura getIdFattura() {
    return this.idFattura;
  }

  public void setIdFattura(IdFattura idFattura) {
    this.idFattura = idFattura;
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

  private static org.govmix.proxy.fatturapa.orm.model.PccPagamentoModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccPagamento.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccPagamento.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccPagamentoModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccPagamentoModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccPagamento.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccPagamento.modelStaticInstance;
  }


  @javax.xml.bind.annotation.XmlSchemaType(name="double")
  @XmlElement(name="importoPagato",required=true,nillable=false)
  protected double importoPagato;

  @XmlTransient
  protected java.lang.String _value_naturaSpesa;

  @XmlElement(name="naturaSpesa",required=true,nillable=false)
  protected NaturaSpesaType naturaSpesa;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="capitoliSpesa",required=false,nillable=false)
  protected java.lang.String capitoliSpesa;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="estremiImpegno",required=false,nillable=false)
  protected java.lang.String estremiImpegno;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="numeroMandato",required=false,nillable=false)
  protected java.lang.String numeroMandato;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="dataMandato",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataMandato;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idFiscaleIvaBeneficiario",required=true,nillable=false)
  protected java.lang.String idFiscaleIvaBeneficiario;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="CodiceCig",required=false,nillable=false)
  protected java.lang.String codiceCig;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="CodiceCup",required=false,nillable=false)
  protected java.lang.String codiceCup;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizione",required=false,nillable=false)
  protected java.lang.String descrizione;

  @XmlElement(name="idFattura",required=true,nillable=false)
  protected IdFattura idFattura;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataRichiesta",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataRichiesta;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataQuery",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataQuery;

}
