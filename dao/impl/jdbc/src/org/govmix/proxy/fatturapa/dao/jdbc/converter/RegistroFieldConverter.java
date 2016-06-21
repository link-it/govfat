/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.dao.jdbc.converter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.sql.AbstractSQLFieldConverter;
import org.openspcoop2.utils.TipiDatabase;

import org.govmix.proxy.fatturapa.Registro;


/**     
 * RegistroFieldConverter
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class RegistroFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public RegistroFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public RegistroFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return Registro.model();
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
		
		if(field.equals(Registro.model().NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Registro.model().USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(Registro.model().PASSWORD)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".password";
			}else{
				return "password";
			}
		}
		if(field.equals(Registro.model().ID_ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Registro.model().REGISTRO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
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
		
		if(field.equals(Registro.model().NOME)){
			return this.toTable(Registro.model(), returnAlias);
		}
		if(field.equals(Registro.model().USERNAME)){
			return this.toTable(Registro.model(), returnAlias);
		}
		if(field.equals(Registro.model().PASSWORD)){
			return this.toTable(Registro.model(), returnAlias);
		}
		if(field.equals(Registro.model().ID_ENTE.NOME)){
			return this.toTable(Registro.model().ID_ENTE, returnAlias);
		}
		if(field.equals(Registro.model().REGISTRO_PROPERTY_VALUE.VALORE)){
			return this.toTable(Registro.model().REGISTRO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
			return this.toTable(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(Registro.model())){
			return "registri";
		}
		if(model.equals(Registro.model().ID_ENTE)){
			return "enti";
		}
		if(model.equals(Registro.model().REGISTRO_PROPERTY_VALUE)){
			return "registri_prop_values";
		}
		if(model.equals(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY)){
			return "registri_props";
		}
		if(model.equals(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE)){
			return "id_ente";
		}


		return super.toTable(model,returnAlias);
		
	}

}
