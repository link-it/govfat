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

import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model DipartimentoPropertyValue 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DipartimentoPropertyValueModel extends AbstractModel<DipartimentoPropertyValue> {

	public DipartimentoPropertyValueModel(){
	
		super();
	
		this.VALORE = new Field("valore",java.lang.String.class,"DipartimentoPropertyValue",DipartimentoPropertyValue.class);
		this.ID_PROPERTY = new org.govmix.proxy.fatturapa.model.IdPropertyModel(new Field("id-property",org.govmix.proxy.fatturapa.IdProperty.class,"DipartimentoPropertyValue",DipartimentoPropertyValue.class));
	
	}
	
	public DipartimentoPropertyValueModel(IField father){
	
		super(father);
	
		this.VALORE = new ComplexField(father,"valore",java.lang.String.class,"DipartimentoPropertyValue",DipartimentoPropertyValue.class);
		this.ID_PROPERTY = new org.govmix.proxy.fatturapa.model.IdPropertyModel(new ComplexField(father,"id-property",org.govmix.proxy.fatturapa.IdProperty.class,"DipartimentoPropertyValue",DipartimentoPropertyValue.class));
	
	}
	
	

	public IField VALORE = null;
	 
	public org.govmix.proxy.fatturapa.model.IdPropertyModel ID_PROPERTY = null;
	 

	@Override
	public Class<DipartimentoPropertyValue> getModeledClass(){
		return DipartimentoPropertyValue.class;
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