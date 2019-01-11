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

import org.govmix.proxy.fatturapa.orm.PccRispedizione;


/**     
 * PccRispedizioneFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccRispedizioneFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public PccRispedizioneFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public PccRispedizioneFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return PccRispedizione.model();
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
		
		if(field.equals(PccRispedizione.model().MAX_NUMERO_TENTATIVI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".max_numero_tentativi";
			}else{
				return "max_numero_tentativi";
			}
		}
		if(field.equals(PccRispedizione.model().INTERVALLO_TENTATIVI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".intervallo_tentativi";
			}else{
				return "intervallo_tentativi";
			}
		}
		if(field.equals(PccRispedizione.model().CODICE_ERRORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_errore";
			}else{
				return "codice_errore";
			}
		}
		if(field.equals(PccRispedizione.model().ABILITATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".abilitato";
			}else{
				return "abilitato";
			}
		}
		if(field.equals(PccRispedizione.model().DESCRIZIONE_ERRORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione_errore";
			}else{
				return "descrizione_errore";
			}
		}
		if(field.equals(PccRispedizione.model().TIPO_ERRORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_errore";
			}else{
				return "tipo_errore";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(PccRispedizione.model().MAX_NUMERO_TENTATIVI)){
			return this.toTable(PccRispedizione.model(), returnAlias);
		}
		if(field.equals(PccRispedizione.model().INTERVALLO_TENTATIVI)){
			return this.toTable(PccRispedizione.model(), returnAlias);
		}
		if(field.equals(PccRispedizione.model().CODICE_ERRORE)){
			return this.toTable(PccRispedizione.model(), returnAlias);
		}
		if(field.equals(PccRispedizione.model().ABILITATO)){
			return this.toTable(PccRispedizione.model(), returnAlias);
		}
		if(field.equals(PccRispedizione.model().DESCRIZIONE_ERRORE)){
			return this.toTable(PccRispedizione.model(), returnAlias);
		}
		if(field.equals(PccRispedizione.model().TIPO_ERRORE)){
			return this.toTable(PccRispedizione.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(PccRispedizione.model())){
			return "pcc_rispedizioni";
		}


		return super.toTable(model,returnAlias);
		
	}

}
