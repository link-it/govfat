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
import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.dao.IPccRispedizioneServiceSearch;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public class PccRispedizioneBD extends BaseBD {



	private IPccRispedizioneServiceSearch service;

	public PccRispedizioneBD() throws Exception {
		this(Logger.getLogger(PccRispedizioneBD.class));
	}

	public PccRispedizioneBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getPccRispedizioneServiceSearch();
	}

	public PccRispedizioneBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getPccRispedizioneServiceSearch();
	}

	public PccRispedizione get(String codErrore) throws Exception {
		try {
			

			
			IPaginatedExpression exp = this.service.newPaginatedExpression();
			exp.equals(PccRispedizione.model().CODICE_ERRORE, codErrore);
			List<PccRispedizione> pccRispedizioneLst = this.service.findAll(exp);
			
			if(pccRispedizioneLst != null && !pccRispedizioneLst.isEmpty()) {
				return pccRispedizioneLst.get(0);
			} else {
				return null;
			}
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

}
