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
		if(field.equals(FatturaElettronica.model().FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
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
		if(field.equals(FatturaElettronica.model().ID_SIP.ID_SIP)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_sip";
			}else{
				return "id_sip";
			}
		}
		if(field.equals(FatturaElettronica.model().ID_SIP.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().ID_SIP.DATA_ULTIMA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_consegna";
			}else{
				return "data_ultima_consegna";
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
		if(field.equals(FatturaElettronica.model().STATO_CONSERVAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_conservazione";
			}else{
				return "stato_conservazione";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_trasmissione";
			}else{
				return "formato_trasmissione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_archivio_invio_fattura";
			}else{
				return "formato_archivio_invio_fattura";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.MESSAGE_ID)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".message_id";
			}else{
				return "message_id";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_denominazione";
			}else{
				return "cp_denominazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nome";
			}else{
				return "cp_nome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_cognome";
			}else{
				return "cp_cognome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_idcodice";
			}else{
				return "cp_idcodice";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nazione";
			}else{
				return "cp_nazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_codicefiscale";
			}else{
				return "cp_codicefiscale";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_denominazione";
			}else{
				return "cc_denominazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nome";
			}else{
				return "cc_nome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_cognome";
			}else{
				return "cc_cognome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_idcodice";
			}else{
				return "cc_idcodice";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nazione";
			}else{
				return "cc_nazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_codicefiscale";
			}else{
				return "cc_codicefiscale";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_denominazione";
			}else{
				return "se_denominazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nome";
			}else{
				return "se_nome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_cognome";
			}else{
				return "se_cognome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_idcodice";
			}else{
				return "se_idcodice";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nazione";
			}else{
				return "se_nazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_codicefiscale";
			}else{
				return "se_codicefiscale";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CODICE_DESTINATARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_destinatario";
			}else{
				return "codice_destinatario";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".xml";
			}else{
				return "xml";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_elaborazione_in_uscita";
			}else{
				return "stato_elaborazione_in_uscita";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TIPI_COMUNICAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipi_comunicazione";
			}else{
				return "tipi_comunicazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_elaborazione";
			}else{
				return "data_ultima_elaborazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DETTAGLIO_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_elaborazione";
			}else{
				return "dettaglio_elaborazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_PROSSIMA_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_prossima_elaborazione";
			}else{
				return "data_prossima_elaborazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TENTATIVI_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tentativi_consegna";
			}else{
				return "tentativi_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_RICEZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ricezione";
			}else{
				return "data_ricezione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_INSERIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_inserimento";
			}else{
				return "stato_inserimento";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_consegna";
			}else{
				return "data_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_consegna";
			}else{
				return "dettaglio_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_protocollazione";
			}else{
				return "stato_protocollazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DOMINIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dominio";
			}else{
				return "dominio";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.SOTTODOMINIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".sottodominio";
			}else{
				return "sottodominio";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.PAGO_PA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".pago_pa";
			}else{
				return "pago_pa";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_protocollazione";
			}else{
				return "data_protocollazione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.PROTOCOLLO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".protocollo";
			}else{
				return "protocollo";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP.ID_SIP)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_sip";
			}else{
				return "id_sip";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP.DATA_ULTIMA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_consegna";
			}else{
				return "data_ultima_consegna";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_EGOV)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_egov";
			}else{
				return "id_egov";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
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
		
		if(field.equals(FatturaElettronica.model().FORMATO_TRASMISSIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().FATTURAZIONE_ATTIVA)){
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
		if(field.equals(FatturaElettronica.model().ID_SIP.ID_SIP)){
			return this.toTable(FatturaElettronica.model().ID_SIP, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ID_SIP.STATO_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().ID_SIP, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ID_SIP.DATA_ULTIMA_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().ID_SIP, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE.ID_TRASMISSIONE_ESITO)){
			return this.toTable(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().ID_ESITO_SCADENZA.ID_TRASMISSIONE_ESITO)){
			return this.toTable(FatturaElettronica.model().ID_ESITO_SCADENZA, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().STATO_CONSERVAZIONE)){
			return this.toTable(FatturaElettronica.model(), returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.CODICE)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ENTE.NOME)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DESCRIZIONE)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.MODALITA_PUSH)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.REGISTRO.NOME)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
			return this.toTable(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_TRASMISSIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.NOME_FILE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.MESSAGE_ID)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.CODICE_DESTINATARIO)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.XML)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TIPI_COMUNICAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DETTAGLIO_ELABORAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_PROSSIMA_ELABORAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.TENTATIVI_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_RICEZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_INSERIMENTO)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_PROTOCOLLAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DOMINIO)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.SOTTODOMINIO)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.PAGO_PA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.PROTOCOLLO)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP.ID_SIP)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP.STATO_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP.DATA_ULTIMA_CONSEGNA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_EGOV)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.CODICE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ENTE.NOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DESCRIZIONE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.FIRMA_AUTOMATICA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.MODALITA_PUSH)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.NOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.REGISTRO.USERNAME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
			return this.toTable(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE, returnAlias);
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
		if(model.equals(FatturaElettronica.model().ID_SIP)){
			return "sip";
		}
		if(model.equals(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE)){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(FatturaElettronica.model().ID_ESITO_SCADENZA)){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(FatturaElettronica.model().DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(FatturaElettronica.model().DIPARTIMENTO.ENTE)){
			return "enti";
		}
		if(model.equals(FatturaElettronica.model().DIPARTIMENTO.REGISTRO)){
			return "registri";
		}
		if(model.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(FatturaElettronica.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE)){
			return "enti";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE)){
			return "lotti";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE.ID_SIP)){
			return "sip";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.ENTE)){
			return "enti";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.REGISTRO)){
			return "registri";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(FatturaElettronica.model().LOTTO_FATTURE.DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE)){
			return "enti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
