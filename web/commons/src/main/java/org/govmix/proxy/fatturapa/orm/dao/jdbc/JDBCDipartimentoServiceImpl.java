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
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
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
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCDipartimentoServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCDipartimentoServiceImpl extends JDBCDipartimentoServiceSearchImpl
	implements IJDBCServiceCRUDWithId<Dipartimento, IdDipartimento, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Dipartimento dipartimento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _ente
		Long id_ente = null;
		org.govmix.proxy.fatturapa.orm.IdEnte idLogic_ente = null;
		idLogic_ente = dipartimento.getEnte();
		if(idLogic_ente!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_ente = ((JDBCEnteServiceSearch)(this.getServiceManager().getEnteServiceSearch())).findTableId(idLogic_ente, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_ente = idLogic_ente.getId();
				if(id_ente==null || id_ente<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _registro
		Long id_registro = null;
		org.govmix.proxy.fatturapa.orm.IdRegistro idLogic_registro = null;
		idLogic_registro = dipartimento.getRegistro();
		if(idLogic_registro!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_registro = ((JDBCRegistroServiceSearch)(this.getServiceManager().getRegistroServiceSearch())).findTableId(idLogic_registro, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_registro = idLogic_registro.getId();
				if(id_registro==null || id_registro<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object dipartimento
		sqlQueryObjectInsert.addInsertTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model()));
		sqlQueryObjectInsert.addInsertField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().CODICE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DESCRIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().ACCETTAZIONE_AUTOMATICA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().MODALITA_PUSH,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().LISTA_EMAIL_NOTIFICHE,false),"?");
		sqlQueryObjectInsert.addInsertField("id_ente","?");
		sqlQueryObjectInsert.addInsertField("id_registro","?");

		// Insert dipartimento
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getDipartimentoFetch().getKeyGeneratorObject(Dipartimento.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(dipartimento.getCodice(),Dipartimento.model().CODICE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(dipartimento.getDescrizione(),Dipartimento.model().DESCRIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(dipartimento.getAccettazioneAutomatica(),Dipartimento.model().ACCETTAZIONE_AUTOMATICA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(dipartimento.getModalitaPush(),Dipartimento.model().MODALITA_PUSH.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(dipartimento.getListaEmailNotifiche(),Dipartimento.model().LISTA_EMAIL_NOTIFICHE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_ente,Long.class),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_registro,Long.class)
		);
		dipartimento.setId(id);

		// for dipartimento
		for (int i = 0; i < dipartimento.getDipartimentoPropertyValueList().size(); i++) {

			// Object _dipartimentoPropertyValue_dipartimentoProperty
			Long id_dipartimentoPropertyValue_dipartimentoProperty = null;
			org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty idLogic_dipartimentoPropertyValue_dipartimentoProperty = null;
			idLogic_dipartimentoPropertyValue_dipartimentoProperty = dipartimento.getDipartimentoPropertyValueList().get(i).getIdProperty();
			if(idLogic_dipartimentoPropertyValue_dipartimentoProperty!=null){
				if(idMappingResolutionBehaviour==null ||
					(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
					id_dipartimentoPropertyValue_dipartimentoProperty = ((JDBCDipartimentoPropertyServiceSearch)(this.getServiceManager().getDipartimentoPropertyServiceSearch())).findTableId(idLogic_dipartimentoPropertyValue_dipartimentoProperty, false);
				}
				else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
					id_dipartimentoPropertyValue_dipartimentoProperty = idLogic_dipartimentoPropertyValue_dipartimentoProperty.getId();
					if(id_dipartimentoPropertyValue_dipartimentoProperty==null || id_dipartimentoPropertyValue_dipartimentoProperty<=0){
						throw new Exception("Logic id not contains table id");
					}
				}
			}


			// Object dipartimento.getDipartimentoPropertyValueList().get(i)
			ISQLQueryObject sqlQueryObjectInsert_dipartimentoPropertyValue = sqlQueryObjectInsert.newSQLQueryObject();
			sqlQueryObjectInsert_dipartimentoPropertyValue.addInsertTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
			sqlQueryObjectInsert_dipartimentoPropertyValue.addInsertField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE,false),"?");
			sqlQueryObjectInsert_dipartimentoPropertyValue.addInsertField("id_dipartimento_property","?");
			sqlQueryObjectInsert_dipartimentoPropertyValue.addInsertField("id_dipartimento","?");

			// Insert dipartimento.getDipartimentoPropertyValueList().get(i)
			org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_dipartimentoPropertyValue = this.getDipartimentoFetch().getKeyGeneratorObject(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE);
			long id_dipartimentoPropertyValue = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_dipartimentoPropertyValue, keyGenerator_dipartimentoPropertyValue, jdbcProperties.isShowSql(),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(dipartimento.getDipartimentoPropertyValueList().get(i).getValore(),Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_dipartimentoPropertyValue_dipartimentoProperty,Long.class),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
			);
			dipartimento.getDipartimentoPropertyValueList().get(i).setId(id_dipartimentoPropertyValue);
		} // fine for 

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento oldId, Dipartimento dipartimento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdDipartimento(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = dipartimento.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: dipartimento.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			dipartimento.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, dipartimento, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Dipartimento dipartimento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _dipartimento_ente
		Long id_dipartimento_ente = null;
		org.govmix.proxy.fatturapa.orm.IdEnte idLogic_dipartimento_ente = null;
		idLogic_dipartimento_ente = dipartimento.getEnte();
		if(idLogic_dipartimento_ente!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_dipartimento_ente = ((JDBCEnteServiceSearch)(this.getServiceManager().getEnteServiceSearch())).findTableId(idLogic_dipartimento_ente, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_dipartimento_ente = idLogic_dipartimento_ente.getId();
				if(id_dipartimento_ente==null || id_dipartimento_ente<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _dipartimento_registro
		Long id_dipartimento_registro = null;
		org.govmix.proxy.fatturapa.orm.IdRegistro idLogic_dipartimento_registro = null;
		idLogic_dipartimento_registro = dipartimento.getRegistro();
		if(idLogic_dipartimento_registro!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_dipartimento_registro = ((JDBCRegistroServiceSearch)(this.getServiceManager().getRegistroServiceSearch())).findTableId(idLogic_dipartimento_registro, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_dipartimento_registro = idLogic_dipartimento_registro.getId();
				if(id_dipartimento_registro==null || id_dipartimento_registro<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object dipartimento
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model()));
		boolean isUpdate_dipartimento = true;
		java.util.List<JDBCObject> lstObjects_dipartimento = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().CODICE,false), "?");
		lstObjects_dipartimento.add(new JDBCObject(dipartimento.getCodice(), Dipartimento.model().CODICE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DESCRIZIONE,false), "?");
		lstObjects_dipartimento.add(new JDBCObject(dipartimento.getDescrizione(), Dipartimento.model().DESCRIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().ACCETTAZIONE_AUTOMATICA,false), "?");
		lstObjects_dipartimento.add(new JDBCObject(dipartimento.getAccettazioneAutomatica(), Dipartimento.model().ACCETTAZIONE_AUTOMATICA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().MODALITA_PUSH,false), "?");
		lstObjects_dipartimento.add(new JDBCObject(dipartimento.getModalitaPush(), Dipartimento.model().MODALITA_PUSH.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().LISTA_EMAIL_NOTIFICHE,false), "?");
		lstObjects_dipartimento.add(new JDBCObject(dipartimento.getListaEmailNotifiche(), Dipartimento.model().LISTA_EMAIL_NOTIFICHE.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_ente","?");
		}
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_registro","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_dipartimento.add(new JDBCObject(id_dipartimento_ente, Long.class));
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_dipartimento.add(new JDBCObject(id_dipartimento_registro, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_dipartimento.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_dipartimento) {
			// Update dipartimento
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_dipartimento.toArray(new JDBCObject[]{}));
		}
		// for dipartimento_dipartimentoPropertyValue

		java.util.List<Long> ids_dipartimento_dipartimentoPropertyValue_da_non_eliminare = new java.util.ArrayList<Long>();
		for (Object dipartimento_dipartimentoPropertyValue_object : dipartimento.getDipartimentoPropertyValueList()) {
			DipartimentoPropertyValue dipartimento_dipartimentoPropertyValue = (DipartimentoPropertyValue) dipartimento_dipartimentoPropertyValue_object;

			// Object _dipartimento_dipartimentoPropertyValue_dipartimentoProperty
			Long id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty = null;
			org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty idLogic_dipartimento_dipartimentoPropertyValue_dipartimentoProperty = null;
			idLogic_dipartimento_dipartimentoPropertyValue_dipartimentoProperty = dipartimento_dipartimentoPropertyValue.getIdProperty();
			if(idLogic_dipartimento_dipartimentoPropertyValue_dipartimentoProperty!=null){
				if(idMappingResolutionBehaviour==null ||
					(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
					id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty = ((JDBCDipartimentoPropertyServiceSearch)(this.getServiceManager().getDipartimentoPropertyServiceSearch())).findTableId(idLogic_dipartimento_dipartimentoPropertyValue_dipartimentoProperty, false);
				}
				else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
					id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty = idLogic_dipartimento_dipartimentoPropertyValue_dipartimentoProperty.getId();
					if(id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty==null || id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty<=0){
						throw new Exception("Logic id not contains table id");
					}
				}
			}
			
			
			if(dipartimento_dipartimentoPropertyValue.getId() == null || dipartimento_dipartimentoPropertyValue.getId().longValue() <= 0) {
				try {
					ISQLQueryObject dipartimentoPropValueSQLQueryObject = sqlQueryObject.newSQLQueryObject();
					dipartimentoPropValueSQLQueryObject.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
					dipartimentoPropValueSQLQueryObject.setANDLogicOperator(true);
					dipartimentoPropValueSQLQueryObject.addSelectField("id");
					dipartimentoPropValueSQLQueryObject.addWhereCondition("id_dipartimento_property=?");
					dipartimentoPropValueSQLQueryObject.addWhereCondition("id_dipartimento=?");
					
					// Get dipartimento_dipartimentoPropertyValue
					Long dipPropValue = (Long) jdbcUtilities.executeQuerySingleResult(dipartimentoPropValueSQLQueryObject.createSQLQuery(), jdbcProperties.isShowSql(), Long.class,
							new JDBCObject(id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty,Long.class), 	
						new JDBCObject(dipartimento.getId(),Long.class));
					
					dipartimento_dipartimentoPropertyValue.setId(dipPropValue);
				} catch(NotFoundException e) {}

				
			}
			
			
			if(dipartimento_dipartimentoPropertyValue.getId() == null || dipartimento_dipartimentoPropertyValue.getId().longValue() <= 0) {

				long id = dipartimento.getId();			

				// Object dipartimento_dipartimentoPropertyValue
				ISQLQueryObject sqlQueryObjectInsert_dipartimento_dipartimentoPropertyValue = sqlQueryObjectInsert.newSQLQueryObject();
				sqlQueryObjectInsert_dipartimento_dipartimentoPropertyValue.addInsertTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
				sqlQueryObjectInsert_dipartimento_dipartimentoPropertyValue.addInsertField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE,false),"?");
				sqlQueryObjectInsert_dipartimento_dipartimentoPropertyValue.addInsertField("id_dipartimento_property","?");
				sqlQueryObjectInsert_dipartimento_dipartimentoPropertyValue.addInsertField("id_dipartimento","?");

				// Insert dipartimento_dipartimentoPropertyValue
				org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator_dipartimento_dipartimentoPropertyValue = this.getDipartimentoFetch().getKeyGeneratorObject(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE);
				long id_dipartimento_dipartimentoPropertyValue = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert_dipartimento_dipartimentoPropertyValue, keyGenerator_dipartimento_dipartimentoPropertyValue, jdbcProperties.isShowSql(),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(dipartimento_dipartimentoPropertyValue.getValore(),Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE.getFieldType()),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty,Long.class),
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class)
				);
				dipartimento_dipartimentoPropertyValue.setId(id_dipartimento_dipartimentoPropertyValue);

				ids_dipartimento_dipartimentoPropertyValue_da_non_eliminare.add(dipartimento_dipartimentoPropertyValue.getId());
			} else {

				// Object dipartimento_dipartimentoPropertyValue
				ISQLQueryObject sqlQueryObjectUpdate_dipartimento_dipartimentoPropertyValue = sqlQueryObjectUpdate.newSQLQueryObject();
				sqlQueryObjectUpdate_dipartimento_dipartimentoPropertyValue.setANDLogicOperator(true);
				sqlQueryObjectUpdate_dipartimento_dipartimentoPropertyValue.addUpdateTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
				boolean isUpdate_dipartimento_dipartimentoPropertyValue = true;
				java.util.List<JDBCObject> lstObjects_dipartimento_dipartimentoPropertyValue = new java.util.ArrayList<JDBCObject>();
				sqlQueryObjectUpdate_dipartimento_dipartimentoPropertyValue.addUpdateField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE,false), "?");
				lstObjects_dipartimento_dipartimentoPropertyValue.add(new JDBCObject(dipartimento_dipartimentoPropertyValue.getValore(), Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE.getFieldType()));
				if(setIdMappingResolutionBehaviour){
					sqlQueryObjectUpdate_dipartimento_dipartimentoPropertyValue.addUpdateField("id_dipartimento_property","?");
				}
				if(setIdMappingResolutionBehaviour){
					lstObjects_dipartimento_dipartimentoPropertyValue.add(new JDBCObject(id_dipartimento_dipartimentoPropertyValue_dipartimentoProperty, Long.class));
				}
				sqlQueryObjectUpdate_dipartimento_dipartimentoPropertyValue.addWhereCondition("id=?");
				ids_dipartimento_dipartimentoPropertyValue_da_non_eliminare.add(dipartimento_dipartimentoPropertyValue.getId());
				lstObjects_dipartimento_dipartimentoPropertyValue.add(new JDBCObject(new Long(dipartimento_dipartimentoPropertyValue.getId()),Long.class));

				if(isUpdate_dipartimento_dipartimentoPropertyValue) {
					// Update dipartimento_dipartimentoPropertyValue
					jdbcUtilities.executeUpdate(sqlQueryObjectUpdate_dipartimento_dipartimentoPropertyValue.createSQLUpdate(), jdbcProperties.isShowSql(), 
						lstObjects_dipartimento_dipartimentoPropertyValue.toArray(new JDBCObject[]{}));
				}
			}
		} // fine for dipartimento_dipartimentoPropertyValue

		// elimino tutte le occorrenze di dipartimento_dipartimentoPropertyValue non presenti nell'update

		ISQLQueryObject sqlQueryObjectUpdate_dipartimentoPropertyValue_deleteList = sqlQueryObjectUpdate.newSQLQueryObject();
		sqlQueryObjectUpdate_dipartimentoPropertyValue_deleteList.setANDLogicOperator(true);
		sqlQueryObjectUpdate_dipartimentoPropertyValue_deleteList.addDeleteTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
		java.util.List<JDBCObject> jdbcObjects_dipartimento_dipartimentoPropertyValue_delete = new java.util.ArrayList<JDBCObject>();

		sqlQueryObjectUpdate_dipartimentoPropertyValue_deleteList.addWhereCondition("id_dipartimento=?");
		jdbcObjects_dipartimento_dipartimentoPropertyValue_delete.add(new JDBCObject(dipartimento.getId(), Long.class));

		StringBuffer marks_dipartimento_dipartimentoPropertyValue = new StringBuffer();
		if(ids_dipartimento_dipartimentoPropertyValue_da_non_eliminare.size() > 0) {
			for(Long ids : ids_dipartimento_dipartimentoPropertyValue_da_non_eliminare) {
				if(marks_dipartimento_dipartimentoPropertyValue.length() > 0) {
					marks_dipartimento_dipartimentoPropertyValue.append(",");
				}
				marks_dipartimento_dipartimentoPropertyValue.append("?");
				jdbcObjects_dipartimento_dipartimentoPropertyValue_delete.add(new JDBCObject(ids, Long.class));

			}
			sqlQueryObjectUpdate_dipartimentoPropertyValue_deleteList.addWhereCondition("id NOT IN ("+marks_dipartimento_dipartimentoPropertyValue.toString()+")");
		}

		jdbcUtilities.execute(sqlQueryObjectUpdate_dipartimentoPropertyValue_deleteList.createSQLDelete(), jdbcProperties.isShowSql(), jdbcObjects_dipartimento_dipartimentoPropertyValue_delete.toArray(new JDBCObject[]{}));

	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getDipartimentoFieldConverter().toTable(Dipartimento.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getDipartimentoFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getDipartimentoFieldConverter().toTable(Dipartimento.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getDipartimentoFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getDipartimentoFieldConverter().toTable(Dipartimento.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getDipartimentoFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getDipartimentoFieldConverter().toTable(Dipartimento.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getDipartimentoFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getDipartimentoFieldConverter().toTable(Dipartimento.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getDipartimentoFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getDipartimentoFieldConverter().toTable(Dipartimento.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getDipartimentoFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento oldId, Dipartimento dipartimento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, dipartimento,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, dipartimento,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Dipartimento dipartimento, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, dipartimento,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, dipartimento,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Dipartimento dipartimento) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (dipartimento.getId()!=null) && (dipartimento.getId()>0) ){
			longId = dipartimento.getId();
		}
		else{
			IdDipartimento idDipartimento = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,dipartimento);
			longId = this.findIdDipartimento(jdbcProperties,log,connection,sqlQueryObject,idDipartimento,false);
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
		

		//Recupero oggetto _dipartimento_dipartimentoPropertyValue
		ISQLQueryObject sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue_getToDelete = sqlQueryObjectDelete.newSQLQueryObject();
		sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue_getToDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue_getToDelete.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
		sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue_getToDelete.addWhereCondition("id_dipartimento=?");
		java.util.List<Object> dipartimento_dipartimentoPropertyValue_toDelete_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue_getToDelete.createSQLQuery(), jdbcProperties.isShowSql(), Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE, this.getDipartimentoFetch(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(id),Long.class));

		// for dipartimento_dipartimentoPropertyValue
		for (Object dipartimento_dipartimentoPropertyValue_object : dipartimento_dipartimentoPropertyValue_toDelete_list) {
			DipartimentoPropertyValue dipartimento_dipartimentoPropertyValue = (DipartimentoPropertyValue) dipartimento_dipartimentoPropertyValue_object;

			// Object dipartimento_dipartimentoPropertyValue
			ISQLQueryObject sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue = sqlQueryObjectDelete.newSQLQueryObject();
			sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue.setANDLogicOperator(true);
			sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue.addDeleteTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
			sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue.addWhereCondition("id=?");

			// Delete dipartimento_dipartimentoPropertyValue
			if(dipartimento_dipartimentoPropertyValue != null){
				jdbcUtilities.execute(sqlQueryObjectDelete_dipartimento_dipartimentoPropertyValue.createSQLDelete(), jdbcProperties.isShowSql(), 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(new Long(dipartimento_dipartimentoPropertyValue.getId()),Long.class));
			}
		} // fine for dipartimento_dipartimentoPropertyValue

		// Object dipartimento
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete dipartimento
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento idDipartimento) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdDipartimento(jdbcProperties, log, connection, sqlQueryObject, idDipartimento, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getDipartimentoFieldConverter()));

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
