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
package org.govmix.proxy.fatturapa.orm.model;

import org.govmix.proxy.fatturapa.orm.IdRegistroProperty;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model IdRegistroProperty 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class IdRegistroPropertyModel extends AbstractModel<IdRegistroProperty> {

	public IdRegistroPropertyModel(){
	
		super();
	
		this.NOME = new Field("nome",java.lang.String.class,"id-registro-property",IdRegistroProperty.class);
		this.ID_PROTOCOLLO = new org.govmix.proxy.fatturapa.orm.model.IdProtocolloModel(new Field("id-protocollo",org.govmix.proxy.fatturapa.orm.IdProtocollo.class,"id-registro-property",IdRegistroProperty.class));
	
	}
	
	public IdRegistroPropertyModel(IField father){
	
		super(father);
	
		this.NOME = new ComplexField(father,"nome",java.lang.String.class,"id-registro-property",IdRegistroProperty.class);
		this.ID_PROTOCOLLO = new org.govmix.proxy.fatturapa.orm.model.IdProtocolloModel(new ComplexField(father,"id-protocollo",org.govmix.proxy.fatturapa.orm.IdProtocollo.class,"id-registro-property",IdRegistroProperty.class));
	
	}
	
	

	public IField NOME = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdProtocolloModel ID_PROTOCOLLO = null;
	 

	@Override
	public Class<IdRegistroProperty> getModeledClass(){
		return IdRegistroProperty.class;
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