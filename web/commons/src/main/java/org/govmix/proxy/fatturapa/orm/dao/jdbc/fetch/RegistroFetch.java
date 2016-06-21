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

import org.govmix.proxy.fatturapa.orm.IdProtocollo;
import org.govmix.proxy.fatturapa.orm.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.orm.Registro;


/**     
 * RegistroFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class RegistroFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(Registro.model())){
				Registro object = new Registro();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setNome", Registro.model().NOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome", Registro.model().NOME.getFieldType()));
				setParameter(object, "setUsername", Registro.model().USERNAME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "username", Registro.model().USERNAME.getFieldType()));
				setParameter(object, "setPassword", Registro.model().PASSWORD.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "password", Registro.model().PASSWORD.getFieldType()));
				return object;
			}
			if(model.equals(Registro.model().REGISTRO_PROPERTY_VALUE)){
				RegistroPropertyValue object = new RegistroPropertyValue();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setValore", Registro.model().REGISTRO_PROPERTY_VALUE.VALORE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "valore", Registro.model().REGISTRO_PROPERTY_VALUE.VALORE.getFieldType()));
				return object;
			}
			if(model.equals(Registro.model().ID_PROTOCOLLO)){
				IdProtocollo object = new IdProtocollo();
				setParameter(object, "setNome", Registro.model().ID_PROTOCOLLO.NOME.getFieldType(),
						jdbcParameterUtilities.readParameter(rs, "nome", Registro.model().ID_PROTOCOLLO.NOME.getFieldType()));
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

			if(model.equals(Registro.model())){
				Registro object = new Registro();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setNome", Registro.model().NOME.getFieldType(),
					this.getObjectFromMap(map,"nome"));
				setParameter(object, "setUsername", Registro.model().USERNAME.getFieldType(),
					this.getObjectFromMap(map,"username"));
				setParameter(object, "setPassword", Registro.model().PASSWORD.getFieldType(),
					this.getObjectFromMap(map,"password"));
				return object;
			}
			if(model.equals(Registro.model().REGISTRO_PROPERTY_VALUE)){
				RegistroPropertyValue object = new RegistroPropertyValue();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"RegistroPropertyValue.id"));
				setParameter(object, "setValore", Registro.model().REGISTRO_PROPERTY_VALUE.VALORE.getFieldType(),
					this.getObjectFromMap(map,"RegistroPropertyValue.valore"));
				return object;
			}
			if(model.equals(Registro.model().ID_PROTOCOLLO)){
				IdProtocollo object = new IdProtocollo();
				setParameter(object, "setNome", Registro.model().ID_PROTOCOLLO.NOME.getFieldType(),
					this.getObjectFromMap(map,"idProtocollo.nome"));
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

			if(model.equals(Registro.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("registri","id","seq_registri","registri_init_seq");
			}
			if(model.equals(Registro.model().REGISTRO_PROPERTY_VALUE)){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("registri_prop_values","id","seq_registri_prop_values","registri_prop_values_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
