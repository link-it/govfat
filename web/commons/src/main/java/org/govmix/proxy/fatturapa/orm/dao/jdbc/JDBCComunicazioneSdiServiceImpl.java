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
import org.govmix.proxy.fatturapa.orm.IdComunicazione;
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

import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.ComunicazioneSdi;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCComunicazioneSdiServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCComunicazioneSdiServiceImpl extends JDBCComunicazioneSdiServiceSearchImpl
	implements IJDBCServiceCRUDWithId<ComunicazioneSdi, IdComunicazione, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, ComunicazioneSdi comunicazioneSdi, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				


		// Object comunicazioneSdi
		sqlQueryObjectInsert.addInsertTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()));
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().IDENTIFICATIVO_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().TIPO_COMUNICAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().PROGRESSIVO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DATA_RICEZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().NOME_FILE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().CONTENT_TYPE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().RAW_DATA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().STATO_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DATA_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DETTAGLIO_CONSEGNA,false),"?");

		// Insert comunicazioneSdi
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getComunicazioneSdiFetch().getKeyGeneratorObject(ComunicazioneSdi.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getIdentificativoSdi(),ComunicazioneSdi.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getTipoComunicazione(),ComunicazioneSdi.model().TIPO_COMUNICAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getProgressivo(),ComunicazioneSdi.model().PROGRESSIVO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getDataRicezione(),ComunicazioneSdi.model().DATA_RICEZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getNomeFile(),ComunicazioneSdi.model().NOME_FILE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getContentType(),ComunicazioneSdi.model().CONTENT_TYPE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getRawData(),ComunicazioneSdi.model().RAW_DATA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getStatoConsegna(),ComunicazioneSdi.model().STATO_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getDataConsegna(),ComunicazioneSdi.model().DATA_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getDettaglioConsegna(),ComunicazioneSdi.model().DETTAGLIO_CONSEGNA.getFieldType())
		);
		comunicazioneSdi.setId(id);

		// for comunicazioneSdi
		for (int i = 0; i < comunicazioneSdi.getMetadatoList().size(); i++) {


			// Object comunicazioneSdi.getMetadatoList().get(i)
			ISQLQueryObject sqlQueryObjectInsert_metadato = sqlQueryObjectInsert.newSQLQueryObject();
			sqlQueryObjectInsert_metadato.addInsertTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model().METADATO));
			sqlQueryObjectInsert_metadato.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.NOME,false),"?");
			sqlQueryObjectInsert_metadato.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.VALORE,false),"?");
			sqlQueryObjectInsert_metadato.addInsertField("id_comunicazione_sdi","?");

			// Insert comunicazioneSdi.getMetadatoList().get(i)
			org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_metadato = this.getComunicazioneSdiFetch().getKeyGeneratorObject(ComunicazioneSdi.model().METADATO);
			long id_metadato = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_metadato, keyGenerator_metadato, jdbcProperties.isShowSql(),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getMetadatoList().get(i).getNome(),ComunicazioneSdi.model().METADATO.NOME.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi.getMetadatoList().get(i).getValore(),ComunicazioneSdi.model().METADATO.VALORE.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
			);
			comunicazioneSdi.getMetadatoList().get(i).setId(id_metadato);
		} // fine for 

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione oldId, ComunicazioneSdi comunicazioneSdi, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdComunicazioneSdi(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = comunicazioneSdi.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: comunicazioneSdi.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			comunicazioneSdi.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, comunicazioneSdi, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, ComunicazioneSdi comunicazioneSdi, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
		


		// Object comunicazioneSdi
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()));
		boolean isUpdate_comunicazioneSdi = true;
		java.util.List<JDBCObject> lstObjects_comunicazioneSdi = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().IDENTIFICATIVO_SDI,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getIdentificativoSdi(), ComunicazioneSdi.model().IDENTIFICATIVO_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().TIPO_COMUNICAZIONE,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getTipoComunicazione(), ComunicazioneSdi.model().TIPO_COMUNICAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().PROGRESSIVO,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getProgressivo(), ComunicazioneSdi.model().PROGRESSIVO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DATA_RICEZIONE,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getDataRicezione(), ComunicazioneSdi.model().DATA_RICEZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().NOME_FILE,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getNomeFile(), ComunicazioneSdi.model().NOME_FILE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().CONTENT_TYPE,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getContentType(), ComunicazioneSdi.model().CONTENT_TYPE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().RAW_DATA,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getRawData(), ComunicazioneSdi.model().RAW_DATA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().STATO_CONSEGNA,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getStatoConsegna(), ComunicazioneSdi.model().STATO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DATA_CONSEGNA,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getDataConsegna(), ComunicazioneSdi.model().DATA_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DETTAGLIO_CONSEGNA,false), "?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(comunicazioneSdi.getDettaglioConsegna(), ComunicazioneSdi.model().DETTAGLIO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_comunicazioneSdi.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_comunicazioneSdi) {
			// Update comunicazioneSdi
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_comunicazioneSdi.toArray(new JDBCObject[]{}));
		}
		// for comunicazioneSdi_metadato

		java.util.List<Long> ids_comunicazioneSdi_metadato_da_non_eliminare = new java.util.ArrayList<Long>();
		for (Object comunicazioneSdi_metadato_object : comunicazioneSdi.getMetadatoList()) {
			Metadato comunicazioneSdi_metadato = (Metadato) comunicazioneSdi_metadato_object;
			if(comunicazioneSdi_metadato.getId() == null || comunicazioneSdi_metadato.getId().longValue() <= 0) {

				long id = comunicazioneSdi.getId();			

				// Object comunicazioneSdi_metadato
				ISQLQueryObject sqlQueryObjectInsert_comunicazioneSdi_metadato = sqlQueryObjectInsert.newSQLQueryObject();
				sqlQueryObjectInsert_comunicazioneSdi_metadato.addInsertTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model().METADATO));
				sqlQueryObjectInsert_comunicazioneSdi_metadato.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.NOME,false),"?");
				sqlQueryObjectInsert_comunicazioneSdi_metadato.addInsertField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.VALORE,false),"?");
				sqlQueryObjectInsert_comunicazioneSdi_metadato.addInsertField("id_comunicazione_sdi","?");

				// Insert comunicazioneSdi_metadato
				org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_comunicazioneSdi_metadato = this.getComunicazioneSdiFetch().getKeyGeneratorObject(ComunicazioneSdi.model().METADATO);
				long id_comunicazioneSdi_metadato = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_comunicazioneSdi_metadato, keyGenerator_comunicazioneSdi_metadato, jdbcProperties.isShowSql(),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi_metadato.getNome(),ComunicazioneSdi.model().METADATO.NOME.getFieldType()),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(comunicazioneSdi_metadato.getValore(),ComunicazioneSdi.model().METADATO.VALORE.getFieldType()),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
				);
				comunicazioneSdi_metadato.setId(id_comunicazioneSdi_metadato);

				ids_comunicazioneSdi_metadato_da_non_eliminare.add(comunicazioneSdi_metadato.getId());
			} else {


				// Object comunicazioneSdi_metadato
				ISQLQueryObject sqlQueryObjectUpdate_comunicazioneSdi_metadato = sqlQueryObjectUpdate.newSQLQueryObject();
				sqlQueryObjectUpdate_comunicazioneSdi_metadato.setANDLogicOperator(true);
				sqlQueryObjectUpdate_comunicazioneSdi_metadato.addUpdateTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model().METADATO));
				boolean isUpdate_comunicazioneSdi_metadato = true;
				java.util.List<JDBCObject> lstObjects_comunicazioneSdi_metadato = new java.util.ArrayList<JDBCObject>();
				sqlQueryObjectUpdate_comunicazioneSdi_metadato.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.NOME,false), "?");
				lstObjects_comunicazioneSdi_metadato.add(new JDBCObject(comunicazioneSdi_metadato.getNome(), ComunicazioneSdi.model().METADATO.NOME.getFieldType()));
				sqlQueryObjectUpdate_comunicazioneSdi_metadato.addUpdateField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.VALORE,false), "?");
				lstObjects_comunicazioneSdi_metadato.add(new JDBCObject(comunicazioneSdi_metadato.getValore(), ComunicazioneSdi.model().METADATO.VALORE.getFieldType()));
				sqlQueryObjectUpdate_comunicazioneSdi_metadato.addWhereCondition("id=?");
				ids_comunicazioneSdi_metadato_da_non_eliminare.add(comunicazioneSdi_metadato.getId());
				lstObjects_comunicazioneSdi_metadato.add(new JDBCObject(new Long(comunicazioneSdi_metadato.getId()),Long.class));

				if(isUpdate_comunicazioneSdi_metadato) {
					// Update comunicazioneSdi_metadato
					jdbcUtilities.executeUpdate(sqlQueryObjectUpdate_comunicazioneSdi_metadato.createSQLUpdate(), jdbcProperties.isShowSql(), 
						lstObjects_comunicazioneSdi_metadato.toArray(new JDBCObject[]{}));
				}
			}
		} // fine for comunicazioneSdi_metadato

		// elimino tutte le occorrenze di comunicazioneSdi_metadato non presenti nell'update

		ISQLQueryObject sqlQueryObjectUpdate_metadato_deleteList = sqlQueryObjectUpdate.newSQLQueryObject();
		sqlQueryObjectUpdate_metadato_deleteList.setANDLogicOperator(true);
		sqlQueryObjectUpdate_metadato_deleteList.addDeleteTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model().METADATO));
		java.util.List<JDBCObject> jdbcObjects_comunicazioneSdi_metadato_delete = new java.util.ArrayList<JDBCObject>();

		sqlQueryObjectUpdate_metadato_deleteList.addWhereCondition("id_comunicazione_sdi=?");
		jdbcObjects_comunicazioneSdi_metadato_delete.add(new JDBCObject(comunicazioneSdi.getId(), Long.class));

		StringBuffer marks_comunicazioneSdi_metadato = new StringBuffer();
		if(ids_comunicazioneSdi_metadato_da_non_eliminare.size() > 0) {
			for(Long ids : ids_comunicazioneSdi_metadato_da_non_eliminare) {
				if(marks_comunicazioneSdi_metadato.length() > 0) {
					marks_comunicazioneSdi_metadato.append(",");
				}
				marks_comunicazioneSdi_metadato.append("?");
				jdbcObjects_comunicazioneSdi_metadato_delete.add(new JDBCObject(ids, Long.class));

			}
			sqlQueryObjectUpdate_metadato_deleteList.addWhereCondition("id NOT IN ("+marks_comunicazioneSdi_metadato.toString()+")");
		}

		jdbcUtilities.execute(sqlQueryObjectUpdate_metadato_deleteList.createSQLDelete(), jdbcProperties.isShowSql(), jdbcObjects_comunicazioneSdi_metadato_delete.toArray(new JDBCObject[]{}));



	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getComunicazioneSdiFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getComunicazioneSdiFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getComunicazioneSdiFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getComunicazioneSdiFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getComunicazioneSdiFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getComunicazioneSdiFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione oldId, ComunicazioneSdi comunicazioneSdi, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, comunicazioneSdi,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, comunicazioneSdi,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, ComunicazioneSdi comunicazioneSdi, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, comunicazioneSdi,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, comunicazioneSdi,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, ComunicazioneSdi comunicazioneSdi) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (comunicazioneSdi.getId()!=null) && (comunicazioneSdi.getId()>0) ){
			longId = comunicazioneSdi.getId();
		}
		else{
			IdComunicazione idComunicazioneSdi = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,comunicazioneSdi);
			longId = this.findIdComunicazioneSdi(jdbcProperties,log,connection,sqlQueryObject,idComunicazioneSdi,false);
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
		

		//Recupero oggetto _comunicazioneSdi_metadato
		ISQLQueryObject sqlQueryObjectDelete_comunicazioneSdi_metadato_getToDelete = sqlQueryObjectDelete.newSQLQueryObject();
		sqlQueryObjectDelete_comunicazioneSdi_metadato_getToDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete_comunicazioneSdi_metadato_getToDelete.addFromTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model().METADATO));
		sqlQueryObjectDelete_comunicazioneSdi_metadato_getToDelete.addWhereCondition("id_comunicazione_sdi=?");
		java.util.List<Object> comunicazioneSdi_metadato_toDelete_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectDelete_comunicazioneSdi_metadato_getToDelete.createSQLQuery(), jdbcProperties.isShowSql(), ComunicazioneSdi.model().METADATO, this.getComunicazioneSdiFetch(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class));

		// for comunicazioneSdi_metadato
		for (Object comunicazioneSdi_metadato_object : comunicazioneSdi_metadato_toDelete_list) {
			Metadato comunicazioneSdi_metadato = (Metadato) comunicazioneSdi_metadato_object;

			// Object comunicazioneSdi_metadato
			ISQLQueryObject sqlQueryObjectDelete_comunicazioneSdi_metadato = sqlQueryObjectDelete.newSQLQueryObject();
			sqlQueryObjectDelete_comunicazioneSdi_metadato.setANDLogicOperator(true);
			sqlQueryObjectDelete_comunicazioneSdi_metadato.addDeleteTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model().METADATO));
			sqlQueryObjectDelete_comunicazioneSdi_metadato.addWhereCondition("id=?");

			// Delete comunicazioneSdi_metadato
			if(comunicazioneSdi_metadato != null){
				jdbcUtilities.execute(sqlQueryObjectDelete_comunicazioneSdi_metadato.createSQLDelete(), jdbcProperties.isShowSql(), 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(comunicazioneSdi_metadato.getId()),Long.class));
			}
		} // fine for comunicazioneSdi_metadato

		// Object comunicazioneSdi
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete comunicazioneSdi
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione idComunicazioneSdi) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdComunicazioneSdi(jdbcProperties, log, connection, sqlQueryObject, idComunicazioneSdi, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getComunicazioneSdiFieldConverter()));

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
