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
package org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch;

import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.jdbc.utils.AbstractJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCParameterUtilities;
import org.openspcoop2.generic_project.exception.ServiceException;

import java.sql.ResultSet;
import java.util.Map;

import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.jdbc.IKeyGeneratorObject;

import org.govmix.proxy.fatturapa.orm.PccScadenza;


/**     
 * PccScadenzaFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccScadenzaFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(PccScadenza.model())){
				PccScadenza object = new PccScadenza();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setImportoInScadenza", PccScadenza.model().IMPORTO_IN_SCADENZA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "importo_in_scadenza", PccScadenza.model().IMPORTO_IN_SCADENZA.getFieldType()));
				setParameter(object, "setImportoIniziale", PccScadenza.model().IMPORTO_INIZIALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "importo_iniziale", PccScadenza.model().IMPORTO_INIZIALE.getFieldType()));
				setParameter(object, "setPagatoRicontabilizzato", PccScadenza.model().PAGATO_RICONTABILIZZATO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "pagato_ricontabilizzato", PccScadenza.model().PAGATO_RICONTABILIZZATO.getFieldType()));
				setParameter(object, "setDataScadenza", PccScadenza.model().DATA_SCADENZA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_scadenza", PccScadenza.model().DATA_SCADENZA.getFieldType()));
				setParameter(object, "setSistemaRichiedente", PccScadenza.model().SISTEMA_RICHIEDENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "sistema_richiedente", PccScadenza.model().SISTEMA_RICHIEDENTE.getFieldType()));
				setParameter(object, "setUtenteRichiedente", PccScadenza.model().UTENTE_RICHIEDENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "utente_richiedente", PccScadenza.model().UTENTE_RICHIEDENTE.getFieldType()));
				setParameter(object, "setDataRichiesta", PccScadenza.model().DATA_RICHIESTA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_richiesta", PccScadenza.model().DATA_RICHIESTA.getFieldType()));
				setParameter(object, "setDataQuery", PccScadenza.model().DATA_QUERY.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_query", PccScadenza.model().DATA_QUERY.getFieldType()));
				return object;
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by fetch: "+this.getClass().getName());
			}	
					
		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in fetch: "+e.getMessage(),e);
		}
		
	}
	
	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , Map<String,Object> map ) throws ServiceException {
		
		try{

			if(model.equals(PccScadenza.model())){
				PccScadenza object = new PccScadenza();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setImportoInScadenza", PccScadenza.model().IMPORTO_IN_SCADENZA.getFieldType(),
					this.getObjectFromMap(map,"importoInScadenza"));
				setParameter(object, "setImportoIniziale", PccScadenza.model().IMPORTO_INIZIALE.getFieldType(),
					this.getObjectFromMap(map,"importoIniziale"));
				setParameter(object, "setPagatoRicontabilizzato", PccScadenza.model().PAGATO_RICONTABILIZZATO.getFieldType(),
					this.getObjectFromMap(map,"pagatoRicontabilizzato"));
				setParameter(object, "setDataScadenza", PccScadenza.model().DATA_SCADENZA.getFieldType(),
					this.getObjectFromMap(map,"dataScadenza"));
				setParameter(object, "setSistemaRichiedente", PccScadenza.model().SISTEMA_RICHIEDENTE.getFieldType(),
					this.getObjectFromMap(map,"sistemaRichiedente"));
				setParameter(object, "setUtenteRichiedente", PccScadenza.model().UTENTE_RICHIEDENTE.getFieldType(),
					this.getObjectFromMap(map,"utenteRichiedente"));
				setParameter(object, "setDataRichiesta", PccScadenza.model().DATA_RICHIESTA.getFieldType(),
					this.getObjectFromMap(map,"dataRichiesta"));
				setParameter(object, "setDataQuery", PccScadenza.model().DATA_QUERY.getFieldType(),
					this.getObjectFromMap(map,"dataQuery"));
				return object;
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by fetch: "+this.getClass().getName());
			}	
					
		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in fetch: "+e.getMessage(),e);
		}
		
	}
	
	
	@Override
	public IKeyGeneratorObject getKeyGeneratorObject( IModel<?> model )  throws ServiceException {
		
		try{

			if(model.equals(PccScadenza.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("pcc_scadenze","id","seq_pcc_scadenze","pcc_scadenze_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
