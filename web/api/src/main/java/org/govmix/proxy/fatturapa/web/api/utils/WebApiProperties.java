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
package org.govmix.proxy.fatturapa.web.api.utils;

import java.net.URL;

import org.govmix.proxy.pcc.fatture.utils.AbstractProperties;

public class WebApiProperties extends AbstractProperties{

	private static WebApiProperties props;
	
	public static WebApiProperties getInstance() throws Exception {
		if(props == null) {
			props = new WebApiProperties("/web.api.properties");
		}
		
		return props;
		
	}
	
	private boolean validazioneDAOAbilitata;
	private Integer limitGetIdFatture;

	private URL invioFatturaURL;
	private String invioFatturaUsername;
	private String invioFatturaPassword;
	private String invioFatturaIdCodice;
	private String invioFatturaIdPaese;


	public String getInvioFatturaIdCodice() {
		return invioFatturaIdCodice;
	}
	public String getInvioFatturaIdPaese() {
		return invioFatturaIdPaese;
	}
	private WebApiProperties(String path) throws Exception {
		super(path);
		initWebApiProperties(path);
	}
	
	public void reloadProperties(String path) throws Exception {
		initWebApiProperties(path);
	}
	
	private void initWebApiProperties(String path) throws Exception {

		this.limitGetIdFatture = Integer.valueOf(this.getProperty("org.govmix.proxy.fatturapa.web.api.pull.idfattura.limit", true));

		String validazioneDAOAbilitataString = this.getProperty("org.govmix.proxy.fatturapa.web.api.validazioneDAOAbilitata", false);
		
		if(validazioneDAOAbilitataString != null) {
			this.validazioneDAOAbilitata = Boolean.parseBoolean(validazioneDAOAbilitataString);
		} else {
			this.validazioneDAOAbilitata = true;
		}
		this.invioFatturaURL = this.getURLProperty("org.govmix.proxy.fatturapa.web.api.trasmittente.invioFattura.url", false);
		this.invioFatturaUsername = this.getProperty("org.govmix.proxy.fatturapa.web.api.trasmittente.invioFattura.username", false);
		this.invioFatturaPassword = this.getProperty("org.govmix.proxy.fatturapa.web.api.trasmittente.invioFattura.password", false);
		this.invioFatturaIdCodice = this.getProperty("org.govmix.proxy.fatturapa.web.api.trasmittente.invioFattura.idCodice", false);
		this.invioFatturaIdPaese = this.getProperty("org.govmix.proxy.fatturapa.web.api.trasmittente.invioFattura.idPaese", false);

	}
	public Integer getLimitGetIdFatture() {
		return this.limitGetIdFatture;
	}

	public boolean isValidazioneDAOAbilitata() {
		return this.validazioneDAOAbilitata;
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
	
}
