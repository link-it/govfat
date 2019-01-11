/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.dao.IDBLottoFattureService;
import org.govmix.proxy.fatturapa.orm.dao.IDBLottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.LottoFattureFieldConverter;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class LottoBD extends BaseBD {

	protected ILottoFattureService service;

	public LottoBD() throws Exception {
		this(Logger.getLogger(LottoBD.class));
	}

	public LottoBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getLottoFattureService();
	}


	public LottoBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getLottoFattureService();
	}

	public void create(LottoFatture lotto) throws Exception {
		try {
			this.service.create(lotto, this.validate);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public LottoFatture get(IdLotto idLotto) throws Exception {
		try {
			return this.service.get(idLotto);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public LottoFatture getById(Long idLotto) throws Exception {
		try {
			return ((IDBLottoFattureServiceSearch)this.service).get(idLotto);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public LottoFatture getByMessageId(String msgid) throws Exception {
		try {
			IExpression exp = this.service.newExpression();
			exp.equals(LottoFatture.model().MESSAGE_ID, msgid);
			return this.service.find(exp);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public IdLotto convertToId(LottoFatture lotto) throws Exception {
		try {
			return this.service.convertToId(lotto);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public boolean exists(IdLotto idLotto)throws Exception {
		try {
			return this.service.exists(idLotto);
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void assegnaIdSip(LottoFatture lottoFatture, Long id) throws ServiceException {
		try {
			LottoFattureFieldConverter converter = new LottoFattureFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField idSipCustomField = new CustomField("id_sip", Long.class, "id_sip", converter.toTable(LottoFatture.model()));
			UpdateField sipUpdateField = new UpdateField(idSipCustomField, id);

			((IDBLottoFattureService)this.service).updateFields(lottoFatture.getId(), sipUpdateField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public List<LottoFatture> getLottiDaConservare(Date dataLimite, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.toPaginatedExpression(getLottiDaConservareExpression(dataLimite));

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(LottoFatture.model().DATA_RICEZIONE);

			expression.offset(offset);
			expression.limit(limit);

			return this.service.findAll(expression);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	private IExpression getLottiDaConservareExpression(Date dataLimite) throws Exception {
		IExpression expression = this.service.newExpression();
		expression.in(LottoFatture.model().ID_SIP.STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA, StatoConsegnaType.IN_RICONSEGNA);
		expression.lessEquals(LottoFatture.model().ID_SIP.DATA_ULTIMA_CONSEGNA, dataLimite);
		return expression;
	}

	public long countLottiDaConservare(Date dataLimite) throws Exception {
		try {
			IExpression expression = getLottiDaConservareExpression(dataLimite);

			return this.service.count(expression).longValue();
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

}
