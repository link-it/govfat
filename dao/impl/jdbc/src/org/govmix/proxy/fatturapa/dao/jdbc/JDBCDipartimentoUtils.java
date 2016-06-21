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

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.DipartimentoFieldConverter;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.jdbc.JDBCAdapterException;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class JDBCDipartimentoUtils {

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id) throws NotImplementedException, Exception {

		Long longId = toLongId(jdbcProperties, log, connection, sqlQueryObject, id, false);
		if(longId == null) {
			throw  new ServiceException("IdDipartimento ["+id.toJson()+"] non trovato");
		}
		
		return longId;
	}		

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdDipartimento id, boolean throwNotFound) throws NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		DipartimentoFieldConverter fieldConverter = new DipartimentoFieldConverter(jdbcProperties.getDatabase());

		// Object _dipartimento
		sqlQueryObjectGet.addFromTable(fieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);

		sqlQueryObjectGet.addWhereCondition(fieldConverter.toColumn(Dipartimento.model().CODICE,true)+"=?");

		// Recupero _dipartimento
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_dipartimento = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getCodice(), Dipartimento.model().CODICE.getFieldType())
		};
		Long id_dipartimento = null;
		try{
			id_dipartimento = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
					Long.class, searchParams_dipartimento);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_dipartimento==null || id_dipartimento<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}

		return id_dipartimento;

	}

	public static IdDipartimento toLogicId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long longId) throws SQLQueryObjectException, ExpressionException, ServiceException, MultipleResultException, JDBCAdapterException {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		DipartimentoFieldConverter fatturaFieldConverter = new DipartimentoFieldConverter(jdbcProperties.getDatabase());

		ISQLQueryObject sqlQueryObjectGetId = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectGetId.addFromTable(fatturaFieldConverter.toTable(Dipartimento.model()));
		sqlQueryObjectGetId.addSelectField(fatturaFieldConverter.toColumn(Dipartimento.model().CODICE,false));

		sqlQueryObjectGetId.setANDLogicOperator(true);
		sqlQueryObjectGetId.addWhereCondition("id=?");

		// Recupero _notificaDecorrenzaTermini_dipartimento
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_notificaDecorrenzaTermini_dipartimento = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(longId,Long.class)
		};

		List<Class<?>> listaFieldIdReturnType_notificaDecorrenzaTermini_dipartimento = new ArrayList<Class<?>>();
		listaFieldIdReturnType_notificaDecorrenzaTermini_dipartimento.add(Dipartimento.model().CODICE.getFieldType());

		List<Object> listaFieldIdDipartimento = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGetId.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_notificaDecorrenzaTermini_dipartimento, searchParams_notificaDecorrenzaTermini_dipartimento);

		IdDipartimento IdDipartimento = new IdDipartimento();

		IdDipartimento.setCodice((String)listaFieldIdDipartimento.get(0));
		return IdDipartimento;
	}
}
