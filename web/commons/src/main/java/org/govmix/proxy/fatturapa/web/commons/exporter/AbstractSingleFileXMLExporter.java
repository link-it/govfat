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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.exporter.PDFCreator.TipoXSL;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public abstract class AbstractSingleFileXMLExporter<T,K> extends AbstractSingleFileExporter<T, K> {

	public String exportAsPdf(T object,OutputStream out) throws ExportException {
		InputStream xmlfile = new ByteArrayInputStream(this.getRawContent(object));
		try {
			PDFCreator.getInstance(log).createPDF(xmlfile, getTipoXsl(object), out);
		} catch (Exception e) {
			throw new ExportException(e);
		}
		return this.getRawName(object) + ".pdf";
	}
	
	public String exportAsPdfById(K id,OutputStream out) throws ExportException {
		return exportAsPdf(this.convertToObject(id), out);
	}
	
	protected abstract String getNomeRisorsaXLST(T object) throws Exception;
	protected abstract TipoXSL getTipoXsl(T object);

	protected InputStream getXsltFileStream(T object) throws ExportException {
		InputStream xsltfile = null;
		String nomeFile = null;
		try{

			log.debug("Caricamento dello schema XSL in corso...");

			String baseDir = StringUtils.isEmpty(CommonsProperties.getInstance(this.log).getXslBaseDir()) ? "" :
				File.separatorChar + CommonsProperties.getInstance(this.log).getXslBaseDir();

			nomeFile = baseDir + File.separatorChar + this.getNomeRisorsaXLST(object);
			log.debug("Verra' utilizzato il file ["+nomeFile+"]");

			xsltfile = AbstractSingleFileExporter.class.getResourceAsStream(nomeFile);

			if(xsltfile == null)
				throw new NullPointerException("Impossibile caricare il file ["+nomeFile+"]");
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il caricamento dell'xsl ["+nomeFile+"]: " + e.getMessage(), e);
			throw new ExportException(e);
		}

		return xsltfile;
	}

	@Override
	public void exportAsZip(T object, ZipOutputStream zip,
			String rootDir) throws ExportException {
		ByteArrayOutputStream baos = null;
		try{
			super.exportAsZip(object, zip, rootDir);
			
			String nomeCompleto = null;
			byte[] content = this.getRawContent(object);
			if(content != null){
				baos = new ByteArrayOutputStream();
				exportAsPdf(object, baos);
				nomeCompleto = this.getRawName(object)+ ".pdf";
				addEntryToZip(zip, nomeCompleto, baos.toByteArray());
			}
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione dell'oggetto di classe["+object.getClass()+ "] nome ["+this.getRawName(object)+"]";
			throw new ExportException(msg, e);
		} finally {
			if(baos != null)
				try {
					baos.close();
				} catch (IOException e) {}
		}
	}

	@Override
	public String getRawExtension(T object) {
		return "xml";
	}

	public AbstractSingleFileXMLExporter(Logger log) throws ServiceException, NotImplementedException, Exception {
		super(log);
	}

	public AbstractSingleFileXMLExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
	}

}
