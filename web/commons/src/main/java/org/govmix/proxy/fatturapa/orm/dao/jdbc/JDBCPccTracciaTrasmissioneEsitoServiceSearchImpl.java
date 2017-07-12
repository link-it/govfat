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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.sql.Connection;

import org.apache.log4j.Logger;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.dao.jdbc.utils.IJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithId;
import org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito;
import org.openspcoop2.generic_project.utils.UtilsTemplate;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.InUse;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UnionExpression;
import org.openspcoop2.generic_project.beans.Union;
import org.openspcoop2.generic_project.beans.FunctionField;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;

import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.PccTracciaTrasmissioneEsitoFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.PccTracciaTrasmissioneEsitoFetch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;

/**     
 * JDBCPccTracciaTrasmissioneEsitoServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccTracciaTrasmissioneEsitoServiceSearchImpl implements IJDBCServiceSearchWithId<PccTracciaTrasmissioneEsito, IdTrasmissioneEsito, JDBCServiceManager> {

	private PccTracciaTrasmissioneEsitoFieldConverter _pccTracciaTrasmissioneEsitoFieldConverter = null;
	public PccTracciaTrasmissioneEsitoFieldConverter getPccTracciaTrasmissioneEsitoFieldConverter() {
		if(this._pccTracciaTrasmissioneEsitoFieldConverter==null){
			this._pccTracciaTrasmissioneEsitoFieldConverter = new PccTracciaTrasmissioneEsitoFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._pccTracciaTrasmissioneEsitoFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getPccTracciaTrasmissioneEsitoFieldConverter();
	}
	
	private PccTracciaTrasmissioneEsitoFetch pccTracciaTrasmissioneEsitoFetch = new PccTracciaTrasmissioneEsitoFetch();
	public PccTracciaTrasmissioneEsitoFetch getPccTracciaTrasmissioneEsitoFetch() {
		return this.pccTracciaTrasmissioneEsitoFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getPccTracciaTrasmissioneEsitoFetch();
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
	public IdTrasmissioneEsito convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito) throws NotImplementedException, ServiceException, Exception{
	
		IdTrasmissioneEsito idPccTracciaTrasmissioneEsito = new IdTrasmissioneEsito();
		idPccTracciaTrasmissioneEsito.setIdTrasmissioneEsito(pccTracciaTrasmissioneEsito.getId());
	
		return idPccTracciaTrasmissioneEsito;
	}
	
	@Override
	public PccTracciaTrasmissioneEsito get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_pccTracciaTrasmissioneEsito = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdPccTracciaTrasmissioneEsito(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_pccTracciaTrasmissioneEsito,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_pccTracciaTrasmissioneEsito = this.findIdPccTracciaTrasmissioneEsito(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_pccTracciaTrasmissioneEsito != null && id_pccTracciaTrasmissioneEsito > 0;
		
	}
	
	@Override
	public List<IdTrasmissioneEsito> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<IdTrasmissioneEsito> list = new ArrayList<IdTrasmissioneEsito>();

		// TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari a create l'ID logico
		// 2. Usare metodo getPccTracciaTrasmissioneEsitoFetch() sul risultato della select per ottenere un oggetto PccTracciaTrasmissioneEsito
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 
		// 3. Usare metodo convertToId per ottenere l'id

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito = this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
			IdTrasmissioneEsito idPccTracciaTrasmissioneEsito = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,pccTracciaTrasmissioneEsito);
        	list.add(idPccTracciaTrasmissioneEsito);
        }

        return list;
		
	}
	
	@Override
	public List<PccTracciaTrasmissioneEsito> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<PccTracciaTrasmissioneEsito> list = new ArrayList<PccTracciaTrasmissioneEsito>();
        
        // TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari
		// 2. Usare metodo getPccTracciaTrasmissioneEsitoFetch() sul risultato della select per ottenere un oggetto PccTracciaTrasmissioneEsito
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	list.add(this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour));
        }

        return list;      
		
	}
	
	@Override
	public PccTracciaTrasmissioneEsito find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
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
												this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model());
		
		sqlQueryObject.addSelectCountField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_pccTracciaTrasmissioneEsito = this.findIdPccTracciaTrasmissioneEsito(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_pccTracciaTrasmissioneEsito);
		
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
												this.getPccTracciaTrasmissioneEsitoFieldConverter(), field);

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
        						expression, this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(),
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
        						this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(), 
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
        						this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(), 
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
			return new JDBCExpression(this.getPccTracciaTrasmissioneEsitoFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getPccTracciaTrasmissioneEsitoFieldConverter());
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
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id, PccTracciaTrasmissioneEsito obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTracciaTrasmissioneEsito obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTracciaTrasmissioneEsito obj, PccTracciaTrasmissioneEsito imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getIdTrasmissione()!=null && 
				imgSaved.getIdTrasmissione()!=null){
			obj.getIdTrasmissione().setId(imgSaved.getIdTrasmissione().getId());
		}

	}
	
	@Override
	public PccTracciaTrasmissioneEsito get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private PccTracciaTrasmissioneEsito _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito = new PccTracciaTrasmissioneEsito();
		

		// Object pccTracciaTrasmissioneEsito
		ISQLQueryObject sqlQueryObjectGet_pccTracciaTrasmissioneEsito = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.setANDLogicOperator(true);
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addFromTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField("id");
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE,true));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DESCRIZIONE_ELABORAZIONE,true));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DATA_FINE_ELABORAZIONE,true));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().GDO,true));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ESITO_TRASMISSIONE,true));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().DETTAGLIO_ERRORE_TRASMISSIONE,true));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ID_EGOV_RICHIESTA,true));
		sqlQueryObjectGet_pccTracciaTrasmissioneEsito.addWhereCondition("id=?");

		// Get pccTracciaTrasmissioneEsito
		pccTracciaTrasmissioneEsito = (PccTracciaTrasmissioneEsito) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_pccTracciaTrasmissioneEsito.createSQLQuery(), jdbcProperties.isShowSql(), PccTracciaTrasmissioneEsito.model(), this.getPccTracciaTrasmissioneEsitoFetch(),
			new JDBCObject(tableId,Long.class));


		if(idMappingResolutionBehaviour==null ||
			(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
		){
			// Object _pccTracciaTrasmissioneEsito_pccTracciaTrasmissione (recupero id)
			ISQLQueryObject sqlQueryObjectGet_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione_readFkId = sqlQueryObjectGet.newSQLQueryObject();
			sqlQueryObjectGet_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione_readFkId.addFromTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito.model()));
			sqlQueryObjectGet_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione_readFkId.addSelectField("id_pcc_traccia_trasmissione");
			sqlQueryObjectGet_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione_readFkId.addWhereCondition("id=?");
			sqlQueryObjectGet_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione_readFkId.setANDLogicOperator(true);
			Long idFK_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
					new JDBCObject(pccTracciaTrasmissioneEsito.getId(),Long.class));
			
			org.govmix.proxy.fatturapa.orm.IdTrasmissione id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = null;
			if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
				id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = ((JDBCPccTracciaTrasmissioneServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneServiceSearch())).findId(idFK_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione, false);
			}else{
				id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione = new org.govmix.proxy.fatturapa.orm.IdTrasmissione();
			}
			id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione.setId(idFK_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione);
			pccTracciaTrasmissioneEsito.setIdTrasmissione(id_pccTracciaTrasmissioneEsito_pccTracciaTrasmissione);
		}

        return pccTracciaTrasmissioneEsito;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsPccTracciaTrasmissioneEsito = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()));
		sqlQueryObject.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toColumn(PccTracciaTrasmissioneEsito.model().ESITO_ELABORAZIONE,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists pccTracciaTrasmissioneEsito
		existsPccTracciaTrasmissioneEsito = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsPccTracciaTrasmissioneEsito;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{
	
		if(expression.inUseModel(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE,false)){
			String tableName1 = this.getPccTracciaTrasmissioneEsitoFieldConverter().toAliasTable(PccTracciaTrasmissioneEsito.model());
			String tableName2 = this.getPccTracciaTrasmissioneEsitoFieldConverter().toAliasTable(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE);
			sqlQueryObject.addWhereCondition(tableName1+".id_pcc_traccia_trasmissione="+tableName2+".id");
		}
        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdPccTracciaTrasmissioneEsito(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		PccTracciaTrasmissioneEsitoFieldConverter converter = this.getPccTracciaTrasmissioneEsitoFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// PccTracciaTrasmissioneEsito.model()
		mapTableToPKColumn.put(converter.toTable(PccTracciaTrasmissioneEsito.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(PccTracciaTrasmissioneEsito.model()))
			));

		// PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE
		mapTableToPKColumn.put(converter.toTable(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE))
			));

        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getPccTracciaTrasmissioneEsitoFieldConverter(), PccTracciaTrasmissioneEsito.model(), objectIdClass, listaQuery);
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
	public IdTrasmissioneEsito findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _pccTracciaTrasmissioneEsito
		sqlQueryObjectGet.addFromTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _pccTracciaTrasmissioneEsito
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_pccTracciaTrasmissioneEsito = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_pccTracciaTrasmissioneEsito = new ArrayList<Class<?>>();
		listaFieldIdReturnType_pccTracciaTrasmissioneEsito.add(Long.class);

		org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito id_pccTracciaTrasmissioneEsito = null;
		List<Object> listaFieldId_pccTracciaTrasmissioneEsito = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_pccTracciaTrasmissioneEsito, searchParams_pccTracciaTrasmissioneEsito);
		if(listaFieldId_pccTracciaTrasmissioneEsito==null || listaFieldId_pccTracciaTrasmissioneEsito.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			// set _pccTracciaTrasmissioneEsito
			id_pccTracciaTrasmissioneEsito = new org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito();
			id_pccTracciaTrasmissioneEsito.setIdTrasmissioneEsito((Long)listaFieldId_pccTracciaTrasmissioneEsito.get(0));
		}
		
		return id_pccTracciaTrasmissioneEsito;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdPccTracciaTrasmissioneEsito(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	protected Long findIdPccTracciaTrasmissioneEsito(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTrasmissioneEsito id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _pccTracciaTrasmissioneEsito
		sqlQueryObjectGet.addFromTable(this.getPccTracciaTrasmissioneEsitoFieldConverter().toTable(PccTracciaTrasmissioneEsito.model()));
		sqlQueryObjectGet.addSelectField("id");
		// Devono essere mappati nella where condition i metodi dell'oggetto id.getXXX
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _pccTracciaTrasmissioneEsito
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_pccTracciaTrasmissioneEsito = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdTrasmissioneEsito(),Long.class),
		};
		Long id_pccTracciaTrasmissioneEsito = null;
		try{
			id_pccTracciaTrasmissioneEsito = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_pccTracciaTrasmissioneEsito);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_pccTracciaTrasmissioneEsito==null || id_pccTracciaTrasmissioneEsito<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_pccTracciaTrasmissioneEsito;
	}
}
