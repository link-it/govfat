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

import org.govmix.proxy.fatturapa.orm.SIP;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model SIP 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class SIPModel extends AbstractModel<SIP> {

	public SIPModel(){
	
		super();
	
		this.REGISTRO = new Field("registro",java.lang.String.class,"SIP",SIP.class);
		this.ANNO = new Field("anno",int.class,"SIP",SIP.class);
		this.NUMERO = new Field("numero",java.lang.String.class,"SIP",SIP.class);
		this.STATO_CONSEGNA = new Field("statoConsegna",java.lang.String.class,"SIP",SIP.class);
		this.ERRORE_TIMEOUT = new Field("erroreTimeout",boolean.class,"SIP",SIP.class);
		this.DATA_ULTIMA_CONSEGNA = new Field("dataUltimaConsegna",java.util.Date.class,"SIP",SIP.class);
		this.RAPPORTO_VERSAMENTO = new Field("rapportoVersamento",java.lang.String.class,"SIP",SIP.class);
	
	}
	
	public SIPModel(IField father){
	
		super(father);
	
		this.REGISTRO = new ComplexField(father,"registro",java.lang.String.class,"SIP",SIP.class);
		this.ANNO = new ComplexField(father,"anno",int.class,"SIP",SIP.class);
		this.NUMERO = new ComplexField(father,"numero",java.lang.String.class,"SIP",SIP.class);
		this.STATO_CONSEGNA = new ComplexField(father,"statoConsegna",java.lang.String.class,"SIP",SIP.class);
		this.ERRORE_TIMEOUT = new ComplexField(father,"erroreTimeout",boolean.class,"SIP",SIP.class);
		this.DATA_ULTIMA_CONSEGNA = new ComplexField(father,"dataUltimaConsegna",java.util.Date.class,"SIP",SIP.class);
		this.RAPPORTO_VERSAMENTO = new ComplexField(father,"rapportoVersamento",java.lang.String.class,"SIP",SIP.class);
	
	}
	
	

	public IField REGISTRO = null;
	 
	public IField ANNO = null;
	 
	public IField NUMERO = null;
	 
	public IField STATO_CONSEGNA = null;
	 
	public IField ERRORE_TIMEOUT = null;
	 
	public IField DATA_ULTIMA_CONSEGNA = null;
	 
	public IField RAPPORTO_VERSAMENTO = null;
	 

	@Override
	public Class<SIP> getModeledClass(){
		return SIP.class;
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