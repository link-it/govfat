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
package org.govmix.proxy.pcc.fatture.authorization;

import org.govmix.pcc.fatture.IdentificazioneGeneraleTipo;
import org.govmix.pcc.fatture.IdentificazioneSDITipo;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;

public class AuthorizationBeanRequest {

	private String principal;
	private String utenteRichiedente;
	private NomePccOperazioneType operazione;
	
	private IdentificazioneSDITipo identificativoSdi;
	private String identificativoPcc;
	private IdentificazioneGeneraleTipo identificativoGenerale;
	private IdFattura idLogicoFattura;
	private Long idFattura;
	private String codiceDipartimento;
	
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public IdentificazioneSDITipo getIdentificativoSdi() {
		return identificativoSdi;
	}
	public void setIdentificativoSdi(IdentificazioneSDITipo identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}
	public String getIdentificativoPcc() {
		return identificativoPcc;
	}
	public void setIdentificativoPcc(String identificativoPcc) {
		this.identificativoPcc = identificativoPcc;
	}
	public IdentificazioneGeneraleTipo getIdentificativoGenerale() {
		return identificativoGenerale;
	}
	public void setIdentificativoGenerale(IdentificazioneGeneraleTipo identificativoGenerale) {
		this.identificativoGenerale = identificativoGenerale;
	}
	public String getCodiceDipartimento() {
		return codiceDipartimento;
	}
	public void setCodiceDipartimento(String codiceDipartimento) {
		this.codiceDipartimento = codiceDipartimento;
	}
	public IdFattura getIdLogicoFattura() {
		return idLogicoFattura;
	}
	public void setIdLogicoFattura(IdFattura idLogicoFattura) {
		this.idLogicoFattura = idLogicoFattura;
	}
	public Long getIdFattura() {
		return idFattura;
	}
	public void setIdFattura(Long idFattura) {
		this.idFattura = idFattura;
	}
	public NomePccOperazioneType getOperazione() {
		return operazione;
	}
	public void setOperazione(NomePccOperazioneType operazione) {
		this.operazione = operazione;
	}
	public String getUtenteRichiedente() {
		return utenteRichiedente;
	}
	public void setUtenteRichiedente(String utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}
}
