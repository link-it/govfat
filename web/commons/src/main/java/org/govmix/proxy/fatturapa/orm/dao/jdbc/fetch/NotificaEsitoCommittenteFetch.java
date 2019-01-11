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

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;


/**     
 * NotificaEsitoCommittenteFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class NotificaEsitoCommittenteFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(NotificaEsitoCommittente.model())){
				NotificaEsitoCommittente object = new NotificaEsitoCommittente();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setIdentificativoSdi", NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "identificativo_sdi", NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI.getFieldType()));
				setParameter(object, "setNumeroFattura", NotificaEsitoCommittente.model().NUMERO_FATTURA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "numero_fattura", NotificaEsitoCommittente.model().NUMERO_FATTURA.getFieldType()));
				setParameter(object, "setAnno", NotificaEsitoCommittente.model().ANNO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "anno", NotificaEsitoCommittente.model().ANNO.getFieldType()));
				setParameter(object, "setPosizione", NotificaEsitoCommittente.model().POSIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "posizione", NotificaEsitoCommittente.model().POSIZIONE.getFieldType(), org.openspcoop2.generic_project.dao.jdbc.utils.JDBCDefaultForXSDType.FORCE_ZERO_AS_NULL));
				setParameter(object, "set_value_esito", String.class,
					jdbcParameterUtilities.readParameter(rs, "esito", NotificaEsitoCommittente.model().ESITO.getFieldType())+"");
				setParameter(object, "setDescrizione", NotificaEsitoCommittente.model().DESCRIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione", NotificaEsitoCommittente.model().DESCRIZIONE.getFieldType()));
				setParameter(object, "setMessageIdCommittente", NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "message_id_committente", NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE.getFieldType()));
				setParameter(object, "setNomeFile", NotificaEsitoCommittente.model().NOME_FILE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome_file", NotificaEsitoCommittente.model().NOME_FILE.getFieldType()));
				setParameter(object, "setModalitaBatch", NotificaEsitoCommittente.model().MODALITA_BATCH.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "modalita_batch", NotificaEsitoCommittente.model().MODALITA_BATCH.getFieldType()));
				setParameter(object, "setDataInvioEnte", NotificaEsitoCommittente.model().DATA_INVIO_ENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_invio_ente", NotificaEsitoCommittente.model().DATA_INVIO_ENTE.getFieldType()));
				setParameter(object, "setDataInvioSdi", NotificaEsitoCommittente.model().DATA_INVIO_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_invio_sdi", NotificaEsitoCommittente.model().DATA_INVIO_SDI.getFieldType()));
				setParameter(object, "set_value_statoConsegnaSdi", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_consegna_sdi", NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI.getFieldType())+"");
				setParameter(object, "setDataUltimaConsegnaSdi", NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ultima_consegna_sdi", NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI.getFieldType()));
				setParameter(object, "setDataProssimaConsegnaSdi", NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_prossima_consegna_sdi", NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI.getFieldType()));
				setParameter(object, "setTentativiConsegnaSdi", NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "tentativi_consegna_sdi", NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI.getFieldType()));
				setParameter(object, "set_value_scarto", String.class,
					jdbcParameterUtilities.readParameter(rs, "scarto", NotificaEsitoCommittente.model().SCARTO.getFieldType())+"");
				setParameter(object, "setScartoNote", NotificaEsitoCommittente.model().SCARTO_NOTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "scarto_note", NotificaEsitoCommittente.model().SCARTO_NOTE.getFieldType()));
				setParameter(object, "setScartoXml", NotificaEsitoCommittente.model().SCARTO_XML.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "scarto_xml", NotificaEsitoCommittente.model().SCARTO_XML.getFieldType()));
				setParameter(object, "setXml", NotificaEsitoCommittente.model().XML.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "xml", NotificaEsitoCommittente.model().XML.getFieldType()));
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

			if(model.equals(NotificaEsitoCommittente.model())){
				NotificaEsitoCommittente object = new NotificaEsitoCommittente();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setIdentificativoSdi", NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI.getFieldType(),
					this.getObjectFromMap(map,"identificativoSdi"));
				setParameter(object, "setNumeroFattura", NotificaEsitoCommittente.model().NUMERO_FATTURA.getFieldType(),
					this.getObjectFromMap(map,"numeroFattura"));
				setParameter(object, "setAnno", NotificaEsitoCommittente.model().ANNO.getFieldType(),
					this.getObjectFromMap(map,"anno"));
				setParameter(object, "setPosizione", NotificaEsitoCommittente.model().POSIZIONE.getFieldType(),
					this.getObjectFromMap(map,"posizione"));
				setParameter(object, "set_value_esito", String.class,
					this.getObjectFromMap(map,"esito"));
				setParameter(object, "setDescrizione", NotificaEsitoCommittente.model().DESCRIZIONE.getFieldType(),
					this.getObjectFromMap(map,"descrizione"));
				setParameter(object, "setMessageIdCommittente", NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE.getFieldType(),
					this.getObjectFromMap(map,"messageIdCommittente"));
				setParameter(object, "setNomeFile", NotificaEsitoCommittente.model().NOME_FILE.getFieldType(),
					this.getObjectFromMap(map,"nomeFile"));
				setParameter(object, "setModalitaBatch", NotificaEsitoCommittente.model().MODALITA_BATCH.getFieldType(),
					this.getObjectFromMap(map,"modalita-batch"));
				setParameter(object, "setDataInvioEnte", NotificaEsitoCommittente.model().DATA_INVIO_ENTE.getFieldType(),
					this.getObjectFromMap(map,"dataInvioEnte"));
				setParameter(object, "setDataInvioSdi", NotificaEsitoCommittente.model().DATA_INVIO_SDI.getFieldType(),
					this.getObjectFromMap(map,"dataInvioSdi"));
				setParameter(object, "set_value_statoConsegnaSdi", String.class,
					this.getObjectFromMap(map,"statoConsegnaSdi"));
				setParameter(object, "setDataUltimaConsegnaSdi", NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI.getFieldType(),
					this.getObjectFromMap(map,"dataUltimaConsegnaSdi"));
				setParameter(object, "setDataProssimaConsegnaSdi", NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI.getFieldType(),
					this.getObjectFromMap(map,"dataProssimaConsegnaSdi"));
				setParameter(object, "setTentativiConsegnaSdi", NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI.getFieldType(),
					this.getObjectFromMap(map,"tentativiConsegnaSdi"));
				setParameter(object, "set_value_scarto", String.class,
					this.getObjectFromMap(map,"scarto"));
				setParameter(object, "setScartoNote", NotificaEsitoCommittente.model().SCARTO_NOTE.getFieldType(),
					this.getObjectFromMap(map,"scartoNote"));
				setParameter(object, "setScartoXml", NotificaEsitoCommittente.model().SCARTO_XML.getFieldType(),
					this.getObjectFromMap(map,"scartoXml"));
				setParameter(object, "setXml", NotificaEsitoCommittente.model().XML.getFieldType(),
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

			if(model.equals(NotificaEsitoCommittente.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("esito_committente","id","seq_esito_committente","esito_committente_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
