/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import org.govmix.proxy.fatturapa.orm.PccRispedizione;


/**     
 * PccRispedizioneFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccRispedizioneFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(PccRispedizione.model())){
				PccRispedizione object = new PccRispedizione();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setMaxNumeroTentativi", PccRispedizione.model().MAX_NUMERO_TENTATIVI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "max_numero_tentativi", PccRispedizione.model().MAX_NUMERO_TENTATIVI.getFieldType()));
				setParameter(object, "setIntervalloTentativi", PccRispedizione.model().INTERVALLO_TENTATIVI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "intervallo_tentativi", PccRispedizione.model().INTERVALLO_TENTATIVI.getFieldType()));
				setParameter(object, "setCodiceErrore", PccRispedizione.model().CODICE_ERRORE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_errore", PccRispedizione.model().CODICE_ERRORE.getFieldType()));
				setParameter(object, "setAbilitato", PccRispedizione.model().ABILITATO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "abilitato", PccRispedizione.model().ABILITATO.getFieldType()));
				setParameter(object, "setDescrizioneErrore", PccRispedizione.model().DESCRIZIONE_ERRORE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione_errore", PccRispedizione.model().DESCRIZIONE_ERRORE.getFieldType()));
				setParameter(object, "set_value_tipoErrore", String.class,
					jdbcParameterUtilities.readParameter(rs, "tipo_errore", PccRispedizione.model().TIPO_ERRORE.getFieldType())+"");
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

			if(model.equals(PccRispedizione.model())){
				PccRispedizione object = new PccRispedizione();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setMaxNumeroTentativi", PccRispedizione.model().MAX_NUMERO_TENTATIVI.getFieldType(),
					this.getObjectFromMap(map,"maxNumeroTentativi"));
				setParameter(object, "setIntervalloTentativi", PccRispedizione.model().INTERVALLO_TENTATIVI.getFieldType(),
					this.getObjectFromMap(map,"intervalloTentativi"));
				setParameter(object, "setCodiceErrore", PccRispedizione.model().CODICE_ERRORE.getFieldType(),
					this.getObjectFromMap(map,"codiceErrore"));
				setParameter(object, "setAbilitato", PccRispedizione.model().ABILITATO.getFieldType(),
					this.getObjectFromMap(map,"abilitato"));
				setParameter(object, "setDescrizioneErrore", PccRispedizione.model().DESCRIZIONE_ERRORE.getFieldType(),
					this.getObjectFromMap(map,"descrizioneErrore"));
				setParameter(object, "set_value_tipoErrore", String.class,
					this.getObjectFromMap(map,"tipoErrore"));
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

			if(model.equals(PccRispedizione.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("pcc_rispedizioni","id","seq_pcc_rispedizioni","pcc_rispedizioni_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
