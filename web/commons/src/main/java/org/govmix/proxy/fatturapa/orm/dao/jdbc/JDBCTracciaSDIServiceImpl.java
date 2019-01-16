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
import org.govmix.proxy.fatturapa.orm.IdTracciaSdi;
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
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
/**     
 * JDBCTracciaSDIServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCTracciaSDIServiceImpl extends JDBCTracciaSDIServiceSearchImpl
	implements IJDBCServiceCRUDWithId<TracciaSDI, IdTracciaSdi, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				


		// Object tracciaSDI
		sqlQueryObjectInsert.addInsertTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()));
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().IDENTIFICATIVO_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().POSIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().TIPO_COMUNICAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().NOME_FILE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().CODICE_DIPARTIMENTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DATA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().ID_EGOV,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().CONTENT_TYPE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().RAW_DATA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().STATO_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DATA_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE,false),"?");

		// Insert tracciaSDI
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getTracciaSDIFetch().getKeyGeneratorObject(TracciaSDI.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getIdentificativoSdi(),TracciaSDI.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getPosizione(),TracciaSDI.model().POSIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getTipoComunicazione(),TracciaSDI.model().TIPO_COMUNICAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getNomeFile(),TracciaSDI.model().NOME_FILE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getCodiceDipartimento(),TracciaSDI.model().CODICE_DIPARTIMENTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getData(),TracciaSDI.model().DATA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getIdEgov(),TracciaSDI.model().ID_EGOV.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getContentType(),TracciaSDI.model().CONTENT_TYPE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getRawData(),TracciaSDI.model().RAW_DATA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getStatoProtocollazione(),TracciaSDI.model().STATO_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getDataProtocollazione(),TracciaSDI.model().DATA_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getDataProssimaProtocollazione(),TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getTentativiProtocollazione(),TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getDettaglioProtocollazione(),TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE.getFieldType())
		);
		tracciaSDI.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTracciaSdi oldId, TracciaSDI tracciaSDI, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdTracciaSDI(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = tracciaSDI.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: tracciaSDI.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			tracciaSDI.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, tracciaSDI, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, TracciaSDI tracciaSDI, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
		


		// Object tracciaSDI
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()));
		boolean isUpdate_tracciaSDI = true;
		java.util.List<JDBCObject> lstObjects_tracciaSDI = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().IDENTIFICATIVO_SDI,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getIdentificativoSdi(), TracciaSDI.model().IDENTIFICATIVO_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().POSIZIONE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getPosizione(), TracciaSDI.model().POSIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().TIPO_COMUNICAZIONE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getTipoComunicazione(), TracciaSDI.model().TIPO_COMUNICAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().NOME_FILE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getNomeFile(), TracciaSDI.model().NOME_FILE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().CODICE_DIPARTIMENTO,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getCodiceDipartimento(), TracciaSDI.model().CODICE_DIPARTIMENTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DATA,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getData(), TracciaSDI.model().DATA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().ID_EGOV,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getIdEgov(), TracciaSDI.model().ID_EGOV.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().CONTENT_TYPE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getContentType(), TracciaSDI.model().CONTENT_TYPE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().RAW_DATA,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getRawData(), TracciaSDI.model().RAW_DATA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().STATO_PROTOCOLLAZIONE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getStatoProtocollazione(), TracciaSDI.model().STATO_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DATA_PROTOCOLLAZIONE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getDataProtocollazione(), TracciaSDI.model().DATA_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getDataProssimaProtocollazione(), TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getTentativiProtocollazione(), TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE,false), "?");
		lstObjects_tracciaSDI.add(new JDBCObject(tracciaSDI.getDettaglioProtocollazione(), TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_tracciaSDI.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_tracciaSDI) {
			// Update tracciaSDI
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_tracciaSDI.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTracciaSdi id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getTracciaSDIFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTracciaSdi id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getTracciaSDIFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTracciaSdi id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getTracciaSDIFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getTracciaSDIFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getTracciaSDIFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getTracciaSDIFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTracciaSdi oldId, TracciaSDI tracciaSDI, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, tracciaSDI,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, tracciaSDI,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, TracciaSDI tracciaSDI, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, tracciaSDI,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, tracciaSDI,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (tracciaSDI.getId()!=null) && (tracciaSDI.getId()>0) ){
			longId = tracciaSDI.getId();
		}
		else{
			IdTracciaSdi idTracciaSDI = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,tracciaSDI);
			longId = this.findIdTracciaSDI(jdbcProperties,log,connection,sqlQueryObject,idTracciaSDI,false);
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
		

		// Object tracciaSDI
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete tracciaSDI
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTracciaSdi idTracciaSDI) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdTracciaSDI(jdbcProperties, log, connection, sqlQueryObject, idTracciaSDI, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getTracciaSDIFieldConverter()));

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
