package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.TracciaSdIBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.TracciaSdIFilter;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.TracciaSDIBean;
import org.govmix.proxy.fatturapa.web.console.iservice.ITracciaSDIService;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;

public class TracciaSDIService implements ITracciaSDIService{ 

	private IdFattura idFattura = null;
	private TracciaSdIBD tracciaSdiBD = null;

	private static Logger log = LoggerManager.getDaoLogger();

	public TracciaSDIService() {
		try{
			this.tracciaSdiBD = new TracciaSdIBD(log);
		}catch(Exception e){
			TracciaSDIService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}


	@Override
	public List<TracciaSDIBean> findAll(int arg0, int arg1) throws ServiceException {
		List<TracciaSDIBean> lstRet = new ArrayList<TracciaSDIBean>();

		try {
			if(this.eseguiRicerca()) {
				TracciaSdIFilter filter = this.getFilter();
				
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.ASC);
				fsw.setField(TracciaSDI.model().DATA);
				filter.getFilterSortList().add(fsw );
				
				filter.setOffset(arg0);
				filter.setLimit(arg1);

				List<TracciaSDI> list = this.tracciaSdiBD.findAll(filter);

				if(list!= null){
					for (TracciaSDI dto : list) {
						TracciaSDIBean bean = new TracciaSDIBean();
						bean.setDTO(dto);
						bean.setIdFattura(this.idFattura);

						lstRet.add(bean);
					}
				}
			}
		}catch (ServiceException e) {
			TracciaSDIService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw e;
		}  

		return lstRet;
	}

	@Override
	public int totalCount() throws ServiceException {
		int cnt = 0;

		try {
			if(this.eseguiRicerca()) {
				TracciaSdIFilter filter = this.getFilter();

				cnt  = (int) this.tracciaSdiBD.count(filter);
			}
		}catch (ServiceException e) {
			TracciaSDIService.log.error("Si e' verificato un errore durante la count comunicazioni:" + e.getMessage(), e);
			throw e;
		}   

		return cnt;
	}

	@Override
	public void delete(TracciaSDIBean arg0) throws ServiceException {
	}

	@Override
	public void deleteById(Long arg0) throws ServiceException {
	}

	@Override
	public boolean exists(TracciaSDIBean arg0) throws ServiceException {
		return false;
	}

	@Override
	public List<TracciaSDIBean> findAll() throws ServiceException {
		List<TracciaSDIBean> lstRet = new ArrayList<TracciaSDIBean>();

		try {
			if(this.eseguiRicerca()) {
				TracciaSdIFilter filter = this.getFilter();
				
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.ASC);
				fsw.setField(TracciaSDI.model().DATA);
				filter.getFilterSortList().add(fsw );
				
				List<TracciaSDI> list = this.tracciaSdiBD.findAll(filter);

				if(list!= null){
					for (TracciaSDI dto : list) {
						TracciaSDIBean bean = new TracciaSDIBean();
						bean.setDTO(dto);
						bean.setIdFattura(this.idFattura);

						lstRet.add(bean);
					}
				}
			}
		}catch (ServiceException e) {
			TracciaSDIService.log.error("Si e' verificato un errore durante la find all allegati:" + e.getMessage(), e);
			throw e;
		}  

		return lstRet;
	}

	@Override
	public TracciaSDIBean findById(Long arg0) throws ServiceException {
		return null;
	}

	@Override
	public void store(TracciaSDIBean arg0) throws ServiceException {
	}


	private TracciaSdIFilter getFilter() {
		TracciaSdIFilter filter = this.tracciaSdiBD.newFilter();

		filter.setIdentificativoSdi(this.idFattura.getIdentificativoSdi());
		filter.setPosizione(this.idFattura.getPosizione());
		filter.setConsentiPosizioneNull(true);

		return filter;
	}

	public boolean eseguiRicerca() {
		return this.idFattura != null;
	}

	@Override
	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}

	@Override
	public IdFattura getIdFattura() {
		return this.idFattura;
	}
}
