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

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.ILottoConverter;
import org.openspcoop2.protocol.sdi.utils.P7MInfo;

public class FatturaDeserializerUtils {

	public static ILottoConverter getLottoConverter(LottoFatture lotto, Logger log) throws Exception {
		ConsegnaFatturaParameters params = new ConsegnaFatturaParameters();
		params.setCodiceDestinatario(lotto.getCodiceDestinatario());
		params.setDataRicezione(lotto.getDataRicezione());
		params.setDominio(lotto.getDominio());
		params.setSottodominio(lotto.getSottodominio());
		params.setFatturazioneAttiva(lotto.getFatturazioneAttiva());
		params.setFormatoArchivioInvioFattura(lotto.getFormatoArchivioInvioFattura());
		params.setFormatoFatturaPA(lotto.getFormatoTrasmissione().toString());
		params.setIdEgov(lotto.getIdEgov());
		params.setIdentificativoSdI(lotto.getIdentificativoSdi());
		params.setMessageId(lotto.getMessageId());
		params.setNomeFile(lotto.getNomeFile());
		params.setRaw(lotto.getXml());
		params.setLog(log);
		
		params.setXml(FatturaDeserializerUtils.getLottoXml(params.getFormatoArchivioInvioFattura(), params.getRaw(), params.getIdentificativoSdI(), log));

		return getLottoConverter(params);
	}

	public static ILottoConverter getLottoConverter(ConsegnaFatturaParameters params) throws InserimentoLottiException {
		return getLottoConverter(params.getFormatoFatturaPA(), params.getXml(), params);
	}

	private static Map<String, Constructor<? extends ILottoConverter>> converters;

	@SuppressWarnings("unchecked")
	private static void addConverter(Map<String, Constructor<? extends ILottoConverter>> map, String key, String className) throws InserimentoLottiException {
		try {
			Class<? extends ILottoConverter> classReader = (Class<? extends ILottoConverter>) Class.forName(className);
			map.put(key, classReader.getConstructor(byte[].class, ConsegnaFatturaParameters.class));
		} catch(Exception e) {
			throw new InserimentoLottiException(CODICE.ERRORE_GENERICO, e.getMessage());
		}
		
	}
	
	private static ILottoConverter getLottoConverter(String formato, byte[] xml, ConsegnaFatturaParameters params) throws InserimentoLottiException {

		if(converters == null) {
				converters = new HashMap<String, Constructor<? extends ILottoConverter>>();
				addConverter(converters, "SDI10","org.govmix.proxy.fatturapa.web.commons.converter.fattura.LottoSDI10Converter");
				addConverter(converters, "SDI11","org.govmix.proxy.fatturapa.web.commons.converter.fattura.LottoSDI11Converter");
				addConverter(converters, "FPA12","org.govmix.proxy.fatturapa.web.commons.converter.fattura.LottoFPA12Converter");
				addConverter(converters, "UBL21","org.govmix.proxy.fatturapa.web.commons.converter.UBLLottoConverter");
		}

		
		if(formato == null) {
			for(String form: converters.keySet()) {
				Constructor<? extends ILottoConverter> constructor = converters.get(form);
				try {
					return constructor.newInstance(xml, params);
				} catch(InstantiationException e) {}
				catch (Exception e) {
					throw new InserimentoLottiException(CODICE.ERRORE_GENERICO, e.getMessage());
				}
			}
			
			throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "Formato file non riconosciuto");
		} else {
			if(converters.containsKey(formato)) {
				Constructor<? extends ILottoConverter> constructor = converters.get(formato);
				try {
					return constructor.newInstance(xml, params);
				} catch (Exception e) {
					throw new InserimentoLottiException(CODICE.ERRORE_GENERICO, e.getMessage());
				}
			} else {
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, "Formato FatturaPA ["+formato+"] non riconosciuto");
			}
		}
		
	}

	public static byte[] getLottoXml(FormatoArchivioInvioFatturaType formatoArchivioInvioFattura, byte[] xml, Long identificativoSdI, Logger log) throws Exception {
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



}
