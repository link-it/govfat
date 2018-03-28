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
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.Metadato;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.TracciaSDIFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.TracciaSDIFetch;
import org.openspcoop2.generic_project.beans.AliasField;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.FunctionField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.InUse;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.Union;
import org.openspcoop2.generic_project.beans.UnionExpression;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithoutId;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.IJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.utils.UtilsTemplate;
import org.openspcoop2.utils.sql.ISQLQueryObject;

/**     
 * JDBCTracciaSDIServiceSearchImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCTracciaSDIServiceSearchImpl implements IJDBCServiceSearchWithoutId<TracciaSDI, JDBCServiceManager> {

	private TracciaSDIFieldConverter _tracciaSDIFieldConverter = null;
	public TracciaSDIFieldConverter getTracciaSDIFieldConverter() {
		if(this._tracciaSDIFieldConverter==null){
			this._tracciaSDIFieldConverter = new TracciaSDIFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._tracciaSDIFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getTracciaSDIFieldConverter();
	}

	private TracciaSDIFetch tracciaSDIFetch = new TracciaSDIFetch();
	public TracciaSDIFetch getTracciaSDIFetch() {
		return this.tracciaSDIFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getTracciaSDIFetch();
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
	public List<TracciaSDI> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<TracciaSDI> list = new ArrayList<TracciaSDI>();

		try {
			List<IField> fields = new ArrayList<IField>();

			String id = "id";
			fields.add(new CustomField(id, Long.class, id, this.getFieldConverter().toTable(TracciaSDI.model())));
			fields.add(TracciaSDI.model().IDENTIFICATIVO_SDI);
			fields.add(TracciaSDI.model().POSIZIONE);
			fields.add(TracciaSDI.model().TIPO_COMUNICAZIONE);
			fields.add(TracciaSDI.model().NOME_FILE);
			fields.add(TracciaSDI.model().DATA);
			fields.add(TracciaSDI.model().ID_EGOV);
			fields.add(TracciaSDI.model().CONTENT_TYPE);
			fields.add(TracciaSDI.model().RAW_DATA);
			fields.add(TracciaSDI.model().STATO_PROTOCOLLAZIONE);
			fields.add(TracciaSDI.model().DATA_PROTOCOLLAZIONE);
			fields.add(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE);
			fields.add(TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE);
			fields.add(TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE);

			String lottoTable = "LottoFatture";
			String lottoId = lottoTable + ".id";
			fields.add(new AliasField(new CustomField(lottoId, Long.class, "id", this.getFieldConverter().toTable(TracciaSDI.model().LOTTO_FATTURE)), "l_id"));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.FORMATO_TRASMISSIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.NOME_FILE, lottoTable));
			fields.add(new AliasField(TracciaSDI.model().LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA, "l_formatoArchivio"));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.MESSAGE_ID, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.CODICE_DESTINATARIO, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.XML, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.DATA_RICEZIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.STATO_INSERIMENTO, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.STATO_CONSEGNA, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.DATA_CONSEGNA, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.STATO_PROTOCOLLAZIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.PROTOCOLLO, lottoTable));
			fields.add(getCustomField(TracciaSDI.model().LOTTO_FATTURE.ID_EGOV, lottoTable));


			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

			for(Map<String, Object> map: returnMap) {
				TracciaSDI tracciaSdI = (TracciaSDI)this.getTracciaSDIFetch().fetch(jdbcProperties.getDatabase(), TracciaSDI.model(), map);
				LottoFatture lottoFatture = (LottoFatture)this.getTracciaSDIFetch().fetch(jdbcProperties.getDatabase(), TracciaSDI.model().LOTTO_FATTURE, map);
				tracciaSdI.setLottoFatture(lottoFatture);
				

				// Object tracciaSDI_metadato
				ISQLQueryObject sqlQueryObjectGet_tracciaSDI_metadato = sqlQueryObject.newSQLQueryObject();
				sqlQueryObjectGet_tracciaSDI_metadato.setANDLogicOperator(true);
				sqlQueryObjectGet_tracciaSDI_metadato.addFromTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model().METADATO));
				sqlQueryObjectGet_tracciaSDI_metadato.addSelectField("id");
				sqlQueryObjectGet_tracciaSDI_metadato.addSelectField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.RICHIESTA,true));
				sqlQueryObjectGet_tracciaSDI_metadato.addSelectField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.NOME,true));
				sqlQueryObjectGet_tracciaSDI_metadato.addSelectField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().METADATO.VALORE,true));
				sqlQueryObjectGet_tracciaSDI_metadato.addWhereCondition("id_traccia_sdi=?");

				// Get tracciaSDI_metadato
				java.util.List<Object> tracciaSDI_metadato_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_tracciaSDI_metadato.createSQLQuery(), jdbcProperties.isShowSql(), TracciaSDI.model().METADATO, this.getTracciaSDIFetch(),
						new JDBCObject(tracciaSdI.getId(),Long.class));

				if(tracciaSDI_metadato_list != null) {
					for (Object tracciaSDI_metadato_object: tracciaSDI_metadato_list) {
						Metadato tracciaSDI_metadato = (Metadato) tracciaSDI_metadato_object;


						tracciaSdI.addMetadato(tracciaSDI_metadato);
					}
				}

				
				list.add(tracciaSdI);
			}

		} catch(NotFoundException e) {}
		return list;      

	}

	private IField getCustomField(IField field, String table) throws ExpressionException {
		
		String columnName = this.getFieldConverter().toColumn(field, false);
		String aliasTableName = table.substring(0,  1);
		String aliasColumnName = aliasTableName + columnName;
		
		return new AliasField(field, aliasColumnName);
	}
	
	@Override
	public TracciaSDI find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
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
				this.getTracciaSDIFieldConverter(), TracciaSDI.model());

		sqlQueryObject.addSelectCountField(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model())+".id","tot",true);

		_join(expression,sqlQueryObject);

		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
				this.getTracciaSDIFieldConverter(), TracciaSDI.model(),listaQuery);
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
							this.getTracciaSDIFieldConverter(), field);

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
				expression, this.getTracciaSDIFieldConverter(), TracciaSDI.model(), 
				listaQuery,listaParams);

		_join(expression,sqlQueryObject);

		List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
				org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
				expression, this.getTracciaSDIFieldConverter(), TracciaSDI.model(),
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
				this.getTracciaSDIFieldConverter(), TracciaSDI.model(), 
				sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);

		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}

		List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter(), TracciaSDI.model(), 
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
				this.getTracciaSDIFieldConverter(), TracciaSDI.model(), 
				sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);

		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}

		NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
				this.getTracciaSDIFieldConverter(), TracciaSDI.model(), 
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
			return new JDBCExpression(this.getTracciaSDIFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getTracciaSDIFieldConverter());
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
	public TracciaSDI get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}

	private TracciaSDI _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {

		JDBCPaginatedExpression expression = this.newPaginatedExpression(log);
		CustomField idField = new CustomField("id", Long.class, "id", this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()));
		expression.equals(idField, tableId);
		expression.offset(0);
		expression.limit(2);
		
		expression.addOrder(idField, org.openspcoop2.generic_project.expression.SortOrder.ASC);
		List<TracciaSDI> lst = this.findAll(jdbcProperties, log, connection, sqlQueryObject, expression, idMappingResolutionBehaviour);
		
		if(lst == null || lst.size() == 0) {
			throw new NotFoundException();
		} else if(lst.size() > 1) {
			throw new MultipleResultException();
		} else {
			return lst.get(0);
		}

	} 

	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}

	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		boolean existsTracciaSDI = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model()));
		sqlQueryObject.addSelectField(this.getTracciaSDIFieldConverter().toColumn(TracciaSDI.model().IDENTIFICATIVO_SDI,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists tracciaSDI
		existsTracciaSDI = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
				new JDBCObject(tableId,Long.class));


		return existsTracciaSDI;

	}

	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{

		if(expression.inUseModel(TracciaSDI.model().METADATO,false)){
			String tableName1 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model());
			String tableName2 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model().METADATO);
			sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_traccia_sdi");
		}

		if(expression.inUseModel(TracciaSDI.model().LOTTO_FATTURE,false)){
			String tableName1 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model());
			String tableName2 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model().LOTTO_FATTURE);
			sqlQueryObject.addWhereCondition(tableName1+".identificativo_sdi="+tableName2+".identificativo_sdi");
		}

		if(expression.inUseModel(TracciaSDI.model().LOTTO_FATTURE.DIPARTIMENTO,false)){

			if(!expression.inUseModel(TracciaSDI.model().LOTTO_FATTURE,false)){
				String tableName1 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model());
				String tableName2 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model().LOTTO_FATTURE);
				sqlQueryObject.addWhereCondition(tableName1+".identificativo_sdi="+tableName2+".identificativo_sdi");
				sqlQueryObject.addFromTable(tableName2);
			}

			String tableName1 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model().LOTTO_FATTURE);
			String tableName2 = this.getTracciaSDIFieldConverter().toAliasTable(TracciaSDI.model().LOTTO_FATTURE.DIPARTIMENTO);
			sqlQueryObject.addWhereCondition(tableName1+".codice_destinatario="+tableName2+".codice");
		}


	}

	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, TracciaSDI tracciaSDI) throws NotFoundException, ServiceException, NotImplementedException, Exception{
		// Identificativi
		java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		rootTableIdValues.add(tracciaSDI.getId());

		return rootTableIdValues;
	}

	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{

		TracciaSDIFieldConverter converter = this.getTracciaSDIFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		// TracciaSDI.model()
		mapTableToPKColumn.put(converter.toTable(TracciaSDI.model()),
				utilities.newList(
						new CustomField("id", Long.class, "id", converter.toTable(TracciaSDI.model()))
						));

		// TracciaSDI.model().METADATO
		mapTableToPKColumn.put(converter.toTable(TracciaSDI.model().METADATO),
				utilities.newList(
						new CustomField("id", Long.class, "id", converter.toTable(TracciaSDI.model().METADATO))
						));


		return mapTableToPKColumn;		
	}

	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {

		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model())+".id");
		Class<?> objectIdClass = Long.class;

		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
				this.getTracciaSDIFieldConverter(), TracciaSDI.model());

		_join(paginatedExpression,sqlQueryObject);

		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
				this.getTracciaSDIFieldConverter(), TracciaSDI.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

		return list;

	}

	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getTracciaSDIFieldConverter().toTable(TracciaSDI.model())+".id");
		Class<?> objectIdClass = Long.class;

		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
				this.getTracciaSDIFieldConverter(), TracciaSDI.model());

		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
				this.getTracciaSDIFieldConverter(), TracciaSDI.model(), objectIdClass, listaQuery);
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
