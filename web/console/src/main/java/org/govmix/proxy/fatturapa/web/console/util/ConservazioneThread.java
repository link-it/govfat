package org.govmix.proxy.fatturapa.web.console.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;

public class ConservazioneThread implements Runnable{

	private List<Long> ids = null;
	private boolean isAll;
	private FatturaBD fatturaBD;
	private FatturaFilter fatturaFilter;
	private static Logger log = LoggerManager.getConsoleLogger();

	public ConservazioneThread() {
		ConservazioneThread.log.debug("Init Conservazione Thread completata.");
	}

	@Override
	public void run() {
		try {
			ConservazioneThread.log.debug("Conservazione Thread inizio elaborazione");
			if(isAll){
				ids = getLstIdFattura(fatturaBD, fatturaFilter);
			}

			this.fatturaBD.inviaInConservazione(ids);
			
			ConservazioneThread.log.debug("Conservazione Thread elaborazione completata");
		} catch(Exception e) {
			ConservazioneThread.log.error("Conservazione Thread elaborazione terminata con errore: " + e.getMessage(), e);
		}
	}


	private List<Long> getLstIdFattura(FatturaBD fatturaBD, FatturaFilter expressionFromSearch) throws Exception {

		FatturaFilter filter = null;
		int start = 0;
		int limit = 1000;
		if(expressionFromSearch != null){
			filter = expressionFromSearch;
			List<FilterSortWrapper> filterSortList = new ArrayList<FilterSortWrapper>();
			FilterSortWrapper wrap = new FilterSortWrapper();
			wrap.setField(FatturaElettronica.model().DATA_RICEZIONE);
			wrap.setSortOrder(SortOrder.DESC);
			filterSortList.add(wrap);
			filter.setFilterSortList(filterSortList);
		} else {
			filter = fatturaBD.newFilter();
		} 

		filter.setOffset(start);
		filter.setLimit(limit);

		List<Long> listFattura = fatturaBD.findAllTableIds(filter);

		int size = listFattura.size();
		while(size>0){

			start+=listFattura.size();
			filter.setOffset(start);
			filter.setLimit(limit);

			List<Long> findAllIds =  fatturaBD.findAllTableIds(filter);
			listFattura.addAll(findAllIds);
			size = findAllIds.size();
		}
		return listFattura;

	}

	public List<Long> getIds() {
		return ids;
	}


	public void setIds(List<Long> ids) {
		this.ids = ids;
	}


	public boolean isAll() {
		return isAll;
	}


	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}


	public FatturaBD getFatturaBD() {
		return fatturaBD;
	}


	public void setFatturaBD(FatturaBD fatturaBD) {
		this.fatturaBD = fatturaBD;
	}


	public FatturaFilter getFatturaFilter() {
		return fatturaFilter;
	}


	public void setFatturaFilter(FatturaFilter fatturaFilter) {
		this.fatturaFilter = fatturaFilter;
	}



}
