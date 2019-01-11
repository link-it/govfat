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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.activation.FileDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.io.ZipUtilities;

public abstract class AbstractSingleFileImporter<T> {


	protected abstract T newObject();
	protected abstract void process(T archivio, byte[] fileAsByte, String currentDirName, String nome, String tipo);
	protected abstract void processDirectory(String nomeDirectory);
	
	protected Logger log;

	public AbstractSingleFileImporter(Logger log) {
		this.log = log;
	}

	/**
	 * Ritorna la rappresentazione java di un archivio
	 * 
	 * @param m InputStream
	 * @return T
	 * @throws Exception
	 */
	public T getArchive(InputStream m) throws Exception{
		ByteArrayOutputStream bout = null;
		try{
			bout = new ByteArrayOutputStream();
			IOUtils.copy(m, bout);
			IOUtils.closeQuietly(m);

			return getArchive(bout.toByteArray());

		}finally{
			IOUtils.closeQuietly(bout);
		}
	}


	/**
	 * Ritorna la rappresentazione java di un archivio
	 * 
	 * @param zip byte[]
	 * @return Archive
	 * @throws Exception
	 */
	public T getArchive(byte[] zip) throws Exception{
		File tmp = null;
		FileOutputStream fout = null; 
		try{
			tmp = File.createTempFile("importer", ".zip");

			fout = new FileOutputStream(tmp);
			fout.write(zip);
			fout.flush();
			fout.close();

			return getArchive(tmp);

		}finally{
			try{
				if(fout!=null)
					fout.close();
				if(tmp!=null)
					tmp.delete();
			}catch(Exception eClose){}
		}

	}
	
	
	/**
	 * Ritorna la rappresentazione java di un archivio
	 * 
	 * @param zip File
	 * @return Archive
	 * @throws Exception
	 */
	public T getArchive(File zip) throws Exception{
		ZipFile zipFile = null;
		try{
			zipFile = new ZipFile(zip);
			return getArchive(zipFile);
		}finally{
			try{
				if(zipFile!=null)
					zipFile.close();
			}catch(Exception eClose){}
		}
	}


	/**
	 * Restituisce la rappresentazione java di un archivio
	 * 
	 * @param zip File
	 * @return Object
	 * @throws Exception
	 */
	public T getArchive(ZipFile zip) throws Exception{
		try{
			T archivio = this.newObject();

			Enumeration<?> entry = zip.entries();
			while(entry.hasMoreElements()) {

				ZipEntry zipEntry = (ZipEntry)entry.nextElement();
				
				log.debug("Nome cartella da processare: ["+zipEntry.getName()+"] directory? ["+zipEntry.isDirectory()+"]");
				if(!zipEntry.isDirectory()) {
					String entryName = ZipUtilities.operativeSystemConversion(zipEntry.getName());
					String rootDir = getDir(entryName);
					log.debug("current dir ["+rootDir+"]");
					this.processDirectory(rootDir);
					FileDataSource fds = new FileDataSource(entryName);
					String nome = fds.getName();
	
					String tipo = nome.substring(nome.lastIndexOf(".")+1,nome.length()).toLowerCase(); 
	
					nome = nome.substring(0, nome.lastIndexOf("."));
					//System.out.println("VERIFICARE NAME["+nome+"] TIPO["+tipo+"]");
					InputStream inputStream = null;
					try {						
						inputStream = zip.getInputStream(zipEntry);
						byte[]fileAsByte = null;
						try {
							fileAsByte = Utilities.getAsByteArray(inputStream);
						} catch(UtilsException e) {
							fileAsByte = new byte[] {};
						}
						this.process(archivio, fileAsByte, rootDir, nome, tipo);
					} finally {
						if(inputStream != null) {
							try {inputStream.close();} catch(Exception e) {}
						}
					}
				}
			}

			return archivio;

		}catch(Exception e){
			throw new Exception("Errore durante l'import dallo zip: "+e.getMessage(),e);
		}
	}

	private static String getDir(String entry) throws UtilsException{
		try{
			String rootDir = null;
			String dir = entry;
			int indexOf=dir.lastIndexOf(File.separatorChar);
			if(indexOf<=0){
				return null;
			}
			dir = dir.substring(0,indexOf);
			if(dir==null || "".equals(dir)){
				return null;
			}
			rootDir=dir+File.separatorChar;
			return rootDir;
		}catch(Exception e){
			throw new UtilsException("Errore durante la comprensione della directory presente all'interno dello zip: "+e.getMessage(),e);
		}
	}

}
