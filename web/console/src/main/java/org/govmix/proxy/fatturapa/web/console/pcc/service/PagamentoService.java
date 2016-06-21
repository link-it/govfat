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
import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccPagamentoBD;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.PagamentoPccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IPagamentoService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.PagamentoSearchForm;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class PagamentoService extends BaseService<PagamentoSearchForm>  implements IPagamentoService{

	private IdFattura idFattura = null;
	private static Logger log = LoggerManager.getDaoLogger();
	private PccPagamentoBD bd = null;
	public PagamentoService() {
		try{
			this.bd = new PccPagamentoBD(log);
		}catch(Exception e){
			log.error("Errore durante la init di PagamentoService: "+ e.getMessage(), e); 
		}
	}

	@Override
	public List<PagamentoPccBean> findAll(int start, int limit) throws ServiceException {
		return _findAll(start,limit);
	}

	@Override
	public int totalCount() throws ServiceException {
		return (int) this.bd.getNumeroPagamentiByIdFattura(this.idFattura);		
	}

	@Override
	public void store(PagamentoPccBean obj) throws ServiceException {
		PccPagamento dto = obj.getDTO();
		this.bd.create(dto);
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		this.bd.deleteById(key);
	}

	@Override
	public void delete(PagamentoPccBean obj) throws ServiceException {
		this.bd.delete(obj.getDTO());	
	}

	@Override
	public PagamentoPccBean findById(Long key) throws ServiceException {
		PagamentoPccBean bean = null;

		bean = new PagamentoPccBean();
		try {
			PccPagamento dto = this.bd.findById(key);
			bean.setDTO(dto);
		} catch (NotFoundException e) {
			log.debug("Elemento non trovato");
		}

		return bean;
	}
	@Override
	public List<PagamentoPccBean> findAll() throws ServiceException {
		return _findAll(null,null);
	}

	private List<PagamentoPccBean> _findAll(Integer start, Integer limit) {
		String methodName = "_findAll(Integer start, Integer limit)";
		List<PagamentoPccBean> lst = new ArrayList<PagamentoPccBean>();

		try {
			
			FilterSortWrapper filtro = new FilterSortWrapper();
			filtro.setField(PccPagamento.model().DATA_MANDATO);
			filtro.setSortOrder(SortOrder.DESC);
			List<FilterSortWrapper> listaFiltri = new ArrayList<FilterSortWrapper>();
			listaFiltri.add(filtro);
			List<PccPagamento> contabilizzazioniByIdFattura = this.bd.getPagamentiByIdFattura(this.idFattura,start,limit,listaFiltri);

			if(contabilizzazioniByIdFattura != null && contabilizzazioniByIdFattura.size() > 0){
				for (PccPagamento pccPagamento : contabilizzazioniByIdFattura) {
					PagamentoPccBean bean = new PagamentoPccBean();
					bean.setDTO(pccPagamento);
					lst.add(bean);
				}
			}

		} catch (Exception e) {
			log.error("Errore durante l'esecuzione del metodo ["+methodName+"] : "+ e.getMessage() , e);
		}

		return lst;
	}

	@Override
	public boolean exists(PagamentoPccBean obj) throws ServiceException {
		PccPagamento pagamento = obj.getDTO();
		return this.bd.exists(pagamento);
	}

	public IdFattura getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}

	@Override
	public List<PagamentoPccBean> findAll(PagamentoSearchForm form) throws ServiceException {
		return _findAll(null,null);
	}
}
