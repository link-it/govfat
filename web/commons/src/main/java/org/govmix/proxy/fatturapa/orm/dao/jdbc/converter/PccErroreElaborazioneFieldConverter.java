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

import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;


/**     
 * PccErroreElaborazioneFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccErroreElaborazioneFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public PccErroreElaborazioneFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public PccErroreElaborazioneFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return PccErroreElaborazione.model();
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
		
		if(field.equals(PccErroreElaborazione.model().ID_ESITO.ID_TRASMISSIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id";
			}else{
				return "id";
			}
		}
		if(field.equals(PccErroreElaborazione.model().TIPO_OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_operazione";
			}else{
				return "tipo_operazione";
			}
		}
		if(field.equals(PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".progressivo_operazione";
			}else{
				return "progressivo_operazione";
			}
		}
		if(field.equals(PccErroreElaborazione.model().CODICE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_esito";
			}else{
				return "codice_esito";
			}
		}
		if(field.equals(PccErroreElaborazione.model().DESCRIZIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione_esito";
			}else{
				return "descrizione_esito";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(PccErroreElaborazione.model().ID_ESITO.ID_TRASMISSIONE_ESITO)){
			return this.toTable(PccErroreElaborazione.model().ID_ESITO, returnAlias);
		}
		if(field.equals(PccErroreElaborazione.model().TIPO_OPERAZIONE)){
			return this.toTable(PccErroreElaborazione.model(), returnAlias);
		}
		if(field.equals(PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE)){
			return this.toTable(PccErroreElaborazione.model(), returnAlias);
		}
		if(field.equals(PccErroreElaborazione.model().CODICE_ESITO)){
			return this.toTable(PccErroreElaborazione.model(), returnAlias);
		}
		if(field.equals(PccErroreElaborazione.model().DESCRIZIONE_ESITO)){
			return this.toTable(PccErroreElaborazione.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(PccErroreElaborazione.model())){
			return "pcc_errori_elaborazione";
		}
		if(model.equals(PccErroreElaborazione.model().ID_ESITO)){
			return "tracce_trasmissioni_esiti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
