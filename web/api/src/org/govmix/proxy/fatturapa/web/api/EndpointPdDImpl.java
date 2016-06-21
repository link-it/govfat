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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.api.business.consegnaFattura.ConsegnaFattura;
import org.govmix.proxy.fatturapa.web.api.business.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.api.business.consegnaFattura.ConsegnaFatturaParameters.Soggetto;
import org.govmix.proxy.fatturapa.web.api.business.riceviNotificaDT.RiceviNotifica;
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.protocol.sdi.constants.SDICostanti;

public class EndpointPdDImpl implements EndpointPdD {

	private ConsegnaFattura consegnaFattura;
	private RiceviNotifica riceviNotifica;
	private LottoBD lottoBD;

	private Logger log;

	public EndpointPdDImpl() throws Exception {
		this.log = LoggerManager.getEndpointPdDLogger();
		this.log.info("Inizializzazione endpoint PdD...");
		this.consegnaFattura = new ConsegnaFattura(this.log);
		this.riceviNotifica = new RiceviNotifica(this.log);
		this.lottoBD = new LottoBD(log);
		this.lottoBD.setValidate(WebApiProperties.getInstance().isValidazioneDAOAbilitata());

		this.log.info("Inizializzazione endpoint PdD completata");
	}

	@Override
	public Response postRiceviLotto(String X_SDI_FormatoFatturaPA,
			Integer X_SDI_IdentificativoSDI,
			String X_SDI_NomeFile, 
			String X_SDI_FormatoArchivioInvioFattura,
			String X_SDI_MessageId,
			String X_SDI_CodiceDestinatario,
			String X_SDI_CedentePrestatore_Denominazione,
			String X_SDI_CedentePrestatore_Nome,
			String X_SDI_CedentePrestatore_Cognome,
			String X_SDI_CedentePrestatore_CodiceFiscale,
			String X_SDI_CedentePrestatore_IdCodice,
			String X_SDI_CedentePrestatore_IdPaese,
			String X_SDI_CessionarioCommittente_Denominazione,
			String X_SDI_CessionarioCommittente_Nome,
			String X_SDI_CessionarioCommittente_Cognome,
			String X_SDI_CessionarioCommittente_CodiceFiscale,
			String X_SDI_CessionarioCommittente_IdCodice,
			String X_SDI_CessionarioCommittente_IdPaese,
			String X_SDI_TerzoIntermediarioOSoggettoEmittente_Denominazione,
			String X_SDI_TerzoIntermediarioOSoggettoEmittente_Nome,
			String X_SDI_TerzoIntermediarioOSoggettoEmittente_Cognome,
			String X_SDI_TerzoIntermediarioOSoggettoEmittente_CodiceFiscale,
			String X_SDI_TerzoIntermediarioOSoggettoEmittente_IdCodice,
			String X_SDI_TerzoIntermediarioOSoggettoEmittente_IdPaese,
			InputStream fatturaStream) {

		this.log.info("Invoke riceviLotto");
		try {

			LottoFatture lotto = new LottoFatture();

			FormatoArchivioInvioFatturaType formatoArchivioInvioFattura = null;
			if(SDICostanti.SDI_TIPO_FATTURA_XML.equals(X_SDI_FormatoArchivioInvioFattura)) {
				formatoArchivioInvioFattura = FormatoArchivioInvioFatturaType.XML;
			} else if(SDICostanti.SDI_TIPO_FATTURA_P7M.equals(X_SDI_FormatoArchivioInvioFattura)) {
				formatoArchivioInvioFattura = FormatoArchivioInvioFatturaType.P7M;
			} else {
				throw new Exception("Parametro [X-SDI-FormatoArchivioInvioFattura] non valido:" + X_SDI_FormatoArchivioInvioFattura);
			}

			lotto.setFormatoArchivioInvioFattura(formatoArchivioInvioFattura);
			lotto.setCedentePrestatoreCodice(X_SDI_CedentePrestatore_IdCodice);
			lotto.setCedentePrestatorePaese(X_SDI_CedentePrestatore_IdPaese);
			lotto.setCedentePrestatoreCodiceFiscale(X_SDI_CedentePrestatore_CodiceFiscale);
			lotto.setCedentePrestatoreCognome(X_SDI_CedentePrestatore_Cognome);
			lotto.setCedentePrestatoreNome(X_SDI_CedentePrestatore_Nome);
			lotto.setCedentePrestatoreDenominazione(X_SDI_CedentePrestatore_Denominazione);

			lotto.setCessionarioCommittenteCodice(X_SDI_CessionarioCommittente_IdCodice);
			lotto.setCessionarioCommittentePaese(X_SDI_CessionarioCommittente_IdPaese);
			lotto.setCessionarioCommittenteCodiceFiscale(X_SDI_CessionarioCommittente_CodiceFiscale);
			lotto.setCessionarioCommittenteCognome(X_SDI_CessionarioCommittente_Cognome);
			lotto.setCessionarioCommittenteNome(X_SDI_CessionarioCommittente_Nome);
			lotto.setCessionarioCommittenteDenominazione(X_SDI_CessionarioCommittente_Denominazione);

			lotto.setTerzoIntermediarioOSoggettoEmittenteCodice(X_SDI_TerzoIntermediarioOSoggettoEmittente_IdCodice);
			lotto.setTerzoIntermediarioOSoggettoEmittentePaese(X_SDI_TerzoIntermediarioOSoggettoEmittente_IdPaese);
			lotto.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(X_SDI_TerzoIntermediarioOSoggettoEmittente_CodiceFiscale);
			lotto.setTerzoIntermediarioOSoggettoEmittenteCognome(X_SDI_TerzoIntermediarioOSoggettoEmittente_Cognome);
			lotto.setTerzoIntermediarioOSoggettoEmittenteNome(X_SDI_TerzoIntermediarioOSoggettoEmittente_Nome);
			lotto.setTerzoIntermediarioOSoggettoEmittenteDenominazione(X_SDI_TerzoIntermediarioOSoggettoEmittente_Denominazione);

			lotto.setIdentificativoSdi(X_SDI_IdentificativoSDI);

			lotto.setCodiceDestinatario(X_SDI_CodiceDestinatario);
			lotto.setFormatoTrasmissione(FormatoTrasmissioneType.valueOf(X_SDI_FormatoFatturaPA));

			lotto.setNomeFile(X_SDI_NomeFile);
			lotto.setMessageId(X_SDI_MessageId);

			byte[] xml = streamToBytes(fatturaStream);
			this.log.info("Lotto XML [Inserimento lotto]: " + (new String(xml)));
			lotto.setXml(xml);

			lotto.setDataRicezione(new Date());
			lotto.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
			lotto.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);

			this.lottoBD.create(lotto);
		} catch(Exception e) {
			this.log.error("riceviLotto completata con errore:"+ e.getMessage(), e);
			return Response.status(500).build();
		}

		this.log.info("riceviLotto completata con successo");
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

			try {
				params.validate();
			} catch(Exception e) {
				throw new Exception("Parametri ["+params.toString()+"] ricevuti in ingresso non validi:"+e.getMessage());
			}

			if(fattura == null) {
				throw new Exception("La fattura ricevuta in ingresso e' null");
			}



			this.consegnaFattura.consegnaFattura(params, streamToString(fattura));
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

			this.riceviNotifica.ricevi(streamToString(notifica));
		} catch(Exception e) {
			this.log.error("riceviNotificaDT completata con errore:"+ e.getMessage(), e);
			return Response.status(500).build();
		}

		this.log.info("riceviNotificaDT completata con successo");
		return Response.ok().build();
	}

	private static byte[] streamToBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = null;

		try {
			baos = new ByteArrayOutputStream();
			byte[] buff = new byte[2048];
			int len = is.read(buff);
			while(len > 0) {
				baos.write(buff, 0, len);
				len = is.read(buff);
			}

			return baos.toByteArray();
		} finally {
			if(is != null)
				try {is.close(); } catch(Exception e) {} //IOUtils non chiude sempre (TODO usare commons IO di apache) 
		}
	}

	private static String streamToString(InputStream is) throws IOException {
		try {
			return IOUtils.toString(is);
		} finally {
			if(is != null)
				try {is.close(); } catch(Exception e) {} //IOUtils non chiude sempre (TODO usare commons IO di apache) 
		}
	}
}