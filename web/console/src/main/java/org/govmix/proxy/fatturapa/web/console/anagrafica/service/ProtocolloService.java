/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdProtocollo;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.dao.IProtocolloServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCProtocolloService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.ProtocolloBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.ProtocolloSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IProtocolloService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class ProtocolloService extends BaseService<ProtocolloSearchForm> implements IProtocolloService {

	private IProtocolloServiceSearch protocolloSearchDao = null;
	private org.govmix.proxy.fatturapa.orm.dao.IProtocolloService protocolloDao = null;

	private static Logger log = LoggerManager.getDaoLogger();

	public ProtocolloService(){
		try{
			this.protocolloDao = DAOFactory.getInstance().getServiceManager().getProtocolloService();
			this.protocolloSearchDao = DAOFactory.getInstance().getServiceManager().getProtocolloServiceSearch();
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<ProtocolloBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<ProtocolloBean> lst = new ArrayList<ProtocolloBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Protocollo.model().NOME);

			IPaginatedExpression pagExpr = this.protocolloSearchDao.toPaginatedExpression(expr);

			pagExpr.offset(start);
			pagExpr.limit(limit);

			List<Protocollo> findAll = this.protocolloSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (Protocollo ente : findAll) {
					ProtocolloBean toRet = new ProtocolloBean();
					toRet.setDTO(ente);
					lst.add(toRet);
				}
			}

		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.protocolloSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(ProtocolloBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Protocollo ente = obj.getDTO();

			IdProtocollo idProtocollo = new IdProtocollo();
			idProtocollo.setNome(ente.getNome());

			if(this.protocolloDao.exists(idProtocollo))
				this.protocolloDao.update(idProtocollo, ente);
			else
				this.protocolloDao.create(ente);
		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		try{
			((JDBCProtocolloService)this.protocolloDao).deleteById(key.longValue());
		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void delete(ProtocolloBean obj) throws ServiceException {
		String methodName = "delete()";

		try{
			Protocollo ente = obj.getDTO();

			IdProtocollo idProtocollo = new IdProtocollo();
			idProtocollo.setNome(ente.getNome());

			this.protocolloDao.deleteById(idProtocollo); 
		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public ProtocolloBean findById(Long key) throws ServiceException {
		String methodName = "findById(id)";

		try{
			Protocollo d = ((JDBCProtocolloService)this.protocolloDao).get(key.longValue());
			ProtocolloBean ut = new ProtocolloBean();
			ut.setDTO(d);

			return ut;
		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(NotFoundException e){
			ProtocolloService.log.debug("["+methodName+"] Protocollo non trovato: "+ e.getMessage());
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<ProtocolloBean> findAll() throws ServiceException {
		String methodName = "findAll()";

		List<ProtocolloBean> lst = new ArrayList<ProtocolloBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Protocollo.model().NOME);

			IPaginatedExpression pagExpr = this.protocolloSearchDao.toPaginatedExpression(expr);

			List<Protocollo> findAll = this.protocolloSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (Protocollo ente : findAll) {
					ProtocolloBean toRet = new ProtocolloBean();
					toRet.setDTO(ente);
					lst.add(toRet);
				}
			}

		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	private IExpression getExpressionFromSearch(ProtocolloSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.protocolloSearchDao.newExpression();
			if(search != null){
				if(search.getNome().getValue()!= null){
					expr.equals(Protocollo.model().NOME, search.getNome().getValue());
				}
			}
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	@Override
	public ProtocolloBean findProtocolloByNome(String nome) throws ServiceException {
		String methodName = "findProtocolloByNome("+nome+")";
		IExpression expr = null;

		try{
			expr = this.protocolloSearchDao.newExpression();
			expr.equals(Protocollo.model().NOME, nome);

			Protocollo u = this.protocolloSearchDao.find(expr);
			ProtocolloBean ente = new ProtocolloBean();
			ente.setDTO(u);

			return ente;

		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public boolean exists(ProtocolloBean obj) throws ServiceException {
		String methodName = "exists()";
		try{
			IdProtocollo idObj = new IdProtocollo();
			idObj.setNome(obj.getDTO().getNome());
			return this.protocolloSearchDao.exists(idObj );

		}catch(ServiceException e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			ProtocolloService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;
	}

	@Override
	public List<ProtocolloBean> findAll(ProtocolloSearchForm form) throws ServiceException {
		return null;
	}

}

