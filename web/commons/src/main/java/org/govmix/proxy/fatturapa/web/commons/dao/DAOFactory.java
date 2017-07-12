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
package org.govmix.proxy.fatturapa.web.commons.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;
import org.govmix.proxy.fatturapa.web.commons.dao.utils.DAOFactoryProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.generic_project.utils.ServiceManagerProperties;


public class DAOFactory {

	private String url  = null;
	private String driver  = null;
	private String userName  = null;
	private String password  = null;
	private String databaseType  = null;
	private String datasourceJndiName = null;
	private String tipoConnessione = null;

	private DataSource ds; 
	
	private Logger log = null;
	private boolean showSql;
	private boolean generateDdl; 
	private boolean autocommit; 
	private Properties contextProperties = new Properties();

	/** Copia Statica */
	private static DAOFactory daoFactory = null;

//	private static synchronized void initialize(org.apache.log4j.Logger log) throws Exception{
//
//		if(DAOFactory.daoFactory==null)
//			DAOFactory.daoFactory = new DAOFactory(log);	
//
//	}

	public static DAOFactory getInstance() throws Exception{

		if(DAOFactory.daoFactory==null){
			Logger log = LoggerManager.getDaoLogger();
			DAOFactory.daoFactory = new DAOFactory(log);
		}
			

		return DAOFactory.daoFactory;
	}

	public DAOFactory(org.apache.log4j.Logger log) throws Exception{
		try{
			this.log = log;

			this.url = DAOFactoryProperties.getInstance(log).getConnectionUrl() ;
			this.datasourceJndiName = DAOFactoryProperties.getInstance(log).getDsJndiName() ;
			this.driver = DAOFactoryProperties.getInstance(log).getJdbcDriver() ;
			this.password = DAOFactoryProperties.getInstance(log).getPassword() ;
			this.tipoConnessione = DAOFactoryProperties.getInstance(log).getTipoConnessione() ;
			this.databaseType = DAOFactoryProperties.getInstance(log).getTipoDatabase() ;
			this.userName = DAOFactoryProperties.getInstance(log).getUsername() ;
			this.autocommit = DAOFactoryProperties.getInstance(log).isAutocommit() ;
			this.generateDdl = DAOFactoryProperties.getInstance(log).isGenerateDDL() ;
			this.showSql = DAOFactoryProperties.getInstance(log).isShowSql() ;

			this.getServiceManager();
		}catch(Exception e){
			throw e;
		}
	}

	public JDBCServiceManager getServiceManager() throws Exception{

		JDBCServiceManager serviceManager = null;
		if(this.tipoConnessione.equalsIgnoreCase("datasource")) {
			serviceManager = new JDBCServiceManager(this.datasourceJndiName, this.contextProperties,getServiceManagerProperties(),this.log);
			Context initialContext = new InitialContext();
			try{
				this.ds = (DataSource)initialContext.lookup(this.datasourceJndiName);
			} catch(NameNotFoundException e) {
				this.ds = (DataSource)initialContext.lookup("java:/"+this.datasourceJndiName);

			}
		} else if(this.tipoConnessione.equalsIgnoreCase("connection")) {
			serviceManager = new JDBCServiceManager(this.url,this.driver,this.userName,this.password,getServiceManagerProperties(),this.log);
		}

		return serviceManager;
	}

	public JDBCServiceManager getServiceManager(Connection connection, boolean autocommit) throws Exception{
		JDBCServiceManager serviceManager = new JDBCServiceManager(connection,getServiceManagerProperties(autocommit),this.log); 
		return serviceManager;
	}
	
	public Connection getConnection() throws SQLException {
		if(this.tipoConnessione.equalsIgnoreCase("datasource")) {
			return this.ds.getConnection();
		} else if(this.tipoConnessione.equalsIgnoreCase("connection")) {
			return DriverManager.getConnection(this.url, this.userName, this.password);
		}
		return null;
	}



	public ServiceManagerProperties getServiceManagerProperties() throws Exception{
		ServiceManagerProperties sm = new ServiceManagerProperties();
		sm.setDatabaseType(this.databaseType);
		sm.setShowSql(this.showSql);
		sm.setGenerateDdl(this.generateDdl);
		sm.setAutomaticTransactionManagement(this.autocommit);
		return sm;
	}

	public ServiceManagerProperties getServiceManagerProperties(boolean autoCommit) throws Exception{
		ServiceManagerProperties sm = new ServiceManagerProperties();
		sm.setDatabaseType(this.databaseType);
		sm.setShowSql(this.showSql);
		sm.setGenerateDdl(this.generateDdl);
		sm.setAutomaticTransactionManagement(autoCommit);
		return sm;
	}



}
