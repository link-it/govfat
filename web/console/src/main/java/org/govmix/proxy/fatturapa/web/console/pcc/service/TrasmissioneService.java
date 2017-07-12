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
package org.govmix.proxy.fatturapa.web.console.pcc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.TracciaTrasmissionePCCBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.ITrasmissioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.TrasmissioneSearchForm;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class TrasmissioneService extends BaseService<TrasmissioneSearchForm> implements ITrasmissioneService{

	private IdTraccia idTraccia = null;
	private static Logger log = LoggerManager.getDaoLogger();

	private PccTracciamentoBD tracciamentoBD = null;
	
	public TrasmissioneService() {
		try {
			this.tracciamentoBD = new PccTracciamentoBD(log);
		
		} catch (Exception e) {
			log.error("Errore durante la init di TrasmissioneService: " + e.getMessage(),e); 
		}
	}

	@Override
	public List<TracciaTrasmissionePCCBean> findAll(int start, int limit) throws ServiceException {
		return this._findAll(this.getForm(), start, limit);
	}

	@Override
	public int totalCount() throws ServiceException {
		try {
			// Dettaglio Operazione
			if(this.idTraccia != null){
				
				return (int) this.tracciamentoBD.countTrasmissioniByIdTraccia(this.idTraccia);
			} else {
				// ricerca libera
			}

			return 0;
		} catch (Exception e) {
			log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
	}

	@Override
	public void store(TracciaTrasmissionePCCBean obj) throws ServiceException {
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
	}

	@Override
	public void delete(TracciaTrasmissionePCCBean obj) throws ServiceException {
	}

	@Override
	public TracciaTrasmissionePCCBean findById(Long key) throws ServiceException {
		TracciaTrasmissionePCCBean bean = new TracciaTrasmissionePCCBean();
		
		try {
			PccTracciaTrasmissione trasmissioniById = this.tracciamentoBD.getTrasmissioneById(key.longValue());
			bean.setDTO(trasmissioniById);
			
			
			
			return bean;
		} catch(NotFoundException e){
			log.debug("Nessuna trasmissione con ID ["+key+"] trovata.");
		}catch (Exception e) {
			log.error("Errore durante la findById: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
		
		return null;
	}

	@Override
	public List<TracciaTrasmissionePCCBean> findAll() throws ServiceException {
		return this._findAll(this.getForm(), null, null);
	}

	@Override
	public boolean exists(TracciaTrasmissionePCCBean obj) throws ServiceException {
		return false;
	}

	@Override
	public List<TracciaTrasmissionePCCBean> findAll(TrasmissioneSearchForm arg0) throws ServiceException {
		return this._findAll(arg0, null,null);
	}

	private List<TracciaTrasmissionePCCBean> _findAll(TrasmissioneSearchForm form,Integer start, Integer limit) throws ServiceException {
		List<TracciaTrasmissionePCCBean> lst = new ArrayList<TracciaTrasmissionePCCBean>();

		try {
			// Dettaglio Operazione
			if(this.idTraccia != null){
				
				FilterSortWrapper filtro = new FilterSortWrapper();
				filtro.setField(PccTracciaTrasmissione.model().TS_TRASMISSIONE);
				filtro.setSortOrder(SortOrder.DESC);
				List<FilterSortWrapper> listaFiltri = new ArrayList<FilterSortWrapper>();
				listaFiltri.add(filtro);
				List<PccTracciaTrasmissione> trasmissioniByIdTraccia = this.tracciamentoBD.getTrasmissioniByIdTraccia(this.idTraccia,start,limit,listaFiltri);

				if(trasmissioniByIdTraccia != null && trasmissioniByIdTraccia.size() > 0){
					for (PccTracciaTrasmissione pccTracciaTrasmissione : trasmissioniByIdTraccia) {
						TracciaTrasmissionePCCBean bean = new TracciaTrasmissionePCCBean();
						bean.setDTO(pccTracciaTrasmissione);
						
						lst.add(bean);
					}
				}
			} else {
				// ricerca libera

			}

			return lst;
		} catch (Exception e) {
			log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
	}

	public IdTraccia getIdTraccia() {
		return idTraccia;
	}

	public void setIdTraccia(IdTraccia idTraccia) {
		this.idTraccia = idTraccia;
	}



}
