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
import org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ErroreElaborazionePccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IErroreService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.ErroreSearchForm;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class ErroreService extends BaseService<ErroreSearchForm> implements IErroreService{

	private IdTrasmissioneEsito idEsito = null;
	private static Logger log = LoggerManager.getDaoLogger();

	private PccTracciamentoBD tracciamentoBD = null;


	public ErroreService() {
		try {
			this.tracciamentoBD = new PccTracciamentoBD(log);
		} catch (Exception e) {
			log.error("Errore durante la init di ErroreService: " + e.getMessage(),e); 
		}
	}

	@Override
	public List<ErroreElaborazionePccBean> findAll(int start, int limit) throws ServiceException {
		return this._findAll(this.getForm(), start, limit);
	}

	@Override
	public int totalCount() throws ServiceException {
		try {
			// Dettaglio Operazione
			if(this.idEsito != null){
				return (int) this.tracciamentoBD.countErroriByIdEsito(this.idEsito.getIdTrasmissioneEsito());
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
	public void store(ErroreElaborazionePccBean obj) throws ServiceException {
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
	}

	@Override
	public void delete(ErroreElaborazionePccBean obj) throws ServiceException {
	}

	@Override
	public ErroreElaborazionePccBean findById(Long key) throws ServiceException {
		ErroreElaborazionePccBean bean = new ErroreElaborazionePccBean();
		
		try {
			PccErroreElaborazione trasmissioniById = this.tracciamentoBD.getErroreById(key.longValue());
			bean.setDTO(trasmissioniById);
			
			return bean;
		} catch(NotFoundException e){
			log.debug("Nessuna esito con ID ["+key+"] trovata.");
		}catch (Exception e) {
			log.error("Errore durante la findById: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
		
		return null;
	}

	@Override
	public List<ErroreElaborazionePccBean> findAll() throws ServiceException {
		return this._findAll(this.getForm(), null, null);
	}

	@Override
	public boolean exists(ErroreElaborazionePccBean obj) throws ServiceException {
		return false;
	}

	@Override
	public List<ErroreElaborazionePccBean> findAll(ErroreSearchForm arg0) throws ServiceException {
		return this._findAll(arg0, null,null);
	}

	private List<ErroreElaborazionePccBean> _findAll(ErroreSearchForm form,Integer start, Integer limit) throws ServiceException {
		List<ErroreElaborazionePccBean> lst = new ArrayList<ErroreElaborazionePccBean>();

		try {
			// Dettaglio Operazione
			if(this.idEsito != null){
				
				FilterSortWrapper filtro = new FilterSortWrapper();
				filtro.setField(PccErroreElaborazione.model().CODICE_ESITO);
				filtro.setSortOrder(SortOrder.DESC);
				List<FilterSortWrapper> listaFiltri = new ArrayList<FilterSortWrapper>();
				listaFiltri.add(filtro);
				List<PccErroreElaborazione> trasmissioniByIdTraccia =
						this.tracciamentoBD.getErroriByIdEsito(this.idEsito.getIdTrasmissioneEsito(),start,limit,listaFiltri);

				if(trasmissioniByIdTraccia != null && trasmissioniByIdTraccia.size() > 0){
					for (PccErroreElaborazione pccTracciaErrore : trasmissioniByIdTraccia) {
						ErroreElaborazionePccBean bean = new ErroreElaborazionePccBean();
						bean.setDTO(pccTracciaErrore);
						
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

	public IdTrasmissioneEsito getIdEsito() {
		return idEsito;
	}

	public void setIdEsito(IdTrasmissioneEsito idEsito) {
		this.idEsito = idEsito;
	}
 



}

