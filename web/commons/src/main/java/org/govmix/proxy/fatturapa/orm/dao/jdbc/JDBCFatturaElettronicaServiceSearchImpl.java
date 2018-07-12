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
package org.govmix.proxy.fatturapa.orm.dao.jdbc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.FatturaElettronicaFetch;
import org.openspcoop2.generic_project.beans.AliasField;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.FunctionField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.InUse;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.Union;
import org.openspcoop2.generic_project.beans.UnionExpression;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithId;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.IJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.utils.UtilsTemplate;
import org.openspcoop2.utils.sql.ISQLQueryObject;

/**     
 * JDBCFatturaElettronicaServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCFatturaElettronicaServiceSearchImpl implements IJDBCServiceSearchWithId<FatturaElettronica, IdFattura, JDBCServiceManager> {

	private FatturaElettronicaFieldConverter _fatturaElettronicaFieldConverter = null;
	public FatturaElettronicaFieldConverter getFatturaElettronicaFieldConverter() {
		if(this._fatturaElettronicaFieldConverter==null){
			this._fatturaElettronicaFieldConverter = new FatturaElettronicaFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._fatturaElettronicaFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getFatturaElettronicaFieldConverter();
	}
	
	private FatturaElettronicaFetch fatturaElettronicaFetch = new FatturaElettronicaFetch();
	public FatturaElettronicaFetch getFatturaElettronicaFetch() {
		return this.fatturaElettronicaFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getFatturaElettronicaFetch();
	}
	
	
	private JDBCServiceManager jdbcServiceManager = null;

	@Override
	public void setServiceManager(JDBCServiceManager serviceManager) throws ServiceException{
		this.jdbcServiceManager = serviceManager;
	}
	
	@Override
	public JDBCServiceManager getServiceManager() throws ServiceException{
		return this.jdbcServiceManager;
	}
	

	@Override
	public IdFattura convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, FatturaElettronica fatturaElettronica) throws NotImplementedException, ServiceException, Exception{
	
        IdFattura idFatturaElettronica = new IdFattura(fatturaElettronica.isFatturazioneAttiva());
        idFatturaElettronica.setIdentificativoSdi(fatturaElettronica.getIdentificativoSdi());
        idFatturaElettronica.setPosizione(fatturaElettronica.getPosizione());
        
        return idFatturaElettronica;
	}
	
	@Override
	public FatturaElettronica get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_fatturaElettronica = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdFatturaElettronica(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_fatturaElettronica,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_fatturaElettronica = this.findIdFatturaElettronica(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_fatturaElettronica != null && id_fatturaElettronica > 0;
		
	}
	
	@Override
	public List<IdFattura> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<IdFattura> list = new ArrayList<IdFattura>();

		try {
			List<IField> fields = new ArrayList<IField>();

			String id = "id";
			fields.add(new CustomField(id, Long.class, id, this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())));
			fields.add(FatturaElettronica.model().IDENTIFICATIVO_SDI);
			fields.add(FatturaElettronica.model().POSIZIONE);

			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

			for(Map<String, Object> map: returnMap) {
				list.add(this.convertToId(jdbcProperties, log, connection, sqlQueryObject, (FatturaElettronica)this.getFatturaElettronicaFetch().fetch(jdbcProperties.getDatabase(), FatturaElettronica.model(), map)));
			}



		} catch(NotFoundException e) {}
		return list;

	}
	
	@Override
	public List<FatturaElettronica> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {
		
		List<FatturaElettronica> list = new ArrayList<FatturaElettronica>();

		try {
			List<IField> fields = new ArrayList<IField>();

			String id = "id";
			fields.add(new CustomField(id, Long.class, id, this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())));
			fields.add(FatturaElettronica.model().FORMATO_TRASMISSIONE);
			fields.add(FatturaElettronica.model().IDENTIFICATIVO_SDI);
			fields.add(FatturaElettronica.model().POSIZIONE);
			fields.add(FatturaElettronica.model().FATTURAZIONE_ATTIVA);
			fields.add(FatturaElettronica.model().DATA_RICEZIONE);
			fields.add(FatturaElettronica.model().NOME_FILE);
			fields.add(FatturaElettronica.model().MESSAGE_ID);
			fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
			fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE);
			fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE);
			fields.add(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE);
			fields.add(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE);
			fields.add(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE);
			fields.add(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE);
			fields.add(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE);
			fields.add(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE);
			fields.add(FatturaElettronica.model().CODICE_DESTINATARIO);
			fields.add(FatturaElettronica.model().TIPO_DOCUMENTO);
			fields.add(FatturaElettronica.model().DIVISA);
			fields.add(FatturaElettronica.model().DATA);
			fields.add(FatturaElettronica.model().ANNO);
			fields.add(FatturaElettronica.model().NUMERO);
			fields.add(FatturaElettronica.model().ESITO);
			fields.add(FatturaElettronica.model().DA_PAGARE);
			fields.add(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO);
			fields.add(FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO);
			fields.add(FatturaElettronica.model().CAUSALE);
			fields.add(FatturaElettronica.model().STATO_CONSEGNA);
			fields.add(FatturaElettronica.model().DATA_CONSEGNA);
			fields.add(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA);
			fields.add(FatturaElettronica.model().DATA_SCADENZA);
			fields.add(FatturaElettronica.model().TENTATIVI_CONSEGNA);
			fields.add(FatturaElettronica.model().DETTAGLIO_CONSEGNA);
			fields.add(FatturaElettronica.model().STATO_PROTOCOLLAZIONE);
			fields.add(FatturaElettronica.model().DATA_PROTOCOLLAZIONE);
			fields.add(FatturaElettronica.model().PROTOCOLLO);
			fields.add(FatturaElettronica.model().XML);
			String idDecorrenzaTerminiField = "id_notifica_decorrenza_termini";
			fields.add(new CustomField(idDecorrenzaTerminiField, Long.class, idDecorrenzaTerminiField, this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())));
			String idEsitoContabilizzazioneField = "id_contabilizzazione";
			fields.add(new CustomField(idEsitoContabilizzazioneField, Long.class, idEsitoContabilizzazioneField, this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())));
			String idEsitoScadenzaField = "id_scadenza";
			fields.add(new CustomField(idEsitoScadenzaField, Long.class, idEsitoScadenzaField, this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())));
			
			
			String lottoTable = "LottoFatture";
			String lottoId = lottoTable + ".id";
			fields.add(new AliasField(new CustomField(lottoId, Long.class, "id", this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model().LOTTO_FATTURE)), "l_id"));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_TRASMISSIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.NOME_FILE, lottoTable));
			fields.add(new AliasField(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA, "l_formatoArchivio"));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.MESSAGE_ID, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CODICE_DESTINATARIO, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.XML, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_RICEZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_INSERIMENTO, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_CONSEGNA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_CONSEGNA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_PROTOCOLLAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.PROTOCOLLO, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.ID_EGOV, lottoTable));


			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

			for(Map<String, Object> map: returnMap) {
				Object idFK_fatturaElettronica_notificaDecorrenzaTermini = map.remove(idDecorrenzaTerminiField);
				Object idFK_fatturaElettronica_esitoContabilizzazione = map.remove(idEsitoContabilizzazioneField);
				Object idFK_fatturaElettronica_esitoScadenza = map.remove(idEsitoScadenzaField);

				FatturaElettronica fatturaElettronica = (FatturaElettronica)this.getFatturaElettronicaFetch().fetch(jdbcProperties.getDatabase(), FatturaElettronica.model(), map);

				LottoFatture lottoFatture = (LottoFatture)this.getFatturaElettronicaFetch().fetch(jdbcProperties.getDatabase(), FatturaElettronica.model().LOTTO_FATTURE, map);

				fatturaElettronica.setLottoFatture(lottoFatture);
				if(idFK_fatturaElettronica_notificaDecorrenzaTermini != null && idFK_fatturaElettronica_notificaDecorrenzaTermini instanceof Long) {
					
					if(idMappingResolutionBehaviour==null ||
							(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
						){
							org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini id_fatturaElettronica_notificaDecorrenzaTermini = null;
							if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
								id_fatturaElettronica_notificaDecorrenzaTermini = ((JDBCNotificaDecorrenzaTerminiServiceSearch)(this.getServiceManager().getNotificaDecorrenzaTerminiServiceSearch())).findId((Long) idFK_fatturaElettronica_notificaDecorrenzaTermini, false);
							}else{
								id_fatturaElettronica_notificaDecorrenzaTermini = new org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini();
							}
							id_fatturaElettronica_notificaDecorrenzaTermini.setId((Long) idFK_fatturaElettronica_notificaDecorrenzaTermini);
							fatturaElettronica.setIdDecorrenzaTermini(id_fatturaElettronica_notificaDecorrenzaTermini);
						}
				}
				
				if(idFK_fatturaElettronica_esitoContabilizzazione != null && idFK_fatturaElettronica_esitoContabilizzazione instanceof Long) {
					if(idMappingResolutionBehaviour==null ||
							(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
						){
							try {
								// Object _fatturaElettronica_pccTracciaTrasmissioneEsito (recupero id)
								Long idFK_fatturaElettronica_pccTracciaTrasmissioneEsito = (Long) idFK_fatturaElettronica_esitoContabilizzazione;
								
								org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito id_fatturaElettronica_pccTracciaTrasmissioneEsito = null;
								if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
									id_fatturaElettronica_pccTracciaTrasmissioneEsito = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findId(idFK_fatturaElettronica_pccTracciaTrasmissioneEsito, false);
								}else{
									id_fatturaElettronica_pccTracciaTrasmissioneEsito = new org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito();
								}
								id_fatturaElettronica_pccTracciaTrasmissioneEsito.setId(idFK_fatturaElettronica_pccTracciaTrasmissioneEsito);
								fatturaElettronica.setIdEsitoContabilizzazione(id_fatturaElettronica_pccTracciaTrasmissioneEsito);
							}catch(NotFoundException e) {}
						}
				}

				if(idFK_fatturaElettronica_esitoScadenza != null && idFK_fatturaElettronica_esitoScadenza instanceof Long) {
					if(idMappingResolutionBehaviour==null ||
							(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
						){
							try {
								Long idFK_fatturaElettronica_pccTracciaTrasmissioneEsito = (Long) idFK_fatturaElettronica_esitoScadenza;
								
								org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito id_fatturaElettronica_pccTracciaTrasmissioneEsito = null;
								if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
									id_fatturaElettronica_pccTracciaTrasmissioneEsito = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findId(idFK_fatturaElettronica_pccTracciaTrasmissioneEsito, false);
								}else{
									id_fatturaElettronica_pccTracciaTrasmissioneEsito = new org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito();
								}
								id_fatturaElettronica_pccTracciaTrasmissioneEsito.setId(idFK_fatturaElettronica_pccTracciaTrasmissioneEsito);
								fatturaElettronica.setIdEsitoScadenza(id_fatturaElettronica_pccTracciaTrasmissioneEsito);
							}catch(NotFoundException e) {}
						}
				}

				list.add(fatturaElettronica);
			}

		} catch(NotFoundException e) {}
		return list;
		
	}
	
	private IField getCustomField(IField field, String table) throws ExpressionException {
		
		String columnName = this.getFatturaElettronicaFieldConverter().toColumn(field, false);
		String aliasTableName = table.substring(0,  1);
		String aliasColumnName = aliasTableName + columnName;
		
		return new AliasField(field, aliasColumnName);
	}
	@Override
	public FatturaElettronica find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
		throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {

        long id = this.findTableId(jdbcProperties, log, connection, sqlQueryObject, expression);
        if(id>0){
        	return this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
        }else{
        	throw new NotFoundException("Entry with id["+id+"] not found");
        }
		
	}
	
	@Override
	public NonNegativeNumber count(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareCount(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model());
		
		sqlQueryObject.addSelectCountField(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_fatturaElettronica = this.findIdFatturaElettronica(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_fatturaElettronica);
		
	}

	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		List<Map<String,Object>> map = 
			this.select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, distinct, new IField[]{field});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectSingleObject(map);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,field);
		try{
		
			ISQLQueryObject sqlQueryObjectDistinct = 
						org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(distinct,sqlQueryObject, paginatedExpression, log,
												this.getFatturaElettronicaFieldConverter(), field);

			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, sqlQueryObjectDistinct);
			
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,field);
		}
	}

	@Override
	public Object aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		Map<String,Object> map = 
			this.aggregate(jdbcProperties, log, connection, sqlQueryObject, expression, new FunctionField[]{functionField});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectAggregateObject(map,functionField);
	}
	
	@Override
	public Map<String,Object> aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {													
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			List<Map<String,Object>> list = _select(jdbcProperties, log, connection, sqlQueryObject, expression);
			return list.get(0);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(expression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, expression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}
	

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(paginatedExpression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,functionField);
		}
	}
	
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return _select(jdbcProperties, log, connection, sqlQueryObject, expression, null);
	}
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression, ISQLQueryObject sqlQueryObjectDistinct) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		List<Object> listaQuery = new ArrayList<Object>();
		List<JDBCObject> listaParams = new ArrayList<JDBCObject>();
		List<Object> returnField = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSelect(jdbcProperties, log, connection, sqlQueryObject, 
        						expression, this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(),
        								listaQuery,listaParams,returnField);
		if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}
	
	@Override
	public List<Map<String,Object>> union(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnion(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}								
	}
	
	@Override
	public NonNegativeNumber unionCount(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnionCount(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(number!=null && number.longValue()>=0){
			return number;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}



	// -- ConstructorExpression	

	@Override
	public JDBCExpression newExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(this.getFatturaElettronicaFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getFatturaElettronicaFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public JDBCExpression toExpression(JDBCPaginatedExpression paginatedExpression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(paginatedExpression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public JDBCPaginatedExpression toPaginatedExpression(JDBCExpression expression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(expression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
	
	// -- DB

	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, FatturaElettronica obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, FatturaElettronica obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, FatturaElettronica obj, FatturaElettronica imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getIdDecorrenzaTermini()!=null && 
				imgSaved.getIdDecorrenzaTermini()!=null){
			obj.getIdDecorrenzaTermini().setId(imgSaved.getIdDecorrenzaTermini().getId());
		}
		if(obj.getIdEsitoContabilizzazione()!=null && 
				imgSaved.getIdEsitoContabilizzazione()!=null){
			obj.getIdEsitoContabilizzazione().setId(imgSaved.getIdEsitoContabilizzazione().getId());
		}
		if(obj.getIdEsitoScadenza()!=null && 
				imgSaved.getIdEsitoScadenza()!=null){
			obj.getIdEsitoScadenza().setId(imgSaved.getIdEsitoScadenza().getId());
		}

	}
	
	@Override
	public FatturaElettronica get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private FatturaElettronica _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		JDBCPaginatedExpression expression = this.newPaginatedExpression(log);
		CustomField idField = new CustomField("id", Long.class, "id", this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		expression.equals(idField, tableId);
		expression.offset(0);
		expression.limit(2);
		
		expression.addOrder(idField, org.openspcoop2.generic_project.expression.SortOrder.ASC);
		List<FatturaElettronica> lst = this.findAll(jdbcProperties, log, connection, sqlQueryObject, expression, idMappingResolutionBehaviour);
		
		if(lst == null || lst.size() == 0) {
			throw new NotFoundException();
		} else if(lst.size() > 1) {
			throw new MultipleResultException();
		} else {
			return lst.get(0);
		}

	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsFatturaElettronica = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObject.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().FORMATO_TRASMISSIONE,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists fatturaElettronica
		existsFatturaElettronica = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsFatturaElettronica;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{

		if(expression.inUseModel(FatturaElettronica.model().ID_DECORRENZA_TERMINI,false)){
			String tableName1 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model());
			String tableName2 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model().ID_DECORRENZA_TERMINI);
			sqlQueryObject.addWhereCondition(tableName1+".id_notifica_decorrenza_termini="+tableName2+".id");
		}

		if(expression.inUseModel(FatturaElettronica.model().DIPARTIMENTO,false)){
			String tableName1 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model());
			String tableName2 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model().DIPARTIMENTO);
			sqlQueryObject.addWhereCondition(tableName1+".codice_destinatario="+tableName2+".codice");
		}

		if(expression.inUseModel(FatturaElettronica.model().DIPARTIMENTO.ENTE,false)){
			String tableName1 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model().DIPARTIMENTO);
			String tableName2 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model().DIPARTIMENTO.ENTE);
			sqlQueryObject.addWhereCondition(tableName1+".id_ente="+tableName2+".id");

			if(!expression.inUseModel(FatturaElettronica.model().DIPARTIMENTO,false)){
				sqlQueryObject.addFromTable(tableName1);
				String tableName3 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model());
				sqlQueryObject.addWhereCondition(tableName3+".codice_destinatario="+tableName1+".codice");
			}
		}

		if(expression.inUseModel(FatturaElettronica.model().LOTTO_FATTURE,false)){
			String tableName1 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model());
			String tableName2 = this.getFatturaElettronicaFieldConverter().toAliasTable(FatturaElettronica.model().LOTTO_FATTURE);
			sqlQueryObject.addWhereCondition(tableName1+".identificativo_sdi="+tableName2+".identificativo_sdi");
			sqlQueryObject.addWhereCondition(tableName1+".fatturazione_attiva="+tableName2+".fatturazione_attiva");
		}

	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdFatturaElettronica(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		FatturaElettronicaFieldConverter converter = this.getFatturaElettronicaFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// FatturaElettronica.model()
		mapTableToPKColumn.put(converter.toTable(FatturaElettronica.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(FatturaElettronica.model()))
			));

		// FatturaElettronica.model().ID_DECORRENZA_TERMINI
		mapTableToPKColumn.put(converter.toTable(FatturaElettronica.model().ID_DECORRENZA_TERMINI),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(FatturaElettronica.model().ID_DECORRENZA_TERMINI))
			));

		// FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE
		mapTableToPKColumn.put(converter.toTable(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(FatturaElettronica.model().ID_ESITO_CONTABILIZZAZIONE))
			));

		// FatturaElettronica.model().ID_ESITO_SCADENZA
		mapTableToPKColumn.put(converter.toTable(FatturaElettronica.model().ID_ESITO_SCADENZA),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(FatturaElettronica.model().ID_ESITO_SCADENZA))
			));

        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getFatturaElettronicaFieldConverter(), FatturaElettronica.model(), objectIdClass, listaQuery);
		if(res!=null && (((Long) res).longValue()>0) ){
			return ((Long) res).longValue();
		}
		else{
			throw new NotFoundException("Not Found");
		}
		
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {
		return this._inUse(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}

	private InUse _inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {

		InUse inUse = new InUse();
		inUse.setInUse(false);
		
		/* 
		 * TODO: implement code that checks whether the object identified by the id parameter is used by other objects
		*/
		
		// Delete this line when you have implemented the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have implemented the method

        return inUse;

	}
	
	@Override
	public IdFattura findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _fatturaElettronica
		sqlQueryObjectGet.addFromTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectGet.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObjectGet.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().POSIZIONE,true));
		sqlQueryObjectGet.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().FATTURAZIONE_ATTIVA,true));
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _fatturaElettronica
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_fatturaElettronica = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_fatturaElettronica = new ArrayList<Class<?>>();
		listaFieldIdReturnType_fatturaElettronica.add(FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType());
		listaFieldIdReturnType_fatturaElettronica.add(FatturaElettronica.model().POSIZIONE.getFieldType());
		listaFieldIdReturnType_fatturaElettronica.add(FatturaElettronica.model().FATTURAZIONE_ATTIVA.getFieldType());

		org.govmix.proxy.fatturapa.orm.IdFattura id_fatturaElettronica = null;
		List<Object> listaFieldId_fatturaElettronica = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_fatturaElettronica, searchParams_fatturaElettronica);
		if(listaFieldId_fatturaElettronica==null || listaFieldId_fatturaElettronica.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			// set _fatturaElettronica
			id_fatturaElettronica = new org.govmix.proxy.fatturapa.orm.IdFattura((Boolean)listaFieldId_fatturaElettronica.get(2));
			id_fatturaElettronica.setIdentificativoSdi((Integer)listaFieldId_fatturaElettronica.get(0));
			id_fatturaElettronica.setPosizione((Integer)listaFieldId_fatturaElettronica.get(1));
		}
		
		return id_fatturaElettronica;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdFatturaElettronica(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	public int nativeUpdate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeUpdate(jdbcProperties, log, connection, sqlQueryObject,
																							sql,param);
														
	}
	
	protected Long findIdFatturaElettronica(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities =
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		FatturaElettronicaFieldConverter fatturaFieldConverter = new FatturaElettronicaFieldConverter(jdbcProperties.getDatabase());

		// Object _fatturaElettronica
		sqlQueryObjectGet.addFromTable(fatturaFieldConverter.toTable(FatturaElettronica.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);

		sqlQueryObjectGet.addWhereCondition(fatturaFieldConverter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(fatturaFieldConverter.toColumn(FatturaElettronica.model().POSIZIONE,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(fatturaFieldConverter.toColumn(FatturaElettronica.model().FATTURAZIONE_ATTIVA,true)+"=?");

		// Recupero _fatturaElettronica
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_fatturaElettronica = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] {
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdentificativoSdi(), FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getPosizione(), FatturaElettronica.model().POSIZIONE.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getFatturazioneAttiva(), FatturaElettronica.model().FATTURAZIONE_ATTIVA.getFieldType())
		};
		Long id_fatturaElettronica = null;
		try{
			id_fatturaElettronica = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
					Long.class, searchParams_fatturaElettronica);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_fatturaElettronica==null || id_fatturaElettronica<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}

		return id_fatturaElettronica;

	}
}
