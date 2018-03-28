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

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCAllegatoFatturaServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCAllegatoFatturaServiceImpl extends JDBCAllegatoFatturaServiceSearchImpl
	implements IJDBCServiceCRUDWithoutId<AllegatoFattura, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AllegatoFattura allegatoFattura, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _fatturaElettronica
		Long id_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_fatturaElettronica = null;
		idLogic_fatturaElettronica = allegatoFattura.getIdFattura();
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


		// Object allegatoFattura
		sqlQueryObjectInsert.addInsertTable(this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()));
		sqlQueryObjectInsert.addInsertField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().NOME_ATTACHMENT,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().ALGORITMO_COMPRESSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().FORMATO_ATTACHMENT,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().DESCRIZIONE_ATTACHMENT,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().ATTACHMENT,false),"?");
		sqlQueryObjectInsert.addInsertField("id_fattura_elettronica","?");

		// Insert allegatoFattura
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getAllegatoFatturaFetch().getKeyGeneratorObject(AllegatoFattura.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(allegatoFattura.getNomeAttachment(),AllegatoFattura.model().NOME_ATTACHMENT.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(allegatoFattura.getAlgoritmoCompressione(),AllegatoFattura.model().ALGORITMO_COMPRESSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(allegatoFattura.getFormatoAttachment(),AllegatoFattura.model().FORMATO_ATTACHMENT.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(allegatoFattura.getDescrizioneAttachment(),AllegatoFattura.model().DESCRIZIONE_ATTACHMENT.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(allegatoFattura.getAttachment(),AllegatoFattura.model().ATTACHMENT.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_fatturaElettronica,Long.class)
		);
		allegatoFattura.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AllegatoFattura allegatoFattura, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		Long tableId = allegatoFattura.getId();
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, allegatoFattura, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, AllegatoFattura allegatoFattura, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _allegatoFattura_fatturaElettronica
		Long id_allegatoFattura_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_allegatoFattura_fatturaElettronica = null;
		idLogic_allegatoFattura_fatturaElettronica = allegatoFattura.getIdFattura();
		if(idLogic_allegatoFattura_fatturaElettronica!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_allegatoFattura_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findTableId(idLogic_allegatoFattura_fatturaElettronica, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_allegatoFattura_fatturaElettronica = idLogic_allegatoFattura_fatturaElettronica.getId();
				if(id_allegatoFattura_fatturaElettronica==null || id_allegatoFattura_fatturaElettronica<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object allegatoFattura
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()));
		boolean isUpdate_allegatoFattura = true;
		java.util.List<JDBCObject> lstObjects_allegatoFattura = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().NOME_ATTACHMENT,false), "?");
		lstObjects_allegatoFattura.add(new JDBCObject(allegatoFattura.getNomeAttachment(), AllegatoFattura.model().NOME_ATTACHMENT.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().ALGORITMO_COMPRESSIONE,false), "?");
		lstObjects_allegatoFattura.add(new JDBCObject(allegatoFattura.getAlgoritmoCompressione(), AllegatoFattura.model().ALGORITMO_COMPRESSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().FORMATO_ATTACHMENT,false), "?");
		lstObjects_allegatoFattura.add(new JDBCObject(allegatoFattura.getFormatoAttachment(), AllegatoFattura.model().FORMATO_ATTACHMENT.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().DESCRIZIONE_ATTACHMENT,false), "?");
		lstObjects_allegatoFattura.add(new JDBCObject(allegatoFattura.getDescrizioneAttachment(), AllegatoFattura.model().DESCRIZIONE_ATTACHMENT.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getAllegatoFatturaFieldConverter().toColumn(AllegatoFattura.model().ATTACHMENT,false), "?");
		lstObjects_allegatoFattura.add(new JDBCObject(allegatoFattura.getAttachment(), AllegatoFattura.model().ATTACHMENT.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_fattura_elettronica","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_allegatoFattura.add(new JDBCObject(id_allegatoFattura_fatturaElettronica, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_allegatoFattura.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_allegatoFattura) {
			// Update allegatoFattura
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_allegatoFattura.toArray(new JDBCObject[]{}));
		}

	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AllegatoFattura allegatoFattura, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, allegatoFattura),
				this.getAllegatoFatturaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AllegatoFattura allegatoFattura, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, allegatoFattura),
				this.getAllegatoFatturaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AllegatoFattura allegatoFattura, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, allegatoFattura),
				this.getAllegatoFatturaFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getAllegatoFatturaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getAllegatoFatturaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getAllegatoFatturaFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AllegatoFattura allegatoFattura, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		Long id = allegatoFattura.getId();
		if(id != null && this.exists(jdbcProperties, log, connection, sqlQueryObject, id)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, allegatoFattura,idMappingResolutionBehaviour);		
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, allegatoFattura,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, AllegatoFattura allegatoFattura, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, allegatoFattura,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, allegatoFattura,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AllegatoFattura allegatoFattura) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if(allegatoFattura.getId()==null){
			throw new Exception("Parameter "+allegatoFattura.getClass().getName()+".id is null");
		}
		if(allegatoFattura.getId()<=0){
			throw new Exception("Parameter "+allegatoFattura.getClass().getName()+".id is less equals 0");
		}
		longId = allegatoFattura.getId();
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object allegatoFattura
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getAllegatoFatturaFieldConverter().toTable(AllegatoFattura.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete allegatoFattura
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getAllegatoFatturaFieldConverter()));

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
