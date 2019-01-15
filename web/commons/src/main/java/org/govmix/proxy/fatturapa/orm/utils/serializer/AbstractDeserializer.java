/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.openspcoop2.generic_project.exception.DeserializerException;

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
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.RegistroProperty;
import org.govmix.proxy.fatturapa.orm.IdProtocollo;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.IdRispedizione;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.IdOperazione;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.IdTracciaSdi;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
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
import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.orm.IdDocumento;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.IdContabilizzazione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.Evento;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;

/**     
 * XML Deserializer of beans
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
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
	 Object: PccScadenza
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccScadenza readPccScadenza(String fileName) throws DeserializerException {
		return (PccScadenza) this.xmlToObj(fileName, PccScadenza.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccScadenza readPccScadenza(File file) throws DeserializerException {
		return (PccScadenza) this.xmlToObj(file, PccScadenza.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccScadenza readPccScadenza(InputStream in) throws DeserializerException {
		return (PccScadenza) this.xmlToObj(in, PccScadenza.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccScadenza readPccScadenza(byte[] in) throws DeserializerException {
		return (PccScadenza) this.xmlToObj(in, PccScadenza.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccScadenza readPccScadenzaFromString(String in) throws DeserializerException {
		return (PccScadenza) this.xmlToObj(in.getBytes(), PccScadenza.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-fattura
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(String fileName) throws DeserializerException {
		return (IdFattura) this.xmlToObj(fileName, IdFattura.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(File file) throws DeserializerException {
		return (IdFattura) this.xmlToObj(file, IdFattura.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(InputStream in) throws DeserializerException {
		return (IdFattura) this.xmlToObj(in, IdFattura.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFattura(byte[] in) throws DeserializerException {
		return (IdFattura) this.xmlToObj(in, IdFattura.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdFattura readIdFatturaFromString(String in) throws DeserializerException {
		return (IdFattura) this.xmlToObj(in.getBytes(), IdFattura.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Protocollo
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Protocollo readProtocollo(String fileName) throws DeserializerException {
		return (Protocollo) this.xmlToObj(fileName, Protocollo.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Protocollo readProtocollo(File file) throws DeserializerException {
		return (Protocollo) this.xmlToObj(file, Protocollo.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Protocollo readProtocollo(InputStream in) throws DeserializerException {
		return (Protocollo) this.xmlToObj(in, Protocollo.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Protocollo readProtocollo(byte[] in) throws DeserializerException {
		return (Protocollo) this.xmlToObj(in, Protocollo.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Protocollo readProtocolloFromString(String in) throws DeserializerException {
		return (Protocollo) this.xmlToObj(in.getBytes(), Protocollo.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-sip
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdSip readIdSip(String fileName) throws DeserializerException {
		return (IdSip) this.xmlToObj(fileName, IdSip.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdSip readIdSip(File file) throws DeserializerException {
		return (IdSip) this.xmlToObj(file, IdSip.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdSip readIdSip(InputStream in) throws DeserializerException {
		return (IdSip) this.xmlToObj(in, IdSip.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdSip readIdSip(byte[] in) throws DeserializerException {
		return (IdSip) this.xmlToObj(in, IdSip.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdSip}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdSip readIdSipFromString(String in) throws DeserializerException {
		return (IdSip) this.xmlToObj(in.getBytes(), IdSip.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-scadenza
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdScadenza readIdScadenza(String fileName) throws DeserializerException {
		return (IdScadenza) this.xmlToObj(fileName, IdScadenza.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdScadenza readIdScadenza(File file) throws DeserializerException {
		return (IdScadenza) this.xmlToObj(file, IdScadenza.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdScadenza readIdScadenza(InputStream in) throws DeserializerException {
		return (IdScadenza) this.xmlToObj(in, IdScadenza.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdScadenza readIdScadenza(byte[] in) throws DeserializerException {
		return (IdScadenza) this.xmlToObj(in, IdScadenza.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdScadenza}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdScadenza readIdScadenzaFromString(String in) throws DeserializerException {
		return (IdScadenza) this.xmlToObj(in.getBytes(), IdScadenza.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-lotto
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(String fileName) throws DeserializerException {
		return (IdLotto) this.xmlToObj(fileName, IdLotto.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(File file) throws DeserializerException {
		return (IdLotto) this.xmlToObj(file, IdLotto.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(InputStream in) throws DeserializerException {
		return (IdLotto) this.xmlToObj(in, IdLotto.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLotto(byte[] in) throws DeserializerException {
		return (IdLotto) this.xmlToObj(in, IdLotto.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdLotto}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdLotto readIdLottoFromString(String in) throws DeserializerException {
		return (IdLotto) this.xmlToObj(in.getBytes(), IdLotto.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: FatturaElettronica
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(String fileName) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(fileName, FatturaElettronica.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(File file) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(file, FatturaElettronica.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(InputStream in) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(in, FatturaElettronica.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronica(byte[] in) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(in, FatturaElettronica.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public FatturaElettronica readFatturaElettronicaFromString(String in) throws DeserializerException {
		return (FatturaElettronica) this.xmlToObj(in.getBytes(), FatturaElettronica.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-notifica-decorrenza-termini
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(String fileName) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(fileName, IdNotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(File file) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(file, IdNotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(InputStream in) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(in, IdNotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTermini(byte[] in) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(in, IdNotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdNotificaDecorrenzaTermini readIdNotificaDecorrenzaTerminiFromString(String in) throws DeserializerException {
		return (IdNotificaDecorrenzaTermini) this.xmlToObj(in.getBytes(), IdNotificaDecorrenzaTermini.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-trasmissione-esito
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissioneEsito readIdTrasmissioneEsito(String fileName) throws DeserializerException {
		return (IdTrasmissioneEsito) this.xmlToObj(fileName, IdTrasmissioneEsito.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissioneEsito readIdTrasmissioneEsito(File file) throws DeserializerException {
		return (IdTrasmissioneEsito) this.xmlToObj(file, IdTrasmissioneEsito.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissioneEsito readIdTrasmissioneEsito(InputStream in) throws DeserializerException {
		return (IdTrasmissioneEsito) this.xmlToObj(in, IdTrasmissioneEsito.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissioneEsito readIdTrasmissioneEsito(byte[] in) throws DeserializerException {
		return (IdTrasmissioneEsito) this.xmlToObj(in, IdTrasmissioneEsito.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissioneEsito readIdTrasmissioneEsitoFromString(String in) throws DeserializerException {
		return (IdTrasmissioneEsito) this.xmlToObj(in.getBytes(), IdTrasmissioneEsito.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Dipartimento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(String fileName) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(fileName, Dipartimento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(File file) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(file, Dipartimento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(InputStream in) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(in, Dipartimento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimento(byte[] in) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(in, Dipartimento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Dipartimento readDipartimentoFromString(String in) throws DeserializerException {
		return (Dipartimento) this.xmlToObj(in.getBytes(), Dipartimento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: LottoFatture
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(String fileName) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(fileName, LottoFatture.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(File file) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(file, LottoFatture.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(InputStream in) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(in, LottoFatture.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFatture(byte[] in) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(in, LottoFatture.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public LottoFatture readLottoFattureFromString(String in) throws DeserializerException {
		return (LottoFatture) this.xmlToObj(in.getBytes(), LottoFatture.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-trasmissione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissione readIdTrasmissione(String fileName) throws DeserializerException {
		return (IdTrasmissione) this.xmlToObj(fileName, IdTrasmissione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissione readIdTrasmissione(File file) throws DeserializerException {
		return (IdTrasmissione) this.xmlToObj(file, IdTrasmissione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissione readIdTrasmissione(InputStream in) throws DeserializerException {
		return (IdTrasmissione) this.xmlToObj(in, IdTrasmissione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissione readIdTrasmissione(byte[] in) throws DeserializerException {
		return (IdTrasmissione) this.xmlToObj(in, IdTrasmissione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTrasmissione readIdTrasmissioneFromString(String in) throws DeserializerException {
		return (IdTrasmissione) this.xmlToObj(in.getBytes(), IdTrasmissione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccTracciaTrasmissioneEsito
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissioneEsito readPccTracciaTrasmissioneEsito(String fileName) throws DeserializerException {
		return (PccTracciaTrasmissioneEsito) this.xmlToObj(fileName, PccTracciaTrasmissioneEsito.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissioneEsito readPccTracciaTrasmissioneEsito(File file) throws DeserializerException {
		return (PccTracciaTrasmissioneEsito) this.xmlToObj(file, PccTracciaTrasmissioneEsito.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissioneEsito readPccTracciaTrasmissioneEsito(InputStream in) throws DeserializerException {
		return (PccTracciaTrasmissioneEsito) this.xmlToObj(in, PccTracciaTrasmissioneEsito.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissioneEsito readPccTracciaTrasmissioneEsito(byte[] in) throws DeserializerException {
		return (PccTracciaTrasmissioneEsito) this.xmlToObj(in, PccTracciaTrasmissioneEsito.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissioneEsito readPccTracciaTrasmissioneEsitoFromString(String in) throws DeserializerException {
		return (PccTracciaTrasmissioneEsito) this.xmlToObj(in.getBytes(), PccTracciaTrasmissioneEsito.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccErroreElaborazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccErroreElaborazione readPccErroreElaborazione(String fileName) throws DeserializerException {
		return (PccErroreElaborazione) this.xmlToObj(fileName, PccErroreElaborazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccErroreElaborazione readPccErroreElaborazione(File file) throws DeserializerException {
		return (PccErroreElaborazione) this.xmlToObj(file, PccErroreElaborazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccErroreElaborazione readPccErroreElaborazione(InputStream in) throws DeserializerException {
		return (PccErroreElaborazione) this.xmlToObj(in, PccErroreElaborazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccErroreElaborazione readPccErroreElaborazione(byte[] in) throws DeserializerException {
		return (PccErroreElaborazione) this.xmlToObj(in, PccErroreElaborazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccErroreElaborazione readPccErroreElaborazioneFromString(String in) throws DeserializerException {
		return (PccErroreElaborazione) this.xmlToObj(in.getBytes(), PccErroreElaborazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: DipartimentoProperty
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(String fileName) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(fileName, DipartimentoProperty.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(File file) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(file, DipartimentoProperty.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(InputStream in) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(in, DipartimentoProperty.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoProperty(byte[] in) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(in, DipartimentoProperty.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoProperty readDipartimentoPropertyFromString(String in) throws DeserializerException {
		return (DipartimentoProperty) this.xmlToObj(in.getBytes(), DipartimentoProperty.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-ente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(String fileName) throws DeserializerException {
		return (IdEnte) this.xmlToObj(fileName, IdEnte.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(File file) throws DeserializerException {
		return (IdEnte) this.xmlToObj(file, IdEnte.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(InputStream in) throws DeserializerException {
		return (IdEnte) this.xmlToObj(in, IdEnte.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnte(byte[] in) throws DeserializerException {
		return (IdEnte) this.xmlToObj(in, IdEnte.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdEnte}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdEnte readIdEnteFromString(String in) throws DeserializerException {
		return (IdEnte) this.xmlToObj(in.getBytes(), IdEnte.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: RegistroProperty
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(String fileName) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(fileName, RegistroProperty.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(File file) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(file, RegistroProperty.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(InputStream in) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(in, RegistroProperty.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroProperty(byte[] in) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(in, RegistroProperty.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroProperty readRegistroPropertyFromString(String in) throws DeserializerException {
		return (RegistroProperty) this.xmlToObj(in.getBytes(), RegistroProperty.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-protocollo
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProtocollo readIdProtocollo(String fileName) throws DeserializerException {
		return (IdProtocollo) this.xmlToObj(fileName, IdProtocollo.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProtocollo readIdProtocollo(File file) throws DeserializerException {
		return (IdProtocollo) this.xmlToObj(file, IdProtocollo.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProtocollo readIdProtocollo(InputStream in) throws DeserializerException {
		return (IdProtocollo) this.xmlToObj(in, IdProtocollo.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProtocollo readIdProtocollo(byte[] in) throws DeserializerException {
		return (IdProtocollo) this.xmlToObj(in, IdProtocollo.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdProtocollo}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdProtocollo readIdProtocolloFromString(String in) throws DeserializerException {
		return (IdProtocollo) this.xmlToObj(in.getBytes(), IdProtocollo.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: AllegatoFattura
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(String fileName) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(fileName, AllegatoFattura.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(File file) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(file, AllegatoFattura.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(InputStream in) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(in, AllegatoFattura.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFattura(byte[] in) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(in, AllegatoFattura.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public AllegatoFattura readAllegatoFatturaFromString(String in) throws DeserializerException {
		return (AllegatoFattura) this.xmlToObj(in.getBytes(), AllegatoFattura.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-rispedizione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRispedizione readIdRispedizione(String fileName) throws DeserializerException {
		return (IdRispedizione) this.xmlToObj(fileName, IdRispedizione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRispedizione readIdRispedizione(File file) throws DeserializerException {
		return (IdRispedizione) this.xmlToObj(file, IdRispedizione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRispedizione readIdRispedizione(InputStream in) throws DeserializerException {
		return (IdRispedizione) this.xmlToObj(in, IdRispedizione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRispedizione readIdRispedizione(byte[] in) throws DeserializerException {
		return (IdRispedizione) this.xmlToObj(in, IdRispedizione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRispedizione readIdRispedizioneFromString(String in) throws DeserializerException {
		return (IdRispedizione) this.xmlToObj(in.getBytes(), IdRispedizione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-registro
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(String fileName) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(fileName, IdRegistro.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(File file) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(file, IdRegistro.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(InputStream in) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(in, IdRegistro.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistro(byte[] in) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(in, IdRegistro.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistro readIdRegistroFromString(String in) throws DeserializerException {
		return (IdRegistro) this.xmlToObj(in.getBytes(), IdRegistro.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-dipartimento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(String fileName) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(fileName, IdDipartimento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(File file) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(file, IdDipartimento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(InputStream in) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(in, IdDipartimento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimento(byte[] in) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(in, IdDipartimento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimento readIdDipartimentoFromString(String in) throws DeserializerException {
		return (IdDipartimento) this.xmlToObj(in.getBytes(), IdDipartimento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccDipartimentoOperazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccDipartimentoOperazione readPccDipartimentoOperazione(String fileName) throws DeserializerException {
		return (PccDipartimentoOperazione) this.xmlToObj(fileName, PccDipartimentoOperazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccDipartimentoOperazione readPccDipartimentoOperazione(File file) throws DeserializerException {
		return (PccDipartimentoOperazione) this.xmlToObj(file, PccDipartimentoOperazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccDipartimentoOperazione readPccDipartimentoOperazione(InputStream in) throws DeserializerException {
		return (PccDipartimentoOperazione) this.xmlToObj(in, PccDipartimentoOperazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccDipartimentoOperazione readPccDipartimentoOperazione(byte[] in) throws DeserializerException {
		return (PccDipartimentoOperazione) this.xmlToObj(in, PccDipartimentoOperazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccDipartimentoOperazione readPccDipartimentoOperazioneFromString(String in) throws DeserializerException {
		return (PccDipartimentoOperazione) this.xmlToObj(in.getBytes(), PccDipartimentoOperazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-operazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdOperazione readIdOperazione(String fileName) throws DeserializerException {
		return (IdOperazione) this.xmlToObj(fileName, IdOperazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdOperazione readIdOperazione(File file) throws DeserializerException {
		return (IdOperazione) this.xmlToObj(file, IdOperazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdOperazione readIdOperazione(InputStream in) throws DeserializerException {
		return (IdOperazione) this.xmlToObj(in, IdOperazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdOperazione readIdOperazione(byte[] in) throws DeserializerException {
		return (IdOperazione) this.xmlToObj(in, IdOperazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdOperazione readIdOperazioneFromString(String in) throws DeserializerException {
		return (IdOperazione) this.xmlToObj(in.getBytes(), IdOperazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: NotificaDecorrenzaTermini
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(String fileName) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(fileName, NotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(File file) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(file, NotificaDecorrenzaTermini.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(InputStream in) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(in, NotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTermini(byte[] in) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(in, NotificaDecorrenzaTermini.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaDecorrenzaTermini readNotificaDecorrenzaTerminiFromString(String in) throws DeserializerException {
		return (NotificaDecorrenzaTermini) this.xmlToObj(in.getBytes(), NotificaDecorrenzaTermini.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-traccia-sdi
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTracciaSdi readIdTracciaSdi(String fileName) throws DeserializerException {
		return (IdTracciaSdi) this.xmlToObj(fileName, IdTracciaSdi.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTracciaSdi readIdTracciaSdi(File file) throws DeserializerException {
		return (IdTracciaSdi) this.xmlToObj(file, IdTracciaSdi.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTracciaSdi readIdTracciaSdi(InputStream in) throws DeserializerException {
		return (IdTracciaSdi) this.xmlToObj(in, IdTracciaSdi.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTracciaSdi readIdTracciaSdi(byte[] in) throws DeserializerException {
		return (IdTracciaSdi) this.xmlToObj(in, IdTracciaSdi.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTracciaSdi}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTracciaSdi readIdTracciaSdiFromString(String in) throws DeserializerException {
		return (IdTracciaSdi) this.xmlToObj(in.getBytes(), IdTracciaSdi.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-traccia
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTraccia readIdTraccia(String fileName) throws DeserializerException {
		return (IdTraccia) this.xmlToObj(fileName, IdTraccia.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTraccia readIdTraccia(File file) throws DeserializerException {
		return (IdTraccia) this.xmlToObj(file, IdTraccia.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTraccia readIdTraccia(InputStream in) throws DeserializerException {
		return (IdTraccia) this.xmlToObj(in, IdTraccia.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTraccia readIdTraccia(byte[] in) throws DeserializerException {
		return (IdTraccia) this.xmlToObj(in, IdTraccia.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdTraccia readIdTracciaFromString(String in) throws DeserializerException {
		return (IdTraccia) this.xmlToObj(in.getBytes(), IdTraccia.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: DipartimentoPropertyValue
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(String fileName) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(fileName, DipartimentoPropertyValue.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(File file) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(file, DipartimentoPropertyValue.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(InputStream in) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(in, DipartimentoPropertyValue.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValue(byte[] in) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(in, DipartimentoPropertyValue.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public DipartimentoPropertyValue readDipartimentoPropertyValueFromString(String in) throws DeserializerException {
		return (DipartimentoPropertyValue) this.xmlToObj(in.getBytes(), DipartimentoPropertyValue.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Metadato
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Metadato readMetadato(String fileName) throws DeserializerException {
		return (Metadato) this.xmlToObj(fileName, Metadato.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Metadato readMetadato(File file) throws DeserializerException {
		return (Metadato) this.xmlToObj(file, Metadato.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Metadato readMetadato(InputStream in) throws DeserializerException {
		return (Metadato) this.xmlToObj(in, Metadato.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Metadato readMetadato(byte[] in) throws DeserializerException {
		return (Metadato) this.xmlToObj(in, Metadato.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Metadato}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Metadato readMetadatoFromString(String in) throws DeserializerException {
		return (Metadato) this.xmlToObj(in.getBytes(), Metadato.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccRispedizione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccRispedizione readPccRispedizione(String fileName) throws DeserializerException {
		return (PccRispedizione) this.xmlToObj(fileName, PccRispedizione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccRispedizione readPccRispedizione(File file) throws DeserializerException {
		return (PccRispedizione) this.xmlToObj(file, PccRispedizione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccRispedizione readPccRispedizione(InputStream in) throws DeserializerException {
		return (PccRispedizione) this.xmlToObj(in, PccRispedizione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccRispedizione readPccRispedizione(byte[] in) throws DeserializerException {
		return (PccRispedizione) this.xmlToObj(in, PccRispedizione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccRispedizione readPccRispedizioneFromString(String in) throws DeserializerException {
		return (PccRispedizione) this.xmlToObj(in.getBytes(), PccRispedizione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: TracciaSDI
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public TracciaSDI readTracciaSDI(String fileName) throws DeserializerException {
		return (TracciaSDI) this.xmlToObj(fileName, TracciaSDI.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public TracciaSDI readTracciaSDI(File file) throws DeserializerException {
		return (TracciaSDI) this.xmlToObj(file, TracciaSDI.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public TracciaSDI readTracciaSDI(InputStream in) throws DeserializerException {
		return (TracciaSDI) this.xmlToObj(in, TracciaSDI.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public TracciaSDI readTracciaSDI(byte[] in) throws DeserializerException {
		return (TracciaSDI) this.xmlToObj(in, TracciaSDI.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public TracciaSDI readTracciaSDIFromString(String in) throws DeserializerException {
		return (TracciaSDI) this.xmlToObj(in.getBytes(), TracciaSDI.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Registro
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(String fileName) throws DeserializerException {
		return (Registro) this.xmlToObj(fileName, Registro.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(File file) throws DeserializerException {
		return (Registro) this.xmlToObj(file, Registro.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(InputStream in) throws DeserializerException {
		return (Registro) this.xmlToObj(in, Registro.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistro(byte[] in) throws DeserializerException {
		return (Registro) this.xmlToObj(in, Registro.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Registro readRegistroFromString(String in) throws DeserializerException {
		return (Registro) this.xmlToObj(in.getBytes(), Registro.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: RegistroPropertyValue
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(String fileName) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(fileName, RegistroPropertyValue.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(File file) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(file, RegistroPropertyValue.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(InputStream in) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(in, RegistroPropertyValue.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValue(byte[] in) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(in, RegistroPropertyValue.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.RegistroPropertyValue}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public RegistroPropertyValue readRegistroPropertyValueFromString(String in) throws DeserializerException {
		return (RegistroPropertyValue) this.xmlToObj(in.getBytes(), RegistroPropertyValue.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-pagamento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdPagamento readIdPagamento(String fileName) throws DeserializerException {
		return (IdPagamento) this.xmlToObj(fileName, IdPagamento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdPagamento readIdPagamento(File file) throws DeserializerException {
		return (IdPagamento) this.xmlToObj(file, IdPagamento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdPagamento readIdPagamento(InputStream in) throws DeserializerException {
		return (IdPagamento) this.xmlToObj(in, IdPagamento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdPagamento readIdPagamento(byte[] in) throws DeserializerException {
		return (IdPagamento) this.xmlToObj(in, IdPagamento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdPagamento readIdPagamentoFromString(String in) throws DeserializerException {
		return (IdPagamento) this.xmlToObj(in.getBytes(), IdPagamento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-comunicazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdComunicazione readIdComunicazione(String fileName) throws DeserializerException {
		return (IdComunicazione) this.xmlToObj(fileName, IdComunicazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdComunicazione readIdComunicazione(File file) throws DeserializerException {
		return (IdComunicazione) this.xmlToObj(file, IdComunicazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdComunicazione readIdComunicazione(InputStream in) throws DeserializerException {
		return (IdComunicazione) this.xmlToObj(in, IdComunicazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdComunicazione readIdComunicazione(byte[] in) throws DeserializerException {
		return (IdComunicazione) this.xmlToObj(in, IdComunicazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdComunicazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdComunicazione readIdComunicazioneFromString(String in) throws DeserializerException {
		return (IdComunicazione) this.xmlToObj(in.getBytes(), IdComunicazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccContabilizzazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccContabilizzazione readPccContabilizzazione(String fileName) throws DeserializerException {
		return (PccContabilizzazione) this.xmlToObj(fileName, PccContabilizzazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccContabilizzazione readPccContabilizzazione(File file) throws DeserializerException {
		return (PccContabilizzazione) this.xmlToObj(file, PccContabilizzazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccContabilizzazione readPccContabilizzazione(InputStream in) throws DeserializerException {
		return (PccContabilizzazione) this.xmlToObj(in, PccContabilizzazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccContabilizzazione readPccContabilizzazione(byte[] in) throws DeserializerException {
		return (PccContabilizzazione) this.xmlToObj(in, PccContabilizzazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccContabilizzazione readPccContabilizzazioneFromString(String in) throws DeserializerException {
		return (PccContabilizzazione) this.xmlToObj(in.getBytes(), PccContabilizzazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccNotifica
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccNotifica readPccNotifica(String fileName) throws DeserializerException {
		return (PccNotifica) this.xmlToObj(fileName, PccNotifica.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccNotifica readPccNotifica(File file) throws DeserializerException {
		return (PccNotifica) this.xmlToObj(file, PccNotifica.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccNotifica readPccNotifica(InputStream in) throws DeserializerException {
		return (PccNotifica) this.xmlToObj(in, PccNotifica.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccNotifica readPccNotifica(byte[] in) throws DeserializerException {
		return (PccNotifica) this.xmlToObj(in, PccNotifica.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccNotifica readPccNotificaFromString(String in) throws DeserializerException {
		return (PccNotifica) this.xmlToObj(in.getBytes(), PccNotifica.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccTraccia
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTraccia readPccTraccia(String fileName) throws DeserializerException {
		return (PccTraccia) this.xmlToObj(fileName, PccTraccia.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTraccia readPccTraccia(File file) throws DeserializerException {
		return (PccTraccia) this.xmlToObj(file, PccTraccia.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTraccia readPccTraccia(InputStream in) throws DeserializerException {
		return (PccTraccia) this.xmlToObj(in, PccTraccia.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTraccia readPccTraccia(byte[] in) throws DeserializerException {
		return (PccTraccia) this.xmlToObj(in, PccTraccia.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTraccia readPccTracciaFromString(String in) throws DeserializerException {
		return (PccTraccia) this.xmlToObj(in.getBytes(), PccTraccia.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-registro-property
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistroProperty readIdRegistroProperty(String fileName) throws DeserializerException {
		return (IdRegistroProperty) this.xmlToObj(fileName, IdRegistroProperty.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistroProperty readIdRegistroProperty(File file) throws DeserializerException {
		return (IdRegistroProperty) this.xmlToObj(file, IdRegistroProperty.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistroProperty readIdRegistroProperty(InputStream in) throws DeserializerException {
		return (IdRegistroProperty) this.xmlToObj(in, IdRegistroProperty.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistroProperty readIdRegistroProperty(byte[] in) throws DeserializerException {
		return (IdRegistroProperty) this.xmlToObj(in, IdRegistroProperty.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdRegistroProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdRegistroProperty readIdRegistroPropertyFromString(String in) throws DeserializerException {
		return (IdRegistroProperty) this.xmlToObj(in.getBytes(), IdRegistroProperty.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-dipartimento-property
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimentoProperty readIdDipartimentoProperty(String fileName) throws DeserializerException {
		return (IdDipartimentoProperty) this.xmlToObj(fileName, IdDipartimentoProperty.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimentoProperty readIdDipartimentoProperty(File file) throws DeserializerException {
		return (IdDipartimentoProperty) this.xmlToObj(file, IdDipartimentoProperty.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimentoProperty readIdDipartimentoProperty(InputStream in) throws DeserializerException {
		return (IdDipartimentoProperty) this.xmlToObj(in, IdDipartimentoProperty.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimentoProperty readIdDipartimentoProperty(byte[] in) throws DeserializerException {
		return (IdDipartimentoProperty) this.xmlToObj(in, IdDipartimentoProperty.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDipartimentoProperty readIdDipartimentoPropertyFromString(String in) throws DeserializerException {
		return (IdDipartimentoProperty) this.xmlToObj(in.getBytes(), IdDipartimentoProperty.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Ente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(String fileName) throws DeserializerException {
		return (Ente) this.xmlToObj(fileName, Ente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(File file) throws DeserializerException {
		return (Ente) this.xmlToObj(file, Ente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(InputStream in) throws DeserializerException {
		return (Ente) this.xmlToObj(in, Ente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnte(byte[] in) throws DeserializerException {
		return (Ente) this.xmlToObj(in, Ente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Ente readEnteFromString(String in) throws DeserializerException {
		return (Ente) this.xmlToObj(in.getBytes(), Ente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: SIP
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public SIP readSIP(String fileName) throws DeserializerException {
		return (SIP) this.xmlToObj(fileName, SIP.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public SIP readSIP(File file) throws DeserializerException {
		return (SIP) this.xmlToObj(file, SIP.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public SIP readSIP(InputStream in) throws DeserializerException {
		return (SIP) this.xmlToObj(in, SIP.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public SIP readSIP(byte[] in) throws DeserializerException {
		return (SIP) this.xmlToObj(in, SIP.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public SIP readSIPFromString(String in) throws DeserializerException {
		return (SIP) this.xmlToObj(in.getBytes(), SIP.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: UtenteDipartimento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(String fileName) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(fileName, UtenteDipartimento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(File file) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(file, UtenteDipartimento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(InputStream in) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(in, UtenteDipartimento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimento(byte[] in) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(in, UtenteDipartimento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.UtenteDipartimento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public UtenteDipartimento readUtenteDipartimentoFromString(String in) throws DeserializerException {
		return (UtenteDipartimento) this.xmlToObj(in.getBytes(), UtenteDipartimento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccPagamento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccPagamento readPccPagamento(String fileName) throws DeserializerException {
		return (PccPagamento) this.xmlToObj(fileName, PccPagamento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccPagamento readPccPagamento(File file) throws DeserializerException {
		return (PccPagamento) this.xmlToObj(file, PccPagamento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccPagamento readPccPagamento(InputStream in) throws DeserializerException {
		return (PccPagamento) this.xmlToObj(in, PccPagamento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccPagamento readPccPagamento(byte[] in) throws DeserializerException {
		return (PccPagamento) this.xmlToObj(in, PccPagamento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccPagamento readPccPagamentoFromString(String in) throws DeserializerException {
		return (PccPagamento) this.xmlToObj(in.getBytes(), PccPagamento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-documento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDocumento readIdDocumento(String fileName) throws DeserializerException {
		return (IdDocumento) this.xmlToObj(fileName, IdDocumento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDocumento readIdDocumento(File file) throws DeserializerException {
		return (IdDocumento) this.xmlToObj(file, IdDocumento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDocumento readIdDocumento(InputStream in) throws DeserializerException {
		return (IdDocumento) this.xmlToObj(in, IdDocumento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDocumento readIdDocumento(byte[] in) throws DeserializerException {
		return (IdDocumento) this.xmlToObj(in, IdDocumento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdDocumento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdDocumento readIdDocumentoFromString(String in) throws DeserializerException {
		return (IdDocumento) this.xmlToObj(in.getBytes(), IdDocumento.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-utente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(String fileName) throws DeserializerException {
		return (IdUtente) this.xmlToObj(fileName, IdUtente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(File file) throws DeserializerException {
		return (IdUtente) this.xmlToObj(file, IdUtente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(InputStream in) throws DeserializerException {
		return (IdUtente) this.xmlToObj(in, IdUtente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtente(byte[] in) throws DeserializerException {
		return (IdUtente) this.xmlToObj(in, IdUtente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdUtente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdUtente readIdUtenteFromString(String in) throws DeserializerException {
		return (IdUtente) this.xmlToObj(in.getBytes(), IdUtente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccUtenteOperazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccUtenteOperazione readPccUtenteOperazione(String fileName) throws DeserializerException {
		return (PccUtenteOperazione) this.xmlToObj(fileName, PccUtenteOperazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccUtenteOperazione readPccUtenteOperazione(File file) throws DeserializerException {
		return (PccUtenteOperazione) this.xmlToObj(file, PccUtenteOperazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccUtenteOperazione readPccUtenteOperazione(InputStream in) throws DeserializerException {
		return (PccUtenteOperazione) this.xmlToObj(in, PccUtenteOperazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccUtenteOperazione readPccUtenteOperazione(byte[] in) throws DeserializerException {
		return (PccUtenteOperazione) this.xmlToObj(in, PccUtenteOperazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccUtenteOperazione readPccUtenteOperazioneFromString(String in) throws DeserializerException {
		return (PccUtenteOperazione) this.xmlToObj(in.getBytes(), PccUtenteOperazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: NotificaEsitoCommittente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(String fileName) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(fileName, NotificaEsitoCommittente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(File file) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(file, NotificaEsitoCommittente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(InputStream in) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(in, NotificaEsitoCommittente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittente(byte[] in) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(in, NotificaEsitoCommittente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public NotificaEsitoCommittente readNotificaEsitoCommittenteFromString(String in) throws DeserializerException {
		return (NotificaEsitoCommittente) this.xmlToObj(in.getBytes(), NotificaEsitoCommittente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: id-contabilizzazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdContabilizzazione readIdContabilizzazione(String fileName) throws DeserializerException {
		return (IdContabilizzazione) this.xmlToObj(fileName, IdContabilizzazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdContabilizzazione readIdContabilizzazione(File file) throws DeserializerException {
		return (IdContabilizzazione) this.xmlToObj(file, IdContabilizzazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdContabilizzazione readIdContabilizzazione(InputStream in) throws DeserializerException {
		return (IdContabilizzazione) this.xmlToObj(in, IdContabilizzazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdContabilizzazione readIdContabilizzazione(byte[] in) throws DeserializerException {
		return (IdContabilizzazione) this.xmlToObj(in, IdContabilizzazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.IdContabilizzazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public IdContabilizzazione readIdContabilizzazioneFromString(String in) throws DeserializerException {
		return (IdContabilizzazione) this.xmlToObj(in.getBytes(), IdContabilizzazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccTracciaTrasmissione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissione readPccTracciaTrasmissione(String fileName) throws DeserializerException {
		return (PccTracciaTrasmissione) this.xmlToObj(fileName, PccTracciaTrasmissione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissione readPccTracciaTrasmissione(File file) throws DeserializerException {
		return (PccTracciaTrasmissione) this.xmlToObj(file, PccTracciaTrasmissione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissione readPccTracciaTrasmissione(InputStream in) throws DeserializerException {
		return (PccTracciaTrasmissione) this.xmlToObj(in, PccTracciaTrasmissione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissione readPccTracciaTrasmissione(byte[] in) throws DeserializerException {
		return (PccTracciaTrasmissione) this.xmlToObj(in, PccTracciaTrasmissione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccTracciaTrasmissione readPccTracciaTrasmissioneFromString(String in) throws DeserializerException {
		return (PccTracciaTrasmissione) this.xmlToObj(in.getBytes(), PccTracciaTrasmissione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: PccOperazione
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccOperazione readPccOperazione(String fileName) throws DeserializerException {
		return (PccOperazione) this.xmlToObj(fileName, PccOperazione.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccOperazione readPccOperazione(File file) throws DeserializerException {
		return (PccOperazione) this.xmlToObj(file, PccOperazione.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccOperazione readPccOperazione(InputStream in) throws DeserializerException {
		return (PccOperazione) this.xmlToObj(in, PccOperazione.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccOperazione readPccOperazione(byte[] in) throws DeserializerException {
		return (PccOperazione) this.xmlToObj(in, PccOperazione.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public PccOperazione readPccOperazioneFromString(String in) throws DeserializerException {
		return (PccOperazione) this.xmlToObj(in.getBytes(), PccOperazione.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Utente
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(String fileName) throws DeserializerException {
		return (Utente) this.xmlToObj(fileName, Utente.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(File file) throws DeserializerException {
		return (Utente) this.xmlToObj(file, Utente.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(InputStream in) throws DeserializerException {
		return (Utente) this.xmlToObj(in, Utente.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtente(byte[] in) throws DeserializerException {
		return (Utente) this.xmlToObj(in, Utente.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Utente readUtenteFromString(String in) throws DeserializerException {
		return (Utente) this.xmlToObj(in.getBytes(), Utente.class);
	}	
	
	
	
	/*
	 =================================================================================
	 Object: Evento
	 =================================================================================
	*/
	
	/**
	 * Transform the xml in <var>fileName</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param fileName Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Evento readEvento(String fileName) throws DeserializerException {
		return (Evento) this.xmlToObj(fileName, Evento.class);
	}
	
	/**
	 * Transform the xml in <var>file</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param file Xml file to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Evento readEvento(File file) throws DeserializerException {
		return (Evento) this.xmlToObj(file, Evento.class);
	}
	
	/**
	 * Transform the input stream <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param in InputStream to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Evento readEvento(InputStream in) throws DeserializerException {
		return (Evento) this.xmlToObj(in, Evento.class);
	}	
	
	/**
	 * Transform the byte array <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param in Byte array to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Evento readEvento(byte[] in) throws DeserializerException {
		return (Evento) this.xmlToObj(in, Evento.class);
	}	
	
	/**
	 * Transform the String <var>in</var> in the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * 
	 * @param in String to use for the reconstruction of the object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @return Object type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 * @throws DeserializerException The exception that is thrown when an error occurs during deserialization
	 */
	public Evento readEventoFromString(String in) throws DeserializerException {
		return (Evento) this.xmlToObj(in.getBytes(), Evento.class);
	}	
	
	
	

}
