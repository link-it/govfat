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
package org.govmix.proxy.fatturapa.web.commons.sonde;

import java.util.Date;

public class ParametriSonda {

	public enum STATO {OK,KO}
	private Date dataInizioOp;
	private Date dataUltimaOp;
	private long tempoWarning;
	private long tempoError;
	private STATO stato;
	private String dettaglio;
	
	public Date getDataInizioOp() {
		return dataInizioOp;
	}
	public void setDataInizioOp(Date dataInizioOp) {
		this.dataInizioOp = dataInizioOp;
	}
	public Date getDataUltimaOp() {
		return dataUltimaOp;
	}
	public void setDataUltimaOp(Date dataUltimaOp) {
		this.dataUltimaOp = dataUltimaOp;
	}
	public long getTempoError() {
		return tempoError;
	}
	public void setTempoError(long tempoError) {
		this.tempoError = tempoError;
	}
	public long getTempoWarning() {
		return tempoWarning;
	}
	public void setTempoWarning(long tempoWarning) {
		this.tempoWarning = tempoWarning;
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
}
