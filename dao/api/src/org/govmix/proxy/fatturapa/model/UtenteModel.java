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

import org.govmix.proxy.fatturapa.Utente;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model Utente 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class UtenteModel extends AbstractModel<Utente> {

	public UtenteModel(){
	
		super();
	
		this.USERNAME = new Field("username",java.lang.String.class,"Utente",Utente.class);
		this.PASSWORD = new Field("password",java.lang.String.class,"Utente",Utente.class);
		this.NOME = new Field("nome",java.lang.String.class,"Utente",Utente.class);
		this.COGNOME = new Field("cognome",java.lang.String.class,"Utente",Utente.class);
		this.ROLE = new Field("role",java.lang.String.class,"Utente",Utente.class);
		this.ENTE = new org.govmix.proxy.fatturapa.model.IdEnteModel(new Field("ente",org.govmix.proxy.fatturapa.IdEnte.class,"Utente",Utente.class));
		this.UTENTE_DIPARTIMENTO = new org.govmix.proxy.fatturapa.model.UtenteDipartimentoModel(new Field("UtenteDipartimento",org.govmix.proxy.fatturapa.UtenteDipartimento.class,"Utente",Utente.class));
	
	}
	
	public UtenteModel(IField father){
	
		super(father);
	
		this.USERNAME = new ComplexField(father,"username",java.lang.String.class,"Utente",Utente.class);
		this.PASSWORD = new ComplexField(father,"password",java.lang.String.class,"Utente",Utente.class);
		this.NOME = new ComplexField(father,"nome",java.lang.String.class,"Utente",Utente.class);
		this.COGNOME = new ComplexField(father,"cognome",java.lang.String.class,"Utente",Utente.class);
		this.ROLE = new ComplexField(father,"role",java.lang.String.class,"Utente",Utente.class);
		this.ENTE = new org.govmix.proxy.fatturapa.model.IdEnteModel(new ComplexField(father,"ente",org.govmix.proxy.fatturapa.IdEnte.class,"Utente",Utente.class));
		this.UTENTE_DIPARTIMENTO = new org.govmix.proxy.fatturapa.model.UtenteDipartimentoModel(new ComplexField(father,"UtenteDipartimento",org.govmix.proxy.fatturapa.UtenteDipartimento.class,"Utente",Utente.class));
	
	}
	
	

	public IField USERNAME = null;
	 
	public IField PASSWORD = null;
	 
	public IField NOME = null;
	 
	public IField COGNOME = null;
	 
	public IField ROLE = null;
	 
	public org.govmix.proxy.fatturapa.model.IdEnteModel ENTE = null;
	 
	public org.govmix.proxy.fatturapa.model.UtenteDipartimentoModel UTENTE_DIPARTIMENTO = null;
	 

	@Override
	public Class<Utente> getModeledClass(){
		return Utente.class;
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