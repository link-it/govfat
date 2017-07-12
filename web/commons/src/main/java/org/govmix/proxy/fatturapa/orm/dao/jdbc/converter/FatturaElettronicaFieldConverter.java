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
package org.govmix.proxy.fatturapa.orm.dao.jdbc.converter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.sql.AbstractSQLFieldConverter;
import org.openspcoop2.utils.TipiDatabase;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;


/**     
 * FatturaElettronicaFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class FatturaElettronicaFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public FatturaElettronicaFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public FatturaElettronicaFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return FatturaElettronica.model();
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
		
		if(field.equals(FatturaElettronica.model().FORMATO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_trasmissione";
			}else{
				return "formato_trasmissione";
			}
		}
		if(field.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(FatturaElettronica.model().DATA_RICEZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ricezione";
			}else{
				return "data_ricezione";
			}
		}
		if(field.equals(FatturaElettronica.model().NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(FatturaElettronica.model().MESSAGE_ID)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".message_id";
			}else{
				return "message_id";
			}
		}
		if(field.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_denominazione";
			}else{
				return "cp_denominazione";
			}
		}
		if(field.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nazione";
			}else{
				return "cp_nazione";
			}
		}
		if(field.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_codicefiscale";
			}else{
				return "cp_codicefiscale";
			}
		}
		if(field.equals(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_denominazione";
			}else{
				return "cc_denominazione";
			}
		}
		if(field.equals(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nazione";
			}else{
				return "cc_nazione";
			}
		}
		if(field.equals(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_codicefiscale";
			}else{
				return "cc_codicefiscale";
			}
		}
		if(field.equals(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_denominazione";
			}else{
				return "se_denominazione";
			}
		}
		if(field.equals(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nazione";
			}else{
				return "se_nazione";
			}
		}
		if(field.equals(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_codicefiscale";
			}else{
				return "se_codicefiscale";
			}
		}
		if(field.equals(FatturaElettronica.model().POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(FatturaElettronica.model().CODICE_DESTINATARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_destinatario";
			}else{
				return "codice_destinatario";
			}
		}
		if(field.equals(FatturaElettronica.model().TIPO_DOCUMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_documento";
			}else{
				return "tipo_documento";
			}
		}
		if(field.equals(FatturaElettronica.model().DIVISA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".divisa";
			}else{
				return "divisa";
			}
		}
		if(field.equals(FatturaElettronica.model().DATA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data";
			}else{
				return "data";
			}
		}
		if(field.equals(FatturaElettronica.model().ANNO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".anno";
			}else{
				return "anno";
			}
		}
		if(field.equals(FatturaElettronica.model().NUMERO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".numero";
			}else{
				return "numero";
			}
		}
		if(field.equals(FatturaElettronica.model().ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito";
			}else{
				return "esito";
			}
		}
		if(field.equals(FatturaElettronica.model().DA_PAGARE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".da_pagare";
			}else{
				return "da_pagare";
			}
		}
		if(field.equals(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_totale_documento";
			}else{
				return "importo_totale_documento";
			}
		}
		if(field.equals(FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_totale_riepilogo";
			}else{
				return "importo_totale_riepilogo";
			}
		}
		if(field.equals(FatturaElettronica.model().CAUSALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".causale";
			}else{
				return "causale";
			}
		}
		if(field.equals(FatturaElettronica.model().STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().DATA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_consegna";
			}else{
				return "data_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_prossima_consegna";
			}else{
				return "data_prossima_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().TENTATIVI_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tentativi_consegna";
			}else{
				return "tentativi_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().DETTAGLIO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_consegna";
			}else{
				return "dettaglio_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().STATO_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_protocollazione";
			}else{
				return "stato_protocollazione";
			}
		}
		if(field.equals(FatturaElettronica.model().DATA_SCADENZA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_scadenza";
			}else{
				return "data_scadenza";
			}
		}
		if(field.equals(FatturaElettronica.model().DATA_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_protocollazione";
			}else{
				return "data_protocollazione";
			}
		}
		if(field.equals(FatturaElettronica.model().PROTOCOLLO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".protocollo";
			}else{
				return "protocollo";
			}
		}
		if(field.equals(FatturaElettronica.model().XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".xml";
			}else{
				return "xml";
			}
		}
		if(field.equals(FatturaElettronica.model().ID_DECORRENZA_TERMINI.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE.ID_TRASMISSIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_trasmissione_esito";
			}else{
				return "id_trasmissione_esito";
			}
		}
		if(field.equals(FatturaElettronica.model().ID_ESITO_SCADENZA.ID_TRASMISSIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_trasmissione_esito";
			}else{
				return "id_trasmissione_esito";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(FatturaElettronica.model().FORMATO_TRASMISSIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DATA_RICEZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().NOME_FILE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().MESSAGE_ID)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().POSIZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CODICE_DESTINATARIO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().TIPO_DOCUMENTO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIVISA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DATA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ANNO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().NUMERO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ESITO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DA_PAGARE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().CAUSALE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().STATO_CONSEGNA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DATA_CONSEGNA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().TENTATIVI_CONSEGNA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DETTAGLIO_CONSEGNA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().STATO_PROTOCOLLAZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DATA_SCADENZA)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DATA_PROTOCOLLAZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().PROTOCOLLO)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().XML)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ID_DECORRENZA_TERMINI.IDENTIFICATIVO_SDI)){
			return this.toTable(FatturaElettronica.model().ID_DECORRENZA_TERMINI, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE.ID_TRASMISSIONE_ESITO)){
			return this.toTable(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ID_ESITO_SCADENZA.ID_TRASMISSIONE_ESITO)){
			return this.toTable(FatturaElettronica.model().ID_ESITO_SCADENZA, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(FatturaElettronica.model())){
			return "fatture";
		}
		if(model.equals(FatturaElettronica.model().ID_DECORRENZA_TERMINI)){
			return "decorrenza_termini";
		}
		if(model.equals(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE)){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(FatturaElettronica.model().ID_ESITO_SCADENZA)){
			return "tracce_trasmissioni_esiti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
