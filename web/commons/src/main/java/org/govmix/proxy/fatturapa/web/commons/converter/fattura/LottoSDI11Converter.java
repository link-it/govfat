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
package org.govmix.proxy.fatturapa.web.commons.converter.fattura;

import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;

import it.gov.fatturapa.sdi.fatturapa.v1_1.AllegatiType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.AnagraficaType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiAnagraficiCedenteType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiAnagraficiCessionarioType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiAnagraficiTerzoIntermediarioType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiGeneraliDocumentoType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiRiepilogoType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaBodyType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.IdFiscaleType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbDeserializer;

public class LottoSDI11Converter extends AbstractLottoSDIConverter<FatturaElettronicaType> {

	private static JaxbDeserializer deserializer;

	static {
		deserializer = new JaxbDeserializer();
	}

	private static FatturaElettronicaType readFatturaElettronicaType(byte[] fatturaBytes) throws InserimentoLottiException {
		try {
			return deserializer.readFatturaElettronicaType(fatturaBytes);
		} catch(Exception e) {
			throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, e.getMessage());
		}
	}


	public LottoSDI11Converter(byte[] fattura, ConsegnaFatturaParameters params) throws InserimentoLottiException {
		super(LottoSDI11Converter.readFatturaElettronicaType(fattura), fattura, params);
	}


	@Override
	public void populateFatturaConDatiSpecifici(FatturaElettronica fatturaElettronica, int index) {
		DatiAnagraficiCedenteType datiAnagraficiCP = this.getLotto().getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici();
		AnagraficaType anagraficaCP = datiAnagraficiCP.getAnagrafica();
		IdFiscaleType idFiscaleCP = datiAnagraficiCP.getIdFiscaleIVA();

		//Cedente prestatore
		if(anagraficaCP.getDenominazione() != null) {
			fatturaElettronica.setCedentePrestatoreDenominazione(anagraficaCP.getDenominazione());
		} else {
			fatturaElettronica.setCedentePrestatoreDenominazione(anagraficaCP.getNome() + " " + anagraficaCP.getCognome());
		}
		
		fatturaElettronica.setCedentePrestatoreCodiceFiscale(idFiscaleCP.getIdCodice());
		fatturaElettronica.setCedentePrestatorePaese(idFiscaleCP.getIdPaese());

		
		
		DatiAnagraficiCessionarioType datiAnagraficiCC = this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici();
		AnagraficaType anagraficaCC = datiAnagraficiCC.getAnagrafica();
		IdFiscaleType idFiscaleCC = datiAnagraficiCC.getIdFiscaleIVA() != null ? datiAnagraficiCC.getIdFiscaleIVA() : new IdFiscaleType();
		
		//Cessionario committente
		if(anagraficaCC.getDenominazione() != null) {
			fatturaElettronica.setCessionarioCommittenteDenominazione(anagraficaCC.getDenominazione());
		} else {
			fatturaElettronica.setCessionarioCommittenteDenominazione(anagraficaCC.getNome() + " " + anagraficaCC.getCognome());
		}
		
		if(idFiscaleCC.getIdCodice() == null || idFiscaleCC.getIdPaese() == null) {
			if(datiAnagraficiCC.getCodiceFiscale() != null) {
				fatturaElettronica.setCessionarioCommittenteCodiceFiscale(datiAnagraficiCC.getCodiceFiscale());
			}
		} else {
			if(idFiscaleCC.getIdCodice() != null && idFiscaleCC.getIdPaese() != null) {
				fatturaElettronica.setCessionarioCommittenteCodiceFiscale(idFiscaleCC.getIdCodice());
				fatturaElettronica.setCessionarioCommittentePaese(idFiscaleCC.getIdPaese());
			}
		}
		
		//Terzo intermediario o soggetto emittente
		if(this.getLotto().getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente() != null) {
			DatiAnagraficiTerzoIntermediarioType datiAnagraficiTI = this.getLotto().getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente().getDatiAnagrafici();
			AnagraficaType anagraficaTI = datiAnagraficiTI.getAnagrafica();
			
			if(anagraficaTI.getDenominazione() != null) {
				fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getDenominazione());
			} else {
				fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getNome() + " " + anagraficaTI.getCognome());
			}
			
			if(datiAnagraficiTI.getIdFiscaleIVA() != null) {
				IdFiscaleType idFiscaleTI = datiAnagraficiTI.getIdFiscaleIVA();
				if(idFiscaleTI.getIdCodice() == null || idFiscaleTI.getIdPaese() == null) {
					if(datiAnagraficiTI.getCodiceFiscale() != null) {
						fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
					}
				} else {
					if(idFiscaleTI.getIdCodice() != null && idFiscaleTI.getIdPaese() != null) {
						fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(idFiscaleTI.getIdCodice());
						fatturaElettronica.setTerzoIntermediarioOSoggettoEmittentePaese(idFiscaleTI.getIdPaese());
					}
				}
			} else {
				if(datiAnagraficiTI.getCodiceFiscale() != null) {
					fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
				}
			}
			
		}

		DatiGeneraliDocumentoType datiGeneraliDocumento =  this.getLotto().getFatturaElettronicaBody(index).getDatiGenerali().getDatiGeneraliDocumento();
		
		TipoDocumentoType tipoDoc = null;
		if(datiGeneraliDocumento.getTipoDocumento()!=null) {
			switch(datiGeneraliDocumento.getTipoDocumento()) {
			case TD01: tipoDoc = TipoDocumentoType.TD01;
				break;
			case TD02: tipoDoc = TipoDocumentoType.TD02;
				break;
			case TD03: tipoDoc = TipoDocumentoType.TD03;
				break;
			case TD04: tipoDoc = TipoDocumentoType.TD04;
				break;
			case TD05: tipoDoc = TipoDocumentoType.TD05;
				break;
			case TD06: tipoDoc = TipoDocumentoType.TD06;
				break;
			}
		} else {
			tipoDoc = TipoDocumentoType.TDXX;
		}
		
		fatturaElettronica.setTipoDocumento(tipoDoc);
		
		fatturaElettronica.setDivisa(datiGeneraliDocumento.getDivisa());

		fatturaElettronica.setData(datiGeneraliDocumento.getData());
		fatturaElettronica.setAnno(new Integer(this.getSdfYear().format(datiGeneraliDocumento.getData())));

		fatturaElettronica.setNumero(datiGeneraliDocumento.getNumero());
		
	}
	

	@Override
	protected void populateLottoConDatiSpecifici(LottoFatture lottoFatture) {
		DatiAnagraficiCedenteType datiAnagraficiCP = this.getLotto().getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici();
		AnagraficaType anagraficaCP = datiAnagraficiCP.getAnagrafica();
		IdFiscaleType idFiscaleCP = datiAnagraficiCP.getIdFiscaleIVA();

		//Cedente prestatore
		if(anagraficaCP.getDenominazione() != null) {
			lottoFatture.setCedentePrestatoreDenominazione(anagraficaCP.getDenominazione());
		} else {
			lottoFatture.setCedentePrestatoreDenominazione(anagraficaCP.getNome() + " " + anagraficaCP.getCognome());
		}
		
		lottoFatture.setCedentePrestatoreCodiceFiscale(idFiscaleCP.getIdCodice());
		lottoFatture.setCedentePrestatorePaese(idFiscaleCP.getIdPaese());

		
		
		DatiAnagraficiCessionarioType datiAnagraficiCC = this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici();
		AnagraficaType anagraficaCC = datiAnagraficiCC.getAnagrafica();
		IdFiscaleType idFiscaleCC = datiAnagraficiCC.getIdFiscaleIVA() != null ? datiAnagraficiCC.getIdFiscaleIVA() : new IdFiscaleType();
		
		//Cessionario committente
		if(anagraficaCC.getDenominazione() != null) {
			lottoFatture.setCessionarioCommittenteDenominazione(anagraficaCC.getDenominazione());
		} else {
			lottoFatture.setCessionarioCommittenteDenominazione(anagraficaCC.getNome() + " " + anagraficaCC.getCognome());
		}
		
		if(idFiscaleCC.getIdCodice() == null || idFiscaleCC.getIdPaese() == null) {
			if(datiAnagraficiCC.getCodiceFiscale() != null) {
				lottoFatture.setCessionarioCommittenteCodiceFiscale(datiAnagraficiCC.getCodiceFiscale());
			}
		} else {
			if(idFiscaleCC.getIdCodice() != null && idFiscaleCC.getIdPaese() != null) {
				lottoFatture.setCessionarioCommittenteCodiceFiscale(idFiscaleCC.getIdCodice());
				lottoFatture.setCessionarioCommittentePaese(idFiscaleCC.getIdPaese());
			}
		}
		
		//Terzo intermediario o soggetto emittente
		if(this.getLotto().getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente() != null) {
			DatiAnagraficiTerzoIntermediarioType datiAnagraficiTI = this.getLotto().getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente().getDatiAnagrafici();
			AnagraficaType anagraficaTI = datiAnagraficiTI.getAnagrafica();
			
			if(anagraficaTI.getDenominazione() != null) {
				lottoFatture.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getDenominazione());
			} else {
				lottoFatture.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getNome() + " " + anagraficaTI.getCognome());
			}
			
			if(datiAnagraficiTI.getIdFiscaleIVA() != null) {
				IdFiscaleType idFiscaleTI = datiAnagraficiTI.getIdFiscaleIVA();
				if(idFiscaleTI.getIdCodice() == null || idFiscaleTI.getIdPaese() == null) {
					if(datiAnagraficiTI.getCodiceFiscale() != null) {
						lottoFatture.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
					}
				} else {
					if(idFiscaleTI.getIdCodice() != null && idFiscaleTI.getIdPaese() != null) {
						lottoFatture.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(idFiscaleTI.getIdCodice());
						lottoFatture.setTerzoIntermediarioOSoggettoEmittentePaese(idFiscaleTI.getIdPaese());
					}
				}
			} else {
				if(datiAnagraficiTI.getCodiceFiscale() != null) {
					lottoFatture.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
				}
			}
			
		}
	}
	
	@Override
	public List<String> getCausali(int index) {
		FatturaElettronicaBodyType fatturaBody = getLotto().getFatturaElettronicaBody(index);
		
		List<String> lst = new ArrayList<String>();
		
		if(fatturaBody.getDatiGenerali() != null && 
				fatturaBody.getDatiGenerali().getDatiGeneraliDocumento() != null &&
				fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getCausaleList() != null) {
			lst.addAll(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getCausaleList());
		}
		
		return lst;
	}
	
	@Override
	public List<AllegatoFattura> getAllegati(int index) {
		
		FatturaElettronicaBodyType fatturaBody = getLotto().getFatturaElettronicaBody(index);

		List<AllegatoFattura> lst = new ArrayList<AllegatoFattura>();
		if(fatturaBody.getAllegatiList() != null) {

			for(AllegatiType allegatoType: fatturaBody.getAllegatiList()) {
				AllegatoFattura allegato = new AllegatoFattura();

				allegato.setNomeAttachment(allegatoType.getNomeAttachment());
				allegato.setAlgoritmoCompressione(allegatoType.getAlgoritmoCompressione());
				allegato.setFormatoAttachment(allegatoType.getFormatoAttachment());
				allegato.setDescrizioneAttachment(allegatoType.getDescrizioneAttachment());
				allegato.setAttachment(allegatoType.getAttachment());
				
				lst.add(allegato);

			}
		}
		
		return lst;
	}


	@Override
	public void validate(boolean strictValidation) throws InserimentoLottiException {
		FatturaElettronicaType getFattura = this.getLotto();
		if(getFattura == null)
			throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "File lotto non presente");
		if(getFattura.getFatturaElettronicaBodyList() == null || getFattura.getFatturaElettronicaBodyList().isEmpty())
			throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "Nessuna fattura contenuta nel lotto ricevuto");

		
		for(FatturaElettronicaBodyType fatturaBody: getFattura.getFatturaElettronicaBodyList()) {
			
			if(fatturaBody.getDatiGenerali() == null)
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali");
			
			if(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento() == null)
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento");
			
			DatiGeneraliDocumentoType datiGeneraliDocumento = fatturaBody.getDatiGenerali().getDatiGeneraliDocumento();
			
			if(strictValidation) {
				if(datiGeneraliDocumento.getTipoDocumento()==null)
					throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento.tipoDocumento");
			}
			
			if(datiGeneraliDocumento.getData() == null)
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento.data");
		}

	}
	
	@Override
	public double getImportoTotaleDocumento(int index) {
		//NOTA: FIX 15.4.2015 a seguito della CR 82. Vedi corpo del metodo _getImportoTotale()
		return _getImportoTotale(index);
	}
	
//	private double _getImportoTotaleDettagli() {
//
//		double importo = 0;
//		List<DettaglioLineeType> riepilogoLst = fattura.getFatturaElettronicaBody(0).getDatiBeniServizi().getDettaglioLineeList();
//		
//		for(DettaglioLineeType dettaglio : riepilogoLst) {
//			importo += dettaglio.getPrezzoTotale().doubleValue() + (dettaglio.getPrezzoTotale().doubleValue() * (dettaglio.getAliquotaIVA().doubleValue() / 100.0));
//		}
//		
//		return importo;
//
//	}
	
	private Double _getImportoTotaleDocumento(int index) {
		return getLotto().getFatturaElettronicaBody(index).getDatiGenerali().getDatiGeneraliDocumento().getImportoTotaleDocumento();
	}
	
	@Override
	public double getImportoTotaleRiepilogo(int index) {
		//NOTA: FIX 15.4.2015 a seguito della CR 82. Vedi corpo del metodo _getImportoTotale()
		return _getImportoTotale(index);
	}

	private double _getImportoTotale(int index) {
		//NOTA: a seguito della CR 82: l'importo totale torna ad essere uno solo e viene cosi' calcolato: 
		// - se l'importo totale documento e' valorizzato, si usa quello
		// - altrimenti si calcola dagli importi indicati nel riepilogo 
		Double importo = _getImportoTotaleDocumento(index);
		if(importo == null) {
			importo = _getImportoTotaleRiepilogo(index);
		}
		return importo;
	}
	
	private double _getImportoTotaleRiepilogo(int index) {

		double importo = 0;
		List<DatiRiepilogoType> riepilogoLst = getLotto().getFatturaElettronicaBody(index).getDatiBeniServizi().getDatiRiepilogoList();
		
		for(DatiRiepilogoType riepilogo : riepilogoLst) {
			importo += riepilogo.getImponibileImporto().doubleValue() + riepilogo.getImposta().doubleValue();
		}
		
		return importo;

	}
	
	public String getDenominazioneDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getDenominazione();
	}
	
	public String getNomeDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getNome();
	}
	
	public String getCognomeDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getCognome();
	}
	
	public String getCodiceDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getDatiTrasmissione().getCodiceDestinatario();
	}
	
	public String getIndirizzoDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getIndirizzo();
	}
	
	public String getCapDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getCap();
	}
	
	public String getComuneDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getComune();
	}
	
	public String getProvincia() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getProvincia();
	}
	
	public String getStato() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getNazione();
	}
	
	public String getPartitaIVADestinatario() {
		IdFiscaleType idFiscaleIVA = this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getIdFiscaleIVA();
			if(idFiscaleIVA == null)
				return null;
			return idFiscaleIVA.getIdCodice();
	}

	public String getCFMittente() {
		return this.getLotto().getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici().getCodiceFiscale();
	}

	public String getCodiceFiscaleDestinatario() {
		return this.getLotto().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getCodiceFiscale();
	}
}

