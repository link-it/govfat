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

import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccErroreElaborazioneServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccErroreElaborazioneServiceImpl extends JDBCPccErroreElaborazioneServiceSearchImpl
	implements IJDBCServiceCRUDWithoutId<PccErroreElaborazione, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccErroreElaborazione pccErroreElaborazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _pccTracciaTrasmissioneEsito
		Long id_pccTracciaTrasmissioneEsito = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito idLogic_pccTracciaTrasmissioneEsito = null;
		idLogic_pccTracciaTrasmissioneEsito = pccErroreElaborazione.getIdEsito();
		if(idLogic_pccTracciaTrasmissioneEsito!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccTracciaTrasmissioneEsito = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findTableId(idLogic_pccTracciaTrasmissioneEsito, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccTracciaTrasmissioneEsito = idLogic_pccTracciaTrasmissioneEsito.getId();
				if(id_pccTracciaTrasmissioneEsito==null || id_pccTracciaTrasmissioneEsito<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccErroreElaborazione
		sqlQueryObjectInsert.addInsertTable(this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().TIPO_OPERAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().CODICE_ESITO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().DESCRIZIONE_ESITO,false),"?");
		sqlQueryObjectInsert.addInsertField("id_esito","?");

		// Insert pccErroreElaborazione
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccErroreElaborazioneFetch().getKeyGeneratorObject(PccErroreElaborazione.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccErroreElaborazione.getTipoOperazione(),PccErroreElaborazione.model().TIPO_OPERAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccErroreElaborazione.getProgressivoOperazione(),PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccErroreElaborazione.getCodiceEsito(),PccErroreElaborazione.model().CODICE_ESITO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccErroreElaborazione.getDescrizioneEsito(),PccErroreElaborazione.model().DESCRIZIONE_ESITO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_pccTracciaTrasmissioneEsito,Long.class)
		);
		pccErroreElaborazione.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccErroreElaborazione pccErroreElaborazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		Long tableId = pccErroreElaborazione.getId();
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccErroreElaborazione, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccErroreElaborazione pccErroreElaborazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccErroreElaborazione_pccTracciaTrasmissioneEsito
		Long id_pccErroreElaborazione_pccTracciaTrasmissioneEsito = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito idLogic_pccErroreElaborazione_pccTracciaTrasmissioneEsito = null;
		idLogic_pccErroreElaborazione_pccTracciaTrasmissioneEsito = pccErroreElaborazione.getIdEsito();
		if(idLogic_pccErroreElaborazione_pccTracciaTrasmissioneEsito!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccErroreElaborazione_pccTracciaTrasmissioneEsito = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findTableId(idLogic_pccErroreElaborazione_pccTracciaTrasmissioneEsito, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccErroreElaborazione_pccTracciaTrasmissioneEsito = idLogic_pccErroreElaborazione_pccTracciaTrasmissioneEsito.getId();
				if(id_pccErroreElaborazione_pccTracciaTrasmissioneEsito==null || id_pccErroreElaborazione_pccTracciaTrasmissioneEsito<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccErroreElaborazione
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()));
		boolean isUpdate_pccErroreElaborazione = true;
		java.util.List<JDBCObject> lstObjects_pccErroreElaborazione = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().TIPO_OPERAZIONE,false), "?");
		lstObjects_pccErroreElaborazione.add(new JDBCObject(pccErroreElaborazione.getTipoOperazione(), PccErroreElaborazione.model().TIPO_OPERAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE,false), "?");
		lstObjects_pccErroreElaborazione.add(new JDBCObject(pccErroreElaborazione.getProgressivoOperazione(), PccErroreElaborazione.model().PROGRESSIVO_OPERAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().CODICE_ESITO,false), "?");
		lstObjects_pccErroreElaborazione.add(new JDBCObject(pccErroreElaborazione.getCodiceEsito(), PccErroreElaborazione.model().CODICE_ESITO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccErroreElaborazioneFieldConverter().toColumn(PccErroreElaborazione.model().DESCRIZIONE_ESITO,false), "?");
		lstObjects_pccErroreElaborazione.add(new JDBCObject(pccErroreElaborazione.getDescrizioneEsito(), PccErroreElaborazione.model().DESCRIZIONE_ESITO.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_esito","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccErroreElaborazione.add(new JDBCObject(id_pccErroreElaborazione_pccTracciaTrasmissioneEsito, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccErroreElaborazione.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccErroreElaborazione) {
			// Update pccErroreElaborazione
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccErroreElaborazione.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccErroreElaborazione pccErroreElaborazione, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccErroreElaborazione),
				this.getPccErroreElaborazioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccErroreElaborazione pccErroreElaborazione, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccErroreElaborazione),
				this.getPccErroreElaborazioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccErroreElaborazione pccErroreElaborazione, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, pccErroreElaborazione),
				this.getPccErroreElaborazioneFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccErroreElaborazioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccErroreElaborazioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccErroreElaborazioneFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccErroreElaborazione pccErroreElaborazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		Long id = pccErroreElaborazione.getId();
		if(id != null && this.exists(jdbcProperties, log, connection, sqlQueryObject, id)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, pccErroreElaborazione,idMappingResolutionBehaviour);		
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccErroreElaborazione,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccErroreElaborazione pccErroreElaborazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccErroreElaborazione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccErroreElaborazione,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccErroreElaborazione pccErroreElaborazione) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if(pccErroreElaborazione.getId()==null){
			throw new Exception("Parameter "+pccErroreElaborazione.getClass().getName()+".id is null");
		}
		if(pccErroreElaborazione.getId()<=0){
			throw new Exception("Parameter "+pccErroreElaborazione.getClass().getName()+".id is less equals 0");
		}
		longId = pccErroreElaborazione.getId();
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object pccErroreElaborazione
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccErroreElaborazioneFieldConverter().toTable(PccErroreElaborazione.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccErroreElaborazione
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccErroreElaborazioneFieldConverter()));

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
