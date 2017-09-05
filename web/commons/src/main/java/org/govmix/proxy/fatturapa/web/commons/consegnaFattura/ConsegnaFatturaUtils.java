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
package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import it.gov.fatturapa.sdi.fatturapa.v1_0.AnagraficaType;
import it.gov.fatturapa.sdi.fatturapa.v1_0.DatiAnagraficiCedenteType;
import it.gov.fatturapa.sdi.fatturapa.v1_0.DatiAnagraficiCessionarioType;
import it.gov.fatturapa.sdi.fatturapa.v1_0.DatiAnagraficiTerzoIntermediarioType;
import it.gov.fatturapa.sdi.fatturapa.v1_0.IdFiscaleType;
import it.gov.fatturapa.sdi.fatturapa.v1_0.utils.serializer.JaxbDeserializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters.Soggetto;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.protocol.sdi.constants.SDICostanti;
import org.openspcoop2.protocol.sdi.utils.P7MInfo;
import org.openspcoop2.protocol.sdi.utils.SDILottoUtils;

public class ConsegnaFatturaUtils {


	public static ConsegnaFatturaParameters getParameters(String formatoFatturaPA,
			Integer identificativoSDI, String nomeFile,
			String formatoArchivioInvioFatturaString,
			String formatoArchivioBase64, String messageId,
			String codiceDestinatario, String cedentePrestatoreDenominazione,
			String cedentePrestatoreNome, String cedentePrestatoreCognome,
			String cedentePrestatoreCodiceFiscale,
			String cedentePrestatoreIdCodice, String cedentePrestatoreIdPaese,
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
			boolean fatturazioneAttiva,
			InputStream fatturaStream) throws Exception, IOException {
		
		boolean isBase64 = false;
		if(formatoArchivioBase64 != null) {
			try {
				isBase64= Boolean.parseBoolean(formatoArchivioBase64);
			} catch(Exception e){
				throw new Exception("Parametro [X-SDI-FormatoArchivioBase64] non valido:" + formatoArchivioBase64);
			}
		}

		return getParameters(formatoFatturaPA, identificativoSDI, nomeFile, formatoArchivioInvioFatturaString, formatoArchivioBase64, 
				messageId, codiceDestinatario, 
				cedentePrestatoreDenominazione, cedentePrestatoreNome, cedentePrestatoreCognome, cedentePrestatoreCodiceFiscale, cedentePrestatoreIdCodice, cedentePrestatoreIdPaese, 
				cessionarioCommittenteDenominazione, cessionarioCommittenteNome, cessionarioCommittenteCognome, cessionarioCommittenteCodiceFiscale, cessionarioCommittenteIdCodice, cessionarioCommittenteIdPaese, 
				terzoIntermediarioOSoggettoEmittenteDenominazione, terzoIntermediarioOSoggettoEmittenteNome, terzoIntermediarioOSoggettoEmittenteCognome, terzoIntermediarioOSoggettoEmittenteCodiceFiscale, 
				terzoIntermediarioOSoggettoEmittenteIdCodice, terzoIntermediarioOSoggettoEmittenteIdPaese, fatturazioneAttiva, getXmlBytes(fatturaStream, isBase64));
	}
	

	public static ConsegnaFatturaParameters getParameters(String formatoFatturaPA,
			Integer identificativoSDI, String nomeFile,
			String formatoArchivioInvioFatturaString,
			String formatoArchivioBase64, String messageId,
			String codiceDestinatario, String cedentePrestatoreDenominazione,
			String cedentePrestatoreNome, String cedentePrestatoreCognome,
			String cedentePrestatoreCodiceFiscale,
			String cedentePrestatoreIdCodice, String cedentePrestatoreIdPaese,
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
			boolean fatturazioneAttiva,
			byte[] xml) throws Exception, IOException {
		ConsegnaFatturaParameters params = new ConsegnaFatturaParameters();

		params.setXml(xml);
		
		FormatoArchivioInvioFatturaType formatoArchivioInvioFattura = null;
		if(SDICostanti.SDI_TIPO_FATTURA_XML.equals(formatoArchivioInvioFatturaString)) {
			formatoArchivioInvioFattura = FormatoArchivioInvioFatturaType.XML;
		} else if(SDICostanti.SDI_TIPO_FATTURA_P7M.equals(formatoArchivioInvioFatturaString)) {
			formatoArchivioInvioFattura = FormatoArchivioInvioFatturaType.P7M;
		} else {
			throw new Exception("Parametro [X-SDI-FormatoArchivioInvioFattura] non valido:" + formatoArchivioInvioFatturaString);
		}

		params.setFormatoFatturaPA(formatoFatturaPA);
		params.setFormatoArchivioInvioFattura(formatoArchivioInvioFattura);

		params.setIdentificativoSdI(identificativoSDI);
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
		params.setFatturazioneAttiva(fatturazioneAttiva);

		return params;
	}

	public static ConsegnaFatturaParameters getParameters(String formatoFatturaPA,
			Integer identificativoSDI, String nomeFile,
			String formatoArchivioInvioFatturaString,
			String formatoArchivioBase64, String messageId,
			boolean fatturazioneAttiva,
			byte[] xml) throws Exception, IOException {
		
		byte[] xmlDecoded = getLottoXml(FormatoArchivioInvioFatturaType.toEnumConstant(formatoArchivioInvioFatturaString), xml, identificativoSDI, LoggerManager.getEndpointPdDLogger());
		
		ConsegnaFatturaParameters params;
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.equals(formatoFatturaPA)) {
			JaxbDeserializer deserializer10 = new JaxbDeserializer();
			it.gov.fatturapa.sdi.fatturapa.v1_0.FatturaElettronicaType fattura = deserializer10.readFatturaElettronicaType(xmlDecoded);

			DatiAnagraficiCedenteType datiAnagraficiCP = fattura.getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici();
			AnagraficaType anagraficaCP = datiAnagraficiCP.getAnagrafica();
			IdFiscaleType idFiscaleCP = datiAnagraficiCP.getIdFiscaleIVA();
			
			DatiAnagraficiCessionarioType datiAnagraficiCC = fattura.getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici();
			AnagraficaType anagraficaCC = datiAnagraficiCC.getAnagrafica();
			IdFiscaleType idFiscaleCC = datiAnagraficiCC.getIdFiscaleIVA() != null ? datiAnagraficiCC.getIdFiscaleIVA() : new IdFiscaleType();
			
			DatiAnagraficiTerzoIntermediarioType datiAnagraficiTI;
			AnagraficaType anagraficaTI;
			IdFiscaleType idFiscaleTI;
			
			if(fattura.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente() != null) {
				datiAnagraficiTI = fattura.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente().getDatiAnagrafici();
				anagraficaTI = datiAnagraficiTI.getAnagrafica();
				
				if(datiAnagraficiTI.getIdFiscaleIVA() != null)
					idFiscaleTI = datiAnagraficiTI.getIdFiscaleIVA();
				else 
					idFiscaleTI = new IdFiscaleType();
				
			} else {
				datiAnagraficiTI = new DatiAnagraficiTerzoIntermediarioType();
				anagraficaTI = new AnagraficaType();
				idFiscaleTI = new IdFiscaleType();
			}
			
			params = getParameters(formatoFatturaPA, identificativoSDI, nomeFile, formatoArchivioInvioFatturaString, formatoArchivioBase64, messageId, fattura.getFatturaElettronicaHeader().getDatiTrasmissione().getCodiceDestinatario(), 
					 anagraficaCP.getDenominazione(), anagraficaCP.getNome(), anagraficaCP.getCognome(),
					 datiAnagraficiCP.getCodiceFiscale(), idFiscaleCP.getIdCodice(), idFiscaleCP.getIdPaese(),
					 anagraficaCC.getDenominazione(), anagraficaCC.getNome(), anagraficaCC.getCognome(), 
					 datiAnagraficiCC.getCodiceFiscale(), idFiscaleCC.getIdCodice(), idFiscaleCC.getIdPaese(),
					 anagraficaTI.getDenominazione(), anagraficaTI.getNome(), anagraficaTI.getCognome(),
					 datiAnagraficiTI.getCodiceFiscale(), idFiscaleTI.getIdCodice(), idFiscaleTI.getIdPaese(), fatturazioneAttiva, xml);
		} else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.equals(formatoFatturaPA)) {
			it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbDeserializer deserializer11 = new it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbDeserializer();
			it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaType fattura = deserializer11.readFatturaElettronicaType(xmlDecoded);

			it.gov.fatturapa.sdi.fatturapa.v1_1.DatiAnagraficiCedenteType datiAnagraficiCP = fattura.getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici();
			it.gov.fatturapa.sdi.fatturapa.v1_1.AnagraficaType anagraficaCP = datiAnagraficiCP.getAnagrafica();
			it.gov.fatturapa.sdi.fatturapa.v1_1.IdFiscaleType idFiscaleCP = datiAnagraficiCP.getIdFiscaleIVA();
			
			it.gov.fatturapa.sdi.fatturapa.v1_1.DatiAnagraficiCessionarioType datiAnagraficiCC = fattura.getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici();
			it.gov.fatturapa.sdi.fatturapa.v1_1.AnagraficaType anagraficaCC = datiAnagraficiCC.getAnagrafica();
			it.gov.fatturapa.sdi.fatturapa.v1_1.IdFiscaleType idFiscaleCC = datiAnagraficiCC.getIdFiscaleIVA() != null ? datiAnagraficiCC.getIdFiscaleIVA() : new it.gov.fatturapa.sdi.fatturapa.v1_1.IdFiscaleType();
			
			it.gov.fatturapa.sdi.fatturapa.v1_1.DatiAnagraficiTerzoIntermediarioType datiAnagraficiTI;
			it.gov.fatturapa.sdi.fatturapa.v1_1.AnagraficaType anagraficaTI;
			it.gov.fatturapa.sdi.fatturapa.v1_1.IdFiscaleType idFiscaleTI;
			
			
			if(fattura.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente() != null) {
				datiAnagraficiTI = fattura.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente().getDatiAnagrafici();
				anagraficaTI = datiAnagraficiTI.getAnagrafica();
				
				if(datiAnagraficiTI.getIdFiscaleIVA() != null)
					idFiscaleTI = datiAnagraficiTI.getIdFiscaleIVA();
				else 
					idFiscaleTI = new it.gov.fatturapa.sdi.fatturapa.v1_1.IdFiscaleType();
				
			} else {
				datiAnagraficiTI = new it.gov.fatturapa.sdi.fatturapa.v1_1.DatiAnagraficiTerzoIntermediarioType();
				anagraficaTI = new it.gov.fatturapa.sdi.fatturapa.v1_1.AnagraficaType();
				idFiscaleTI = new it.gov.fatturapa.sdi.fatturapa.v1_1.IdFiscaleType();
			}
			
			params = getParameters(formatoFatturaPA, identificativoSDI, nomeFile, formatoArchivioInvioFatturaString, formatoArchivioBase64, messageId, fattura.getFatturaElettronicaHeader().getDatiTrasmissione().getCodiceDestinatario(), 
					 anagraficaCP.getDenominazione(), anagraficaCP.getNome(), anagraficaCP.getCognome(),
					 datiAnagraficiCP.getCodiceFiscale(), idFiscaleCP.getIdCodice(), idFiscaleCP.getIdPaese(),
					 anagraficaCC.getDenominazione(), anagraficaCC.getNome(), anagraficaCC.getCognome(), 
					 datiAnagraficiCC.getCodiceFiscale(), idFiscaleCC.getIdCodice(), idFiscaleCC.getIdPaese(),
					 anagraficaTI.getDenominazione(), anagraficaTI.getNome(), anagraficaTI.getCognome(),
					 datiAnagraficiTI.getCodiceFiscale(), idFiscaleTI.getIdCodice(), idFiscaleTI.getIdPaese(), fatturazioneAttiva, xml);
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.equals(formatoFatturaPA) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.equals(formatoFatturaPA)) {
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.utils.serializer.JaxbDeserializer deserializer12 = new it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.utils.serializer.JaxbDeserializer();
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.FatturaElettronicaType fattura = deserializer12.readFatturaElettronicaType(xmlDecoded);

			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.DatiAnagraficiCedenteType datiAnagraficiCP = fattura.getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici();
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.AnagraficaType anagraficaCP = datiAnagraficiCP.getAnagrafica();
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.IdFiscaleType idFiscaleCP = datiAnagraficiCP.getIdFiscaleIVA();
			
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.DatiAnagraficiCessionarioType datiAnagraficiCC = fattura.getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici();
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.AnagraficaType anagraficaCC = datiAnagraficiCC.getAnagrafica();
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.IdFiscaleType idFiscaleCC = datiAnagraficiCC.getIdFiscaleIVA() != null ? datiAnagraficiCC.getIdFiscaleIVA() : new it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.IdFiscaleType();
			
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.DatiAnagraficiTerzoIntermediarioType datiAnagraficiTI;
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.AnagraficaType anagraficaTI;
			it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.IdFiscaleType idFiscaleTI;
			
			
			if(fattura.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente() != null) {
				datiAnagraficiTI = fattura.getFatturaElettronicaHeader().getTerzoIntermediarioOSoggettoEmittente().getDatiAnagrafici();
				anagraficaTI = datiAnagraficiTI.getAnagrafica();
				
				if(datiAnagraficiTI.getIdFiscaleIVA() != null)
					idFiscaleTI = datiAnagraficiTI.getIdFiscaleIVA();
				else 
					idFiscaleTI = new it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.IdFiscaleType();
				
			} else {
				datiAnagraficiTI = new it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.DatiAnagraficiTerzoIntermediarioType();
				anagraficaTI = new it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.AnagraficaType();
				idFiscaleTI = new it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.IdFiscaleType();
			}
			
			params = getParameters(formatoFatturaPA, identificativoSDI, nomeFile, formatoArchivioInvioFatturaString, formatoArchivioBase64, messageId, fattura.getFatturaElettronicaHeader().getDatiTrasmissione().getCodiceDestinatario(), 
					 anagraficaCP.getDenominazione(), anagraficaCP.getNome(), anagraficaCP.getCognome(),
					 datiAnagraficiCP.getCodiceFiscale(), idFiscaleCP.getIdCodice(), idFiscaleCP.getIdPaese(),
					 anagraficaCC.getDenominazione(), anagraficaCC.getNome(), anagraficaCC.getCognome(), 
					 datiAnagraficiCC.getCodiceFiscale(), idFiscaleCC.getIdCodice(), idFiscaleCC.getIdPaese(),
					 anagraficaTI.getDenominazione(), anagraficaTI.getNome(), anagraficaTI.getCognome(),
					 datiAnagraficiTI.getCodiceFiscale(), idFiscaleTI.getIdCodice(), idFiscaleTI.getIdPaese(), fatturazioneAttiva, xml);
		} else {
			throw new Exception("Formato FatturaPA ["+formatoFatturaPA+"] non riconosciuto");
		}

		return params;
	}

	public static ConsegnaFatturaParameters getParameters(String formatoFatturaPA,
			Integer identificativoSDI, String nomeFile,
			String formatoArchivioInvioFatturaString,
			String formatoArchivioBase64, String messageId,
			boolean fatturazioneAttiva,
			InputStream fatturaStream) throws Exception, IOException {
		
		
		
		
		boolean isBase64 = false;
		if(formatoArchivioBase64 != null) {
			try {
				isBase64= Boolean.parseBoolean(formatoArchivioBase64);
			} catch(Exception e){
				throw new Exception("Parametro [X-SDI-FormatoArchivioBase64] non valido:" + formatoArchivioBase64);
			}
		}
		byte[] xml = getXmlBytes(fatturaStream, isBase64);
		return getParameters(formatoFatturaPA, identificativoSDI, nomeFile, formatoArchivioInvioFatturaString, formatoArchivioBase64, messageId, fatturazioneAttiva, xml);
	}

	public static ConsegnaFatturaParameters getParameters(LottoFatture lotto,
			int posizione, String nomeFile, byte[] xml) throws Exception {

		ConsegnaFatturaParameters params = getParameters(lotto.getFormatoTrasmissione().toString(), lotto.getIdentificativoSdi(), nomeFile, lotto.getFormatoArchivioInvioFattura().toString(), null, 
				lotto.getMessageId(), lotto.getCodiceDestinatario(), lotto.getCedentePrestatoreDenominazione(), lotto.getCedentePrestatoreNome(), lotto.getCedentePrestatoreCognome(), lotto.getCedentePrestatoreCodiceFiscale(),
				lotto.getCedentePrestatoreCodice(), lotto.getCedentePrestatorePaese(),
				lotto.getCessionarioCommittenteDenominazione(), lotto.getCessionarioCommittenteNome(), lotto.getCessionarioCommittenteCognome(), lotto.getCessionarioCommittenteCodiceFiscale(), 
				lotto.getCessionarioCommittenteCodice(), lotto.getCessionarioCommittentePaese(), 
				lotto.getTerzoIntermediarioOSoggettoEmittenteDenominazione(), lotto.getTerzoIntermediarioOSoggettoEmittenteNome(), lotto.getTerzoIntermediarioOSoggettoEmittenteCognome(), 
				lotto.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(), lotto.getTerzoIntermediarioOSoggettoEmittenteCodice(), lotto.getTerzoIntermediarioOSoggettoEmittentePaese(), lotto.getFatturazioneAttiva(), xml);
		params.setPosizioneFatturaPA(posizione);
		
		return params;
	}

	public static byte[] getLottoXml(LottoFatture lotto, Logger log) throws Exception {
		return getLottoXml(lotto.getFormatoArchivioInvioFattura(), lotto.getXml(), lotto.getIdentificativoSdi(), log);
	}
	
	public static byte[] getLottoXml(FormatoArchivioInvioFatturaType formatoArchivioInvioFattura, byte[] xml, Integer identificativoSdI, Logger log) throws Exception {
		if(FormatoArchivioInvioFatturaType.P7M.equals(formatoArchivioInvioFattura)) {
			try {
				P7MInfo info = new P7MInfo(xml, log);

				return info.getXmlDecoded();
			} catch(Throwable t) {
				throw new Exception("Lotto["+identificativoSdI+"]: "+t.getMessage(), t);
			}
		} else {
			return xml;
		}
	}

	public static String getNomeLottoXml(LottoFatture lotto, Logger log) throws Exception {
		if(FormatoArchivioInvioFatturaType.P7M.equals(lotto.getFormatoArchivioInvioFattura())) {
			if(lotto.getNomeFile().toLowerCase().endsWith(".p7m")) {
				int index = lotto.getNomeFile().toLowerCase().indexOf(".p7m");
				return lotto.getNomeFile().substring(0, index);
			} else {
				return lotto.getNomeFile();
			}
		} else {
			return lotto.getNomeFile();
		}

	}

	public static List<byte[]> getXmlWithSDIUtils(byte[] lottoXML) throws Exception {
		 return SDILottoUtils.splitLotto(lottoXML);
	}
	
	private static byte[] getXmlBytes(InputStream fatturaStream, boolean isBase64) throws IOException {
		if(isBase64) {
			byte[] xmlBase64 = streamToBytes(fatturaStream);
			return org.apache.soap.encoding.soapenc.Base64.decode(new String(xmlBase64));
		} else {
			return streamToBytes(fatturaStream);
		}
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

}
