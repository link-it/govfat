/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.orm.RegistroPropertyValue;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model RegistroPropertyValue 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class RegistroPropertyValueModel extends AbstractModel<RegistroPropertyValue> {

	public RegistroPropertyValueModel(){
	
		super();
	
		this.VALORE = new Field("valore",java.lang.String.class,"RegistroPropertyValue",RegistroPropertyValue.class);
		this.ID_PROPERTY = new org.govmix.proxy.fatturapa.orm.model.IdRegistroPropertyModel(new Field("id-property",org.govmix.proxy.fatturapa.orm.IdRegistroProperty.class,"RegistroPropertyValue",RegistroPropertyValue.class));
	
	}
	
	public RegistroPropertyValueModel(IField father){
	
		super(father);
	
		this.VALORE = new ComplexField(father,"valore",java.lang.String.class,"RegistroPropertyValue",RegistroPropertyValue.class);
		this.ID_PROPERTY = new org.govmix.proxy.fatturapa.orm.model.IdRegistroPropertyModel(new ComplexField(father,"id-property",org.govmix.proxy.fatturapa.orm.IdRegistroProperty.class,"RegistroPropertyValue",RegistroPropertyValue.class));
	
	}
	
	

	public IField VALORE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdRegistroPropertyModel ID_PROPERTY = null;
	 

	@Override
	public Class<RegistroPropertyValue> getModeledClass(){
		return RegistroPropertyValue.class;
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