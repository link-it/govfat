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
package org.govmix.proxy.pcc.fatture.converter;



import it.tesoro.fatture.ComunicaScadenzaTipo;
import it.tesoro.fatture.ComunicazioneScadenzaTipo;
import it.tesoro.fatture.ContabilizzazioneTipo;
import it.tesoro.fatture.ListaContabilizzazioneTipo;
import it.tesoro.fatture.MandatoPagamentoTipo;
import it.tesoro.fatture.NaturaSpesaContabiliTipo;
import it.tesoro.fatture.NaturaSpesaTipo;
import it.tesoro.fatture.OperazioneContabilizzazioneTipo;
import it.tesoro.fatture.PagamentoStornoTipo;
import it.tesoro.fatture.PagamentoTipo;
import it.tesoro.fatture.RicezioneFatturaTipo;
import it.tesoro.fatture.RifiutoFatturaTipo;
import it.tesoro.fatture.StatoDebitoTipo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.govmix.pcc.fatture.Allegato;
import org.govmix.pcc.fatture.CertificazioneTipo;
import org.govmix.pcc.fatture.CodiceEsenzioneIVATipo;
import org.govmix.pcc.fatture.DatiAmministrazioneTipo;
import org.govmix.pcc.fatture.DatiCessionarioTipo;
import org.govmix.pcc.fatture.DatiDistintaTrasmissioneTipo;
import org.govmix.pcc.fatture.DatiFornitoreTipo;
import org.govmix.pcc.fatture.DatiRichiestaOperazioneContabileProxyTipo;
import org.govmix.pcc.fatture.DatiRispostaDatiFatturaQueryTipo;
import org.govmix.pcc.fatture.DatiRispostaDownloadDocumentoReadTipo;
import org.govmix.pcc.fatture.DatiRispostaElencoMovimentiErarioReadTipo;
import org.govmix.pcc.fatture.DatiRispostaInserimentoFatturaQueryTipo;
import org.govmix.pcc.fatture.DatiRispostaInterrogazioneEsitiQueryTipo;
import org.govmix.pcc.fatture.DatiRispostaOperazioneContabileQueryTipo;
import org.govmix.pcc.fatture.DatiRispostaProxyTipo;
import org.govmix.pcc.fatture.DatiRispostaStatoFatturaReadTipo;
import org.govmix.pcc.fatture.DettaglioDocumentoReadTipo;
import org.govmix.pcc.fatture.DettaglioDocumentoTipo;
import org.govmix.pcc.fatture.DettaglioFatturaTipo;
import org.govmix.pcc.fatture.DettaglioMovimentoTipo;
import org.govmix.pcc.fatture.DettaglioPagamentoTipo;
import org.govmix.pcc.fatture.ErroreElaborazioneOperazioneTipo;
import org.govmix.pcc.fatture.ErroreTipo;
import org.govmix.pcc.fatture.EsitoElabTransazTipo;
import org.govmix.pcc.fatture.EsitoOkKoTipo;
import org.govmix.pcc.fatture.EsitoTipo;
import org.govmix.pcc.fatture.FlagSiNoTipo;
import org.govmix.pcc.fatture.IdentificazioneSDITipo;
import org.govmix.pcc.fatture.ListaCertificazioneTipo;
import org.govmix.pcc.fatture.ListaDettaglioMovimentoTipo;
import org.govmix.pcc.fatture.ListaDettaglioPagamentoTipo;
import org.govmix.pcc.fatture.ListaErroreElaborazioneOperazioneTipo;
import org.govmix.pcc.fatture.ListaErroreTipo;
import org.govmix.pcc.fatture.ListaEsitoTipo;
import org.govmix.pcc.fatture.ListaOperazioneTipo;
import org.govmix.pcc.fatture.ListaPagamentoStornoIvaTipo;
import org.govmix.pcc.fatture.ListaPianoComunicazioneScadenzaTipo;
import org.govmix.pcc.fatture.ListaRiepilogoAliquoteTipo;
import org.govmix.pcc.fatture.OperazioneTipo;
import org.govmix.pcc.fatture.PagamentoStornoIvaTipo;
import org.govmix.pcc.fatture.PaginazioneTipo;
import org.govmix.pcc.fatture.PianoComunicazioneScadenzaTipo;
import org.govmix.pcc.fatture.ProxyOperazioneContabileRichiestaTipo;
import org.govmix.pcc.fatture.ProxyRispostaTipo;
import org.govmix.pcc.fatture.QueryDatiFatturaRispostaTipo;
import org.govmix.pcc.fatture.QueryInserimentoFatturaRispostaTipo;
import org.govmix.pcc.fatture.QueryInterrogazioneEsitiRispostaTipo;
import org.govmix.pcc.fatture.QueryOperazioneContabileRispostaTipo;
import org.govmix.pcc.fatture.QueryPagamentoIvaRispostaTipo;
import org.govmix.pcc.fatture.ReadDownloadDocumentoRispostaTipo;
import org.govmix.pcc.fatture.ReadElencoMovimentiErarioIvaRispostaTipo;
import org.govmix.pcc.fatture.ReadStatoFatturaRispostaTipo;
import org.govmix.pcc.fatture.RiepilogoAliquoteTipo;
import org.govmix.pcc.fatture.RipartizioneAttualeTipo;
import org.govmix.pcc.fatture.StatoContabileFatturaTipo;
import org.govmix.pcc.fatture.StatoDocumentoTipo;
import org.govmix.pcc.fatture.StatoTransazioneTipo;
import org.govmix.pcc.fatture.StrutturaDatiOperazioneTipo;
import org.govmix.pcc.fatture.TestataAsyncRichiestaTipo;
import org.govmix.pcc.fatture.TestataRispTipo;
import org.govmix.pcc.fatture.TipoDocumentoTipo;
import org.govmix.pcc.fatture.TipoOperazioneTipo;
import org.govmix.pcc.fatture.TipologiaMovimentoErarioTipo;
import org.govmix.proxy.fatturapa.web.commons.utils.TransformUtils;

public class ProxyConverter {

	public static ProxyRispostaTipo toProxy(it.tesoro.fatture.ProxyRispostaTipo vo, TestataRispTipo testata) {
		ProxyRispostaTipo proxyVO = new ProxyRispostaTipo();
		proxyVO.setTestataRisposta(testata);
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}

	public static ProxyRispostaTipo toProxyDefault(TestataRispTipo testata) {
		ProxyRispostaTipo proxyVO = new ProxyRispostaTipo();
		proxyVO.setTestataRisposta(testata);
		proxyVO.setDatiRisposta(toProxy(testata));
		return proxyVO;
	}


	private static DatiRispostaProxyTipo toProxy(TestataRispTipo testata) {
		DatiRispostaProxyTipo risp = new DatiRispostaProxyTipo();
		risp.setEsitoTrasmissione(testata.getEsito());
		return risp;
	}

	public static QueryInserimentoFatturaRispostaTipo toProxy(it.tesoro.fatture.QueryInserimentoFatturaRispostaTipo vo) {
		QueryInserimentoFatturaRispostaTipo proxyVO = new QueryInserimentoFatturaRispostaTipo();
		proxyVO.setTestataRisposta(TestataConverter.toProxyTestataQuery(vo.getTestataRisposta()));
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}


	public static ReadStatoFatturaRispostaTipo toProxy(it.tesoro.fatture.ReadStatoFatturaRispostaTipo vo) {
		ReadStatoFatturaRispostaTipo proxyVO = new ReadStatoFatturaRispostaTipo();
		proxyVO.setTestataRisposta(TestataConverter.toProxyTestataRead(vo.getTestataRisposta()));
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}


	public static QueryOperazioneContabileRispostaTipo toProxy(it.tesoro.fatture.QueryOperazioneContabileRispostaTipo vo) {
		QueryOperazioneContabileRispostaTipo proxyVO = new QueryOperazioneContabileRispostaTipo();
		proxyVO.setTestataRisposta(TestataConverter.toProxyTestataQuery(vo.getTestataRisposta()));
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}


	public static ReadDownloadDocumentoRispostaTipo toProxy(it.tesoro.fatture.ReadDownloadDocumentoRispostaTipo vo) {
		ReadDownloadDocumentoRispostaTipo proxyVO = new ReadDownloadDocumentoRispostaTipo();
		proxyVO.setTestataRisposta(TestataConverter.toProxyTestataRead(vo.getTestataRisposta()));
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}


	public static QueryDatiFatturaRispostaTipo toProxy(it.tesoro.fatture.QueryDatiFatturaRispostaTipo vo) {
		QueryDatiFatturaRispostaTipo proxyVO = new QueryDatiFatturaRispostaTipo();
		proxyVO.setTestataRisposta(TestataConverter.toProxyTestataQuery(vo.getTestataRisposta()));
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}


	public static QueryPagamentoIvaRispostaTipo toProxy(it.tesoro.fatture.QueryPagamentoIvaRispostaTipo vo) {
		QueryPagamentoIvaRispostaTipo proxyVO = new QueryPagamentoIvaRispostaTipo();
		proxyVO.setTestataRisposta(TestataConverter.toProxyTestataQuery(vo.getTestataRisposta()));
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}


	public static ReadElencoMovimentiErarioIvaRispostaTipo toProxy(it.tesoro.fatture.ReadElencoMovimentiErarioIvaRispostaTipo vo) {
		ReadElencoMovimentiErarioIvaRispostaTipo proxyVO = new ReadElencoMovimentiErarioIvaRispostaTipo();
		proxyVO.setTestataRisposta(TestataConverter.toProxyTestataRead(vo.getTestataRisposta()));
		proxyVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return proxyVO;
	}
	
	public static QueryInterrogazioneEsitiRispostaTipo toProxy(it.tesoro.fatture.QueryInterrogazioneEsitiRispostaTipo vo) {
		QueryInterrogazioneEsitiRispostaTipo pccVO = new QueryInterrogazioneEsitiRispostaTipo();
		pccVO.setTestataRisposta(TestataConverter.toProxyTestataQuery(vo.getTestataRisposta()));
		pccVO.setDatiRisposta(toProxy(vo.getDatiRisposta()));
		return pccVO;
	}

	/** privati **/

	private static DatiRispostaInterrogazioneEsitiQueryTipo toProxy(it.tesoro.fatture.DatiRispostaInterrogazioneEsitiQueryTipo vo) {
		if(vo == null) return null;
		DatiRispostaInterrogazioneEsitiQueryTipo pccVO = new DatiRispostaInterrogazioneEsitiQueryTipo();
		pccVO.setPaginazione(toProxy(vo.getPaginazione()));
		pccVO.setListaEsito(toProxy(vo.getListaEsito()));
		return pccVO;
	}
	
	private static ListaEsitoTipo toProxy(it.tesoro.fatture.ListaEsitoTipo vo) {
		if(vo == null) return null;
		ListaEsitoTipo pccVO = new ListaEsitoTipo();
		pccVO.getEsito().addAll(toProxyEsitoTipo(vo.getEsito()));
		return pccVO;
	}
	
	private static List<EsitoTipo> toProxyEsitoTipo(List<it.tesoro.fatture.EsitoTipo> vo) {
		if(vo == null) return null;
		List<EsitoTipo> pccVO = new ArrayList<EsitoTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.EsitoTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	
	private static EsitoTipo toProxy(it.tesoro.fatture.EsitoTipo vo) {
		if(vo == null) return null;
		EsitoTipo pccVO = new EsitoTipo();
		pccVO.setIdentificativoTransazionePCC(vo.getIdentificativoTransazionePCC());
		pccVO.setIdentificativoTransazionePA(vo.getIdentificativoTransazionePA());
		pccVO.setIdentificativoPCCAmministrazione(vo.getIdentificativoPCCAmministrazione());
		pccVO.setStatoTransazione(toProxy(vo.getStatoTransazione()));
		return pccVO;
	}

	private static StatoTransazioneTipo toProxy(it.tesoro.fatture.StatoTransazioneTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case EL_AT_TRANSAZIONE_IN_ATTESA_DI_ELABORAZIONE: return StatoTransazioneTipo.EL_AT_TRANSAZIONE_IN_ATTESA_DI_ELABORAZIONE;
		case EL_CO_TRANSAZIONE_IN_CORSO_DI_ELABORAZIONE: return StatoTransazioneTipo.EL_CO_TRANSAZIONE_IN_CORSO_DI_ELABORAZIONE;
		case EL_OK_TRANSAZIONE_ELABORATA_CON_ESITO_POSITIVO: return StatoTransazioneTipo.EL_OK_TRANSAZIONE_ELABORATA_CON_ESITO_POSITIVO;
		case EL_KO_TRANSAZIONE_ELABORATA_CON_ESITO_NEGATIVO: return StatoTransazioneTipo.EL_KO_TRANSAZIONE_ELABORATA_CON_ESITO_NEGATIVO;
		default:
			break;
		}
		return null;
	}

	private static DatiRispostaElencoMovimentiErarioReadTipo toProxy(it.tesoro.fatture.DatiRispostaElencoMovimentiErarioReadTipo vo) {
		if(vo == null) return null;
		DatiRispostaElencoMovimentiErarioReadTipo pccVO = new DatiRispostaElencoMovimentiErarioReadTipo();
		pccVO.setDataFineElaborazione(new Date());
		pccVO.setEsitoTrasmissione(EsitoOkKoTipo.fromValue(vo.getEsitoTrasmissione().value()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		pccVO.setTotaleImportoPagato(vo.getTotaleImportoPagato());
		pccVO.setTotaleIva(vo.getTotaleIva());
		pccVO.setListaPagamentiStornoIva(toProxy(vo.getListaPagamentiStornoIva()));
		pccVO.setPaginazione(toProxy(vo.getPaginazione()));
		return pccVO;
	}

	private static PaginazioneTipo toProxy(it.tesoro.fatture.PaginazioneTipo vo) {
		if(vo == null) return null;
		PaginazioneTipo pccVO = new PaginazioneTipo();
		pccVO.setPaginaCorrente(vo.getPaginaCorrente());
		pccVO.setNumeroPagine(vo.getNumeroPagine());
		pccVO.setNumeroTotaleElementi(vo.getNumeroTotaleElementi());
		return pccVO;
	}

	private static ListaPagamentoStornoIvaTipo toProxy(it.tesoro.fatture.ListaPagamentoStornoIvaTipo vo) {
		if(vo == null) return null;
		ListaPagamentoStornoIvaTipo pccVO = new ListaPagamentoStornoIvaTipo();
		pccVO.getPagamentoStorniIva().addAll(toProxy(vo.getPagamentoStorniIva()));
		return pccVO;
	}
	
	private static List<PagamentoStornoIvaTipo> toProxy(List<it.tesoro.fatture.PagamentoStornoIvaTipo> vo) {
		if(vo == null) return null;
		List<PagamentoStornoIvaTipo> pccVO = new ArrayList<PagamentoStornoIvaTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.PagamentoStornoIvaTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	
	private static PagamentoStornoIvaTipo toProxy(it.tesoro.fatture.PagamentoStornoIvaTipo vo) {
		if(vo == null) return null;
		PagamentoStornoIvaTipo pccVO = new PagamentoStornoIvaTipo();
		pccVO.setIdentificativoMovimentoIva(vo.getIdentificativoMovimentoIva());
		pccVO.setTipoMovimento(toProxy(vo.getTipoMovimento()));
		pccVO.setDescrizioneUfficio(vo.getDescrizioneUfficio());
		pccVO.setIdentificativoPCCUfficio(vo.getIdentificativoPCCUfficio());
		pccVO.setDataMandato(vo.getDataMandato());
		pccVO.setDataRegistrazione(vo.getDataRegistrazione());
		pccVO.setMumeroMandato(vo.getMumeroMandato());
		pccVO.setImporto(vo.getImporto());
		pccVO.setCapitoloSpesa(vo.getCapitoloSpesa());
		pccVO.setEstremiImpegno(vo.getEstremiImpegno());
		pccVO.setNoteAggiuntive(vo.getNoteAggiuntive());
		pccVO.setIdentificativoAllegato(vo.getIdentificativoAllegato());
		return pccVO;
	}

	private static TipologiaMovimentoErarioTipo toProxy(it.tesoro.fatture.TipologiaMovimentoErarioTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case PAGAMENTO: return TipologiaMovimentoErarioTipo.PAGAMENTO;
		case STORNO: return TipologiaMovimentoErarioTipo.STORNO;
		default:
			break;
		}
		return null;
	}

	private static EsitoElabTransazTipo toProxy(it.tesoro.fatture.EsitoElabTransazTipo vo) {
		if(vo == null) return null;
		EsitoElabTransazTipo pccVO = new EsitoElabTransazTipo();
		pccVO.setDataFineElaborazione(new Date());
		pccVO.setEsitoTrasmissione(EsitoOkKoTipo.fromValue(vo.getEsitoTrasmissione().value()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		pccVO.setEsitoElaborazioneTransazione(vo.getEsitoElaborazioneTransazione());
		pccVO.setDescrizioneElaborazioneTransazione(vo.getDescrizioneElaborazioneTransazione());
		return pccVO;
	}

	private static DatiRispostaDatiFatturaQueryTipo toProxy(it.tesoro.fatture.DatiRispostaDatiFatturaQueryTipo vo) {
		if(vo == null) return null;
		DatiRispostaDatiFatturaQueryTipo pccVO = new DatiRispostaDatiFatturaQueryTipo();
		pccVO.setDataFineElaborazione(new Date());
		pccVO.setEsitoTrasmissione(EsitoOkKoTipo.fromValue(vo.getEsitoTrasmissione().value()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		pccVO.setEsitoElaborazioneTransazione(vo.getEsitoElaborazioneTransazione());
		pccVO.setDescrizioneElaborazioneTransazione(vo.getDescrizioneElaborazioneTransazione());

		pccVO.setDettaglioFattura(toProxy(vo.getDettaglioFattura()));
		return pccVO;
	}

	private static DettaglioFatturaTipo toProxy(it.tesoro.fatture.DettaglioFatturaTipo vo) {
		if(vo == null) return null;
		DettaglioFatturaTipo pccVO = new DettaglioFatturaTipo();
		pccVO.setDatiFornitore(toProxy(vo.getDatiFornitore()));
		pccVO.setDatiCessionario(toProxy(vo.getDatiCessionario()));
		pccVO.setDatiAmministrazione(toProxy(vo.getDatiAmministrazione()));
		pccVO.setDatiDistintaTrasmissione(toProxy(vo.getDatiDistintaTrasmissione()));
		pccVO.setFlagFatturaCertificata(toProxy(vo.getFlagFatturaCertificata()));
		pccVO.setListaCertificazione(toProxy(vo.getListaCertificazione()));
		pccVO.setImportoTotaleComunicazioniScadenze(vo.getImportoTotaleComunicazioniScadenze());
		pccVO.setListaComunicazioneScadenza(toProxy(vo.getListaComunicazioneScadenza()));
		pccVO.setDatiDocumento(toProxy(vo.getDatiDocumento()));
		return pccVO;
	}
	
	private static DettaglioDocumentoTipo toProxy(it.tesoro.fatture.DettaglioDocumentoTipo vo) {
		if(vo == null) return null;
		DettaglioDocumentoTipo pccVO = new DettaglioDocumentoTipo();
		pccVO.setStatoDocumento(toProxy(vo.getStatoDocumento()));
		pccVO.setIdFiscaleIVA(vo.getIdFiscaleIVA());
		pccVO.setProgressivoRegistrazione(vo.getProgressivoRegistrazione());
		pccVO.setIdentificazioneSDI(toProxy(vo.getIdentificazioneSDI()));
		pccVO.setNumeroProtocollo(vo.getNumeroProtocollo());
		pccVO.setDataRicezione(vo.getDataRicezione());
		pccVO.setTipoDocumento(toProxy(vo.getTipoDocumento()));
		pccVO.setDataEmissione(vo.getDataEmissione());
		pccVO.setNumeroDocumento(vo.getNumeroDocumento());
		pccVO.setDescrizioneCausale(vo.getDescrizioneCausale());
		pccVO.setImportoTotale(vo.getImportoTotale());
		pccVO.setImportoImponibile(vo.getImportoImponibile());
		pccVO.setTotaleImposta(vo.getTotaleImposta());
		pccVO.setSplitPayment(toProxy(vo.getSplitPayment()));
		pccVO.setListaRiepilogoAliquote(toProxy(vo.getListaRiepilogoAliquote()));
		pccVO.setRipartizioneAttuale(toProxy(vo.getRipartizioneAttuale()));
		pccVO.setListaDettaglioPagamento(toProxy(vo.getListaDettaglioPagamento()));
		pccVO.setListaDettaglioMovimento(toProxy(vo.getListaDettaglioMovimento()));
		return pccVO;
	}
	
	private static ListaDettaglioMovimentoTipo toProxy(it.tesoro.fatture.ListaDettaglioMovimentoTipo vo) {
		if(vo == null) return null;
		ListaDettaglioMovimentoTipo pccVO = new ListaDettaglioMovimentoTipo();
		pccVO.getMovimento().addAll(toProxyDettaglioMovimentoTipo(vo.getMovimento()));
		return pccVO;
	}

	private static List<DettaglioMovimentoTipo> toProxyDettaglioMovimentoTipo(List<it.tesoro.fatture.DettaglioMovimentoTipo> vo) {
		if(vo == null) return null;
		List<DettaglioMovimentoTipo> pccVO = new ArrayList<DettaglioMovimentoTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.DettaglioMovimentoTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	private static DettaglioMovimentoTipo toProxy(it.tesoro.fatture.DettaglioMovimentoTipo vo) {
		if(vo == null) return null;
		DettaglioMovimentoTipo pccVO = new DettaglioMovimentoTipo();
		pccVO.setDataMovimento(vo.getDataMovimento());
		pccVO.setImporto(vo.getImporto());
		pccVO.setStatoDebito(vo.getStatoDebito());
		pccVO.setCausale(vo.getCausale());
		pccVO.setImportoNaturaCorrente(vo.getImportoNaturaCorrente());
		pccVO.setImportoContoCapitale(vo.getImportoContoCapitale());
		pccVO.setCapitoloPgConto(vo.getCapitoloPgConto());
		pccVO.setEstremiImpegno(vo.getEstremiImpegno());
		pccVO.setCodiceCIG(vo.getCodiceCIG());
		pccVO.setCodiceCUP(vo.getCodiceCUP());
		pccVO.setDescrizioneContabilizzazione(vo.getDescrizioneContabilizzazione());
		return pccVO;
	}

	private static ListaDettaglioPagamentoTipo toProxy(it.tesoro.fatture.ListaDettaglioPagamentoTipo vo) {
		if(vo == null) return null;
		ListaDettaglioPagamentoTipo pccVO = new ListaDettaglioPagamentoTipo();
		pccVO.getDettaglioPagamento().addAll(toProxyDettaglioPagamentoTipo(vo.getDettaglioPagamento()));
		return pccVO;
	}

	private static List<DettaglioPagamentoTipo> toProxyDettaglioPagamentoTipo(List<it.tesoro.fatture.DettaglioPagamentoTipo> vo) {
		if(vo == null) return null;
		List<DettaglioPagamentoTipo> pccVO = new ArrayList<DettaglioPagamentoTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.DettaglioPagamentoTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	private static DettaglioPagamentoTipo toProxy(it.tesoro.fatture.DettaglioPagamentoTipo vo) {
		if(vo == null) return null;
		DettaglioPagamentoTipo pccVO = new DettaglioPagamentoTipo();
		pccVO.setDataRiferimentoTerminiPagamento(vo.getDataRiferimentoTerminiPagamento());
		pccVO.setGiorniTerminiPagamento(vo.getGiorniTerminiPagamento());
		pccVO.setDataScadenzaPagamento(vo.getDataScadenzaPagamento());
		pccVO.setImportoPagamento(vo.getImportoPagamento());
		return pccVO;
	}

	private static ListaRiepilogoAliquoteTipo toProxy(it.tesoro.fatture.ListaRiepilogoAliquoteTipo vo) {
		if(vo == null) return null;
		ListaRiepilogoAliquoteTipo pccVO = new ListaRiepilogoAliquoteTipo();
		pccVO.getRiepilogoAliquote().addAll(toProxyRiepilogoAliquoteTipo(vo.getRiepilogoAliquote()));
		return pccVO;
	}
	
	private static List<RiepilogoAliquoteTipo> toProxyRiepilogoAliquoteTipo(List<it.tesoro.fatture.RiepilogoAliquoteTipo> vo) {
		if(vo == null) return null;
		List<RiepilogoAliquoteTipo> pccVO = new ArrayList<RiepilogoAliquoteTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.RiepilogoAliquoteTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	
	private static RiepilogoAliquoteTipo toProxy(it.tesoro.fatture.RiepilogoAliquoteTipo vo) {
		if(vo == null) return null;
		RiepilogoAliquoteTipo pccVO = new RiepilogoAliquoteTipo();
		pccVO.setAliquotaIVA(vo.getAliquotaIVA());
		pccVO.setCodiceEsenzioneIVA(toProxy(vo.getCodiceEsenzioneIVA()));
		pccVO.setTotaleImponibileAliquota(vo.getTotaleImponibileAliquota());
		pccVO.setTotaleImpostaAliquota(vo.getTotaleImpostaAliquota());
		return pccVO;
	}
	
	private static CodiceEsenzioneIVATipo toProxy(it.tesoro.fatture.CodiceEsenzioneIVATipo vo) {
		if(vo == null) return null;
		switch(vo){
		case N_1: return CodiceEsenzioneIVATipo.N_1;
		case N_2: return CodiceEsenzioneIVATipo.N_2;
		case N_3: return CodiceEsenzioneIVATipo.N_3;
		case N_4: return CodiceEsenzioneIVATipo.N_4;
		case N_5: return CodiceEsenzioneIVATipo.N_5;
		default:
			break;
		}
		return null;
	}

	private static IdentificazioneSDITipo toProxy(it.tesoro.fatture.IdentificazioneSDITipo vo) {
		if(vo == null) return null;
		IdentificazioneSDITipo pccVO = new IdentificazioneSDITipo();
		pccVO.setLottoSDI(vo.getLottoSDI());
		pccVO.setNumeroFattura(vo.getNumeroFattura());
		return pccVO;
	}

	private static ListaPianoComunicazioneScadenzaTipo toProxy(it.tesoro.fatture.ListaPianoComunicazioneScadenzaTipo vo) {
		if(vo == null) return null;
		ListaPianoComunicazioneScadenzaTipo pccVO = new ListaPianoComunicazioneScadenzaTipo();
		pccVO.getComunicazioneScadenza().addAll(toProxyPianoComunicazioneScadenzaTipo(vo.getComunicazioneScadenza()));
		return pccVO;
	}

	private static List<PianoComunicazioneScadenzaTipo> toProxyPianoComunicazioneScadenzaTipo(List<it.tesoro.fatture.PianoComunicazioneScadenzaTipo> vo) {
		if(vo == null) return null;
		List<PianoComunicazioneScadenzaTipo> pccVO = new ArrayList<PianoComunicazioneScadenzaTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.PianoComunicazioneScadenzaTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	private static PianoComunicazioneScadenzaTipo toProxy(it.tesoro.fatture.PianoComunicazioneScadenzaTipo vo) {
		if(vo == null) return null;
		PianoComunicazioneScadenzaTipo pccVO = new PianoComunicazioneScadenzaTipo();
		pccVO.setDataScadenza(vo.getDataScadenza());
		pccVO.setImportoInScadenza(vo.getImportoInScadenza());
		pccVO.setImportoIniziale(vo.getImportoIniziale());
		pccVO.setPagatoRicontabilizzato(vo.getPagatoRicontabilizzato());
		return pccVO;
	}

	private static ListaCertificazioneTipo toProxy(it.tesoro.fatture.ListaCertificazioneTipo vo) {
		if(vo == null) return null;
		ListaCertificazioneTipo pccVO = new ListaCertificazioneTipo();
		pccVO.getCertificazione().addAll(toProxyCertificazioneTipo(vo.getCertificazione()));
		return pccVO;
	}
	
	private static List<CertificazioneTipo> toProxyCertificazioneTipo(List<it.tesoro.fatture.CertificazioneTipo> vo) {
		if(vo == null) return null;
		List<CertificazioneTipo> pccVO = new ArrayList<CertificazioneTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.CertificazioneTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	private static CertificazioneTipo toProxy(it.tesoro.fatture.CertificazioneTipo vo) {
		if(vo == null) return null;
		CertificazioneTipo pccVO = new CertificazioneTipo();
		pccVO.setNumeroIstanza(vo.getNumeroIstanza());
		pccVO.setNumeroCertificazione(vo.getNumeroCertificazione());
		return pccVO;
	}


	private static DatiCessionarioTipo toProxy(it.tesoro.fatture.DatiCessionarioTipo vo) {
		if(vo == null) return null;
		DatiCessionarioTipo pccVO = new DatiCessionarioTipo();
		pccVO.setCodiceFiscaleGiuridicoTipo(vo.getCodiceFiscaleGiuridicoTipo());
		pccVO.setDenominazione(vo.getDenominazione());
		pccVO.setDataCessione(vo.getDataCessione());
		return pccVO;
	}

	private static DatiRispostaDownloadDocumentoReadTipo toProxy(it.tesoro.fatture.DatiRispostaDownloadDocumentoReadTipo vo) {
		if(vo == null) return null;
		DatiRispostaDownloadDocumentoReadTipo pccVO = new DatiRispostaDownloadDocumentoReadTipo();
		pccVO.setDocumento(toProxy(vo.getDocumento()));
		pccVO.setDataFineElaborazione(new Date());
		pccVO.setEsitoTrasmissione(EsitoOkKoTipo.fromValue(vo.getEsitoTrasmissione().value()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		return pccVO;
	}

	private static Allegato toProxy(it.tesoro.fatture.Allegato vo) {
		if(vo == null) return null;
		Allegato pccVO = new Allegato();
		pccVO.setFileAllegato(vo.getFileAllegato());
		pccVO.setNomeFile(vo.getNomeFile());
		pccVO.setContentType(vo.getContentType());
		return pccVO;
	}

	private static DatiRispostaOperazioneContabileQueryTipo toProxy(it.tesoro.fatture.DatiRispostaOperazioneContabileQueryTipo vo) {
		if(vo == null) return null;
		DatiRispostaOperazioneContabileQueryTipo pccVO = new DatiRispostaOperazioneContabileQueryTipo();
		pccVO.setEsitoTrasmissione(toProxy(vo.getEsitoTrasmissione()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		pccVO.setDataFineElaborazione(vo.getDataFineElaborazione());
		pccVO.setEsitoElaborazioneTransazione(vo.getEsitoElaborazioneTransazione());
		pccVO.setListaErroreElaborazione(toProxy(vo.getListaErroreElaborazione()));
		return pccVO;
	}
	
	private static ListaErroreElaborazioneOperazioneTipo toProxy(it.tesoro.fatture.ListaErroreElaborazioneOperazioneTipo vo) {
		if(vo == null) return null;
		ListaErroreElaborazioneOperazioneTipo pccVO = new ListaErroreElaborazioneOperazioneTipo();
		pccVO.getErroreElaborazione().addAll(toProxyErroreElaborazioneTipo(vo.getErroreElaborazione()));
		return pccVO;
	}
	
	private static List<ErroreElaborazioneOperazioneTipo> toProxyErroreElaborazioneTipo(List<it.tesoro.fatture.ErroreElaborazioneOperazioneTipo> vo) {
		if(vo == null) return null;
		List<ErroreElaborazioneOperazioneTipo> pccVO = new ArrayList<ErroreElaborazioneOperazioneTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.ErroreElaborazioneOperazioneTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	private static ErroreElaborazioneOperazioneTipo toProxy(it.tesoro.fatture.ErroreElaborazioneOperazioneTipo vo) {
		if(vo == null) return null;
		ErroreElaborazioneOperazioneTipo pccVO = new ErroreElaborazioneOperazioneTipo();
		pccVO.setTipoOperazione(toProxy(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(vo.getProgressivoOperazione());
		pccVO.setCodiceEsitoElaborazioneOperazione(vo.getCodiceEsitoElaborazioneOperazione());
		pccVO.setDescrizioneEsitoElaborazioneOperazione(vo.getDescrizioneEsitoElaborazioneOperazione());
		return pccVO;
	}

	private static TipoOperazioneTipo toProxy(it.tesoro.fatture.TipoOperazioneTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case CP: return TipoOperazioneTipo.CP;
		case SP: return TipoOperazioneTipo.SP;
		case CS: return TipoOperazioneTipo.CS;
		case CO: return TipoOperazioneTipo.CO;
		case RF: return TipoOperazioneTipo.RF;
		case RC: return TipoOperazioneTipo.RC;
		case CCS: return TipoOperazioneTipo.CCS;
		case SC: return TipoOperazioneTipo.SC;
		default:
			break;
		}
		return null;
	}

	private static ListaErroreTipo toProxy(it.tesoro.fatture.ListaErroreTipo vo) {
		if(vo == null) return null;
		ListaErroreTipo pccVO = new ListaErroreTipo();
		pccVO.getErroreTrasmissione().addAll(toProxyErroreTrasmissioneTipo(vo.getErroreTrasmissione()));
		return pccVO;
	}

	private static List<ErroreTipo> toProxyErroreTrasmissioneTipo(List<it.tesoro.fatture.ErroreTipo> vo) {
		if(vo == null) return null;
		List<ErroreTipo> pccVO = new ArrayList<ErroreTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(it.tesoro.fatture.ErroreTipo obj: vo) {
				pccVO.add(toProxy(obj));
			}
		}
		return pccVO;
	}
	
	private static ErroreTipo toProxy(it.tesoro.fatture.ErroreTipo vo) {
		if(vo == null) return null;
		ErroreTipo pccVO = new ErroreTipo();
		pccVO.setCodice(vo.getCodice());
		pccVO.setDescrizione(vo.getDescrizione());
		return pccVO;
	}

	private static EsitoOkKoTipo toProxy(it.tesoro.fatture.EsitoOkKoTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case OK: return EsitoOkKoTipo.OK;
		case KO: return EsitoOkKoTipo.KO;
		default:
			break;
		}
		return null;
	}

	private static DatiRispostaStatoFatturaReadTipo toProxy(it.tesoro.fatture.DatiRispostaStatoFatturaReadTipo vo) {
		if(vo == null) return null;
		DatiRispostaStatoFatturaReadTipo pccVO = new DatiRispostaStatoFatturaReadTipo();
		pccVO.setDataFineElaborazione(new Date());
		pccVO.setEsitoTrasmissione(EsitoOkKoTipo.fromValue(vo.getEsitoTrasmissione().value()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		pccVO.setStatoContabileFattura(toProxy(vo.getStatoContabileFattura()));
		return pccVO;
	}

	private static StatoContabileFatturaTipo toProxy(it.tesoro.fatture.StatoContabileFatturaTipo vo) {
		if(vo == null) return null;
		StatoContabileFatturaTipo pccVO = new StatoContabileFatturaTipo();
		pccVO.setDatiFornitore(toProxy(vo.getDatiFornitore()));
		pccVO.setDatiAmministrazione(toProxy(vo.getDatiAmministrazione()));
		pccVO.setDatiDistintaTrasmissione(toProxy(vo.getDatiDistintaTrasmissione()));
		pccVO.setDatiDocumento(toProxy(vo.getDatiDocumento()));
		return pccVO;
	}

	private static DettaglioDocumentoReadTipo toProxy(it.tesoro.fatture.DettaglioDocumentoReadTipo vo) {
		if(vo == null) return null;
		DettaglioDocumentoReadTipo pccVO = new DettaglioDocumentoReadTipo();
		pccVO.setStatoDocumento(toProxy(vo.getStatoDocumento()));
		pccVO.setIdFiscaleIVA(vo.getIdFiscaleIVA());
		pccVO.setProgressivoRegistrazione(vo.getProgressivoRegistrazione());
		pccVO.setNumeroProtocollo(vo.getNumeroProtocollo());
		pccVO.setDataRicezione(vo.getDataRicezione());
		pccVO.setTipoDocumento(toProxy(vo.getTipoDocumento()));
		pccVO.setDataEmissione(vo.getDataEmissione());
		pccVO.setNumeroDocumento(vo.getNumeroDocumento());
		pccVO.setDescrizioneCausale(vo.getDescrizioneCausale());
		pccVO.setImportoTotale(vo.getImportoTotale());
		pccVO.setImportoImponibile(vo.getImportoImponibile());
		pccVO.setTotaleImposta(vo.getTotaleImposta());
		pccVO.setSplitPayment(toProxy(vo.getSplitPayment()));
		pccVO.setRipartizioneAttuale(toProxy(vo.getRipartizioneAttuale()));
		return pccVO;
	}

	private static RipartizioneAttualeTipo toProxy(it.tesoro.fatture.RipartizioneAttualeTipo vo) {
		if(vo == null) return null;
		RipartizioneAttualeTipo pccVO = new RipartizioneAttualeTipo();
		pccVO.setImportoLiquidato(vo.getImportoLiquidato());
		pccVO.setImportoNonLiquidato(vo.getImportoNonLiquidato());
		pccVO.setImportoSospeso(vo.getImportoSospeso());
		pccVO.setImportoPagato(vo.getImportoPagato());
		pccVO.setDataCertificazione(vo.getDataCertificazione());
		pccVO.setImportoCertificato(vo.getImportoCertificato());
		return pccVO;
	}

	private static FlagSiNoTipo toProxy(it.tesoro.fatture.FlagSiNoTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case SI: return FlagSiNoTipo.SI;
		case NO: return FlagSiNoTipo.NO;
		default:
			break;
		}
		return null;
	}

	private static StatoDocumentoTipo toProxy(it.tesoro.fatture.StatoDocumentoTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case IL: return StatoDocumentoTipo.IL;
		case BO: return StatoDocumentoTipo.BO;
		case PR: return StatoDocumentoTipo.PR;
		case RI: return StatoDocumentoTipo.RI;
		case LA: return StatoDocumentoTipo.LA;
		case RE: return StatoDocumentoTipo.RE;
		default:
			break;
		}
		return null;
	}

	private static TipoDocumentoTipo toProxy(it.tesoro.fatture.TipoDocumentoTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case TD_01: return TipoDocumentoTipo.TD_01;
		case TD_02: return TipoDocumentoTipo.TD_02;
		case TD_03: return TipoDocumentoTipo.TD_03;
		case TD_04: return TipoDocumentoTipo.TD_04;
		case TD_05: return TipoDocumentoTipo.TD_05;
		case TD_06: return TipoDocumentoTipo.TD_06;
		default:
			break;
		}
		return null;
	}

	private static DatiDistintaTrasmissioneTipo toProxy(it.tesoro.fatture.DatiDistintaTrasmissioneTipo vo) {
		if(vo == null) return null;
		DatiDistintaTrasmissioneTipo pccVO = new DatiDistintaTrasmissioneTipo();
		pccVO.setIdentificativoDistinta(vo.getIdentificativoDistinta());
		pccVO.setDataImmissione(vo.getDataImmissione());
		return pccVO;
	}

	private static DatiFornitoreTipo toProxy(it.tesoro.fatture.DatiFornitoreTipo vo) {
		if(vo == null) return null;
		DatiFornitoreTipo pccVO = new DatiFornitoreTipo();
		pccVO.setCodiceFiscale(vo.getCodiceFiscale());
		pccVO.setDenominazione(vo.getDenominazione());
		return pccVO;
	}

	private static DatiAmministrazioneTipo toProxy(it.tesoro.fatture.DatiAmministrazioneTipo vo) {
		if(vo == null) return null;
		DatiAmministrazioneTipo pccVO = new DatiAmministrazioneTipo();
		pccVO.setCodiceFiscale(vo.getCodiceFiscale());
		pccVO.setDenominazione(vo.getDenominazione());
		pccVO.setCodiceUfficioPCC(vo.getCodiceUfficioPCC());
		pccVO.setDenominazioneUfficioPCC(vo.getDenominazioneUfficioPCC());
		pccVO.setCodiceUnivocoUfficioIPA(vo.getCodiceUnivocoUfficioIPA());
		pccVO.setDenominazioneUfficioIPA(vo.getDenominazioneUfficioIPA());
		return pccVO;
	}

	private static DatiRispostaInserimentoFatturaQueryTipo toProxy(it.tesoro.fatture.DatiRispostaInserimentoFatturaQueryTipo vo) {
		if(vo == null) return null;
		DatiRispostaInserimentoFatturaQueryTipo pccVO = new DatiRispostaInserimentoFatturaQueryTipo();
		pccVO.setCodiceRegistrazioneFattura(vo.getCodiceRegistrazioneFattura());
		pccVO.setEsitoTrasmissione(toProxy(vo.getEsitoTrasmissione()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		pccVO.setDataFineElaborazione(vo.getDataFineElaborazione());
		pccVO.setEsitoElaborazioneTransazione(vo.getEsitoElaborazioneTransazione());
		pccVO.setDescrizioneElaborazioneTransazione(vo.getDescrizioneElaborazioneTransazione());
		
		return pccVO;
	}

	private static DatiRispostaProxyTipo toProxy(it.tesoro.fatture.DatiRispostaProxyTipo vo) {
		if(vo == null) return null;
		DatiRispostaProxyTipo pccVO = new DatiRispostaProxyTipo();
		pccVO.setIdentificativoTransazionePCC(vo.getIdentificativoTransazionePCC());
		pccVO.setEsitoTrasmissione(EsitoOkKoTipo.fromValue(vo.getEsitoTrasmissione().value()));
		pccVO.setListaErroreTrasmissione(toProxy(vo.getListaErroreTrasmissione()));
		return pccVO;
	}

	public static ProxyOperazioneContabileRichiestaTipo newCancellazioneScadenze(ProxyOperazioneContabileRichiestaTipo proxyOperazioneContabileRichiestaTipo) {
		ProxyOperazioneContabileRichiestaTipo pccVO = new ProxyOperazioneContabileRichiestaTipo();
		pccVO.setTestataRichiesta(newTestata(proxyOperazioneContabileRichiestaTipo.getTestataRichiesta()));
		DatiRichiestaOperazioneContabileProxyTipo dati = new DatiRichiestaOperazioneContabileProxyTipo();
		dati.setIdentificazioneSDI(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getIdentificazioneSDI());
		dati.setIdentificazionePCC(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getIdentificazionePCC());
		dati.setIdentificazioneGenerale(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getIdentificazioneGenerale());
		OperazioneTipo operazione = new OperazioneTipo();
		operazione.setProgressivoOperazione(1);
		operazione.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
		operazione.setTipoOperazione(TipoOperazioneTipo.CCS);
		ListaOperazioneTipo listaOp = new ListaOperazioneTipo();
		listaOp.setOperazione(operazione);
		dati.setListaOperazione(listaOp);
		
		pccVO.setDatiRichiesta(dati);
		return pccVO;
	}


	private static TestataAsyncRichiestaTipo newTestata(TestataAsyncRichiestaTipo testataRichiesta) {
		TestataAsyncRichiestaTipo testataCanc = new TestataAsyncRichiestaTipo();
		testataCanc.setIdentificativoTransazionePA(testataRichiesta.getIdentificativoTransazionePA() + "CANC");
		testataCanc.setUtenteRichiedente(testataRichiesta.getUtenteRichiedente());
		return testataCanc;
	}

	public static OperazioneTipo toProxyOperazioneList(List<it.tesoro.fatture.OperazioneTipo> voList) {
		
		if(voList == null || voList.isEmpty()) return null;
		
		it.tesoro.fatture.OperazioneTipo vo = voList.get(0);
		
		int index = 0;
		if(vo.getTipoOperazione().equals(it.tesoro.fatture.TipoOperazioneTipo.CCS)) {
			OperazioneTipo op = new OperazioneTipo();
			op.setProgressivoOperazione(1);
			op.setTipoOperazione(TipoOperazioneTipo.CCS);
			op.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
			return op;
		} else if(vo.getTipoOperazione().equals(it.tesoro.fatture.TipoOperazioneTipo.SC)) {
			OperazioneTipo op = new OperazioneTipo();
			op.setProgressivoOperazione(1);
			op.setTipoOperazione(TipoOperazioneTipo.SC);
			op.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
			return op;
		} else if(vo.getStrutturaDatiOperazione().getComunicazioneScadenza() != null) {
				return toProxy(vo, vo.getStrutturaDatiOperazione().getComunicazioneScadenza(), index++);
		} else if(vo.getStrutturaDatiOperazione().getRifiutoFattura() != null) {
				return toProxy(vo, vo.getStrutturaDatiOperazione().getRifiutoFattura(), index++);
		} else if(vo.getStrutturaDatiOperazione().getPagamento() != null) {
				return toProxy(vo, vo.getStrutturaDatiOperazione().getPagamento(), index++);
		} else if(vo.getStrutturaDatiOperazione().getPagamentoStorno() != null) {
				return toProxy(vo, vo.getStrutturaDatiOperazione().getPagamentoStorno(), index++);
		} else if(vo.getStrutturaDatiOperazione().getRicezioneFattura() != null) {
				return toProxy(vo, vo.getStrutturaDatiOperazione().getRicezioneFattura(), index++);
		} else if(vo.getStrutturaDatiOperazione().getRifiutoFattura() != null) {
				return toProxy(vo, vo.getStrutturaDatiOperazione().getRifiutoFattura(), index++);
		} else if(vo.getStrutturaDatiOperazione().getListaContabilizzazione() != null) {
			return toProxy(vo, vo.getStrutturaDatiOperazione().getListaContabilizzazione(), index++);
		}
		return null;
	}
	
	private static OperazioneTipo toProxy(it.tesoro.fatture.OperazioneTipo vo, ListaContabilizzazioneTipo lista, int index) {
		if(lista == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toProxy(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		
		List<ContabilizzazioneTipo> contabilizzazione = lista.getContabilizzazione();
		if(contabilizzazione != null && !contabilizzazione.isEmpty()) {
			for(ContabilizzazioneTipo cont : contabilizzazione) {
				strutturaDatiOperazione.getListaContabilizzazione().add(toProxy(cont));
			}
		}
		
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toProxy(it.tesoro.fatture.OperazioneTipo vo, RicezioneFatturaTipo ricezioneFattura, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toProxy(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.getRicezioneFattura().add(toProxy(ricezioneFattura));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static org.govmix.pcc.fatture.RicezioneFatturaTipo toProxy(RicezioneFatturaTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.RicezioneFatturaTipo pccVO = new org.govmix.pcc.fatture.RicezioneFatturaTipo();
		pccVO.setNumeroProtocolloEntrata(vo.getNumeroProtocolloEntrata());
		pccVO.setDataRicezione(vo.getDataRicezione());
		pccVO.setNote(vo.getNote());
		return pccVO;
	}
	
	private static OperazioneTipo toProxy(it.tesoro.fatture.OperazioneTipo vo, PagamentoTipo pagamento, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toProxy(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.getPagamento().add(toProxy(pagamento));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toProxy(it.tesoro.fatture.OperazioneTipo vo, PagamentoStornoTipo pagamentoStorno, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toProxy(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.getPagamentoStorno().add(toProxy(pagamentoStorno));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toProxy(it.tesoro.fatture.OperazioneTipo vo, ComunicazioneScadenzaTipo comunicazioneScadenza, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toProxy(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.getComunicazioneScadenza().add(toProxy(comunicazioneScadenza));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toProxy(it.tesoro.fatture.OperazioneTipo vo, RifiutoFatturaTipo rifiutoFattura, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toProxy(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.getRifiutoFattura().add(toProxy(rifiutoFattura));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static org.govmix.pcc.fatture.RifiutoFatturaTipo toProxy(RifiutoFatturaTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.RifiutoFatturaTipo pccVO = new org.govmix.pcc.fatture.RifiutoFatturaTipo();
		pccVO.setDataRifiuto(vo.getDataRifiuto());
		pccVO.setDescrizioneRifiuto(vo.getDescrizioneRifiuto());
		return pccVO;
	}


	private static org.govmix.pcc.fatture.ContabilizzazioneTipo toProxy(ContabilizzazioneTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.ContabilizzazioneTipo pccVO = new org.govmix.pcc.fatture.ContabilizzazioneTipo();
		pccVO.setImportoMovimento(vo.getImportoMovimento());
		pccVO.setNaturaSpesa(toProxy(vo.getNaturaSpesa()));
		pccVO.setCapitoliSpesa(vo.getCapitoliSpesa());
		pccVO.setOperazione(toProxy(vo.getOperazione()));
		
		try {
			TransformUtils.populateContabilizzazione(pccVO, vo.getDescrizione());
		} catch (Exception e) {
			pccVO.setDescrizione(vo.getDescrizione());
		}

		pccVO.setEstremiImpegno(vo.getEstremiImpegno());
		pccVO.setCodiceCIG(vo.getCodiceCIG());
		pccVO.setCodiceCUP(vo.getCodiceCUP());
		return pccVO;
	}
	
	private static org.govmix.pcc.fatture.OperazioneContabilizzazioneTipo toProxy(OperazioneContabilizzazioneTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.OperazioneContabilizzazioneTipo pccVO = new org.govmix.pcc.fatture.OperazioneContabilizzazioneTipo();
		pccVO.setStatoDebito(toProxy(vo.getStatoDebito()));
		if(vo.getCausale() != null)
			pccVO.setCausale(vo.getCausale());
		return pccVO;
	}

	private static org.govmix.pcc.fatture.StatoDebitoTipo toProxy(StatoDebitoTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case LIQ: return org.govmix.pcc.fatture.StatoDebitoTipo.LIQ;
		case SOSP: return org.govmix.pcc.fatture.StatoDebitoTipo.SOSP;
		case NOLIQ: return org.govmix.pcc.fatture.StatoDebitoTipo.NOLIQ;
		default:
			break;
		}
		return null;
	}

	private static org.govmix.pcc.fatture.NaturaSpesaContabiliTipo toProxy(NaturaSpesaContabiliTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case CO: return org.govmix.pcc.fatture.NaturaSpesaContabiliTipo.CO;
		case CA: return org.govmix.pcc.fatture.NaturaSpesaContabiliTipo.CA;
		case NA: return org.govmix.pcc.fatture.NaturaSpesaContabiliTipo.NA;
		default:
			break;
		}
		return null;
	}

	private static org.govmix.pcc.fatture.ComunicazioneScadenzaTipo toProxy(ComunicazioneScadenzaTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.ComunicazioneScadenzaTipo pccVO = new org.govmix.pcc.fatture.ComunicazioneScadenzaTipo();
		pccVO.setComunicaScadenza(toProxy(vo.getComunicaScadenza()));
		pccVO.setImporto(vo.getImporto());
		pccVO.setDataScadenza(vo.getDataScadenza());
		return pccVO;
	}

	private static org.govmix.pcc.fatture.ComunicaScadenzaTipo toProxy(ComunicaScadenzaTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case SI: return org.govmix.pcc.fatture.ComunicaScadenzaTipo.SI;
		default:
			break;
		}
		return null;
	}

	private static org.govmix.pcc.fatture.PagamentoStornoTipo toProxy(PagamentoStornoTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.PagamentoStornoTipo pccVO = new org.govmix.pcc.fatture.PagamentoStornoTipo();
		pccVO.setImportoStorno(vo.getImportoStorno());
		pccVO.setNaturaSpesa(toProxy(vo.getNaturaSpesa()));
		pccVO.setIdFiscaleIVABeneficiario(vo.getIdFiscaleIVABeneficiario());
		return pccVO;
	}

	private static org.govmix.pcc.fatture.NaturaSpesaTipo toProxy(NaturaSpesaTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case CO: return org.govmix.pcc.fatture.NaturaSpesaTipo.CO;
		case CA: return org.govmix.pcc.fatture.NaturaSpesaTipo.CA;
		default:
			break;
		}
		return null;
	}

	private static org.govmix.pcc.fatture.PagamentoTipo toProxy(PagamentoTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.PagamentoTipo pccVO = new org.govmix.pcc.fatture.PagamentoTipo();
		pccVO.setImportoPagato(vo.getImportoPagato());
		pccVO.setNaturaSpesa(toProxy(vo.getNaturaSpesa()));
		pccVO.setCapitoliSpesa(vo.getCapitoliSpesa());
		pccVO.setEstremiImpegno(vo.getEstremiImpegno());
		pccVO.setMandatoPagamento(toProxy(vo.getMandatoPagamento()));
		pccVO.setIdFiscaleIVABeneficiario(vo.getIdFiscaleIVABeneficiario());
		pccVO.setCodiceCIG(vo.getCodiceCIG());
		pccVO.setCodiceCUP(vo.getCodiceCUP());
		pccVO.setDescrizione(vo.getDescrizione());
		return pccVO;
	}

	private static org.govmix.pcc.fatture.MandatoPagamentoTipo toProxy(MandatoPagamentoTipo vo) {
		if(vo == null) return null;
		org.govmix.pcc.fatture.MandatoPagamentoTipo pccVO = new org.govmix.pcc.fatture.MandatoPagamentoTipo();
		pccVO.setNumero(vo.getNumero());
		pccVO.setDataMandatoPagamento(vo.getDataMandatoPagamento());
		return pccVO;
	}

}
