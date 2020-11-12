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

	@javax.xml.bind.annotation.XmlEnumValue("PRESA_IN_CARICO")
	PRESA_IN_CARICO ("PRESA_IN_CARICO"),
	@javax.xml.bind.annotation.XmlEnumValue("IN_CORSO_DI_PROTOCOLLAZIONE")
	IN_CORSO_DI_PROTOCOLLAZIONE ("IN_CORSO_DI_PROTOCOLLAZIONE"),
	@javax.xml.bind.annotation.XmlEnumValue("IN_CORSO_DI_FIRMA")
	IN_CORSO_DI_FIRMA ("IN_CORSO_DI_FIRMA"),
	@javax.xml.bind.annotation.XmlEnumValue("DA_INVIARE_ALLO_SDI")
	DA_INVIARE_ALLO_SDI ("DA_INVIARE_ALLO_SDI"),
	@javax.xml.bind.annotation.XmlEnumValue("ERRORE_DI_FIRMA")
	ERRORE_DI_FIRMA ("ERRORE_DI_FIRMA"),
	@javax.xml.bind.annotation.XmlEnumValue("ERRORE_DI_PROTOCOLLO")
	ERRORE_DI_PROTOCOLLO ("ERRORE_DI_PROTOCOLLO"),
	@javax.xml.bind.annotation.XmlEnumValue("ERRORE_DI_SPEDIZIONE")
	ERRORE_DI_SPEDIZIONE ("ERRORE_DI_SPEDIZIONE"),
	@javax.xml.bind.annotation.XmlEnumValue("RICEVUTA_DALLO_SDI")
	RICEVUTA_DALLO_SDI ("RICEVUTA_DALLO_SDI"),
	@javax.xml.bind.annotation.XmlEnumValue("RICEVUTO_SCARTO_SDI")
	RICEVUTO_SCARTO_SDI ("RICEVUTO_SCARTO_SDI"),
	@javax.xml.bind.annotation.XmlEnumValue("RICEVUTA_DAL_DESTINATARIO")
	RICEVUTA_DAL_DESTINATARIO ("RICEVUTA_DAL_DESTINATARIO"),
	@javax.xml.bind.annotation.XmlEnumValue("MANCATA_CONSEGNA")
	MANCATA_CONSEGNA ("MANCATA_CONSEGNA"),
	@javax.xml.bind.annotation.XmlEnumValue("IMPOSSIBILITA_DI_RECAPITO")
	IMPOSSIBILITA_DI_RECAPITO ("IMPOSSIBILITA_DI_RECAPITO"),
	@javax.xml.bind.annotation.XmlEnumValue("RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE")
	RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE ("RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE"),
	@javax.xml.bind.annotation.XmlEnumValue("RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO")
	RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO ("RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO"),
	@javax.xml.bind.annotation.XmlEnumValue("RICEVUTA_DECORRENZA_TERMINI")
	RICEVUTA_DECORRENZA_TERMINI ("RICEVUTA_DECORRENZA_TERMINI"),
	@javax.xml.bind.annotation.XmlEnumValue("SOLO_CONSERVAZIONE")
	SOLO_CONSERVAZIONE ("SOLO_CONSERVAZIONE");
	
	
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
