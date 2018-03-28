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

import org.govmix.proxy.fatturapa.orm.PccNotifica;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccNotificaServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccNotificaServiceImpl extends JDBCPccNotificaServiceSearchImpl
	implements IJDBCServiceCRUDWithoutId<PccNotifica, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccNotifica pccNotifica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _pccTraccia
		Long id_pccTraccia = null;
		org.govmix.proxy.fatturapa.orm.IdTraccia idLogic_pccTraccia = null;
		idLogic_pccTraccia = pccNotifica.getIdTraccia();
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

		// Object _dipartimento
		Long id_dipartimento = null;
		org.govmix.proxy.fatturapa.orm.IdDipartimento idLogic_dipartimento = null;
		idLogic_dipartimento = pccNotifica.getIdDipartimento();
		if(idLogic_dipartimento!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_dipartimento = ((JDBCDipartimentoServiceSearch)(this.getServiceManager().getDipartimentoServiceSearch())).findTableId(idLogic_dipartimento, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_dipartimento = idLogic_dipartimento.getId();
				if(id_dipartimento==null || id_dipartimento<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccNotifica
		sqlQueryObjectInsert.addInsertTable(this.getPccNotificaFieldConverter().toTable(PccNotifica.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccNotificaFieldConverter().toColumn(PccNotifica.model().STATO_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccNotificaFieldConverter().toColumn(PccNotifica.model().DATA_CREAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccNotificaFieldConverter().toColumn(PccNotifica.model().DATA_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField("id_pcc_traccia","?");
		sqlQueryObjectInsert.addInsertField("id_dipartimento","?");

		// Insert pccNotifica
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccNotificaFetch().getKeyGeneratorObject(PccNotifica.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccNotifica.getStatoConsegna(),PccNotifica.model().STATO_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccNotifica.getDataCreazione(),PccNotifica.model().DATA_CREAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccNotifica.getDataConsegna(),PccNotifica.model().DATA_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_pccTraccia,Long.class),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_dipartimento,Long.class)
		);
		pccNotifica.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccNotifica pccNotifica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		Long tableId = pccNotifica.getId();
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccNotifica, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccNotifica pccNotifica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccNotifica_pccTraccia
		Long id_pccNotifica_pccTraccia = null;
		org.govmix.proxy.fatturapa.orm.IdTraccia idLogic_pccNotifica_pccTraccia = null;
		idLogic_pccNotifica_pccTraccia = pccNotifica.getIdTraccia();
		if(idLogic_pccNotifica_pccTraccia!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccNotifica_pccTraccia = ((JDBCPccTracciaServiceSearch)(this.getServiceManager().getPccTracciaServiceSearch())).findTableId(idLogic_pccNotifica_pccTraccia, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccNotifica_pccTraccia = idLogic_pccNotifica_pccTraccia.getId();
				if(id_pccNotifica_pccTraccia==null || id_pccNotifica_pccTraccia<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _pccNotifica_dipartimento
		Long id_pccNotifica_dipartimento = null;
		org.govmix.proxy.fatturapa.orm.IdDipartimento idLogic_pccNotifica_dipartimento = null;
		idLogic_pccNotifica_dipartimento = pccNotifica.getIdDipartimento();
		if(idLogic_pccNotifica_dipartimento!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccNotifica_dipartimento = ((JDBCDipartimentoServiceSearch)(this.getServiceManager().getDipartimentoServiceSearch())).findTableId(idLogic_pccNotifica_dipartimento, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccNotifica_dipartimento = idLogic_pccNotifica_dipartimento.getId();
				if(id_pccNotifica_dipartimento==null || id_pccNotifica_dipartimento<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccNotifica
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccNotificaFieldConverter().toTable(PccNotifica.model()));
		boolean isUpdate_pccNotifica = true;
		java.util.List<JDBCObject> lstObjects_pccNotifica = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccNotificaFieldConverter().toColumn(PccNotifica.model().STATO_CONSEGNA,false), "?");
		lstObjects_pccNotifica.add(new JDBCObject(pccNotifica.getStatoConsegna(), PccNotifica.model().STATO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccNotificaFieldConverter().toColumn(PccNotifica.model().DATA_CREAZIONE,false), "?");
		lstObjects_pccNotifica.add(new JDBCObject(pccNotifica.getDataCreazione(), PccNotifica.model().DATA_CREAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccNotificaFieldConverter().toColumn(PccNotifica.model().DATA_CONSEGNA,false), "?");
		lstObjects_pccNotifica.add(new JDBCObject(pccNotifica.getDataConsegna(), PccNotifica.model().DATA_CONSEGNA.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_pcc_traccia","?");
		}
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_dipartimento","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccNotifica.add(new JDBCObject(id_pccNotifica_pccTraccia, Long.class));
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccNotifica.add(new JDBCObject(id_pccNotifica_dipartimento, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccNotifica.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccNotifica) {
			// Update pccNotifica
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccNotifica.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccNotifica pccNotifica, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccNotificaFieldConverter().toTable(PccNotifica.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccNotifica),
				this.getPccNotificaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccNotifica pccNotifica, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccNotificaFieldConverter().toTable(PccNotifica.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccNotifica),
				this.getPccNotificaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccNotifica pccNotifica, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccNotificaFieldConverter().toTable(PccNotifica.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccNotifica),
				this.getPccNotificaFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccNotificaFieldConverter().toTable(PccNotifica.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccNotificaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccNotificaFieldConverter().toTable(PccNotifica.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccNotificaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccNotificaFieldConverter().toTable(PccNotifica.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccNotificaFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccNotifica pccNotifica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		Long id = pccNotifica.getId();
		if(id != null && this.exists(jdbcProperties, log, connection, sqlQueryObject, id)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, pccNotifica,idMappingResolutionBehaviour);		
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccNotifica,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccNotifica pccNotifica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccNotifica,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccNotifica,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccNotifica pccNotifica) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if(pccNotifica.getId()==null){
			throw new Exception("Parameter "+pccNotifica.getClass().getName()+".id is null");
		}
		if(pccNotifica.getId()<=0){
			throw new Exception("Parameter "+pccNotifica.getClass().getName()+".id is less equals 0");
		}
		longId = pccNotifica.getId();
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object pccNotifica
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccNotificaFieldConverter().toTable(PccNotifica.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccNotifica
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccNotificaFieldConverter()));

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
