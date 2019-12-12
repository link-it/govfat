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
package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.Date;

import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;

public class ConsegnaFatturaParameters {

	private String formatoFatturaPA;
	private FormatoArchivioInvioFatturaType formatoArchivioInvioFattura;
	private Integer posizioneFatturaPA;
	private String identificativoSdI;
	
	private byte[] xml;

	private String nomeFile;
	private String messageId;
	private String codiceDestinatario;

	private Soggetto cedentePrestatore;
	private Soggetto cessionarioCommittente;
	private Soggetto terzoIntermediarioOSoggettoEmittente;

	private boolean fatturazioneAttiva;
	private String protocollo;
	private Date dataRicezione;
	private boolean pagoPA;
	
	public String getFormatoFatturaPA() {
		return formatoFatturaPA;
	}

	public void setFormatoFatturaPA(String formatoFatturaPA) {
		this.formatoFatturaPA = formatoFatturaPA;
	}

	public String getIdentificativoSdI() {
		return this.identificativoSdI;
	}

	public void setIdentificativoSdI(String identificativoSdI) {
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

	public Soggetto getCedentePrestatore() {
		return this.cedentePrestatore;
	}

	public Soggetto getCessionarioCommittente() {
		return this.cessionarioCommittente;
	}

	public Soggetto getTerzoIntermediarioOSoggettoEmittente() {
		return this.terzoIntermediarioOSoggettoEmittente;
	}

	public void setCedentePrestatore(Soggetto cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public void setCessionarioCommittente(Soggetto cessionarioCommittente) {
		this.cessionarioCommittente = cessionarioCommittente;
	}

	public void setTerzoIntermediarioOSoggettoEmittente(
			Soggetto terzoIntermediarioOSoggettoEmittente) {
		this.terzoIntermediarioOSoggettoEmittente = terzoIntermediarioOSoggettoEmittente;
	}

	public Integer getPosizioneFatturaPA() {
		return this.posizioneFatturaPA;
	}
	public void setPosizioneFatturaPA(Integer posizioneFatturaPA) {
		this.posizioneFatturaPA = posizioneFatturaPA;
	}

	public Date getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public class Soggetto {
		private String denominazione;
		private String nome;
		private String cognome;
		private String idPaese;
		private String idCodice;
		private String codiceFiscale;

		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getCognome() {
			return cognome;
		}
		public void setCognome(String cognome) {
			this.cognome = cognome;
		}
		public String getIdPaese() {
			return idPaese;
		}
		public void setIdPaese(String idPaese) {
			this.idPaese = idPaese;
		}
		public String getIdCodice() {
			return idCodice;
		}
		public void setIdCodice(String idCodice) {
			this.idCodice = idCodice;
		}
		public String getDenominazione() {
			return this.denominazione;
		}
		public void setDenominazione(String denominazione) {
			this.denominazione = denominazione;
		}

		public void validate() throws Exception {
			//NOTA: vengono controllati sotto perche' i vincoli variano a seconda del soggetto
//			if(this.idCodice == null) {
//				throw new Exception("idCodice non puo' essere null");
//			}
//
//			if(this.idPaese == null) {
//				throw new Exception("idPaese non puo' essere null");
//			}

			if((this.denominazione == null && (this.nome == null || this.cognome == null)) ||
					(this.denominazione != null && (this.nome != null || this.cognome != null))
					) {
				throw new Exception("Solo una tra denominazione e la coppia nome cognome deve essere valorizzata: trovati nome["+this.nome+"], cognome ["+this.cognome+"] denominazione ["+this.denominazione+"]");
			}
			
		}
		public String getCodiceFiscale() {
			return codiceFiscale;
		}
		public void setCodiceFiscale(String codiceFiscale) {
			this.codiceFiscale = codiceFiscale;
		}
		
		@Override
		public String toString() {
			return "IdCodice ["+this.idCodice+"] "
					+ "IdPaese ["+this.idPaese+"] "
					+ "CodiceFiscale ["+this.codiceFiscale+"] "
					+ "Nome ["+this.nome+"] "
					+ "Cognome ["+this.cognome+"] "
					+ "Denominazione ["+this.denominazione+"] "
					;
			
		}
	}

	public void validate() throws Exception {
		this.validate(false);
	}
	
	public void validate(boolean isLotto) throws Exception {
		if(this.formatoFatturaPA == null){
			throw new Exception("formatoFatturaPA non puo' essere null");
		}
		if(!isLotto) {
			if(this.posizioneFatturaPA == null){
				throw new Exception("posizioneFatturaPA non puo' essere null");
			}
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
		
		if(this.cedentePrestatore == null) {
			throw new Exception("cedentePrestatore non puo' essere null");
		}
		
		try {
			this.cedentePrestatore.validate();
		} catch(Exception e) {
			throw new Exception("cedentePrestatore non valido: " + e.getMessage());
		}
		
		if(this.cedentePrestatore.getIdCodice() == null || this.cedentePrestatore.getIdPaese() == null) {
			throw new Exception("IdCodice ["+this.cedentePrestatore.getIdCodice()+"] e IdPaese ["+this.cedentePrestatore.getIdPaese()+"] non possono essere null per il cedentePrestatore.");
		}
		
		if(this.cessionarioCommittente == null) {
			throw new Exception("cessionarioCommittente non puo' essere null");
		}
		
		try {
			this.cessionarioCommittente.validate();
		} catch(Exception e) {
			throw new Exception("cessionarioCommittente non valido: " + e.getMessage());
		}
		
		if(this.cessionarioCommittente.getIdCodice() == null || this.cessionarioCommittente.getIdPaese() == null) {
			if(this.cessionarioCommittente.getCodiceFiscale() == null) {
				throw new Exception("Dati inconsistenti per il Cessionario Commitente. Devono essere valorizzati IdPaese ["+this.cessionarioCommittente.getIdPaese()+"] e IdCodice ["
			+this.cessionarioCommittente.getIdCodice()+"] oppure CodiceFiscale ["+this.cessionarioCommittente.getCodiceFiscale()+"].");
			}
		}
		
		if(this.terzoIntermediarioOSoggettoEmittente != null) {
			try {
				this.terzoIntermediarioOSoggettoEmittente.validate();
			} catch(Exception e) {
				throw new Exception("terzoIntermediarioOSoggettoEmittente non valido: " + e.getMessage());
			}
			
//			if(this.terzoIntermediarioOSoggettoEmittente.getIdCodice() == null || this.terzoIntermediarioOSoggettoEmittente.getIdPaese() == null) {
//				if(this.terzoIntermediarioOSoggettoEmittente.getCodiceFiscale() == null) {
//						throw new Exception("Dati inconsistenti per il Terzo Intermediario o Soggetto Emittente. Devono essere valorizzati IdPaese ["+this.terzoIntermediarioOSoggettoEmittente.getIdPaese()+"] e IdCodice ["
//					+this.terzoIntermediarioOSoggettoEmittente.getIdCodice()+"] oppure CodiceFiscale ["+this.terzoIntermediarioOSoggettoEmittente.getCodiceFiscale()+"].");
//				}
//			}

		}

	}
	
	@Override
	public String toString() {
		
		String str =  "formatoFatturaPA ["+this.formatoFatturaPA+"]\n"
				+ "posizioneFatturaPA ["+this.posizioneFatturaPA+"]\n"
				+ "identificativoSdI ["+this.identificativoSdI+"]\n"
				+ "nomeFile ["+this.nomeFile+"]\n"
				+ "messageId ["+this.messageId+"]\n"
				+ "codiceDestinatario ["+this.codiceDestinatario+"]\n";
		
		str += "cedentePrestatore ["+this.cedentePrestatore.toString()+"]\n";
		str += "cessionarioCommittente["+this.cessionarioCommittente.toString()+"]\n";
		
		if(this.terzoIntermediarioOSoggettoEmittente != null) {
			str += "terzoIntermediarioOSoggettoEmittente["+this.terzoIntermediarioOSoggettoEmittente.toString()+"]\n";
		}
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

	public boolean isPagoPA() {
		return pagoPA;
	}

	public void setPagoPA(boolean pagoPA) {
		this.pagoPA = pagoPA;
	}

}
