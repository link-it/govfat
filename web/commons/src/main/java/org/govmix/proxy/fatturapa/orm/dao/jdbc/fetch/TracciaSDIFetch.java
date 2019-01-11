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

import java.sql.ResultSet;
import java.util.Map;

import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.jdbc.utils.AbstractJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCParameterUtilities;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.jdbc.IKeyGeneratorObject;


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
				setParameter(object, "setPosizione", TracciaSDI.model().POSIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "posizione", TracciaSDI.model().POSIZIONE.getFieldType(), org.openspcoop2.generic_project.dao.jdbc.utils.JDBCDefaultForXSDType.FORCE_ZERO_AS_NULL));
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
			if(model.equals(TracciaSDI.model().LOTTO_FATTURE)){
		        LottoFatture object = new LottoFatture();
		        setParameter(object, "setId", Long.class,
		                jdbcParameterUtilities.readParameter(rs, "lotto.id", Long.class));
		        setParameter(object, "set_value_formatoTrasmissione", String.class,
		                jdbcParameterUtilities.readParameter(rs, "formato_trasmissione", TracciaSDI.model().LOTTO_FATTURE.FORMATO_TRASMISSIONE.getFieldType())+"");
		        setParameter(object, "setIdentificativoSdi", TracciaSDI.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "identificativo_sdi", TracciaSDI.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI.getFieldType()));
		        setParameter(object, "setNomeFile", TracciaSDI.model().LOTTO_FATTURE.NOME_FILE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "nome_file", TracciaSDI.model().LOTTO_FATTURE.NOME_FILE.getFieldType()));
		        setParameter(object, "set_value_formatoArchivioInvioFattura", String.class,
		                jdbcParameterUtilities.readParameter(rs, "formato_archivio_invio_fattura", TracciaSDI.model().LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA.getFieldType())+"");
		        setParameter(object, "setMessageId", TracciaSDI.model().LOTTO_FATTURE.MESSAGE_ID.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "message_id", TracciaSDI.model().LOTTO_FATTURE.MESSAGE_ID.getFieldType()));
		        setParameter(object, "setCedentePrestatoreDenominazione", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cp_denominazione", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()));
		        setParameter(object, "setCedentePrestatoreNome", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cp_nome", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME.getFieldType()));
		        setParameter(object, "setCedentePrestatoreCognome", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cp_cognome", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME.getFieldType()));
		        setParameter(object, "setCedentePrestatoreCodice", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cp_idcodice", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE.getFieldType()));
		        setParameter(object, "setCedentePrestatorePaese", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cp_nazione", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE.getFieldType()));
		        setParameter(object, "setCedentePrestatoreCodiceFiscale", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cp_codicefiscale", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()));
		        setParameter(object, "setCessionarioCommittenteDenominazione", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cc_denominazione", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()));
		        setParameter(object, "setCessionarioCommittenteNome", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cc_nome", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME.getFieldType()));
		        setParameter(object, "setCessionarioCommittenteCognome", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cc_cognome", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME.getFieldType()));
		        setParameter(object, "setCessionarioCommittenteCodice", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cc_idcodice", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE.getFieldType()));
		        setParameter(object, "setCessionarioCommittentePaese", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cc_nazione", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE.getFieldType()));
		        setParameter(object, "setCessionarioCommittenteCodiceFiscale", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "cc_codicefiscale", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()));
		        setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteDenominazione", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "se_denominazione", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()));
		        setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteNome", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "se_nome", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType()));
		        setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCognome", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "se_cognome", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType()));
		        setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodice", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "se_idcodice", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType()));
		        setParameter(object, "setTerzoIntermediarioOSoggettoEmittentePaese", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "se_nazione", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()));
		        setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "se_codicefiscale", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()));
		        setParameter(object, "setCodiceDestinatario", TracciaSDI.model().LOTTO_FATTURE.CODICE_DESTINATARIO.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "codice_destinatario", TracciaSDI.model().LOTTO_FATTURE.CODICE_DESTINATARIO.getFieldType()));
		        setParameter(object, "setXml", TracciaSDI.model().LOTTO_FATTURE.XML.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "xml", TracciaSDI.model().LOTTO_FATTURE.XML.getFieldType()));
		        setParameter(object, "setFatturazioneAttiva", TracciaSDI.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "fatturazione_attiva", TracciaSDI.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA.getFieldType()));
		        setParameter(object, "set_value_statoElaborazioneInUscita", String.class,
		                jdbcParameterUtilities.readParameter(rs, "stato_elaborazione_in_uscita", TracciaSDI.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA.getFieldType())+"");
		        setParameter(object, "setDataUltimaElaborazione", TracciaSDI.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "data_ultima_elaborazione", TracciaSDI.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE.getFieldType()));
		        setParameter(object, "setDataRicezione", TracciaSDI.model().LOTTO_FATTURE.DATA_RICEZIONE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "data_ricezione", TracciaSDI.model().LOTTO_FATTURE.DATA_RICEZIONE.getFieldType()));
		        setParameter(object, "set_value_statoInserimento", String.class,
		                jdbcParameterUtilities.readParameter(rs, "stato_inserimento", TracciaSDI.model().LOTTO_FATTURE.STATO_INSERIMENTO.getFieldType())+"");
		        setParameter(object, "set_value_statoConsegna", String.class,
		                jdbcParameterUtilities.readParameter(rs, "stato_consegna", TracciaSDI.model().LOTTO_FATTURE.STATO_CONSEGNA.getFieldType())+"");
		        setParameter(object, "setDataConsegna", TracciaSDI.model().LOTTO_FATTURE.DATA_CONSEGNA.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "data_consegna", TracciaSDI.model().LOTTO_FATTURE.DATA_CONSEGNA.getFieldType()));
		        setParameter(object, "setDettaglioConsegna", TracciaSDI.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "dettaglio_consegna", TracciaSDI.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA.getFieldType()));
		        setParameter(object, "set_value_statoProtocollazione", String.class,
		                jdbcParameterUtilities.readParameter(rs, "stato_protocollazione", TracciaSDI.model().LOTTO_FATTURE.STATO_PROTOCOLLAZIONE.getFieldType())+"");
		        setParameter(object, "setDataProtocollazione", TracciaSDI.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "data_protocollazione", TracciaSDI.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE.getFieldType()));
		        setParameter(object, "setProtocollo", TracciaSDI.model().LOTTO_FATTURE.PROTOCOLLO.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "protocollo", TracciaSDI.model().LOTTO_FATTURE.PROTOCOLLO.getFieldType()));
		        setParameter(object, "setIdEgov", TracciaSDI.model().LOTTO_FATTURE.ID_EGOV.getFieldType(),
		                jdbcParameterUtilities.readParameter(rs, "id_egov", TracciaSDI.model().LOTTO_FATTURE.ID_EGOV.getFieldType()));
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
				setParameter(object, "setPosizione", TracciaSDI.model().POSIZIONE.getFieldType(),
					this.getObjectFromMap(map,"posizione"));
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
			  if(model.equals(TracciaSDI.model().LOTTO_FATTURE)){
		          LottoFatture object = new LottoFatture();
		          setParameter(object, "setId", Long.class,
		                  this.getObjectFromMap(map,"LottoFatture.id"));
		          setParameter(object, "set_value_formatoTrasmissione", String.class,
		                  this.getObjectFromMap(map,"LottoFatture.formatoTrasmissione"));
		          setParameter(object, "setIdentificativoSdi", TracciaSDI.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.identificativoSdi"));
		          setParameter(object, "setNomeFile", TracciaSDI.model().LOTTO_FATTURE.NOME_FILE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.nomeFile"));
		          setParameter(object, "set_value_formatoArchivioInvioFattura", String.class,
		                  this.getObjectFromMap(map,"LottoFatture.formatoArchivioInvioFattura"));
		          setParameter(object, "setMessageId", TracciaSDI.model().LOTTO_FATTURE.MESSAGE_ID.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.messageId"));
		          setParameter(object, "setCedentePrestatoreDenominazione", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cedentePrestatoreDenominazione"));
		          setParameter(object, "setCedentePrestatoreNome", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cedentePrestatoreNome"));
		          setParameter(object, "setCedentePrestatoreCognome", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cedentePrestatoreCognome"));
		          setParameter(object, "setCedentePrestatoreCodice", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cedentePrestatoreCodice"));
		          setParameter(object, "setCedentePrestatorePaese", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cedentePrestatorePaese"));
		          setParameter(object, "setCedentePrestatoreCodiceFiscale", TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cedentePrestatoreCodiceFiscale"));
		          setParameter(object, "setCessionarioCommittenteDenominazione", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cessionarioCommittenteDenominazione"));
		          setParameter(object, "setCessionarioCommittenteNome", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cessionarioCommittenteNome"));
		          setParameter(object, "setCessionarioCommittenteCognome", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cessionarioCommittenteCognome"));
		          setParameter(object, "setCessionarioCommittenteCodice", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cessionarioCommittenteCodice"));
		          setParameter(object, "setCessionarioCommittentePaese", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cessionarioCommittentePaese"));
		          setParameter(object, "setCessionarioCommittenteCodiceFiscale", TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.cessionarioCommittenteCodiceFiscale"));
		          setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteDenominazione", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.terzoIntermediarioOSoggettoEmittenteDenominazione"));
		          setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteNome", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.terzoIntermediarioOSoggettoEmittenteNome"));
		          setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCognome", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.terzoIntermediarioOSoggettoEmittenteCognome"));
		          setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodice", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.terzoIntermediarioOSoggettoEmittenteCodice"));
		          setParameter(object, "setTerzoIntermediarioOSoggettoEmittentePaese", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.terzoIntermediarioOSoggettoEmittentePaese"));
		          setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale", TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.terzoIntermediarioOSoggettoEmittenteCodiceFiscale"));
		          setParameter(object, "setCodiceDestinatario", TracciaSDI.model().LOTTO_FATTURE.CODICE_DESTINATARIO.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.codiceDestinatario"));
		          setParameter(object, "setXml", TracciaSDI.model().LOTTO_FATTURE.XML.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.xml"));
		          setParameter(object, "setFatturazioneAttiva", TracciaSDI.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.fatturazioneAttiva"));
		          setParameter(object, "set_value_statoElaborazioneInUscita", String.class,
		                  this.getObjectFromMap(map,"LottoFatture.statoElaborazioneInUscita"));
		          setParameter(object, "setDataRicezione", TracciaSDI.model().LOTTO_FATTURE.DATA_RICEZIONE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.dataRicezione"));
		          setParameter(object, "set_value_statoInserimento", String.class,
		                  this.getObjectFromMap(map,"LottoFatture.statoInserimento"));
		          setParameter(object, "set_value_statoConsegna", String.class,
		                  this.getObjectFromMap(map,"LottoFatture.statoConsegna"));
		          setParameter(object, "setDataConsegna", TracciaSDI.model().LOTTO_FATTURE.DATA_CONSEGNA.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.dataConsegna"));
		          setParameter(object, "setDettaglioConsegna", TracciaSDI.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.dettaglioConsegna"));
		          setParameter(object, "set_value_statoProtocollazione", String.class,
		                  this.getObjectFromMap(map,"LottoFatture.statoProtocollazione"));
		          setParameter(object, "setDataProtocollazione", TracciaSDI.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.dataProtocollazione"));
		          setParameter(object, "setProtocollo", TracciaSDI.model().LOTTO_FATTURE.PROTOCOLLO.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.protocollo"));
		          setParameter(object, "setIdEgov", TracciaSDI.model().LOTTO_FATTURE.ID_EGOV.getFieldType(),
		                  this.getObjectFromMap(map,"LottoFatture.id-egov"));
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
