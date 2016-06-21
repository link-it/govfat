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
 * Enumeration dell'elemento Utente.role xsd (tipo:string) 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@javax.xml.bind.annotation.XmlType(name = "user-role")
@javax.xml.bind.annotation.XmlEnum(String.class)
public enum UserRole implements IEnumeration , Serializable , Cloneable {

	@javax.xml.bind.annotation.XmlEnumValue("ADMIN")
	ADMIN ("ADMIN"),
	@javax.xml.bind.annotation.XmlEnumValue("DEPT_ADMIN")
	DEPT_ADMIN ("DEPT_ADMIN"),
	@javax.xml.bind.annotation.XmlEnumValue("USER")
	USER ("USER");
	
	
	/** Value */
	private String value;
	@Override
	public String getValue()
	{
		return this.value;
	}


	/** Official Constructor */
	UserRole(String value)
	{
		this.value = value;
	}


	
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(UserRole object){
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
		if( !(object instanceof UserRole) ){
			throw new RuntimeException("Wrong type: "+object.getClass().getName());
		}
		return this.equals(((UserRole)object));
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
		for (UserRole tmp : values()) {
			res[i]=tmp.getValue();
			i++;
		}
		return res;
	}	
	public static String[] toStringArray(){
		String[] res = new String[values().length];
		int i=0;
		for (UserRole tmp : values()) {
			res[i]=tmp.toString();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[values().length];
		int i=0;
		for (UserRole tmp : values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}
	
	public static boolean contains(String value){
		return toEnumConstant(value)!=null;
	}
	
	public static UserRole toEnumConstant(String value){
		UserRole res = null;
		if(UserRole.ADMIN.getValue().equals(value)){
			res = UserRole.ADMIN;
		}else if(UserRole.DEPT_ADMIN.getValue().equals(value)){
			res = UserRole.DEPT_ADMIN;
		}else if(UserRole.USER.getValue().equals(value)){
			res = UserRole.USER;
		}
		return res;
	}
	
	public static IEnumeration toEnumConstantFromString(String value){
		UserRole res = null;
		if(UserRole.ADMIN.toString().equals(value)){
			res = UserRole.ADMIN;
		}else if(UserRole.DEPT_ADMIN.toString().equals(value)){
			res = UserRole.DEPT_ADMIN;
		}else if(UserRole.USER.toString().equals(value)){
			res = UserRole.USER;
		}
		return res;
	}
}
