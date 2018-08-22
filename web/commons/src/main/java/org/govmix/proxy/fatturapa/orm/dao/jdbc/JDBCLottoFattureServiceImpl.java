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

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.apache.log4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.beans.UpdateModel;

import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;

import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;

import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCLottoFattureServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCLottoFattureServiceImpl extends JDBCLottoFattureServiceSearchImpl
	implements IJDBCServiceCRUDWithId<LottoFatture, IdLotto, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, LottoFatture lottoFatture, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _sip
		Long id_sip = null;
		org.govmix.proxy.fatturapa.orm.IdSip idLogic_sip = null;
		idLogic_sip = lottoFatture.getIdSIP();
		if(idLogic_sip!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_sip = ((JDBCSIPServiceSearch)(this.getServiceManager().getSIPServiceSearch())).findTableId(idLogic_sip, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_sip = idLogic_sip.getId();
				if(id_sip==null || id_sip<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object lottoFatture
		sqlQueryObjectInsert.addInsertTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FORMATO_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().IDENTIFICATIVO_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().NOME_FILE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().MESSAGE_ID,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_NOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_COGNOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_CODICE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_PAESE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CODICE_DESTINATARIO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().XML,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FATTURAZIONE_ATTIVA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TIPI_COMUNICAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_ULTIMA_ELABORAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DETTAGLIO_ELABORAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TENTATIVI_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_RICEZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_INSERIMENTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DETTAGLIO_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DOMINIO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().SOTTODOMINIO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().PAGO_PA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().PROTOCOLLO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().ID_EGOV,false),"?");
		sqlQueryObjectInsert.addInsertField("id_sip","?");

		// Insert lottoFatture
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getLottoFattureFetch().getKeyGeneratorObject(LottoFatture.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getFormatoTrasmissione(),LottoFatture.model().FORMATO_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getIdentificativoSdi(),LottoFatture.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getNomeFile(),LottoFatture.model().NOME_FILE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getFormatoArchivioInvioFattura(),LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getMessageId(),LottoFatture.model().MESSAGE_ID.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCedentePrestatoreDenominazione(),LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCedentePrestatoreNome(),LottoFatture.model().CEDENTE_PRESTATORE_NOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCedentePrestatoreCognome(),LottoFatture.model().CEDENTE_PRESTATORE_COGNOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCedentePrestatoreCodice(),LottoFatture.model().CEDENTE_PRESTATORE_CODICE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCedentePrestatorePaese(),LottoFatture.model().CEDENTE_PRESTATORE_PAESE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCedentePrestatoreCodiceFiscale(),LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCessionarioCommittenteDenominazione(),LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCessionarioCommittenteNome(),LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCessionarioCommittenteCognome(),LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCessionarioCommittenteCodice(),LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCessionarioCommittentePaese(),LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCessionarioCommittenteCodiceFiscale(),LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteDenominazione(),LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteNome(),LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteCognome(),LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteCodice(),LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittentePaese(),LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(),LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getCodiceDestinatario(),LottoFatture.model().CODICE_DESTINATARIO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getXml(),LottoFatture.model().XML.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getFatturazioneAttiva(),LottoFatture.model().FATTURAZIONE_ATTIVA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getStatoElaborazioneInUscita(),LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTipiComunicazione(),LottoFatture.model().TIPI_COMUNICAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDataUltimaElaborazione(),LottoFatture.model().DATA_ULTIMA_ELABORAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDettaglioElaborazione(),LottoFatture.model().DETTAGLIO_ELABORAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDataProssimaElaborazione(),LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getTentativiConsegna(),LottoFatture.model().TENTATIVI_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDataRicezione(),LottoFatture.model().DATA_RICEZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getStatoInserimento(),LottoFatture.model().STATO_INSERIMENTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getStatoConsegna(),LottoFatture.model().STATO_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDataConsegna(),LottoFatture.model().DATA_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDettaglioConsegna(),LottoFatture.model().DETTAGLIO_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getStatoProtocollazione(),LottoFatture.model().STATO_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDominio(),LottoFatture.model().DOMINIO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getSottodominio(),LottoFatture.model().SOTTODOMINIO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getPagoPA(),LottoFatture.model().PAGO_PA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getDataProtocollazione(),LottoFatture.model().DATA_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getProtocollo(),LottoFatture.model().PROTOCOLLO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(lottoFatture.getIdEgov(),LottoFatture.model().ID_EGOV.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_sip,Long.class)
		);
		lottoFatture.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto oldId, LottoFatture lottoFatture, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdLottoFatture(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = lottoFatture.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: lottoFatture.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			lottoFatture.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, lottoFatture, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, LottoFatture lottoFatture, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
		
		boolean setIdMappingResolutionBehaviour = 
			(idMappingResolutionBehaviour==null) ||
			org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) ||
			org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour);
			

		// Object _lottoFatture_sip
		Long id_lottoFatture_sip = null;
		org.govmix.proxy.fatturapa.orm.IdSip idLogic_lottoFatture_sip = null;
		idLogic_lottoFatture_sip = lottoFatture.getIdSIP();
		if(idLogic_lottoFatture_sip!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_lottoFatture_sip = ((JDBCSIPServiceSearch)(this.getServiceManager().getSIPServiceSearch())).findTableId(idLogic_lottoFatture_sip, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_lottoFatture_sip = idLogic_lottoFatture_sip.getId();
				if(id_lottoFatture_sip==null || id_lottoFatture_sip<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object lottoFatture
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		boolean isUpdate_lottoFatture = true;
		java.util.List<JDBCObject> lstObjects_lottoFatture = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FORMATO_TRASMISSIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getFormatoTrasmissione(), LottoFatture.model().FORMATO_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().IDENTIFICATIVO_SDI,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getIdentificativoSdi(), LottoFatture.model().IDENTIFICATIVO_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().NOME_FILE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getNomeFile(), LottoFatture.model().NOME_FILE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getFormatoArchivioInvioFattura(), LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().MESSAGE_ID,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getMessageId(), LottoFatture.model().MESSAGE_ID.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCedentePrestatoreDenominazione(), LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_NOME,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCedentePrestatoreNome(), LottoFatture.model().CEDENTE_PRESTATORE_NOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_COGNOME,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCedentePrestatoreCognome(), LottoFatture.model().CEDENTE_PRESTATORE_COGNOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_CODICE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCedentePrestatoreCodice(), LottoFatture.model().CEDENTE_PRESTATORE_CODICE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_PAESE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCedentePrestatorePaese(), LottoFatture.model().CEDENTE_PRESTATORE_PAESE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCedentePrestatoreCodiceFiscale(), LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCessionarioCommittenteDenominazione(), LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCessionarioCommittenteNome(), LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCessionarioCommittenteCognome(), LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCessionarioCommittenteCodice(), LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCessionarioCommittentePaese(), LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCessionarioCommittenteCodiceFiscale(), LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteDenominazione(), LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteNome(), LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteCognome(), LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteCodice(), LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittentePaese(), LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(), LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CODICE_DESTINATARIO,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getCodiceDestinatario(), LottoFatture.model().CODICE_DESTINATARIO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().XML,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getXml(), LottoFatture.model().XML.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FATTURAZIONE_ATTIVA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getFatturazioneAttiva(), LottoFatture.model().FATTURAZIONE_ATTIVA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getStatoElaborazioneInUscita(), LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TIPI_COMUNICAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTipiComunicazione(), LottoFatture.model().TIPI_COMUNICAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_ULTIMA_ELABORAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDataUltimaElaborazione(), LottoFatture.model().DATA_ULTIMA_ELABORAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DETTAGLIO_ELABORAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDettaglioElaborazione(), LottoFatture.model().DETTAGLIO_ELABORAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDataProssimaElaborazione(), LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TENTATIVI_CONSEGNA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getTentativiConsegna(), LottoFatture.model().TENTATIVI_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_RICEZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDataRicezione(), LottoFatture.model().DATA_RICEZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_INSERIMENTO,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getStatoInserimento(), LottoFatture.model().STATO_INSERIMENTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_CONSEGNA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getStatoConsegna(), LottoFatture.model().STATO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_CONSEGNA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDataConsegna(), LottoFatture.model().DATA_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DETTAGLIO_CONSEGNA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDettaglioConsegna(), LottoFatture.model().DETTAGLIO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_PROTOCOLLAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getStatoProtocollazione(), LottoFatture.model().STATO_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DOMINIO,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDominio(), LottoFatture.model().DOMINIO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().SOTTODOMINIO,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getSottodominio(), LottoFatture.model().SOTTODOMINIO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().PAGO_PA,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getPagoPA(), LottoFatture.model().PAGO_PA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_PROTOCOLLAZIONE,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getDataProtocollazione(), LottoFatture.model().DATA_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().PROTOCOLLO,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getProtocollo(), LottoFatture.model().PROTOCOLLO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().ID_EGOV,false), "?");
		lstObjects_lottoFatture.add(new JDBCObject(lottoFatture.getIdEgov(), LottoFatture.model().ID_EGOV.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_sip","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_lottoFatture.add(new JDBCObject(id_lottoFatture_sip, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_lottoFatture.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_lottoFatture) {
			// Update lottoFatture
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_lottoFatture.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getLottoFattureFieldConverter().toTable(LottoFatture.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getLottoFattureFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getLottoFattureFieldConverter().toTable(LottoFatture.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getLottoFattureFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getLottoFattureFieldConverter().toTable(LottoFatture.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getLottoFattureFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getLottoFattureFieldConverter().toTable(LottoFatture.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getLottoFattureFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getLottoFattureFieldConverter().toTable(LottoFatture.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getLottoFattureFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getLottoFattureFieldConverter().toTable(LottoFatture.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getLottoFattureFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto oldId, LottoFatture lottoFatture, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, lottoFatture,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, lottoFatture,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, LottoFatture lottoFatture, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, lottoFatture,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, lottoFatture,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, LottoFatture lottoFatture) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (lottoFatture.getId()!=null) && (lottoFatture.getId()>0) ){
			longId = lottoFatture.getId();
		}
		else{
			IdLotto idLottoFatture = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,lottoFatture);
			longId = this.findIdLottoFatture(jdbcProperties,log,connection,sqlQueryObject,idLottoFatture,false);
			if(longId == null){
				return; // entry not exists
			}
		}		
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object lottoFatture
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete lottoFatture
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto idLottoFatture) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdLottoFatture(jdbcProperties, log, connection, sqlQueryObject, idLottoFatture, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getLottoFattureFieldConverter()));

	}

	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {

		java.util.List<Long> lst = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, new JDBCPaginatedExpression(expression));
		
		for(Long id : lst) {
			this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		}
		
		return new NonNegativeNumber(lst.size());
	
	}



	// -- DB
	
	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotImplementedException, Exception {
		this._delete(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
}
