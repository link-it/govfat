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

import it.tesoro.fatture.EsitoOkKoTipo;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdScadenza;
import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.dao.IPccScadenzaService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneEsitoService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccScadenzaService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccTracciaTrasmissioneEsitoService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class PccScadenzaBD  extends BaseBD {

	private IPccScadenzaService scadenzaService;
	private IPccTracciaTrasmissioneEsitoService esitoService;

	public PccScadenzaBD() throws Exception {
		this(Logger.getLogger(PccScadenzaBD.class));
	}

	public PccScadenzaBD(Logger log) throws Exception {
		super(log);
		this.scadenzaService = this.serviceManager.getPccScadenzaService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
	}

	public PccScadenzaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.scadenzaService = this.serviceManager.getPccScadenzaService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
	}

	public List<PccScadenza> getScadenzeByIdFattura(IdFattura idFattura) throws ServiceException {
		return this.getScadenzeByIdFattura(idFattura, null, null);
	}
	
	
	public List<PccScadenza> getScadenzeByIdFattura(IdFattura idFattura, Integer start, Integer limit) throws ServiceException {
		return this.getScadenzeByIdFattura(idFattura, start, limit, getFilterSortList());
	}
	
	public List<PccScadenza> getScadenzeByIdFattura(IdFattura idFattura, Integer start, Integer limit,List<FilterSortWrapper> filterSortList ) throws ServiceException {
		String methodName = "getScadenzeByIdFattura";
		try {
			IExpression expr = this.scadenzaService.newExpression();

			expr.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			
			IPaginatedExpression pagExpression = this.toPaginatedExpression(this.scadenzaService, expr, start, limit,filterSortList);

			return this.scadenzaService.findAll(pagExpression);

		} catch (ServiceException e) {
			
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public long getNumeroScadenzeByIdFattura(IdFattura idFattura) throws ServiceException {
		return getNumeroScadenzeByIdFattura(idFattura, true);
	}
	
	public long getNumeroScadenzeByIdFattura(IdFattura idFattura, boolean includeAll) throws ServiceException {
		String methodName = "getScadenzeByIdFattura";
		try {
			IExpression expr = this.scadenzaService.newExpression();

			expr.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			if(!includeAll) {
				expr.lessEquals(PccScadenza.model().PAGATO_RICONTABILIZZATO, 0d);
			}
			NonNegativeNumber nnn = this.scadenzaService.count(expr);

			if(nnn != null)
				return nnn.longValue();
			
		} catch (ServiceException e) {
			
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		}
		
		return 0;
	}
	
	public void create(PccScadenza scadenza) throws ServiceException {
		try {
			this.scadenzaService.create(scadenza);
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public void update(PccScadenza scadenza) throws ServiceException {
		try {
			IdScadenza idScadenza = this.scadenzaService.convertToId(scadenza);
			this.scadenzaService.update(idScadenza, scadenza);
		} catch (ServiceException e) {
			this.log.error("Errore durante la update: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la update: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la update: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public void delete(PccScadenza scadenza) throws ServiceException {
		try {
			this.scadenzaService.delete(scadenza);
		} catch (ServiceException e) {
			this.log.error("Errore durante la delete: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la delete: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public void deleteById(Long idScadenza) throws ServiceException {
		try {
			((JDBCPccScadenzaService)this.scadenzaService).deleteById(idScadenza.longValue());
		} catch (ServiceException e) {
			this.log.error("Errore durante la deleteById: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la deleteById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public boolean exists(PccScadenza scadenza) throws ServiceException {
		try {
			
			IdScadenza id = this.scadenzaService.convertToId(scadenza);
			
			return this.scadenzaService.exists(id);
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	
	
	public void updateScadenzaByIdFattura(IdFattura idFattura,	EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		try {
			IExpression expression = this.scadenzaService.newExpression();
			expression.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			this.scadenzaService.deleteAll(expression);

		} catch (ServiceException e) {
			this.log.error("Errore durante la updateScadenzaByIdFattura: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateScadenzaByIdFattura: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
//	public void deleteScadenze(IdFattura idFattura) throws Exception {
//		this.deleteScadenze(idFattura, true);
//	}
	
	public void deleteScadenze(IdFattura idFattura, boolean deleteAll) throws Exception {
		try {
			IExpression expression = this.scadenzaService.newExpression();
			expression.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			if(!deleteAll) {
				expression.lessEquals(PccScadenza.model().PAGATO_RICONTABILIZZATO, 0d);
			}
			
			this.scadenzaService.deleteAll(expression);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la prendiInCaricoDeleteScadenze: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la prendiInCaricoDeleteScadenze: " + e.getMessage(), e);
			throw new Exception(e);
		}
		
		
	}
	
	public void updateCancellazioneComunicazioniScadenzaByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		//NOP
//		try {
//			if(esito.equals(EsitoOkKoTipo.OK)) {
//				IExpression expression = this.scadenzaService.newExpression();
//				expression.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
//				expression.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
//				this.scadenzaService.deleteAll(expression);
//			} else {
//				IPaginatedExpression expression = this.scadenzaService.newPaginatedExpression();
//
//				expression.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
//				expression.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
//
//				List<IdScadenza> findAllIds = this.scadenzaService.findAllIds(expression);
//				for(IdScadenza ids: findAllIds) {
//					this.scadenzaService.updateFields(ids, new UpdateField(PccScadenza.model().STATO, StatoOperazioneContabileType.ERRORE_CANCELLAZIONE));
//				}
//			}
//
//		} catch (ServiceException e) {
//			this.log.error("Errore durante la updateCancellazioneComunicazioniScadenzaByIdFattura: " + e.getMessage(), e);
//			throw new Exception(e);
//		} catch (NotImplementedException e) {
//			this.log.error("Errore durante la updateCancellazioneComunicazioniScadenzaByIdFattura: " + e.getMessage(), e);
//			throw new Exception(e);
//		}
	}
	
	public void updateStornoScadenzaByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		try {
			IExpression expression = this.scadenzaService.newExpression();
			expression.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			this.scadenzaService.deleteAll(expression);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStornoScadenzaByIdFattura: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStornoScadenzaByIdFattura: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public PccScadenza findById(Long idScadenza) throws ServiceException,NotFoundException {
		try {
			return ((JDBCPccScadenzaService)this.scadenzaService).get(idScadenza.longValue());
		} catch (ServiceException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.debug("findById ["+idScadenza+"] elemento non trovato");
			throw e;
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public Date getMaxDateByIdFattura(IdFattura idFattura) throws ServiceException {
		
		try {
			IPaginatedExpression expression = this.scadenzaService.newPaginatedExpression();
			expression.equals(PccScadenza.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccScadenza.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			expression.offset(0);
			expression.limit(1);
			
			expression.addOrder(PccScadenza.model().DATA_RICHIESTA, SortOrder.DESC);

			List<PccScadenza> cont = this.scadenzaService.findAll(expression);
			
			if(cont != null && !cont.isEmpty())
				return cont.get(0).getDataRichiesta();
			return null;
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la getMaxDateByIdFattura: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la getMaxDateByIdFattura: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getMaxDateByIdFattura: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public PccTracciaTrasmissioneEsito getEsitoById(Long idEsito) throws ServiceException {
		
		try {
			return ((JDBCPccTracciaTrasmissioneEsitoService)this.esitoService).get(idEsito.longValue());
		} catch (NotFoundException e) {
			this.log.debug("getEsitoById ["+idEsito+"] elemento non trovato");
			return null;
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la getEsitoById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getEsitoById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
}
