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
 * Enumeration dell'elemento OperazioneType xsd (tipo:string) 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@javax.xml.bind.annotation.XmlType(name = "OperazioneType")
@javax.xml.bind.annotation.XmlEnum(String.class)
public enum OperazioneType implements IEnumeration , Serializable , Cloneable {

	@javax.xml.bind.annotation.XmlEnumValue("ProxyPagamentoIva")
	PROXY_PAGAMENTO_IVA ("ProxyPagamentoIva"),
	@javax.xml.bind.annotation.XmlEnumValue("QueryInserimentoFattura")
	QUERY_INSERIMENTO_FATTURA ("QueryInserimentoFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("ReadStatoFattura")
	READ_STATO_FATTURA ("ReadStatoFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("QueryOperazioneContabile")
	QUERY_OPERAZIONE_CONTABILE ("QueryOperazioneContabile"),
	@javax.xml.bind.annotation.XmlEnumValue("ProxyDatiFattura")
	PROXY_DATI_FATTURA ("ProxyDatiFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("ProxyInserimentoFattura")
	PROXY_INSERIMENTO_FATTURA ("ProxyInserimentoFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("ProxyOperazioneContabile")
	PROXY_OPERAZIONE_CONTABILE ("ProxyOperazioneContabile"),
	@javax.xml.bind.annotation.XmlEnumValue("ReadDownloadDocumento")
	READ_DOWNLOAD_DOCUMENTO ("ReadDownloadDocumento"),
	@javax.xml.bind.annotation.XmlEnumValue("QueryDatiFattura")
	QUERY_DATI_FATTURA ("QueryDatiFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("QueryPagamentoIva")
	QUERY_PAGAMENTO_IVA ("QueryPagamentoIva"),
	@javax.xml.bind.annotation.XmlEnumValue("ReadElencoMovimentiErarioIva")
	READ_ELENCO_MOVIMENTI_ERARIO_IVA ("ReadElencoMovimentiErarioIva"),
	@javax.xml.bind.annotation.XmlEnumValue("QueryInterrogazioneEsiti")
	QUERY_INTERROGAZIONE_ESITI ("QueryInterrogazioneEsiti");
	
	
	/** Value */
	private String value;
	@Override
	public String getValue()
	{
		return this.value;
	}


	/** Official Constructor */
	OperazioneType(String value)
	{
		this.value = value;
	}


	
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(OperazioneType object){
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
		if( !(object instanceof OperazioneType) ){
			throw new RuntimeException("Wrong type: "+object.getClass().getName());
		}
		return this.equals(((OperazioneType)object));
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
		for (OperazioneType tmp : values()) {
			res[i]=tmp.getValue();
			i++;
		}
		return res;
	}	
	public static String[] toStringArray(){
		String[] res = new String[values().length];
		int i=0;
		for (OperazioneType tmp : values()) {
			res[i]=tmp.toString();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[values().length];
		int i=0;
		for (OperazioneType tmp : values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}
	
	public static boolean contains(String value){
		return toEnumConstant(value)!=null;
	}
	
	public static OperazioneType toEnumConstant(String value){
		try{
			return toEnumConstant(value,false);
		}catch(NotFoundException notFound){
			return null;
		}
	}
	public static OperazioneType toEnumConstant(String value, boolean throwNotFoundException) throws NotFoundException{
		OperazioneType res = null;
		for (OperazioneType tmp : values()) {
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
		OperazioneType res = null;
		for (OperazioneType tmp : values()) {
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
