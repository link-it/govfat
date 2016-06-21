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
package org.govmix.proxy.fatturapa.web.timers.policies;

public class PolicyRispedizioneRetry implements IPolicyRispedizione {

	private int maxTentativiRispedizione;
	private int fattore;
	private static final int SECONDS = 1000;
	
	@Override
	public long getOffsetRispedizione(PolicyRispedizioneParameters params) {
		return this.getFattore()*params.getTentativi()*SECONDS;
	}

	public int getFattore() {
		return fattore;
	}

	public void setFattore(int fattore) {
		this.fattore = fattore;
	}

	public int getMaxTentativiRispedizione() {
		return maxTentativiRispedizione;
	}

	public void setMaxTentativiRispedizione(int maxTentativiRispedizione) {
		this.maxTentativiRispedizione = maxTentativiRispedizione;
	}

	@Override
	public boolean isRispedizioneAbilitata(PolicyRispedizioneParameters params) {
		return params.getTentativi() < this.maxTentativiRispedizione;
	}

}
