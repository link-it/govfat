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
package org.govmix.proxy.fatturapa.model;

import org.govmix.proxy.fatturapa.UtenteDipartimento;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model UtenteDipartimento 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class UtenteDipartimentoModel extends AbstractModel<UtenteDipartimento> {

	public UtenteDipartimentoModel(){
	
		super();
	
		this.ID_DIPARTIMENTO = new org.govmix.proxy.fatturapa.model.IdDipartimentoModel(new Field("idDipartimento",org.govmix.proxy.fatturapa.IdDipartimento.class,"UtenteDipartimento",UtenteDipartimento.class));
	
	}
	
	public UtenteDipartimentoModel(IField father){
	
		super(father);
	
		this.ID_DIPARTIMENTO = new org.govmix.proxy.fatturapa.model.IdDipartimentoModel(new ComplexField(father,"idDipartimento",org.govmix.proxy.fatturapa.IdDipartimento.class,"UtenteDipartimento",UtenteDipartimento.class));
	
	}
	
	

	public org.govmix.proxy.fatturapa.model.IdDipartimentoModel ID_DIPARTIMENTO = null;
	 

	@Override
	public Class<UtenteDipartimento> getModeledClass(){
		return UtenteDipartimento.class;
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