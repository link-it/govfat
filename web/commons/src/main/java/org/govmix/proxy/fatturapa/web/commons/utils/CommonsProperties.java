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
package org.govmix.proxy.fatturapa.web.commons.utils;

import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;


/***
 * 
 * Classe di properties per il progetto commons
 * 
 * 
 * @author pintori
 *
 */
public class CommonsProperties {

	/** XSL **/
	private String xslFatturaSDI10;
	private String xslFatturaSDI11;
	private String xslFatturaV12;
	private String xslNotificaEC;
	private String xslNotificaDT;
	private String xslScartoEC;
	private String xslPccRiallineamento;
	private String xslBaseDir;
	
	private URL invioFatturaURL;
	private String invioFatturaUsername;
	private String invioFatturaPassword;

	private String idEgovHeader;

	private static final String propertiesPath = "/webCommons.properties";
	/** Copia Statica */
	private static CommonsProperties commonsProperties = null;

	public static synchronized void initialize(org.apache.log4j.Logger log) throws Exception{

		if(CommonsProperties.commonsProperties==null)
			CommonsProperties.commonsProperties = new CommonsProperties(log);	
	}

	public static CommonsProperties getInstance(org.apache.log4j.Logger log) throws Exception{

		if(CommonsProperties.commonsProperties==null)
			initialize(log);

		return CommonsProperties.commonsProperties;
	}

	/* ********  F I E L D S  P R I V A T I  ******** */

	/** Reader delle proprieta' impostate nel file 'server.properties' */
	private Properties reader;


	/* ********  C O S T R U T T O R E  ******** */

	/**
	 * Viene chiamato in causa per istanziare il properties reader
	 *
	 * 
	 */
	public CommonsProperties(org.apache.log4j.Logger log) throws Exception{

		/* ---- Lettura del cammino del file di configurazione ---- */

		Properties propertiesReader = new Properties();
		java.io.InputStream properties = null;
		try{  
			properties = CommonsProperties.class.getResourceAsStream(propertiesPath);
			if(properties==null){
				throw new Exception("Properties "+propertiesPath+" not found");
			}
			propertiesReader.load(properties);
			properties.close();
		}catch(java.io.IOException e) {
			log.error("Riscontrato errore durante la lettura del file '"+propertiesPath+"': "+e.getMessage(),e);
			try{
				if(properties!=null)
					properties.close();
			}catch(Exception er){}
			throw e;
		}	

		this.reader = propertiesReader;

		//carico le properties
		this.xslFatturaSDI10 = getProperty("xsl.fatturapa.sdi10", true);
		this.xslFatturaSDI11 = getProperty("xsl.fatturapa.sdi11", true);
		this.xslFatturaV12 = getProperty("xsl.fatturapa.v12", true);
		this.xslNotificaDT = getProperty("xsl.notificaDT", true);
		this.xslNotificaEC = getProperty("xsl.notificaEC", true);
		this.xslScartoEC = getProperty("xsl.scartoEC", true);
		this.xslPccRiallineamento = getProperty("xsl.PccRiallineamento", true);
		this.xslBaseDir = getProperty("xsl.baseDir", true);
		
		this.invioFatturaURL = new URL(this.getProperty("invioFattura.url", true));
		this.invioFatturaUsername = this.getProperty("invioFattura.username", false);
		this.invioFatturaPassword = this.getProperty("invioFattura.password", false);

		this.idEgovHeader = this.getProperty("header.idegov", true);

	}

	/* ********  P R O P E R T I E S  ******** */

	public Enumeration<?> keys(){
		return this.reader.propertyNames();
	}

	public String getProperty(String name,boolean required) throws Exception{
		String tmp = null;

		tmp = this.reader.getProperty(name);

		if(tmp==null){
			if(required){
				throw new Exception("Property ["+name+"] not found");
			}
		}
		if(tmp!=null){
			return tmp.trim();
		}else{
			return null;
		}
	}

	public Boolean getBooleanProperty(String name,boolean required) throws Exception{
		String propAsString = getProperty(name, required);

		if(propAsString != null){
			Boolean b = new Boolean(propAsString.equalsIgnoreCase("true"));
			return b;
		}
		return null;
	}

	public String getXslNotificaEC() {
		return xslNotificaEC;
	}

	public void setXslNotificaEC(String xslNotificaEC) {
		this.xslNotificaEC = xslNotificaEC;
	}

	public String getXslNotificaDT() {
		return xslNotificaDT;
	}

	public void setXslNotificaDT(String xslNotificaDT) {
		this.xslNotificaDT = xslNotificaDT;
	}

	public String getXslScartoEC() {
		return xslScartoEC;
	}

	public void setXslScartoEC(String xslScartoEC) {
		this.xslScartoEC = xslScartoEC;
	}

	public String getXslBaseDir() {
		return xslBaseDir;
	}

	public void setXslBaseDir(String xslBaseDir) {
		this.xslBaseDir = xslBaseDir;
	}

	public String getXslFatturaSDI10() {
		return this.xslFatturaSDI10;
	}

	public void setXslFatturaSDI10(String xslFatturaSDI10) {
		this.xslFatturaSDI10 = xslFatturaSDI10;
	}

	public String getXslFatturaSDI11() {
		return this.xslFatturaSDI11;
	}

	public void setXslFatturaSDI11(String xslFatturaSDI11) {
		this.xslFatturaSDI11 = xslFatturaSDI11;
	}

	public String getXslPccRiallineamento() {
		return this.xslPccRiallineamento;
	}

	public void setXslPccRiallineamento(String xslPccRiallineamento) {
		this.xslPccRiallineamento = xslPccRiallineamento;
	}

	public String getXslFatturaV12() {
		return xslFatturaV12;
	}

	public void setXslFatturaV12(String xslFatturaV12) {
		this.xslFatturaV12 = xslFatturaV12;
	}

	public URL getInvioFatturaURL() {
		return invioFatturaURL;
	}
	public String getInvioFatturaUsername() {
		return invioFatturaUsername;
	}
	public String getInvioFatturaPassword() {
		return invioFatturaPassword;
	}

	public String getIdEgovHeader() {
		return idEgovHeader;
	}


}
