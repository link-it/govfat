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

import org.govmix.proxy.fatturapa.orm.PccPagamento;


/**     
 * PccPagamentoFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccPagamentoFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(PccPagamento.model())){
				PccPagamento object = new PccPagamento();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setImportoPagato", PccPagamento.model().IMPORTO_PAGATO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "importo_pagato", PccPagamento.model().IMPORTO_PAGATO.getFieldType()));
				setParameter(object, "set_value_naturaSpesa", String.class,
					jdbcParameterUtilities.readParameter(rs, "natura_spesa", PccPagamento.model().NATURA_SPESA.getFieldType())+"");
				setParameter(object, "setCapitoliSpesa", PccPagamento.model().CAPITOLI_SPESA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "capitoli_spesa", PccPagamento.model().CAPITOLI_SPESA.getFieldType()));
				setParameter(object, "setEstremiImpegno", PccPagamento.model().ESTREMI_IMPEGNO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "estremi_impegno", PccPagamento.model().ESTREMI_IMPEGNO.getFieldType()));
				setParameter(object, "setNumeroMandato", PccPagamento.model().NUMERO_MANDATO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "numero_mandato", PccPagamento.model().NUMERO_MANDATO.getFieldType()));
				setParameter(object, "setDataMandato", PccPagamento.model().DATA_MANDATO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_mandato", PccPagamento.model().DATA_MANDATO.getFieldType()));
				setParameter(object, "setIdFiscaleIvaBeneficiario", PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_fiscale_iva_beneficiario", PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO.getFieldType()));
				setParameter(object, "setCodiceCig", PccPagamento.model().CODICE_CIG.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_cig", PccPagamento.model().CODICE_CIG.getFieldType()));
				setParameter(object, "setCodiceCup", PccPagamento.model().CODICE_CUP.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_cup", PccPagamento.model().CODICE_CUP.getFieldType()));
				setParameter(object, "setDescrizione", PccPagamento.model().DESCRIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "descrizione", PccPagamento.model().DESCRIZIONE.getFieldType()));
				setParameter(object, "setDataRichiesta", PccPagamento.model().DATA_RICHIESTA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_richiesta", PccPagamento.model().DATA_RICHIESTA.getFieldType()));
				setParameter(object, "setDataQuery", PccPagamento.model().DATA_QUERY.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_query", PccPagamento.model().DATA_QUERY.getFieldType()));
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

			if(model.equals(PccPagamento.model())){
				PccPagamento object = new PccPagamento();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setImportoPagato", PccPagamento.model().IMPORTO_PAGATO.getFieldType(),
					this.getObjectFromMap(map,"importoPagato"));
				setParameter(object, "set_value_naturaSpesa", String.class,
					this.getObjectFromMap(map,"naturaSpesa"));
				setParameter(object, "setCapitoliSpesa", PccPagamento.model().CAPITOLI_SPESA.getFieldType(),
					this.getObjectFromMap(map,"capitoliSpesa"));
				setParameter(object, "setEstremiImpegno", PccPagamento.model().ESTREMI_IMPEGNO.getFieldType(),
					this.getObjectFromMap(map,"estremiImpegno"));
				setParameter(object, "setNumeroMandato", PccPagamento.model().NUMERO_MANDATO.getFieldType(),
					this.getObjectFromMap(map,"numeroMandato"));
				setParameter(object, "setDataMandato", PccPagamento.model().DATA_MANDATO.getFieldType(),
					this.getObjectFromMap(map,"dataMandato"));
				setParameter(object, "setIdFiscaleIvaBeneficiario", PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO.getFieldType(),
					this.getObjectFromMap(map,"idFiscaleIvaBeneficiario"));
				setParameter(object, "setCodiceCig", PccPagamento.model().CODICE_CIG.getFieldType(),
					this.getObjectFromMap(map,"CodiceCig"));
				setParameter(object, "setCodiceCup", PccPagamento.model().CODICE_CUP.getFieldType(),
					this.getObjectFromMap(map,"CodiceCup"));
				setParameter(object, "setDescrizione", PccPagamento.model().DESCRIZIONE.getFieldType(),
					this.getObjectFromMap(map,"descrizione"));
				setParameter(object, "setDataRichiesta", PccPagamento.model().DATA_RICHIESTA.getFieldType(),
					this.getObjectFromMap(map,"dataRichiesta"));
				setParameter(object, "setDataQuery", PccPagamento.model().DATA_QUERY.getFieldType(),
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

			if(model.equals(PccPagamento.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("pcc_pagamenti","id","seq_pcc_pagamenti","pcc_pagamenti_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
