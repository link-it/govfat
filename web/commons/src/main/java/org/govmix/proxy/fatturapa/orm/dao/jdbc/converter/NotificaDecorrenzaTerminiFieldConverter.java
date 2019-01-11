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

import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;


/**     
 * NotificaDecorrenzaTerminiFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class NotificaDecorrenzaTerminiFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public NotificaDecorrenzaTerminiFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public NotificaDecorrenzaTerminiFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return NotificaDecorrenzaTermini.model();
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
		
		if(field.equals(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(NotificaDecorrenzaTermini.model().NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(NotificaDecorrenzaTermini.model().DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(NotificaDecorrenzaTermini.model().MESSAGE_ID)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".message_id";
			}else{
				return "message_id";
			}
		}
		if(field.equals(NotificaDecorrenzaTermini.model().NOTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".note";
			}else{
				return "note";
			}
		}
		if(field.equals(NotificaDecorrenzaTermini.model().DATA_RICEZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ricezione";
			}else{
				return "data_ricezione";
			}
		}
		if(field.equals(NotificaDecorrenzaTermini.model().XML)){
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
		
		if(field.equals(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI)){
			return this.toTable(NotificaDecorrenzaTermini.model(), returnAlias);
		}
		if(field.equals(NotificaDecorrenzaTermini.model().NOME_FILE)){
			return this.toTable(NotificaDecorrenzaTermini.model(), returnAlias);
		}
		if(field.equals(NotificaDecorrenzaTermini.model().DESCRIZIONE)){
			return this.toTable(NotificaDecorrenzaTermini.model(), returnAlias);
		}
		if(field.equals(NotificaDecorrenzaTermini.model().MESSAGE_ID)){
			return this.toTable(NotificaDecorrenzaTermini.model(), returnAlias);
		}
		if(field.equals(NotificaDecorrenzaTermini.model().NOTE)){
			return this.toTable(NotificaDecorrenzaTermini.model(), returnAlias);
		}
		if(field.equals(NotificaDecorrenzaTermini.model().DATA_RICEZIONE)){
			return this.toTable(NotificaDecorrenzaTermini.model(), returnAlias);
		}
		if(field.equals(NotificaDecorrenzaTermini.model().XML)){
			return this.toTable(NotificaDecorrenzaTermini.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(NotificaDecorrenzaTermini.model())){
			return "decorrenza_termini";
		}


		return super.toTable(model,returnAlias);
		
	}

}
