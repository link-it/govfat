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

import org.govmix.proxy.fatturapa.Ente;


/**     
 * EnteFieldConverter
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class EnteFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public EnteFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public EnteFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return Ente.model();
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
		
		if(field.equals(Ente.model().NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Ente.model().DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(Ente.model().ENDPOINT)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".endpoint";
			}else{
				return "endpoint";
			}
		}
		if(field.equals(Ente.model().ENDPOINT_CONSEGNA_LOTTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".endpoint_consegna_lotto";
			}else{
				return "endpoint_consegna_lotto";
			}
		}
		if(field.equals(Ente.model().ENDPOINT_RICHIEDI_PROTOCOLLO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".endpoint_richiedi_protocollo";
			}else{
				return "endpoint_richiedi_protocollo";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(Ente.model().NOME)){
			return this.toTable(Ente.model(), returnAlias);
		}
		if(field.equals(Ente.model().DESCRIZIONE)){
			return this.toTable(Ente.model(), returnAlias);
		}
		if(field.equals(Ente.model().ENDPOINT)){
			return this.toTable(Ente.model(), returnAlias);
		}
		if(field.equals(Ente.model().ENDPOINT_CONSEGNA_LOTTO)){
			return this.toTable(Ente.model(), returnAlias);
		}
		if(field.equals(Ente.model().ENDPOINT_RICHIEDI_PROTOCOLLO)){
			return this.toTable(Ente.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(Ente.model())){
			return "enti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
