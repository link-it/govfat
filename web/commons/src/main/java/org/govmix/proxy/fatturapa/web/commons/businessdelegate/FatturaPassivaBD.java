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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.govmix.proxy.pcc.fatture.tracciamento.OperazioneNonPermessaException;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class FatturaPassivaBD extends BaseBD {

	private IFatturaElettronicaService service;

	public FatturaPassivaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaPassivaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaPassivaBD() throws Exception {
		this(Logger.getLogger(FatturaPassivaBD.class));
	}

	public void createFatturaPassiva(FatturaElettronica fattura) throws ServiceException {
		try {
			fattura.setFatturazioneAttiva(false);
			this.service.create(fattura);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	public void validate(FatturaElettronica fattura) throws Exception {
		try {

			this.service.validate(fattura);
		} catch (ServiceException e) {
			this.log.error("Errore durante la validate: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la validate: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long count(FatturaPassivaFilter filter)throws ServiceException {
		try {
			return this.service.count(filter.toExpression()).longValue();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<Map<String,Object>> select(FatturaPassivaFilter filter, IField... fields) throws ServiceException {
		try {
			return this.service.select(filter.toPaginatedExpression(), fields);
		} catch (NotFoundException e) {
			return null;
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<FatturaElettronica> findAll(FatturaPassivaFilter filter)throws ServiceException {
		try {
			return this.service.findAll(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	public FatturaPassivaFilter newFilter() {
		return new FatturaPassivaFilter(this.service);
	}
	
	public FatturaElettronica get(IdFattura id) throws Exception {
		try {

			FatturaPassivaFilter filter = this.newFilter();
			filter.setIdentificativoSdi(id.getIdentificativoSdi());
			filter.setPosizione(id.getPosizione());
			
			if(this.count(filter) == 0) {
				throw new NotFoundException("Fattura passiva ["+id.toJson()+"] non esistente nel sistema.");
			}

			return this.findAll(filter).get(0);
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void updateEsito(IdFattura idFattura, EsitoType esito) throws Exception {
		try {
			UpdateField esitoField = new UpdateField(FatturaElettronica.model().ESITO, esito);
			this.service.updateFields(idFattura, esitoField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	public FatturaElettronica findByIdSdiNumero(Integer identificativoSdi, String numero) throws Exception {
		try {

			FatturaPassivaFilter filter = this.newFilter();
			filter.setIdentificativoSdi(identificativoSdi);
			filter.setNumero(numero);
			
			if(this.count(filter) == 0) {
				throw new NotFoundException();
			}

			return this.findAll(filter).get(0);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public IdFattura convertToId(FatturaElettronica fattura) throws Exception {
		try {
			return this.service.convertToId(fattura);
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public IdFattura findId(long idFisico) throws Exception {
		try {
			return this.service.convertToId(this.getById(idFisico));

		} catch (ServiceException e) {
			this.log.error("Errore durante la findId: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findId: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public FatturaElettronica getById(long idFisico) throws Exception {
		try {
			return ((IDBFatturaElettronicaServiceSearch)this.service).get(idFisico);

		} catch (ServiceException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public FatturaElettronica findByIdFiscaleNumeroDataImporto(String idFiscaleFornitore, String numero, Date date, Double importo) throws Exception {
		try {

			
			String codNazione = null;
			String idFiscale = null;

			FatturaPassivaFilter filter = this.newFilter();

			if(idFiscaleFornitore.length() > 2) {
				codNazione = idFiscaleFornitore.substring(0, 2);
				idFiscale = idFiscaleFornitore.substring(2);
				filter.setCpPaese(codNazione);
			} else {
				idFiscale = idFiscaleFornitore;
			}

			filter.setCpCodiceFiscale(idFiscale);
			filter.setNumero(numero);
			
			Calendar dataMin = Calendar.getInstance();
			dataMin.setTimeInMillis(date.getTime());
			dataMin.set(Calendar.HOUR, 0);
			dataMin.set(Calendar.MINUTE, 0);
			dataMin.set(Calendar.SECOND, 0);
			
			Calendar dataMax = Calendar.getInstance();
			dataMax.setTimeInMillis(dataMin.getTimeInMillis());
			dataMax.add(Calendar.DATE, 1);
			
			filter.setDataFatturaMin(dataMin.getTime());
			filter.setDataFatturaMax(dataMax.getTime());
			
			if(importo != null)
				filter.setImporto(importo);

			List<FatturaElettronica> findAllIds = this.findAll(filter);
			
			if(findAllIds == null || findAllIds.isEmpty())
				throw new NotFoundException();
			if(findAllIds.size() > 1)
				throw new MultipleResultException();
			
			return findAllIds.get(0);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public FatturaElettronica findByIdPcc(String idPcc) throws Exception {
		throw new NotImplementedException();
	}
	
	public boolean exists(IdFattura id) throws Exception {
		try {
			FatturaPassivaFilter filter = this.newFilter();
			filter.setIdentificativoSdi(id.getIdentificativoSdi());
			filter.setPosizione(id.getPosizione());
			return this.count(filter) > 0;
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public List<IdFattura> findAllIdFatturaByIdentificativoSdi(Integer identificativoSdI) throws Exception {
		try {
			FatturaPassivaFilter filter = this.newFilter();
			filter.setIdentificativoSdi(identificativoSdI);
			List<FatturaElettronica> findAll = this.findAll(filter);
			List<IdFattura> findAllIds = new ArrayList<IdFattura>();
			for(FatturaElettronica fatt: findAll) {
				findAllIds.add(this.convertToId(fatt));
			}
			
			return findAllIds;
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateDecorrenzaTermini(IdFattura idF,
			IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws Exception {
		try {
			FatturaElettronica fattura = this.get(idF);
			fattura.setIdDecorrenzaTermini(idNotificaDecorrenzaTermini);
			this.service.update(idF, fattura);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void assegnaProtocolloAInteroLotto(IdLotto idLotto, String protocollo) throws Exception {
		try {

			StringBuffer update = new StringBuffer();

			List<Class<?>> listClass = new ArrayList<Class<?>>();
			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase());
			
			update.append("update "+converter.toTable(FatturaElettronica.model())+" set ");
			if(protocollo != null) {
				update.append(converter.toColumn(FatturaElettronica.model().PROTOCOLLO, false)).append(" = ? , ");
				listClass.add(FatturaElettronica.model().PROTOCOLLO.getFieldType());
				listObjects.add(protocollo);
			}

			update.append(converter.toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, false)).append(" = ? , ");
			listClass.add(FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType());
			listObjects.add(protocollo != null ? StatoProtocollazioneType.PROTOCOLLATA : StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE);
			
			update.append(converter.toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE, false)).append(" = ? ");
			listClass.add(FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType());
			listObjects.add(new Date());
			
			update.append(" where ").append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ");
			listClass.add(FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType());
			listObjects.add(idLotto.getIdentificativoSdi());
			
			this.service.nativeQuery(update.toString(), listClass, listObjects);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void erroreProtocolloAInteroLotto(IdLotto idLotto) throws Exception {
		this.assegnaProtocolloAInteroLotto(idLotto, null);
	}
	
	public List<FatturaElettronica> getFattureDaSpedireContestuale(int offset, int limit, Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, true);
			filter.setOffset(offset);
			filter.setLimit(limit);
			return this.findAll(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaSpedireContestuale(Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, true);
			return this.count(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	public List<FatturaElettronica> getFattureDaSpedire(int offset, int limit, Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, false);
			return this.findAll(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaSpedire(Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, false);
			return this.count(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private FatturaPassivaFilter getFattureDaSpedireFilter(Date date, boolean contestuale) {
		FatturaPassivaFilter filter = this.newFilter();
		filter.setModalitaPush(true);
		filter.setConsegnaContestuale(contestuale);
		filter.setDataRicezioneMin(date);
		List<StatoConsegnaType> statiConsegna = new ArrayList<StatoConsegnaType>();
		statiConsegna.add(StatoConsegnaType.NON_CONSEGNATA);
		statiConsegna.add(StatoConsegnaType.IN_RICONSEGNA);
		filter.setStatiConsegna(statiConsegna);
		return filter;
	}

	public List<FatturaElettronica> getFattureDaAccettare(int offset, int limit, Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaAccettareFilter(date);
			filter.setOffset(offset);
			filter.setLimit(limit);
			return this.findAll(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaAccettare(Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaAccettareFilter(date);
			return this.count(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private FatturaPassivaFilter getFattureDaAccettareFilter(Date date) {
		FatturaPassivaFilter filter = this.newFilter();
		filter.setDataRicezioneMin(date);
		filter.setDaAccettareAutomaticamente(true);
		return filter;
	}

	public void updateProtocollo(IdFattura idFattura, StatoProtocollazioneType statoProtocollazioneAttuale, String protocollo, boolean asincrono) throws Exception {
		try {
			
			List<UpdateField> lst = new ArrayList<UpdateField>();
			lst.add(new UpdateField(FatturaElettronica.model().STATO_CONSEGNA, StatoConsegnaType.CONSEGNATA));
			lst.add(new UpdateField(FatturaElettronica.model().DETTAGLIO_CONSEGNA, null));
			lst.add(new UpdateField(FatturaElettronica.model().DATA_CONSEGNA, new Date()));
			
			if(asincrono) {
				 // Lo stato potrebbe essere gia' stato aggiornato nel frattempo dal passaggio del batch di associazione protocollo
				// Se non e' stato aggiornato, lo aggiorno 
				if(!StatoProtocollazioneType.PROTOCOLLATA.equals(statoProtocollazioneAttuale))
					lst.add(new UpdateField(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA_IN_ELABORAZIONE));
			} else if(protocollo != null) {
				lst.add(new UpdateField(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA));
				lst.add(new UpdateField(FatturaElettronica.model().DATA_PROTOCOLLAZIONE, new Date()));
				lst.add(new UpdateField(FatturaElettronica.model().PROTOCOLLO, protocollo));
			} else {
				lst.add(new UpdateField(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.NON_PROTOCOLLATA));
			}

			this.service.updateFields(idFattura, lst.toArray(new UpdateField[1]));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoConsegna(IdFattura idFattura, String dettaglio) throws Exception {
		this.updateStatoConsegna(idFattura, StatoConsegnaType.ERRORE_CONSEGNA, dettaglio);
	}

	public void updateStatoConsegna(IdFattura idFattura, StatoConsegnaType statoConsegna, String dettaglio) throws Exception {
		try {
			List<UpdateField> lstFields = new ArrayList<UpdateField>();
			lstFields.add(new UpdateField(FatturaElettronica.model().STATO_CONSEGNA, statoConsegna));
			lstFields.add(new UpdateField(FatturaElettronica.model().DATA_CONSEGNA, new Date()));
			if(dettaglio != null)
				lstFields.add(new UpdateField(FatturaElettronica.model().DETTAGLIO_CONSEGNA, dettaglio));
			
			this.service.updateFields(idFattura, lstFields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoConsegna(FatturaElettronica fattura) throws Exception {
		try {
			List<UpdateField> lstFields = new ArrayList<UpdateField>();
			lstFields.add(new UpdateField(FatturaElettronica.model().STATO_CONSEGNA, fattura.getStatoConsegna()));
			lstFields.add(new UpdateField(FatturaElettronica.model().DATA_CONSEGNA, new Date()));
			lstFields.add(new UpdateField(FatturaElettronica.model().DETTAGLIO_CONSEGNA, fattura.getDettaglioConsegna()));
			lstFields.add(new UpdateField(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA, fattura.getDataProssimaConsegna()));
			lstFields.add(new UpdateField(FatturaElettronica.model().TENTATIVI_CONSEGNA, fattura.getTentativiConsegna()));

			this.service.updateFields(this.service.convertToId(fattura), lstFields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void forzaRispedizioneFattura(FatturaElettronica fattura) throws Exception {
		try {
			IdFattura idFattura = this.convertToId(fattura); 
			FatturaElettronica fatturaDaDB = this.get(idFattura);
			if(StatoConsegnaType.ERRORE_CONSEGNA.equals(fatturaDaDB.getStatoConsegna()) || StatoConsegnaType.IN_RICONSEGNA.equals(fatturaDaDB.getStatoConsegna())) {
				fatturaDaDB.setStatoConsegna(StatoConsegnaType.IN_RICONSEGNA);
				fatturaDaDB.setDataProssimaConsegna(new Date());
				fatturaDaDB.setTentativiConsegna(new Integer(0));
				this.service.update(idFattura, fatturaDaDB);
			} else
				throw new Exception("La fattura ["+idFattura.toJson()+"] non puo' essere forzata in riconsegna perche' il suo stato attuale e' ["+fatturaDaDB.getStatoConsegna()+"]");
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la forzaRispedizioneFattura: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la forzaRispedizioneFattura: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	public void updateEsitoScadenza(IdFattura idFattura, PccTracciaTrasmissioneEsito esito)  throws Exception {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField esitoCustomField = new CustomField("id_scadenza", Long.class, "id_scadenza", converter.toTable(FatturaElettronica.model()));
			UpdateField esitoField = null;
			if(esito != null) {
				esitoField = new UpdateField(esitoCustomField, esito.getId());
			}else {
				esitoField = new UpdateField(esitoCustomField, null);					
			}
			this.service.updateFields(idFattura, esitoField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void checkEsitoScadenza(IdFattura idFattura)  throws Exception {
		try {
			FatturaElettronica f = this.get(idFattura);
			if((f.getIdEsitoScadenza() == null || f.getIdEsitoScadenza().getIdTrasmissioneEsito() < 0)) {
				throw new OperazioneNonPermessaException("Fattura ["+idFattura.toJson()+"] in stato di elaborazione, impossibile inviare un piano di scadenze");
			}
		} catch (ServiceException e) {
			this.log.error("Errore durante la checkEsitoScadenza: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la checkEsitoScadenza: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateEsitoContabilizzazione(IdFattura idFattura, PccTracciaTrasmissioneEsito esito)  throws Exception {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField esitoCustomField = new CustomField("id_contabilizzazione", Long.class, "id_contabilizzazione", converter.toTable(FatturaElettronica.model()));
			UpdateField esitoField = null;
			if(esito != null) {
				esitoField = new UpdateField(esitoCustomField, esito.getId());
			}else {
				esitoField = new UpdateField(esitoCustomField, null);					
			}

			this.service.updateFields(idFattura, esitoField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void checkEsitoContabilizzazione(IdFattura idFattura)  throws Exception {
		try {
			FatturaElettronica f = this.get(idFattura);
			if((f.getIdEsitoContabilizzazione() == null || f.getIdEsitoContabilizzazione().getIdTrasmissioneEsito() < 0)) {
				throw new OperazioneNonPermessaException("Fattura ["+idFattura.toJson()+"] in stato di elaborazione, impossibile inviare un piano di contabilizzazioni");
			}

		} catch (ServiceException e) {
			this.log.error("Errore durante la checkEsitoContabilizzazione: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la checkEsitoContabilizzazione: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateDataScadenza(IdFattura idFattura, Date dataScadenza, boolean daPagare) throws Exception {
		try {
			
			UpdateField updateField = new UpdateField(FatturaElettronica.model().DATA_SCADENZA, dataScadenza);
			UpdateField updateField2 = new UpdateField(FatturaElettronica.model().DA_PAGARE, daPagare);

			this.service.updateFields(idFattura, updateField, updateField2);
		} catch (ServiceException e) {
			this.log.error("Errore durante la aggiornaDataScadenza: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la aggiornaDataScadenza: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public Long getIdEsitoScadenza(long idFattura) throws ServiceException, NotFoundException, MultipleResultException {
		try {
			String idEsito = "id_scadenza";
			CustomField esitoField = new CustomField(idEsito, Long.class, idEsito, this.getRootTable(this.service));
			FatturaPassivaFilter newFilter = newFilter();
			newFilter.setId(idFattura);
			
			List<Map<String,Object>> select = this.select(newFilter, esitoField);

			if(select.size() > 1) {
				throw new MultipleResultException();
			} else if(select.size() > 0) {
				Object idEsitoObj = select.get(0).get(idEsito);
				if(idEsitoObj instanceof Long)
					return (Long) idEsitoObj;
				else
					return null;
			} else {
				throw new NotFoundException();
			}

		} catch (ExpressionException e) {
			throw new ServiceException(e);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Long getIdEsitoContabilizzazione(long idFattura) throws ServiceException, NotFoundException, MultipleResultException {
		try {
			String idEsito = "id_contabilizzazione";
			CustomField esitoField = new CustomField(idEsito, Long.class, idEsito, this.getRootTable(this.service));
			FatturaPassivaFilter newFilter = newFilter();
			newFilter.setId(idFattura);
			
			List<Map<String,Object>> select = this.select(newFilter, esitoField);

			if(select.size() > 1) {
				throw new MultipleResultException();
			} else if(select.size() > 0) {
				Object idEsitoObj = select.get(0).get(idEsito);
				if(idEsitoObj instanceof Long)
					return (Long) idEsitoObj;
				else
					return null;
			} else {
				throw new NotFoundException();
			}

		} catch (ExpressionException e) {
			throw new ServiceException(e);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public List<String> getAutocompletamentoCedentePrestatoreDenominazione(String valore) throws ServiceException {
		IField field = FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE;
		FatturaPassivaFilter newFilter = newFilter();
		newFilter.getCpDenominazioneList().add(valore);
		
		List<Map<String,Object>> select = this.select(newFilter, field);

		List<String> cpValues = new ArrayList<String>();
		if(select.size() > 0) {
			for(Map<String, Object> record: select) {
				cpValues.add((String)record.get(field));
			}
		}
		
		return cpValues;
	}
	
	public List<String> getAutocompletamentoNumero(String valore) throws ServiceException {
		IField field = FatturaElettronica.model().NUMERO;
		FatturaPassivaFilter newFilter = newFilter();
		newFilter.setNumeroLike(valore);
		
		List<Map<String,Object>> select = this.select(newFilter, field);

		List<String> cpValues = new ArrayList<String>();
		if(select.size() > 0) {
			for(Map<String, Object> record: select) {
				cpValues.add((String)record.get(field));
			}
		}
		
		return cpValues;
	}

}
