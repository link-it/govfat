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

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.govmix.proxy.fatturapa.orm.IdScadenza;
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

import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccScadenzaServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccScadenzaServiceImpl extends JDBCPccScadenzaServiceSearchImpl
	implements IJDBCServiceCRUDWithId<PccScadenza, IdScadenza, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccScadenza pccScadenza, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _fatturaElettronica
		Long id_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_fatturaElettronica = null;
		idLogic_fatturaElettronica = pccScadenza.getIdFattura();
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


		// Object pccScadenza
		sqlQueryObjectInsert.addInsertTable(this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().IMPORTO_IN_SCADENZA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().IMPORTO_INIZIALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().PAGATO_RICONTABILIZZATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().DATA_SCADENZA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().SISTEMA_RICHIEDENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().UTENTE_RICHIEDENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().DATA_RICHIESTA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().DATA_QUERY,false),"?");
		sqlQueryObjectInsert.addInsertField("id_fattura_elettronica","?");

		// Insert pccScadenza
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccScadenzaFetch().getKeyGeneratorObject(PccScadenza.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getImportoInScadenza(),PccScadenza.model().IMPORTO_IN_SCADENZA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getImportoIniziale(),PccScadenza.model().IMPORTO_INIZIALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getPagatoRicontabilizzato(),PccScadenza.model().PAGATO_RICONTABILIZZATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getDataScadenza(),PccScadenza.model().DATA_SCADENZA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getSistemaRichiedente(),PccScadenza.model().SISTEMA_RICHIEDENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getUtenteRichiedente(),PccScadenza.model().UTENTE_RICHIEDENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getDataRichiesta(),PccScadenza.model().DATA_RICHIESTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccScadenza.getDataQuery(),PccScadenza.model().DATA_QUERY.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_fatturaElettronica,Long.class)
		);
		pccScadenza.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdScadenza oldId, PccScadenza pccScadenza, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdPccScadenza(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = pccScadenza.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: pccScadenza.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			pccScadenza.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccScadenza, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccScadenza pccScadenza, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccScadenza_fatturaElettronica
		Long id_pccScadenza_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_pccScadenza_fatturaElettronica = null;
		idLogic_pccScadenza_fatturaElettronica = pccScadenza.getIdFattura();
		if(idLogic_pccScadenza_fatturaElettronica!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccScadenza_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findTableId(idLogic_pccScadenza_fatturaElettronica, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccScadenza_fatturaElettronica = idLogic_pccScadenza_fatturaElettronica.getId();
				if(id_pccScadenza_fatturaElettronica==null || id_pccScadenza_fatturaElettronica<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccScadenza
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()));
		boolean isUpdate_pccScadenza = true;
		java.util.List<JDBCObject> lstObjects_pccScadenza = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().IMPORTO_IN_SCADENZA,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getImportoInScadenza(), PccScadenza.model().IMPORTO_IN_SCADENZA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().IMPORTO_INIZIALE,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getImportoIniziale(), PccScadenza.model().IMPORTO_INIZIALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().PAGATO_RICONTABILIZZATO,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getPagatoRicontabilizzato(), PccScadenza.model().PAGATO_RICONTABILIZZATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().DATA_SCADENZA,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getDataScadenza(), PccScadenza.model().DATA_SCADENZA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().SISTEMA_RICHIEDENTE,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getSistemaRichiedente(), PccScadenza.model().SISTEMA_RICHIEDENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().UTENTE_RICHIEDENTE,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getUtenteRichiedente(), PccScadenza.model().UTENTE_RICHIEDENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().DATA_RICHIESTA,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getDataRichiesta(), PccScadenza.model().DATA_RICHIESTA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccScadenzaFieldConverter().toColumn(PccScadenza.model().DATA_QUERY,false), "?");
		lstObjects_pccScadenza.add(new JDBCObject(pccScadenza.getDataQuery(), PccScadenza.model().DATA_QUERY.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_fattura_elettronica","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccScadenza.add(new JDBCObject(id_pccScadenza_fatturaElettronica, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccScadenza.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccScadenza) {
			// Update pccScadenza
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccScadenza.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdScadenza id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccScadenzaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdScadenza id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccScadenzaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdScadenza id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccScadenzaFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccScadenzaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccScadenzaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccScadenzaFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdScadenza oldId, PccScadenza pccScadenza, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, pccScadenza,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccScadenza,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccScadenza pccScadenza, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccScadenza,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccScadenza,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccScadenza pccScadenza) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (pccScadenza.getId()!=null) && (pccScadenza.getId()>0) ){
			longId = pccScadenza.getId();
		}
		else{
			IdScadenza idPccScadenza = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccScadenza);
			longId = this.findIdPccScadenza(jdbcProperties,log,connection,sqlQueryObject,idPccScadenza,false);
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
		

		// Object pccScadenza
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccScadenzaFieldConverter().toTable(PccScadenza.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccScadenza
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdScadenza idPccScadenza) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdPccScadenza(jdbcProperties, log, connection, sqlQueryObject, idPccScadenza, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccScadenzaFieldConverter()));

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
