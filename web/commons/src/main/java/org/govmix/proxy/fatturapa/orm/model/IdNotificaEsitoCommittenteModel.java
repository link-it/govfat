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

import org.govmix.proxy.fatturapa.orm.IdNotificaEsitoCommittente;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model IdNotificaEsitoCommittente 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class IdNotificaEsitoCommittenteModel extends AbstractModel<IdNotificaEsitoCommittente> {

	public IdNotificaEsitoCommittenteModel(){
	
		super();
	
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Long.class,"id-notifica-esito-committente",IdNotificaEsitoCommittente.class);
		this.POSIZIONE = new Field("posizione",java.lang.Integer.class,"id-notifica-esito-committente",IdNotificaEsitoCommittente.class);
		this.MOTIVI_RIFIUTO = new Field("motiviRifiuto",java.lang.String.class,"id-notifica-esito-committente",IdNotificaEsitoCommittente.class);
	
	}
	
	public IdNotificaEsitoCommittenteModel(IField father){
	
		super(father);
	
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Long.class,"id-notifica-esito-committente",IdNotificaEsitoCommittente.class);
		this.POSIZIONE = new ComplexField(father,"posizione",java.lang.Integer.class,"id-notifica-esito-committente",IdNotificaEsitoCommittente.class);
		this.MOTIVI_RIFIUTO = new ComplexField(father,"motiviRifiuto",java.lang.String.class,"id-notifica-esito-committente",IdNotificaEsitoCommittente.class);
	
	}
	
	

	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField POSIZIONE = null;
	 
	public IField MOTIVI_RIFIUTO = null;
	 

	@Override
	public Class<IdNotificaEsitoCommittente> getModeledClass(){
		return IdNotificaEsitoCommittente.class;
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