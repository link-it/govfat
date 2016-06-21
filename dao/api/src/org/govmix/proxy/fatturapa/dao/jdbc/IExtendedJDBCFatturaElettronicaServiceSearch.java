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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.IdUtente;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithId;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.sql.ISQLQueryObject;

public interface IExtendedJDBCFatturaElettronicaServiceSearch extends IJDBCServiceSearchWithId<FatturaElettronica, IdFattura, JDBCServiceManager> {
	public long countFatturePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date) throws NotImplementedException, ServiceException,Exception;	
	public List<FatturaElettronica> findAllFatturePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception;
	public long countFattureContestualePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date) throws NotImplementedException, ServiceException,Exception;	
	public List<FatturaElettronica> findAllFattureContestualePush(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception;
	public long countFatturePullByUser(JDBCServiceManagerProperties jdbcProperties, Logger log, ISQLQueryObject sqlQueryObject, Connection connection, Date date, IdUtente idUtente) throws NotImplementedException, ServiceException,Exception;
	public List<FatturaElettronica> findAllFatturePullByUser(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, IdUtente idUtente, int offset, int limit) throws NotImplementedException, ServiceException,Exception;
	public long countFattureDaAccettare(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date) throws NotImplementedException, ServiceException,Exception;	
	public List<FatturaElettronica> findAllFattureDaAccettare(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception;

	public void assegnaProtocolloAInteroLotto(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdLotto idLotto, String protocollo) throws NotImplementedException, ServiceException,Exception;
}
