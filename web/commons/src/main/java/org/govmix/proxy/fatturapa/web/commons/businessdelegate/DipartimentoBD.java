/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoService;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class DipartimentoBD extends BaseBD {


	protected IDipartimentoService service;

	public DipartimentoBD() throws Exception {
		this(Logger.getLogger(DipartimentoBD.class));
	}

	public DipartimentoBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getDipartimentoService();
	}

	public DipartimentoBD(Logger log, Connection connection, boolean autoCommit) throws Exception {
		super(log, connection, autoCommit);
		this.service = this.serviceManager.getDipartimentoService();
	}

	public Dipartimento get(IdDipartimento id) throws Exception {
		return this._get(id);
	}
	
	private Dipartimento _get(IdDipartimento id) throws Exception {
		try {
			
			if(!this.service.exists(id)) {
				throw new Exception("Dipartimento con id ["+id.toJson()+"] non esiste.");
			}
			
			return this.service.get(id);
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public List<Dipartimento> findAll() throws Exception {
		try {
			
			return this.service.findAll(this.service.newPaginatedExpression());
		} catch (ServiceException e) {
			this.log.error("Errore durante la findAll: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findAll: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public boolean isPull(IdDipartimento id) throws Exception {
		try {
			return !this._get(id).getModalitaPush();
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

}
