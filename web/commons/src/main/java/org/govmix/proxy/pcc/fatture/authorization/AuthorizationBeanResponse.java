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
package org.govmix.proxy.pcc.fatture.authorization;

import org.govmix.proxy.fatturapa.orm.IdFattura;


public class AuthorizationBeanResponse {

	private String idPccAmministrazione;
	private String cfTrasmittente;
	private String sistemaRichiedente;
	private String utenteRichiedente;
	private String codiceDipartimento;
	private Long idFattura;
	private IdFattura idLogicoFattura;

	public String getCfTrasmittente() {
		return cfTrasmittente;
	}

	public void setCfTrasmittente(String cfTrasmittente) {
		this.cfTrasmittente = cfTrasmittente;
	}

	public String getSistemaRichiedente() {
		return sistemaRichiedente;
	}

	public void setSistemaRichiedente(String sistemaRichiedente) {
		this.sistemaRichiedente = sistemaRichiedente;
	}

	public Long getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(Long idFattura) {
		this.idFattura = idFattura;
	}

	public IdFattura getIdLogicoFattura() {
		return idLogicoFattura;
	}

	public void setIdLogicoFattura(IdFattura idLogicoFattura) {
		this.idLogicoFattura = idLogicoFattura;
	}

	public String getIdPccAmministrazione() {
		return idPccAmministrazione;
	}

	public void setIdPccAmministrazione(String idPccAmministrazione) {
		this.idPccAmministrazione = idPccAmministrazione;
	}

	public String getCodiceDipartimento() {
		return codiceDipartimento;
	}

	public void setCodiceDipartimento(String codiceDipartimento) {
		this.codiceDipartimento = codiceDipartimento;
	}

	public String getUtenteRichiedente() {
		return utenteRichiedente;
	}

	public void setUtenteRichiedente(String utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}
}
