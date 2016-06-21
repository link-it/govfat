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

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;


/**     
 * FatturaElettronicaFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class FatturaElettronicaFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(FatturaElettronica.model())){
				FatturaElettronica object = new FatturaElettronica();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "set_value_formatoTrasmissione", String.class,
					jdbcParameterUtilities.readParameter(rs, "formato_trasmissione", FatturaElettronica.model().FORMATO_TRASMISSIONE.getFieldType())+"");
				setParameter(object, "setIdentificativoSdi", FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "identificativo_sdi", FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()));
				setParameter(object, "setDataRicezione", FatturaElettronica.model().DATA_RICEZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ricezione", FatturaElettronica.model().DATA_RICEZIONE.getFieldType()));
				setParameter(object, "setNomeFile", FatturaElettronica.model().NOME_FILE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome_file", FatturaElettronica.model().NOME_FILE.getFieldType()));
				setParameter(object, "setMessageId", FatturaElettronica.model().MESSAGE_ID.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "message_id", FatturaElettronica.model().MESSAGE_ID.getFieldType()));
				setParameter(object, "setCedentePrestatoreDenominazione", FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_denominazione", FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()));
				setParameter(object, "setCedentePrestatorePaese", FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_nazione", FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE.getFieldType()));
				setParameter(object, "setCedentePrestatoreCodiceFiscale", FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_codicefiscale", FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()));
				setParameter(object, "setCessionarioCommittenteDenominazione", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_denominazione", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()));
				setParameter(object, "setCessionarioCommittentePaese", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_nazione", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType()));
				setParameter(object, "setCessionarioCommittenteCodiceFiscale", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_codicefiscale", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteDenominazione", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_denominazione", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittentePaese", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_nazione", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_codicefiscale", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()));
				setParameter(object, "setPosizione", FatturaElettronica.model().POSIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "posizione", FatturaElettronica.model().POSIZIONE.getFieldType(), org.openspcoop2.generic_project.dao.jdbc.utils.JDBCDefaultForXSDType.FORCE_ZERO_AS_NULL));
				setParameter(object, "setCodiceDestinatario", FatturaElettronica.model().CODICE_DESTINATARIO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_destinatario", FatturaElettronica.model().CODICE_DESTINATARIO.getFieldType()));
				setParameter(object, "set_value_tipoDocumento", String.class,
					jdbcParameterUtilities.readParameter(rs, "tipo_documento", FatturaElettronica.model().TIPO_DOCUMENTO.getFieldType())+"");
				setParameter(object, "setDivisa", FatturaElettronica.model().DIVISA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "divisa", FatturaElettronica.model().DIVISA.getFieldType()));
				setParameter(object, "setData", FatturaElettronica.model().DATA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data", FatturaElettronica.model().DATA.getFieldType()));
				setParameter(object, "setAnno", FatturaElettronica.model().ANNO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "anno", FatturaElettronica.model().ANNO.getFieldType()));
				setParameter(object, "setNumero", FatturaElettronica.model().NUMERO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "numero", FatturaElettronica.model().NUMERO.getFieldType()));
				setParameter(object, "set_value_esito", String.class,
					jdbcParameterUtilities.readParameter(rs, "esito", FatturaElettronica.model().ESITO.getFieldType())+"");
				setParameter(object, "setDaPagare", FatturaElettronica.model().DA_PAGARE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "da_pagare", FatturaElettronica.model().DA_PAGARE.getFieldType()));
				setParameter(object, "setImportoTotaleDocumento", FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "importo_totale_documento", FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO.getFieldType()));
				setParameter(object, "setImportoTotaleRiepilogo", FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "importo_totale_riepilogo", FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO.getFieldType()));
				setParameter(object, "setCausale", FatturaElettronica.model().CAUSALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "causale", FatturaElettronica.model().CAUSALE.getFieldType()));
				setParameter(object, "set_value_statoConsegna", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_consegna", FatturaElettronica.model().STATO_CONSEGNA.getFieldType())+"");
				setParameter(object, "setDataConsegna", FatturaElettronica.model().DATA_CONSEGNA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_consegna", FatturaElettronica.model().DATA_CONSEGNA.getFieldType()));
				setParameter(object, "setDataProssimaConsegna", FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_prossima_consegna", FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA.getFieldType()));
				setParameter(object, "setTentativiConsegna", FatturaElettronica.model().TENTATIVI_CONSEGNA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "tentativi_consegna", FatturaElettronica.model().TENTATIVI_CONSEGNA.getFieldType()));
				setParameter(object, "setDettaglioConsegna", FatturaElettronica.model().DETTAGLIO_CONSEGNA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "dettaglio_consegna", FatturaElettronica.model().DETTAGLIO_CONSEGNA.getFieldType()));
				setParameter(object, "set_value_statoProtocollazione", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_protocollazione", FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType())+"");
				setParameter(object, "setDataScadenza", FatturaElettronica.model().DATA_SCADENZA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_scadenza", FatturaElettronica.model().DATA_SCADENZA.getFieldType()));
				setParameter(object, "setDataProtocollazione", FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_protocollazione", FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType()));
				setParameter(object, "setProtocollo", FatturaElettronica.model().PROTOCOLLO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "protocollo", FatturaElettronica.model().PROTOCOLLO.getFieldType()));
				setParameter(object, "setXml", FatturaElettronica.model().XML.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "xml", FatturaElettronica.model().XML.getFieldType()));
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

			if(model.equals(FatturaElettronica.model())){
				FatturaElettronica object = new FatturaElettronica();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "set_value_formatoTrasmissione", String.class,
					this.getObjectFromMap(map,"formatoTrasmissione"));
				setParameter(object, "setIdentificativoSdi", FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType(),
					this.getObjectFromMap(map,"identificativoSdi"));
				setParameter(object, "setDataRicezione", FatturaElettronica.model().DATA_RICEZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataRicezione"));
				setParameter(object, "setNomeFile", FatturaElettronica.model().NOME_FILE.getFieldType(),
					this.getObjectFromMap(map,"nomeFile"));
				setParameter(object, "setMessageId", FatturaElettronica.model().MESSAGE_ID.getFieldType(),
					this.getObjectFromMap(map,"messageId"));
				setParameter(object, "setCedentePrestatoreDenominazione", FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatoreDenominazione"));
				setParameter(object, "setCedentePrestatorePaese", FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatorePaese"));
				setParameter(object, "setCedentePrestatoreCodiceFiscale", FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatoreCodiceFiscale"));
				setParameter(object, "setCessionarioCommittenteDenominazione", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittenteDenominazione"));
				setParameter(object, "setCessionarioCommittentePaese", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittentePaese"));
				setParameter(object, "setCessionarioCommittenteCodiceFiscale", FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittenteCodiceFiscale"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteDenominazione", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittenteDenominazione"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittentePaese", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittentePaese"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale", FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittenteCodiceFiscale"));
				setParameter(object, "setPosizione", FatturaElettronica.model().POSIZIONE.getFieldType(),
					this.getObjectFromMap(map,"posizione"));
				setParameter(object, "setCodiceDestinatario", FatturaElettronica.model().CODICE_DESTINATARIO.getFieldType(),
					this.getObjectFromMap(map,"codiceDestinatario"));
				setParameter(object, "set_value_tipoDocumento", String.class,
					this.getObjectFromMap(map,"tipoDocumento"));
				setParameter(object, "setDivisa", FatturaElettronica.model().DIVISA.getFieldType(),
					this.getObjectFromMap(map,"divisa"));
				setParameter(object, "setData", FatturaElettronica.model().DATA.getFieldType(),
					this.getObjectFromMap(map,"data"));
				setParameter(object, "setAnno", FatturaElettronica.model().ANNO.getFieldType(),
					this.getObjectFromMap(map,"anno"));
				setParameter(object, "setNumero", FatturaElettronica.model().NUMERO.getFieldType(),
					this.getObjectFromMap(map,"numero"));
				setParameter(object, "set_value_esito", String.class,
					this.getObjectFromMap(map,"esito"));
				setParameter(object, "setDaPagare", FatturaElettronica.model().DA_PAGARE.getFieldType(),
					this.getObjectFromMap(map,"daPagare"));
				setParameter(object, "setImportoTotaleDocumento", FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO.getFieldType(),
					this.getObjectFromMap(map,"importoTotaleDocumento"));
				setParameter(object, "setImportoTotaleRiepilogo", FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO.getFieldType(),
					this.getObjectFromMap(map,"importoTotaleRiepilogo"));
				setParameter(object, "setCausale", FatturaElettronica.model().CAUSALE.getFieldType(),
					this.getObjectFromMap(map,"causale"));
				setParameter(object, "set_value_statoConsegna", String.class,
					this.getObjectFromMap(map,"statoConsegna"));
				setParameter(object, "setDataConsegna", FatturaElettronica.model().DATA_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"dataConsegna"));
				setParameter(object, "setDataProssimaConsegna", FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"dataProssimaConsegna"));
				setParameter(object, "setTentativiConsegna", FatturaElettronica.model().TENTATIVI_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"tentativiConsegna"));
				setParameter(object, "setDettaglioConsegna", FatturaElettronica.model().DETTAGLIO_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"dettaglioConsegna"));
				setParameter(object, "set_value_statoProtocollazione", String.class,
					this.getObjectFromMap(map,"statoProtocollazione"));
				setParameter(object, "setDataScadenza", FatturaElettronica.model().DATA_SCADENZA.getFieldType(),
					this.getObjectFromMap(map,"dataScadenza"));
				setParameter(object, "setDataProtocollazione", FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataProtocollazione"));
				setParameter(object, "setProtocollo", FatturaElettronica.model().PROTOCOLLO.getFieldType(),
					this.getObjectFromMap(map,"protocollo"));
				setParameter(object, "setXml", FatturaElettronica.model().XML.getFieldType(),
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

			if(model.equals(FatturaElettronica.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("fatture","id","seq_fatture","fatture_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
