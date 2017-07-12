/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;


/**
 * ConsoleProperties classe per la gestione delle properties dell'applicazione.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class ConsoleProperties {

	//Properties Connession DB
	private String invioEsitoCommittenteURL;


	private static final String propertiesPath = "/fatturapa.properties";
	/** Copia Statica */
	private static ConsoleProperties consoleProperties = null;

	public static synchronized void initialize(org.apache.log4j.Logger log) throws Exception{

		if(ConsoleProperties.consoleProperties==null)
			ConsoleProperties.consoleProperties = new ConsoleProperties(log);	
	}

	public static ConsoleProperties getInstance(org.apache.log4j.Logger log) throws Exception{

		if(ConsoleProperties.consoleProperties==null)
			initialize(log);

		return ConsoleProperties.consoleProperties;
	}

	/* ********  F I E L D S  P R I V A T I  ******** */

	/** Reader delle proprieta' impostate nel file 'server.properties' */
	private Properties reader;

	private	Properties externalProps ;


	/* ********  C O S T R U T T O R E  ******** */

	/**
	 * Viene chiamato in causa per istanziare il properties reader
	 *
	 * 
	 */
	public ConsoleProperties(org.apache.log4j.Logger log) throws Exception{

		/* ---- Lettura del cammino del file di configurazione ---- */

		Properties propertiesReader = new Properties();
		java.io.InputStream properties = null;
		try{  
			properties = ConsoleProperties.class.getResourceAsStream(propertiesPath);
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
		String externalPropertiesPath = null;
		Properties externalPropertiesReader = new Properties();
		FileInputStream fis = null;
		try{
			
			//carico le properties
			externalPropertiesPath = getProperty("org.govmix.proxy.fatturapa.web.console.properties.path", true);

			File propertiesFile = new File(externalPropertiesPath + "/fatturaPAConsole.properties");

			if(!propertiesFile.exists()) {
				throw new Exception("il file ["+propertiesFile.getAbsolutePath()+"] non esiste. Impossibile avviare l'applicazione");
			}
			  fis = new FileInputStream(propertiesFile);
			externalPropertiesReader.load(fis);
			fis.close();
		}catch(Exception e){
			log.error("Riscontrato errore durante la lettura del file '"+externalPropertiesPath + "/fatturaPAConsole.properties"+"': "+e.getMessage(),e);
			try{
				if(fis!=null)
					fis.close();
			}catch(Exception er){}
			throw e;
		}

		this.externalProps = externalPropertiesReader;
		// 
		this.invioEsitoCommittenteURL = this.externalProps.getProperty("invioEsitoCommittente.url");

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

	public String getInvioEsitoCommittenteURL() {
		return invioEsitoCommittenteURL;
	}

	public void setInvioEsitoCommittenteURL(String invioEsitoCommittenteURL) {
		this.invioEsitoCommittenteURL = invioEsitoCommittenteURL;
	}

}
