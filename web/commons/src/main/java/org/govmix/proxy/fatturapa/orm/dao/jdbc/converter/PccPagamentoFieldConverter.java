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

import org.govmix.proxy.fatturapa.orm.PccPagamento;


/**     
 * PccPagamentoFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccPagamentoFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public PccPagamentoFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public PccPagamentoFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return PccPagamento.model();
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
		
		if(field.equals(PccPagamento.model().IMPORTO_PAGATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".importo_pagato";
			}else{
				return "importo_pagato";
			}
		}
		if(field.equals(PccPagamento.model().NATURA_SPESA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".natura_spesa";
			}else{
				return "natura_spesa";
			}
		}
		if(field.equals(PccPagamento.model().CAPITOLI_SPESA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".capitoli_spesa";
			}else{
				return "capitoli_spesa";
			}
		}
		if(field.equals(PccPagamento.model().ESTREMI_IMPEGNO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".estremi_impegno";
			}else{
				return "estremi_impegno";
			}
		}
		if(field.equals(PccPagamento.model().NUMERO_MANDATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".numero_mandato";
			}else{
				return "numero_mandato";
			}
		}
		if(field.equals(PccPagamento.model().DATA_MANDATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_mandato";
			}else{
				return "data_mandato";
			}
		}
		if(field.equals(PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_fiscale_iva_beneficiario";
			}else{
				return "id_fiscale_iva_beneficiario";
			}
		}
		if(field.equals(PccPagamento.model().CODICE_CIG)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_cig";
			}else{
				return "codice_cig";
			}
		}
		if(field.equals(PccPagamento.model().CODICE_CUP)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_cup";
			}else{
				return "codice_cup";
			}
		}
		if(field.equals(PccPagamento.model().DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".identificativo_sdi";
			}else{
				return "identificativo_sdi";
			}
		}
		if(field.equals(PccPagamento.model().ID_FATTURA.POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
			}
		}
		if(field.equals(PccPagamento.model().DATA_RICHIESTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_richiesta";
			}else{
				return "data_richiesta";
			}
		}
		if(field.equals(PccPagamento.model().DATA_QUERY)){
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
		
		if(field.equals(PccPagamento.model().IMPORTO_PAGATO)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().NATURA_SPESA)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().CAPITOLI_SPESA)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().ESTREMI_IMPEGNO)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().NUMERO_MANDATO)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().DATA_MANDATO)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().CODICE_CIG)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().CODICE_CUP)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().DESCRIZIONE)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI)){
			return this.toTable(PccPagamento.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(PccPagamento.model().ID_FATTURA.POSIZIONE)){
			return this.toTable(PccPagamento.model().ID_FATTURA, returnAlias);
		}
		if(field.equals(PccPagamento.model().DATA_RICHIESTA)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}
		if(field.equals(PccPagamento.model().DATA_QUERY)){
			return this.toTable(PccPagamento.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(PccPagamento.model())){
			return "pcc_pagamenti";
		}
		if(model.equals(PccPagamento.model().ID_FATTURA)){
			return "fatture";
		}


		return super.toTable(model,returnAlias);
		
	}

}
