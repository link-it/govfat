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
package org.govmix.proxy.fatturapa.constants;

import java.io.Serializable;
import java.util.List;

import org.openspcoop2.generic_project.beans.IEnumeration;

/**     
 * Enumeration dell'elemento FatturaElettronica.esito xsd (tipo:string) 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@javax.xml.bind.annotation.XmlType(name = "EsitoType")
@javax.xml.bind.annotation.XmlEnum(String.class)
public enum EsitoType implements IEnumeration , Serializable , Cloneable {

	@javax.xml.bind.annotation.XmlEnumValue("IN_ELABORAZIONE_ACCETTATO")
	IN_ELABORAZIONE_ACCETTATO ("IN_ELABORAZIONE_ACCETTATO"),
	@javax.xml.bind.annotation.XmlEnumValue("IN_ELABORAZIONE_RIFIUTATO")
	IN_ELABORAZIONE_RIFIUTATO ("IN_ELABORAZIONE_RIFIUTATO"),
	@javax.xml.bind.annotation.XmlEnumValue("INVIATA_ACCETTATO")
	INVIATA_ACCETTATO ("INVIATA_ACCETTATO"),
	@javax.xml.bind.annotation.XmlEnumValue("INVIATA_RIFIUTATO")
	INVIATA_RIFIUTATO ("INVIATA_RIFIUTATO"),
	@javax.xml.bind.annotation.XmlEnumValue("SCARTATA_ACCETTATO")
	SCARTATA_ACCETTATO ("SCARTATA_ACCETTATO"),
	@javax.xml.bind.annotation.XmlEnumValue("SCARTATA_RIFIUTATO")
	SCARTATA_RIFIUTATO ("SCARTATA_RIFIUTATO");
	
	
	/** Value */
	private String value;
	@Override
	public String getValue()
	{
		return this.value;
	}


	/** Official Constructor */
	EsitoType(String value)
	{
		this.value = value;
	}


	
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(EsitoType object){
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
		if( !(object instanceof EsitoType) ){
			throw new RuntimeException("Wrong type: "+object.getClass().getName());
		}
		return this.equals(((EsitoType)object));
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
		for (EsitoType tmp : values()) {
			res[i]=tmp.getValue();
			i++;
		}
		return res;
	}	
	public static String[] toStringArray(){
		String[] res = new String[values().length];
		int i=0;
		for (EsitoType tmp : values()) {
			res[i]=tmp.toString();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[values().length];
		int i=0;
		for (EsitoType tmp : values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}
	
	public static boolean contains(String value){
		return toEnumConstant(value)!=null;
	}
	
	public static EsitoType toEnumConstant(String value){
		EsitoType res = null;
		if(EsitoType.IN_ELABORAZIONE_ACCETTATO.getValue().equals(value)){
			res = EsitoType.IN_ELABORAZIONE_ACCETTATO;
		}else if(EsitoType.IN_ELABORAZIONE_RIFIUTATO.getValue().equals(value)){
			res = EsitoType.IN_ELABORAZIONE_RIFIUTATO;
		}else if(EsitoType.INVIATA_ACCETTATO.getValue().equals(value)){
			res = EsitoType.INVIATA_ACCETTATO;
		}else if(EsitoType.INVIATA_RIFIUTATO.getValue().equals(value)){
			res = EsitoType.INVIATA_RIFIUTATO;
		}else if(EsitoType.SCARTATA_ACCETTATO.getValue().equals(value)){
			res = EsitoType.SCARTATA_ACCETTATO;
		}else if(EsitoType.SCARTATA_RIFIUTATO.getValue().equals(value)){
			res = EsitoType.SCARTATA_RIFIUTATO;
		}
		return res;
	}
	
	public static IEnumeration toEnumConstantFromString(String value){
		EsitoType res = null;
		if(EsitoType.IN_ELABORAZIONE_ACCETTATO.toString().equals(value)){
			res = EsitoType.IN_ELABORAZIONE_ACCETTATO;
		}else if(EsitoType.IN_ELABORAZIONE_RIFIUTATO.toString().equals(value)){
			res = EsitoType.IN_ELABORAZIONE_RIFIUTATO;
		}else if(EsitoType.INVIATA_ACCETTATO.toString().equals(value)){
			res = EsitoType.INVIATA_ACCETTATO;
		}else if(EsitoType.INVIATA_RIFIUTATO.toString().equals(value)){
			res = EsitoType.INVIATA_RIFIUTATO;
		}else if(EsitoType.SCARTATA_ACCETTATO.toString().equals(value)){
			res = EsitoType.SCARTATA_ACCETTATO;
		}else if(EsitoType.SCARTATA_RIFIUTATO.toString().equals(value)){
			res = EsitoType.SCARTATA_RIFIUTATO;
		}
		return res;
	}
}
