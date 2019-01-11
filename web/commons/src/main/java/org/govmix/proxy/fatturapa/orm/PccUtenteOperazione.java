/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.io.Serializable;


/** <p>Java class for PccUtenteOperazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccUtenteOperazione">
 * 		&lt;sequence>
 * 			&lt;element name="idUtente" type="{http://www.govmix.org/proxy/fatturapa/orm}id-utente" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idOperazione" type="{http://www.govmix.org/proxy/fatturapa/orm}id-operazione" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "PccUtenteOperazione", 
  propOrder = {
  	"idUtente",
  	"idOperazione"
  }
)

@XmlRootElement(name = "PccUtenteOperazione")

public class PccUtenteOperazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccUtenteOperazione() {
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

  public IdUtente getIdUtente() {
    return this.idUtente;
  }

  public void setIdUtente(IdUtente idUtente) {
    this.idUtente = idUtente;
  }

  public IdOperazione getIdOperazione() {
    return this.idOperazione;
  }

  public void setIdOperazione(IdOperazione idOperazione) {
    this.idOperazione = idOperazione;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static org.govmix.proxy.fatturapa.orm.model.PccUtenteOperazioneModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccUtenteOperazione.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccUtenteOperazione.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccUtenteOperazioneModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccUtenteOperazioneModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccUtenteOperazione.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccUtenteOperazione.modelStaticInstance;
  }


  @XmlElement(name="idUtente",required=true,nillable=false)
  protected IdUtente idUtente;

  @XmlElement(name="idOperazione",required=true,nillable=false)
  protected IdOperazione idOperazione;

}
