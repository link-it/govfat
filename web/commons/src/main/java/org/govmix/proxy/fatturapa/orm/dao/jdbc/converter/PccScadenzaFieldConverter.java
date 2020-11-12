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

import org.govmix.proxy.fatturapa.orm.PccScadenza;


/**     
 * PccScadenzaFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccScadenzaFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public PccScadenzaFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public PccScadenzaFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return PccScadenza.model();
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
		
		if(field.equals(PccScadenza.model().IMPORTO_IN_SCADENZA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_in_scadenza";
			}else{
				return "importo_in_scadenza";
			}
		}
		if(field.equals(PccScadenza.model().IMPORTO_INIZIALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_iniziale";
			}else{
				return "importo_iniziale";
			}
		}
		if(field.equals(PccScadenza.model().PAGATO_RICONTABILIZZATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".pagato_ricontabilizzato";
			}else{
				return "pagato_ricontabilizzato";
			}
		}
		if(field.equals(PccScadenza.model().DATA_SCADENZA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_scadenza";
			}else{
				return "data_scadenza";
			}
		}
		if(field.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(PccScadenza.model().ID_FATTURA.POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(PccScadenza.model().ID_FATTURA.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(PccScadenza.model().SISTEMA_RICHIEDENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".sistema_richiedente";
			}else{
				return "sistema_richiedente";
			}
		}
		if(field.equals(PccScadenza.model().UTENTE_RICHIEDENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".utente_richiedente";
			}else{
				return "utente_richiedente";
			}
		}
		if(field.equals(PccScadenza.model().DATA_RICHIESTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_richiesta";
			}else{
				return "data_richiesta";
			}
		}
		if(field.equals(PccScadenza.model().DATA_QUERY)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_query";
			}else{
				return "data_query";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(PccScadenza.model().IMPORTO_IN_SCADENZA)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}
		if(field.equals(PccScadenza.model().IMPORTO_INIZIALE)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}
		if(field.equals(PccScadenza.model().PAGATO_RICONTABILIZZATO)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}
		if(field.equals(PccScadenza.model().DATA_SCADENZA)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}
		if(field.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			return this.toTable(PccScadenza.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(PccScadenza.model().ID_FATTURA.POSIZIONE)){
			return this.toTable(PccScadenza.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(PccScadenza.model().ID_FATTURA.FATTURAZIONE_ATTIVA)){
			return this.toTable(PccScadenza.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(PccScadenza.model().SISTEMA_RICHIEDENTE)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}
		if(field.equals(PccScadenza.model().UTENTE_RICHIEDENTE)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}
		if(field.equals(PccScadenza.model().DATA_RICHIESTA)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}
		if(field.equals(PccScadenza.model().DATA_QUERY)){
			return this.toTable(PccScadenza.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(PccScadenza.model())){
			return "pcc_scadenze";
		}
		if(model.equals(PccScadenza.model().ID_FATTURA)){
			return "fatture";
		}


		return super.toTable(model,returnAlias);
		
	}

}
