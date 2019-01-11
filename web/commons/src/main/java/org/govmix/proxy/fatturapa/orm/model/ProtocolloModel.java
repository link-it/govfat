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

import org.govmix.proxy.fatturapa.orm.Protocollo;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model Protocollo 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ProtocolloModel extends AbstractModel<Protocollo> {

	public ProtocolloModel(){
	
		super();
	
		this.NOME = new Field("nome",java.lang.String.class,"Protocollo",Protocollo.class);
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"Protocollo",Protocollo.class);
		this.ENDPOINT = new Field("endpoint",java.net.URI.class,"Protocollo",Protocollo.class);
	
	}
	
	public ProtocolloModel(IField father){
	
		super(father);
	
		this.NOME = new ComplexField(father,"nome",java.lang.String.class,"Protocollo",Protocollo.class);
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"Protocollo",Protocollo.class);
		this.ENDPOINT = new ComplexField(father,"endpoint",java.net.URI.class,"Protocollo",Protocollo.class);
	
	}
	
	

	public IField NOME = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField ENDPOINT = null;
	 

	@Override
	public Class<Protocollo> getModeledClass(){
		return Protocollo.class;
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