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

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class FatturaAttivaBD extends FatturaBD {


	public FatturaAttivaBD(Logger log) throws Exception {
		super(log);
	}

	public FatturaAttivaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
	}

	public FatturaAttivaBD() throws Exception {
		this(Logger.getLogger(FatturaAttivaBD.class));
	}

	public void createFatturaAttiva(FatturaElettronica fattura) throws ServiceException {
		try {
			fattura.setFatturazioneAttiva(true);
			this.service.create(fattura);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public FatturaAttivaFilter newFilter() {
		return new FatturaAttivaFilter(this.service);
	}
}
