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

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;


/**     
 * AllegatoFatturaFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class AllegatoFatturaFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(AllegatoFattura.model())){
				AllegatoFattura object = new AllegatoFattura();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setNomeAttachment", AllegatoFattura.model().NOME_ATTACHMENT.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome_attachment", AllegatoFattura.model().NOME_ATTACHMENT.getFieldType()));
				setParameter(object, "setAlgoritmoCompressione", AllegatoFattura.model().ALGORITMO_COMPRESSIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "algoritmo_compressione", AllegatoFattura.model().ALGORITMO_COMPRESSIONE.getFieldType()));
				setParameter(object, "setFormatoAttachment", AllegatoFattura.model().FORMATO_ATTACHMENT.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "formato_attachment", AllegatoFattura.model().FORMATO_ATTACHMENT.getFieldType()));
				setParameter(object, "setDescrizioneAttachment", AllegatoFattura.model().DESCRIZIONE_ATTACHMENT.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione_attachment", AllegatoFattura.model().DESCRIZIONE_ATTACHMENT.getFieldType()));
				setParameter(object, "setAttachment", AllegatoFattura.model().ATTACHMENT.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "attachment", AllegatoFattura.model().ATTACHMENT.getFieldType()));
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

			if(model.equals(AllegatoFattura.model())){
				AllegatoFattura object = new AllegatoFattura();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setNomeAttachment", AllegatoFattura.model().NOME_ATTACHMENT.getFieldType(),
					this.getObjectFromMap(map,"nomeAttachment"));
				setParameter(object, "setAlgoritmoCompressione", AllegatoFattura.model().ALGORITMO_COMPRESSIONE.getFieldType(),
					this.getObjectFromMap(map,"algoritmoCompressione"));
				setParameter(object, "setFormatoAttachment", AllegatoFattura.model().FORMATO_ATTACHMENT.getFieldType(),
					this.getObjectFromMap(map,"formatoAttachment"));
				setParameter(object, "setDescrizioneAttachment", AllegatoFattura.model().DESCRIZIONE_ATTACHMENT.getFieldType(),
					this.getObjectFromMap(map,"descrizioneAttachment"));
				setParameter(object, "setAttachment", AllegatoFattura.model().ATTACHMENT.getFieldType(),
					this.getObjectFromMap(map,"attachment"));
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

			if(model.equals(AllegatoFattura.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("allegati","id","seq_allegati","allegati_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
