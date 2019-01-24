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
package org.govmix.proxy.fatturapa.orm.dao.jdbc;

import java.sql.Connection;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.apache.log4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
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

import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCNotificaDecorrenzaTerminiServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCNotificaDecorrenzaTerminiServiceImpl extends JDBCNotificaDecorrenzaTerminiServiceSearchImpl
	implements IJDBCServiceCRUDWithId<NotificaDecorrenzaTermini, IdNotificaDecorrenzaTermini, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaDecorrenzaTermini notificaDecorrenzaTermini, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _tracciaSDI
		Long id_tracciaSDI = null;
		org.govmix.proxy.fatturapa.orm.IdTracciaSdi idLogic_tracciaSDI = null;
		idLogic_tracciaSDI = notificaDecorrenzaTermini.getIdTraccia();
		if(idLogic_tracciaSDI!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_tracciaSDI = ((JDBCTracciaSDIServiceSearch)(this.getServiceManager().getTracciaSDIServiceSearch())).findTableId(idLogic_tracciaSDI, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_tracciaSDI = idLogic_tracciaSDI.getId();
				if(id_tracciaSDI==null || id_tracciaSDI<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object notificaDecorrenzaTermini
		sqlQueryObjectInsert.addInsertTable(this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()));
		sqlQueryObjectInsert.addInsertField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().NOME_FILE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().DESCRIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().MESSAGE_ID,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().NOTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().DATA_RICEZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField("id_traccia","?");

		// Insert notificaDecorrenzaTermini
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getNotificaDecorrenzaTerminiFetch().getKeyGeneratorObject(NotificaDecorrenzaTermini.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaDecorrenzaTermini.getIdentificativoSdi(),NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaDecorrenzaTermini.getNomeFile(),NotificaDecorrenzaTermini.model().NOME_FILE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaDecorrenzaTermini.getDescrizione(),NotificaDecorrenzaTermini.model().DESCRIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaDecorrenzaTermini.getMessageId(),NotificaDecorrenzaTermini.model().MESSAGE_ID.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaDecorrenzaTermini.getNote(),NotificaDecorrenzaTermini.model().NOTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(notificaDecorrenzaTermini.getDataRicezione(),NotificaDecorrenzaTermini.model().DATA_RICEZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_tracciaSDI,Long.class)
		);
		notificaDecorrenzaTermini.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini oldId, NotificaDecorrenzaTermini notificaDecorrenzaTermini, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdNotificaDecorrenzaTermini(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = notificaDecorrenzaTermini.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: notificaDecorrenzaTermini.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			notificaDecorrenzaTermini.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, notificaDecorrenzaTermini, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, NotificaDecorrenzaTermini notificaDecorrenzaTermini, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _notificaDecorrenzaTermini_tracciaSDI
		Long id_notificaDecorrenzaTermini_tracciaSDI = null;
		org.govmix.proxy.fatturapa.orm.IdTracciaSdi idLogic_notificaDecorrenzaTermini_tracciaSDI = null;
		idLogic_notificaDecorrenzaTermini_tracciaSDI = notificaDecorrenzaTermini.getIdTraccia();
		if(idLogic_notificaDecorrenzaTermini_tracciaSDI!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_notificaDecorrenzaTermini_tracciaSDI = ((JDBCTracciaSDIServiceSearch)(this.getServiceManager().getTracciaSDIServiceSearch())).findTableId(idLogic_notificaDecorrenzaTermini_tracciaSDI, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_notificaDecorrenzaTermini_tracciaSDI = idLogic_notificaDecorrenzaTermini_tracciaSDI.getId();
				if(id_notificaDecorrenzaTermini_tracciaSDI==null || id_notificaDecorrenzaTermini_tracciaSDI<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object notificaDecorrenzaTermini
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()));
		boolean isUpdate_notificaDecorrenzaTermini = true;
		java.util.List<JDBCObject> lstObjects_notificaDecorrenzaTermini = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI,false), "?");
		lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(notificaDecorrenzaTermini.getIdentificativoSdi(), NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().NOME_FILE,false), "?");
		lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(notificaDecorrenzaTermini.getNomeFile(), NotificaDecorrenzaTermini.model().NOME_FILE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().DESCRIZIONE,false), "?");
		lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(notificaDecorrenzaTermini.getDescrizione(), NotificaDecorrenzaTermini.model().DESCRIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().MESSAGE_ID,false), "?");
		lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(notificaDecorrenzaTermini.getMessageId(), NotificaDecorrenzaTermini.model().MESSAGE_ID.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().NOTE,false), "?");
		lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(notificaDecorrenzaTermini.getNote(), NotificaDecorrenzaTermini.model().NOTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getNotificaDecorrenzaTerminiFieldConverter().toColumn(NotificaDecorrenzaTermini.model().DATA_RICEZIONE,false), "?");
		lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(notificaDecorrenzaTermini.getDataRicezione(), NotificaDecorrenzaTermini.model().DATA_RICEZIONE.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_traccia","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(id_notificaDecorrenzaTermini_tracciaSDI, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_notificaDecorrenzaTermini.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_notificaDecorrenzaTermini) {
			// Update notificaDecorrenzaTermini
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_notificaDecorrenzaTermini.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getNotificaDecorrenzaTerminiFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getNotificaDecorrenzaTerminiFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getNotificaDecorrenzaTerminiFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getNotificaDecorrenzaTerminiFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getNotificaDecorrenzaTerminiFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getNotificaDecorrenzaTerminiFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini oldId, NotificaDecorrenzaTermini notificaDecorrenzaTermini, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, notificaDecorrenzaTermini,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, notificaDecorrenzaTermini,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, NotificaDecorrenzaTermini notificaDecorrenzaTermini, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, notificaDecorrenzaTermini,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, notificaDecorrenzaTermini,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (notificaDecorrenzaTermini.getId()!=null) && (notificaDecorrenzaTermini.getId()>0) ){
			longId = notificaDecorrenzaTermini.getId();
		}
		else{
			IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,notificaDecorrenzaTermini);
			longId = this.findIdNotificaDecorrenzaTermini(jdbcProperties,log,connection,sqlQueryObject,idNotificaDecorrenzaTermini,false);
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
		

		// Object notificaDecorrenzaTermini
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getNotificaDecorrenzaTerminiFieldConverter().toTable(NotificaDecorrenzaTermini.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete notificaDecorrenzaTermini
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdNotificaDecorrenzaTermini(jdbcProperties, log, connection, sqlQueryObject, idNotificaDecorrenzaTermini, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getNotificaDecorrenzaTerminiFieldConverter()));

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
