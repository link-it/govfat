/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm.constants;

import java.io.Serializable;
import java.util.List;

import org.openspcoop2.generic_project.beans.IEnumeration;
import org.openspcoop2.generic_project.exception.NotFoundException;

/**     
 * Enumeration dell'elemento ComunicazioneSdi.tipoComunicazione xsd (tipo:string) 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@javax.xml.bind.annotation.XmlType(name = "TipoComunicazioneType")
@javax.xml.bind.annotation.XmlEnum(String.class)
public enum TipoComunicazioneType implements IEnumeration , Serializable , Cloneable {

	@javax.xml.bind.annotation.XmlEnumValue("FATTURA_USCITA")
	FATTURA_USCITA ("FATTURA_USCITA"),
	@javax.xml.bind.annotation.XmlEnumValue("NOTIFICA_SCARTO")
	NOTIFICA_SCARTO ("NOTIFICA_SCARTO"),
	@javax.xml.bind.annotation.XmlEnumValue("RICEVUTA_CONSEGNA")
	RICEVUTA_CONSEGNA ("RICEVUTA_CONSEGNA"),
	@javax.xml.bind.annotation.XmlEnumValue("NOTIFICA_MANCATA_CONSEGNA")
	NOTIFICA_MANCATA_CONSEGNA ("NOTIFICA_MANCATA_CONSEGNA"),
	@javax.xml.bind.annotation.XmlEnumValue("ATTESTAZIONE_TRASMISSIONE_FATTURA")
	ATTESTAZIONE_TRASMISSIONE_FATTURA ("ATTESTAZIONE_TRASMISSIONE_FATTURA"),
	@javax.xml.bind.annotation.XmlEnumValue("NOTIFICA_ESITO_COMMITTENTE")
	NOTIFICA_ESITO_COMMITTENTE ("NOTIFICA_ESITO_COMMITTENTE"),
	@javax.xml.bind.annotation.XmlEnumValue("NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE")
	NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE ("NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE"),
	@javax.xml.bind.annotation.XmlEnumValue("AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO")
	AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO ("AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO");
	
	
	/** Value */
	private String value;
	@Override
	public String getValue()
	{
		return this.value;
	}


	/** Official Constructor */
	TipoComunicazioneType(String value)
	{
		this.value = value;
	}


	
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(TipoComunicazioneType object){
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
		if( !(object instanceof TipoComunicazioneType) ){
			throw new RuntimeException("Wrong type: "+object.getClass().getName());
		}
		return this.equals(((TipoComunicazioneType)object));
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
		for (TipoComunicazioneType tmp : values()) {
			res[i]=tmp.getValue();
			i++;
		}
		return res;
	}	
	public static String[] toStringArray(){
		String[] res = new String[values().length];
		int i=0;
		for (TipoComunicazioneType tmp : values()) {
			res[i]=tmp.toString();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[values().length];
		int i=0;
		for (TipoComunicazioneType tmp : values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}
	
	public static boolean contains(String value){
		return toEnumConstant(value)!=null;
	}
	
	public static TipoComunicazioneType toEnumConstant(String value){
		try{
			return toEnumConstant(value,false);
		}catch(NotFoundException notFound){
			return null;
		}
	}
	public static TipoComunicazioneType toEnumConstant(String value, boolean throwNotFoundException) throws NotFoundException{
		TipoComunicazioneType res = null;
		for (TipoComunicazioneType tmp : values()) {
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
		TipoComunicazioneType res = null;
		for (TipoComunicazioneType tmp : values()) {
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
