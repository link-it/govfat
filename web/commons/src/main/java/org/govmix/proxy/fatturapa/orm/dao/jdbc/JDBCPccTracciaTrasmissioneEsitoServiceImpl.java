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
import org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito;
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

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccTracciaTrasmissioneEsitoServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccTracciaTrasmissioneEsitoServiceImpl extends JDBCPccTracciaTrasmissioneEsitoServiceSearchImpl
	implements IJDBCServiceCRUDWithId<PccTracciaTrasmissioneEsito, IdTrasmissioneEsito, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _pccTracciaTrasmissione
		Long id_pccTracciaTrasmissione = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissione idLogic_pccTracciaTrasmissione = null;
		idLogic_pccTracciaTrasmissione = pccTracciaTrasmissioneEsito.getIdTrasmissione();
		if(idLogic_pccTracciaTrasmissione!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccTracciaTrasmissione = ((JDBCPccTracciaTrasmissioneServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneServiceSearch())).findTableId(idLogic_pccTracciaTrasmissione, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccTracciaTrasmissione = idLogic_pccTracciaTrasmissione.getId();
				if(id_pccTracciaTrasmissione==null || id_pccTracciaTrasmissione<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccTracciaTrasmissioneEsito
		sqlQueryObjectInsert.addInsertTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DESCRIZIONE_ELABORAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DATA_FINE_ELABORAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().GDO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ESITO_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DETTAGLIO_ERRORE_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ID_EGOV_RICHIESTA,false),"?");
		sqlQueryObjectInsert.addInsertField("id_pcc_traccia_trasmissione","?");

		// Insert pccTracciaTrasmissioneEsito
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccTracciaTrasmissioneEsitoFetch().getKeyGeneratorObject(PccTracciaTrasmissioneEsito.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissioneEsito.getEsitoElaborazione(),PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissioneEsito.getDescrizioneElaborazione(),PccTracciaTrasmissioneEsito.model().DESCRIZIONE_ELABORAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissioneEsito.getDataFineElaborazione(),PccTracciaTrasmissioneEsito.model().DATA_FINE_ELABORAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissioneEsito.getGdo(),PccTracciaTrasmissioneEsito.model().GDO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissioneEsito.getEsitoTrasmissione(),PccTracciaTrasmissioneEsito.model().ESITO_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissioneEsito.getDettaglioErroreTrasmissione(),PccTracciaTrasmissioneEsito.model().DETTAGLIO_ERRORE_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccTracciaTrasmissioneEsito.getIdEgovRichiesta(),PccTracciaTrasmissioneEsito.model().ID_EGOV_RICHIESTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_pccTracciaTrasmissione,Long.class)
		);
		pccTracciaTrasmissioneEsito.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito oldId, PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdPccTracciaTrasmissioneEsito(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = pccTracciaTrasmissioneEsito.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: pccTracciaTrasmissioneEsito.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			pccTracciaTrasmissioneEsito.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccTracciaTrasmissioneEsito, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccTracciaTrasmissioneEsito_pccTracciaTrasmissione
		Long id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissione idLogic_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = null;
		idLogic_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = pccTracciaTrasmissioneEsito.getIdTrasmissione();
		if(idLogic_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = ((JDBCPccTracciaTrasmissioneServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneServiceSearch())).findTableId(idLogic_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = idLogic_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione.getId();
				if(id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione==null || id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccTracciaTrasmissioneEsito
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()));
		boolean isUpdate_pccTracciaTrasmissioneEsito = true;
		java.util.List<JDBCObject> lstObjects_pccTracciaTrasmissioneEsito = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE,false), "?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(pccTracciaTrasmissioneEsito.getEsitoElaborazione(), PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DESCRIZIONE_ELABORAZIONE,false), "?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(pccTracciaTrasmissioneEsito.getDescrizioneElaborazione(), PccTracciaTrasmissioneEsito.model().DESCRIZIONE_ELABORAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DATA_FINE_ELABORAZIONE,false), "?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(pccTracciaTrasmissioneEsito.getDataFineElaborazione(), PccTracciaTrasmissioneEsito.model().DATA_FINE_ELABORAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().GDO,false), "?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(pccTracciaTrasmissioneEsito.getGdo(), PccTracciaTrasmissioneEsito.model().GDO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ESITO_TRASMISSIONE,false), "?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(pccTracciaTrasmissioneEsito.getEsitoTrasmissione(), PccTracciaTrasmissioneEsito.model().ESITO_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DETTAGLIO_ERRORE_TRASMISSIONE,false), "?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(pccTracciaTrasmissioneEsito.getDettaglioErroreTrasmissione(), PccTracciaTrasmissioneEsito.model().DETTAGLIO_ERRORE_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ID_EGOV_RICHIESTA,false), "?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(pccTracciaTrasmissioneEsito.getIdEgovRichiesta(), PccTracciaTrasmissioneEsito.model().ID_EGOV_RICHIESTA.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_pcc_traccia_trasmissione","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccTracciaTrasmissioneEsito.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccTracciaTrasmissioneEsito) {
			// Update pccTracciaTrasmissioneEsito
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccTracciaTrasmissioneEsito.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaTrasmissioneEsitoFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaTrasmissioneEsitoFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccTracciaTrasmissioneEsitoFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaTrasmissioneEsitoFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaTrasmissioneEsitoFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccTracciaTrasmissioneEsitoFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito oldId, PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, pccTracciaTrasmissioneEsito,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccTracciaTrasmissioneEsito,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccTracciaTrasmissioneEsito,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccTracciaTrasmissioneEsito,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (pccTracciaTrasmissioneEsito.getId()!=null) && (pccTracciaTrasmissioneEsito.getId()>0) ){
			longId = pccTracciaTrasmissioneEsito.getId();
		}
		else{
			IdTrasmissioneEsito idPccTracciaTrasmissioneEsito = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccTracciaTrasmissioneEsito);
			longId = this.findIdPccTracciaTrasmissioneEsito(jdbcProperties,log,connection,sqlQueryObject,idPccTracciaTrasmissioneEsito,false);
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
		

		// Object pccTracciaTrasmissioneEsito
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccTracciaTrasmissioneEsito
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito idPccTracciaTrasmissioneEsito) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdPccTracciaTrasmissioneEsito(jdbcProperties, log, connection, sqlQueryObject, idPccTracciaTrasmissioneEsito, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccTracciaTrasmissioneEsitoFieldConverter()));

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
