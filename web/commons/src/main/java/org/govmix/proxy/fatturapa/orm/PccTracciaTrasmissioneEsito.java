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
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for PccTracciaTrasmissioneEsito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccTracciaTrasmissioneEsito">
 * 		&lt;sequence>
 * 			&lt;element name="idTrasmissione" type="{http://www.govmix.org/proxy/fatturapa/orm}id-trasmissione" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="esitoElaborazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizioneElaborazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataFineElaborazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="gdo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="esitoTrasmissione" type="{http://www.govmix.org/proxy/fatturapa/orm}EsitoTrasmissioneType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dettaglioErroreTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idEgovRichiesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="PccErroreElaborazione" type="{http://www.govmix.org/proxy/fatturapa/orm}PccErroreElaborazione" minOccurs="1" maxOccurs="unbounded"/>
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
@XmlType(name = "PccTracciaTrasmissioneEsito", 
  propOrder = {
  	"idTrasmissione",
  	"esitoElaborazione",
  	"descrizioneElaborazione",
  	"dataFineElaborazione",
  	"gdo",
  	"esitoTrasmissione",
  	"dettaglioErroreTrasmissione",
  	"idEgovRichiesta",
  	"pccErroreElaborazione"
  }
)

@XmlRootElement(name = "PccTracciaTrasmissioneEsito")

public class PccTracciaTrasmissioneEsito extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccTracciaTrasmissioneEsito() {
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

  public IdTrasmissione getIdTrasmissione() {
    return this.idTrasmissione;
  }

  public void setIdTrasmissione(IdTrasmissione idTrasmissione) {
    this.idTrasmissione = idTrasmissione;
  }

  public java.lang.String getEsitoElaborazione() {
    return this.esitoElaborazione;
  }

  public void setEsitoElaborazione(java.lang.String esitoElaborazione) {
    this.esitoElaborazione = esitoElaborazione;
  }

  public java.lang.String getDescrizioneElaborazione() {
    return this.descrizioneElaborazione;
  }

  public void setDescrizioneElaborazione(java.lang.String descrizioneElaborazione) {
    this.descrizioneElaborazione = descrizioneElaborazione;
  }

  public java.util.Date getDataFineElaborazione() {
    return this.dataFineElaborazione;
  }

  public void setDataFineElaborazione(java.util.Date dataFineElaborazione) {
    this.dataFineElaborazione = dataFineElaborazione;
  }

  public java.util.Date getGdo() {
    return this.gdo;
  }

  public void setGdo(java.util.Date gdo) {
    this.gdo = gdo;
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

  public void addPccErroreElaborazione(PccErroreElaborazione pccErroreElaborazione) {
    this.pccErroreElaborazione.add(pccErroreElaborazione);
  }

  public PccErroreElaborazione getPccErroreElaborazione(int index) {
    return this.pccErroreElaborazione.get( index );
  }

  public PccErroreElaborazione removePccErroreElaborazione(int index) {
    return this.pccErroreElaborazione.remove( index );
  }

  public List<PccErroreElaborazione> getPccErroreElaborazioneList() {
    return this.pccErroreElaborazione;
  }

  public void setPccErroreElaborazioneList(List<PccErroreElaborazione> pccErroreElaborazione) {
    this.pccErroreElaborazione=pccErroreElaborazione;
  }

  public int sizePccErroreElaborazioneList() {
    return this.pccErroreElaborazione.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneEsitoModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneEsitoModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneEsitoModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito.modelStaticInstance;
  }


  @XmlElement(name="idTrasmissione",required=true,nillable=false)
  protected IdTrasmissione idTrasmissione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="esitoElaborazione",required=true,nillable=false)
  protected java.lang.String esitoElaborazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizioneElaborazione",required=false,nillable=false)
  protected java.lang.String descrizioneElaborazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataFineElaborazione",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataFineElaborazione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="gdo",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date gdo;

  @XmlTransient
  protected java.lang.String _value_esitoTrasmissione;

  @XmlElement(name="esitoTrasmissione",required=true,nillable=false)
  protected EsitoTrasmissioneType esitoTrasmissione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="dettaglioErroreTrasmissione",required=false,nillable=false)
  protected java.lang.String dettaglioErroreTrasmissione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idEgovRichiesta",required=true,nillable=false)
  protected java.lang.String idEgovRichiesta;

  @XmlElement(name="PccErroreElaborazione",required=true,nillable=false)
  protected List<PccErroreElaborazione> pccErroreElaborazione = new ArrayList<PccErroreElaborazione>();

  /**
   * @deprecated Use method getPccErroreElaborazioneList
   * @return List<PccErroreElaborazione>
  */
  @Deprecated
  public List<PccErroreElaborazione> getPccErroreElaborazione() {
  	return this.pccErroreElaborazione;
  }

  /**
   * @deprecated Use method setPccErroreElaborazioneList
   * @param pccErroreElaborazione List<PccErroreElaborazione>
  */
  @Deprecated
  public void setPccErroreElaborazione(List<PccErroreElaborazione> pccErroreElaborazione) {
  	this.pccErroreElaborazione=pccErroreElaborazione;
  }

  /**
   * @deprecated Use method sizePccErroreElaborazioneList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizePccErroreElaborazione() {
  	return this.pccErroreElaborazione.size();
  }

}
