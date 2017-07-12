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

import org.govmix.proxy.fatturapa.orm.ComunicazioneSdi;


/**     
 * ComunicazioneSdiFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ComunicazioneSdiFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public ComunicazioneSdiFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public ComunicazioneSdiFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return ComunicazioneSdi.model();
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
		
		if(field.equals(ComunicazioneSdi.model().IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(ComunicazioneSdi.model().TIPO_COMUNICAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_comunicazione";
			}else{
				return "tipo_comunicazione";
			}
		}
		if(field.equals(ComunicazioneSdi.model().PROGRESSIVO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".progressivo";
			}else{
				return "progressivo";
			}
		}
		if(field.equals(ComunicazioneSdi.model().DATA_RICEZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_ricezione";
			}else{
				return "data_ricezione";
			}
		}
		if(field.equals(ComunicazioneSdi.model().NOME_FILE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_file";
			}else{
				return "nome_file";
			}
		}
		if(field.equals(ComunicazioneSdi.model().CONTENT_TYPE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".content_type";
			}else{
				return "content_type";
			}
		}
		if(field.equals(ComunicazioneSdi.model().RAW_DATA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".raw_data";
			}else{
				return "raw_data";
			}
		}
		if(field.equals(ComunicazioneSdi.model().STATO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_consegna";
			}else{
				return "stato_consegna";
			}
		}
		if(field.equals(ComunicazioneSdi.model().DATA_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_consegna";
			}else{
				return "data_consegna";
			}
		}
		if(field.equals(ComunicazioneSdi.model().DETTAGLIO_CONSEGNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_consegna";
			}else{
				return "dettaglio_consegna";
			}
		}
		if(field.equals(ComunicazioneSdi.model().METADATO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(ComunicazioneSdi.model().METADATO.VALORE)){
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
		
		if(field.equals(ComunicazioneSdi.model().IDENTIFICATIVO_SDI)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().TIPO_COMUNICAZIONE)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().PROGRESSIVO)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().DATA_RICEZIONE)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().NOME_FILE)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().CONTENT_TYPE)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().RAW_DATA)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().STATO_CONSEGNA)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().DATA_CONSEGNA)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().DETTAGLIO_CONSEGNA)){
			return this.toTable(ComunicazioneSdi.model(), returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().METADATO.NOME)){
			return this.toTable(ComunicazioneSdi.model().METADATO, returnAlias);
		}
		if(field.equals(ComunicazioneSdi.model().METADATO.VALORE)){
			return this.toTable(ComunicazioneSdi.model().METADATO, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(ComunicazioneSdi.model())){
			return "comunicazioni_sdi";
		}
		if(model.equals(ComunicazioneSdi.model().METADATO)){
			return "metadati";
		}


		return super.toTable(model,returnAlias);
		
	}

}
