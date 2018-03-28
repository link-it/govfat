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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.PccNotifica;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.dao.IPccNotificaService;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public class PccNotificaBD extends BaseBD {



	private IPccNotificaService service;

	public PccNotificaBD() throws Exception {
		this(Logger.getLogger(PccNotificaBD.class));
	}

	public PccNotificaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getPccNotificaService();
	}

	public PccNotificaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getPccNotificaService();
	}

	public void createNotifica(PccNotifica notifica) throws Exception {
		try {
			this.service.create(notifica);
		} catch (ServiceException e) {
			this.log.error("Errore durante la createNotifica: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la createNotifica: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public List<PccNotifica> findAllNotificheDaSpedire(Date date, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression exp = this.service.toPaginatedExpression(getNotificheDaSpedireExpression(date));
			
			return this.service.findAll(exp);
		} catch (ServiceException e) {
			this.log.error("Errore durante la findAllNotificheDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findAllNotificheDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public long countNotificheDaSpedire(Date date) throws Exception {
		try {
			IExpression exp = this.getNotificheDaSpedireExpression(date);
			
			return this.service.count(exp).longValue();
		} catch (ServiceException e) {
			this.log.error("Errore durante la countNotificheDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la countNotificheDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	private IExpression getNotificheDaSpedireExpression(Date date) throws Exception {
		IExpression exp1 = this.service.newExpression();
		exp1.lessEquals(PccNotifica.model().DATA_CONSEGNA, date).or().isNull(PccNotifica.model().DATA_CONSEGNA);
		
		IExpression exp2 = this.service.newExpression();
		exp2.equals(PccNotifica.model().STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA).or().equals(PccNotifica.model().STATO_CONSEGNA, StatoConsegnaType.IN_RICONSEGNA);
		
		IExpression exp = this.service.newExpression();
		exp.and(exp1, exp2);
		return exp;
	}

}
