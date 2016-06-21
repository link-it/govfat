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
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.IdRegistro;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroPropertyValue;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.RegistroFieldConverter;
import org.govmix.proxy.fatturapa.dao.jdbc.fetch.RegistroFetch;
import org.openspcoop2.generic_project.beans.AliasField;
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
 * JDBCRegistroServiceSearchImpl
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCRegistroServiceSearchImpl implements IJDBCRegistroServiceSearch {

	private RegistroFieldConverter _registroFieldConverter = null;
	public RegistroFieldConverter getRegistroFieldConverter() {
		if(this._registroFieldConverter==null){
			this._registroFieldConverter = new RegistroFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._registroFieldConverter;
	}
	
	private RegistroFetch registroFetch = new RegistroFetch();
	public RegistroFetch getRegistroFetch() {
		return this.registroFetch;
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
	public IdRegistro convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Registro registro) throws NotImplementedException, ServiceException, Exception{
	
		IdRegistro idRegistro = new IdRegistro();
		idRegistro.setNome(registro.getNome());
	
		return idRegistro;
	}
	
	@Override
	public Registro get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_registro = this.findIdRegistro(jdbcProperties, log, connection, sqlQueryObject, id, true);
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_registro);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_registro = this.findIdRegistro(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_registro != null && id_registro > 0;
		
	}
	
	@Override
	public List<IdRegistro> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression) throws NotImplementedException, ServiceException,Exception {

		List<IdRegistro> list = new ArrayList<IdRegistro>();

		try {
			List<IField> fields = new ArrayList<IField>();
	        
			fields.add(Registro.model().NOME);
	        
			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));
			
			for(Map<String, Object> map: returnMap) {
				list.add(this.convertToId(jdbcProperties, log, connection, sqlQueryObject, (Registro)this.getRegistroFetch().fetch(jdbcProperties.getDatabase(), Registro.model(), map)));
			}
		} catch(NotFoundException e){}

        return list;
		
	}

	
	@Override
	public List<Registro> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression) throws NotImplementedException, ServiceException,Exception {
		return this.findAll(jdbcProperties, log, connection, sqlQueryObject, expression, true);
	}
	
	@Override
	public List<Registro> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, boolean fetchChildren) throws NotImplementedException, ServiceException,Exception {

        List<Registro> list = new ArrayList<Registro>();
        try {
			List<IField> fields = new ArrayList<IField>();
			String id = "id"; 
			fields.add(new CustomField(id, Long.class, id, this.getRegistroFieldConverter().toTable(Registro.model())));
			fields.add(new AliasField(Registro.model().NOME, "nomeRegistro"));
			fields.add(Registro.model().USERNAME);
			fields.add(Registro.model().PASSWORD);
			fields.add(new AliasField(Registro.model().ID_ENTE.NOME, "nomeEnte"));
			
	        
			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));
			
			for(Map<String, Object> map: returnMap) {
				Registro registro = (Registro)this.getRegistroFetch().fetch(jdbcProperties.getDatabase(), Registro.model(), map);
				registro.setIdEnte((IdEnte)this.getRegistroFetch().fetch(jdbcProperties.getDatabase(), Registro.model().ID_ENTE, map));
				
				if(fetchChildren) {
					
					org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
							new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
						
					ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
	
					// Object registro_registroPropertyValue
					ISQLQueryObject sqlQueryObjectGet_registro_registroPropertyValue = sqlQueryObjectGet.newSQLQueryObject();
					sqlQueryObjectGet_registro_registroPropertyValue.setANDLogicOperator(true);
					sqlQueryObjectGet_registro_registroPropertyValue.addFromTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
					sqlQueryObjectGet_registro_registroPropertyValue.addSelectField("id");
					sqlQueryObjectGet_registro_registroPropertyValue.addSelectField(this.getRegistroFieldConverter().toColumn(Registro.model().REGISTRO_PROPERTY_VALUE.VALORE,true));
					sqlQueryObjectGet_registro_registroPropertyValue.addWhereCondition("id_registro=?");
	
					// Get registro_registroPropertyValue
					java.util.List<Object> registro_registroPropertyValue_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_registro_registroPropertyValue.createSQLQuery(), jdbcProperties.isShowSql(), Registro.model().REGISTRO_PROPERTY_VALUE, this.getRegistroFetch(),
						new JDBCObject(registro.getId(),Long.class));
	
					if(registro_registroPropertyValue_list != null) {
						for (Object registro_registroPropertyValue_object: registro_registroPropertyValue_list) {
							RegistroPropertyValue registro_registroPropertyValue = (RegistroPropertyValue) registro_registroPropertyValue_object;
	
	
							// Object _registro_registroPropertyValue_registroProperty (recupero id)
							ISQLQueryObject sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId = sqlQueryObjectGet.newSQLQueryObject();
							sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.addFromTable("registri_prop_values");
							sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.addSelectField("id_registro_property");
							sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.addWhereCondition("id=?");
							sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.setANDLogicOperator(true);
							Long idFK_registro_registroPropertyValue_registroProperty = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
									new JDBCObject(registro_registroPropertyValue.getId(),Long.class));
							
							registro_registroPropertyValue.setIdProperty(JDBCRegistroPropertyUtils.toLogicId(jdbcProperties, log, connection, sqlQueryObject, idFK_registro_registroPropertyValue_registroProperty));
	
							registro.addRegistroPropertyValue(registro_registroPropertyValue);
						}
					}
	
				}
				
				list.add(registro);
			}
        } catch(NotFoundException e) {}
        return list;      
		
	}
	
	@Override
	public Registro find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) 
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
												this.getRegistroFieldConverter(), Registro.model());
		
		sqlQueryObject.addSelectCountField(this.getRegistroFieldConverter().toTable(Registro.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getRegistroFieldConverter(), Registro.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_registro = this.findIdRegistro(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_registro);
		
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
												this.getRegistroFieldConverter(), field);

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
        						expression, this.getRegistroFieldConverter(), Registro.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getRegistroFieldConverter(), Registro.model(),
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
        						this.getRegistroFieldConverter(), Registro.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getRegistroFieldConverter(), Registro.model(), 
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
        						this.getRegistroFieldConverter(), Registro.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getRegistroFieldConverter(), Registro.model(), 
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
			return new JDBCExpression(this.getRegistroFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getRegistroFieldConverter());
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
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id, Registro obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Registro obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Registro obj, Registro imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getIdEnte()!=null && 
				imgSaved.getIdEnte()!=null){
			obj.getIdEnte().setId(imgSaved.getIdEnte().getId());
		}
		if(obj.getRegistroPropertyValueList()!=null){
			List<org.govmix.proxy.fatturapa.RegistroPropertyValue> listObj_ = obj.getRegistroPropertyValueList();
			for(org.govmix.proxy.fatturapa.RegistroPropertyValue itemObj_ : listObj_){
				org.govmix.proxy.fatturapa.RegistroPropertyValue itemAlreadySaved_ = null;
				if(imgSaved.getRegistroPropertyValueList()!=null){
					List<org.govmix.proxy.fatturapa.RegistroPropertyValue> listImgSaved_ = imgSaved.getRegistroPropertyValueList();
					for(org.govmix.proxy.fatturapa.RegistroPropertyValue itemImgSaved_ : listImgSaved_){
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
	public Registro get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private Registro _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		Registro registro = new Registro();
		

		// Object registro
		ISQLQueryObject sqlQueryObjectGet_registro = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_registro.setANDLogicOperator(true);
		sqlQueryObjectGet_registro.addFromTable(this.getRegistroFieldConverter().toTable(Registro.model()));
		sqlQueryObjectGet_registro.addSelectField("id");
		sqlQueryObjectGet_registro.addSelectField(this.getRegistroFieldConverter().toColumn(Registro.model().NOME,true));
		sqlQueryObjectGet_registro.addSelectField(this.getRegistroFieldConverter().toColumn(Registro.model().USERNAME,true));
		sqlQueryObjectGet_registro.addSelectField(this.getRegistroFieldConverter().toColumn(Registro.model().PASSWORD,true));
		sqlQueryObjectGet_registro.addWhereCondition("id=?");

		// Get registro
		registro = (Registro) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_registro.createSQLQuery(), jdbcProperties.isShowSql(), Registro.model(), this.getRegistroFetch(),
			new JDBCObject(tableId,Long.class));


		// Object _registro_ente (recupero id)
		ISQLQueryObject sqlQueryObjectGet_registro_ente_readFkId = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_registro_ente_readFkId.addFromTable("registri");
		sqlQueryObjectGet_registro_ente_readFkId.addSelectField("id_ente");
		sqlQueryObjectGet_registro_ente_readFkId.addWhereCondition("id=?");
		sqlQueryObjectGet_registro_ente_readFkId.setANDLogicOperator(true);
		Long idFK_registro_ente = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_registro_ente_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
				new JDBCObject(registro.getId(),Long.class));
		
		registro.setIdEnte(JDBCEnteUtils.toLogicId(jdbcProperties, log, connection, sqlQueryObject, idFK_registro_ente));


		// Object registro_registroPropertyValue
		ISQLQueryObject sqlQueryObjectGet_registro_registroPropertyValue = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_registro_registroPropertyValue.setANDLogicOperator(true);
		sqlQueryObjectGet_registro_registroPropertyValue.addFromTable(this.getRegistroFieldConverter().toTable(Registro.model().REGISTRO_PROPERTY_VALUE));
		sqlQueryObjectGet_registro_registroPropertyValue.addSelectField("id");
		sqlQueryObjectGet_registro_registroPropertyValue.addSelectField(this.getRegistroFieldConverter().toColumn(Registro.model().REGISTRO_PROPERTY_VALUE.VALORE,true));
		sqlQueryObjectGet_registro_registroPropertyValue.addWhereCondition("id_registro=?");

		// Get registro_registroPropertyValue
		java.util.List<Object> registro_registroPropertyValue_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_registro_registroPropertyValue.createSQLQuery(), jdbcProperties.isShowSql(), Registro.model().REGISTRO_PROPERTY_VALUE, this.getRegistroFetch(),
			new JDBCObject(registro.getId(),Long.class));

		if(registro_registroPropertyValue_list != null) {
			for (Object registro_registroPropertyValue_object: registro_registroPropertyValue_list) {
				RegistroPropertyValue registro_registroPropertyValue = (RegistroPropertyValue) registro_registroPropertyValue_object;


				// Object _registro_registroPropertyValue_registroProperty (recupero id)
				ISQLQueryObject sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId = sqlQueryObjectGet.newSQLQueryObject();
				sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.addFromTable("registri_prop_values");
				sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.addSelectField("id_registro_property");
				sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.addWhereCondition("id=?");
				sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.setANDLogicOperator(true);
				Long idFK_registro_registroPropertyValue_registroProperty = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_registro_registroPropertyValue_registroProperty_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
						new JDBCObject(registro_registroPropertyValue.getId(),Long.class));
				
				registro_registroPropertyValue.setIdProperty(JDBCRegistroPropertyUtils.toLogicId(jdbcProperties, log, connection, sqlQueryObject, idFK_registro_registroPropertyValue_registroProperty));

				registro.addRegistroPropertyValue(registro_registroPropertyValue);
			}
		}
		
        return registro;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsRegistro = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getRegistroFieldConverter().toTable(Registro.model()));
		sqlQueryObject.addSelectField(this.getRegistroFieldConverter().toColumn(Registro.model().NOME,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists registro
		existsRegistro = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsRegistro;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{
		
		if(expression.inUseModel(Registro.model().ID_ENTE,false)){
			String tableName1 = this.getRegistroFieldConverter().toAliasTable(Registro.model());
			String tableName2 = this.getRegistroFieldConverter().toAliasTable(Registro.model().ID_ENTE);
			sqlQueryObject.addWhereCondition(tableName1+".id_ente="+tableName2+".id");
		}
		
		if(expression.inUseModel(Registro.model().REGISTRO_PROPERTY_VALUE,false)){
			String tableName1 = this.getRegistroFieldConverter().toAliasTable(Registro.model().REGISTRO_PROPERTY_VALUE);
			String tableName2 = this.getRegistroFieldConverter().toAliasTable(Registro.model());
			sqlQueryObject.addWhereCondition(tableName1+".id_registro="+tableName2+".id");
		}
		
		if(expression.inUseModel(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY,false)){
			if(!expression.inUseModel(Registro.model().REGISTRO_PROPERTY_VALUE,false)) {
				String tableName1 = this.getRegistroFieldConverter().toAliasTable(Registro.model().REGISTRO_PROPERTY_VALUE);
				String tableName2 = this.getRegistroFieldConverter().toAliasTable(Registro.model());
				sqlQueryObject.addWhereCondition(tableName1+".id_registro="+tableName2+".id");
			}
			
			String tableName1 = this.getRegistroFieldConverter().toAliasTable(Registro.model().REGISTRO_PROPERTY_VALUE);
			String tableName2 = this.getRegistroFieldConverter().toAliasTable(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY);
			sqlQueryObject.addWhereCondition(tableName1+".id_registro_property="+tableName2+".id");
			
		}
		
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdRegistro(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		RegistroFieldConverter converter = this.getRegistroFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		// Registro.model()
		mapTableToPKColumn.put(converter.toTable(Registro.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Registro.model()))
			));

		// Registro.model().ID_ENTE
		mapTableToPKColumn.put(converter.toTable(Registro.model().ID_ENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Registro.model().ID_ENTE))
			));

		// Registro.model().REGISTRO_PROPERTY_VALUE
		mapTableToPKColumn.put(converter.toTable(Registro.model().REGISTRO_PROPERTY_VALUE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Registro.model().REGISTRO_PROPERTY_VALUE))
			));

		// Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY
		mapTableToPKColumn.put(converter.toTable(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY))
			));

		// Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE
		mapTableToPKColumn.put(converter.toTable(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Registro.model().REGISTRO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE))
			));

        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getRegistroFieldConverter().toTable(Registro.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getRegistroFieldConverter(), Registro.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getRegistroFieldConverter(), Registro.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getRegistroFieldConverter().toTable(Registro.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getRegistroFieldConverter(), Registro.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getRegistroFieldConverter(), Registro.model(), objectIdClass, listaQuery);
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
	
	protected Long findIdRegistro(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRegistro id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _registro
		sqlQueryObjectGet.addFromTable(this.getRegistroFieldConverter().toTable(Registro.model()));
		sqlQueryObjectGet.addSelectField("id");

		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition(this.getRegistroFieldConverter().toColumn(Registro.model().NOME,true)+"=?");

		// Recupero _registro
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_registro = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getNome(),Registro.model().NOME.getFieldType())
		};
		Long id_registro = null;
		try{
			id_registro = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_registro);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_registro==null || id_registro<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_registro;
	}
}
