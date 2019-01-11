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

import org.govmix.proxy.fatturapa.orm.IdSip;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model IdSip 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class IdSipModel extends AbstractModel<IdSip> {

	public IdSipModel(){
	
		super();
	
		this.ID_SIP = new Field("idSip",long.class,"id-sip",IdSip.class);
		this.STATO_CONSEGNA = new Field("statoConsegna",java.lang.String.class,"id-sip",IdSip.class);
		this.DATA_ULTIMA_CONSEGNA = new Field("dataUltimaConsegna",java.util.Date.class,"id-sip",IdSip.class);
	
	}
	
	public IdSipModel(IField father){
	
		super(father);
	
		this.ID_SIP = new ComplexField(father,"idSip",long.class,"id-sip",IdSip.class);
		this.STATO_CONSEGNA = new ComplexField(father,"statoConsegna",java.lang.String.class,"id-sip",IdSip.class);
		this.DATA_ULTIMA_CONSEGNA = new ComplexField(father,"dataUltimaConsegna",java.util.Date.class,"id-sip",IdSip.class);
	
	}
	
	

	public IField ID_SIP = null;
	 
	public IField STATO_CONSEGNA = null;
	 
	public IField DATA_ULTIMA_CONSEGNA = null;
	 

	@Override
	public Class<IdSip> getModeledClass(){
		return IdSip.class;
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