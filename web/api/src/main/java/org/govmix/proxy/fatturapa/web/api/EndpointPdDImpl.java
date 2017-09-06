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
package org.govmix.proxy.fatturapa.web.api;

import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaUtils;
import org.govmix.proxy.fatturapa.web.commons.riceviNotificaDT.RiceviNotifica;
import org.govmix.proxy.fatturapa.web.commons.ricevicomunicazionesdi.RiceviComunicazioneSdI;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class EndpointPdDImpl implements EndpointPdD {

	private static final String ID_EGOV = "X-SDI-DirectVM-IDMessaggioRichiesta";
	private RiceviNotifica riceviNotifica;
	private LottoBD lottoBD;
	private static final String HEADER_IDENTIFICATIVO_SDI = "X-SDI-IdentificativoSdI";
	private RiceviComunicazioneSdI riceviComunicazioneSdi;

	
	private Logger log;

	public EndpointPdDImpl() throws Exception {
		this.log = LoggerManager.getEndpointPdDLogger();
		this.log.info("Inizializzazione endpoint PdD...");
		this.riceviNotifica = new RiceviNotifica(this.log);
		this.lottoBD = new LottoBD(log);
		this.lottoBD.setValidate(WebApiProperties.getInstance().isValidazioneDAOAbilitata());
		this.log.info("Inizializzazione endpoint PdD completata");
	}

	@Override
	public Response postRiceviLotto(String formatoFatturaPA,
			String identificativoSDIString,
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
			HttpHeaders headers,
			InputStream fatturaStream) {

		this.log.info("Invoke riceviLotto");
		
		
		if(!headers.getRequestHeaders().keySet().isEmpty()) {
			this.log.debug("Headers: ");
			for(String header : headers.getRequestHeaders().keySet()){
				this.log.debug(header + ": " + headers.getRequestHeaders().getFirst(header));
			}
		}

		if(identificativoSDIString == null) {
			this.log.error("Impossibile inserire il lotto, identificativo SdI nullo.");
			return Response.status(500).build();
		}

		Integer identificativoSDI = null;
		try{
			identificativoSDI = Integer.parseInt(identificativoSDIString);
		} catch(NumberFormatException e) {
			this.log.error("Impossibile inserire il lotto, formato identificativo SdI ["+identificativoSDIString+"] errato:"+e.getMessage());
			return Response.status(500).build();
		}

		try {
			IdLotto idLotto = new IdLotto();
			idLotto.setIdentificativoSdi(identificativoSDI);
			if(this.lottoBD.exists(idLotto)) {
				this.log.warn("Lotto con identificativo SdI ["+identificativoSDI+"] esiste gia', inserimento non avvenuto");
			} else {
				
				String idEgov = null;
				if(!headers.getRequestHeaders().keySet().isEmpty()) {
					this.log.debug("Headers: ");
					for(String header : headers.getRequestHeaders().keySet()){
						this.log.debug(header + ": " + headers.getRequestHeaders().getFirst(header));
						if(header.equalsIgnoreCase(ID_EGOV)) {
							idEgov = headers.getRequestHeaders().getFirst(header);
						}
					}
				}

				ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(formatoFatturaPA,
						identificativoSDI, nomeFile,
						formatoArchivioInvioFatturaString, formatoArchivioBase64,
						messageId,
						false,
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

				lotto.setIdEgov(idEgov);
				lotto.setNomeFile(params.getNomeFile());
				lotto.setMessageId(params.getMessageId());

				lotto.setXml(params.getXml());
				lotto.setFatturazioneAttiva(false);
				
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
	
	@Override
	public Response riceviComunicazioniSdI(String tipo, Integer X_SDI_IdentificativoSDI, Integer X_SDI_IdentificativoSDIFattura, String X_SDI_NomeFile, String contentType, InputStream comunicazioneStream) {
		this.log.info("Invoke riceviComunicazioniSdi");

		try {
			if(comunicazioneStream == null) {
				throw new Exception("La comunicazione ricevuta in ingresso e' null");
			}
			
			TipoComunicazioneType tipoComunicazione = RiceviComunicazioneSdI.getTipoComunicazione(tipo);

			TracciaSDI tracciaSdi = new TracciaSDI();
			
			tracciaSdi.setIdentificativoSdi(X_SDI_IdentificativoSDIFattura);
			tracciaSdi.setTipoComunicazione(tipoComunicazione);
			tracciaSdi.setData(new Date());
			tracciaSdi.setContentType(contentType);
			tracciaSdi.setNomeFile(X_SDI_NomeFile);
			tracciaSdi.setRawData(IOUtils.readBytesFromStream(comunicazioneStream));
			tracciaSdi.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
			//TODO lista di metadati da salvare
			Metadato metadato = new Metadato();
			metadato.setNome(HEADER_IDENTIFICATIVO_SDI);
			metadato.setValore(""+X_SDI_IdentificativoSDI);
			tracciaSdi.addMetadato(metadato);

			this.riceviComunicazioneSdi.ricevi(tracciaSdi);
		} catch(Exception e) {
			this.log.error("riceviComunicazioniSdi completata con errore:"+ e.getMessage(), e);
			return Response.status(500).build();
		}

		this.log.info("riceviComunicazioniSdi completata con successo");
		return Response.ok().build();
	}

}