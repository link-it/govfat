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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public abstract class AbstractSingleFileExporter<T, K> {

	public enum FORMAT {RAW, PDF, ZIP}

	public static final String BASE_DIR_NAME = "Fatture";
	public static final String FORMATO_ZIP_CON_ALLEGATI = "zipWA";
	public static final String FORMATO_ZIP = "zip";
	public static final String FORMATO_PDF = "pdf";
	public static final String FORMATO_XML = "xml";

	public static final String PARAMETRO_ACTION_FATTURA = "f";
	public static final String PARAMETRO_ACTION_ALLEGATO = "a";
	public static final String PARAMETRO_ACTION_NOTIFICA_EC = "ec";
	public static final String PARAMETRO_ACTION_NOTIFICA_DT = "dt";
	public static final String PARAMETRO_ACTION_SCARTO = "sc";
    public static final String PARAMETRO_ACTION_PCC_RIALLINEAMENTO = "pccDf";
    public static final String PARAMETRO_ACTION_RAPPORTO_VERSAMENTO = "rv";
    public static final String PARAMETRO_ACTION_RAPPORTO_VERSAMENTO_LOTTO = "rvl";
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_FATTURA_USCITA = "com_"+ TipoComunicazioneType.FAT_OUT.toString();
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_RICEVUTA_CONSEGNA = "com_"+ TipoComunicazioneType.RC.toString();
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_SCARTO = "com_"+ TipoComunicazioneType.NS.toString();
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_MANCATA_CONSEGNA = "com_"+ TipoComunicazioneType.MC.toString();
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_ESITO_COMMITTENTE = "com_"+ TipoComunicazioneType.NE.toString();
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE = "com_"+ TipoComunicazioneType.DT.toString();
    public static final String PARAMETRO_ACTION_COMUNICAZIONE_AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO = "com_"+ TipoComunicazioneType.AT.toString();

	protected Logger log;
	private IUtenteServiceSearch utenteSearchDAO;
	private FatturaBD fatturaBD;


	public AbstractSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		this.log = log;
		this.utenteSearchDAO = DAOFactory.getInstance().getServiceManager(connection, autocommit).getUtenteServiceSearch();
		this.fatturaBD = new FatturaBD(log, connection, autocommit);
	}

	public AbstractSingleFileExporter(Logger log) throws ServiceException, NotImplementedException, Exception {
		this.log = log;
		this.utenteSearchDAO = DAOFactory.getInstance().getServiceManager().getUtenteServiceSearch();
		this.fatturaBD = new FatturaBD(log);
	}

	public abstract void export(T object, OutputStream out, FORMAT format) throws ExportException;
	
	public abstract T convertToObject(K id) throws ExportException;
	public abstract T convertToObject(String id) throws ExportException;

	public abstract byte[] getRawContent(T object);
	public abstract String getRawName(T object);
	public abstract String getRawExtension(T object);

	public String exportAsRaw(T object, OutputStream out) throws ExportException {
		try {
			out.write(this.getRawContent(object));
		} catch (IOException e) {
			throw new ExportException(e);
		}
		
		String ext = this.getRawExtension(object);
		
		if(ext != null) {
			return this.getRawName(object) + "." +  this.getRawExtension(object);
		} else {
			return this.getRawName(object);
		}
	}


	public String exportAsRawById(K id,OutputStream out) throws ExportException {
		return exportAsRaw(this.convertToObject(id), out);
	}

	public void exportAsZip(List<T> list,ZipOutputStream zip, String rootDir) throws ExportException {
		for (T object: list) {
			this.exportAsZip(object, zip, rootDir);
		}
	}

	public void exportAsZip(List<T> list,OutputStream out) throws ExportException {
		Date startTime = Calendar.getInstance().getTime();
		ZipOutputStream zip = new ZipOutputStream(out);
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			this.exportAsZip(list, zip, "");

			// chiusura entry
			zip.flush();
			zip.closeEntry();

			Date dataFine = Calendar.getInstance().getTime();

			// chiusura zip

			zip.flush();
			zip.close();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

		}catch(ExportException e){
			this.log.error("Errore durante esportazione zip",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione zip",e);
			throw new ExportException("Errore durante esportazione zip", e);
		}
	}
	
	protected void addEntryToZip(ZipOutputStream zip, String nomeEntry, byte[] content) throws Exception {
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream in = null;
		
		byte[] buf = new byte[1024];
		int len;
		try {
			// Creazione File XML
			zip.putNextEntry(new ZipEntry(nomeEntry));
	
			baos = new ByteArrayOutputStream();
			baos.write(content);
	
			in = new ByteArrayInputStream(baos.toByteArray());
	
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
	
			if(in!=null)
				in.close();
	
			zip.flush();
			zip.closeEntry();
		} finally {
			if(in != null) {
				in.close();
			}
			if(baos != null) {
				baos.close();
			}
		}

	}
	
	public void exportAsZip(T object, ZipOutputStream zip, String rootDir) throws ExportException {
		try{

			String nomeCompleto = null;
			byte[] content = this.getRawContent(object);
			if(content != null){
	
				String ext = this.getRawExtension(object);
				
				if(ext != null) {
					nomeCompleto = this.getRawName(object) + "." +  this.getRawExtension(object);
				} else {
					nomeCompleto = this.getRawName(object);
				}

				addEntryToZip(zip, rootDir + nomeCompleto, content);
			}
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione dell'oggetto di classe["+object.getClass()+ "] nome ["+this.getRawName(object)+"]";
			throw new ExportException(msg, e);
		}
	}

	public void exportAsZipById(K id,ZipOutputStream out, String rootDir) throws ExportException {
		exportAsZip(this.convertToObject(id), out, rootDir);
	}


	protected abstract List<IdFattura> findIdFattura(String[] ids, boolean isAll) throws ServiceException, NotFoundException;

	public boolean checkautorizzazioneExport(String username, String []ids, boolean isAll) throws ExportException {

		try {
			log.debug("Controllo autorizzazione per l'utente ["+username+"] in corso...");
			IExpression utenteExpr = this.utenteSearchDAO.newExpression();
			utenteExpr.equals(Utente.model().USERNAME, username);
			Utente utente = null;
			try{
				utente = this.utenteSearchDAO.find(utenteExpr);
				log.debug("Controllo autorizzazione per l'utente ["+username+"] completato.");
			}catch(NotFoundException e){
				log.debug("Controllo autorizzazione per l'utente ["+username+"] fallito, utente non registrato.");
				throw new ExportException("Controllo autorizzazione per l'utente ["+username+"] fallito, utente non registrato.");
			}

			UserRole role = utente.getRole();
			log.debug("Controllo autorizzazione per l'utente ["+username+"] Ruolo Trovato["+role.toString()+"].");
			if(role.equals(UserRole.ADMIN)){
				log.debug("Controllo autorizzazione per l'utente ["+username+"] completato, l'utente con ruolo ADMIN e' autorizzato.");
				return true;
			}

			List<IdFattura> idFatturaRichiesti = null;
			try {
				idFatturaRichiesti = findIdFattura(ids, isAll);
			}catch(NotFoundException e){
				log.debug("Impossibile trovare la risorsa richiesta:"+ e.getMessage(), e);
				throw new ExportException("Impossibile trovare la risorsa richiesta.");
			}

			// passo alla bd l'id utente che mi restituisce una lista di idFattura 
			List<IdFattura> idFattureByUtente = this.fatturaBD.getIdFattureByUtente(utente);


			for (IdFattura idFattura : idFatturaRichiesti) {
				boolean found = false;
				for (IdFattura idFatturaAutorizzata : idFattureByUtente) {
					if(idFattura.equals(idFatturaAutorizzata)){
						found = true;
						break;
					}
				}
				if(!found)
					return false;
			}

			return true;


		}catch(Exception e){
			log.error("Si e' verificato un errore durante la verifica delle autorizzazione export per l'utente ["+username+"]: "+ e.getMessage() , e);
			throw new ExportException("Si e' verificato un errore durante la verifica delle autorizzazione export per l'utente ["+username+"]");
		}

	}
//
//	public IFatturaElettronicaServiceSearch getFatturaSearchDAO() {
//		return fatturaSearchDAO;
//	}

	public FatturaBD getFatturaBD() {
		return fatturaBD;
	}

}
