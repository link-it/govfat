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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdContabilizzazione;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.PccContabilizzazioneFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.PccContabilizzazioneFetch;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.FunctionField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.InUse;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.Union;
import org.openspcoop2.generic_project.beans.UnionExpression;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithId;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.IJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.utils.UtilsTemplate;
import org.openspcoop2.utils.sql.ISQLQueryObject;

/**     
 * JDBCPccContabilizzazioneServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccContabilizzazioneServiceSearchImpl implements IJDBCServiceSearchWithId<PccContabilizzazione, IdContabilizzazione, JDBCServiceManager> {

	private PccContabilizzazioneFieldConverter _pccContabilizzazioneFieldConverter = null;
	public PccContabilizzazioneFieldConverter getPccContabilizzazioneFieldConverter() {
		if(this._pccContabilizzazioneFieldConverter==null){
			this._pccContabilizzazioneFieldConverter = new PccContabilizzazioneFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._pccContabilizzazioneFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getPccContabilizzazioneFieldConverter();
	}
	
	private PccContabilizzazioneFetch pccContabilizzazioneFetch = new PccContabilizzazioneFetch();
	public PccContabilizzazioneFetch getPccContabilizzazioneFetch() {
		return this.pccContabilizzazioneFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getPccContabilizzazioneFetch();
	}
	
	
	private JDBCServiceManager jdbcServiceManager = null;

	@Override
	public void setServiceManager(JDBCServiceManager serviceManager) throws ServiceException{
		this.jdbcServiceManager = serviceManager;
	}
	
	@Override
	public JDBCServiceManager getServiceManager() throws ServiceException{
		return this.jdbcServiceManager;
	}
	

	@Override
	public IdContabilizzazione convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccContabilizzazione pccContabilizzazione) throws NotImplementedException, ServiceException, Exception{
	
		IdContabilizzazione idPccContabilizzazione = new IdContabilizzazione();
		
		idPccContabilizzazione.setIdImporto(pccContabilizzazione.getIdImporto());
		idPccContabilizzazione.setSistemaRichiedente(pccContabilizzazione.getSistemaRichiedente());
		idPccContabilizzazione.setIdFattura(pccContabilizzazione.getIdFattura());
	
		return idPccContabilizzazione;
	}
	
	@Override
	public PccContabilizzazione get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_pccContabilizzazione = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdPccContabilizzazione(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_pccContabilizzazione,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_pccContabilizzazione = this.findIdPccContabilizzazione(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_pccContabilizzazione != null && id_pccContabilizzazione > 0;
		
	}
	
	@Override
	public List<IdContabilizzazione> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<IdContabilizzazione> list = new ArrayList<IdContabilizzazione>();

		// TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari a create l'ID logico
		// 2. Usare metodo getPccContabilizzazioneFetch() sul risultato della select per ottenere un oggetto PccContabilizzazione
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 
		// 3. Usare metodo convertToId per ottenere l'id

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	PccContabilizzazione pccContabilizzazione = this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
			IdContabilizzazione idPccContabilizzazione = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccContabilizzazione);
        	list.add(idPccContabilizzazione);
        }

        return list;
		
	}
	
	@Override
	public List<PccContabilizzazione> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<PccContabilizzazione> list = new ArrayList<PccContabilizzazione>();
        
        // TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari
		// 2. Usare metodo getPccContabilizzazioneFetch() sul risultato della select per ottenere un oggetto PccContabilizzazione
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	list.add(this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour));
        }

        return list;      
		
	}
	
	@Override
	public PccContabilizzazione find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
		throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {

        long id = this.findTableId(jdbcProperties, log, connection, sqlQueryObject, expression);
        if(id>0){
        	return this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
        }else{
        	throw new NotFoundException("Entry with id["+id+"] not found");
        }
		
	}
	
	@Override
	public NonNegativeNumber count(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareCount(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model());
		
		sqlQueryObject.addSelectCountField(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_pccContabilizzazione = this.findIdPccContabilizzazione(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_pccContabilizzazione);
		
	}

	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		List<Map<String,Object>> map = 
			this.select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, distinct, new IField[]{field});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectSingleObject(map);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,field);
		try{
		
			ISQLQueryObject sqlQueryObjectDistinct = 
						org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(distinct,sqlQueryObject, paginatedExpression, log,
												this.getPccContabilizzazioneFieldConverter(), field);

			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, sqlQueryObjectDistinct);
			
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,field);
		}
	}

	@Override
	public Object aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		Map<String,Object> map = 
			this.aggregate(jdbcProperties, log, connection, sqlQueryObject, expression, new FunctionField[]{functionField});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectAggregateObject(map,functionField);
	}
	
	@Override
	public Map<String,Object> aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {													
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			List<Map<String,Object>> list = _select(jdbcProperties, log, connection, sqlQueryObject, expression);
			return list.get(0);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(expression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, expression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}
	

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(paginatedExpression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,functionField);
		}
	}
	
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return _select(jdbcProperties, log, connection, sqlQueryObject, expression, null);
	}
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression, ISQLQueryObject sqlQueryObjectDistinct) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		List<Object> listaQuery = new ArrayList<Object>();
		List<JDBCObject> listaParams = new ArrayList<JDBCObject>();
		List<Object> returnField = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSelect(jdbcProperties, log, connection, sqlQueryObject, 
        						expression, this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(),
        								listaQuery,listaParams,returnField);
		if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}
	
	@Override
	public List<Map<String,Object>> union(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnion(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}								
	}
	
	@Override
	public NonNegativeNumber unionCount(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnionCount(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(number!=null && number.longValue()>=0){
			return number;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}



	// -- ConstructorExpression	

	@Override
	public JDBCExpression newExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(this.getPccContabilizzazioneFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getPccContabilizzazioneFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public JDBCExpression toExpression(JDBCPaginatedExpression paginatedExpression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(paginatedExpression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public JDBCPaginatedExpression toPaginatedExpression(JDBCExpression expression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(expression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
	
	// -- DB

	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id, PccContabilizzazione obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccContabilizzazione obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccContabilizzazione obj, PccContabilizzazione imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getIdFattura()!=null && 
				imgSaved.getIdFattura()!=null){
			obj.getIdFattura().setId(imgSaved.getIdFattura().getId());
		}

	}
	
	@Override
	public PccContabilizzazione get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private PccContabilizzazione _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		PccContabilizzazione pccContabilizzazione = new PccContabilizzazione();
		

		// Object pccContabilizzazione
		ISQLQueryObject sqlQueryObjectGet_pccContabilizzazione = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_pccContabilizzazione.setANDLogicOperator(true);
		sqlQueryObjectGet_pccContabilizzazione.addFromTable(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField("id");
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().IMPORTO_MOVIMENTO,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().NATURA_SPESA,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CAPITOLI_SPESA,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().STATO_DEBITO,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CAUSALE,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DESCRIZIONE,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ESTREMI_IMPEGNO,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CODICE_CIG,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().CODICE_CUP,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ID_IMPORTO,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().SISTEMA_RICHIEDENTE,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().UTENTE_RICHIEDENTE,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DATA_RICHIESTA,true));
		sqlQueryObjectGet_pccContabilizzazione.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().DATA_QUERY,true));
		sqlQueryObjectGet_pccContabilizzazione.addWhereCondition("id=?");

		// Get pccContabilizzazione
		pccContabilizzazione = (PccContabilizzazione) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_pccContabilizzazione.createSQLQuery(), jdbcProperties.isShowSql(), PccContabilizzazione.model(), this.getPccContabilizzazioneFetch(),
			new JDBCObject(tableId,Long.class));


		if(idMappingResolutionBehaviour==null ||
			(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
		){
			// Object _pccContabilizzazione_fatturaElettronica (recupero id)
			ISQLQueryObject sqlQueryObjectGet_pccContabilizzazione_fatturaElettronica_readFkId = sqlQueryObjectGet.newSQLQueryObject();
			sqlQueryObjectGet_pccContabilizzazione_fatturaElettronica_readFkId.addFromTable(this.getPccContabilizzazioneFieldConverter().toTable(org.govmix.proxy.fatturapa.orm.PccContabilizzazione.model()));
			sqlQueryObjectGet_pccContabilizzazione_fatturaElettronica_readFkId.addSelectField("id_fattura_elettronica");
			sqlQueryObjectGet_pccContabilizzazione_fatturaElettronica_readFkId.addWhereCondition("id=?");
			sqlQueryObjectGet_pccContabilizzazione_fatturaElettronica_readFkId.setANDLogicOperator(true);
			Long idFK_pccContabilizzazione_fatturaElettronica = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_pccContabilizzazione_fatturaElettronica_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
					new JDBCObject(pccContabilizzazione.getId(),Long.class));
			
			org.govmix.proxy.fatturapa.orm.IdFattura id_pccContabilizzazione_fatturaElettronica = null;
			if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
				id_pccContabilizzazione_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findId(idFK_pccContabilizzazione_fatturaElettronica, false);
			}else{
				id_pccContabilizzazione_fatturaElettronica = new org.govmix.proxy.fatturapa.orm.IdFattura();
			}
			id_pccContabilizzazione_fatturaElettronica.setId(idFK_pccContabilizzazione_fatturaElettronica);
			pccContabilizzazione.setIdFattura(id_pccContabilizzazione_fatturaElettronica);
		}
		
        return pccContabilizzazione;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsPccContabilizzazione = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()));
		sqlQueryObject.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().IMPORTO_MOVIMENTO,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists pccContabilizzazione
		existsPccContabilizzazione = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsPccContabilizzazione;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{

		if(expression.inUseModel(PccContabilizzazione.model().ID_FATTURA,false)){
			String tableName1 = this.getPccContabilizzazioneFieldConverter().toAliasTable(PccContabilizzazione.model());
			String tableName2 = this.getPccContabilizzazioneFieldConverter().toAliasTable(PccContabilizzazione.model().ID_FATTURA);
			sqlQueryObject.addWhereCondition(tableName1+".id_fattura_elettronica="+tableName2+".id");
		}
        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdPccContabilizzazione(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		PccContabilizzazioneFieldConverter converter = this.getPccContabilizzazioneFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		// PccContabilizzazione.model()
		mapTableToPKColumn.put(converter.toTable(PccContabilizzazione.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(PccContabilizzazione.model()))
			));

		// PccContabilizzazione.model().ID_FATTURA
		mapTableToPKColumn.put(converter.toTable(PccContabilizzazione.model().ID_FATTURA),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(PccContabilizzazione.model().ID_FATTURA))
			));


        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getPccContabilizzazioneFieldConverter(), PccContabilizzazione.model(), objectIdClass, listaQuery);
		if(res!=null && (((Long) res).longValue()>0) ){
			return ((Long) res).longValue();
		}
		else{
			throw new NotFoundException("Not Found");
		}
		
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {
		return this._inUse(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}

	private InUse _inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {

		InUse inUse = new InUse();
		inUse.setInUse(false);
		
		/* 
		 * TODO: implement code that checks whether the object identified by the id parameter is used by other objects
		*/
		
		// Delete this line when you have implemented the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have implemented the method

        return inUse;

	}
	
	@Override
	public IdContabilizzazione findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _pccContabilizzazione
		sqlQueryObjectGet.addFromTable(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()));
		sqlQueryObjectGet.addSelectField("id_fattura_elettronica");
		sqlQueryObjectGet.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ID_IMPORTO, false));
		sqlQueryObjectGet.addSelectField(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().SISTEMA_RICHIEDENTE, false));
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _pccContabilizzazione
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_pccContabilizzazione = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_pccContabilizzazione = new ArrayList<Class<?>>();
		listaFieldIdReturnType_pccContabilizzazione.add(Long.class);
		listaFieldIdReturnType_pccContabilizzazione.add(PccContabilizzazione.model().ID_IMPORTO.getFieldType());
		listaFieldIdReturnType_pccContabilizzazione.add(PccContabilizzazione.model().SISTEMA_RICHIEDENTE.getFieldType());

		org.govmix.proxy.fatturapa.orm.IdContabilizzazione id_pccContabilizzazione = null;
		List<Object> listaFieldId_pccContabilizzazione = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_pccContabilizzazione, searchParams_pccContabilizzazione);
		if(listaFieldId_pccContabilizzazione==null || listaFieldId_pccContabilizzazione.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			id_pccContabilizzazione = new org.govmix.proxy.fatturapa.orm.IdContabilizzazione();
			id_pccContabilizzazione.setIdFattura(((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findId((Long)listaFieldId_pccContabilizzazione.get(0), throwNotFound));
			id_pccContabilizzazione.setIdImporto((String)listaFieldId_pccContabilizzazione.get(1));
			id_pccContabilizzazione.setSistemaRichiedente((String)listaFieldId_pccContabilizzazione.get(2));
		}
		
		return id_pccContabilizzazione;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdPccContabilizzazione(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	protected Long findIdPccContabilizzazione(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdContabilizzazione id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _pccContabilizzazione
		sqlQueryObjectGet.addFromTable(this.getPccContabilizzazioneFieldConverter().toTable(PccContabilizzazione.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition("id_fattura_elettronica=?");
		sqlQueryObjectGet.addWhereCondition(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().ID_IMPORTO, false) + "=?");
		sqlQueryObjectGet.addWhereCondition(this.getPccContabilizzazioneFieldConverter().toColumn(PccContabilizzazione.model().SISTEMA_RICHIEDENTE, false) + "=?");

		
		Long idFattura = ((IDBFatturaElettronicaServiceSearch)this.getServiceManager().getFatturaElettronicaServiceSearch()).findTableId(id.getIdFattura(), throwNotFound);
		// Recupero _pccContabilizzazione
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_pccContabilizzazione = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(idFattura, Long.class),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdImporto(), PccContabilizzazione.model().ID_IMPORTO.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getSistemaRichiedente(), PccContabilizzazione.model().SISTEMA_RICHIEDENTE.getFieldType()),
		};
		Long id_pccContabilizzazione = null;
		try{
			id_pccContabilizzazione = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_pccContabilizzazione);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_pccContabilizzazione==null || id_pccContabilizzazione<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_pccContabilizzazione;
	}
}
