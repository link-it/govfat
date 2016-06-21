package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.EnteService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf2.mbean.BaseListView;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;
import org.openspcoop2.generic_project.web.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.mbean.exception.ModificaException;
import org.openspcoop2.generic_project.web.mbean.exception.RestoreSearchException;

@ViewScoped @ManagedBean(name="enteMBean")
public class EnteMBean extends BaseListView<EnteBean, Long, EnteSearchForm,EnteForm,Ente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showForm = false;

	private String azione = null;

	//	private RegistroMBean registroMBean = null;

	public EnteMBean () throws Exception{
		super(LoggerManager.getConsoleLogger());


		//		this.registroMBean = new RegistroMBean();
		//		IRegistroService registroService = new RegistroService();
		//		RegistroSearchForm registroSearchForm = new RegistroSearchForm();
		//		registroSearchForm.getEnte().setValue(Utils.getEnte().getNome()); 
		//		registroService.setForm(registroSearchForm); 
		//		this.registroMBean.setService(registroService);
		//		this.registroMBean.setEnteMBean(this);
	}

	@PostConstruct
	private void initManagedProperties(){
		setSearch((EnteSearchForm) Utils.findBean("enteSearchForm"));
		this.service = (EnteService) Utils.findBean("enteService");

		//	setRegistroMBean((RegistroMBean) Utils.findBean("registroMBean")); 
		//		this.getRegistroMBean().setEnteMBean(this); menuLinkEnti


		String reqParameterValue = Utils.getRequestParameter("menuLinkEnti");

		// In teoria risolve il problema della navigazione
		if(reqParameterValue != null ){
			// menu click
			if(reqParameterValue.equals("menuLinkEnti"))
				try {
					this.azioneMenuAction();
				} catch (MenuActionException e) {
					this.getLog().error(e,e);
				}
		}
	}
	
	@Override
	public void initSelectedIdParamName() throws Exception {
		setSelectedIdParamName("selectedIdEnte"); 
	}

	@Override
	public void init() throws Exception {
		this.log.debug("Ente MBean");


		this.form = new EnteForm();
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;

	}

	@Override
	public void initNavigationManager() throws Exception {
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome("listaEnti?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("ente");
		this.getNavigationManager().setModificaOutcome(null);//"modifica");
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome("listaEnti?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("ente");
	}

	@Override
	public void setService(IBaseService<EnteBean, Long, EnteSearchForm> service) {
		super.setService(service);
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(EnteSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.search.getNome().setValue(Utils.getEnte().getNome()); 
	}
	
	public void setSelectedIdParam(String selectedIdParValue) {
		this.selectedIdParam = selectedIdParValue;

		this.log.debug("Selected Id ["+selectedIdParValue+"]");

		if(selectedIdParValue!= null){
			this.setSelectedId(new Long(this.selectedIdParam)); 
		}
	}
	 

	@Override
	public void setSelectedElement(EnteBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();
		this.showForm = false;
		this.azione = null;
	}

	@Override
	public String azioneMenuAction() throws MenuActionException {
		try{
			this.search.reset();
			// In questa versione non c'e' la lista degli enti
			Ente ente = Utils.getEnte();
			EnteBean bean = new EnteBean();
			bean.setDTO(ente);

			this.setSelectedElement(bean);

		}catch(Exception e){
			throw new MenuActionException(e);
		}
		return super.azioneMenuAction();
	}

	@Override
	public String azioneRestoreSearch() throws RestoreSearchException {
		try{
			this.search.setRestoreSearch(true); 

			// In questa versione non c'e' la lista degli enti
			Ente ente = Utils.getEnte();
			EnteBean bean = new EnteBean();
			bean.setDTO(ente);

			this.setSelectedElement(bean);
		}catch(Exception e){
			throw new RestoreSearchException(e);
		}
		return this.getNavigationManager().getRestoreSearchOutcome();
	}

	@Override
	public String azioneModifica() throws ModificaException {
		try{
			this.showForm = true;
			this.azione = "update";
			this.form.setRendered(this.showForm);
			this.form.setObject(this.selectedElement);
			this.form.reset();
		}catch(Exception e){
			throw new ModificaException(e);
		}
		return super.azioneModifica();
	}

	@Override
	public String azioneInvia() throws InviaException{
		String msg = this.form.valida();

		if(msg!= null){
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione")+": " + msg);
			//			return null;
		}

		try{
			long oldId = -1;
			Ente newEnte = this.form.getObject();
			//	boolean isAdmin = false;
			// Add
			if(!this.azione.equals("update")){
				EnteBean bean = new EnteBean();
				bean.setDTO(newEnte);
				boolean exists = this.service.exists(bean);
				//				EnteBean oldEnte = ((IEnteService) this.service).findEnteByNome(this.form.getNome().getValue());

				if(exists){
					throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione") +
							": " +org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.dipartimentoEsistente",this.form.getNome().getValue()));
					//					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreValidazione") +
					//							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("ente.form.dipartimentoEsistente",this.form.getNome().getValue()));
					//					return null;
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
				//isAdmin = this.selectedElement.getDTO().getSuperuser();
			}


			newEnte.setId(oldId);

			EnteBean bean = new EnteBean();
			bean.setDTO(newEnte);

			this.service.store(bean);
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.salvataggioOk"));
			this.setSelectedElement(bean);

			//aggiorno ente
			Utils.getLoginBean().setEnte(newEnte); 

			return this.getNavigationManager().getInviaOutcome();//"invia";
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio dell'ente: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("ente.form.erroreGenerico"));
			//			return null;
		}
	}

	@Override
	public String azioneAnnulla() throws AnnullaException {
		
//		this.getNavigationManager().setAnnullaOutcome("listaEnti");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
//			this.getNavigationManager().setAnnullaOutcome("annulla");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return super.azioneAnnulla();
	}

	public String annullaRegistro(){
		String action = this.getRegistroMBean().annulla();

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
		this.form.setObject(null);
		this.form.setRendered(this.showForm); 
		this.form.reset();
	}

	public boolean isShowForm() {
		return this.showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}

	public String getAzione() {
		return this.azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

	public RegistroMBean getRegistroMBean() {
		return (RegistroMBean) Utils.findBean("registroMBean");
		//		return this.registroMBean;
	}

	//	public void setRegistroMBean(RegistroMBean registroMBean) {
	//		this.registroMBean = registroMBean;
	//	}

}
