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
import org.govmix.proxy.fatturapa.orm.IdContabilizzazione;
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

import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCPccContabilizzazioneServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccContabilizzazioneServiceImpl extends JDBCPccContabilizzazioneServiceSearchImpl
	implements IJDBCServiceCRUDWithId<PccContabilizzazione, IdContabilizzazione, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccContabilizzazione pccContabilizzazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _fatturaElettronica
		Long id_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_fatturaElettronica = null;
		idLogic_fatturaElettronica = pccContabilizzazione.getIdFattura();
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


		// Object pccContabilizzazione
		sqlQueryObjectInsert.addInsertTable(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()));
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().IMPORTO_MOVIMENTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().NATURA_SPESA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CAPITOLI_SPESA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().STATO_DEBITO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CAUSALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DESCRIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ESTREMI_IMPEGNO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CODICE_CIG,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CODICE_CUP,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ID_IMPORTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().SISTEMA_RICHIEDENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().UTENTE_RICHIEDENTE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DATA_RICHIESTA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DATA_QUERY,false),"?");
		sqlQueryObjectInsert.addInsertField("id_fattura_elettronica","?");

		// Insert pccContabilizzazione
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getPccContabilizzazioneFetch().getKeyGeneratorObject(PccContabilizzazione.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getImportoMovimento(),PccContabilizzazione.model().IMPORTO_MOVIMENTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getNaturaSpesa(),PccContabilizzazione.model().NATURA_SPESA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getCapitoliSpesa(),PccContabilizzazione.model().CAPITOLI_SPESA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getStatoDebito(),PccContabilizzazione.model().STATO_DEBITO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getCausale(),PccContabilizzazione.model().CAUSALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getDescrizione(),PccContabilizzazione.model().DESCRIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getEstremiImpegno(),PccContabilizzazione.model().ESTREMI_IMPEGNO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getCodiceCig(),PccContabilizzazione.model().CODICE_CIG.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getCodiceCup(),PccContabilizzazione.model().CODICE_CUP.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getIdImporto(),PccContabilizzazione.model().ID_IMPORTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getSistemaRichiedente(),PccContabilizzazione.model().SISTEMA_RICHIEDENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getUtenteRichiedente(),PccContabilizzazione.model().UTENTE_RICHIEDENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getDataRichiesta(),PccContabilizzazione.model().DATA_RICHIESTA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(pccContabilizzazione.getDataQuery(),PccContabilizzazione.model().DATA_QUERY.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_fatturaElettronica,Long.class)
		);
		pccContabilizzazione.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione oldId, PccContabilizzazione pccContabilizzazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdPccContabilizzazione(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = pccContabilizzazione.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: pccContabilizzazione.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			pccContabilizzazione.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccContabilizzazione, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccContabilizzazione pccContabilizzazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
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
			

		// Object _pccContabilizzazione_fatturaElettronica
		Long id_pccContabilizzazione_fatturaElettronica = null;
		org.govmix.proxy.fatturapa.orm.IdFattura idLogic_pccContabilizzazione_fatturaElettronica = null;
		idLogic_pccContabilizzazione_fatturaElettronica = pccContabilizzazione.getIdFattura();
		if(idLogic_pccContabilizzazione_fatturaElettronica!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccContabilizzazione_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findTableId(idLogic_pccContabilizzazione_fatturaElettronica, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccContabilizzazione_fatturaElettronica = idLogic_pccContabilizzazione_fatturaElettronica.getId();
				if(id_pccContabilizzazione_fatturaElettronica==null || id_pccContabilizzazione_fatturaElettronica<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object pccContabilizzazione
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()));
		boolean isUpdate_pccContabilizzazione = true;
		java.util.List<JDBCObject> lstObjects_pccContabilizzazione = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().IMPORTO_MOVIMENTO,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getImportoMovimento(), PccContabilizzazione.model().IMPORTO_MOVIMENTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().NATURA_SPESA,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getNaturaSpesa(), PccContabilizzazione.model().NATURA_SPESA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CAPITOLI_SPESA,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getCapitoliSpesa(), PccContabilizzazione.model().CAPITOLI_SPESA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().STATO_DEBITO,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getStatoDebito(), PccContabilizzazione.model().STATO_DEBITO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CAUSALE,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getCausale(), PccContabilizzazione.model().CAUSALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DESCRIZIONE,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getDescrizione(), PccContabilizzazione.model().DESCRIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ESTREMI_IMPEGNO,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getEstremiImpegno(), PccContabilizzazione.model().ESTREMI_IMPEGNO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CODICE_CIG,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getCodiceCig(), PccContabilizzazione.model().CODICE_CIG.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CODICE_CUP,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getCodiceCup(), PccContabilizzazione.model().CODICE_CUP.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ID_IMPORTO,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getIdImporto(), PccContabilizzazione.model().ID_IMPORTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().SISTEMA_RICHIEDENTE,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getSistemaRichiedente(), PccContabilizzazione.model().SISTEMA_RICHIEDENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().UTENTE_RICHIEDENTE,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getUtenteRichiedente(), PccContabilizzazione.model().UTENTE_RICHIEDENTE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DATA_RICHIESTA,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getDataRichiesta(), PccContabilizzazione.model().DATA_RICHIESTA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DATA_QUERY,false), "?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(pccContabilizzazione.getDataQuery(), PccContabilizzazione.model().DATA_QUERY.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_fattura_elettronica","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_pccContabilizzazione.add(new JDBCObject(id_pccContabilizzazione_fatturaElettronica, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_pccContabilizzazione.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_pccContabilizzazione) {
			// Update pccContabilizzazione
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_pccContabilizzazione.toArray(new JDBCObject[]{}));
		}

	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccContabilizzazioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccContabilizzazioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getPccContabilizzazioneFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccContabilizzazioneFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccContabilizzazioneFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getPccContabilizzazioneFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione oldId, PccContabilizzazione pccContabilizzazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, pccContabilizzazione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccContabilizzazione,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccContabilizzazione pccContabilizzazione, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, pccContabilizzazione,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, pccContabilizzazione,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccContabilizzazione pccContabilizzazione) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (pccContabilizzazione.getId()!=null) && (pccContabilizzazione.getId()>0) ){
			longId = pccContabilizzazione.getId();
		}
		else{
			IdContabilizzazione idPccContabilizzazione = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccContabilizzazione);
			longId = this.findIdPccContabilizzazione(jdbcProperties,log,connection,sqlQueryObject,idPccContabilizzazione,false);
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
		

		// Object pccContabilizzazione
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete pccContabilizzazione
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione idPccContabilizzazione) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdPccContabilizzazione(jdbcProperties, log, connection, sqlQueryObject, idPccContabilizzazione, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getPccContabilizzazioneFieldConverter()));

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
