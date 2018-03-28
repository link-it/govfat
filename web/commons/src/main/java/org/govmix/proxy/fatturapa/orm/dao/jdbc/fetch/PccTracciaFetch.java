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
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.PccTraccia;


/**     
 * PccTracciaFetch
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccTracciaFetch extends AbstractJDBCFetch {

	@Override
	public Object fetch(TipiDatabase tipoDatabase, IModel<?> model , ResultSet rs) throws ServiceException {
		
		try{
			JDBCParameterUtilities jdbcParameterUtilities =  
					new JDBCParameterUtilities(tipoDatabase);

			if(model.equals(PccTraccia.model())){
				PccTraccia object = new PccTraccia();
				setParameter(object, "setId", Long.class,
					jdbcParameterUtilities.readParameter(rs, "id", Long.class));
				setParameter(object, "setDataCreazione", PccTraccia.model().DATA_CREAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_creazione", PccTraccia.model().DATA_CREAZIONE.getFieldType()));
				setParameter(object, "setCfTrasmittente", PccTraccia.model().CF_TRASMITTENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "cf_trasmittente", PccTraccia.model().CF_TRASMITTENTE.getFieldType()));
				setParameter(object, "setVersioneApplicativa", PccTraccia.model().VERSIONE_APPLICATIVA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "versione_applicativa", PccTraccia.model().VERSIONE_APPLICATIVA.getFieldType()));
				setParameter(object, "setIdPccAmministrazione", PccTraccia.model().ID_PCC_AMMINISTRAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_pcc_amministrazione", PccTraccia.model().ID_PCC_AMMINISTRAZIONE.getFieldType()));
				setParameter(object, "setIdPaTransazione", PccTraccia.model().ID_PA_TRANSAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_pa_transazione", PccTraccia.model().ID_PA_TRANSAZIONE.getFieldType()));
				setParameter(object, "setIdPaTransazioneRispedizione", PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_pa_transazione_rispedizione", PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE.getFieldType()));
				setParameter(object, "setSistemaRichiedente", PccTraccia.model().SISTEMA_RICHIEDENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "sistema_richiedente", PccTraccia.model().SISTEMA_RICHIEDENTE.getFieldType()));
				setParameter(object, "setUtenteRichiedente", PccTraccia.model().UTENTE_RICHIEDENTE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "utente_richiedente", PccTraccia.model().UTENTE_RICHIEDENTE.getFieldType()));
				setParameter(object, "setIdFattura", PccTraccia.model().ID_FATTURA.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "id_fattura", PccTraccia.model().ID_FATTURA.getFieldType()));
				setParameter(object, "setCodiceDipartimento", PccTraccia.model().CODICE_DIPARTIMENTO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codice_dipartimento", PccTraccia.model().CODICE_DIPARTIMENTO.getFieldType()));
				setParameter(object, "setRichiestaXml", PccTraccia.model().RICHIESTA_XML.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "richiesta_xml", PccTraccia.model().RICHIESTA_XML.getFieldType()));
				setParameter(object, "setRispostaXml", PccTraccia.model().RISPOSTA_XML.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "risposta_xml", PccTraccia.model().RISPOSTA_XML.getFieldType()));
				setParameter(object, "setOperazione", PccTraccia.model().OPERAZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "operazione", PccTraccia.model().OPERAZIONE.getFieldType()));
				setParameter(object, "set_value_tipoOperazione", String.class,
					jdbcParameterUtilities.readParameter(rs, "tipo_operazione", PccTraccia.model().TIPO_OPERAZIONE.getFieldType())+"");
				setParameter(object, "set_value_stato", String.class,
					jdbcParameterUtilities.readParameter(rs, "stato", PccTraccia.model().STATO.getFieldType())+"");
				setParameter(object, "setDataUltimaTrasmissione", PccTraccia.model().DATA_ULTIMA_TRASMISSIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ultima_trasmissione", PccTraccia.model().DATA_ULTIMA_TRASMISSIONE.getFieldType()));
				setParameter(object, "setDataUltimoTentativoEsito", PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "data_ultimo_tentativo_esito", PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO.getFieldType()));
				setParameter(object, "setCodiciErrore", PccTraccia.model().CODICI_ERRORE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "codici_errore", PccTraccia.model().CODICI_ERRORE.getFieldType()));
				setParameter(object, "setRispedizione", PccTraccia.model().RISPEDIZIONE.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "rispedizione", PccTraccia.model().RISPEDIZIONE.getFieldType()));
				setParameter(object, "setRispedizioneDopoQuery", PccTraccia.model().RISPEDIZIONE_DOPO_QUERY.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "rispedizione_dopo_query", PccTraccia.model().RISPEDIZIONE_DOPO_QUERY.getFieldType()));
				setParameter(object, "setRispedizioneMaxTentativi", PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "rispedizione_max_tentativi", PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI.getFieldType()));
				setParameter(object, "setRispedizioneProssimoTentativo", PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "rispedizione_prox_tentativo", PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO.getFieldType()));
				setParameter(object, "setRispedizioneNumeroTentativi", PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "rispedizione_numero_tentativi", PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI.getFieldType()));
				setParameter(object, "setRispedizioneUltimoTentativo", PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO.getFieldType(),
					jdbcParameterUtilities.readParameter(rs, "rispedizione_ultimo_tentativo", PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO.getFieldType()));
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

			if(model.equals(PccTraccia.model())){
				PccTraccia object = new PccTraccia();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"id"));
				setParameter(object, "setDataCreazione", PccTraccia.model().DATA_CREAZIONE.getFieldType(),
					this.getObjectFromMap(map,"dataCreazione"));
				setParameter(object, "setCfTrasmittente", PccTraccia.model().CF_TRASMITTENTE.getFieldType(),
					this.getObjectFromMap(map,"cfTrasmittente"));
				setParameter(object, "setVersioneApplicativa", PccTraccia.model().VERSIONE_APPLICATIVA.getFieldType(),
					this.getObjectFromMap(map,"versioneApplicativa"));
				setParameter(object, "setIdPccAmministrazione", PccTraccia.model().ID_PCC_AMMINISTRAZIONE.getFieldType(),
					this.getObjectFromMap(map,"idPccAmministrazione"));
				setParameter(object, "setIdPaTransazione", PccTraccia.model().ID_PA_TRANSAZIONE.getFieldType(),
					this.getObjectFromMap(map,"idPaTransazione"));
				setParameter(object, "setIdPaTransazioneRispedizione", PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE.getFieldType(),
					this.getObjectFromMap(map,"idPaTransazioneRispedizione"));
				setParameter(object, "setSistemaRichiedente", PccTraccia.model().SISTEMA_RICHIEDENTE.getFieldType(),
					this.getObjectFromMap(map,"sistemaRichiedente"));
				setParameter(object, "setUtenteRichiedente", PccTraccia.model().UTENTE_RICHIEDENTE.getFieldType(),
					this.getObjectFromMap(map,"utenteRichiedente"));
				setParameter(object, "setIdFattura", PccTraccia.model().ID_FATTURA.getFieldType(),
					this.getObjectFromMap(map,"idFattura"));
				setParameter(object, "setCodiceDipartimento", PccTraccia.model().CODICE_DIPARTIMENTO.getFieldType(),
					this.getObjectFromMap(map,"codiceDipartimento"));
				setParameter(object, "setRichiestaXml", PccTraccia.model().RICHIESTA_XML.getFieldType(),
					this.getObjectFromMap(map,"richiestaXml"));
				setParameter(object, "setRispostaXml", PccTraccia.model().RISPOSTA_XML.getFieldType(),
					this.getObjectFromMap(map,"rispostaXml"));
				setParameter(object, "setOperazione", PccTraccia.model().OPERAZIONE.getFieldType(),
					this.getObjectFromMap(map,"operazione"));
				setParameter(object, "set_value_tipoOperazione", String.class,
					this.getObjectFromMap(map,"tipoOperazione"));
				setParameter(object, "set_value_stato", String.class,
					this.getObjectFromMap(map,"stato"));
				setParameter(object, "setDataUltimaTrasmissione", PccTraccia.model().DATA_ULTIMA_TRASMISSIONE.getFieldType(),
					this.getObjectFromMap(map,"dataUltimaTrasmissione"));
				setParameter(object, "setDataUltimoTentativoEsito", PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO.getFieldType(),
					this.getObjectFromMap(map,"dataUltimoTentativoEsito"));
				setParameter(object, "setCodiciErrore", PccTraccia.model().CODICI_ERRORE.getFieldType(),
					this.getObjectFromMap(map,"codiciErrore"));
				setParameter(object, "setRispedizione", PccTraccia.model().RISPEDIZIONE.getFieldType(),
					this.getObjectFromMap(map,"rispedizione"));
				setParameter(object, "setRispedizioneDopoQuery", PccTraccia.model().RISPEDIZIONE_DOPO_QUERY.getFieldType(),
					this.getObjectFromMap(map,"rispedizioneDopoQuery"));
				setParameter(object, "setRispedizioneMaxTentativi", PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI.getFieldType(),
					this.getObjectFromMap(map,"rispedizioneMaxTentativi"));
				setParameter(object, "setRispedizioneProssimoTentativo", PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO.getFieldType(),
					this.getObjectFromMap(map,"rispedizioneProssimoTentativo"));
				setParameter(object, "setRispedizioneNumeroTentativi", PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI.getFieldType(),
					this.getObjectFromMap(map,"rispedizioneNumeroTentativi"));
				setParameter(object, "setRispedizioneUltimoTentativo", PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO.getFieldType(),
					this.getObjectFromMap(map,"rispedizioneUltimoTentativo"));
				return object;
			}
			
			if(model.equals(PccTraccia.model().FATTURA_ELETTRONICA)){
				FatturaElettronica object = new FatturaElettronica();
				setParameter(object, "setId", Long.class,
					this.getObjectFromMap(map,"FatturaElettronica.id"));
				setParameter(object, "set_value_formatoTrasmissione", String.class,
					this.getObjectFromMap(map,"FatturaElettronica.formatoTrasmissione"));
				setParameter(object, "setIdentificativoSdi", PccTraccia.model().FATTURA_ELETTRONICA.IDENTIFICATIVO_SDI.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.identificativoSdi"));
				setParameter(object, "setDataRicezione", PccTraccia.model().FATTURA_ELETTRONICA.DATA_RICEZIONE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.dataRicezione"));
				setParameter(object, "setNomeFile", PccTraccia.model().FATTURA_ELETTRONICA.NOME_FILE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.nomeFile"));
				setParameter(object, "setMessageId", PccTraccia.model().FATTURA_ELETTRONICA.MESSAGE_ID.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.messageId"));
				setParameter(object, "setCedentePrestatoreDenominazione", PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.cedentePrestatoreDenominazione"));
				setParameter(object, "setCedentePrestatorePaese", PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.cedentePrestatorePaese"));
				setParameter(object, "setCedentePrestatoreCodiceFiscale", PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.cedentePrestatoreCodiceFiscale"));
				setParameter(object, "setCessionarioCommittenteDenominazione", PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.cessionarioCommittenteDenominazione"));
				setParameter(object, "setCessionarioCommittentePaese", PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.cessionarioCommittentePaese"));
				setParameter(object, "setCessionarioCommittenteCodiceFiscale", PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.cessionarioCommittenteCodiceFiscale"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteDenominazione", PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.terzoIntermediarioOSoggettoEmittenteDenominazione"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittentePaese", PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.terzoIntermediarioOSoggettoEmittentePaese"));
				setParameter(object, "setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale", PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.terzoIntermediarioOSoggettoEmittenteCodiceFiscale"));
				setParameter(object, "setPosizione", PccTraccia.model().FATTURA_ELETTRONICA.POSIZIONE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.posizione"));
				setParameter(object, "setCodiceDestinatario", PccTraccia.model().FATTURA_ELETTRONICA.CODICE_DESTINATARIO.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.codiceDestinatario"));
				setParameter(object, "set_value_tipoDocumento", String.class,
					this.getObjectFromMap(map,"FatturaElettronica.tipoDocumento"));
				setParameter(object, "setDivisa", PccTraccia.model().FATTURA_ELETTRONICA.DIVISA.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.divisa"));
				setParameter(object, "setData", PccTraccia.model().FATTURA_ELETTRONICA.DATA.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.data"));
				setParameter(object, "setAnno", PccTraccia.model().FATTURA_ELETTRONICA.ANNO.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.anno"));
				setParameter(object, "setNumero", PccTraccia.model().FATTURA_ELETTRONICA.NUMERO.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.numero"));
				setParameter(object, "set_value_esito", String.class,
					this.getObjectFromMap(map,"FatturaElettronica.esito"));
				setParameter(object, "setDaPagare", PccTraccia.model().FATTURA_ELETTRONICA.DA_PAGARE.getFieldType(),
						this.getObjectFromMap(map,"FatturaElettronica.daPagare"));
				setParameter(object, "setImportoTotaleDocumento", PccTraccia.model().FATTURA_ELETTRONICA.IMPORTO_TOTALE_DOCUMENTO.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.importoTotaleDocumento"));
				setParameter(object, "setImportoTotaleRiepilogo", PccTraccia.model().FATTURA_ELETTRONICA.IMPORTO_TOTALE_RIEPILOGO.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.importoTotaleRiepilogo"));
				setParameter(object, "setCausale", PccTraccia.model().FATTURA_ELETTRONICA.CAUSALE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.causale"));
				setParameter(object, "set_value_statoConsegna", String.class,
					this.getObjectFromMap(map,"FatturaElettronica.statoConsegna"));
				setParameter(object, "setDataConsegna", PccTraccia.model().FATTURA_ELETTRONICA.DATA_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.dataConsegna"));
				setParameter(object, "setDataProssimaConsegna", PccTraccia.model().FATTURA_ELETTRONICA.DATA_PROSSIMA_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.dataProssimaConsegna"));
				setParameter(object, "setTentativiConsegna", PccTraccia.model().FATTURA_ELETTRONICA.TENTATIVI_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.tentativiConsegna"));
				setParameter(object, "setDettaglioConsegna", PccTraccia.model().FATTURA_ELETTRONICA.DETTAGLIO_CONSEGNA.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.dettaglioConsegna"));
				setParameter(object, "set_value_statoProtocollazione", String.class,
					this.getObjectFromMap(map,"FatturaElettronica.statoProtocollazione"));
				setParameter(object, "setDataProtocollazione", PccTraccia.model().FATTURA_ELETTRONICA.DATA_PROTOCOLLAZIONE.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.dataProtocollazione"));
				setParameter(object, "setProtocollo", PccTraccia.model().FATTURA_ELETTRONICA.PROTOCOLLO.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.protocollo"));
				setParameter(object, "setXml", PccTraccia.model().FATTURA_ELETTRONICA.XML.getFieldType(),
					this.getObjectFromMap(map,"FatturaElettronica.xml"));
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

			if(model.equals(PccTraccia.model())){
				return new org.openspcoop2.utils.jdbc.CustomKeyGeneratorObject("pcc_tracce","id","seq_pcc_tracce","pcc_tracce_init_seq");
			}
			
			else{
				throw new ServiceException("Model ["+model.toString()+"] not supported by getKeyGeneratorObject: "+this.getClass().getName());
			}

		}catch(Exception e){
			throw new ServiceException("Model ["+model.toString()+"] occurs error in getKeyGeneratorObject: "+e.getMessage(),e);
		}
		
	}

}
