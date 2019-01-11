/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import org.govmix.proxy.fatturapa.orm.IdTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.EsitoPccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IEsitoService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.EsitoSearchForm;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class EsitoService extends BaseService<EsitoSearchForm> implements IEsitoService{

	private IdTrasmissione idTrasmissione = null;
	private static Logger log = LoggerManager.getDaoLogger();

	private PccTracciamentoBD tracciamentoBD = null;


	public EsitoService() {
		try {
			this.tracciamentoBD = new PccTracciamentoBD(EsitoService.log);
		} catch (Exception e) {
			EsitoService.log.error("Errore durante la init di EsitoService: " + e.getMessage(),e); 
		}
	}

	@Override
	public List<EsitoPccBean> findAll(int start, int limit) throws ServiceException {
		return this._findAll(this.getForm(), start, limit);
	}

	@Override
	public int totalCount() throws ServiceException {
		try {
			// Dettaglio Operazione
			if(this.idTrasmissione != null){
				return (int) this.tracciamentoBD.countEsitiByIdTrasmissione(this.idTrasmissione.getIdTrasmissione());
			} else {
				// ricerca libera

			}

			return 0;
		} catch (Exception e) {
			EsitoService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
	}

	@Override
	public void store(EsitoPccBean obj) throws ServiceException {
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
	}

	@Override
	public void delete(EsitoPccBean obj) throws ServiceException {
	}

	@Override
	public EsitoPccBean findById(Long key) throws ServiceException {
		EsitoPccBean bean = new EsitoPccBean();
		
		try {
			PccTracciaTrasmissioneEsito trasmissioniById = this.tracciamentoBD.getEsitoById(key.longValue());
			bean.setDTO(trasmissioniById);
			
			return bean;
		} catch(NotFoundException e){
			EsitoService.log.debug("Nessuna esito con ID ["+key+"] trovata.");
		}catch (Exception e) {
			EsitoService.log.error("Errore durante la findById: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
		
		return null;
	}

	@Override
	public List<EsitoPccBean> findAll() throws ServiceException {
		return this._findAll(this.getForm(), null, null);
	}

	@Override
	public boolean exists(EsitoPccBean obj) throws ServiceException {
		return false;
	}

	@Override
	public List<EsitoPccBean> findAll(EsitoSearchForm arg0) throws ServiceException {
		return this._findAll(arg0, null,null);
	}

	private List<EsitoPccBean> _findAll(EsitoSearchForm form,Integer start, Integer limit) throws ServiceException {
		List<EsitoPccBean> lst = new ArrayList<EsitoPccBean>();

		try {
			// Dettaglio Operazione
			if(this.idTrasmissione != null){
				
				
				FilterSortWrapper filtro = new FilterSortWrapper();
				filtro.setField(PccTracciaTrasmissioneEsito.model().GDO);
				filtro.setSortOrder(SortOrder.DESC);
				List<FilterSortWrapper> listaFiltri = new ArrayList<FilterSortWrapper>();
				listaFiltri.add(filtro);
				List<PccTracciaTrasmissioneEsito> trasmissioniByIdTraccia =
						this.tracciamentoBD.getEsitiByIdTrasmissione(this.idTrasmissione.getIdTrasmissione(),start,limit,listaFiltri);

				if(trasmissioniByIdTraccia != null && trasmissioniByIdTraccia.size() > 0){
					for (PccTracciaTrasmissioneEsito pccTracciaEsito : trasmissioniByIdTraccia) {
						EsitoPccBean bean = new EsitoPccBean();
						bean.setDTO(pccTracciaEsito);
						
						lst.add(bean);
					}
				}
			} else {
				// ricerca libera

			}

			return lst;
		} catch (Exception e) {
			EsitoService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
	}

	@Override
	public IdTrasmissione getIdTrasmissione() {
		return idTrasmissione;
	}

	@Override
	public void setIdTrasmissione(IdTrasmissione idTrasmissione) {
		this.idTrasmissione = idTrasmissione;
	}
 



}

