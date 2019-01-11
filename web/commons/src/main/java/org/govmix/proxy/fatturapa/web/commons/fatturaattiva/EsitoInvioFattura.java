/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.fatturaattiva;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsitoInvioFattura {

	public enum ESITO {OK, KO}
	private ESITO esito;
	private Map<String, List<String>> metadati;
	
	public ESITO getEsito() {
		return esito;
	}
	public void setEsito(ESITO esito) {
		this.esito = esito;
	}
	public Map<String, List<String>> getMetadati() {
		if(this.metadati == null) this.metadati = new HashMap<String, List<String>>();
		return metadati;
	}
	public void setMetadati(Map<String, List<String>> metadati) {
		this.metadati = metadati;
	}
	
	public String getMetadato(String nome) {
		for(String ks: this.getMetadati().keySet()) {
			if(nome.equalsIgnoreCase(ks))
				return this.getMetadati().get(ks).get(0);
		}
		return null;
	} 
}
