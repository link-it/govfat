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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBC_SQLObjectFactory;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class FatturaBD extends BaseBD {

	protected IFatturaElettronicaService service;

	public FatturaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaBD() throws Exception {
		this(Logger.getLogger(FatturaBD.class));
	}

	public FatturaFilter newFilter(Boolean fatturazioneAttiva) {
		return new FatturaFilter(this.service,fatturazioneAttiva);
	}

	public void create(FatturaElettronica fattura) throws ServiceException {
		try {
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

	public FatturaElettronica getById(long idFisico) throws ServiceException, NotFoundException {
		try {
			return ((IDBFatturaElettronicaService)this.service).get(idFisico);

		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}


	public FatturaElettronica get(IdFattura id) throws NotFoundException, ServiceException {
		FatturaFilter filter = this.newFilter();
		filter.setIdentificativoSdi(id.getIdentificativoSdi());
		filter.setPosizione(id.getPosizione());

		if(this.count(filter) == 0) {
			throw new NotFoundException();
		}

		return this.findAll(filter).get(0);
	}

	public FatturaElettronica findByIdSdiNumero(Integer identificativoSdi, String numero) throws Exception {
		try {

			FatturaFilter filter = this.newFilter();
			filter.setIdentificativoSdi(identificativoSdi);
			filter.setNumero(numero);

			if(this.count(filter) == 0) {
				throw new NotFoundException();
			}

			return this.findAll(filter).get(0);

		} catch (ServiceException e) {
			throw new Exception(e);
		}
	}

	public FatturaElettronica findByCodDipartimentoNumeroData(String codiceDipartimento, String numero, Date dataFattura) throws Exception {
		try {

			FatturaFilter filter = this.newFilter();
			filter.setCodiceDestinatario(codiceDipartimento);
			filter.setNumero(numero);
			
			filter.setDataFattura(dataFattura);

			FilterSortWrapper sort = new FilterSortWrapper();
			sort.setField(FatturaElettronica.model().DATA);
			sort.setSortOrder(SortOrder.DESC);
			filter.getFilterSortList().add(sort);
			
			if(this.count(filter) == 0) {
				throw new NotFoundException();
			}

			return this.findAll(filter).get(0);

		} catch (ServiceException e) {
			throw new Exception(e);
		}
	}

	public IdFattura findId(long idFisico) throws ServiceException, NotFoundException {
		return this.convertToId(this.getById(idFisico));
	}

	public FatturaElettronica findByIdFiscaleNumeroDataImporto(String idFiscaleFornitore, String numero, Date date, Double importo) throws ServiceException, NotFoundException {
		try {


			String codNazione = null;
			String idFiscale = null;

			FatturaFilter filter = this.newFilter();

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

		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		}
	}

	public FatturaElettronica findByIdPcc(String idPcc) throws Exception {
		throw new NotImplementedException();
	}

	public boolean exists(IdFattura id) throws ServiceException {
		FatturaFilter filter = this.newFilter();
		filter.setIdentificativoSdi(id.getIdentificativoSdi());
		filter.setPosizione(id.getPosizione());
		return this.count(filter) > 0;
	}

	public List<IdFattura> findAllIdFatturaByIdLotto(IdLotto idLotto) throws ServiceException {
		FatturaFilter filter = this.newFilter();
		filter.setIdentificativoSdi(idLotto.getIdentificativoSdi());
		filter.setFatturazioneAttiva(idLotto.getFatturazioneAttiva());
		List<FatturaElettronica> findAll = this.findAll(filter);
		List<IdFattura> findAllIds = new ArrayList<IdFattura>();
		for(FatturaElettronica fatt: findAll) {
			findAllIds.add(this.convertToId(fatt));
		}

		return findAllIds;
	}

	public IdFattura convertToId(FatturaElettronica fattura) throws ServiceException {
		try {
			return this.service.convertToId(fattura);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public boolean existFatture() throws Exception {
		try {

			return this.service.count(this.service.newExpression()).longValue() > 0;
		} catch (ServiceException e) {
			this.log.error("Errore durante la existFatture: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la existFatture: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void aggiornaProtocollo(IdFattura idFattura, String protocollo) throws Exception {
		try {
			this.service.updateFields(idFattura, new UpdateField(FatturaElettronica.model().PROTOCOLLO, protocollo));
		} catch (ServiceException e) {
			this.log.error("Errore durante la aggiornaProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la aggiornaProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<IdFattura> getIdFattureByUtente(Utente utente) throws Exception {

		try {

			FatturaFilter newFilter = newFilter();
			newFilter.setUtente(utente);

			return this.service.findAllIds(newFilter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (Exception e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long count(FatturaFilter filter)throws ServiceException {
		try {
			return this.service.count(filter.toExpression()).longValue();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<FatturaElettronica> findAll(FatturaFilter filter)throws ServiceException {
		try {
			return this.service.findAll(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<IdFattura> findAllIds(FatturaFilter filter)throws ServiceException {
		try {
			return this.service.findAllIds(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<Long> findAllTableIds(FatturaFilter filter)throws ServiceException {
		try {
			return ((IDBFatturaElettronicaService)this.service).findAllTableIds(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<Map<String,Object>> select(FatturaFilter filter, IField... fields) throws ServiceException {
		try {
			return this.service.select(filter.toPaginatedExpression(), fields);
		} catch (NotFoundException e) {
			return new ArrayList<Map<String,Object>>();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<String> getListAutocomplete(FatturaFilter filter, IField field) throws ServiceException {

		List<Map<String,Object>> select = null;
		select = this.select(filter, field);
		List<String> cpValues = new ArrayList<String>();
		if(select.size() > 0) {
			for(Map<String, Object> record: select) {
				cpValues.add((String)record.get(field.getFieldName()));
			}
		}


		return cpValues;
	}

	public void assegnaIdentificativoSDIAInteroLotto(IdLotto idLotto, Integer identificativoSDI) throws Exception {
		try {

			StringBuffer update = new StringBuffer();

			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase());

			update.append("update "+converter.toTable(FatturaElettronica.model())+" set ");
			update.append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ");
			listObjects.add(identificativoSDI);

			update.append(" where ").append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ");
			update.append(" AND ").append(converter.toColumn(FatturaElettronica.model().FATTURAZIONE_ATTIVA, false)).append(" = ? ");
			listObjects.add(idLotto.getIdentificativoSdi());
			listObjects.add(idLotto.isFatturazioneAttiva());

			this.service.nativeUpdate(update.toString(), listObjects.toArray(new Object[]{}));

		} catch (ServiceException e) {
			this.log.error("Errore durante la assegnaIdentificativoSDIAInteroLotto: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la assegnaIdentificativoSDIAInteroLotto: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	public void assegnaProtocolloAInteroLotto(IdLotto idLotto, String protocollo) throws Exception {
		try {

			StringBuffer update = new StringBuffer();

			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase());

			update.append("update "+converter.toTable(FatturaElettronica.model())+" set ");
			if(protocollo != null) {
				update.append(converter.toColumn(FatturaElettronica.model().PROTOCOLLO, false)).append(" = ? , ");
				listObjects.add(protocollo);
			}

			update.append(converter.toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, false)).append(" = ? , ");
			listObjects.add(protocollo != null ? StatoProtocollazioneType.PROTOCOLLATA : StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE);

			update.append(converter.toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE, false)).append(" = ? ");
			listObjects.add(new Date());

			update.append(" where ")
			.append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ")
			.append(" AND ").append(converter.toColumn(FatturaElettronica.model().FATTURAZIONE_ATTIVA, false)).append(" = ? ");

			listObjects.add(idLotto.getIdentificativoSdi());
			listObjects.add(idLotto.getFatturazioneAttiva());

			this.service.nativeUpdate(update.toString(), listObjects.toArray(new Object[]{}));

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

	public Long getIdEsitoScadenza(long idFattura) throws ServiceException, NotFoundException {
		try {
			String idEsito = "id_scadenza";
			CustomField esitoField = new CustomField(idEsito, Long.class, idEsito, this.getRootTable(this.service));
			FatturaFilter newFilter = this.newFilter();
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
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}

	public Long getIdEsitoContabilizzazione(long idFattura) throws ServiceException, NotFoundException {
		try {
			String idEsito = "id_contabilizzazione";
			CustomField esitoField = new CustomField(idEsito, Long.class, idEsito, this.getRootTable(this.service));
			FatturaFilter newFilter = this.newFilter();
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

		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}


	public List<String> getAutocompletamentoCedentePrestatoreDenominazione(FatturaPassivaFilter filter) throws ServiceException {
		return this.getListAutocomplete(filter, FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
	}

	public List<String> getAutocompletamentoCessionarioCommittenteDenominazione(FatturaFilter filter) throws ServiceException {
		return this.getListAutocomplete(filter, FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE);
	}

	public List<String> getAutocompletamentoNumero(FatturaFilter filter) throws ServiceException {
		return this.getListAutocomplete(filter, FatturaElettronica.model().NUMERO);
	}

	public void inviaInConservazione(FatturaFilter filter, StatoConservazioneType statoConservazione) throws ServiceException {
		this.log.info("invio in conservazione fatture");

		try {

			StatoConservazioneType newStatoConservazione = StatoConservazioneType.PRESA_IN_CARICO;
			switch (statoConservazione) {
			case CONSERVAZIONE_COMPLETATA: throw new ServiceException("Operazione non valida");
			case CONSERVAZIONE_FALLITA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case ERRORE_CONSEGNA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case IN_RICONSEGNA: throw new ServiceException("Operazione non valida");
			case NON_INVIATA: newStatoConservazione= StatoConservazioneType.PRESA_IN_CARICO; break;
			case PRESA_IN_CARICO: throw new ServiceException("Operazione non valida");
			}

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase());

			ArrayList<Object> params = new ArrayList<Object>();

			ISQLQueryObject sqlQueryObject = new JDBC_SQLObjectFactory().createSQLQueryObject(this.serviceManager.getJdbcProperties().getDatabase());

			String fatturaTable = converter.toTable(FatturaElettronica.model());
			String lottiTable = converter.toTable(FatturaElettronica.model().LOTTO_FATTURE);
			String dipartimentiTable = converter.toTable(FatturaElettronica.model().DIPARTIMENTO);
			String entiTable = converter.toTable(FatturaElettronica.model().DIPARTIMENTO.ENTE);
			((JDBCExpression)filter._toExpression()).toSqlForPreparedStatementWithFromCondition(sqlQueryObject, params, fatturaTable);
			sqlQueryObject.addSelectField(fatturaTable+".id");
			sqlQueryObject.addWhereCondition(fatturaTable+".identificativo_sdi = "+lottiTable+".identificativo_sdi and "+fatturaTable+".fatturazione_attiva = "+lottiTable+".fatturazione_attiva");

			sqlQueryObject.addFromTable(dipartimentiTable);
			
			sqlQueryObject.setANDLogicOperator(true);
			sqlQueryObject.addWhereCondition(fatturaTable+".codice_destinatario="+dipartimentiTable+".codice");
			
			sqlQueryObject.addWhereCondition(entiTable+".id="+dipartimentiTable+".id_ente");
			
			ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
			
			sqlQueryObjectUpdate.addUpdateTable(fatturaTable);
			sqlQueryObjectUpdate.addUpdateField(converter.toColumn(FatturaElettronica.model().STATO_CONSERVAZIONE, false), "?");
			
			sqlQueryObjectUpdate.setANDLogicOperator(true);
			sqlQueryObjectUpdate.addWhereINSelectSQLCondition(false, "id", sqlQueryObject);

			ArrayList<Object> realParams = new ArrayList<Object>();
			realParams.add(newStatoConservazione.toString());
			realParams.addAll(params);
			this.service.nativeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), realParams.toArray());
		} catch (ServiceException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw e;
		} catch (ExpressionException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (SQLQueryObjectException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public void inviaInConservazione(List<Long> ids, StatoConservazioneType statoConservazione) throws ServiceException {
		this.log.info(String.format("invio in conservazione %d fatture", ids.size()));

		try {

			StringBuffer update = new StringBuffer();

			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase());

			update.append("update ").append(converter.toTable(FatturaElettronica.model())).append(" set ");
			update.append(converter.toColumn(FatturaElettronica.model().STATO_CONSERVAZIONE, false)).append(" = ? ");

			StatoConservazioneType newStatoConservazione = StatoConservazioneType.PRESA_IN_CARICO;
			switch (statoConservazione) {
			case CONSERVAZIONE_COMPLETATA: throw new ServiceException("Operazione non valida");
			case CONSERVAZIONE_FALLITA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case ERRORE_CONSEGNA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case IN_RICONSEGNA: throw new ServiceException("Operazione non valida");
			case NON_INVIATA: newStatoConservazione= StatoConservazioneType.PRESA_IN_CARICO; break;
			case PRESA_IN_CARICO: throw new ServiceException("Operazione non valida");
			}
			listObjects.add(newStatoConservazione .toString());

			StringBuffer questionMarks = new StringBuffer();
			for(Long id: ids) {
				if(questionMarks.length() > 0) {
					questionMarks.append(",");
				}
				questionMarks.append("?");

				listObjects.add(id);
			}
			update.append("where id in (").append(questionMarks.toString()).append(")");

			this.service.nativeUpdate(update.toString(), listObjects.toArray(new Object[]{}));

		} catch (ServiceException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	public FatturaFilter newFilter() {
		return new FatturaFilter(this.service);
	}
	public FatturaFilter newFatturaPassivaFilter() {
		return new FatturaPassivaFilter(this.service);
	}
	public FatturaFilter newFatturaAttivaFilter() {
		return new FatturaAttivaFilter(this.service);
	}


	public void assegnaIdSip(FatturaElettronica fattura, Long id) throws ServiceException {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField idSipCustomField = new CustomField("id_sip", Long.class, "id_sip", converter.toTable(FatturaElettronica.model()));
			UpdateField sipUpdateField = new UpdateField(idSipCustomField, id);

			((IDBFatturaElettronicaService)this.service).updateFields(fattura.getId(), sipUpdateField);
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

	public void updateStatoConservazione(FatturaElettronica fattura, StatoConservazioneType statoConsegna) throws Exception {
		try {
			List<UpdateField> fields = new ArrayList<UpdateField>();
			fields.add(new UpdateField(FatturaElettronica.model().STATO_CONSERVAZIONE, statoConsegna));
			IdFattura idFAttura = this.service.convertToId(fattura);
			this.service.updateFields(idFAttura, fields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}
	public List<FatturaElettronica> getFattureDaConservare(Date dataLimite, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.toPaginatedExpression(getFattureDaConservareExpression(dataLimite));

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(FatturaElettronica.model().DATA_RICEZIONE);

			expression.offset(offset);
			expression.limit(limit);

			return this.service.findAll(expression);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	private IExpression getFattureDaConservareExpression(Date dataLimite) throws Exception {
		IExpression expression = this.service.newExpression();
		expression.in(FatturaElettronica.model().ID_SIP.STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA, StatoConsegnaType.IN_RICONSEGNA);
		expression.lessEquals(FatturaElettronica.model().ID_SIP.DATA_ULTIMA_CONSEGNA, dataLimite);
		return expression;
	}

}
