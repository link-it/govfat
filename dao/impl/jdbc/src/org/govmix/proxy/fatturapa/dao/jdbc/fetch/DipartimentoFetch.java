/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.dao.jdbc.fetch;

import java.sql.ResultSet;
import java.util.Map;

import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.IdEnte;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.jdbc.utils.AbstractJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCParameterUtilities;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.jdbc.IKeyGeneratorObject;


/**     
 * DipartimentoFetch
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DipartimentoFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(Dipartimento.model())){
				Dipartimento object = new Dipartimento();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setCodice", Dipartimento.model().CODICE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice", Dipartimento.model().CODICE.getFieldType()));
				setParameter(object, "setDescrizione", Dipartimento.model().DESCRIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione", Dipartimento.model().DESCRIZIONE.getFieldType()));
				setParameter(object, "setAccettazioneAutomatica", Dipartimento.model().ACCETTAZIONE_AUTOMATICA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "accettazione_automatica", Dipartimento.model().ACCETTAZIONE_AUTOMATICA.getFieldType()));
				setParameter(object, "setModalitaPush", Dipartimento.model().MODALITA_PUSH.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "modalita_push", Dipartimento.model().MODALITA_PUSH.getFieldType()));
				return object;
			}
			if(model.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE)){
				DipartimentoPropertyValue object = new DipartimentoPropertyValue();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setValore", Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "valore", Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE.getFieldType()));
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

			if(model.equals(Dipartimento.model())){
				Dipartimento object = new Dipartimento();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setCodice", Dipartimento.model().CODICE.getFieldType(),
					this.getObjectFromMap(map,"codice"));
				setParameter(object, "setDescrizione", Dipartimento.model().DESCRIZIONE.getFieldType(),
					this.getObjectFromMap(map,"descrizione"));
				setParameter(object, "setAccettazioneAutomatica", Dipartimento.model().ACCETTAZIONE_AUTOMATICA.getFieldType(),
					this.getObjectFromMap(map,"accettazioneAutomatica"));
				setParameter(object, "setModalitaPush", Dipartimento.model().MODALITA_PUSH.getFieldType(),
					this.getObjectFromMap(map,"modalitaPush"));
				return object;
			}
			if(model.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE)){
				DipartimentoPropertyValue object = new DipartimentoPropertyValue();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"DipartimentoPropertyValue.id"));
				setParameter(object, "setValore", Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE.getFieldType(),
					this.getObjectFromMap(map,"DipartimentoPropertyValue.valore"));
				return object;
			}
			if(model.equals(Dipartimento.model().ENTE)){
				IdEnte object = new IdEnte();
				setParameter(object, "setNome", Dipartimento.model().ENTE.NOME.getFieldType(),
					this.getObjectFromMap(map,"ente.nome"));
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

			if(model.equals(Dipartimento.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("dipartimenti","id","seq_dipartimenti","dipartimenti_init_seq");
			}
			if(model.equals(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE)){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("dipartimenti_prop_values","id","seq_dipartimenti_prop_values","dipartimenti_prop_values_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
