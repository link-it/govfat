/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
import it.tesoro.fatture.QueryPagamentoIvaRichiestaTipo;
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

			it.tesoro.fatture.ProxyRispostaTipo wSProxyPagamentoIva = port.wSProxyPagamentoIva(proxyPagamentoIvaRichiestaTipo);

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
					this.tracciamentoUtils.aggiornaTracciaRispedizione(traccia);
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
			traccia = this.tracciamentoUtils.getTracciaProxy(NomePccOperazioneType.PAGAMENTO_IVA, proxyPagamentoIvaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(request));
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_PAGAMENTO_IVA);

			wSProxyPagamentoIva = port.wSProxyPagamentoIva(request);

			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyPagamentoIva,getIdEgov(port));
			this.log.info("Invoke: wSProxyPagamentoIva: OK");

			return ProxyConverter.toProxy(wSProxyPagamentoIva, testata);
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			if(traccia != null && wSProxyPagamentoIva != null) {
				try {
					TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					return ProxyConverter.toProxy(wSProxyPagamentoIva, testata);
				} catch (Exception ec) {
					throw new WSGenericFault(ec.getMessage(), ec);
				}
			}
			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
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
			it.tesoro.fatture.QueryInserimentoFatturaRispostaTipo wSQueryInserimentoFattura = port.wSQueryInserimentoFattura(PccConverter.toPCCInserimentoFattura(traccia, trasm.getIdPccTransazione()));
			this.tracciamentoUtils.aggiornaTracciaETrasmissioneQuery(traccia, trasm, beanRequest, wSQueryInserimentoFattura.getDatiRisposta(), getIdEgov(port), this.jaxbSerializer.toXML(wSQueryInserimentoFattura));
			
			this.checkPerNotifica(traccia, wSQueryInserimentoFattura.getDatiRisposta().getEsitoTrasmissione(), wSQueryInserimentoFattura.getDatiRisposta().getEsitoElaborazioneTransazione());
			
			this.log.info("Invoke: wSQueryInserimentoFattura: OK");
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}
	
	private void checkPerNotifica(PccTraccia traccia, EsitoOkKoTipo esitoTrasmissione, String esitoElaborazione) throws Exception {
		if(esitoTrasmissione.equals(EsitoOkKoTipo.KO)  || !esitoElaborazione.equals("OK")) {
			if(!traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
				this.tracciamentoUtils.creaNotifica(traccia);
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

			it.tesoro.fatture.ReadStatoFatturaRispostaTipo wSReadStatoFattura = port.wSReadStatoFattura(pccRequest);


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
					this.tracciamentoUtils.insertTraccia(traccia);
					if(traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {

						PccTracciaTrasmissioneEsito esito = null;
						for(PccTracciaTrasmissione trasm: traccia.getPccTracciaTrasmissioneList()){
							for(PccTracciaTrasmissioneEsito es: trasm.getPccTracciaTrasmissioneEsitoList()) {
								esito = es;
								break;
							}
						}
						this.fatturaBD.updateEsitoContabilizzazione(beanResponse.getIdLogicoFattura(), esito, false);
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
			it.tesoro.fatture.QueryOperazioneContabileRispostaTipo wSQueryOperazioneContabile = port.wSQueryOperazioneContabile(PccConverter.toPCCOperazioneContabile(traccia, trasm.getIdPccTransazione()));

			beanRequest.setCodiceDipartimento(fattura.getCodiceDestinatario());
			PccTracciaTrasmissioneEsito esito = this.tracciamentoUtils.aggiornaTracciaETrasmissioneQueryOperazioneContabile(traccia, trasm, beanRequest, wSQueryOperazioneContabile.getDatiRisposta(), getIdEgov(port), this.jaxbSerializer.toXML(wSQueryOperazioneContabile));

			if(tipoOperazione.value().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.CO.value())) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], aggiorno l'esito contabilizzazione");
				this.fatturaBD.updateEsitoContabilizzazione(idFattura, esito, false);
			} else if(tipoOperazione.value().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.CS.value())) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CS+"], aggiorno l'esito scadenza");
				this.fatturaBD.updateEsitoScadenza(idFattura, esito, false);
			} else if(tipoOperazione.value().equals(org.govmix.pcc.fatture.TipoOperazioneTipo.CCS.value())) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CCS+"], aggiorno l'esito scadenza");
				this.fatturaBD.updateEsitoScadenza(idFattura, esito, false);
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
			
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_DATI_FATTURA);
			it.tesoro.fatture.ProxyRispostaTipo wSProxyDatiFattura = port.wSProxyDatiFattura(proxyDatiFatturaRichiestaTipo);

			this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyDatiFattura, getIdEgov(port));
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
					this.tracciamentoUtils.aggiornaTracciaRispedizione(traccia);
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

			traccia = this.tracciamentoUtils.getTracciaProxy(NomePccOperazioneType.DATI_FATTURA, proxyDatiFatturaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest));

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_DATI_FATTURA);
			wSProxyDatiFattura = port.wSProxyDatiFattura(pccRequest);
			
			boolean existScadenze = this.tracciamentoOperazioneContabileUtils.existScadenze(beanResponse.getIdLogicoFattura());
			boolean existsContabilizzazioni = this.tracciamentoOperazioneContabileUtils.existContabilizzazioni(beanResponse.getIdLogicoFattura());

			if(beanResponse.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto()) && wSProxyDatiFattura.getDatiRisposta().getEsitoTrasmissione().value().equals(EsitoOkKoTipo.OK.value())) {
				this.fatturaBD.updateEsitoContabilizzazione(beanResponse.getIdLogicoFattura(), null, existsContabilizzazioni);
				this.fatturaBD.updateEsitoScadenza(beanResponse.getIdLogicoFattura(), null, existScadenze);
			}
			
			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyDatiFattura, getIdEgov(port));
			this.log.info("Invoke: wSProxyDatiFattura: OK");
			return ProxyConverter.toProxy(wSProxyDatiFattura, testata);
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e); 
			if(traccia != null && wSProxyDatiFattura != null) {
				try {
					TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					return ProxyConverter.toProxy(wSProxyDatiFattura, testata);
				} catch (Exception ec) {
					throw new WSGenericFault(ec.getMessage(), ec);
				}
			}
			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public void wSProxyInserimentoFattura(PccTraccia traccia) throws WSGenericFault {
		this.log.info("invoke : wSProxyInserimentoFattura");
		try {

			it.tesoro.fatture.ProxyInserimentoFatturaRichiestaTipo proxyInserimentoFatturaRichiestaTipo = this.jaxbSerializer.toProxyInserimentoFatturaRichiestaTipo(traccia.getRichiestaXml());
			this.processTestataPerRispedizione(proxyInserimentoFatturaRichiestaTipo.getTestataRichiesta(), traccia);
			
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_INSERIMENTO_FATTURA);
			it.tesoro.fatture.ProxyRispostaTipo wSProxyInserimentoFattura = port.wSProxyInserimentoFattura(proxyInserimentoFatturaRichiestaTipo);
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
					this.tracciamentoUtils.insertTraccia(traccia);
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

			traccia = this.tracciamentoUtils.getTracciaProxy(NomePccOperazioneType.INSERIMENTO_FATTURA, proxyInserimentoFatturaRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest));

			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.PROXY_INSERIMENTO_FATTURA);
			wSProxyInserimentoFattura = port.wSProxyInserimentoFattura(pccRequest);
			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyInserimentoFattura, getIdEgov(port));
			this.log.info("Invoke: wSProxyInserimentoFattura: OK");
			return ProxyConverter.toProxy(wSProxyInserimentoFattura, testata);
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e); 
			if(traccia != null && wSProxyInserimentoFattura != null) {
				try {
					TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					return ProxyConverter.toProxy(wSProxyInserimentoFattura, testata);
				} catch (Exception ec) {
					throw new WSGenericFault(ec.getMessage(), ec);
				}
			}
			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
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

			it.tesoro.fatture.ProxyRispostaTipo wSProxyOperazioneContabile = port.wSProxyOperazioneContabile(proxyOperazioneContabileRichiestaTipo);

			this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyOperazioneContabile, getIdEgov(port));

			this.log.info("Invoke: wSProxyOperazioneContabile: OK");
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
					this.tracciamentoUtils.aggiornaTracciaRispedizione(traccia);
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

			boolean existsScadenze = false;
			boolean existsContabilizzazioni = false;
			List<ContabilizzazioneTipo> contabilizzazioniOriginali = null;

			if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.CO.value())) {

				if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione() != null &&
						!proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione().isEmpty()) {
					//aggiorno la descrizione per inviare i metadati alla PCC
					for(ContabilizzazioneTipo cont: proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione()) {
						cont.setDescrizione(TransformUtils.toRawDescrizioneImportoContabilizzazione(cont.getDescrizione(), beanResponse.getSistemaRichiedente(), 
								beanResponse.getUtenteRichiedente(), cont.getIdentificativoMovimento()));
					}
				}
				
				contabilizzazioniOriginali = new ArrayList<ContabilizzazioneTipo>();
				contabilizzazioniOriginali.addAll(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione());

				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], aggrego le contabilizzazioni inviatemi con quelle gia presenti nel DB");
				List<ContabilizzazioneTipo> contabilizzazione = null;
				if(beanResponse.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
					contabilizzazione = this.tracciamentoOperazioneContabileUtils.getContabilizzazioniByIdFatturaEDiversoSistemaRichiedente(idFattura, beanResponse.getSistemaRichiedente());
				} else {
					contabilizzazione = this.tracciamentoOperazioneContabileUtils.getContabilizzazioniByIdFatturaDiversoSistemaRichiedenteEIdImportoDiversi(idFattura, beanResponse.getSistemaRichiedente(), contabilizzazioniOriginali);
				}
				proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione().addAll(contabilizzazione);

				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], controllo che la lista di contabilizzazioni inviata alla PCC non sia vuota");
				if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione() == null ||
						proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione() == null || 
						proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione().isEmpty()) {
					throw new FormatoErratoException("Impossibile inviare una lista di contabilizzazioni senza alcuna contabilizzazione");
				}

				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], controllo che l'importo totale inviato non sia inferiore a quanto contabilizzato in precedenza");
				
				List<ContabilizzazioneTipo> contabilizzazioniByIdFattura = this.tracciamentoOperazioneContabileUtils.getContabilizzazioniByIdFattura(idFattura);
				existsContabilizzazioni = !contabilizzazioniByIdFattura.isEmpty();
				
				double importoGiaContabilizzato = 0;
				double importoDaContabilizzare = 0;
				
				for(ContabilizzazioneTipo contab: contabilizzazioniByIdFattura) {
					importoGiaContabilizzato += contab.getImportoMovimento().doubleValue();
				}
				
				for(ContabilizzazioneTipo contab: proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione()) {
					importoDaContabilizzare += contab.getImportoMovimento().doubleValue();
				}
				
				if(importoDaContabilizzare < importoGiaContabilizzato) {
					throw new OperazioneNonPermessaException("Impossibile contabilizzare l'importo"+importoDaContabilizzare+" in quanto risulta gia' contabilizzato un importo maggiore ("+importoGiaContabilizzato+")"); 
				}

			} else if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.CS.value())) {

				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CS+"]");

				List<PccScadenza> lstScadenze = this.tracciamentoOperazioneContabileUtils.getScadenze(idFattura);
				for(PccScadenza scadenza: lstScadenze) {
					if(scadenza.getPagatoRicontabilizzato() != null && scadenza.getPagatoRicontabilizzato().doubleValue() == 0) {
						existsScadenze = true;
					}
						
				}

				List<ComunicazioneScadenzaTipo> comunicazioneScadenza = proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getComunicazioneScadenza();
				
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
				
				if(existsScadenze) {
					
					this.log.info("Trovate scadenze per la fattura, eseguo prima una Cancellazione Scadenze. IdPA originale["+proxyOperazioneContabileRichiestaTipo.getTestataRichiesta().getIdentificativoTransazionePA()+"]");

					ProxyOperazioneContabileRichiestaTipo proxyOperazioneContabileRichiestaTipo2 = ProxyConverter.newCancellazioneScadenze(proxyOperazioneContabileRichiestaTipo);

					this.log.info("Trovate scadenze per la fattura, eseguo prima una Cancellazione Scadenze. IdPa ["+proxyOperazioneContabileRichiestaTipo2.getTestataRichiesta().getIdentificativoTransazionePA()+"]"
							+ "IdPA originale["+proxyOperazioneContabileRichiestaTipo.getTestataRichiesta().getIdentificativoTransazionePA()+"]");
					ProxyRispostaTipo wSProxyCCS = this.wSProxyOperazioneContabile(proxyOperazioneContabileRichiestaTipo2, beanResponse);

					if(wSProxyCCS.getDatiRisposta().getEsitoTrasmissione().equals(EsitoOkKoTipo.KO)) {
						return wSProxyCCS;
					}
				}
			} else if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.SP.value())) {
				this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.SP+"], controllo che il saldo dei pagamenti sia maggiore dell'importo stornato");
				for(PagamentoStornoTipo pagamento: proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getPagamentoStorno()) {
					double importo = this.tracciamentoOperazioneContabileUtils.getImportoByIdFattura(beanResponse.getIdLogicoFattura(), pagamento.getNaturaSpesa().value());
					if(importo < pagamento.getImportoStorno().doubleValue()) {
						throw new OperazioneNonPermessaException("Impossibile stornare un importo di ["+pagamento.getImportoStorno().doubleValue()+"] con natura ["+pagamento.getNaturaSpesa().value()+"]. Importo massimo stornabile ["+importo+"]");
					}
				}
			}

			it.tesoro.fatture.ProxyOperazioneContabileRichiestaTipo pccRequest = PccConverter.toPCC(proxyOperazioneContabileRichiestaTipo, beanResponse);

			wSProxyOperazioneContabile = port.wSProxyOperazioneContabile(pccRequest);
			
			traccia = this.tracciamentoUtils.getTracciaProxy(op, 
					proxyOperazioneContabileRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest));

			TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaETrasmissioneProxy(traccia, wSProxyOperazioneContabile, getIdEgov(port));

			this.log.info("Esito invocazione pcc: " +testata.getEsito() + ". Origine: " + testata.getOrigine());
			if(testata.getEsito().value().equals(org.govmix.pcc.fatture.EsitoOkKoTipo.OK.value())) {

				if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.CO.value())) {
					this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], annullo l'esito contabilizzazione");
					this.fatturaBD.updateEsitoContabilizzazione(idFattura, null, existsContabilizzazioni);
					
					this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CO+"], aggiorno di conseguenza le scadenze");
					this.tracciamentoOperazioneContabileUtils.aggiornaScadenzeDopoContabilizzazione(idFattura);

					proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione().clear();
					proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione().addAll(contabilizzazioniOriginali);
				} else if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.CS.value()) && !existsScadenze) {
					this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CS+"], annullo l'esito scadenza");
					this.fatturaBD.updateEsitoScadenza(idFattura, null, existsScadenze);
				} else if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.CCS.value())) {
					this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CCS+"], annullo l'esito scadenza");
					this.fatturaBD.updateEsitoScadenza(idFattura, null, existsScadenze);
				} else if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.CP.value())) {
					this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.CP+"], aggiorno contabilizzazioni e scadenze rispetto ai pagamenti inviati");
					
					for(PagamentoTipo pagamento: proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getPagamento()) {
						this.tracciamentoOperazioneContabileUtils.aggiornaContabilizzazioniEScadenzeDopoPagamento(idFattura, pagamento);
					}
				} else if(proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione().value().equals(TipoOperazioneTipo.SP.value())) {
					
					this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.SP+"], controllo che il saldo dei pagamenti sia maggiore dell'importo stornato ok");
					this.log.info("Tipo operazione contabile ["+TipoOperazioneTipo.SP+"], aggiorno contabilizzazioni rispetto agli storni di pagamenti inviati");
					
					for(PagamentoStornoTipo pagamento: proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getStrutturaDatiOperazione().getPagamentoStorno()) {
						this.tracciamentoOperazioneContabileUtils.aggiornaContabilizzazioniDopoStornoPagamento(idFattura, pagamento, beanResponse);
					}
				} else {
					this.log.info("Tipo operazione contabile ["+proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione().getTipoOperazione()+"]");
				}
				this.tracciamentoOperazioneContabileUtils.tracciaOperazioneContabile(proxyOperazioneContabileRichiestaTipo, beanResponse, idFattura);

				this.log.info("Tracciamento operazione contabile completato");
				
				this.log.info("Controllo data scadenza della fattura");
				Date dataScadenza = this.tracciamentoOperazioneContabileUtils.getDataScadenza(idFattura, fattura.getImportoTotaleDocumento());
				boolean daPagare = this.tracciamentoOperazioneContabileUtils.isDaPagare(idFattura);
				this.log.info("Aggiornamento data scadenza della fattura ["+dataScadenza+"]...");
				this.fatturaBD.aggiornaDataScadenza(idFattura, dataScadenza, daPagare);
				this.log.info("Aggiornamento data scadenza della fattura ["+dataScadenza+"] completato");
			}
			this.log.info("Invoke: wSProxyOperazioneContabile: OK");
			return ProxyConverter.toProxy(wSProxyOperazioneContabile, testata);
		}catch(Exception e) {
			this.log.error("Error: " + e.getMessage(), e);
			if(traccia != null && wSProxyOperazioneContabile != null) {
				try {
					TestataRispTipo testata = this.tracciamentoUtils.popolaTracciaConInfoRispedizioneDefault(traccia, e);
					return ProxyConverter.toProxy(wSProxyOperazioneContabile, testata);
				} catch (Exception ec) {
					throw new WSGenericFault(e.getMessage(), e);
				}
			}
			throw new WSGenericFault(e.getMessage(), e);
		} finally {
			try {
				if(traccia != null)
					this.tracciamentoUtils.insertTraccia(traccia);
			} catch(Exception e) {
				this.log.error("Errore durante l'inserimento della traccia: " + e.getMessage(), e);
			}
		}
	}

	public ReadDownloadDocumentoRispostaTipo wSReadDownloadDocumento(ReadDownloadDocumentoRichiestaTipo readDownloadDocumentoRichiestaTipo, AuthorizationBeanResponse beanResponse) throws WSGenericFault {
		this.log.info("invoke : wSReadDownloadDocumento");
		PccTraccia traccia = null;
		try {
			
			it.tesoro.fatture.ReadDownloadDocumentoRichiestaTipo pccRequest = PccConverter.toPCC(readDownloadDocumentoRichiestaTipo, beanResponse);

			
			traccia = this.tracciamentoUtils.getTracciaRead(NomePccOperazioneType.DOWNLOAD_DOCUMENTO, readDownloadDocumentoRichiestaTipo.getTestataRichiesta(), beanResponse, this.jaxbSerializer.toXML(pccRequest));
			it.tesoro.fatture.FattureWS port = this.getFattureWS(OperazioneType.READ_DOWNLOAD_DOCUMENTO);
			it.tesoro.fatture.ReadDownloadDocumentoRispostaTipo wSReadDownloadDocumento = port.wSReadDownloadDocumento(pccRequest);

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
					this.tracciamentoUtils.insertTraccia(traccia);
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
			it.tesoro.fatture.QueryDatiFatturaRispostaTipo wSQueryDatiFattura = port.wSQueryDatiFattura(PccConverter.toPCCDatiFattura(traccia, trasm.getIdPccTransazione()));

			
			PccTracciaTrasmissioneEsito esito = this.tracciamentoUtils.aggiornaTracciaETrasmissioneQuery(traccia, trasm, beanRequest, wSQueryDatiFattura.getDatiRisposta(), getIdEgov(port), this.jaxbSerializer.toXML(wSQueryDatiFattura));

			if(traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto()) && wSQueryDatiFattura.getDatiRisposta().getEsitoTrasmissione().value().equals(EsitoOkKoTipo.OK.value())) {
				
				this.log.info("Eseguo il riallineamento della fattura");
				
				this.tracciamentoOperazioneContabileUtils.riallineaStatoContabileFattura(idFattura, wSQueryDatiFattura);
				this.fatturaBD.updateEsitoContabilizzazione(idFattura, esito, false);
				this.fatturaBD.updateEsitoScadenza(idFattura, esito, false);
				
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
			it.tesoro.fatture.QueryPagamentoIvaRispostaTipo wSQueryPagamentoIva = port.wSQueryPagamentoIva(pccRequest);
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
			it.tesoro.fatture.ReadElencoMovimentiErarioIvaRispostaTipo wSReadElencoMovimentiErarioIva = port.wSReadElencoMovimentiErarioIva(pccRequest);

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
					this.tracciamentoUtils.insertTraccia(traccia);
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
