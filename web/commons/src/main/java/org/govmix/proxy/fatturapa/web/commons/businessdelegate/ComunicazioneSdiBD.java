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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.ComunicazioneSdi;
import org.govmix.proxy.fatturapa.orm.IdComunicazione;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IComunicazioneSdiService;
import org.govmix.proxy.fatturapa.orm.dao.IComunicazioneSdiServiceSearch;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class ComunicazioneSdiBD extends BaseBD {

	private IComunicazioneSdiServiceSearch serviceSearch;
	private IComunicazioneSdiService service;

	public ComunicazioneSdiBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getComunicazioneSdiService();
		this.serviceSearch = this.serviceManager.getComunicazioneSdiServiceSearch();
	}

	public ComunicazioneSdiBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getComunicazioneSdiService();
		this.serviceSearch = this.serviceManager.getComunicazioneSdiServiceSearch();
	}

	public ComunicazioneSdiBD() throws Exception {
		this(Logger.getLogger(ComunicazioneSdiBD.class));
	}
	
	public void create(ComunicazioneSdi comunicazione) throws Exception {
		try {
			this.service.create(comunicazione, true);
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public IdComunicazione convertToId(ComunicazioneSdi comunicazione) throws Exception {
		try {
			return this.service.convertToId(comunicazione);
		} catch (ServiceException e) {
			this.log.error("Errore durante la convertToId: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la convertToId: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public int getNextProgressivo(Integer identificativoSDI, TipoComunicazioneType tipo) throws Exception {
		try {
			IExpression expression = this.service.newExpression();
			expression.equals(ComunicazioneSdi.model().IDENTIFICATIVO_SDI, identificativoSDI);
			expression.equals(ComunicazioneSdi.model().TIPO_COMUNICAZIONE, tipo);
			return (int) this.serviceSearch.count(expression).longValue() + 1;
		} catch (ServiceException e) {
			this.log.error("Errore durante la getNextProgressivo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getNextProgressivo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public long countComunicazioneDaSpedire(Date date) throws Exception {
		try {
			IExpression expression = getComunicazioneDaSpedireExpression(date);
			return this.serviceSearch.count(expression).longValue();
		} catch (ServiceException e) {
			this.log.error("Errore durante la countComunicazioneDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la countComunicazioneDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoComunicazione(IdComunicazione id, StatoConsegnaType stato, String dettaglioConsegna) throws Exception {
		try {
			List<UpdateField> lstFields = new ArrayList<UpdateField>();
			lstFields.add(new UpdateField(ComunicazioneSdi.model().DATA_CONSEGNA, new Date()));
			lstFields.add(new UpdateField(ComunicazioneSdi.model().STATO_CONSEGNA, stato));
			
			lstFields.add(new UpdateField(ComunicazioneSdi.model().DETTAGLIO_CONSEGNA, dettaglioConsegna));
			
			this.service.updateFields(id, lstFields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoComunicazione: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoComunicazione: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public List<ComunicazioneSdi> findAllComunicazioneDaSpedire(int offset, int limit, Date date) throws Exception {
		try {
			IPaginatedExpression expression = this.serviceSearch.toPaginatedExpression(getComunicazioneDaSpedireExpression(date));
			
			
			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(ComunicazioneSdi.model().DATA_RICEZIONE);
			expression.limit(limit);
			expression.offset(offset);
			
			return this.serviceSearch.findAll(expression);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countComunicazioneDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la countComunicazioneDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private IExpression getComunicazioneDaSpedireExpression(Date date)
			throws ServiceException, NotImplementedException,
			ExpressionNotImplementedException, ExpressionException {
		IExpression expression = this.service.newExpression();
		
		IExpression dateExpression = this.service.newExpression();
		dateExpression.lessEquals(ComunicazioneSdi.model().DATA_CONSEGNA, date).or().isNull(ComunicazioneSdi.model().DATA_CONSEGNA);

		IExpression stateExpression = this.service.newExpression();
		stateExpression.equals(ComunicazioneSdi.model().STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA).or().equals(ComunicazioneSdi.model().STATO_CONSEGNA, StatoConsegnaType.ERRORE_CONSEGNA);

		expression.and(dateExpression, stateExpression);
		return expression;
	}
}
