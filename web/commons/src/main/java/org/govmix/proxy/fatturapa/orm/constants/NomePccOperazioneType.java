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
 * Enumeration dell'elemento idOperazione.nome xsd (tipo:string) 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@javax.xml.bind.annotation.XmlType(name = "NomePccOperazioneType")
@javax.xml.bind.annotation.XmlEnum(String.class)
public enum NomePccOperazioneType implements IEnumeration , Serializable , Cloneable {

	@javax.xml.bind.annotation.XmlEnumValue("ConsultazioneTracce")
	CONSULTAZIONE_TRACCE ("ConsultazioneTracce"),
	@javax.xml.bind.annotation.XmlEnumValue("DatiFattura")
	DATI_FATTURA ("DatiFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("PagamentoIva")
	PAGAMENTO_IVA ("PagamentoIva"),
	@javax.xml.bind.annotation.XmlEnumValue("InserimentoFattura")
	INSERIMENTO_FATTURA ("InserimentoFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("StatoFattura")
	STATO_FATTURA ("StatoFattura"),
	@javax.xml.bind.annotation.XmlEnumValue("ElencoMovimentiErarioIva")
	ELENCO_MOVIMENTI_ERARIO_IVA ("ElencoMovimentiErarioIva"),
	@javax.xml.bind.annotation.XmlEnumValue("DownloadDocumento")
	DOWNLOAD_DOCUMENTO ("DownloadDocumento"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_CP")
	OPERAZIONE_CONTABILE_CP ("OperazioneContabile_CP"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_CO")
	OPERAZIONE_CONTABILE_CO ("OperazioneContabile_CO"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_CS")
	OPERAZIONE_CONTABILE_CS ("OperazioneContabile_CS"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_CCS")
	OPERAZIONE_CONTABILE_CCS ("OperazioneContabile_CCS"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_CPS")
	OPERAZIONE_CONTABILE_CPS ("OperazioneContabile_CPS"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_CSPC")
	OPERAZIONE_CONTABILE_CSPC ("OperazioneContabile_CSPC"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_SP")
	OPERAZIONE_CONTABILE_SP ("OperazioneContabile_SP"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_RF")
	OPERAZIONE_CONTABILE_RF ("OperazioneContabile_RF"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_SC")
	OPERAZIONE_CONTABILE_SC ("OperazioneContabile_SC"),
	@javax.xml.bind.annotation.XmlEnumValue("OperazioneContabile_RC")
	OPERAZIONE_CONTABILE_RC ("OperazioneContabile_RC");
	
	
	/** Value */
	private String value;
	@Override
	public String getValue()
	{
		return this.value;
	}


	/** Official Constructor */
	NomePccOperazioneType(String value)
	{
		this.value = value;
	}


	
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(NomePccOperazioneType object){
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
		if( !(object instanceof NomePccOperazioneType) ){
			throw new RuntimeException("Wrong type: "+object.getClass().getName());
		}
		return this.equals(((NomePccOperazioneType)object));
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
		for (NomePccOperazioneType tmp : values()) {
			res[i]=tmp.getValue();
			i++;
		}
		return res;
	}	
	public static String[] toStringArray(){
		String[] res = new String[values().length];
		int i=0;
		for (NomePccOperazioneType tmp : values()) {
			res[i]=tmp.toString();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[values().length];
		int i=0;
		for (NomePccOperazioneType tmp : values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}
	
	public static boolean contains(String value){
		return toEnumConstant(value)!=null;
	}
	
	public static NomePccOperazioneType toEnumConstant(String value){
		try{
			return toEnumConstant(value,false);
		}catch(NotFoundException notFound){
			return null;
		}
	}
	public static NomePccOperazioneType toEnumConstant(String value, boolean throwNotFoundException) throws NotFoundException{
		NomePccOperazioneType res = null;
		for (NomePccOperazioneType tmp : values()) {
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
		NomePccOperazioneType res = null;
		for (NomePccOperazioneType tmp : values()) {
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
