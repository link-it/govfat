package org.govmix.fatturapa.parer.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.govmix.proxy.pcc.fatture.utils.AbstractProperties;


public class ConservazioneProperties extends AbstractProperties{

	private String urlParER;
	private String username;
	private String password;
	
	private String versioneServizio;
	
	private String ambienteVersatore;
	
	private String userIDVersatore;
	
	private static final String PATH = "/conservazione.properties";
	
	/* ********  F I E L D S  P R I V A T I  ******** */

	/** Reader delle proprieta' impostate nel file 'server.properties' */
	private Properties reader;

	private static ConservazioneProperties instance;
	
	public static ConservazioneProperties getInstance() throws Exception {
		if(instance == null) {
			instance = new ConservazioneProperties(PATH);
		}
		return instance;
	}
	
	private ConservazioneProperties(String path) throws Exception {
		super(path);
		
		Properties propertiesReader = new Properties();
		java.io.InputStream properties = null;
		try{  
			properties = CommonsProperties.class.getResourceAsStream(path);
			if(properties==null){
				throw new Exception("Properties "+path+" not found");
			}
			propertiesReader.load(properties);
			properties.close();
		}catch(java.io.IOException e) {
			log.error("Riscontrato errore durante la lettura del file '"+path+"': "+e.getMessage(),e);
			try{
				if(properties!=null)
					properties.close();
			}catch(Exception er){}
			throw e;
		}	

		this.reader = propertiesReader;
		
		this.versioneServizio = getProperty("org.govmix.proxy.fatturapa.conservazione.versioneServizio",true);
		this.ambienteVersatore = getProperty("org.govmix.proxy.fatturapa.conservazione.ambienteVersatore",true);

		this.userIDVersatore = getProperty( "org.govmix.proxy.fatturapa.conservazione.userIDVersatore",true);
		this.urlParER = getProperty( "org.govmix.proxy.fatturapa.conservazione.urlParER",true);
		
		this.username = getProperty( "org.govmix.proxy.fatturapa.conservazione.username",true);
		this.password = getProperty( "org.govmix.proxy.fatturapa.conservazione.password",true);
		
	}
	
//	
//	private String getProperty(Properties props, String name) throws Exception {
//		return getProperty(props, name, true);
//	}
//	
//	private String getProperty(Properties props, String name, boolean mandatory) throws Exception {
//		if(props.containsKey(name)){
//			return props.getProperty(name).trim();
//		} else {
//			if(mandatory)
//				throw new Exception("Property ["+name+"] non trovata");
//		}
//		return null;
//	}
	
	/* ********  P R O P E R T I E S  ******** */

	public Enumeration<?> keys(){
		return this.reader.propertyNames();
	}
	
	public String getVersioneServizio() {
		return versioneServizio;
	}
	public String getAmbienteVersatore() {
		return ambienteVersatore;
	}
	public String getUserIDVersatore() {
		return userIDVersatore;
	}
	public String getUrlParER() {
		return urlParER;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
