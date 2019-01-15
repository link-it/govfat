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
		if(field.equals(TracciaSDI.model().POSIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".posizione";
			}else{
				return "posizione";
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
		if(field.equals(TracciaSDI.model().CODICE_DIPARTIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_dipartimento";
			}else{
				return "codice_dipartimento";
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
		if(field.equals(TracciaSDI.model().CONTENT_TYPE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".content_type";
			}else{
				return "content_type";
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
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.CODICE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice";
			}else{
				return "codice";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".fatturazione_attiva";
			}else{
				return "fatturazione_attiva";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento";
			}else{
				return "id_procedimento";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_procedimento_b2b";
			}else{
				return "id_procedimento_b2b";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".firma_automatica";
			}else{
				return "firma_automatica";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".accettazione_automatica";
			}else{
				return "accettazione_automatica";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.MODALITA_PUSH)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".modalita_push";
			}else{
				return "modalita_push";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.REGISTRO.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".username";
			}else{
				return "username";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".lista_email_notifiche";
			}else{
				return "lista_email_notifiche";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".valore";
			}else{
				return "valore";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nome";
			}else{
				return "nome";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NODO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".nodo_codice_pagamento";
			}else{
				return "nodo_codice_pagamento";
			}
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.PREFISSO_CODICE_PAGAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".prefisso_codice_pagamento";
			}else{
				return "prefisso_codice_pagamento";
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
		if(field.equals(TracciaSDI.model().POSIZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().TIPO_COMUNICAZIONE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().NOME_FILE)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().CODICE_DIPARTIMENTO)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().DATA)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().ID_EGOV)){
			return this.toTable(TracciaSDI.model(), returnAlias);
		}
		if(field.equals(TracciaSDI.model().CONTENT_TYPE)){
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
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.CODICE)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ENTE.NOME)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ENTE.NODO_CODICE_PAGAMENTO)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ENTE.PREFISSO_CODICE_PAGAMENTO)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.ENTE, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DESCRIZIONE)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.FATTURAZIONE_ATTIVA)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ID_PROCEDIMENTO)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ID_PROCEDIMENTO_B2B)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.FIRMA_AUTOMATICA)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.MODALITA_PUSH)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.REGISTRO.NOME)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.REGISTRO.USERNAME)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.REGISTRO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.LISTA_EMAIL_NOTIFICHE)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.VALORE)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.NOME)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NOME)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.NODO_CODICE_PAGAMENTO)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE, returnAlias);
		}
		if(field.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE.PREFISSO_CODICE_PAGAMENTO)){
			return this.toTable(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE, returnAlias);
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
		if(model.equals(TracciaSDI.model().DIPARTIMENTO)){
			return "dipartimenti";
		}
		if(model.equals(TracciaSDI.model().DIPARTIMENTO.ENTE)){
			return "enti";
		}
		if(model.equals(TracciaSDI.model().DIPARTIMENTO.REGISTRO)){
			return "registri";
		}
		if(model.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE)){
			return "dipartimenti_prop_values";
		}
		if(model.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY)){
			return "dipartimenti_props";
		}
		if(model.equals(TracciaSDI.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE)){
			return "enti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
