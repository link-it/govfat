/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
import org.govmix.proxy.fatturapa.orm.IdRispedizione;
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

import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccRispedizioneServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccRispedizioneServiceImpl extends JDBCPccRispedizioneServiceSearchImpl
	implements IJDBCServiceCRUDWithId<PccRispedizione, IdRispedizione, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccRispedizione pccRispedizione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				


		// Object pccRispedizione
		sqlQueryObjectInsert.addInsertTable(this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().MAX_NUMERO_TENTATIVI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().INTERVALLO_TENTATIVI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().CODICE_ERRORE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().ABILITATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().DESCRIZIONE_ERRORE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().TIPO_ERRORE,false),"?");

		// Insert pccRispedizione
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccRispedizioneFetch().getKeyGeneratorObject(PccRispedizione.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccRispedizione.getMaxNumeroTentativi(),PccRispedizione.model().MAX_NUMERO_TENTATIVI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccRispedizione.getIntervalloTentativi(),PccRispedizione.model().INTERVALLO_TENTATIVI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccRispedizione.getCodiceErrore(),PccRispedizione.model().CODICE_ERRORE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccRispedizione.getAbilitato(),PccRispedizione.model().ABILITATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccRispedizione.getDescrizioneErrore(),PccRispedizione.model().DESCRIZIONE_ERRORE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccRispedizione.getTipoErrore(),PccRispedizione.model().TIPO_ERRORE.getFieldType())
		);
		pccRispedizione.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRispedizione oldId, PccRispedizione pccRispedizione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdPccRispedizione(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = pccRispedizione.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: pccRispedizione.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			pccRispedizione.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccRispedizione, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccRispedizione pccRispedizione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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

		// Object pccRispedizione
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()));
		boolean isUpdate_pccRispedizione = true;
		java.util.List<JDBCObject> lstObjects_pccRispedizione = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().MAX_NUMERO_TENTATIVI,false), "?");
		lstObjects_pccRispedizione.add(new JDBCObject(pccRispedizione.getMaxNumeroTentativi(), PccRispedizione.model().MAX_NUMERO_TENTATIVI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().INTERVALLO_TENTATIVI,false), "?");
		lstObjects_pccRispedizione.add(new JDBCObject(pccRispedizione.getIntervalloTentativi(), PccRispedizione.model().INTERVALLO_TENTATIVI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().CODICE_ERRORE,false), "?");
		lstObjects_pccRispedizione.add(new JDBCObject(pccRispedizione.getCodiceErrore(), PccRispedizione.model().CODICE_ERRORE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().ABILITATO,false), "?");
		lstObjects_pccRispedizione.add(new JDBCObject(pccRispedizione.getAbilitato(), PccRispedizione.model().ABILITATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().DESCRIZIONE_ERRORE,false), "?");
		lstObjects_pccRispedizione.add(new JDBCObject(pccRispedizione.getDescrizioneErrore(), PccRispedizione.model().DESCRIZIONE_ERRORE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccRispedizioneFieldConverter().toColumn(PccRispedizione.model().TIPO_ERRORE,false), "?");
		lstObjects_pccRispedizione.add(new JDBCObject(pccRispedizione.getTipoErrore(), PccRispedizione.model().TIPO_ERRORE.getFieldType()));
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccRispedizione.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccRispedizione) {
			// Update pccRispedizione
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccRispedizione.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRispedizione id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccRispedizioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRispedizione id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccRispedizioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRispedizione id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccRispedizioneFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccRispedizioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccRispedizioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccRispedizioneFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRispedizione oldId, PccRispedizione pccRispedizione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, pccRispedizione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccRispedizione,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccRispedizione pccRispedizione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccRispedizione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccRispedizione,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccRispedizione pccRispedizione) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (pccRispedizione.getId()!=null) && (pccRispedizione.getId()>0) ){
			longId = pccRispedizione.getId();
		}
		else{
			IdRispedizione idPccRispedizione = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccRispedizione);
			longId = this.findIdPccRispedizione(jdbcProperties,log,connection,sqlQueryObject,idPccRispedizione,false);
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
		

		// Object pccRispedizione
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccRispedizioneFieldConverter().toTable(PccRispedizione.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccRispedizione
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRispedizione idPccRispedizione) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdPccRispedizione(jdbcProperties, log, connection, sqlQueryObject, idPccRispedizione, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccRispedizioneFieldConverter()));

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
