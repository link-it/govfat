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

import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;


/**     
 * PccContabilizzazioneFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccContabilizzazioneFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(PccContabilizzazione.model())){
				PccContabilizzazione object = new PccContabilizzazione();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setImportoMovimento", PccContabilizzazione.model().IMPORTO_MOVIMENTO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "importo_movimento", PccContabilizzazione.model().IMPORTO_MOVIMENTO.getFieldType()));
				setParameter(object, "set_value_naturaSpesa", String.class,
					jdbcParameterUtilities.readParameter(rs, "natura_spesa", PccContabilizzazione.model().NATURA_SPESA.getFieldType())+"");
				setParameter(object, "setCapitoliSpesa", PccContabilizzazione.model().CAPITOLI_SPESA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "capitoli_spesa", PccContabilizzazione.model().CAPITOLI_SPESA.getFieldType()));
				setParameter(object, "set_value_statoDebito", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_debito", PccContabilizzazione.model().STATO_DEBITO.getFieldType())+"");
				setParameter(object, "set_value_causale", String.class,
					jdbcParameterUtilities.readParameter(rs, "causale", PccContabilizzazione.model().CAUSALE.getFieldType())+"");
				setParameter(object, "setDescrizione", PccContabilizzazione.model().DESCRIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione", PccContabilizzazione.model().DESCRIZIONE.getFieldType()));
				setParameter(object, "setEstremiImpegno", PccContabilizzazione.model().ESTREMI_IMPEGNO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "estremi_impegno", PccContabilizzazione.model().ESTREMI_IMPEGNO.getFieldType()));
				setParameter(object, "setCodiceCig", PccContabilizzazione.model().CODICE_CIG.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_cig", PccContabilizzazione.model().CODICE_CIG.getFieldType()));
				setParameter(object, "setCodiceCup", PccContabilizzazione.model().CODICE_CUP.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_cup", PccContabilizzazione.model().CODICE_CUP.getFieldType()));
				setParameter(object, "setIdImporto", PccContabilizzazione.model().ID_IMPORTO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_importo", PccContabilizzazione.model().ID_IMPORTO.getFieldType()));
				setParameter(object, "setSistemaRichiedente", PccContabilizzazione.model().SISTEMA_RICHIEDENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "sistema_richiedente", PccContabilizzazione.model().SISTEMA_RICHIEDENTE.getFieldType()));
				setParameter(object, "setUtenteRichiedente", PccContabilizzazione.model().UTENTE_RICHIEDENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "utente_richiedente", PccContabilizzazione.model().UTENTE_RICHIEDENTE.getFieldType()));
				setParameter(object, "setDataRichiesta", PccContabilizzazione.model().DATA_RICHIESTA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_richiesta", PccContabilizzazione.model().DATA_RICHIESTA.getFieldType()));
				setParameter(object, "setDataQuery", PccContabilizzazione.model().DATA_QUERY.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_query", PccContabilizzazione.model().DATA_QUERY.getFieldType()));
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

			if(model.equals(PccContabilizzazione.model())){
				PccContabilizzazione object = new PccContabilizzazione();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setImportoMovimento", PccContabilizzazione.model().IMPORTO_MOVIMENTO.getFieldType(),
					this.getObjectFromMap(map,"importoMovimento"));
				setParameter(object, "set_value_naturaSpesa", String.class,
					this.getObjectFromMap(map,"naturaSpesa"));
				setParameter(object, "setCapitoliSpesa", PccContabilizzazione.model().CAPITOLI_SPESA.getFieldType(),
					this.getObjectFromMap(map,"capitoliSpesa"));
				setParameter(object, "set_value_statoDebito", String.class,
					this.getObjectFromMap(map,"statoDebito"));
				setParameter(object, "set_value_causale", String.class,
					this.getObjectFromMap(map,"causale"));
				setParameter(object, "setDescrizione", PccContabilizzazione.model().DESCRIZIONE.getFieldType(),
					this.getObjectFromMap(map,"descrizione"));
				setParameter(object, "setEstremiImpegno", PccContabilizzazione.model().ESTREMI_IMPEGNO.getFieldType(),
					this.getObjectFromMap(map,"estremiImpegno"));
				setParameter(object, "setCodiceCig", PccContabilizzazione.model().CODICE_CIG.getFieldType(),
					this.getObjectFromMap(map,"CodiceCig"));
				setParameter(object, "setCodiceCup", PccContabilizzazione.model().CODICE_CUP.getFieldType(),
					this.getObjectFromMap(map,"CodiceCup"));
				setParameter(object, "setIdImporto", PccContabilizzazione.model().ID_IMPORTO.getFieldType(),
					this.getObjectFromMap(map,"idImporto"));
				setParameter(object, "setSistemaRichiedente", PccContabilizzazione.model().SISTEMA_RICHIEDENTE.getFieldType(),
					this.getObjectFromMap(map,"sistemaRichiedente"));
				setParameter(object, "setUtenteRichiedente", PccContabilizzazione.model().UTENTE_RICHIEDENTE.getFieldType(),
					this.getObjectFromMap(map,"utenteRichiedente"));
				setParameter(object, "setDataRichiesta", PccContabilizzazione.model().DATA_RICHIESTA.getFieldType(),
					this.getObjectFromMap(map,"dataRichiesta"));
				setParameter(object, "setDataQuery", PccContabilizzazione.model().DATA_QUERY.getFieldType(),
					this.getObjectFromMap(map,"dataQuery"));
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

			if(model.equals(PccContabilizzazione.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("pcc_contabilizzazioni","id","seq_pcc_contabilizzazioni","pcc_contabilizzazioni_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
