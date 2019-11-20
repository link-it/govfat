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
package org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch;

import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.jdbc.utils.AbstractJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCParameterUtilities;
import org.openspcoop2.generic_project.exception.ServiceException;

import java.sql.ResultSet;
import java.util.Map;

import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.jdbc.IKeyGeneratorObject;

import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;


/**     
 * UtenteFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class UtenteFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(Utente.model())){
				Utente object = new Utente();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setUsername", Utente.model().USERNAME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "username", Utente.model().USERNAME.getFieldType()));
				setParameter(object, "setPassword", Utente.model().PASSWORD.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "password", Utente.model().PASSWORD.getFieldType()));
				setParameter(object, "setNome", Utente.model().NOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome", Utente.model().NOME.getFieldType()));
				setParameter(object, "setCognome", Utente.model().COGNOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cognome", Utente.model().COGNOME.getFieldType()));
				setParameter(object, "set_value_role", String.class,
					jdbcParameterUtilities.readParameter(rs, "role", Utente.model().ROLE.getFieldType())+"");
				setParameter(object, "set_value_tipo", String.class,
					jdbcParameterUtilities.readParameter(rs, "tipo", Utente.model().TIPO.getFieldType())+"");
				setParameter(object, "setEsterno", Utente.model().ESTERNO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "esterno", Utente.model().ESTERNO.getFieldType()));
				setParameter(object, "setSistema", Utente.model().SISTEMA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "sistema", Utente.model().SISTEMA.getFieldType()));
				return object;
			}
			if(model.equals(Utente.model().UTENTE_DIPARTIMENTO)){
				UtenteDipartimento object = new UtenteDipartimento();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setDataUltimaModifica", Utente.model().UTENTE_DIPARTIMENTO.DATA_ULTIMA_MODIFICA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ultima_modifica", Utente.model().UTENTE_DIPARTIMENTO.DATA_ULTIMA_MODIFICA.getFieldType()));
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

			if(model.equals(Utente.model())){
				Utente object = new Utente();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setUsername", Utente.model().USERNAME.getFieldType(),
					this.getObjectFromMap(map,"username"));
				setParameter(object, "setPassword", Utente.model().PASSWORD.getFieldType(),
					this.getObjectFromMap(map,"password"));
				setParameter(object, "setNome", Utente.model().NOME.getFieldType(),
					this.getObjectFromMap(map,"nome"));
				setParameter(object, "setCognome", Utente.model().COGNOME.getFieldType(),
					this.getObjectFromMap(map,"cognome"));
				setParameter(object, "set_value_role", String.class,
					this.getObjectFromMap(map,"role"));
				setParameter(object, "set_value_tipo", String.class,
					this.getObjectFromMap(map,"tipo"));
				setParameter(object, "setEsterno", Utente.model().ESTERNO.getFieldType(),
					this.getObjectFromMap(map,"esterno"));
				setParameter(object, "setSistema", Utente.model().SISTEMA.getFieldType(),
					this.getObjectFromMap(map,"sistema"));
				return object;
			}
			if(model.equals(Utente.model().UTENTE_DIPARTIMENTO)){
				UtenteDipartimento object = new UtenteDipartimento();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"UtenteDipartimento.id"));
				setParameter(object, "setDataUltimaModifica", Utente.model().UTENTE_DIPARTIMENTO.DATA_ULTIMA_MODIFICA.getFieldType(),
					this.getObjectFromMap(map,"UtenteDipartimento.dataUltimaModifica"));
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

			if(model.equals(Utente.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("utenti","id","seq_utenti","utenti_init_seq");
			}
			if(model.equals(Utente.model().UTENTE_DIPARTIMENTO)){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("utenti_dipartimenti","id","seq_utenti_dipartimenti","utenti_dipartimenti_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
