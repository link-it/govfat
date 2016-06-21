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

import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.dao.IRegistroService;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public class RegistroBD extends BaseBD {


	private IRegistroService service;

	public RegistroBD() throws Exception {
		this(Logger.getLogger(RegistroBD.class));
	}

	public RegistroBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getRegistroService();
	}

	public List<Registro> findByIdEnte(IdEnte idEnte) throws Exception {
		try {
			
			IPaginatedExpression expression = this.service.newPaginatedExpression();
			
			expression.equals(Registro.model().ID_ENTE.NOME, idEnte.getNome());
			return this.service.findAll(expression);
					
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

}
