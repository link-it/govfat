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

import org.govmix.proxy.fatturapa.orm.PccRispedizione;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccRispedizione 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccRispedizioneModel extends AbstractModel<PccRispedizione> {

	public PccRispedizioneModel(){
	
		super();
	
		this.MAX_NUMERO_TENTATIVI = new Field("maxNumeroTentativi",int.class,"PccRispedizione",PccRispedizione.class);
		this.INTERVALLO_TENTATIVI = new Field("intervalloTentativi",int.class,"PccRispedizione",PccRispedizione.class);
		this.CODICE_ERRORE = new Field("codiceErrore",java.lang.String.class,"PccRispedizione",PccRispedizione.class);
		this.ABILITATO = new Field("abilitato",boolean.class,"PccRispedizione",PccRispedizione.class);
		this.DESCRIZIONE_ERRORE = new Field("descrizioneErrore",java.lang.String.class,"PccRispedizione",PccRispedizione.class);
		this.TIPO_ERRORE = new Field("tipoErrore",java.lang.String.class,"PccRispedizione",PccRispedizione.class);
	
	}
	
	public PccRispedizioneModel(IField father){
	
		super(father);
	
		this.MAX_NUMERO_TENTATIVI = new ComplexField(father,"maxNumeroTentativi",int.class,"PccRispedizione",PccRispedizione.class);
		this.INTERVALLO_TENTATIVI = new ComplexField(father,"intervalloTentativi",int.class,"PccRispedizione",PccRispedizione.class);
		this.CODICE_ERRORE = new ComplexField(father,"codiceErrore",java.lang.String.class,"PccRispedizione",PccRispedizione.class);
		this.ABILITATO = new ComplexField(father,"abilitato",boolean.class,"PccRispedizione",PccRispedizione.class);
		this.DESCRIZIONE_ERRORE = new ComplexField(father,"descrizioneErrore",java.lang.String.class,"PccRispedizione",PccRispedizione.class);
		this.TIPO_ERRORE = new ComplexField(father,"tipoErrore",java.lang.String.class,"PccRispedizione",PccRispedizione.class);
	
	}
	
	

	public IField MAX_NUMERO_TENTATIVI = null;
	 
	public IField INTERVALLO_TENTATIVI = null;
	 
	public IField CODICE_ERRORE = null;
	 
	public IField ABILITATO = null;
	 
	public IField DESCRIZIONE_ERRORE = null;
	 
	public IField TIPO_ERRORE = null;
	 

	@Override
	public Class<PccRispedizione> getModeledClass(){
		return PccRispedizione.class;
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