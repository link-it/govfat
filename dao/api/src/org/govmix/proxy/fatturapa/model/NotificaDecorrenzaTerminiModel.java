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

import org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model NotificaDecorrenzaTermini 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class NotificaDecorrenzaTerminiModel extends AbstractModel<NotificaDecorrenzaTermini> {

	public NotificaDecorrenzaTerminiModel(){
	
		super();
	
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Integer.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOME_FILE = new Field("nomeFile",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.MESSAGE_ID = new Field("messageId",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOTE = new Field("note",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DATA_RICEZIONE = new Field("dataRicezione",java.util.Date.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.XML = new Field("xml",byte[].class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
	
	}
	
	public NotificaDecorrenzaTerminiModel(IField father){
	
		super(father);
	
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Integer.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOME_FILE = new ComplexField(father,"nomeFile",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.MESSAGE_ID = new ComplexField(father,"messageId",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOTE = new ComplexField(father,"note",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DATA_RICEZIONE = new ComplexField(father,"dataRicezione",java.util.Date.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.XML = new ComplexField(father,"xml",byte[].class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
	
	}
	
	

	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField NOME_FILE = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField MESSAGE_ID = null;
	 
	public IField NOTE = null;
	 
	public IField DATA_RICEZIONE = null;
	 
	public IField XML = null;
	 

	@Override
	public Class<NotificaDecorrenzaTermini> getModeledClass(){
		return NotificaDecorrenzaTermini.class;
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