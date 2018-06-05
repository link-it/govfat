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

import org.govmix.proxy.fatturapa.orm.IdFattura;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model IdFattura 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class IdFatturaModel extends AbstractModel<IdFattura> {

	public IdFatturaModel(){
	
		super();
	
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Integer.class,"id-fattura",IdFattura.class);
		this.POSIZIONE = new Field("posizione",java.lang.Integer.class,"id-fattura",IdFattura.class);
		this.FATTURAZIONE_ATTIVA = new Field("fatturazioneAttiva",boolean.class,"id-fattura",IdFattura.class);
	
	}
	
	public IdFatturaModel(IField father){
	
		super(father);
	
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Integer.class,"id-fattura",IdFattura.class);
		this.POSIZIONE = new ComplexField(father,"posizione",java.lang.Integer.class,"id-fattura",IdFattura.class);
		this.FATTURAZIONE_ATTIVA = new ComplexField(father,"fatturazioneAttiva",boolean.class,"id-fattura",IdFattura.class);
	
	}
	
	

	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField POSIZIONE = null;
	 
	public IField FATTURAZIONE_ATTIVA = null;
	 

	@Override
	public Class<IdFattura> getModeledClass(){
		return IdFattura.class;
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