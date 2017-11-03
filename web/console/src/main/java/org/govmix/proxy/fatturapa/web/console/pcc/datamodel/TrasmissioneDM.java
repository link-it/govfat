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
package org.govmix.proxy.fatturapa.web.console.pcc.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.TracciaTrasmissionePCCBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.ITrasmissioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.TrasmissioneSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf1.datamodel.ParameterizedDataModel;

public class TrasmissioneDM extends

ParameterizedDataModel<PccTracciaTrasmissione, Long, TracciaTrasmissionePCCBean, ITrasmissioneService,  TrasmissioneSearchForm>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3498902673441284275L;

	private IdTraccia idTraccia = null;

	private static Logger log = LoggerManager.getConsoleLogger();

	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		try{
			if(detached){
				for (Long  key : this.wrappedKeys) {
					setRowKey(key);
					visitor.process(context, key, argument);
				}
			}else{
				int start = 0; int limit = 0;
				// ripristino la ricerca.
				if(this.getDataProvider().getForm().isRestoreSearch()){
					start = this.getDataProvider().getForm().getStart();
					limit = this.getDataProvider().getForm().getLimit();
					this.getDataProvider().getForm().setRestoreSearch(false);

					int pageIndex = (start / limit) + 1;
					this.getDataProvider().getForm().setPageIndex(pageIndex);
					this.getDataProvider().getForm().setCurrentPage(pageIndex);
					// Aggiorno valori paginazione
					range = new SequenceRange(start,limit);
				}
				else{
					start = ((SequenceRange)range).getFirstRow();
					limit = ((SequenceRange)range).getRows();
				}

				TrasmissioneDM.log.debug("Richiesti Record S["+start+"] L["+limit+"]"); 

				this.getDataProvider().getForm().setStart(start);
				this.getDataProvider().getForm().setLimit(limit); 

				this.wrappedKeys = new ArrayList<Long>();
				if(this.idTraccia != null)
					this.getDataProvider().setIdTraccia(this.idTraccia);
				List<TracciaTrasmissionePCCBean> list = this.getDataProvider().findAll(start, limit);
				for (TracciaTrasmissionePCCBean traccia : list) {
					Long id = traccia.getDTO().getId();

					this.wrappedData.put(id, traccia);
					this.wrappedKeys.add(id);
					visitor.process(context, id, argument);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}

	@Override
	public int getRowCount() {
		if(this.rowCount==null){
			try {
				if(this.idTraccia != null)
					this.getDataProvider().setIdTraccia(this.idTraccia);

				this.rowCount = this.getDataProvider().totalCount();
				this.getDataProvider().getForm().setTotalCount(this.rowCount);
			} catch (ServiceException e) {
				this.getDataProvider().getForm().setTotalCount(0);
				return 0;
			}
		}
		return this.rowCount;
	}

	public IdTraccia getIdTraccia() {
		return idTraccia;
	}

	public void setIdTraccia(IdTraccia idTraccia) {
		this.idTraccia = idTraccia;
	}
}
