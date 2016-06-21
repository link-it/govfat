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
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.DipartimentoFieldConverter;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.EnteFieldConverter;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.LottoFattureFieldConverter;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.NotificaEsitoCommittenteFieldConverter;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.UtenteFieldConverter;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class JDBCExtendedFatturaElettronicaServiceSearchImpl extends
JDBCFatturaElettronicaServiceSearchImpl implements IExtendedJDBCFatturaElettronicaServiceSearch {

	public ISQLQueryObject getSQLQueryObjectForFindFattureContestualePush(
			ISQLQueryObject sqlQueryObject) throws ExpressionException,
			SQLQueryObjectException {
		DipartimentoFieldConverter dipartimentoFieldConverter = new DipartimentoFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());
		EnteFieldConverter enteFieldConverter = new EnteFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());
		LottoFattureFieldConverter lottoFieldConverter = new LottoFattureFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());

		ISQLQueryObject sqlQueryObjectCnt = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectCnt.addFromTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectCnt.addFromTable(lottoFieldConverter.toTable(LottoFatture.model()));
		sqlQueryObjectCnt.addFromTable(dipartimentoFieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectCnt.addFromTable(enteFieldConverter.toTable(Ente.model()));
		
		sqlQueryObjectCnt.addWhereCondition(lottoFieldConverter.toColumn(LottoFatture.model().IDENTIFICATIVO_SDI, true) + "=" + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, true));
		sqlQueryObjectCnt.addWhereCondition(lottoFieldConverter.toColumn(LottoFatture.model().PROTOCOLLO, true) + " IS NOT NULL");
		sqlQueryObjectCnt.addWhereCondition(lottoFieldConverter.toColumn(LottoFatture.model().STATO_CONSEGNA, true) + " =?");

		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().CODICE, true) + "=" + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO, true));
		
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().MODALITA_PUSH, true) + "= ?");
		sqlQueryObjectCnt.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true) + " <= ?");
		sqlQueryObjectCnt.addWhereCondition(false, this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_CONSEGNA, true) + " <= ?", this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_CONSEGNA, true) + " IS NULL");
		sqlQueryObjectCnt.addWhereCondition(false, this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA, true) + " = ?", this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA, true) + " = ?");
		sqlQueryObjectCnt.setANDLogicOperator(true);
		return sqlQueryObjectCnt;
	}

	public JDBCObject[] getParamsFattureContestualePush(Date date) {
		JDBCObject[] params = new JDBCObject[] {
				new JDBCObject(StatoConsegnaType.CONSEGNATA, LottoFatture.model().STATO_CONSEGNA.getFieldType()),
				new JDBCObject(true, Dipartimento.model().MODALITA_PUSH.getFieldType()), 
				new JDBCObject(date, FatturaElettronica.model().DATA_RICEZIONE.getFieldType()), 
				new JDBCObject(date, FatturaElettronica.model().DATA_CONSEGNA.getFieldType()), 
				new JDBCObject(StatoConsegnaType.NON_CONSEGNATA, FatturaElettronica.model().STATO_CONSEGNA.getFieldType()),
				new JDBCObject(StatoConsegnaType.ERRORE_CONSEGNA, FatturaElettronica.model().STATO_CONSEGNA.getFieldType())};
		return params;
	}

	public ISQLQueryObject getSQLQueryObjectForFindFatturePush(
			ISQLQueryObject sqlQueryObject) throws ExpressionException,
			SQLQueryObjectException {
		DipartimentoFieldConverter dipartimentoFieldConverter = new DipartimentoFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());
		EnteFieldConverter enteFieldConverter = new EnteFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());

		ISQLQueryObject sqlQueryObjectCnt = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectCnt.addFromTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectCnt.addFromTable(dipartimentoFieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().CODICE, true) + "=" + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO, true));
		
		sqlQueryObjectCnt.addFromTable(enteFieldConverter.toTable(Ente.model()));
		
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().MODALITA_PUSH, true) + "= ?");
		sqlQueryObjectCnt.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true) + " <= ?");
		sqlQueryObjectCnt.addWhereCondition(false, this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_CONSEGNA, true) + " <= ?", this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_CONSEGNA, true) + " IS NULL");
		sqlQueryObjectCnt.addWhereCondition(false, this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA, true) + " =?", this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA, true) + " =?");
		sqlQueryObjectCnt.setANDLogicOperator(true);
		return sqlQueryObjectCnt;
	}

	public JDBCObject[] getParamsFatturePush(Date date) {
		JDBCObject[] params = new JDBCObject[] {
				new JDBCObject(true, Dipartimento.model().MODALITA_PUSH.getFieldType()), 
				new JDBCObject(date, FatturaElettronica.model().DATA_RICEZIONE.getFieldType()), 
				new JDBCObject(date, FatturaElettronica.model().DATA_CONSEGNA.getFieldType()), 
				new JDBCObject(StatoConsegnaType.NON_CONSEGNATA, FatturaElettronica.model().STATO_CONSEGNA.getFieldType()),
				new JDBCObject(StatoConsegnaType.ERRORE_CONSEGNA, FatturaElettronica.model().STATO_CONSEGNA.getFieldType())};
		return params;
	}
	
	@Override
	public void assegnaProtocolloAInteroLotto(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto idLotto, String protocollo) throws NotImplementedException, ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		sqlQueryObject.setANDLogicOperator(true);
		
		sqlQueryObject.addUpdateTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		
		if(protocollo != null) {
			sqlQueryObject.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().PROTOCOLLO, false), "?");
		}

		sqlQueryObject.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, false), "?");
		sqlQueryObject.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE, false), "?");
		sqlQueryObject.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false) + "=?");
		
		List<JDBCObject> params = new ArrayList<JDBCObject>();
		
		if(protocollo != null) {
			params.add(new JDBCObject(protocollo, FatturaElettronica.model().PROTOCOLLO.getFieldType()));
			params.add(new JDBCObject(StatoProtocollazioneType.PROTOCOLLATA, FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType()));
			params.add(new JDBCObject(new Date(), FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType()));
			params.add(new JDBCObject(idLotto.getIdentificativoSdi(), FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()));
		} else {
			params.add(new JDBCObject(StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE, FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType()));
			params.add(new JDBCObject(new Date(), FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType()));
			params.add(new JDBCObject(idLotto.getIdentificativoSdi(), FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()));
		}

		jdbcUtilities.execute(sqlQueryObject.createSQLUpdate(), jdbcProperties.isShowSql(), params.toArray(new JDBCObject[1]));
	}

	@Override
	public long countFatturePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date) throws NotImplementedException, ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		ISQLQueryObject sqlQueryObjectCnt = getSQLQueryObjectForFindFatturePush(sqlQueryObject);

		sqlQueryObjectCnt.addSelectCountField("id");


		JDBCObject[] params = getParamsFatturePush(date);

		return jdbcUtilities.count(sqlQueryObjectCnt.createSQLQuery(), jdbcProperties.isShowSql(), params);
	}

	@Override
	public List<FatturaElettronica> findAllFattureContestualePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception {

		List<FatturaElettronica> list = new ArrayList<FatturaElettronica>();

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		LottoFattureFieldConverter lottoFieldConverter = new LottoFattureFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());


		ISQLQueryObject sqlQueryObjectFind = getSQLQueryObjectForFindFattureContestualePush(sqlQueryObject);

		List<IField> fields= new ArrayList<IField>();
		fields.add(FatturaElettronica.model().IDENTIFICATIVO_SDI);
		fields.add(FatturaElettronica.model().POSIZIONE);
		fields.add(FatturaElettronica.model().NOME_FILE);
		fields.add(FatturaElettronica.model().CODICE_DESTINATARIO);
		fields.add(FatturaElettronica.model().STATO_CONSEGNA);
		fields.add(FatturaElettronica.model().DETTAGLIO_CONSEGNA);
		fields.add(FatturaElettronica.model().DATA);
		fields.add(FatturaElettronica.model().DATA_RICEZIONE);
		fields.add(FatturaElettronica.model().NUMERO);
		fields.add(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO);
		fields.add(FatturaElettronica.model().DIVISA);
		fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE);
		fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
		
		
		for(IField field : fields) {
			sqlQueryObjectFind.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(field, true));
		}
		
		sqlQueryObjectFind.addSelectField(lottoFieldConverter.toColumn(LottoFatture.model().PROTOCOLLO, true));
		
		sqlQueryObjectFind.addOrderBy(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true));
		
		sqlQueryObjectFind.setOffset(offset);
		sqlQueryObjectFind.setLimit(limit);


		JDBCObject[] params = getParamsFattureContestualePush(date);
		List<Class<?>> lstReturnType = new ArrayList<Class<?>>();
		
		for(IField field : fields) {
			lstReturnType.add(field.getFieldType());
		}
		
		lstReturnType.add(LottoFatture.model().PROTOCOLLO.getFieldType());

		List<List<Object>> lstObj = jdbcUtilities.executeQuery(sqlQueryObjectFind.createSQLQuery(), jdbcProperties.isShowSql(), lstReturnType, params);

		for(List<Object> lstFields: lstObj) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			for(int i = 0;i < fields.size();i++) {
				map.put(fields.get(i).getFieldName(), lstFields.get(i));
			}
			
			FatturaElettronica fattura = (FatturaElettronica) this.getFatturaElettronicaFetch().fetch(jdbcProperties.getDatabase(), FatturaElettronica.model(), map);
			
			fattura.setProtocollo((String) lstFields.get(fields.size()));
			
			list.add(fattura);
		}

		return list;      

	}
	@Override
	public long countFattureContestualePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date) throws NotImplementedException, ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		ISQLQueryObject sqlQueryObjectCnt = getSQLQueryObjectForFindFattureContestualePush(sqlQueryObject);

		sqlQueryObjectCnt.addSelectCountField("id");


		JDBCObject[] params = getParamsFattureContestualePush(date);

		return jdbcUtilities.count(sqlQueryObjectCnt.createSQLQuery(), jdbcProperties.isShowSql(), params);
	}

	@Override
	public List<FatturaElettronica> findAllFatturePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception {

		List<FatturaElettronica> list = new ArrayList<FatturaElettronica>();

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		ISQLQueryObject sqlQueryObjectFind = getSQLQueryObjectForFindFatturePush(sqlQueryObject);

		List<IField> fields= new ArrayList<IField>();
		fields.add(FatturaElettronica.model().IDENTIFICATIVO_SDI);
		fields.add(FatturaElettronica.model().POSIZIONE);
		fields.add(FatturaElettronica.model().NOME_FILE);
		fields.add(FatturaElettronica.model().CODICE_DESTINATARIO);
		fields.add(FatturaElettronica.model().STATO_CONSEGNA);
		fields.add(FatturaElettronica.model().DETTAGLIO_CONSEGNA);
		fields.add(FatturaElettronica.model().DATA);
		fields.add(FatturaElettronica.model().DATA_RICEZIONE);
		fields.add(FatturaElettronica.model().NUMERO);
		fields.add(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO);
		fields.add(FatturaElettronica.model().DIVISA);
		fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE);
		fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
		
		
		for(IField field : fields) {
			sqlQueryObjectFind.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(field, true));
		}
		
		sqlQueryObjectFind.addOrderBy(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true));
		
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
			
			FatturaElettronica fattura = (FatturaElettronica) this.getFatturaElettronicaFetch().fetch(jdbcProperties.getDatabase(), FatturaElettronica.model(), map);
			list.add(fattura);
		}

		return list;      

	}

	@Override
	public long countFatturePullByUser(JDBCServiceManagerProperties jdbcProperties, Logger log, ISQLQueryObject sqlQueryObject, Connection connection, Date date, IdUtente idUtente) throws NotImplementedException, ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		DipartimentoFieldConverter dipartimentoFieldConverter = new DipartimentoFieldConverter(sqlQueryObject.getTipoDatabaseOpenSPCoop2());
		UtenteFieldConverter utenteFieldConverter = new UtenteFieldConverter(sqlQueryObject.getTipoDatabaseOpenSPCoop2());

		ISQLQueryObject sqlQueryObjectCnt = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectCnt.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, true));
		sqlQueryObjectCnt.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().POSIZIONE, true));
		sqlQueryObjectCnt.addFromTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectCnt.addFromTable(dipartimentoFieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().CODICE, true) + "=" + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO, true));
		sqlQueryObjectCnt.addFromTable(utenteFieldConverter.toTable(Utente.model().UTENTE_DIPARTIMENTO));
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toTable(Dipartimento.model()) + ".id=" + utenteFieldConverter.toTable(Utente.model().UTENTE_DIPARTIMENTO) + ".id_dipartimento");
		sqlQueryObjectCnt.addFromTable(utenteFieldConverter.toTable(Utente.model()));
		sqlQueryObjectCnt.addWhereCondition(utenteFieldConverter.toTable(Utente.model().UTENTE_DIPARTIMENTO) + ".id_utente=" + utenteFieldConverter.toTable(Utente.model()) + ".id");
		sqlQueryObjectCnt.addWhereCondition(utenteFieldConverter.toColumn(Utente.model().USERNAME, true) + "= ?");
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().MODALITA_PUSH, true) + "= ?");
		sqlQueryObjectCnt.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true) + " <= ?");
		sqlQueryObjectCnt.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA, true) + " = ?");
		sqlQueryObjectCnt.setANDLogicOperator(true);


		JDBCObject[] params = new JDBCObject[] {new JDBCObject(idUtente.getUsername(), Utente.model().USERNAME.getFieldType()), new JDBCObject(false, Dipartimento.model().MODALITA_PUSH.getFieldType()), new JDBCObject(date, FatturaElettronica.model().DATA_RICEZIONE.getFieldType()), new JDBCObject(StatoConsegnaType.NON_CONSEGNATA, FatturaElettronica.model().STATO_CONSEGNA.getFieldType())};
		List<Class<?>> lstReturnType = new ArrayList<Class<?>>();
		lstReturnType.add(FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType());
		lstReturnType.add(FatturaElettronica.model().POSIZIONE.getFieldType());

		return jdbcUtilities.count(sqlQueryObjectCnt.createSQLQuery(), jdbcProperties.isShowSql(), params);

	}

	@Override
	public List<FatturaElettronica> findAllFatturePullByUser(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, IdUtente idUtente, int offset, int limit) throws NotImplementedException, ServiceException,Exception {

		List<FatturaElettronica> list = new ArrayList<FatturaElettronica>();

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		DipartimentoFieldConverter dipartimentoFieldConverter = new DipartimentoFieldConverter(sqlQueryObject.getTipoDatabaseOpenSPCoop2());
		UtenteFieldConverter utenteFieldConverter = new UtenteFieldConverter(sqlQueryObject.getTipoDatabaseOpenSPCoop2());

		ISQLQueryObject sqlQueryObjectFind = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectFind.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, true));
		sqlQueryObjectFind.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().POSIZIONE, true));
		sqlQueryObjectFind.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true));
		sqlQueryObjectFind.addFromTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectFind.addFromTable(dipartimentoFieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectFind.addFromTable(utenteFieldConverter.toTable(Utente.model().UTENTE_DIPARTIMENTO));
		sqlQueryObjectFind.addFromTable(utenteFieldConverter.toTable(Utente.model()));

		sqlQueryObjectFind.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().CODICE, true) + "=" + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO, true));
		sqlQueryObjectFind.addWhereCondition(dipartimentoFieldConverter.toTable(Dipartimento.model()) + ".id=" + utenteFieldConverter.toTable(Utente.model().UTENTE_DIPARTIMENTO) + ".id_dipartimento");
		sqlQueryObjectFind.addWhereCondition(utenteFieldConverter.toTable(Utente.model().UTENTE_DIPARTIMENTO) + ".id_utente=" + utenteFieldConverter.toTable(Utente.model()) + ".id");
		sqlQueryObjectFind.addWhereCondition(utenteFieldConverter.toColumn(Utente.model().USERNAME, true) + "= ?");
		sqlQueryObjectFind.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().MODALITA_PUSH, true) + "= ?");
		sqlQueryObjectFind.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true) + " <= ?");
		sqlQueryObjectFind.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA, true) + " = ?");
		sqlQueryObjectFind.setANDLogicOperator(true);

		sqlQueryObjectFind.addOrderBy(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true));
		sqlQueryObjectFind.setOffset(offset);
		sqlQueryObjectFind.setLimit(limit);

		JDBCObject[] params = new JDBCObject[] {
				new JDBCObject(idUtente.getUsername(), Utente.model().USERNAME.getFieldType()), 
				new JDBCObject(false, Dipartimento.model().MODALITA_PUSH.getFieldType()), 
				new JDBCObject(date, FatturaElettronica.model().DATA_RICEZIONE.getFieldType()), 
				new JDBCObject(StatoConsegnaType.NON_CONSEGNATA, FatturaElettronica.model().STATO_CONSEGNA.getFieldType())};
		
		List<Class<?>> lstReturnType = new ArrayList<Class<?>>();
		lstReturnType.add(FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType());
		lstReturnType.add(FatturaElettronica.model().POSIZIONE.getFieldType());

		List<List<Object>> lstObj = jdbcUtilities.executeQuery(sqlQueryObjectFind.createSQLQuery(), jdbcProperties.isShowSql(), lstReturnType, params);

		for(List<Object> lstFields: lstObj) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldName(), lstFields.get(0));
			map.put(FatturaElettronica.model().POSIZIONE.getFieldName(), lstFields.get(1));

			FatturaElettronica fattura = (FatturaElettronica) this.getFatturaElettronicaFetch().fetch(jdbcProperties.getDatabase(), FatturaElettronica.model(), map);
			list.add(fattura);
		}

		return list;      

	}

	@Override
	public long countFattureDaAccettare(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date) throws NotImplementedException, ServiceException,Exception {
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		ISQLQueryObject sqlQueryObjectCnt = getSQLQueryObjectForFindFattureDaAccettare(sqlQueryObject);

		sqlQueryObjectCnt.addSelectCountField("id");


		JDBCObject[] params = getParamsFattureDaAccettare(date);

		return jdbcUtilities.count(sqlQueryObjectCnt.createSQLQuery(), jdbcProperties.isShowSql(), params);
	}
	
	@Override
	public List<FatturaElettronica> findAllFattureDaAccettare(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception {

		List<FatturaElettronica> list = new ArrayList<FatturaElettronica>();

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);


		ISQLQueryObject sqlQueryObjectFind = getSQLQueryObjectForFindFattureDaAccettare(sqlQueryObject);

		List<IField> fields= new ArrayList<IField>();
		fields.add(FatturaElettronica.model().IDENTIFICATIVO_SDI);
		fields.add(FatturaElettronica.model().POSIZIONE);
		fields.add(FatturaElettronica.model().DATA_RICEZIONE);
		
		
		for(IField field : fields) {
			sqlQueryObjectFind.addSelectField(this.getFatturaElettronicaFieldConverter().toColumn(field, true));
		}
		
		sqlQueryObjectFind.addOrderBy(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true));
		
		sqlQueryObjectFind.setOffset(offset);
		sqlQueryObjectFind.setLimit(limit);


		JDBCObject[] params = getParamsFattureDaAccettare(date);
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
			
			FatturaElettronica fattura = (FatturaElettronica) this.getFatturaElettronicaFetch().fetch(jdbcProperties.getDatabase(), FatturaElettronica.model(), map);
			list.add(fattura);
		}

		return list;      

	}

	public ISQLQueryObject getSQLQueryObjectForFindFattureDaAccettare(
			ISQLQueryObject sqlQueryObject) throws ExpressionException,
			SQLQueryObjectException {
		DipartimentoFieldConverter dipartimentoFieldConverter = new DipartimentoFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());
		NotificaEsitoCommittenteFieldConverter notificaEsitoCommittenteFieldConverter = new NotificaEsitoCommittenteFieldConverter(this.getFatturaElettronicaFieldConverter().getDatabaseType());

		ISQLQueryObject sqlQueryObjectCnt = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectInner = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectInner.setANDLogicOperator(true);
		sqlQueryObjectInner.addFromTable(notificaEsitoCommittenteFieldConverter.toTable(NotificaEsitoCommittente.model()));
		sqlQueryObjectInner.addSelectField("id");
		sqlQueryObjectInner.addWhereCondition(notificaEsitoCommittenteFieldConverter.toColumn(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI, true)+ " = " + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, true));
		sqlQueryObjectInner.addWhereCondition(notificaEsitoCommittenteFieldConverter.toColumn(NotificaEsitoCommittente.model().POSIZIONE, false)+ " = " + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().POSIZIONE, true));
		
		
		sqlQueryObjectCnt.addFromTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectCnt.addFromTable(dipartimentoFieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().CODICE, true) + "=" + this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO, true));
		
		sqlQueryObjectCnt.addWhereCondition(dipartimentoFieldConverter.toColumn(Dipartimento.model().ACCETTAZIONE_AUTOMATICA, true) + "= ?");
		sqlQueryObjectCnt.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE, true) + " <= ?");
		sqlQueryObjectCnt.addWhereCondition(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().ESITO, true) + " IS NULL");
		sqlQueryObjectCnt.addWhereCondition(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()) + ".id_notifica_decorrenza_termini IS NULL");
		sqlQueryObjectCnt.addWhereExistsCondition(true, sqlQueryObjectInner);
		sqlQueryObjectCnt.setANDLogicOperator(true);
		return sqlQueryObjectCnt;
	}
	
	public JDBCObject[] getParamsFattureDaAccettare(Date date) {
		JDBCObject[] params = new JDBCObject[] {
				new JDBCObject(true, Dipartimento.model().ACCETTAZIONE_AUTOMATICA.getFieldType()), 
				new JDBCObject(date, FatturaElettronica.model().DATA_RICEZIONE.getFieldType())}; 
		return params;
	}




	
}
