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

import org.govmix.proxy.fatturapa.orm.LottoFatture;


/**     
 * LottoFattureFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class LottoFattureFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public LottoFattureFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public LottoFattureFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return LottoFatture.model();
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
		
		if(field.equals(LottoFatture.model().FORMATO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_trasmissione";
			}else{
				return "formato_trasmissione";
			}
		}
		if(field.equals(LottoFatture.model().IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(LottoFatture.model().NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_archivio_invio_fattura";
			}else{
				return "formato_archivio_invio_fattura";
			}
		}
		if(field.equals(LottoFatture.model().MESSAGE_ID)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".message_id";
			}else{
				return "message_id";
			}
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_denominazione";
			}else{
				return "cp_denominazione";
			}
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nome";
			}else{
				return "cp_nome";
			}
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_cognome";
			}else{
				return "cp_cognome";
			}
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_idcodice";
			}else{
				return "cp_idcodice";
			}
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_nazione";
			}else{
				return "cp_nazione";
			}
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cp_codicefiscale";
			}else{
				return "cp_codicefiscale";
			}
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_denominazione";
			}else{
				return "cc_denominazione";
			}
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nome";
			}else{
				return "cc_nome";
			}
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_cognome";
			}else{
				return "cc_cognome";
			}
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_idcodice";
			}else{
				return "cc_idcodice";
			}
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_nazione";
			}else{
				return "cc_nazione";
			}
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cc_codicefiscale";
			}else{
				return "cc_codicefiscale";
			}
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_denominazione";
			}else{
				return "se_denominazione";
			}
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nome";
			}else{
				return "se_nome";
			}
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_cognome";
			}else{
				return "se_cognome";
			}
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_idcodice";
			}else{
				return "se_idcodice";
			}
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_nazione";
			}else{
				return "se_nazione";
			}
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".se_codicefiscale";
			}else{
				return "se_codicefiscale";
			}
		}
		if(field.equals(LottoFatture.model().CODICE_DESTINATARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_destinatario";
			}else{
				return "codice_destinatario";
			}
		}
		if(field.equals(LottoFatture.model().XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".xml";
			}else{
				return "xml";
			}
		}
		if(field.equals(LottoFatture.model().FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_elaborazione_in_uscita";
			}else{
				return "stato_elaborazione_in_uscita";
			}
		}
		if(field.equals(LottoFatture.model().TIPI_COMUNICAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipi_comunicazione";
			}else{
				return "tipi_comunicazione";
			}
		}
		if(field.equals(LottoFatture.model().DATA_ULTIMA_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_elaborazione";
			}else{
				return "data_ultima_elaborazione";
			}
		}
		if(field.equals(LottoFatture.model().DETTAGLIO_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_elaborazione";
			}else{
				return "dettaglio_elaborazione";
			}
		}
		if(field.equals(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_prossima_elaborazione";
			}else{
				return "data_prossima_elaborazione";
			}
		}
		if(field.equals(LottoFatture.model().TENTATIVI_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tentativi_consegna";
			}else{
				return "tentativi_consegna";
			}
		}
		if(field.equals(LottoFatture.model().DATA_RICEZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ricezione";
			}else{
				return "data_ricezione";
			}
		}
		if(field.equals(LottoFatture.model().STATO_INSERIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_inserimento";
			}else{
				return "stato_inserimento";
			}
		}
		if(field.equals(LottoFatture.model().STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(LottoFatture.model().DATA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_consegna";
			}else{
				return "data_consegna";
			}
		}
		if(field.equals(LottoFatture.model().DETTAGLIO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_consegna";
			}else{
				return "dettaglio_consegna";
			}
		}
		if(field.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_protocollazione";
			}else{
				return "stato_protocollazione";
			}
		}
		if(field.equals(LottoFatture.model().DOMINIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dominio";
			}else{
				return "dominio";
			}
		}
		if(field.equals(LottoFatture.model().SOTTODOMINIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".sottodominio";
			}else{
				return "sottodominio";
			}
		}
		if(field.equals(LottoFatture.model().PAGO_PA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".pago_pa";
			}else{
				return "pago_pa";
			}
		}
		if(field.equals(LottoFatture.model().DATA_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_protocollazione";
			}else{
				return "data_protocollazione";
			}
		}
		if(field.equals(LottoFatture.model().PROTOCOLLO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".protocollo";
			}else{
				return "protocollo";
			}
		}
		if(field.equals(LottoFatture.model().ID_SIP.ID_SIP)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_sip";
			}else{
				return "id_sip";
			}
		}
		if(field.equals(LottoFatture.model().ID_SIP.STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(LottoFatture.model().ID_SIP.DATA_ULTIMA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_consegna";
			}else{
				return "data_ultima_consegna";
			}
		}
		if(field.equals(LottoFatture.model().ID_EGOV)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_egov";
			}else{
				return "id_egov";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
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
		
		if(field.equals(LottoFatture.model().FORMATO_TRASMISSIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().IDENTIFICATIVO_SDI)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().NOME_FILE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().MESSAGE_ID)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_NOME)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_COGNOME)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_CODICE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_PAESE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().CODICE_DESTINATARIO)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().XML)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().FATTURAZIONE_ATTIVA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TIPI_COMUNICAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DATA_ULTIMA_ELABORAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DETTAGLIO_ELABORAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().TENTATIVI_CONSEGNA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DATA_RICEZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().STATO_INSERIMENTO)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().STATO_CONSEGNA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DATA_CONSEGNA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DETTAGLIO_CONSEGNA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DOMINIO)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().SOTTODOMINIO)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().PAGO_PA)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DATA_PROTOCOLLAZIONE)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().PROTOCOLLO)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().ID_SIP.ID_SIP)){
			return this.toTable(LottoFatture.model().ID_SIP, returnAlias);
		}
		if(field.equals(LottoFatture.model().ID_SIP.STATO_CONSEGNA)){
			return this.toTable(LottoFatture.model().ID_SIP, returnAlias);
		}
		if(field.equals(LottoFatture.model().ID_SIP.DATA_ULTIMA_CONSEGNA)){
			return this.toTable(LottoFatture.model().ID_SIP, returnAlias);
		}
		if(field.equals(LottoFatture.model().ID_EGOV)){
			return this.toTable(LottoFatture.model(), returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.CODICE)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ENTE.NOME)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DESCRIZIONE)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.MODALITA_PUSH)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO.NOME)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO.NOME)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			return this.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(LottoFatture.model())){
			return "lotti";
		}
		if(model.equals(LottoFatture.model().ID_SIP)){
			return "sip";
		}
		if(model.equals(LottoFatture.model().DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(LottoFatture.model().DIPARTIMENTO.ENTE)){
			return "enti";
		}
		if(model.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO)){
			return "registri";
		}
		if(model.equals(LottoFatture.model().DIPARTIMENTO.REGISTRO.ID_PROTOCOLLO)){
			return "protocolli";
		}
		if(model.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO)){
			return "protocolli";
		}


		return super.toTable(model,returnAlias);
		
	}

}
