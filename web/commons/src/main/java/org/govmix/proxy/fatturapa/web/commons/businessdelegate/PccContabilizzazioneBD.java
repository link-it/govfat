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

import it.tesoro.fatture.EsitoOkKoTipo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdContabilizzazione;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.dao.IPccContabilizzazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneEsitoService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccContabilizzazioneService;
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

public class PccContabilizzazioneBD  extends BaseBD {

	private IPccContabilizzazioneService contabilizzazioneService;
	private IPccTracciaTrasmissioneEsitoService esitoService;

	public PccContabilizzazioneBD() throws Exception {
		this(Logger.getLogger(PccContabilizzazioneBD.class));
	}

	public PccContabilizzazioneBD(Logger log) throws Exception {
		super(log);
		this.contabilizzazioneService = this.serviceManager.getPccContabilizzazioneService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
	}

	public PccContabilizzazioneBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.contabilizzazioneService = this.serviceManager.getPccContabilizzazioneService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
	}

	public List<PccContabilizzazione> getContabilizzazioniByIdFatturaEDiversoSistemaRichiedente(IdFattura idFattura, String sistemaRichiedente) throws ServiceException {
		String methodName = "getContabilizzazioniByIdFatturaEDiversoSistemaRichiedente";
		try {
			IExpression expr = this.contabilizzazioneService.newExpression();

			expr.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			expr.notEquals(PccContabilizzazione.model().SISTEMA_RICHIEDENTE, sistemaRichiedente);

			return this.getContabilizzazioni(expr, null, null, getFilterSortList());
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

	public List<PccContabilizzazione> getContabilizzazioniByIdFatturaDiversoSistemaRichiedenteEIdImportoDiversi(IdFattura idFattura, String sistemaRichiedente, List<String> idImporti) throws ServiceException {
		String methodName = "getContabilizzazioniByIdFatturaEDiversoSistemaRichiedente";
		try {
			IExpression expr = this.contabilizzazioneService.newExpression();

			expr.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			List<PccContabilizzazione> contabilizzazioni = this.getContabilizzazioni(expr, null, null, getFilterSortList());
			List<PccContabilizzazione> contabilizzazioniToReturn = new ArrayList<PccContabilizzazione>();
			
			for(PccContabilizzazione cont: contabilizzazioni) {
				if(!cont.getSistemaRichiedente().equals(sistemaRichiedente) || !idImporti.contains(cont.getIdImporto())){
					contabilizzazioniToReturn.add(cont);
				}
			}
			
			return contabilizzazioniToReturn;
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

	public List<PccContabilizzazione> getContabilizzazioniByIdFattura(IdFattura idFattura) throws ServiceException {
		String methodName = "getContabilizzazioniByIdFattura";
		try {
			IExpression expr = this.contabilizzazioneService.newExpression();

			expr.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			return this.getContabilizzazioni(expr, null, null, getFilterSortList());
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
	
	public List<PccContabilizzazione> getContabilizzazioniByIdFattura(IdFattura idFattura,Integer start, Integer limit) throws ServiceException {
		return this.getContabilizzazioniByIdFattura(idFattura, start, limit, getFilterSortList());
	}
	
	public List<PccContabilizzazione> getContabilizzazioniByIdFattura(IdFattura idFattura,Integer start, Integer limit,List<FilterSortWrapper> filterSortList) throws ServiceException {
		String methodName = "getContabilizzazioniByIdFattura";
		try {
			IExpression expr = this.contabilizzazioneService.newExpression();

			expr.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			
			return this.getContabilizzazioni(expr, start, limit, filterSortList);

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
	
	private List<PccContabilizzazione> getContabilizzazioni(IExpression expr,Integer start, Integer limit,List<FilterSortWrapper> filterSortList) throws ServiceException {
		String methodName = "getContabilizzazioniByIdFattura";
		try {
		
			IPaginatedExpression pagExpression = this.toPaginatedExpression(this.contabilizzazioneService, expr, start, limit,filterSortList);

			return this.contabilizzazioneService.findAll(pagExpression);

		} catch (ServiceException e) {
			
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la " + methodName + ": " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public long getNumeroContabilizzazioniByIdFattura(IdFattura idFattura) throws ServiceException {
		String methodName = "getContabilizzazioniByIdFattura";
		try {
			IExpression expr = this.contabilizzazioneService.newExpression();

			expr.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expr.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());

			NonNegativeNumber nnn = this.contabilizzazioneService.count(expr);

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
	
	public void create(PccContabilizzazione contabilizzazione) throws ServiceException {
		try {
			
			IdContabilizzazione id = this.contabilizzazioneService.convertToId(contabilizzazione);
			
			if(this.contabilizzazioneService.exists(id)) {
				this.contabilizzazioneService.update(id, contabilizzazione);
			} else {
				this.contabilizzazioneService.create(contabilizzazione);
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
	
	public void update(PccContabilizzazione contabilizzazione) throws ServiceException {
		try {
			
			IdContabilizzazione id = this.contabilizzazioneService.convertToId(contabilizzazione);
			this.contabilizzazioneService.update(id, contabilizzazione);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public void delete(PccContabilizzazione contabilizzazione) throws ServiceException {
		try {
			this.contabilizzazioneService.delete(contabilizzazione);
		} catch (ServiceException e) {
			this.log.error("Errore durante la delete: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la delete: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public void deleteById(Long idContabilizzazione) throws ServiceException {
		try {
			((JDBCPccContabilizzazioneService)this.contabilizzazioneService).deleteById(idContabilizzazione.longValue());
		} catch (ServiceException e) {
			this.log.error("Errore durante la deleteById: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la deleteById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public boolean exists(PccContabilizzazione contabilizzazione) throws ServiceException {
		try {
			
			IdContabilizzazione id = this.contabilizzazioneService.convertToId(contabilizzazione);
			
			return this.contabilizzazioneService.exists(id);
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
	
	public void deleteContabilizzazioni(IdFattura idFattura) throws Exception {
		try {
			IExpression expression = this.contabilizzazioneService.newExpression();
			expression.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			this.contabilizzazioneService.deleteAll(expression);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la prendiInCaricoDeleteContabilizzazioni: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la prendiInCaricoDeleteContabilizzazioni: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void updateStornoContabilizzazioneByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		try {
			IExpression expression = this.contabilizzazioneService.newExpression();
			expression.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			this.contabilizzazioneService.deleteAll(expression);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStornoContabilizzazioneByIdFattura: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStornoContabilizzazioneByIdFattura: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public PccContabilizzazione findById(Long idContabilizzazione) throws ServiceException,NotFoundException {
		try {
			return ((JDBCPccContabilizzazioneService)this.contabilizzazioneService).get(idContabilizzazione.longValue());
		} catch (ServiceException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.debug("findById ["+idContabilizzazione+"] elemento non trovato");
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
			IPaginatedExpression expression = this.contabilizzazioneService.newPaginatedExpression();
			expression.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			
			expression.offset(0);
			expression.limit(1);
			
			expression.addOrder(PccContabilizzazione.model().DATA_RICHIESTA, SortOrder.DESC);

			List<PccContabilizzazione> cont = this.contabilizzazioneService.findAll(expression);
			
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

	public void deleteByIdFatturaSistemaRichiedente(IdFattura idFattura, String sistemaRichiedente) throws Exception {
		try {
			IExpression exp = this.contabilizzazioneService.newExpression();
			exp.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			exp.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			exp.equals(PccContabilizzazione.model().SISTEMA_RICHIEDENTE, sistemaRichiedente);
			
			this.contabilizzazioneService.deleteAll(exp);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la deleteByIdFatturaSistemaRichiedente: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	public boolean exists(IdFattura idFattura) throws ServiceException {
		try {
			
			IExpression expression = this.contabilizzazioneService.newExpression();
			expression.equals(PccContabilizzazione.model().ID_FATTURA.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
			expression.equals(PccContabilizzazione.model().ID_FATTURA.POSIZIONE, idFattura.getPosizione());
			
			return this.contabilizzazioneService.count(expression).longValue() > 0;
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	

}
