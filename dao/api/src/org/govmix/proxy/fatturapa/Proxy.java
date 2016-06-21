/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for Proxy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Proxy">
 * 		&lt;sequence>
 * 			&lt;element name="LottoFatture" type="{http://www.govmix.org/proxy/fatturapa}LottoFatture" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="NotificaDecorrenzaTermini" type="{http://www.govmix.org/proxy/fatturapa}NotificaDecorrenzaTermini" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="FatturaElettronica" type="{http://www.govmix.org/proxy/fatturapa}FatturaElettronica" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="AllegatoFattura" type="{http://www.govmix.org/proxy/fatturapa}AllegatoFattura" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="Ente" type="{http://www.govmix.org/proxy/fatturapa}Ente" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="Registro" type="{http://www.govmix.org/proxy/fatturapa}Registro" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="RegistroProperty" type="{http://www.govmix.org/proxy/fatturapa}RegistroProperty" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="Utente" type="{http://www.govmix.org/proxy/fatturapa}Utente" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="NotificaEsitoCommittente" type="{http://www.govmix.org/proxy/fatturapa}NotificaEsitoCommittente" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="Dipartimento" type="{http://www.govmix.org/proxy/fatturapa}Dipartimento" minOccurs="0" maxOccurs="unbounded"/>
 * 			&lt;element name="DipartimentoProperty" type="{http://www.govmix.org/proxy/fatturapa}DipartimentoProperty" minOccurs="0" maxOccurs="unbounded"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Proxy", 
  propOrder = {
  	"lottoFatture",
  	"notificaDecorrenzaTermini",
  	"fatturaElettronica",
  	"allegatoFattura",
  	"ente",
  	"registro",
  	"registroProperty",
  	"utente",
  	"notificaEsitoCommittente",
  	"dipartimento",
  	"dipartimentoProperty"
  }
)

@XmlRootElement(name = "Proxy")

public class Proxy extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Proxy() {
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

  public void addLottoFatture(LottoFatture lottoFatture) {
    this.lottoFatture.add(lottoFatture);
  }

  public LottoFatture getLottoFatture(int index) {
    return this.lottoFatture.get( index );
  }

  public LottoFatture removeLottoFatture(int index) {
    return this.lottoFatture.remove( index );
  }

  public List<LottoFatture> getLottoFattureList() {
    return this.lottoFatture;
  }

  public void setLottoFattureList(List<LottoFatture> lottoFatture) {
    this.lottoFatture=lottoFatture;
  }

  public int sizeLottoFattureList() {
    return this.lottoFatture.size();
  }

  public void addNotificaDecorrenzaTermini(NotificaDecorrenzaTermini notificaDecorrenzaTermini) {
    this.notificaDecorrenzaTermini.add(notificaDecorrenzaTermini);
  }

  public NotificaDecorrenzaTermini getNotificaDecorrenzaTermini(int index) {
    return this.notificaDecorrenzaTermini.get( index );
  }

  public NotificaDecorrenzaTermini removeNotificaDecorrenzaTermini(int index) {
    return this.notificaDecorrenzaTermini.remove( index );
  }

  public List<NotificaDecorrenzaTermini> getNotificaDecorrenzaTerminiList() {
    return this.notificaDecorrenzaTermini;
  }

  public void setNotificaDecorrenzaTerminiList(List<NotificaDecorrenzaTermini> notificaDecorrenzaTermini) {
    this.notificaDecorrenzaTermini=notificaDecorrenzaTermini;
  }

  public int sizeNotificaDecorrenzaTerminiList() {
    return this.notificaDecorrenzaTermini.size();
  }

  public void addFatturaElettronica(FatturaElettronica fatturaElettronica) {
    this.fatturaElettronica.add(fatturaElettronica);
  }

  public FatturaElettronica getFatturaElettronica(int index) {
    return this.fatturaElettronica.get( index );
  }

  public FatturaElettronica removeFatturaElettronica(int index) {
    return this.fatturaElettronica.remove( index );
  }

  public List<FatturaElettronica> getFatturaElettronicaList() {
    return this.fatturaElettronica;
  }

  public void setFatturaElettronicaList(List<FatturaElettronica> fatturaElettronica) {
    this.fatturaElettronica=fatturaElettronica;
  }

  public int sizeFatturaElettronicaList() {
    return this.fatturaElettronica.size();
  }

  public void addAllegatoFattura(AllegatoFattura allegatoFattura) {
    this.allegatoFattura.add(allegatoFattura);
  }

  public AllegatoFattura getAllegatoFattura(int index) {
    return this.allegatoFattura.get( index );
  }

  public AllegatoFattura removeAllegatoFattura(int index) {
    return this.allegatoFattura.remove( index );
  }

  public List<AllegatoFattura> getAllegatoFatturaList() {
    return this.allegatoFattura;
  }

  public void setAllegatoFatturaList(List<AllegatoFattura> allegatoFattura) {
    this.allegatoFattura=allegatoFattura;
  }

  public int sizeAllegatoFatturaList() {
    return this.allegatoFattura.size();
  }

  public void addEnte(Ente ente) {
    this.ente.add(ente);
  }

  public Ente getEnte(int index) {
    return this.ente.get( index );
  }

  public Ente removeEnte(int index) {
    return this.ente.remove( index );
  }

  public List<Ente> getEnteList() {
    return this.ente;
  }

  public void setEnteList(List<Ente> ente) {
    this.ente=ente;
  }

  public int sizeEnteList() {
    return this.ente.size();
  }

  public void addRegistro(Registro registro) {
    this.registro.add(registro);
  }

  public Registro getRegistro(int index) {
    return this.registro.get( index );
  }

  public Registro removeRegistro(int index) {
    return this.registro.remove( index );
  }

  public List<Registro> getRegistroList() {
    return this.registro;
  }

  public void setRegistroList(List<Registro> registro) {
    this.registro=registro;
  }

  public int sizeRegistroList() {
    return this.registro.size();
  }

  public void addRegistroProperty(RegistroProperty registroProperty) {
    this.registroProperty.add(registroProperty);
  }

  public RegistroProperty getRegistroProperty(int index) {
    return this.registroProperty.get( index );
  }

  public RegistroProperty removeRegistroProperty(int index) {
    return this.registroProperty.remove( index );
  }

  public List<RegistroProperty> getRegistroPropertyList() {
    return this.registroProperty;
  }

  public void setRegistroPropertyList(List<RegistroProperty> registroProperty) {
    this.registroProperty=registroProperty;
  }

  public int sizeRegistroPropertyList() {
    return this.registroProperty.size();
  }

  public void addUtente(Utente utente) {
    this.utente.add(utente);
  }

  public Utente getUtente(int index) {
    return this.utente.get( index );
  }

  public Utente removeUtente(int index) {
    return this.utente.remove( index );
  }

  public List<Utente> getUtenteList() {
    return this.utente;
  }

  public void setUtenteList(List<Utente> utente) {
    this.utente=utente;
  }

  public int sizeUtenteList() {
    return this.utente.size();
  }

  public void addNotificaEsitoCommittente(NotificaEsitoCommittente notificaEsitoCommittente) {
    this.notificaEsitoCommittente.add(notificaEsitoCommittente);
  }

  public NotificaEsitoCommittente getNotificaEsitoCommittente(int index) {
    return this.notificaEsitoCommittente.get( index );
  }

  public NotificaEsitoCommittente removeNotificaEsitoCommittente(int index) {
    return this.notificaEsitoCommittente.remove( index );
  }

  public List<NotificaEsitoCommittente> getNotificaEsitoCommittenteList() {
    return this.notificaEsitoCommittente;
  }

  public void setNotificaEsitoCommittenteList(List<NotificaEsitoCommittente> notificaEsitoCommittente) {
    this.notificaEsitoCommittente=notificaEsitoCommittente;
  }

  public int sizeNotificaEsitoCommittenteList() {
    return this.notificaEsitoCommittente.size();
  }

  public void addDipartimento(Dipartimento dipartimento) {
    this.dipartimento.add(dipartimento);
  }

  public Dipartimento getDipartimento(int index) {
    return this.dipartimento.get( index );
  }

  public Dipartimento removeDipartimento(int index) {
    return this.dipartimento.remove( index );
  }

  public List<Dipartimento> getDipartimentoList() {
    return this.dipartimento;
  }

  public void setDipartimentoList(List<Dipartimento> dipartimento) {
    this.dipartimento=dipartimento;
  }

  public int sizeDipartimentoList() {
    return this.dipartimento.size();
  }

  public void addDipartimentoProperty(DipartimentoProperty dipartimentoProperty) {
    this.dipartimentoProperty.add(dipartimentoProperty);
  }

  public DipartimentoProperty getDipartimentoProperty(int index) {
    return this.dipartimentoProperty.get( index );
  }

  public DipartimentoProperty removeDipartimentoProperty(int index) {
    return this.dipartimentoProperty.remove( index );
  }

  public List<DipartimentoProperty> getDipartimentoPropertyList() {
    return this.dipartimentoProperty;
  }

  public void setDipartimentoPropertyList(List<DipartimentoProperty> dipartimentoProperty) {
    this.dipartimentoProperty=dipartimentoProperty;
  }

  public int sizeDipartimentoPropertyList() {
    return this.dipartimentoProperty.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @XmlElement(name="LottoFatture",required=true,nillable=false)
  protected List<LottoFatture> lottoFatture = new ArrayList<LottoFatture>();

  /**
   * @deprecated Use method getLottoFattureList
   * @return List<LottoFatture>
  */
  @Deprecated
  public List<LottoFatture> getLottoFatture() {
  	return this.lottoFatture;
  }

  /**
   * @deprecated Use method setLottoFattureList
   * @param lottoFatture List<LottoFatture>
  */
  @Deprecated
  public void setLottoFatture(List<LottoFatture> lottoFatture) {
  	this.lottoFatture=lottoFatture;
  }

  /**
   * @deprecated Use method sizeLottoFattureList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeLottoFatture() {
  	return this.lottoFatture.size();
  }

  @XmlElement(name="NotificaDecorrenzaTermini",required=true,nillable=false)
  protected List<NotificaDecorrenzaTermini> notificaDecorrenzaTermini = new ArrayList<NotificaDecorrenzaTermini>();

  /**
   * @deprecated Use method getNotificaDecorrenzaTerminiList
   * @return List<NotificaDecorrenzaTermini>
  */
  @Deprecated
  public List<NotificaDecorrenzaTermini> getNotificaDecorrenzaTermini() {
  	return this.notificaDecorrenzaTermini;
  }

  /**
   * @deprecated Use method setNotificaDecorrenzaTerminiList
   * @param notificaDecorrenzaTermini List<NotificaDecorrenzaTermini>
  */
  @Deprecated
  public void setNotificaDecorrenzaTermini(List<NotificaDecorrenzaTermini> notificaDecorrenzaTermini) {
  	this.notificaDecorrenzaTermini=notificaDecorrenzaTermini;
  }

  /**
   * @deprecated Use method sizeNotificaDecorrenzaTerminiList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeNotificaDecorrenzaTermini() {
  	return this.notificaDecorrenzaTermini.size();
  }

  @XmlElement(name="FatturaElettronica",required=true,nillable=false)
  protected List<FatturaElettronica> fatturaElettronica = new ArrayList<FatturaElettronica>();

  /**
   * @deprecated Use method getFatturaElettronicaList
   * @return List<FatturaElettronica>
  */
  @Deprecated
  public List<FatturaElettronica> getFatturaElettronica() {
  	return this.fatturaElettronica;
  }

  /**
   * @deprecated Use method setFatturaElettronicaList
   * @param fatturaElettronica List<FatturaElettronica>
  */
  @Deprecated
  public void setFatturaElettronica(List<FatturaElettronica> fatturaElettronica) {
  	this.fatturaElettronica=fatturaElettronica;
  }

  /**
   * @deprecated Use method sizeFatturaElettronicaList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeFatturaElettronica() {
  	return this.fatturaElettronica.size();
  }

  @XmlElement(name="AllegatoFattura",required=true,nillable=false)
  protected List<AllegatoFattura> allegatoFattura = new ArrayList<AllegatoFattura>();

  /**
   * @deprecated Use method getAllegatoFatturaList
   * @return List<AllegatoFattura>
  */
  @Deprecated
  public List<AllegatoFattura> getAllegatoFattura() {
  	return this.allegatoFattura;
  }

  /**
   * @deprecated Use method setAllegatoFatturaList
   * @param allegatoFattura List<AllegatoFattura>
  */
  @Deprecated
  public void setAllegatoFattura(List<AllegatoFattura> allegatoFattura) {
  	this.allegatoFattura=allegatoFattura;
  }

  /**
   * @deprecated Use method sizeAllegatoFatturaList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeAllegatoFattura() {
  	return this.allegatoFattura.size();
  }

  @XmlElement(name="Ente",required=true,nillable=false)
  protected List<Ente> ente = new ArrayList<Ente>();

  /**
   * @deprecated Use method getEnteList
   * @return List<Ente>
  */
  @Deprecated
  public List<Ente> getEnte() {
  	return this.ente;
  }

  /**
   * @deprecated Use method setEnteList
   * @param ente List<Ente>
  */
  @Deprecated
  public void setEnte(List<Ente> ente) {
  	this.ente=ente;
  }

  /**
   * @deprecated Use method sizeEnteList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeEnte() {
  	return this.ente.size();
  }

  @XmlElement(name="Registro",required=true,nillable=false)
  protected List<Registro> registro = new ArrayList<Registro>();

  /**
   * @deprecated Use method getRegistroList
   * @return List<Registro>
  */
  @Deprecated
  public List<Registro> getRegistro() {
  	return this.registro;
  }

  /**
   * @deprecated Use method setRegistroList
   * @param registro List<Registro>
  */
  @Deprecated
  public void setRegistro(List<Registro> registro) {
  	this.registro=registro;
  }

  /**
   * @deprecated Use method sizeRegistroList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeRegistro() {
  	return this.registro.size();
  }

  @XmlElement(name="RegistroProperty",required=true,nillable=false)
  protected List<RegistroProperty> registroProperty = new ArrayList<RegistroProperty>();

  /**
   * @deprecated Use method getRegistroPropertyList
   * @return List<RegistroProperty>
  */
  @Deprecated
  public List<RegistroProperty> getRegistroProperty() {
  	return this.registroProperty;
  }

  /**
   * @deprecated Use method setRegistroPropertyList
   * @param registroProperty List<RegistroProperty>
  */
  @Deprecated
  public void setRegistroProperty(List<RegistroProperty> registroProperty) {
  	this.registroProperty=registroProperty;
  }

  /**
   * @deprecated Use method sizeRegistroPropertyList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeRegistroProperty() {
  	return this.registroProperty.size();
  }

  @XmlElement(name="Utente",required=true,nillable=false)
  protected List<Utente> utente = new ArrayList<Utente>();

  /**
   * @deprecated Use method getUtenteList
   * @return List<Utente>
  */
  @Deprecated
  public List<Utente> getUtente() {
  	return this.utente;
  }

  /**
   * @deprecated Use method setUtenteList
   * @param utente List<Utente>
  */
  @Deprecated
  public void setUtente(List<Utente> utente) {
  	this.utente=utente;
  }

  /**
   * @deprecated Use method sizeUtenteList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeUtente() {
  	return this.utente.size();
  }

  @XmlElement(name="NotificaEsitoCommittente",required=true,nillable=false)
  protected List<NotificaEsitoCommittente> notificaEsitoCommittente = new ArrayList<NotificaEsitoCommittente>();

  /**
   * @deprecated Use method getNotificaEsitoCommittenteList
   * @return List<NotificaEsitoCommittente>
  */
  @Deprecated
  public List<NotificaEsitoCommittente> getNotificaEsitoCommittente() {
  	return this.notificaEsitoCommittente;
  }

  /**
   * @deprecated Use method setNotificaEsitoCommittenteList
   * @param notificaEsitoCommittente List<NotificaEsitoCommittente>
  */
  @Deprecated
  public void setNotificaEsitoCommittente(List<NotificaEsitoCommittente> notificaEsitoCommittente) {
  	this.notificaEsitoCommittente=notificaEsitoCommittente;
  }

  /**
   * @deprecated Use method sizeNotificaEsitoCommittenteList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeNotificaEsitoCommittente() {
  	return this.notificaEsitoCommittente.size();
  }

  @XmlElement(name="Dipartimento",required=true,nillable=false)
  protected List<Dipartimento> dipartimento = new ArrayList<Dipartimento>();

  /**
   * @deprecated Use method getDipartimentoList
   * @return List<Dipartimento>
  */
  @Deprecated
  public List<Dipartimento> getDipartimento() {
  	return this.dipartimento;
  }

  /**
   * @deprecated Use method setDipartimentoList
   * @param dipartimento List<Dipartimento>
  */
  @Deprecated
  public void setDipartimento(List<Dipartimento> dipartimento) {
  	this.dipartimento=dipartimento;
  }

  /**
   * @deprecated Use method sizeDipartimentoList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeDipartimento() {
  	return this.dipartimento.size();
  }

  @XmlElement(name="DipartimentoProperty",required=true,nillable=false)
  protected List<DipartimentoProperty> dipartimentoProperty = new ArrayList<DipartimentoProperty>();

  /**
   * @deprecated Use method getDipartimentoPropertyList
   * @return List<DipartimentoProperty>
  */
  @Deprecated
  public List<DipartimentoProperty> getDipartimentoProperty() {
  	return this.dipartimentoProperty;
  }

  /**
   * @deprecated Use method setDipartimentoPropertyList
   * @param dipartimentoProperty List<DipartimentoProperty>
  */
  @Deprecated
  public void setDipartimentoProperty(List<DipartimentoProperty> dipartimentoProperty) {
  	this.dipartimentoProperty=dipartimentoProperty;
  }

  /**
   * @deprecated Use method sizeDipartimentoPropertyList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeDipartimentoProperty() {
  	return this.dipartimentoProperty.size();
  }

}
