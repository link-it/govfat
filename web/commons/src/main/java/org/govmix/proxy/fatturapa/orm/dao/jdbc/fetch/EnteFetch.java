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
package org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch;

import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.jdbc.utils.AbstractJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCParameterUtilities;
import org.openspcoop2.generic_project.exception.ServiceException;

import java.sql.ResultSet;
import java.util.Map;

import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.jdbc.IKeyGeneratorObject;

import org.govmix.proxy.fatturapa.orm.Ente;


/**     
 * EnteFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class EnteFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(Ente.model())){
				Ente object = new Ente();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setNome", Ente.model().NOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome", Ente.model().NOME.getFieldType()));
				setParameter(object, "setIdPccAmministrazione", Ente.model().ID_PCC_AMMINISTRAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_pcc_amministrazione", Ente.model().ID_PCC_AMMINISTRAZIONE.getFieldType()));
				setParameter(object, "setCfAuth", Ente.model().CF_AUTH.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cf_auth", Ente.model().CF_AUTH.getFieldType()));
				setParameter(object, "setDescrizione", Ente.model().DESCRIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione", Ente.model().DESCRIZIONE.getFieldType()));
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

			if(model.equals(Ente.model())){
				Ente object = new Ente();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setNome", Ente.model().NOME.getFieldType(),
					this.getObjectFromMap(map,"nome"));
				setParameter(object, "setIdPccAmministrazione", Ente.model().ID_PCC_AMMINISTRAZIONE.getFieldType(),
					this.getObjectFromMap(map,"idPccAmministrazione"));
				setParameter(object, "setCfAuth", Ente.model().CF_AUTH.getFieldType(),
					this.getObjectFromMap(map,"cfAuth"));
				setParameter(object, "setDescrizione", Ente.model().DESCRIZIONE.getFieldType(),
					this.getObjectFromMap(map,"descrizione"));
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

			if(model.equals(Ente.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("enti","id","seq_enti","enti_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
