/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.timers.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openspcoop2.utils.UtilsException;


public class BatchProperties extends org.govmix.proxy.fatturapa.web.commons.utils.timers.BatchProperties {

	public BatchProperties(String resourceName) throws Exception {
		super(resourceName);
		this.mailHost = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche.mail.host", true);
		this.mailPort = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche.mail.port", true));
		this.mailUsername = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche.mail.username", false);
		this.mailPassword = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche.mail.password", false);
		this.mailFrom = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche.mail.from", true);
		this.mailTemplateOggettoPath = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche.mail.templateOggettoPath", true);
		this.mailTemplateMessaggioPath = this.getProperty("org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche.mail.templateMessaggioPath", true);
		
		this.operazioni = new HashMap<String, Map<String,String>>();
		this.operazioni.put("it", this.readOps("it"));
		this.operazioni.put("de", this.readOps("de"));

	}
	
	private Map<String,String> readOps(String lang) throws UtilsException {
		String prefix = "org.govmix.proxy.fatturapa.web.api.timer.spedizioneNotifiche."+lang+".operazioni.";
		Properties props = this.readProperties(prefix);
		HashMap<String, String> operazioni = new HashMap<String, String>();
		for(Object propOBJ: props.keySet()) {
			String prop = (String) propOBJ;
			String key = prop.replace(prefix, "");
			operazioni.put(key,props.getProperty(prop));
		}
		return operazioni;
	}

	private static BatchProperties batchProperties;
	
	private String mailHost;
	private int mailPort;
	private String mailUsername;
	private String mailPassword;

	private String mailFrom;
	private String mailTemplateOggettoPath;
	private String mailTemplateMessaggioPath;
	private Map<String, Map<String, String>> operazioni; 


	public String getMailTemplateOggettoPath() {
		return mailTemplateOggettoPath;
	}

	public String getMailTemplateMessaggioPath() {
		return mailTemplateMessaggioPath;
	}

	public String getMailHost() {
		return mailHost;
	}

	public int getMailPort() {
		return mailPort;
	}

	public String getMailUsername() {
		return mailUsername;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public Map<String, Map<String, String>> getOperazioni() {
		return operazioni;
	}

	public static BatchProperties getInstance() throws Exception {
		if(batchProperties == null) {
			batchProperties = new BatchProperties("/batch.properties");
		}
		
		return batchProperties;
	}
	
	
}
