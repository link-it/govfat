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
import org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.NotificaDecorrenzaTerminiFieldConverter;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.jdbc.JDBCAdapterException;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class JDBCNotificaDecorrenzaTerminiUtils {

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini id) throws NotImplementedException, Exception {

		Long longId = toLongId(jdbcProperties, log, connection, sqlQueryObject, id, false);
		if(longId == null) {
			throw  new ServiceException("IdNotificaDecorrenzaTermini ["+id.toJson()+"] non trovato");
		}
		
		return longId;
	}		

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdNotificaDecorrenzaTermini id, boolean throwNotFound) throws NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		NotificaDecorrenzaTerminiFieldConverter fieldConverter = new NotificaDecorrenzaTerminiFieldConverter(jdbcProperties.getDatabase());

		// Object _dipartimento
		sqlQueryObjectGet.addFromTable(fieldConverter.toTable(NotificaDecorrenzaTermini.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);

		sqlQueryObjectGet.addWhereCondition(fieldConverter.toColumn(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI,true)+"=?");

		// Recupero _dipartimento
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_dipartimento = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdentificativoSdi(), NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI.getFieldType())
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

	public static IdNotificaDecorrenzaTermini toLogicId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long longId) throws SQLQueryObjectException, ExpressionException, ServiceException, MultipleResultException, JDBCAdapterException {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		NotificaDecorrenzaTerminiFieldConverter fatturaFieldConverter = new NotificaDecorrenzaTerminiFieldConverter(jdbcProperties.getDatabase());

		ISQLQueryObject sqlQueryObjectGetId = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectGetId.addFromTable(fatturaFieldConverter.toTable(NotificaDecorrenzaTermini.model()));
		sqlQueryObjectGetId.addSelectField(fatturaFieldConverter.toColumn(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI,false));

		sqlQueryObjectGetId.setANDLogicOperator(true);
		sqlQueryObjectGetId.addWhereCondition("id=?");

		// Recupero _notificaDecorrenzaTermini_dipartimento
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_notificaDecorrenzaTermini_dipartimento = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(longId,Long.class)
		};

		List<Class<?>> listaFieldIdReturnType_notificaDecorrenzaTermini_dipartimento = new ArrayList<Class<?>>();
		listaFieldIdReturnType_notificaDecorrenzaTermini_dipartimento.add(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI.getFieldType());

		List<Object> listaFieldIdNotificaDecorrenzaTermini = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGetId.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_notificaDecorrenzaTermini_dipartimento, searchParams_notificaDecorrenzaTermini_dipartimento);

		IdNotificaDecorrenzaTermini IdNotificaDecorrenzaTermini = new IdNotificaDecorrenzaTermini();

		IdNotificaDecorrenzaTermini.setIdentificativoSdi((Integer)listaFieldIdNotificaDecorrenzaTermini.get(0));
		return IdNotificaDecorrenzaTermini;
	}
}
