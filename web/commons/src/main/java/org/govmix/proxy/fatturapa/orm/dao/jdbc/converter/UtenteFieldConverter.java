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

import org.govmix.proxy.fatturapa.orm.Utente;


/**     
 * UtenteFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class UtenteFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public UtenteFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public UtenteFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return Utente.model();
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
		
		if(field.equals(Utente.model().USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(Utente.model().PASSWORD)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".password";
			}else{
				return "password";
			}
		}
		if(field.equals(Utente.model().NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Utente.model().COGNOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cognome";
			}else{
				return "cognome";
			}
		}
		if(field.equals(Utente.model().ROLE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".role";
			}else{
				return "role";
			}
		}
		if(field.equals(Utente.model().TIPO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo";
			}else{
				return "tipo";
			}
		}
		if(field.equals(Utente.model().ESTERNO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esterno";
			}else{
				return "esterno";
			}
		}
		if(field.equals(Utente.model().SISTEMA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".sistema";
			}else{
				return "sistema";
			}
		}
		if(field.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(Utente.model().UTENTE_DIPARTIMENTO.DATA_ULTIMA_MODIFICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ultima_modifica";
			}else{
				return "data_ultima_modifica";
			}
		}
		if(field.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_RESPONSABILE.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(Utente.model().USERNAME)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().PASSWORD)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().NOME)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().COGNOME)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().ROLE)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().TIPO)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().ESTERNO)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().SISTEMA)){
			return this.toTable(Utente.model(), returnAlias);
		}
		if(field.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE)){
			return this.toTable(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO, returnAlias);
		}
		if(field.equals(Utente.model().UTENTE_DIPARTIMENTO.DATA_ULTIMA_MODIFICA)){
			return this.toTable(Utente.model().UTENTE_DIPARTIMENTO, returnAlias);
		}
		if(field.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_RESPONSABILE.USERNAME)){
			return this.toTable(Utente.model().UTENTE_DIPARTIMENTO.ID_RESPONSABILE, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(Utente.model())){
			return "utenti";
		}
		if(model.equals(Utente.model().UTENTE_DIPARTIMENTO)){
			return "utenti_dipartimenti";
		}
		if(model.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_RESPONSABILE)){
			return "utenti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
