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
package org.govmix.proxy.fatturapa.web.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.pcc.fatture.utils.PccProperties;

public class TransformUtils {

	private static final String MAIL_ADDRESS_SEPARATOR = ",";
	private static final String DESCRIZIONE_SEPARATOR = "$$$";
	private static final String DESCRIZIONE_REGEX_FIELD_SEPARATOR = "\\$";
	private static final char DESCRIZIONE_FIELD_SEPARATOR = '$';
	private static final String NULL_DESCRIZIONE = "##NULLDESCR###";
	
	public static List<String> toListMailAddress(String raw) throws Exception {
		String[] split = raw.split(MAIL_ADDRESS_SEPARATOR);
		List<String> splitLst = new ArrayList<String>();
		for(String s: split) {
			splitLst.add(s);
		}
		return splitLst;
	}
	
	public static String toRawMailAddress(List<String> lst) throws Exception {
		StringBuffer sb = new StringBuffer();
		for(String s: lst) {
			if(sb.length() > 0) {
				sb.append(MAIL_ADDRESS_SEPARATOR);
			}
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	public static String toRawDescrizioneImportoContabilizzazione(String descrizione, String sistemaRichiedente, String utenteRichiedente, String idImporto) {
		String realDescrizione = descrizione != null ? descrizione : NULL_DESCRIZIONE;
		return sistemaRichiedente + DESCRIZIONE_FIELD_SEPARATOR + utenteRichiedente + DESCRIZIONE_FIELD_SEPARATOR + idImporto + DESCRIZIONE_SEPARATOR + realDescrizione;
	} 
	
	public static void populateContabilizzazione(org.govmix.pcc.fatture.ContabilizzazioneTipo cont, String descrizioneRaw) throws Exception {
		if(descrizioneRaw == null) throw new Exception("Descrizione non puo' essere null");
		
		String campiNascosti = descrizioneRaw.substring(0, descrizioneRaw.indexOf(DESCRIZIONE_SEPARATOR));

		String[] campiNascostiSplit = campiNascosti.split(DESCRIZIONE_REGEX_FIELD_SEPARATOR);
		
		if(campiNascostiSplit.length != 3) throw new Exception("Formato descrizione ["+descrizioneRaw+"] non valido. La sottostringa ["+campiNascosti+"]: la stringa ["+DESCRIZIONE_FIELD_SEPARATOR+"] dovrebbe separare 3 stringhe, trovate ["+campiNascostiSplit.length+"]");

		cont.setIdentificativoMovimento(campiNascostiSplit[2]);

		cont.setDescrizione(toStringDescrizioneImportoContabilizzazione(descrizioneRaw));

	} 

	public static boolean isDescrizioneRaw(String descrizione) {
		return descrizione.contains(DESCRIZIONE_SEPARATOR);
	}
	
	public static void populateContabilizzazione(PccContabilizzazione cont, String descrizioneRaw) throws Exception {
		if(descrizioneRaw == null) throw new Exception("Descrizione non puo' essere null");
		
		String campiNascosti = descrizioneRaw.substring(0, descrizioneRaw.indexOf(DESCRIZIONE_SEPARATOR));

		String[] campiNascostiSplit = campiNascosti.split(DESCRIZIONE_REGEX_FIELD_SEPARATOR);
		
		if(campiNascostiSplit.length != 3) throw new Exception("Formato descrizione ["+descrizioneRaw+"] non valido. La sottostringa ["+campiNascosti+"]: la stringa ["+DESCRIZIONE_FIELD_SEPARATOR+"] dovrebbe separare 3 stringhe, trovate ["+campiNascostiSplit.length+"]");

		cont.setSistemaRichiedente(campiNascostiSplit[0]);
		cont.setUtenteRichiedente(campiNascostiSplit[1]);
		cont.setIdImporto(campiNascostiSplit[2]);

		cont.setDescrizione(toStringDescrizioneImportoContabilizzazione(descrizioneRaw));

	} 

	public static String toStringDescrizioneImportoContabilizzazione(String descrizioneRaw) {
		if(descrizioneRaw == null) return null;
		
		if(!descrizioneRaw.contains(DESCRIZIONE_SEPARATOR))
			return descrizioneRaw;
		
		String descrizione = descrizioneRaw.substring(descrizioneRaw.indexOf(DESCRIZIONE_SEPARATOR) + DESCRIZIONE_SEPARATOR.length());
		
		if(descrizione.equals(NULL_DESCRIZIONE)) return null;
		
		return descrizione;
	}

	public static void populateContabilizzazioneDefault(PccContabilizzazione pccContabilizzazione, boolean cruscotto) {
		if(cruscotto) {
			pccContabilizzazione.setSistemaRichiedente(PccProperties.getInstance().getSistemaRichiedenteCruscotto());
			pccContabilizzazione.setUtenteRichiedente(PccProperties.getInstance().getUtenteRichiedenteCruscotto());
		} else {
			pccContabilizzazione.setSistemaRichiedente(PccProperties.getInstance().getSistemaRichiedenteGestionale());
			pccContabilizzazione.setUtenteRichiedente(PccProperties.getInstance().getUtenteRichiedenteGestionale());
		}

		pccContabilizzazione.setIdImporto(UUID.randomUUID().toString().substring(0, 3));
		pccContabilizzazione.setDescrizione(toRawDescrizioneImportoContabilizzazione(null, pccContabilizzazione.getSistemaRichiedente(), pccContabilizzazione.getUtenteRichiedente(), pccContabilizzazione.getIdImporto()));
	} 
	
	
}
