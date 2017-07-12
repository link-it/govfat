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
import java.io.Serializable;


/** <p>Java class for PccDipartimentoOperazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PccDipartimentoOperazione">
 * 		&lt;sequence>
 * 			&lt;element name="idDipartimento" type="{http://www.govmix.org/proxy/fatturapa/orm}id-dipartimento" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "PccDipartimentoOperazione", 
  propOrder = {
  	"idDipartimento",
  	"idOperazione"
  }
)

@XmlRootElement(name = "PccDipartimentoOperazione")

public class PccDipartimentoOperazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PccDipartimentoOperazione() {
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

  public IdDipartimento getIdDipartimento() {
    return this.idDipartimento;
  }

  public void setIdDipartimento(IdDipartimento idDipartimento) {
    this.idDipartimento = idDipartimento;
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

  private static org.govmix.proxy.fatturapa.orm.model.PccDipartimentoOperazioneModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione.modelStaticInstance==null){
  			org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione.modelStaticInstance = new org.govmix.proxy.fatturapa.orm.model.PccDipartimentoOperazioneModel();
	  }
  }
  public static org.govmix.proxy.fatturapa.orm.model.PccDipartimentoOperazioneModel model(){
	  if(org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione.modelStaticInstance;
  }


  @XmlElement(name="idDipartimento",required=true,nillable=false)
  protected IdDipartimento idDipartimento;

  @XmlElement(name="idOperazione",required=true,nillable=false)
  protected IdOperazione idOperazione;

}
