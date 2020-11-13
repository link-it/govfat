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
package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatiDocumentiCorrelatiType { 

	private List<Integer> riferimentoNumeroLinea = new ArrayList<Integer>();
	private String idDocumento;
	private Date data;
	private String numItem;
	private String codiceCommessaConvenzione;
	private String codiceCUP;
	private String codiceCIG;

	public List<Integer> getRiferimentoNumeroLinea() {
		return this.riferimentoNumeroLinea;
	}
	public void setRiferimentoNumeroLinea(List<Integer> riferimentoNumeroLinea) {
		this.riferimentoNumeroLinea = riferimentoNumeroLinea;
	}
	public String getIdDocumento() {
		return this.idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Date getData() {
		return this.data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getNumItem() {
		return this.numItem;
	}
	public void setNumItem(String numItem) {
		this.numItem = numItem;
	}
	public String getCodiceCommessaConvenzione() {
		return this.codiceCommessaConvenzione;
	}
	public void setCodiceCommessaConvenzione(String codiceCommessaConvenzione) {
		this.codiceCommessaConvenzione = codiceCommessaConvenzione;
	}
	public String getCodiceCUP() {
		return this.codiceCUP;
	}
	public void setCodiceCUP(String codiceCUP) {
		this.codiceCUP = codiceCUP;
	}
	public String getCodiceCIG() {
		return this.codiceCIG;
	}
	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}
}
