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
import org.govmix.proxy.fatturapa.orm.IdPagamento;
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

import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccPagamentoServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccPagamentoServiceImpl extends JDBCPccPagamentoServiceSearchImpl
	implements IJDBCServiceCRUDWithId<PccPagamento, IdPagamento, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccPagamento pccPagamento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _fatturaElettronica
		Long id_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_fatturaElettronica = null;
		idLogic_fatturaElettronica = pccPagamento.getIdFattura();
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


		// Object pccPagamento
		sqlQueryObjectInsert.addInsertTable(this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().IMPORTO_PAGATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().NATURA_SPESA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().CAPITOLI_SPESA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().ESTREMI_IMPEGNO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().NUMERO_MANDATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DATA_MANDATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().CODICE_CIG,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().CODICE_CUP,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DESCRIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DATA_RICHIESTA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DATA_QUERY,false),"?");
		sqlQueryObjectInsert.addInsertField("id_fattura_elettronica","?");

		// Insert pccPagamento
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccPagamentoFetch().getKeyGeneratorObject(PccPagamento.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getImportoPagato(),PccPagamento.model().IMPORTO_PAGATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getNaturaSpesa(),PccPagamento.model().NATURA_SPESA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getCapitoliSpesa(),PccPagamento.model().CAPITOLI_SPESA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getEstremiImpegno(),PccPagamento.model().ESTREMI_IMPEGNO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getNumeroMandato(),PccPagamento.model().NUMERO_MANDATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getDataMandato(),PccPagamento.model().DATA_MANDATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getIdFiscaleIvaBeneficiario(),PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getCodiceCig(),PccPagamento.model().CODICE_CIG.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getCodiceCup(),PccPagamento.model().CODICE_CUP.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getDescrizione(),PccPagamento.model().DESCRIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getDataRichiesta(),PccPagamento.model().DATA_RICHIESTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccPagamento.getDataQuery(),PccPagamento.model().DATA_QUERY.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_fatturaElettronica,Long.class)
		);
		pccPagamento.setId(id);

	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdPagamento oldId, PccPagamento pccPagamento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdPccPagamento(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = pccPagamento.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: pccPagamento.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			pccPagamento.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccPagamento, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccPagamento pccPagamento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccPagamento_fatturaElettronica
		Long id_pccPagamento_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_pccPagamento_fatturaElettronica = null;
		idLogic_pccPagamento_fatturaElettronica = pccPagamento.getIdFattura();
		if(idLogic_pccPagamento_fatturaElettronica!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccPagamento_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findTableId(idLogic_pccPagamento_fatturaElettronica, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccPagamento_fatturaElettronica = idLogic_pccPagamento_fatturaElettronica.getId();
				if(id_pccPagamento_fatturaElettronica==null || id_pccPagamento_fatturaElettronica<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccPagamento
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()));
		boolean isUpdate_pccPagamento = true;
		java.util.List<JDBCObject> lstObjects_pccPagamento = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().IMPORTO_PAGATO,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getImportoPagato(), PccPagamento.model().IMPORTO_PAGATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().NATURA_SPESA,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getNaturaSpesa(), PccPagamento.model().NATURA_SPESA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().CAPITOLI_SPESA,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getCapitoliSpesa(), PccPagamento.model().CAPITOLI_SPESA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().ESTREMI_IMPEGNO,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getEstremiImpegno(), PccPagamento.model().ESTREMI_IMPEGNO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().NUMERO_MANDATO,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getNumeroMandato(), PccPagamento.model().NUMERO_MANDATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DATA_MANDATO,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getDataMandato(), PccPagamento.model().DATA_MANDATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getIdFiscaleIvaBeneficiario(), PccPagamento.model().ID_FISCALE_IVA_BENEFICIARIO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().CODICE_CIG,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getCodiceCig(), PccPagamento.model().CODICE_CIG.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().CODICE_CUP,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getCodiceCup(), PccPagamento.model().CODICE_CUP.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DESCRIZIONE,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getDescrizione(), PccPagamento.model().DESCRIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DATA_RICHIESTA,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getDataRichiesta(), PccPagamento.model().DATA_RICHIESTA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccPagamentoFieldConverter().toColumn(PccPagamento.model().DATA_QUERY,false), "?");
		lstObjects_pccPagamento.add(new JDBCObject(pccPagamento.getDataQuery(), PccPagamento.model().DATA_QUERY.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_fattura_elettronica","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccPagamento.add(new JDBCObject(id_pccPagamento_fatturaElettronica, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccPagamento.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccPagamento) {
			// Update pccPagamento
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccPagamento.toArray(new JDBCObject[]{}));
		}

	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdPagamento id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccPagamentoFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdPagamento id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccPagamentoFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdPagamento id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccPagamentoFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccPagamentoFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccPagamentoFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccPagamentoFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdPagamento oldId, PccPagamento pccPagamento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, pccPagamento,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccPagamento,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccPagamento pccPagamento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccPagamento,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccPagamento,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccPagamento pccPagamento) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (pccPagamento.getId()!=null) && (pccPagamento.getId()>0) ){
			longId = pccPagamento.getId();
		}
		else{
			IdPagamento idPccPagamento = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccPagamento);
			longId = this.findIdPccPagamento(jdbcProperties,log,connection,sqlQueryObject,idPccPagamento,false);
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
		

		// Object pccPagamento
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccPagamentoFieldConverter().toTable(PccPagamento.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccPagamento
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdPagamento idPccPagamento) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdPccPagamento(jdbcProperties, log, connection, sqlQueryObject, idPccPagamento, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccPagamentoFieldConverter()));

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
