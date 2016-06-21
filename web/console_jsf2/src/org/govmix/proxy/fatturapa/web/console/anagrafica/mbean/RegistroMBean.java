package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.RegistroService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf2.mbean.BaseListView;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.MessageUtils;
import org.openspcoop2.generic_project.web.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.mbean.exception.ModificaException;

@ViewScoped @ManagedBean(name="registroMBean")
public class RegistroMBean extends BaseListView<RegistroBean, Long, RegistroSearchForm,RegistroForm, Registro>{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private boolean showForm = false;

	private String azione = null;

	// private EnteMBean enteMBean = null;

	public RegistroMBean () throws Exception{
		super(LoggerManager.getConsoleLogger());
	}

	@Override
	public void init() throws Exception {
		this.log.debug("RegistroMBean");

		this.form = new RegistroForm();
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;

	}

	@PostConstruct
	private void initManagedProperties(){
		setSearch((RegistroSearchForm) Utils.findBean("registroSearchForm"));
		this.search.getEnte().setValue(Utils.getEnte().getNome()); 
		this.service = (RegistroService) Utils.findBean("registroService");

		// Navigazione verso la pagina di dettaglio
		String reqParameterValue = Utils.getRequestParameter(this.getSelectedIdParamName());

		// In teoria risolve il problema della navigazione
		if(reqParameterValue != null){
			setSelectedIdParam(reqParameterValue);
		}

		// navigazione dalla tabella alla schermata nuovo
		String nuovoValue = Utils.getRequestParameterEndsWith("_aggiungi");
		if(nuovoValue != null)
			this.addNewListener(null);
	}

	@Override
	public void initSelectedIdParamName() throws Exception {
		setSelectedIdParamName("selectedIdRegistro"); 
	}

	@Override
	public void initNavigationManager() throws Exception {
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("registro");
		this.getNavigationManager().setFiltraOutcome("listaEnti?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome(null);
		this.getNavigationManager().setModificaOutcome(null); //"modifica");
		this.getNavigationManager().setNuovoOutcome("registro");
		this.getNavigationManager().setResetOutcome("listaEnti?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome(null);
	}

	@Override
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;

		if(this.selectedId != null){

			try {
				//carico dettaglio utente
				RegistroBean dettaglioRegistro = this.service.findById(this.selectedId);
				setSelectedElement(dettaglioRegistro); 
			} catch (ServiceException e) {
				this.log.error("Si e' verificato un errore durante il caricamento del dettaglio registro: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public void setSelectedElement(RegistroBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;
	}

	public void setSelectedIdParam(String selectedIdParValue) {
		this.selectedIdParam = selectedIdParValue;

		this.log.debug("Selected Id ["+selectedIdParValue+"]");

		if(selectedIdParValue!= null){
			this.setSelectedId(new Long(this.selectedIdParam)); 
		}
	}

	@Override
	public String azioneModifica() throws ModificaException {
		try{
			this.showForm = true;
			this.azione = "update";
			this.form.setRendered(this.showForm);
			this.form.setObject(this.selectedElement);
			this.form.setListaNomiProperties(this.getListaRegistroProperties());
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
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione")+": " + msg);
			//			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione")+": " + msg);
			//			return null;
		}

		try{
			long oldId = -1;
			Registro newRegistro = this.form.getObject();
			newRegistro.setIdEnte(Utils.getIdEnte());
			//	boolean isAdmin = false;
			// Add
			if(!this.azione.equals("update")){
				RegistroBean bean = new RegistroBean();
				bean.setDTO(newRegistro);
				boolean exists = this.service.exists(bean);
				//				RegistroBean oldEnte = this.enteService.findRegistroByNome(this.form.getNome().getValue());

				if(exists){
					throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione") +
							": " +org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("registro.form.dipartimentoEsistente",this.form.getNome().getValue()));
					//					MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione") +
					//							": " +org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("registro.form.dipartimentoEsistente",this.form.getNome().getValue()));
					//					return null;
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
				//isAdmin = this.selectedElement.getDTO().getSuperuser();
			}


			newRegistro.setId(oldId);


			RegistroBean bean = new RegistroBean();
			bean.setDTO(newRegistro);
			bean.setListaNomiProperties(this.getListaRegistroProperties());


			this.service.store(bean);
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.salvataggioOk"));
			this.setSelectedElement(bean);

			return this.getNavigationManager().getInviaOutcome();//"invia";
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio del registro: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreGenerico"));
			//			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreGenerico"));
			//			return null;
		}
	}

	@Override
	public String azioneAnnulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome("ente");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("registro");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		if(this.getNavigationManager().getAnnullaOutcome().equals("ente")){
			EnteMBean enteMBean = (EnteMBean ) Utils.findBean("enteMBean");
			return enteMBean.restoreSearch(); //this.getEnteMBean().restoreSearch();
		}
		else 
			return super.azioneAnnulla();
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		try{
			super.addNewListener(ae);
			this.selectedElement = null;
			this.selectedId = null;
			this.showForm = true;
			this.azione = "new";
			this.form.setObject(null);
			this.form.setListaNomiProperties(this.getListaRegistroProperties());
			this.form.setRendered(this.showForm); 
			this.form.reset();
		}catch(Exception e){
			this.getLog().error(e, e);
		}
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

	public List<RegistroProperty> getListaRegistroProperties(){
		List<RegistroProperty> lista = new ArrayList<RegistroProperty>();

		try{
			lista = ((IRegistroService)  this.service).getListaRegistroPropertiesEnte(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte());
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il caricamento delle properties: " + e.getMessage(), e);
		}


		return lista;
	}

	@Override
	public String delete(){
		this.toRemove = new ArrayList<RegistroBean>();
		Iterator<RegistroBean> it = this.selectedIds.keySet().iterator();
		while (it.hasNext()) {
			RegistroBean elem = it.next();
			if(this.selectedIds.get(elem).booleanValue()){
				this.toRemove.add(elem);
				it.remove();
			}
		}

		String deleteMsg = null;
		for (RegistroBean elem : this.toRemove) {
			try{
				this.service.delete(elem);
			}catch (Exception e) {
				deleteMsg = org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromCommonsResourceBundle("DELETE_ERROR") + ": " + e.getMessage();
			}
		}

		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			this.log.debug("["+eliminati+"] Registri eliminati con successo.");
			MessageUtils.addInfoMsg(
					org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils.getInstance().getMessageFromResourceBundle("registro.deleteOk"));
		}else {
			this.log.error("Si e' verificato un errore durante l'eliminazione dei registri: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
	}

	//	public EnteMBean getEnteMBean() {
	//		return enteMBean;
	//	}
	//
	//	public void setEnteMBean(EnteMBean enteMBean) {
	//		this.enteMBean = enteMBean;
	//	}
}
