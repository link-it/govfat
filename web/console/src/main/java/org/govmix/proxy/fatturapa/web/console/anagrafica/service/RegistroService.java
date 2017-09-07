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
import org.govmix.proxy.fatturapa.orm.IdProtocollo;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.RegistroProperty;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroPropertyServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCRegistroService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class RegistroService extends BaseService<RegistroSearchForm> implements IRegistroService{

	private IRegistroServiceSearch registroSearchDao = null;
	private IRegistroPropertyServiceSearch registroPropertySearchDao = null;
	private org.govmix.proxy.fatturapa.orm.dao.IRegistroService registroDao = null;
	private static Logger log = LoggerManager.getDaoLogger();

	public RegistroService(){
		try{
			this.registroDao = DAOFactory.getInstance().getServiceManager().getRegistroService();
			this.registroSearchDao = DAOFactory.getInstance().getServiceManager().getRegistroServiceSearch();
			this.registroPropertySearchDao = DAOFactory.getInstance().getServiceManager().getRegistroPropertyServiceSearch();

		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<RegistroBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<RegistroBean> lst = new ArrayList<RegistroBean>();

		try{

			lst = this._findAllRegistri(this.form,start,limit);


		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			IExpression expr = this.getRegistroExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.registroSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(RegistroBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Registro registro = obj.getDTO();

			IdRegistro idRegistro = new IdRegistro();
			idRegistro.setNome(registro.getNome());

			if(this.registroDao.exists(idRegistro))
				this.registroDao.update(idRegistro, registro);
			else
				this.registroDao.create(registro);
		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		try{
			((JDBCRegistroService)this.registroDao).deleteById(key.longValue());
		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(RegistroBean obj) throws ServiceException {
		String methodName = "delete(Registro)";

		try{
			Registro registro = obj.getDTO();

			IdRegistro idRegistro = new IdRegistro();
			idRegistro.setNome(registro.getNome());

			this.registroDao.deleteById(idRegistro); 
		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
	public RegistroBean findById(Long key) throws ServiceException {
			String methodName = "findById(id)";

			try{
				Registro registro = ((JDBCRegistroService)this.registroDao).get(key.longValue());
				RegistroBean bean = new RegistroBean();
				bean.setDTO(registro);
				List<RegistroProperty> listaPropertiesEnte = this.getListaRegistroPropertiesProtocollo(registro.getIdProtocollo()); 
				bean.setListaNomiProperties(listaPropertiesEnte);

				return bean;
			}catch (NotFoundException e) {
				RegistroService.log.debug("Metodo ["+methodName+"]: Nessun registro trovato."+ e.getMessage());
			}catch(ServiceException e){
				RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			}catch(Exception e){
				RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			}
			return null;
	}

	@Override
	public List<RegistroBean> findAll() throws ServiceException {
		String methodName = "findAll()";

			List<RegistroBean> lst = new ArrayList<RegistroBean>();

			try{

				lst = this._findAllRegistri(this.form,null,null);

			}catch(ServiceException e){
				RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			}catch(Exception e){
				RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			}

			return lst;
	}

	@Override
	public boolean exists(RegistroBean obj) throws ServiceException {
		String methodName = "exists()";
		try{
			IdRegistro idObj = new IdRegistro();
			idObj.setNome(obj.getDTO().getNome());
			return this.registroSearchDao.exists(idObj);

		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;		
	}

	@Override
	public RegistroBean findRegistroByNome(String nome) throws ServiceException {
		String methodName = "findRegistroByNome("+nome+")";
		IExpression expr = null;

		try{
			expr = this.registroSearchDao.newExpression();
			expr.equals(Registro.model().NOME, nome);

			Registro u = this.registroSearchDao.find(expr);
			RegistroBean bean = new RegistroBean();
			bean.setDTO(u);
			List<RegistroProperty> listaPropertiesEnte = this.getListaRegistroPropertiesProtocollo(u.getIdProtocollo()); 
			bean.setListaNomiProperties(listaPropertiesEnte);

			return bean;

		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch (NotFoundException e) {
			RegistroService.log.debug("Metodo ["+methodName+"]: Nessun registro con nome ["+nome+"]trovato."+ e.getMessage());
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public List<RegistroProperty> getListaRegistroPropertiesProtocollo(IdProtocollo idProtocollo)
			throws ServiceException {
		List<RegistroProperty> lst = new ArrayList<RegistroProperty>();

		try {
			IPaginatedExpression pagExpr =  this.registroPropertySearchDao.newPaginatedExpression();
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(RegistroProperty.model().NOME);
			pagExpr.equals(RegistroProperty.model().ID_PROTOCOLLO.NOME, idProtocollo.getNome());

			lst = this.registroPropertySearchDao.findAll(pagExpr);

		} catch (ServiceException e) {
			RegistroService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idProtocollo.getNome()+"]:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			RegistroService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idProtocollo.getNome()+"]:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			RegistroService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idProtocollo.getNome()+"]:" +e.getMessage(), e);
		} catch (ExpressionException e) {
			RegistroService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idProtocollo.getNome()+"]:" +e.getMessage(), e);
		}

		return lst;
	}
	
	@Override
	public List<RegistroBean> findAll(RegistroSearchForm form) throws ServiceException {
		String methodName = "findAll(RegistroSearchForm form)";

		List<RegistroBean> lst = new ArrayList<RegistroBean>();

		try{

			lst = this._findAllRegistri( form,null,null);

		}catch(ServiceException e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}
	
	private List<RegistroBean> _findAllRegistri(RegistroSearchForm form,Integer start, Integer limit) throws ServiceException {
		List<RegistroBean> lst = new ArrayList<RegistroBean>();

		try{

			IExpression expr = this.getRegistroExpressionFromSearch( form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Registro.model().NOME);

			IPaginatedExpression pagExpr = this.registroSearchDao.toPaginatedExpression(expr);
			if(start!= null)
				pagExpr.offset(start);
			if(limit != null)
				pagExpr.limit(limit);

			List<Registro> findAll = this.registroSearchDao.findAll(pagExpr,false);

			if(findAll != null && findAll.size() > 0){
				for (Registro registro : findAll) {
					RegistroBean bean = new RegistroBean();
					bean.setDTO(registro);
//					List<RegistroProperty> listaPropertiesEnte = this.getListaRegistroPropertiesEnte(registro.getIdEnte()); 
//					bean.setListaNomiProperties(listaPropertiesEnte);
					lst.add(bean);
				}
			}

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return lst;
	}

	private IExpression getRegistroExpressionFromSearch(RegistroSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.registroSearchDao.newExpression();
			if(search != null){
				if(search.getProtocollo().getValue()!= null){
					expr.equals(Registro.model().ID_PROTOCOLLO.NOME, search.getProtocollo().getValue());
				}
			}
		}catch(Exception e){
			RegistroService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}
}
