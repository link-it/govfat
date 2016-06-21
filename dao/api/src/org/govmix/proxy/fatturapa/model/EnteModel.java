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

import org.govmix.proxy.fatturapa.Ente;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model Ente 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class EnteModel extends AbstractModel<Ente> {

	public EnteModel(){
	
		super();
	
		this.NOME = new Field("nome",java.lang.String.class,"Ente",Ente.class);
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"Ente",Ente.class);
		this.ENDPOINT = new Field("endpoint",java.net.URI.class,"Ente",Ente.class);
		this.ENDPOINT_CONSEGNA_LOTTO = new Field("endpointConsegnaLotto",java.net.URI.class,"Ente",Ente.class);
		this.ENDPOINT_RICHIEDI_PROTOCOLLO = new Field("endpointRichiediProtocollo",java.net.URI.class,"Ente",Ente.class);
	
	}
	
	public EnteModel(IField father){
	
		super(father);
	
		this.NOME = new ComplexField(father,"nome",java.lang.String.class,"Ente",Ente.class);
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"Ente",Ente.class);
		this.ENDPOINT = new ComplexField(father,"endpoint",java.net.URI.class,"Ente",Ente.class);
		this.ENDPOINT_CONSEGNA_LOTTO = new ComplexField(father,"endpointConsegnaLotto",java.net.URI.class,"Ente",Ente.class);
		this.ENDPOINT_RICHIEDI_PROTOCOLLO = new ComplexField(father,"endpointRichiediProtocollo",java.net.URI.class,"Ente",Ente.class);
	
	}
	
	

	public IField NOME = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField ENDPOINT = null;
	 
	public IField ENDPOINT_CONSEGNA_LOTTO = null;
	 
	public IField ENDPOINT_RICHIEDI_PROTOCOLLO = null;
	 

	@Override
	public Class<Ente> getModeledClass(){
		return Ente.class;
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