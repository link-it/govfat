package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.RegistroService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public class EnteMBean extends BaseMBean<EnteBean, Long, EnteSearchForm>{

	private static Logger log = LoggerManager.getConsoleLogger();

	private boolean showForm = false;

	private String azione = null;

	private EnteForm form = null;

	private RegistroMBean registroMBean = null;

	public EnteMBean (){
		log.debug("Ente MBean");

		this.registroMBean = new RegistroMBean();
		IRegistroService registroService = new RegistroService();
		RegistroSearchForm registroSearchForm = new RegistroSearchForm();
		registroSearchForm.getEnte().setValue(Utils.getEnte().getNome()); 
		registroService.setForm(registroSearchForm); 
		this.registroMBean.setService(registroService);

		this.form = new EnteForm();
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;

	}

	@Override
	public void setService(IBaseService<EnteBean, Long, EnteSearchForm> service) {
		super.setService(service);
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	public void setSearch(EnteSearchForm search) {
		this.search = search;
		this.selectedElement = null;

		this.search.getNome().setValue(Utils.getEnte().getNome()); 
	}

	@Override
	public void setSelectedElement(EnteBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;
	}

	public String filtra(){
		//		return "filtra";
		return "listaEnti?faces-redirect=true";
	}


	public String resetFiltro(){
		this.search.reset();

		//		return "reset";
		return "listaEnti?faces-redirect=true";
	}


	public String menuAction(){
		this.search.reset();
		// In questa versione non c'e' la lista degli enti
		Ente ente = Utils.getEnte();
		EnteBean bean = new EnteBean();
		bean.setDTO(ente);

		this.setSelectedElement(bean);

		return "ente";

		//		return "listaEnti";
	}

	public String restoreSearch(){
		this.search.setRestoreSearch(true); 

		// In questa versione non c'e' la lista degli enti
		Ente ente = Utils.getEnte();
		EnteBean bean = new EnteBean();
		bean.setDTO(ente);

		this.setSelectedElement(bean);

		return "ente";
		//		return "listaEnti";
	}

	public String modifica(){
		this.showForm = true;
		this.azione = "update";
		this.form.setRendered(this.showForm);
		this.form.setValues(this.selectedElement);
		this.form.reset();
		return "modifica";
	}

	public String invia(){
		String msg = this.form.valida();

		if(msg!= null){
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
			return null;
		}

		try{
			long oldId = -1;
			Ente newEnte = this.form.getEnte();
			//	boolean isAdmin = false;
			// Add
			if(!azione.equals("update")){
				EnteBean bean = new EnteBean();
				bean.setDTO(newEnte);
				boolean exists = this.service.exists(bean);
//				EnteBean oldEnte = ((IEnteService) this.service).findEnteByNome(this.form.getNome().getValue());

				if(exists){
					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione") +
							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.dipartimentoEsistente",this.form.getNome().getValue()));
					return null;
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
				//isAdmin = this.selectedElement.getDTO().getSuperuser();
			}

			
			newEnte.setId(oldId);

			EnteBean bean = new EnteBean();
			bean.setDTO(newEnte);

			this.service.store(bean);
			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.salvataggioOk"));
			this.setSelectedElement(bean);

			//aggiorno ente
			Utils.getLoginBean().setEnte(newEnte); 

			return null;//"invia";
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il salvataggio dell'ente: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
			return null;
		}
	}

	public String annulla(){
		String action = "listaEnti";

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			action = "annulla";
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return action;
	}
	
	public String annullaRegistro(){
		String action = this.registroMBean.annulla();

		if(action.equals("ente")){
			return this.restoreSearch();
		}
		
		return action;
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.showForm = true;
		this.azione = "new";
		this.form.setValues(null);
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

	public EnteForm getForm() {
		return form;
	}

	public void setForm(EnteForm form) {
		this.form = form;
	}

	public RegistroMBean getRegistroMBean() {
		return registroMBean;
	}

	public void setRegistroMBean(RegistroMBean registroMBean) {
		this.registroMBean = registroMBean;
	}

}
