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
package org.govmix.proxy.fatturapa.web.console.costanti;

public class Costanti {

	/**Costanti formati date */
	
	public static final String FORMATO_DATA_DD_M_YYYY = "dd/M/yyyy";
	public static final String FORMATO_DATA_DD_M_YYYY_HH_MM_SS = "dd/M/yyyy HH:mm:ss";
	public static final String FORMATO_DATA_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String FORMATO_DATA_YYYY_M_MDD_H_HMMSS = "yyyyMMdd_HHmmss";
	
	/** Costanti classi css*/
	
	public static final String CSS_CLASS_WHITE_SPACE_NEW_LINE = "whiteSpaceNewLine";
	public static final String CSS_CLASS_DIAG_ERROR = "diag_error";
	public static final String CSS_CLASS_ALLINATO_DX = "allinatoDX";
	public static final String CSS_CLASS_DATI_TRASMISSIONE_TABLE = "datiTrasmissioneTable";
	public static final String CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE_TOP = "labelAllineataDx align-top,valueAllineataSx";
	public static final String CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE = "labelAllineataDx,valueAllineataSx";
	public static final String CSS_CLASS_DATI_DETTAGLIO_TRE_COLONNE = "labelAllineataDx,valueAllineataSx,valueAllineataSx";
	public static final String CSS_CLASS_DATI_DETTAGLIO_QUATTRO_COLONNE = "labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx";
	public static final String CSS_CLASS_DATI_DETTAGLIO_CINQUE_COLONNE = "labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx,valueAllineataSx";
	public static final String CSS_CLASS_DATI_DETTAGLIO_SEI_COLONNE = "labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx";
	
	/** Constanti select lista data ricezione */	
	
	public static final String DATA_RICEZIONE_PERIODO_PERSONALIZZATO = "3";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI = "2";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMO_MESE = "1";
	public static final String DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA = "0";
	
	/** Costanti select list data invio */
	
	public static final String DATA_INVIO_PERIODO_PERSONALIZZATO = "3";
	public static final String DATA_INVIO_PERIODO_ULTIMI_TRE_MESI = "2";
	public static final String DATA_INVIO_PERIODO_ULTIMO_MESE = "1";
	public static final String DATA_INVIO_PERIODO_ULTIMA_SETTIMANA = "0";
	
	/** Costanti data operazioni pcc */
	
	public static final String DATA_PERIODO_PERSONALIZZATO = "3";
	public static final String DATA_PERIODO_ULTIMI_TRE_MESI = "2";
	public static final String DATA_PERIODO_ULTIMO_MESE = "1";
	public static final String DATA_PERIODO_ULTIMA_SETTIMANA = "0";
	
	/** Path icone  */
	
	public static final String PATH_ICONA_ZIP = "/images/fatturapa/icons/zip.png";
	public static final String PATH_ICONA_PDF = "/images/fatturapa/icons/pdf.png";
	public static final String PATH_ICONA_XML = "/images/fatturapa/icons/xml.png";
	public static final String PATH_ICONA_DOCUMENT = "/images/fatturapa/icons/document.png";
}
