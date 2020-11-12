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
package org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch;

import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.jdbc.utils.AbstractJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCParameterUtilities;
import org.openspcoop2.generic_project.exception.ServiceException;

import java.sql.ResultSet;
import java.util.Map;

import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.jdbc.IKeyGeneratorObject;

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;


/**     
 * PccTracciaTrasmissioneFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccTracciaTrasmissioneFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(PccTracciaTrasmissione.model())){
				PccTracciaTrasmissione object = new PccTracciaTrasmissione();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setTsTrasmissione", PccTracciaTrasmissione.model().TS_TRASMISSIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "ts_trasmissione", PccTracciaTrasmissione.model().TS_TRASMISSIONE.getFieldType()));
				setParameter(object, "setIdPccTransazione", PccTracciaTrasmissione.model().ID_PCC_TRANSAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_pcc_transazione", PccTracciaTrasmissione.model().ID_PCC_TRANSAZIONE.getFieldType()));
				setParameter(object, "set_value_esitoTrasmissione", String.class,
					jdbcParameterUtilities.readParameter(rs, "esito_trasmissione", PccTracciaTrasmissione.model().ESITO_TRASMISSIONE.getFieldType())+"");
				setParameter(object, "set_value_statoEsito", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_esito", PccTracciaTrasmissione.model().STATO_ESITO.getFieldType())+"");
				setParameter(object, "setGdo", PccTracciaTrasmissione.model().GDO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "gdo", PccTracciaTrasmissione.model().GDO.getFieldType()));
				setParameter(object, "setDataFineElaborazione", PccTracciaTrasmissione.model().DATA_FINE_ELABORAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_fine_elaborazione", PccTracciaTrasmissione.model().DATA_FINE_ELABORAZIONE.getFieldType()));
				setParameter(object, "setDettaglioErroreTrasmissione", PccTracciaTrasmissione.model().DETTAGLIO_ERRORE_TRASMISSIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "dettaglio_errore_trasmissione", PccTracciaTrasmissione.model().DETTAGLIO_ERRORE_TRASMISSIONE.getFieldType()));
				setParameter(object, "setIdEgovRichiesta", PccTracciaTrasmissione.model().ID_EGOV_RICHIESTA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_egov_richiesta", PccTracciaTrasmissione.model().ID_EGOV_RICHIESTA.getFieldType()));
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

			if(model.equals(PccTracciaTrasmissione.model())){
				PccTracciaTrasmissione object = new PccTracciaTrasmissione();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setTsTrasmissione", PccTracciaTrasmissione.model().TS_TRASMISSIONE.getFieldType(),
					this.getObjectFromMap(map,"tsTrasmissione"));
				setParameter(object, "setIdPccTransazione", PccTracciaTrasmissione.model().ID_PCC_TRANSAZIONE.getFieldType(),
					this.getObjectFromMap(map,"idPccTransazione"));
				setParameter(object, "set_value_esitoTrasmissione", String.class,
					this.getObjectFromMap(map,"esitoTrasmissione"));
				setParameter(object, "set_value_statoEsito", String.class,
					this.getObjectFromMap(map,"statoEsito"));
				setParameter(object, "setGdo", PccTracciaTrasmissione.model().GDO.getFieldType(),
					this.getObjectFromMap(map,"gdo"));
				setParameter(object, "setDataFineElaborazione", PccTracciaTrasmissione.model().DATA_FINE_ELABORAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataFineElaborazione"));
				setParameter(object, "setDettaglioErroreTrasmissione", PccTracciaTrasmissione.model().DETTAGLIO_ERRORE_TRASMISSIONE.getFieldType(),
					this.getObjectFromMap(map,"dettaglioErroreTrasmissione"));
				setParameter(object, "setIdEgovRichiesta", PccTracciaTrasmissione.model().ID_EGOV_RICHIESTA.getFieldType(),
					this.getObjectFromMap(map,"idEgovRichiesta"));
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

			if(model.equals(PccTracciaTrasmissione.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("pcc_tracce_trasmissioni","id","seq_pcc_tracce_trasmissioni","pcc_tracce_trasmissioni_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
