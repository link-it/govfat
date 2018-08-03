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
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.dao.IAllegatoFatturaService;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public class AllegatoFatturaBD extends BaseBD {

	private IAllegatoFatturaService service;

	public AllegatoFatturaBD() throws Exception {
		this(Logger.getLogger(AllegatoFatturaBD.class));
	}

	public AllegatoFatturaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getAllegatoFatturaService();
	}

	public AllegatoFatturaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getAllegatoFatturaService();
	}

	public void create(AllegatoFattura allegato) throws Exception {
		try {
			this.service.create(allegato, this.validate);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public void validate(AllegatoFattura allegato) throws Exception {
		try {
			this.service.validate(allegato);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public List<AllegatoFattura> getAllegati(IdFattura id) throws ServiceException {
		try {
			IPaginatedExpression exp = this.service.newPaginatedExpression();
			exp.equals(AllegatoFattura.model().ID_FATTURA.IDENTIFICATIVO_SDI, id.getIdentificativoSdi());
			exp.equals(AllegatoFattura.model().ID_FATTURA.POSIZIONE, id.getPosizione());
			exp.equals(AllegatoFattura.model().ID_FATTURA.FATTURAZIONE_ATTIVA, id.getFatturazioneAttiva());
			return this.service.findAll(exp);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}
}
