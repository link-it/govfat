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

import org.slf4j.Logger;

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

import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCTracciaSDIServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCTracciaSDIServiceImpl extends JDBCTracciaSDIServiceSearchImpl
	implements IJDBCServiceCRUDWithoutId<TracciaSDI, JDBCServiceManager> {

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

		// for tracciaSDI
		for (int i = 0; i < tracciaSDI.getMetadatoList().size(); i++) {


			// Object tracciaSDI.getMetadatoList().get(i)
			ISQLQueryObject sqlQueryObjectInsert_metadato = sqlQueryObjectInsert.newSQLQueryObject();
			sqlQueryObjectInsert_metadato.addInsertTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model().METADATO));
			sqlQueryObjectInsert_metadato.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.RICHIESTA,false),"?");
			sqlQueryObjectInsert_metadato.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.NOME,false),"?");
			sqlQueryObjectInsert_metadato.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.VALORE,false),"?");
			sqlQueryObjectInsert_metadato.addInsertField("id_traccia_sdi","?");

			// Insert tracciaSDI.getMetadatoList().get(i)
			org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_metadato = this.getTracciaSDIFetch().getKeyGeneratorObject(TracciaSDI.model().METADATO);
			long id_metadato = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_metadato, keyGenerator_metadato, jdbcProperties.isShowSql(),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getMetadatoList().get(i).getRichiesta(),TracciaSDI.model().METADATO.RICHIESTA.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getMetadatoList().get(i).getNome(),TracciaSDI.model().METADATO.NOME.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI.getMetadatoList().get(i).getValore(),TracciaSDI.model().METADATO.VALORE.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
			);
			tracciaSDI.getMetadatoList().get(i).setId(id_metadato);
		} // fine for 

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		Long tableId = tracciaSDI.getId();
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
		// for tracciaSDI_metadato

		java.util.List<Long> ids_tracciaSDI_metadato_da_non_eliminare = new java.util.ArrayList<Long>();
		for (Object tracciaSDI_metadato_object : tracciaSDI.getMetadatoList()) {
			Metadato tracciaSDI_metadato = (Metadato) tracciaSDI_metadato_object;
			if(tracciaSDI_metadato.getId() == null || tracciaSDI_metadato.getId().longValue() <= 0) {

				long id = tracciaSDI.getId();			

				// Object tracciaSDI_metadato
				ISQLQueryObject sqlQueryObjectInsert_tracciaSDI_metadato = sqlQueryObjectInsert.newSQLQueryObject();
				sqlQueryObjectInsert_tracciaSDI_metadato.addInsertTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model().METADATO));
				sqlQueryObjectInsert_tracciaSDI_metadato.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.RICHIESTA,false),"?");
				sqlQueryObjectInsert_tracciaSDI_metadato.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.NOME,false),"?");
				sqlQueryObjectInsert_tracciaSDI_metadato.addInsertField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.VALORE,false),"?");
				sqlQueryObjectInsert_tracciaSDI_metadato.addInsertField("id_traccia_sdi","?");

				// Insert tracciaSDI_metadato
				org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_tracciaSDI_metadato = this.getTracciaSDIFetch().getKeyGeneratorObject(TracciaSDI.model().METADATO);
				long id_tracciaSDI_metadato = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_tracciaSDI_metadato, keyGenerator_tracciaSDI_metadato, jdbcProperties.isShowSql(),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI_metadato.getRichiesta(),TracciaSDI.model().METADATO.RICHIESTA.getFieldType()),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI_metadato.getNome(),TracciaSDI.model().METADATO.NOME.getFieldType()),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tracciaSDI_metadato.getValore(),TracciaSDI.model().METADATO.VALORE.getFieldType()),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
				);
				tracciaSDI_metadato.setId(id_tracciaSDI_metadato);

				ids_tracciaSDI_metadato_da_non_eliminare.add(tracciaSDI_metadato.getId());
			} else {


				// Object tracciaSDI_metadato
				ISQLQueryObject sqlQueryObjectUpdate_tracciaSDI_metadato = sqlQueryObjectUpdate.newSQLQueryObject();
				sqlQueryObjectUpdate_tracciaSDI_metadato.setANDLogicOperator(true);
				sqlQueryObjectUpdate_tracciaSDI_metadato.addUpdateTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model().METADATO));
				boolean isUpdate_tracciaSDI_metadato = true;
				java.util.List<JDBCObject> lstObjects_tracciaSDI_metadato = new java.util.ArrayList<JDBCObject>();
				sqlQueryObjectUpdate_tracciaSDI_metadato.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.RICHIESTA,false), "?");
				lstObjects_tracciaSDI_metadato.add(new JDBCObject(tracciaSDI_metadato.getRichiesta(), TracciaSDI.model().METADATO.RICHIESTA.getFieldType()));
				sqlQueryObjectUpdate_tracciaSDI_metadato.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.NOME,false), "?");
				lstObjects_tracciaSDI_metadato.add(new JDBCObject(tracciaSDI_metadato.getNome(), TracciaSDI.model().METADATO.NOME.getFieldType()));
				sqlQueryObjectUpdate_tracciaSDI_metadato.addUpdateField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.VALORE,false), "?");
				lstObjects_tracciaSDI_metadato.add(new JDBCObject(tracciaSDI_metadato.getValore(), TracciaSDI.model().METADATO.VALORE.getFieldType()));
				sqlQueryObjectUpdate_tracciaSDI_metadato.addWhereCondition("id=?");
				ids_tracciaSDI_metadato_da_non_eliminare.add(tracciaSDI_metadato.getId());
				lstObjects_tracciaSDI_metadato.add(new JDBCObject(new Long(tracciaSDI_metadato.getId()),Long.class));

				if(isUpdate_tracciaSDI_metadato) {
					// Update tracciaSDI_metadato
					jdbcUtilities.executeUpdate(sqlQueryObjectUpdate_tracciaSDI_metadato.createSQLUpdate(), jdbcProperties.isShowSql(), 
						lstObjects_tracciaSDI_metadato.toArray(new JDBCObject[]{}));
				}
			}
		} // fine for tracciaSDI_metadato

		// elimino tutte le occorrenze di tracciaSDI_metadato non presenti nell'update

		ISQLQueryObject sqlQueryObjectUpdate_metadato_deleteList = sqlQueryObjectUpdate.newSQLQueryObject();
		sqlQueryObjectUpdate_metadato_deleteList.setANDLogicOperator(true);
		sqlQueryObjectUpdate_metadato_deleteList.addDeleteTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model().METADATO));
		java.util.List<JDBCObject> jdbcObjects_tracciaSDI_metadato_delete = new java.util.ArrayList<JDBCObject>();

		sqlQueryObjectUpdate_metadato_deleteList.addWhereCondition("id_traccia_sdi=?");
		jdbcObjects_tracciaSDI_metadato_delete.add(new JDBCObject(tracciaSDI.getId(), Long.class));

		StringBuffer marks_tracciaSDI_metadato = new StringBuffer();
		if(ids_tracciaSDI_metadato_da_non_eliminare.size() > 0) {
			for(Long ids : ids_tracciaSDI_metadato_da_non_eliminare) {
				if(marks_tracciaSDI_metadato.length() > 0) {
					marks_tracciaSDI_metadato.append(",");
				}
				marks_tracciaSDI_metadato.append("?");
				jdbcObjects_tracciaSDI_metadato_delete.add(new JDBCObject(ids, Long.class));

			}
			sqlQueryObjectUpdate_metadato_deleteList.addWhereCondition("id NOT IN ("+marks_tracciaSDI_metadato.toString()+")");
		}

		jdbcUtilities.execute(sqlQueryObjectUpdate_metadato_deleteList.createSQLDelete(), jdbcProperties.isShowSql(), jdbcObjects_tracciaSDI_metadato_delete.toArray(new JDBCObject[]{}));



	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, tracciaSDI),
				this.getTracciaSDIFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, tracciaSDI),
				this.getTracciaSDIFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, tracciaSDI),
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
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		Long id = tracciaSDI.getId();
		if(id != null && this.exists(jdbcProperties, log, connection, sqlQueryObject, id)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tracciaSDI,idMappingResolutionBehaviour);		
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
		if(tracciaSDI.getId()==null){
			throw new Exception("Parameter "+tracciaSDI.getClass().getName()+".id is null");
		}
		if(tracciaSDI.getId()<=0){
			throw new Exception("Parameter "+tracciaSDI.getClass().getName()+".id is less equals 0");
		}
		longId = tracciaSDI.getId();
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		//Recupero oggetto _tracciaSDI_metadato
		ISQLQueryObject sqlQueryObjectDelete_tracciaSDI_metadato_getToDelete = sqlQueryObjectDelete.newSQLQueryObject();
		sqlQueryObjectDelete_tracciaSDI_metadato_getToDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete_tracciaSDI_metadato_getToDelete.addFromTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model().METADATO));
		sqlQueryObjectDelete_tracciaSDI_metadato_getToDelete.addWhereCondition("id_traccia_sdi=?");
		java.util.List<Object> tracciaSDI_metadato_toDelete_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectDelete_tracciaSDI_metadato_getToDelete.createSQLQuery(), jdbcProperties.isShowSql(), TracciaSDI.model().METADATO, this.getTracciaSDIFetch(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class));

		// for tracciaSDI_metadato
		for (Object tracciaSDI_metadato_object : tracciaSDI_metadato_toDelete_list) {
			Metadato tracciaSDI_metadato = (Metadato) tracciaSDI_metadato_object;

			// Object tracciaSDI_metadato
			ISQLQueryObject sqlQueryObjectDelete_tracciaSDI_metadato = sqlQueryObjectDelete.newSQLQueryObject();
			sqlQueryObjectDelete_tracciaSDI_metadato.setANDLogicOperator(true);
			sqlQueryObjectDelete_tracciaSDI_metadato.addDeleteTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model().METADATO));
			sqlQueryObjectDelete_tracciaSDI_metadato.addWhereCondition("id=?");

			// Delete tracciaSDI_metadato
			if(tracciaSDI_metadato != null){
				jdbcUtilities.execute(sqlQueryObjectDelete_tracciaSDI_metadato.createSQLDelete(), jdbcProperties.isShowSql(), 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(tracciaSDI_metadato.getId()),Long.class));
			}
		} // fine for tracciaSDI_metadato

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
