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
package org.govmix.proxy.fatturapa.orm.model;

import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccDipartimentoOperazione 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccDipartimentoOperazioneModel extends AbstractModel<PccDipartimentoOperazione> {

	public PccDipartimentoOperazioneModel(){
	
		super();
	
		this.ID_DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.IdDipartimentoModel(new Field("idDipartimento",org.govmix.proxy.fatturapa.orm.IdDipartimento.class,"PccDipartimentoOperazione",PccDipartimentoOperazione.class));
		this.ID_OPERAZIONE = new org.govmix.proxy.fatturapa.orm.model.IdOperazioneModel(new Field("idOperazione",org.govmix.proxy.fatturapa.orm.IdOperazione.class,"PccDipartimentoOperazione",PccDipartimentoOperazione.class));
	
	}
	
	public PccDipartimentoOperazioneModel(IField father){
	
		super(father);
	
		this.ID_DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.IdDipartimentoModel(new ComplexField(father,"idDipartimento",org.govmix.proxy.fatturapa.orm.IdDipartimento.class,"PccDipartimentoOperazione",PccDipartimentoOperazione.class));
		this.ID_OPERAZIONE = new org.govmix.proxy.fatturapa.orm.model.IdOperazioneModel(new ComplexField(father,"idOperazione",org.govmix.proxy.fatturapa.orm.IdOperazione.class,"PccDipartimentoOperazione",PccDipartimentoOperazione.class));
	
	}
	
	

	public org.govmix.proxy.fatturapa.orm.model.IdDipartimentoModel ID_DIPARTIMENTO = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdOperazioneModel ID_OPERAZIONE = null;
	 

	@Override
	public Class<PccDipartimentoOperazione> getModeledClass(){
		return PccDipartimentoOperazione.class;
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