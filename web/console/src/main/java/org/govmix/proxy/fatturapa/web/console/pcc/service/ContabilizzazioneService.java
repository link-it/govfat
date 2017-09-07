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

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccContabilizzazioneBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.commons.utils.TransformUtils;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ContabilizzazionePccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IContabilizzazioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.ContabilizzazioneSearchForm;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class ContabilizzazioneService extends BaseService<ContabilizzazioneSearchForm>  implements IContabilizzazioneService {


	private IdFattura idFattura = null;
	private static Logger log = LoggerManager.getDaoLogger();
	private PccContabilizzazioneBD bd = null;
	private List<ContabilizzazionePccBean> cacheDati = null;

	public ContabilizzazioneService() {
		try{
			this.bd = new PccContabilizzazioneBD(log);
		}catch(Exception e){
			log.error("Errore durante la init di ContabilizzazioneService: "+ e.getMessage(), e); 
		}
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
	public List<ContabilizzazionePccBean> findAll(int start, int limit) throws ServiceException {
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
		return (int) this.bd.getNumeroContabilizzazioniByIdFattura(this.idFattura);		
	}

	@Override
	public void store(ContabilizzazionePccBean obj) throws ServiceException {
		PccContabilizzazione dto = obj.getDTO();
		
		if(this.cacheDati != null){
			if(dto.getId() > 0){
				ContabilizzazionePccBean  found = null;
				for (ContabilizzazionePccBean contabilizzazionePccBean : cacheDati) {
					if(contabilizzazionePccBean.getDTO().getId().longValue() == dto.getId().longValue()){
						found = contabilizzazionePccBean;
						break;
					}
				}

				if(found != null)
					found.setDTO(dto);
			} else {
				dto.setId(getNextId());
				// inserisco in cima
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
					ContabilizzazionePccBean contabilizzazionePccBean = cacheDati.get(i);
					if(contabilizzazionePccBean.getDTO().getId().longValue() == key.longValue()){
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
	public void delete(ContabilizzazionePccBean obj) throws ServiceException {
		PccContabilizzazione dto = obj.getDTO();
		if(this.cacheDati != null){
			if(dto.getId() > 0){
				int idx = -1;
				for (int i =0 ; i < cacheDati.size() ; i ++ ){
					ContabilizzazionePccBean contabilizzazionePccBean = cacheDati.get(i);
					if(contabilizzazionePccBean.getDTO().getId().longValue() == dto.getId().longValue()){
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
	public ContabilizzazionePccBean findById(Long key) throws ServiceException {
		ContabilizzazionePccBean bean = null;
		PccContabilizzazione dto = null;
		if(this.cacheDati != null){
			if(key.longValue() > 0){
				for (ContabilizzazionePccBean contabilizzazionePccBean : cacheDati) {
					if(contabilizzazionePccBean.getDTO().getId().longValue() == key.longValue()){
						bean = contabilizzazionePccBean;
						break;
					}
				}
			}  
		}else {
			try {
				dto = this.bd.findById(key);
				bean = new ContabilizzazionePccBean();
				bean.setDTO(dto);
			} catch (NotFoundException e) {
				log.debug("Elemento non trovato");
			}
		}
		return bean;
	}
	@Override
	public List<ContabilizzazionePccBean> findAll() throws ServiceException {
		return _findAll(null,null);
	}

	private List<ContabilizzazionePccBean> _findAll(Integer start, Integer limit) {
		String methodName = "_findAll(ContabilizzazioneSearchForm form,Integer start, Integer limit)";
		List<ContabilizzazionePccBean> lst = new ArrayList<ContabilizzazionePccBean>();

		try {
			FilterSortWrapper filtro = new FilterSortWrapper();
			filtro.setField(PccContabilizzazione.model().DATA_RICHIESTA);
			filtro.setSortOrder(SortOrder.DESC);
			List<FilterSortWrapper> listaFiltri = new ArrayList<FilterSortWrapper>();
			listaFiltri.add(filtro);
			List<PccContabilizzazione> contabilizzazioniByIdFattura = this.bd.getContabilizzazioniByIdFattura(this.idFattura,start,limit,listaFiltri);

			if(contabilizzazioniByIdFattura != null && contabilizzazioniByIdFattura.size() > 0){
				for (PccContabilizzazione pccContabilizzazione : contabilizzazioniByIdFattura) {
					ContabilizzazionePccBean bean = new ContabilizzazionePccBean();
					bean.setDTO(pccContabilizzazione);
					// quelle che leggo dal db sono gia state inviate
					bean.setNonInviata(false); 
					String descr = pccContabilizzazione.getDescrizione();
					if(descr != null){
						String stringDescrizioneImportoContabilizzazione = TransformUtils.toStringDescrizioneImportoContabilizzazione(descr);
						bean.getDescrizione().setValue(stringDescrizioneImportoContabilizzazione);
					}
					
					lst.add(bean);
				}
			}

		} catch (Exception e) {
			log.error("Errore durante l'esecuzione del metodo ["+methodName+"] : "+ e.getMessage() , e);
		}

		return lst;
	}

	@Override
	public boolean exists(ContabilizzazionePccBean obj) throws ServiceException {
		PccContabilizzazione contabilizzazione = obj.getDTO();
		return this.bd.exists(contabilizzazione);
	}

	public IdFattura getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}
	
	@Override
	public List<ContabilizzazionePccBean> findAll(ContabilizzazioneSearchForm form) throws ServiceException {
		return _findAll(null,null);
	}

	public List<ContabilizzazionePccBean> getCacheDati() {
		return cacheDati;
	}

	public void setCacheDati(List<ContabilizzazionePccBean> cacheDati) {
		this.cacheDati = cacheDati;
	}
	
}
