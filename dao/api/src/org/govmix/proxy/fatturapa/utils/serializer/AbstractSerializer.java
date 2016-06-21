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
package org.govmix.proxy.fatturapa.utils.serializer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBElement;

import org.govmix.proxy.fatturapa.AllegatoFattura;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.IdRegistro;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.UtenteDipartimento;
import org.openspcoop2.generic_project.exception.SerializerException;
import org.openspcoop2.utils.beans.WriteToSerializerType;
import org.openspcoop2.utils.xml.JaxbUtils;

/**     
 * XML Serializer of beans
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public abstract class AbstractSerializer {


	protected abstract WriteToSerializerType getType();
	
	protected void _objToXml(OutputStream out, Class<?> c, Object object,
			boolean prettyPrint) throws Exception {
		if(object instanceof JAXBElement){
			// solo per il tipo WriteToSerializerType.JAXB
			JaxbUtils.objToXml(out, c, object, prettyPrint);
		}else{
			Method m = c.getMethod("writeTo", OutputStream.class, WriteToSerializerType.class);
			m.invoke(object, out, this.getType());
		}
	}
	
	protected void objToXml(OutputStream out,Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		try{
			this._objToXml(out, c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
		finally{
			try{
				out.flush();
			}catch(Exception e){}
		}
	}
	protected void objToXml(String fileName,Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		try{
			this.objToXml(new File(fileName), c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
	}
	protected void objToXml(File file,Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		FileOutputStream fout = null;
		try{
			fout = new FileOutputStream(file);
			this._objToXml(fout, c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
		finally{
			try{
				fout.flush();
			}catch(Exception e){}
			try{
				fout.close();
			}catch(Exception e){}
		}
	}
	protected ByteArrayOutputStream objToXml(Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		ByteArrayOutputStream bout = null;
		try{
			bout = new ByteArrayOutputStream();
			this._objToXml(bout, c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
		finally{
			try{
				bout.flush();
			}catch(Exception e){}
			try{
				bout.close();
			}catch(Exception e){}
		}
		return bout;
	}




	/*
	 =================================================================================
	 Object: LottoFatture
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param fileName Xml file to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,LottoFatture lottoFatture) throws SerializerException {
		this.objToXml(fileName, LottoFatture.class, lottoFatture, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param fileName Xml file to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,LottoFatture lottoFatture,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, LottoFatture.class, lottoFatture, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param file Xml file to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,LottoFatture lottoFatture) throws SerializerException {
		this.objToXml(file, LottoFatture.class, lottoFatture, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param file Xml file to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,LottoFatture lottoFatture,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, LottoFatture.class, lottoFatture, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param out OutputStream to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,LottoFatture lottoFatture) throws SerializerException {
		this.objToXml(out, LottoFatture.class, lottoFatture, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param out OutputStream to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,LottoFatture lottoFatture,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, LottoFatture.class, lottoFatture, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param lottoFatture Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(LottoFatture lottoFatture) throws SerializerException {
		return this.objToXml(LottoFatture.class, lottoFatture, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param lottoFatture Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(LottoFatture lottoFatture,boolean prettyPrint) throws SerializerException {
		return this.objToXml(LottoFatture.class, lottoFatture, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param lottoFatture Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(LottoFatture lottoFatture) throws SerializerException {
		return this.objToXml(LottoFatture.class, lottoFatture, false).toString();
	}
	/**
	 * Serialize to String the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param lottoFatture Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(LottoFatture lottoFatture,boolean prettyPrint) throws SerializerException {
		return this.objToXml(LottoFatture.class, lottoFatture, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: NotificaDecorrenzaTermini
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(fileName, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,NotificaDecorrenzaTermini notificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(file, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,NotificaDecorrenzaTermini notificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param out OutputStream to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(out, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param out OutputStream to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,NotificaDecorrenzaTermini notificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param notificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param notificaDecorrenzaTermini Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(NotificaDecorrenzaTermini notificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		return this.objToXml(NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param notificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false).toString();
	}
	/**
	 * Serialize to String the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param notificaDecorrenzaTermini Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(NotificaDecorrenzaTermini notificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		return this.objToXml(NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: FatturaElettronica
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param fileName Xml file to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,FatturaElettronica fatturaElettronica) throws SerializerException {
		this.objToXml(fileName, FatturaElettronica.class, fatturaElettronica, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param fileName Xml file to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,FatturaElettronica fatturaElettronica,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, FatturaElettronica.class, fatturaElettronica, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param file Xml file to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,FatturaElettronica fatturaElettronica) throws SerializerException {
		this.objToXml(file, FatturaElettronica.class, fatturaElettronica, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param file Xml file to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,FatturaElettronica fatturaElettronica,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, FatturaElettronica.class, fatturaElettronica, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param out OutputStream to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,FatturaElettronica fatturaElettronica) throws SerializerException {
		this.objToXml(out, FatturaElettronica.class, fatturaElettronica, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param out OutputStream to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,FatturaElettronica fatturaElettronica,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, FatturaElettronica.class, fatturaElettronica, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param fatturaElettronica Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(FatturaElettronica fatturaElettronica) throws SerializerException {
		return this.objToXml(FatturaElettronica.class, fatturaElettronica, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param fatturaElettronica Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(FatturaElettronica fatturaElettronica,boolean prettyPrint) throws SerializerException {
		return this.objToXml(FatturaElettronica.class, fatturaElettronica, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param fatturaElettronica Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(FatturaElettronica fatturaElettronica) throws SerializerException {
		return this.objToXml(FatturaElettronica.class, fatturaElettronica, false).toString();
	}
	/**
	 * Serialize to String the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param fatturaElettronica Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(FatturaElettronica fatturaElettronica,boolean prettyPrint) throws SerializerException {
		return this.objToXml(FatturaElettronica.class, fatturaElettronica, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: AllegatoFattura
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param fileName Xml file to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,AllegatoFattura allegatoFattura) throws SerializerException {
		this.objToXml(fileName, AllegatoFattura.class, allegatoFattura, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param fileName Xml file to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,AllegatoFattura allegatoFattura,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, AllegatoFattura.class, allegatoFattura, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param file Xml file to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,AllegatoFattura allegatoFattura) throws SerializerException {
		this.objToXml(file, AllegatoFattura.class, allegatoFattura, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param file Xml file to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,AllegatoFattura allegatoFattura,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, AllegatoFattura.class, allegatoFattura, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param out OutputStream to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,AllegatoFattura allegatoFattura) throws SerializerException {
		this.objToXml(out, AllegatoFattura.class, allegatoFattura, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param out OutputStream to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,AllegatoFattura allegatoFattura,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, AllegatoFattura.class, allegatoFattura, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param allegatoFattura Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(AllegatoFattura allegatoFattura) throws SerializerException {
		return this.objToXml(AllegatoFattura.class, allegatoFattura, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param allegatoFattura Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(AllegatoFattura allegatoFattura,boolean prettyPrint) throws SerializerException {
		return this.objToXml(AllegatoFattura.class, allegatoFattura, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param allegatoFattura Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(AllegatoFattura allegatoFattura) throws SerializerException {
		return this.objToXml(AllegatoFattura.class, allegatoFattura, false).toString();
	}
	/**
	 * Serialize to String the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param allegatoFattura Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(AllegatoFattura allegatoFattura,boolean prettyPrint) throws SerializerException {
		return this.objToXml(AllegatoFattura.class, allegatoFattura, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Ente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param fileName Xml file to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Ente ente) throws SerializerException {
		this.objToXml(fileName, Ente.class, ente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param fileName Xml file to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Ente ente,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Ente.class, ente, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param file Xml file to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Ente ente) throws SerializerException {
		this.objToXml(file, Ente.class, ente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param file Xml file to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Ente ente,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Ente.class, ente, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param out OutputStream to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Ente ente) throws SerializerException {
		this.objToXml(out, Ente.class, ente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param out OutputStream to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Ente ente,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Ente.class, ente, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param ente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Ente ente) throws SerializerException {
		return this.objToXml(Ente.class, ente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param ente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Ente ente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Ente.class, ente, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param ente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Ente ente) throws SerializerException {
		return this.objToXml(Ente.class, ente, false).toString();
	}
	/**
	 * Serialize to String the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param ente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Ente ente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Ente.class, ente, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Registro
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param fileName Xml file to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Registro registro) throws SerializerException {
		this.objToXml(fileName, Registro.class, registro, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param fileName Xml file to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Registro registro,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Registro.class, registro, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param file Xml file to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Registro registro) throws SerializerException {
		this.objToXml(file, Registro.class, registro, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param file Xml file to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Registro registro,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Registro.class, registro, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param out OutputStream to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Registro registro) throws SerializerException {
		this.objToXml(out, Registro.class, registro, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param out OutputStream to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Registro registro,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Registro.class, registro, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param registro Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Registro registro) throws SerializerException {
		return this.objToXml(Registro.class, registro, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param registro Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Registro registro,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Registro.class, registro, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param registro Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Registro registro) throws SerializerException {
		return this.objToXml(Registro.class, registro, false).toString();
	}
	/**
	 * Serialize to String the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param registro Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Registro registro,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Registro.class, registro, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: RegistroProperty
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,RegistroProperty registroProperty) throws SerializerException {
		this.objToXml(fileName, RegistroProperty.class, registroProperty, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,RegistroProperty registroProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, RegistroProperty.class, registroProperty, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param file Xml file to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,RegistroProperty registroProperty) throws SerializerException {
		this.objToXml(file, RegistroProperty.class, registroProperty, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param file Xml file to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,RegistroProperty registroProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, RegistroProperty.class, registroProperty, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,RegistroProperty registroProperty) throws SerializerException {
		this.objToXml(out, RegistroProperty.class, registroProperty, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,RegistroProperty registroProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, RegistroProperty.class, registroProperty, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param registroProperty Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(RegistroProperty registroProperty) throws SerializerException {
		return this.objToXml(RegistroProperty.class, registroProperty, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param registroProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(RegistroProperty registroProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(RegistroProperty.class, registroProperty, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param registroProperty Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(RegistroProperty registroProperty) throws SerializerException {
		return this.objToXml(RegistroProperty.class, registroProperty, false).toString();
	}
	/**
	 * Serialize to String the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param registroProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(RegistroProperty registroProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(RegistroProperty.class, registroProperty, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Utente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param fileName Xml file to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Utente utente) throws SerializerException {
		this.objToXml(fileName, Utente.class, utente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param fileName Xml file to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Utente utente,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Utente.class, utente, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param file Xml file to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Utente utente) throws SerializerException {
		this.objToXml(file, Utente.class, utente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param file Xml file to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Utente utente,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Utente.class, utente, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param out OutputStream to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Utente utente) throws SerializerException {
		this.objToXml(out, Utente.class, utente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param out OutputStream to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Utente utente,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Utente.class, utente, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param utente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Utente utente) throws SerializerException {
		return this.objToXml(Utente.class, utente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param utente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Utente utente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Utente.class, utente, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param utente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Utente utente) throws SerializerException {
		return this.objToXml(Utente.class, utente, false).toString();
	}
	/**
	 * Serialize to String the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param utente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Utente utente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Utente.class, utente, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: NotificaEsitoCommittente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param fileName Xml file to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		this.objToXml(fileName, NotificaEsitoCommittente.class, notificaEsitoCommittente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param fileName Xml file to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,NotificaEsitoCommittente notificaEsitoCommittente,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, NotificaEsitoCommittente.class, notificaEsitoCommittente, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param file Xml file to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		this.objToXml(file, NotificaEsitoCommittente.class, notificaEsitoCommittente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param file Xml file to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,NotificaEsitoCommittente notificaEsitoCommittente,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, NotificaEsitoCommittente.class, notificaEsitoCommittente, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param out OutputStream to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		this.objToXml(out, NotificaEsitoCommittente.class, notificaEsitoCommittente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param out OutputStream to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,NotificaEsitoCommittente notificaEsitoCommittente,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, NotificaEsitoCommittente.class, notificaEsitoCommittente, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param notificaEsitoCommittente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		return this.objToXml(NotificaEsitoCommittente.class, notificaEsitoCommittente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param notificaEsitoCommittente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(NotificaEsitoCommittente notificaEsitoCommittente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(NotificaEsitoCommittente.class, notificaEsitoCommittente, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param notificaEsitoCommittente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		return this.objToXml(NotificaEsitoCommittente.class, notificaEsitoCommittente, false).toString();
	}
	/**
	 * Serialize to String the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param notificaEsitoCommittente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(NotificaEsitoCommittente notificaEsitoCommittente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(NotificaEsitoCommittente.class, notificaEsitoCommittente, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Dipartimento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Dipartimento dipartimento) throws SerializerException {
		this.objToXml(fileName, Dipartimento.class, dipartimento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Dipartimento dipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Dipartimento.class, dipartimento, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Dipartimento dipartimento) throws SerializerException {
		this.objToXml(file, Dipartimento.class, dipartimento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Dipartimento dipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Dipartimento.class, dipartimento, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Dipartimento dipartimento) throws SerializerException {
		this.objToXml(out, Dipartimento.class, dipartimento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Dipartimento dipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Dipartimento.class, dipartimento, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param dipartimento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Dipartimento dipartimento) throws SerializerException {
		return this.objToXml(Dipartimento.class, dipartimento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param dipartimento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Dipartimento dipartimento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Dipartimento.class, dipartimento, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param dipartimento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Dipartimento dipartimento) throws SerializerException {
		return this.objToXml(Dipartimento.class, dipartimento, false).toString();
	}
	/**
	 * Serialize to String the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param dipartimento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Dipartimento dipartimento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Dipartimento.class, dipartimento, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: DipartimentoProperty
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,DipartimentoProperty dipartimentoProperty) throws SerializerException {
		this.objToXml(fileName, DipartimentoProperty.class, dipartimentoProperty, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,DipartimentoProperty dipartimentoProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, DipartimentoProperty.class, dipartimentoProperty, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,DipartimentoProperty dipartimentoProperty) throws SerializerException {
		this.objToXml(file, DipartimentoProperty.class, dipartimentoProperty, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,DipartimentoProperty dipartimentoProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, DipartimentoProperty.class, dipartimentoProperty, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,DipartimentoProperty dipartimentoProperty) throws SerializerException {
		this.objToXml(out, DipartimentoProperty.class, dipartimentoProperty, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,DipartimentoProperty dipartimentoProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, DipartimentoProperty.class, dipartimentoProperty, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param dipartimentoProperty Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(DipartimentoProperty dipartimentoProperty) throws SerializerException {
		return this.objToXml(DipartimentoProperty.class, dipartimentoProperty, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param dipartimentoProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(DipartimentoProperty dipartimentoProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(DipartimentoProperty.class, dipartimentoProperty, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param dipartimentoProperty Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(DipartimentoProperty dipartimentoProperty) throws SerializerException {
		return this.objToXml(DipartimentoProperty.class, dipartimentoProperty, false).toString();
	}
	/**
	 * Serialize to String the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param dipartimentoProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(DipartimentoProperty dipartimentoProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(DipartimentoProperty.class, dipartimentoProperty, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-registro
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param fileName Xml file to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdRegistro idRegistro) throws SerializerException {
		this.objToXml(fileName, IdRegistro.class, idRegistro, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param fileName Xml file to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdRegistro idRegistro,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdRegistro.class, idRegistro, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param file Xml file to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdRegistro idRegistro) throws SerializerException {
		this.objToXml(file, IdRegistro.class, idRegistro, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param file Xml file to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdRegistro idRegistro,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdRegistro.class, idRegistro, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param out OutputStream to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdRegistro idRegistro) throws SerializerException {
		this.objToXml(out, IdRegistro.class, idRegistro, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param out OutputStream to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdRegistro idRegistro,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdRegistro.class, idRegistro, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param idRegistro Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdRegistro idRegistro) throws SerializerException {
		return this.objToXml(IdRegistro.class, idRegistro, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param idRegistro Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdRegistro idRegistro,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdRegistro.class, idRegistro, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param idRegistro Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdRegistro idRegistro) throws SerializerException {
		return this.objToXml(IdRegistro.class, idRegistro, false).toString();
	}
	/**
	 * Serialize to String the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param idRegistro Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdRegistro idRegistro,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdRegistro.class, idRegistro, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-ente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param fileName Xml file to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdEnte idEnte) throws SerializerException {
		this.objToXml(fileName, IdEnte.class, idEnte, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param fileName Xml file to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdEnte idEnte,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdEnte.class, idEnte, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param file Xml file to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdEnte idEnte) throws SerializerException {
		this.objToXml(file, IdEnte.class, idEnte, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param file Xml file to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdEnte idEnte,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdEnte.class, idEnte, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param out OutputStream to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdEnte idEnte) throws SerializerException {
		this.objToXml(out, IdEnte.class, idEnte, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param out OutputStream to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdEnte idEnte,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdEnte.class, idEnte, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param idEnte Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdEnte idEnte) throws SerializerException {
		return this.objToXml(IdEnte.class, idEnte, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param idEnte Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdEnte idEnte,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdEnte.class, idEnte, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param idEnte Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdEnte idEnte) throws SerializerException {
		return this.objToXml(IdEnte.class, idEnte, false).toString();
	}
	/**
	 * Serialize to String the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param idEnte Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdEnte idEnte,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdEnte.class, idEnte, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: DipartimentoPropertyValue
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		this.objToXml(fileName, DipartimentoPropertyValue.class, dipartimentoPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,DipartimentoPropertyValue dipartimentoPropertyValue,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, DipartimentoPropertyValue.class, dipartimentoPropertyValue, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		this.objToXml(file, DipartimentoPropertyValue.class, dipartimentoPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,DipartimentoPropertyValue dipartimentoPropertyValue,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, DipartimentoPropertyValue.class, dipartimentoPropertyValue, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		this.objToXml(out, DipartimentoPropertyValue.class, dipartimentoPropertyValue, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,DipartimentoPropertyValue dipartimentoPropertyValue,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, DipartimentoPropertyValue.class, dipartimentoPropertyValue, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param dipartimentoPropertyValue Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		return this.objToXml(DipartimentoPropertyValue.class, dipartimentoPropertyValue, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param dipartimentoPropertyValue Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(DipartimentoPropertyValue dipartimentoPropertyValue,boolean prettyPrint) throws SerializerException {
		return this.objToXml(DipartimentoPropertyValue.class, dipartimentoPropertyValue, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param dipartimentoPropertyValue Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		return this.objToXml(DipartimentoPropertyValue.class, dipartimentoPropertyValue, false).toString();
	}
	/**
	 * Serialize to String the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param dipartimentoPropertyValue Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(DipartimentoPropertyValue dipartimentoPropertyValue,boolean prettyPrint) throws SerializerException {
		return this.objToXml(DipartimentoPropertyValue.class, dipartimentoPropertyValue, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: UtenteDipartimento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,UtenteDipartimento utenteDipartimento) throws SerializerException {
		this.objToXml(fileName, UtenteDipartimento.class, utenteDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,UtenteDipartimento utenteDipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, UtenteDipartimento.class, utenteDipartimento, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,UtenteDipartimento utenteDipartimento) throws SerializerException {
		this.objToXml(file, UtenteDipartimento.class, utenteDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,UtenteDipartimento utenteDipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, UtenteDipartimento.class, utenteDipartimento, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,UtenteDipartimento utenteDipartimento) throws SerializerException {
		this.objToXml(out, UtenteDipartimento.class, utenteDipartimento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,UtenteDipartimento utenteDipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, UtenteDipartimento.class, utenteDipartimento, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param utenteDipartimento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(UtenteDipartimento utenteDipartimento) throws SerializerException {
		return this.objToXml(UtenteDipartimento.class, utenteDipartimento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param utenteDipartimento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(UtenteDipartimento utenteDipartimento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(UtenteDipartimento.class, utenteDipartimento, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param utenteDipartimento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(UtenteDipartimento utenteDipartimento) throws SerializerException {
		return this.objToXml(UtenteDipartimento.class, utenteDipartimento, false).toString();
	}
	/**
	 * Serialize to String the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param utenteDipartimento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(UtenteDipartimento utenteDipartimento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(UtenteDipartimento.class, utenteDipartimento, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-notifica-decorrenza-termini
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(fileName, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(file, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param out OutputStream to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(out, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param out OutputStream to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param idNotificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param idNotificaDecorrenzaTermini Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param idNotificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false).toString();
	}
	/**
	 * Serialize to String the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param idNotificaDecorrenzaTermini Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-property
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>idProperty</var>
	 * @param idProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdProperty idProperty) throws SerializerException {
		this.objToXml(fileName, IdProperty.class, idProperty, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>idProperty</var>
	 * @param idProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdProperty idProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdProperty.class, idProperty, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param file Xml file to serialize the object <var>idProperty</var>
	 * @param idProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdProperty idProperty) throws SerializerException {
		this.objToXml(file, IdProperty.class, idProperty, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param file Xml file to serialize the object <var>idProperty</var>
	 * @param idProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdProperty idProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdProperty.class, idProperty, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>idProperty</var>
	 * @param idProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdProperty idProperty) throws SerializerException {
		this.objToXml(out, IdProperty.class, idProperty, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>idProperty</var>
	 * @param idProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdProperty idProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdProperty.class, idProperty, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param idProperty Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdProperty idProperty) throws SerializerException {
		return this.objToXml(IdProperty.class, idProperty, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param idProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdProperty idProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdProperty.class, idProperty, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param idProperty Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdProperty idProperty) throws SerializerException {
		return this.objToXml(IdProperty.class, idProperty, false).toString();
	}
	/**
	 * Serialize to String the object <var>idProperty</var> of type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param idProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdProperty idProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdProperty.class, idProperty, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-fattura
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param fileName Xml file to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdFattura idFattura) throws SerializerException {
		this.objToXml(fileName, IdFattura.class, idFattura, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param fileName Xml file to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdFattura idFattura,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdFattura.class, idFattura, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param file Xml file to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdFattura idFattura) throws SerializerException {
		this.objToXml(file, IdFattura.class, idFattura, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param file Xml file to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdFattura idFattura,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdFattura.class, idFattura, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param out OutputStream to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdFattura idFattura) throws SerializerException {
		this.objToXml(out, IdFattura.class, idFattura, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param out OutputStream to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdFattura idFattura,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdFattura.class, idFattura, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param idFattura Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdFattura idFattura) throws SerializerException {
		return this.objToXml(IdFattura.class, idFattura, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param idFattura Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdFattura idFattura,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdFattura.class, idFattura, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param idFattura Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdFattura idFattura) throws SerializerException {
		return this.objToXml(IdFattura.class, idFattura, false).toString();
	}
	/**
	 * Serialize to String the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param idFattura Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdFattura idFattura,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdFattura.class, idFattura, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-lotto
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param fileName Xml file to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdLotto idLotto) throws SerializerException {
		this.objToXml(fileName, IdLotto.class, idLotto, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param fileName Xml file to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdLotto idLotto,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdLotto.class, idLotto, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param file Xml file to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdLotto idLotto) throws SerializerException {
		this.objToXml(file, IdLotto.class, idLotto, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param file Xml file to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdLotto idLotto,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdLotto.class, idLotto, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param out OutputStream to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdLotto idLotto) throws SerializerException {
		this.objToXml(out, IdLotto.class, idLotto, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param out OutputStream to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdLotto idLotto,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdLotto.class, idLotto, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param idLotto Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdLotto idLotto) throws SerializerException {
		return this.objToXml(IdLotto.class, idLotto, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param idLotto Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdLotto idLotto,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdLotto.class, idLotto, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param idLotto Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdLotto idLotto) throws SerializerException {
		return this.objToXml(IdLotto.class, idLotto, false).toString();
	}
	/**
	 * Serialize to String the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param idLotto Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdLotto idLotto,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdLotto.class, idLotto, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-utente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param fileName Xml file to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdUtente idUtente) throws SerializerException {
		this.objToXml(fileName, IdUtente.class, idUtente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param fileName Xml file to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdUtente idUtente,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdUtente.class, idUtente, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param file Xml file to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdUtente idUtente) throws SerializerException {
		this.objToXml(file, IdUtente.class, idUtente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param file Xml file to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdUtente idUtente,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdUtente.class, idUtente, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param out OutputStream to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdUtente idUtente) throws SerializerException {
		this.objToXml(out, IdUtente.class, idUtente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param out OutputStream to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdUtente idUtente,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdUtente.class, idUtente, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param idUtente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdUtente idUtente) throws SerializerException {
		return this.objToXml(IdUtente.class, idUtente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param idUtente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdUtente idUtente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdUtente.class, idUtente, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param idUtente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdUtente idUtente) throws SerializerException {
		return this.objToXml(IdUtente.class, idUtente, false).toString();
	}
	/**
	 * Serialize to String the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param idUtente Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdUtente idUtente,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdUtente.class, idUtente, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: RegistroPropertyValue
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param fileName Xml file to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,RegistroPropertyValue registroPropertyValue) throws SerializerException {
		this.objToXml(fileName, RegistroPropertyValue.class, registroPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param fileName Xml file to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,RegistroPropertyValue registroPropertyValue,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, RegistroPropertyValue.class, registroPropertyValue, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param file Xml file to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,RegistroPropertyValue registroPropertyValue) throws SerializerException {
		this.objToXml(file, RegistroPropertyValue.class, registroPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param file Xml file to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,RegistroPropertyValue registroPropertyValue,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, RegistroPropertyValue.class, registroPropertyValue, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param out OutputStream to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,RegistroPropertyValue registroPropertyValue) throws SerializerException {
		this.objToXml(out, RegistroPropertyValue.class, registroPropertyValue, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param out OutputStream to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,RegistroPropertyValue registroPropertyValue,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, RegistroPropertyValue.class, registroPropertyValue, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param registroPropertyValue Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(RegistroPropertyValue registroPropertyValue) throws SerializerException {
		return this.objToXml(RegistroPropertyValue.class, registroPropertyValue, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param registroPropertyValue Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(RegistroPropertyValue registroPropertyValue,boolean prettyPrint) throws SerializerException {
		return this.objToXml(RegistroPropertyValue.class, registroPropertyValue, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param registroPropertyValue Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(RegistroPropertyValue registroPropertyValue) throws SerializerException {
		return this.objToXml(RegistroPropertyValue.class, registroPropertyValue, false).toString();
	}
	/**
	 * Serialize to String the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param registroPropertyValue Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(RegistroPropertyValue registroPropertyValue,boolean prettyPrint) throws SerializerException {
		return this.objToXml(RegistroPropertyValue.class, registroPropertyValue, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-dipartimento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdDipartimento idDipartimento) throws SerializerException {
		this.objToXml(fileName, IdDipartimento.class, idDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdDipartimento idDipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdDipartimento.class, idDipartimento, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdDipartimento idDipartimento) throws SerializerException {
		this.objToXml(file, IdDipartimento.class, idDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdDipartimento idDipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdDipartimento.class, idDipartimento, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdDipartimento idDipartimento) throws SerializerException {
		this.objToXml(out, IdDipartimento.class, idDipartimento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdDipartimento idDipartimento,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdDipartimento.class, idDipartimento, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param idDipartimento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdDipartimento idDipartimento) throws SerializerException {
		return this.objToXml(IdDipartimento.class, idDipartimento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param idDipartimento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdDipartimento idDipartimento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdDipartimento.class, idDipartimento, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param idDipartimento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDipartimento idDipartimento) throws SerializerException {
		return this.objToXml(IdDipartimento.class, idDipartimento, false).toString();
	}
	/**
	 * Serialize to String the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param idDipartimento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDipartimento idDipartimento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdDipartimento.class, idDipartimento, prettyPrint).toString();
	}
	
	
	

}
