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

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.RegistroPropertyValue;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.beans.UpdateModel;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.utils.sql.ISQLQueryObject;

/**     
 * JDBCRegistroServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCRegistroServiceImpl extends JDBCRegistroServiceSearchImpl
	implements IJDBCServiceCRUDWithId<Registro, IdRegistro, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Registro registro, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _protocollo
		Long id_protocollo = null;
		org.govmix.proxy.fatturapa.orm.IdProtocollo idLogic_protocollo = null;
		idLogic_protocollo = registro.getIdProtocollo();
		if(idLogic_protocollo!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_protocollo = ((JDBCProtocolloServiceSearch)(this.getServiceManager().getProtocolloServiceSearch())).findTableId(idLogic_protocollo, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_protocollo = idLogic_protocollo.getId();
				if(id_protocollo==null || id_protocollo<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object registro
		sqlQueryObjectInsert.addInsertTable(this.getRegistroFieldConverter().toTable(Registro.model()));
		sqlQueryObjectInsert.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().NOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().USERNAME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().PASSWORD,false),"?");
		sqlQueryObjectInsert.addInsertField("id_protocollo","?");

		// Insert registro
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getRegistroFetch().getKeyGeneratorObject(Registro.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro.getNome(),Registro.model().NOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro.getUsername(),Registro.model().USERNAME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro.getPassword(),Registro.model().PASSWORD.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_protocollo,Long.class)
		);
		registro.setId(id);

		// for registro
		for (int i = 0; i < registro.getRegistroPropertyValueList().size(); i++) {

			// Object _registroPropertyValue_registroProperty
			Long id_registroPropertyValue_registroProperty = null;
			org.govmix.proxy.fatturapa.orm.IdRegistroProperty idLogic_registroPropertyValue_registroProperty = null;
			idLogic_registroPropertyValue_registroProperty = registro.getRegistroPropertyValueList().get(i).getIdProperty();
			if(idLogic_registroPropertyValue_registroProperty!=null){
				if(idMappingResolutionBehaviour==null ||
					(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
					id_registroPropertyValue_registroProperty = ((JDBCRegistroPropertyServiceSearch)(this.getServiceManager().getRegistroPropertyServiceSearch())).findTableId(idLogic_registroPropertyValue_registroProperty, false);
				}
				else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
					id_registroPropertyValue_registroProperty = idLogic_registroPropertyValue_registroProperty.getId();
					if(id_registroPropertyValue_registroProperty==null || id_registroPropertyValue_registroProperty<=0){
						throw new Exception("Logic id not contains table id");
					}
				}
			}


			// Object registro.getRegistroPropertyValueList().get(i)
			ISQLQueryObject sqlQueryObjectInsert_registroPropertyValue = sqlQueryObjectInsert.newSQLQueryObject();
			sqlQueryObjectInsert_registroPropertyValue.addInsertTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
			sqlQueryObjectInsert_registroPropertyValue.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().REGISTRO_PROPERTY_VALUE.VALORE,false),"?");
			sqlQueryObjectInsert_registroPropertyValue.addInsertField("id_registro_property","?");
			sqlQueryObjectInsert_registroPropertyValue.addInsertField("id_registro","?");

			// Insert registro.getRegistroPropertyValueList().get(i)
			org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_registroPropertyValue = this.getRegistroFetch().getKeyGeneratorObject(Registro.model().REGISTRO_PROPERTY_VALUE);
			long id_registroPropertyValue = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_registroPropertyValue, keyGenerator_registroPropertyValue, jdbcProperties.isShowSql(),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro.getRegistroPropertyValueList().get(i).getValore(),Registro.model().REGISTRO_PROPERTY_VALUE.VALORE.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_registroPropertyValue_registroProperty,Long.class),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
			);
			registro.getRegistroPropertyValueList().get(i).setId(id_registroPropertyValue);
		} // fine for 

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro oldId, Registro registro, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdRegistro(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = registro.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: registro.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			registro.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, registro, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Registro registro, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _registro_protocollo
		Long id_registro_protocollo = null;
		org.govmix.proxy.fatturapa.orm.IdProtocollo idLogic_registro_protocollo = null;
		idLogic_registro_protocollo = registro.getIdProtocollo();
		if(idLogic_registro_protocollo!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_registro_protocollo = ((JDBCProtocolloServiceSearch)(this.getServiceManager().getProtocolloServiceSearch())).findTableId(idLogic_registro_protocollo, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_registro_protocollo = idLogic_registro_protocollo.getId();
				if(id_registro_protocollo==null || id_registro_protocollo<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object registro
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getRegistroFieldConverter().toTable(Registro.model()));
		boolean isUpdate_registro = true;
		java.util.List<JDBCObject> lstObjects_registro = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getRegistroFieldConverter().toColumn(Registro.model().NOME,false), "?");
		lstObjects_registro.add(new JDBCObject(registro.getNome(), Registro.model().NOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getRegistroFieldConverter().toColumn(Registro.model().USERNAME,false), "?");
		lstObjects_registro.add(new JDBCObject(registro.getUsername(), Registro.model().USERNAME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getRegistroFieldConverter().toColumn(Registro.model().PASSWORD,false), "?");
		lstObjects_registro.add(new JDBCObject(registro.getPassword(), Registro.model().PASSWORD.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_protocollo","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_registro.add(new JDBCObject(id_registro_protocollo, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_registro.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_registro) {
			// Update registro
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_registro.toArray(new JDBCObject[]{}));
		}
		// for registro_registroPropertyValue

		java.util.List<Long> ids_registro_registroPropertyValue_da_non_eliminare = new java.util.ArrayList<Long>();
		for (Object registro_registroPropertyValue_object : registro.getRegistroPropertyValueList()) {
			RegistroPropertyValue registro_registroPropertyValue = (RegistroPropertyValue) registro_registroPropertyValue_object;

			// Object _registro_registroPropertyValue_registroProperty
			Long id_registro_registroPropertyValue_registroProperty = null;
			org.govmix.proxy.fatturapa.orm.IdRegistroProperty idLogic_registro_registroPropertyValue_registroProperty = null;
			idLogic_registro_registroPropertyValue_registroProperty = registro_registroPropertyValue.getIdProperty();
			if(idLogic_registro_registroPropertyValue_registroProperty!=null){
				if(idMappingResolutionBehaviour==null ||
					(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
					id_registro_registroPropertyValue_registroProperty = ((JDBCRegistroPropertyServiceSearch)(this.getServiceManager().getRegistroPropertyServiceSearch())).findTableId(idLogic_registro_registroPropertyValue_registroProperty, false);
				}
				else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
					id_registro_registroPropertyValue_registroProperty = idLogic_registro_registroPropertyValue_registroProperty.getId();
					if(id_registro_registroPropertyValue_registroProperty==null || id_registro_registroPropertyValue_registroProperty<=0){
						throw new Exception("Logic id not contains table id");
					}
				}
			}

			if(registro_registroPropertyValue.getId() == null || registro_registroPropertyValue.getId().longValue() <= 0) {				
				try {
					ISQLQueryObject registroPropValueSQLQueryObject = sqlQueryObject.newSQLQueryObject();
					registroPropValueSQLQueryObject.addFromTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
					registroPropValueSQLQueryObject.setANDLogicOperator(true);
					registroPropValueSQLQueryObject.addSelectField("id");
					registroPropValueSQLQueryObject.addWhereCondition("id_registro_property=?");
					registroPropValueSQLQueryObject.addWhereCondition("id_registro=?");
					
					// Get dipartimento_dipartimentoPropertyValue
					Long regPropValue = (Long) jdbcUtilities.executeQuerySingleResult(registroPropValueSQLQueryObject.createSQLQuery(), jdbcProperties.isShowSql(), Long.class,
							new JDBCObject(id_registro_registroPropertyValue_registroProperty,Long.class), 	
						new JDBCObject(registro.getId(),Long.class));
					
					registro_registroPropertyValue.setId(regPropValue);
				} catch(NotFoundException e) {}

				
			}
			
			if(registro_registroPropertyValue.getId() == null || registro_registroPropertyValue.getId().longValue() <= 0) {

				long id = registro.getId();			

				// Object registro_registroPropertyValue
				ISQLQueryObject sqlQueryObjectInsert_registro_registroPropertyValue = sqlQueryObjectInsert.newSQLQueryObject();
				sqlQueryObjectInsert_registro_registroPropertyValue.addInsertTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
				sqlQueryObjectInsert_registro_registroPropertyValue.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().REGISTRO_PROPERTY_VALUE.VALORE,false),"?");
				sqlQueryObjectInsert_registro_registroPropertyValue.addInsertField("id_registro_property","?");
				sqlQueryObjectInsert_registro_registroPropertyValue.addInsertField("id_registro","?");

				// Insert registro_registroPropertyValue
				org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_registro_registroPropertyValue = this.getRegistroFetch().getKeyGeneratorObject(Registro.model().REGISTRO_PROPERTY_VALUE);
				long id_registro_registroPropertyValue = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_registro_registroPropertyValue, keyGenerator_registro_registroPropertyValue, jdbcProperties.isShowSql(),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro_registroPropertyValue.getValore(),Registro.model().REGISTRO_PROPERTY_VALUE.VALORE.getFieldType()),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_registro_registroPropertyValue_registroProperty,Long.class),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
				);
				registro_registroPropertyValue.setId(id_registro_registroPropertyValue);

				ids_registro_registroPropertyValue_da_non_eliminare.add(registro_registroPropertyValue.getId());
			} else {

				// Object registro_registroPropertyValue
				ISQLQueryObject sqlQueryObjectUpdate_registro_registroPropertyValue = sqlQueryObjectUpdate.newSQLQueryObject();
				sqlQueryObjectUpdate_registro_registroPropertyValue.setANDLogicOperator(true);
				sqlQueryObjectUpdate_registro_registroPropertyValue.addUpdateTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
				boolean isUpdate_registro_registroPropertyValue = true;
				java.util.List<JDBCObject> lstObjects_registro_registroPropertyValue = new java.util.ArrayList<JDBCObject>();
				sqlQueryObjectUpdate_registro_registroPropertyValue.addUpdateField(this.getRegistroFieldConverter().toColumn(Registro.model().REGISTRO_PROPERTY_VALUE.VALORE,false), "?");
				lstObjects_registro_registroPropertyValue.add(new JDBCObject(registro_registroPropertyValue.getValore(), Registro.model().REGISTRO_PROPERTY_VALUE.VALORE.getFieldType()));
				if(setIdMappingResolutionBehaviour){
					sqlQueryObjectUpdate_registro_registroPropertyValue.addUpdateField("id_registro_property","?");
				}
				if(setIdMappingResolutionBehaviour){
					lstObjects_registro_registroPropertyValue.add(new JDBCObject(id_registro_registroPropertyValue_registroProperty, Long.class));
				}
				sqlQueryObjectUpdate_registro_registroPropertyValue.addWhereCondition("id=?");
				ids_registro_registroPropertyValue_da_non_eliminare.add(registro_registroPropertyValue.getId());
				lstObjects_registro_registroPropertyValue.add(new JDBCObject(new Long(registro_registroPropertyValue.getId()),Long.class));

				if(isUpdate_registro_registroPropertyValue) {
					// Update registro_registroPropertyValue
					jdbcUtilities.executeUpdate(sqlQueryObjectUpdate_registro_registroPropertyValue.createSQLUpdate(), jdbcProperties.isShowSql(), 
						lstObjects_registro_registroPropertyValue.toArray(new JDBCObject[]{}));
				}
			}
		} // fine for registro_registroPropertyValue

		// elimino tutte le occorrenze di registro_registroPropertyValue non presenti nell'update

		ISQLQueryObject sqlQueryObjectUpdate_registroPropertyValue_deleteList = sqlQueryObjectUpdate.newSQLQueryObject();
		sqlQueryObjectUpdate_registroPropertyValue_deleteList.setANDLogicOperator(true);
		sqlQueryObjectUpdate_registroPropertyValue_deleteList.addDeleteTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
		java.util.List<JDBCObject> jdbcObjects_registro_registroPropertyValue_delete = new java.util.ArrayList<JDBCObject>();

		sqlQueryObjectUpdate_registroPropertyValue_deleteList.addWhereCondition("id_registro=?");
		jdbcObjects_registro_registroPropertyValue_delete.add(new JDBCObject(registro.getId(), Long.class));

		StringBuffer marks_registro_registroPropertyValue = new StringBuffer();
		if(ids_registro_registroPropertyValue_da_non_eliminare.size() > 0) {
			for(Long ids : ids_registro_registroPropertyValue_da_non_eliminare) {
				if(marks_registro_registroPropertyValue.length() > 0) {
					marks_registro_registroPropertyValue.append(",");
				}
				marks_registro_registroPropertyValue.append("?");
				jdbcObjects_registro_registroPropertyValue_delete.add(new JDBCObject(ids, Long.class));

			}
			sqlQueryObjectUpdate_registroPropertyValue_deleteList.addWhereCondition("id NOT IN ("+marks_registro_registroPropertyValue.toString()+")");
		}

		jdbcUtilities.execute(sqlQueryObjectUpdate_registroPropertyValue_deleteList.createSQLDelete(), jdbcProperties.isShowSql(), jdbcObjects_registro_registroPropertyValue_delete.toArray(new JDBCObject[]{}));


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRegistroFieldConverter().toTable(Registro.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getRegistroFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRegistroFieldConverter().toTable(Registro.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getRegistroFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRegistroFieldConverter().toTable(Registro.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getRegistroFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRegistroFieldConverter().toTable(Registro.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getRegistroFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRegistroFieldConverter().toTable(Registro.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getRegistroFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRegistroFieldConverter().toTable(Registro.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getRegistroFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro oldId, Registro registro, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, registro,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, registro,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Registro registro, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, registro,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, registro,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Registro registro) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (registro.getId()!=null) && (registro.getId()>0) ){
			longId = registro.getId();
		}
		else{
			IdRegistro idRegistro = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,registro);
			longId = this.findIdRegistro(jdbcProperties,log,connection,sqlQueryObject,idRegistro,false);
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
		

		//Recupero oggetto _registro_registroPropertyValue
		ISQLQueryObject sqlQueryObjectDelete_registro_registroPropertyValue_getToDelete = sqlQueryObjectDelete.newSQLQueryObject();
		sqlQueryObjectDelete_registro_registroPropertyValue_getToDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete_registro_registroPropertyValue_getToDelete.addFromTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
		sqlQueryObjectDelete_registro_registroPropertyValue_getToDelete.addWhereCondition("id_registro=?");
		java.util.List<Object> registro_registroPropertyValue_toDelete_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectDelete_registro_registroPropertyValue_getToDelete.createSQLQuery(), jdbcProperties.isShowSql(), Registro.model().REGISTRO_PROPERTY_VALUE, this.getRegistroFetch(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class));

		// for registro_registroPropertyValue
		for (Object registro_registroPropertyValue_object : registro_registroPropertyValue_toDelete_list) {
			RegistroPropertyValue registro_registroPropertyValue = (RegistroPropertyValue) registro_registroPropertyValue_object;

			// Object registro_registroPropertyValue
			ISQLQueryObject sqlQueryObjectDelete_registro_registroPropertyValue = sqlQueryObjectDelete.newSQLQueryObject();
			sqlQueryObjectDelete_registro_registroPropertyValue.setANDLogicOperator(true);
			sqlQueryObjectDelete_registro_registroPropertyValue.addDeleteTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
			sqlQueryObjectDelete_registro_registroPropertyValue.addWhereCondition("id=?");

			// Delete registro_registroPropertyValue
			if(registro_registroPropertyValue != null){
				jdbcUtilities.execute(sqlQueryObjectDelete_registro_registroPropertyValue.createSQLDelete(), jdbcProperties.isShowSql(), 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(registro_registroPropertyValue.getId()),Long.class));
			}
		} // fine for registro_registroPropertyValue

		// Object registro
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getRegistroFieldConverter().toTable(Registro.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete registro
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro idRegistro) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdRegistro(jdbcProperties, log, connection, sqlQueryObject, idRegistro, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getRegistroFieldConverter()));

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
