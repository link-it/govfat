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

import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;


/**     
 * PccErroreElaborazioneFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccErroreElaborazioneFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(PccErroreElaborazione.model())){
				PccErroreElaborazione object = new PccErroreElaborazione();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "set_value_tipoOperazione", String.class,
					jdbcParameterUtilities.readParameter(rs, "tipo_operazione", PccErroreElaborazione.model().TIPO_OPERAZIONE.getFieldType())+"");
				setParameter(object, "setProgressivoOperazione", PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "progressivo_operazione", PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE.getFieldType()));
				setParameter(object, "setCodiceEsito", PccErroreElaborazione.model().CODICE_ESITO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_esito", PccErroreElaborazione.model().CODICE_ESITO.getFieldType()));
				setParameter(object, "setDescrizioneEsito", PccErroreElaborazione.model().DESCRIZIONE_ESITO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione_esito", PccErroreElaborazione.model().DESCRIZIONE_ESITO.getFieldType()));
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

			if(model.equals(PccErroreElaborazione.model())){
				PccErroreElaborazione object = new PccErroreElaborazione();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "set_value_tipoOperazione", String.class,
					this.getObjectFromMap(map,"tipoOperazione"));
				setParameter(object, "setProgressivoOperazione", PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE.getFieldType(),
					this.getObjectFromMap(map,"progressivoOperazione"));
				setParameter(object, "setCodiceEsito", PccErroreElaborazione.model().CODICE_ESITO.getFieldType(),
					this.getObjectFromMap(map,"codiceEsito"));
				setParameter(object, "setDescrizioneEsito", PccErroreElaborazione.model().DESCRIZIONE_ESITO.getFieldType(),
					this.getObjectFromMap(map,"descrizioneEsito"));
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

			if(model.equals(PccErroreElaborazione.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("pcc_errori_elaborazione","id","seq_pcc_errori_elaborazione","pcc_errori_elaborazione_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
