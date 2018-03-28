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

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;


/**     
 * PccTracciaTrasmissioneEsitoFieldConverter
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccTracciaTrasmissioneEsitoFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public PccTracciaTrasmissioneEsitoFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public PccTracciaTrasmissioneEsitoFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return PccTracciaTrasmissioneEsito.model();
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
		
		if(field.equals(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE.ID_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id";
			}else{
				return "id";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito_elaborazione";
			}else{
				return "esito_elaborazione";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().DESCRIZIONE_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione_elaborazione";
			}else{
				return "descrizione_elaborazione";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().DATA_FINE_ELABORAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data_fine_elaborazione";
			}else{
				return "data_fine_elaborazione";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().GDO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".gdo";
			}else{
				return "gdo";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().ESITO_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito_trasmissione";
			}else{
				return "esito_trasmissione";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().DETTAGLIO_ERRORE_TRASMISSIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dettaglio_errore_trasmissione";
			}else{
				return "dettaglio_errore_trasmissione";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().ID_EGOV_RICHIESTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_egov_richiesta";
			}else{
				return "id_egov_richiesta";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.ID_ESITO.ID_TRASMISSIONE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_trasmissione_esito";
			}else{
				return "id_trasmissione_esito";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.TIPO_OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_operazione";
			}else{
				return "tipo_operazione";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.PROGRESSIVO_OPERAZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".progressivo_operazione";
			}else{
				return "progressivo_operazione";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.CODICE_ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codice_esito";
			}else{
				return "codice_esito";
			}
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.DESCRIZIONE_ESITO)){
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
		
		if(field.equals(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE.ID_TRASMISSIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE, returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model(), returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().DESCRIZIONE_ELABORAZIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model(), returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().DATA_FINE_ELABORAZIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model(), returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().GDO)){
			return this.toTable(PccTracciaTrasmissioneEsito.model(), returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().ESITO_TRASMISSIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model(), returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().DETTAGLIO_ERRORE_TRASMISSIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model(), returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().ID_EGOV_RICHIESTA)){
			return this.toTable(PccTracciaTrasmissioneEsito.model(), returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.ID_ESITO.ID_TRASMISSIONE_ESITO)){
			return this.toTable(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.ID_ESITO, returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.TIPO_OPERAZIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE, returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.PROGRESSIVO_OPERAZIONE)){
			return this.toTable(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE, returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.CODICE_ESITO)){
			return this.toTable(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE, returnAlias);
		}
		if(field.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.DESCRIZIONE_ESITO)){
			return this.toTable(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(PccTracciaTrasmissioneEsito.model())){
			return "tracce_trasmissioni_esiti";
		}
		if(model.equals(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE)){
			return "pcc_tracce_trasmissioni";
		}
		if(model.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE)){
			return "pcc_errori_elaborazione";
		}
		if(model.equals(PccTracciaTrasmissioneEsito.model().PCC_ERRORE_ELABORAZIONE.ID_ESITO)){
			return "tracce_trasmissioni_esiti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
