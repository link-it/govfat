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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.UtenteFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.UtenteFetch;
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
 * JDBCUtenteServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCUtenteServiceSearchImpl implements IJDBCUtenteServiceSearch {

	private UtenteFieldConverter _utenteFieldConverter = null;
	public UtenteFieldConverter getUtenteFieldConverter() {
		if(this._utenteFieldConverter==null){
			this._utenteFieldConverter = new UtenteFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._utenteFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getUtenteFieldConverter();
	}
	
	private UtenteFetch utenteFetch = new UtenteFetch();
	public UtenteFetch getUtenteFetch() {
		return this.utenteFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getUtenteFetch();
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
	public IdUtente convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Utente utente) throws NotImplementedException, ServiceException, Exception{
        IdUtente idUtente = new IdUtente();
        idUtente.setUsername(utente.getUsername());

        return idUtente;
	}
	
	@Override
	public Utente get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_utente = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdUtente(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_utente,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_utente = this.findIdUtente(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_utente != null && id_utente > 0;
		
	}
	
	@Override
	public List<IdUtente> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<IdUtente> list = new ArrayList<IdUtente>();
        try {
                List<IField> fields = new ArrayList<IField>();

                fields.add(Utente.model().USERNAME);

                List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

                for(Map<String, Object> map: returnMap) {
                        list.add(this.convertToId(jdbcProperties, log, connection, sqlQueryObject, (Utente)this.getUtenteFetch().fetch(jdbcProperties.getDatabase(), Utente.model(), map)));
                }
        } catch(NotFoundException e){}


        return list;

		
	}
	
    @Override
    public List<Utente> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {
            return findAll(jdbcProperties, log, connection, sqlQueryObject, expression,true, idMappingResolutionBehaviour);
    }

    @Override
    public List<Utente> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, boolean caricaDipartimenti) throws NotImplementedException, ServiceException,Exception {
        return findAll(jdbcProperties, log, connection, sqlQueryObject, expression,true, null);
    }

    @Override
    public List<Utente> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, boolean caricaDipartimenti, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<Utente> list = new ArrayList<Utente>();

        try {
                List<IField> fields = new ArrayList<IField>();

                String id = "id";
                fields.add(new CustomField(id, Long.class, id, this.getUtenteFieldConverter().toTable(Utente.model())));
                fields.add(Utente.model().USERNAME);
                fields.add(Utente.model().PASSWORD);
                fields.add(new AliasField(Utente.model().NOME, "nomeUtente"));
                fields.add(Utente.model().COGNOME);
                fields.add(Utente.model().ROLE);
                fields.add(Utente.model().TIPO);
                fields.add(Utente.model().ESTERNO);
                fields.add(Utente.model().SISTEMA);

                List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

                for(Map<String, Object> map: returnMap) {

                        Utente utente = (Utente)this.getUtenteFetch().fetch(jdbcProperties.getDatabase(), Utente.model(), map);
                        if(caricaDipartimenti) {
                                try {
                                        JDBCPaginatedExpression exprDipartimenti = this.newPaginatedExpression(log);

                                        exprDipartimenti.equals(Utente.model().USERNAME, utente.getUsername());
                                        ISQLQueryObject sqlQueryObjectDip = sqlQueryObject.newSQLQueryObject();
                                        sqlQueryObjectDip.setANDLogicOperator(true);

                                        List<Object> returnMapDipartimenti = this.select(jdbcProperties, log, connection, sqlQueryObjectDip, exprDipartimenti, Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE);

                                        for(Object codiceObj: returnMapDipartimenti) {
                                                String codice = (String) codiceObj;
                                                UtenteDipartimento utenteDipartimento = new UtenteDipartimento();
                                                IdDipartimento idDipartimento = new IdDipartimento();
                                                idDipartimento.setCodice(codice);
                                                utenteDipartimento.setIdDipartimento(idDipartimento);
                                        }
                                } catch(NotFoundException e) {}
                        }

                        list.add(utente);
                }
        } catch(NotFoundException e) {}


        return list;

	}
	
	@Override
	public Utente find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
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
												this.getUtenteFieldConverter(), Utente.model());
		
		sqlQueryObject.addSelectCountField(this.getUtenteFieldConverter().toTable(Utente.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getUtenteFieldConverter(), Utente.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_utente = this.findIdUtente(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_utente);
		
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
												this.getUtenteFieldConverter(), field);

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
        						expression, this.getUtenteFieldConverter(), Utente.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getUtenteFieldConverter(), Utente.model(),
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
        						this.getUtenteFieldConverter(), Utente.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getUtenteFieldConverter(), Utente.model(), 
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
        						this.getUtenteFieldConverter(), Utente.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getUtenteFieldConverter(), Utente.model(), 
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
			return new JDBCExpression(this.getUtenteFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getUtenteFieldConverter());
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
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id, Utente obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, Utente obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Utente obj, Utente imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getUtenteDipartimentoList()!=null){
			List<org.govmix.proxy.fatturapa.orm.UtenteDipartimento> listObj_ = obj.getUtenteDipartimentoList();
			for(org.govmix.proxy.fatturapa.orm.UtenteDipartimento itemObj_ : listObj_){
				org.govmix.proxy.fatturapa.orm.UtenteDipartimento itemAlreadySaved_ = null;
				if(imgSaved.getUtenteDipartimentoList()!=null){
					List<org.govmix.proxy.fatturapa.orm.UtenteDipartimento> listImgSaved_ = imgSaved.getUtenteDipartimentoList();
					for(org.govmix.proxy.fatturapa.orm.UtenteDipartimento itemImgSaved_ : listImgSaved_){
						boolean objEqualsToImgSaved_ = false;
						objEqualsToImgSaved_ = org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_.getIdDipartimento(),itemImgSaved_.getIdDipartimento());
						if(objEqualsToImgSaved_){
							itemAlreadySaved_=itemImgSaved_;
							break;
						}
					}
				}
				if(itemAlreadySaved_!=null){
					itemObj_.setId(itemAlreadySaved_.getId());
					if(itemObj_.getIdDipartimento()!=null && 
							itemAlreadySaved_.getIdDipartimento()!=null){
						itemObj_.getIdDipartimento().setId(itemAlreadySaved_.getIdDipartimento().getId());
					}
				}
			}
		}

	}
	
	@Override
	public Utente get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private Utente _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		Utente utente = new Utente();
		

		// Object utente
		ISQLQueryObject sqlQueryObjectGet_utente = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_utente.setANDLogicOperator(true);
		sqlQueryObjectGet_utente.addFromTable(this.getUtenteFieldConverter().toTable(Utente.model()));
		sqlQueryObjectGet_utente.addSelectField("id");
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().USERNAME,true));
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().PASSWORD,true));
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().NOME,true));
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().COGNOME,true));
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().ROLE,true));
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().TIPO,true));
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().ESTERNO,true));
		sqlQueryObjectGet_utente.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().SISTEMA,true));
		sqlQueryObjectGet_utente.addWhereCondition("id=?");

		// Get utente
		utente = (Utente) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_utente.createSQLQuery(), jdbcProperties.isShowSql(), Utente.model(), this.getUtenteFetch(),
			new JDBCObject(tableId,Long.class));




		// Object utente_utenteDipartimento
		ISQLQueryObject sqlQueryObjectGet_utente_utenteDipartimento = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_utente_utenteDipartimento.setANDLogicOperator(true);
		sqlQueryObjectGet_utente_utenteDipartimento.addFromTable(this.getUtenteFieldConverter().toTable(Utente.model().UTENTE_DIPARTIMENTO));
		sqlQueryObjectGet_utente_utenteDipartimento.addSelectField("id");
		sqlQueryObjectGet_utente_utenteDipartimento.addWhereCondition("id_utente=?");

		// Get utente_utenteDipartimento
		java.util.List<Object> utente_utenteDipartimento_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_utente_utenteDipartimento.createSQLQuery(), jdbcProperties.isShowSql(), Utente.model().UTENTE_DIPARTIMENTO, this.getUtenteFetch(),
			new JDBCObject(utente.getId(),Long.class));

		if(utente_utenteDipartimento_list != null) {
			for (Object utente_utenteDipartimento_object: utente_utenteDipartimento_list) {
				UtenteDipartimento utente_utenteDipartimento = (UtenteDipartimento) utente_utenteDipartimento_object;


				if(idMappingResolutionBehaviour==null ||
					(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
				){
					// Object _utente_utenteDipartimento_dipartimento (recupero id)
					ISQLQueryObject sqlQueryObjectGet_utente_utenteDipartimento_dipartimento_readFkId = sqlQueryObjectGet.newSQLQueryObject();
					sqlQueryObjectGet_utente_utenteDipartimento_dipartimento_readFkId.addFromTable(this.getUtenteFieldConverter().toTable(org.govmix.proxy.fatturapa.orm.Utente.model().UTENTE_DIPARTIMENTO));
					sqlQueryObjectGet_utente_utenteDipartimento_dipartimento_readFkId.addSelectField("id_dipartimento");
					sqlQueryObjectGet_utente_utenteDipartimento_dipartimento_readFkId.addWhereCondition("id=?");
					sqlQueryObjectGet_utente_utenteDipartimento_dipartimento_readFkId.setANDLogicOperator(true);
					Long idFK_utente_utenteDipartimento_dipartimento = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_utente_utenteDipartimento_dipartimento_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
							new JDBCObject(utente_utenteDipartimento.getId(),Long.class));
					
					org.govmix.proxy.fatturapa.orm.IdDipartimento id_utente_utenteDipartimento_dipartimento = null;
					if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
						id_utente_utenteDipartimento_dipartimento = ((JDBCDipartimentoServiceSearch)(this.getServiceManager().getDipartimentoServiceSearch())).findId(idFK_utente_utenteDipartimento_dipartimento, false);
					}else{
						id_utente_utenteDipartimento_dipartimento = new org.govmix.proxy.fatturapa.orm.IdDipartimento();
					}
					id_utente_utenteDipartimento_dipartimento.setId(idFK_utente_utenteDipartimento_dipartimento);
					utente_utenteDipartimento.setIdDipartimento(id_utente_utenteDipartimento_dipartimento);
				}

				utente.addUtenteDipartimento(utente_utenteDipartimento);
			}
		}

        return utente;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsUtente = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getUtenteFieldConverter().toTable(Utente.model()));
		sqlQueryObject.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().USERNAME,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists utente
		existsUtente = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsUtente;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{

		if(expression.inUseModel(Utente.model().UTENTE_DIPARTIMENTO,false)){
			String tableName1 = this.getUtenteFieldConverter().toAliasTable(Utente.model());
			String tableName2 = this.getUtenteFieldConverter().toAliasTable(Utente.model().UTENTE_DIPARTIMENTO);
			sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_utente");
		}

		if(expression.inUseModel(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO,false)){
			if(!expression.inUseModel(Utente.model().UTENTE_DIPARTIMENTO,false)){
				String tableName1 = this.getUtenteFieldConverter().toAliasTable(Utente.model());
				String tableName2 = this.getUtenteFieldConverter().toAliasTable(Utente.model().UTENTE_DIPARTIMENTO);
				sqlQueryObject.addFromTable(tableName2);
				sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_utente");
			}

			String tableName1 = this.getUtenteFieldConverter().toAliasTable(Utente.model().UTENTE_DIPARTIMENTO);
			String tableName2 = this.getUtenteFieldConverter().toAliasTable(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO);
			sqlQueryObject.addWhereCondition(tableName1+".id_dipartimento="+tableName2+".id");
		}

        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdUtente(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		UtenteFieldConverter converter = this.getUtenteFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// Utente.model()
		mapTableToPKColumn.put(converter.toTable(Utente.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Utente.model()))
			));


		// Utente.model().UTENTE_DIPARTIMENTO
		mapTableToPKColumn.put(converter.toTable(Utente.model().UTENTE_DIPARTIMENTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Utente.model().UTENTE_DIPARTIMENTO))
			));

		// Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO
		mapTableToPKColumn.put(converter.toTable(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO))
			));

        
        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getUtenteFieldConverter().toTable(Utente.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getUtenteFieldConverter(), Utente.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getUtenteFieldConverter(), Utente.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getUtenteFieldConverter().toTable(Utente.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getUtenteFieldConverter(), Utente.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getUtenteFieldConverter(), Utente.model(), objectIdClass, listaQuery);
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
	public IdUtente findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _utente
		sqlQueryObjectGet.addFromTable(this.getUtenteFieldConverter().toTable(Utente.model()));
		sqlQueryObjectGet.addSelectField(this.getUtenteFieldConverter().toColumn(Utente.model().USERNAME,true));
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _utente
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_utente = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_utente = new ArrayList<Class<?>>();
		listaFieldIdReturnType_utente.add(Utente.model().USERNAME.getFieldType());
		
		org.govmix.proxy.fatturapa.orm.IdUtente id_utente = null;
		List<Object> listaFieldId_utente = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_utente, searchParams_utente);
		if(listaFieldId_utente==null || listaFieldId_utente.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			id_utente = new org.govmix.proxy.fatturapa.orm.IdUtente();
			id_utente.setUsername((String)listaFieldId_utente.get(0));
		}
		
		return id_utente;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdUtente(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	protected Long findIdUtente(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdUtente id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _utente
		sqlQueryObjectGet.addFromTable(this.getUtenteFieldConverter().toTable(Utente.model()));
		sqlQueryObjectGet.addSelectField("id");
		// Devono essere mappati nella where condition i metodi dell'oggetto id.getXXX
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition(this.getUtenteFieldConverter().toColumn(Utente.model().USERNAME,true)+"=?");

		// Recupero _utente
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_utente = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getUsername(),Utente.model().USERNAME.getFieldType())
		};
		Long id_utente = null;
		try{
			id_utente = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_utente);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_utente==null || id_utente<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_utente;
	}
}
