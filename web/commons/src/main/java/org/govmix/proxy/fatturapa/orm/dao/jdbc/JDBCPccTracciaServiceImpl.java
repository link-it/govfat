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

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
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

import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccTracciaServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccTracciaServiceImpl extends JDBCPccTracciaServiceSearchImpl
	implements IJDBCServiceCRUDWithId<PccTraccia, IdTraccia, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTraccia pccTraccia, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				


		// Object pccTraccia
		sqlQueryObjectInsert.addInsertTable(this.getPccTracciaFieldConverter().toTable(PccTraccia.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_CREAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CF_TRASMITTENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().VERSIONE_APPLICATIVA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PCC_AMMINISTRAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PA_TRANSAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().SISTEMA_RICHIEDENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().UTENTE_RICHIEDENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_FATTURA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CODICE_DIPARTIMENTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RICHIESTA_XML,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPOSTA_XML,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().OPERAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().TIPO_OPERAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().STATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_ULTIMA_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CODICI_ERRORE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_DOPO_QUERY,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO,false),"?");

		// Insert pccTraccia
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccTracciaFetch().getKeyGeneratorObject(PccTraccia.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getDataCreazione(),PccTraccia.model().DATA_CREAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getCfTrasmittente(),PccTraccia.model().CF_TRASMITTENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getVersioneApplicativa(),PccTraccia.model().VERSIONE_APPLICATIVA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getIdPccAmministrazione(),PccTraccia.model().ID_PCC_AMMINISTRAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getIdPaTransazione(),PccTraccia.model().ID_PA_TRANSAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getIdPaTransazioneRispedizione(),PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getSistemaRichiedente(),PccTraccia.model().SISTEMA_RICHIEDENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getUtenteRichiedente(),PccTraccia.model().UTENTE_RICHIEDENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getIdFattura(),PccTraccia.model().ID_FATTURA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getCodiceDipartimento(),PccTraccia.model().CODICE_DIPARTIMENTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRichiestaXml(),PccTraccia.model().RICHIESTA_XML.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRispostaXml(),PccTraccia.model().RISPOSTA_XML.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getOperazione(),PccTraccia.model().OPERAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getTipoOperazione(),PccTraccia.model().TIPO_OPERAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getStato(),PccTraccia.model().STATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getDataUltimaTrasmissione(),PccTraccia.model().DATA_ULTIMA_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getDataUltimoTentativoEsito(),PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getCodiciErrore(),PccTraccia.model().CODICI_ERRORE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRispedizione(),PccTraccia.model().RISPEDIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRispedizioneDopoQuery(),PccTraccia.model().RISPEDIZIONE_DOPO_QUERY.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRispedizioneMaxTentativi(),PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRispedizioneProssimoTentativo(),PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRispedizioneNumeroTentativi(),PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTraccia.getRispedizioneUltimoTentativo(),PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO.getFieldType())
		);
		pccTraccia.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia oldId, PccTraccia pccTraccia, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdPccTraccia(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = pccTraccia.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: pccTraccia.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			pccTraccia.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccTraccia, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTraccia pccTraccia, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
		
//		boolean setIdMappingResolutionBehaviour = 
//			(idMappingResolutionBehaviour==null) ||
//			org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) ||
//			org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour);
			


		// Object pccTraccia
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccTracciaFieldConverter().toTable(PccTraccia.model()));
		boolean isUpdate_pccTraccia = true;
		java.util.List<JDBCObject> lstObjects_pccTraccia = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_CREAZIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getDataCreazione(), PccTraccia.model().DATA_CREAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CF_TRASMITTENTE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getCfTrasmittente(), PccTraccia.model().CF_TRASMITTENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().VERSIONE_APPLICATIVA,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getVersioneApplicativa(), PccTraccia.model().VERSIONE_APPLICATIVA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PCC_AMMINISTRAZIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getIdPccAmministrazione(), PccTraccia.model().ID_PCC_AMMINISTRAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PA_TRANSAZIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getIdPaTransazione(), PccTraccia.model().ID_PA_TRANSAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getIdPaTransazioneRispedizione(), PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().SISTEMA_RICHIEDENTE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getSistemaRichiedente(), PccTraccia.model().SISTEMA_RICHIEDENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().UTENTE_RICHIEDENTE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getUtenteRichiedente(), PccTraccia.model().UTENTE_RICHIEDENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_FATTURA,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getIdFattura(), PccTraccia.model().ID_FATTURA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CODICE_DIPARTIMENTO,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getCodiceDipartimento(), PccTraccia.model().CODICE_DIPARTIMENTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RICHIESTA_XML,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRichiestaXml(), PccTraccia.model().RICHIESTA_XML.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPOSTA_XML,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRispostaXml(), PccTraccia.model().RISPOSTA_XML.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().OPERAZIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getOperazione(), PccTraccia.model().OPERAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().TIPO_OPERAZIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getTipoOperazione(), PccTraccia.model().TIPO_OPERAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().STATO,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getStato(), PccTraccia.model().STATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_ULTIMA_TRASMISSIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getDataUltimaTrasmissione(), PccTraccia.model().DATA_ULTIMA_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getDataUltimoTentativoEsito(), PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CODICI_ERRORE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getCodiciErrore(), PccTraccia.model().CODICI_ERRORE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRispedizione(), PccTraccia.model().RISPEDIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_DOPO_QUERY,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRispedizioneDopoQuery(), PccTraccia.model().RISPEDIZIONE_DOPO_QUERY.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRispedizioneMaxTentativi(), PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRispedizioneProssimoTentativo(), PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRispedizioneNumeroTentativi(), PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO,false), "?");
		lstObjects_pccTraccia.add(new JDBCObject(pccTraccia.getRispedizioneUltimoTentativo(), PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO.getFieldType()));
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccTraccia.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccTraccia) {
			// Update pccTraccia
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccTraccia.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter().toTable(PccTraccia.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter().toTable(PccTraccia.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter().toTable(PccTraccia.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter().toTable(PccTraccia.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter().toTable(PccTraccia.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter().toTable(PccTraccia.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia oldId, PccTraccia pccTraccia, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, pccTraccia,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccTraccia,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTraccia pccTraccia, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccTraccia,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccTraccia,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTraccia pccTraccia) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (pccTraccia.getId()!=null) && (pccTraccia.getId()>0) ){
			longId = pccTraccia.getId();
		}
		else{
			IdTraccia idPccTraccia = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccTraccia);
			longId = this.findIdPccTraccia(jdbcProperties,log,connection,sqlQueryObject,idPccTraccia,false);
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
		

		// Object pccTraccia
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccTracciaFieldConverter().toTable(PccTraccia.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccTraccia
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia idPccTraccia) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdPccTraccia(jdbcProperties, log, connection, sqlQueryObject, idPccTraccia, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccTracciaFieldConverter()));

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
