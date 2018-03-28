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
package org.govmix.proxy.fatturapa.web.commons.sonde;


public class RisultatoSonda {

	public enum STATO {OK, WARN, KO}
	
	private STATO stato;
	private String dettaglio;

	
	public RisultatoSonda() {}
	
	public RisultatoSonda(STATO stato, String dettaglio) {
		this.stato = stato;
		this.dettaglio = dettaglio;
	}
	
	public STATO getStato() {
		return stato;
	}
	public void setStato(STATO stato) {
		this.stato = stato;
	}
	public String getDettaglio() {
		return dettaglio;
	}
	public void setDettaglio(String dettaglio) {
		this.dettaglio = dettaglio;
	}
	
	@Override
	public String toString() {
		String result = "";
		switch(this.stato){
		case OK: result = "0";
			break;
		case WARN: result = "1";
			break;
		case KO: result = "2";
			break;
		}
		
		return result + " " + this.dettaglio;
	}
	
	public static RisultatoSonda parse(String raw) throws Exception {
		if(raw == null)
			throw new Exception("Impossibile costruire il risultato. Stringa nulla");

		if(raw.length() < 1) {
			throw new Exception("Impossibile costruire il risultato. Stringa di dimensione non sufficiente");
		}
		
		Character state = raw.charAt(0);
		STATO stato = null;
		if(state.equals('0')) {
			stato = STATO.OK;
		} else if(state.equals('1')) {
			stato = STATO.WARN;
		} else if(state.equals('2')) {
			stato = STATO.KO;
		} else {
			throw new Exception("Impossibile costruire il risultato. Stringa ["+raw+"] dovrebbe iniziare con un numero tra 0, 1 e 2");
		}

		if(raw.length() >= 2 && !Character.isWhitespace(raw.charAt(1))) {
			throw new Exception("Impossibile costruire il risultato. Il secondo carattere della stringa dovrebbe essere uno spazio");
		}

		RisultatoSonda risultato = new RisultatoSonda();
		risultato.setStato(stato);
		risultato.setDettaglio(raw.substring(2));
		return risultato;
	}
}
