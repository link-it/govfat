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
package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IEnteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCEnteService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IEnteService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.LikeMode;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.service.BaseService;

public class EnteService extends BaseService<EnteSearchForm> implements IEnteService {

	private IEnteServiceSearch enteSearchDao = null;
	private IDipartimentoServiceSearch dipartimentoSearchDao = null;
	private org.govmix.proxy.fatturapa.orm.dao.IEnteService enteDao = null;

	private static Logger log = LoggerManager.getDaoLogger();

	public EnteService(){
		try{
			this.enteDao = DAOFactory.getInstance().getServiceManager().getEnteService();
			this.enteSearchDao = DAOFactory.getInstance().getServiceManager().getEnteServiceSearch();
			this.dipartimentoSearchDao = DAOFactory.getInstance().getServiceManager().getDipartimentoServiceSearch();
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<EnteBean> findAll(int start, int limit)
			throws ServiceException {
		return this._findAll(this.form, start, limit);
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.enteSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(EnteBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Ente ente = obj.getDTO();

			IdEnte idEnte = new IdEnte();
			idEnte.setNome(ente.getNome());

			if(this.enteDao.exists(idEnte))
				this.enteDao.update(idEnte, ente);
			else
				this.enteDao.create(ente);
		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		IExpression expr = null;
		try{
			EnteBean findById = this.findById(key);
			
			Ente ente = findById.getDTO();
			
			expr = this.dipartimentoSearchDao.newExpression();
			expr.equals(Dipartimento.model().ENTE.NOME, ente.getNome());
			NonNegativeNumber nnn = this.dipartimentoSearchDao.count(expr);
			
			if(nnn.longValue() > 0) {
				throw new ServiceException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.deleteError.dipartimentiPresenti", ente.getNome()));
			}
			
			
			((JDBCEnteService)this.enteDao).deleteById(key.longValue());
		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void delete(EnteBean obj) throws ServiceException {
		String methodName = "delete()";
		
		IExpression expr = null;
		
		try{
			Ente ente = obj.getDTO();
			
			expr = this.dipartimentoSearchDao.newExpression();
			expr.equals(Dipartimento.model().ENTE.NOME, ente.getNome());
			NonNegativeNumber nnn = this.dipartimentoSearchDao.count(expr);
			
			if(nnn.longValue() > 0) {
				throw new ServiceException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.deleteError.dipartimentiPresenti", ente.getNome()));
			}

			IdEnte idEnte = new IdEnte();
			idEnte.setNome(ente.getNome());

			this.enteDao.deleteById(idEnte); 
		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public EnteBean findById(Long key) throws ServiceException {
		String methodName = "findById(id)";

		try{
			Ente d = ((JDBCEnteService)this.enteDao).get(key.longValue());
			EnteBean ut = new EnteBean();
			ut.setDTO(d);

			return ut;
		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(NotFoundException e){
			EnteService.log.debug("["+methodName+"] Ente non trovato: "+ e.getMessage());
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<EnteBean> findAll() throws ServiceException {
		return this._findAll(this.form, null, null);
	}

	private IExpression getExpressionFromSearch(EnteSearchForm search) throws Exception{
		IExpression expr = null;
		IExpression nomeExpr = null;
		IExpression descExpr = null;
		IExpression nomeOrDescExpr = null;

		try{
			expr = this.enteSearchDao.newExpression();
			if(search != null){
				if(search.getNome().getValue() != null && !StringUtils.isEmpty(search.getNome().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getNome().getValue())){
					nomeExpr = this.enteSearchDao.newExpression();
					nomeExpr.ilike(Ente.model().NOME, search.getNome().getValue(), LikeMode.ANYWHERE);
					nomeOrDescExpr = this.enteSearchDao.newExpression();
				}
				if(search.getDescrizione().getValue() != null && !StringUtils.isEmpty(search.getDescrizione().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getDescrizione().getValue())){
					descExpr = this.enteSearchDao.newExpression();
					descExpr.ilike(Ente.model().DESCRIZIONE, search.getDescrizione().getValue(), LikeMode.ANYWHERE);
					nomeOrDescExpr = this.enteSearchDao.newExpression();
				}
				
				if(nomeOrDescExpr != null){
					// Ho impostato la descrizione 
					if(descExpr != null){
						// se ho impostato il codice vado in  or
						if(nomeExpr != null){
							nomeOrDescExpr.or(descExpr, nomeExpr);
						}else{
							nomeOrDescExpr.or(descExpr);
						}

					}else {
						// non ho impostato la descrizione 
						if(nomeExpr != null)
							nomeOrDescExpr.or(nomeExpr);
					}

					expr.and(nomeOrDescExpr);
				}
			}
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	@Override
	public EnteBean findEnteByNome(String nome) throws ServiceException {
		String methodName = "findEnteByNome("+nome+")";
		IExpression expr = null;

		try{
			expr = this.enteSearchDao.newExpression();
			expr.equals(Ente.model().NOME, nome);

			Ente u = this.enteSearchDao.find(expr);
			EnteBean ente = new EnteBean();
			ente.setDTO(u);

			return ente;

		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public boolean exists(EnteBean obj) throws ServiceException {
		String methodName = "exists()";
		try{
			IdEnte idObj = new IdEnte();
			idObj.setNome(obj.getDTO().getNome());
			return this.enteSearchDao.exists(idObj );

		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;
	}

	@Override
	public List<EnteBean> findAll(EnteSearchForm form) throws ServiceException {
		return this._findAll(form, null, null);
	}

	private List<EnteBean> _findAll(EnteSearchForm form ,Integer start, Integer limit)
			throws ServiceException {
		String methodName = "_findAll(form,start,limit)";

		List<EnteBean> lst = new ArrayList<EnteBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Ente.model().NOME);

			IPaginatedExpression pagExpr = this.enteSearchDao.toPaginatedExpression(expr);

			if(start != null)
				pagExpr.offset(start);
			if(limit != null)
				pagExpr.limit(limit);

			List<Ente> findAll = this.enteSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (Ente ente : findAll) {
					EnteBean toRet = new EnteBean();
					toRet.setDTO(ente);
					lst.add(toRet);
				}
			}

		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	
	@Override
	public EnteBean findEnteByDipartimento(String nome) throws ServiceException {
		String methodName = "findEnteByNome("+nome+")";
		IPaginatedExpression expr = null;

		try{
			expr = this.dipartimentoSearchDao.newPaginatedExpression();
			expr.equals(Dipartimento.model().CODICE, nome);
			
			List<Dipartimento> dipartimentoLst = this.dipartimentoSearchDao.findAll(expr);
			
			if(dipartimentoLst.isEmpty())
				throw new NotFoundException();
			
			String nomeEnte = dipartimentoLst.get(0).getEnte().getNome(); 
			
			expr = this.enteSearchDao.newPaginatedExpression();
			expr.equals(Ente.model().NOME, nomeEnte);

			List<Ente> enteLst = this.enteSearchDao.findAll(expr);
			if(enteLst.isEmpty())
				throw new NotFoundException();
			
			EnteBean ente = new EnteBean();
			ente.setDTO(enteLst.get(0));

			return ente;

		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}
	
	@Override
	public List<Ente> getDescrizioneAutoComplete(String val) throws ServiceException {
		String methodName = "getDescrizioneAutoComplete(val)";

		List<Ente> lst = new ArrayList<Ente>();

		try{

			IPaginatedExpression pagExpr = this.enteSearchDao.newPaginatedExpression();
			pagExpr.ilike(Ente.model().DESCRIZIONE, val, LikeMode.ANYWHERE);

			//order by
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Ente.model().NOME);

			lst = this.enteSearchDao.findAll(pagExpr);
		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}
	
	@Override
	public List<Ente> getNomeAutoComplete(String val) throws ServiceException {
		String methodName = "getDescrizioneAutoComplete(val)";

		List<Ente> lst = new ArrayList<Ente>();

		try{

			IPaginatedExpression pagExpr = this.enteSearchDao.newPaginatedExpression();
			pagExpr.ilike(Ente.model().NOME, val, LikeMode.ANYWHERE);

			//order by
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Ente.model().NOME);

			lst = this.enteSearchDao.findAll(pagExpr);
		}catch(ServiceException e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			EnteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}
}

