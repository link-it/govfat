package org.govmix.proxy.pcc.fatture.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.resources.PropertiesReader;

public abstract class AbstractProperties {

	private PropertiesReader reader;
	private PropertiesReader externalReader;
	private static final String PATH_PROP_NAME = "properties.path"; 
	protected Logger log;

	public AbstractProperties(String path) throws RuntimeException {
		this(LoggerManager.getBatchStartupLogger(), path); //TODO logger corretto
	}
	public AbstractProperties(Logger log, String path) throws RuntimeException {
		try {
			this.log = log;

			if(path == null)
				throw new Exception("Path per localizzare le properties non trovato");
			
			InputStream is = AbstractProperties.class.getResourceAsStream(path);
			this.reader = getPropertiesReader(is, path);
			String externalResourcePath = getProperty(PATH_PROP_NAME, false);
			if(externalResourcePath != null) {
				InputStream isExternal = new FileInputStream(new File(externalResourcePath + "/proxyFatturaPA.properties"));
				this.externalReader = getPropertiesReader(isExternal, externalResourcePath);
			} else {
				this.log.debug("La proprieta' ["+PATH_PROP_NAME+"] non e' stata specificata nel file ["+path+"]. Nessun file esterno verra' letto");
			}
		}catch(Exception e) {
			this.log.error("Errore durante la lettura delle properties dal path ["+path+"]:" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	private PropertiesReader getPropertiesReader(InputStream is, String path) throws Exception {
		if(is == null) {
			throw new Exception("Risorsa ["+path+"] non trovata");
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		Properties props = new Properties();
		props.load(in);
		return new PropertiesReader(props, false);
	}

	protected String getProperty(String name, boolean required) throws Exception {
		String value = null;
		if(this.externalReader != null) {
			value = this.externalReader.getValue_convertEnvProperties(name);
		}
		
		if(value != null && !value.trim().equals("")) {
			this.log.debug("Leggo la property ["+name+"] dal file di configurazione esterno: valore trovato ["+value+"]");
			return value.trim();			
		} else {
			value = this.reader.getValue_convertEnvProperties(name);
			if(value != null && !value.trim().equals("")) {
				this.log.debug("Leggo la property ["+name+"] dal file di configurazione interno: valore trovato ["+value+"]");
				return value.trim();			
			} else {
				if(required){
					this.log.error("Property ["+name+"] obbligatoria non trovata");
					throw new Exception("Property ["+name+"] obbligatoria non trovata");
				} else{
					this.log.debug("Property ["+name+"] non trovata");
					return null;
				}
					
			}
		}
		
	}

	protected Boolean getBooleanProperty(String name,boolean required) throws Exception{
		String prop = this.getProperty(name, required);
		if(prop != null)
			return Boolean.parseBoolean(prop);
		return null;
	}

	protected URL getURLProperty(String name,boolean required) throws Exception{
		String prop = this.getProperty(name, required);
		if(prop != null)
			return new URL(prop);
		return null;
	}

	protected Properties readProperties(String name) throws UtilsException {
		return this.reader.readProperties_convertEnvProperties(name);
	}

	public PropertiesReader getReader() {
		return reader;
	}

	public PropertiesReader getExternalReader() {
		return externalReader;
	}

}
