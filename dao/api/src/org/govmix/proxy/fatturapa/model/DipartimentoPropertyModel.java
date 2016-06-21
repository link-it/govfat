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

import org.govmix.proxy.fatturapa.DipartimentoProperty;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model DipartimentoProperty 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DipartimentoPropertyModel extends AbstractModel<DipartimentoProperty> {

	public DipartimentoPropertyModel(){
	
		super();
	
		this.NOME = new Field("nome",java.lang.String.class,"DipartimentoProperty",DipartimentoProperty.class);
		this.LABEL = new Field("label",java.lang.String.class,"DipartimentoProperty",DipartimentoProperty.class);
		this.ID_ENTE = new org.govmix.proxy.fatturapa.model.IdEnteModel(new Field("idEnte",org.govmix.proxy.fatturapa.IdEnte.class,"DipartimentoProperty",DipartimentoProperty.class));
	
	}
	
	public DipartimentoPropertyModel(IField father){
	
		super(father);
	
		this.NOME = new ComplexField(father,"nome",java.lang.String.class,"DipartimentoProperty",DipartimentoProperty.class);
		this.LABEL = new ComplexField(father,"label",java.lang.String.class,"DipartimentoProperty",DipartimentoProperty.class);
		this.ID_ENTE = new org.govmix.proxy.fatturapa.model.IdEnteModel(new ComplexField(father,"idEnte",org.govmix.proxy.fatturapa.IdEnte.class,"DipartimentoProperty",DipartimentoProperty.class));
	
	}
	
	

	public IField NOME = null;
	 
	public IField LABEL = null;
	 
	public org.govmix.proxy.fatturapa.model.IdEnteModel ID_ENTE = null;
	 

	@Override
	public Class<DipartimentoProperty> getModeledClass(){
		return DipartimentoProperty.class;
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