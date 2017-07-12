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
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.dao.IExtendedLottoFattureServiceSearch;
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
 * Service can be used to search for the backend objects of type {@link org.govmix.proxy.fatturapa.LottoFatture} 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCExtendedLottoFattureServiceSearch extends JDBCLottoFattureServiceSearch implements IExtendedLottoFattureServiceSearch {


	protected JDBCServiceManagerProperties jdbcProperties = null;
	protected JDBCServiceManager jdbcServiceManager = null;
	protected Logger log = null;
	protected IExtendedJDBCLottoFattureServiceSearch serviceSearch = null;
	public JDBCExtendedLottoFattureServiceSearch(JDBCServiceManager jdbcServiceManager) throws ServiceException {
		super(jdbcServiceManager);
		this.jdbcServiceManager = jdbcServiceManager;
		this.jdbcProperties = jdbcServiceManager.getJdbcProperties();
		this.log = jdbcServiceManager.getLog();
		this.log.debug(JDBCExtendedLottoFattureServiceSearch.class.getName()+ " initialized");
		this.serviceSearch = JDBCProperties.getInstance(ProjectInfo.getInstance()).getServiceSearch("lottoFattureExtended");
		this.serviceSearch.setServiceManager(new JDBCLimitedServiceManager(this.jdbcServiceManager));
	}

	@Override
	public long countLottiPush(Date date) throws NotImplementedException,
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

			return this.serviceSearch.countLottiPush(this.jdbcProperties, this.log, connection, sqlQueryObject, date);

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
	public List<LottoFatture> findAllLottiPush(Date date, int offset,
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

			return this.serviceSearch.findAllLottiPush(this.jdbcProperties, this.log, connection, sqlQueryObject, date, offset, limit);

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

}
