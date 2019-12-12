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

import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model NotificaDecorrenzaTermini 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class NotificaDecorrenzaTerminiModel extends AbstractModel<NotificaDecorrenzaTermini> {

	public NotificaDecorrenzaTerminiModel(){
	
		super();
	
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Long.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOME_FILE = new Field("nomeFile",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.MESSAGE_ID = new Field("messageId",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOTE = new Field("note",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DATA_RICEZIONE = new Field("dataRicezione",java.util.Date.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.ID_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.IdTracciaSdiModel(new Field("idTraccia",org.govmix.proxy.fatturapa.orm.IdTracciaSdi.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class));
	
	}
	
	public NotificaDecorrenzaTerminiModel(IField father){
	
		super(father);
	
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Long.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOME_FILE = new ComplexField(father,"nomeFile",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.MESSAGE_ID = new ComplexField(father,"messageId",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.NOTE = new ComplexField(father,"note",java.lang.String.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.DATA_RICEZIONE = new ComplexField(father,"dataRicezione",java.util.Date.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class);
		this.ID_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.IdTracciaSdiModel(new ComplexField(father,"idTraccia",org.govmix.proxy.fatturapa.orm.IdTracciaSdi.class,"NotificaDecorrenzaTermini",NotificaDecorrenzaTermini.class));
	
	}
	
	

	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField NOME_FILE = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField MESSAGE_ID = null;
	 
	public IField NOTE = null;
	 
	public IField DATA_RICEZIONE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdTracciaSdiModel ID_TRACCIA = null;
	 

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