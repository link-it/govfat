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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IExtendedLottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureService;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class LottoBD extends BaseBD {

	private ILottoFattureService service;
	private IExtendedLottoFattureServiceSearch serviceSearch;

	public LottoBD() throws Exception {
		this(Logger.getLogger(LottoBD.class));
	}

	public LottoBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getLottoFattureService();
		this.serviceSearch = this.serviceManager.getExtendedLottoFattureServiceSearch();
	}


	public LottoBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getLottoFattureService();
		this.serviceSearch = this.serviceManager.getExtendedLottoFattureServiceSearch();
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

	public List<LottoFatture> getLottiDaInserire(Date dataRicezione, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.serviceSearch.newPaginatedExpression();
			expression.lessEquals(LottoFatture.model().DATA_RICEZIONE, dataRicezione);
			expression.equals(LottoFatture.model().STATO_INSERIMENTO, StatoInserimentoType.NON_INSERITO);

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(LottoFatture.model().DATA_RICEZIONE);
			
			expression.offset(offset);
			expression.limit(limit);
			
			return this.serviceSearch.findAll(expression);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public long countLottiDaInserire(Date dataRicezione) throws Exception {
		try {
			IExpression expression = this.serviceSearch.newExpression();
			expression.lessEquals(LottoFatture.model().DATA_RICEZIONE, dataRicezione);
			expression.equals(LottoFatture.model().STATO_INSERIMENTO, StatoInserimentoType.NON_INSERITO);
			
			return this.serviceSearch.count(expression).longValue();
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public List<LottoFatture> getLottiDaConsegnare(Date dataRicezione, int offset, int limit) throws Exception {
		try {
			return this.serviceSearch.findAllLottiPush(dataRicezione, offset, limit);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public LottoFatture get(IdLotto idLotto) throws Exception {
		try {
			return this.serviceSearch.get(idLotto);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public List<LottoFatture> getLottiDaAssociare(Date dataRicezione, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.newPaginatedExpression();
			
			IPaginatedExpression ricezioneNull = this.service.newPaginatedExpression();
			ricezioneNull.isNull(LottoFatture.model().DATA_PROTOCOLLAZIONE);

			IPaginatedExpression ricezioneLessEquals = this.service.newPaginatedExpression();
			ricezioneLessEquals.lessEquals(LottoFatture.model().DATA_PROTOCOLLAZIONE, dataRicezione);

			expression.or(ricezioneNull, ricezioneLessEquals);
			
			IPaginatedExpression protocollataInElaborazione = this.service.newPaginatedExpression();
			protocollataInElaborazione.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA_IN_ELABORAZIONE);
			
			IPaginatedExpression erroreProtocollazione = this.service.newPaginatedExpression();
			erroreProtocollazione.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE);
			
			expression.or(protocollataInElaborazione, erroreProtocollazione);
			
			

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(LottoFatture.model().DATA_PROTOCOLLAZIONE);
			
			expression.offset(offset);
			expression.limit(limit);
			
			return this.service.findAll(expression);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}
	
	public long countLottiDaAssociare(Date dataRicezione) throws Exception {
		try {
			IExpression expression = this.service.newExpression();
			
			IExpression ricezioneNull = this.service.newPaginatedExpression();
			ricezioneNull.isNull(LottoFatture.model().DATA_PROTOCOLLAZIONE);

			IExpression ricezioneLessEquals = this.service.newPaginatedExpression();
			ricezioneLessEquals.lessEquals(LottoFatture.model().DATA_PROTOCOLLAZIONE, dataRicezione);

			expression.or(ricezioneNull, ricezioneLessEquals);
			
			IExpression protocollataInElaborazione = this.service.newPaginatedExpression();
			protocollataInElaborazione.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA_IN_ELABORAZIONE);
			
			IExpression erroreProtocollazione = this.service.newPaginatedExpression();
			erroreProtocollazione.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE);
			
			expression.or(protocollataInElaborazione, erroreProtocollazione);
			
			return this.service.count(expression).longValue();
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public long countLottiDaConsegnare(Date dataRicezione) throws Exception {
		try {
			return this.serviceSearch.countLottiPush(dataRicezione);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}


	public void setProcessato(IdLotto idLotto) throws Exception {
		this.setProcessato(idLotto, false);
	}
	
	public void setProcessato(IdLotto idLotto, boolean errore) throws Exception {
		try {
			StatoInserimentoType stato = errore ? StatoInserimentoType.ERRORE_INSERIMENTO :StatoInserimentoType.INSERITO;
			UpdateField updateField = new UpdateField(LottoFatture.model().STATO_INSERIMENTO, stato);
			this.service.updateFields(idLotto, updateField);
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

//	public void assegnaProtocollo(IdLotto idLotto, String protocollo) throws Exception {
//		try {
//
//			List<UpdateField> lst = new ArrayList<UpdateField>();
//			
//			lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA));
//			lst.add(new UpdateField(LottoFatture.model().DATA_PROTOCOLLAZIONE, new Date()));
//			lst.add(new UpdateField(LottoFatture.model().PROTOCOLLO, protocollo));
//			
//			this.service.updateFields(idLotto, lst.toArray(new UpdateField[1]));
//		} catch (ServiceException e) {
//			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
//			throw new Exception(e);
//		} catch (NotImplementedException e) {
//			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
//			throw new Exception(e);
//		}
//	}

	public void erroreProtocollo(IdLotto idLotto) throws Exception {
		try {

			List<UpdateField> lst = new ArrayList<UpdateField>();
			
			lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE));
			lst.add(new UpdateField(LottoFatture.model().DATA_PROTOCOLLAZIONE, new Date()));
			
			this.service.updateFields(idLotto, lst.toArray(new UpdateField[1]));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void updateProtocollo(IdLotto idLotto, String protocollo,boolean asincrono) throws Exception {
		try {

			List<UpdateField> lst = new ArrayList<UpdateField>();
			lst.add(new UpdateField(LottoFatture.model().STATO_CONSEGNA, StatoConsegnaType.CONSEGNATA));
			lst.add(new UpdateField(LottoFatture.model().DATA_CONSEGNA, new Date()));
			
			if(asincrono) {
				lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA_IN_ELABORAZIONE));
				lst.add(new UpdateField(LottoFatture.model().PROTOCOLLO, protocollo));
			} else if(protocollo != null) {
				lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA));
				lst.add(new UpdateField(LottoFatture.model().DATA_PROTOCOLLAZIONE, new Date()));
				lst.add(new UpdateField(LottoFatture.model().PROTOCOLLO, protocollo));
			} else {
				lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.NON_PROTOCOLLATA));
			}
			
			this.service.updateFields(idLotto, lst.toArray(new UpdateField[1]));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoConsegna(IdLotto idLotto, String response,String detail) throws Exception {
		try {
			UpdateField statoConsegnaField = new UpdateField(LottoFatture.model().STATO_CONSEGNA, StatoConsegnaType.ERRORE_CONSEGNA);
			UpdateField dataConsegnaField = new UpdateField(LottoFatture.model().DATA_CONSEGNA, new Date());
			UpdateField dettaglioConsegnaField = new UpdateField(LottoFatture.model().DETTAGLIO_CONSEGNA, detail + ": " + response);
			this.service.updateFields(idLotto, statoConsegnaField, dataConsegnaField, dettaglioConsegnaField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoProtocollazioneOK(IdLotto idLotto) throws Exception {
		try {
			UpdateField statoProtocollazioneField = new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA);
			UpdateField dataProtocollazioneField = new UpdateField(LottoFatture.model().DATA_PROTOCOLLAZIONE, new Date());
			this.service.updateFields(idLotto, statoProtocollazioneField, dataProtocollazioneField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoProtocollazioneOK: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoProtocollazioneOK: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public boolean exists(IdLotto idLotto)throws Exception {
		try {
			return this.serviceSearch.exists(idLotto);
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}

	}

}
