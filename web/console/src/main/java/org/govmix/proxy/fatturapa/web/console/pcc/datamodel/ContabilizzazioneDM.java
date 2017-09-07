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
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ContabilizzazionePccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IContabilizzazioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.mbean.ContabilizzazioneMBean;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf1.datamodel.BaseDataModel;

public class ContabilizzazioneDM extends BaseDataModel<ContabilizzazionePccBean,Long, IContabilizzazioneService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerManager.getConsoleLogger();
	private IdFattura idFattura = null;
	private ContabilizzazioneMBean contabilizzazioneMBean = null;

	@Override
	public Long getId(ContabilizzazionePccBean object) {
		if(object != null)
			return object.getId();

		return null;
	}

	@Override
	public int getRowCount() {
		if(this.rowCount==null){
			try {
				this.getDataProvider().setIdFattura(this.idFattura);
				this.rowCount = this.getDataProvider().totalCount();
			} catch (ServiceException e) {
				return 0;
			}
		}
		return this.rowCount;
	}

	@Override
	public Object getRowData() {
		if(this.currentPk==null)
			return null;
		else{
			ContabilizzazionePccBean t = this.wrappedData.get(this.currentPk);
			if(t==null){
				try {
					t=this.getDataProvider().findById(this.currentPk);
					this.wrappedData.put(this.currentPk, t);
				} catch (ServiceException e) {}
			}
			return t;
		}
	}

	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) throws IOException {
		try{
			if(this.detached){
				for (Long  key : this.wrappedKeys) {
					setRowKey(key);
					visitor.process(context, key, argument);
				}
			}else{
				int start = 0; int limit = 0;

				start = ((SequenceRange)range).getFirstRow();
				limit = ((SequenceRange)range).getRows();

				this.wrappedKeys = new ArrayList<Long>();
				this.getDataProvider().setIdFattura(this.idFattura);
				
				List<ContabilizzazionePccBean> list = this.getDataProvider().findAll(start, limit);
				for (ContabilizzazionePccBean bean : list) {
					Long id = bean.getId();

					this.wrappedData.put(id, bean);
					this.wrappedKeys.add(id);
					visitor.process(context, id, argument);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}

	public IdFattura getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}

	public ContabilizzazioneMBean getContabilizzazioneMBean() {
		return contabilizzazioneMBean;
	}

	public void setContabilizzazioneMBean(ContabilizzazioneMBean contabilizzazioneMBean) {
		this.contabilizzazioneMBean = contabilizzazioneMBean;
		
		if(this.contabilizzazioneMBean != null && this.getDataProvider() != null){
			this.getDataProvider().setCacheDati(this.contabilizzazioneMBean.getListaContabilizzazioni());
			
		}
	}
	
	

}
 