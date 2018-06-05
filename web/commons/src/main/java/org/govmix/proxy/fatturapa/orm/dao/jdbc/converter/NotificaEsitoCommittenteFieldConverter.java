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
package org.govmix.proxy.fatturapa.orm.dao.jdbc.converter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.sql.AbstractSQLFieldConverter;
import org.openspcoop2.utils.TipiDatabase;

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;


/**     
 * NotificaEsitoCommittenteFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class NotificaEsitoCommittenteFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public NotificaEsitoCommittenteFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public NotificaEsitoCommittenteFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return NotificaEsitoCommittente.model();
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
		
		if(field.equals(NotificaEsitoCommittente.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().ID_FATTURA.POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().ID_FATTURA.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().NUMERO_FATTURA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".numero_fattura";
			}else{
				return "numero_fattura";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().ANNO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".anno";
			}else{
				return "anno";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito";
			}else{
				return "esito";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".message_id_committente";
			}else{
				return "message_id_committente";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().UTENTE.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().MODALITA_BATCH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_batch";
			}else{
				return "modalita_batch";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_INVIO_ENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_invio_ente";
			}else{
				return "data_invio_ente";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_INVIO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_invio_sdi";
			}else{
				return "data_invio_sdi";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna_sdi";
			}else{
				return "stato_consegna_sdi";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_consegna_sdi";
			}else{
				return "data_ultima_consegna_sdi";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_prossima_consegna_sdi";
			}else{
				return "data_prossima_consegna_sdi";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tentativi_consegna_sdi";
			}else{
				return "tentativi_consegna_sdi";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().SCARTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".scarto";
			}else{
				return "scarto";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().SCARTO_NOTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".scarto_note";
			}else{
				return "scarto_note";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().SCARTO_XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".scarto_xml";
			}else{
				return "scarto_xml";
			}
		}
		if(field.equals(NotificaEsitoCommittente.model().XML)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".xml";
			}else{
				return "xml";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(NotificaEsitoCommittente.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			return this.toTable(NotificaEsitoCommittente.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().ID_FATTURA.POSIZIONE)){
			return this.toTable(NotificaEsitoCommittente.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().ID_FATTURA.FATTURAZIONE_ATTIVA)){
			return this.toTable(NotificaEsitoCommittente.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().NUMERO_FATTURA)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().ANNO)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().POSIZIONE)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().ESITO)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().DESCRIZIONE)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().NOME_FILE)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().UTENTE.USERNAME)){
			return this.toTable(NotificaEsitoCommittente.model().UTENTE, returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().MODALITA_BATCH)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_INVIO_ENTE)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_INVIO_SDI)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().SCARTO)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().SCARTO_NOTE)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().SCARTO_XML)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}
		if(field.equals(NotificaEsitoCommittente.model().XML)){
			return this.toTable(NotificaEsitoCommittente.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(NotificaEsitoCommittente.model())){
			return "esito_committente";
		}
		if(model.equals(NotificaEsitoCommittente.model().ID_FATTURA)){
			return "fatture";
		}
		if(model.equals(NotificaEsitoCommittente.model().UTENTE)){
			return "utenti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
