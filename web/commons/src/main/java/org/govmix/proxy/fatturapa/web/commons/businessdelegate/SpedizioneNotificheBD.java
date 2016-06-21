/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.PccNotifica;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoService;
import org.govmix.proxy.fatturapa.orm.dao.IPccNotificaService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaService;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class SpedizioneNotificheBD extends BaseBD {

	private IPccNotificaService service;
	private IPccTracciaService tracciamentoService;
	private IDipartimentoService dipartimentoService;
	public SpedizioneNotificheBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getPccNotificaService();
		this.tracciamentoService = this.serviceManager.getPccTracciaService();
		this.dipartimentoService = this.serviceManager.getDipartimentoService();
	}
	
	public SpedizioneNotificheBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getPccNotificaService();
		this.tracciamentoService = this.serviceManager.getPccTracciaService();
		this.dipartimentoService = this.serviceManager.getDipartimentoService();
	}

	public boolean existsNotificaEsitoCommittente(IdFattura idFattura) throws Exception {
		try {
			IExpression expression = this.service.newExpression();
			expression.equals(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(NotificaEsitoCommittente.model().POSIZIONE, idFattura.getPosizione());
			
			return this.service.count(expression).longValue() > 0;
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void update(PccNotifica pccNotifica) throws Exception {
		try {
			this.service.update(pccNotifica);
		} catch (ServiceException e) {
			this.log.error("Errore durante la update: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la update: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public List<PccNotifica> findAllNotifiche(Date data, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression exp = this.service.newPaginatedExpression();
			exp.equals(PccNotifica.model().STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA);
			exp.lessEquals(PccNotifica.model().DATA_CREAZIONE, data);
			
			exp.sortOrder(SortOrder.ASC);
			exp.addOrder(PccNotifica.model().DATA_CREAZIONE);
			exp.offset(offset);
			exp.limit(limit);
			List<PccNotifica> findAll = this.service.findAll(exp);
			
			for(PccNotifica notifica: findAll) {
				notifica.setDipartimento(this.dipartimentoService.get(notifica.getIdDipartimento()));
				notifica.setPccTraccia(this.tracciamentoService.get(notifica.getIdTraccia()));
			}
			
			return findAll;

		} catch (ServiceException e) {
			this.log.error("Errore durante la findAllNotifiche: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la findAllNotifiche: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la findAllNotifiche: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findAllNotifiche: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public long countNotifiche(Date date) throws Exception {
		try {
			
			IExpression exp = this.service.newExpression();
			exp.equals(PccNotifica.model().STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA);
			exp.lessEquals(PccNotifica.model().DATA_CREAZIONE, date);
			return this.service.count(exp).longValue();
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
}
