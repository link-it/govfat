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

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model AllegatoFattura 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class AllegatoFatturaModel extends AbstractModel<AllegatoFattura> {

	public AllegatoFatturaModel(){
	
		super();
	
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new Field("idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"AllegatoFattura",AllegatoFattura.class));
		this.NOME_ATTACHMENT = new Field("nomeAttachment",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.ALGORITMO_COMPRESSIONE = new Field("algoritmoCompressione",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.FORMATO_ATTACHMENT = new Field("formatoAttachment",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.DESCRIZIONE_ATTACHMENT = new Field("descrizioneAttachment",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.ATTACHMENT = new Field("attachment",byte[].class,"AllegatoFattura",AllegatoFattura.class);
	
	}
	
	public AllegatoFatturaModel(IField father){
	
		super(father);
	
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new ComplexField(father,"idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"AllegatoFattura",AllegatoFattura.class));
		this.NOME_ATTACHMENT = new ComplexField(father,"nomeAttachment",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.ALGORITMO_COMPRESSIONE = new ComplexField(father,"algoritmoCompressione",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.FORMATO_ATTACHMENT = new ComplexField(father,"formatoAttachment",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.DESCRIZIONE_ATTACHMENT = new ComplexField(father,"descrizioneAttachment",java.lang.String.class,"AllegatoFattura",AllegatoFattura.class);
		this.ATTACHMENT = new ComplexField(father,"attachment",byte[].class,"AllegatoFattura",AllegatoFattura.class);
	
	}
	
	

	public org.govmix.proxy.fatturapa.orm.model.IdFatturaModel ID_FATTURA = null;
	 
	public IField NOME_ATTACHMENT = null;
	 
	public IField ALGORITMO_COMPRESSIONE = null;
	 
	public IField FORMATO_ATTACHMENT = null;
	 
	public IField DESCRIZIONE_ATTACHMENT = null;
	 
	public IField ATTACHMENT = null;
	 

	@Override
	public Class<AllegatoFattura> getModeledClass(){
		return AllegatoFattura.class;
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