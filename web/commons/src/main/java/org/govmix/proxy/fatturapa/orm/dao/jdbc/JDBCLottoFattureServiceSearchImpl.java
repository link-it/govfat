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
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.LottoFattureFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.LottoFattureFetch;
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
 * JDBCLottoFattureServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCLottoFattureServiceSearchImpl implements IJDBCServiceSearchWithId<LottoFatture, IdLotto, JDBCServiceManager> {

	private LottoFattureFieldConverter _lottoFattureFieldConverter = null;
	public LottoFattureFieldConverter getLottoFattureFieldConverter() {
		if(this._lottoFattureFieldConverter==null){
			this._lottoFattureFieldConverter = new LottoFattureFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._lottoFattureFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getLottoFattureFieldConverter();
	}
	
	private LottoFattureFetch lottoFattureFetch = new LottoFattureFetch();
	public LottoFattureFetch getLottoFattureFetch() {
		return this.lottoFattureFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getLottoFattureFetch();
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
	public IdLotto convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, LottoFatture lottoFatture) throws NotImplementedException, ServiceException, Exception{
        IdLotto idLottoFatture = new IdLotto(lottoFatture.isFatturazioneAttiva());
        idLottoFatture.setIdentificativoSdi(lottoFatture.getIdentificativoSdi());
        return idLottoFatture;
	}
	
	@Override
	public LottoFatture get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_lottoFatture = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdLottoFatture(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_lottoFatture,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_lottoFatture = this.findIdLottoFatture(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_lottoFatture != null && id_lottoFatture > 0;
		
	}
	
	@Override
	public List<IdLotto> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<IdLotto> list = new ArrayList<IdLotto>();

		// TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari a create l'ID logico
		// 2. Usare metodo getLottoFattureFetch() sul risultato della select per ottenere un oggetto LottoFatture
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 
		// 3. Usare metodo convertToId per ottenere l'id

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	LottoFatture lottoFatture = this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
			IdLotto idLottoFatture = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,lottoFatture);
        	list.add(idLottoFatture);
        }

        return list;
		
	}
	
	@Override
	public List<LottoFatture> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<LottoFatture> list = new ArrayList<LottoFatture>();
        
        // TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari
		// 2. Usare metodo getLottoFattureFetch() sul risultato della select per ottenere un oggetto LottoFatture
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	list.add(this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour));
        }

        return list;      
		
	}
	
	@Override
	public LottoFatture find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
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
												this.getLottoFattureFieldConverter(), LottoFatture.model());
		
		sqlQueryObject.addSelectCountField(this.getLottoFattureFieldConverter().toTable(LottoFatture.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getLottoFattureFieldConverter(), LottoFatture.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_lottoFatture = this.findIdLottoFatture(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_lottoFatture);
		
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
												this.getLottoFattureFieldConverter(), field);

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
        						expression, this.getLottoFattureFieldConverter(), LottoFatture.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getLottoFattureFieldConverter(), LottoFatture.model(),
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
        						this.getLottoFattureFieldConverter(), LottoFatture.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getLottoFattureFieldConverter(), LottoFatture.model(), 
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
        						this.getLottoFattureFieldConverter(), LottoFatture.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getLottoFattureFieldConverter(), LottoFatture.model(), 
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
			return new JDBCExpression(this.getLottoFattureFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getLottoFattureFieldConverter());
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
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id, LottoFatture obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, LottoFatture obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, LottoFatture obj, LottoFatture imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getIdSIP()!=null && 
				imgSaved.getIdSIP()!=null){
			obj.getIdSIP().setId(imgSaved.getIdSIP().getId());
		}
		if(obj.getDipartimento()!=null && 
				imgSaved.getDipartimento()!=null){
			obj.getDipartimento().setId(imgSaved.getDipartimento().getId());
			if(obj.getDipartimento().getEnte()!=null && 
					imgSaved.getDipartimento().getEnte()!=null){
				obj.getDipartimento().getEnte().setId(imgSaved.getDipartimento().getEnte().getId());
			}
			if(obj.getDipartimento().getRegistro()!=null && 
					imgSaved.getDipartimento().getRegistro()!=null){
				obj.getDipartimento().getRegistro().setId(imgSaved.getDipartimento().getRegistro().getId());
			}
			if(obj.getDipartimento().getDipartimentoPropertyValueList()!=null){
				List<org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue> listObj_dipartimento = obj.getDipartimento().getDipartimentoPropertyValueList();
				for(org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue itemObj_dipartimento : listObj_dipartimento){
					org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue itemAlreadySaved_dipartimento = null;
					if(imgSaved.getDipartimento().getDipartimentoPropertyValueList()!=null){
						List<org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue> listImgSaved_dipartimento = imgSaved.getDipartimento().getDipartimentoPropertyValueList();
						for(org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue itemImgSaved_dipartimento : listImgSaved_dipartimento){
							boolean objEqualsToImgSaved_dipartimento = false;
							objEqualsToImgSaved_dipartimento = org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_dipartimento.getValore(),itemImgSaved_dipartimento.getValore());
							if(objEqualsToImgSaved_dipartimento){
								itemAlreadySaved_dipartimento=itemImgSaved_dipartimento;
								break;
							}
						}
					}
					if(itemAlreadySaved_dipartimento!=null){
						itemObj_dipartimento.setId(itemAlreadySaved_dipartimento.getId());
						if(itemObj_dipartimento.getIdProperty()!=null && 
								itemAlreadySaved_dipartimento.getIdProperty()!=null){
							itemObj_dipartimento.getIdProperty().setId(itemAlreadySaved_dipartimento.getIdProperty().getId());
							if(itemObj_dipartimento.getIdProperty().getIdEnte()!=null && 
									itemAlreadySaved_dipartimento.getIdProperty().getIdEnte()!=null){
								itemObj_dipartimento.getIdProperty().getIdEnte().setId(itemAlreadySaved_dipartimento.getIdProperty().getIdEnte().getId());
							}
						}
					}
				}
			}
		}

	}
	
	@Override
	public LottoFatture get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private LottoFatture _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		LottoFatture lottoFatture = new LottoFatture();
		

		// Object lottoFatture
		ISQLQueryObject sqlQueryObjectGet_lottoFatture = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_lottoFatture.setANDLogicOperator(true);
		sqlQueryObjectGet_lottoFatture.addFromTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		sqlQueryObjectGet_lottoFatture.addSelectField("id");
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FORMATO_TRASMISSIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().NOME_FILE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().MESSAGE_ID,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_NOME,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_COGNOME,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_CODICE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_PAESE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CODICE_DESTINATARIO,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().XML,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FATTURAZIONE_ATTIVA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TIPI_COMUNICAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_ULTIMA_ELABORAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DETTAGLIO_ELABORAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().TENTATIVI_CONSEGNA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_RICEZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_INSERIMENTO,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_CONSEGNA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_CONSEGNA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DETTAGLIO_CONSEGNA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_PROTOCOLLAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DOMINIO,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().SOTTODOMINIO,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().PAGO_PA,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_PROTOCOLLAZIONE,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().PROTOCOLLO,true));
		sqlQueryObjectGet_lottoFatture.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().ID_EGOV,true));
		sqlQueryObjectGet_lottoFatture.addWhereCondition("id=?");

		// Get lottoFatture
		lottoFatture = (LottoFatture) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_lottoFatture.createSQLQuery(), jdbcProperties.isShowSql(), LottoFatture.model(), this.getLottoFattureFetch(),
			new JDBCObject(tableId,Long.class));


		if(idMappingResolutionBehaviour==null ||
			(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
		){
			try {
				// Object _lottoFatture_sip (recupero id)
				ISQLQueryObject sqlQueryObjectGet_lottoFatture_sip_readFkId = sqlQueryObjectGet.newSQLQueryObject();
				sqlQueryObjectGet_lottoFatture_sip_readFkId.addFromTable(this.getLottoFattureFieldConverter().toTable(org.govmix.proxy.fatturapa.orm.LottoFatture.model()));
				sqlQueryObjectGet_lottoFatture_sip_readFkId.addSelectField("id_sip");
				sqlQueryObjectGet_lottoFatture_sip_readFkId.addWhereCondition("id=?");
				sqlQueryObjectGet_lottoFatture_sip_readFkId.setANDLogicOperator(true);
				Long idFK_lottoFatture_sip = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_lottoFatture_sip_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
						new JDBCObject(lottoFatture.getId(),Long.class));
				
				org.govmix.proxy.fatturapa.orm.IdSip id_lottoFatture_sip = null;
				if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
					id_lottoFatture_sip = ((JDBCSIPServiceSearch)(this.getServiceManager().getSIPServiceSearch())).findId(idFK_lottoFatture_sip, false);
				}else{
					id_lottoFatture_sip = new org.govmix.proxy.fatturapa.orm.IdSip();
				}
				id_lottoFatture_sip.setId(idFK_lottoFatture_sip);
				lottoFatture.setIdSIP(id_lottoFatture_sip);
			} catch(NotFoundException e) {}
		}

		
        return lottoFatture;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsLottoFatture = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		sqlQueryObject.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FORMATO_TRASMISSIONE,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists lottoFatture
		existsLottoFatture = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsLottoFatture;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{
		if(expression.inUseModel(LottoFatture.model().DIPARTIMENTO,false)){
			String tableName1 = this.getLottoFattureFieldConverter().toAliasTable(LottoFatture.model());
			String tableName2 = this.getLottoFattureFieldConverter().toAliasTable(LottoFatture.model().DIPARTIMENTO);
			sqlQueryObject.addWhereCondition(tableName1+".codice_destinatario="+tableName2+".codice");
		}

		if(expression.inUseModel(LottoFatture.model().ID_SIP,false)){
			String tableName1 = this.getLottoFattureFieldConverter().toAliasTable(LottoFatture.model());
			String tableName2 = this.getLottoFattureFieldConverter().toAliasTable(LottoFatture.model().ID_SIP);
			sqlQueryObject.addWhereCondition(tableName1+".id_sip="+tableName2+".id");
		}

	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdLottoFatture(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		LottoFattureFieldConverter converter = this.getLottoFattureFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// LottoFatture.model()
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model()))
			));

		// LottoFatture.model().ID_SIP
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model().ID_SIP),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model().ID_SIP))
			));

		// LottoFatture.model().DIPARTIMENTO
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model().DIPARTIMENTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model().DIPARTIMENTO))
			));

		// LottoFatture.model().DIPARTIMENTO.ENTE
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model().DIPARTIMENTO.ENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model().DIPARTIMENTO.ENTE))
			));

		// LottoFatture.model().DIPARTIMENTO.REGISTRO
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model().DIPARTIMENTO.REGISTRO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model().DIPARTIMENTO.REGISTRO))
			));

		// LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE))
			));

		// LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY))
			));

		// LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE
		mapTableToPKColumn.put(converter.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(LottoFatture.model().DIPARTIMENTO.DIPARTIMENTO_PROPERTY_VALUE.ID_PROPERTY.ID_ENTE))
			));

        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getLottoFattureFieldConverter().toTable(LottoFatture.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getLottoFattureFieldConverter(), LottoFatture.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getLottoFattureFieldConverter(), LottoFatture.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getLottoFattureFieldConverter().toTable(LottoFatture.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getLottoFattureFieldConverter(), LottoFatture.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getLottoFattureFieldConverter(), LottoFatture.model(), objectIdClass, listaQuery);
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
		
        return inUse;

	}
	
	@Override
	public IdLotto findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _lottoFatture
		sqlQueryObjectGet.addFromTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		sqlQueryObjectGet.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObjectGet.addSelectField(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FATTURAZIONE_ATTIVA,true));
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _lottoFatture
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_lottoFatture = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_lottoFatture = new ArrayList<Class<?>>();
		listaFieldIdReturnType_lottoFatture.add(LottoFatture.model().IDENTIFICATIVO_SDI.getFieldType());
		listaFieldIdReturnType_lottoFatture.add(LottoFatture.model().FATTURAZIONE_ATTIVA.getFieldType());

		org.govmix.proxy.fatturapa.orm.IdLotto id_lottoFatture = null;
		List<Object> listaFieldId_lottoFatture = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_lottoFatture, searchParams_lottoFatture);
		if(listaFieldId_lottoFatture==null || listaFieldId_lottoFatture.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			// set _lottoFatture
			id_lottoFatture = new org.govmix.proxy.fatturapa.orm.IdLotto((Boolean)listaFieldId_lottoFatture.get(1));
			id_lottoFatture.setIdentificativoSdi((Integer)listaFieldId_lottoFatture.get(0));
		}
		
		return id_lottoFatture;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdLottoFatture(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	protected Long findIdLottoFatture(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _lottoFatture
		sqlQueryObjectGet.addFromTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		sqlQueryObjectGet.addSelectField("id");
		// Devono essere mappati nella where condition i metodi dell'oggetto id.getXXX
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().IDENTIFICATIVO_SDI,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().FATTURAZIONE_ATTIVA,true)+"=?");

		// Recupero _lottoFatture
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_lottoFatture = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdentificativoSdi(),LottoFatture.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getFatturazioneAttiva(),LottoFatture.model().FATTURAZIONE_ATTIVA.getFieldType())
		};
		Long id_lottoFatture = null;
		try{
			id_lottoFatture = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_lottoFatture);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_lottoFatture==null || id_lottoFatture<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_lottoFatture;
	}
}
