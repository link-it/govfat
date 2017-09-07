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
package org.govmix.proxy.fatturapa.orm.dao.jdbc;

import java.sql.Connection;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.slf4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithoutId;
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

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCNotificaEsitoCommittenteServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCNotificaEsitoCommittenteServiceImpl extends JDBCNotificaEsitoCommittenteServiceSearchImpl
	implements IJDBCServiceCRUDWithoutId<NotificaEsitoCommittente, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _fatturaElettronica
		Long id_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_fatturaElettronica = null;
		idLogic_fatturaElettronica = notificaEsitoCommittente.getIdFattura();
		if(idLogic_fatturaElettronica!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findTableId(idLogic_fatturaElettronica, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_fatturaElettronica = idLogic_fatturaElettronica.getId();
				if(id_fatturaElettronica==null || id_fatturaElettronica<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _utente
		Long id_utente = null;
		org.govmix.proxy.fatturapa.orm.IdUtente idLogic_utente = null;
		idLogic_utente = notificaEsitoCommittente.getUtente();
		if(idLogic_utente!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_utente = ((JDBCUtenteServiceSearch)(this.getServiceManager().getUtenteServiceSearch())).findTableId(idLogic_utente, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_utente = idLogic_utente.getId();
				if(id_utente==null || id_utente<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object notificaEsitoCommittente
		sqlQueryObjectInsert.addInsertTable(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()));
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().NUMERO_FATTURA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().ANNO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().POSIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().ESITO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DESCRIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().NOME_FILE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().MODALITA_BATCH,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_INVIO_ENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_INVIO_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO_NOTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO_XML,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().XML,false),"?");
		sqlQueryObjectInsert.addInsertField("id_fattura_elettronica","?");
		sqlQueryObjectInsert.addInsertField("id_utente","?");

		// Insert notificaEsitoCommittente
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getNotificaEsitoCommittenteFetch().getKeyGeneratorObject(NotificaEsitoCommittente.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getIdentificativoSdi(),NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getNumeroFattura(),NotificaEsitoCommittente.model().NUMERO_FATTURA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getAnno(),NotificaEsitoCommittente.model().ANNO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getPosizione(),NotificaEsitoCommittente.model().POSIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getEsito(),NotificaEsitoCommittente.model().ESITO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getDescrizione(),NotificaEsitoCommittente.model().DESCRIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getMessageIdCommittente(),NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getNomeFile(),NotificaEsitoCommittente.model().NOME_FILE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getModalitaBatch(),NotificaEsitoCommittente.model().MODALITA_BATCH.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getDataInvioEnte(),NotificaEsitoCommittente.model().DATA_INVIO_ENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getDataInvioSdi(),NotificaEsitoCommittente.model().DATA_INVIO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getStatoConsegnaSdi(),NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getDataUltimaConsegnaSdi(),NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getDataProssimaConsegnaSdi(),NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getTentativiConsegnaSdi(),NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getScarto(),NotificaEsitoCommittente.model().SCARTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getScartoNote(),NotificaEsitoCommittente.model().SCARTO_NOTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getScartoXml(),NotificaEsitoCommittente.model().SCARTO_XML.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaEsitoCommittente.getXml(),NotificaEsitoCommittente.model().XML.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_fatturaElettronica,Long.class),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_utente,Long.class)
		);
		notificaEsitoCommittente.setId(id);

	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		Long tableId = notificaEsitoCommittente.getId();
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, notificaEsitoCommittente, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, NotificaEsitoCommittente notificaEsitoCommittente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _notificaEsitoCommittente_fatturaElettronica
		Long id_notificaEsitoCommittente_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_notificaEsitoCommittente_fatturaElettronica = null;
		idLogic_notificaEsitoCommittente_fatturaElettronica = notificaEsitoCommittente.getIdFattura();
		if(idLogic_notificaEsitoCommittente_fatturaElettronica!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_notificaEsitoCommittente_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findTableId(idLogic_notificaEsitoCommittente_fatturaElettronica, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_notificaEsitoCommittente_fatturaElettronica = idLogic_notificaEsitoCommittente_fatturaElettronica.getId();
				if(id_notificaEsitoCommittente_fatturaElettronica==null || id_notificaEsitoCommittente_fatturaElettronica<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _notificaEsitoCommittente_utente
		Long id_notificaEsitoCommittente_utente = null;
		org.govmix.proxy.fatturapa.orm.IdUtente idLogic_notificaEsitoCommittente_utente = null;
		idLogic_notificaEsitoCommittente_utente = notificaEsitoCommittente.getUtente();
		if(idLogic_notificaEsitoCommittente_utente!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_notificaEsitoCommittente_utente = ((JDBCUtenteServiceSearch)(this.getServiceManager().getUtenteServiceSearch())).findTableId(idLogic_notificaEsitoCommittente_utente, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_notificaEsitoCommittente_utente = idLogic_notificaEsitoCommittente_utente.getId();
				if(id_notificaEsitoCommittente_utente==null || id_notificaEsitoCommittente_utente<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object notificaEsitoCommittente
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()));
		boolean isUpdate_notificaEsitoCommittente = true;
		java.util.List<JDBCObject> lstObjects_notificaEsitoCommittente = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getIdentificativoSdi(), NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().NUMERO_FATTURA,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getNumeroFattura(), NotificaEsitoCommittente.model().NUMERO_FATTURA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().ANNO,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getAnno(), NotificaEsitoCommittente.model().ANNO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().POSIZIONE,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getPosizione(), NotificaEsitoCommittente.model().POSIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().ESITO,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getEsito(), NotificaEsitoCommittente.model().ESITO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DESCRIZIONE,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getDescrizione(), NotificaEsitoCommittente.model().DESCRIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getMessageIdCommittente(), NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().NOME_FILE,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getNomeFile(), NotificaEsitoCommittente.model().NOME_FILE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().MODALITA_BATCH,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getModalitaBatch(), NotificaEsitoCommittente.model().MODALITA_BATCH.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_INVIO_ENTE,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getDataInvioEnte(), NotificaEsitoCommittente.model().DATA_INVIO_ENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_INVIO_SDI,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getDataInvioSdi(), NotificaEsitoCommittente.model().DATA_INVIO_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getStatoConsegnaSdi(), NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getDataUltimaConsegnaSdi(), NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getDataProssimaConsegnaSdi(), NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getTentativiConsegnaSdi(), NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getScarto(), NotificaEsitoCommittente.model().SCARTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO_NOTE,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getScartoNote(), NotificaEsitoCommittente.model().SCARTO_NOTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO_XML,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getScartoXml(), NotificaEsitoCommittente.model().SCARTO_XML.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().XML,false), "?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(notificaEsitoCommittente.getXml(), NotificaEsitoCommittente.model().XML.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_fattura_elettronica","?");
		}
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_utente","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_notificaEsitoCommittente.add(new JDBCObject(id_notificaEsitoCommittente_fatturaElettronica, Long.class));
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_notificaEsitoCommittente.add(new JDBCObject(id_notificaEsitoCommittente_utente, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_notificaEsitoCommittente.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_notificaEsitoCommittente) {
			// Update notificaEsitoCommittente
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_notificaEsitoCommittente.toArray(new JDBCObject[]{}));
		}

	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, notificaEsitoCommittente),
				this.getNotificaEsitoCommittenteFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, notificaEsitoCommittente),
				this.getNotificaEsitoCommittenteFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, notificaEsitoCommittente),
				this.getNotificaEsitoCommittenteFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getNotificaEsitoCommittenteFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getNotificaEsitoCommittenteFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getNotificaEsitoCommittenteFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		Long id = notificaEsitoCommittente.getId();
		if(id != null && this.exists(jdbcProperties, log, connection, sqlQueryObject, id)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, notificaEsitoCommittente,idMappingResolutionBehaviour);		
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, notificaEsitoCommittente,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, NotificaEsitoCommittente notificaEsitoCommittente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, notificaEsitoCommittente,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, notificaEsitoCommittente,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if(notificaEsitoCommittente.getId()==null){
			throw new Exception("Parameter "+notificaEsitoCommittente.getClass().getName()+".id is null");
		}
		if(notificaEsitoCommittente.getId()<=0){
			throw new Exception("Parameter "+notificaEsitoCommittente.getClass().getName()+".id is less equals 0");
		}
		longId = notificaEsitoCommittente.getId();
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object notificaEsitoCommittente
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete notificaEsitoCommittente
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getNotificaEsitoCommittenteFieldConverter()));

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
