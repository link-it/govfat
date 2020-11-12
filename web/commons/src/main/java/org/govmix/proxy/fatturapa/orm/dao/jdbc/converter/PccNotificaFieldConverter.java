/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm.dao.jdbc.converter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.sql.AbstractSQLFieldConverter;
import org.openspcoop2.utils.TipiDatabase;

import org.govmix.proxy.fatturapa.orm.PccNotifica;


/**     
 * PccNotificaFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccNotificaFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public PccNotificaFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public PccNotificaFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return PccNotifica.model();
	}
	
	@Override
	public TipiDatabase getDatabaseType() throws ExpressionException {
		return this.databaseType;
	}
	


	@Override
	public String toColumn(IField field,boolean returnAlias,boolean appendTablePrefix) throws ExpressionException {
		
		// In the case of columns with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the column containing the alias
		
		if(field.equals(PccNotifica.model().ID_TRACCIA.ID_TRACCIA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_traccia";
			}else{
				return "id_traccia";
			}
		}
		if(field.equals(PccNotifica.model().ID_DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(PccNotifica.model().STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(PccNotifica.model().DATA_CREAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_creazione";
			}else{
				return "data_creazione";
			}
		}
		if(field.equals(PccNotifica.model().DATA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_consegna";
			}else{
				return "data_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.DATA_CREAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_creazione";
			}else{
				return "data_creazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.CF_TRASMITTENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cf_trasmittente";
			}else{
				return "cf_trasmittente";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.VERSIONE_APPLICATIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".versione_applicativa";
			}else{
				return "versione_applicativa";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_PCC_AMMINISTRAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_pcc_amministrazione";
			}else{
				return "id_pcc_amministrazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_PA_TRANSAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_pa_transazione";
			}else{
				return "id_pa_transazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_PA_TRANSAZIONE_RISPEDIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_pa_transazione_rispedizione";
			}else{
				return "id_pa_transazione_rispedizione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.SISTEMA_RICHIEDENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".sistema_richiedente";
			}else{
				return "sistema_richiedente";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.UTENTE_RICHIEDENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".utente_richiedente";
			}else{
				return "utente_richiedente";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_FATTURA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_fattura";
			}else{
				return "id_fattura";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.CODICE_DIPARTIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_dipartimento";
			}else{
				return "codice_dipartimento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RICHIESTA_XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".richiesta_xml";
			}else{
				return "richiesta_xml";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPOSTA_XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".risposta_xml";
			}else{
				return "risposta_xml";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".operazione";
			}else{
				return "operazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.TIPO_OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_operazione";
			}else{
				return "tipo_operazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.STATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato";
			}else{
				return "stato";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.DATA_ULTIMA_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_trasmissione";
			}else{
				return "data_ultima_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.DATA_ULTIMO_TENTATIVO_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultimo_tentativo_esito";
			}else{
				return "data_ultimo_tentativo_esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.CODICI_ERRORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codici_errore";
			}else{
				return "codici_errore";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".rispedizione";
			}else{
				return "rispedizione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_DOPO_QUERY)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".rispedizione_dopo_query";
			}else{
				return "rispedizione_dopo_query";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_MAX_TENTATIVI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".rispedizione_max_tentativi";
			}else{
				return "rispedizione_max_tentativi";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_PROSSIMO_TENTATIVO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".rispedizione_prox_tentativo";
			}else{
				return "rispedizione_prox_tentativo";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_NUMERO_TENTATIVI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".rispedizione_numero_tentativi";
			}else{
				return "rispedizione_numero_tentativi";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_ULTIMO_TENTATIVO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".rispedizione_ultimo_tentativo";
			}else{
				return "rispedizione_ultimo_tentativo";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_TRACCIA.ID_TRACCIA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_traccia";
			}else{
				return "id_traccia";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.TS_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".ts_trasmissione";
			}else{
				return "ts_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_PCC_TRANSAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_pcc_transazione";
			}else{
				return "id_pcc_transazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ESITO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito_trasmissione";
			}else{
				return "esito_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.STATO_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_esito";
			}else{
				return "stato_esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.GDO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".gdo";
			}else{
				return "gdo";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.DATA_FINE_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_fine_elaborazione";
			}else{
				return "data_fine_elaborazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.DETTAGLIO_ERRORE_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_errore_trasmissione";
			}else{
				return "dettaglio_errore_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_EGOV_RICHIESTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_egov_richiesta";
			}else{
				return "id_egov_richiesta";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ID_TRASMISSIONE.ID_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_trasmissione";
			}else{
				return "id_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ESITO_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito_elaborazione";
			}else{
				return "esito_elaborazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.DESCRIZIONE_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione_elaborazione";
			}else{
				return "descrizione_elaborazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.DATA_FINE_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_fine_elaborazione";
			}else{
				return "data_fine_elaborazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.GDO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".gdo";
			}else{
				return "gdo";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ESITO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito_trasmissione";
			}else{
				return "esito_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.DETTAGLIO_ERRORE_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_errore_trasmissione";
			}else{
				return "dettaglio_errore_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ID_EGOV_RICHIESTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_egov_richiesta";
			}else{
				return "id_egov_richiesta";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.ID_ESITO.ID_TRASMISSIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_trasmissione_esito";
			}else{
				return "id_trasmissione_esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.TIPO_OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_operazione";
			}else{
				return "tipo_operazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.PROGRESSIVO_OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".progressivo_operazione";
			}else{
				return "progressivo_operazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.CODICE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_esito";
			}else{
				return "codice_esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.DESCRIZIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione_esito";
			}else{
				return "descrizione_esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.FORMATO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_trasmissione";
			}else{
				return "formato_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_RICEZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ricezione";
			}else{
				return "data_ricezione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.MESSAGE_ID)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".message_id";
			}else{
				return "message_id";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_denominazione";
			}else{
				return "cp_denominazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nazione";
			}else{
				return "cp_nazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_codicefiscale";
			}else{
				return "cp_codicefiscale";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_denominazione";
			}else{
				return "cc_denominazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nazione";
			}else{
				return "cc_nazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_codicefiscale";
			}else{
				return "cc_codicefiscale";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_denominazione";
			}else{
				return "se_denominazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nazione";
			}else{
				return "se_nazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_codicefiscale";
			}else{
				return "se_codicefiscale";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CODICE_DESTINATARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_destinatario";
			}else{
				return "codice_destinatario";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TIPO_DOCUMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_documento";
			}else{
				return "tipo_documento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIVISA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".divisa";
			}else{
				return "divisa";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data";
			}else{
				return "data";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ANNO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".anno";
			}else{
				return "anno";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.NUMERO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".numero";
			}else{
				return "numero";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito";
			}else{
				return "esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DA_PAGARE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".da_pagare";
			}else{
				return "da_pagare";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.IMPORTO_TOTALE_DOCUMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_totale_documento";
			}else{
				return "importo_totale_documento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.IMPORTO_TOTALE_RIEPILOGO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_totale_riepilogo";
			}else{
				return "importo_totale_riepilogo";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CAUSALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".causale";
			}else{
				return "causale";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_consegna";
			}else{
				return "data_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_PROSSIMA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_prossima_consegna";
			}else{
				return "data_prossima_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TENTATIVI_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tentativi_consegna";
			}else{
				return "tentativi_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DETTAGLIO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_consegna";
			}else{
				return "dettaglio_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.STATO_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_protocollazione";
			}else{
				return "stato_protocollazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_SCADENZA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_scadenza";
			}else{
				return "data_scadenza";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_protocollazione";
			}else{
				return "data_protocollazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.PROTOCOLLO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".protocollo";
			}else{
				return "protocollo";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".xml";
			}else{
				return "xml";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_DECORRENZA_TERMINI.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP.ID_SIP)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_sip";
			}else{
				return "id_sip";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP.DATA_ULTIMA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_consegna";
			}else{
				return "data_ultima_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_CONTABILIZZAZIONE.ID_TRASMISSIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_trasmissione_esito";
			}else{
				return "id_trasmissione_esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_SCADENZA.ID_TRASMISSIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_trasmissione_esito";
			}else{
				return "id_trasmissione_esito";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.STATO_CONSERVAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_conservazione";
			}else{
				return "stato_conservazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.FORMATO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_trasmissione";
			}else{
				return "formato_trasmissione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_archivio_invio_fattura";
			}else{
				return "formato_archivio_invio_fattura";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.MESSAGE_ID)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".message_id";
			}else{
				return "message_id";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_denominazione";
			}else{
				return "cp_denominazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nome";
			}else{
				return "cp_nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_cognome";
			}else{
				return "cp_cognome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_idcodice";
			}else{
				return "cp_idcodice";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nazione";
			}else{
				return "cp_nazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_codicefiscale";
			}else{
				return "cp_codicefiscale";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_denominazione";
			}else{
				return "cc_denominazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nome";
			}else{
				return "cc_nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_cognome";
			}else{
				return "cc_cognome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_idcodice";
			}else{
				return "cc_idcodice";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nazione";
			}else{
				return "cc_nazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_codicefiscale";
			}else{
				return "cc_codicefiscale";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_denominazione";
			}else{
				return "se_denominazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nome";
			}else{
				return "se_nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_cognome";
			}else{
				return "se_cognome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_idcodice";
			}else{
				return "se_idcodice";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nazione";
			}else{
				return "se_nazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_codicefiscale";
			}else{
				return "se_codicefiscale";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CODICE_DESTINATARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_destinatario";
			}else{
				return "codice_destinatario";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".xml";
			}else{
				return "xml";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_elaborazione_in_uscita";
			}else{
				return "stato_elaborazione_in_uscita";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TIPI_COMUNICAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipi_comunicazione";
			}else{
				return "tipi_comunicazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_elaborazione";
			}else{
				return "data_ultima_elaborazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DETTAGLIO_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_elaborazione";
			}else{
				return "dettaglio_elaborazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_PROSSIMA_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_prossima_elaborazione";
			}else{
				return "data_prossima_elaborazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TENTATIVI_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tentativi_consegna";
			}else{
				return "tentativi_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_RICEZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ricezione";
			}else{
				return "data_ricezione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_INSERIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_inserimento";
			}else{
				return "stato_inserimento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_consegna";
			}else{
				return "data_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DETTAGLIO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_consegna";
			}else{
				return "dettaglio_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_protocollazione";
			}else{
				return "stato_protocollazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DOMINIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dominio";
			}else{
				return "dominio";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.SOTTODOMINIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".sottodominio";
			}else{
				return "sottodominio";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.PAGO_PA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".pago_pa";
			}else{
				return "pago_pa";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_protocollazione";
			}else{
				return "data_protocollazione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.PROTOCOLLO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".protocollo";
			}else{
				return "protocollo";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP.ID_SIP)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_sip";
			}else{
				return "id_sip";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP.DATA_ULTIMA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_consegna";
			}else{
				return "data_ultima_consegna";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_EGOV)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_egov";
			}else{
				return "id_egov";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(PccNotifica.model().ID_TRACCIA.ID_TRACCIA)){
			return this.toTable(PccNotifica.model().ID_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().ID_DIPARTIMENTO.CODICE)){
			return this.toTable(PccNotifica.model().ID_DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().STATO_CONSEGNA)){
			return this.toTable(PccNotifica.model(), returnAlias);
		}
		if(field.equals(PccNotifica.model().DATA_CREAZIONE)){
			return this.toTable(PccNotifica.model(), returnAlias);
		}
		if(field.equals(PccNotifica.model().DATA_CONSEGNA)){
			return this.toTable(PccNotifica.model(), returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.DATA_CREAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.CF_TRASMITTENTE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.VERSIONE_APPLICATIVA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_PCC_AMMINISTRAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_PA_TRANSAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_PA_TRANSAZIONE_RISPEDIZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.SISTEMA_RICHIEDENTE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.UTENTE_RICHIEDENTE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.ID_FATTURA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.CODICE_DIPARTIMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RICHIESTA_XML)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPOSTA_XML)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.OPERAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.TIPO_OPERAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.STATO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.DATA_ULTIMA_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.DATA_ULTIMO_TENTATIVO_ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.CODICI_ERRORE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_DOPO_QUERY)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_MAX_TENTATIVI)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_PROSSIMO_TENTATIVO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_NUMERO_TENTATIVI)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.RISPEDIZIONE_ULTIMO_TENTATIVO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_TRACCIA.ID_TRACCIA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_TRACCIA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.TS_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_PCC_TRANSAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ESITO_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.STATO_ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.GDO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.DATA_FINE_ELABORAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.DETTAGLIO_ERRORE_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_EGOV_RICHIESTA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ID_TRASMISSIONE.ID_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ID_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ESITO_ELABORAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.DESCRIZIONE_ELABORAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.DATA_FINE_ELABORAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.GDO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ESITO_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.DETTAGLIO_ERRORE_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ID_EGOV_RICHIESTA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.ID_ESITO.ID_TRASMISSIONE_ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.ID_ESITO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.TIPO_OPERAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.PROGRESSIVO_OPERAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.CODICE_ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.DESCRIZIONE_ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.FORMATO_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.IDENTIFICATIVO_SDI)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.FATTURAZIONE_ATTIVA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_RICEZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.NOME_FILE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.MESSAGE_ID)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_PAESE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_CODICE_FISCALE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_PAESE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.POSIZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CODICE_DESTINATARIO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TIPO_DOCUMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIVISA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ANNO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.NUMERO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DA_PAGARE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.IMPORTO_TOTALE_DOCUMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.IMPORTO_TOTALE_RIEPILOGO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.CAUSALE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.STATO_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_PROSSIMA_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.TENTATIVI_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DETTAGLIO_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.STATO_PROTOCOLLAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_SCADENZA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DATA_PROTOCOLLAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.PROTOCOLLO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.XML)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_DECORRENZA_TERMINI.IDENTIFICATIVO_SDI)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_DECORRENZA_TERMINI, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP.ID_SIP)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP.STATO_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP.DATA_ULTIMA_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_CONTABILIZZAZIONE.ID_TRASMISSIONE_ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_CONTABILIZZAZIONE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_SCADENZA.ID_TRASMISSIONE_ESITO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_SCADENZA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.STATO_CONSERVAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.CODICE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DESCRIZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ID_PROCEDIMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.FIRMA_AUTOMATICA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.MODALITA_PUSH)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.USERNAME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.FORMATO_TRASMISSIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.IDENTIFICATIVO_SDI)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.NOME_FILE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.MESSAGE_ID)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.CODICE_DESTINATARIO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.XML)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.FATTURAZIONE_ATTIVA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TIPI_COMUNICAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DETTAGLIO_ELABORAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_PROSSIMA_ELABORAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.TENTATIVI_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_RICEZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_INSERIMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DETTAGLIO_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.STATO_PROTOCOLLAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DOMINIO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.SOTTODOMINIO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.PAGO_PA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DATA_PROTOCOLLAZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.PROTOCOLLO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP.ID_SIP)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP.STATO_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP.DATA_ULTIMA_CONSEGNA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_EGOV)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.CODICE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DESCRIZIONE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.FIRMA_AUTOMATICA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.MODALITA_PUSH)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.USERNAME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			return this.toTable(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.CODICE)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ENTE.NOME)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DESCRIZIONE)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.MODALITA_PUSH)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO.NOME)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			return this.toTable(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(PccNotifica.model())){
			return "pcc_notifiche";
		}
		if(model.equals(PccNotifica.model().ID_TRACCIA)){
			return "pcc_tracce";
		}
		if(model.equals(PccNotifica.model().ID_DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA)){
			return "pcc_tracce";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE)){
			return "pcc_tracce_trasmissioni";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.ID_TRACCIA)){
			return "pcc_tracce";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO)){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.ID_TRASMISSIONE)){
			return "pcc_tracce_trasmissioni";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE)){
			return "pcc_errori_elaborazione";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE.ID_ESITO)){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA)){
			return "fatture";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_DECORRENZA_TERMINI)){
			return "decorrenza_termini";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_SIP)){
			return "sip";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_CONTABILIZZAZIONE)){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.ID_ESITO_SCADENZA)){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.ENTE)){
			return "enti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO)){
			return "registri";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO)){
			return "protocolli";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO)){
			return "protocolli";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE)){
			return "lotti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.ID_SIP)){
			return "sip";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.ENTE)){
			return "enti";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO)){
			return "registri";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO)){
			return "protocolli";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(PccNotifica.model().PCC_TRACCIA.FATTURA_ELETTRONICA.LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO)){
			return "id_protocollo";
		}
		if(model.equals(PccNotifica.model().DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(PccNotifica.model().DIPARTIMENTO.ENTE)){
			return "enti";
		}
		if(model.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO)){
			return "registri";
		}
		if(model.equals(PccNotifica.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO)){
			return "protocolli";
		}
		if(model.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(PccNotifica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO)){
			return "protocolli";
		}


		return super.toTable(model,returnAlias);
		
	}

}
