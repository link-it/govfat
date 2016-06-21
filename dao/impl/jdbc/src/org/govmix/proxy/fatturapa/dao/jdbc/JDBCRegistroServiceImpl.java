/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.dao.jdbc;

import java.sql.Connection;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.apache.log4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.govmix.proxy.fatturapa.IdRegistro;
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

import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCRegistroServiceImpl
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCRegistroServiceImpl extends JDBCRegistroServiceSearchImpl
	implements IJDBCServiceCRUDWithId<Registro, IdRegistro, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Registro registro) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		

		Long id_ente = JDBCEnteUtils.toLongId(jdbcProperties, log, connection, sqlQueryObject, registro.getIdEnte());


		// Object registro
		sqlQueryObjectInsert.addInsertTable(this.getRegistroFieldConverter().toTable(Registro.model()));
		sqlQueryObjectInsert.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().NOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().USERNAME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRegistroFieldConverter().toColumn(Registro.model().PASSWORD,false),"?");
		sqlQueryObjectInsert.addInsertField("id_ente","?");

		// Insert registro
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getRegistroFetch().getKeyGeneratorObject(Registro.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro.getNome(),Registro.model().NOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro.getUsername(),Registro.model().USERNAME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(registro.getPassword(),Registro.model().PASSWORD.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_ente,Long.class)
		);
		registro.setId(id);

		// for registro
		for (int i = 0; i < registro.getRegistroPropertyValueList().size(); i++) {

			Long id_registroPropertyValue_registroProperty = JDBCRegistroPropertyUtils.toLongId(jdbcProperties, log, connection, sqlQueryObject, registro.getRegistroPropertyValueList().get(i).getIdProperty());


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
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro oldId, Registro registro) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		this.mappingTableIds(jdbcProperties, log, connection, sqlQueryObject, oldId, registro);

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
				
		Long longIdByLogicId = this.findIdRegistro(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long longId = registro.getId();
		if(longId != null && longId.longValue() > 0) {
			if(longId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: registro.id ["+longId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			longId = longIdByLogicId;
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
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_registro.add(new JDBCObject(longId, Long.class));

		if(isUpdate_registro) {
			// Update registro
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_registro.toArray(new JDBCObject[]{}));
		}
		// for registro_registroPropertyValue

		java.util.List<Long> ids_registro_registroPropertyValue_da_non_eliminare = new java.util.ArrayList<Long>();
		for (Object registro_registroPropertyValue_object : registro.getRegistroPropertyValueList()) {
			RegistroPropertyValue registro_registroPropertyValue = (RegistroPropertyValue) registro_registroPropertyValue_object;
			if(registro_registroPropertyValue.getId() == null || registro_registroPropertyValue.getId().longValue() <= 0) {

				long id = registro.getId();			

				Long id_registro_registroPropertyValue_registroProperty = JDBCRegistroPropertyUtils.toLongId(jdbcProperties, log, connection, sqlQueryObject, registro_registroPropertyValue.getIdProperty());



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
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro oldId, Registro registro) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, registro);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, registro);
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
