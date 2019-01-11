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
package org.govmix.proxy.pcc.fatture.tracciamento;

import it.tesoro.fatture.DatiRispostaOperazioneContabileQueryTipo;
import it.tesoro.fatture.ErroreElaborazioneOperazioneTipo;
import it.tesoro.fatture.ErroreTipo;
import it.tesoro.fatture.EsitoElabTransazTipo;
import it.tesoro.fatture.EsitoOkKoTipo;
import it.tesoro.fatture.ListaErroreElaborazioneOperazioneTipo;
import it.tesoro.fatture.ListaErroreTipo;
import it.tesoro.fatture.ProxyRispostaTipo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.xml.ws.WebServiceException;

import org.apache.log4j.Logger;
import org.govmix.pcc.fatture.OrigineTipo;
import org.govmix.pcc.fatture.TestataAsyncRichiestaTipo;
import org.govmix.pcc.fatture.TestataBaseRichiestaTipo;
import org.govmix.pcc.fatture.TestataRispTipo;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.PccNotifica;
import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoEsitoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.orm.constants.TipoOperazionePccType;
import org.govmix.proxy.fatturapa.orm.constants.TipoOperazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccNotificaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccRispedizioneBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanRequest;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanResponse;
import org.govmix.proxy.pcc.fatture.utils.PccProperties;

public class TracciamentoUtils {

	private PccRispedizioneBD pccRispedizioneBD;
	private PccTracciamentoBD pccTracciamentoBD;
	private PccNotificaBD pccNotificaBD;
	private Logger log;
	
	public TracciamentoUtils(Logger log) throws Exception {
		this.log = log;
		this.pccRispedizioneBD = new PccRispedizioneBD(log);
		this.pccTracciamentoBD = new PccTracciamentoBD(log);
		this.pccNotificaBD = new PccNotificaBD(log);
	}
	
	public PccTraccia getTracciaByIdPa(String idPa) throws Exception {
		return this.pccTracciamentoBD.getTracciaByIdPa(idPa);
	}
	


	public PccTraccia getTracciaProxy(NomePccOperazioneType operazione, TestataAsyncRichiestaTipo testata, AuthorizationBeanResponse beanResponse, byte[] xml, TracciamentoUtils tracciamentoUtils) throws Exception {

		if(this.pccTracciamentoBD.existsTracciaByIdPa(testata.getIdentificativoTransazionePA())) {
			throw new IdentificativoTransazionePADuplicatoException(testata.getIdentificativoTransazionePA());
		}
		
		PccTraccia traccia = new PccTraccia();
		traccia.setDataCreazione(new Date());
		traccia.setUtenteRichiedente(testata.getUtenteRichiedente());
		traccia.setSistemaRichiedente(beanResponse.getSistemaRichiedente());
		traccia.setCfTrasmittente(beanResponse.getCfTrasmittente());
		
		traccia.setIdPccAmministrazione(Integer.parseInt(beanResponse.getIdPccAmministrazione()));
		traccia.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		traccia.setRichiestaXml(xml);
		
		if(beanResponse.getIdFattura() != null && beanResponse.getIdFattura() > 0)
			traccia.setIdFattura(beanResponse.getIdFattura());
		
		traccia.setCodiceDipartimento(beanResponse.getCodiceDipartimento());
		
		traccia.setIdPaTransazione(testata.getIdentificativoTransazionePA());
		String operazioneValue = operazione.getValue();
		traccia.setOperazione(operazioneValue);
		traccia.setTipoOperazione(TipoOperazionePccType.PROXY);
		tracciamentoUtils.insertOrUpdateTraccia(traccia);
		return traccia;
	}

	public PccTraccia getTracciaRead(NomePccOperazioneType operazione, TestataBaseRichiestaTipo testata, AuthorizationBeanResponse beanResponse, byte[] xml) throws Exception {
		
		
		PccTraccia traccia = new PccTraccia();
		traccia.setDataCreazione(new Date());
		traccia.setUtenteRichiedente(testata.getUtenteRichiedente());
		traccia.setSistemaRichiedente(beanResponse.getSistemaRichiedente());
		traccia.setCfTrasmittente(beanResponse.getCfTrasmittente());

		traccia.setIdPccAmministrazione(Integer.parseInt(beanResponse.getIdPccAmministrazione()));

		traccia.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		traccia.setRichiestaXml(xml);
		
		if(beanResponse.getIdFattura() != null && beanResponse.getIdFattura() > 0)
			traccia.setIdFattura(beanResponse.getIdFattura());
		
		traccia.setCodiceDipartimento(beanResponse.getCodiceDipartimento());
		traccia.setOperazione(operazione.getValue());
		traccia.setTipoOperazione(TipoOperazionePccType.READ);
		return traccia;
	}

	public void popolaTracciaETrasmissioneRead(PccTraccia traccia, ListaErroreTipo listaErrori, Date dataTrasmissione, Date dataFineElaborazione, String idEgov, byte[] xml) {
		
		PccTracciaTrasmissione trasmissione = new PccTracciaTrasmissione();
		
		Date date = new Date();
		trasmissione.setGdo(date);
		
		trasmissione.setTsTrasmissione(dataTrasmissione);
		trasmissione.setDataFineElaborazione(dataFineElaborazione);
		trasmissione.setStatoEsito(StatoEsitoTrasmissioneType.PRESENTE);
		PccTracciaTrasmissioneEsito esito = new PccTracciaTrasmissioneEsito();

		if(listaErrori == null || listaErrori.getErroreTrasmissione().isEmpty()) {
			traccia.setStato(StatoType.S_OK);
			traccia.setRispedizione(false);
			traccia.setRispedizioneProssimoTentativo(null);
			traccia.setRispedizioneMaxTentativi(0);
			traccia.setRispedizioneNumeroTentativi(0);
			trasmissione.setEsitoTrasmissione(EsitoTrasmissioneType.OK);
			esito.setEsitoTrasmissione(EsitoTrasmissioneType.OK);
			esito.setEsitoElaborazione("OK");
		} else {
			traccia.setStato(StatoType.S_ERRORE);
			trasmissione.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
			String error = "";

			for(ErroreTipo errore: listaErrori.getErroreTrasmissione()) {
				error += errore.getDescrizione() + "\n";
			}
			trasmissione.setDettaglioErroreTrasmissione(error);
			esito.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
			esito.setEsitoElaborazione("KO");
			esito.setDettaglioErroreTrasmissione(error);
			

		}
		esito.setGdo(date);
		esito.setDataFineElaborazione(date);
		esito.setIdEgovRichiesta(idEgov);

		trasmissione.addPccTracciaTrasmissioneEsito(esito);
		trasmissione.setIdEgovRichiesta(idEgov);
		
		traccia.setRispostaXml(xml);
		traccia.setDataUltimaTrasmissione(dataTrasmissione);
		traccia.addPccTracciaTrasmissione(trasmissione);
		
	}

	public TestataRispTipo popolaTracciaETrasmissioneProxy(PccTraccia traccia, ProxyRispostaTipo risposta, String idEgov, byte[] richiesta) throws Exception {
		
		
		String idPa = traccia.getIdPaTransazioneRispedizione() != null ? traccia.getIdPaTransazioneRispedizione() : traccia.getIdPaTransazione();
		
		this.log.debug("Traccia idPA["+idPa+"]: popolo e inserisco una trasmissione");
		PccTracciaTrasmissione trasmissione = new PccTracciaTrasmissione();
		
		Date dataTrasmissione = risposta.getTestataRisposta().getTimestampTrasmissione();
		
		Date date = new Date();
		trasmissione.setGdo(date);
		trasmissione.setTsTrasmissione(dataTrasmissione);
		trasmissione.setIdPccTransazione(risposta.getDatiRisposta().getIdentificativoTransazionePCC());
		trasmissione.setStatoEsito(StatoEsitoTrasmissioneType.NON_PRESENTE);

		
		trasmissione.setIdEgovRichiesta(idEgov);
		
		traccia.setDataUltimoTentativoEsito(new Date(System.currentTimeMillis()+ (PccProperties.getInstance().getIntervalloSpedizioneEsito() * 60000) ));
		traccia.setDataUltimaTrasmissione(dataTrasmissione);
		traccia.setRichiestaXml(richiesta);
		
		traccia.addPccTracciaTrasmissione(trasmissione);

		if(risposta.getDatiRisposta().getEsitoTrasmissione().equals(EsitoOkKoTipo.OK)) {
			this.log.debug("Traccia idPA["+idPa+"]:esito OK, prendo in carico e non inserisco la rispedizione");
			traccia.setStato(StatoType.AS_PRESA_IN_CARICO);
			traccia.setRispedizione(false);

			trasmissione.setEsitoTrasmissione(EsitoTrasmissioneType.OK);
			
		} else {
			this.log.debug("Traccia idPA["+idPa+"]: esito KO, verifico la possibilita' di rispedizione");
			traccia.setStato(StatoType.AS_ERRORE_PRESA_IN_CARICO);
			String error = "";
			PccTracciaTrasmissioneEsito esito = new PccTracciaTrasmissioneEsito();
			for(ErroreTipo errore: risposta.getDatiRisposta().getListaErroreTrasmissione().getErroreTrasmissione()) {
				error += errore.getDescrizione() + "\n";
				
				PccErroreElaborazione erroreElaboraz = new PccErroreElaborazione();
				erroreElaboraz.setCodiceEsito(errore.getCodice());
				erroreElaboraz.setDescrizioneEsito(errore.getDescrizione());
				esito.addPccErroreElaborazione(erroreElaboraz);
			}

			trasmissione.setDettaglioErroreTrasmissione(error);
			trasmissione.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
			
			esito.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
			esito.setEsitoElaborazione("KO");
			esito.setDettaglioErroreTrasmissione(error);
			
			esito.setGdo(date);
			esito.setDataFineElaborazione(date);
			esito.setIdEgovRichiesta(idEgov);
			populateTracciaConInfoTracciaRispedizione(traccia, risposta.getDatiRisposta().getListaErroreTrasmissione(), false);

			trasmissione.addPccTracciaTrasmissioneEsito(esito);

		}

		return getTestataRisp(traccia.getIdPaTransazione(), risposta.getDatiRisposta().getEsitoTrasmissione(), traccia.getRispedizione());
	}


	public TestataRispTipo popolaTracciaETrasmissioneProxyErrore(PccTraccia traccia, WebServiceException e, String idEgov) throws Exception {
		
		
		String idPa = traccia.getIdPaTransazioneRispedizione() != null ? traccia.getIdPaTransazioneRispedizione() : traccia.getIdPaTransazione();
		
		this.log.debug("Traccia idPA["+idPa+"]: popolo e inserisco una trasmissione");
		PccTracciaTrasmissione trasmissione = new PccTracciaTrasmissione();
		
		Date dataTrasmissione = new Date();
		
		Date date = new Date();
		trasmissione.setGdo(date);
		trasmissione.setTsTrasmissione(dataTrasmissione);
		trasmissione.setStatoEsito(StatoEsitoTrasmissioneType.NON_PRESENTE);

		
		trasmissione.setIdEgovRichiesta(idEgov);
		
		traccia.setDataUltimoTentativoEsito(new Date(System.currentTimeMillis()+ (PccProperties.getInstance().getIntervalloSpedizioneEsito() * 60000) ));
		traccia.setDataUltimaTrasmissione(dataTrasmissione);
		
		traccia.addPccTracciaTrasmissione(trasmissione);

		this.log.debug("Traccia idPA["+idPa+"]: esito KO, dettaglio["+e.getMessage()+"]");

		trasmissione.setDettaglioErroreTrasmissione("Impossibile contattare la PCC");
		trasmissione.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
		PccTracciaTrasmissioneEsito esito = new PccTracciaTrasmissioneEsito();

		PccErroreElaborazione erroreElaboraz = new PccErroreElaborazione();
		erroreElaboraz.setCodiceEsito("KO");
		erroreElaboraz.setDescrizioneEsito("Impossibile contattare la PCC");
		esito.addPccErroreElaborazione(erroreElaboraz);

		esito.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
		esito.setEsitoElaborazione("--");
		esito.setDettaglioErroreTrasmissione("Impossibile contattare la PCC");
		
		esito.setGdo(date);
		esito.setDataFineElaborazione(date);
		esito.setIdEgovRichiesta("--");
		trasmissione.addPccTracciaTrasmissioneEsito(esito);

		return getTestataRisp(traccia.getIdPaTransazione(), EsitoOkKoTipo.KO, traccia.getRispedizione());
	}

	public PccTracciaTrasmissioneEsito aggiornaTracciaETrasmissioneQueryOperazioneContabile(
			PccTraccia traccia, PccTracciaTrasmissione tracciaTrasmissione, AuthorizationBeanRequest beanResponse,
			DatiRispostaOperazioneContabileQueryTipo datiRisposta,
			String idEgov, byte[] risposta) throws Exception {
		return aggiornaTracciaETrasmissioneQuery(traccia, tracciaTrasmissione, beanResponse, datiRisposta.getEsitoTrasmissione(), datiRisposta.getListaErroreTrasmissione(), datiRisposta.getEsitoElaborazioneTransazione(), datiRisposta.getDataFineElaborazione(), datiRisposta.getListaErroreElaborazione(), idEgov, risposta);
		
	}

	public PccTracciaTrasmissioneEsito aggiornaTracciaETrasmissioneQuery(PccTraccia traccia, PccTracciaTrasmissione tracciaTrasmissione, AuthorizationBeanRequest beanResponse, EsitoElabTransazTipo esito, String idEgov, byte[] risposta) throws Exception {
		return aggiornaTracciaETrasmissioneQuery(traccia, tracciaTrasmissione, beanResponse, esito.getEsitoTrasmissione(), esito.getListaErroreTrasmissione(), esito.getEsitoElaborazioneTransazione(), esito.getDataFineElaborazione(), null, idEgov, risposta);
	}

	public PccTracciaTrasmissioneEsito aggiornaTracciaETrasmissioneQuery(PccTraccia traccia, PccTracciaTrasmissione trasmissione, AuthorizationBeanRequest beanResponse, EsitoOkKoTipo esitoTrasmissione, ListaErroreTipo listaErroriTrasmissione, String esitoElaborazione, Date dataFineElaborazione, ListaErroreElaborazioneOperazioneTipo listaErroreElaborazione, String idEgov, byte[] risposta) throws Exception {
		
		checkForFW0010(traccia, listaErroriTrasmissione, listaErroreElaborazione);

		PccTracciaTrasmissioneEsito trasmissioneEsito = new PccTracciaTrasmissioneEsito();
		
		trasmissioneEsito.setGdo(new Date());
		trasmissioneEsito.setEsitoElaborazione(esitoElaborazione);
		trasmissioneEsito.setDataFineElaborazione(dataFineElaborazione);
		
		boolean isOkTrasmissione = false;
		boolean isOkElaborazione = false;
		
		List<PccErroreElaborazione> lstErrori = new ArrayList<PccErroreElaborazione>(); 

		if((listaErroriTrasmissione == null || listaErroriTrasmissione.getErroreTrasmissione() == null || listaErroriTrasmissione.getErroreTrasmissione().isEmpty())) {
			trasmissioneEsito.setEsitoTrasmissione(EsitoTrasmissioneType.OK);
			if(listaErroreElaborazione != null && listaErroreElaborazione.getErroreElaborazione() != null && !listaErroreElaborazione.getErroreElaborazione().isEmpty())
				trasmissioneEsito.setEsitoElaborazione("KO");
			else
				trasmissioneEsito.setEsitoElaborazione("OK");
			
			isOkTrasmissione = true;
			traccia.setRispostaXml(risposta);
			traccia.setRispedizione(false);

		} else {
			String error = "";
			for(ErroreTipo errore: listaErroriTrasmissione.getErroreTrasmissione()) {
				if(error.length() > 0)  error+= "\n";
				error += errore.getDescrizione();
				
				PccErroreElaborazione erroreElaboraz = new PccErroreElaborazione();
				erroreElaboraz.setCodiceEsito(errore.getCodice());
				erroreElaboraz.setDescrizioneEsito(errore.getDescrizione());
				lstErrori.add(erroreElaboraz);
			}
			trasmissione.setDettaglioErroreTrasmissione(error);
			trasmissioneEsito.setDettaglioErroreTrasmissione(error);
			trasmissioneEsito.setEsitoTrasmissione(EsitoTrasmissioneType.KO);
			trasmissioneEsito.setEsitoElaborazione("KO");
			
			populateTracciaConInfoTracciaRispedizione(traccia, listaErroriTrasmissione, true);
			
			if(traccia.getRispedizione() == false)
				traccia.setRispostaXml(risposta);
			
			
		}

		trasmissioneEsito.setIdEgovRichiesta(idEgov);

		this.pccTracciamentoBD.associaATrasmissione(trasmissione, trasmissioneEsito);
		
		if(!lstErrori.isEmpty()) {
			for(PccErroreElaborazione errore: lstErrori) {
				this.pccTracciamentoBD.associaAEsito(trasmissioneEsito, errore);
			}
		}

		if(listaErroreElaborazione != null && listaErroreElaborazione.getErroreElaborazione() != null && !listaErroreElaborazione.getErroreElaborazione().isEmpty()) {
			for(ErroreElaborazioneOperazioneTipo errore: listaErroreElaborazione.getErroreElaborazione()) {
				PccErroreElaborazione pccErroreElaborazione = new PccErroreElaborazione();
				pccErroreElaborazione.setCodiceEsito(errore.getCodiceEsitoElaborazioneOperazione());
				pccErroreElaborazione.setDescrizioneEsito(errore.getDescrizioneEsitoElaborazioneOperazione());
				if(errore.getTipoOperazione() != null)
					pccErroreElaborazione.setTipoOperazione(TipoOperazioneType.toEnumConstant(errore.getTipoOperazione().value()));
				
				if(errore.getProgressivoOperazione() != null)
					pccErroreElaborazione.setProgressivoOperazione(errore.getProgressivoOperazione().intValue());
				this.pccTracciamentoBD.associaAEsito(trasmissioneEsito, pccErroreElaborazione);

			}
			
			populateTracciaConStatoEInfoTracciaRispedizione(traccia, listaErroriTrasmissione, listaErroreElaborazione);
		} else {
			isOkElaborazione = true;
		}

		trasmissione.setDataFineElaborazione(trasmissioneEsito.getGdo());
		trasmissione.setStatoEsito(StatoEsitoTrasmissioneType.PRESENTE);
		this.pccTracciamentoBD.update(trasmissione);

		traccia.setDataUltimaTrasmissione(trasmissione.getTsTrasmissione());
		traccia.setStato((isOkTrasmissione && isOkElaborazione) ? StatoType.AS_OK : StatoType.AS_ERRORE);
		this.pccTracciamentoBD.update(traccia);
		
		return trasmissioneEsito;
	}
	
	private boolean checkForFW0010(String errorCode) {
		return "FW0010".equals(errorCode);
	}
	
	private void checkForFW0010(PccTraccia traccia, ListaErroreTipo listaErroriTrasmissione,
			ListaErroreElaborazioneOperazioneTipo listaErroreElaborazione) throws Exception {

		if(listaErroriTrasmissione != null) {
			for(ErroreTipo errore: listaErroriTrasmissione.getErroreTrasmissione()) {
				if(checkForFW0010(errore.getCodice())) {
					throw new Exception("Errore ["+errore.getCodice()+"] Descrizione ["+errore.getDescrizione()+"]: esito non pronto");
				}
					
			}
		}

		if(listaErroreElaborazione != null) {
			for(ErroreElaborazioneOperazioneTipo errore: listaErroreElaborazione.getErroreElaborazione()) {
				if(checkForFW0010(errore.getCodiceEsitoElaborazioneOperazione())) {
					throw new Exception("Errore ["+errore.getCodiceEsitoElaborazioneOperazione()+"] Descrizione ["+errore.getDescrizioneEsitoElaborazioneOperazione()+"]: esito non pronto");
				}
			}
		}

		return;
	}

	private void populateTracciaConStatoEInfoTracciaRispedizione(PccTraccia traccia, ListaErroreTipo listaErroriTrasmissione, ListaErroreElaborazioneOperazioneTipo listaErroreElaborazione) throws Exception {
		PccRispedizione rispedizione = getRispedizioneByListaErrore(listaErroriTrasmissione, listaErroreElaborazione);
		String idPa = traccia.getIdPaTransazioneRispedizione() != null ? traccia.getIdPaTransazioneRispedizione() : traccia.getIdPaTransazione();

		if(rispedizione != null && rispedizione.isAbilitato()) {
			traccia.setRispedizioneProssimoTentativo(new Date(System.currentTimeMillis()+ (rispedizione.getIntervalloTentativi() * 60000) )); //intervallo tentativi espresso in minuti
			traccia.setRispedizioneMaxTentativi(rispedizione.getMaxNumeroTentativi());
			traccia.setRispedizioneNumeroTentativi(traccia.getRispedizioneNumeroTentativi() + 1);
			traccia.setRispedizioneUltimoTentativo(new Date());
			traccia.setRispedizione(traccia.getRispedizioneNumeroTentativi() < traccia.getRispedizioneMaxTentativi());
			traccia.setRispedizioneDopoQuery(true);
			if(traccia.getRispedizione()) {
				this.log.debug("Traccia idPA["+idPa+"]: trovata rispedizione: maxTentativi ["+traccia.getRispedizioneMaxTentativi()+"] numTentativi ["+traccia.getRispedizioneNumeroTentativi()+"] proxTentativo ["+traccia.getRispedizioneProssimoTentativo()+"]");
			} else {
				this.log.debug("Traccia idPA["+idPa+"]: numero di rispedizioni massimo raggiunto. Inserisco in lista notifica");
				traccia.setStato(StatoType.AS_ERRORE);
				this.creaNotifica(traccia);
			}
		} else {
			traccia.setRispedizione(false);
			traccia.setStato(StatoType.AS_ERRORE);
			this.log.debug("Traccia idPA["+idPa+"]: rispedizione non trovata");
			if(!traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
				this.log.debug("Traccia idPA["+idPa+"]: Inserisco notifica in quanto il sistema richiedente e ["+traccia.getSistemaRichiedente()+"]");
				this.creaNotifica(traccia);
			}
		}
	}
	
	public void creaNotifica(PccTraccia traccia) throws Exception {
		PccNotifica notifica = new PccNotifica();
		notifica.setDataCreazione(new Date());
		
		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(traccia.getCodiceDipartimento());
		notifica.setIdDipartimento(idDipartimento);

		IdTraccia idTraccia = new IdTraccia();
		idTraccia.setIdTraccia(traccia.getId());
		notifica.setIdTraccia(idTraccia);
		
		notifica.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
		this.pccNotificaBD.createNotifica(notifica);
		String idPa = traccia.getIdPaTransazioneRispedizione() != null ? traccia.getIdPaTransazioneRispedizione() : traccia.getIdPaTransazione();
		this.log.debug("Creata notifica per la traccia idPA["+idPa+"]");
		
	}

	private PccRispedizione getRispedizioneByListaErrore(ListaErroreTipo listaErroriTrasmissione, ListaErroreElaborazioneOperazioneTipo listaErroreElaborazione) {
		if(listaErroriTrasmissione != null) {
			try {
				for(ErroreTipo errore: listaErroriTrasmissione.getErroreTrasmissione()) {
					PccRispedizione pccRispedizione = this.pccRispedizioneBD.get(errore.getCodice());
					if(pccRispedizione != null && pccRispedizione.isAbilitato())
						return pccRispedizione;
				}
			}catch(Exception e){}
		}

		if(listaErroreElaborazione != null) {
			try {
				for(ErroreElaborazioneOperazioneTipo errore: listaErroreElaborazione.getErroreElaborazione()) {
					PccRispedizione pccRispedizione = this.pccRispedizioneBD.get(errore.getCodiceEsitoElaborazioneOperazione());
					if(pccRispedizione != null && pccRispedizione.isAbilitato())
						return pccRispedizione;
				}
			}catch(Exception e){}
		}
		
			return null;
	}

	public void insertOrUpdateTraccia(PccTraccia traccia) throws Exception {
		
		if(traccia.getStato() == null) {
			switch(traccia.getTipoOperazione()){
			case PROXY: traccia.setStato(StatoType.AS_ERRORE);
				break;
			case READ: traccia.setStato(StatoType.S_ERRORE);
				break;
			default:
				break;
			
			}
		}

		populateCodiciErrore(traccia);
		
		if(traccia.getDataUltimaTrasmissione() == null)
			traccia.setDataUltimaTrasmissione(traccia.getDataCreazione());
		
		if(traccia.getId() != null && traccia.getId() < 0) {
			this.pccTracciamentoBD.create(traccia);
			if(traccia.sizePccTracciaTrasmissioneList() > 0) {
				for(PccTracciaTrasmissione trasm : traccia.getPccTracciaTrasmissioneList()) {
					this.pccTracciamentoBD.associaATraccia(trasm, traccia);
					
					if(trasm.sizePccTracciaTrasmissioneEsitoList() > 0) {
						for(PccTracciaTrasmissioneEsito esito: trasm.getPccTracciaTrasmissioneEsitoList()) {
							this.pccTracciamentoBD.associaATrasmissione(trasm, esito);
	
							if(esito.sizePccErroreElaborazioneList() > 0) {
								for(PccErroreElaborazione errore: esito.getPccErroreElaborazioneList()) {
									this.pccTracciamentoBD.associaAEsito(esito, errore);
								}
							}	
							
						}
					}	
					
				}
			}
		} else {
			this.aggiornaTraccia(traccia);
		}

	}

	private void populateCodiciErrore(PccTraccia traccia) {
		HashSet<String> codes = new HashSet<String>();
		if(traccia.sizePccTracciaTrasmissioneList() > 0) {
			for(PccTracciaTrasmissione trasm : traccia.getPccTracciaTrasmissioneList()) {
				if(trasm.sizePccTracciaTrasmissioneEsitoList() > 0) {
					for(PccTracciaTrasmissioneEsito esito: trasm.getPccTracciaTrasmissioneEsitoList()) {
						if(esito.sizePccErroreElaborazioneList() > 0) {
							for(PccErroreElaborazione errore: esito.getPccErroreElaborazioneList()) {
								codes.add(errore.getCodiceEsito());
							}
						}	
					}
				}	
			}
		}
		
		String codices = "";
		for(String code: codes) {
			if(codices.length() > 0) {
				codices += ",";
			}
			codices+=code;
		}
		traccia.setCodiciErrore(codices);
	}
	
	private void aggiornaTraccia(PccTraccia traccia) throws Exception {
		this.pccTracciamentoBD.update(traccia);
		if(traccia.sizePccTracciaTrasmissioneList() > 0) {
			for(PccTracciaTrasmissione trasm : traccia.getPccTracciaTrasmissioneList()) {
				if(trasm.getId() == null || trasm.getId() < 0) {
					this.pccTracciamentoBD.associaATraccia(trasm, traccia);
					
					if(trasm.sizePccTracciaTrasmissioneEsitoList() > 0) {
						for(PccTracciaTrasmissioneEsito esito: trasm.getPccTracciaTrasmissioneEsitoList()) {
							this.pccTracciamentoBD.associaATrasmissione(trasm, esito);
	
							if(esito.sizePccErroreElaborazioneList() > 0) {
								for(PccErroreElaborazione errore: esito.getPccErroreElaborazioneList()) {
									this.pccTracciamentoBD.associaAEsito(esito, errore);
								}
							}	
						}
					}	
				}				
			}
		}
	}

	private void populateTracciaConInfoTracciaRispedizione(PccTraccia traccia, ListaErroreTipo listaErroreTipo, boolean rispedizioneDopoQuery) throws Exception {
		PccRispedizione rispedizione = getRispedizioneByListaErrore(listaErroreTipo);

		String idPa = traccia.getIdPaTransazioneRispedizione() != null ? traccia.getIdPaTransazioneRispedizione() : traccia.getIdPaTransazione();

		if(rispedizione != null && rispedizione.isAbilitato()) {
			traccia.setRispedizioneProssimoTentativo(new Date(System.currentTimeMillis()+ (rispedizione.getIntervalloTentativi() * 60000) )); //intervallo tentativi espresso in minuti
			traccia.setRispedizioneMaxTentativi(rispedizione.getMaxNumeroTentativi());
			traccia.setRispedizioneNumeroTentativi(traccia.getRispedizioneNumeroTentativi() + 1);
			traccia.setRispedizioneUltimoTentativo(new Date());
			traccia.setRispedizione(traccia.getRispedizioneNumeroTentativi() < traccia.getRispedizioneMaxTentativi());
			traccia.setRispedizioneDopoQuery(rispedizioneDopoQuery);
			if(traccia.getRispedizione()) {
				this.log.debug("Traccia idPA["+idPa+"]: trovata rispedizione: maxTentativi ["+traccia.getRispedizioneMaxTentativi()+"] numTentativi ["+traccia.getRispedizioneNumeroTentativi()+"] proxTentativo ["+traccia.getRispedizioneProssimoTentativo()+"]");
			} else {
				this.log.debug("Traccia idPA["+idPa+"]: numero di rispedizioni massimo raggiunto. Inserisco in lista notifica");
				traccia.setStato(StatoType.AS_ERRORE);
				this.creaNotifica(traccia);
			}
		} else {
			traccia.setRispedizione(false);
			traccia.setStato(StatoType.AS_ERRORE);
			this.log.debug("Traccia idPA["+idPa+"]: rispedizione non trovata");
			if(!traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
				this.log.debug("Traccia idPA["+idPa+"]: Inserisco notifica in quanto il sistema richiedente e ["+traccia.getSistemaRichiedente()+"]");
				this.creaNotifica(traccia);
			}
		}

	}
	
	private PccRispedizione getRispedizioneByListaErrore(ListaErroreTipo listaErroreTipo) {
		if(listaErroreTipo != null) {
			try {
				for(ErroreTipo errore: listaErroreTipo.getErroreTrasmissione()) {
					PccRispedizione pccRispedizione = this.pccRispedizioneBD.get(errore.getCodice());
					if(pccRispedizione != null && pccRispedizione.isAbilitato())
						return pccRispedizione;
				}
			}catch(Exception e){}
		}
	
		return null;
	}

	private TestataRispTipo getTestataRisp(String idPaTransazione, EsitoOkKoTipo esitoTrasmissione, boolean rispedizione) {
		TestataRispTipo testata = new TestataRispTipo();
		if(esitoTrasmissione.equals(it.tesoro.fatture.EsitoOkKoTipo.OK)) {
			testata.setEsito(org.govmix.pcc.fatture.EsitoOkKoTipo.OK);
			testata.setOrigine(OrigineTipo.PCC);
		} else {
			if(rispedizione) {
				testata.setEsito(org.govmix.pcc.fatture.EsitoOkKoTipo.OK);
				testata.setOrigine(OrigineTipo.PROXY_PCC);
			} else {
				testata.setEsito(org.govmix.pcc.fatture.EsitoOkKoTipo.KO);
				testata.setOrigine(OrigineTipo.PCC);
			}
		}
		testata.setDataElaborazione(new Date());
		return testata;
	}

	public TestataRispTipo popolaTracciaConInfoRispedizioneDefault(PccTraccia traccia, Exception e) throws Exception {
		
		List<Class<?>> lstEccezioniCheNonPrevedonoRispedizione = new ArrayList<Class<?>>();
		lstEccezioniCheNonPrevedonoRispedizione.add(IdentificativoTransazionePADuplicatoException.class);
		lstEccezioniCheNonPrevedonoRispedizione.add(FormatoErratoException.class);
		lstEccezioniCheNonPrevedonoRispedizione.add(OperazioneNonPermessaException.class);
		
		
		
		boolean isRispedizionePerEccezioneAbilitata = !(lstEccezioniCheNonPrevedonoRispedizione.contains(e.getClass())); 
				

		String idPa = traccia.getIdPaTransazioneRispedizione() != null ? traccia.getIdPaTransazioneRispedizione() : traccia.getIdPaTransazione();

		if(isRispedizionePerEccezioneAbilitata) {
			traccia.setRispedizione(true);
			
			if(traccia.getStato() == null)
				traccia.setStato(StatoType.AS_PRESA_IN_CARICO);
			
			traccia.setRispedizioneProssimoTentativo(new Date(System.currentTimeMillis()+ (PccProperties.getInstance().getIntervalloRispedizioneDefault() * 60000) )); //intervallo tentativi espresso in minuti
			traccia.setRispedizioneUltimoTentativo(new Date());
			this.log.debug("Traccia idPA["+idPa+"]: trovata rispedizione di default: proxTentativo ["+traccia.getRispedizioneProssimoTentativo()+"]");
		} else {
			traccia.setRispedizione(false);
			traccia.setStato(StatoType.AS_ERRORE);
			this.log.debug("Traccia idPA["+idPa+"]: rispedizione non trovata");
			if(!traccia.getSistemaRichiedente().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
				this.log.debug("Traccia idPA["+idPa+"]: Inserisco notifica in quanto il sistema richiedente e ["+traccia.getSistemaRichiedente()+"]");
				this.creaNotifica(traccia);
			}
		}

		return getTestataRisp(traccia.getIdPaTransazione(), EsitoOkKoTipo.KO, traccia.isRispedizione());
	}

}
