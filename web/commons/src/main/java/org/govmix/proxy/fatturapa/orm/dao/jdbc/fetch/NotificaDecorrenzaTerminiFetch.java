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

import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;


/**     
 * NotificaDecorrenzaTerminiFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class NotificaDecorrenzaTerminiFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(NotificaDecorrenzaTermini.model())){
				NotificaDecorrenzaTermini object = new NotificaDecorrenzaTermini();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setIdentificativoSdi", NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "identificativo_sdi", NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI.getFieldType()));
				setParameter(object, "setNomeFile", NotificaDecorrenzaTermini.model().NOME_FILE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome_file", NotificaDecorrenzaTermini.model().NOME_FILE.getFieldType()));
				setParameter(object, "setDescrizione", NotificaDecorrenzaTermini.model().DESCRIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione", NotificaDecorrenzaTermini.model().DESCRIZIONE.getFieldType()));
				setParameter(object, "setMessageId", NotificaDecorrenzaTermini.model().MESSAGE_ID.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "message_id", NotificaDecorrenzaTermini.model().MESSAGE_ID.getFieldType()));
				setParameter(object, "setNote", NotificaDecorrenzaTermini.model().NOTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "note", NotificaDecorrenzaTermini.model().NOTE.getFieldType()));
				setParameter(object, "setDataRicezione", NotificaDecorrenzaTermini.model().DATA_RICEZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ricezione", NotificaDecorrenzaTermini.model().DATA_RICEZIONE.getFieldType()));
				setParameter(object, "setXml", NotificaDecorrenzaTermini.model().XML.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "xml", NotificaDecorrenzaTermini.model().XML.getFieldType()));
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

			if(model.equals(NotificaDecorrenzaTermini.model())){
				NotificaDecorrenzaTermini object = new NotificaDecorrenzaTermini();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setIdentificativoSdi", NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI.getFieldType(),
					this.getObjectFromMap(map,"identificativoSdi"));
				setParameter(object, "setNomeFile", NotificaDecorrenzaTermini.model().NOME_FILE.getFieldType(),
					this.getObjectFromMap(map,"nomeFile"));
				setParameter(object, "setDescrizione", NotificaDecorrenzaTermini.model().DESCRIZIONE.getFieldType(),
					this.getObjectFromMap(map,"descrizione"));
				setParameter(object, "setMessageId", NotificaDecorrenzaTermini.model().MESSAGE_ID.getFieldType(),
					this.getObjectFromMap(map,"messageId"));
				setParameter(object, "setNote", NotificaDecorrenzaTermini.model().NOTE.getFieldType(),
					this.getObjectFromMap(map,"note"));
				setParameter(object, "setDataRicezione", NotificaDecorrenzaTermini.model().DATA_RICEZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataRicezione"));
				setParameter(object, "setXml", NotificaDecorrenzaTermini.model().XML.getFieldType(),
					this.getObjectFromMap(map,"xml"));
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

			if(model.equals(NotificaDecorrenzaTermini.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("decorrenza_termini","id","seq_decorrenza_termini","decorrenza_termini_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
