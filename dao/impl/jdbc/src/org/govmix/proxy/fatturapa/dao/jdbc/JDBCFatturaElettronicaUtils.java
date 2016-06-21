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
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.jdbc.JDBCAdapterException;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class JDBCFatturaElettronicaUtils {

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id) throws NotImplementedException, Exception {

		Long longId = toLongId(jdbcProperties, log, connection, sqlQueryObject, id, false);
		if(longId == null) {
			throw new ServiceException("IdFattura ["+id.toJson()+"] non trovato");
		}
		
		return longId;
	}		

	public static Long toLongId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, boolean throwNotFound) throws NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		FatturaElettronicaFieldConverter fatturaFieldConverter = new FatturaElettronicaFieldConverter(jdbcProperties.getDatabase());

		// Object _fatturaElettronica
		sqlQueryObjectGet.addFromTable(fatturaFieldConverter.toTable(FatturaElettronica.model()));
		sqlQueryObjectGet.addSelectField("id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);

		sqlQueryObjectGet.addWhereCondition(fatturaFieldConverter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(fatturaFieldConverter.toColumn(FatturaElettronica.model().POSIZIONE,true)+"=?");

		// Recupero _fatturaElettronica
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_fatturaElettronica = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdentificativoSdi(), FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()),
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getPosizione(), FatturaElettronica.model().POSIZIONE.getFieldType())
		};
		Long id_fatturaElettronica = null;
		try{
			id_fatturaElettronica = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
					Long.class, searchParams_fatturaElettronica);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_fatturaElettronica==null || id_fatturaElettronica<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}

		return id_fatturaElettronica;

	}

	public static IdFattura toFatturaId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long longId) throws SQLQueryObjectException, ExpressionException, ServiceException, MultipleResultException, JDBCAdapterException {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		FatturaElettronicaFieldConverter fatturaFieldConverter = new FatturaElettronicaFieldConverter(jdbcProperties.getDatabase());

		ISQLQueryObject sqlQueryObjectGetId = sqlQueryObject.newSQLQueryObject();
		sqlQueryObjectGetId.addFromTable(fatturaFieldConverter.toTable(FatturaElettronica.model()));
		sqlQueryObjectGetId.addSelectField(fatturaFieldConverter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI,false));
		sqlQueryObjectGetId.addSelectField(fatturaFieldConverter.toColumn(FatturaElettronica.model().POSIZIONE,false));

		sqlQueryObjectGetId.setANDLogicOperator(true);
		sqlQueryObjectGetId.addWhereCondition("id=?");

		// Recupero _notificaDecorrenzaTermini_fatturaElettronica
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_notificaDecorrenzaTermini_fatturaElettronica = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(longId,Long.class)
		};

		List<Class<?>> listaFieldIdReturnType_notificaDecorrenzaTermini_fatturaElettronica = new ArrayList<Class<?>>();
		listaFieldIdReturnType_notificaDecorrenzaTermini_fatturaElettronica.add(FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType());
		listaFieldIdReturnType_notificaDecorrenzaTermini_fatturaElettronica.add(FatturaElettronica.model().POSIZIONE.getFieldType());

		List<Object> listaFieldId_notificaDecorrenzaTermini_fatturaElettronica = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGetId.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_notificaDecorrenzaTermini_fatturaElettronica, searchParams_notificaDecorrenzaTermini_fatturaElettronica);

		IdFattura idFattura = new IdFattura();

		idFattura.setIdentificativoSdi((Integer)listaFieldId_notificaDecorrenzaTermini_fatturaElettronica.get(0));
		idFattura.setPosizione((Integer)listaFieldId_notificaDecorrenzaTermini_fatturaElettronica.get(1));
		return idFattura;
	}
}
