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
package org.govmix.proxy.fatturapa.web.console.pcc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccScadenzaBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ScadenzaPccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IScadenzaService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.ScadenzaSearchForm;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class ScadenzaService extends BaseService<ScadenzaSearchForm>  implements IScadenzaService{

	private IdFattura idFattura = null;
	private static Logger log = LoggerManager.getDaoLogger();
	private PccScadenzaBD bd = null;
	private List<ScadenzaPccBean> cacheDati = null;

	public ScadenzaService() {
		try{
			this.bd = new PccScadenzaBD(log);
		}catch(Exception e){
			log.error("Errore durante la init di ScadenzaService: "+ e.getMessage(), e); 
		}
	}

	@Override
	public List<ScadenzaPccBean> findAll(int start, int limit) throws ServiceException {
		if(this.cacheDati != null){
			int end = start + limit;

			if((start+limit )> this.cacheDati.size()){
				end = this.cacheDati.size();
			} 

			return this.cacheDati.subList(start, end); 
		}else 
			return _findAll(start,limit);
	}

	@Override
	public int totalCount() throws ServiceException {
		if(this.cacheDati != null){
			return this.cacheDati.size();
		}else
			return (int) this.bd.getNumeroScadenzeByIdFattura(this.idFattura);		
	}

	private Long getNextId(){
		long max = 1l;

		if(this.cacheDati != null){


			for (int i = 0; i < this.cacheDati.size(); i++) {
				if(this.cacheDati.get(i).getId().longValue() > max){
					max = this.cacheDati.get(i).getId().longValue() ;
				}
			}

		}
		return new Long(max +1);
	}


	@Override
	public void store(ScadenzaPccBean obj) throws ServiceException {
		PccScadenza dto = obj.getDTO();
		if(this.cacheDati != null){
			if(dto.getId() > 0){
				ScadenzaPccBean  found = null;
				for (ScadenzaPccBean scadenzaPccBean : cacheDati) {
					if(scadenzaPccBean.getDTO().getId().longValue() == dto.getId().longValue()){
						found = scadenzaPccBean;
						break;
					}
				}

				if(found != null)
					found.setDTO(dto);
			} else {
				dto.setId(getNextId()); 
				//	inserisco in cima
				this.cacheDati.add(0,obj);
			}

		}else{
			this.bd.create(dto);
		}
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		if(this.cacheDati != null){
			if(key.longValue() > 0){
				int idx = -1;
				for (int i =0 ; i < cacheDati.size() ; i ++ ){
					ScadenzaPccBean scadenzaPccBean = cacheDati.get(i);
					if(scadenzaPccBean.getDTO().getId().longValue() == key.longValue()){
						idx = i;
						break;
					}
				}

				if(idx > -1)
					cacheDati.remove(idx);
			}  

		}else{
			this.bd.deleteById(key);
		}


	}

	@Override
	public void delete(ScadenzaPccBean obj) throws ServiceException {
		PccScadenza dto = obj.getDTO();
		if(this.cacheDati != null){
			if(dto.getId() > 0){
				int idx = -1;
				for (int i =0 ; i < cacheDati.size() ; i ++ ){
					ScadenzaPccBean scadenzaPccBean = cacheDati.get(i);
					if(scadenzaPccBean.getDTO().getId().longValue() == dto.getId().longValue()){
						idx = i;
						break;
					}
				}

				if(idx > -1)
					cacheDati.remove(idx);
			}  
		}else{
			this.bd.delete(dto);
		}
	}

	@Override
	public ScadenzaPccBean findById(Long key) throws ServiceException {
		ScadenzaPccBean bean = null;
		PccScadenza dto = null;
		if(this.cacheDati != null){
			if(key.longValue() > 0){
				for (ScadenzaPccBean scadenzaPccBean : cacheDati) {
					if(scadenzaPccBean.getDTO().getId().longValue() == key.longValue()){
						bean = scadenzaPccBean;
						break;
					}
				}
			}  
		}else {
			try {
				dto = this.bd.findById(key);
				bean = new ScadenzaPccBean();
				bean.setDTO(dto);
			} catch (NotFoundException e) {
				log.debug("Elemento non trovato");
			}
		}
		return bean;
	}
	@Override
	public List<ScadenzaPccBean> findAll() throws ServiceException {
		return _findAll(null,null);
	}

	private List<ScadenzaPccBean> _findAll(Integer start, Integer limit) {
		String methodName = "_findAll(Integer start, Integer limit)";
		List<ScadenzaPccBean> lst = new ArrayList<ScadenzaPccBean>();

		try {
			
			FilterSortWrapper filtro = new FilterSortWrapper();
			filtro.setField(PccScadenza.model().DATA_SCADENZA);
			filtro.setSortOrder(SortOrder.DESC);
			List<FilterSortWrapper> listaFiltri = new ArrayList<FilterSortWrapper>();
			listaFiltri.add(filtro);
			List<PccScadenza> sacadenzeByIdFattura = this.bd.getScadenzeByIdFattura(this.idFattura,start,limit,listaFiltri);

			if(sacadenzeByIdFattura != null && sacadenzeByIdFattura.size() > 0){
				for (PccScadenza pccScadenza : sacadenzeByIdFattura) {
					ScadenzaPccBean bean = new ScadenzaPccBean();
					bean.setDTO(pccScadenza);
					lst.add(bean);
				}
			}

		} catch (Exception e) {
			log.error("Errore durante l'esecuzione del metodo ["+methodName+"] : "+ e.getMessage() , e);
		}

		return lst;
	}

	@Override
	public boolean exists(ScadenzaPccBean obj) throws ServiceException {
		PccScadenza scadenza = obj.getDTO();
		return this.bd.exists(scadenza);
	}

	public IdFattura getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}

	@Override
	public List<ScadenzaPccBean> findAll(ScadenzaSearchForm form) throws ServiceException {
		return _findAll(null,null);
	}

	@Override
	public void setCacheDati(List<ScadenzaPccBean> statoOriginale) {
		this.cacheDati = statoOriginale;		
	}
}
