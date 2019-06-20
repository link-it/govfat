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
package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.AbstractFatturaConverter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FPA12Converter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FatturaV10Converter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FatturaV11Converter;

import it.gov.fatturapa.sdi.fatturapa.v1_0.DatiPagamentoType;
import it.gov.fatturapa.sdi.fatturapa.v1_0.DettaglioPagamentoType;
import it.gov.fatturapa.sdi.fatturapa.v1_0.FatturaElettronicaBodyType;

public class FatturaDeserializerUtils {

	public static LottoFatture getLotto(ConsegnaFatturaParameters params, String dipartimento) throws Exception {

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

		lotto.setCodiceDestinatario(dipartimento);
		lotto.setFormatoTrasmissione(FormatoTrasmissioneType.valueOf(params.getFormatoFatturaPA()));

		lotto.setNomeFile(params.getNomeFile());
		lotto.setMessageId(params.getMessageId());

		lotto.setXml(params.getXml());
		
		lotto.setDataRicezione(new Date());
		lotto.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
		lotto.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
		lotto.setStatoInserimento(StatoInserimentoType.NON_INSERITO);

		return lotto;
	}

	
	public static ConsegnaFatturaParameters getParams(byte[] raw, Integer identificativo, String messageId,
			String nomeFile, boolean fatturazioneAttiva, String type) throws Exception, IOException {
		ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(identificativo, nomeFile,
						type, null,
						messageId,
						fatturazioneAttiva,
						raw);
		params.validate(true);
		return params;
	}
	
	public static ConsegnaFatturaParameters getParameters(LottoFatture lotto,
			int posizione, String nomeFile, boolean fatturazioneAttiva, byte[] xml) throws Exception {

		ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(lotto.getFormatoTrasmissione().toString(), lotto.getIdentificativoSdi(), nomeFile, lotto.getFormatoArchivioInvioFattura().toString(), null, 
				lotto.getMessageId(), lotto.getCodiceDestinatario(), lotto.getCedentePrestatoreDenominazione(), lotto.getCedentePrestatoreNome(), lotto.getCedentePrestatoreCognome(), lotto.getCedentePrestatoreCodiceFiscale(),
				lotto.getCedentePrestatoreCodice(), lotto.getCedentePrestatorePaese(),
				lotto.getCessionarioCommittenteDenominazione(), lotto.getCessionarioCommittenteNome(), lotto.getCessionarioCommittenteCognome(), lotto.getCessionarioCommittenteCodiceFiscale(), 
				lotto.getCessionarioCommittenteCodice(), lotto.getCessionarioCommittentePaese(), 
				lotto.getTerzoIntermediarioOSoggettoEmittenteDenominazione(), lotto.getTerzoIntermediarioOSoggettoEmittenteNome(), lotto.getTerzoIntermediarioOSoggettoEmittenteCognome(), 
				lotto.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(), lotto.getTerzoIntermediarioOSoggettoEmittenteCodice(), lotto.getTerzoIntermediarioOSoggettoEmittentePaese(), fatturazioneAttiva, xml);
		params.setPosizioneFatturaPA(posizione);
		
		return params;
	}
	
	public static String getDenominazioneDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getDenominazione();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getDenominazione();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getDenominazione();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getNomeDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getNome();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getNome();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getNome();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getCognomeDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getCognome();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getCognome();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getAnagrafica().getCognome();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getCodiceDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getDatiTrasmissione().getCodiceDestinatario();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getDatiTrasmissione().getCodiceDestinatario();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getDatiTrasmissione().getCodiceDestinatario();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getIndirizzoDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getIndirizzo();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getIndirizzo();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getIndirizzo();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getCapDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getCap();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getCap();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getCap();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getComuneDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getComune();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getComune();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getComune();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getProvinciaFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getProvincia();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getProvincia();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getProvincia();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getStatoFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getNazione();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getNazione();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getSede().getNazione();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}
	
	public static String getPartitaIVADestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			if(converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getIdFiscaleIVA() == null)
				return null;
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getIdFiscaleIVA().getIdCodice();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			if(converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getIdFiscaleIVA() == null)
				return null;
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getIdFiscaleIVA().getIdCodice();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			if(converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getIdFiscaleIVA() == null)
				return null;
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getIdFiscaleIVA().getIdCodice();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}

	public static String getCFMittenteFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici().getCodiceFiscale();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici().getCodiceFiscale();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCedentePrestatore().getDatiAnagrafici().getCodiceFiscale();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}

	public static String getNappFromFattura(FatturaElettronica fattura) throws Exception {
		
		String napp = null;
		String prefix = "PagoPA-";
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			for(FatturaElettronicaBodyType body: converter.getFattura().getFatturaElettronicaBodyList()) {
				for(DatiPagamentoType datiPagamento: body.getDatiPagamentoList()) {
					for(DettaglioPagamentoType dettagliPagamento: datiPagamento.getDettaglioPagamentoList()) {
						if(dettagliPagamento.getCodicePagamento()!= null && dettagliPagamento.getCodicePagamento().startsWith(prefix)) {
							napp = dettagliPagamento.getCodicePagamento();
						}
					}
				}
			}
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			for(it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaBodyType body: converter.getFattura().getFatturaElettronicaBodyList()) {
				for(it.gov.fatturapa.sdi.fatturapa.v1_1.DatiPagamentoType datiPagamento: body.getDatiPagamentoList()) {
					for(it.gov.fatturapa.sdi.fatturapa.v1_1.DettaglioPagamentoType dettagliPagamento: datiPagamento.getDettaglioPagamentoList()) {
						if(dettagliPagamento.getCodicePagamento()!= null && dettagliPagamento.getCodicePagamento().startsWith(prefix)) {
							napp = dettagliPagamento.getCodicePagamento();
						}
					}
				}
			}
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			for(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.FatturaElettronicaBodyType body: converter.getFattura().getFatturaElettronicaBodyList()) {
				for(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.DatiPagamentoType datiPagamento: body.getDatiPagamentoList()) {
					for(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.DettaglioPagamentoType dettagliPagamento: datiPagamento.getDettaglioPagamentoList()) {
						if(dettagliPagamento.getCodicePagamento()!= null && dettagliPagamento.getCodicePagamento().startsWith(prefix)) {
							napp = dettagliPagamento.getCodicePagamento();
						}
					}
				}
			}
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
		
		return napp.replace(prefix, "");
	}

	public static String getCodiceFiscaleDestinatarioFromFattura(FatturaElettronica fattura) throws Exception {
		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV10Converter converter = new FatturaV10Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getCodiceFiscale();
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FatturaV11Converter converter = new FatturaV11Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getCodiceFiscale();
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.getValue().equals(fattura.getFormatoTrasmissione().getValue()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.getValue().equals(fattura.getFormatoTrasmissione().getValue())) {
			FPA12Converter converter = new FPA12Converter(fattura.getXml(), null);
			return converter.getFattura().getFatturaElettronicaHeader().getCessionarioCommittente().getDatiAnagrafici().getCodiceFiscale();
		} else {
			throw new Exception("Formato FatturaPA ["+fattura.getFormatoTrasmissione()+"] non riconosciuto");
		}
	}

	public static FatturaElettronica getFattura(Logger log, byte[] raw, Integer identificativo, int posizione, String messageId,
			String nomeFile, String type, boolean fatturazioneAttiva, String dipartimento) throws Exception {
		LottoFatture lotto = getLotto(getParams(raw, identificativo, messageId, nomeFile, fatturazioneAttiva, type), dipartimento);
		
		byte[] lottoXml = ConsegnaFatturaUtils.getLottoXml(lotto, log);
		List<byte[]> fattureLst =ConsegnaFatturaUtils.getXmlWithSDIUtils(lottoXml);

		ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(lotto, posizione, nomeFile, fattureLst.get(posizione-1));
		AbstractFatturaConverter<?> converter = ConsegnaFattura.getConverter(params);
		return converter.getFatturaElettronica();
	}

	public static AbstractFatturaConverter<?> getConverter(Logger log, byte[] raw, Integer identificativo, int posizione, String messageId,
			String nomeFile, String type, boolean fatturazioneAttiva, String dipartimento) throws Exception {
		LottoFatture lotto = getLotto(getParams(raw, identificativo, messageId, nomeFile, fatturazioneAttiva, type), dipartimento);
		
		byte[] lottoXml = ConsegnaFatturaUtils.getLottoXml(lotto, log);
		List<byte[]> fattureLst =ConsegnaFatturaUtils.getXmlWithSDIUtils(lottoXml);

		ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(lotto, posizione, nomeFile, fattureLst.get(posizione-1));
		return ConsegnaFattura.getConverter(params);
	}


	
}
