/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.dao.jdbc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.DipartimentoFieldConverter;
import org.govmix.proxy.fatturapa.dao.jdbc.fetch.DipartimentoFetch;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.FunctionField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.InUse;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.Union;
import org.openspcoop2.generic_project.beans.UnionExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.utils.UtilsTemplate;
import org.openspcoop2.utils.sql.ISQLQueryObject;

/**     
 * JDBCDipartimentoServiceSearchImpl
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCDipartimentoServiceSearchImpl implements IJDBCDipartimentoServiceSearch {

	private DipartimentoFieldConverter _dipartimentoFieldConverter = null;
	public DipartimentoFieldConverter getDipartimentoFieldConverter() {
		if(this._dipartimentoFieldConverter==null){
			this._dipartimentoFieldConverter = new DipartimentoFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._dipartimentoFieldConverter;
	}
	
	private DipartimentoFetch dipartimentoFetch = new DipartimentoFetch();
	public DipartimentoFetch getDipartimentoFetch() {
		return this.dipartimentoFetch;
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
	public IdDipartimento convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Dipartimento dipartimento) throws NotImplementedException, ServiceException, Exception{
	
		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(dipartimento.getCodice());
	
		return idDipartimento;
	}
	
	@Override
	public Dipartimento get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_dipartimento = this.findIdDipartimento(jdbcProperties, log, connection, sqlQueryObject, id, true);
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_dipartimento);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_dipartimento = this.findIdDipartimento(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_dipartimento != null && id_dipartimento > 0;
		
	}
	
	@Override
	public List<IdDipartimento> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression) throws NotImplementedException, ServiceException,Exception {

		List<IdDipartimento> list = new ArrayList<IdDipartimento>();
        
		try {
			List<IField> fields = new ArrayList<IField>();
	        
			fields.add(Dipartimento.model().CODICE);
	        
			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));
			
			for(Map<String, Object> map: returnMap) {
				list.add(this.convertToId(jdbcProperties, log, connection, sqlQueryObject, (Dipartimento)this.getDipartimentoFetch().fetch(jdbcProperties.getDatabase(), Dipartimento.model(), map)));
			}
		} catch (NotFoundException e){}

        return list;
		
	}
	
	@Override
	public List<Dipartimento> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression) throws NotImplementedException, ServiceException,Exception {

		return this.findAll(jdbcProperties, log, connection, sqlQueryObject, expression, true);
	}
	
	@Override
	public List<Dipartimento> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, boolean fetchChildren) throws NotImplementedException, ServiceException,Exception {

        List<Dipartimento> list = new ArrayList<Dipartimento>();
        
        try {
			List<IField> fields = new ArrayList<IField>();
	        
			String id = "id"; 
			fields.add(new CustomField(id, Long.class, id, this.getDipartimentoFieldConverter().toTable(Dipartimento.model())));
			fields.add(Dipartimento.model().CODICE);
			fields.add(Dipartimento.model().DESCRIZIONE);
			fields.add(Dipartimento.model().ACCETTAZIONE_AUTOMATICA);
			fields.add(Dipartimento.model().MODALITA_PUSH);
			fields.add(Dipartimento.model().ENTE.NOME);
	
			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));
			
			for(Map<String, Object> map: returnMap) {
				Dipartimento dipartimento = (Dipartimento)this.getDipartimentoFetch().fetch(jdbcProperties.getDatabase(), Dipartimento.model(), map);
				dipartimento.setEnte((IdEnte)this.getDipartimentoFetch().fetch(jdbcProperties.getDatabase(), Dipartimento.model().ENTE, map));
				if(fetchChildren) {
					org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
							new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
						
					ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
	
					// Object dipartimento_dipartimentoPropertyValue
					ISQLQueryObject sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue = sqlQueryObjectGet.newSQLQueryObject();
					sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.setANDLogicOperator(true);
					sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
					sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addSelectField("id");
					sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addSelectField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE,true));
					sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addWhereCondition("id_dipartimento=?");
					
					// Get dipartimento_dipartimentoPropertyValue
					java.util.List<Object> dipartimento_dipartimentoPropertyValue_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.createSQLQuery(), jdbcProperties.isShowSql(), Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE, this.getDipartimentoFetch(),
						new JDBCObject(dipartimento.getId(),Long.class));
	
					if(dipartimento_dipartimentoPropertyValue_list != null) {
						for (Object dipartimento_dipartimentoPropertyValue_object: dipartimento_dipartimentoPropertyValue_list) {
							DipartimentoPropertyValue dipartimento_dipartimentoPropertyValue = (DipartimentoPropertyValue) dipartimento_dipartimentoPropertyValue_object;
	
	
							// Object _dipartimento_dipartimentoPropertyValue_dipartimentoProperty (recupero id)
							ISQLQueryObject sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId = sqlQueryObjectGet.newSQLQueryObject();
							sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
							sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.addSelectField("id_dipartimento_property");
							sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.addWhereCondition("id=?");
							sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.setANDLogicOperator(true);
							Long idFK_dipartimento_dipartimentoPropertyValue_dipartimentoProperty = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
									new JDBCObject(dipartimento_dipartimentoPropertyValue.getId(),Long.class));
							
							// Object _dipartimento_dipartimentoPropertyValue_dipartimentoProperty
							dipartimento_dipartimentoPropertyValue.setIdProperty(JDBCDipartimentoPropertyUtils.toLogicId(jdbcProperties, log, connection, sqlQueryObject, idFK_dipartimento_dipartimentoPropertyValue_dipartimentoProperty));
	
							dipartimento.addDipartimentoPropertyValue(dipartimento_dipartimentoPropertyValue);
						}
					}
	
				}
				list.add(dipartimento);
			}
        } catch(NotFoundException e) {}

        return list;      
		
	}
	
	@Override
	public Dipartimento find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) 
		throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {

        long id = this.findTableId(jdbcProperties, log, connection, sqlQueryObject, expression);
        if(id>0){
        	return this.get(jdbcProperties, log, connection, sqlQueryObject, id);
        }else{
        	throw new NotFoundException("Entry with id["+id+"] not found");
        }
		
	}
	
	@Override
	public NonNegativeNumber count(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareCount(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getDipartimentoFieldConverter(), Dipartimento.model());
		
		sqlQueryObject.addSelectCountField(this.getDipartimentoFieldConverter().toTable(Dipartimento.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getDipartimentoFieldConverter(), Dipartimento.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_dipartimento = this.findIdDipartimento(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_dipartimento);
		
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
												this.getDipartimentoFieldConverter(), field);

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
        						expression, this.getDipartimentoFieldConverter(), Dipartimento.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getDipartimentoFieldConverter(), Dipartimento.model(),
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
        						this.getDipartimentoFieldConverter(), Dipartimento.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getDipartimentoFieldConverter(), Dipartimento.model(), 
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
        						this.getDipartimentoFieldConverter(), Dipartimento.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getDipartimentoFieldConverter(), Dipartimento.model(), 
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
			return new JDBCExpression(this.getDipartimentoFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getDipartimentoFieldConverter());
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
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id, Dipartimento obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Dipartimento obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Dipartimento obj, Dipartimento imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getEnte()!=null && 
				imgSaved.getEnte()!=null){
			obj.getEnte().setId(imgSaved.getEnte().getId());
		}
		if(obj.getDipartimentoPropertyValueList()!=null){
			List<org.govmix.proxy.fatturapa.DipartimentoPropertyValue> listObj_ = obj.getDipartimentoPropertyValueList();
			for(org.govmix.proxy.fatturapa.DipartimentoPropertyValue itemObj_ : listObj_){
				org.govmix.proxy.fatturapa.DipartimentoPropertyValue itemAlreadySaved_ = null;
				if(imgSaved.getDipartimentoPropertyValueList()!=null){
					List<org.govmix.proxy.fatturapa.DipartimentoPropertyValue> listImgSaved_ = imgSaved.getDipartimentoPropertyValueList();
					for(org.govmix.proxy.fatturapa.DipartimentoPropertyValue itemImgSaved_ : listImgSaved_){
						boolean objEqualsToImgSaved_ = false;
						objEqualsToImgSaved_ = org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_.getIdProperty().getNome(),itemImgSaved_.getIdProperty().getNome());
						if(objEqualsToImgSaved_){
							itemAlreadySaved_=itemImgSaved_;
							break;
						}
					}
				}
				if(itemAlreadySaved_!=null){
					itemObj_.setId(itemAlreadySaved_.getId());
					if(itemObj_.getIdProperty()!=null && 
							itemAlreadySaved_.getIdProperty()!=null){
						itemObj_.getIdProperty().setId(itemAlreadySaved_.getIdProperty().getId());
						if(itemObj_.getIdProperty().getIdEnte()!=null && 
								itemAlreadySaved_.getIdProperty().getIdEnte()!=null){
							itemObj_.getIdProperty().getIdEnte().setId(itemAlreadySaved_.getIdProperty().getIdEnte().getId());
						}
					}
				}
			}
		}

	}
	
	@Override
	public Dipartimento get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private Dipartimento _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		Dipartimento dipartimento = new Dipartimento();
		

		// Object dipartimento
		ISQLQueryObject sqlQueryObjectGet_dipartimento = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_dipartimento.setANDLogicOperator(true);
		sqlQueryObjectGet_dipartimento.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model()));
		sqlQueryObjectGet_dipartimento.addSelectField("id");
		sqlQueryObjectGet_dipartimento.addSelectField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().CODICE,true));
		sqlQueryObjectGet_dipartimento.addSelectField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DESCRIZIONE,true));
		sqlQueryObjectGet_dipartimento.addSelectField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().ACCETTAZIONE_AUTOMATICA,true));
		sqlQueryObjectGet_dipartimento.addSelectField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().MODALITA_PUSH,true));
		sqlQueryObjectGet_dipartimento.addWhereCondition("id=?");

		// Get dipartimento
		dipartimento = (Dipartimento) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_dipartimento.createSQLQuery(), jdbcProperties.isShowSql(), Dipartimento.model(), this.getDipartimentoFetch(),
			new JDBCObject(tableId,Long.class));


		// Object _dipartimento_ente (recupero id)
		ISQLQueryObject sqlQueryObjectGet_dipartimento_ente_readFkId = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_dipartimento_ente_readFkId.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model()));
		sqlQueryObjectGet_dipartimento_ente_readFkId.addSelectField("id_ente");
		sqlQueryObjectGet_dipartimento_ente_readFkId.addWhereCondition("id=?");
		sqlQueryObjectGet_dipartimento_ente_readFkId.setANDLogicOperator(true);
		Long idFK_dipartimento_ente = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_dipartimento_ente_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
				new JDBCObject(dipartimento.getId(),Long.class));

		// Object _dipartimento_ente
		dipartimento.setEnte(JDBCEnteUtils.toLogicId(jdbcProperties, log, connection, sqlQueryObject, idFK_dipartimento_ente));


		try {
			// Object _dipartimento_registro (recupero id)
			ISQLQueryObject sqlQueryObjectGet_dipartimento_registro_readFkId = sqlQueryObjectGet.newSQLQueryObject();
			sqlQueryObjectGet_dipartimento_registro_readFkId.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model()));
			sqlQueryObjectGet_dipartimento_registro_readFkId.addSelectField("id_registro");
			sqlQueryObjectGet_dipartimento_registro_readFkId.addWhereCondition("id=?");
			sqlQueryObjectGet_dipartimento_registro_readFkId.setANDLogicOperator(true);
			Long idFK_dipartimento_registro = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_dipartimento_registro_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
					new JDBCObject(dipartimento.getId(),Long.class));
	
			if(idFK_dipartimento_registro != null && idFK_dipartimento_registro.longValue() > 0) {
				// Object _dipartimento_registro
				dipartimento.setRegistro(JDBCRegistroUtils.toLogicId(jdbcProperties, log, connection, sqlQueryObject, idFK_dipartimento_registro));
			}
		} catch(NotFoundException e) {}


		// Object dipartimento_dipartimentoPropertyValue
		ISQLQueryObject sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.setANDLogicOperator(true);
		sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
		sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addSelectField("id");
		sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addSelectField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.VALORE,true));
		sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.addWhereCondition("id_dipartimento=?");
		
		// Get dipartimento_dipartimentoPropertyValue
		java.util.List<Object> dipartimento_dipartimentoPropertyValue_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue.createSQLQuery(), jdbcProperties.isShowSql(), Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE, this.getDipartimentoFetch(),
			new JDBCObject(dipartimento.getId(),Long.class));

		if(dipartimento_dipartimentoPropertyValue_list != null) {
			for (Object dipartimento_dipartimentoPropertyValue_object: dipartimento_dipartimentoPropertyValue_list) {
				DipartimentoPropertyValue dipartimento_dipartimentoPropertyValue = (DipartimentoPropertyValue) dipartimento_dipartimentoPropertyValue_object;


				// Object _dipartimento_dipartimentoPropertyValue_dipartimentoProperty (recupero id)
				ISQLQueryObject sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId = sqlQueryObjectGet.newSQLQueryObject();
				sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE));
				sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.addSelectField("id_dipartimento_property");
				sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.addWhereCondition("id=?");
				sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.setANDLogicOperator(true);
				Long idFK_dipartimento_dipartimentoPropertyValue_dipartimentoProperty = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_dipartimento_dipartimentoPropertyValue_dipartimentoProperty_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
						new JDBCObject(dipartimento_dipartimentoPropertyValue.getId(),Long.class));
				
				// Object _dipartimento_dipartimentoPropertyValue_dipartimentoProperty
				dipartimento_dipartimentoPropertyValue.setIdProperty(JDBCDipartimentoPropertyUtils.toLogicId(jdbcProperties, log, connection, sqlQueryObject, idFK_dipartimento_dipartimentoPropertyValue_dipartimentoProperty));

				dipartimento.addDipartimentoPropertyValue(dipartimento_dipartimentoPropertyValue);
			}
		}

        return dipartimento;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsDipartimento = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getDipartimentoFieldConverter().toTable(Dipartimento.model()));
		sqlQueryObject.addSelectField(this.getDipartimentoFieldConverter().toColumn(Dipartimento.model().CODICE,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists dipartimento
		existsDipartimento = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsDipartimento;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{
	
		if(expression.inUseModel(Dipartimento.model().ENTE,false)){
			String tableName1 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model());
			String tableName2 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model().ENTE);
			sqlQueryObject.addWhereCondition(tableName1+".id_ente="+tableName2+".id");
		}
		
		if(expression.inUseModel(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE,false)){
			String tableName1 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE);
			String tableName2 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model());
			sqlQueryObject.addWhereCondition(tableName1+".id_dipartimento="+tableName2+".id");
		}
		
		if(expression.inUseModel(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY,false)){
			if(!expression.inUseModel(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE,false)) {
				String tableName1 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE);
				String tableName2 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model());
				sqlQueryObject.addWhereCondition(tableName1+".id_dipartimento="+tableName2+".id");
			}
			
			String tableName1 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE);
			String tableName2 = this.getDipartimentoFieldConverter().toAliasTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY);
			sqlQueryObject.addWhereCondition(tableName1+".id_dipartimento_property="+tableName2+".id");

			
		}
        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdDipartimento(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		DipartimentoFieldConverter converter = this.getDipartimentoFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		// Dipartimento.model()
		mapTableToPKColumn.put(converter.toTable(Dipartimento.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Dipartimento.model()))
			));

		// Dipartimento.model().ENTE
		mapTableToPKColumn.put(converter.toTable(Dipartimento.model().ENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Dipartimento.model().ENTE))
			));

		// Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE
		mapTableToPKColumn.put(converter.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE))
			));

		// Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY
		mapTableToPKColumn.put(converter.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY))
			));

		// Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE
		mapTableToPKColumn.put(converter.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Dipartimento.model().DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE))
			));

        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getDipartimentoFieldConverter().toTable(Dipartimento.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getDipartimentoFieldConverter(), Dipartimento.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getDipartimentoFieldConverter(), Dipartimento.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getDipartimentoFieldConverter().toTable(Dipartimento.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getDipartimentoFieldConverter(), Dipartimento.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getDipartimentoFieldConverter(), Dipartimento.model(), objectIdClass, listaQuery);
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
	
	protected Long findIdDipartimento(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		return JDBCDipartimentoUtils.toLongId(jdbcProperties, log, connection, sqlQueryObject, id, throwNotFound);
	}
}
