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

import org.govmix.proxy.fatturapa.orm.TracciaSDI;


/**     
 * TracciaSDIFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TracciaSDIFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public TracciaSDIFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public TracciaSDIFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return TracciaSDI.model();
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
		
		if(field.equals(TracciaSDI.model().IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(TracciaSDI.model().TIPO_COMUNICAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_comunicazione";
			}else{
				return "tipo_comunicazione";
			}
		}
		if(field.equals(TracciaSDI.model().NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(TracciaSDI.model().DATA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data";
			}else{
				return "data";
			}
		}
		if(field.equals(TracciaSDI.model().ID_EGOV)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_egov";
			}else{
				return "id_egov";
			}
		}
		if(field.equals(TracciaSDI.model().RAW_DATA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".raw_data";
			}else{
				return "raw_data";
			}
		}
		if(field.equals(TracciaSDI.model().STATO_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_protocollazione";
			}else{
				return "stato_protocollazione";
			}
		}
		if(field.equals(TracciaSDI.model().DATA_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_protocollazione";
			}else{
				return "data_protocollazione";
			}
		}
		if(field.equals(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_prossima_protocollazione";
			}else{
				return "data_prossima_protocollazione";
			}
		}
		if(field.equals(TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tentativi_protocollazione";
			}else{
				return "tentativi_protocollazione";
			}
		}
		if(field.equals(TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_protocollazione";
			}else{
				return "dettaglio_protocollazione";
			}
		}
		if(field.equals(TracciaSDI.model().METADATO.RICHIESTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".richiesta";
			}else{
				return "richiesta";
			}
		}
		if(field.equals(TracciaSDI.model().METADATO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(TracciaSDI.model().METADATO.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(TracciaSDI.model().IDENTIFICATIVO_SDI)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().TIPO_COMUNICAZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().NOME_FILE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().DATA)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().ID_EGOV)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().RAW_DATA)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().STATO_PROTOCOLLAZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().DATA_PROTOCOLLAZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().METADATO.RICHIESTA)){
			return this.toTable(TracciaSDI.model().METADATO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().METADATO.NOME)){
			return this.toTable(TracciaSDI.model().METADATO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().METADATO.VALORE)){
			return this.toTable(TracciaSDI.model().METADATO, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(TracciaSDI.model())){
			return "tracce_sdi";
		}
		if(model.equals(TracciaSDI.model().METADATO)){
			return "metadati";
		}


		return super.toTable(model,returnAlias);
		
	}

}
