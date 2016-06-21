/*
:q
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

package org.govmix.proxy.fatturapa.logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.openspcoop2.utils.resources.GestoreJNDI;
import org.openspcoop2.utils.resources.Loader;

/**
 * Log4JAppender personalizzato per la gestione del tracciamento
 * 
 * @author Andrea Manca (manca@link.it)
 * @version 1.4, 07/05/07
 */

public class Log4JAppender extends AppenderSkeleton {
	
	/** DataSource dove attingere connessioni */
	private DataSource ds = null;

	private String dburl = "", dbuser = "", dbpw = "";
	private String dbdriver = "";
	private String dataSource = null;
	private String provider = null;
	private String tipoDatabase = null; //tipoDatabase

	//DB Url
	public void setDBUrl(String dburl) {
		this.dburl = dburl;
	}
	public String getDBUrl() {
		return this.dburl;
	}

	//DB User
	public void setDBUser(String dbuser) {
		this.dbuser = dbuser;
	}
	public String getDBUser() {
		return this.dbuser;
	}

	//DB Password
	public void setDBPwd(String dbpw) {
		this.dbpw = dbpw;
	}
	public String getDBPwd() {
		return this.dbpw;
	}
	
	//	DB Driver
	public void setDBDriver(String dbdriver) {
		this.dbdriver= dbdriver;
	}
	public String getDBDriver() {
		return this.dbdriver;
	}

	//	DataSource
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getDataSource() {
		return this.dataSource;
	}

	//	ProviderJNDI
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getProvider() {
		return this.provider;
	}

	public boolean requiresLayout() {
		return true;
	}

	@Override 
	public void activateOptions() {

		/* --- Impostazione Datasource ---- */
		if(this.dataSource!=null){
			try {
				java.util.Properties context = new java.util.Properties();
				if(this.provider!=null){
					context.put(Context.PROVIDER_URL, this.provider);
				}
				GestoreJNDI jndi = new GestoreJNDI(context);
				this.ds = (DataSource) jndi.lookup(this.dataSource);
			} catch (Exception e) {
				System.err.println("[Log4JAppender] Lookup DataSource Exception: "+e.getMessage());
			}
		}else{
			try {
				Loader.getInstance().newInstance(this.dbdriver);
			} catch (Exception ex) {
				System.err.println ("[Log4JAppender] ClassNotFoundException: "+ex.getMessage());
			}
		}
	}

	@Override 
	public void append(LoggingEvent event) {
		
		String msg = (String) event.getMessage();

		if (msg != null) {

			Connection con = null;
			PreparedStatement stmt = null;
				try {
					if(this.ds != null){
						con = this.ds.getConnection();
					}else{				
						con = DriverManager.getConnection (this.dburl, this.dbuser, this.dbpw);
					}
					
					if(con==null)
						throw new Exception("Connection is null");
					
					stmt = con.prepareStatement("Insert into log(data, nome_logger, messaggio) values(?,?,?)");
					stmt.setDate(1, new Date(event.getTimeStamp()));
					stmt.setString(2, event.getLoggerName());
					stmt.setString(3, msg);
					
					stmt.execute();
					
				}catch(Exception e){
					System.err.println("[Log4JAppender] errore: "+e.getMessage());
					return;
				}finally{
					try{
						if(stmt!=null)
							stmt.close();
					}catch(Exception e){}
				}

				//Chiusura della connessione al DB
				try {con.close();} catch (Exception ex) {}
		}
	}

	public String getTipoDatabase() {
		return this.tipoDatabase;
	}
	public void setTipoDatabase(String tipoDatabase) {
		this.tipoDatabase = tipoDatabase;
	}
	@Override
	public void close() {}

}