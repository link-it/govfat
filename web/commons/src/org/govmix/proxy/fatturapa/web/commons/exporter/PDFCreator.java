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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;

/****
 * 
 * Classe che genera il pdf a partire dall'xml 
 * 
 * @author pintori
 *
 */
public class PDFCreator {

	private TransformerFactory factory = null;

	private Logger log = null;


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

		try{
			this.factory = TransformerFactory.newInstance();
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'inizializzazione del PDFCreator: "+e.getMessage(), e);
		}
	}

	public void createPDF(InputStream xmlfile, InputStream xsltfile, OutputStream outputStream) throws Exception{
		try {
			this.log.debug("Creazione del PDF in corso...");
			// Trasformazione 1 XML -> HTML tramite foglio stile XSL
			this.log.debug("Creo Transformer...");
			Transformer transformer = this.factory.newTransformer(new StreamSource(xsltfile));
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
		} catch (IOException e) {
			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
			throw e;
		} catch (DocumentException e) {
			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
			throw e;
		} catch (TransformerException e) {
			this.log.error("Si e' verificato un errore durante la creazione del PDF: " + e.getMessage(), e);
			throw e;
		}

	}
}
