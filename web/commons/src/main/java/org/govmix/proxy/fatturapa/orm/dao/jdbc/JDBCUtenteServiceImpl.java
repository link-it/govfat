/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
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
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.utils.sql.ISQLQueryObject;

/**     
 * JDBCUtenteServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCUtenteServiceImpl extends JDBCUtenteServiceSearchImpl
	implements IJDBCServiceCRUDWithId<Utente, IdUtente, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Utente utente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				



		// Object utente
		sqlQueryObjectInsert.addInsertTable(this.getUtenteFieldConverter().toTable(Utente.model()));
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().USERNAME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().PASSWORD,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().NOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().COGNOME,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().ROLE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().TIPO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().ESTERNO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getUtenteFieldConverter().toColumn(Utente.model().SISTEMA,false),"?");

		// Insert utente
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getUtenteFetch().getKeyGeneratorObject(Utente.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getUsername(),Utente.model().USERNAME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getPassword(),Utente.model().PASSWORD.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getNome(),Utente.model().NOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getCognome(),Utente.model().COGNOME.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getRole(),Utente.model().ROLE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getTipo(),Utente.model().TIPO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getEsterno(),Utente.model().ESTERNO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(utente.getSistema(),Utente.model().SISTEMA.getFieldType())
		);
		utente.setId(id);

		// for utente
		for (int i = 0; i < utente.getUtenteDipartimentoList().size(); i++) {

			// Object _utenteDipartimento_dipartimento
			Long id_utenteDipartimento_dipartimento = null;
			org.govmix.proxy.fatturapa.orm.IdDipartimento idLogic_utenteDipartimento_dipartimento = null;
			idLogic_utenteDipartimento_dipartimento = utente.getUtenteDipartimentoList().get(i).getIdDipartimento();
			if(idLogic_utenteDipartimento_dipartimento!=null){
				if(idMappingResolutionBehaviour==null ||
					(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
					id_utenteDipartimento_dipartimento = ((JDBCDipartimentoServiceSearch)(this.getServiceManager().getDipartimentoServiceSearch())).findTableId(idLogic_utenteDipartimento_dipartimento, false);
				}
				else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
					id_utenteDipartimento_dipartimento = idLogic_utenteDipartimento_dipartimento.getId();
					if(id_utenteDipartimento_dipartimento==null || id_utenteDipartimento_dipartimento<=0){
						throw new Exception("Logic id not contains table id");
					}
				}
			}


			// Object utente.getUtenteDipartimentoList().get(i)
			ISQLQueryObject sqlQueryObjectInsert_utenteDipartimento = sqlQueryObjectInsert.newSQLQueryObject();
			sqlQueryObjectInsert_utenteDipartimento.addInsertTable(this.getUtenteFieldConverter().toTable(Utente.model().UTENTE_DIPARTIMENTO));
			sqlQueryObjectInsert_utenteDipartimento.addInsertField("id_dipartimento","?");
			sqlQueryObjectInsert_utenteDipartimento.addInsertField("id_utente","?");

			// Insert utente.getUtenteDipartimentoList().get(i)
			org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_utenteDipartimento = this.getUtenteFetch().getKeyGeneratorObject(Utente.model().UTENTE_DIPARTIMENTO);
			long id_utenteDipartimento = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_utenteDipartimento, keyGenerator_utenteDipartimento, jdbcProperties.isShowSql(),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_utenteDipartimento_dipartimento,Long.class),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
			);
			utente.getUtenteDipartimentoList().get(i).setId(id_utenteDipartimento);
		} // fine for 

	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente oldId, Utente utente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdUtente(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = utente.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: utente.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			utente.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, utente, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Utente utente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			



		// Object utente
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getUtenteFieldConverter().toTable(Utente.model()));
		boolean isUpdate_utente = true;
		java.util.List<JDBCObject> lstObjects_utente = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().USERNAME,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getUsername(), Utente.model().USERNAME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().PASSWORD,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getPassword(), Utente.model().PASSWORD.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().NOME,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getNome(), Utente.model().NOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().COGNOME,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getCognome(), Utente.model().COGNOME.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().ROLE,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getRole(), Utente.model().ROLE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().TIPO,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getTipo(), Utente.model().TIPO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().ESTERNO,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getEsterno(), Utente.model().ESTERNO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getUtenteFieldConverter().toColumn(Utente.model().SISTEMA,false), "?");
		lstObjects_utente.add(new JDBCObject(utente.getSistema(), Utente.model().SISTEMA.getFieldType()));
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_utente.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_utente) {
			// Update utente
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_utente.toArray(new JDBCObject[]{}));
		}
		// for utente_utenteDipartimento

		java.util.List<Long> ids_utente_utenteDipartimento_da_non_eliminare = new java.util.ArrayList<Long>();
		for (Object utente_utenteDipartimento_object : utente.getUtenteDipartimentoList()) {
			UtenteDipartimento utente_utenteDipartimento = (UtenteDipartimento) utente_utenteDipartimento_object;
			if(utente_utenteDipartimento.getId() == null || utente_utenteDipartimento.getId().longValue() <= 0) {

				long id = utente.getId();			
				// Object _utente_utenteDipartimento_dipartimento
				Long id_utente_utenteDipartimento_dipartimento = null;
				org.govmix.proxy.fatturapa.orm.IdDipartimento idLogic_utente_utenteDipartimento_dipartimento = null;
				idLogic_utente_utenteDipartimento_dipartimento = utente_utenteDipartimento.getIdDipartimento();
				if(idLogic_utente_utenteDipartimento_dipartimento!=null){
					if(idMappingResolutionBehaviour==null ||
						(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
						id_utente_utenteDipartimento_dipartimento = ((JDBCDipartimentoServiceSearch)(this.getServiceManager().getDipartimentoServiceSearch())).findTableId(idLogic_utente_utenteDipartimento_dipartimento, false);
					}
					else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
						id_utente_utenteDipartimento_dipartimento = idLogic_utente_utenteDipartimento_dipartimento.getId();
						if(id_utente_utenteDipartimento_dipartimento==null || id_utente_utenteDipartimento_dipartimento<=0){
							throw new Exception("Logic id not contains table id");
						}
					}
				}


				// Object utente_utenteDipartimento
				ISQLQueryObject sqlQueryObjectInsert_utente_utenteDipartimento = sqlQueryObjectInsert.newSQLQueryObject();
				sqlQueryObjectInsert_utente_utenteDipartimento.addInsertTable(this.getUtenteFieldConverter().toTable(Utente.model().UTENTE_DIPARTIMENTO));
				sqlQueryObjectInsert_utente_utenteDipartimento.addInsertField("id_dipartimento","?");
				sqlQueryObjectInsert_utente_utenteDipartimento.addInsertField("id_utente","?");

				// Insert utente_utenteDipartimento
				org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_utente_utenteDipartimento = this.getUtenteFetch().getKeyGeneratorObject(Utente.model().UTENTE_DIPARTIMENTO);
				long id_utente_utenteDipartimento = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_utente_utenteDipartimento, keyGenerator_utente_utenteDipartimento, jdbcProperties.isShowSql(),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_utente_utenteDipartimento_dipartimento,Long.class),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
				);
				utente_utenteDipartimento.setId(id_utente_utenteDipartimento);

				ids_utente_utenteDipartimento_da_non_eliminare.add(utente_utenteDipartimento.getId());
			} else {

				// Object _utente_utenteDipartimento_dipartimento
				Long id_utente_utenteDipartimento_dipartimento = null;
				org.govmix.proxy.fatturapa.orm.IdDipartimento idLogic_utente_utenteDipartimento_dipartimento = null;
				idLogic_utente_utenteDipartimento_dipartimento = utente_utenteDipartimento.getIdDipartimento();
				if(idLogic_utente_utenteDipartimento_dipartimento!=null){
					if(idMappingResolutionBehaviour==null ||
						(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
						id_utente_utenteDipartimento_dipartimento = ((JDBCDipartimentoServiceSearch)(this.getServiceManager().getDipartimentoServiceSearch())).findTableId(idLogic_utente_utenteDipartimento_dipartimento, false);
					}
					else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
						id_utente_utenteDipartimento_dipartimento = idLogic_utente_utenteDipartimento_dipartimento.getId();
						if(id_utente_utenteDipartimento_dipartimento==null || id_utente_utenteDipartimento_dipartimento<=0){
							throw new Exception("Logic id not contains table id");
						}
					}
				}


				// Object utente_utenteDipartimento
				ISQLQueryObject sqlQueryObjectUpdate_utente_utenteDipartimento = sqlQueryObjectUpdate.newSQLQueryObject();
				sqlQueryObjectUpdate_utente_utenteDipartimento.setANDLogicOperator(true);
				sqlQueryObjectUpdate_utente_utenteDipartimento.addUpdateTable(this.getUtenteFieldConverter().toTable(Utente.model().UTENTE_DIPARTIMENTO));
				boolean isUpdate_utente_utenteDipartimento = true;
				java.util.List<JDBCObject> lstObjects_utente_utenteDipartimento = new java.util.ArrayList<JDBCObject>();
				if(setIdMappingResolutionBehaviour){
					sqlQueryObjectUpdate_utente_utenteDipartimento.addUpdateField("id_dipartimento","?");
				}
				if(setIdMappingResolutionBehaviour){
					lstObjects_utente_utenteDipartimento.add(new JDBCObject(id_utente_utenteDipartimento_dipartimento, Long.class));
				}
				sqlQueryObjectUpdate_utente_utenteDipartimento.addWhereCondition("id=?");
				ids_utente_utenteDipartimento_da_non_eliminare.add(utente_utenteDipartimento.getId());
				lstObjects_utente_utenteDipartimento.add(new JDBCObject(new Long(utente_utenteDipartimento.getId()),Long.class));

				if(isUpdate_utente_utenteDipartimento) {
					// Update utente_utenteDipartimento
					jdbcUtilities.executeUpdate(sqlQueryObjectUpdate_utente_utenteDipartimento.createSQLUpdate(), jdbcProperties.isShowSql(), 
						lstObjects_utente_utenteDipartimento.toArray(new JDBCObject[]{}));
				}
			}
		} // fine for utente_utenteDipartimento

		// elimino tutte le occorrenze di utente_utenteDipartimento non presenti nell'update

		ISQLQueryObject sqlQueryObjectUpdate_utenteDipartimento_deleteList = sqlQueryObjectUpdate.newSQLQueryObject();
		sqlQueryObjectUpdate_utenteDipartimento_deleteList.setANDLogicOperator(true);
		sqlQueryObjectUpdate_utenteDipartimento_deleteList.addDeleteTable(this.getUtenteFieldConverter().toTable(Utente.model().UTENTE_DIPARTIMENTO));
		java.util.List<JDBCObject> jdbcObjects_utente_utenteDipartimento_delete = new java.util.ArrayList<JDBCObject>();

		sqlQueryObjectUpdate_utenteDipartimento_deleteList.addWhereCondition("id_utente=?");
		jdbcObjects_utente_utenteDipartimento_delete.add(new JDBCObject(utente.getId(), Long.class));

		StringBuffer marks_utente_utenteDipartimento = new StringBuffer();
		if(ids_utente_utenteDipartimento_da_non_eliminare.size() > 0) {
			for(Long ids : ids_utente_utenteDipartimento_da_non_eliminare) {
				if(marks_utente_utenteDipartimento.length() > 0) {
					marks_utente_utenteDipartimento.append(",");
				}
				marks_utente_utenteDipartimento.append("?");
				jdbcObjects_utente_utenteDipartimento_delete.add(new JDBCObject(ids, Long.class));

			}
			sqlQueryObjectUpdate_utenteDipartimento_deleteList.addWhereCondition("id NOT IN ("+marks_utente_utenteDipartimento.toString()+")");
		}

		jdbcUtilities.execute(sqlQueryObjectUpdate_utenteDipartimento_deleteList.createSQLDelete(), jdbcProperties.isShowSql(), jdbcObjects_utente_utenteDipartimento_delete.toArray(new JDBCObject[]{}));

	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getUtenteFieldConverter().toTable(Utente.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getUtenteFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getUtenteFieldConverter().toTable(Utente.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getUtenteFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getUtenteFieldConverter().toTable(Utente.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getUtenteFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getUtenteFieldConverter().toTable(Utente.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getUtenteFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getUtenteFieldConverter().toTable(Utente.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getUtenteFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getUtenteFieldConverter().toTable(Utente.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getUtenteFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente oldId, Utente utente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, utente,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, utente,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Utente utente, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, utente,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, utente,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Utente utente) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (utente.getId()!=null) && (utente.getId()>0) ){
			longId = utente.getId();
		}
		else{
			IdUtente idUtente = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,utente);
			longId = this.findIdUtente(jdbcProperties,log,connection,sqlQueryObject,idUtente,false);
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
		IdUtente idUtente = this.convertToId(jdbcProperties, log, connection, sqlQueryObject, this.get(jdbcProperties, log, connection, sqlQueryObject, id, null));
		
		IPaginatedExpression expression = this.getServiceManager().getNotificaEsitoCommittenteServiceSearch().newPaginatedExpression();
		expression.equals(NotificaEsitoCommittente.model().UTENTE.USERNAME, idUtente.getUsername());
		
		if(this.getServiceManager().getNotificaEsitoCommittenteServiceSearch().findAll(expression).size() > 0) {
			throw new ServiceException("utente.delete.ko.esitoCommittente");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		//Recupero oggetto _utente_utenteDipartimento
		ISQLQueryObject sqlQueryObjectDelete_utente_utenteDipartimento_getToDelete = sqlQueryObjectDelete.newSQLQueryObject();
		sqlQueryObjectDelete_utente_utenteDipartimento_getToDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete_utente_utenteDipartimento_getToDelete.addFromTable(this.getUtenteFieldConverter().toTable(Utente.model().UTENTE_DIPARTIMENTO));
		sqlQueryObjectDelete_utente_utenteDipartimento_getToDelete.addWhereCondition("id_utente=?");
		java.util.List<Object> utente_utenteDipartimento_toDelete_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectDelete_utente_utenteDipartimento_getToDelete.createSQLQuery(), jdbcProperties.isShowSql(), Utente.model().UTENTE_DIPARTIMENTO, this.getUtenteFetch(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class));

		// for utente_utenteDipartimento
		for (Object utente_utenteDipartimento_object : utente_utenteDipartimento_toDelete_list) {
			UtenteDipartimento utente_utenteDipartimento = (UtenteDipartimento) utente_utenteDipartimento_object;

			// Object utente_utenteDipartimento
			ISQLQueryObject sqlQueryObjectDelete_utente_utenteDipartimento = sqlQueryObjectDelete.newSQLQueryObject();
			sqlQueryObjectDelete_utente_utenteDipartimento.setANDLogicOperator(true);
			sqlQueryObjectDelete_utente_utenteDipartimento.addDeleteTable(this.getUtenteFieldConverter().toTable(Utente.model().UTENTE_DIPARTIMENTO));
			sqlQueryObjectDelete_utente_utenteDipartimento.addWhereCondition("id=?");

			// Delete utente_utenteDipartimento
			jdbcUtilities.execute(sqlQueryObjectDelete_utente_utenteDipartimento.createSQLDelete(), jdbcProperties.isShowSql(), 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(utente_utenteDipartimento.getId()),Long.class));
		} // fine for utente_utenteDipartimento

		// Object utente
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getUtenteFieldConverter().toTable(Utente.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete utente
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente idUtente) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdUtente(jdbcProperties, log, connection, sqlQueryObject, idUtente, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getUtenteFieldConverter()));

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
