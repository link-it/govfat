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
package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.dao.IAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.AllegatoFatturaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IAllegatiService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

/**
 * AllegatiService classe di servizio per le entita' Allegati nel livello DAO.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class AllegatiService implements IAllegatiService{

	private IdFattura idFattura = null;

	private IAllegatoFatturaServiceSearch allegatoSearchDAO = null;
	private org.govmix.proxy.fatturapa.orm.model.IdFatturaModel idFatturaModel = null;

	private static Logger log = LoggerManager.getDaoLogger();


	public AllegatiService(){
		try{
			this.idFatturaModel = AllegatoFattura.model().ID_FATTURA;
			this.allegatoSearchDAO = DAOFactory.getInstance().getServiceManager().getAllegatoFatturaServiceSearch();
		}catch(Exception e){
			AllegatiService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<AllegatoFatturaBean> findAll(int start, int limit)
			throws ServiceException {

		List<AllegatoFatturaBean> lstRet = new ArrayList<AllegatoFatturaBean>();

		try {

			IExpression expr = NotificaECService.getFiltroRicerca(this.idFattura,this.allegatoSearchDAO, this.idFatturaModel,AllegatiService.log);
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(AllegatoFattura.model().NOME_ATTACHMENT);

			IPaginatedExpression pagExpr = this.allegatoSearchDAO.toPaginatedExpression(expr);
			pagExpr.offset(start);
			pagExpr.limit(limit);

			List<AllegatoFattura> list = this.allegatoSearchDAO.findAll(pagExpr);

			if(list!= null){
				for (AllegatoFattura allegato : list) {
					AllegatoFatturaBean bean = new AllegatoFatturaBean();
					bean.setDTO(allegato);

					lstRet.add(bean);
				}
			}

		}catch (ServiceException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return lstRet;
	}

	@Override
	public int totalCount() throws ServiceException {
		int cnt = 0;

		try {
			IExpression expr =  NotificaECService.getFiltroRicerca(this.idFattura,this.allegatoSearchDAO, this.idFatturaModel,AllegatiService.log);

			NonNegativeNumber nnn = this.allegatoSearchDAO.count(expr);

			if(nnn!= null){
				cnt  = (int) nnn.longValue();
			}

		}catch (ServiceException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la count allegati:" + e.getMessage(), e);
			throw e;
		}  catch (NotImplementedException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la count allegati:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return cnt;
	}

	@Override
	public void store(AllegatoFatturaBean obj) throws ServiceException {
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
	}

	@Override
	public void delete(AllegatoFatturaBean obj) throws ServiceException {
	}

	@Override
	public AllegatoFatturaBean findById(Long key) throws ServiceException {
		return null;
	}

	@Override
	public List<AllegatoFatturaBean> findAll() throws ServiceException {
		List<AllegatoFatturaBean> lstRet = new ArrayList<AllegatoFatturaBean>();

		try {

			IExpression expr = NotificaECService.getFiltroRicerca(this.idFattura,this.allegatoSearchDAO, this.idFatturaModel,AllegatiService.log);
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(AllegatoFattura.model().NOME_ATTACHMENT);

			IPaginatedExpression pagExpr = this.allegatoSearchDAO.toPaginatedExpression(expr);

			List<AllegatoFattura> list = this.allegatoSearchDAO.findAll(pagExpr);

			if(list!= null){
				for (AllegatoFattura allegato : list) {
					AllegatoFatturaBean bean = new AllegatoFatturaBean();
					bean.setDTO(allegato);

					lstRet.add(bean);
				}
			}

		}catch (ServiceException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			AllegatiService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw new ServiceException(e);
		}

		return lstRet;
	}

	@Override
	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}

	@Override
	public IdFattura getIdFattura() {
		return this.idFattura;
	}

	@Override
	public boolean exists(AllegatoFatturaBean obj) throws ServiceException {
		return false;
	}



}
