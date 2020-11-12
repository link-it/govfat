/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IDBTracciaSDIService;
import org.govmix.proxy.fatturapa.orm.dao.ITracciaSDIService;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.TracciaSdIFilter;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class TracciaSdIBD extends BaseBD {

	protected ITracciaSDIService service;

	public TracciaSdIBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getTracciaSDIService();
	}

	public TracciaSdIBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getTracciaSDIService();
	}

	public TracciaSdIBD() throws Exception {
		this(Logger.getLogger(TracciaSdIBD.class));
	}

	public void insert(TracciaSDI tracciaSdI) throws ServiceException {
		try {
			this.service.create(tracciaSdI);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}		
	}

	public TracciaSDI getById(long idFisico) throws ServiceException, NotFoundException {
		try {
			return ((IDBTracciaSDIService)this.service).get(idFisico);

		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		}
	}

	public long count(TracciaSdIFilter filter)throws ServiceException {
		try {
			return this.service.count(filter.toExpression()).longValue();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<TracciaSDI> findAll(TracciaSdIFilter filter)throws ServiceException {
		try {
			return this.service.findAll(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<Map<String,Object>> select(TracciaSdIFilter filter, IField... fields) throws ServiceException {
		try {
			return this.service.select(filter.toPaginatedExpression(), fields);
		} catch (NotFoundException e) {
			return null;
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	public TracciaSdIFilter newFilter() {
		return new TracciaSdIFilter(this.service);
	}

	public void updateStatoProtocollazioneOK(TracciaSDI tracciaSDI, StatoProtocollazioneType stato) throws Exception {
		try {
			this.service.updateFields(tracciaSDI, new UpdateField(TracciaSDI.model().STATO_PROTOCOLLAZIONE, stato), new UpdateField(TracciaSDI.model().DATA_PROTOCOLLAZIONE, new Date()));
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}		
	}

	public void updateStatoProtocollazioneKO(TracciaSDI tracciaSDI, StatoProtocollazioneType stato, String dettaglio, Date dataProssimaProtocollazione, int tentativiProtocollazione) throws Exception {
		try {
			this.service.updateFields(tracciaSDI, new UpdateField(TracciaSDI.model().STATO_PROTOCOLLAZIONE, stato), new UpdateField(TracciaSDI.model().DETTAGLIO_PROTOCOLLAZIONE, dettaglio), new UpdateField(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE, dataProssimaProtocollazione), new UpdateField(TracciaSDI.model().TENTATIVI_PROTOCOLLAZIONE, tentativiProtocollazione));
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}		
	}

}
