/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
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

	private String proxyPccSondaUser;
	private String timersProxyPccSondaUser;
	private String proxyFatturaPASondaUser;
	private String timersProxyFatturaPASondaUser;

	private String proxyPccSondaPassword;
	private String timersProxyPccSondaPassword;
	private String proxyFatturaPASondaPassword;
	private String timersProxyFatturaPASondaPassword;

	private String proxyPccWsFattureUrl;
	private String proxyPccWsFattureUsername;
	private String proxyPccWsFatturePassword;
	
	private int fatturaAttivaCaricamentoMaxNumeroFile;
	private String fatturaAttivaCaricamentoTipologieFileAccettati;
	private Long fatturaAttivaCaricamentoDimensioneMassimaFile;
	
	private Integer numeroGiorniAttesaPerInvioConservazione = null;
	
	private TreeMap<String, String> codiciErrorePCC = null;
	
	
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

		this.proxyPccSondaUser = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPCC.sonda.user", false);
		this.timersProxyPccSondaUser = this.getProperty("org.govmix.proxy.fatturapa.web.console.timersProxyPCC.sonda.user", false);
		this.proxyFatturaPASondaUser = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyFatturaPA.sonda.user", false);
		this.timersProxyFatturaPASondaUser = this.getProperty("org.govmix.proxy.fatturapa.web.console.timersProxyFatturaPA.sonda.user", false);

		this.proxyPccSondaPassword = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPCC.sonda.password", false);
		this.timersProxyPccSondaPassword= this.getProperty("org.govmix.proxy.fatturapa.web.console.timersProxyPCC.sonda.password", false);
		this.proxyFatturaPASondaPassword = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyFatturaPA.sonda.password", false);
		this.timersProxyFatturaPASondaPassword= this.getProperty("org.govmix.proxy.fatturapa.web.console.timersProxyFatturaPA.sonda.password", false);

		this.proxyPccWsFattureUrl = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPcc.wsFatture.url", true);
		this.proxyPccWsFattureUsername = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPcc.wsFatture.username", true);
		this.proxyPccWsFatturePassword = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPcc.wsFatture.password", true);

		this.fatturaAttivaCaricamentoMaxNumeroFile = Integer.parseInt(this.getProperty("org.govmix.proxy.fatturapa.web.console.fatturazioneAttiva.caricamento.maxNumeroFile", true));
		this.fatturaAttivaCaricamentoTipologieFileAccettati = this.getProperty("org.govmix.proxy.fatturapa.web.console.fatturazioneAttiva.caricamento.tipologieFileAccettate", true);
		
		String dimMax = this.getProperty("org.govmix.proxy.fatturapa.web.console.fatturazioneAttiva.caricamento.dimensioneMaxFile", true);
		long lTmp = Long.parseLong(dimMax);
		this.fatturaAttivaCaricamentoDimensioneMassimaFile = lTmp > 0 ? lTmp : null;

		String codErrPCC = this.getProperty("org.govmix.proxy.fatturapa.web.console.proxyPCC.codiciErrore", false);
		this.codiciErrorePCC = new TreeMap<String, String>();
		if(StringUtils.isNotEmpty(codErrPCC)){
			String[] codiciSplit = codErrPCC.split(",");
			if(codiciSplit != null && codiciSplit.length > 0){
				for (String codice : codiciSplit) {
					this.codiciErrorePCC.put(codice.trim(), codice.trim());
				}
			}
		}
		
		
		String numeroGiorniAttesaPerInvioConservazioneS = this.getProperty("org.govmix.proxy.fatturapa.conservazione.giorniIntervallo", true);
		if(numeroGiorniAttesaPerInvioConservazioneS != null) {
			this.numeroGiorniAttesaPerInvioConservazione = Integer.parseInt(numeroGiorniAttesaPerInvioConservazioneS);
		}
		
	}

	/* ********  P R O P E R T I E S  ******** */

	public String getProxyPccSondaUser() {
		return proxyPccSondaUser;
	}

	public String getTimersProxyPccSondaUser() {
		return timersProxyPccSondaUser;
	}

	public String getProxyFatturaPASondaUser() {
		return proxyFatturaPASondaUser;
	}

	public String getTimersProxyFatturaPASondaUser() {
		return timersProxyFatturaPASondaUser;
	}

	public String getProxyPccSondaPassword() {
		return proxyPccSondaPassword;
	}

	public String getTimersProxyPccSondaPassword() {
		return timersProxyPccSondaPassword;
	}

	public String getProxyFatturaPASondaPassword() {
		return proxyFatturaPASondaPassword;
	}

	public String getTimersProxyFatturaPASondaPassword() {
		return timersProxyFatturaPASondaPassword;
	}

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

	public TreeMap<String, String> getCodiciErrorePCC() {
		return codiciErrorePCC;
	}

	public int getFatturaAttivaCaricamentoMaxNumeroFile() {
		return fatturaAttivaCaricamentoMaxNumeroFile;
	}

	public String getFatturaAttivaCaricamentoTipologieFileAccettati() {
		return fatturaAttivaCaricamentoTipologieFileAccettati;
	}

	public Long getFatturaAttivaCaricamentoDimensioneMassimaFile() {
		return fatturaAttivaCaricamentoDimensioneMassimaFile;
	}

	public Integer getNumeroGiorniAttesaPerInvioConservazione() {
		return numeroGiorniAttesaPerInvioConservazione;
	}
	
}
