package org.govmix.fatturapa.parer.utils;

import java.io.InputStream;
import java.util.Properties;


public class ConservazioneProperties {

	private String urlParER;
	private String username;
	private String password;
	
	private String versioneServizio;
	
	private String ambienteVersatore;
	
	private String userIDVersatore;
	
	private static final String PATH = "/conservazione.properties";

	private static ConservazioneProperties instance;
	
	public static ConservazioneProperties getInstance() throws Exception {
		if(instance == null) {
			instance = new ConservazioneProperties();
		}
		return instance;
	}
	
	private ConservazioneProperties() throws Exception {
		Properties props = new Properties();
		InputStream is = ConservazioneProperties.class.getResourceAsStream(PATH);
		props.load(is);
		
		this.versioneServizio = getProperty(props, "org.govmix.proxy.fatturapa.conservazione.versioneServizio");
		this.ambienteVersatore = getProperty(props, "org.govmix.proxy.fatturapa.conservazione.ambienteVersatore");

		this.userIDVersatore = getProperty(props, "org.govmix.proxy.fatturapa.conservazione.userIDVersatore");
		this.urlParER = getProperty(props, "org.govmix.proxy.fatturapa.conservazione.urlParER");
		
		this.username = getProperty(props, "org.govmix.proxy.fatturapa.conservazione.username");
		this.password = getProperty(props, "org.govmix.proxy.fatturapa.conservazione.password");
		
	}
	
	
	private String getProperty(Properties props, String name) throws Exception {
		return getProperty(props, name, true);
	}
	
	private String getProperty(Properties props, String name, boolean mandatory) throws Exception {
		if(props.containsKey(name)){
			return props.getProperty(name).trim();
		} else {
			if(mandatory)
				throw new Exception("Property ["+name+"] non trovata");
		}
		return null;
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
