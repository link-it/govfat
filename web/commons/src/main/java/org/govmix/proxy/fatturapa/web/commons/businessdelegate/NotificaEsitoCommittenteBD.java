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
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.dao.IDBNotificaEsitoCommittenteService;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteService;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class NotificaEsitoCommittenteBD extends BaseBD {

	private INotificaEsitoCommittenteService service;
	public NotificaEsitoCommittenteBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getNotificaEsitoCommittenteService();
	}
	
	public NotificaEsitoCommittenteBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getNotificaEsitoCommittenteService();
	}

	public void create(NotificaEsitoCommittente notificaEsitoCommittente) throws Exception {
		try {
			this.service.create(notificaEsitoCommittente);
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
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
	
	public boolean canNotificaEsitoCommittenteBeSent(IdFattura idFattura) throws Exception {
		try {
			IExpression expression = this.service.newExpression();
			expression.equals(NotificaEsitoCommittente.model().IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(NotificaEsitoCommittente.model().POSIZIONE, idFattura.getPosizione());
			expression.isNull(NotificaEsitoCommittente.model().SCARTO);
//			expression.isNotNull(NotificaEsitoCommittente.model().DATA_INVIO_SDI);
			
			return this.service.count(expression).longValue() <= 0;
		} catch (ServiceException e) {
			this.log.error("Errore durante la canNotificaEsitoCommittenteBeSent: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la canNotificaEsitoCommittenteBeSent: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	public void update(NotificaEsitoCommittente notificaEsitoCommittente) throws Exception {
		try {
			this.service.update(notificaEsitoCommittente);
		} catch (ServiceException e) {
			this.log.error("Errore durante la update: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la update: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public List<NotificaEsitoCommittente> findAllNotifiche(Date date, int offset, int limit) throws Exception {
		try {
			IExpression exp1 = this.service.newExpression();
			exp1.lessEquals(NotificaEsitoCommittente.model().DATA_INVIO_SDI, date).and().isNull(NotificaEsitoCommittente.model().XML);
			
			IExpression exp2 = this.service.newExpression();
			exp2.isNull(NotificaEsitoCommittente.model().DATA_INVIO_SDI);

			IPaginatedExpression exp = this.service.newPaginatedExpression();
			exp.or(exp1, exp2);
			exp.sortOrder(SortOrder.ASC);
			exp.addOrder(NotificaEsitoCommittente.model().DATA_INVIO_ENTE);
			exp.offset(offset);
			exp.limit(limit);
			return this.service.findAll(exp);
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
	
	public long countNotifiche(Date date) throws Exception {
		try {
			
			IExpression exp1 = this.service.newExpression();
			exp1.lessEquals(NotificaEsitoCommittente.model().DATA_INVIO_SDI, date).and().isNull(NotificaEsitoCommittente.model().XML);
			
			IExpression exp2 = this.service.newExpression();
			exp2.isNull(NotificaEsitoCommittente.model().DATA_INVIO_SDI);

			IExpression exp = this.service.newExpression();
			exp.or(exp1, exp2);

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
	public void forzaRispedizioneNotifica(NotificaEsitoCommittente notifica) throws Exception {
		try {
			NotificaEsitoCommittente notificaDaDB = ((IDBNotificaEsitoCommittenteService)this.service).get(notifica.getId());
			if(StatoConsegnaType.ERRORE_CONSEGNA.equals(notificaDaDB.getStatoConsegnaSdi()) || StatoConsegnaType.IN_RICONSEGNA.equals(notificaDaDB.getStatoConsegnaSdi())) {
				notificaDaDB.setStatoConsegnaSdi(StatoConsegnaType.IN_RICONSEGNA);
				notificaDaDB.setDataProssimaConsegnaSdi(new Date());
				notificaDaDB.setTentativiConsegnaSdi(0);
				this.service.update(notificaDaDB);
			} else
				throw new Exception("La notifica relativa alla fattura ["+notifica.getIdFattura().toJson()+"] non puo' essere forzata in riconsegna perche' il suo stato attuale e' ["+notificaDaDB.getStatoConsegnaSdi()+"]");
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la forzaRispedizioneFattura: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la forzaRispedizioneFattura: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
}
