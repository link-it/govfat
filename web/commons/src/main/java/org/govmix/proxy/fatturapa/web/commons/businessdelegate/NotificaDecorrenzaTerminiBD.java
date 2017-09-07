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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.dao.INotificaDecorrenzaTerminiService;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.LoggerWrapperFactory;

public class NotificaDecorrenzaTerminiBD extends BaseBD {

	private INotificaDecorrenzaTerminiService service;
	public NotificaDecorrenzaTerminiBD() throws Exception {
		this(LoggerWrapperFactory.getLogger(NotificaDecorrenzaTerminiBD.class));
	}
	
	public NotificaDecorrenzaTerminiBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getNotificaDecorrenzaTerminiService();
	}
	
	public NotificaDecorrenzaTerminiBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getNotificaDecorrenzaTerminiService();
	}

	public void create(NotificaDecorrenzaTermini notificaDecorrenzaTermini) throws Exception {
		try {
			
			IdNotificaDecorrenzaTermini id = this.service.convertToId(notificaDecorrenzaTermini);
			if(this.service.exists(id)) {
				this.log.warn("Notifica Decorrenza Termini ["+id.toJson()+"] gia' inserita nel sistema. Non la inserisco nuovamente");
				return;
			}
			this.service.create(notificaDecorrenzaTermini);
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
}
