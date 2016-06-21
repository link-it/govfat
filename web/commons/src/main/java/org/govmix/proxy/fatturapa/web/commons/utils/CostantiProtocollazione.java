/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.utils;


public class CostantiProtocollazione {

	public static final String IDENTIFICATIVO_SDI_HEADER_PARAM = "X-ProxyFatturaPA-IdSDI";
	public static final String POSIZIONE_HEADER_PARAM = "X-ProxyFatturaPA-Posizione"; 
	public static final String NUMERO_HEADER_PARAM = "X-ProxyFatturaPA-Numero";
	public static final String DATA_HEADER_PARAM = "X-ProxyFatturaPA-Data";
	public static final String VALUTA_HEADER_PARAM = "X-ProxyFatturaPA-Valuta";
	public static final String IMPORTO_HEADER_PARAM= "X-ProxyFatturaPA-Importo";
	public static final String ID_PROTOCOLLO_HEADER_PARAM= "X-ProxyFatturaPA-Id-Protocollo";
	public static final String MITTENTE_CF_HEADER_PARAM = "X-ProxyFatturaPA-Mittente-CF";
	public static final String MITTENTE_RAGIONESOCIALE_HEADER_PARAM = "X-ProxyFatturaPA-Mittente-RagioneSociale";
	public static final String DESTINATARIO_HEADER_PARAM = "X-ProxyFatturaPA-Destinatario";
	public static final String MSG_ID_HEADER_PARAM = "X-ProxyFatturaPA-MsgID";
	public static final String NOME_FILE_HEADER_PARAM = "X-ProxyFatturaPA-NomeFile";
	public static final String PROTOCOLLAZIONE_ASINCRONA_PARAM = "X-ProxyFatturaPA-Protocollazione-Asincrona";
	public static final String DESTINATARIO_CF_HEADER_PARAM = "X-ProxyFatturaPA-Destinatario-CF";
	public static final String DESTINATARIO_RAGIONESOCIALE_HEADER_PARAM = "X-ProxyFatturaPA-Destinatario-RagioneSociale";
	public static final String TERZO_INTERMEDIARIO_CF_HEADER_PARAM = "X-ProxyFatturaPA-TerzoIntermediario-CF";
	public static final String TERZO_INTERMEDIARIO_RAGIONESOCIALE_HEADER_PARAM = "X-ProxyFatturaPA-TerzoIntermediario-RagioneSociale";
	public static final String DETTAGLIO_CONSEGNA_HEADER_PARAM = "X-ProxyFatturaPA-DettaglioConsegna";
	public static final String TIPO_COMUNICAZIONE_HEADER_PARAM = "X-ProxyFatturaPA-TipoComunicazione";
	public static final String PROGRESSIVO_HEADER_PARAM = "X-ProxyFatturaPA-Progressivo";
}
