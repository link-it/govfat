/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm.constants;

import java.io.Serializable;
import java.util.List;

import org.openspcoop2.generic_project.beans.IEnumeration;
import org.openspcoop2.generic_project.exception.NotFoundException;

/**     
 * Enumeration dell'elemento LottoFatture.statoElaborazioneInUscita xsd (tipo:string) 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@javax.xml.bind.annotation.XmlType(name = "StatoElaborazioneType")
@javax.xml.bind.annotation.XmlEnum(String.class)
public enum StatoElaborazioneType implements IEnumeration , Serializable , Cloneable {

	@javax.xml.bind.annotation.XmlEnumValue("NON_FIRMATO")
	NON_FIRMATO ("NON_FIRMATO"),
	@javax.xml.bind.annotation.XmlEnumValue("FIRMA_IN_PROGRESS")
	FIRMA_IN_PROGRESS ("FIRMA_IN_PROGRESS"),
	@javax.xml.bind.annotation.XmlEnumValue("ERRORE_FIRMA")
	ERRORE_FIRMA ("ERRORE_FIRMA"),
	@javax.xml.bind.annotation.XmlEnumValue("FIRMA_OK")
	FIRMA_OK ("FIRMA_OK"),
	@javax.xml.bind.annotation.XmlEnumValue("PROTOCOLLAZIONE_IN_PROGRESS")
	PROTOCOLLAZIONE_IN_PROGRESS ("PROTOCOLLAZIONE_IN_PROGRESS"),
	@javax.xml.bind.annotation.XmlEnumValue("ERRORE_PROTOCOLLAZIONE")
	ERRORE_PROTOCOLLAZIONE ("ERRORE_PROTOCOLLAZIONE"),
	@javax.xml.bind.annotation.XmlEnumValue("PROTOCOLLAZIONE_OK")
	PROTOCOLLAZIONE_OK ("PROTOCOLLAZIONE_OK"),
	@javax.xml.bind.annotation.XmlEnumValue("ERRORE_SPEDIZIONE")
	ERRORE_SPEDIZIONE ("ERRORE_SPEDIZIONE"),
	@javax.xml.bind.annotation.XmlEnumValue("SPEDIZIONE_OK")
	SPEDIZIONE_OK ("SPEDIZIONE_OK"),
	@javax.xml.bind.annotation.XmlEnumValue("SPEDIZIONE_NON_ATTIVA")
	SPEDIZIONE_NON_ATTIVA ("SPEDIZIONE_NON_ATTIVA");
	
	
	/** Value */
	private String value;
	@Override
	public String getValue()
	{
		return this.value;
	}


	/** Official Constructor */
	StatoElaborazioneType(String value)
	{
		this.value = value;
	}


	
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(StatoElaborazioneType object){
		if(object==null)
			return false;
		if(object.getValue()==null)
			return false;
		return object.getValue().equals(this.getValue());	
	}
	public boolean equals(String object){
		if(object==null)
			return false;
		return object.equals(this.getValue());	
	}
	
		
	
	/** compatibility with the generated bean (reflection) */
	public boolean equals(Object object,List<String> fieldsNotCheck){
		if( !(object instanceof StatoElaborazioneType) ){
			throw new RuntimeException("Wrong type: "+object.getClass().getName());
		}
		return this.equals(((StatoElaborazioneType)object));
	}
	public String toString(boolean reportHTML){
		return toString();
	}
  	public String toString(boolean reportHTML,List<String> fieldsNotIncluded){
  		return toString();
  	}
  	public String diff(Object object,StringBuffer bf,boolean reportHTML){
		return bf.toString();
	}
	public String diff(Object object,StringBuffer bf,boolean reportHTML,List<String> fieldsNotIncluded){
		return bf.toString();
	}
	
	
	/** Utilities */
	
	public static String[] toArray(){
		String[] res = new String[values().length];
		int i=0;
		for (StatoElaborazioneType tmp : values()) {
			res[i]=tmp.getValue();
			i++;
		}
		return res;
	}	
	public static String[] toStringArray(){
		String[] res = new String[values().length];
		int i=0;
		for (StatoElaborazioneType tmp : values()) {
			res[i]=tmp.toString();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[values().length];
		int i=0;
		for (StatoElaborazioneType tmp : values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}
	
	public static boolean contains(String value){
		return toEnumConstant(value)!=null;
	}
	
	public static StatoElaborazioneType toEnumConstant(String value){
		try{
			return toEnumConstant(value,false);
		}catch(NotFoundException notFound){
			return null;
		}
	}
	public static StatoElaborazioneType toEnumConstant(String value, boolean throwNotFoundException) throws NotFoundException{
		StatoElaborazioneType res = null;
		for (StatoElaborazioneType tmp : values()) {
			if(tmp.getValue().equals(value)){
				res = tmp;
				break;
			}
		}
		if(res==null && throwNotFoundException){
			throw new NotFoundException("Enum with value ["+value+"] not found");
		}
		return res;
	}
	
	public static IEnumeration toEnumConstantFromString(String value){
		try{
			return toEnumConstantFromString(value,false);
		}catch(NotFoundException notFound){
			return null;
		}
	}
	public static IEnumeration toEnumConstantFromString(String value, boolean throwNotFoundException) throws NotFoundException{
		StatoElaborazioneType res = null;
		for (StatoElaborazioneType tmp : values()) {
			if(tmp.toString().equals(value)){
				res = tmp;
				break;
			}
		}
		if(res==null && throwNotFoundException){
			throw new NotFoundException("Enum with value ["+value+"] not found");
		}
		return res;
	}
}
