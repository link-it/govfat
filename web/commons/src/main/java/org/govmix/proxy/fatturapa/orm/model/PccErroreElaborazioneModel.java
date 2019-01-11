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
package org.govmix.proxy.fatturapa.orm.model;

import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccErroreElaborazione 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccErroreElaborazioneModel extends AbstractModel<PccErroreElaborazione> {

	public PccErroreElaborazioneModel(){
	
		super();
	
		this.ID_ESITO = new org.govmix.proxy.fatturapa.orm.model.IdTrasmissioneEsitoModel(new Field("idEsito",org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito.class,"PccErroreElaborazione",PccErroreElaborazione.class));
		this.TIPO_OPERAZIONE = new Field("tipoOperazione",java.lang.String.class,"PccErroreElaborazione",PccErroreElaborazione.class);
		this.PROGRESSIVO_OPERAZIONE = new Field("progressivoOperazione",int.class,"PccErroreElaborazione",PccErroreElaborazione.class);
		this.CODICE_ESITO = new Field("codiceEsito",java.lang.String.class,"PccErroreElaborazione",PccErroreElaborazione.class);
		this.DESCRIZIONE_ESITO = new Field("descrizioneEsito",java.lang.String.class,"PccErroreElaborazione",PccErroreElaborazione.class);
	
	}
	
	public PccErroreElaborazioneModel(IField father){
	
		super(father);
	
		this.ID_ESITO = new org.govmix.proxy.fatturapa.orm.model.IdTrasmissioneEsitoModel(new ComplexField(father,"idEsito",org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito.class,"PccErroreElaborazione",PccErroreElaborazione.class));
		this.TIPO_OPERAZIONE = new ComplexField(father,"tipoOperazione",java.lang.String.class,"PccErroreElaborazione",PccErroreElaborazione.class);
		this.PROGRESSIVO_OPERAZIONE = new ComplexField(father,"progressivoOperazione",int.class,"PccErroreElaborazione",PccErroreElaborazione.class);
		this.CODICE_ESITO = new ComplexField(father,"codiceEsito",java.lang.String.class,"PccErroreElaborazione",PccErroreElaborazione.class);
		this.DESCRIZIONE_ESITO = new ComplexField(father,"descrizioneEsito",java.lang.String.class,"PccErroreElaborazione",PccErroreElaborazione.class);
	
	}
	
	

	public org.govmix.proxy.fatturapa.orm.model.IdTrasmissioneEsitoModel ID_ESITO = null;
	 
	public IField TIPO_OPERAZIONE = null;
	 
	public IField PROGRESSIVO_OPERAZIONE = null;
	 
	public IField CODICE_ESITO = null;
	 
	public IField DESCRIZIONE_ESITO = null;
	 

	@Override
	public Class<PccErroreElaborazione> getModeledClass(){
		return PccErroreElaborazione.class;
	}
	
	@Override
	public String toString(){
		if(this.getModeledClass()!=null){
			return this.getModeledClass().getName();
		}else{
			return "N.D.";
		}
	}

}