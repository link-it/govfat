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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public class FatturaElettronicaBD extends BaseBD {

	private IFatturaElettronicaService service;

	public FatturaElettronicaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaElettronicaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaElettronicaBD() throws Exception {
		this(Logger.getLogger(FatturaElettronicaBD.class));
	}

	public FatturaElettronica getById(long idFisico) throws ServiceException, NotFoundException {
		try {
			return ((IDBFatturaElettronicaService)this.service).get(idFisico);

		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public FatturaElettronica get(IdFattura id) throws ServiceException, NotFoundException {
		try {
			return this.service.get(id);

		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}


	public IdFattura convertToId(FatturaElettronica fattura) throws ServiceException {
		try {
			return this.service.convertToId(fattura);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public boolean existFatture() throws Exception {
		try {

			return this.service.count(this.service.newExpression()).longValue() > 0;
		} catch (ServiceException e) {
			this.log.error("Errore durante la existFatture: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la existFatture: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<IdFattura> getIdFattureByUtente(Utente utente) throws Exception {

		try {
			IPaginatedExpression expression = this.service.newPaginatedExpression();
			List<String> dipartimenti = new ArrayList<String>();

			for(UtenteDipartimento id: utente.getUtenteDipartimentoList()) {
				dipartimenti.add(id.getIdDipartimento().getCodice());
			}

			expression.in(FatturaElettronica.model().CODICE_DESTINATARIO, dipartimenti.toArray());
			return this.service.findAllIds(expression);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (Exception e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<IdFattura> findAllIdFatturaByIdentificativoSdi(Integer identificativoSdI) throws Exception {
		try {
			IPaginatedExpression exp = this.service.newPaginatedExpression();
			exp.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI, identificativoSdI);
			return this.service.findAllIds(exp);
		} catch (ServiceException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	

	public long count(FatturaFilter filter)throws Exception {
		return this.service.count(filter.toExpression()).longValue();
	}
	public List<FatturaElettronica> findAll(FatturaFilter filter)throws Exception {
		return this.service.findAll(filter.toPaginatedExpression());
	}
	public List<IdFattura> findAllIds(FatturaFilter filter)throws Exception {
		return this.service.findAllIds(filter.toPaginatedExpression());
	}
	public FatturaFilter newFilter() {
		return new FatturaFilter(this.service);
	}
	public FatturaFilter newFilter(IExpression exp) {
		return new FatturaFilter(this.service, exp);
	}


}
