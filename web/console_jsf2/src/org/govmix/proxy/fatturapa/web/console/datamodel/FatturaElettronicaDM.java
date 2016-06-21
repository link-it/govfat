/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.datamodel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf2.datamodel.ParameterizedDataModel;

/**
 * FatturaElettronicaDM classe di supporto alla visualizzazione delle fatture nella console.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
@RequestScoped @ManagedBean(name="fatturaElettronicaDM")
public class FatturaElettronicaDM extends

ParameterizedDataModel<FatturaElettronica,Long, FatturaElettronicaBean, IFatturaElettronicaService,  FatturaElettronicaSearchForm>{

	
	@PostConstruct
	private void initManagedProperties(){
		this.form = (FatturaElettronicaSearchForm) Utils.findBean("fatturaElettronicaSearch");
		this.setDataProvider((FatturaElettronicaService) Utils.findBean("fatturaElettronicaService"));
	}
	
	/**
	 * 
	 */
//	private static final long serialVersionUID = -3498902673441284275L;
//
//	private static Logger log = LoggerManager.getConsoleLogger();
//	
//	@Override
//	public void walk(FacesContext context, DataVisitor visitor, Range range,
//			Object argument) throws IOException {
//		try{
//			if(detached){
//				for (Long  key : this.wrappedKeys) {
//					setRowKey(key);
//					visitor.process(context, key, argument);
//				}
//			}else{
//				int start = 0; int limit = 0;
//				// ripristino la ricerca.
//				if(this.getDataProvider().getForm().isRestoreSearch()){
//					start = this.getDataProvider().getForm().getStart();
//					limit = this.getDataProvider().getForm().getLimit();
//					this.getDataProvider().getForm().setRestoreSearch(false);
//					
//					int pageIndex = (start / limit) + 1;
//					this.getDataProvider().getForm().setPageIndex(pageIndex);
//					this.getDataProvider().getForm().setCurrentPage(pageIndex);
//					// Aggiorno valori paginazione
//					range = new SequenceRange(start,limit);
//				}
//				else{
//					  start = ((SequenceRange)range).getFirstRow();
//					  limit = ((SequenceRange)range).getRows();
//				}
//				
//				log.debug("Richiesti Record S["+start+"] L["+limit+"]"); 
//
//				this.getDataProvider().getForm().setStart(start);
//				this.getDataProvider().getForm().setLimit(limit); 
//				
//				this.wrappedKeys = new ArrayList<Long>();
//				List<FatturaElettronicaBean> list = this.getDataProvider().findAll(start, limit);
//				for (FatturaElettronicaBean evento : list) {
//					Long id = evento.getDTO().getId();
//
//					this.wrappedData.put(id, evento);
//					this.wrappedKeys.add(id);
//					visitor.process(context, id, argument);
//				}
//			}
//		}catch (Exception e) {
//			log.error(e,e);
//		}
//
//	}
//
//	@Override
//	public int getRowCount() {
//		if(this.rowCount==null){
//			//if(this.getDataProvider().getForm().isNewSearch()){
//				try {
//					this.rowCount = this.getDataProvider().totalCount();
//					this.getDataProvider().getForm().setTotalCount(this.rowCount);
//				} catch (ServiceException e) {
//					this.getDataProvider().getForm().setTotalCount(0);
//					return 0;
//				}
//				this.getDataProvider().getForm().setNewSearch(false); 
////			}
////			else {
////				this.rowCount = this.getDataProvider().getForm().getTotalCount();
////			}
//		}
//		return this.rowCount;
//	}
//
//	@Override
//	public Object getRowData() {
//		if(this.currentPk==null)
//			return null;
//		else{
//			FatturaElettronicaBean t = this.wrappedData.get(this.currentPk);
//			if(t==null){
//				try {
//					t=this.getDataProvider().findById(this.currentPk);
//					this.wrappedData.put(this.currentPk, t);
//				} catch (ServiceException e) {}
//			}
//			return t;
//		}
//	}

}
