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
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoEsitoTrasmissioneType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for PccTracciaTrasmissione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccTracciaTrasmissione">
 * 		&lt;sequence>
 * 			&lt;element name="idTraccia" type="{http://www.govmix.org/proxy/fatturapa/orm}id-traccia" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tsTrasmissione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idPccTransazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="esitoTrasmissione" type="{http://www.govmix.org/proxy/fatturapa/orm}EsitoTrasmissioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="statoEsito" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoEsitoTrasmissioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="gdo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataFineElaborazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dettaglioErroreTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idEgovRichiesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="PccTracciaTrasmissioneEsito" type="{http://www.govmix.org/proxy/fatturapa/orm}PccTracciaTrasmissioneEsito" minOccurs="1" maxOccurs="unbounded"/>
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
@XmlType(name = "PccTracciaTrasmissione", 
  propOrder = {
  	"idTraccia",
  	"tsTrasmissione",
  	"idPccTransazione",
  	"esitoTrasmissione",
  	"statoEsito",
  	"gdo",
  	"dataFineElaborazione",
  	"dettaglioErroreTrasmissione",
  	"idEgovRichiesta",
  	"pccTracciaTrasmissioneEsito"
  }
)

@XmlRootElement(name = "PccTracciaTrasmissione")

public class PccTracciaTrasmissione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccTracciaTrasmissione() {
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

  public IdTraccia getIdTraccia() {
    return this.idTraccia;
  }

  public void setIdTraccia(IdTraccia idTraccia) {
    this.idTraccia = idTraccia;
  }

  public java.util.Date getTsTrasmissione() {
    return this.tsTrasmissione;
  }

  public void setTsTrasmissione(java.util.Date tsTrasmissione) {
    this.tsTrasmissione = tsTrasmissione;
  }

  public java.lang.String getIdPccTransazione() {
    return this.idPccTransazione;
  }

  public void setIdPccTransazione(java.lang.String idPccTransazione) {
    this.idPccTransazione = idPccTransazione;
  }

  public void set_value_esitoTrasmissione(String value) {
    this.esitoTrasmissione = (EsitoTrasmissioneType) EsitoTrasmissioneType.toEnumConstantFromString(value);
  }

  public String get_value_esitoTrasmissione() {
    if(this.esitoTrasmissione == null){
    	return null;
    }else{
    	return this.esitoTrasmissione.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType getEsitoTrasmissione() {
    return this.esitoTrasmissione;
  }

  public void setEsitoTrasmissione(org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType esitoTrasmissione) {
    this.esitoTrasmissione = esitoTrasmissione;
  }

  public void set_value_statoEsito(String value) {
    this.statoEsito = (StatoEsitoTrasmissioneType) StatoEsitoTrasmissioneType.toEnumConstantFromString(value);
  }

  public String get_value_statoEsito() {
    if(this.statoEsito == null){
    	return null;
    }else{
    	return this.statoEsito.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoEsitoTrasmissioneType getStatoEsito() {
    return this.statoEsito;
  }

  public void setStatoEsito(org.govmix.proxy.fatturapa.orm.constants.StatoEsitoTrasmissioneType statoEsito) {
    this.statoEsito = statoEsito;
  }

  public java.util.Date getGdo() {
    return this.gdo;
  }

  public void setGdo(java.util.Date gdo) {
    this.gdo = gdo;
  }

  public java.util.Date getDataFineElaborazione() {
    return this.dataFineElaborazione;
  }

  public void setDataFineElaborazione(java.util.Date dataFineElaborazione) {
    this.dataFineElaborazione = dataFineElaborazione;
  }

  public java.lang.String getDettaglioErroreTrasmissione() {
    return this.dettaglioErroreTrasmissione;
  }

  public void setDettaglioErroreTrasmissione(java.lang.String dettaglioErroreTrasmissione) {
    this.dettaglioErroreTrasmissione = dettaglioErroreTrasmissione;
  }

  public java.lang.String getIdEgovRichiesta() {
    return this.idEgovRichiesta;
  }

  public void setIdEgovRichiesta(java.lang.String idEgovRichiesta) {
    this.idEgovRichiesta = idEgovRichiesta;
  }

  public void addPccTracciaTrasmissioneEsito(PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) {
    this.pccTracciaTrasmissioneEsito.add(pccTracciaTrasmissioneEsito);
  }

  public PccTracciaTrasmissioneEsito getPccTracciaTrasmissioneEsito(int index) {
    return this.pccTracciaTrasmissioneEsito.get( index );
  }

  public PccTracciaTrasmissioneEsito removePccTracciaTrasmissioneEsito(int index) {
    return this.pccTracciaTrasmissioneEsito.remove( index );
  }

  public List<PccTracciaTrasmissioneEsito> getPccTracciaTrasmissioneEsitoList() {
    return this.pccTracciaTrasmissioneEsito;
  }

  public void setPccTracciaTrasmissioneEsitoList(List<PccTracciaTrasmissioneEsito> pccTracciaTrasmissioneEsito) {
    this.pccTracciaTrasmissioneEsito=pccTracciaTrasmissioneEsito;
  }

  public int sizePccTracciaTrasmissioneEsitoList() {
    return this.pccTracciaTrasmissioneEsito.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione.modelStaticInstance;
  }


  @XmlElement(name="idTraccia",required=true,nillable=false)
  protected IdTraccia idTraccia;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="tsTrasmissione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date tsTrasmissione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idPccTransazione",required=false,nillable=false)
  protected java.lang.String idPccTransazione;

  @XmlTransient
  protected java.lang.String _value_esitoTrasmissione;

  @XmlElement(name="esitoTrasmissione",required=true,nillable=false)
  protected EsitoTrasmissioneType esitoTrasmissione;

  @XmlTransient
  protected java.lang.String _value_statoEsito;

  @XmlElement(name="statoEsito",required=true,nillable=false)
  protected StatoEsitoTrasmissioneType statoEsito;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="gdo",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date gdo;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataFineElaborazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataFineElaborazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="dettaglioErroreTrasmissione",required=false,nillable=false)
  protected java.lang.String dettaglioErroreTrasmissione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idEgovRichiesta",required=true,nillable=false)
  protected java.lang.String idEgovRichiesta;

  @XmlElement(name="PccTracciaTrasmissioneEsito",required=true,nillable=false)
  protected List<PccTracciaTrasmissioneEsito> pccTracciaTrasmissioneEsito = new ArrayList<PccTracciaTrasmissioneEsito>();

  /**
   * @deprecated Use method getPccTracciaTrasmissioneEsitoList
   * @return List<PccTracciaTrasmissioneEsito>
  */
  @Deprecated
  public List<PccTracciaTrasmissioneEsito> getPccTracciaTrasmissioneEsito() {
  	return this.pccTracciaTrasmissioneEsito;
  }

  /**
   * @deprecated Use method setPccTracciaTrasmissioneEsitoList
   * @param pccTracciaTrasmissioneEsito List<PccTracciaTrasmissioneEsito>
  */
  @Deprecated
  public void setPccTracciaTrasmissioneEsito(List<PccTracciaTrasmissioneEsito> pccTracciaTrasmissioneEsito) {
  	this.pccTracciaTrasmissioneEsito=pccTracciaTrasmissioneEsito;
  }

  /**
   * @deprecated Use method sizePccTracciaTrasmissioneEsitoList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizePccTracciaTrasmissioneEsito() {
  	return this.pccTracciaTrasmissioneEsito.size();
  }

}
