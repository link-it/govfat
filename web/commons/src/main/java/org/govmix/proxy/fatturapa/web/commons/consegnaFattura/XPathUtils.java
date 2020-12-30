package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathUtils {

	private static DocumentBuilderFactory dbf;
	private static XPathFactory xPathfactory;
	private static boolean init;

	private static synchronized void init() {
		if(!init) {
			dbf = DocumentBuilderFactory.newInstance();
			xPathfactory = XPathFactory.newInstance();
		}
		init = true;
	}

	public static byte[] extractContentFromXadesSignedFile(byte[] xmlIn) throws Exception {
		InputStream xadesIn = null;
		ByteArrayOutputStream bos = null;
		try {
			init();
			xadesIn = new ByteArrayInputStream(xmlIn);
			dbf.setNamespaceAware(true);
			Document doc = dbf.newDocumentBuilder().parse(xadesIn);

			XPath xpath = xPathfactory.newXPath();

			Map<String, String> map = new HashMap<String, String>();
			map.put("ds", "http://www.w3.org/2000/09/xmldsig#");

			xpath.setNamespaceContext(new MapNamespaceContext(map));

			XPathExpression expr = xpath.compile("//ds:Signature");
			NodeList referenceNodes = (NodeList) expr.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);

			if(referenceNodes.getLength() > 0) {
				for(int i=0;i<referenceNodes.getLength();i++){
					Node referenceNode = referenceNodes.item(i);
					referenceNode.getParentNode().removeChild(referenceNode);
				}

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();

				bos=new ByteArrayOutputStream();
				StreamResult result=new StreamResult(bos);
				transformer.transform(new DOMSource(doc), result);
				return bos.toByteArray();
			} else {
				return null;
			}
		} finally {
			if(xadesIn!=null) try {xadesIn.close();} catch(IOException e) {}                
			if(bos!=null) try {bos.flush();bos.close();} catch(IOException e) {}                
		}
	}


	public static String getPagoPA(byte[] xml, String xPathExpression, String prefix, String nomeFile, Logger log) throws InserimentoLottiException {

		ByteArrayInputStream is = null;
		try {
			init();
			is = new ByteArrayInputStream(xml);
			dbf.setNamespaceAware(true);

			XPath xpath = xPathfactory.newXPath();

			XPathExpression expr = null; 
			try {
				log.debug("Compilazione dell'espressione ["+xPathExpression+"]...");
				expr = xpath.compile(xPathExpression);
				log.debug("Compilazione dell'espressione ["+xPathExpression+"] completata con successo");
			} catch(XPathExpressionException e) {
				log.error("Errore durante la valutazione dell'xPath:" + e.getMessage(), e);
				throw new InserimentoLottiException(CODICE.ERRORE_NODO_PAGOPA_NON_VALIDO, nomeFile, xPathExpression);
			}				
			Document doc = dbf.newDocumentBuilder().parse(is);
			log.debug("Valutazione dell'espressione ["+xPathExpression+"]...");
			NodeList nodeset = (NodeList) expr.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);
			log.debug("Valutazione dell'espressione ["+xPathExpression+"] completata. Trovati ["+nodeset.getLength()+"] risultati");


			int size = 0;
			String napp = null;
			for(int i =0; i < nodeset.getLength(); i++) {
				Node item = nodeset.item(i);

				if(item.getTextContent().startsWith(prefix)) {
					log.debug("MATCH risultato ["+i+"]:" + item.getTextContent());
					napp = item.getTextContent().substring(prefix.length());
					size++;
				} else {
					log.debug("NO MATCH risultato ["+i+"]:" + item.getTextContent());
				}
			}

			if(size > 1) {
				log.error("Trovato ["+size+"] numero avviso con prefisso ["+prefix+"]");
				throw new InserimentoLottiException(CODICE.ERRORE_IDENTIFICAZIONE_PAGOPA, nomeFile, size, prefix);
			}

			return napp;
		} catch (InserimentoLottiException e) {
			throw e;
		} catch (Exception e) {
			log.error("Errore durante la valutazione dell'xPath:" + e.getMessage(), e);
			throw new InserimentoLottiException(CODICE.ERRORE_GENERICO);
		} finally {
			if(is != null)
				try {is.close();} catch (IOException e) {}
		}

	}

	public static String getTipoDocumento(byte[] xml, Logger log) throws Exception {

		ByteArrayInputStream is = null;
		try {
			init();
			is = new ByteArrayInputStream(xml);
			dbf.setNamespaceAware(true);

			XPath xpath = xPathfactory.newXPath();

			String xPathExpression = "/*[local-name()='FatturaElettronica']/FatturaElettronicaBody/DatiGenerali/DatiGeneraliDocumento/TipoDocumento/text()";
			XPathExpression expr = null; 
			log.debug("Compilazione dell'espressione ["+xPathExpression+"]...");
			expr = xpath.compile(xPathExpression);
			log.debug("Compilazione dell'espressione ["+xPathExpression+"] completata con successo");
			Document doc = dbf.newDocumentBuilder().parse(is);
			log.debug("Valutazione dell'espressione ["+xPathExpression+"]...");
			String item = (String) expr.evaluate(doc.getDocumentElement(), XPathConstants.STRING);
			log.debug("Valutazione dell'espressione ["+xPathExpression+"] completata. Valore trovato ["+item+"]");
			return item;
		} catch (Exception e) {
			log.error("Errore durante la valutazione dell'xPath:" + e.getMessage(), e);
			throw e;
		} finally {
			if(is != null)
				try {is.close();} catch (IOException e) {}
		}

	}

}
