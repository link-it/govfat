/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.IdTrasmissione;
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

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccTracciaTrasmissioneServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccTracciaTrasmissioneServiceImpl extends JDBCPccTracciaTrasmissioneServiceSearchImpl
	implements IJDBCServiceCRUDWithId<PccTracciaTrasmissione, IdTrasmissione, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTracciaTrasmissione pccTracciaTrasmissione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _pccTraccia
		Long id_pccTraccia = null;
		org.govmix.proxy.fatturapa.orm.IdTraccia idLogic_pccTraccia = null;
		idLogic_pccTraccia = pccTracciaTrasmissione.getIdTraccia();
		if(idLogic_pccTraccia!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccTraccia = ((JDBCPccTracciaServiceSearch)(this.getServiceManager().getPccTracciaServiceSearch())).findTableId(idLogic_pccTraccia, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccTraccia = idLogic_pccTraccia.getId();
				if(id_pccTraccia==null || id_pccTraccia<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccTracciaTrasmissione
		sqlQueryObjectInsert.addInsertTable(this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().TS_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().ID_PCC_TRANSAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().ESITO_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().STATO_ESITO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().GDO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().DATA_FINE_ELABORAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().DETTAGLIO_ERRORE_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().ID_EGOV_RICHIESTA,false),"?");
		sqlQueryObjectInsert.addInsertField("id_pcc_traccia","?");

		// Insert pccTracciaTrasmissione
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccTracciaTrasmissioneFetch().getKeyGeneratorObject(PccTracciaTrasmissione.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getTsTrasmissione(),PccTracciaTrasmissione.model().TS_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getIdPccTransazione(),PccTracciaTrasmissione.model().ID_PCC_TRANSAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getEsitoTrasmissione(),PccTracciaTrasmissione.model().ESITO_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getStatoEsito(),PccTracciaTrasmissione.model().STATO_ESITO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getGdo(),PccTracciaTrasmissione.model().GDO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getDataFineElaborazione(),PccTracciaTrasmissione.model().DATA_FINE_ELABORAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getDettaglioErroreTrasmissione(),PccTracciaTrasmissione.model().DETTAGLIO_ERRORE_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissione.getIdEgovRichiesta(),PccTracciaTrasmissione.model().ID_EGOV_RICHIESTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_pccTraccia,Long.class)
		);
		pccTracciaTrasmissione.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissione oldId, PccTracciaTrasmissione pccTracciaTrasmissione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdPccTracciaTrasmissione(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = pccTracciaTrasmissione.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: pccTracciaTrasmissione.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			pccTracciaTrasmissione.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccTracciaTrasmissione, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTracciaTrasmissione pccTracciaTrasmissione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccTracciaTrasmissione_pccTraccia
		Long id_pccTracciaTrasmissione_pccTraccia = null;
		org.govmix.proxy.fatturapa.orm.IdTraccia idLogic_pccTracciaTrasmissione_pccTraccia = null;
		idLogic_pccTracciaTrasmissione_pccTraccia = pccTracciaTrasmissione.getIdTraccia();
		if(idLogic_pccTracciaTrasmissione_pccTraccia!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccTracciaTrasmissione_pccTraccia = ((JDBCPccTracciaServiceSearch)(this.getServiceManager().getPccTracciaServiceSearch())).findTableId(idLogic_pccTracciaTrasmissione_pccTraccia, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccTracciaTrasmissione_pccTraccia = idLogic_pccTracciaTrasmissione_pccTraccia.getId();
				if(id_pccTracciaTrasmissione_pccTraccia==null || id_pccTracciaTrasmissione_pccTraccia<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccTracciaTrasmissione
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()));
		boolean isUpdate_pccTracciaTrasmissione = true;
		java.util.List<JDBCObject> lstObjects_pccTracciaTrasmissione = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().TS_TRASMISSIONE,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getTsTrasmissione(), PccTracciaTrasmissione.model().TS_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().ID_PCC_TRANSAZIONE,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getIdPccTransazione(), PccTracciaTrasmissione.model().ID_PCC_TRANSAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().ESITO_TRASMISSIONE,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getEsitoTrasmissione(), PccTracciaTrasmissione.model().ESITO_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().STATO_ESITO,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getStatoEsito(), PccTracciaTrasmissione.model().STATO_ESITO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().GDO,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getGdo(), PccTracciaTrasmissione.model().GDO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().DATA_FINE_ELABORAZIONE,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getDataFineElaborazione(), PccTracciaTrasmissione.model().DATA_FINE_ELABORAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().DETTAGLIO_ERRORE_TRASMISSIONE,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getDettaglioErroreTrasmissione(), PccTracciaTrasmissione.model().DETTAGLIO_ERRORE_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneFieldConverter().toColumn(PccTracciaTrasmissione.model().ID_EGOV_RICHIESTA,false), "?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(pccTracciaTrasmissione.getIdEgovRichiesta(), PccTracciaTrasmissione.model().ID_EGOV_RICHIESTA.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_pcc_traccia","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccTracciaTrasmissione.add(new JDBCObject(id_pccTracciaTrasmissione_pccTraccia, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccTracciaTrasmissione.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccTracciaTrasmissione) {
			// Update pccTracciaTrasmissione
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccTracciaTrasmissione.toArray(new JDBCObject[]{}));
		}

	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissione id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaTrasmissioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissione id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaTrasmissioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissione id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaTrasmissioneFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaTrasmissioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaTrasmissioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaTrasmissioneFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissione oldId, PccTracciaTrasmissione pccTracciaTrasmissione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, pccTracciaTrasmissione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccTracciaTrasmissione,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTracciaTrasmissione pccTracciaTrasmissione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccTracciaTrasmissione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccTracciaTrasmissione,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTracciaTrasmissione pccTracciaTrasmissione) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (pccTracciaTrasmissione.getId()!=null) && (pccTracciaTrasmissione.getId()>0) ){
			longId = pccTracciaTrasmissione.getId();
		}
		else{
			IdTrasmissione idPccTracciaTrasmissione = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccTracciaTrasmissione);
			longId = this.findIdPccTracciaTrasmissione(jdbcProperties,log,connection,sqlQueryObject,idPccTracciaTrasmissione,false);
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
		

		// Object pccTracciaTrasmissione
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccTracciaTrasmissioneFieldConverter().toTable(PccTracciaTrasmissione.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccTracciaTrasmissione
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissione idPccTracciaTrasmissione) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdPccTracciaTrasmissione(jdbcProperties, log, connection, sqlQueryObject, idPccTracciaTrasmissione, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccTracciaTrasmissioneFieldConverter()));

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
