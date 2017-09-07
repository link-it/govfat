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
package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdRispedizione;
import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.orm.dao.IPccRispedizioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccRispedizioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccRispedizioneService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RispedizioneBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RispedizioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRispedizioneService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class RispedizioneService extends BaseService<RispedizioneSearchForm> implements IRispedizioneService{

	private IPccRispedizioneService rispedizioneServiceDAO = null;
	private IPccRispedizioneServiceSearch rispedizioneServiceSearchDAO = null;
	private static Logger log = LoggerManager.getDaoLogger();
	
	
	public RispedizioneService() {
		try{
			this.rispedizioneServiceDAO = DAOFactory.getInstance().getServiceManager().getPccRispedizioneService();
			this.rispedizioneServiceSearchDAO = DAOFactory.getInstance().getServiceManager().getPccRispedizioneServiceSearch();
		}catch(Exception e){
			RispedizioneService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<RispedizioneBean> findAll(RispedizioneSearchForm form) throws ServiceException {
		String methodName = "findAll(RispedizioneSearchForm)";
		List<RispedizioneBean> lst = new ArrayList<RispedizioneBean>();
		try{
			return this._findAll(form, null, null);
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
		}
		
		return lst;
	}

	@Override
	public List<RispedizioneBean> findAll(int start, int limit) throws ServiceException {
		String methodName = "findAll(start,limit)";
		List<RispedizioneBean> lst = new ArrayList<RispedizioneBean>();
		try{
			return this._findAll(getForm(), start, limit);
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
		}
		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.rispedizioneServiceSearchDAO.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(ServiceException e){
			RispedizioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			RispedizioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(RispedizioneBean obj) throws ServiceException {
		String methodName = "store";
		try{
			PccRispedizione risp = obj.getDTO();
			
			IdRispedizione idRispedizione = new IdRispedizione();
			idRispedizione.setCodiceErrore(risp.getCodiceErrore());

			if(this.rispedizioneServiceDAO.exists(idRispedizione))
				this.rispedizioneServiceDAO.update(idRispedizione, risp);
			else
				this.rispedizioneServiceDAO.create(risp);

		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
			throw e;
		} catch (Exception e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById";
		try{
			((JDBCPccRispedizioneService)this.rispedizioneServiceDAO).deleteById(key.longValue());
			
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
		} catch (NotImplementedException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
			throw new ServiceException(e);
		}

	}

	@Override
	public void delete(RispedizioneBean obj) throws ServiceException {
		String methodName = "delete";
		try{
			PccRispedizione risp = obj.getDTO(); 
			this.rispedizioneServiceDAO.delete(risp); 
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
		} catch (NotImplementedException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
			throw new ServiceException(e);
		}
	}

	@Override
	public RispedizioneBean findById(Long key) throws ServiceException {
		String methodName = "findById";
		try{
			try {
				PccRispedizione risp = ((JDBCPccRispedizioneService)this.rispedizioneServiceDAO).get(key.longValue());
				RispedizioneBean bean = new RispedizioneBean();
				bean.setDTO(risp);
				
				return bean;
			} catch (NotFoundException e) {
				log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
				throw new ServiceException(e);
			} catch (MultipleResultException e) {
				log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
				throw new ServiceException(e);
			} catch (NotImplementedException e) {
				log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
				throw new ServiceException(e);
			}
			
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
		}
		return null;
	}

	@Override
	public List<RispedizioneBean> findAll() throws ServiceException {
		String methodName = "findAll";
		List<RispedizioneBean> lst = new ArrayList<RispedizioneBean>();
		try{
			return this._findAll(getForm(), null, null);
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
		}
		
		return lst;
	}

	@Override
	public boolean exists(RispedizioneBean obj) throws ServiceException {
		String methodName = "exists";
		try{
			IdRispedizione idObj = new IdRispedizione();
			idObj.setCodiceErrore(obj.getDTO().getCodiceErrore());
			return this.rispedizioneServiceSearchDAO.exists(idObj );
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e);
			throw e;
		} catch (Exception e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e);
			throw new ServiceException(e);
		}
	}

	private List<RispedizioneBean> _findAll(RispedizioneSearchForm form,Integer start, Integer limit) throws ServiceException {
		String methodName = "_findAll(form,start,limit)";
		List<RispedizioneBean> lst = new ArrayList<RispedizioneBean>();
		try{
			IExpression expr = this.getExpressionFromSearch(form);
			
			IPaginatedExpression pagExpr = this.rispedizioneServiceSearchDAO.toPaginatedExpression(expr);
			
			if(start != null && limit != null){
				pagExpr.offset(start);
				pagExpr.limit(limit);
			}
			
			pagExpr.addOrder(PccRispedizione.model().CODICE_ERRORE, SortOrder.ASC);
			
			List<PccRispedizione> lista = this.rispedizioneServiceSearchDAO.findAll(pagExpr);
			
			if(lista != null && lista.size() > 0)
			for (PccRispedizione pccRispedizione : lista) {
				RispedizioneBean bean = new RispedizioneBean();
				bean.setDTO(pccRispedizione);
				
				lst.add(bean);
			}
			
			return lst;
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
			throw e;
		} catch (Exception e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+e.getMessage(),e); 
			throw new ServiceException(e); 
		}
	}
	
	private IExpression getExpressionFromSearch(RispedizioneSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.rispedizioneServiceSearchDAO.newExpression();
		}catch(Exception e){
			RispedizioneService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}
}
