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

import org.openspcoop2.generic_project.exception.DeserializerException;

import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.Proxy;
import org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.AllegatoFattura;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.IdRegistro;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.UtenteDipartimento;
import org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.IdDipartimento;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;

/**     
 * XML Deserializer of beans
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public abstract class AbstractDeserializer {



	protected abstract Object _xmlToObj(InputStream is, Class<?> c) throws Exception;
	
	private Object xmlToObj(InputStream is,Class<?> c) throws DeserializerException{
		try{
			return this._xmlToObj(is, c);
		}catch(Exception e){
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	private Object xmlToObj(String fileName,Class<?> c) throws DeserializerException{
		try{
			return this.xmlToObj(new File(fileName), c);
		}catch(Exception e){
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	private Object xmlToObj(File file,Class<?> c) throws DeserializerException{
		FileInputStream fin = null;
		try{
			fin = new FileInputStream(file);
			return this._xmlToObj(fin, c);
		}catch(Exception e){
			throw new DeserializerException(e.getMessage(),e);
		}finally{
			try{
				fin.close();
			}catch(Exception e){}
		}
	}
	private Object xmlToObj(byte[] file,Class<?> c) throws DeserializerException{
		ByteArrayInputStream fin = null;
		try{
			fin = new ByteArrayInputStream(file);
			return this._xmlToObj(fin, c);
		}catch(Exception e){
			throw new DeserializerException(e.getMessage(),e);
		}finally{
			try{
				fin.close();
			}catch(Exception e){}
		}
	}






	/*
	 =================================================================================
	 Object: LottoFatture
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(String fileName) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(fileName, LottoFatture.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(File file) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(file, LottoFatture.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(InputStream in) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(in, LottoFatture.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(byte[] in) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(in, LottoFatture.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFattureFromString(String in) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(in.getBytes(), LottoFatture.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Proxy
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Proxy readProxy(String fileName) throws DeserializerException {
		return (Proxy) this.xmlToObj(fileName, Proxy.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Proxy readProxy(File file) throws DeserializerException {
		return (Proxy) this.xmlToObj(file, Proxy.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Proxy readProxy(InputStream in) throws DeserializerException {
		return (Proxy) this.xmlToObj(in, Proxy.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Proxy readProxy(byte[] in) throws DeserializerException {
		return (Proxy) this.xmlToObj(in, Proxy.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Proxy}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Proxy readProxyFromString(String in) throws DeserializerException {
		return (Proxy) this.xmlToObj(in.getBytes(), Proxy.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: NotificaDecorrenzaTermini
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(String fileName) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(fileName, NotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(File file) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(file, NotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(InputStream in) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(in, NotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(byte[] in) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(in, NotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTerminiFromString(String in) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(in.getBytes(), NotificaDecorrenzaTermini.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: FatturaElettronica
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(String fileName) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(fileName, FatturaElettronica.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(File file) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(file, FatturaElettronica.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(InputStream in) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(in, FatturaElettronica.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(byte[] in) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(in, FatturaElettronica.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronicaFromString(String in) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(in.getBytes(), FatturaElettronica.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: AllegatoFattura
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(String fileName) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(fileName, AllegatoFattura.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(File file) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(file, AllegatoFattura.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(InputStream in) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(in, AllegatoFattura.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(byte[] in) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(in, AllegatoFattura.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFatturaFromString(String in) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(in.getBytes(), AllegatoFattura.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Ente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(String fileName) throws DeserializerException {
		return (Ente) this.xmlToObj(fileName, Ente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(File file) throws DeserializerException {
		return (Ente) this.xmlToObj(file, Ente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(InputStream in) throws DeserializerException {
		return (Ente) this.xmlToObj(in, Ente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(byte[] in) throws DeserializerException {
		return (Ente) this.xmlToObj(in, Ente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnteFromString(String in) throws DeserializerException {
		return (Ente) this.xmlToObj(in.getBytes(), Ente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Registro
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(String fileName) throws DeserializerException {
		return (Registro) this.xmlToObj(fileName, Registro.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(File file) throws DeserializerException {
		return (Registro) this.xmlToObj(file, Registro.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(InputStream in) throws DeserializerException {
		return (Registro) this.xmlToObj(in, Registro.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(byte[] in) throws DeserializerException {
		return (Registro) this.xmlToObj(in, Registro.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistroFromString(String in) throws DeserializerException {
		return (Registro) this.xmlToObj(in.getBytes(), Registro.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: RegistroProperty
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(String fileName) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(fileName, RegistroProperty.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(File file) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(file, RegistroProperty.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(InputStream in) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(in, RegistroProperty.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(byte[] in) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(in, RegistroProperty.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroPropertyFromString(String in) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(in.getBytes(), RegistroProperty.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Utente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(String fileName) throws DeserializerException {
		return (Utente) this.xmlToObj(fileName, Utente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(File file) throws DeserializerException {
		return (Utente) this.xmlToObj(file, Utente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(InputStream in) throws DeserializerException {
		return (Utente) this.xmlToObj(in, Utente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(byte[] in) throws DeserializerException {
		return (Utente) this.xmlToObj(in, Utente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtenteFromString(String in) throws DeserializerException {
		return (Utente) this.xmlToObj(in.getBytes(), Utente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: NotificaEsitoCommittente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(String fileName) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(fileName, NotificaEsitoCommittente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(File file) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(file, NotificaEsitoCommittente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(InputStream in) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(in, NotificaEsitoCommittente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(byte[] in) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(in, NotificaEsitoCommittente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittenteFromString(String in) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(in.getBytes(), NotificaEsitoCommittente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Dipartimento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(String fileName) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(fileName, Dipartimento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(File file) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(file, Dipartimento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(InputStream in) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(in, Dipartimento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(byte[] in) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(in, Dipartimento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimentoFromString(String in) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(in.getBytes(), Dipartimento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: DipartimentoProperty
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(String fileName) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(fileName, DipartimentoProperty.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(File file) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(file, DipartimentoProperty.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(InputStream in) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(in, DipartimentoProperty.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(byte[] in) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(in, DipartimentoProperty.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoPropertyFromString(String in) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(in.getBytes(), DipartimentoProperty.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-registro
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(String fileName) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(fileName, IdRegistro.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(File file) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(file, IdRegistro.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(InputStream in) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(in, IdRegistro.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(byte[] in) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(in, IdRegistro.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistroFromString(String in) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(in.getBytes(), IdRegistro.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-ente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(String fileName) throws DeserializerException {
		return (IdEnte) this.xmlToObj(fileName, IdEnte.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(File file) throws DeserializerException {
		return (IdEnte) this.xmlToObj(file, IdEnte.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(InputStream in) throws DeserializerException {
		return (IdEnte) this.xmlToObj(in, IdEnte.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(byte[] in) throws DeserializerException {
		return (IdEnte) this.xmlToObj(in, IdEnte.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnteFromString(String in) throws DeserializerException {
		return (IdEnte) this.xmlToObj(in.getBytes(), IdEnte.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: DipartimentoPropertyValue
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(String fileName) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(fileName, DipartimentoPropertyValue.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(File file) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(file, DipartimentoPropertyValue.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(InputStream in) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(in, DipartimentoPropertyValue.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(byte[] in) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(in, DipartimentoPropertyValue.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValueFromString(String in) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(in.getBytes(), DipartimentoPropertyValue.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: UtenteDipartimento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(String fileName) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(fileName, UtenteDipartimento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(File file) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(file, UtenteDipartimento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(InputStream in) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(in, UtenteDipartimento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(byte[] in) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(in, UtenteDipartimento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimentoFromString(String in) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(in.getBytes(), UtenteDipartimento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-notifica-decorrenza-termini
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(String fileName) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(fileName, IdNotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(File file) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(file, IdNotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(InputStream in) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(in, IdNotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(byte[] in) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(in, IdNotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTerminiFromString(String in) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(in.getBytes(), IdNotificaDecorrenzaTermini.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-property
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProperty readIdProperty(String fileName) throws DeserializerException {
		return (IdProperty) this.xmlToObj(fileName, IdProperty.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProperty readIdProperty(File file) throws DeserializerException {
		return (IdProperty) this.xmlToObj(file, IdProperty.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProperty readIdProperty(InputStream in) throws DeserializerException {
		return (IdProperty) this.xmlToObj(in, IdProperty.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProperty readIdProperty(byte[] in) throws DeserializerException {
		return (IdProperty) this.xmlToObj(in, IdProperty.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProperty readIdPropertyFromString(String in) throws DeserializerException {
		return (IdProperty) this.xmlToObj(in.getBytes(), IdProperty.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-fattura
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(String fileName) throws DeserializerException {
		return (IdFattura) this.xmlToObj(fileName, IdFattura.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(File file) throws DeserializerException {
		return (IdFattura) this.xmlToObj(file, IdFattura.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(InputStream in) throws DeserializerException {
		return (IdFattura) this.xmlToObj(in, IdFattura.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(byte[] in) throws DeserializerException {
		return (IdFattura) this.xmlToObj(in, IdFattura.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFatturaFromString(String in) throws DeserializerException {
		return (IdFattura) this.xmlToObj(in.getBytes(), IdFattura.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-lotto
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(String fileName) throws DeserializerException {
		return (IdLotto) this.xmlToObj(fileName, IdLotto.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(File file) throws DeserializerException {
		return (IdLotto) this.xmlToObj(file, IdLotto.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(InputStream in) throws DeserializerException {
		return (IdLotto) this.xmlToObj(in, IdLotto.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(byte[] in) throws DeserializerException {
		return (IdLotto) this.xmlToObj(in, IdLotto.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLottoFromString(String in) throws DeserializerException {
		return (IdLotto) this.xmlToObj(in.getBytes(), IdLotto.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-utente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(String fileName) throws DeserializerException {
		return (IdUtente) this.xmlToObj(fileName, IdUtente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(File file) throws DeserializerException {
		return (IdUtente) this.xmlToObj(file, IdUtente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(InputStream in) throws DeserializerException {
		return (IdUtente) this.xmlToObj(in, IdUtente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(byte[] in) throws DeserializerException {
		return (IdUtente) this.xmlToObj(in, IdUtente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtenteFromString(String in) throws DeserializerException {
		return (IdUtente) this.xmlToObj(in.getBytes(), IdUtente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: RegistroPropertyValue
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(String fileName) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(fileName, RegistroPropertyValue.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(File file) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(file, RegistroPropertyValue.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(InputStream in) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(in, RegistroPropertyValue.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(byte[] in) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(in, RegistroPropertyValue.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValueFromString(String in) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(in.getBytes(), RegistroPropertyValue.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-dipartimento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(String fileName) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(fileName, IdDipartimento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(File file) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(file, IdDipartimento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(InputStream in) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(in, IdDipartimento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(byte[] in) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(in, IdDipartimento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimentoFromString(String in) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(in.getBytes(), IdDipartimento.class);
	}	
	
	
	

}
