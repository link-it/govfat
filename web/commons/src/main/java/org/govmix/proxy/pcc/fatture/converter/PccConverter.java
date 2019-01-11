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
package org.govmix.proxy.pcc.fatture.converter;

import it.tesoro.fatture.Allegato;
import it.tesoro.fatture.CodiceEsenzioneIVATipo;
import it.tesoro.fatture.ComunicaScadenzaTipo;
import it.tesoro.fatture.ComunicazioneScadenzaTipo;
import it.tesoro.fatture.ContabilizzazioneTipo;
import it.tesoro.fatture.DatiAmministrazioneInsFattTipo;
import it.tesoro.fatture.DatiFornitoreInsFattTipo;
import it.tesoro.fatture.DatiGeneraliFatturaTipo;
import it.tesoro.fatture.DatiPagamentoTipo;
import it.tesoro.fatture.DatiRichiestaDatiFatturaProxyTipo;
import it.tesoro.fatture.DatiRichiestaDownloadDocumentoReadTipo;
import it.tesoro.fatture.DatiRichiestaElencoMovimentiErarioReadTipo;
import it.tesoro.fatture.DatiRichiestaInserimentoFatturaProxyTipo;
import it.tesoro.fatture.DatiRichiestaInterrogazioneEsitiQueryTipo;
import it.tesoro.fatture.DatiRichiestaOperazioneContabileProxyTipo;
import it.tesoro.fatture.DatiRichiestaPagamentoIvaProxyTipo;
import it.tesoro.fatture.DettagliPagamentoTipo;
import it.tesoro.fatture.DettaglioMovimentoErarioTipo;
import it.tesoro.fatture.DettaglioPagamentoTipo;
import it.tesoro.fatture.DistribuzioneCupCigTipo;
import it.tesoro.fatture.FlagSiNoTipo;
import it.tesoro.fatture.ForzaturaImmissioneTipo;
import it.tesoro.fatture.IdentificativoTransazioneTipo;
import it.tesoro.fatture.IdentificazioneGeneraleTipo;
import it.tesoro.fatture.IdentificazioneSDITipo;
import it.tesoro.fatture.IntervalloDateMovErarioIvaTipo;
import it.tesoro.fatture.IntervalloDateTipo;
import it.tesoro.fatture.ListaContabilizzazioneTipo;
import it.tesoro.fatture.ListaDettagliPagamentoTipo;
import it.tesoro.fatture.ListaDettaglioPagamentoTipo;
import it.tesoro.fatture.ListaDistribuzioneCupCigTipo;
import it.tesoro.fatture.ListaIdentificativoTransazionePCCTipo;
import it.tesoro.fatture.ListaIdentificativoTransazioneTipo;
import it.tesoro.fatture.ListaOperazioneTipo;
import it.tesoro.fatture.ListaRiepilogoAliquoteTipo;
import it.tesoro.fatture.MandatoPagamentoTipo;
import it.tesoro.fatture.NaturaSpesaContabiliTipo;
import it.tesoro.fatture.NaturaSpesaTipo;
import it.tesoro.fatture.OperazioneContabilizzazioneTipo;
import it.tesoro.fatture.OperazioneTipo;
import it.tesoro.fatture.PagamentoIVATipo;
import it.tesoro.fatture.PagamentoStornoTipo;
import it.tesoro.fatture.PagamentoTipo;
import it.tesoro.fatture.ProxyDatiFatturaRichiestaTipo;
import it.tesoro.fatture.ProxyInserimentoFatturaRichiestaTipo;
import it.tesoro.fatture.ProxyOperazioneContabileRichiestaTipo;
import it.tesoro.fatture.ProxyPagamentoIvaRichiestaTipo;
import it.tesoro.fatture.QueryDatiFatturaRichiestaTipo;
import it.tesoro.fatture.QueryInserimentoFatturaRichiestaTipo;
import it.tesoro.fatture.QueryInterrogazioneEsitiRichiestaTipo;
import it.tesoro.fatture.QueryOperazioneContabileRichiestaTipo;
import it.tesoro.fatture.QueryPagamentoIvaRichiestaTipo;
import it.tesoro.fatture.ReadDownloadDocumentoRichiestaTipo;
import it.tesoro.fatture.ReadElencoMovimentiErarioIvaRichiestaTipo;
import it.tesoro.fatture.ReadStatoFatturaRichiestaTipo;
import it.tesoro.fatture.RicezioneFatturaTipo;
import it.tesoro.fatture.RiepilogoAliquoteTipo;
import it.tesoro.fatture.RifiutoFatturaTipo;
import it.tesoro.fatture.StatoDebitoTipo;
import it.tesoro.fatture.StornoPagamentoIVATipo;
import it.tesoro.fatture.StrutturaDatiOperazioneTipo;
import it.tesoro.fatture.TipoDocumentoTipo;
import it.tesoro.fatture.TipoOperazioneTipo;
import it.tesoro.fatture.TipologiaMovimentoErarioTipo;

import java.util.ArrayList;
import java.util.List;

import org.govmix.pcc.fatture.TestataAsyncSenzaFatturaRichiestaTipo;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanResponse;


public class PccConverter {

	public static ProxyPagamentoIvaRichiestaTipo toPCC(org.govmix.pcc.fatture.ProxyPagamentoIvaRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		it.tesoro.fatture.ProxyPagamentoIvaRichiestaTipo pccVO = new it.tesoro.fatture.ProxyPagamentoIvaRichiestaTipo();

		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataProxy(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getTestataRichiesta(), vo.getDatiRichiesta()));

		return pccVO;
	}

	public static ReadStatoFatturaRichiestaTipo toPCC(org.govmix.pcc.fatture.ReadStatoFatturaRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		ReadStatoFatturaRichiestaTipo pccVO = new ReadStatoFatturaRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataRead(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getDatiRichiesta()));
		return pccVO;
	}

	public static ProxyDatiFatturaRichiestaTipo toPCC(org.govmix.pcc.fatture.ProxyDatiFatturaRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		ProxyDatiFatturaRichiestaTipo pccVO = new ProxyDatiFatturaRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataProxy(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getDatiRichiesta()));
		return pccVO;
	}
	
	public static ProxyInserimentoFatturaRichiestaTipo toPCC(org.govmix.pcc.fatture.ProxyInserimentoFatturaRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		ProxyInserimentoFatturaRichiestaTipo pccVO = new ProxyInserimentoFatturaRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataProxy(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getDatiRichiesta()));
		return pccVO;
	}

	public static ProxyOperazioneContabileRichiestaTipo toPCC(org.govmix.pcc.fatture.ProxyOperazioneContabileRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		ProxyOperazioneContabileRichiestaTipo pccVO = new ProxyOperazioneContabileRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataProxy(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getDatiRichiesta()));
		return pccVO;
	}
	
	public static ReadDownloadDocumentoRichiestaTipo toPCC(org.govmix.pcc.fatture.ReadDownloadDocumentoRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		ReadDownloadDocumentoRichiestaTipo pccVO = new ReadDownloadDocumentoRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataRead(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getDatiRichiesta()));
		return pccVO;
	}

	public static ReadElencoMovimentiErarioIvaRichiestaTipo toPCC(org.govmix.pcc.fatture.ReadElencoMovimentiErarioIvaRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		ReadElencoMovimentiErarioIvaRichiestaTipo pccVO = new ReadElencoMovimentiErarioIvaRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataRead(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getDatiRichiesta(), vo.getTestataRichiesta().getIdentificativoPCCUfficio()));
		return pccVO;
	}
	
	public static QueryInterrogazioneEsitiRichiestaTipo toPCC(org.govmix.pcc.fatture.QueryInterrogazioneEsitiRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		QueryInterrogazioneEsitiRichiestaTipo pccVO = new QueryInterrogazioneEsitiRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataBase(vo.getTestataRichiesta(), beanResponse));
		pccVO.setDatiRichiesta(toPCC(vo.getDatiRichiesta()));
		return pccVO;
	}
	

	public static QueryInserimentoFatturaRichiestaTipo toPCCInserimentoFattura(PccTraccia traccia, String idPccTrasmissione) {
		QueryInserimentoFatturaRichiestaTipo pccVO = new QueryInserimentoFatturaRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataQuery(traccia, idPccTrasmissione));
		return pccVO;
	}

	public static QueryOperazioneContabileRichiestaTipo toPCCOperazioneContabile(PccTraccia traccia, String idPccTrasmissione) {
		QueryOperazioneContabileRichiestaTipo pccVO = new QueryOperazioneContabileRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataQuery(traccia, idPccTrasmissione));
		return pccVO;
	}

	public static QueryDatiFatturaRichiestaTipo toPCCDatiFattura(PccTraccia traccia, String idPccTrasmissione) {
		QueryDatiFatturaRichiestaTipo pccVO = new QueryDatiFatturaRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataQuery(traccia, idPccTrasmissione));
		return pccVO;
	}


	public static QueryPagamentoIvaRichiestaTipo toPCCPagamentoIva(PccTraccia traccia, String idPccTrasmissione) {
		QueryPagamentoIvaRichiestaTipo pccVO = new QueryPagamentoIvaRichiestaTipo();
		pccVO.setTestataRichiesta(TestataConverter.toPCCTestataQuery(traccia, idPccTrasmissione));
		return pccVO;
	}


	/**privati **/

	private static DatiRichiestaInterrogazioneEsitiQueryTipo toPCC(org.govmix.pcc.fatture.DatiRichiestaInterrogazioneEsitiQueryTipo vo) {
		if(vo == null) return null;
		DatiRichiestaInterrogazioneEsitiQueryTipo pccVO = new DatiRichiestaInterrogazioneEsitiQueryTipo();
		pccVO.setListaTransazionePA(toPCC(vo.getListaTransazionePA()));
		pccVO.setListaTransazionePCC(toPCC(vo.getListaTransazionePCC()));
		pccVO.setIntervalloDate(toPCC(vo.getIntervalloDate()));
		pccVO.setFlagEsitiNonComunicati(toPCC(vo.getFlagEsitiNonComunicati()));
		pccVO.setNumeroPagina(vo.getNumeroPagina());
		return pccVO;
	}
	
	private static IntervalloDateTipo toPCC(org.govmix.pcc.fatture.IntervalloDateTipo vo) {
		if(vo == null) return null;
		IntervalloDateTipo pccVO = new IntervalloDateTipo();
		pccVO.setInizioDataRichiestaElaborazione(vo.getInizioDataRichiestaElaborazione());
		pccVO.setFineDataRichiestaElaborazione(vo.getFineDataRichiestaElaborazione());
		return pccVO;
	}

	private static ListaIdentificativoTransazionePCCTipo toPCC(org.govmix.pcc.fatture.ListaIdentificativoTransazionePCCTipo vo) {
		if(vo == null) return null;
		ListaIdentificativoTransazionePCCTipo pccVO = new ListaIdentificativoTransazionePCCTipo();
		pccVO.getIdentificativoTransazionePCC().addAll(vo.getIdentificativoTransazionePCC());
		return pccVO;
	}


	private static ListaIdentificativoTransazioneTipo toPCC(org.govmix.pcc.fatture.ListaIdentificativoTransazioneTipo vo) {
		if(vo == null) return null;
		ListaIdentificativoTransazioneTipo pccVO = new ListaIdentificativoTransazioneTipo();
		pccVO.getTransazione().addAll(toPCCIdentificativoTransazioneTipo(vo.getTransazione()));
		return pccVO;
	}
	
	private static List<IdentificativoTransazioneTipo> toPCCIdentificativoTransazioneTipo(List<org.govmix.pcc.fatture.IdentificativoTransazioneTipo> vo) {
		if(vo == null) return null;
		List<IdentificativoTransazioneTipo> pccVO = new ArrayList<IdentificativoTransazioneTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(org.govmix.pcc.fatture.IdentificativoTransazioneTipo obj: vo) {
				pccVO.add(toPCC(obj));
			}
		}
		return pccVO;
	}
	
	private static IdentificativoTransazioneTipo toPCC(org.govmix.pcc.fatture.IdentificativoTransazioneTipo vo) {
		if(vo == null) return null;
		IdentificativoTransazioneTipo pccVO = new IdentificativoTransazioneTipo();
		pccVO.setIdentificativoTransazionePA(vo.getIdentificativoTransazionePA());
		pccVO.setIdentificativoPCCAmministrazione(vo.getIdentificativoPCCAmministrazione());
		return pccVO;
	}


	private static DatiRichiestaElencoMovimentiErarioReadTipo toPCC(org.govmix.pcc.fatture.DatiRichiestaElencoMovimentiErarioReadTipo vo, String idPccUfficio) {
		if(vo == null) return null;
		DatiRichiestaElencoMovimentiErarioReadTipo pccVO = new DatiRichiestaElencoMovimentiErarioReadTipo();
		pccVO.setIdentificativoPCCUfficio(idPccUfficio);
		pccVO.setIntervalloDate(toPCC(vo.getIntervalloDate()));
		pccVO.setNumeroPagina(vo.getNumeroPagina());
		return pccVO;
	}

	private static IntervalloDateMovErarioIvaTipo toPCC(org.govmix.pcc.fatture.IntervalloDateMovErarioIvaTipo vo) {
		if(vo == null) return null;
		IntervalloDateMovErarioIvaTipo pccVO = new IntervalloDateMovErarioIvaTipo();
		pccVO.setInizioDataRicerca(vo.getInizioDataRicerca());
		pccVO.setFineDataRicerca(vo.getFineDataRicerca());
		return pccVO;
	}

	private static DatiRichiestaDownloadDocumentoReadTipo toPCC(org.govmix.pcc.fatture.DatiRichiestaDownloadDocumentoReadTipo vo) {
		if(vo == null) return null;
		DatiRichiestaDownloadDocumentoReadTipo pccVO = new DatiRichiestaDownloadDocumentoReadTipo();
		pccVO.setIdentificativoAllegato(vo.getIdentificativoAllegato());
		return pccVO;
	}

	private static DatiRichiestaOperazioneContabileProxyTipo toPCC(org.govmix.pcc.fatture.DatiRichiestaOperazioneContabileProxyTipo vo) {
		if(vo == null) return null;
		DatiRichiestaOperazioneContabileProxyTipo pccVO = new DatiRichiestaOperazioneContabileProxyTipo();
		pccVO.setIdentificazionePCC(vo.getIdentificazionePCC());
		pccVO.setIdentificazioneSDI(toPCC(vo.getIdentificazioneSDI()));
		pccVO.setIdentificazioneGenerale(toPCC(vo.getIdentificazioneGenerale()));
		pccVO.setListaOperazione(toPCC(vo.getListaOperazione()));
		return pccVO;
	}
	
	private static ListaOperazioneTipo toPCC(org.govmix.pcc.fatture.ListaOperazioneTipo vo) {
		if(vo == null) return null;
		ListaOperazioneTipo pccVO = new ListaOperazioneTipo();
		pccVO.getOperazione().addAll(toPCCOperazioneTipo(vo.getOperazione()));
		return pccVO;
	}
	
	private static List<OperazioneTipo> toPCCOperazioneTipo(org.govmix.pcc.fatture.OperazioneTipo vo) {
		if(vo == null) return null;
		List<OperazioneTipo> pccVO = new ArrayList<OperazioneTipo>();
		
		
		int index = 0;
		if(vo.getTipoOperazione().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.CCS)) {
			OperazioneTipo op = new OperazioneTipo();
			op.setProgressivoOperazione(1);
			op.setTipoOperazione(TipoOperazioneTipo.CCS);
			op.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
			pccVO.add(op);
		} else if(vo.getTipoOperazione().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.SC)) {
			OperazioneTipo op = new OperazioneTipo();
			op.setProgressivoOperazione(1);
			op.setTipoOperazione(TipoOperazioneTipo.SC);
			op.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
			pccVO.add(op);
		} else if(vo.getStrutturaDatiOperazione().getComunicazioneScadenza() != null && !vo.getStrutturaDatiOperazione().getComunicazioneScadenza().isEmpty()) {
			for(org.govmix.pcc.fatture.ComunicazioneScadenzaTipo op: vo.getStrutturaDatiOperazione().getComunicazioneScadenza()) {
				pccVO.add(toPCC(vo, op, index++));
			}
		} else if(vo.getStrutturaDatiOperazione().getRifiutoFattura() != null && !vo.getStrutturaDatiOperazione().getRifiutoFattura().isEmpty()) {
			for(org.govmix.pcc.fatture.RifiutoFatturaTipo op: vo.getStrutturaDatiOperazione().getRifiutoFattura()) {
				pccVO.add(toPCC(vo, op, index++));
			}
		} else if(vo.getStrutturaDatiOperazione().getPagamento() != null && !vo.getStrutturaDatiOperazione().getPagamento().isEmpty()) {
			for(org.govmix.pcc.fatture.PagamentoTipo op: vo.getStrutturaDatiOperazione().getPagamento()) {
				pccVO.add(toPCC(vo, op, index++));
			}
		} else if(vo.getStrutturaDatiOperazione().getPagamentoStorno() != null && !vo.getStrutturaDatiOperazione().getPagamentoStorno().isEmpty()) {
			for(org.govmix.pcc.fatture.PagamentoStornoTipo op: vo.getStrutturaDatiOperazione().getPagamentoStorno()) {
				pccVO.add(toPCC(vo, op, index++));
			}
		} else if(vo.getStrutturaDatiOperazione().getRicezioneFattura() != null && !vo.getStrutturaDatiOperazione().getRicezioneFattura().isEmpty()) {
			for(org.govmix.pcc.fatture.RicezioneFatturaTipo op: vo.getStrutturaDatiOperazione().getRicezioneFattura()) {
				pccVO.add(toPCC(vo, op, index++));
			}
		} else if(vo.getStrutturaDatiOperazione().getRifiutoFattura() != null && !vo.getStrutturaDatiOperazione().getRifiutoFattura().isEmpty()) {
			for(org.govmix.pcc.fatture.RifiutoFatturaTipo op: vo.getStrutturaDatiOperazione().getRifiutoFattura()) {
				pccVO.add(toPCC(vo, op, index++));
			}
		} else if(vo.getStrutturaDatiOperazione().getListaContabilizzazione() != null && !vo.getStrutturaDatiOperazione().getListaContabilizzazione().isEmpty()) {
			pccVO.add(toPCC(vo, vo.getStrutturaDatiOperazione().getListaContabilizzazione(), index++));
		}
		return pccVO;
	}
	
	private static OperazioneTipo toPCC(org.govmix.pcc.fatture.OperazioneTipo vo, List<org.govmix.pcc.fatture.ContabilizzazioneTipo> contabilizzazione, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toPCC(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		ListaContabilizzazioneTipo listaContabilizzazione = new ListaContabilizzazioneTipo();
		listaContabilizzazione.getContabilizzazione().addAll(toPCCContabilizzazioneTipo(contabilizzazione));
		strutturaDatiOperazione.setListaContabilizzazione(listaContabilizzazione);
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toPCC(org.govmix.pcc.fatture.OperazioneTipo vo, org.govmix.pcc.fatture.RicezioneFatturaTipo ricezioneFattura, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toPCC(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.setRicezioneFattura(toPCC(ricezioneFattura));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toPCC(org.govmix.pcc.fatture.OperazioneTipo vo, org.govmix.pcc.fatture.PagamentoTipo pagamento, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toPCC(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.setPagamento(toPCC(pagamento));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toPCC(org.govmix.pcc.fatture.OperazioneTipo vo, org.govmix.pcc.fatture.PagamentoStornoTipo pagamentoStorno, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toPCC(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.setPagamentoStorno(toPCC(pagamentoStorno));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toPCC(org.govmix.pcc.fatture.OperazioneTipo vo, org.govmix.pcc.fatture.ComunicazioneScadenzaTipo comunicazioneScadenza, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toPCC(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.setComunicazioneScadenza(toPCC(comunicazioneScadenza));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static OperazioneTipo toPCC(org.govmix.pcc.fatture.OperazioneTipo vo, org.govmix.pcc.fatture.RifiutoFatturaTipo rifiutoFattura, int index) {
		if(vo == null) return null;
		OperazioneTipo pccVO = new OperazioneTipo();
		pccVO.setTipoOperazione(toPCC(vo.getTipoOperazione()));
		pccVO.setProgressivoOperazione(index);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();
		strutturaDatiOperazione.setRifiutoFattura(toPCC(rifiutoFattura));
		pccVO.setStrutturaDatiOperazione(strutturaDatiOperazione);
		return pccVO;
	}
	
	private static RifiutoFatturaTipo toPCC(org.govmix.pcc.fatture.RifiutoFatturaTipo vo) {
		if(vo == null) return null;
		RifiutoFatturaTipo pccVO = new RifiutoFatturaTipo();
		pccVO.setDataRifiuto(vo.getDataRifiuto());
		pccVO.setDescrizioneRifiuto(vo.getDescrizioneRifiuto());
		return pccVO;
	}

	private static List<ContabilizzazioneTipo> toPCCContabilizzazioneTipo(List<org.govmix.pcc.fatture.ContabilizzazioneTipo> vo) {
		if(vo == null) return null;
		List<ContabilizzazioneTipo> pccVO = new ArrayList<ContabilizzazioneTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(org.govmix.pcc.fatture.ContabilizzazioneTipo obj: vo) {
				pccVO.add(toPCC(obj));
			}
		}
		return pccVO;
	}
	private static ContabilizzazioneTipo toPCC(org.govmix.pcc.fatture.ContabilizzazioneTipo vo) {
		if(vo == null) return null;
		ContabilizzazioneTipo pccVO = new ContabilizzazioneTipo();
		pccVO.setImportoMovimento(vo.getImportoMovimento());
		pccVO.setNaturaSpesa(toPCC(vo.getNaturaSpesa()));
		pccVO.setCapitoliSpesa(vo.getCapitoliSpesa());
		pccVO.setOperazione(toPCC(vo.getOperazione()));
		pccVO.setDescrizione(vo.getDescrizione());
		pccVO.setEstremiImpegno(vo.getEstremiImpegno());
		pccVO.setCodiceCIG(vo.getCodiceCIG());
		pccVO.setCodiceCUP(vo.getCodiceCUP());
		return pccVO;
	}
	
	private static OperazioneContabilizzazioneTipo toPCC(org.govmix.pcc.fatture.OperazioneContabilizzazioneTipo vo) {
		if(vo == null) return null;
		OperazioneContabilizzazioneTipo pccVO = new OperazioneContabilizzazioneTipo();
		pccVO.setStatoDebito(toPCC(vo.getStatoDebito()));
		if(vo.getCausale() != null)
			pccVO.setCausale(vo.getCausale());
		return pccVO;
	}

	private static StatoDebitoTipo toPCC(org.govmix.pcc.fatture.StatoDebitoTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case LIQ: return StatoDebitoTipo.LIQ;
		case SOSP: return StatoDebitoTipo.SOSP;
		case NOLIQ: return StatoDebitoTipo.NOLIQ;
		default:
			break;
		}
		return null;
	}

	private static NaturaSpesaContabiliTipo toPCC(org.govmix.pcc.fatture.NaturaSpesaContabiliTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case CO: return NaturaSpesaContabiliTipo.CO;
		case CA: return NaturaSpesaContabiliTipo.CA;
		case NA: return NaturaSpesaContabiliTipo.NA;
		default:
			break;
		}
		return null;
	}

	private static ComunicazioneScadenzaTipo toPCC(org.govmix.pcc.fatture.ComunicazioneScadenzaTipo vo) {
		if(vo == null) return null;
		ComunicazioneScadenzaTipo pccVO = new ComunicazioneScadenzaTipo();
		pccVO.setComunicaScadenza(toPCC(vo.getComunicaScadenza()));
		pccVO.setImporto(vo.getImporto());
		pccVO.setDataScadenza(vo.getDataScadenza());
		return pccVO;
	}

	private static ComunicaScadenzaTipo toPCC(org.govmix.pcc.fatture.ComunicaScadenzaTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case SI: return ComunicaScadenzaTipo.SI;
		default:
			break;
		}
		return null;
	}

	private static PagamentoStornoTipo toPCC(org.govmix.pcc.fatture.PagamentoStornoTipo vo) {
		if(vo == null) return null;
		PagamentoStornoTipo pccVO = new PagamentoStornoTipo();
		pccVO.setImportoStorno(vo.getImportoStorno());
		pccVO.setNaturaSpesa(toPCC(vo.getNaturaSpesa()));
		pccVO.setIdFiscaleIVABeneficiario(vo.getIdFiscaleIVABeneficiario());
		return pccVO;
	}

	private static NaturaSpesaTipo toPCC(org.govmix.pcc.fatture.NaturaSpesaTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case CO: return NaturaSpesaTipo.CO;
		case CA: return NaturaSpesaTipo.CA;
		default:
			break;
		}
		return null;
	}

	private static PagamentoTipo toPCC(org.govmix.pcc.fatture.PagamentoTipo vo) {
		if(vo == null) return null;
		PagamentoTipo pccVO = new PagamentoTipo();
		pccVO.setImportoPagato(vo.getImportoPagato());
		pccVO.setNaturaSpesa(toPCC(vo.getNaturaSpesa()));
		pccVO.setCapitoliSpesa(vo.getCapitoliSpesa());
		pccVO.setEstremiImpegno(vo.getEstremiImpegno());
		pccVO.setMandatoPagamento(toPCC(vo.getMandatoPagamento()));
		pccVO.setIdFiscaleIVABeneficiario(vo.getIdFiscaleIVABeneficiario());
		pccVO.setCodiceCIG(vo.getCodiceCIG());
		pccVO.setCodiceCUP(vo.getCodiceCUP());
		pccVO.setDescrizione(vo.getDescrizione());
		return pccVO;
	}

	private static MandatoPagamentoTipo toPCC(org.govmix.pcc.fatture.MandatoPagamentoTipo vo) {
		if(vo == null) return null;
		MandatoPagamentoTipo pccVO = new MandatoPagamentoTipo();
		pccVO.setNumero(vo.getNumero());
		pccVO.setDataMandatoPagamento(vo.getDataMandatoPagamento());
		return pccVO;
	}

	private static TipoOperazioneTipo toPCC(org.govmix.pcc.fatture.TipoOperazioneTipo vo) {
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

	private static DatiRichiestaInserimentoFatturaProxyTipo toPCC(org.govmix.pcc.fatture.DatiRichiestaInserimentoFatturaProxyTipo vo) {
		if(vo == null) return null;
		DatiRichiestaInserimentoFatturaProxyTipo pccVO = new DatiRichiestaInserimentoFatturaProxyTipo();
		pccVO.setDatiFornitore(toPCC(vo.getDatiFornitore()));
		pccVO.setDatiAmministrazione(toPCC(vo.getDatiAmministrazione()));
		pccVO.setIdentificativoDistinta(vo.getIdentificativoDistinta());
		pccVO.setDatiGeneraliFattura(toPCC(vo.getDatiGeneraliFattura()));
		pccVO.setListaRiepilogoAliquote(toPCC(vo.getListaRiepilogoAliquote()));
		pccVO.setListaDistribuzioneCupCig(toPCC(vo.getListaDistribuzioneCupCig()));
		pccVO.setListaDettaglioPagamento(toPCC(vo.getListaDettaglioPagamento()));
		pccVO.setDatiRicezioneFattura(toPCC(vo.getDatiRicezioneFattura()));
		pccVO.setForzaturaImmissione(toPCC(vo.getForzaturaImmissione()));
		return pccVO;
	}
	
	private static ForzaturaImmissioneTipo toPCC(org.govmix.pcc.fatture.ForzaturaImmissioneTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case AG: return ForzaturaImmissioneTipo.AG; 
		case SO: return ForzaturaImmissioneTipo.SO;
		default:
			break;
		}
		return null;
	}

	private static RicezioneFatturaTipo toPCC(org.govmix.pcc.fatture.RicezioneFatturaTipo vo) {
		if(vo == null) return null;
		RicezioneFatturaTipo pccVO = new RicezioneFatturaTipo();
		pccVO.setNumeroProtocolloEntrata(vo.getNumeroProtocolloEntrata());
		pccVO.setDataRicezione(vo.getDataRicezione());
		pccVO.setNote(vo.getNote());
		return pccVO;
	}
	
	private static ListaDettaglioPagamentoTipo toPCC(org.govmix.pcc.fatture.ListaDettaglioPagamentoTipo vo) {
		if(vo == null) return null;
		ListaDettaglioPagamentoTipo pccVO = new ListaDettaglioPagamentoTipo();
		pccVO.getDettaglioPagamento().addAll(toPCCDettaglioPagamentoTipo(vo.getDettaglioPagamento()));
		return pccVO;
	}

	private static List<DettaglioPagamentoTipo> toPCCDettaglioPagamentoTipo(List<org.govmix.pcc.fatture.DettaglioPagamentoTipo> vo) {
		if(vo == null) return null;
		List<DettaglioPagamentoTipo> pccVO = new ArrayList<DettaglioPagamentoTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(org.govmix.pcc.fatture.DettaglioPagamentoTipo obj: vo) {
				pccVO.add(toPCC(obj));
			}
		}
		return pccVO;
	}
	
	private static DettaglioPagamentoTipo toPCC(org.govmix.pcc.fatture.DettaglioPagamentoTipo vo) {
		if(vo == null) return null;
		DettaglioPagamentoTipo pccVO = new DettaglioPagamentoTipo();
		pccVO.setDataRiferimentoTerminiPagamento(vo.getDataRiferimentoTerminiPagamento());
		pccVO.setGiorniTerminiPagamento(vo.getGiorniTerminiPagamento());
		pccVO.setDataScadenzaPagamento(vo.getDataScadenzaPagamento());
		pccVO.setImportoPagamento(vo.getImportoPagamento());
		return pccVO;
	}


	private static ListaDistribuzioneCupCigTipo toPCC(org.govmix.pcc.fatture.ListaDistribuzioneCupCigTipo vo) {
		if(vo == null) return null;
		ListaDistribuzioneCupCigTipo pccVO = new ListaDistribuzioneCupCigTipo();
		pccVO.getDistribuzioneCupCig().addAll(toPCCDistribuzioneCupCigTipo(vo.getDistribuzioneCupCig()));
		return pccVO;
	}
	
	private static List<DistribuzioneCupCigTipo> toPCCDistribuzioneCupCigTipo(List<org.govmix.pcc.fatture.DistribuzioneCupCigTipo> vo) {
		if(vo == null) return null;
		List<DistribuzioneCupCigTipo> pccVO = new ArrayList<DistribuzioneCupCigTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(org.govmix.pcc.fatture.DistribuzioneCupCigTipo obj: vo) {
				pccVO.add(toPCC(obj));
			}
		}
		return pccVO;
	}
	
	private static DistribuzioneCupCigTipo toPCC(org.govmix.pcc.fatture.DistribuzioneCupCigTipo vo) {
		if(vo == null) return null;
		DistribuzioneCupCigTipo pccVO = new DistribuzioneCupCigTipo();
		pccVO.setImporto(vo.getImporto());
		pccVO.setCodiceCUP(vo.getCodiceCUP());
		pccVO.setCodiceCIG(vo.getCodiceCIG());
		return pccVO;
	}

	
	private static ListaRiepilogoAliquoteTipo toPCC(org.govmix.pcc.fatture.ListaRiepilogoAliquoteTipo vo) {
		if(vo == null) return null;
		ListaRiepilogoAliquoteTipo pccVO = new ListaRiepilogoAliquoteTipo();
		pccVO.getRiepilogoAliquote().addAll(toPCCRiepilogoAliquoteTipo(vo.getRiepilogoAliquote()));
		return pccVO;
	}

	private static List<RiepilogoAliquoteTipo> toPCCRiepilogoAliquoteTipo(List<org.govmix.pcc.fatture.RiepilogoAliquoteTipo> vo) {
		if(vo == null) return null;
		List<RiepilogoAliquoteTipo> pccVO = new ArrayList<RiepilogoAliquoteTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(org.govmix.pcc.fatture.RiepilogoAliquoteTipo obj: vo) {
				pccVO.add(toPCC(obj));
			}
		}
		return pccVO;
	}
	
	private static RiepilogoAliquoteTipo toPCC(org.govmix.pcc.fatture.RiepilogoAliquoteTipo vo) {
		if(vo == null) return null;
		RiepilogoAliquoteTipo pccVO = new RiepilogoAliquoteTipo();
		pccVO.setAliquotaIVA(vo.getAliquotaIVA());
		pccVO.setCodiceEsenzioneIVA(toPCC(vo.getCodiceEsenzioneIVA()));
		pccVO.setTotaleImponibileAliquota(vo.getTotaleImponibileAliquota());
		pccVO.setTotaleImpostaAliquota(vo.getTotaleImpostaAliquota());
		return pccVO;
	}

	private static CodiceEsenzioneIVATipo toPCC(org.govmix.pcc.fatture.CodiceEsenzioneIVATipo vo) {
		if(vo == null) return null;
		switch(vo){
		case NA: return CodiceEsenzioneIVATipo.NA;
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

	private static DatiGeneraliFatturaTipo toPCC(org.govmix.pcc.fatture.DatiGeneraliFatturaTipo vo) {
		if(vo == null) return null;
		DatiGeneraliFatturaTipo pccVO = new DatiGeneraliFatturaTipo();
		pccVO.setTipoDocumento(toPCC(vo.getTipoDocumento()));
		pccVO.setDataEmissione(vo.getDataEmissione());
		pccVO.setNumeroFattura(vo.getNumeroFattura());
		pccVO.setDescrizioneCausale(vo.getDescrizioneCausale());
		pccVO.setImportoTotaleDocumento(vo.getImportoTotaleDocumento());
		pccVO.setImportoImponibile(vo.getImportoImponibile());
		pccVO.setTotaleImposta(vo.getTotaleImposta());
		pccVO.setArt73(toPCC(vo.getArt73()));
		return pccVO;
	}
	
	private static FlagSiNoTipo toPCC(org.govmix.pcc.fatture.FlagSiNoTipo vo) {
		if(vo == null) return null;
		switch(vo){
		case SI: return FlagSiNoTipo.SI;
		case NO: return FlagSiNoTipo.NO;
		default:
			break;
		}
		return null;
	}
	
	private static TipoDocumentoTipo toPCC(org.govmix.pcc.fatture.TipoDocumentoTipo vo) {
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

	private static DatiAmministrazioneInsFattTipo toPCC(org.govmix.pcc.fatture.DatiAmministrazioneInsFattTipo vo) {
		if(vo == null) return null;
		DatiAmministrazioneInsFattTipo pccVO = new DatiAmministrazioneInsFattTipo();
		pccVO.setCodiceFiscale(vo.getCodiceFiscale());
		pccVO.setDenominazione(vo.getDenominazione());
		pccVO.setCodiceUfficioPCC(vo.getCodiceUfficioPCC());
		pccVO.setCodiceUnivocoUfficioIPA(vo.getCodiceUnivocoUfficioIPA());
		return pccVO;
	}

	private static DatiFornitoreInsFattTipo toPCC(org.govmix.pcc.fatture.DatiFornitoreInsFattTipo vo) {
		if(vo == null) return null;
		DatiFornitoreInsFattTipo pccVO = new DatiFornitoreInsFattTipo();
		pccVO.setCodiceFiscale(vo.getCodiceFiscale());
		pccVO.setDenominazione(vo.getDenominazione());
		pccVO.setIdFiscaleIVA(vo.getIdFiscaleIVA());
		return pccVO;
	}

	private static DatiRichiestaDatiFatturaProxyTipo toPCC(org.govmix.pcc.fatture.DatiRichiestaDatiFatturaProxyTipo vo) {
		if(vo == null) return null;
		DatiRichiestaDatiFatturaProxyTipo pccVO = new DatiRichiestaDatiFatturaProxyTipo();
		pccVO.setIdentificazionePCC(vo.getIdentificazionePCC());
		pccVO.setIdentificazioneSDI(toPCC(vo.getIdentificazioneSDI()));
		pccVO.setIdentificazioneGenerale(toPCC(vo.getIdentificazioneGenerale()));
		return pccVO;
	}

	private static IdentificazioneSDITipo toPCC(org.govmix.pcc.fatture.IdentificazioneSDITipo vo) {
		if(vo == null) return null;
		IdentificazioneSDITipo pccVO = new IdentificazioneSDITipo();
		pccVO.setLottoSDI(vo.getLottoSDI());
		pccVO.setNumeroFattura(vo.getNumeroFattura());
		return pccVO;
	}

	private static IdentificazioneGeneraleTipo toPCC(org.govmix.pcc.fatture.IdentificazioneGeneraleTipo vo) {
		if(vo == null) return null;
		IdentificazioneGeneraleTipo pccVO = new IdentificazioneGeneraleTipo();
		pccVO.setNumeroFattura(vo.getNumeroFattura());
		pccVO.setDataEmissione(vo.getDataEmissione());
		pccVO.setImportoTotaleDocumento(vo.getImportoTotaleDocumento());
		pccVO.setIdFiscaleIvaFornitore(vo.getIdFiscaleIvaFornitore());
		return pccVO;
	}

	private static DatiRichiestaPagamentoIvaProxyTipo toPCC(TestataAsyncSenzaFatturaRichiestaTipo testata, org.govmix.pcc.fatture.DatiRichiestaPagamentoIvaProxyTipo vo) {
		if(vo == null) return null;
		DatiRichiestaPagamentoIvaProxyTipo pccVO = new DatiRichiestaPagamentoIvaProxyTipo();

		pccVO.setIdentificativoPCCUfficio(testata.getIdentificativoPCCUfficio());
		pccVO.setTipologiaMovimentoErario(toPCC(vo.getTipologiaMovimentoErario()));
		pccVO.setDettaglioMovimentoErario(toPCC(vo.getDettaglioMovimentoErario()));

		return pccVO;
	}

	private static TipologiaMovimentoErarioTipo toPCC(org.govmix.pcc.fatture.TipologiaMovimentoErarioTipo tipologiaMovimentoErario) {
		if(tipologiaMovimentoErario == null) return null;
		switch(tipologiaMovimentoErario){
		case PAGAMENTO: return TipologiaMovimentoErarioTipo.PAGAMENTO; 
		case STORNO:return TipologiaMovimentoErarioTipo.STORNO;
		default:
			break;
		}
		return null;
	}

	private static DettaglioMovimentoErarioTipo toPCC(org.govmix.pcc.fatture.DettaglioMovimentoErarioTipo vo) {
		if(vo == null) return null;
		DettaglioMovimentoErarioTipo pccVO = new DettaglioMovimentoErarioTipo();

		pccVO.setStornoPagamentoIVA(toPCC(vo.getStornoPagamentoIVA()));
		pccVO.setPagamentoIVA(toPCC(vo.getPagamentoIVA()));

		return pccVO;
	}

	private static PagamentoIVATipo toPCC(org.govmix.pcc.fatture.PagamentoIVATipo vo) {
		if(vo == null) return null;
		PagamentoIVATipo pccVO = new PagamentoIVATipo();

		pccVO.setDatiPagamento(toPCC(vo.getDatiPagamento()));
		pccVO.setListaDettagliPagamento(toPCC(vo.getListaDettagliPagamento()));

		return pccVO;
	}

	private static ListaDettagliPagamentoTipo toPCC(org.govmix.pcc.fatture.ListaDettagliPagamentoTipo vo) {
		if(vo == null) return null;
		ListaDettagliPagamentoTipo pccVO = new ListaDettagliPagamentoTipo();
		pccVO.getDettagliPagamento().addAll(toPCC(vo.getDettagliPagamento()));
		return pccVO;
	}

	private static List<DettagliPagamentoTipo> toPCC(List<org.govmix.pcc.fatture.DettagliPagamentoTipo> vo) {
		if(vo == null) return null;
		List<DettagliPagamentoTipo> pccVO = new ArrayList<DettagliPagamentoTipo>();
		if(vo != null && !vo.isEmpty()) {
			for(org.govmix.pcc.fatture.DettagliPagamentoTipo obj: vo) {
				pccVO.add(toPCC(obj));
			}
		}
		return pccVO;
	}

	private static DettagliPagamentoTipo toPCC(org.govmix.pcc.fatture.DettagliPagamentoTipo vo) {
		if(vo == null) return null;
		DettagliPagamentoTipo pccVO = new DettagliPagamentoTipo();
		pccVO.setImporto(vo.getImporto());
		pccVO.setCapitoliSpesa(vo.getCapitoliSpesa());
		pccVO.setEstremiImpegno(vo.getEstremiImpegno());
		pccVO.setNoteAggiuntive(vo.getNoteAggiuntive());
		pccVO.setDocumentoAllegato(toPCC(vo.getDocumentoAllegato()));
		return pccVO;
	}

	private static Allegato toPCC(org.govmix.pcc.fatture.Allegato vo) {
		if(vo == null) return null;
		Allegato pccVO = new Allegato();
		pccVO.setFileAllegato(vo.getFileAllegato());
		pccVO.setNomeFile(vo.getNomeFile());
		pccVO.setContentType(vo.getContentType());
		return pccVO;
	}


	private static DatiPagamentoTipo toPCC(org.govmix.pcc.fatture.DatiPagamentoTipo vo) {
		if(vo == null) return null;
		DatiPagamentoTipo pccVO = new DatiPagamentoTipo();
		pccVO.setNumeroMandato(vo.getNumeroMandato());
		pccVO.setDataMandato(vo.getDataMandato());
		return pccVO;
	}

	private static StornoPagamentoIVATipo toPCC(org.govmix.pcc.fatture.StornoPagamentoIVATipo vo) {
		if(vo == null) return null;
		StornoPagamentoIVATipo pccVO = new StornoPagamentoIVATipo();
		pccVO.setIdentificativoPagamentoIVA(vo.getIdentificativoPagamentoIVA());
		return pccVO;
	}

}
