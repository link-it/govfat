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
package org.govmix.proxy.fatturapa.web.api.utils;

import java.io.InputStream;
import java.util.Properties;

public class WebApiProperties {

	/** tipo di autenticazione: managed o user */
	private static String AUTHENTICATION_TYPE_MANAGED = "managed";
	private static String AUTHENTICATION_TYPE_USER = "user";
	
	private static WebApiProperties props;
	/** tipo di server: j2ee o web */
	
	public static WebApiProperties getInstance() throws Exception {
		if(props == null) {
			props = new WebApiProperties();
		}
		
		return props;
		
	}
	private static String getProperty(String name, Properties props, boolean required) throws Exception {
		String value = props.getProperty(name);
		if(value == null) {
			if(required)
				throw new Exception("Property ["+name+"] non trovata");
			else return null;
		}
		
		return value.trim();
	}
	
	private boolean authenticationManaged;
	private boolean validazioneDAOAbilitata;
	private Integer limitGetIdFatture;

	private WebApiProperties() throws Exception {
		initWebApiProperties();
	}
	
	public void reloadProperties() throws Exception {
		initWebApiProperties();
	}
	
	private void initWebApiProperties() throws Exception {
		InputStream is = WebApiProperties.class.getResourceAsStream("/web.api.properties");
		Properties props = new Properties();
		props.load(is);

		this.limitGetIdFatture = Integer.valueOf(getProperty("org.govmix.proxy.fatturapa.web.api.pull.idfattura.limit", props, true));

		String authenticationType = getProperty("org.govmix.proxy.fatturapa.web.api.authentication", props, true);
		
		if(WebApiProperties.AUTHENTICATION_TYPE_MANAGED.equalsIgnoreCase(authenticationType)){
			this.authenticationManaged = true;
		}else if(WebApiProperties.AUTHENTICATION_TYPE_USER.equalsIgnoreCase(authenticationType)){
			this.authenticationManaged = false;
		}else{
			throw new Exception("Valore ["+authenticationType+"] non conosciuto per la properieta' [org.govmix.proxy.fatturapa.web.api.authentication]");
		}

		String validazioneDAOAbilitataString = getProperty("org.govmix.proxy.fatturapa.web.api.validazioneDAOAbilitata", props, false);
		
		if(validazioneDAOAbilitataString != null) {
			this.validazioneDAOAbilitata = Boolean.parseBoolean(validazioneDAOAbilitataString);
		} else {
			this.validazioneDAOAbilitata = true;
		}

	}
	public Integer getLimitGetIdFatture() {
		return this.limitGetIdFatture;
	}
	
	public boolean isAuthenticationManaged() {
		return this.authenticationManaged;
	}

	public boolean isValidazioneDAOAbilitata() {
		return this.validazioneDAOAbilitata;
	}
	
}
