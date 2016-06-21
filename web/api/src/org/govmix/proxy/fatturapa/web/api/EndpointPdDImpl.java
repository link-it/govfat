/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.api;

import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFattura;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters.Soggetto;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaUtils;
import org.govmix.proxy.fatturapa.web.commons.riceviNotificaDT.RiceviNotifica;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class EndpointPdDImpl implements EndpointPdD {

	private ConsegnaFattura consegnaFattura;
	private RiceviNotifica riceviNotifica;
	private LottoBD lottoBD;

	private Logger log;

	public EndpointPdDImpl() throws Exception {
		this.log = LoggerManager.getEndpointPdDLogger();
		this.log.info("Inizializzazione endpoint PdD...");
		this.consegnaFattura = new ConsegnaFattura(this.log, WebApiProperties.getInstance().isValidazioneDAOAbilitata());
		this.riceviNotifica = new RiceviNotifica(this.log);
		this.lottoBD = new LottoBD(log);
		this.lottoBD.setValidate(WebApiProperties.getInstance().isValidazioneDAOAbilitata());

		this.log.info("Inizializzazione endpoint PdD completata");
	}

	@Override
	public Response postRiceviLotto(String formatoFatturaPA,
			Integer identificativoSDI,
			String nomeFile, 
			String formatoArchivioInvioFatturaString,
			String formatoArchivioBase64,
			String messageId,
			String codiceDestinatario,
			String cedentePrestatoreDenominazione,
			String cedentePrestatoreNome,
			String cedentePrestatoreCognome,
			String cedentePrestatoreCodiceFiscale,
			String cedentePrestatoreIdCodice,
			String cedentePrestatoreIdPaese,
			String cessionarioCommittenteDenominazione,
			String cessionarioCommittenteNome,
			String cessionarioCommittenteCognome,
			String cessionarioCommittenteCodiceFiscale,
			String cessionarioCommittenteIdCodice,
			String cessionarioCommittenteIdPaese,
			String terzoIntermediarioOSoggettoEmittenteDenominazione,
			String terzoIntermediarioOSoggettoEmittenteNome,
			String terzoIntermediarioOSoggettoEmittenteCognome,
			String terzoIntermediarioOSoggettoEmittenteCodiceFiscale,
			String terzoIntermediarioOSoggettoEmittenteIdCodice,
			String terzoIntermediarioOSoggettoEmittenteIdPaese,
			InputStream fatturaStream) {

		this.log.info("Invoke riceviLotto");

		if(identificativoSDI == null) {
			this.log.error("Impossibile inserire il lotto, identificativo SdI nullo.");
			return Response.status(500).build();
		}

		try {
			IdLotto idLotto = new IdLotto();
			idLotto.setIdentificativoSdi(identificativoSDI);
			if(this.lottoBD.exists(idLotto)) {
				this.log.warn("Lotto con identificativo SdI ["+identificativoSDI+"] esiste gia', inserimento non avvenuto");
			} else {
				

				ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(formatoFatturaPA,
						identificativoSDI, nomeFile,
						formatoArchivioInvioFatturaString, formatoArchivioBase64,
						messageId,
						fatturaStream);


				try {
					params.validate(true);
				} catch(Exception e) {
					throw new Exception("Parametri ["+params.toString()+"] ricevuti in ingresso non validi:"+e.getMessage());
				}

				LottoFatture lotto = new LottoFatture();

				lotto.setFormatoArchivioInvioFattura(params.getFormatoArchivioInvioFattura());
				lotto.setCedentePrestatoreCodice(params.getCedentePrestatore().getIdCodice());
				lotto.setCedentePrestatorePaese(params.getCedentePrestatore().getIdPaese());
				lotto.setCedentePrestatoreCodiceFiscale(params.getCedentePrestatore().getCodiceFiscale());
				lotto.setCedentePrestatoreCognome(params.getCedentePrestatore().getCognome());
				lotto.setCedentePrestatoreNome(params.getCedentePrestatore().getNome());
				lotto.setCedentePrestatoreDenominazione(params.getCedentePrestatore().getDenominazione());

				lotto.setCessionarioCommittenteCodice(params.getCessionarioCommittente().getIdCodice());
				lotto.setCessionarioCommittentePaese(params.getCessionarioCommittente().getIdPaese());
				lotto.setCessionarioCommittenteCodiceFiscale(params.getCessionarioCommittente().getCodiceFiscale());
				lotto.setCessionarioCommittenteCognome(params.getCessionarioCommittente().getCognome());
				lotto.setCessionarioCommittenteNome(params.getCessionarioCommittente().getNome());
				lotto.setCessionarioCommittenteDenominazione(params.getCessionarioCommittente().getDenominazione());

				if(params.getTerzoIntermediarioOSoggettoEmittente() != null) {
					lotto.setTerzoIntermediarioOSoggettoEmittenteCodice(params.getTerzoIntermediarioOSoggettoEmittente().getIdCodice());
					lotto.setTerzoIntermediarioOSoggettoEmittentePaese(params.getTerzoIntermediarioOSoggettoEmittente().getIdPaese());
					lotto.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(params.getTerzoIntermediarioOSoggettoEmittente().getCodiceFiscale());
					lotto.setTerzoIntermediarioOSoggettoEmittenteCognome(params.getTerzoIntermediarioOSoggettoEmittente().getCognome());
					lotto.setTerzoIntermediarioOSoggettoEmittenteNome(params.getTerzoIntermediarioOSoggettoEmittente().getNome());
					lotto.setTerzoIntermediarioOSoggettoEmittenteDenominazione(params.getTerzoIntermediarioOSoggettoEmittente().getDenominazione());
				}

				lotto.setIdentificativoSdi(params.getIdentificativoSdI());

				lotto.setCodiceDestinatario(params.getCodiceDestinatario());
				lotto.setFormatoTrasmissione(FormatoTrasmissioneType.valueOf(params.getFormatoFatturaPA()));

				lotto.setNomeFile(params.getNomeFile());
				lotto.setMessageId(params.getMessageId());

				lotto.setXml(params.getXml());

				lotto.setDataRicezione(new Date());
				lotto.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
				lotto.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
				lotto.setStatoInserimento(StatoInserimentoType.NON_INSERITO);

				this.log.info("Inserimento del Lotto con identificativo SdI ["+lotto.getIdentificativoSdi()+"]...");
				this.lottoBD.create(lotto);	
				this.log.info("Inserimento del Lotto con identificativo SdI ["+lotto.getIdentificativoSdi()+"] completato");
			}
			
		} catch(Exception e) {
			this.log.error("riceviLotto completata con errore per il lotto con identificativo SdI ["+identificativoSDI+"]:"+ e.getMessage(), e);
			return Response.status(500).build();
		}

		this.log.info("riceviLotto completata con successo per il lotto con identificativo SdI ["+identificativoSDI+"]");
		return Response.ok().build();
	}

	@Override
	public Response postConsegnaFattura(String formatoFatturaPA, 
			Integer posizioneFatturaPA,
			Integer identificativoSdI, 
			String nomeFile, 
			String messageId,
			String codiceDestinatario,

			String cedentePrestatoreDenominazione, 
			String cedentePrestatoreNome,
			String cedentePrestatoreCognome,
			String cedentePrestatoreCodiceFiscale,
			String cedentePrestatoreIdCodice,
			String cedentePrestatoreIdPaese,

			String cessionarioCommittenteDenominazione, 
			String cessionarioCommittenteNome,
			String cessionarioCommittenteCognome,
			String cessionarioCommittenteCodiceFiscale,
			String cessionarioCommittenteIdCodice,
			String cessionarioCommittenteIdPaese,

			String terzoIntermediarioOSoggettoEmittenteDenominazione, 
			String terzoIntermediarioOSoggettoEmittenteNome,
			String terzoIntermediarioOSoggettoEmittenteCognome,
			String terzoIntermediarioOSoggettoEmittenteCodiceFiscale,
			String terzoIntermediarioOSoggettoEmittenteIdCodice,
			String terzoIntermediarioOSoggettoEmittenteIdPaese,

			InputStream fattura) {

		this.log.info("Invoke riceviFattura");
		ConsegnaFatturaParameters params = new ConsegnaFatturaParameters();

		params.setFormatoFatturaPA(formatoFatturaPA);
		params.setPosizioneFatturaPA(posizioneFatturaPA);

		params.setIdentificativoSdI(identificativoSdI);
		params.setNomeFile(nomeFile);
		params.setMessageId(messageId);
		params.setCodiceDestinatario(codiceDestinatario);

		Soggetto cedentePrestatore = params.new Soggetto();

		cedentePrestatore.setDenominazione(cedentePrestatoreDenominazione);
		cedentePrestatore.setNome(cedentePrestatoreNome);
		cedentePrestatore.setCognome(cedentePrestatoreCognome);
		cedentePrestatore.setIdCodice(cedentePrestatoreIdCodice);
		cedentePrestatore.setCodiceFiscale(cedentePrestatoreCodiceFiscale);
		cedentePrestatore.setIdPaese(cedentePrestatoreIdPaese);

		params.setCedentePrestatore(cedentePrestatore);


		Soggetto cessionarioCommittente = params.new Soggetto();
		cessionarioCommittente.setDenominazione(cessionarioCommittenteDenominazione);
		cessionarioCommittente.setNome(cessionarioCommittenteNome);
		cessionarioCommittente.setCognome(cessionarioCommittenteCognome);
		cessionarioCommittente.setIdCodice(cessionarioCommittenteIdCodice);
		cessionarioCommittente.setCodiceFiscale(cessionarioCommittenteCodiceFiscale);
		cessionarioCommittente.setIdPaese(cessionarioCommittenteIdPaese);

		params.setCessionarioCommittente(cessionarioCommittente);

		if(terzoIntermediarioOSoggettoEmittenteIdCodice != null || terzoIntermediarioOSoggettoEmittenteIdPaese != null
				|| terzoIntermediarioOSoggettoEmittenteNome != null ||
				terzoIntermediarioOSoggettoEmittenteCognome != null ||
				terzoIntermediarioOSoggettoEmittenteDenominazione != null || 
				terzoIntermediarioOSoggettoEmittenteCodiceFiscale != null) {
			Soggetto terzoIntermediarioOSoggettoEmittente = params.new Soggetto();
			terzoIntermediarioOSoggettoEmittente.setDenominazione(terzoIntermediarioOSoggettoEmittenteDenominazione);
			terzoIntermediarioOSoggettoEmittente.setNome(terzoIntermediarioOSoggettoEmittenteNome);
			terzoIntermediarioOSoggettoEmittente.setCognome(terzoIntermediarioOSoggettoEmittenteCognome);
			terzoIntermediarioOSoggettoEmittente.setIdCodice(terzoIntermediarioOSoggettoEmittenteIdCodice);
			terzoIntermediarioOSoggettoEmittente.setCodiceFiscale(terzoIntermediarioOSoggettoEmittenteCodiceFiscale);
			terzoIntermediarioOSoggettoEmittente.setIdPaese(terzoIntermediarioOSoggettoEmittenteIdPaese);

			params.setTerzoIntermediarioOSoggettoEmittente(terzoIntermediarioOSoggettoEmittente);
		}


		try {
			params.setXml(IOUtils.readBytesFromStream(fattura));
			try {
				params.validate();
			} catch(Exception e) {
				throw new Exception("Parametri ["+params.toString()+"] ricevuti in ingresso non validi:"+e.getMessage());
			}

			if(fattura == null) {
				throw new Exception("La fattura ricevuta in ingresso e' null");
			}



			this.consegnaFattura.consegnaFattura(params);
		} catch(Exception e) {
			this.log.error("riceviFattura completata con errore:"+ e.getMessage(), e);
			return Response.status(500).build();
		}

		this.log.info("riceviFattura completata con successo");
		return Response.ok().build();
	}

	@Override
	public Response postConsegnaNotificaDT(InputStream notifica) {
		this.log.info("Invoke riceviNotificaDT");

		try {
			if(notifica == null) {
				throw new Exception("La notifica ricevuta in ingresso e' null");
			}

			this.riceviNotifica.ricevi(IOUtils.readBytesFromStream(notifica));
		} catch(Exception e) {
			this.log.error("riceviNotificaDT completata con errore:"+ e.getMessage(), e);
			return Response.status(500).build();
		}

		this.log.info("riceviNotificaDT completata con successo");
		return Response.ok().build();
	}
}