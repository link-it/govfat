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
import javax.xml.xpath.XPathFactory;

import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.log4j.Logger;
import org.openspcoop2.protocol.sdi.utils.P7MInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LottoFattureAnalyzer {

	private boolean isP7M;
	private boolean isFirmato;
	private byte[] original;
	private byte[] decoded;

	public LottoFattureAnalyzer(byte[] lottoFatture, Logger log) throws Exception {
		this.original = lottoFatture;
		try {
			P7MInfo info = new P7MInfo(lottoFatture, log);
			this.decoded = info.getXmlDecoded();
			this.isP7M = true;
			this.isFirmato = true;
		} catch(Throwable t) {
			log.debug("Errore durante l'acquisizione del lotto P7M:" + t.getMessage() + ". Provo ad acquisire lotto XML");
			this.isP7M = false;
			try {
				this.decoded = extractContentFromXadesSignedFile(lottoFatture);
				if(this.decoded != null) {
					this.isFirmato = true;
				} else {
					this.decoded = lottoFatture;
				}

			} catch(Exception e) {
				log.error("Errore durante l'acquisizione del lotto xml:" + e.getMessage(), e);
				throw e;
			}
		}
	}

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

	private byte[] extractContentFromXadesSignedFile(byte[] xmlIn) throws Exception {
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
					//TODO verificare firma?
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

	public boolean isP7M() {
		return isP7M;
	}

	public void setP7M(boolean isP7M) {
		this.isP7M = isP7M;
	}

	public boolean isFirmato() {
		return isFirmato;
	}

	public void setFirmato(boolean isFirmato) {
		this.isFirmato = isFirmato;
	}

	public byte[] getOriginal() {
		return original;
	}

	public void setOriginal(byte[] original) {
		this.original = original;
	}

	public byte[] getDecoded() {
		return decoded;
	}

	public void setDecoded(byte[] decoded) {
		this.decoded = decoded;
	}

}

