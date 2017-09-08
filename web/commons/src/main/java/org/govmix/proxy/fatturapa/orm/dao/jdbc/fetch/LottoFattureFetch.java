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

import org.govmix.proxy.fatturapa.orm.LottoFatture;


/**     
 * LottoFattureFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class LottoFattureFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(LottoFatture.model())){
				LottoFatture object = new LottoFatture();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "set_value_formatoTrasmissione", String.class,
					jdbcParameterUtilities.readParameter(rs, "formato_trasmissione", LottoFatture.model().FORMATO_TRASMISSIONE.getFieldType())+"");
				setParameter(object, "setIdentificativoSdi", LottoFatture.model().IDENTIFICATIVO_SDI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "identificativo_sdi", LottoFatture.model().IDENTIFICATIVO_SDI.getFieldType()));
				setParameter(object, "setNomeFile", LottoFatture.model().NOME_FILE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "nome_file", LottoFatture.model().NOME_FILE.getFieldType()));
				setParameter(object, "set_value_formatoArchivioInvioFattura", String.class,
					jdbcParameterUtilities.readParameter(rs, "formato_archivio_invio_fattura", LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA.getFieldType())+"");
				setParameter(object, "setMessageId", LottoFatture.model().MESSAGE_ID.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "message_id", LottoFatture.model().MESSAGE_ID.getFieldType()));
				setParameter(object, "setCedentePrestatoreDenominazione", LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_denominazione", LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()));
				setParameter(object, "setCedentePrestatoreNome", LottoFatture.model().CEDENTE_PRESTATORE_NOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_nome", LottoFatture.model().CEDENTE_PRESTATORE_NOME.getFieldType()));
				setParameter(object, "setCedentePrestatoreCognome", LottoFatture.model().CEDENTE_PRESTATORE_COGNOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_cognome", LottoFatture.model().CEDENTE_PRESTATORE_COGNOME.getFieldType()));
				setParameter(object, "setCedentePrestatoreCodice", LottoFatture.model().CEDENTE_PRESTATORE_CODICE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_idcodice", LottoFatture.model().CEDENTE_PRESTATORE_CODICE.getFieldType()));
				setParameter(object, "setCedentePrestatorePaese", LottoFatture.model().CEDENTE_PRESTATORE_PAESE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_nazione", LottoFatture.model().CEDENTE_PRESTATORE_PAESE.getFieldType()));
				setParameter(object, "setCedentePrestatoreCodiceFiscale", LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cp_codicefiscale", LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()));
				setParameter(object, "setCessionarioCommittenteDenominazione", LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_denominazione", LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()));
				setParameter(object, "setCessionarioCommittenteNome", LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_nome", LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME.getFieldType()));
				setParameter(object, "setCessionarioCommittenteCognome", LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_cognome", LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME.getFieldType()));
				setParameter(object, "setCessionarioCommittenteCodice", LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_idcodice", LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE.getFieldType()));
				setParameter(object, "setCessionarioCommittentePaese", LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_nazione", LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType()));
				setParameter(object, "setCessionarioCommittenteCodiceFiscale", LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cc_codicefiscale", LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteDenominazione", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_denominazione", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteNome", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_nome", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCognome", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_cognome", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodice", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_idcodice", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittentePaese", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_nazione", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "se_codicefiscale", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()));
				setParameter(object, "setCodiceDestinatario", LottoFatture.model().CODICE_DESTINATARIO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_destinatario", LottoFatture.model().CODICE_DESTINATARIO.getFieldType()));
				setParameter(object, "setXml", LottoFatture.model().XML.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "xml", LottoFatture.model().XML.getFieldType()));
				setParameter(object, "setFatturazioneAttiva", LottoFatture.model().FATTURAZIONE_ATTIVA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "fatturazione_attiva", LottoFatture.model().FATTURAZIONE_ATTIVA.getFieldType()));
				setParameter(object, "set_value_statoElaborazioneInUscita", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_elaborazione_in_uscita", LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA.getFieldType())+"");
				setParameter(object, "setDataUltimaElaborazione", LottoFatture.model().DATA_ULTIMA_ELABORAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ultima_elaborazione", LottoFatture.model().DATA_ULTIMA_ELABORAZIONE.getFieldType()));
				setParameter(object, "setDataRicezione", LottoFatture.model().DATA_RICEZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ricezione", LottoFatture.model().DATA_RICEZIONE.getFieldType()));
				setParameter(object, "set_value_statoInserimento", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_inserimento", LottoFatture.model().STATO_INSERIMENTO.getFieldType())+"");
				setParameter(object, "set_value_statoConsegna", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_consegna", LottoFatture.model().STATO_CONSEGNA.getFieldType())+"");
				setParameter(object, "setDataConsegna", LottoFatture.model().DATA_CONSEGNA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_consegna", LottoFatture.model().DATA_CONSEGNA.getFieldType()));
				setParameter(object, "setDettaglioConsegna", LottoFatture.model().DETTAGLIO_CONSEGNA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "dettaglio_consegna", LottoFatture.model().DETTAGLIO_CONSEGNA.getFieldType()));
				setParameter(object, "set_value_statoProtocollazione", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato_protocollazione", LottoFatture.model().STATO_PROTOCOLLAZIONE.getFieldType())+"");
				setParameter(object, "setDataProtocollazione", LottoFatture.model().DATA_PROTOCOLLAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_protocollazione", LottoFatture.model().DATA_PROTOCOLLAZIONE.getFieldType()));
				setParameter(object, "setProtocollo", LottoFatture.model().PROTOCOLLO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "protocollo", LottoFatture.model().PROTOCOLLO.getFieldType()));
				setParameter(object, "setIdEgov", LottoFatture.model().ID_EGOV.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_egov", LottoFatture.model().ID_EGOV.getFieldType()));
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

			if(model.equals(LottoFatture.model())){
				LottoFatture object = new LottoFatture();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "set_value_formatoTrasmissione", String.class,
					this.getObjectFromMap(map,"formatoTrasmissione"));
				setParameter(object, "setIdentificativoSdi", LottoFatture.model().IDENTIFICATIVO_SDI.getFieldType(),
					this.getObjectFromMap(map,"identificativoSdi"));
				setParameter(object, "setNomeFile", LottoFatture.model().NOME_FILE.getFieldType(),
					this.getObjectFromMap(map,"nomeFile"));
				setParameter(object, "set_value_formatoArchivioInvioFattura", String.class,
					this.getObjectFromMap(map,"formatoArchivioInvioFattura"));
				setParameter(object, "setMessageId", LottoFatture.model().MESSAGE_ID.getFieldType(),
					this.getObjectFromMap(map,"messageId"));
				setParameter(object, "setCedentePrestatoreDenominazione", LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatoreDenominazione"));
				setParameter(object, "setCedentePrestatoreNome", LottoFatture.model().CEDENTE_PRESTATORE_NOME.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatoreNome"));
				setParameter(object, "setCedentePrestatoreCognome", LottoFatture.model().CEDENTE_PRESTATORE_COGNOME.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatoreCognome"));
				setParameter(object, "setCedentePrestatoreCodice", LottoFatture.model().CEDENTE_PRESTATORE_CODICE.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatoreCodice"));
				setParameter(object, "setCedentePrestatorePaese", LottoFatture.model().CEDENTE_PRESTATORE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatorePaese"));
				setParameter(object, "setCedentePrestatoreCodiceFiscale", LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"cedentePrestatoreCodiceFiscale"));
				setParameter(object, "setCessionarioCommittenteDenominazione", LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittenteDenominazione"));
				setParameter(object, "setCessionarioCommittenteNome", LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittenteNome"));
				setParameter(object, "setCessionarioCommittenteCognome", LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittenteCognome"));
				setParameter(object, "setCessionarioCommittenteCodice", LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittenteCodice"));
				setParameter(object, "setCessionarioCommittentePaese", LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittentePaese"));
				setParameter(object, "setCessionarioCommittenteCodiceFiscale", LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"cessionarioCommittenteCodiceFiscale"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteDenominazione", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittenteDenominazione"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteNome", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittenteNome"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCognome", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittenteCognome"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodice", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittenteCodice"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittentePaese", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittentePaese"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale", LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"terzoIntermediarioOSoggettoEmittenteCodiceFiscale"));
				setParameter(object, "setCodiceDestinatario", LottoFatture.model().CODICE_DESTINATARIO.getFieldType(),
					this.getObjectFromMap(map,"codiceDestinatario"));
				setParameter(object, "setXml", LottoFatture.model().XML.getFieldType(),
					this.getObjectFromMap(map,"xml"));
				setParameter(object, "setFatturazioneAttiva", LottoFatture.model().FATTURAZIONE_ATTIVA.getFieldType(),
					this.getObjectFromMap(map,"fatturazioneAttiva"));
				setParameter(object, "set_value_statoElaborazioneInUscita", String.class,
					this.getObjectFromMap(map,"statoElaborazioneInUscita"));
				setParameter(object, "setDataUltimaElaborazione", LottoFatture.model().DATA_ULTIMA_ELABORAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataUltimaElaborazione"));
				setParameter(object, "setDataRicezione", LottoFatture.model().DATA_RICEZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataRicezione"));
				setParameter(object, "set_value_statoInserimento", String.class,
					this.getObjectFromMap(map,"statoInserimento"));
				setParameter(object, "set_value_statoConsegna", String.class,
					this.getObjectFromMap(map,"statoConsegna"));
				setParameter(object, "setDataConsegna", LottoFatture.model().DATA_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"dataConsegna"));
				setParameter(object, "setDettaglioConsegna", LottoFatture.model().DETTAGLIO_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"dettaglioConsegna"));
				setParameter(object, "set_value_statoProtocollazione", String.class,
					this.getObjectFromMap(map,"statoProtocollazione"));
				setParameter(object, "setDataProtocollazione", LottoFatture.model().DATA_PROTOCOLLAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataProtocollazione"));
				setParameter(object, "setProtocollo", LottoFatture.model().PROTOCOLLO.getFieldType(),
					this.getObjectFromMap(map,"protocollo"));
				setParameter(object, "setIdEgov", LottoFatture.model().ID_EGOV.getFieldType(),
					this.getObjectFromMap(map,"id-egov"));
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

			if(model.equals(LottoFatture.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("lotti","id","seq_lotti","lotti_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
