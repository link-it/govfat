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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.dao.IExtendedFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.utils.ProjectInfo;
import org.openspcoop2.generic_project.dao.jdbc.JDBCProperties;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLObjectFactory;

/**     
 * Service can be used to search for the backend objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica} 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCExtendedFatturaElettronicaServiceSearch extends JDBCFatturaElettronicaServiceSearch implements IExtendedFatturaElettronicaServiceSearch {


	protected JDBCServiceManagerProperties jdbcProperties = null;
	protected JDBCServiceManager jdbcServiceManager = null;
	protected Logger log = null;
	protected IExtendedJDBCFatturaElettronicaServiceSearch serviceSearch = null;
	public JDBCExtendedFatturaElettronicaServiceSearch(JDBCServiceManager jdbcServiceManager) throws ServiceException {
		super(jdbcServiceManager);
		this.jdbcServiceManager = jdbcServiceManager;
		this.jdbcProperties = jdbcServiceManager.getJdbcProperties();
		this.log = jdbcServiceManager.getLog();
		this.log.debug(JDBCExtendedFatturaElettronicaServiceSearch.class.getName()+ " initialized");
		this.serviceSearch = JDBCProperties.getInstance(ProjectInfo.getInstance()).getServiceSearch("fatturaElettronicaExtended");
		this.serviceSearch.setServiceManager(new JDBCLimitedServiceManager(this.jdbcServiceManager));
	}

	@Override
	public long countFatturePush(Date date) throws NotImplementedException,
	ServiceException, Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.countFatturePush(this.jdbcProperties, this.log, connection, sqlQueryObject, date);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("countFatturePush not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}

	}

	@Override
	public List<FatturaElettronica> findAllFatturePush(Date date, int offset,
			int limit) throws NotImplementedException, ServiceException,
			Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.findAllFatturePush(this.jdbcProperties, this.log, connection, sqlQueryObject, date, offset, limit);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("findAllFatturePush not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}
	}


	@Override
	public long countFattureContestualePush(Date date) throws NotImplementedException,
	ServiceException, Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.countFattureContestualePush(this.jdbcProperties, this.log, connection, sqlQueryObject, date);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("countFattureContestualePush not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}

	}

	@Override
	public List<FatturaElettronica> findAllFattureContestualePush(Date date, int offset,
			int limit) throws NotImplementedException, ServiceException,
			Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.findAllFattureContestualePush(this.jdbcProperties, this.log, connection, sqlQueryObject, date, offset, limit);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("findAllFattureContestualePush not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}
	}

	@Override
	public long countFatturePullByUser(Date date, IdUtente idUtente)
			throws NotImplementedException, ServiceException, Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			if(idUtente==null){
				throw new Exception("Parameter (type:"+IdUtente.class+") 'idUtente' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.countFatturePullByUser(this.jdbcProperties, this.log, sqlQueryObject, connection, date, idUtente);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("countFatturePullByUser not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}

	}

	@Override
	public List<FatturaElettronica> findAllFatturePullByUser(Date date,
			IdUtente idUtente, int offset, int limit)
					throws NotImplementedException, ServiceException, Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			if(idUtente==null){
				throw new Exception("Parameter (type:"+IdUtente.class+") 'idUtente' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.findAllFatturePullByUser(this.jdbcProperties, this.log, connection, sqlQueryObject, date, idUtente, offset, limit);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("findAllFatturePullByUser not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}
	}


	@Override
	public long countFattureDaAccettare(Date date) throws NotImplementedException,
	ServiceException, Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.countFattureDaAccettare(this.jdbcProperties, this.log, connection, sqlQueryObject, date);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("countFattureDaAccettare not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}

	}

	@Override
	public List<FatturaElettronica> findAllFattureDaAccettare(Date date, int offset,
			int limit) throws NotImplementedException, ServiceException,
			Exception {
		Connection connection = null;
		try{

			// check parameters
			if(date==null){
				throw new Exception("Parameter (type:"+Date.class+") 'date' is null");
			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			return this.serviceSearch.findAllFattureDaAccettare(this.jdbcProperties, this.log, connection, sqlQueryObject, date, offset, limit);

		}catch(ServiceException e){
			this.log.error(e,e); throw e;
		}catch(NotFoundException e){
			this.log.debug(e,e); throw e;
		}catch(MultipleResultException e){
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			this.log.error(e,e); throw e;
		}catch(Exception e){
			this.log.error(e,e); throw new ServiceException("findAllFattureDaAccettare not completed: "+e.getMessage(),e);
		}finally{
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}
	}


	@Override
	public void assegnaProtocolloAInteroLotto(IdLotto idLotto, String protocollo) throws ServiceException, NotImplementedException, Exception {

		Connection connection = null;
		boolean oldValueAutoCommit = false;
		boolean rollback = false;
		try{

			// check parameters
			if(idLotto==null){
				throw new Exception("Parameter (type:"+IdLotto.class.getName()+") 'idLotto' is null");
			}
//			if(protocollo==null){
//				throw new Exception("Parameter (type:"+String.class.getName()+") 'protocollo' is null");
//			}

			// ISQLQueryObject
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.jdbcProperties.getDatabase());
			sqlQueryObject.setANDLogicOperator(true);
			// Connection sql
			connection = this.jdbcServiceManager.getConnection();

			// transaction
			if(this.jdbcProperties.isAutomaticTransactionManagement()){
				oldValueAutoCommit = connection.getAutoCommit();
				connection.setAutoCommit(false);
			}

			this.serviceSearch.assegnaProtocolloAInteroLotto(this.jdbcProperties, this.log, connection, sqlQueryObject, idLotto, protocollo);

		}catch(ServiceException e){
			rollback = true;
			this.log.error(e,e); throw e;
		}catch(NotImplementedException e){
			rollback = true;
			this.log.error(e,e); throw e;
		}catch(Exception e){
			rollback = true;
			this.log.error(e,e); throw new ServiceException("Update not completed: "+e.getMessage(),e);
		}finally{
			if(this.jdbcProperties.isAutomaticTransactionManagement()){
				if(rollback){
					try{
						if(connection!=null)
							connection.rollback();
					}catch(Exception eIgnore){}
				}else{
					try{
						if(connection!=null)
							connection.commit();
					}catch(Exception eIgnore){}
				}
				try{
					if(connection!=null)
						connection.setAutoCommit(oldValueAutoCommit);
				}catch(Exception eIgnore){}
			}
			if(connection!=null){
				this.jdbcServiceManager.closeConnection(connection);
			}
		}

	}

}
