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
package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.dao.INotificaDecorrenzaTerminiServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.NotificaDTBean;
import org.govmix.proxy.fatturapa.web.console.iservice.INotificaDTService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

/**
 * NotificaDTService classe di servizio per le entita' NotificaDT nel livello DAO.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaDTService implements INotificaDTService{

	private IdNotificaDecorrenzaTermini idNotificaDT = null;

	private INotificaDecorrenzaTerminiServiceSearch notificaDTSearchDAO = null;

	private static Logger log = LoggerManager.getDaoLogger();

	public NotificaDTService(){
		try{
			this.notificaDTSearchDAO = DAOFactory.getInstance().getServiceManager().getNotificaDecorrenzaTerminiServiceSearch();
		}catch(Exception e){
			NotificaDTService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public IdNotificaDecorrenzaTermini getIdNotificaDecorrenzaTermini() {
		return this.idNotificaDT;
	}

	@Override
	public void setIdNotificaDecorrenzaTermini(
			IdNotificaDecorrenzaTermini idNotifica) {
		this.idNotificaDT = idNotifica;
	}

	@Override
	public List<NotificaDTBean> findAll(int start, int limit)
			throws ServiceException {

		List<NotificaDTBean> lstRet = new ArrayList<NotificaDTBean>();

		try {

			IExpression expr = this.notificaDTSearchDAO.newExpression();
			expr.equals(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI, this.idNotificaDT.getIdentificativoSdi());
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(NotificaDecorrenzaTermini.model().DATA_RICEZIONE);

			IPaginatedExpression pagExpr = this.notificaDTSearchDAO.toPaginatedExpression(expr);
			pagExpr.offset(start);
			pagExpr.limit(limit);

			List<NotificaDecorrenzaTermini> list = this.notificaDTSearchDAO.findAll(pagExpr);

			if(list!= null){
				for (NotificaDecorrenzaTermini allegato : list) {
					NotificaDTBean bean = new NotificaDTBean();
					bean.setDTO(allegato);

					lstRet.add(bean);
				}
			}

		}catch (ServiceException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return lstRet;
	}

	@Override
	public int totalCount() throws ServiceException {
		int cnt = 0;

		try {
			IExpression expr = this.notificaDTSearchDAO.newExpression();
				
			expr.equals(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI, this.idNotificaDT.getIdentificativoSdi());
			
			NonNegativeNumber nnn = this.notificaDTSearchDAO.count(expr);

			if(nnn!= null){
				cnt  = (int) nnn.longValue();
			}

		}catch (ServiceException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la count notifiche decorrenza termini:" + e.getMessage(), e);
			throw e;
		}  catch (NotImplementedException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la count notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la count notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la count notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return cnt;
	}

	@Override
	public void store(NotificaDTBean obj) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(NotificaDTBean obj) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public NotificaDTBean findById(Long key) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotificaDTBean> findAll() throws ServiceException {
		List<NotificaDTBean> lstRet = new ArrayList<NotificaDTBean>();

		try {

			IExpression expr = this.notificaDTSearchDAO.newExpression();
			expr.equals(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI, this.idNotificaDT.getIdentificativoSdi());
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(NotificaDecorrenzaTermini.model().DATA_RICEZIONE);

			IPaginatedExpression pagExpr = this.notificaDTSearchDAO.toPaginatedExpression(expr);

			List<NotificaDecorrenzaTermini> list = this.notificaDTSearchDAO.findAll(pagExpr);

			if(list!= null){
				for (NotificaDecorrenzaTermini allegato : list) {
					NotificaDTBean bean = new NotificaDTBean();
					bean.setDTO(allegato);

					lstRet.add(bean);
				}
			}

		}catch (ServiceException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			NotificaDTService.log.error("Si e' verificato un errore durante la find all notifiche decorrenza termini:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return lstRet;
	}
	
	@Override
	public boolean exists(NotificaDTBean obj) throws ServiceException {
		return false;
	}
}
