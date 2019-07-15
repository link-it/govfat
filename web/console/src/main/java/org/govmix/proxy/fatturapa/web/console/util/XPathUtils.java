package org.govmix.proxy.fatturapa.web.console.util;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class XPathUtils {

	private static XPathFactory xPathfactory;
	private static boolean init;

	private static synchronized void init() {
		if(!init) {
			xPathfactory = XPathFactory.newInstance();
		}
		init = true;
	}
	
	public static String validaXpath(String input) {
		init();
		XPath xpath = xPathfactory.newXPath();
		try {
//			log.debug("Compilazione dell'espressione ["+input+"]...");
			xpath.compile(input);
//			log.debug("Compilazione dell'espressione ["+input+"] completata con successo");
		} catch(XPathExpressionException e) {
//			log.error("Errore durante la valutazione dell'xPath:" + e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}
}
