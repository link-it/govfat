/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.util;

import org.govmix.proxy.pcc.fatture.utils.AbstractProperties;


/**
 * ConsoleProperties classe per la gestione delle properties dell'applicazione.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class ConsoleProperties extends AbstractProperties {

	private String urlModificaPassword;
	private boolean visualizzaUrlModificaPassword;
	private boolean utilizzaProfiloUtente;

	private String protocollo;
	private String sistemaRichiedente;

	private String proxyPccSondaUrl;
	private String timersProxyPccSondaUrl;
	private String proxyFatturaPASondaUrl;
	private String timersProxyFatturaPASondaUrl;

	private String proxyPccWsFattureUrl;
	private String proxyPccWsFattureUsername;
	private String proxyPccWsFatturePassword;
	
	
	private static final String propertiesPath = "/fatturapa.properties";
	/** Copia Statica */
	private static ConsoleProperties consoleProperties;

	public static synchronized void initialize(org.apache.log4j.Logger log) throws Exception{

		if(ConsoleProperties.consoleProperties==null)
			ConsoleProperties.consoleProperties = new ConsoleProperties(log, propertiesPath);	
	}

	public static ConsoleProperties getInstance(org.apache.log4j.Logger log) throws Exception{

		if(ConsoleProperties.consoleProperties==null)
			initialize(log);

		return ConsoleProperties.consoleProperties;
	}

	/* ********  C O S T R U T T O R E  ******** */

	/**
	 * Viene chiamato in causa per istanziare il properties reader
	 *
	 * 
	 */
	public ConsoleProperties(org.apache.log4j.Logger log, String path) throws Exception{

		super(log, path);
		
		this.protocollo = this.getProperty("org.govmix.proxy.fatturapa.web.console.protocollo", true);
		this.sistemaRichiedente = this.getProperty("org.govmix.proxy.fatturapa.web.console.sistemaRichiedente", true);
		this.urlModificaPassword = this.getProperty("org.govmix.proxy.fatturapa.web.console.modificaPassword.url", false);
		this.visualizzaUrlModificaPassword = this.getBooleanProperty("org.govmix.proxy.fatturapa.web.console.modificaPassword.visualizzaLink", false);
		this.utilizzaProfiloUtente = this.getBooleanProperty("org.govmix.proxy.fatturapa.web.console.utenti.utilizzaProfilo", false);
		

		this.proxyPccSondaUrl = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPCC.sonda.url", true);
		this.timersProxyPccSondaUrl = this.getProperty("org.govmix.proxy.fatturapa.web.console.timersProxyPCC.sonda.url", true);
		this.proxyFatturaPASondaUrl = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyFatturaPA.sonda.url", true);
		this.timersProxyFatturaPASondaUrl = this.getProperty("org.govmix.proxy.fatturapa.web.console.timersProxyFatturaPA.sonda.url", true);

		this.proxyPccWsFattureUrl = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPcc.wsFatture.url", true);
		this.proxyPccWsFattureUsername = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPcc.wsFatture.username", true);
		this.proxyPccWsFatturePassword = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPcc.wsFatture.password", true);

	}

	/* ********  P R O P E R T I E S  ******** */

	public String getUrlModificaPassword() {
		return urlModificaPassword;
	}

	public void setUrlModificaPassword(String urlModificaPassword) {
		this.urlModificaPassword = urlModificaPassword;
	}

	public boolean isVisualizzaUrlModificaPassword() {
		return visualizzaUrlModificaPassword;
	}

	public void setVisualizzaUrlModificaPassword(boolean visualizzaUrlModificaPassword) {
		this.visualizzaUrlModificaPassword = visualizzaUrlModificaPassword;
	}

	public boolean isUtilizzaProfiloUtente() {
		return utilizzaProfiloUtente;
	}

	public void setUtilizzaProfiloUtente(boolean utilizzaProfiloUtente) {
		this.utilizzaProfiloUtente = utilizzaProfiloUtente;
	}

	public String getProtocollo(){
		return  this.protocollo;
	}

	public String getSistemaRichiedente() {
		return this.sistemaRichiedente;
	}

	public String getProxyPccWsFattureUrl() {
		return proxyPccWsFattureUrl;
	}

	public String getProxyPccWsFattureUsername() {
		return proxyPccWsFattureUsername;
	}

	public String getProxyPccWsFatturePassword() {
		return proxyPccWsFatturePassword;
	}

	public String getProxyPccSondaUrl() {
		return proxyPccSondaUrl;
	}

	public String getTimersProxyPccSondaUrl() {
		return timersProxyPccSondaUrl;
	}

	public String getProxyFatturaPASondaUrl() {
		return proxyFatturaPASondaUrl;
	}

	public String getTimersProxyFatturaPASondaUrl() {
		return timersProxyFatturaPASondaUrl;
	}
	
}
