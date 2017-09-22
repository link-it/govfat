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
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureService;
import org.openspcoop2.generic_project.beans.UpdateField;
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

	public long countByStatiElaborazioneInUscita(List<StatoElaborazioneType> stati)throws Exception {
		try {
			IExpression exp = this.service.newExpression();
			exp.in(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA, stati);
			return this.service.count(exp).longValue();
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<LottoFatture> findAllByStatiElaborazioneInUscita(List<StatoElaborazioneType> stati, int offset, int limit)throws Exception {
		try {
			IPaginatedExpression exp = this.service.newPaginatedExpression();
			exp.in(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA, stati);
			exp.offset(offset);
			exp.limit(limit);
			exp.addOrder(LottoFatture.model().DATA_RICEZIONE, SortOrder.ASC);
			return this.service.findAll(exp);
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void updateStatoElaborazioneInUscita(IdLotto lotto, StatoElaborazioneType stato) throws Exception {
		try {
			this.service.updateFields(lotto, new UpdateField(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA, stato));
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateDocumentoFirmato(IdLotto lotto, byte[] docFirmato) throws Exception {
		try {
			this.service.updateFields(lotto, new UpdateField(LottoFatture.model().XML, docFirmato), new UpdateField(LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA, FormatoArchivioInvioFatturaType.P7M));
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

}
