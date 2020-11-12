/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.orm.Dipartimento;


/**     
 * DipartimentoFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DipartimentoFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public DipartimentoFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public DipartimentoFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return Dipartimento.model();
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
		
		if(field.equals(Dipartimento.model().CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(Dipartimento.model().ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Dipartimento.model().ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(Dipartimento.model().ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
			}
		}
		if(field.equals(Dipartimento.model().DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(Dipartimento.model().FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(Dipartimento.model().ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(Dipartimento.model().ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(Dipartimento.model().FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(Dipartimento.model().ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(Dipartimento.model().MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(Dipartimento.model().REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Dipartimento.model().REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(Dipartimento.model().REGISTRO.ID_PROTOCOLLO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Dipartimento.model().LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
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
		
		if(field.equals(Dipartimento.model().CODICE)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().ENTE.NOME)){
			return this.toTable(Dipartimento.model().ENTE, returnAlias);
		}
		if(field.equals(Dipartimento.model().ENTE.NODO_CODICE_PAGAMENTO)){
			return this.toTable(Dipartimento.model().ENTE, returnAlias);
		}
		if(field.equals(Dipartimento.model().ENTE.PREFISSO_CODICE_PAGAMENTO)){
			return this.toTable(Dipartimento.model().ENTE, returnAlias);
		}
		if(field.equals(Dipartimento.model().DESCRIZIONE)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().FATTURAZIONE_ATTIVA)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().ID_PROCEDIMENTO)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().ID_PROCEDIMENTO_B2B)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().FIRMA_AUTOMATICA)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().MODALITA_PUSH)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().REGISTRO.NOME)){
			return this.toTable(Dipartimento.model().REGISTRO, returnAlias);
		}
		if(field.equals(Dipartimento.model().REGISTRO.USERNAME)){
			return this.toTable(Dipartimento.model().REGISTRO, returnAlias);
		}
		if(field.equals(Dipartimento.model().REGISTRO.ID_PROTOCOLLO.NOME)){
			return this.toTable(Dipartimento.model().REGISTRO.ID_PROTOCOLLO, returnAlias);
		}
		if(field.equals(Dipartimento.model().LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(Dipartimento.model(), returnAlias);
		}
		if(field.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO.NOME)){
			return this.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(Dipartimento.model())){
			return "dipartimenti";
		}
		if(model.equals(Dipartimento.model().ENTE)){
			return "enti";
		}
		if(model.equals(Dipartimento.model().REGISTRO)){
			return "registri";
		}
		if(model.equals(Dipartimento.model().REGISTRO.ID_PROTOCOLLO)){
			return "protocolli";
		}
		if(model.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_PROTOCOLLO)){
			return "protocolli";
		}


		return super.toTable(model,returnAlias);
		
	}

}
