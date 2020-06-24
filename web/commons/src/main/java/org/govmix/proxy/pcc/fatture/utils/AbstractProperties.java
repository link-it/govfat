package org.govmix.proxy.pcc.fatture.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.resources.PropertiesReader;

public abstract class AbstractProperties {

	private List<PropertiesReader> lstReaders;
	private static final String SYS_PROP_NAME = "org.govmix.fatturapa.externalProperties";
	protected Logger log;
	

	public AbstractProperties(List<PropertiesReader> lstReaders) throws RuntimeException {
		this.lstReaders = lstReaders;
		this.log = LoggerManager.getBatchStartupLogger();
	}
	
	public AbstractProperties(String path) throws RuntimeException {
		this(LoggerManager.getBatchStartupLogger(), path);
	}
	
	public AbstractProperties(Logger log, String path) throws RuntimeException {
		
		this.log = log;
		this.lstReaders = new ArrayList<PropertiesReader>();
		String externalResourceFile = null;
		try {
			externalResourceFile = System.getProperty(SYS_PROP_NAME);

			if(externalResourceFile != null) {
				this.log.debug("File esterno: " +externalResourceFile);
				InputStream isExternal = new FileInputStream(new File(externalResourceFile));
				this.lstReaders.add(this.getPropertiesReader(isExternal, externalResourceFile));
			} else {
				this.log.debug("System property ["+SYS_PROP_NAME+"] non specificata. Nessun file esterno verra' letto");
			}
			
		}catch(Exception e) {
			this.log.error("Errore durante la lettura delle properties dal path ["+externalResourceFile+"]:" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		try {
			if(path == null)
				throw new Exception("Path per localizzare le properties non trovato");
			
			InputStream is = AbstractProperties.class.getResourceAsStream(path);
			this.lstReaders.add(this.getPropertiesReader(is, path));
		}catch(Exception e) {
			this.log.error("Errore durante la lettura delle properties dal path ["+path+"]:" + e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}
	
	private PropertiesReader getPropertiesReader(InputStream is, String path) throws Exception {
		if(is == null) {
			throw new Exception("file ["+path+"] non trovato");
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		Properties props = new Properties();
		props.load(in);
		return new PropertiesReader(props, false);
	}

	protected String getProperty(String name, boolean required) throws Exception {
		
		for(int i =0; i < this.lstReaders.size(); i++) {
			String conf = i == 0 ? "esterno" : "interno";
			String value = this.lstReaders.get(i).getValue_convertEnvProperties(name);
			if(value != null && !value.trim().equals("")) {
				this.log.debug("Leggo la property ["+name+"] dal file di configurazione "+conf+": valore trovato ["+value+"]");
				return value.trim();
			}
		}

		if(required){
			this.log.error("Property ["+name+"] obbligatoria non trovata");
			throw new Exception("Property ["+name+"] obbligatoria non trovata");
		} else{
			this.log.debug("Property ["+name+"] non trovata");
			return null;
		}
		
	}

	protected Boolean getBooleanProperty(String name,boolean required) throws Exception{
		String prop = this.getProperty(name, required);
		if(prop != null)
			return Boolean.parseBoolean(prop);
		return null;
	}

	protected Integer getIntegerProperty(String name,boolean required) throws Exception{
		String prop = this.getProperty(name, required);
		if(prop != null)
			return Integer.parseInt(prop);
		return null;
	}

	protected URL getURLProperty(String name,boolean required) throws Exception{
		String prop = this.getProperty(name, required);
		if(prop != null)
			return new URL(prop);
		return null;
	}

	protected Properties readProperties(String name) throws UtilsException {
		Properties prop = new Properties();
		
		for(int i =this.lstReaders.size()-1; i >=0; i--) {
			prop.putAll(this.lstReaders.get(i).readProperties(name));
		}
		
		return prop;
	}

	public List<PropertiesReader> getLstReaders() {
		return this.lstReaders;
	}


}
