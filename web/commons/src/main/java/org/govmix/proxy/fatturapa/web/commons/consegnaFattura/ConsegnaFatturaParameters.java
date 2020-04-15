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
package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.Date;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.SottodominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;

public class ConsegnaFatturaParameters {

	private String formatoFatturaPA;
	private FormatoArchivioInvioFatturaType formatoArchivioInvioFattura;
	private Long identificativoSdI;
	
	private byte[] raw;
	private byte[] xml;

	private String nomeFile;
	private String messageId;
	private String codiceDestinatario;

	private boolean fatturazioneAttiva;
	private StatoElaborazioneType stato;
	
	private DominioType dominio;
	private SottodominioType sottodominio;
	private String nodoCodicePagamento;
	private String prefissoCodicePagamento;

	private String protocollo;
	private String idEgov;
	private Date dataRicezione;
	private Logger log;
	public String getFormatoFatturaPA() {
		return formatoFatturaPA;
	}

	public void setFormatoFatturaPA(String formatoFatturaPA) {
		this.formatoFatturaPA = formatoFatturaPA;
	}

	public Long getIdentificativoSdI() {
		return this.identificativoSdI;
	}

	public void setIdentificativoSdI(Long identificativoSdI) {
		this.identificativoSdI = identificativoSdI;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getCodiceDestinatario() {
		return this.codiceDestinatario;
	}

	public void setCodiceDestinatario(String codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public Date getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}


	public void validate() throws Exception {
		this.validate(false);
	}
	
	public void validate(boolean isLotto) throws Exception {
		if(this.formatoFatturaPA == null){
			throw new Exception("formatoFatturaPA non puo' essere null");
		}
		if(this.identificativoSdI == null){
			throw new Exception("identificativoSdI non puo' essere null");
		}

		if(this.nomeFile == null){
			throw new Exception("nomeFile non puo' essere null");
		}
		if(this.messageId == null){
			throw new Exception("messageId non puo' essere null");
		}
		if(this.codiceDestinatario == null){
			throw new Exception("codiceDestinatario non puo' essere null");
		}
	}
	
	@Override
	public String toString() {
		
		String str =  "formatoFatturaPA ["+this.formatoFatturaPA+"]\n"
				+ "identificativoSdI ["+this.identificativoSdI+"]\n"
				+ "nomeFile ["+this.nomeFile+"]\n"
				+ "messageId ["+this.messageId+"]\n"
				+ "codiceDestinatario ["+this.codiceDestinatario+"]\n";
		
		return str;
	}

	public FormatoArchivioInvioFatturaType getFormatoArchivioInvioFattura() {
		return this.formatoArchivioInvioFattura;
	}

	public void setFormatoArchivioInvioFattura(
			FormatoArchivioInvioFatturaType formatoArchivioInvioFattura) {
		this.formatoArchivioInvioFattura = formatoArchivioInvioFattura;
	}

	public byte[] getXml() {
		return this.xml;
	}

	public void setXml(byte[] xml) {
		this.xml = xml;
	}

	public boolean isFatturazioneAttiva() {
		return fatturazioneAttiva;
	}

	public void setFatturazioneAttiva(boolean fatturazioneAttiva) {
		this.fatturazioneAttiva = fatturazioneAttiva;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getIdEgov() {
		return idEgov;
	}

	public void setIdEgov(String idEgov) {
		this.idEgov = idEgov;
	}

	public DominioType getDominio() {
		return dominio;
	}

	public void setDominio(DominioType dominio) {
		this.dominio = dominio;
	}

	public byte[] getRaw() {
		return raw;
	}

	public void setRaw(byte[] raw) {
		this.raw = raw;
	}

	public StatoElaborazioneType getStato() {
		return stato;
	}

	public void setStato(StatoElaborazioneType stato) {
		this.stato = stato;
	}

	public SottodominioType getSottodominio() {
		return sottodominio;
	}

	public void setSottodominio(SottodominioType sottodominio) {
		this.sottodominio = sottodominio;
	}

	public String getPrefissoCodicePagamento() {
		return prefissoCodicePagamento;
	}

	public void setPrefissoCodicePagamento(String prefissoCodicePagamento) {
		this.prefissoCodicePagamento = prefissoCodicePagamento;
	}

	public String getNodoCodicePagamento() {
		return nodoCodicePagamento;
	}

	public void setNodoCodicePagamento(String nodoCodicePagamento) {
		this.nodoCodicePagamento = nodoCodicePagamento;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
