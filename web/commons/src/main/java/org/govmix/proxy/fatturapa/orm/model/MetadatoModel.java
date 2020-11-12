/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.orm.Metadato;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model Metadato 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class MetadatoModel extends AbstractModel<Metadato> {

	public MetadatoModel(){
	
		super();
	
		this.RICHIESTA = new Field("richiesta",boolean.class,"Metadato",Metadato.class);
		this.NOME = new Field("nome",java.lang.String.class,"Metadato",Metadato.class);
		this.VALORE = new Field("valore",java.lang.String.class,"Metadato",Metadato.class);
	
	}
	
	public MetadatoModel(IField father){
	
		super(father);
	
		this.RICHIESTA = new ComplexField(father,"richiesta",boolean.class,"Metadato",Metadato.class);
		this.NOME = new ComplexField(father,"nome",java.lang.String.class,"Metadato",Metadato.class);
		this.VALORE = new ComplexField(father,"valore",java.lang.String.class,"Metadato",Metadato.class);
	
	}
	
	

	public IField RICHIESTA = null;
	 
	public IField NOME = null;
	 
	public IField VALORE = null;
	 

	@Override
	public Class<Metadato> getModeledClass(){
		return Metadato.class;
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