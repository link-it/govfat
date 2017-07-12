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
package org.govmix.proxy.pcc.wsclient;

import it.tesoro.fatture.EsitoOkKoTipo;
import it.tesoro.fatture.FattureWS_Service;
import it.tesoro.fatture.OperazioneTipo;
import it.tesoro.fatture.QueryPagamentoIvaRichiestaTipo;
import it.tesoro.fatture.StrutturaDatiOperazioneTipo;
import it.tesoro.fatture.TestataAsyncTipo;
import it.tesoro.fatture.TipoOperazioneTipo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.govmix.pcc.fatture.ComunicazioneScadenzaTipo;
import org.govmix.pcc.fatture.ContabilizzazioneTipo;
import org.govmix.pcc.fatture.PagamentoStornoTipo;
import org.govmix.pcc.fatture.PagamentoTipo;
import org.govmix.pcc.fatture.ProxyDatiFatturaRichiestaTipo;
import org.govmix.pcc.fatture.ProxyInserimentoFatturaRichiestaTipo;
import org.govmix.pcc.fatture.ProxyOperazioneContabileRichiestaTipo;
import org.govmix.pcc.fatture.ProxyPagamentoIvaRichiestaTipo;
import org.govmix.pcc.fatture.ProxyRispostaTipo;
import org.govmix.pcc.fatture.QueryDatiFatturaRichiestaTipo;
import org.govmix.pcc.fatture.QueryDatiFatturaRispostaTipo;
import org.govmix.pcc.fatture.QueryInserimentoFatturaRichiestaTipo;
import org.govmix.pcc.fatture.QueryInserimentoFatturaRispostaTipo;
import org.govmix.pcc.fatture.QueryOperazioneContabileRichiestaTipo;
import org.govmix.pcc.fatture.QueryOperazioneContabileRispostaTipo;
import org.govmix.pcc.fatture.QueryPagamentoIvaRispostaTipo;
import org.govmix.pcc.fatture.ReadDownloadDocumentoRichiestaTipo;
import org.govmix.pcc.fatture.ReadDownloadDocumentoRispostaTipo;
import org.govmix.pcc.fatture.ReadElencoMovimentiErarioIvaRichiestaTipo;
import org.govmix.pcc.fatture.ReadElencoMovimentiErarioIvaRispostaTipo;
import org.govmix.pcc.fatture.ReadStatoFatturaRichiestaTipo;
import org.govmix.pcc.fatture.ReadStatoFatturaRispostaTipo;
import org.govmix.pcc.fatture.ResultNotReadyFault;
import org.govmix.pcc.fatture.TestataRispTipo;
import org.govmix.pcc.fatture.WSGenericFault;
import org.govmix.pcc.fatture.WSResultNotReadyFault;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.orm.constants.OperazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.utils.TransformUtils;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanFactory;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanRequest;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanResponse;
import org.govmix.proxy.pcc.fatture.converter.PccConverter;
import org.govmix.proxy.pcc.fatture.converter.ProxyConverter;
import org.govmix.proxy.pcc.fatture.serializer.JAXBSerializer;
import org.govmix.proxy.pcc.fatture.tracciamento.FormatoErratoException;
import org.govmix.proxy.pcc.fatture.tracciamento.OperazioneNonPermessaException;
import org.govmix.proxy.pcc.fatture.tracciamento.TracciamentoOperazioneContabileUtils;
import org.govmix.proxy.pcc.fatture.tracciamento.TracciamentoUtils;
import org.govmix.proxy.pcc.fatture.utils.PccProperties;
import org.openspcoop2.generic_project.exception.NotFoundException;

public class PccWsClient {

	private FattureWS_Service fattureService;
	private Logger log;
	private TracciamentoUtils tracciamentoUtils;
	private TracciamentoOperazioneContabileUtils tracciamentoOperazioneContabileUtils;
	private FatturaElettronicaBD fatturaBD;

	private JAXBSerializer jaxbSerializer;

	public PccWsClient(Logger log) throws Exception {
		this.log = log;
		this.log.info("Inizializzazione Client WSPCC...");
		this.log.info("Inizializzazione FattureWS_Service...");
		try{
			this.fattureService = new FattureWS_Service(PccWsClient.class.getResource("classpath:/wsdl/PCC_WSFATT_v2.0.wsdl"));
		}catch(Throwable t){
			this.log.error("Inizializzazione FattureWS_Service error:" + t.getMessage(), t);			
		}
		this.log.info("Inizializzazione FattureWS_Service completata");
		this.tracciamentoUtils = new TracciamentoUtils(this.log);
		this.tracciamentoOperazioneContabileUtils = new TracciamentoOperazioneContabileUtils(this.log);
		this.fatturaBD = new FatturaElettronicaBD(log);

		this.jaxbSerializer = new JAXBSerializer();
		this.log.info("Inizializzazione Client WSPCC completata");
	}

	private it.tesoro.fatture.FattureWS getFattureWS(OperazioneType operazione) {
		it.tesoro.fatture.FattureWS  port = this.fattureService.getFattureWSSOAP11Port();
		((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,  PccProperties.getInstance().getUrlPCC() + "/wS" +operazione.getValue());

		if(PccProperties.getInstance().getUsernamePCC() != null && !PccProperties.getInstance().getUsernamePCC().isEmpty()) {
			((BindingProvider)port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, PccProperties.getInstance().getUsernamePCC());
			((BindingProvider)port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PccProperties.getInstance().getPasswordPCC());
		}

		return port;
	}

	private String getIdEgov(it.tesoro.fatture.FattureWS port) {
		BindingProvider bp = (BindingProvider) port;

		@SuppressWarnings("unchecked")
		Map<String, List<String>> map = (Map<String, List<String>>) bp.getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS);

		String idEgovHeader = PccProperties.getInstance().getIdEgovRichiestaHeader();
		if(map.containsKey(idEgovHeader)) {
			return map.get(idEgovHeader).get(0);
		}
		return null;
	}

	public void wSProxyPagamentoIva(PccTraccia traccia) throws WSGenericFault {
		this.log.info("invoke : wSProxyPagamentoIva");
		try {

			it.tesoro.fatture.ProxyPagamentoIvaRichiestaTipo proxyPagamentoIvaRichiestaTipo = this.jaxbSerializer.toProxyPagamentoIvaRichiestaTipo(traccia.getRichiestaXml());
			this.processTestataPerRispedizione(proxyPagamentoIvaRichiestaTipo.getTestataRichiesta(),traccia);

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_PAGAMENTO_IVA);

			it.tesoro.fatture.ProxyRispostaTipo wSProxyPagamentoIva = null;
			try {
				wSProxyPagamentoIva = port.wSProxyPagamentoIva(proxyPagamentoIvaRichiestaTipo);
			} catch(javax.xml.ws.WebServiceException e) {
				this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}

			

			this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyPagamentoIva, getIdEgov(port));
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			if(traccia != null) {
				try {
					this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					if(traccia.getRispedizione()) { //se la rispedizione e' abilitata, consideriamo il caso come ok
						return;
					}
				} catch (Exception ec) {}
			}

			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public ProxyRispostaTipo wSProxyPagamentoIva(ProxyPagamentoIvaRichiestaTipo proxyPagamentoIvaRichiestaTipo, AuthorizationBeanResponse beanResponse) throws WSGenericFault {
		this.log.info("invoke : wSProxyPagamentoIva");
		PccTraccia traccia = null;
		it.tesoro.fatture.ProxyRispostaTipo wSProxyPagamentoIva = null;
		try {
			it.tesoro.fatture.ProxyPagamentoIvaRichiestaTipo request = PccConverter.toPCC(proxyPagamentoIvaRichiestaTipo, beanResponse);
			traccia = this.tracciamentoUtils.getTracciaProxy(NomePccOperazioneType.PAGAMENTO_IVA, proxyPagamentoIvaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(request), this.tracciamentoUtils);
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_PAGAMENTO_IVA);

			try{
				wSProxyPagamentoIva = port.wSProxyPagamentoIva(request);
			} catch(javax.xml.ws.WebServiceException e) {
				this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}

			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyPagamentoIva,getIdEgov(port));
			this.log.info("Invoke: wSProxyPagamentoIva: OK");

			return ProxyConverter.toProxy(wSProxyPagamentoIva, testata);
		}catch(Exception e) {
			return handleProxyException(traccia, e);
		} finally{
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch (Exception e) {
				this.log.error("Errore durante l'aggiornamento della traccia: " + e.getMessage(), e);
				throw new WSGenericFault(e.getMessage(), e);
			}
		}
	}

	public void wSQueryInserimentoFattura(PccTraccia traccia, PccTracciaTrasmissione trasm) throws WSGenericFault {
		this.log.info("invoke : wSQueryInserimentoFattura");
		try {

			FatturaElettronica fattura = this.fatturaBD.getById(traccia.getIdFattura());
			IdFattura idFattura = new IdFattura();
			idFattura.setIdentificativoSdi(fattura.getIdentificativoSdi());
			idFattura.setPosizione(fattura.getPosizione());


			AuthorizationBeanRequest beanRequest = AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(null, traccia.getUtenteRichiedente(), fattura.getCodiceDestinatario(), NomePccOperazioneType.DATI_FATTURA);

			beanRequest.setCodiceDipartimento(fattura.getCodiceDestinatario());

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.QUERY_INSERIMENTO_FATTURA);
			it.tesoro.fatture.QueryInserimentoFatturaRispostaTipo wSQueryInserimentoFattura = null; 
			try {
				wSQueryInserimentoFattura = port.wSQueryInserimentoFattura(PccConverter.toPCCInserimentoFattura(traccia, trasm.getIdPccTransazione()));
			} catch(javax.xml.ws.WebServiceException e) {
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}
			this.tracciamentoUtils.aggiornaTracciaETrasmissioneQuery(traccia, trasm, beanRequest, wSQueryInserimentoFattura.getDatiRisposta(), getIdEgov(port), this.jaxbSerializer.toXML(wSQueryInserimentoFattura));

			this.checkPerNotifica(traccia, wSQueryInserimentoFattura.getDatiRisposta().getEsitoTrasmissione(), wSQueryInserimentoFattura.getDatiRisposta().getEsitoElaborazioneTransazione());

			this.log.info("Invoke: wSQueryInserimentoFattura: OK");
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	private void checkPerNotifica(PccTraccia traccia, EsitoOkKoTipo esitoTrasmissione, String esitoElaborazione) throws Exception {
		if(!traccia.getRispedizione()) {
			if(esitoTrasmissione.equals(EsitoOkKoTipo.KO)  || !esitoElaborazione.equals("OK")) {
				if(!traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
					this.tracciamentoUtils.creaNotifica(traccia);
				}
			}
		}
	}

	public QueryInserimentoFatturaRispostaTipo wSQueryInserimentoFattura(PccTraccia traccia) throws WSGenericFault, WSResultNotReadyFault {
		try {
			if(traccia.getRispostaXml() == null) {
				throw getResultNotReadyFault("Identificativo PA: " +traccia.getIdPaTransazione());
			}

			return ProxyConverter.toProxy(this.jaxbSerializer.toQueryInserimentoFatturaRispostaTipo(traccia.getRispostaXml()));
		}catch(WSResultNotReadyFault e) {
			this.log.error("WSResultNotReadyFault: " + e.getMessage(), e);
			throw e;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	public ReadStatoFatturaRispostaTipo wSReadStatoFattura(ReadStatoFatturaRichiestaTipo readStatoFatturaRichiestaTipo, AuthorizationBeanResponse beanResponse)  throws WSGenericFault {
		this.log.info("invoke : wSReadStatoFattura");
		PccTraccia traccia = null;
		try {

			it.tesoro.fatture.ReadStatoFatturaRichiestaTipo pccRequest = PccConverter.toPCC(readStatoFatturaRichiestaTipo, beanResponse);

			traccia = this.tracciamentoUtils.getTracciaRead(NomePccOperazioneType.STATO_FATTURA, readStatoFatturaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest));
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.READ_STATO_FATTURA);

			it.tesoro.fatture.ReadStatoFatturaRispostaTipo wSReadStatoFattura = null; 
			try{
				wSReadStatoFattura = port.wSReadStatoFattura(pccRequest);
			} catch(javax.xml.ws.WebServiceException e) {
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}


			if(traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto()) && wSReadStatoFattura.getDatiRisposta().getEsitoTrasmissione().value().equals(EsitoOkKoTipo.OK.value())) {

				this.tracciamentoOperazioneContabileUtils.riallineaDatiFattura(beanResponse, wSReadStatoFattura.getDatiRisposta().getStatoContabileFattura());
			}


			ReadStatoFatturaRispostaTipo response = ProxyConverter.toProxy(wSReadStatoFattura);
			this.tracciamentoUtils.popolaTracciaETrasmissioneRead(traccia, 
					wSReadStatoFattura.getDatiRisposta().getListaErroreTrasmissione(), 
					pccRequest.getTestataRichiesta().getTimestampTrasmissione(), 
					wSReadStatoFattura.getTestataRisposta().getTimestampTrasmissione(), 
					getIdEgov(port),
					this.jaxbSerializer.toXML(wSReadStatoFattura));

			this.log.info("Invoke: wSReadStatoFattura: OK");
			return response;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e); 
			if(traccia != null)
				traccia.setStato(StatoType.S_ERRORE);

			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null) {
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
					if(traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {

						PccTracciaTrasmissioneEsito esito = null;
						for(PccTracciaTrasmissione trasm: traccia.getPccTracciaTrasmissioneList()){
							for(PccTracciaTrasmissioneEsito es: trasm.getPccTracciaTrasmissioneEsitoList()) {
								esito = es;
								break;
							}
						}
						this.fatturaBD.updateEsitoContabilizzazione(beanResponse.getIdLogicoFattura(), esito);
					}

				}
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public QueryOperazioneContabileRispostaTipo wSQueryOperazioneContabile(PccTraccia traccia) throws WSGenericFault, WSResultNotReadyFault {
		try {
			if(traccia.getRispostaXml() == null) {
				throw getResultNotReadyFault("Identificativo PA: " +traccia.getIdPaTransazione());
			}

			return ProxyConverter.toProxy(this.jaxbSerializer.toQueryOperazioneContabileRispostaTipo(traccia.getRispostaXml()));
		}catch(WSResultNotReadyFault e) {
			this.log.error("WSResultNotReadyFault: " + e.getMessage(), e);
			throw e;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	private WSResultNotReadyFault getResultNotReadyFault(String detail) {
		ResultNotReadyFault resultNotReady = new ResultNotReadyFault();
		resultNotReady.setDetail(detail);
		return new WSResultNotReadyFault("Risultato non presente", resultNotReady);
	}

	private PccTraccia getTraccia(String identificativoTransazionePA) throws Exception {
		try {
			PccTraccia traccia = this.tracciamentoUtils.getTracciaByIdPa(identificativoTransazionePA);

			return traccia;

		} catch(NotFoundException e) {
			throw new WSGenericFault("Identificativo PA["+identificativoTransazionePA+"] non trovato");
		}
	}

	public void wSQueryOperazioneContabile(PccTraccia traccia, PccTracciaTrasmissione trasm, org.govmix.pcc.fatture.TipoOperazioneTipo tipoOperazione) throws WSGenericFault {
		this.log.info("invoke : wSQueryOperazioneContabile");
		try {

			FatturaElettronica fattura = this.fatturaBD.getById(traccia.getIdFattura());
			IdFattura idFattura = new IdFattura();
			idFattura.setIdentificativoSdi(fattura.getIdentificativoSdi());
			idFattura.setPosizione(fattura.getPosizione());

			AuthorizationBeanRequest beanRequest = AuthorizationBeanFactory.getAuthorizationBeanOperazioneContabileByIdFattura(null, traccia.getUtenteRichiedente(), ""+fattura.getIdentificativoSdi(), fattura.getNumero(), tipoOperazione);

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.QUERY_OPERAZIONE_CONTABILE);
			it.tesoro.fatture.QueryOperazioneContabileRispostaTipo wSQueryOperazioneContabile = null;
			try{
				wSQueryOperazioneContabile = port.wSQueryOperazioneContabile(PccConverter.toPCCOperazioneContabile(traccia, trasm.getIdPccTransazione()));
			} catch(javax.xml.ws.WebServiceException e) {
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}

			beanRequest.setCodiceDipartimento(fattura.getCodiceDestinatario());
			PccTracciaTrasmissioneEsito esito = this.tracciamentoUtils.aggiornaTracciaETrasmissioneQueryOperazioneContabile(traccia, trasm, beanRequest, wSQueryOperazioneContabile.getDatiRisposta(), getIdEgov(port), this.jaxbSerializer.toXML(wSQueryOperazioneContabile));

			if(tipoOperazione.value().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.CO.value())) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], aggiorno l'esito contabilizzazione");
				this.fatturaBD.updateEsitoContabilizzazione(idFattura, esito);
			} else if(tipoOperazione.value().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.CS.value())) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CS+"], aggiorno l'esito scadenza");
				this.fatturaBD.updateEsitoScadenza(idFattura, esito);
			} else if(tipoOperazione.value().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.CCS.value())) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CCS+"], aggiorno l'esito scadenza");
				this.fatturaBD.updateEsitoScadenza(idFattura, esito);
			}

			this.checkPerNotifica(traccia, wSQueryOperazioneContabile.getDatiRisposta().getEsitoTrasmissione(), wSQueryOperazioneContabile.getDatiRisposta().getEsitoElaborazioneTransazione());
			this.log.info("Invoke: wSQueryOperazioneContabile: OK");
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	public void wSProxyDatiFattura(PccTraccia traccia) throws WSGenericFault {
		this.log.info("invoke : wSProxyDatiFattura");

		try {
			it.tesoro.fatture.ProxyDatiFatturaRichiestaTipo proxyDatiFatturaRichiestaTipo = this.jaxbSerializer.toProxyDatiFatturaRichiestaTipo(traccia.getRichiestaXml());
			this.processTestataPerRispedizione(proxyDatiFatturaRichiestaTipo.getTestataRichiesta(), traccia);

			FatturaElettronica fattura = this.fatturaBD.getById(traccia.getIdFattura());
			IdFattura idFattura = new IdFattura();
			idFattura.setIdentificativoSdi(fattura.getIdentificativoSdi());
			idFattura.setPosizione(fattura.getPosizione());
			
			boolean existScadenze = this.tracciamentoOperazioneContabileUtils.existScadenze(idFattura);
			
			if(existScadenze) {
				this.fatturaBD.checkEsitoScadenza(idFattura);
			}
			
			boolean existsContabilizzazioni = this.tracciamentoOperazioneContabileUtils.existContabilizzazioni(idFattura);
			
			if(existsContabilizzazioni) {
				this.fatturaBD.checkEsitoContabilizzazione(idFattura);
			}

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_DATI_FATTURA);
			it.tesoro.fatture.ProxyRispostaTipo wSProxyDatiFattura = null;
			try{
				wSProxyDatiFattura = port.wSProxyDatiFattura(proxyDatiFatturaRichiestaTipo);
			} catch(javax.xml.ws.WebServiceException e) {
				this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}

			this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyDatiFattura, getIdEgov(port));
			

			if(!traccia.isRispedizioneDopoQuery()) {
				if(traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto()) && wSProxyDatiFattura.getDatiRisposta().getEsitoTrasmissione().value().equals(EsitoOkKoTipo.OK.value())) {
					this.fatturaBD.updateEsitoContabilizzazione(idFattura, null);
					this.fatturaBD.updateEsitoScadenza(idFattura, null);
				}
			}


			this.log.info("Invoke: wSProxyDatiFattura: OK");
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e); 
			if(traccia != null) {
				try {
					this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					if(traccia.getRispedizione()) { //se la rispedizione e' abilitata, consideriamo il caso come ok
						return;
					}
				} catch (Exception ec) {}
			}

			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public ProxyRispostaTipo wSProxyDatiFattura(ProxyDatiFatturaRichiestaTipo proxyDatiFatturaRichiestaTipo, AuthorizationBeanResponse beanResponse) throws WSGenericFault {
		this.log.info("invoke : wSProxyDatiFattura");
		PccTraccia traccia = null;
		it.tesoro.fatture.ProxyRispostaTipo wSProxyDatiFattura = null;
		try {

			it.tesoro.fatture.ProxyDatiFatturaRichiestaTipo pccRequest = PccConverter.toPCC(proxyDatiFatturaRichiestaTipo, beanResponse);


			boolean existScadenze = this.tracciamentoOperazioneContabileUtils.existScadenze(beanResponse.getIdLogicoFattura());
			
			if(existScadenze) {
				this.fatturaBD.checkEsitoScadenza(beanResponse.getIdLogicoFattura());
			}
			
			boolean existsContabilizzazioni = this.tracciamentoOperazioneContabileUtils.existContabilizzazioni(beanResponse.getIdLogicoFattura());
			
			if(existsContabilizzazioni) {
				this.fatturaBD.checkEsitoContabilizzazione(beanResponse.getIdLogicoFattura());
			}

			traccia = this.tracciamentoUtils.getTracciaProxy(NomePccOperazioneType.DATI_FATTURA, proxyDatiFatturaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest), this.tracciamentoUtils);

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_DATI_FATTURA);
			try {
				wSProxyDatiFattura = port.wSProxyDatiFattura(pccRequest);
			} catch(javax.xml.ws.WebServiceException e) {
				this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}
			

			if(beanResponse.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto()) && wSProxyDatiFattura.getDatiRisposta().getEsitoTrasmissione().value().equals(EsitoOkKoTipo.OK.value())) {
				this.fatturaBD.updateEsitoContabilizzazione(beanResponse.getIdLogicoFattura(), null);
				this.fatturaBD.updateEsitoScadenza(beanResponse.getIdLogicoFattura(), null);
			}

			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyDatiFattura, getIdEgov(port));
			this.log.info("Invoke: wSProxyDatiFattura: OK");
			return ProxyConverter.toProxy(wSProxyDatiFattura, testata);
		}catch(Exception e) {
			return handleProxyException(traccia, e);
		} finally{
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch (Exception e) {
				this.log.error("Errore durante l'aggiornamento della traccia: " + e.getMessage(), e);
				throw new WSGenericFault(e.getMessage(), e);
			}
		}
	}

	public void wSProxyInserimentoFattura(PccTraccia traccia) throws WSGenericFault {
		this.log.info("invoke : wSProxyInserimentoFattura");
		try {

			it.tesoro.fatture.ProxyInserimentoFatturaRichiestaTipo proxyInserimentoFatturaRichiestaTipo = this.jaxbSerializer.toProxyInserimentoFatturaRichiestaTipo(traccia.getRichiestaXml());
			this.processTestataPerRispedizione(proxyInserimentoFatturaRichiestaTipo.getTestataRichiesta(), traccia);

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_INSERIMENTO_FATTURA);
			it.tesoro.fatture.ProxyRispostaTipo wSProxyInserimentoFattura = null;
			try{
				wSProxyInserimentoFattura = port.wSProxyInserimentoFattura(proxyInserimentoFatturaRichiestaTipo);
			} catch(javax.xml.ws.WebServiceException e) {
				this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}
			this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyInserimentoFattura, getIdEgov(port));
			this.log.info("Invoke: wSProxyInserimentoFattura: OK");
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e); 
			if(traccia != null) {
				try {
					this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					if(traccia.getRispedizione()) { //se la rispedizione e' abilitata, consideriamo il caso come ok
						return;
					}
				} catch (Exception ec) {}
			}

			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public ProxyRispostaTipo wSProxyInserimentoFattura(ProxyInserimentoFatturaRichiestaTipo proxyInserimentoFatturaRichiestaTipo, AuthorizationBeanResponse beanResponse) throws WSGenericFault {
		this.log.info("invoke : wSProxyInserimentoFattura");
		PccTraccia traccia = null;
		it.tesoro.fatture.ProxyRispostaTipo wSProxyInserimentoFattura = null;
		try {
			it.tesoro.fatture.ProxyInserimentoFatturaRichiestaTipo pccRequest = PccConverter.toPCC(proxyInserimentoFatturaRichiestaTipo, beanResponse);

			traccia = this.tracciamentoUtils.getTracciaProxy(NomePccOperazioneType.INSERIMENTO_FATTURA, proxyInserimentoFatturaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest), this.tracciamentoUtils);

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_INSERIMENTO_FATTURA);
			try{
				wSProxyInserimentoFattura = port.wSProxyInserimentoFattura(pccRequest);
			} catch(javax.xml.ws.WebServiceException e) {
				this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}
			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyInserimentoFattura, getIdEgov(port));
			this.log.info("Invoke: wSProxyInserimentoFattura: OK");
			return ProxyConverter.toProxy(wSProxyInserimentoFattura, testata);
		}catch(Exception e) {
			return handleProxyException(traccia, e);
		} finally{
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch (Exception e) {
				this.log.error("Errore durante l'aggiornamento della traccia: " + e.getMessage(), e);
				throw new WSGenericFault(e.getMessage(), e);
			}
		}
	}

	private void processTestataPerRispedizione(TestataAsyncTipo testata, PccTraccia traccia) {
		testata.setIdentificativoTransazionePA(traccia.getIdPaTransazioneRispedizione());
		testata.setTimestampTrasmissione(new Date());
	}

	public void wSProxyOperazioneContabile(PccTraccia traccia) throws WSGenericFault {
		this.log.info("invoke : wSProxyOperazioneContabile");
		try {
			it.tesoro.fatture.ProxyOperazioneContabileRichiestaTipo proxyOperazioneContabileRichiestaTipo = this.jaxbSerializer.toProxyOperazioneContabileRichiestaTipo(traccia.getRichiestaXml()); 
			this.processTestataPerRispedizione(proxyOperazioneContabileRichiestaTipo.getTestataRichiesta(),traccia);

			FatturaElettronica fattura = this.fatturaBD.getById(traccia.getIdFattura());
			IdFattura idFattura = new IdFattura();
			idFattura.setIdentificativoSdi(fattura.getIdentificativoSdi());
			idFattura.setPosizione(fattura.getPosizione());

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_OPERAZIONE_CONTABILE);

			org.govmix.pcc.fatture.OperazioneTipo operazione = ProxyConverter.toProxyOperazioneList(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione());

			if(operazione != null) {

				ContabilizzazioneUtilsBean bean = check(idFattura, operazione, traccia.getSistemaRichiedente(), traccia.getUtenteRichiedente());


				if(bean.isExistsScadenze()) {
					OperazioneTipo element = new OperazioneTipo();
					element.setProgressivoOperazione(0);
					element.setTipoOperazione(TipoOperazioneTipo.CCS);
					element.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
					proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().add(0, element);
					for(int i = 0; i < proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().size(); i++) {
						OperazioneTipo oper = proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().get(i);
						oper.setProgressivoOperazione(i);
					}
				} else if(bean.isContabilizzazionePrevioStorno()) {
					OperazioneTipo element = new OperazioneTipo();
					element.setProgressivoOperazione(0);
					element.setTipoOperazione(TipoOperazioneTipo.SC);
					element.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
					proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().add(0, element);
					for(int i = 0; i < proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().size(); i++) {
						OperazioneTipo oper = proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().get(i);
						oper.setProgressivoOperazione(i);
					}
				}

				it.tesoro.fatture.ProxyRispostaTipo wSProxyOperazioneContabile = null;
				try{
					wSProxyOperazioneContabile = port.wSProxyOperazioneContabile(proxyOperazioneContabileRichiestaTipo);
				} catch(javax.xml.ws.WebServiceException e) {
					this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
					throw new Exception("Errore interno: " + e.getMessage(), e);
				}

				TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyOperazioneContabile, getIdEgov(port));

				if(!traccia.isRispedizioneDopoQuery()) {
					if(testata.getEsito().value().equals(org.govmix.pcc.fatture.EsitoOkKoTipo.OK.value()) && testata.getOrigine().value().equals(org.govmix.pcc.fatture.OrigineTipo.PCC.value())) {
	
						updateDB(operazione,
								traccia.getSistemaRichiedente(),
								traccia.getUtenteRichiedente(),
								fattura, idFattura, bean.getContabilizzazioniOriginali());
					}
				}


				this.log.info("Invoke: wSProxyOperazioneContabile: OK");
			} else {
				throw new Exception("Dati incompatibili trovati nella traccia");
			}
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			if(traccia != null) {
				try {
					this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					if(traccia.getRispedizione()) { //se la rispedizione e' abilitata, consideriamo il caso come ok
						return;
					}
				} catch (Exception ec) {}
			}

			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}


	public ProxyRispostaTipo wSProxyOperazioneContabile(ProxyOperazioneContabileRichiestaTipo proxyOperazioneContabileRichiestaTipo, AuthorizationBeanResponse beanResponse) throws WSGenericFault {
		this.log.info("invoke : wSProxyOperazioneContabile");
		PccTraccia traccia = null;
		it.tesoro.fatture.ProxyRispostaTipo wSProxyOperazioneContabile = null;
		try {
			NomePccOperazioneType op = AuthorizationBeanFactory.getOperationName(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione());
			FatturaElettronica fattura = this.fatturaBD.getById(beanResponse.getIdFattura());
			IdFattura idFattura = new IdFattura();
			idFattura.setIdentificativoSdi(fattura.getIdentificativoSdi());
			idFattura.setPosizione(fattura.getPosizione());

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_OPERAZIONE_CONTABILE);

			ContabilizzazioneUtilsBean bean = check(idFattura, proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione(), beanResponse.getSistemaRichiedente(), beanResponse.getUtenteRichiedente());
			
			

			it.tesoro.fatture.ProxyOperazioneContabileRichiestaTipo pccRequest = PccConverter.toPCC(proxyOperazioneContabileRichiestaTipo, beanResponse);

			if(bean.isExistsScadenze()) {
				op = NomePccOperazioneType.OPERAZIONE_CONTABILE_CSPC;

				OperazioneTipo element = new OperazioneTipo();
				element.setProgressivoOperazione(0);
				element.setTipoOperazione(TipoOperazioneTipo.CCS);
				element.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
				pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().add(0, element);
				for(int i = 0; i < pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().size(); i++) {
					OperazioneTipo oper = pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().get(i);
					oper.setProgressivoOperazione(i);
				}
			} else if(bean.isContabilizzazionePrevioStorno()) {
				op = NomePccOperazioneType.OPERAZIONE_CONTABILE_CPS;

				OperazioneTipo element = new OperazioneTipo();
				element.setProgressivoOperazione(0);
				element.setTipoOperazione(TipoOperazioneTipo.SC);
				element.setStrutturaDatiOperazione(new StrutturaDatiOperazioneTipo());
				if(bean.isStornoSenzaRicontabilizzazione()) {
					pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().clear();
					pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().add(element);
				} else {
					pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().add(0, element);
					for(int i = 0; i < pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().size(); i++) {
						OperazioneTipo oper = pccRequest.getDatiRichiesta().getListaOperazione().getOperazione().get(i);
						oper.setProgressivoOperazione(i);
					}
				}
			}

			traccia = this.tracciamentoUtils.getTracciaProxy(op, 
					proxyOperazioneContabileRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest), this.tracciamentoUtils);

			try{
				wSProxyOperazioneContabile = port.wSProxyOperazioneContabile(pccRequest);
			} catch(javax.xml.ws.WebServiceException e) {
				this.tracciamentoUtils.popolaTracciaETrasmissioneProxyErrore(traccia, e, getIdEgov(port));
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}

			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyOperazioneContabile, getIdEgov(port));
			
			this.log.info("Esito invocazione pcc: " +testata.getEsito() + ". Origine: " + testata.getOrigine());
			if(testata.getEsito().value().equals(org.govmix.pcc.fatture.EsitoOkKoTipo.OK.value()) && testata.getOrigine().value().equals(org.govmix.pcc.fatture.OrigineTipo.PCC.value())) {

				updateDB(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione(),
						beanResponse.getSistemaRichiedente(),
						beanResponse.getUtenteRichiedente(),
						fattura, idFattura, bean.getContabilizzazioniOriginali());
			}
			this.log.info("Invoke: wSProxyOperazioneContabile: OK");
			return ProxyConverter.toProxy(wSProxyOperazioneContabile, testata);
		}catch(Exception e) {
			return handleProxyException(traccia, e);
		} finally{
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch (Exception e) {
				this.log.error("Errore durante l'aggiornamento della traccia: " + e.getMessage(), e);
				throw new WSGenericFault(e.getMessage(), e);
			}
		}
	}

	private ProxyRispostaTipo handleProxyException(PccTraccia traccia,
			Exception e) throws WSGenericFault {
		this.log.error("Error: " + e.getMessage(), e);
		if(traccia != null) {
			try {
				TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
				this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
				
				return ProxyConverter.toProxyDefault(testata);
			} catch (Exception ec) {
				throw new WSGenericFault(e.getMessage(), e);
			}
		}
		throw new WSGenericFault(e.getMessage(), e);
	}

	private ContabilizzazioneUtilsBean check(IdFattura idFattura, org.govmix.pcc.fatture.OperazioneTipo operazione, String sistemaRichiedente, String utenteRichiedente) throws Exception {

		ContabilizzazioneUtilsBean bean = new ContabilizzazioneUtilsBean();
		List<ContabilizzazioneTipo> contabilizzazioniOriginali = null;

		if(operazione.getTipoOperazione().value().equals(TipoOperazioneTipo.CO.value())) {

			if(operazione.getStrutturaDatiOperazione().getListaContabilizzazione() != null &&
					!operazione.getStrutturaDatiOperazione().getListaContabilizzazione().isEmpty()) {
				//aggiorno la descrizione per inviare i metadati alla PCC
				for(ContabilizzazioneTipo cont: operazione.getStrutturaDatiOperazione().getListaContabilizzazione()) {
					if(cont.getDescrizione() != null && cont.getDescrizione().length() > 70) {
						throw new Exception("La lunghezza della descrizione della contabilizzazione ("+cont.getDescrizione()+") non puo superare i 70 caratteri.");
					}

					cont.setDescrizione(TransformUtils.toRawDescrizioneImportoContabilizzazione(cont.getDescrizione(), sistemaRichiedente, 
							utenteRichiedente, cont.getIdentificativoMovimento()));
				}
			}

			contabilizzazioniOriginali = new ArrayList<ContabilizzazioneTipo>();
			for(ContabilizzazioneTipo cont: operazione.getStrutturaDatiOperazione().getListaContabilizzazione()) {
					contabilizzazioniOriginali.add(cont);	
			}
			

			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], aggrego le contabilizzazioni inviatemi con quelle gia presenti nel DB");
			List<ContabilizzazioneTipo> contabilizzazione = null;
			if(sistemaRichiedente.equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
				contabilizzazione = this.tracciamentoOperazioneContabileUtils.getContabilizzazioniByIdFatturaEDiversoSistemaRichiedente(idFattura, sistemaRichiedente);
			} else {
				contabilizzazione = this.tracciamentoOperazioneContabileUtils.getContabilizzazioniByIdFatturaDiversoSistemaRichiedenteEIdImportoDiversi(idFattura, sistemaRichiedente, contabilizzazioniOriginali);
			}
			
			operazione.getStrutturaDatiOperazione().getListaContabilizzazione().clear();
			for(ContabilizzazioneTipo cont: contabilizzazioniOriginali) {
				if(cont.getImportoMovimento().doubleValue() != 0)
					operazione.getStrutturaDatiOperazione().getListaContabilizzazione().add(cont);	
			}
			operazione.getStrutturaDatiOperazione().getListaContabilizzazione().addAll(contabilizzazione);

			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], controllo che l'importo totale inviato non sia inferiore a quanto contabilizzato in precedenza");

			List<ContabilizzazioneTipo> contabilizzazioniByIdFattura = this.tracciamentoOperazioneContabileUtils.getContabilizzazioniByIdFattura(idFattura);
//			bean.setExistsContabilizzazioni(!contabilizzazioniByIdFattura.isEmpty());

//			if(bean.isExistsContabilizzazioni()) {
			if(!contabilizzazioniByIdFattura.isEmpty()) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], controllo che il piano di contabilizzazioni inviato sia ancora in attesa di un esito");
				this.fatturaBD.checkEsitoContabilizzazione(idFattura);
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], controllo che il piano di contabilizzazioni inviato sia ancora in attesa di un esito effettuato con successo");
			}
			double importoGiaContabilizzato = 0;
			double importoDaContabilizzare = 0;

			for(ContabilizzazioneTipo contab: contabilizzazioniByIdFattura) {
				importoGiaContabilizzato += contab.getImportoMovimento().doubleValue();
			}

			if(operazione.getStrutturaDatiOperazione() != null && operazione.getStrutturaDatiOperazione().getListaContabilizzazione() != null) {
				for(ContabilizzazioneTipo contab: operazione.getStrutturaDatiOperazione().getListaContabilizzazione()) {
					importoDaContabilizzare += contab.getImportoMovimento().doubleValue();
				}
			}

			if(importoDaContabilizzare < importoGiaContabilizzato) {
				 if(this.tracciamentoOperazioneContabileUtils.existPagamentiByIdFattura(idFattura)) {
					 throw new OperazioneNonPermessaException("Impossibile contabilizzare l'importo"+importoDaContabilizzare+" in quanto risulta gia' contabilizzato un importo maggiore ("+importoGiaContabilizzato+"). Impossibile effettuare lo storno in quanto esistono gia' dei pagamenti per la fattura.");
				 }
				 
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], l'importo totale inviato e' inferiore a quanto contabilizzato in precedenza. Eseguo una contabilizzazione previo storno");
				bean.setContabilizzazionePrevioStorno(true);
			}

			if(operazione.getStrutturaDatiOperazione() == null ||
					operazione.getStrutturaDatiOperazione().getListaContabilizzazione() == null || 
					operazione.getStrutturaDatiOperazione().getListaContabilizzazione().isEmpty()) {
				if(bean.isContabilizzazionePrevioStorno()) {
					this.log.info("Contabilizzazione previo storno, la lista di contabilizzazioni inviata alla PCC puo' essere vuota");
					 //L'utente esegue uno storno di una sola contabilizzazione, ma essendo l'unica contabilizzazione presente si esegue solo lo storno e nessuna ricontabilizzazione
					bean.setStornoSenzaRicontabilizzazione(true);
				} else {
					throw new FormatoErratoException("Impossibile inviare una lista di contabilizzazioni senza alcuna contabilizzazione");
				}
			}

		} else if(operazione.getTipoOperazione().value().equals(TipoOperazioneTipo.CCS.value())) {
			this.log.info("Tipo operazione contabile ["+operazione.getTipoOperazione().value()+"]");

			List<PccScadenza> lstScadenze = this.tracciamentoOperazioneContabileUtils.getScadenze(idFattura);
			this.log.info("Tipo operazione contabile ["+operazione.getTipoOperazione().value()+"], controllo che il piano di scadenze inviato sia ancora in attesa di un esito");
			for(PccScadenza scadenza: lstScadenze) {
				if(scadenza.getPagatoRicontabilizzato() != null && scadenza.getPagatoRicontabilizzato().doubleValue() == 0 && !bean.isExistsScadenze()) {
					bean.setExistsScadenze(true);
					if(bean.isExistsScadenze()) {
						this.fatturaBD.checkEsitoScadenza(idFattura);
					}

				}
			}
			this.log.info("Tipo operazione contabile ["+operazione.getTipoOperazione().value()+"], controllo che il piano di scadenze inviato sia ancora in attesa di un esito effettuato con successo");

		} else if(operazione.getTipoOperazione().value().equals(TipoOperazioneTipo.CS.value())) {

			this.log.info("Tipo operazione contabile ["+operazione.getTipoOperazione().value()+"]");

			List<PccScadenza> lstScadenze = this.tracciamentoOperazioneContabileUtils.getScadenze(idFattura);
			this.log.info("Tipo operazione contabile ["+operazione.getTipoOperazione().value()+"], controllo che il piano di scadenze inviato sia ancora in attesa di un esito");
			for(PccScadenza scadenza: lstScadenze) {
				if(scadenza.getPagatoRicontabilizzato() != null && scadenza.getPagatoRicontabilizzato().doubleValue() == 0 && !bean.isExistsScadenze()) {
					bean.setExistsScadenze(true);
					if(bean.isExistsScadenze()) {
						this.fatturaBD.checkEsitoScadenza(idFattura);
					}
				}
				this.log.info("Tipo operazione contabile ["+operazione.getTipoOperazione().value()+"], controllo che il piano di scadenze inviato sia ancora in attesa di un esito effettuato con successo");
			}

			List<ComunicazioneScadenzaTipo> comunicazioneScadenza = operazione.getStrutturaDatiOperazione().getComunicazioneScadenza();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			double importoTotalePianoScadenze = 0;
			boolean existScadenzaNull = false;
			for(ComunicazioneScadenzaTipo scadenza: comunicazioneScadenza) {
				for(PccScadenza scadenzaDB: lstScadenze) {
					if(scadenza.getDataScadenza() != null && scadenzaDB.getDataScadenza() != null) {
						String dataInputString = sdf.format(scadenza.getDataScadenza());
						String dataDBString = sdf.format(scadenzaDB.getDataScadenza());
						if(dataInputString.equals(dataDBString) && scadenzaDB.getPagatoRicontabilizzato() != null && scadenzaDB.getPagatoRicontabilizzato() > 0) {
							throw new OperazioneNonPermessaException("Impossibile comunicare una scadenza per la data "+scadenzaDB.getDataScadenza()+" in quanto per quella data esiste gia' una scadenza parzialmente pagata"); 
						}
					}
				}
				if(scadenza.getImporto() != null)
					importoTotalePianoScadenze+= scadenza.getImporto().doubleValue();
				else
					existScadenzaNull = true;
			}

			if(!existScadenzaNull)
				this.tracciamentoOperazioneContabileUtils.checkImportoScadenze(idFattura, importoTotalePianoScadenze);

		} else if(operazione.getTipoOperazione().value().equals(TipoOperazioneTipo.SP.value())) {
			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.SP+"], controllo che il saldo dei pagamenti sia maggiore dell'importo stornato");
			for(PagamentoStornoTipo pagamento: operazione.getStrutturaDatiOperazione().getPagamentoStorno()) {
				double importo = this.tracciamentoOperazioneContabileUtils.getImportoByIdFattura(idFattura, pagamento.getNaturaSpesa().value());
				if(importo < pagamento.getImportoStorno().doubleValue()) {
					throw new OperazioneNonPermessaException("Impossibile stornare un importo di ["+pagamento.getImportoStorno().doubleValue()+"] con natura ["+pagamento.getNaturaSpesa().value()+"]. Importo massimo stornabile ["+importo+"]");
				}
			}
			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.SP+"], controllo che il saldo dei pagamenti sia maggiore dell'importo stornato effettuato con successo");
		}
		
		bean.setContabilizzazioniOriginali(contabilizzazioniOriginali);

		return bean;
	}
	
	private void updateDB(org.govmix.pcc.fatture.OperazioneTipo operazione,
			String sistemaRichiedente, String utenteRichiedente, FatturaElettronica fattura,
			IdFattura idFattura,
			List<ContabilizzazioneTipo> contabilizzazioniOriginali)
			throws Exception {


		org.govmix.pcc.fatture.TipoOperazioneTipo tipoOperazione = operazione.getTipoOperazione();
		org.govmix.pcc.fatture.StrutturaDatiOperazioneTipo strutturaDatiOperazione = operazione.getStrutturaDatiOperazione();

		if(tipoOperazione.value().equals(TipoOperazioneTipo.CO.value())) {
			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], annullo l'esito contabilizzazione");
			this.fatturaBD.updateEsitoContabilizzazione(idFattura, null);

			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], aggiorno di conseguenza le scadenze");
			this.tracciamentoOperazioneContabileUtils.aggiornaScadenzeDopoContabilizzazione(idFattura);

			strutturaDatiOperazione.getListaContabilizzazione().clear();
			
			for(ContabilizzazioneTipo cont: contabilizzazioniOriginali) {
//				if(cont.getImportoMovimento().doubleValue() != 0)
					strutturaDatiOperazione.getListaContabilizzazione().add(cont);
			}
			
		} else if(tipoOperazione.value().equals(TipoOperazioneTipo.CS.value())) {
			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CS+"], annullo l'esito scadenza");
			this.fatturaBD.updateEsitoScadenza(idFattura, null);
		} else if(tipoOperazione.value().equals(TipoOperazioneTipo.CCS.value())) {
			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CCS+"], annullo l'esito scadenza");
			this.fatturaBD.updateEsitoScadenza(idFattura, null);
		} else if(tipoOperazione.value().equals(TipoOperazioneTipo.CP.value())) {
			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CP+"], aggiorno contabilizzazioni e scadenze rispetto ai pagamenti inviati");

			for(PagamentoTipo pagamento: strutturaDatiOperazione.getPagamento()) {
				this.tracciamentoOperazioneContabileUtils.aggiornaContabilizzazioniEScadenzeDopoPagamento(idFattura, pagamento);
			}
		} else if(tipoOperazione.value().equals(TipoOperazioneTipo.SP.value())) {

			this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.SP+"], aggiorno contabilizzazioni rispetto agli storni di pagamenti inviati");

			for(PagamentoStornoTipo pagamento: strutturaDatiOperazione.getPagamentoStorno()) {
				this.tracciamentoOperazioneContabileUtils.aggiornaContabilizzazioniDopoStornoPagamento(idFattura, pagamento, sistemaRichiedente, utenteRichiedente);
			}
		} else {
			this.log.info("Tipo operazione contabile ["+tipoOperazione+"]");
		}
		this.tracciamentoOperazioneContabileUtils.tracciaOperazioneContabile(operazione, sistemaRichiedente, utenteRichiedente, idFattura);

		this.log.info("Tracciamento operazione contabile completato");

		this.log.info("Controllo data scadenza della fattura");
		Date dataScadenza = this.tracciamentoOperazioneContabileUtils.getDataScadenza(idFattura, fattura.getImportoTotaleDocumento());
		boolean daPagare = this.tracciamentoOperazioneContabileUtils.isDaPagare(idFattura);
		this.log.info("Aggiornamento data scadenza della fattura ["+dataScadenza+"]...");
		this.fatturaBD.aggiornaDataScadenza(idFattura, dataScadenza, daPagare);
		this.log.info("Aggiornamento data scadenza della fattura ["+dataScadenza+"] completato");
	}

	public ReadDownloadDocumentoRispostaTipo wSReadDownloadDocumento(ReadDownloadDocumentoRichiestaTipo readDownloadDocumentoRichiestaTipo, AuthorizationBeanResponse beanResponse) throws WSGenericFault {
		this.log.info("invoke : wSReadDownloadDocumento");
		PccTraccia traccia = null;
		try {

			it.tesoro.fatture.ReadDownloadDocumentoRichiestaTipo pccRequest = PccConverter.toPCC(readDownloadDocumentoRichiestaTipo, beanResponse);


			traccia = this.tracciamentoUtils.getTracciaRead(NomePccOperazioneType.DOWNLOAD_DOCUMENTO, readDownloadDocumentoRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest));
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.READ_DOWNLOAD_DOCUMENTO);
			it.tesoro.fatture.ReadDownloadDocumentoRispostaTipo wSReadDownloadDocumento = null;
			try{
				wSReadDownloadDocumento = port.wSReadDownloadDocumento(pccRequest);
			} catch(javax.xml.ws.WebServiceException e) {
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}

			ReadDownloadDocumentoRispostaTipo response = ProxyConverter.toProxy(wSReadDownloadDocumento);

			this.tracciamentoUtils.popolaTracciaETrasmissioneRead(traccia, 
					wSReadDownloadDocumento.getDatiRisposta().getListaErroreTrasmissione(), 
					pccRequest.getTestataRichiesta().getTimestampTrasmissione(), 
					wSReadDownloadDocumento.getTestataRisposta().getTimestampTrasmissione(), 
					getIdEgov(port),
					this.jaxbSerializer.toXML(wSReadDownloadDocumento));

			this.log.info("Invoke: wSReadDownloadDocumento: OK");
			return response;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			if(traccia != null)
				traccia.setStato(StatoType.S_ERRORE);

			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public it.tesoro.fatture.QueryDatiFatturaRispostaTipo wSQueryDatiFattura(PccTraccia traccia, PccTracciaTrasmissione trasm) throws WSGenericFault {
		this.log.info("invoke : wSQueryDatiFattura");
		try {

			FatturaElettronica fattura = this.fatturaBD.getById(traccia.getIdFattura());
			IdFattura idFattura = new IdFattura();
			idFattura.setIdentificativoSdi(fattura.getIdentificativoSdi());
			idFattura.setPosizione(fattura.getPosizione());


			AuthorizationBeanRequest beanRequest = AuthorizationBeanFactory.getAuthorizationBeanByIdFattura(null, traccia.getUtenteRichiedente(), idFattura, NomePccOperazioneType.DATI_FATTURA);

			beanRequest.setCodiceDipartimento(fattura.getCodiceDestinatario());

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.QUERY_DATI_FATTURA);
			it.tesoro.fatture.QueryDatiFatturaRispostaTipo wSQueryDatiFattura = null;
			try{
				wSQueryDatiFattura = port.wSQueryDatiFattura(PccConverter.toPCCDatiFattura(traccia, trasm.getIdPccTransazione()));
			} catch(javax.xml.ws.WebServiceException e) {
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}


			PccTracciaTrasmissioneEsito esito = this.tracciamentoUtils.aggiornaTracciaETrasmissioneQuery(traccia, trasm, beanRequest, wSQueryDatiFattura.getDatiRisposta(), getIdEgov(port), this.jaxbSerializer.toXML(wSQueryDatiFattura));

			if(traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto()) && wSQueryDatiFattura.getDatiRisposta().getEsitoTrasmissione().value().equals(EsitoOkKoTipo.OK.value())) {

				this.log.info("Eseguo il riallineamento della fattura");

				this.tracciamentoOperazioneContabileUtils.riallineaStatoContabileFattura(idFattura, wSQueryDatiFattura);
				this.fatturaBD.updateEsitoContabilizzazione(idFattura, esito);
				this.fatturaBD.updateEsitoScadenza(idFattura, esito);

				this.log.info("Controllo data scadenza della fattura");
				Date dataScadenza = this.tracciamentoOperazioneContabileUtils.getDataScadenza(idFattura, fattura.getImportoTotaleDocumento());
				boolean daPagare = this.tracciamentoOperazioneContabileUtils.isDaPagare(idFattura);
				this.log.info("Aggiornamento data scadenza della fattura ["+dataScadenza+"]...");
				this.fatturaBD.aggiornaDataScadenza(idFattura, dataScadenza, daPagare);
				this.log.info("Aggiornamento data scadenza della fattura ["+dataScadenza+"] completato");

			}

			this.checkPerNotifica(traccia, wSQueryDatiFattura.getDatiRisposta().getEsitoTrasmissione(), wSQueryDatiFattura.getDatiRisposta().getEsitoElaborazioneTransazione());

			this.log.info("Invoke: wSQueryDatiFattura: OK");
			return wSQueryDatiFattura;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	public QueryDatiFatturaRispostaTipo wSQueryDatiFattura(PccTraccia traccia) throws WSGenericFault, WSResultNotReadyFault {
		try {
			if(traccia.getRispostaXml() == null) {
				throw getResultNotReadyFault("Identificativo PA: " +traccia.getIdPaTransazione());
			}

			return ProxyConverter.toProxy(this.jaxbSerializer.toQueryDatiFatturaRispostaTipo(traccia.getRispostaXml()));
		}catch(WSResultNotReadyFault e) {
			this.log.error("WSResultNotReadyFault: " + e.getMessage(), e);
			throw e;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	public QueryPagamentoIvaRispostaTipo wSQueryPagamentoIva(PccTraccia traccia) throws WSGenericFault, WSResultNotReadyFault {
		try {

			if(traccia.getRispostaXml() == null) {
				throw getResultNotReadyFault("Identificativo PA: " +traccia.getIdPaTransazione());
			}

			return ProxyConverter.toProxy(this.jaxbSerializer.toQueryPagamentoIvaRispostaTipo(traccia.getRispostaXml()));
		}catch(WSResultNotReadyFault e) {
			this.log.error("WSResultNotReadyFault: " + e.getMessage(), e);
			throw e;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	public void wSQueryPagamentoIva(PccTraccia traccia, PccTracciaTrasmissione trasm) throws WSGenericFault {
		this.log.info("invoke : wSQueryPagamentoIva");
		try {


			FatturaElettronica fattura = this.fatturaBD.getById(traccia.getIdFattura());
			IdFattura idFattura = new IdFattura();
			idFattura.setIdentificativoSdi(fattura.getIdentificativoSdi());
			idFattura.setPosizione(fattura.getPosizione());

			AuthorizationBeanRequest beanRequest = AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(null, traccia.getUtenteRichiedente(), fattura.getCodiceDestinatario(), NomePccOperazioneType.PAGAMENTO_IVA);

			beanRequest.setCodiceDipartimento(fattura.getCodiceDestinatario());

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.QUERY_PAGAMENTO_IVA);

			QueryPagamentoIvaRichiestaTipo pccRequest = PccConverter.toPCCPagamentoIva(traccia, trasm.getIdPccTransazione());
			it.tesoro.fatture.QueryPagamentoIvaRispostaTipo wSQueryPagamentoIva = null;
			try{
				wSQueryPagamentoIva = port.wSQueryPagamentoIva(pccRequest);
			} catch(javax.xml.ws.WebServiceException e) {
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}
			this.tracciamentoUtils.aggiornaTracciaETrasmissioneQuery(traccia, trasm, beanRequest, wSQueryPagamentoIva.getDatiRisposta(), getIdEgov(port), this.jaxbSerializer.toXML(wSQueryPagamentoIva));

			this.checkPerNotifica(traccia, wSQueryPagamentoIva.getDatiRisposta().getEsitoTrasmissione(), wSQueryPagamentoIva.getDatiRisposta().getEsitoElaborazioneTransazione());

			this.log.info("Invoke: wSQueryPagamentoIva: OK");
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	public ReadElencoMovimentiErarioIvaRispostaTipo wSReadElencoMovimentiErarioIva(ReadElencoMovimentiErarioIvaRichiestaTipo readElencoMovimentiErarioIvaRichiestaTipo, AuthorizationBeanResponse beanResponse) throws WSGenericFault {
		this.log.info("invoke : wSReadElencoMovimentiErarioIva");
		PccTraccia traccia = null;
		try {

			it.tesoro.fatture.ReadElencoMovimentiErarioIvaRichiestaTipo pccRequest = PccConverter.toPCC(readElencoMovimentiErarioIvaRichiestaTipo, beanResponse);

			traccia = this.tracciamentoUtils.getTracciaRead(NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA, readElencoMovimentiErarioIvaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest));
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.READ_ELENCO_MOVIMENTI_ERARIO_IVA);
			it.tesoro.fatture.ReadElencoMovimentiErarioIvaRispostaTipo wSReadElencoMovimentiErarioIva = null;
			try {
				wSReadElencoMovimentiErarioIva = port.wSReadElencoMovimentiErarioIva(pccRequest);
			} catch(javax.xml.ws.WebServiceException e) {
				throw new Exception("Errore interno: " + e.getMessage(), e);
			}

			ReadElencoMovimentiErarioIvaRispostaTipo response = ProxyConverter.toProxy(wSReadElencoMovimentiErarioIva);


			this.tracciamentoUtils.popolaTracciaETrasmissioneRead(traccia, 
					wSReadElencoMovimentiErarioIva.getDatiRisposta().getListaErroreTrasmissione(), 
					pccRequest.getTestataRichiesta().getTimestampTrasmissione(), 
					wSReadElencoMovimentiErarioIva.getTestataRisposta().getTimestampTrasmissione(), 
					getIdEgov(port),
					this.jaxbSerializer.toXML(wSReadElencoMovimentiErarioIva));
			this.log.info("Invoke: wSReadElencoMovimentiErarioIva: OK");
			return response;
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			if(traccia != null)
				traccia.setStato(StatoType.S_ERRORE);

			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertOrUpdateTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public PccTraccia getTraccia(QueryInserimentoFatturaRichiestaTipo queryInserimentoFatturaRichiestaTipo) throws Exception {
		return getTraccia(queryInserimentoFatturaRichiestaTipo.getTestataRichiesta().getIdentificativoTransazionePA());
	}

	public PccTraccia getTraccia(QueryOperazioneContabileRichiestaTipo queryOperazioneContabileRichiestaTipo) throws Exception {
		return getTraccia(queryOperazioneContabileRichiestaTipo.getTestataRichiesta().getIdentificativoTransazionePA());
	}

	public PccTraccia getTraccia(org.govmix.pcc.fatture.QueryPagamentoIvaRichiestaTipo queryPagamentoIvaRichiestaTipo) throws Exception {
		return getTraccia(queryPagamentoIvaRichiestaTipo.getTestataRichiesta().getIdentificativoTransazionePA());
	}

	public PccTraccia getTraccia(QueryDatiFatturaRichiestaTipo queryDatiFatturaRichiestaTipo) throws Exception {
		return getTraccia(queryDatiFatturaRichiestaTipo.getTestataRichiesta().getIdentificativoTransazionePA());
	}


}
