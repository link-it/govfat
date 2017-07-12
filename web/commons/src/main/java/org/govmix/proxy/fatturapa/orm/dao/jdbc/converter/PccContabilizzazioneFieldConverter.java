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

import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;


/**     
 * PccContabilizzazioneFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccContabilizzazioneFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public PccContabilizzazioneFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public PccContabilizzazioneFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return PccContabilizzazione.model();
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
		
		if(field.equals(PccContabilizzazione.model().IMPORTO_MOVIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_movimento";
			}else{
				return "importo_movimento";
			}
		}
		if(field.equals(PccContabilizzazione.model().NATURA_SPESA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".natura_spesa";
			}else{
				return "natura_spesa";
			}
		}
		if(field.equals(PccContabilizzazione.model().CAPITOLI_SPESA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".capitoli_spesa";
			}else{
				return "capitoli_spesa";
			}
		}
		if(field.equals(PccContabilizzazione.model().STATO_DEBITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".stato_debito";
			}else{
				return "stato_debito";
			}
		}
		if(field.equals(PccContabilizzazione.model().CAUSALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".causale";
			}else{
				return "causale";
			}
		}
		if(field.equals(PccContabilizzazione.model().DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(PccContabilizzazione.model().ESTREMI_IMPEGNO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".estremi_impegno";
			}else{
				return "estremi_impegno";
			}
		}
		if(field.equals(PccContabilizzazione.model().CODICE_CIG)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_cig";
			}else{
				return "codice_cig";
			}
		}
		if(field.equals(PccContabilizzazione.model().CODICE_CUP)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_cup";
			}else{
				return "codice_cup";
			}
		}
		if(field.equals(PccContabilizzazione.model().ID_IMPORTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_importo";
			}else{
				return "id_importo";
			}
		}
		if(field.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(PccContabilizzazione.model().SISTEMA_RICHIEDENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".sistema_richiedente";
			}else{
				return "sistema_richiedente";
			}
		}
		if(field.equals(PccContabilizzazione.model().UTENTE_RICHIEDENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".utente_richiedente";
			}else{
				return "utente_richiedente";
			}
		}
		if(field.equals(PccContabilizzazione.model().DATA_RICHIESTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_richiesta";
			}else{
				return "data_richiesta";
			}
		}
		if(field.equals(PccContabilizzazione.model().DATA_QUERY)){
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
		
		if(field.equals(PccContabilizzazione.model().IMPORTO_MOVIMENTO)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().NATURA_SPESA)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().CAPITOLI_SPESA)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().STATO_DEBITO)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().CAUSALE)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().DESCRIZIONE)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().ESTREMI_IMPEGNO)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().CODICE_CIG)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().CODICE_CUP)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().ID_IMPORTO)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			return this.toTable(PccContabilizzazione.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE)){
			return this.toTable(PccContabilizzazione.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().SISTEMA_RICHIEDENTE)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().UTENTE_RICHIEDENTE)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().DATA_RICHIESTA)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}
		if(field.equals(PccContabilizzazione.model().DATA_QUERY)){
			return this.toTable(PccContabilizzazione.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(PccContabilizzazione.model())){
			return "pcc_contabilizzazioni";
		}
		if(model.equals(PccContabilizzazione.model().ID_FATTURA)){
			return "fatture";
		}


		return super.toTable(model,returnAlias);
		
	}

}
