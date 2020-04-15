package org.govmix.proxy.fatturapa.web.commons.converter;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.AbstractLottoConverter;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class UBLLottoConverter extends AbstractLottoConverter<InvoiceType> {

	public UBLLottoConverter(byte[] fattura, ConsegnaFatturaParameters params) throws InserimentoLottiException {
		super(UBLLottoConverter.readFatturaElettronicaType(fattura), fattura, params);
	}
	private static JAXBContext ctx;

	static {
		try {
			ctx = JAXBContext.newInstance(InvoiceType.class.getPackage().getName());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private static InvoiceType readFatturaElettronicaType(byte[] fatturaBytes) throws InserimentoLottiException {
		try {
			Unmarshaller unmarsh = ctx.createUnmarshaller();
			
            JAXBElement<InvoiceType> invoice = (JAXBElement<InvoiceType>) unmarsh.unmarshal(new StreamSource(new ByteArrayInputStream(fatturaBytes)),InvoiceType.class);

            return invoice.getValue();
		} catch(Exception e) {
			throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, e.getMessage());
		}
	}
	
	protected void initFatture() throws InserimentoLottiException {
		try {
			if(this.fatture == null) {
				this.fatture = new HashMap<String, FatturaElettronica>();
					FatturaElettronica fat = initFatturaElettronica(0, this.lottoAsByte);
					this.fatture.put("1", fat);
			}
		} catch(Exception e) {
			throw new InserimentoLottiException(CODICE.ERRORE_GENERICO, e.getMessage());
		}
	} 


	@Override
	public void populateFatturaConDatiSpecifici(FatturaElettronica fatturaElettronica, int index) {

		InvoiceType lotto = this.getLotto();
////		DatiAnagraficiCedenteType datiAnagraficiCP = lotto.getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici();
////		AnagraficaType anagraficaCP = datiAnagraficiCP.getAnagrafica();
////		IdFiscaleType idFiscaleCP = datiAnagraficiCP.getIdFiscaleIVA();
////
////		//Cedente prestatore
////		if(anagraficaCP.getDenominazione() != null) {
////			fatturaElettronica.setCedentePrestatoreDenominazione(anagraficaCP.getDenominazione());
////		} else {
////			fatturaElettronica.setCedentePrestatoreDenominazione(anagraficaCP.getNome() + " " + anagraficaCP.getCognome());
////		}
////
////		fatturaElettronica.setCedentePrestatoreCodiceFiscale(idFiscaleCP.getIdCodice());
////		fatturaElettronica.setCedentePrestatorePaese(idFiscaleCP.getIdPaese());
////
////
////
////		DatiAnagraficiCessionarioType datiAnagraficiCC = lotto.getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici();
////		AnagraficaType anagraficaCC = datiAnagraficiCC.getAnagrafica();
////		IdFiscaleType idFiscaleCC = datiAnagraficiCC.getIdFiscaleIVA() != null ? datiAnagraficiCC.getIdFiscaleIVA() : new IdFiscaleType();
////
////		//Cessionario committente
////		if(anagraficaCC.getDenominazione() != null) {
////			fatturaElettronica.setCessionarioCommittenteDenominazione(anagraficaCC.getDenominazione());
////		} else {
////			fatturaElettronica.setCessionarioCommittenteDenominazione(anagraficaCC.getNome() + " " + anagraficaCC.getCognome());
////		}
////
////		if(idFiscaleCC.getIdCodice() == null || idFiscaleCC.getIdPaese() == null) {
////			if(datiAnagraficiCC.getCodiceFiscale() != null) {
////				fatturaElettronica.setCessionarioCommittenteCodiceFiscale(datiAnagraficiCC.getCodiceFiscale());
////			}
////		} else {
////			if(idFiscaleCC.getIdCodice() != null && idFiscaleCC.getIdPaese() != null) {
////				fatturaElettronica.setCessionarioCommittenteCodiceFiscale(idFiscaleCC.getIdCodice());
////				fatturaElettronica.setCessionarioCommittentePaese(idFiscaleCC.getIdPaese());
////			}
////		}
////
////		//Terzo intermediario o soggetto emittente
////		if(lotto.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente() != null) {
////			DatiAnagraficiTerzoIntermediarioType datiAnagraficiTI = lotto.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente().getDatiAnagrafici();
////			AnagraficaType anagraficaTI = datiAnagraficiTI.getAnagrafica();
////
////			if(anagraficaTI.getDenominazione() != null) {
////				fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getDenominazione());
////			} else {
////				fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getNome() + " " + anagraficaTI.getCognome());
////			}
////
////			if(datiAnagraficiTI.getIdFiscaleIVA() != null) {
////				IdFiscaleType idFiscaleTI = datiAnagraficiTI.getIdFiscaleIVA();
////				if(idFiscaleTI.getIdCodice() == null || idFiscaleTI.getIdPaese() == null) {
////					if(datiAnagraficiTI.getCodiceFiscale() != null) {
////						fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
////					}
////				} else {
////					if(idFiscaleTI.getIdCodice() != null && idFiscaleTI.getIdPaese() != null) {
////						fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(idFiscaleTI.getIdCodice());
////						fatturaElettronica.setTerzoIntermediarioOSoggettoEmittentePaese(idFiscaleTI.getIdPaese());
////					}
////				}
////			} else {
////				if(datiAnagraficiTI.getCodiceFiscale() != null) {
////					fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
////				}
////			}
////
////		}
////
////		DatiGeneraliDocumentoType datiGeneraliDocumento =  lotto.getFatturaElettronicaBody(index).getDatiGenerali().getDatiGeneraliDocumento();
////
////		TipoDocumentoType tipoDoc = null;
////		if(datiGeneraliDocumento.getTipoDocumento()!=null) {
////			switch(datiGeneraliDocumento.getTipoDocumento()) {
////			case TD01: tipoDoc = TipoDocumentoType.TD01;
////			break;
////			case TD02: tipoDoc = TipoDocumentoType.TD02;
////			break;
////			case TD03: tipoDoc = TipoDocumentoType.TD03;
////			break;
////			case TD04: tipoDoc = TipoDocumentoType.TD04;
////			break;
////			case TD05: tipoDoc = TipoDocumentoType.TD05;
////			break;
////			case TD06: tipoDoc = TipoDocumentoType.TD06;
////			break;
////			}
////		} else {
////			Logger log = LoggerManager.getBatchInserimentoFatturaLogger();
////			try {
////				String tipoDocumento = XPathUtils.getTipoDocumento(fatturaElettronica.getXml(), log); //per TD20
////				log.info("Trovato tipoDocumento ["+tipoDocumento+"]");
////				tipoDoc = TipoDocumentoType.toEnumConstant(tipoDocumento, true);
////				log.info("Trovato tipoDocumentoType ["+tipoDoc+"]");
////			} catch (NotFoundException e) {
////				tipoDoc = TipoDocumentoType.TDXX;
////				log.error("TipoDocumentoType non trovato: " + e.getMessage(), e);
////			} catch (Exception e) {
////				tipoDoc = TipoDocumentoType.TDXX;
////				log.error("Errore durante la lettura del TipoDocumento: " + e.getMessage(), e);
////			}
////		}
//
//		fatturaElettronica.setTipoDocumento(tipoDoc);

		fatturaElettronica.setDivisa(lotto.getDocumentCurrencyCode().getValue());

		fatturaElettronica.setData(lotto.getTaxPointDate().getValue().toGregorianCalendar().getTime());
		fatturaElettronica.setAnno(new Integer(this.getSdfYear().format(fatturaElettronica.getData())));

		fatturaElettronica.setNumero(lotto.getID().getValue());
	}
	@Override
	protected void populateLottoConDatiSpecifici(LottoFatture lottoFatture) {
//		SupplierPartyType datiAnagraficiCP = this.getLotto().getAccountingSupplierParty();
//		AnagraficaType anagraficaCP = datiAnagraficiCP.getAnagrafica();
//		IdFiscaleType idFiscaleCP = datiAnagraficiCP.getIdFiscaleIVA();

		//Cedente prestatore
//		if(datiAnagraficiCP.getSellerContact().getName() != null) {
//			lottoFatture.setCedentePrestatoreDenominazione(datiAnagraficiCP.getSellerContact().getName().getValue());
//		} else {
//			lottoFatture.setCedentePrestatoreDenominazione(anagraficaCP.getNome() + " " + anagraficaCP.getCognome());
//		}

//		lottoFatture.setCedentePrestatoreCodiceFiscale(datiAnagraficiCP.getParty().getContact().
//		lottoFatture.setCedentePrestatorePaese(idFiscaleCP.getIdPaese());
//
//
//		CustomerPartyType datiAnagraficiCC = this.getLotto().getAccountingCustomerParty();
//
//		AnagraficaType anagraficaCC = datiAnagraficiCC.getAnagrafica();
//		IdFiscaleType idFiscaleCC = datiAnagraficiCC.getIdFiscaleIVA() != null ? datiAnagraficiCC.getIdFiscaleIVA() : new IdFiscaleType();
//
//		//Cessionario committente
//		if(anagraficaCC.getDenominazione() != null) {
//			lottoFatture.setCessionarioCommittenteDenominazione(anagraficaCC.getDenominazione());
//		} else {
//			lottoFatture.setCessionarioCommittenteDenominazione(anagraficaCC.getNome() + " " + anagraficaCC.getCognome());
//		}
//
//		if(idFiscaleCC.getIdCodice() == null || idFiscaleCC.getIdPaese() == null) {
//			if(datiAnagraficiCC.getCodiceFiscale() != null) {
//				lottoFatture.setCessionarioCommittenteCodiceFiscale(datiAnagraficiCC.getCodiceFiscale());
//			}
//		} else {
//			if(idFiscaleCC.getIdCodice() != null && idFiscaleCC.getIdPaese() != null) {
//				lottoFatture.setCessionarioCommittenteCodiceFiscale(idFiscaleCC.getIdCodice());
//				lottoFatture.setCessionarioCommittentePaese(idFiscaleCC.getIdPaese());
//			}
//		}
////
//		//Terzo intermediario o soggetto emittente
//		if(this.getLotto().getTaxRepresentativeParty() != null) {
//			PartyType datiAnagraficiTI = this.getLotto().getTaxRepresentativeParty();
//			AnagraficaType anagraficaTI = datiAnagraficiTI.getAnagrafica();
//
//			if(anagraficaTI.getDenominazione() != null) {
//				lottoFatture.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getDenominazione());
//			} else {
//				lottoFatture.setTerzoIntermediarioOSoggettoEmittenteDenominazione(anagraficaTI.getNome() + " " + anagraficaTI.getCognome());
//			}
//
//			if(datiAnagraficiTI.getIdFiscaleIVA() != null) {
//				IdFiscaleType idFiscaleTI = datiAnagraficiTI.getIdFiscaleIVA();
//				if(idFiscaleTI.getIdCodice() == null || idFiscaleTI.getIdPaese() == null) {
//					if(datiAnagraficiTI.getCodiceFiscale() != null) {
//						lottoFatture.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
//					}
//				} else {
//					if(idFiscaleTI.getIdCodice() != null && idFiscaleTI.getIdPaese() != null) {
//						lottoFatture.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(idFiscaleTI.getIdCodice());
//						lottoFatture.setTerzoIntermediarioOSoggettoEmittentePaese(idFiscaleTI.getIdPaese());
//					}
//				}
//			} else {
//				if(datiAnagraficiTI.getCodiceFiscale() != null) {
//					lottoFatture.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(datiAnagraficiTI.getCodiceFiscale());
//				}
//			}
//
//		}
	}

	@Override
	public List<String> getCausali(int index) {
		InvoiceLineType fattura = this.getLotto().getInvoiceLine().get(index);
		List<String> lst = new ArrayList<String>();

		if(fattura.getNote() != null) {
			for(NoteType note: fattura.getNote()) {
				lst.add(note.getValue());	
			}
		}

		return lst;
	}
	@Override
	public List<AllegatoFattura> getAllegati(int index) {

		InvoiceLineType fattura = this.getLotto().getInvoiceLine().get(index);

		List<AllegatoFattura> lst = new ArrayList<AllegatoFattura>();
		if(fattura.getDocumentReference() != null) {

			for(DocumentReferenceType allegatoType: fattura.getDocumentReference()) {
				AllegatoFattura allegato = new AllegatoFattura();

				//TODO EXTERNAL REFERENCE ?
				allegato.setNomeAttachment(allegatoType.getAttachment().getEmbeddedDocumentBinaryObject().getFilename());
				allegato.setFormatoAttachment(allegatoType.getAttachment().getEmbeddedDocumentBinaryObject().getFormat());
				
				StringBuffer sb = new StringBuffer();
				for(DocumentDescriptionType dsc: allegatoType.getDocumentDescription()) {
					if(sb.length() > 0) {
						sb.append(",");
					}
					sb.append(dsc.getValue());
				}
				allegato.setDescrizioneAttachment(sb.toString());
				allegato.setAttachment(allegatoType.getAttachment().getEmbeddedDocumentBinaryObject().getValue());

				lst.add(allegato);

			}
		}

		return lst;
	}

	@Override
	public void validate(boolean strictValidation) throws InserimentoLottiException {
//		FatturaElettronicaType getFattura = this.getLotto();
//		if(getFattura == null)
//			throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "File lotto non presente");
//		if(getFattura.getFatturaElettronicaBodyList() == null || getFattura.getFatturaElettronicaBodyList().isEmpty())
//			throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "Nessuna fattura contenuta nel lotto ricevuto");
//
//
//		for(FatturaElettronicaBodyType fatturaBody: getFattura.getFatturaElettronicaBodyList()) {
//
//			if(fatturaBody.getDatiGenerali() == null)
//				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali");
//
//			if(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento() == null)
//				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento");
//
//			DatiGeneraliDocumentoType datiGeneraliDocumento = fatturaBody.getDatiGenerali().getDatiGeneraliDocumento();
//
//			if(strictValidation) {
//				if(datiGeneraliDocumento.getTipoDocumento()==null)
//					throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento.tipoDocumento");
//			}
//
//			if(datiGeneraliDocumento.getData() == null)
//				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento.data");
//		}
	}

	@Override
	public double getImportoTotaleDocumento(int index) {
		//NOTA: FIX 15.4.2015 a seguito della CR 82. Vedi corpo del metodo _getImportoTotale()
		return _getImportoTotale(index);
	}

	//		private double _getImportoTotaleDettagli() {
	//
	//			double importo = 0;
	//			List<DettaglioLineeType> riepilogoLst = fattura.getFatturaElettronicaBody(0).getDatiBeniServizi().getDettaglioLineeList();
	//			
	//			for(DettaglioLineeType dettaglio : riepilogoLst) {
	//				importo += dettaglio.getPrezzoTotale().doubleValue() + (dettaglio.getPrezzoTotale().doubleValue() * (dettaglio.getAliquotaIVA().doubleValue() / 100.0));
	//			}
	//			
	//			return importo;
	//
	//		}

	private Double _getImportoTotaleDocumento(int index) {
		return getLotto().getInvoiceLine().get(index).getPrice().getPriceAmount().getValue().doubleValue();
//				getDatiGenerali().getDatiGeneraliDocumento().getImportoTotaleDocumento();
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

//		double importo = 0;
//		List<DatiRiepilogoType> riepilogoLst = getLotto().getInvoiceLine().get(index).getDatiBeniServizi().getDatiRiepilogoList();
//
//		for(DatiRiepilogoType riepilogo : riepilogoLst) {
//			importo += riepilogo.getImponibileImporto().doubleValue() + riepilogo.getImposta().doubleValue();
//		}
//
//		return importo;

		return getLotto().getInvoiceLine().get(index).getPrice().getPriceAmount().getValue().doubleValue();

	}

	public String getDenominazioneDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getNomeDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getCognomeDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getCodiceDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getParty().getEndpointID().getValue();
	}

	public String getIndirizzoDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getCapDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getComuneDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getProvincia() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getStato() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getPartitaIVADestinatario() {
		return this.getLotto().getAccountingCustomerParty().getBuyerContact().getName().getValue();
	}

	public String getCFMittente() {
		return this.getLotto().getSellerSupplierParty().getSellerContact().getName().getValue();
	}

	public String getCodiceFiscaleDestinatario() {
		return this.getLotto().getAccountingCustomerParty().getParty().getPartyIdentification().get(0).getID().getValue();
	}

}

