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
package org.govmix.pcc.fatture;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanFactory;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanResponse;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationManager;
import org.govmix.proxy.pcc.wsclient.PccWsClient;

public class FattureWSImpl implements FattureWS {

	@Resource 
	private WebServiceContext wsContext;

	private PccWsClient client;
	private AuthorizationManager authorizationManager;
	private Logger log;

	public FattureWSImpl() throws Exception {
		this.log = LoggerManager.getEndpointProxyPccLogger();
		this.log.info("Inizializzazione Endpoint WSFatture...");
		this.client = new PccWsClient(this.log);
		this.authorizationManager = new AuthorizationManager(log);
		this.log.info("Inizializzazione Endpoint WSFatture completata");
		this.log.info("Info versione: " + CommonsProperties.getInstance(log).getInfoVersione());
	}

	private String getPrincipal() throws Exception {
		List<String> principals = ((Map<Object, List<String>>)wsContext.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS)).get("PRINCIPAL_PROXY");
		
		if(principals != null && principals.size() > 0) {
			
			return principals.get(0);
		} else {
			throw new Exception("Principal utente non trovato");
		}

	}

	@Override
	public ProxyRispostaTipo wSProxyPagamentoIva(ProxyPagamentoIvaRichiestaTipo proxyPagamentoIvaRichiestaTipo) throws WSGenericFault, WSAuthorizationFault {
		try {
			AuthorizationBeanResponse beanResponse = this.authorizationManager.authorizeByCodiceDestinatario(AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(
					this.getPrincipal(), proxyPagamentoIvaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), proxyPagamentoIvaRichiestaTipo.getTestataRichiesta().getIdentificativoUnitaOrganizzativa(), NomePccOperazioneType.PAGAMENTO_IVA));
			return this.client.wSProxyPagamentoIva(proxyPagamentoIvaRichiestaTipo, beanResponse);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public QueryInserimentoFatturaRispostaTipo wSQueryInserimentoFattura(QueryInserimentoFatturaRichiestaTipo queryInserimentoFatturaRichiestaTipo) throws WSGenericFault, WSAuthorizationFault, WSResultNotReadyFault {
		try {
			PccTraccia traccia = this.client.getTraccia(queryInserimentoFatturaRichiestaTipo);
			this.authorizationManager.authorizeByCodiceDestinatario(AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(this.getPrincipal(), queryInserimentoFatturaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), traccia.getCodiceDipartimento(), NomePccOperazioneType.INSERIMENTO_FATTURA));
			return this.client.wSQueryInserimentoFattura(traccia);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e); 
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public ReadStatoFatturaRispostaTipo wSReadStatoFattura(ReadStatoFatturaRichiestaTipo readStatoFatturaRichiestaTipo)  throws WSGenericFault, WSAuthorizationFault {
		try {
			AuthorizationBeanResponse beanResponse = this.authorizationManager.authorizeByIdFattura(AuthorizationBeanFactory.getAuthorizationBeanByIdFattura(this.getPrincipal(), readStatoFatturaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), readStatoFatturaRichiestaTipo.getDatiRichiesta(), NomePccOperazioneType.STATO_FATTURA));
			return this.client.wSReadStatoFattura(readStatoFatturaRichiestaTipo, beanResponse);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public QueryOperazioneContabileRispostaTipo wSQueryOperazioneContabile(QueryOperazioneContabileRichiestaTipo queryOperazioneContabileRichiestaTipo) throws WSGenericFault, WSAuthorizationFault, WSResultNotReadyFault {
		try {
			PccTraccia traccia = this.client.getTraccia(queryOperazioneContabileRichiestaTipo);
			
			NomePccOperazioneType nomePccOperazione = NomePccOperazioneType.toEnumConstant(traccia.getOperazione());
			if(nomePccOperazione.equals(NomePccOperazioneType.OPERAZIONE_CONTABILE_CPS)) {
				this.authorizationManager.authorizeByIdFattura(AuthorizationBeanFactory.getAuthorizationBeanByIdFattura(this.getPrincipal(), queryOperazioneContabileRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), traccia.getIdFattura(), NomePccOperazioneType.OPERAZIONE_CONTABILE_CO));
			} else if(nomePccOperazione.equals(NomePccOperazioneType.OPERAZIONE_CONTABILE_CSPC)) {
				this.authorizationManager.authorizeByIdFattura(AuthorizationBeanFactory.getAuthorizationBeanByIdFattura(this.getPrincipal(), queryOperazioneContabileRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), traccia.getIdFattura(), NomePccOperazioneType.OPERAZIONE_CONTABILE_CS));
			} else {
				this.authorizationManager.authorizeByIdFattura(AuthorizationBeanFactory.getAuthorizationBeanByIdFattura(this.getPrincipal(), queryOperazioneContabileRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), traccia.getIdFattura(), nomePccOperazione));
			}
			return this.client.wSQueryOperazioneContabile(traccia);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public ProxyRispostaTipo wSProxyDatiFattura(ProxyDatiFatturaRichiestaTipo proxyDatiFatturaRichiestaTipo) throws WSGenericFault, WSAuthorizationFault {
		try {
			AuthorizationBeanResponse beanResponse = this.authorizationManager.authorizeByIdFattura(AuthorizationBeanFactory.getAuthorizationBeanByIdFattura(this.getPrincipal(), proxyDatiFatturaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), proxyDatiFatturaRichiestaTipo.getDatiRichiesta(), NomePccOperazioneType.DATI_FATTURA));
			return this.client.wSProxyDatiFattura(proxyDatiFatturaRichiestaTipo, beanResponse);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e); 
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public ProxyRispostaTipo wSProxyInserimentoFattura(ProxyInserimentoFatturaRichiestaTipo proxyInserimentoFatturaRichiestaTipo) throws WSGenericFault, WSAuthorizationFault {
		try {
			AuthorizationBeanResponse beanResponse = this.authorizationManager.authorizeByCodiceDestinatario(AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(this.getPrincipal(), proxyInserimentoFatturaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), proxyInserimentoFatturaRichiestaTipo.getDatiRichiesta().getDatiAmministrazione().getCodiceUnivocoUfficioIPA(), NomePccOperazioneType.INSERIMENTO_FATTURA));
			return this.client.wSProxyInserimentoFattura(proxyInserimentoFatturaRichiestaTipo, beanResponse);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e); 
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public ProxyRispostaTipo wSProxyOperazioneContabile(ProxyOperazioneContabileRichiestaTipo proxyOperazioneContabileRichiestaTipo) throws WSGenericFault, WSAuthorizationFault {
		try {
			AuthorizationBeanResponse beanResponse = this.authorizationManager.authorizeByIdFattura(AuthorizationBeanFactory.getAuthorizationBeanOperazioneContabileByIdFattura(this.getPrincipal(), proxyOperazioneContabileRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), proxyOperazioneContabileRichiestaTipo.getDatiRichiesta()));
			return this.client.wSProxyOperazioneContabile(proxyOperazioneContabileRichiestaTipo, beanResponse);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public ReadDownloadDocumentoRispostaTipo wSReadDownloadDocumento(ReadDownloadDocumentoRichiestaTipo readDownloadDocumentoRichiestaTipo) throws WSGenericFault, WSAuthorizationFault {
		try {
			AuthorizationBeanResponse beanResponse = this.authorizationManager.authorizeByCodiceDestinatario(AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(this.getPrincipal(), readDownloadDocumentoRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), readDownloadDocumentoRichiestaTipo.getTestataRichiesta().getIdentificativoPCCUfficio(), NomePccOperazioneType.DOWNLOAD_DOCUMENTO));
			return this.client.wSReadDownloadDocumento(readDownloadDocumentoRichiestaTipo, beanResponse);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public QueryDatiFatturaRispostaTipo wSQueryDatiFattura(QueryDatiFatturaRichiestaTipo queryDatiFatturaRichiestaTipo) throws WSGenericFault, WSAuthorizationFault, WSResultNotReadyFault {
		try {
			PccTraccia traccia = this.client.getTraccia(queryDatiFatturaRichiestaTipo);
			this.authorizationManager.authorizeByIdFattura(AuthorizationBeanFactory.getAuthorizationBeanByIdFattura(this.getPrincipal(), queryDatiFatturaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), traccia.getIdFattura(), NomePccOperazioneType.DATI_FATTURA));
			return this.client.wSQueryDatiFattura(traccia);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public QueryPagamentoIvaRispostaTipo wSQueryPagamentoIva(QueryPagamentoIvaRichiestaTipo queryPagamentoIvaRichiestaTipo) throws WSGenericFault, WSAuthorizationFault, WSResultNotReadyFault {
		try {
			PccTraccia traccia = this.client.getTraccia(queryPagamentoIvaRichiestaTipo);
			
			this.authorizationManager.authorizeByCodiceDestinatario(AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(this.getPrincipal(), queryPagamentoIvaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), traccia.getCodiceDipartimento(), NomePccOperazioneType.PAGAMENTO_IVA));

			return this.client.wSQueryPagamentoIva(traccia);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}

	@Override
	public ReadElencoMovimentiErarioIvaRispostaTipo wSReadElencoMovimentiErarioIva(ReadElencoMovimentiErarioIvaRichiestaTipo readElencoMovimentiErarioIvaRichiestaTipo) throws WSGenericFault, WSAuthorizationFault {
		log.info("invoke : wSReadElencoMovimentiErarioIva");
		try {
			AuthorizationBeanResponse beanResponse = this.authorizationManager.authorizeByCodiceDestinatario(AuthorizationBeanFactory.getAuthorizationBeanByCodiceDestinatario(this.getPrincipal(), readElencoMovimentiErarioIvaRichiestaTipo.getTestataRichiesta().getUtenteRichiedente(), readElencoMovimentiErarioIvaRichiestaTipo.getTestataRichiesta().getIdentificativoUnitaOrganizzativa(), NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA));
			return this.client.wSReadElencoMovimentiErarioIva(readElencoMovimentiErarioIvaRichiestaTipo, beanResponse);
		}catch(WSAuthorizationFault e) {
			log.error("Authorization Fault: " +e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage(), e);
			throw new WSGenericFault(e.getMessage(), e);
		}
	}
	
	

}
