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

import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccDipartimentoOperazioneServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccDipartimentoOperazioneServiceImpl extends JDBCPccDipartimentoOperazioneServiceSearchImpl
	implements IJDBCServiceCRUDWithoutId<PccDipartimentoOperazione, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccDipartimentoOperazione pccDipartimentoOperazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _dipartimento
		Long id_dipartimento = null;
		org.govmix.proxy.fatturapa.orm.IdDipartimento idLogic_dipartimento = null;
		idLogic_dipartimento = pccDipartimentoOperazione.getIdDipartimento();
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

		// Object _pccOperazione
		Long id_pccOperazione = null;
		org.govmix.proxy.fatturapa.orm.IdOperazione idLogic_pccOperazione = null;
		idLogic_pccOperazione = pccDipartimentoOperazione.getIdOperazione();
		if(idLogic_pccOperazione!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccOperazione = ((JDBCPccOperazioneServiceSearch)(this.getServiceManager().getPccOperazioneServiceSearch())).findTableId(idLogic_pccOperazione, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccOperazione = idLogic_pccOperazione.getId();
				if(id_pccOperazione==null || id_pccOperazione<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccDipartimentoOperazione
		sqlQueryObjectInsert.addInsertTable(this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()));
		sqlQueryObjectInsert.addInsertField("id_dipartimento","?");
		sqlQueryObjectInsert.addInsertField("id_pcc_operazione","?");

		// Insert pccDipartimentoOperazione
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccDipartimentoOperazioneFetch().getKeyGeneratorObject(PccDipartimentoOperazione.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_dipartimento,Long.class),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_pccOperazione,Long.class)
		);
		pccDipartimentoOperazione.setId(id);

	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccDipartimentoOperazione pccDipartimentoOperazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		Long tableId = pccDipartimentoOperazione.getId();
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccDipartimentoOperazione, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccDipartimentoOperazione pccDipartimentoOperazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccDipartimentoOperazione_dipartimento
		Long id_pccDipartimentoOperazione_dipartimento = null;
		org.govmix.proxy.fatturapa.orm.IdDipartimento idLogic_pccDipartimentoOperazione_dipartimento = null;
		idLogic_pccDipartimentoOperazione_dipartimento = pccDipartimentoOperazione.getIdDipartimento();
		if(idLogic_pccDipartimentoOperazione_dipartimento!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccDipartimentoOperazione_dipartimento = ((JDBCDipartimentoServiceSearch)(this.getServiceManager().getDipartimentoServiceSearch())).findTableId(idLogic_pccDipartimentoOperazione_dipartimento, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccDipartimentoOperazione_dipartimento = idLogic_pccDipartimentoOperazione_dipartimento.getId();
				if(id_pccDipartimentoOperazione_dipartimento==null || id_pccDipartimentoOperazione_dipartimento<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _pccDipartimentoOperazione_pccOperazione
		Long id_pccDipartimentoOperazione_pccOperazione = null;
		org.govmix.proxy.fatturapa.orm.IdOperazione idLogic_pccDipartimentoOperazione_pccOperazione = null;
		idLogic_pccDipartimentoOperazione_pccOperazione = pccDipartimentoOperazione.getIdOperazione();
		if(idLogic_pccDipartimentoOperazione_pccOperazione!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccDipartimentoOperazione_pccOperazione = ((JDBCPccOperazioneServiceSearch)(this.getServiceManager().getPccOperazioneServiceSearch())).findTableId(idLogic_pccDipartimentoOperazione_pccOperazione, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccDipartimentoOperazione_pccOperazione = idLogic_pccDipartimentoOperazione_pccOperazione.getId();
				if(id_pccDipartimentoOperazione_pccOperazione==null || id_pccDipartimentoOperazione_pccOperazione<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccDipartimentoOperazione
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()));
		boolean isUpdate_pccDipartimentoOperazione = true;
		java.util.List<JDBCObject> lstObjects_pccDipartimentoOperazione = new java.util.ArrayList<JDBCObject>();
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_dipartimento","?");
		}
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_pcc_operazione","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccDipartimentoOperazione.add(new JDBCObject(id_pccDipartimentoOperazione_dipartimento, Long.class));
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccDipartimentoOperazione.add(new JDBCObject(id_pccDipartimentoOperazione_pccOperazione, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccDipartimentoOperazione.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccDipartimentoOperazione) {
			// Update pccDipartimentoOperazione
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccDipartimentoOperazione.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccDipartimentoOperazione pccDipartimentoOperazione, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccDipartimentoOperazione),
				this.getPccDipartimentoOperazioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccDipartimentoOperazione pccDipartimentoOperazione, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccDipartimentoOperazione),
				this.getPccDipartimentoOperazioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccDipartimentoOperazione pccDipartimentoOperazione, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccDipartimentoOperazione),
				this.getPccDipartimentoOperazioneFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccDipartimentoOperazioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccDipartimentoOperazioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccDipartimentoOperazioneFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccDipartimentoOperazione pccDipartimentoOperazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		Long id = pccDipartimentoOperazione.getId();
		if(id != null && this.exists(jdbcProperties, log, connection, sqlQueryObject, id)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, pccDipartimentoOperazione,idMappingResolutionBehaviour);		
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccDipartimentoOperazione,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccDipartimentoOperazione pccDipartimentoOperazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccDipartimentoOperazione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccDipartimentoOperazione,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccDipartimentoOperazione pccDipartimentoOperazione) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if(pccDipartimentoOperazione.getId()==null){
			throw new Exception("Parameter "+pccDipartimentoOperazione.getClass().getName()+".id is null");
		}
		if(pccDipartimentoOperazione.getId()<=0){
			throw new Exception("Parameter "+pccDipartimentoOperazione.getClass().getName()+".id is less equals 0");
		}
		longId = pccDipartimentoOperazione.getId();
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object pccDipartimentoOperazione
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccDipartimentoOperazioneFieldConverter().toTable(PccDipartimentoOperazione.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccDipartimentoOperazione
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccDipartimentoOperazioneFieldConverter()));

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
