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
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

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
	
	public List<IdFattura> findAllIdFatturaByIdentificativoSdi(Integer identificativoSdI) throws ServiceException {
		FatturaFilter filter = this.newFilter();
		filter.setIdentificativoSdi(identificativoSdI);
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

	public List<IdFattura> getIdFattureByUtente(Utente utente) throws Exception {

		try {
			IPaginatedExpression expression = this.service.newPaginatedExpression();
			List<String> dipartimenti = new ArrayList<String>();

			for(UtenteDipartimento id: utente.getUtenteDipartimentoList()) {
				dipartimenti.add(id.getIdDipartimento().getCodice());
			}

			expression.in(FatturaElettronica.model().CODICE_DESTINATARIO, dipartimenti.toArray());
			return this.service.findAllIds(expression);
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

	public List<Map<String,Object>> select(FatturaFilter filter, IField... fields) throws ServiceException {
		try {
			return this.service.select(filter.toPaginatedExpression(), fields);
		} catch (NotFoundException e) {
			return null;
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<String> getListAutocomplete(FatturaFilter filter, IField field) throws ServiceException {
		
		List<Map<String,Object>> select = this.select(filter, field);

		List<String> cpValues = new ArrayList<String>();
		if(select.size() > 0) {
			for(Map<String, Object> record: select) {
				cpValues.add((String)record.get(field));
			}
		}
		
		return cpValues;
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


	public FatturaFilter newFilter() {
		return new FatturaFilter(this.service);
	}

}
