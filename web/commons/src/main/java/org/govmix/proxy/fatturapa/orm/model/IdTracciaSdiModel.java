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

import org.govmix.proxy.fatturapa.orm.IdTracciaSdi;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model IdTracciaSdi 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class IdTracciaSdiModel extends AbstractModel<IdTracciaSdi> {

	public IdTracciaSdiModel(){
	
		super();
	
		this.ID_TRACCIA = new Field("idTraccia",long.class,"id-traccia-sdi",IdTracciaSdi.class);
		this.STATO_PROTOCOLLAZIONE = new Field("statoProtocollazione",java.lang.String.class,"id-traccia-sdi",IdTracciaSdi.class);
		this.DATA_PROSSIMA_PROTOCOLLAZIONE = new Field("dataProssimaProtocollazione",java.util.Date.class,"id-traccia-sdi",IdTracciaSdi.class);
	
	}
	
	public IdTracciaSdiModel(IField father){
	
		super(father);
	
		this.ID_TRACCIA = new ComplexField(father,"idTraccia",long.class,"id-traccia-sdi",IdTracciaSdi.class);
		this.STATO_PROTOCOLLAZIONE = new ComplexField(father,"statoProtocollazione",java.lang.String.class,"id-traccia-sdi",IdTracciaSdi.class);
		this.DATA_PROSSIMA_PROTOCOLLAZIONE = new ComplexField(father,"dataProssimaProtocollazione",java.util.Date.class,"id-traccia-sdi",IdTracciaSdi.class);
	
	}
	
	

	public IField ID_TRACCIA = null;
	 
	public IField STATO_PROTOCOLLAZIONE = null;
	 
	public IField DATA_PROSSIMA_PROTOCOLLAZIONE = null;
	 

	@Override
	public Class<IdTracciaSdi> getModeledClass(){
		return IdTracciaSdi.class;
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