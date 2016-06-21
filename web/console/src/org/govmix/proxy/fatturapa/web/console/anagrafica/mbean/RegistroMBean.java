package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;

public class RegistroMBean extends BaseMBean<RegistroBean, Long, RegistroSearchForm>{ 

	private static Logger log = LoggerManager.getConsoleLogger();

	private boolean showForm = false;

	private String azione = null;

	private RegistroForm form = null;
	
	private Long selectedId = null;
	
	public RegistroMBean (){
		log.debug("RegistroMBean");
		
		this.form = new RegistroForm();
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;

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
				RegistroBean dettaglioRegistro = this.service.findById(this.selectedId);
				setSelectedElement(dettaglioRegistro); 
			} catch (ServiceException e) {
				log.error("Si e' verificato un errore durante il caricamento del dettaglio registro: " + e.getMessage(), e);
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
	
	public String filtra(){
		//		return "filtra";
		return "listaEnti?faces-redirect=true";
	}


	public String resetFiltro(){
		this.search.reset();
		//		return "reset";
		return "listaEnti?faces-redirect=true";
	}
	
	public String modifica(){
		this.showForm = true;
		this.azione = "update";
		this.form.setRendered(this.showForm);
		this.form.setValues(this.selectedElement);
		this.form.setListaNomiProperties(this.getListaRegistroProperties());
		this.form.reset();
		return "modifica";
	}
	
	public String invia(){
		String msg = this.form.valida();

		if(msg!= null){
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione")+": " + msg);
			return null;
		}

		try{
			long oldId = -1;
			Registro newRegistro = this.form.getRegistro();
			newRegistro.setIdEnte(Utils.getIdEnte());
			//	boolean isAdmin = false;
			// Add
			if(!azione.equals("update")){
				RegistroBean bean = new RegistroBean();
				bean.setDTO(newRegistro);
				boolean exists = this.service.exists(bean);
//				RegistroBean oldEnte = this.enteService.findRegistroByNome(this.form.getNome().getValue());

				if(exists){
					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreValidazione") +
							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("registro.form.dipartimentoEsistente",this.form.getNome().getValue()));
					return null;
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
			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("registro.form.salvataggioOk"));
			this.setSelectedElement(bean);

			return null;//"invia";
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il salvataggio del registro: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("registro.form.erroreGenerico"));
			return null;
		}
	}

	public String annulla(){
		String action = "ente";

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			action = "registro";
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return action;
	}
	
	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
		this.showForm = true;
		this.azione = "new";
		this.form.setValues(null);
		this.form.setListaNomiProperties(this.getListaRegistroProperties());
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

	public RegistroForm getForm() {
		return form;
	}

	public void setForm(RegistroForm form) {
		this.form = form;
	}

	public List<RegistroProperty> getListaRegistroProperties(){
		List<RegistroProperty> lista = new ArrayList<RegistroProperty>();
		
		try{
			lista = ((IRegistroService)  this.service).getListaRegistroPropertiesEnte(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il caricamento delle properties: " + e.getMessage(), e);
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
				deleteMsg = Utils.getInstance().getMessageFromCommonsResourceBundle("DELETE_ERROR") + ": " + e.getMessage();
			}
		}

		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			log.debug("["+eliminati+"] Registri eliminati con successo.");
			MessageUtils.addInfoMsg(
					Utils.getInstance().getMessageFromResourceBundle("registro.deleteOk"));
		}else {
			log.error("Si e' verificato un errore durante l'eliminazione dei registri: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}
		
		return deleteMsg;
	}
}
