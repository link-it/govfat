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

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;


/**     
 * AllegatoFatturaFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class AllegatoFatturaFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public AllegatoFatturaFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public AllegatoFatturaFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return AllegatoFattura.model();
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
		
		if(field.equals(AllegatoFattura.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(AllegatoFattura.model().ID_FATTURA.POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(AllegatoFattura.model().ID_FATTURA.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(AllegatoFattura.model().NOME_ATTACHMENT)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome_attachment";
			}else{
				return "nome_attachment";
			}
		}
		if(field.equals(AllegatoFattura.model().ALGORITMO_COMPRESSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".algoritmo_compressione";
			}else{
				return "algoritmo_compressione";
			}
		}
		if(field.equals(AllegatoFattura.model().FORMATO_ATTACHMENT)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".formato_attachment";
			}else{
				return "formato_attachment";
			}
		}
		if(field.equals(AllegatoFattura.model().DESCRIZIONE_ATTACHMENT)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione_attachment";
			}else{
				return "descrizione_attachment";
			}
		}
		if(field.equals(AllegatoFattura.model().ATTACHMENT)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".attachment";
			}else{
				return "attachment";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(AllegatoFattura.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			return this.toTable(AllegatoFattura.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(AllegatoFattura.model().ID_FATTURA.POSIZIONE)){
			return this.toTable(AllegatoFattura.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(AllegatoFattura.model().ID_FATTURA.FATTURAZIONE_ATTIVA)){
			return this.toTable(AllegatoFattura.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(AllegatoFattura.model().NOME_ATTACHMENT)){
			return this.toTable(AllegatoFattura.model(), returnAlias);
		}
		if(field.equals(AllegatoFattura.model().ALGORITMO_COMPRESSIONE)){
			return this.toTable(AllegatoFattura.model(), returnAlias);
		}
		if(field.equals(AllegatoFattura.model().FORMATO_ATTACHMENT)){
			return this.toTable(AllegatoFattura.model(), returnAlias);
		}
		if(field.equals(AllegatoFattura.model().DESCRIZIONE_ATTACHMENT)){
			return this.toTable(AllegatoFattura.model(), returnAlias);
		}
		if(field.equals(AllegatoFattura.model().ATTACHMENT)){
			return this.toTable(AllegatoFattura.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(AllegatoFattura.model())){
			return "allegati";
		}
		if(model.equals(AllegatoFattura.model().ID_FATTURA)){
			return "fatture";
		}


		return super.toTable(model,returnAlias);
		
	}

}
