/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.PccTracciaFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.PccTracciaFetch;
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
 * JDBCPccTracciaServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCPccTracciaServiceSearchImpl implements IJDBCServiceSearchWithId<PccTraccia, IdTraccia, JDBCServiceManager> {

	private PccTracciaFieldConverter _pccTracciaFieldConverter = null;
	public PccTracciaFieldConverter getPccTracciaFieldConverter() {
		if(this._pccTracciaFieldConverter==null){
			this._pccTracciaFieldConverter = new PccTracciaFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._pccTracciaFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getPccTracciaFieldConverter();
	}

	private PccTracciaFetch pccTracciaFetch = new PccTracciaFetch();
	public PccTracciaFetch getPccTracciaFetch() {
		return this.pccTracciaFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getPccTracciaFetch();
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
	public IdTraccia convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTraccia pccTraccia) throws NotImplementedException, ServiceException, Exception{

		IdTraccia idPccTraccia = new IdTraccia();
		idPccTraccia.setIdTraccia(pccTraccia.getId());

		return idPccTraccia;
	}

	@Override
	public PccTraccia get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_pccTraccia = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdPccTraccia(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_pccTraccia,idMappingResolutionBehaviour);


	}

	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_pccTraccia = this.findIdPccTraccia(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_pccTraccia != null && id_pccTraccia > 0;

	}

	@Override
	public List<IdTraccia> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<IdTraccia> list = new ArrayList<IdTraccia>();

		try{
			List<IField> fields = new ArrayList<IField>();
			fields.add(new CustomField("id", Long.class, "id", this.getPccTracciaFieldConverter().toTable(PccTraccia.model())));


			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

			for(Map<String, Object> map: returnMap) {
				IdTraccia idUo = new IdTraccia();
				idUo.setIdTraccia((Long) map.get("id"));
				list.add(idUo);
			}
		} catch(NotFoundException e) {}

		return list;

	}

	@Override
	public List<PccTraccia> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<PccTraccia> list = new ArrayList<PccTraccia>();

		try{
			List<IField> fields = new ArrayList<IField>();
			fields.add(new CustomField("id", Long.class, "id", this.getPccTracciaFieldConverter().toTable(PccTraccia.model())));
			fields.add(PccTraccia.model().CF_TRASMITTENTE);
			fields.add(PccTraccia.model().DATA_CREAZIONE);
			fields.add(PccTraccia.model().VERSIONE_APPLICATIVA);
			fields.add(PccTraccia.model().ID_PCC_AMMINISTRAZIONE);
			fields.add(PccTraccia.model().ID_PA_TRANSAZIONE);
			fields.add(PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE);
			fields.add(PccTraccia.model().SISTEMA_RICHIEDENTE);
			fields.add(PccTraccia.model().UTENTE_RICHIEDENTE);
			fields.add(PccTraccia.model().ID_FATTURA);
			fields.add(PccTraccia.model().CODICE_DIPARTIMENTO);
			fields.add(PccTraccia.model().RICHIESTA_XML);
			fields.add(PccTraccia.model().RISPOSTA_XML);
			fields.add(PccTraccia.model().OPERAZIONE);
			fields.add(PccTraccia.model().TIPO_OPERAZIONE);
			fields.add(PccTraccia.model().STATO);
			fields.add(PccTraccia.model().DATA_ULTIMA_TRASMISSIONE);
			fields.add(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO);
			fields.add(PccTraccia.model().CODICI_ERRORE);
			fields.add(PccTraccia.model().RISPEDIZIONE);
			fields.add(PccTraccia.model().RISPEDIZIONE_DOPO_QUERY);
			fields.add(PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI);
			fields.add(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO);
			fields.add(PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI);
			fields.add(PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.FORMATO_TRASMISSIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.IDENTIFICATIVO_SDI);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_RICEZIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.NOME_FILE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.MESSAGE_ID);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_PAESE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_CODICE_FISCALE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_DENOMINAZIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_PAESE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_CODICE_FISCALE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.POSIZIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CODICE_DESTINATARIO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TIPO_DOCUMENTO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DIVISA);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.ANNO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.NUMERO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.ESITO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.IMPORTO_TOTALE_DOCUMENTO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.IMPORTO_TOTALE_RIEPILOGO);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CAUSALE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.STATO_CONSEGNA);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_CONSEGNA);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_PROSSIMA_CONSEGNA);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TENTATIVI_CONSEGNA);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DETTAGLIO_CONSEGNA);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.STATO_PROTOCOLLAZIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_PROTOCOLLAZIONE);
//			fields.add(PccTraccia.model().FATTURA_ELETTRONICA.PROTOCOLLO);


			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

			for(Map<String, Object> map: returnMap) {
				PccTraccia traccia = (PccTraccia)this.getPccTracciaFetch().fetch(jdbcProperties.getDatabase(), PccTraccia.model(), map);
				if(traccia.getIdFattura() > 0)
					traccia.setFatturaElettronica(((IDBFatturaElettronicaServiceSearch)this.getServiceManager().getFatturaElettronicaServiceSearch()).get(traccia.getIdFattura()));
				list.add(traccia);
			}
		} catch(NotFoundException e) {}
		return list;

	}

	@Override
	public PccTraccia find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
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
				this.getPccTracciaFieldConverter(), PccTraccia.model());

		sqlQueryObject.addSelectCountField(this.getPccTracciaFieldConverter().toTable(PccTraccia.model())+".id","tot",true);

		_join(expression,sqlQueryObject);

		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
				this.getPccTracciaFieldConverter(), PccTraccia.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id) throws NotFoundException, NotImplementedException, ServiceException,Exception {

		Long id_pccTraccia = this.findIdPccTraccia(jdbcProperties, log, connection, sqlQueryObject, id, true);
		return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_pccTraccia);

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
							this.getPccTracciaFieldConverter(), field);

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
				expression, this.getPccTracciaFieldConverter(), PccTraccia.model(), 
				listaQuery,listaParams);

		_join(expression,sqlQueryObject);

		List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
				org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
				expression, this.getPccTracciaFieldConverter(), PccTraccia.model(),
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
				this.getPccTracciaFieldConverter(), PccTraccia.model(), 
				sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);

		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}

		List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter(), PccTraccia.model(), 
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
				this.getPccTracciaFieldConverter(), PccTraccia.model(), 
				sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);

		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}

		NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
				this.getPccTracciaFieldConverter(), PccTraccia.model(), 
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
			return new JDBCExpression(this.getPccTracciaFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getPccTracciaFieldConverter());
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
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id, PccTraccia obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}

	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, PccTraccia obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, PccTraccia obj, PccTraccia imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());

	}

	@Override
	public PccTraccia get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}

	private PccTraccia _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		PccTraccia pccTraccia = new PccTraccia();


		// Object pccTraccia
		ISQLQueryObject sqlQueryObjectGet_pccTraccia = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_pccTraccia.setANDLogicOperator(true);
		sqlQueryObjectGet_pccTraccia.addFromTable(this.getPccTracciaFieldConverter().toTable(PccTraccia.model()));
		sqlQueryObjectGet_pccTraccia.addSelectField("id");
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_CREAZIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CF_TRASMITTENTE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().VERSIONE_APPLICATIVA,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PCC_AMMINISTRAZIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PA_TRANSAZIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_PA_TRANSAZIONE_RISPEDIZIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().SISTEMA_RICHIEDENTE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().UTENTE_RICHIEDENTE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().ID_FATTURA,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CODICE_DIPARTIMENTO,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RICHIESTA_XML,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPOSTA_XML,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().OPERAZIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().TIPO_OPERAZIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().STATO,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_ULTIMA_TRASMISSIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CODICI_ERRORE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_DOPO_QUERY,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI,true));
		sqlQueryObjectGet_pccTraccia.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO,true));
		sqlQueryObjectGet_pccTraccia.addWhereCondition("id=?");

		// Get pccTraccia
		pccTraccia = (PccTraccia) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_pccTraccia.createSQLQuery(), jdbcProperties.isShowSql(), PccTraccia.model(), this.getPccTracciaFetch(),
				new JDBCObject(tableId,Long.class));

		return pccTraccia;  

	} 

	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}

	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		boolean existsPccTraccia = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getPccTracciaFieldConverter().toTable(PccTraccia.model()));
		sqlQueryObject.addSelectField(this.getPccTracciaFieldConverter().toColumn(PccTraccia.model().CF_TRASMITTENTE,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists pccTraccia
		existsPccTraccia = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
				new JDBCObject(tableId,Long.class));


		return existsPccTraccia;

	}

	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{

		if(expression.inUseModel(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE,false)){
			String tableName1 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model());
			String tableName2 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE);
			sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_pcc_traccia");
		}

		if(expression.inUseModel(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO,false)){
			if(!expression.inUseModel(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE,false)){
				String tableName1 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model());
				String tableName2 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE);
				sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_pcc_traccia");
				sqlQueryObject.addFromTable(tableName2);
			}

			String tableName1 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE);
			String tableName2 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO);
			sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_pcc_traccia_trasmissione");
		}

		if(expression.inUseModel(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE,false)){
			if(!expression.inUseModel(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE,false)){
				String tableName1 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model());
				String tableName2 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE);
				sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_pcc_traccia");
				sqlQueryObject.addFromTable(tableName2);
			}

			if(!expression.inUseModel(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO,false)){
				String tableName1 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE);
				String tableName2 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO);
				sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_pcc_traccia_trasmissione");
				sqlQueryObject.addFromTable(tableName2);
			}

			String tableName1 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO);
			String tableName2 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().PCC_TRACCIA_TRASMISSIONE.PCC_TRACCIA_TRASMISSIONE_ESITO.PCC_ERRORE_ELABORAZIONE);
			sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_esito");
		}

		if(expression.inUseModel(PccTraccia.model().FATTURA_ELETTRONICA,false)){
			String tableName1 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model());
			String tableName2 = this.getPccTracciaFieldConverter().toAliasTable(PccTraccia.model().FATTURA_ELETTRONICA);
			sqlQueryObject.addWhereCondition(tableName1+".id_fattura="+tableName2+".id");
		}


	}

	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
		// Identificativi
		java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdPccTraccia(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);

		return rootTableIdValues;
	}

	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{

		PccTracciaFieldConverter converter = this.getPccTracciaFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// PccTraccia.model()
		mapTableToPKColumn.put(converter.toTable(PccTraccia.model()),
				utilities.newList(
						new CustomField("id", Long.class, "id", converter.toTable(PccTraccia.model()))
						));


		return mapTableToPKColumn;		
	}

	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {

		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getPccTracciaFieldConverter().toTable(PccTraccia.model())+".id");
		Class<?> objectIdClass = Long.class;

		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
				this.getPccTracciaFieldConverter(), PccTraccia.model());

		_join(paginatedExpression,sqlQueryObject);

		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
				this.getPccTracciaFieldConverter(), PccTraccia.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

		return list;

	}

	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getPccTracciaFieldConverter().toTable(PccTraccia.model())+".id");
		Class<?> objectIdClass = Long.class;

		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
				this.getPccTracciaFieldConverter(), PccTraccia.model());

		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
				this.getPccTracciaFieldConverter(), PccTraccia.model(), objectIdClass, listaQuery);
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
	public IdTraccia findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _pccTraccia
		sqlQueryObjectGet.addFromTable(this.getPccTracciaFieldConverter().toTable(PccTraccia.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _pccTraccia
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_pccTraccia = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_pccTraccia = new ArrayList<Class<?>>();
		listaFieldIdReturnType_pccTraccia.add(Long.class);

		org.govmix.proxy.fatturapa.orm.IdTraccia id_pccTraccia = null;
		List<Object> listaFieldId_pccTraccia = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_pccTraccia, searchParams_pccTraccia);
		if(listaFieldId_pccTraccia==null || listaFieldId_pccTraccia.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			// set _pccTraccia
			id_pccTraccia = new org.govmix.proxy.fatturapa.orm.IdTraccia();
			id_pccTraccia.setIdTraccia((Long)listaFieldId_pccTraccia.get(0));
		}

		return id_pccTraccia;

	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {

		return this.findIdPccTraccia(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);

	}

	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
			String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{

		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
				sql,returnClassTypes,param);

	}

	protected Long findIdPccTraccia(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdTraccia id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _pccTraccia
		sqlQueryObjectGet.addFromTable(this.getPccTracciaFieldConverter().toTable(PccTraccia.model()));
		sqlQueryObjectGet.addSelectField("id");
		// Devono essere mappati nella where condition i metodi dell'oggetto id.getXXX
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _pccTraccia
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_pccTraccia = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdTraccia(),Long.class),
		};
		Long id_pccTraccia = null;
		try{
			id_pccTraccia = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
					Long.class, searchParams_pccTraccia);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_pccTraccia==null || id_pccTraccia<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}

		return id_pccTraccia;
	}
}
