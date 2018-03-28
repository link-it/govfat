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
package org.govmix.pcc.fatture.tracciamento.ricerca;

import java.util.List;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.apache.log4j.Logger;
import org.govmix.pcc.gettracce.EsitoTrasmissioneType;
import org.govmix.pcc.gettracce.RecuperaDettaglioTracciaRichiestaTipo;
import org.govmix.pcc.gettracce.RecuperaTracceRichiestaTipo;
import org.govmix.pcc.gettracce.RecuperaTracceRispostaTipo;
import org.govmix.pcc.gettracce.SommarioPccTraccia;
import org.govmix.pcc.gettracce.StatoType;
import org.govmix.pcc.gettracce.TipoOperazionePccType;
import org.govmix.pcc.gettracce.TipoOperazioneType;
import org.govmix.pcc.gettracce.WsTracce;
import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class RecuperaTracceImpl implements WsTracce {

	@Resource 
	private WebServiceContext wsContext;
	private PccTracciamentoBD tracciamentoBD;
	private UtenteBD utenteBD;
	private Logger log;

	public RecuperaTracceImpl() throws Exception {
		this.log = LoggerManager.getEndpointGetTracceLogger();
		this.log.info("Inizializzazione del servizio WSTracce...");
		this.tracciamentoBD = new PccTracciamentoBD(log);
		this.utenteBD = new UtenteBD(log);
		this.log.info("Inizializzazione del servizio WSTracce completata");
		this.log.info("Info versione: " + CommonsProperties.getInstance(log).getInfoVersione());
	}
	
	@Override
	public RecuperaTracceRispostaTipo recuperaTracce(RecuperaTracceRichiestaTipo recuperaTracceRichiestaTipo) {
		this.log.info("RecuperaTracce...");
		try {
			RecuperaTracceRispostaTipo getTracceRisposta = new RecuperaTracceRispostaTipo();
			
			List<PccTraccia> findAllTracce = this.tracciamentoBD.getTracce(toPaginatedExp(this.tracciamentoBD.newExp(), recuperaTracceRichiestaTipo, wsContext.getUserPrincipal().getName()), false);

			this.log.info("Trovate ["+findAllTracce.size()+"] tracce");
			
			if(findAllTracce != null) {
				for(PccTraccia traccia: findAllTracce) {
					getTracceRisposta.getSommarioPccTraccia().add(toSommarioTracciaWS(traccia));
				}
			}
			this.log.info("RecuperaTracce ok");
			return getTracceRisposta;
		} catch(Exception e) {
			this.log.error("Errore RecuperaTracce:" + e.getMessage(), e);
			return new RecuperaTracceRispostaTipo(); //TODO FAULT
		}
	}

	@Override
	public org.govmix.pcc.gettracce.PccTraccia recuperaDettaglioTraccia(
			RecuperaDettaglioTracciaRichiestaTipo recuperaTracceRichiestaTipo) {
		this.log.info("RecuperaDettaglioTraccia...");
		try {
			
			PccTraccia traccia = this.tracciamentoBD.getTracciaById(recuperaTracceRichiestaTipo.getIdtraccia());

			org.govmix.pcc.gettracce.PccTraccia getTracceRisposta = toTracciaWS(traccia);
			this.log.info("RecuperaDettaglioTraccia ok");
			return getTracceRisposta;
		} catch(Exception e) {
			this.log.error("Errore RecuperaDettaglioTraccia:" + e.getMessage(), e);
			return new org.govmix.pcc.gettracce.PccTraccia(); //TODO FAULT
		}
	}

	private IPaginatedExpression toPaginatedExp(IPaginatedExpression exp, RecuperaTracceRichiestaTipo getTracceRichiestaTipo, String principal) throws Exception {

		Utente utente = this.utenteBD.findByUsername(principal);
		
		exp.equals(PccTraccia.model().SISTEMA_RICHIEDENTE, utente.getSistema());
		
		if(getTracceRichiestaTipo.getUtenteRichiedente() != null)
			exp.equals(PccTraccia.model().UTENTE_RICHIEDENTE, getTracceRichiestaTipo.getUtenteRichiedente());
		
		if(getTracceRichiestaTipo.getSistemaRichiedente() != null)
			exp.equals(PccTraccia.model().SISTEMA_RICHIEDENTE, getTracceRichiestaTipo.getSistemaRichiedente());
		
		if(getTracceRichiestaTipo.getOperazione() != null)
			exp.equals(PccTraccia.model().OPERAZIONE, getTracceRichiestaTipo.getOperazione());
		
		if(getTracceRichiestaTipo.getTipoOperazione() != null)
			exp.equals(PccTraccia.model().TIPO_OPERAZIONE, getTracceRichiestaTipo.getTipoOperazione().value());
		
		if(getTracceRichiestaTipo.getIdFattura() != null)
			exp.equals(PccTraccia.model().ID_FATTURA, getTracceRichiestaTipo.getIdFattura());
		
		if(getTracceRichiestaTipo.getStato() != null)
			exp.equals(PccTraccia.model().STATO, getTracceRichiestaTipo.getStato().value());
		
		if(getTracceRichiestaTipo.getDataTrasmissioneMin() != null)
			exp.greaterEquals(PccTraccia.model().DATA_CREAZIONE, getTracceRichiestaTipo.getDataTrasmissioneMin());
		
		if(getTracceRichiestaTipo.getDataTrasmissioneMax() != null)
			exp.lessEquals(PccTraccia.model().DATA_CREAZIONE, getTracceRichiestaTipo.getDataTrasmissioneMax());
		
		if(getTracceRichiestaTipo.getOffset() != null && getTracceRichiestaTipo.getLimit() != null) {
			exp.sortOrder(SortOrder.ASC);
			exp.addOrder(PccTraccia.model().CF_TRASMITTENTE);
			exp.offset(getTracceRichiestaTipo.getOffset());
			exp.limit(getTracceRichiestaTipo.getLimit());
		}
		
		return exp;
	}

	private SommarioPccTraccia toSommarioTracciaWS(PccTraccia pccTraccia) {
		SommarioPccTraccia traccia = new SommarioPccTraccia();
		
		traccia.setId(pccTraccia.getId());
		traccia.setCfTrasmittente(pccTraccia.getCfTrasmittente());
		traccia.setVersioneApplicativa(pccTraccia.getVersioneApplicativa());
		traccia.setIdPccAmministrazione(pccTraccia.getIdPccAmministrazione());
		traccia.setIdPaTransazione(pccTraccia.getIdPaTransazione());
		traccia.setSistemaRichiedente(pccTraccia.getSistemaRichiedente());
		traccia.setUtenteRichiedente(pccTraccia.getUtenteRichiedente());
		
		if(pccTraccia.getIdFattura() > 0)
			traccia.setIdFattura(pccTraccia.getIdFattura());
		
		traccia.setOperazione(pccTraccia.getOperazione());
		switch(pccTraccia.getTipoOperazione()) {
		case PROXY: traccia.setTipoOperazione(TipoOperazionePccType.PROXY);
			break;
		case READ: traccia.setTipoOperazione(TipoOperazionePccType.READ);
			break;
		default:
			break;
		
		}
		
		switch(pccTraccia.getStato()) {
		case AS_ERRORE: traccia.setStato(StatoType.AS_ERRORE);
			break;
		case AS_ERRORE_PRESA_IN_CARICO: traccia.setStato(StatoType.AS_ERRORE_PRESA_IN_CARICO);
			break;
		case AS_OK: traccia.setStato(StatoType.AS_OK);
			break;
		case AS_PRESA_IN_CARICO: traccia.setStato(StatoType.AS_PRESA_IN_CARICO);
			break;
		case S_ERRORE: traccia.setStato(StatoType.S_ERRORE);
			break;
		case S_OK: traccia.setStato(StatoType.S_OK);
			break;
		default:
			break;
		}

		return traccia;
	}


	private org.govmix.pcc.gettracce.PccTraccia toTracciaWS(PccTraccia pccTraccia) {
		org.govmix.pcc.gettracce.PccTraccia traccia = new org.govmix.pcc.gettracce.PccTraccia();
		
		traccia.setDataCreazione(pccTraccia.getDataCreazione());
		traccia.setCfTrasmittente(pccTraccia.getCfTrasmittente());
		traccia.setVersioneApplicativa(pccTraccia.getVersioneApplicativa());
		traccia.setIdPccAmministrazione(pccTraccia.getIdPccAmministrazione());
		traccia.setIdPaTransazione(pccTraccia.getIdPaTransazione());
		traccia.setSistemaRichiedente(pccTraccia.getSistemaRichiedente());
		traccia.setUtenteRichiedente(pccTraccia.getUtenteRichiedente());
		
		if(pccTraccia.getIdFattura() > 0)
			traccia.setIdFattura(pccTraccia.getIdFattura());
		
		traccia.setOperazione(pccTraccia.getOperazione());
		switch(pccTraccia.getTipoOperazione()) {
		case PROXY: traccia.setTipoOperazione(TipoOperazionePccType.PROXY);
			break;
		case READ: traccia.setTipoOperazione(TipoOperazionePccType.READ);
			break;
		default:
			break;
		
		}
		
		switch(pccTraccia.getStato()) {
		case AS_ERRORE: traccia.setStato(StatoType.AS_ERRORE);
			break;
		case AS_ERRORE_PRESA_IN_CARICO: traccia.setStato(StatoType.AS_ERRORE_PRESA_IN_CARICO);
			break;
		case AS_OK: traccia.setStato(StatoType.AS_OK);
			break;
		case AS_PRESA_IN_CARICO: traccia.setStato(StatoType.AS_PRESA_IN_CARICO);
			break;
		case S_ERRORE: traccia.setStato(StatoType.S_ERRORE);
			break;
		case S_OK: traccia.setStato(StatoType.S_OK);
			break;
		default:
			break;
		}
		
		for(int i =0; i < pccTraccia.sizePccTracciaTrasmissioneList(); i++) {
			traccia.getPccTracciaTrasmissione().add(toTracciaTrasmissioneWS(pccTraccia.getPccTracciaTrasmissione(i)));
		}

		return traccia;
	}

	private org.govmix.pcc.gettracce.PccTracciaTrasmissione toTracciaTrasmissioneWS(PccTracciaTrasmissione pccTracciaTrasmissione) {
		org.govmix.pcc.gettracce.PccTracciaTrasmissione tracciaTrasmissione = new org.govmix.pcc.gettracce.PccTracciaTrasmissione();
		tracciaTrasmissione.setTsTrasmissione(pccTracciaTrasmissione.getTsTrasmissione());
		tracciaTrasmissione.setIdPccTransazione(pccTracciaTrasmissione.getIdPccTransazione());
		switch(pccTracciaTrasmissione.getEsitoTrasmissione()) {
		case KO: tracciaTrasmissione.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
			break;
		case OK: tracciaTrasmissione.setEsitoTrasmissione(EsitoTrasmissioneType.OK);
			break;
		default:
			break;
		
		}
		
		tracciaTrasmissione.setGdo(pccTracciaTrasmissione.getGdo());
		tracciaTrasmissione.setDataFineElaborazione(pccTracciaTrasmissione.getDataFineElaborazione());
		tracciaTrasmissione.setDettaglioErroreTrasmissione(pccTracciaTrasmissione.getDettaglioErroreTrasmissione());
		tracciaTrasmissione.setIdEgovRichiesta(pccTracciaTrasmissione.getIdEgovRichiesta());
		
		for(int i =0; i < pccTracciaTrasmissione.sizePccTracciaTrasmissioneEsitoList(); i++) {
			tracciaTrasmissione.getPccTracciaTrasmissioneEsito().add(toTracciaTrasmissioneEsitoWS(pccTracciaTrasmissione.getPccTracciaTrasmissioneEsito(i)));
		}

		return tracciaTrasmissione;

	}

	private org.govmix.pcc.gettracce.PccTracciaTrasmissioneEsito toTracciaTrasmissioneEsitoWS(PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) {
		org.govmix.pcc.gettracce.PccTracciaTrasmissioneEsito esito = new org.govmix.pcc.gettracce.PccTracciaTrasmissioneEsito();
		esito.setEsitoElaborazione(pccTracciaTrasmissioneEsito.getEsitoElaborazione());
		esito.setDescrizioneElaborazione(pccTracciaTrasmissioneEsito.getDescrizioneElaborazione());
		esito.setDataFineElaborazione(pccTracciaTrasmissioneEsito.getDataFineElaborazione());
		esito.setGdo(pccTracciaTrasmissioneEsito.getGdo());
		switch(pccTracciaTrasmissioneEsito.getEsitoTrasmissione()) {
		case KO: esito.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
			break;
		case OK: esito.setEsitoTrasmissione(EsitoTrasmissioneType.OK);
			break;
		default:
			break;
		
		}
		
		esito.setDettaglioErroreTrasmissione(pccTracciaTrasmissioneEsito.getDettaglioErroreTrasmissione());
		esito.setIdEgovRichiesta(pccTracciaTrasmissioneEsito.getIdEgovRichiesta());
		for(int i =0; i < pccTracciaTrasmissioneEsito.sizePccErroreElaborazioneList(); i++) {
			esito.getPccErroreElaborazione().add(toTracciaTrasmissioneEsitoErroreWS(pccTracciaTrasmissioneEsito.getPccErroreElaborazione(i)));
		}

		return esito;

	}

	private org.govmix.pcc.gettracce.PccErroreElaborazione toTracciaTrasmissioneEsitoErroreWS(PccErroreElaborazione pccErrore) {
		org.govmix.pcc.gettracce.PccErroreElaborazione errore = new org.govmix.pcc.gettracce.PccErroreElaborazione();
		switch(pccErrore.getTipoOperazione()) {
		case CCS: errore.setTipoOperazione(TipoOperazioneType.CCS);
			break;
		case CO: errore.setTipoOperazione(TipoOperazioneType.CO);
			break;
		case CP: errore.setTipoOperazione(TipoOperazioneType.CP);
			break;
		case CS: errore.setTipoOperazione(TipoOperazioneType.CS);
			break;
		case RC: errore.setTipoOperazione(TipoOperazioneType.RC);
			break;
		case RF: errore.setTipoOperazione(TipoOperazioneType.RF);
			break;
		case SC: errore.setTipoOperazione(TipoOperazioneType.SC);
			break;
		case SP: errore.setTipoOperazione(TipoOperazioneType.SP);
			break;
		default:
			break;
		
		}
		errore.setProgressivoOperazione(pccErrore.getProgressivoOperazione());
		errore.setCodiceEsito(pccErrore.getCodiceEsito());
		errore.setDescrizioneEsito(pccErrore.getDescrizioneEsito());
		return errore;
	}
}
