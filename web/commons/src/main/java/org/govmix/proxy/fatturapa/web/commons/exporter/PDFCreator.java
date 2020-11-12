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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.xhtmlrenderer.pdf.ITextRenderer;

/****
 * 
 * Classe che genera il pdf a partire dall'xml 
 * 
 * @author pintori
 *
 */
public class PDFCreator {

	public enum TipoXSL {
		FATTURA_V10,
		FATTURA_V11, 
		FATTURA_V12, 
		NOTIFICA_EC, 
		SCARTO_EC, 
		NOTIFICA_DT, 
		PCC_RIALLINEAMENTO,
		TRACCIA_AT, 
		TRACCIA_MC, 
		TRACCIA_MT, 
		TRACCIA_NE, 
		TRACCIA_NS, 
		TRACCIA_RC
		}
	
//	private static TransformerFactory factory = null;
	private Logger log = null;


	private Map<TipoXSL, Transformer> transformerMap;
	private static PDFCreator _instance = null;

	public static PDFCreator getInstance(Logger log){
		if(PDFCreator._instance == null)
			init(log);

		return PDFCreator._instance;
	}

	private static synchronized void init(Logger log){
		if(PDFCreator._instance == null)
			PDFCreator._instance = new PDFCreator(log);
	}

	public PDFCreator(Logger log){
		this.log = log;
		this.transformerMap = this.getTransformers(log, TransformerFactory.newInstance());
	}

//	public void createPDF(InputStream xmlfile, InputStream xsltfile, OutputStream outputStream) throws Exception{
//		try {
//			this.log.debug("Creazione del PDF in corso...");
//			// Trasformazione 1 XML -> HTML tramite foglio stile XSL
//			this.log.debug("Creo Transformer...");
//			Transformer transformer = PDFCreator.factory.newTransformer(new StreamSource(xsltfile));
//			this.log.debug("Transformer creato.");
//
//			this.log.debug("Creo Source XML da trasformare...");
//			Source src = new StreamSource(xmlfile);
//			this.log.debug("Source creato.");
//
////			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			org.w3c.dom.Document pdfDoc = factory.newDocumentBuilder().newDocument();			
//			Result res = new DOMResult(pdfDoc);
//
//			this.log.debug("Eseguo la trasformazione...");
//			transformer.transform(src, res);
//			this.log.debug("Trasformazione completata.");
//
////			XMLUtils.getInstance().writeTo(pdfDoc, baos);
////			this.log.info(baos.toString()); 
//			// Trasformazione 2 HTML -> PDF
//			this.log.debug("Eseguo la scrittura del PDF...");
//			this.log.debug("Creazione Rendered in corso...");
//			ITextRenderer renderer = new ITextRenderer();
//			this.log.debug("Creazione Rendered completata.");
//			renderer.setDocument(pdfDoc, null);
//			renderer.layout();
//			
//			this.log.debug("Scrittura PDF in corso...");
//			renderer.createPDF(outputStream);
//			this.log.debug("Scrittura PDF in completata.");
//
//			this.log.debug("Creazione del PDF completata con successo.");
////		} catch (IOException e) {
////			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
////			throw e;
////		} catch (DocumentException e) {
////			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
////			throw e;
//		} catch (TransformerException e) {
//			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
//			throw e;
//		}
//
//	}

	public Map<TipoXSL, Transformer> getTransformers(Logger log, TransformerFactory transformerFactory) {
		String nomeFile = null;
		
		 Map<TipoXSL, Transformer> tMap = new HashMap<PDFCreator.TipoXSL, Transformer>();
		for(TipoXSL tipoXSL: TipoXSL.values()) {
			try{
				log.debug("Caricamento dello schema XSL per il tipo ["+tipoXSL+"] in corso...");
	
				String baseDir = StringUtils.isEmpty(CommonsProperties.getInstance(log).getXslBaseDir()) ? "" :
					File.separatorChar + CommonsProperties.getInstance(log).getXslBaseDir();
	
				String xslFileName = CommonsProperties.getInstance(log).getXslTraccia(tipoXSL);
				Transformer transformer = transformerFactory.newTransformer(new StreamSource(getTransformerInputStream(baseDir, xslFileName, log)));
				tMap.put(tipoXSL, transformer);
			}catch(Exception e){
				log.error("Si e' verificato un errore durante il caricamento dell'xsl ["+nomeFile+"]: " + e.getMessage(), e);
			}
		}
		return tMap;
	}
	
	private static InputStream getTransformerInputStream(String baseDir, String xslFileName, Logger log) throws Exception{
		InputStream xsltfile = null;
		try{

			String nomeFile = baseDir + File.separatorChar + xslFileName;

			File file = new File(baseDir, xslFileName);
			
			if(file.exists()) {
				xsltfile = new FileInputStream(file);
			} else {
				xsltfile = PDFCreator.class.getResourceAsStream(file.getAbsolutePath());
			}
				

			if(xsltfile == null)
				throw new NullPointerException("Impossibile caricare il file ["+nomeFile+"]");
			
			return xsltfile;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il caricamento dell'xsl ["+xslFileName+"]: " + e.getMessage(), e);
			throw new ExportException(e);
		}
	}
	
	public void createPDF(InputStream xmlfile, TipoXSL tipoXSL, OutputStream outputStream) throws Exception{
		try {
			this.log.debug("Creazione del PDF in corso...");
			// Trasformazione 1 XML -> HTML tramite foglio stile XSL
			this.log.debug("Creo Transformer...");
			Transformer transformer = this.transformerMap.get(tipoXSL);//this.factory.newTransformer(new StreamSource(xsltfile));
			
			if(transformer == null) {
				throw new Exception("Transformer non inizializzato per il tipo ["+tipoXSL+"]");
			}
			this.log.debug("Transformer creato.");

			this.log.debug("Creo Source XML da trasformare...");
			Source src = new StreamSource(xmlfile);
			this.log.debug("Source creato.");

//			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			org.w3c.dom.Document pdfDoc = factory.newDocumentBuilder().newDocument();			
			Result res = new DOMResult(pdfDoc);

			this.log.debug("Eseguo la trasformazione...");
			transformer.transform(src, res);
			this.log.debug("Trasformazione completata.");

//			XMLUtils.getInstance().writeTo(pdfDoc, baos);
//			this.log.info(baos.toString()); 
			// Trasformazione 2 HTML -> PDF
			this.log.debug("Eseguo la scrittura del PDF...");
			this.log.debug("Creazione Rendered in corso...");
			ITextRenderer renderer = new ITextRenderer();
			this.log.debug("Creazione Rendered completata.");
			renderer.setDocument(pdfDoc, null);
			renderer.layout();
			
			this.log.debug("Scrittura PDF in corso...");
			renderer.createPDF(outputStream);
			this.log.debug("Scrittura PDF in completata.");

			this.log.debug("Creazione del PDF completata con successo.");
//		} catch (IOException e) {
//			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
//			throw e;
//		} catch (DocumentException e) {
//			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
//			throw e;
		} catch (TransformerException e) {
			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
			throw e;
		}

	}
}
