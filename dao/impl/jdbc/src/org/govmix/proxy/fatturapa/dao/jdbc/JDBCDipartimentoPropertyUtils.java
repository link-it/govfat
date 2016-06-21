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
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.DipartimentoPropertyFieldConverter;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.jdbc.JDBCAdapterException;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class JDBCDipartimentoPropertyUtils {

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdProperty id) throws NotImplementedException, Exception {

		Long longId = toLongId(jdbcProperties, log, connection, sqlQueryObject, id, false);
		if(longId == null) {
			throw  new ServiceException("IdDipartimentoProperty ["+id.toJson()+"] non trovato");
		}
		
		return longId;
	}		

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdProperty id, boolean throwNotFound) throws NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		DipartimentoPropertyFieldConverter fieldConverter = new DipartimentoPropertyFieldConverter(jdbcProperties.getDatabase());

		// Object _ente
		sqlQueryObjectGet.addFromTable(fieldConverter.toTable(DipartimentoProperty.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);

		sqlQueryObjectGet.addWhereCondition(fieldConverter.toColumn(DipartimentoProperty.model().NOME,true)+"=?");

		// Recupero _ente
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_ente = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getNome(), DipartimentoProperty.model().NOME.getFieldType())
		};
		Long id_dipartimento_property = null;
		try{
			id_dipartimento_property = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
					Long.class, searchParams_ente);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_dipartimento_property==null || id_dipartimento_property<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}

		return id_dipartimento_property;

	}

	public static IdProperty toLogicId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long longId) throws SQLQueryObjectException, ExpressionException, ServiceException, MultipleResultException, JDBCAdapterException {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		DipartimentoPropertyFieldConverter fatturaFieldConverter = new DipartimentoPropertyFieldConverter(jdbcProperties.getDatabase());

		ISQLQueryObject sqlQueryObjectGetId = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectGetId.addFromTable(fatturaFieldConverter.toTable(DipartimentoProperty.model()));
		sqlQueryObjectGetId.addSelectField(fatturaFieldConverter.toColumn(DipartimentoProperty.model().NOME,false));

		sqlQueryObjectGetId.setANDLogicOperator(true);
		sqlQueryObjectGetId.addWhereCondition("id=?");

		// Recupero _notificaDecorrenzaTermini_ente
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_notificaDecorrenzaTermini_ente = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(longId,Long.class)
		};

		List<Class<?>> listaFieldIdReturnType_notificaDecorrenzaTermini_ente = new ArrayList<Class<?>>();
		listaFieldIdReturnType_notificaDecorrenzaTermini_ente.add(DipartimentoProperty.model().NOME.getFieldType());

		List<Object> listaFieldIdEnte = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGetId.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_notificaDecorrenzaTermini_ente, searchParams_notificaDecorrenzaTermini_ente);

		IdProperty IdDipartimentoProperty = new IdProperty();

		IdDipartimentoProperty.setNome((String)listaFieldIdEnte.get(0));
		return IdDipartimentoProperty;
	}
}
