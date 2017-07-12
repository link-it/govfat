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

import it.tesoro.fatture.EsitoOkKoTipo;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdPagamento;
import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.dao.IPccPagamentoService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneEsitoService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccPagamentoService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccTracciaTrasmissioneEsitoService;
import org.openspcoop2.generic_project.beans.Function;
import org.openspcoop2.generic_project.beans.FunctionField;
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

public class PccPagamentoBD  extends BaseBD {

	private IPccPagamentoService pagamentoService;
	private IPccTracciaTrasmissioneEsitoService esitoService;

	public PccPagamentoBD() throws Exception {
		this(Logger.getLogger(PccPagamentoBD.class));
	}

	public PccPagamentoBD(Logger log) throws Exception {
		super(log);
		this.pagamentoService = this.serviceManager.getPccPagamentoService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
	}

	public PccPagamentoBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.pagamentoService = this.serviceManager.getPccPagamentoService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
	}

	public List<PccPagamento> getPagamentiByIdFattura(IdFattura idFattura) throws ServiceException {
		return this.getPagamentiByIdFattura(idFattura, null, null);
	}

	public List<PccPagamento> getPagamentiByIdFattura(IdFattura idFattura,Integer start, Integer limit) throws ServiceException {
		return this.getPagamentiByIdFattura(idFattura, start, limit, getFilterSortList());	
	}

	public List<PccPagamento> getPagamentiByIdFattura(IdFattura idFattura,Integer start, Integer limit,List<FilterSortWrapper> filterSortList) throws ServiceException {
		String methodName = "getPagamentiByIdFattura";
		try {
			IExpression expr = this.pagamentoService.newExpression();

			expr.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			IPaginatedExpression pagExpression = this.toPaginatedExpression(this.pagamentoService, expr, start, limit,filterSortList);

			return this.pagamentoService.findAll(pagExpression);

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

	public long getNumeroPagamentiByIdFattura(IdFattura idFattura) throws ServiceException {
		String methodName = "getPagamentiByIdFattura";
		try {
			IExpression expr = this.pagamentoService.newExpression();

			expr.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			NonNegativeNumber nnn = this.pagamentoService.count(expr);

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

	public boolean existPagamentiByIdFattura(IdFattura idFattura) throws ServiceException {
		String methodName = "getPagamentiByIdFattura";
		try {
			IPaginatedExpression expr = this.pagamentoService.newPaginatedExpression();

			expr.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			List<PccPagamento> lst = this.pagamentoService.findAll(expr);
			
			double importo = 0;
			if(lst != null && !lst.isEmpty()) {
				for(PccPagamento pag: lst) {
					importo += pag.getImportoPagato();
				}
			}
			return importo > 0;
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

	public void create(PccPagamento pagamento) throws ServiceException {
		try {

			IdPagamento id = this.pagamentoService.convertToId(pagamento);

			if(this.pagamentoService.exists(id)) {
				this.pagamentoService.update(id, pagamento);
			} else {
				this.pagamentoService.create(pagamento);
			}

		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public void delete(PccPagamento pagamento) throws ServiceException {
		try {
			this.pagamentoService.delete(pagamento);
		} catch (ServiceException e) {
			this.log.error("Errore durante la delete: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la delete: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public void deleteById(Long idPagamento) throws ServiceException {
		try {
			((JDBCPccPagamentoService)this.pagamentoService).deleteById(idPagamento.longValue());
		} catch (ServiceException e) {
			this.log.error("Errore durante la deleteById: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la deleteById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public boolean exists(PccPagamento pagamento) throws ServiceException {
		try {

			IdPagamento id = this.pagamentoService.convertToId(pagamento);

			return this.pagamentoService.exists(id);
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

	public void deletePagamenti(IdFattura idFattura) throws Exception {
		try {
			IExpression expression = this.pagamentoService.newExpression();
			expression.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			this.pagamentoService.deleteAll(expression);

		} catch (ServiceException e) {
			this.log.error("Errore durante la prendiInCaricoDeletePagamenti: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la prendiInCaricoDeletePagamenti: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStornoPagamentoByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		//NOP
		//		try {
		//			if(esito.equals(EsitoOkKoTipo.OK)) {
		//				IExpression expression = this.pagamentoService.newExpression();
		//				expression.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
		//				expression.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
		//				this.pagamentoService.deleteAll(expression);
		//			} else {
		//				IPaginatedExpression expression = this.pagamentoService.newPaginatedExpression();
		//				
		//				expression.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
		//				expression.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
		//				
		//				List<IdPagamento> findAllIds = this.pagamentoService.findAllIds(expression);
		//				for(IdPagamento ids: findAllIds) {
		//					this.pagamentoService.updateFields(ids, new UpdateField(PccPagamento.model().STATO, StatoOperazioneContabileType.ERRORE_CANCELLAZIONE));
		//				}
		//			}
		//		} catch (ServiceException e) {
		//			this.log.error("Errore durante la updateStornoPagamentoByIdFattura: " + e.getMessage(), e);
		//			throw new Exception(e);
		//		} catch (NotImplementedException e) {
		//			this.log.error("Errore durante la updateStornoPagamentoByIdFattura: " + e.getMessage(), e);
		//			throw new Exception(e);
		//		}
	}

	public PccPagamento findById(Long idPagamento) throws ServiceException,NotFoundException {
		try {
			return ((JDBCPccPagamentoService)this.pagamentoService).get(idPagamento.longValue());
		} catch (ServiceException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.debug("findById ["+idPagamento+"] elemento non trovato");
			throw e;
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
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

	public Date getMaxDateByIdFattura(IdFattura idFattura) throws ServiceException {
		
		try {
			IPaginatedExpression expression = this.pagamentoService.newPaginatedExpression();
			expression.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			
			expression.offset(0);
			expression.limit(1);
			
			expression.addOrder(PccPagamento.model().DATA_RICHIESTA, SortOrder.DESC);

			List<PccPagamento> cont = this.pagamentoService.findAll(expression);
			
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

	public double getTotalePagamentiByIdFattura(IdFattura idFattura) throws ServiceException {
		String methodName = "getTotalePagamentiByIdFattura";
		try {
			IExpression expr = this.pagamentoService.newExpression();

			expr.equals(PccPagamento.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccPagamento.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			FunctionField sum = new FunctionField(PccPagamento.model().IMPORTO_PAGATO, Function.SUM, "sum_importi");
			Object o = this.pagamentoService.aggregate(expr, sum);

			return (Double) o;

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
		} catch (NotFoundException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

}
