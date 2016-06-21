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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCServiceManager;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;

public abstract class BaseBD {

	protected JDBCServiceManager serviceManager;
	protected Logger log;
	protected boolean validate;
	
	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public BaseBD(Logger log) throws Exception {
		this.log = log;
		this.serviceManager = DAOFactory.getInstance(this.log).getServiceManager();
	}

	public BaseBD(Logger log, Connection conn, boolean autocommit) throws Exception {
		this.log = log;
		this.serviceManager = DAOFactory.getInstance(this.log).getServiceManager(conn, autocommit);
	}
	
}
