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
package org.govmix.proxy.fatturapa.orm.utils.serializer;

import org.openspcoop2.generic_project.exception.SerializerException;
import org.openspcoop2.utils.beans.WriteToSerializerType;
import org.openspcoop2.utils.xml.JaxbUtils;

import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.IdSip;
import org.govmix.proxy.fatturapa.orm.IdScadenza;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.IdTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.DipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.IdProtocollo;
import org.govmix.proxy.fatturapa.orm.RegistroProperty;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.IdRispedizione;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.IdOperazione;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.orm.IdPagamento;
import org.govmix.proxy.fatturapa.orm.IdComunicazione;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.PccNotifica;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.IdRegistroProperty;
import org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.SIP;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.orm.IdDocumento;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.IdContabilizzazione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.Evento;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBElement;

/**     
 * XML Serializer of beans
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
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
	 Object: PccScadenza
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccScadenza</var>
	 * @param pccScadenza Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccScadenza pccScadenza) throws SerializerException {
		this.objToXml(fileName, PccScadenza.class, pccScadenza, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccScadenza</var>
	 * @param pccScadenza Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccScadenza pccScadenza,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccScadenza.class, pccScadenza, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param file Xml file to serialize the object <var>pccScadenza</var>
	 * @param pccScadenza Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccScadenza pccScadenza) throws SerializerException {
		this.objToXml(file, PccScadenza.class, pccScadenza, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param file Xml file to serialize the object <var>pccScadenza</var>
	 * @param pccScadenza Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccScadenza pccScadenza,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccScadenza.class, pccScadenza, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param out OutputStream to serialize the object <var>pccScadenza</var>
	 * @param pccScadenza Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccScadenza pccScadenza) throws SerializerException {
		this.objToXml(out, PccScadenza.class, pccScadenza, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param out OutputStream to serialize the object <var>pccScadenza</var>
	 * @param pccScadenza Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccScadenza pccScadenza,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccScadenza.class, pccScadenza, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param pccScadenza Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccScadenza pccScadenza) throws SerializerException {
		return this.objToXml(PccScadenza.class, pccScadenza, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param pccScadenza Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccScadenza pccScadenza,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccScadenza.class, pccScadenza, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param pccScadenza Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccScadenza pccScadenza) throws SerializerException {
		return this.objToXml(PccScadenza.class, pccScadenza, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param pccScadenza Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccScadenza pccScadenza,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccScadenza.class, pccScadenza, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-fattura
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param fileName Xml file to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdFattura idFattura) throws SerializerException {
		this.objToXml(fileName, IdFattura.class, idFattura, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
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
	 * Serialize to file system in <var>file</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param file Xml file to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdFattura idFattura) throws SerializerException {
		this.objToXml(file, IdFattura.class, idFattura, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
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
	 * Serialize to output stream <var>out</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param out OutputStream to serialize the object <var>idFattura</var>
	 * @param idFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdFattura idFattura) throws SerializerException {
		this.objToXml(out, IdFattura.class, idFattura, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
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
	 * Serialize to byte array the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param idFattura Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdFattura idFattura) throws SerializerException {
		return this.objToXml(IdFattura.class, idFattura, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
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
	 * Serialize to String the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param idFattura Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdFattura idFattura) throws SerializerException {
		return this.objToXml(IdFattura.class, idFattura, false).toString();
	}
	/**
	 * Serialize to String the object <var>idFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
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
	 Object: Protocollo
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param fileName Xml file to serialize the object <var>protocollo</var>
	 * @param protocollo Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Protocollo protocollo) throws SerializerException {
		this.objToXml(fileName, Protocollo.class, protocollo, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param fileName Xml file to serialize the object <var>protocollo</var>
	 * @param protocollo Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Protocollo protocollo,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Protocollo.class, protocollo, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param file Xml file to serialize the object <var>protocollo</var>
	 * @param protocollo Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Protocollo protocollo) throws SerializerException {
		this.objToXml(file, Protocollo.class, protocollo, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param file Xml file to serialize the object <var>protocollo</var>
	 * @param protocollo Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Protocollo protocollo,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Protocollo.class, protocollo, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param out OutputStream to serialize the object <var>protocollo</var>
	 * @param protocollo Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Protocollo protocollo) throws SerializerException {
		this.objToXml(out, Protocollo.class, protocollo, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param out OutputStream to serialize the object <var>protocollo</var>
	 * @param protocollo Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Protocollo protocollo,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Protocollo.class, protocollo, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param protocollo Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Protocollo protocollo) throws SerializerException {
		return this.objToXml(Protocollo.class, protocollo, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param protocollo Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Protocollo protocollo,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Protocollo.class, protocollo, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param protocollo Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Protocollo protocollo) throws SerializerException {
		return this.objToXml(Protocollo.class, protocollo, false).toString();
	}
	/**
	 * Serialize to String the object <var>protocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param protocollo Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Protocollo protocollo,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Protocollo.class, protocollo, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-sip
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param fileName Xml file to serialize the object <var>idSip</var>
	 * @param idSip Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdSip idSip) throws SerializerException {
		this.objToXml(fileName, IdSip.class, idSip, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param fileName Xml file to serialize the object <var>idSip</var>
	 * @param idSip Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdSip idSip,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdSip.class, idSip, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param file Xml file to serialize the object <var>idSip</var>
	 * @param idSip Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdSip idSip) throws SerializerException {
		this.objToXml(file, IdSip.class, idSip, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param file Xml file to serialize the object <var>idSip</var>
	 * @param idSip Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdSip idSip,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdSip.class, idSip, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param out OutputStream to serialize the object <var>idSip</var>
	 * @param idSip Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdSip idSip) throws SerializerException {
		this.objToXml(out, IdSip.class, idSip, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param out OutputStream to serialize the object <var>idSip</var>
	 * @param idSip Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdSip idSip,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdSip.class, idSip, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param idSip Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdSip idSip) throws SerializerException {
		return this.objToXml(IdSip.class, idSip, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param idSip Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdSip idSip,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdSip.class, idSip, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param idSip Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdSip idSip) throws SerializerException {
		return this.objToXml(IdSip.class, idSip, false).toString();
	}
	/**
	 * Serialize to String the object <var>idSip</var> of type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param idSip Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdSip idSip,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdSip.class, idSip, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-scadenza
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param fileName Xml file to serialize the object <var>idScadenza</var>
	 * @param idScadenza Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdScadenza idScadenza) throws SerializerException {
		this.objToXml(fileName, IdScadenza.class, idScadenza, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param fileName Xml file to serialize the object <var>idScadenza</var>
	 * @param idScadenza Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdScadenza idScadenza,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdScadenza.class, idScadenza, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param file Xml file to serialize the object <var>idScadenza</var>
	 * @param idScadenza Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdScadenza idScadenza) throws SerializerException {
		this.objToXml(file, IdScadenza.class, idScadenza, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param file Xml file to serialize the object <var>idScadenza</var>
	 * @param idScadenza Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdScadenza idScadenza,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdScadenza.class, idScadenza, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param out OutputStream to serialize the object <var>idScadenza</var>
	 * @param idScadenza Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdScadenza idScadenza) throws SerializerException {
		this.objToXml(out, IdScadenza.class, idScadenza, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param out OutputStream to serialize the object <var>idScadenza</var>
	 * @param idScadenza Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdScadenza idScadenza,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdScadenza.class, idScadenza, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param idScadenza Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdScadenza idScadenza) throws SerializerException {
		return this.objToXml(IdScadenza.class, idScadenza, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param idScadenza Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdScadenza idScadenza,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdScadenza.class, idScadenza, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param idScadenza Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdScadenza idScadenza) throws SerializerException {
		return this.objToXml(IdScadenza.class, idScadenza, false).toString();
	}
	/**
	 * Serialize to String the object <var>idScadenza</var> of type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param idScadenza Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdScadenza idScadenza,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdScadenza.class, idScadenza, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-lotto
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param fileName Xml file to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdLotto idLotto) throws SerializerException {
		this.objToXml(fileName, IdLotto.class, idLotto, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
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
	 * Serialize to file system in <var>file</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param file Xml file to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdLotto idLotto) throws SerializerException {
		this.objToXml(file, IdLotto.class, idLotto, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
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
	 * Serialize to output stream <var>out</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param out OutputStream to serialize the object <var>idLotto</var>
	 * @param idLotto Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdLotto idLotto) throws SerializerException {
		this.objToXml(out, IdLotto.class, idLotto, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
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
	 * Serialize to byte array the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param idLotto Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdLotto idLotto) throws SerializerException {
		return this.objToXml(IdLotto.class, idLotto, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
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
	 * Serialize to String the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param idLotto Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdLotto idLotto) throws SerializerException {
		return this.objToXml(IdLotto.class, idLotto, false).toString();
	}
	/**
	 * Serialize to String the object <var>idLotto</var> of type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
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
	 Object: FatturaElettronica
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param fileName Xml file to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,FatturaElettronica fatturaElettronica) throws SerializerException {
		this.objToXml(fileName, FatturaElettronica.class, fatturaElettronica, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
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
	 * Serialize to file system in <var>file</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param file Xml file to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,FatturaElettronica fatturaElettronica) throws SerializerException {
		this.objToXml(file, FatturaElettronica.class, fatturaElettronica, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
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
	 * Serialize to output stream <var>out</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param out OutputStream to serialize the object <var>fatturaElettronica</var>
	 * @param fatturaElettronica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,FatturaElettronica fatturaElettronica) throws SerializerException {
		this.objToXml(out, FatturaElettronica.class, fatturaElettronica, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
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
	 * Serialize to byte array the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param fatturaElettronica Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(FatturaElettronica fatturaElettronica) throws SerializerException {
		return this.objToXml(FatturaElettronica.class, fatturaElettronica, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
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
	 * Serialize to String the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param fatturaElettronica Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(FatturaElettronica fatturaElettronica) throws SerializerException {
		return this.objToXml(FatturaElettronica.class, fatturaElettronica, false).toString();
	}
	/**
	 * Serialize to String the object <var>fatturaElettronica</var> of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
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
	 Object: id-notifica-decorrenza-termini
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(fileName, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
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
	 * Serialize to file system in <var>file</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(file, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
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
	 * Serialize to output stream <var>out</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param out OutputStream to serialize the object <var>idNotificaDecorrenzaTermini</var>
	 * @param idNotificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(out, IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
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
	 * Serialize to byte array the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param idNotificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
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
	 * Serialize to String the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param idNotificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(IdNotificaDecorrenzaTermini.class, idNotificaDecorrenzaTermini, false).toString();
	}
	/**
	 * Serialize to String the object <var>idNotificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
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
	 Object: id-trasmissione-esito
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param fileName Xml file to serialize the object <var>idTrasmissioneEsito</var>
	 * @param idTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdTrasmissioneEsito idTrasmissioneEsito) throws SerializerException {
		this.objToXml(fileName, IdTrasmissioneEsito.class, idTrasmissioneEsito, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param fileName Xml file to serialize the object <var>idTrasmissioneEsito</var>
	 * @param idTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdTrasmissioneEsito idTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdTrasmissioneEsito.class, idTrasmissioneEsito, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param file Xml file to serialize the object <var>idTrasmissioneEsito</var>
	 * @param idTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdTrasmissioneEsito idTrasmissioneEsito) throws SerializerException {
		this.objToXml(file, IdTrasmissioneEsito.class, idTrasmissioneEsito, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param file Xml file to serialize the object <var>idTrasmissioneEsito</var>
	 * @param idTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdTrasmissioneEsito idTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdTrasmissioneEsito.class, idTrasmissioneEsito, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param out OutputStream to serialize the object <var>idTrasmissioneEsito</var>
	 * @param idTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdTrasmissioneEsito idTrasmissioneEsito) throws SerializerException {
		this.objToXml(out, IdTrasmissioneEsito.class, idTrasmissioneEsito, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param out OutputStream to serialize the object <var>idTrasmissioneEsito</var>
	 * @param idTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdTrasmissioneEsito idTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdTrasmissioneEsito.class, idTrasmissioneEsito, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param idTrasmissioneEsito Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdTrasmissioneEsito idTrasmissioneEsito) throws SerializerException {
		return this.objToXml(IdTrasmissioneEsito.class, idTrasmissioneEsito, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param idTrasmissioneEsito Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdTrasmissioneEsito idTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdTrasmissioneEsito.class, idTrasmissioneEsito, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param idTrasmissioneEsito Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdTrasmissioneEsito idTrasmissioneEsito) throws SerializerException {
		return this.objToXml(IdTrasmissioneEsito.class, idTrasmissioneEsito, false).toString();
	}
	/**
	 * Serialize to String the object <var>idTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param idTrasmissioneEsito Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdTrasmissioneEsito idTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdTrasmissioneEsito.class, idTrasmissioneEsito, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Dipartimento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Dipartimento dipartimento) throws SerializerException {
		this.objToXml(fileName, Dipartimento.class, dipartimento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
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
	 * Serialize to file system in <var>file</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Dipartimento dipartimento) throws SerializerException {
		this.objToXml(file, Dipartimento.class, dipartimento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
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
	 * Serialize to output stream <var>out</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimento</var>
	 * @param dipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Dipartimento dipartimento) throws SerializerException {
		this.objToXml(out, Dipartimento.class, dipartimento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
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
	 * Serialize to byte array the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param dipartimento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Dipartimento dipartimento) throws SerializerException {
		return this.objToXml(Dipartimento.class, dipartimento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
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
	 * Serialize to String the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param dipartimento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Dipartimento dipartimento) throws SerializerException {
		return this.objToXml(Dipartimento.class, dipartimento, false).toString();
	}
	/**
	 * Serialize to String the object <var>dipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
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
	 Object: LottoFatture
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param fileName Xml file to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,LottoFatture lottoFatture) throws SerializerException {
		this.objToXml(fileName, LottoFatture.class, lottoFatture, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
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
	 * Serialize to file system in <var>file</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param file Xml file to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,LottoFatture lottoFatture) throws SerializerException {
		this.objToXml(file, LottoFatture.class, lottoFatture, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
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
	 * Serialize to output stream <var>out</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param out OutputStream to serialize the object <var>lottoFatture</var>
	 * @param lottoFatture Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,LottoFatture lottoFatture) throws SerializerException {
		this.objToXml(out, LottoFatture.class, lottoFatture, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
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
	 * Serialize to byte array the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param lottoFatture Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(LottoFatture lottoFatture) throws SerializerException {
		return this.objToXml(LottoFatture.class, lottoFatture, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
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
	 * Serialize to String the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param lottoFatture Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(LottoFatture lottoFatture) throws SerializerException {
		return this.objToXml(LottoFatture.class, lottoFatture, false).toString();
	}
	/**
	 * Serialize to String the object <var>lottoFatture</var> of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
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
	 Object: id-trasmissione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idTrasmissione</var>
	 * @param idTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdTrasmissione idTrasmissione) throws SerializerException {
		this.objToXml(fileName, IdTrasmissione.class, idTrasmissione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idTrasmissione</var>
	 * @param idTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdTrasmissione idTrasmissione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdTrasmissione.class, idTrasmissione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param file Xml file to serialize the object <var>idTrasmissione</var>
	 * @param idTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdTrasmissione idTrasmissione) throws SerializerException {
		this.objToXml(file, IdTrasmissione.class, idTrasmissione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param file Xml file to serialize the object <var>idTrasmissione</var>
	 * @param idTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdTrasmissione idTrasmissione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdTrasmissione.class, idTrasmissione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param out OutputStream to serialize the object <var>idTrasmissione</var>
	 * @param idTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdTrasmissione idTrasmissione) throws SerializerException {
		this.objToXml(out, IdTrasmissione.class, idTrasmissione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param out OutputStream to serialize the object <var>idTrasmissione</var>
	 * @param idTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdTrasmissione idTrasmissione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdTrasmissione.class, idTrasmissione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param idTrasmissione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdTrasmissione idTrasmissione) throws SerializerException {
		return this.objToXml(IdTrasmissione.class, idTrasmissione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param idTrasmissione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdTrasmissione idTrasmissione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdTrasmissione.class, idTrasmissione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param idTrasmissione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdTrasmissione idTrasmissione) throws SerializerException {
		return this.objToXml(IdTrasmissione.class, idTrasmissione, false).toString();
	}
	/**
	 * Serialize to String the object <var>idTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param idTrasmissione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdTrasmissione idTrasmissione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdTrasmissione.class, idTrasmissione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccTracciaTrasmissioneEsito
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccTracciaTrasmissioneEsito</var>
	 * @param pccTracciaTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) throws SerializerException {
		this.objToXml(fileName, PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccTracciaTrasmissioneEsito</var>
	 * @param pccTracciaTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param file Xml file to serialize the object <var>pccTracciaTrasmissioneEsito</var>
	 * @param pccTracciaTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) throws SerializerException {
		this.objToXml(file, PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param file Xml file to serialize the object <var>pccTracciaTrasmissioneEsito</var>
	 * @param pccTracciaTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param out OutputStream to serialize the object <var>pccTracciaTrasmissioneEsito</var>
	 * @param pccTracciaTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) throws SerializerException {
		this.objToXml(out, PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param out OutputStream to serialize the object <var>pccTracciaTrasmissioneEsito</var>
	 * @param pccTracciaTrasmissioneEsito Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param pccTracciaTrasmissioneEsito Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param pccTracciaTrasmissioneEsito Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param pccTracciaTrasmissioneEsito Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccTracciaTrasmissioneEsito</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param pccTracciaTrasmissioneEsito Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissioneEsito.class, pccTracciaTrasmissioneEsito, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccErroreElaborazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccErroreElaborazione</var>
	 * @param pccErroreElaborazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccErroreElaborazione pccErroreElaborazione) throws SerializerException {
		this.objToXml(fileName, PccErroreElaborazione.class, pccErroreElaborazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccErroreElaborazione</var>
	 * @param pccErroreElaborazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccErroreElaborazione pccErroreElaborazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccErroreElaborazione.class, pccErroreElaborazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccErroreElaborazione</var>
	 * @param pccErroreElaborazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccErroreElaborazione pccErroreElaborazione) throws SerializerException {
		this.objToXml(file, PccErroreElaborazione.class, pccErroreElaborazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccErroreElaborazione</var>
	 * @param pccErroreElaborazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccErroreElaborazione pccErroreElaborazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccErroreElaborazione.class, pccErroreElaborazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccErroreElaborazione</var>
	 * @param pccErroreElaborazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccErroreElaborazione pccErroreElaborazione) throws SerializerException {
		this.objToXml(out, PccErroreElaborazione.class, pccErroreElaborazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccErroreElaborazione</var>
	 * @param pccErroreElaborazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccErroreElaborazione pccErroreElaborazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccErroreElaborazione.class, pccErroreElaborazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param pccErroreElaborazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccErroreElaborazione pccErroreElaborazione) throws SerializerException {
		return this.objToXml(PccErroreElaborazione.class, pccErroreElaborazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param pccErroreElaborazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccErroreElaborazione pccErroreElaborazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccErroreElaborazione.class, pccErroreElaborazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param pccErroreElaborazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccErroreElaborazione pccErroreElaborazione) throws SerializerException {
		return this.objToXml(PccErroreElaborazione.class, pccErroreElaborazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccErroreElaborazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param pccErroreElaborazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccErroreElaborazione pccErroreElaborazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccErroreElaborazione.class, pccErroreElaborazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: DipartimentoProperty
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,DipartimentoProperty dipartimentoProperty) throws SerializerException {
		this.objToXml(fileName, DipartimentoProperty.class, dipartimentoProperty, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
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
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,DipartimentoProperty dipartimentoProperty) throws SerializerException {
		this.objToXml(file, DipartimentoProperty.class, dipartimentoProperty, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
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
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimentoProperty</var>
	 * @param dipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,DipartimentoProperty dipartimentoProperty) throws SerializerException {
		this.objToXml(out, DipartimentoProperty.class, dipartimentoProperty, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
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
	 * Serialize to byte array the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param dipartimentoProperty Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(DipartimentoProperty dipartimentoProperty) throws SerializerException {
		return this.objToXml(DipartimentoProperty.class, dipartimentoProperty, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
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
	 * Serialize to String the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param dipartimentoProperty Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(DipartimentoProperty dipartimentoProperty) throws SerializerException {
		return this.objToXml(DipartimentoProperty.class, dipartimentoProperty, false).toString();
	}
	/**
	 * Serialize to String the object <var>dipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
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
	 Object: id-protocollo
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param fileName Xml file to serialize the object <var>idProtocollo</var>
	 * @param idProtocollo Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdProtocollo idProtocollo) throws SerializerException {
		this.objToXml(fileName, IdProtocollo.class, idProtocollo, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param fileName Xml file to serialize the object <var>idProtocollo</var>
	 * @param idProtocollo Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdProtocollo idProtocollo,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdProtocollo.class, idProtocollo, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param file Xml file to serialize the object <var>idProtocollo</var>
	 * @param idProtocollo Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdProtocollo idProtocollo) throws SerializerException {
		this.objToXml(file, IdProtocollo.class, idProtocollo, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param file Xml file to serialize the object <var>idProtocollo</var>
	 * @param idProtocollo Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdProtocollo idProtocollo,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdProtocollo.class, idProtocollo, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param out OutputStream to serialize the object <var>idProtocollo</var>
	 * @param idProtocollo Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdProtocollo idProtocollo) throws SerializerException {
		this.objToXml(out, IdProtocollo.class, idProtocollo, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param out OutputStream to serialize the object <var>idProtocollo</var>
	 * @param idProtocollo Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdProtocollo idProtocollo,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdProtocollo.class, idProtocollo, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param idProtocollo Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdProtocollo idProtocollo) throws SerializerException {
		return this.objToXml(IdProtocollo.class, idProtocollo, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param idProtocollo Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdProtocollo idProtocollo,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdProtocollo.class, idProtocollo, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param idProtocollo Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdProtocollo idProtocollo) throws SerializerException {
		return this.objToXml(IdProtocollo.class, idProtocollo, false).toString();
	}
	/**
	 * Serialize to String the object <var>idProtocollo</var> of type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param idProtocollo Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdProtocollo idProtocollo,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdProtocollo.class, idProtocollo, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: RegistroProperty
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,RegistroProperty registroProperty) throws SerializerException {
		this.objToXml(fileName, RegistroProperty.class, registroProperty, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
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
	 * Serialize to file system in <var>file</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param file Xml file to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,RegistroProperty registroProperty) throws SerializerException {
		this.objToXml(file, RegistroProperty.class, registroProperty, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
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
	 * Serialize to output stream <var>out</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>registroProperty</var>
	 * @param registroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,RegistroProperty registroProperty) throws SerializerException {
		this.objToXml(out, RegistroProperty.class, registroProperty, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
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
	 * Serialize to byte array the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param registroProperty Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(RegistroProperty registroProperty) throws SerializerException {
		return this.objToXml(RegistroProperty.class, registroProperty, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
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
	 * Serialize to String the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param registroProperty Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(RegistroProperty registroProperty) throws SerializerException {
		return this.objToXml(RegistroProperty.class, registroProperty, false).toString();
	}
	/**
	 * Serialize to String the object <var>registroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
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
	 Object: AllegatoFattura
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param fileName Xml file to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,AllegatoFattura allegatoFattura) throws SerializerException {
		this.objToXml(fileName, AllegatoFattura.class, allegatoFattura, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
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
	 * Serialize to file system in <var>file</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param file Xml file to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,AllegatoFattura allegatoFattura) throws SerializerException {
		this.objToXml(file, AllegatoFattura.class, allegatoFattura, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
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
	 * Serialize to output stream <var>out</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param out OutputStream to serialize the object <var>allegatoFattura</var>
	 * @param allegatoFattura Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,AllegatoFattura allegatoFattura) throws SerializerException {
		this.objToXml(out, AllegatoFattura.class, allegatoFattura, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
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
	 * Serialize to byte array the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param allegatoFattura Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(AllegatoFattura allegatoFattura) throws SerializerException {
		return this.objToXml(AllegatoFattura.class, allegatoFattura, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
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
	 * Serialize to String the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param allegatoFattura Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(AllegatoFattura allegatoFattura) throws SerializerException {
		return this.objToXml(AllegatoFattura.class, allegatoFattura, false).toString();
	}
	/**
	 * Serialize to String the object <var>allegatoFattura</var> of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
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
	 Object: id-rispedizione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idRispedizione</var>
	 * @param idRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdRispedizione idRispedizione) throws SerializerException {
		this.objToXml(fileName, IdRispedizione.class, idRispedizione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idRispedizione</var>
	 * @param idRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdRispedizione idRispedizione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdRispedizione.class, idRispedizione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param file Xml file to serialize the object <var>idRispedizione</var>
	 * @param idRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdRispedizione idRispedizione) throws SerializerException {
		this.objToXml(file, IdRispedizione.class, idRispedizione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param file Xml file to serialize the object <var>idRispedizione</var>
	 * @param idRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdRispedizione idRispedizione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdRispedizione.class, idRispedizione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param out OutputStream to serialize the object <var>idRispedizione</var>
	 * @param idRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdRispedizione idRispedizione) throws SerializerException {
		this.objToXml(out, IdRispedizione.class, idRispedizione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param out OutputStream to serialize the object <var>idRispedizione</var>
	 * @param idRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdRispedizione idRispedizione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdRispedizione.class, idRispedizione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param idRispedizione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdRispedizione idRispedizione) throws SerializerException {
		return this.objToXml(IdRispedizione.class, idRispedizione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param idRispedizione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdRispedizione idRispedizione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdRispedizione.class, idRispedizione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param idRispedizione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdRispedizione idRispedizione) throws SerializerException {
		return this.objToXml(IdRispedizione.class, idRispedizione, false).toString();
	}
	/**
	 * Serialize to String the object <var>idRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param idRispedizione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdRispedizione idRispedizione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdRispedizione.class, idRispedizione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-registro
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param fileName Xml file to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdRegistro idRegistro) throws SerializerException {
		this.objToXml(fileName, IdRegistro.class, idRegistro, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
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
	 * Serialize to file system in <var>file</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param file Xml file to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdRegistro idRegistro) throws SerializerException {
		this.objToXml(file, IdRegistro.class, idRegistro, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
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
	 * Serialize to output stream <var>out</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param out OutputStream to serialize the object <var>idRegistro</var>
	 * @param idRegistro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdRegistro idRegistro) throws SerializerException {
		this.objToXml(out, IdRegistro.class, idRegistro, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
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
	 * Serialize to byte array the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param idRegistro Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdRegistro idRegistro) throws SerializerException {
		return this.objToXml(IdRegistro.class, idRegistro, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
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
	 * Serialize to String the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param idRegistro Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdRegistro idRegistro) throws SerializerException {
		return this.objToXml(IdRegistro.class, idRegistro, false).toString();
	}
	/**
	 * Serialize to String the object <var>idRegistro</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
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
	 Object: id-dipartimento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdDipartimento idDipartimento) throws SerializerException {
		this.objToXml(fileName, IdDipartimento.class, idDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
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
	 * Serialize to file system in <var>file</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdDipartimento idDipartimento) throws SerializerException {
		this.objToXml(file, IdDipartimento.class, idDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
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
	 * Serialize to output stream <var>out</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>idDipartimento</var>
	 * @param idDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdDipartimento idDipartimento) throws SerializerException {
		this.objToXml(out, IdDipartimento.class, idDipartimento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
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
	 * Serialize to byte array the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param idDipartimento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdDipartimento idDipartimento) throws SerializerException {
		return this.objToXml(IdDipartimento.class, idDipartimento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
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
	 * Serialize to String the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param idDipartimento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDipartimento idDipartimento) throws SerializerException {
		return this.objToXml(IdDipartimento.class, idDipartimento, false).toString();
	}
	/**
	 * Serialize to String the object <var>idDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param idDipartimento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDipartimento idDipartimento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdDipartimento.class, idDipartimento, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccDipartimentoOperazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccDipartimentoOperazione</var>
	 * @param pccDipartimentoOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccDipartimentoOperazione pccDipartimentoOperazione) throws SerializerException {
		this.objToXml(fileName, PccDipartimentoOperazione.class, pccDipartimentoOperazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccDipartimentoOperazione</var>
	 * @param pccDipartimentoOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccDipartimentoOperazione pccDipartimentoOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccDipartimentoOperazione.class, pccDipartimentoOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccDipartimentoOperazione</var>
	 * @param pccDipartimentoOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccDipartimentoOperazione pccDipartimentoOperazione) throws SerializerException {
		this.objToXml(file, PccDipartimentoOperazione.class, pccDipartimentoOperazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccDipartimentoOperazione</var>
	 * @param pccDipartimentoOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccDipartimentoOperazione pccDipartimentoOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccDipartimentoOperazione.class, pccDipartimentoOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccDipartimentoOperazione</var>
	 * @param pccDipartimentoOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccDipartimentoOperazione pccDipartimentoOperazione) throws SerializerException {
		this.objToXml(out, PccDipartimentoOperazione.class, pccDipartimentoOperazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccDipartimentoOperazione</var>
	 * @param pccDipartimentoOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccDipartimentoOperazione pccDipartimentoOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccDipartimentoOperazione.class, pccDipartimentoOperazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param pccDipartimentoOperazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccDipartimentoOperazione pccDipartimentoOperazione) throws SerializerException {
		return this.objToXml(PccDipartimentoOperazione.class, pccDipartimentoOperazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param pccDipartimentoOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccDipartimentoOperazione pccDipartimentoOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccDipartimentoOperazione.class, pccDipartimentoOperazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param pccDipartimentoOperazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccDipartimentoOperazione pccDipartimentoOperazione) throws SerializerException {
		return this.objToXml(PccDipartimentoOperazione.class, pccDipartimentoOperazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccDipartimentoOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param pccDipartimentoOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccDipartimentoOperazione pccDipartimentoOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccDipartimentoOperazione.class, pccDipartimentoOperazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-operazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idOperazione</var>
	 * @param idOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdOperazione idOperazione) throws SerializerException {
		this.objToXml(fileName, IdOperazione.class, idOperazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idOperazione</var>
	 * @param idOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdOperazione idOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdOperazione.class, idOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>idOperazione</var>
	 * @param idOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdOperazione idOperazione) throws SerializerException {
		this.objToXml(file, IdOperazione.class, idOperazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>idOperazione</var>
	 * @param idOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdOperazione idOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdOperazione.class, idOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>idOperazione</var>
	 * @param idOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdOperazione idOperazione) throws SerializerException {
		this.objToXml(out, IdOperazione.class, idOperazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>idOperazione</var>
	 * @param idOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdOperazione idOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdOperazione.class, idOperazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param idOperazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdOperazione idOperazione) throws SerializerException {
		return this.objToXml(IdOperazione.class, idOperazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param idOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdOperazione idOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdOperazione.class, idOperazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param idOperazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdOperazione idOperazione) throws SerializerException {
		return this.objToXml(IdOperazione.class, idOperazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>idOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param idOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdOperazione idOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdOperazione.class, idOperazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: NotificaDecorrenzaTermini
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(fileName, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
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
	 * Serialize to file system in <var>file</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(file, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
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
	 * Serialize to output stream <var>out</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param out OutputStream to serialize the object <var>notificaDecorrenzaTermini</var>
	 * @param notificaDecorrenzaTermini Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		this.objToXml(out, NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
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
	 * Serialize to byte array the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param notificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
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
	 * Serialize to String the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param notificaDecorrenzaTermini Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws SerializerException {
		return this.objToXml(NotificaDecorrenzaTermini.class, notificaDecorrenzaTermini, false).toString();
	}
	/**
	 * Serialize to String the object <var>notificaDecorrenzaTermini</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
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
	 Object: id-traccia
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param fileName Xml file to serialize the object <var>idTraccia</var>
	 * @param idTraccia Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdTraccia idTraccia) throws SerializerException {
		this.objToXml(fileName, IdTraccia.class, idTraccia, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param fileName Xml file to serialize the object <var>idTraccia</var>
	 * @param idTraccia Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdTraccia idTraccia,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdTraccia.class, idTraccia, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param file Xml file to serialize the object <var>idTraccia</var>
	 * @param idTraccia Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdTraccia idTraccia) throws SerializerException {
		this.objToXml(file, IdTraccia.class, idTraccia, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param file Xml file to serialize the object <var>idTraccia</var>
	 * @param idTraccia Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdTraccia idTraccia,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdTraccia.class, idTraccia, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param out OutputStream to serialize the object <var>idTraccia</var>
	 * @param idTraccia Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdTraccia idTraccia) throws SerializerException {
		this.objToXml(out, IdTraccia.class, idTraccia, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param out OutputStream to serialize the object <var>idTraccia</var>
	 * @param idTraccia Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdTraccia idTraccia,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdTraccia.class, idTraccia, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param idTraccia Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdTraccia idTraccia) throws SerializerException {
		return this.objToXml(IdTraccia.class, idTraccia, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param idTraccia Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdTraccia idTraccia,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdTraccia.class, idTraccia, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param idTraccia Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdTraccia idTraccia) throws SerializerException {
		return this.objToXml(IdTraccia.class, idTraccia, false).toString();
	}
	/**
	 * Serialize to String the object <var>idTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param idTraccia Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdTraccia idTraccia,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdTraccia.class, idTraccia, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-ente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param fileName Xml file to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdEnte idEnte) throws SerializerException {
		this.objToXml(fileName, IdEnte.class, idEnte, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
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
	 * Serialize to file system in <var>file</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param file Xml file to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdEnte idEnte) throws SerializerException {
		this.objToXml(file, IdEnte.class, idEnte, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
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
	 * Serialize to output stream <var>out</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param out OutputStream to serialize the object <var>idEnte</var>
	 * @param idEnte Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdEnte idEnte) throws SerializerException {
		this.objToXml(out, IdEnte.class, idEnte, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
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
	 * Serialize to byte array the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param idEnte Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdEnte idEnte) throws SerializerException {
		return this.objToXml(IdEnte.class, idEnte, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
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
	 * Serialize to String the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param idEnte Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdEnte idEnte) throws SerializerException {
		return this.objToXml(IdEnte.class, idEnte, false).toString();
	}
	/**
	 * Serialize to String the object <var>idEnte</var> of type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
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
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param fileName Xml file to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		this.objToXml(fileName, DipartimentoPropertyValue.class, dipartimentoPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
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
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param file Xml file to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		this.objToXml(file, DipartimentoPropertyValue.class, dipartimentoPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
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
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param out OutputStream to serialize the object <var>dipartimentoPropertyValue</var>
	 * @param dipartimentoPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		this.objToXml(out, DipartimentoPropertyValue.class, dipartimentoPropertyValue, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
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
	 * Serialize to byte array the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param dipartimentoPropertyValue Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		return this.objToXml(DipartimentoPropertyValue.class, dipartimentoPropertyValue, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
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
	 * Serialize to String the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param dipartimentoPropertyValue Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(DipartimentoPropertyValue dipartimentoPropertyValue) throws SerializerException {
		return this.objToXml(DipartimentoPropertyValue.class, dipartimentoPropertyValue, false).toString();
	}
	/**
	 * Serialize to String the object <var>dipartimentoPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
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
	 Object: Metadato
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param fileName Xml file to serialize the object <var>metadato</var>
	 * @param metadato Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Metadato metadato) throws SerializerException {
		this.objToXml(fileName, Metadato.class, metadato, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param fileName Xml file to serialize the object <var>metadato</var>
	 * @param metadato Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Metadato metadato,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Metadato.class, metadato, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param file Xml file to serialize the object <var>metadato</var>
	 * @param metadato Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Metadato metadato) throws SerializerException {
		this.objToXml(file, Metadato.class, metadato, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param file Xml file to serialize the object <var>metadato</var>
	 * @param metadato Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Metadato metadato,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Metadato.class, metadato, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param out OutputStream to serialize the object <var>metadato</var>
	 * @param metadato Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Metadato metadato) throws SerializerException {
		this.objToXml(out, Metadato.class, metadato, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param out OutputStream to serialize the object <var>metadato</var>
	 * @param metadato Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Metadato metadato,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Metadato.class, metadato, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param metadato Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Metadato metadato) throws SerializerException {
		return this.objToXml(Metadato.class, metadato, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param metadato Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Metadato metadato,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Metadato.class, metadato, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param metadato Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Metadato metadato) throws SerializerException {
		return this.objToXml(Metadato.class, metadato, false).toString();
	}
	/**
	 * Serialize to String the object <var>metadato</var> of type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param metadato Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Metadato metadato,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Metadato.class, metadato, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccRispedizione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccRispedizione</var>
	 * @param pccRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccRispedizione pccRispedizione) throws SerializerException {
		this.objToXml(fileName, PccRispedizione.class, pccRispedizione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccRispedizione</var>
	 * @param pccRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccRispedizione pccRispedizione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccRispedizione.class, pccRispedizione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param file Xml file to serialize the object <var>pccRispedizione</var>
	 * @param pccRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccRispedizione pccRispedizione) throws SerializerException {
		this.objToXml(file, PccRispedizione.class, pccRispedizione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param file Xml file to serialize the object <var>pccRispedizione</var>
	 * @param pccRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccRispedizione pccRispedizione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccRispedizione.class, pccRispedizione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccRispedizione</var>
	 * @param pccRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccRispedizione pccRispedizione) throws SerializerException {
		this.objToXml(out, PccRispedizione.class, pccRispedizione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccRispedizione</var>
	 * @param pccRispedizione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccRispedizione pccRispedizione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccRispedizione.class, pccRispedizione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param pccRispedizione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccRispedizione pccRispedizione) throws SerializerException {
		return this.objToXml(PccRispedizione.class, pccRispedizione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param pccRispedizione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccRispedizione pccRispedizione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccRispedizione.class, pccRispedizione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param pccRispedizione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccRispedizione pccRispedizione) throws SerializerException {
		return this.objToXml(PccRispedizione.class, pccRispedizione, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccRispedizione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param pccRispedizione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccRispedizione pccRispedizione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccRispedizione.class, pccRispedizione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: TracciaSDI
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param fileName Xml file to serialize the object <var>tracciaSDI</var>
	 * @param tracciaSDI Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,TracciaSDI tracciaSDI) throws SerializerException {
		this.objToXml(fileName, TracciaSDI.class, tracciaSDI, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param fileName Xml file to serialize the object <var>tracciaSDI</var>
	 * @param tracciaSDI Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,TracciaSDI tracciaSDI,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, TracciaSDI.class, tracciaSDI, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param file Xml file to serialize the object <var>tracciaSDI</var>
	 * @param tracciaSDI Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,TracciaSDI tracciaSDI) throws SerializerException {
		this.objToXml(file, TracciaSDI.class, tracciaSDI, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param file Xml file to serialize the object <var>tracciaSDI</var>
	 * @param tracciaSDI Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,TracciaSDI tracciaSDI,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, TracciaSDI.class, tracciaSDI, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param out OutputStream to serialize the object <var>tracciaSDI</var>
	 * @param tracciaSDI Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,TracciaSDI tracciaSDI) throws SerializerException {
		this.objToXml(out, TracciaSDI.class, tracciaSDI, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param out OutputStream to serialize the object <var>tracciaSDI</var>
	 * @param tracciaSDI Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,TracciaSDI tracciaSDI,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, TracciaSDI.class, tracciaSDI, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param tracciaSDI Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(TracciaSDI tracciaSDI) throws SerializerException {
		return this.objToXml(TracciaSDI.class, tracciaSDI, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param tracciaSDI Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(TracciaSDI tracciaSDI,boolean prettyPrint) throws SerializerException {
		return this.objToXml(TracciaSDI.class, tracciaSDI, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param tracciaSDI Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(TracciaSDI tracciaSDI) throws SerializerException {
		return this.objToXml(TracciaSDI.class, tracciaSDI, false).toString();
	}
	/**
	 * Serialize to String the object <var>tracciaSDI</var> of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param tracciaSDI Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(TracciaSDI tracciaSDI,boolean prettyPrint) throws SerializerException {
		return this.objToXml(TracciaSDI.class, tracciaSDI, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Registro
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param fileName Xml file to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Registro registro) throws SerializerException {
		this.objToXml(fileName, Registro.class, registro, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
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
	 * Serialize to file system in <var>file</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param file Xml file to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Registro registro) throws SerializerException {
		this.objToXml(file, Registro.class, registro, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
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
	 * Serialize to output stream <var>out</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param out OutputStream to serialize the object <var>registro</var>
	 * @param registro Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Registro registro) throws SerializerException {
		this.objToXml(out, Registro.class, registro, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
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
	 * Serialize to byte array the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param registro Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Registro registro) throws SerializerException {
		return this.objToXml(Registro.class, registro, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
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
	 * Serialize to String the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param registro Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Registro registro) throws SerializerException {
		return this.objToXml(Registro.class, registro, false).toString();
	}
	/**
	 * Serialize to String the object <var>registro</var> of type {@link org.govmix.proxy.fatturapa.orm.Registro}
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
	 Object: RegistroPropertyValue
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param fileName Xml file to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,RegistroPropertyValue registroPropertyValue) throws SerializerException {
		this.objToXml(fileName, RegistroPropertyValue.class, registroPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
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
	 * Serialize to file system in <var>file</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param file Xml file to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,RegistroPropertyValue registroPropertyValue) throws SerializerException {
		this.objToXml(file, RegistroPropertyValue.class, registroPropertyValue, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
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
	 * Serialize to output stream <var>out</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param out OutputStream to serialize the object <var>registroPropertyValue</var>
	 * @param registroPropertyValue Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,RegistroPropertyValue registroPropertyValue) throws SerializerException {
		this.objToXml(out, RegistroPropertyValue.class, registroPropertyValue, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
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
	 * Serialize to byte array the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param registroPropertyValue Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(RegistroPropertyValue registroPropertyValue) throws SerializerException {
		return this.objToXml(RegistroPropertyValue.class, registroPropertyValue, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
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
	 * Serialize to String the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param registroPropertyValue Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(RegistroPropertyValue registroPropertyValue) throws SerializerException {
		return this.objToXml(RegistroPropertyValue.class, registroPropertyValue, false).toString();
	}
	/**
	 * Serialize to String the object <var>registroPropertyValue</var> of type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
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
	 Object: id-pagamento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param fileName Xml file to serialize the object <var>idPagamento</var>
	 * @param idPagamento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdPagamento idPagamento) throws SerializerException {
		this.objToXml(fileName, IdPagamento.class, idPagamento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param fileName Xml file to serialize the object <var>idPagamento</var>
	 * @param idPagamento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdPagamento idPagamento,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdPagamento.class, idPagamento, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param file Xml file to serialize the object <var>idPagamento</var>
	 * @param idPagamento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdPagamento idPagamento) throws SerializerException {
		this.objToXml(file, IdPagamento.class, idPagamento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param file Xml file to serialize the object <var>idPagamento</var>
	 * @param idPagamento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdPagamento idPagamento,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdPagamento.class, idPagamento, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param out OutputStream to serialize the object <var>idPagamento</var>
	 * @param idPagamento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdPagamento idPagamento) throws SerializerException {
		this.objToXml(out, IdPagamento.class, idPagamento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param out OutputStream to serialize the object <var>idPagamento</var>
	 * @param idPagamento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdPagamento idPagamento,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdPagamento.class, idPagamento, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param idPagamento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdPagamento idPagamento) throws SerializerException {
		return this.objToXml(IdPagamento.class, idPagamento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param idPagamento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdPagamento idPagamento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdPagamento.class, idPagamento, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param idPagamento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdPagamento idPagamento) throws SerializerException {
		return this.objToXml(IdPagamento.class, idPagamento, false).toString();
	}
	/**
	 * Serialize to String the object <var>idPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param idPagamento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdPagamento idPagamento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdPagamento.class, idPagamento, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-comunicazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idComunicazione</var>
	 * @param idComunicazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdComunicazione idComunicazione) throws SerializerException {
		this.objToXml(fileName, IdComunicazione.class, idComunicazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idComunicazione</var>
	 * @param idComunicazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdComunicazione idComunicazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdComunicazione.class, idComunicazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param file Xml file to serialize the object <var>idComunicazione</var>
	 * @param idComunicazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdComunicazione idComunicazione) throws SerializerException {
		this.objToXml(file, IdComunicazione.class, idComunicazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param file Xml file to serialize the object <var>idComunicazione</var>
	 * @param idComunicazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdComunicazione idComunicazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdComunicazione.class, idComunicazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param out OutputStream to serialize the object <var>idComunicazione</var>
	 * @param idComunicazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdComunicazione idComunicazione) throws SerializerException {
		this.objToXml(out, IdComunicazione.class, idComunicazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param out OutputStream to serialize the object <var>idComunicazione</var>
	 * @param idComunicazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdComunicazione idComunicazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdComunicazione.class, idComunicazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param idComunicazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdComunicazione idComunicazione) throws SerializerException {
		return this.objToXml(IdComunicazione.class, idComunicazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param idComunicazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdComunicazione idComunicazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdComunicazione.class, idComunicazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param idComunicazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdComunicazione idComunicazione) throws SerializerException {
		return this.objToXml(IdComunicazione.class, idComunicazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>idComunicazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param idComunicazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdComunicazione idComunicazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdComunicazione.class, idComunicazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccContabilizzazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccContabilizzazione</var>
	 * @param pccContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccContabilizzazione pccContabilizzazione) throws SerializerException {
		this.objToXml(fileName, PccContabilizzazione.class, pccContabilizzazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccContabilizzazione</var>
	 * @param pccContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccContabilizzazione pccContabilizzazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccContabilizzazione.class, pccContabilizzazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccContabilizzazione</var>
	 * @param pccContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccContabilizzazione pccContabilizzazione) throws SerializerException {
		this.objToXml(file, PccContabilizzazione.class, pccContabilizzazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccContabilizzazione</var>
	 * @param pccContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccContabilizzazione pccContabilizzazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccContabilizzazione.class, pccContabilizzazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccContabilizzazione</var>
	 * @param pccContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccContabilizzazione pccContabilizzazione) throws SerializerException {
		this.objToXml(out, PccContabilizzazione.class, pccContabilizzazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccContabilizzazione</var>
	 * @param pccContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccContabilizzazione pccContabilizzazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccContabilizzazione.class, pccContabilizzazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param pccContabilizzazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccContabilizzazione pccContabilizzazione) throws SerializerException {
		return this.objToXml(PccContabilizzazione.class, pccContabilizzazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param pccContabilizzazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccContabilizzazione pccContabilizzazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccContabilizzazione.class, pccContabilizzazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param pccContabilizzazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccContabilizzazione pccContabilizzazione) throws SerializerException {
		return this.objToXml(PccContabilizzazione.class, pccContabilizzazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param pccContabilizzazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccContabilizzazione pccContabilizzazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccContabilizzazione.class, pccContabilizzazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccNotifica
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccNotifica</var>
	 * @param pccNotifica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccNotifica pccNotifica) throws SerializerException {
		this.objToXml(fileName, PccNotifica.class, pccNotifica, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccNotifica</var>
	 * @param pccNotifica Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccNotifica pccNotifica,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccNotifica.class, pccNotifica, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param file Xml file to serialize the object <var>pccNotifica</var>
	 * @param pccNotifica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccNotifica pccNotifica) throws SerializerException {
		this.objToXml(file, PccNotifica.class, pccNotifica, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param file Xml file to serialize the object <var>pccNotifica</var>
	 * @param pccNotifica Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccNotifica pccNotifica,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccNotifica.class, pccNotifica, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param out OutputStream to serialize the object <var>pccNotifica</var>
	 * @param pccNotifica Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccNotifica pccNotifica) throws SerializerException {
		this.objToXml(out, PccNotifica.class, pccNotifica, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param out OutputStream to serialize the object <var>pccNotifica</var>
	 * @param pccNotifica Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccNotifica pccNotifica,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccNotifica.class, pccNotifica, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param pccNotifica Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccNotifica pccNotifica) throws SerializerException {
		return this.objToXml(PccNotifica.class, pccNotifica, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param pccNotifica Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccNotifica pccNotifica,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccNotifica.class, pccNotifica, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param pccNotifica Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccNotifica pccNotifica) throws SerializerException {
		return this.objToXml(PccNotifica.class, pccNotifica, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccNotifica</var> of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param pccNotifica Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccNotifica pccNotifica,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccNotifica.class, pccNotifica, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccTraccia
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccTraccia</var>
	 * @param pccTraccia Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccTraccia pccTraccia) throws SerializerException {
		this.objToXml(fileName, PccTraccia.class, pccTraccia, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccTraccia</var>
	 * @param pccTraccia Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccTraccia pccTraccia,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccTraccia.class, pccTraccia, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param file Xml file to serialize the object <var>pccTraccia</var>
	 * @param pccTraccia Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccTraccia pccTraccia) throws SerializerException {
		this.objToXml(file, PccTraccia.class, pccTraccia, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param file Xml file to serialize the object <var>pccTraccia</var>
	 * @param pccTraccia Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccTraccia pccTraccia,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccTraccia.class, pccTraccia, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param out OutputStream to serialize the object <var>pccTraccia</var>
	 * @param pccTraccia Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccTraccia pccTraccia) throws SerializerException {
		this.objToXml(out, PccTraccia.class, pccTraccia, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param out OutputStream to serialize the object <var>pccTraccia</var>
	 * @param pccTraccia Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccTraccia pccTraccia,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccTraccia.class, pccTraccia, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param pccTraccia Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccTraccia pccTraccia) throws SerializerException {
		return this.objToXml(PccTraccia.class, pccTraccia, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param pccTraccia Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccTraccia pccTraccia,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccTraccia.class, pccTraccia, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param pccTraccia Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccTraccia pccTraccia) throws SerializerException {
		return this.objToXml(PccTraccia.class, pccTraccia, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccTraccia</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param pccTraccia Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccTraccia pccTraccia,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccTraccia.class, pccTraccia, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-registro-property
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>idRegistroProperty</var>
	 * @param idRegistroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdRegistroProperty idRegistroProperty) throws SerializerException {
		this.objToXml(fileName, IdRegistroProperty.class, idRegistroProperty, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>idRegistroProperty</var>
	 * @param idRegistroProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdRegistroProperty idRegistroProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdRegistroProperty.class, idRegistroProperty, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param file Xml file to serialize the object <var>idRegistroProperty</var>
	 * @param idRegistroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdRegistroProperty idRegistroProperty) throws SerializerException {
		this.objToXml(file, IdRegistroProperty.class, idRegistroProperty, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param file Xml file to serialize the object <var>idRegistroProperty</var>
	 * @param idRegistroProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdRegistroProperty idRegistroProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdRegistroProperty.class, idRegistroProperty, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>idRegistroProperty</var>
	 * @param idRegistroProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdRegistroProperty idRegistroProperty) throws SerializerException {
		this.objToXml(out, IdRegistroProperty.class, idRegistroProperty, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>idRegistroProperty</var>
	 * @param idRegistroProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdRegistroProperty idRegistroProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdRegistroProperty.class, idRegistroProperty, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param idRegistroProperty Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdRegistroProperty idRegistroProperty) throws SerializerException {
		return this.objToXml(IdRegistroProperty.class, idRegistroProperty, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param idRegistroProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdRegistroProperty idRegistroProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdRegistroProperty.class, idRegistroProperty, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param idRegistroProperty Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdRegistroProperty idRegistroProperty) throws SerializerException {
		return this.objToXml(IdRegistroProperty.class, idRegistroProperty, false).toString();
	}
	/**
	 * Serialize to String the object <var>idRegistroProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param idRegistroProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdRegistroProperty idRegistroProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdRegistroProperty.class, idRegistroProperty, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-dipartimento-property
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>idDipartimentoProperty</var>
	 * @param idDipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdDipartimentoProperty idDipartimentoProperty) throws SerializerException {
		this.objToXml(fileName, IdDipartimentoProperty.class, idDipartimentoProperty, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param fileName Xml file to serialize the object <var>idDipartimentoProperty</var>
	 * @param idDipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdDipartimentoProperty idDipartimentoProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdDipartimentoProperty.class, idDipartimentoProperty, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param file Xml file to serialize the object <var>idDipartimentoProperty</var>
	 * @param idDipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdDipartimentoProperty idDipartimentoProperty) throws SerializerException {
		this.objToXml(file, IdDipartimentoProperty.class, idDipartimentoProperty, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param file Xml file to serialize the object <var>idDipartimentoProperty</var>
	 * @param idDipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdDipartimentoProperty idDipartimentoProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdDipartimentoProperty.class, idDipartimentoProperty, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>idDipartimentoProperty</var>
	 * @param idDipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdDipartimentoProperty idDipartimentoProperty) throws SerializerException {
		this.objToXml(out, IdDipartimentoProperty.class, idDipartimentoProperty, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param out OutputStream to serialize the object <var>idDipartimentoProperty</var>
	 * @param idDipartimentoProperty Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdDipartimentoProperty idDipartimentoProperty,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdDipartimentoProperty.class, idDipartimentoProperty, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param idDipartimentoProperty Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdDipartimentoProperty idDipartimentoProperty) throws SerializerException {
		return this.objToXml(IdDipartimentoProperty.class, idDipartimentoProperty, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param idDipartimentoProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdDipartimentoProperty idDipartimentoProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdDipartimentoProperty.class, idDipartimentoProperty, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param idDipartimentoProperty Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDipartimentoProperty idDipartimentoProperty) throws SerializerException {
		return this.objToXml(IdDipartimentoProperty.class, idDipartimentoProperty, false).toString();
	}
	/**
	 * Serialize to String the object <var>idDipartimentoProperty</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param idDipartimentoProperty Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDipartimentoProperty idDipartimentoProperty,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdDipartimentoProperty.class, idDipartimentoProperty, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Ente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param fileName Xml file to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Ente ente) throws SerializerException {
		this.objToXml(fileName, Ente.class, ente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
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
	 * Serialize to file system in <var>file</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param file Xml file to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Ente ente) throws SerializerException {
		this.objToXml(file, Ente.class, ente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
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
	 * Serialize to output stream <var>out</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param out OutputStream to serialize the object <var>ente</var>
	 * @param ente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Ente ente) throws SerializerException {
		this.objToXml(out, Ente.class, ente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
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
	 * Serialize to byte array the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param ente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Ente ente) throws SerializerException {
		return this.objToXml(Ente.class, ente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
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
	 * Serialize to String the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param ente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Ente ente) throws SerializerException {
		return this.objToXml(Ente.class, ente, false).toString();
	}
	/**
	 * Serialize to String the object <var>ente</var> of type {@link org.govmix.proxy.fatturapa.orm.Ente}
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
	 Object: SIP
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param fileName Xml file to serialize the object <var>sip</var>
	 * @param sip Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,SIP sip) throws SerializerException {
		this.objToXml(fileName, SIP.class, sip, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param fileName Xml file to serialize the object <var>sip</var>
	 * @param sip Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,SIP sip,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, SIP.class, sip, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param file Xml file to serialize the object <var>sip</var>
	 * @param sip Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,SIP sip) throws SerializerException {
		this.objToXml(file, SIP.class, sip, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param file Xml file to serialize the object <var>sip</var>
	 * @param sip Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,SIP sip,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, SIP.class, sip, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param out OutputStream to serialize the object <var>sip</var>
	 * @param sip Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,SIP sip) throws SerializerException {
		this.objToXml(out, SIP.class, sip, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param out OutputStream to serialize the object <var>sip</var>
	 * @param sip Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,SIP sip,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, SIP.class, sip, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param sip Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(SIP sip) throws SerializerException {
		return this.objToXml(SIP.class, sip, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param sip Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(SIP sip,boolean prettyPrint) throws SerializerException {
		return this.objToXml(SIP.class, sip, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param sip Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(SIP sip) throws SerializerException {
		return this.objToXml(SIP.class, sip, false).toString();
	}
	/**
	 * Serialize to String the object <var>sip</var> of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param sip Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(SIP sip,boolean prettyPrint) throws SerializerException {
		return this.objToXml(SIP.class, sip, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: UtenteDipartimento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param fileName Xml file to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,UtenteDipartimento utenteDipartimento) throws SerializerException {
		this.objToXml(fileName, UtenteDipartimento.class, utenteDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
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
	 * Serialize to file system in <var>file</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param file Xml file to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,UtenteDipartimento utenteDipartimento) throws SerializerException {
		this.objToXml(file, UtenteDipartimento.class, utenteDipartimento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
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
	 * Serialize to output stream <var>out</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param out OutputStream to serialize the object <var>utenteDipartimento</var>
	 * @param utenteDipartimento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,UtenteDipartimento utenteDipartimento) throws SerializerException {
		this.objToXml(out, UtenteDipartimento.class, utenteDipartimento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
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
	 * Serialize to byte array the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param utenteDipartimento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(UtenteDipartimento utenteDipartimento) throws SerializerException {
		return this.objToXml(UtenteDipartimento.class, utenteDipartimento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
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
	 * Serialize to String the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param utenteDipartimento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(UtenteDipartimento utenteDipartimento) throws SerializerException {
		return this.objToXml(UtenteDipartimento.class, utenteDipartimento, false).toString();
	}
	/**
	 * Serialize to String the object <var>utenteDipartimento</var> of type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
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
	 Object: id-utente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param fileName Xml file to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdUtente idUtente) throws SerializerException {
		this.objToXml(fileName, IdUtente.class, idUtente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
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
	 * Serialize to file system in <var>file</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param file Xml file to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdUtente idUtente) throws SerializerException {
		this.objToXml(file, IdUtente.class, idUtente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
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
	 * Serialize to output stream <var>out</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param out OutputStream to serialize the object <var>idUtente</var>
	 * @param idUtente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdUtente idUtente) throws SerializerException {
		this.objToXml(out, IdUtente.class, idUtente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
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
	 * Serialize to byte array the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param idUtente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdUtente idUtente) throws SerializerException {
		return this.objToXml(IdUtente.class, idUtente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
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
	 * Serialize to String the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param idUtente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdUtente idUtente) throws SerializerException {
		return this.objToXml(IdUtente.class, idUtente, false).toString();
	}
	/**
	 * Serialize to String the object <var>idUtente</var> of type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
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
	 Object: PccPagamento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccPagamento</var>
	 * @param pccPagamento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccPagamento pccPagamento) throws SerializerException {
		this.objToXml(fileName, PccPagamento.class, pccPagamento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccPagamento</var>
	 * @param pccPagamento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccPagamento pccPagamento,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccPagamento.class, pccPagamento, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param file Xml file to serialize the object <var>pccPagamento</var>
	 * @param pccPagamento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccPagamento pccPagamento) throws SerializerException {
		this.objToXml(file, PccPagamento.class, pccPagamento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param file Xml file to serialize the object <var>pccPagamento</var>
	 * @param pccPagamento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccPagamento pccPagamento,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccPagamento.class, pccPagamento, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param out OutputStream to serialize the object <var>pccPagamento</var>
	 * @param pccPagamento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccPagamento pccPagamento) throws SerializerException {
		this.objToXml(out, PccPagamento.class, pccPagamento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param out OutputStream to serialize the object <var>pccPagamento</var>
	 * @param pccPagamento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccPagamento pccPagamento,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccPagamento.class, pccPagamento, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param pccPagamento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccPagamento pccPagamento) throws SerializerException {
		return this.objToXml(PccPagamento.class, pccPagamento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param pccPagamento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccPagamento pccPagamento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccPagamento.class, pccPagamento, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param pccPagamento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccPagamento pccPagamento) throws SerializerException {
		return this.objToXml(PccPagamento.class, pccPagamento, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccPagamento</var> of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param pccPagamento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccPagamento pccPagamento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccPagamento.class, pccPagamento, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-documento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param fileName Xml file to serialize the object <var>idDocumento</var>
	 * @param idDocumento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdDocumento idDocumento) throws SerializerException {
		this.objToXml(fileName, IdDocumento.class, idDocumento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param fileName Xml file to serialize the object <var>idDocumento</var>
	 * @param idDocumento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdDocumento idDocumento,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdDocumento.class, idDocumento, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param file Xml file to serialize the object <var>idDocumento</var>
	 * @param idDocumento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdDocumento idDocumento) throws SerializerException {
		this.objToXml(file, IdDocumento.class, idDocumento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param file Xml file to serialize the object <var>idDocumento</var>
	 * @param idDocumento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdDocumento idDocumento,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdDocumento.class, idDocumento, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param out OutputStream to serialize the object <var>idDocumento</var>
	 * @param idDocumento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdDocumento idDocumento) throws SerializerException {
		this.objToXml(out, IdDocumento.class, idDocumento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param out OutputStream to serialize the object <var>idDocumento</var>
	 * @param idDocumento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdDocumento idDocumento,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdDocumento.class, idDocumento, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param idDocumento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdDocumento idDocumento) throws SerializerException {
		return this.objToXml(IdDocumento.class, idDocumento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param idDocumento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdDocumento idDocumento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdDocumento.class, idDocumento, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param idDocumento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDocumento idDocumento) throws SerializerException {
		return this.objToXml(IdDocumento.class, idDocumento, false).toString();
	}
	/**
	 * Serialize to String the object <var>idDocumento</var> of type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param idDocumento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdDocumento idDocumento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdDocumento.class, idDocumento, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccUtenteOperazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccUtenteOperazione</var>
	 * @param pccUtenteOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccUtenteOperazione pccUtenteOperazione) throws SerializerException {
		this.objToXml(fileName, PccUtenteOperazione.class, pccUtenteOperazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccUtenteOperazione</var>
	 * @param pccUtenteOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccUtenteOperazione pccUtenteOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccUtenteOperazione.class, pccUtenteOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccUtenteOperazione</var>
	 * @param pccUtenteOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccUtenteOperazione pccUtenteOperazione) throws SerializerException {
		this.objToXml(file, PccUtenteOperazione.class, pccUtenteOperazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccUtenteOperazione</var>
	 * @param pccUtenteOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccUtenteOperazione pccUtenteOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccUtenteOperazione.class, pccUtenteOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccUtenteOperazione</var>
	 * @param pccUtenteOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccUtenteOperazione pccUtenteOperazione) throws SerializerException {
		this.objToXml(out, PccUtenteOperazione.class, pccUtenteOperazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccUtenteOperazione</var>
	 * @param pccUtenteOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccUtenteOperazione pccUtenteOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccUtenteOperazione.class, pccUtenteOperazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param pccUtenteOperazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccUtenteOperazione pccUtenteOperazione) throws SerializerException {
		return this.objToXml(PccUtenteOperazione.class, pccUtenteOperazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param pccUtenteOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccUtenteOperazione pccUtenteOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccUtenteOperazione.class, pccUtenteOperazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param pccUtenteOperazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccUtenteOperazione pccUtenteOperazione) throws SerializerException {
		return this.objToXml(PccUtenteOperazione.class, pccUtenteOperazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccUtenteOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param pccUtenteOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccUtenteOperazione pccUtenteOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccUtenteOperazione.class, pccUtenteOperazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: NotificaEsitoCommittente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param fileName Xml file to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		this.objToXml(fileName, NotificaEsitoCommittente.class, notificaEsitoCommittente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
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
	 * Serialize to file system in <var>file</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param file Xml file to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		this.objToXml(file, NotificaEsitoCommittente.class, notificaEsitoCommittente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
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
	 * Serialize to output stream <var>out</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param out OutputStream to serialize the object <var>notificaEsitoCommittente</var>
	 * @param notificaEsitoCommittente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		this.objToXml(out, NotificaEsitoCommittente.class, notificaEsitoCommittente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
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
	 * Serialize to byte array the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param notificaEsitoCommittente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		return this.objToXml(NotificaEsitoCommittente.class, notificaEsitoCommittente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
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
	 * Serialize to String the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param notificaEsitoCommittente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(NotificaEsitoCommittente notificaEsitoCommittente) throws SerializerException {
		return this.objToXml(NotificaEsitoCommittente.class, notificaEsitoCommittente, false).toString();
	}
	/**
	 * Serialize to String the object <var>notificaEsitoCommittente</var> of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
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
	 Object: id-contabilizzazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idContabilizzazione</var>
	 * @param idContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdContabilizzazione idContabilizzazione) throws SerializerException {
		this.objToXml(fileName, IdContabilizzazione.class, idContabilizzazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idContabilizzazione</var>
	 * @param idContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdContabilizzazione idContabilizzazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdContabilizzazione.class, idContabilizzazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param file Xml file to serialize the object <var>idContabilizzazione</var>
	 * @param idContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdContabilizzazione idContabilizzazione) throws SerializerException {
		this.objToXml(file, IdContabilizzazione.class, idContabilizzazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param file Xml file to serialize the object <var>idContabilizzazione</var>
	 * @param idContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdContabilizzazione idContabilizzazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdContabilizzazione.class, idContabilizzazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param out OutputStream to serialize the object <var>idContabilizzazione</var>
	 * @param idContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdContabilizzazione idContabilizzazione) throws SerializerException {
		this.objToXml(out, IdContabilizzazione.class, idContabilizzazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param out OutputStream to serialize the object <var>idContabilizzazione</var>
	 * @param idContabilizzazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdContabilizzazione idContabilizzazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdContabilizzazione.class, idContabilizzazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param idContabilizzazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdContabilizzazione idContabilizzazione) throws SerializerException {
		return this.objToXml(IdContabilizzazione.class, idContabilizzazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param idContabilizzazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdContabilizzazione idContabilizzazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdContabilizzazione.class, idContabilizzazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param idContabilizzazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdContabilizzazione idContabilizzazione) throws SerializerException {
		return this.objToXml(IdContabilizzazione.class, idContabilizzazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>idContabilizzazione</var> of type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param idContabilizzazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdContabilizzazione idContabilizzazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdContabilizzazione.class, idContabilizzazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccTracciaTrasmissione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccTracciaTrasmissione</var>
	 * @param pccTracciaTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccTracciaTrasmissione pccTracciaTrasmissione) throws SerializerException {
		this.objToXml(fileName, PccTracciaTrasmissione.class, pccTracciaTrasmissione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccTracciaTrasmissione</var>
	 * @param pccTracciaTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccTracciaTrasmissione pccTracciaTrasmissione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccTracciaTrasmissione.class, pccTracciaTrasmissione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param file Xml file to serialize the object <var>pccTracciaTrasmissione</var>
	 * @param pccTracciaTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccTracciaTrasmissione pccTracciaTrasmissione) throws SerializerException {
		this.objToXml(file, PccTracciaTrasmissione.class, pccTracciaTrasmissione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param file Xml file to serialize the object <var>pccTracciaTrasmissione</var>
	 * @param pccTracciaTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccTracciaTrasmissione pccTracciaTrasmissione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccTracciaTrasmissione.class, pccTracciaTrasmissione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccTracciaTrasmissione</var>
	 * @param pccTracciaTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccTracciaTrasmissione pccTracciaTrasmissione) throws SerializerException {
		this.objToXml(out, PccTracciaTrasmissione.class, pccTracciaTrasmissione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccTracciaTrasmissione</var>
	 * @param pccTracciaTrasmissione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccTracciaTrasmissione pccTracciaTrasmissione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccTracciaTrasmissione.class, pccTracciaTrasmissione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param pccTracciaTrasmissione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccTracciaTrasmissione pccTracciaTrasmissione) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissione.class, pccTracciaTrasmissione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param pccTracciaTrasmissione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccTracciaTrasmissione pccTracciaTrasmissione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissione.class, pccTracciaTrasmissione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param pccTracciaTrasmissione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccTracciaTrasmissione pccTracciaTrasmissione) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissione.class, pccTracciaTrasmissione, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccTracciaTrasmissione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param pccTracciaTrasmissione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccTracciaTrasmissione pccTracciaTrasmissione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccTracciaTrasmissione.class, pccTracciaTrasmissione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: PccOperazione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccOperazione</var>
	 * @param pccOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccOperazione pccOperazione) throws SerializerException {
		this.objToXml(fileName, PccOperazione.class, pccOperazione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param fileName Xml file to serialize the object <var>pccOperazione</var>
	 * @param pccOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,PccOperazione pccOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, PccOperazione.class, pccOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccOperazione</var>
	 * @param pccOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccOperazione pccOperazione) throws SerializerException {
		this.objToXml(file, PccOperazione.class, pccOperazione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param file Xml file to serialize the object <var>pccOperazione</var>
	 * @param pccOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,PccOperazione pccOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, PccOperazione.class, pccOperazione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccOperazione</var>
	 * @param pccOperazione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccOperazione pccOperazione) throws SerializerException {
		this.objToXml(out, PccOperazione.class, pccOperazione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param out OutputStream to serialize the object <var>pccOperazione</var>
	 * @param pccOperazione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,PccOperazione pccOperazione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, PccOperazione.class, pccOperazione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param pccOperazione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccOperazione pccOperazione) throws SerializerException {
		return this.objToXml(PccOperazione.class, pccOperazione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param pccOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(PccOperazione pccOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccOperazione.class, pccOperazione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param pccOperazione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccOperazione pccOperazione) throws SerializerException {
		return this.objToXml(PccOperazione.class, pccOperazione, false).toString();
	}
	/**
	 * Serialize to String the object <var>pccOperazione</var> of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param pccOperazione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(PccOperazione pccOperazione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(PccOperazione.class, pccOperazione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: Utente
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param fileName Xml file to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Utente utente) throws SerializerException {
		this.objToXml(fileName, Utente.class, utente, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
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
	 * Serialize to file system in <var>file</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param file Xml file to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Utente utente) throws SerializerException {
		this.objToXml(file, Utente.class, utente, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
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
	 * Serialize to output stream <var>out</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param out OutputStream to serialize the object <var>utente</var>
	 * @param utente Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Utente utente) throws SerializerException {
		this.objToXml(out, Utente.class, utente, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
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
	 * Serialize to byte array the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param utente Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Utente utente) throws SerializerException {
		return this.objToXml(Utente.class, utente, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
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
	 * Serialize to String the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param utente Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Utente utente) throws SerializerException {
		return this.objToXml(Utente.class, utente, false).toString();
	}
	/**
	 * Serialize to String the object <var>utente</var> of type {@link org.govmix.proxy.fatturapa.orm.Utente}
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
	 Object: Evento
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param fileName Xml file to serialize the object <var>evento</var>
	 * @param evento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Evento evento) throws SerializerException {
		this.objToXml(fileName, Evento.class, evento, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param fileName Xml file to serialize the object <var>evento</var>
	 * @param evento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Evento evento,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Evento.class, evento, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param file Xml file to serialize the object <var>evento</var>
	 * @param evento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Evento evento) throws SerializerException {
		this.objToXml(file, Evento.class, evento, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param file Xml file to serialize the object <var>evento</var>
	 * @param evento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Evento evento,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Evento.class, evento, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param out OutputStream to serialize the object <var>evento</var>
	 * @param evento Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Evento evento) throws SerializerException {
		this.objToXml(out, Evento.class, evento, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param out OutputStream to serialize the object <var>evento</var>
	 * @param evento Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Evento evento,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Evento.class, evento, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param evento Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Evento evento) throws SerializerException {
		return this.objToXml(Evento.class, evento, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param evento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Evento evento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Evento.class, evento, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param evento Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Evento evento) throws SerializerException {
		return this.objToXml(Evento.class, evento, false).toString();
	}
	/**
	 * Serialize to String the object <var>evento</var> of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param evento Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Evento evento,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Evento.class, evento, prettyPrint).toString();
	}
	
	
	

}
