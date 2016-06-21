package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.RegistroService;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;

public class DipartimentoMBean extends BaseMBean<DipartimentoBean, Long, DipartimentoSearchForm>{


	private static Logger log = LoggerManager.getConsoleLogger();

	private boolean showForm = false;

	private String azione = null;

	private DipartimentoForm form = null;
	
	private IRegistroService registroService = null;
	
	private Long selectedId = null;

	public DipartimentoMBean(){
		log.debug("Dipartimento MBean");

		this.form = new DipartimentoForm();
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;
		
		this.registroService = new RegistroService();
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	public void setSearch(DipartimentoSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedId = null;

		this.search.setmBean(this);
	}

	public Long getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;
		
		if(this.selectedId != null){
			
			try {
				//carico dettaglio utente
				DipartimentoBean dettaglioDipartimento = this.service.findById(this.selectedId);
				setSelectedElement(dettaglioDipartimento); 
			} catch (ServiceException e) {
				log.error("Si e' verificato un errore durante il caricamento del dettaglio dipartimento: " + e.getMessage(), e);
			}
		}
	}
	
	@Override
	public void setSelectedElement(DipartimentoBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;
	}

	// Action della pagina di ricerca
	public String filtra(){
//		return "filtra";
		return "listaDipartimenti?faces-redirect=true";
	}


	public String resetFiltro(){
		this.search.reset();
		
//		return "reset";
		return "listaDipartimenti?faces-redirect=true";
	}
 
	
	public String menuAction(){
		this.search.setRestoreSearch(true); 
		return "listaDipartimenti";
	}
	
	public String restoreSearch(){
		this.search.setRestoreSearch(true); 
		return "listaDipartimenti";
	}
	
	public String modifica(){
		this.showForm = true;
		this.azione = "update";
		this.form.setRendered(this.showForm);
		this.form.setValues(this.selectedElement);
		this.form.setListaNomiProperties(this.getListaDipartimentoProperties());
		this.form.getRegistro().setElencoSelectItems(this.getElencoRegistri());
		this.form.reset();
		return "reset";
	}

	public String invia(){
		String msg = this.form.valida();

		if(msg!= null){
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione")+": " + msg);
			return null;
		}

		try{
			long oldId = -1;
			Dipartimento newDip = this.form.getDipartimento();
			newDip.setEnte(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte()); 
			// Add
			if(!azione.equals("update")){
				DipartimentoBean bean = new DipartimentoBean();
				bean.setDTO(newDip);
				boolean exists = this.service.exists(bean);
//				DipartimentoBean oldDip = ((IDipartimentoService) this.service).findDipartimentoByCodice(this.form.getCodice().getValue());

				if(exists){
					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione") +
							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("dipartimento.form.dipartimentoEsistente",this.form.getCodice().getValue()));
					return null;
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
			}

			
			newDip.setId(oldId);
			

			DipartimentoBean bean = new DipartimentoBean();
			bean.setDTO(newDip);
			bean.setListaNomiProperties(this.getListaDipartimentoProperties());

			this.service.store(bean);
			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.salvataggioOk"));
			this.setSelectedElement(bean);

			// reset lista dei dipartimenti dell'utente
			org.govmix.proxy.fatturapa.web.console.util.Utils.getLoginBean().setListDipartimenti(null); 
			
			return null;//"invia";
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il salvataggio del dipartimento: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreGenerico"));
			return null;
		}
	}


	public String annulla(){
		String action = "listaDipartimenti";

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			action = "annulla";
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return action;
	}


	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> descrizioneAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<Dipartimento> lstDipartimenti = ((IDipartimentoService)this.service).getDescrizioneAutoComplete((String)val);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
								dipartimento.getDescrizione(), dipartimento.getDescrizione()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}
	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> codiceAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<Dipartimento> lstDipartimenti = ((IDipartimentoService)this.service).getCodiceAutoComplete((String)val);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
								dipartimento.getCodice(),dipartimento.getCodice()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
		this.showForm = true;
		this.azione = "new";
		this.form.setValues(null);
		this.form.setListaNomiProperties(this.getListaDipartimentoProperties());
		this.form.getRegistro().setElencoSelectItems(this.getElencoRegistri());
		this.form.setRendered(this.showForm); 
		this.form.reset();
	}



	public boolean isShowForm() {
		return showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public String getAzione() {
		return azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

	public DipartimentoForm getForm() {
		return form;
	}

	public void setForm(DipartimentoForm form) {
		this.form = form;
	}
	
	public List<DipartimentoProperty> getListaDipartimentoProperties(){
		List<DipartimentoProperty> lista = new ArrayList<DipartimentoProperty>();
		
		try{
			lista = ((IDipartimentoService)this.service).getListaPropertiesEnte(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il caricamento delle properties: " + e.getMessage(), e);
		}
		
		
		return lista;
	}
	
	private List<SelectItem> getElencoRegistri() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		
		SelectItem elem0 = new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));
		
		
		try {
			RegistroSearchForm registroForm = new RegistroSearchForm();
			registroForm.getEnte().setValue(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte().getNome());
			List<RegistroBean> findAllRegistri = this.registroService.findAll(registroForm);
			
			if(findAllRegistri != null && findAllRegistri.size() > 0){
				for (RegistroBean bean : findAllRegistri) {
					SelectItem item = new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
							bean.getNome().getValue(),bean.getNome().getValue()));
					lista.add(item);
				}
				
			}
		} catch (ServiceException e) {
			log.error("Si e' verificato un errore durante il caricamento dei registri: " + e.getMessage(), e);
		}
		
		lista.add(0, elem0);
		
		return lista;
	}
	
	@Override
	public String delete() {
	
		String deleteMsg = super.delete();
		
		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			log.debug("["+eliminati+"] Dipartimenti eliminati con successo.");
			String deletemsgok = Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteOk");
			MessageUtils.addInfoMsg(deletemsgok);
		}else {
			log.error("Si e' verificato un errore durante l'eliminazione dei dipartimenti: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}
		
		return deleteMsg;
	}
}
