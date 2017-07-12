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
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.orm.constants.TipoOperazionePccType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for PccTraccia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccTraccia">
 * 		&lt;sequence>
 * 			&lt;element name="dataCreazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="cfTrasmittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="versioneApplicativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idPccAmministrazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idPaTransazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idPaTransazioneRispedizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="sistemaRichiedente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="utenteRichiedente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idFattura" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codiceDipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="richiestaXml" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="rispostaXml" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="operazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="tipoOperazione" type="{http://www.govmix.org/proxy/fatturapa/orm}TipoOperazionePccType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="stato" type="{http://www.govmix.org/proxy/fatturapa/orm}StatoType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataUltimaTrasmissione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataUltimoTentativoEsito" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="codiciErrore" type="{http://www.govmix.org/proxy/fatturapa/orm}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="rispedizione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="rispedizioneDopoQuery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="rispedizioneMaxTentativi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="rispedizioneProssimoTentativo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="rispedizioneNumeroTentativi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="rispedizioneUltimoTentativo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="PccTracciaTrasmissione" type="{http://www.govmix.org/proxy/fatturapa/orm}PccTracciaTrasmissione" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="FatturaElettronica" type="{http://www.govmix.org/proxy/fatturapa/orm}FatturaElettronica" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "PccTraccia", 
  propOrder = {
  	"dataCreazione",
  	"cfTrasmittente",
  	"versioneApplicativa",
  	"idPccAmministrazione",
  	"idPaTransazione",
  	"idPaTransazioneRispedizione",
  	"sistemaRichiedente",
  	"utenteRichiedente",
  	"idFattura",
  	"codiceDipartimento",
  	"richiestaXml",
  	"rispostaXml",
  	"operazione",
  	"tipoOperazione",
  	"stato",
  	"dataUltimaTrasmissione",
  	"dataUltimoTentativoEsito",
  	"codiciErrore",
  	"rispedizione",
  	"rispedizioneDopoQuery",
  	"rispedizioneMaxTentativi",
  	"rispedizioneProssimoTentativo",
  	"rispedizioneNumeroTentativi",
  	"rispedizioneUltimoTentativo",
  	"pccTracciaTrasmissione",
  	"fatturaElettronica"
  }
)

@XmlRootElement(name = "PccTraccia")

public class PccTraccia extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccTraccia() {
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

  public java.util.Date getDataCreazione() {
    return this.dataCreazione;
  }

  public void setDataCreazione(java.util.Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public java.lang.String getCfTrasmittente() {
    return this.cfTrasmittente;
  }

  public void setCfTrasmittente(java.lang.String cfTrasmittente) {
    this.cfTrasmittente = cfTrasmittente;
  }

  public java.lang.String getVersioneApplicativa() {
    return this.versioneApplicativa;
  }

  public void setVersioneApplicativa(java.lang.String versioneApplicativa) {
    this.versioneApplicativa = versioneApplicativa;
  }

  public int getIdPccAmministrazione() {
    return this.idPccAmministrazione;
  }

  public void setIdPccAmministrazione(int idPccAmministrazione) {
    this.idPccAmministrazione = idPccAmministrazione;
  }

  public java.lang.String getIdPaTransazione() {
    return this.idPaTransazione;
  }

  public void setIdPaTransazione(java.lang.String idPaTransazione) {
    this.idPaTransazione = idPaTransazione;
  }

  public java.lang.String getIdPaTransazioneRispedizione() {
    return this.idPaTransazioneRispedizione;
  }

  public void setIdPaTransazioneRispedizione(java.lang.String idPaTransazioneRispedizione) {
    this.idPaTransazioneRispedizione = idPaTransazioneRispedizione;
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

  public long getIdFattura() {
    return this.idFattura;
  }

  public void setIdFattura(long idFattura) {
    this.idFattura = idFattura;
  }

  public java.lang.String getCodiceDipartimento() {
    return this.codiceDipartimento;
  }

  public void setCodiceDipartimento(java.lang.String codiceDipartimento) {
    this.codiceDipartimento = codiceDipartimento;
  }

  public byte[] getRichiestaXml() {
    return this.richiestaXml;
  }

  public void setRichiestaXml(byte[] richiestaXml) {
    this.richiestaXml = richiestaXml;
  }

  public byte[] getRispostaXml() {
    return this.rispostaXml;
  }

  public void setRispostaXml(byte[] rispostaXml) {
    this.rispostaXml = rispostaXml;
  }

  public java.lang.String getOperazione() {
    return this.operazione;
  }

  public void setOperazione(java.lang.String operazione) {
    this.operazione = operazione;
  }

  public void set_value_tipoOperazione(String value) {
    this.tipoOperazione = (TipoOperazionePccType) TipoOperazionePccType.toEnumConstantFromString(value);
  }

  public String get_value_tipoOperazione() {
    if(this.tipoOperazione == null){
    	return null;
    }else{
    	return this.tipoOperazione.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.TipoOperazionePccType getTipoOperazione() {
    return this.tipoOperazione;
  }

  public void setTipoOperazione(org.govmix.proxy.fatturapa.orm.constants.TipoOperazionePccType tipoOperazione) {
    this.tipoOperazione = tipoOperazione;
  }

  public void set_value_stato(String value) {
    this.stato = (StatoType) StatoType.toEnumConstantFromString(value);
  }

  public String get_value_stato() {
    if(this.stato == null){
    	return null;
    }else{
    	return this.stato.toString();
    }
  }

  public org.govmix.proxy.fatturapa.orm.constants.StatoType getStato() {
    return this.stato;
  }

  public void setStato(org.govmix.proxy.fatturapa.orm.constants.StatoType stato) {
    this.stato = stato;
  }

  public java.util.Date getDataUltimaTrasmissione() {
    return this.dataUltimaTrasmissione;
  }

  public void setDataUltimaTrasmissione(java.util.Date dataUltimaTrasmissione) {
    this.dataUltimaTrasmissione = dataUltimaTrasmissione;
  }

  public java.util.Date getDataUltimoTentativoEsito() {
    return this.dataUltimoTentativoEsito;
  }

  public void setDataUltimoTentativoEsito(java.util.Date dataUltimoTentativoEsito) {
    this.dataUltimoTentativoEsito = dataUltimoTentativoEsito;
  }

  public java.lang.String getCodiciErrore() {
    return this.codiciErrore;
  }

  public void setCodiciErrore(java.lang.String codiciErrore) {
    this.codiciErrore = codiciErrore;
  }

  public boolean isRispedizione() {
    return this.rispedizione;
  }

  public boolean getRispedizione() {
    return this.rispedizione;
  }

  public void setRispedizione(boolean rispedizione) {
    this.rispedizione = rispedizione;
  }

  public boolean isRispedizioneDopoQuery() {
    return this.rispedizioneDopoQuery;
  }

  public boolean getRispedizioneDopoQuery() {
    return this.rispedizioneDopoQuery;
  }

  public void setRispedizioneDopoQuery(boolean rispedizioneDopoQuery) {
    this.rispedizioneDopoQuery = rispedizioneDopoQuery;
  }

  public int getRispedizioneMaxTentativi() {
    return this.rispedizioneMaxTentativi;
  }

  public void setRispedizioneMaxTentativi(int rispedizioneMaxTentativi) {
    this.rispedizioneMaxTentativi = rispedizioneMaxTentativi;
  }

  public java.util.Date getRispedizioneProssimoTentativo() {
    return this.rispedizioneProssimoTentativo;
  }

  public void setRispedizioneProssimoTentativo(java.util.Date rispedizioneProssimoTentativo) {
    this.rispedizioneProssimoTentativo = rispedizioneProssimoTentativo;
  }

  public int getRispedizioneNumeroTentativi() {
    return this.rispedizioneNumeroTentativi;
  }

  public void setRispedizioneNumeroTentativi(int rispedizioneNumeroTentativi) {
    this.rispedizioneNumeroTentativi = rispedizioneNumeroTentativi;
  }

  public java.util.Date getRispedizioneUltimoTentativo() {
    return this.rispedizioneUltimoTentativo;
  }

  public void setRispedizioneUltimoTentativo(java.util.Date rispedizioneUltimoTentativo) {
    this.rispedizioneUltimoTentativo = rispedizioneUltimoTentativo;
  }

  public void addPccTracciaTrasmissione(PccTracciaTrasmissione pccTracciaTrasmissione) {
    this.pccTracciaTrasmissione.add(pccTracciaTrasmissione);
  }

  public PccTracciaTrasmissione getPccTracciaTrasmissione(int index) {
    return this.pccTracciaTrasmissione.get( index );
  }

  public PccTracciaTrasmissione removePccTracciaTrasmissione(int index) {
    return this.pccTracciaTrasmissione.remove( index );
  }

  public List<PccTracciaTrasmissione> getPccTracciaTrasmissioneList() {
    return this.pccTracciaTrasmissione;
  }

  public void setPccTracciaTrasmissioneList(List<PccTracciaTrasmissione> pccTracciaTrasmissione) {
    this.pccTracciaTrasmissione=pccTracciaTrasmissione;
  }

  public int sizePccTracciaTrasmissioneList() {
    return this.pccTracciaTrasmissione.size();
  }

  public FatturaElettronica getFatturaElettronica() {
    return this.fatturaElettronica;
  }

  public void setFatturaElettronica(FatturaElettronica fatturaElettronica) {
    this.fatturaElettronica = fatturaElettronica;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccTracciaModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccTraccia.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccTraccia.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccTracciaModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccTracciaModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccTraccia.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccTraccia.modelStaticInstance;
  }


  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataCreazione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataCreazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="cfTrasmittente",required=true,nillable=false)
  protected java.lang.String cfTrasmittente;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="versioneApplicativa",required=true,nillable=false)
  protected java.lang.String versioneApplicativa;

  @javax.xml.bind.annotation.XmlSchemaType(name="int")
  @XmlElement(name="idPccAmministrazione",required=true,nillable=false)
  protected int idPccAmministrazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idPaTransazione",required=false,nillable=false)
  protected java.lang.String idPaTransazione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="idPaTransazioneRispedizione",required=false,nillable=false)
  protected java.lang.String idPaTransazioneRispedizione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="sistemaRichiedente",required=true,nillable=false)
  protected java.lang.String sistemaRichiedente;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="utenteRichiedente",required=true,nillable=false)
  protected java.lang.String utenteRichiedente;

  @javax.xml.bind.annotation.XmlSchemaType(name="long")
  @XmlElement(name="idFattura",required=false,nillable=false)
  protected long idFattura;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codiceDipartimento",required=false,nillable=false)
  protected java.lang.String codiceDipartimento;

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="richiestaXml",required=true,nillable=false)
  protected byte[] richiestaXml;

  @javax.xml.bind.annotation.XmlSchemaType(name="base64Binary")
  @XmlElement(name="rispostaXml",required=false,nillable=false)
  protected byte[] rispostaXml;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="operazione",required=true,nillable=false)
  protected java.lang.String operazione;

  @XmlTransient
  protected java.lang.String _value_tipoOperazione;

  @XmlElement(name="tipoOperazione",required=true,nillable=false)
  protected TipoOperazionePccType tipoOperazione;

  @XmlTransient
  protected java.lang.String _value_stato;

  @XmlElement(name="stato",required=true,nillable=false)
  protected StatoType stato;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataUltimaTrasmissione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataUltimaTrasmissione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataUltimoTentativoEsito",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataUltimoTentativoEsito;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="codiciErrore",required=false,nillable=false)
  protected java.lang.String codiciErrore;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="rispedizione",required=true,nillable=false,defaultValue="false")
  protected boolean rispedizione = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="rispedizioneDopoQuery",required=true,nillable=false)
  protected boolean rispedizioneDopoQuery;

  @javax.xml.bind.annotation.XmlSchemaType(name="int")
  @XmlElement(name="rispedizioneMaxTentativi",required=false,nillable=false)
  protected int rispedizioneMaxTentativi;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="rispedizioneProssimoTentativo",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date rispedizioneProssimoTentativo;

  @javax.xml.bind.annotation.XmlSchemaType(name="int")
  @XmlElement(name="rispedizioneNumeroTentativi",required=false,nillable=false)
  protected int rispedizioneNumeroTentativi;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="rispedizioneUltimoTentativo",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date rispedizioneUltimoTentativo;

  @XmlElement(name="PccTracciaTrasmissione",required=true,nillable=false)
  protected List<PccTracciaTrasmissione> pccTracciaTrasmissione = new ArrayList<PccTracciaTrasmissione>();

  /**
   * @deprecated Use method getPccTracciaTrasmissioneList
   * @return List<PccTracciaTrasmissione>
  */
  @Deprecated
  public List<PccTracciaTrasmissione> getPccTracciaTrasmissione() {
  	return this.pccTracciaTrasmissione;
  }

  /**
   * @deprecated Use method setPccTracciaTrasmissioneList
   * @param pccTracciaTrasmissione List<PccTracciaTrasmissione>
  */
  @Deprecated
  public void setPccTracciaTrasmissione(List<PccTracciaTrasmissione> pccTracciaTrasmissione) {
  	this.pccTracciaTrasmissione=pccTracciaTrasmissione;
  }

  /**
   * @deprecated Use method sizePccTracciaTrasmissioneList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizePccTracciaTrasmissione() {
  	return this.pccTracciaTrasmissione.size();
  }

  @XmlElement(name="FatturaElettronica",required=false,nillable=false)
  protected FatturaElettronica fatturaElettronica;

}
