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

import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;


/**     
 * TracciaSDIFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TracciaSDIFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(TracciaSDI.model())){
				TracciaSDI object = new TracciaSDI();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setIdentificativoSdi", TracciaSDI.model().IDENTIFICATIVO_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "identificativo_sdi", TracciaSDI.model().IDENTIFICATIVO_SDI.getFieldType()));
				setParameter(object, "set_value_tipoComunicazione", String.class,
					jdbcParameterUtilities.readParameter(rs, "tipo_comunicazione", TracciaSDI.model().TIPO_COMUNICAZIONE.getFieldType())+"");
				setParameter(object, "setNomeFile", TracciaSDI.model().NOME_FILE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome_file", TracciaSDI.model().NOME_FILE.getFieldType()));
				setParameter(object, "setData", TracciaSDI.model().DATA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data", TracciaSDI.model().DATA.getFieldType()));
				setParameter(object, "setIdEgov", TracciaSDI.model().ID_EGOV.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_egov", TracciaSDI.model().ID_EGOV.getFieldType()));
				setParameter(object, "setContentType", TracciaSDI.model().CONTENT_TYPE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "content_type", TracciaSDI.model().CONTENT_TYPE.getFieldType()));
				setParameter(object, "setRawData", TracciaSDI.model().RAW_DATA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "raw_data", TracciaSDI.model().RAW_DATA.getFieldType()));
				setParameter(object, "set_value_statoProtocollazione", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_protocollazione", TracciaSDI.model().STATO_PROTOCOLLAZIONE.getFieldType())+"");
				setParameter(object, "setDataProtocollazione", TracciaSDI.model().DATA_PROTOCOLLAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_protocollazione", TracciaSDI.model().DATA_PROTOCOLLAZIONE.getFieldType()));
				setParameter(object, "setDataProssimaProtocollazione", TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_prossima_protocollazione", TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE.getFieldType()));
				setParameter(object, "setTentativiProtocollazione", TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "tentativi_protocollazione", TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE.getFieldType()));
				setParameter(object, "setDettaglioProtocollazione", TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "dettaglio_protocollazione", TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE.getFieldType()));
				return object;
			}
			if(model.equals(TracciaSDI.model().METADATO)){
				Metadato object = new Metadato();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setRichiesta", TracciaSDI.model().METADATO.RICHIESTA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "richiesta", TracciaSDI.model().METADATO.RICHIESTA.getFieldType()));
				setParameter(object, "setNome", TracciaSDI.model().METADATO.NOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome", TracciaSDI.model().METADATO.NOME.getFieldType()));
				setParameter(object, "setValore", TracciaSDI.model().METADATO.VALORE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "valore", TracciaSDI.model().METADATO.VALORE.getFieldType()));
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

			if(model.equals(TracciaSDI.model())){
				TracciaSDI object = new TracciaSDI();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setIdentificativoSdi", TracciaSDI.model().IDENTIFICATIVO_SDI.getFieldType(),
					this.getObjectFromMap(map,"identificativoSdi"));
				setParameter(object, "set_value_tipoComunicazione", String.class,
					this.getObjectFromMap(map,"tipoComunicazione"));
				setParameter(object, "setNomeFile", TracciaSDI.model().NOME_FILE.getFieldType(),
					this.getObjectFromMap(map,"nomeFile"));
				setParameter(object, "setData", TracciaSDI.model().DATA.getFieldType(),
					this.getObjectFromMap(map,"data"));
				setParameter(object, "setIdEgov", TracciaSDI.model().ID_EGOV.getFieldType(),
					this.getObjectFromMap(map,"idEgov"));
				setParameter(object, "setContentType", TracciaSDI.model().CONTENT_TYPE.getFieldType(),
					this.getObjectFromMap(map,"contentType"));
				setParameter(object, "setRawData", TracciaSDI.model().RAW_DATA.getFieldType(),
					this.getObjectFromMap(map,"rawData"));
				setParameter(object, "set_value_statoProtocollazione", String.class,
					this.getObjectFromMap(map,"statoProtocollazione"));
				setParameter(object, "setDataProtocollazione", TracciaSDI.model().DATA_PROTOCOLLAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataProtocollazione"));
				setParameter(object, "setDataProssimaProtocollazione", TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataProssimaProtocollazione"));
				setParameter(object, "setTentativiProtocollazione", TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE.getFieldType(),
					this.getObjectFromMap(map,"tentativiProtocollazione"));
				setParameter(object, "setDettaglioProtocollazione", TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dettaglioProtocollazione"));
				return object;
			}
			if(model.equals(TracciaSDI.model().METADATO)){
				Metadato object = new Metadato();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"Metadato.id"));
				setParameter(object, "setRichiesta", TracciaSDI.model().METADATO.RICHIESTA.getFieldType(),
					this.getObjectFromMap(map,"Metadato.richiesta"));
				setParameter(object, "setNome", TracciaSDI.model().METADATO.NOME.getFieldType(),
					this.getObjectFromMap(map,"Metadato.nome"));
				setParameter(object, "setValore", TracciaSDI.model().METADATO.VALORE.getFieldType(),
					this.getObjectFromMap(map,"Metadato.valore"));
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

			if(model.equals(TracciaSDI.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("tracce_sdi","id","seq_tracce_sdi","tracce_sdi_init_seq");
			}
			if(model.equals(TracciaSDI.model().METADATO)){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("metadati","id","seq_metadati","metadati_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
