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
import org.govmix.proxy.fatturapa.orm.IdComunicazione;
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
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.ComunicazioneSdiFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.ComunicazioneSdiFetch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;
import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.ComunicazioneSdi;

/**     
 * JDBCComunicazioneSdiServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCComunicazioneSdiServiceSearchImpl implements IJDBCServiceSearchWithId<ComunicazioneSdi, IdComunicazione, JDBCServiceManager> {

	private ComunicazioneSdiFieldConverter _comunicazioneSdiFieldConverter = null;
	public ComunicazioneSdiFieldConverter getComunicazioneSdiFieldConverter() {
		if(this._comunicazioneSdiFieldConverter==null){
			this._comunicazioneSdiFieldConverter = new ComunicazioneSdiFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._comunicazioneSdiFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getComunicazioneSdiFieldConverter();
	}
	
	private ComunicazioneSdiFetch comunicazioneSdiFetch = new ComunicazioneSdiFetch();
	public ComunicazioneSdiFetch getComunicazioneSdiFetch() {
		return this.comunicazioneSdiFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getComunicazioneSdiFetch();
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
	public IdComunicazione convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, ComunicazioneSdi comunicazioneSdi) throws NotImplementedException, ServiceException, Exception{
	
		IdComunicazione idComunicazioneSdi = new IdComunicazione();
		idComunicazioneSdi.setIdentificativoSdi(comunicazioneSdi.getIdentificativoSdi());
		idComunicazioneSdi.setProgressivo(comunicazioneSdi.getProgressivo());
		idComunicazioneSdi.setTipoComunicazione(comunicazioneSdi.getTipoComunicazione());
	
		return idComunicazioneSdi;
	}
	
	@Override
	public ComunicazioneSdi get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_comunicazioneSdi = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdComunicazioneSdi(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_comunicazioneSdi,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_comunicazioneSdi = this.findIdComunicazioneSdi(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_comunicazioneSdi != null && id_comunicazioneSdi > 0;
		
	}
	
	@Override
	public List<IdComunicazione> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<IdComunicazione> list = new ArrayList<IdComunicazione>();

		// TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari a create l'ID logico
		// 2. Usare metodo getComunicazioneSdiFetch() sul risultato della select per ottenere un oggetto ComunicazioneSdi
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 
		// 3. Usare metodo convertToId per ottenere l'id

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	ComunicazioneSdi comunicazioneSdi = this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
			IdComunicazione idComunicazioneSdi = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,comunicazioneSdi);
        	list.add(idComunicazioneSdi);
        }

        return list;
		
	}
	
	@Override
	public List<ComunicazioneSdi> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<ComunicazioneSdi> list = new ArrayList<ComunicazioneSdi>();
        
        // TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari
		// 2. Usare metodo getComunicazioneSdiFetch() sul risultato della select per ottenere un oggetto ComunicazioneSdi
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	list.add(this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour));
        }

        return list;      
		
	}
	
	@Override
	public ComunicazioneSdi find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
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
												this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model());
		
		sqlQueryObject.addSelectCountField(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_comunicazioneSdi = this.findIdComunicazioneSdi(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_comunicazioneSdi);
		
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
												this.getComunicazioneSdiFieldConverter(), field);

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
        						expression, this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(),
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
        						this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(), 
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
        						this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(), 
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
			return new JDBCExpression(this.getComunicazioneSdiFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getComunicazioneSdiFieldConverter());
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
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id, ComunicazioneSdi obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, ComunicazioneSdi obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, ComunicazioneSdi obj, ComunicazioneSdi imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getMetadatoList()!=null){
			List<org.govmix.proxy.fatturapa.orm.Metadato> listObj_ = obj.getMetadatoList();
			for(org.govmix.proxy.fatturapa.orm.Metadato itemObj_ : listObj_){
				org.govmix.proxy.fatturapa.orm.Metadato itemAlreadySaved_ = null;
				if(imgSaved.getMetadatoList()!=null){
					List<org.govmix.proxy.fatturapa.orm.Metadato> listImgSaved_ = imgSaved.getMetadatoList();
					for(org.govmix.proxy.fatturapa.orm.Metadato itemImgSaved_ : listImgSaved_){
						boolean objEqualsToImgSaved_ = false;
						objEqualsToImgSaved_ = org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_.getNome(),itemImgSaved_.getNome());
						if(objEqualsToImgSaved_){
							itemAlreadySaved_=itemImgSaved_;
							break;
						}
					}
				}
				if(itemAlreadySaved_!=null){
					itemObj_.setId(itemAlreadySaved_.getId());
				}
			}
		}

	}
	
	@Override
	public ComunicazioneSdi get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private ComunicazioneSdi _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		ComunicazioneSdi comunicazioneSdi = new ComunicazioneSdi();
		

		// Object comunicazioneSdi
		ISQLQueryObject sqlQueryObjectGet_comunicazioneSdi = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_comunicazioneSdi.setANDLogicOperator(true);
		sqlQueryObjectGet_comunicazioneSdi.addFromTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField("id");
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().TIPO_COMUNICAZIONE,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().PROGRESSIVO,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DATA_RICEZIONE,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().NOME_FILE,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().CONTENT_TYPE,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().RAW_DATA,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().STATO_CONSEGNA,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DATA_CONSEGNA,true));
		sqlQueryObjectGet_comunicazioneSdi.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().DETTAGLIO_CONSEGNA,true));
		sqlQueryObjectGet_comunicazioneSdi.addWhereCondition("id=?");

		// Get comunicazioneSdi
		comunicazioneSdi = (ComunicazioneSdi) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_comunicazioneSdi.createSQLQuery(), jdbcProperties.isShowSql(), ComunicazioneSdi.model(), this.getComunicazioneSdiFetch(),
			new JDBCObject(tableId,Long.class));



		// Object comunicazioneSdi_metadato
		ISQLQueryObject sqlQueryObjectGet_comunicazioneSdi_metadato = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_comunicazioneSdi_metadato.setANDLogicOperator(true);
		sqlQueryObjectGet_comunicazioneSdi_metadato.addFromTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model().METADATO));
		sqlQueryObjectGet_comunicazioneSdi_metadato.addSelectField("id");
		sqlQueryObjectGet_comunicazioneSdi_metadato.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.NOME,true));
		sqlQueryObjectGet_comunicazioneSdi_metadato.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().METADATO.VALORE,true));
		sqlQueryObjectGet_comunicazioneSdi_metadato.addWhereCondition("id_comunicazione_sdi=?");

		// Get comunicazioneSdi_metadato
		java.util.List<Object> comunicazioneSdi_metadato_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_comunicazioneSdi_metadato.createSQLQuery(), jdbcProperties.isShowSql(), ComunicazioneSdi.model().METADATO, this.getComunicazioneSdiFetch(),
			new JDBCObject(comunicazioneSdi.getId(),Long.class));

		if(comunicazioneSdi_metadato_list != null) {
			for (Object comunicazioneSdi_metadato_object: comunicazioneSdi_metadato_list) {
				Metadato comunicazioneSdi_metadato = (Metadato) comunicazioneSdi_metadato_object;


				comunicazioneSdi.addMetadato(comunicazioneSdi_metadato);
			}
		}

		
        return comunicazioneSdi;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsComunicazioneSdi = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()));
		sqlQueryObject.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists comunicazioneSdi
		existsComunicazioneSdi = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsComunicazioneSdi;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{
	
		if(expression.inUseModel(ComunicazioneSdi.model().METADATO,false)){
			String tableName1 = this.getComunicazioneSdiFieldConverter().toAliasTable(ComunicazioneSdi.model());
			String tableName2 = this.getComunicazioneSdiFieldConverter().toAliasTable(ComunicazioneSdi.model().METADATO);
			sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_comunicazione_sdi");
		}
        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdComunicazioneSdi(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		ComunicazioneSdiFieldConverter converter = this.getComunicazioneSdiFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// ComunicazioneSdi.model()
		mapTableToPKColumn.put(converter.toTable(ComunicazioneSdi.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(ComunicazioneSdi.model()))
			));

		// ComunicazioneSdi.model().METADATO
		mapTableToPKColumn.put(converter.toTable(ComunicazioneSdi.model().METADATO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(ComunicazioneSdi.model().METADATO))
			));


        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getComunicazioneSdiFieldConverter(), ComunicazioneSdi.model(), objectIdClass, listaQuery);
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
	public IdComunicazione findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _comunicazioneSdi
		sqlQueryObjectGet.addFromTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()));
		sqlQueryObjectGet.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObjectGet.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().PROGRESSIVO,true));
		sqlQueryObjectGet.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().TIPO_COMUNICAZIONE,true));
		//...
		//sqlQueryObjectGet.addSelectField(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().NOME_COLONNA_N,true));
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _comunicazioneSdi
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_comunicazioneSdi = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_comunicazioneSdi = new ArrayList<Class<?>>();
		listaFieldIdReturnType_comunicazioneSdi.add(ComunicazioneSdi.model().IDENTIFICATIVO_SDI.getFieldType());
		listaFieldIdReturnType_comunicazioneSdi.add(ComunicazioneSdi.model().PROGRESSIVO.getFieldType());
		listaFieldIdReturnType_comunicazioneSdi.add(ComunicazioneSdi.model().TIPO_COMUNICAZIONE.getFieldType());
		//...
		//listaFieldIdReturnType_comunicazioneSdi.add(IdN.class);
		org.govmix.proxy.fatturapa.orm.IdComunicazione id_comunicazioneSdi = null;
		List<Object> listaFieldId_comunicazioneSdi = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_comunicazioneSdi, searchParams_comunicazioneSdi);
		if(listaFieldId_comunicazioneSdi==null || listaFieldId_comunicazioneSdi.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			// set _comunicazioneSdi
			id_comunicazioneSdi = new org.govmix.proxy.fatturapa.orm.IdComunicazione();
			id_comunicazioneSdi.setIdentificativoSdi((Integer)listaFieldId_comunicazioneSdi.get(0));
			id_comunicazioneSdi.setProgressivo((Integer)listaFieldId_comunicazioneSdi.get(1));
			id_comunicazioneSdi.setTipoComunicazione(TipoComunicazioneType.toEnumConstant((String)listaFieldId_comunicazioneSdi.get(2)));
		}
		
		return id_comunicazioneSdi;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdComunicazioneSdi(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	protected Long findIdComunicazioneSdi(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdComunicazione id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _comunicazioneSdi
		sqlQueryObjectGet.addFromTable(this.getComunicazioneSdiFieldConverter().toTable(ComunicazioneSdi.model()));
		sqlQueryObjectGet.addSelectField("id");
		// Devono essere mappati nella where condition i metodi dell'oggetto id.getXXX
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().IDENTIFICATIVO_SDI,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().PROGRESSIVO,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(this.getComunicazioneSdiFieldConverter().toColumn(ComunicazioneSdi.model().TIPO_COMUNICAZIONE,true)+"=?");

		// Recupero _comunicazioneSdi
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_comunicazioneSdi = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdentificativoSdi(),ComunicazioneSdi.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdentificativoSdi(),ComunicazioneSdi.model().PROGRESSIVO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdentificativoSdi(),ComunicazioneSdi.model().TIPO_COMUNICAZIONE.getFieldType())
		};
		Long id_comunicazioneSdi = null;
		try{
			id_comunicazioneSdi = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_comunicazioneSdi);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_comunicazioneSdi==null || id_comunicazioneSdi<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_comunicazioneSdi;
	}
}
