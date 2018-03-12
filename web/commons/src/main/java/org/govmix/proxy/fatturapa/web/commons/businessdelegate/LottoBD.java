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
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IDBLottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureService;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
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

	public long countByStatiElaborazioneInUscita(List<StatoElaborazioneType> stati, Date date)throws Exception {
		try {
			IExpression exp = getExpByStatiElaborazioneInUscita(stati, date);
			return this.service.count(exp).longValue();
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private IExpression getExpByStatiElaborazioneInUscita(List<StatoElaborazioneType> stati, Date date)
			throws ServiceException, NotImplementedException, ExpressionNotImplementedException, ExpressionException {
		IExpression exp = this.service.newExpression();
		exp.in(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA, stati);
		exp.lessEquals(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE, date);
		return exp;
	}

	public List<LottoFatture> findAllByStatiElaborazioneInUscita(List<StatoElaborazioneType> stati, Date date, int offset, int limit)throws Exception {
		try {
			IPaginatedExpression exp = this.service.toPaginatedExpression(getExpByStatiElaborazioneInUscita(stati, date));
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
	
	public void updateStatoElaborazioneInUscitaOK(IdLotto lotto, StatoElaborazioneType stato) throws Exception {
		this.updateStatoElaborazioneInUscitaOK(lotto, stato, null);
	}
	
	public void updateStatoElaborazioneInUscitaOK(IdLotto lotto, StatoElaborazioneType stato, String tipiComunicazione) throws Exception {
		try {
			List<UpdateField> list = new ArrayList<UpdateField>();
			list.add(new UpdateField(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA, stato));
			
			if(tipiComunicazione!= null)
				list.add(new UpdateField(LottoFatture.model().TIPI_COMUNICAZIONE, tipiComunicazione));
			
			list.add(new UpdateField(LottoFatture.model().DATA_ULTIMA_ELABORAZIONE, new Date()));
			list.add(new UpdateField(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE, new Date()));
			list.add(new UpdateField(LottoFatture.model().TENTATIVI_CONSEGNA, 0));
			
			this.service.updateFields(lotto, list.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoElaborazioneInUscita: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoElaborazioneInUscita: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void updateStatoElaborazioneInUscitaKO(IdLotto lotto, StatoElaborazioneType stato, Date dataProssimaElaborazione, String dettaglioElaborazione, Integer tentativiConsegna) throws Exception {
		try {
			List<UpdateField> list = new ArrayList<UpdateField>();
			list.add(new UpdateField(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA, stato));
			list.add(new UpdateField(LottoFatture.model().DATA_ULTIMA_ELABORAZIONE, new Date()));
			list.add(new UpdateField(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE, dataProssimaElaborazione));
			list.add(new UpdateField(LottoFatture.model().TENTATIVI_CONSEGNA, tentativiConsegna));
			
			if(dettaglioElaborazione != null)
				list.add(new UpdateField(LottoFatture.model().DETTAGLIO_ELABORAZIONE, dettaglioElaborazione));
			
			this.service.updateFields(lotto, list.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoElaborazioneInUscita: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoElaborazioneInUscita: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateDocumentoFirmato(LottoFatture lotto, byte[] docFirmato) throws Exception {
		try {
			this.service.updateFields(this.service.convertToId(lotto), 
					new UpdateField(LottoFatture.model().XML, docFirmato), 
					new UpdateField(LottoFatture.model().FORMATO_ARCHIVIO_INVIO_FATTURA, FormatoArchivioInvioFatturaType.P7M),
					new UpdateField(LottoFatture.model().NOME_FILE, lotto.getNomeFile() + ".p7m"));
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateDocumentoFirmato: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateDocumentoFirmato: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public StatoElaborazioneType ritentaConsegna(IdLotto lotto) throws Exception {
		try {
			
			LottoFatture lottoDaDB = this.get(lotto);
			
			StatoElaborazioneType nuovoStato = null;
			switch(lottoDaDB.getStatoElaborazioneInUscita()) {
			case ERRORE_DI_FIRMA: nuovoStato = StatoElaborazioneType.PRESA_IN_CARICO;
				break;
			case ERRORE_DI_PROTOCOLLO:  nuovoStato = StatoElaborazioneType.PRESA_IN_CARICO;
				break;
			case ERRORE_DI_SPEDIZIONE: nuovoStato = StatoElaborazioneType.PROTOCOLLATA;
				break;
			default: throw new Exception("Lo stato elaborazione ["+lottoDaDB.getStatoElaborazioneInUscita()+"] non prevede una rispedizione");
			
			}
			
			this.updateStatoElaborazioneInUscitaOK(lotto, nuovoStato);
			
			return nuovoStato;
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoElaborazioneInUscita: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoElaborazioneInUscita: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	

}
