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
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class FatturaPassivaBD extends BaseBD {

	private IFatturaElettronicaServiceSearch serviceSearch;
	private IFatturaElettronicaService service;

	public FatturaPassivaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getFatturaElettronicaService();
		this.serviceSearch = this.serviceManager.getFatturaElettronicaServiceSearch();
	}

	public FatturaPassivaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getFatturaElettronicaService();
		this.serviceSearch = this.serviceManager.getFatturaElettronicaServiceSearch();
	}

	public FatturaPassivaBD() throws Exception {
		this(Logger.getLogger(FatturaPassivaBD.class));
	}

	public void createFatturaPassiva(FatturaElettronica fattura) throws ServiceException {
		try {
			fattura.setFatturazioneAttiva(false);
			this.service.create(fattura);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	public long count(FatturaPassivaFilter filter)throws Exception {
		return this.serviceSearch.count(filter.toExpression()).longValue();
	}
	public List<FatturaElettronica> findAll(FatturaPassivaFilter filter)throws Exception {
		return this.serviceSearch.findAll(filter.toPaginatedExpression());
	}
	public FatturaPassivaFilter newFilter() {
		return new FatturaPassivaFilter(this.serviceSearch);
	}
}