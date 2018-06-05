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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.sql.Connection;

import org.apache.log4j.Logger;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.dao.jdbc.utils.IJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithoutId;
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
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.NotificaEsitoCommittenteFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.NotificaEsitoCommittenteFetch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;

/**     
 * JDBCNotificaEsitoCommittenteServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCNotificaEsitoCommittenteServiceSearchImpl implements IJDBCServiceSearchWithoutId<NotificaEsitoCommittente, JDBCServiceManager> {

	private NotificaEsitoCommittenteFieldConverter _notificaEsitoCommittenteFieldConverter = null;
	public NotificaEsitoCommittenteFieldConverter getNotificaEsitoCommittenteFieldConverter() {
		if(this._notificaEsitoCommittenteFieldConverter==null){
			this._notificaEsitoCommittenteFieldConverter = new NotificaEsitoCommittenteFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._notificaEsitoCommittenteFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getNotificaEsitoCommittenteFieldConverter();
	}
	
	private NotificaEsitoCommittenteFetch notificaEsitoCommittenteFetch = new NotificaEsitoCommittenteFetch();
	public NotificaEsitoCommittenteFetch getNotificaEsitoCommittenteFetch() {
		return this.notificaEsitoCommittenteFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getNotificaEsitoCommittenteFetch();
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
	public List<NotificaEsitoCommittente> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<NotificaEsitoCommittente> list = new ArrayList<NotificaEsitoCommittente>();
        
        // TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari
		// 2. Usare metodo getNotificaEsitoCommittenteFetch() sul risultato della select per ottenere un oggetto NotificaEsitoCommittente
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	list.add(this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour));
        }

        return list;      
		
	}
	
	@Override
	public NotificaEsitoCommittente find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
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
												this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model());
		
		sqlQueryObject.addSelectCountField(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(),listaQuery);
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
												this.getNotificaEsitoCommittenteFieldConverter(), field);

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
        						expression, this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(),
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
        						this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(), 
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
        						this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(), 
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
			return new JDBCExpression(this.getNotificaEsitoCommittenteFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getNotificaEsitoCommittenteFieldConverter());
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
	public NotificaEsitoCommittente get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private NotificaEsitoCommittente _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		NotificaEsitoCommittente notificaEsitoCommittente = new NotificaEsitoCommittente();
		

		// Object notificaEsitoCommittente
		ISQLQueryObject sqlQueryObjectGet_notificaEsitoCommittente = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_notificaEsitoCommittente.setANDLogicOperator(true);
		sqlQueryObjectGet_notificaEsitoCommittente.addFromTable(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField("id");
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().NUMERO_FATTURA,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().ANNO,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().POSIZIONE,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().ESITO,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DESCRIZIONE,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().MESSAGE_ID_COMMITTENTE,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().NOME_FILE,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().MODALITA_BATCH,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_INVIO_ENTE,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_INVIO_SDI,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().STATO_CONSEGNA_SDI,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_ULTIMA_CONSEGNA_SDI,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().DATA_PROSSIMA_CONSEGNA_SDI,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().TENTATIVI_CONSEGNA_SDI,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO_NOTE,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().SCARTO_XML,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().XML,true));
		sqlQueryObjectGet_notificaEsitoCommittente.addWhereCondition("id=?");

		// Get notificaEsitoCommittente
		notificaEsitoCommittente = (NotificaEsitoCommittente) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_notificaEsitoCommittente.createSQLQuery(), jdbcProperties.isShowSql(), NotificaEsitoCommittente.model(), this.getNotificaEsitoCommittenteFetch(),
			new JDBCObject(tableId,Long.class));


		if(idMappingResolutionBehaviour==null ||
			(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
		){
			// Object _notificaEsitoCommittente_fatturaElettronica (recupero id)
			ISQLQueryObject sqlQueryObjectGet_notificaEsitoCommittente_fatturaElettronica_readFkId = sqlQueryObjectGet.newSQLQueryObject();
			sqlQueryObjectGet_notificaEsitoCommittente_fatturaElettronica_readFkId.addFromTable(this.getNotificaEsitoCommittenteFieldConverter().toTable(org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente.model()));
			sqlQueryObjectGet_notificaEsitoCommittente_fatturaElettronica_readFkId.addSelectField("id_fattura_elettronica");
			sqlQueryObjectGet_notificaEsitoCommittente_fatturaElettronica_readFkId.addWhereCondition("id=?");
			sqlQueryObjectGet_notificaEsitoCommittente_fatturaElettronica_readFkId.setANDLogicOperator(true);
			Long idFK_notificaEsitoCommittente_fatturaElettronica = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_notificaEsitoCommittente_fatturaElettronica_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
					new JDBCObject(notificaEsitoCommittente.getId(),Long.class));
			
			org.govmix.proxy.fatturapa.orm.IdFattura id_notificaEsitoCommittente_fatturaElettronica = null;
			if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
				id_notificaEsitoCommittente_fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)(this.getServiceManager().getFatturaElettronicaServiceSearch())).findId(idFK_notificaEsitoCommittente_fatturaElettronica, false);
			}else{
				id_notificaEsitoCommittente_fatturaElettronica = org.govmix.proxy.fatturapa.orm.IdFattura.newIdFattura();
			}
			id_notificaEsitoCommittente_fatturaElettronica.setId(idFK_notificaEsitoCommittente_fatturaElettronica);
			notificaEsitoCommittente.setIdFattura(id_notificaEsitoCommittente_fatturaElettronica);
		}

		if(idMappingResolutionBehaviour==null ||
			(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
		){
			// Object _notificaEsitoCommittente_utente (recupero id)
			ISQLQueryObject sqlQueryObjectGet_notificaEsitoCommittente_utente_readFkId = sqlQueryObjectGet.newSQLQueryObject();
			sqlQueryObjectGet_notificaEsitoCommittente_utente_readFkId.addFromTable(this.getNotificaEsitoCommittenteFieldConverter().toTable(org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente.model()));
			sqlQueryObjectGet_notificaEsitoCommittente_utente_readFkId.addSelectField("id_utente");
			sqlQueryObjectGet_notificaEsitoCommittente_utente_readFkId.addWhereCondition("id=?");
			sqlQueryObjectGet_notificaEsitoCommittente_utente_readFkId.setANDLogicOperator(true);
			Long idFK_notificaEsitoCommittente_utente = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_notificaEsitoCommittente_utente_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
					new JDBCObject(notificaEsitoCommittente.getId(),Long.class));
			
			org.govmix.proxy.fatturapa.orm.IdUtente id_notificaEsitoCommittente_utente = null;
			if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
				id_notificaEsitoCommittente_utente = ((JDBCUtenteServiceSearch)(this.getServiceManager().getUtenteServiceSearch())).findId(idFK_notificaEsitoCommittente_utente, false);
			}else{
				id_notificaEsitoCommittente_utente = new org.govmix.proxy.fatturapa.orm.IdUtente();
			}
			id_notificaEsitoCommittente_utente.setId(idFK_notificaEsitoCommittente_utente);
			notificaEsitoCommittente.setUtente(id_notificaEsitoCommittente_utente);
		}

        return notificaEsitoCommittente;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsNotificaEsitoCommittente = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model()));
		sqlQueryObject.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toColumn(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists notificaEsitoCommittente
		existsNotificaEsitoCommittente = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsNotificaEsitoCommittente;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{
		if(expression.inUseModel(NotificaEsitoCommittente.model().ID_FATTURA,false)){
			String tableName1 = this.getNotificaEsitoCommittenteFieldConverter().toAliasTable(NotificaEsitoCommittente.model());
			String tableName2 = this.getNotificaEsitoCommittenteFieldConverter().toAliasTable(NotificaEsitoCommittente.model().ID_FATTURA);
			sqlQueryObject.addWhereCondition(tableName1+".id_fattura_elettronica="+tableName2+".id");
		}

		if(expression.inUseModel(NotificaEsitoCommittente.model().UTENTE,false)){
			String tableName1 = this.getNotificaEsitoCommittenteFieldConverter().toAliasTable(NotificaEsitoCommittente.model());
			String tableName2 = this.getNotificaEsitoCommittenteFieldConverter().toAliasTable(NotificaEsitoCommittente.model().UTENTE);
			sqlQueryObject.addWhereCondition(tableName1+".id_utente="+tableName2+".id");
		}
        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, NotificaEsitoCommittente notificaEsitoCommittente) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		rootTableIdValues.add(notificaEsitoCommittente.getId());
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		NotificaEsitoCommittenteFieldConverter converter = this.getNotificaEsitoCommittenteFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// NotificaEsitoCommittente.model()
		mapTableToPKColumn.put(converter.toTable(NotificaEsitoCommittente.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(NotificaEsitoCommittente.model()))
			));

		// NotificaEsitoCommittente.model().ID_FATTURA
		mapTableToPKColumn.put(converter.toTable(NotificaEsitoCommittente.model().ID_FATTURA),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(NotificaEsitoCommittente.model().ID_FATTURA))
			));

		// NotificaEsitoCommittente.model().UTENTE
		mapTableToPKColumn.put(converter.toTable(NotificaEsitoCommittente.model().UTENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(NotificaEsitoCommittente.model().UTENTE))
			));

        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getNotificaEsitoCommittenteFieldConverter().toTable(NotificaEsitoCommittente.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getNotificaEsitoCommittenteFieldConverter(), NotificaEsitoCommittente.model(), objectIdClass, listaQuery);
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
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
}
