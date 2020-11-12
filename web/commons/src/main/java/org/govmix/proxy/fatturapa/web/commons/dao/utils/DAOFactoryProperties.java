/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.dao.utils;

import org.govmix.proxy.pcc.fatture.utils.AbstractProperties;

public class DAOFactoryProperties extends AbstractProperties {

	
	//Properties Connession DB
		private String tipoDatabase;
		private String tipoConnessione;
		private String dsJndiName;
		private String connectionUrl;
		private String username;
		private String password;
		private String jdbcDriver;
		private boolean autocommit;
		private boolean showSql;
		private boolean generateDDL;
		
		
		private static final String propertiesPath = "/daoFactory.properties";
		/** Copia Statica */
		private static DAOFactoryProperties daoFactoryProperties = null;

		public static synchronized void initialize(org.apache.log4j.Logger log) throws Exception{

			if(DAOFactoryProperties.daoFactoryProperties==null)
				DAOFactoryProperties.daoFactoryProperties = new DAOFactoryProperties(log, propertiesPath);	
		}

		public static DAOFactoryProperties getInstance(org.apache.log4j.Logger log) throws Exception{

			if(DAOFactoryProperties.daoFactoryProperties==null)
				initialize(log);

			return DAOFactoryProperties.daoFactoryProperties;
		}
		
		/* ********  C O S T R U T T O R E  ******** */

		/**
		 * Viene chiamato in causa per istanziare il properties reader
		 *
		 * 
		 */
		public DAOFactoryProperties(org.apache.log4j.Logger log, String path) throws Exception{

			super(path);
			
			//carico le properties
			this.autocommit = getBooleanProperty("db.autoCommit", true);
			this.showSql = getBooleanProperty("db.showSql", true);
			this.generateDDL = getBooleanProperty("db.generateDDL", true);
			this.tipoDatabase = getProperty("db.tipoDatabase", true);
			
			this.tipoConnessione = getProperty("db.tipo", true);

			boolean connection = this.tipoConnessione.equals("connection");
			boolean dataSource = this.tipoConnessione.equals("datasource");
			this.connectionUrl = getProperty("db.connection.url", connection);
			this.jdbcDriver = getProperty("db.connection.driver", connection);
			this.username = getProperty("db.connection.user", connection);
			this.password =getProperty("db.connection.password", connection);

			this.dsJndiName = getProperty("db.datasource.jndiName", dataSource);

		}

		/* ********  P R O P E R T I E S  ******** */

		public String getTipoDatabase() {
			return tipoDatabase;
		}

		public void setTipoDatabase(String tipoDatabase) {
			this.tipoDatabase = tipoDatabase;
		}

		public String getTipoConnessione() {
			return tipoConnessione;
		}

		public void setTipoConnessione(String tipoConnessione) {
			this.tipoConnessione = tipoConnessione;
		}

		public String getDsJndiName() {
			return dsJndiName;
		}

		public void setDsJndiName(String dsJndiName) {
			this.dsJndiName = dsJndiName;
		}

		public String getConnectionUrl() {
			return connectionUrl;
		}

		public void setConnectionUrl(String connectionUrl) {
			this.connectionUrl = connectionUrl;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getJdbcDriver() {
			return jdbcDriver;
		}

		public void setJdbcDriver(String jdbcDriver) {
			this.jdbcDriver = jdbcDriver;
		}

		public boolean isAutocommit() {
			return autocommit;
		}

		public void setAutocommit(boolean autocommit) {
			this.autocommit = autocommit;
		}

		public boolean isShowSql() {
			return showSql;
		}

		public void setShowSql(boolean showSql) {
			this.showSql = showSql;
		}

		public boolean isGenerateDDL() {
			return generateDDL;
		}

		public void setGenerateDDL(boolean generateDDL) {
			this.generateDDL = generateDDL;
		}
}
