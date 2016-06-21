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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.DipartimentoFieldConverter;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class JDBCExtendedLottoFattureServiceSearchImpl extends
JDBCLottoFattureServiceSearchImpl implements IExtendedJDBCLottoFattureServiceSearch {

	public ISQLQueryObject getSQLQueryObjectForFindFatturePush(
			ISQLQueryObject sqlQueryObject) throws ExpressionException,
			SQLQueryObjectException {
		DipartimentoFieldConverter dipartimentoFieldConverter = new DipartimentoFieldConverter(this.getLottoFattureFieldConverter().getDatabaseType());

		ISQLQueryObject sqlQueryObjectCnt = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectCnt.addFromTable(this.getLottoFattureFieldConverter().toTable(LottoFatture.model()));
		sqlQueryObjectCnt.addFromTable(dipartimentoFieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().CODICE, true) + "=" + this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().CODICE_DESTINATARIO, true));
		
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().MODALITA_PUSH, true) + "= ?");
		sqlQueryObjectCnt.addWhereCondition(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_RICEZIONE, true) + " <= ?");
		sqlQueryObjectCnt.addWhereCondition(false, this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_CONSEGNA, true) + " <= ?", this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_CONSEGNA, true) + " IS NULL");
		sqlQueryObjectCnt.addWhereCondition(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_INSERIMENTO, true) + " = ?");
		sqlQueryObjectCnt.addWhereCondition(false, this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_CONSEGNA, true) + " =?", this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().STATO_CONSEGNA, true) + " =?");
		sqlQueryObjectCnt.setANDLogicOperator(true);
		return sqlQueryObjectCnt;
	}

	public JDBCObject[] getParamsFatturePush(Date date) {
		JDBCObject[] params = new JDBCObject[] {
				new JDBCObject(true, Dipartimento.model().MODALITA_PUSH.getFieldType()), 
				new JDBCObject(date, LottoFatture.model().DATA_RICEZIONE.getFieldType()), 
				new JDBCObject(date, LottoFatture.model().DATA_CONSEGNA.getFieldType()),
				new JDBCObject(StatoInserimentoType.INSERITO, LottoFatture.model().STATO_INSERIMENTO.getFieldType()), 
				new JDBCObject(StatoConsegnaType.NON_CONSEGNATA, LottoFatture.model().STATO_CONSEGNA.getFieldType()),
				new JDBCObject(StatoConsegnaType.ERRORE_CONSEGNA, LottoFatture.model().STATO_CONSEGNA.getFieldType())};
		return params;
	}

	@Override
	public long countLottiPush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date) throws NotImplementedException, ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		ISQLQueryObject sqlQueryObjectCnt = getSQLQueryObjectForFindFatturePush(sqlQueryObject);

		sqlQueryObjectCnt.addSelectCountField("id");


		JDBCObject[] params = getParamsFatturePush(date);

		return jdbcUtilities.count(sqlQueryObjectCnt.createSQLQuery(), jdbcProperties.isShowSql(), params);
	}

	@Override
	public List<LottoFatture> findAllLottiPush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception {

		List<LottoFatture> list = new ArrayList<LottoFatture>();

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		ISQLQueryObject sqlQueryObjectFind = getSQLQueryObjectForFindFatturePush(sqlQueryObject);

		List<IField> fields= new ArrayList<IField>();
		fields.add(LottoFatture.model().FORMATO_TRASMISSIONE);
		fields.add(LottoFatture.model().IDENTIFICATIVO_SDI);
		fields.add(LottoFatture.model().NOME_FILE);
		fields.add(LottoFatture.model().MESSAGE_ID);
		fields.add(LottoFatture.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
		fields.add(LottoFatture.model().CEDENTE_PRESTATORE_NOME);
		fields.add(LottoFatture.model().CEDENTE_PRESTATORE_COGNOME);
		fields.add(LottoFatture.model().CEDENTE_PRESTATORE_CODICE);
		fields.add(LottoFatture.model().CEDENTE_PRESTATORE_PAESE);
		fields.add(LottoFatture.model().CEDENTE_PRESTATORE_CODICE_FISCALE);
		fields.add(LottoFatture.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE);
		fields.add(LottoFatture.model().CESSIONARIO_COMMITTENTE_NOME);
		fields.add(LottoFatture.model().CESSIONARIO_COMMITTENTE_COGNOME);
		fields.add(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE);
		fields.add(LottoFatture.model().CESSIONARIO_COMMITTENTE_PAESE);
		fields.add(LottoFatture.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE);
		fields.add(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE);
		fields.add(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME);
		fields.add(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME);
		fields.add(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE);
		fields.add(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE);
		fields.add(LottoFatture.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE);
		fields.add(LottoFatture.model().CODICE_DESTINATARIO);
		fields.add(LottoFatture.model().XML);
		fields.add(LottoFatture.model().DATA_RICEZIONE);
		fields.add(LottoFatture.model().STATO_INSERIMENTO);
		fields.add(LottoFatture.model().STATO_CONSEGNA);

		for(IField field : fields) {
			sqlQueryObjectFind.addSelectField(this.getLottoFattureFieldConverter().toColumn(field, true));
		}
		
		sqlQueryObjectFind.addOrderBy(this.getLottoFattureFieldConverter().toColumn(LottoFatture.model().DATA_RICEZIONE, true));
		
		sqlQueryObjectFind.setOffset(offset);
		sqlQueryObjectFind.setLimit(limit);


		JDBCObject[] params = getParamsFatturePush(date);
		List<Class<?>> lstReturnType = new ArrayList<Class<?>>();
		
		for(IField field : fields) {
			lstReturnType.add(field.getFieldType());
		}
		
		List<List<Object>> lstObj = jdbcUtilities.executeQuery(sqlQueryObjectFind.createSQLQuery(), jdbcProperties.isShowSql(), lstReturnType, params);

		for(List<Object> lstFields: lstObj) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			for(int i = 0;i < fields.size();i++) {
				map.put(fields.get(i).getFieldName(), lstFields.get(i));
			}
			
			LottoFatture fattura = (LottoFatture) this.getLottoFattureFetch().fetch(jdbcProperties.getDatabase(), LottoFatture.model(), map);
			list.add(fattura);
		}

		return list;      

	}
	
}
