package org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel;

import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.openspcoop2.generic_project.web.impl.jsf1.datamodel.ParameterizedDataModel;

public class RegistroDM  


extends ParameterizedDataModel<Long, RegistroBean, IRegistroService, Registro, RegistroSearchForm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
//				log.debug("Richiesti Record S["+start+"] L["+limit+"], FiltroPagina ["+this.getDataProvider().getForm().getCurrentPage()+"]"); 
//
//				this.getDataProvider().getForm().setStart(start);
//				this.getDataProvider().getForm().setLimit(limit); 
//
//				this.wrappedKeys = new ArrayList<Long>();
//				List<RegistroBean> list = this.getDataProvider().findAllRegistri(start, limit);
//				for (RegistroBean utente : list) {
//					Long id = utente.getDTO().getId();
//
//					this.wrappedData.put(id, utente);
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
//		//	if(this.getDataProvider().getForm().isNewSearch()){
//				try {
//					this.rowCount = this.getDataProvider().totalCountRegistri();
//					this.getDataProvider().getForm().setTotalCount(this.rowCount);
//				} catch (ServiceException e) {
//					this.getDataProvider().getForm().setTotalCount(0);
//					return 0;
//				}
//				this.getDataProvider().getForm().setNewSearch(false); 
//		//	}
//		//	else {
//		//		this.rowCount = this.getDataProvider().getForm().getTotalCount();
//		//	}
//		}
//		return this.rowCount;
//	}
//
//	@Override
//	public Object getRowData() {
//		if(this.currentPk==null)
//			return null;
//		else{
//			RegistroBean t = this.wrappedData.get(this.currentPk);
//			if(t==null){
//				try {
//					t=this.getDataProvider().findRegistroById(this.currentPk);
//					this.wrappedData.put(this.currentPk, t);
//				} catch (ServiceException e) {}
//			}
//			return t;
//		}
//	}
}
