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
package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteService;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCNotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.NotificaECBean;
import org.govmix.proxy.fatturapa.web.console.form.NotificaECForm;
import org.govmix.proxy.fatturapa.web.console.iservice.INotificaECService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.dao.IServiceSearchWithoutId;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

/**
 * NotificaECService classe di servizio per le entita' NotificaEC nel livello DAO.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECService extends BaseService<NotificaECForm> implements INotificaECService{

	private INotificaEsitoCommittenteService notificaDAO;
	private INotificaEsitoCommittenteServiceSearch notificaSearchDAO;

	private IdFattura idFattura = null;
	
	private org.govmix.proxy.fatturapa.orm.model.IdFatturaModel idFatturaModel = null;


	private static Logger log = LoggerManager.getDaoLogger();
	public NotificaECService(){
		try{
			this.idFatturaModel = NotificaEsitoCommittente.model().ID_FATTURA;
			this.notificaDAO = DAOFactory.getInstance().getServiceManager().getNotificaEsitoCommittenteService();
			this.notificaSearchDAO = DAOFactory.getInstance().getServiceManager().getNotificaEsitoCommittenteServiceSearch();
		}catch(Exception e){
			NotificaECService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public IdFattura getIdFattura() {
		return this.idFattura;
	}

	@Override
	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;

	}

	@Override
	public List<NotificaECBean> findAll(int start, int limit)
			throws ServiceException {

		List<NotificaECBean> lstRet = new ArrayList<NotificaECBean>();

		try {

			IExpression expr = NotificaECService.getFiltroRicerca(this.idFattura,this.notificaSearchDAO, this.idFatturaModel,NotificaECService.log);
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(NotificaEsitoCommittente.model().DATA_INVIO_SDI);

			

			IPaginatedExpression pagExpr = this.notificaSearchDAO.toPaginatedExpression(expr);
			pagExpr.offset(start);
			pagExpr.limit(limit);

			List<NotificaEsitoCommittente> list = this.notificaSearchDAO.findAll(pagExpr);

			if(list!= null){
				for (NotificaEsitoCommittente notificaEsitoCommittente : list) {
					NotificaECBean bean = new NotificaECBean();
					bean.setDTO(notificaEsitoCommittente);

					lstRet.add(bean);
				}
			}

		}catch (ServiceException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return lstRet;
	}

	@Override
	public int totalCount() throws ServiceException {

		int cnt = 0;

		try {
			IExpression expr =  NotificaECService.getFiltroRicerca(this.idFattura,this.notificaSearchDAO, this.idFatturaModel,NotificaECService.log);

			NonNegativeNumber nnn = this.notificaSearchDAO.count(expr);

			if(nnn!= null){
				cnt  = (int) nnn.longValue();
			}

		}catch (ServiceException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la count delle notifiche:" + e.getMessage(), e);
			throw e;
		}  catch (NotImplementedException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la count delle notifiche:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return cnt;
	}

	@Override
	public void store(NotificaECBean obj) throws ServiceException {

		NotificaEsitoCommittente dto = obj.getDTO();

		try {
			this.notificaDAO.updateOrCreate(dto);
		} 
		catch (NotImplementedException e) {
			NotificaECService.log.error("Si e' verificato un errore durante l'esecuzione della store della notifica:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ServiceException e) {
			NotificaECService.log.error("Si e' verificato un errore durante l'esecuzione della store della notifica:" + e.getMessage(), e);
			throw e;
		}catch (Exception e) {
			NotificaECService.log.error("Si e' verificato un errore durante l'esecuzione della store della notifica:" + e.getMessage(), e);
			throw new ServiceException(e);
		} 
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(NotificaECBean obj) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public NotificaECBean findById(Long key)  throws ServiceException {

		try {
			NotificaEsitoCommittente notificaEsitoCommittente = ((JDBCNotificaEsitoCommittenteServiceSearch)this.notificaSearchDAO).get(key);

			NotificaECBean bean = new NotificaECBean();
			bean.setDTO(notificaEsitoCommittente);

			return bean;
		}catch(NotFoundException e){
			NotificaECService.log.debug("[FindbyId] NotificaEC Non trovata: "+ e.getMessage());
			throw new ServiceException(e);
		}  catch (MultipleResultException e) {
			NotificaECService.log.error("Si e' verificato un errore durante l'esecuzione della find by id ["+key+"]:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			NotificaECService.log.error("Si e' verificato un errore durante l'esecuzione della find by id ["+key+"]:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ServiceException e) {
			NotificaECService.log.error("Si e' verificato un errore durante l'esecuzione della find by id ["+key+"]:" + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<NotificaECBean> findAll() throws ServiceException {
		List<NotificaECBean> lstRet = new ArrayList<NotificaECBean>();

		try {
			IExpression expr =  NotificaECService.getFiltroRicerca(this.idFattura,this.notificaSearchDAO, this.idFatturaModel,NotificaECService.log);
			
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(NotificaEsitoCommittente.model().DATA_INVIO_SDI);

			IPaginatedExpression pagExpr = this.notificaSearchDAO.toPaginatedExpression(expr);

			List<NotificaEsitoCommittente> list = this.notificaSearchDAO.findAll(pagExpr);

			if(list!= null){
				for (NotificaEsitoCommittente notificaEsitoCommittente : list) {
					NotificaECBean bean = new NotificaECBean();
					bean.setDTO(notificaEsitoCommittente);

					lstRet.add(bean);
				}
			}

		}catch (ServiceException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			NotificaECService.log.error("Si e' verificato un errore durante la find all delle notifiche:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return lstRet;
	}


	public static IExpression getFiltroRicerca(IdFattura idFattura, IServiceSearchWithoutId<?> searchDAO, org.govmix.proxy.fatturapa.orm.model.IdFatturaModel idFatturaModel, Logger log) throws ServiceException {

		IExpression expr;
		try {
			expr = searchDAO.newExpression();

			boolean addAnd = false;
			if( idFattura != null){
				if(idFattura.getIdentificativoSdi() != null){
					if(addAnd )
						expr.and();
					expr.equals(idFatturaModel.IDENTIFICATIVO_SDI, idFattura.getIdentificativoSdi());
				}

				if(idFattura.getPosizione() != null){
					if(addAnd )
						expr.and();
					expr.equals(idFatturaModel.POSIZIONE, idFattura.getPosizione());
				}
			}
			return expr;
		} catch (ServiceException e) {
			log.error("Si e' verificato un errore durante la creazione del filtro di ricerca:" + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			log.error("Si e' verificato un errore durante la creazione del filtro di ricerca:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			log.error("Si e' verificato un errore durante la creazione del filtro di ricerca:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			log.error("Si e' verificato un errore durante la creazione del filtro di ricerca:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

	}
	
	@Override
	public boolean exists(NotificaECBean obj) throws ServiceException {
		return false;
	}
	@Override
	public List<NotificaECBean> findAll(NotificaECForm form)
			throws ServiceException {
			return null;
	}
}
