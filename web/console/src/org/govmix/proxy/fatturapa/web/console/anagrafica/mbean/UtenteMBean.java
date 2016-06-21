package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.UtenteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.UtenteForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.UtenteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IUtenteService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.DipartimentoService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.utils.crypt.Password;

public class UtenteMBean extends BaseMBean<UtenteBean, Long, UtenteSearchForm> {

	private IDipartimentoService dipartimentoService = null;

	private List<SelectItem> listaDipartimentiEnte = null;

	private List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaDipartimentiEnte2 = null;

	private static Logger log = LoggerManager.getConsoleLogger();

	private boolean showForm = false;

	private String azione = null;

	private UtenteForm form = null;

	private Long selectedId = null;

	public UtenteMBean (){
		log.debug("Utente MBean");

		this.form = new UtenteForm();
		this.form.setmBean(this); 
		this.form.setRendered(false);
		this.form.reset();

		this.showForm = false;
		this.azione = null;

		this.dipartimentoService = new DipartimentoService();

	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	public void setSearch(UtenteSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedId = null;

		this.search.getDipartimento().setElencoSelectItems(this.getListaDipartimentiEnte());
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
				UtenteBean dettaglioUtente = this.service.findById(this.selectedId);
				setSelectedElement(dettaglioUtente); 
			} catch (ServiceException e) {
				log.error("Si e' verificato un errore durante il caricamento del dettaglio utente: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public void setSelectedElement(UtenteBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.form.setRendered(false);
		this.form.reset();

		if(this.selectedElement != null){
			try {
				List<DipartimentoBean> listaDipartimentiUtente = this.dipartimentoService.getListaDipartimentiUtente(selectedElement.getDTO(), org.govmix.proxy.fatturapa.web.console.util.Utils.getEnte(), true);

				this.selectedElement.setListaDipartimenti(listaDipartimentiUtente); 
			} catch (ServiceException e) {
				log.error("Si e' verificato un errore durante la lettura dei dipartimenti utente: " + e.getMessage(), e);
			}
		}
		this.showForm = false;
		this.azione = null;
	}

	// Action della pagina di ricerca

	public String filtra(){
		//		return "filtra";
		return "listaUtenti?faces-redirect=true";
	}


	public String resetFiltro(){
		this.search.reset();

		//		return "reset";
		return "listaUtenti?faces-redirect=true";
	}


	public String menuAction(){
		this.search.setRestoreSearch(true); 
		return "listaUtenti";
	}

	public String restoreSearch(){
		this.search.setRestoreSearch(true); 
		return "listaUtenti";
	}

	public String modifica(){
		this.showForm = true;
		this.azione = "update";
		this.form.setRendered(this.showForm);
		this.form.setValues(this.selectedElement);
		this.form.getRuolo().setElencoSelectItems(getListaRuoli());

		this.listaDipartimentiEnte2 = null;
		this.form.getDipartimento().setElencoCustomTypeSelectItems(this.getListaDipartimentiEnte2());


		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> itemCorrenti = this.form.getDipartimento().getDefaultValue();

		List<Integer> listaId = new ArrayList<Integer>();
		for (int i = 0 ; i < this.form.getDipartimento().getElencoCustomTypeSelectItems().size() ; i ++) {
			org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = this.form.getDipartimento().getElencoCustomTypeSelectItems().get(i);
			for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem oldItem : itemCorrenti) {
				if(item.getValue().equals(oldItem.getValue())){
					listaId.add(0,i);
				}
			}
		}

		for (Integer idToRemove : listaId) {
			this.form.getDipartimento().getElencoCustomTypeSelectItems().remove(idToRemove.intValue());
		}

		this.form.reset();
		this.form.getDipartimento().checkDimensions(); 
		return "reset";
	}

	public String invia(){
		String msg = this.form.valida();

		if(msg!= null){
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("utente.form.erroreValidazione")+": " + msg);
			return null;
		}

		try{
			long oldId = -1;
			Utente newUtente = this.form.getUtente();
			newUtente.setEnte(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte());
			//	boolean isAdmin = false;
			// Add
			if(!azione.equals("update")){
				UtenteBean bean = new UtenteBean();
				bean.setDTO(newUtente);
				boolean exists = this.service.exists(bean);
				//				UtenteBean oldUtente = ((IUtenteService) this.service).findUtenteByUsername(this.form.getUsername().getValue());

				if(exists){
					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("utente.form.erroreValidazione") +
							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("utente.form.dipartimentoEsistente",this.form.getUsername().getValue()));
					return null;
				}
			} else {
				oldId = this.selectedElement.getDTO().getId();
				//isAdmin = this.selectedElement.getDTO().getSuperuser();
			}


			newUtente.setId(oldId);

			//newUtente.setSuperuser(isAdmin);

			Password password = new Password();
			String passwordInserita = this.form.getPassword().getValue();
			// Cifro la pwd solo se e' un nuovo utente o se l'ho modificata
			if(!Utils.getLoginBean().isNoPasswordLogin()){

				String cryptPw = password.cryptPw(passwordInserita);

				// nuovo
				if(oldId == -1){
					newUtente.setPassword(cryptPw);
				}
				else {
					// Se ho aggiornato la password
					if(passwordInserita.equals(this.form.getPassword().getDefaultValue())) {
						newUtente.setPassword(this.form.getPassword().getDefaultValue());
					}else {
						newUtente.setPassword(cryptPw);
					}
				}
			} else {
				newUtente.setPassword(passwordInserita);
			}

			UtenteBean bean = new UtenteBean();
			bean.setDTO(newUtente);

			this.service.store(bean);
			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("utente.form.salvataggioOk"));
			this.setSelectedElement(bean);

			// reset lista dei dipartimenti dell'utente
			org.govmix.proxy.fatturapa.web.console.util.Utils.getLoginBean().setListDipartimenti(null);

			return null;//"invia";
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il salvataggio dell'utente: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("utente.form.erroreGenerico"));
			return null;
		}
	}

	public String annulla(){
		String action = "listaUtenti";

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			action = "annulla";
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return action;
	}


	public List<SelectItem> getListaDipartimentiEnte() {
		this.listaDipartimentiEnte = new ArrayList<SelectItem>();

		log.debug("Caricamento Lista Dipartimenti in corso...");
		try{
			
			List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaDipartimentiEnte2 = this.getListaDipartimentiEnte2();
			
			if(listaDipartimentiEnte2 != null && listaDipartimentiEnte2.size() > 0){
				for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item : listaDipartimentiEnte2) {
					this.listaDipartimentiEnte.add(new SelectItem(item));
				}
			}
		}catch(Exception e){
			log.error("Si e' verificato un errore durante il caricamento della lista dipartimenti: "+ e.getMessage(), e);
		}

		this.listaDipartimentiEnte.add(0,new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,
						("commons.label.qualsiasi"))));
		
		log.debug("Caricamento Lista Dipartimenti completata.");

		return listaDipartimentiEnte;
	}

	public void setListaDipartimentiEnte2(List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaDipartimentiEnte) {
		this.listaDipartimentiEnte2 = listaDipartimentiEnte;
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> getListaDipartimentiEnte2() {
		if(this.listaDipartimentiEnte2 == null){

			this.listaDipartimentiEnte2 = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

			log.debug("Caricamento Lista Dipartimenti in corso...");
			try{
				DipartimentoSearchForm ricerca = new DipartimentoSearchForm();
				List<DipartimentoBean> lstDip = this.dipartimentoService.findAll(ricerca);

				if(lstDip != null && lstDip.size() > 0){
					for (DipartimentoBean dipartimentoBean : lstDip) {
						Dipartimento d = dipartimentoBean.getDTO();

						String descrizioneConCodice = d.getDescrizione()+ " (" + d.getCodice() + ")";
//						log.debug("Descr ["+descrizioneConCodice+"] L["+descrizioneConCodice.length()+"]");
						org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
								d.getCodice(),descrizioneConCodice.trim());
						this.listaDipartimentiEnte2.add(item);

					}
				}
			}catch(Exception e){
				log.error("Si e' verificato un errore durante il caricamento della lista dipartimenti: "+ e.getMessage(), e);
			}
		}
		log.debug("Caricamento Lista Dipartimenti completata.");
		
		//		this.listaDipartimentiEnte.add(0,new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO)));

		return listaDipartimentiEnte2;
	}

	public void setListaDipartimentiEnte(List<SelectItem> listaDipartimentiEnte) {
		this.listaDipartimentiEnte = listaDipartimentiEnte;
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> denominazioneAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{
				List<String> lstNomiCognomi = ((IUtenteService)this.service).getDenominazioneAutoComplete((String)val);

				if(lstNomiCognomi != null && lstNomiCognomi.size() > 0){
					for (String string : lstNomiCognomi) {
						//						String label = string.replace(UtenteService.SEPARATORE, " ");

						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(string,string));
					}
				}
			}

		}catch(Exception e ){
			log.error("Si e' verificato un errore durante la lettura dei nomi utente: "+ e.getMessage(), e);
		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	public List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> descrizioneDipartimentoAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
//		log.debug("descrizione dipartimento autocomplete["+val+"]");
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem>();
			else{

				List<Dipartimento> lstDipartimenti = this.getDipartimentiDisponibili((String)val, this.form.getDipartimento().getValue());

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						String descrizioneConCodice = dipartimento.getDescrizione() + " ("+ dipartimento.getCodice() + ")";
//						log.debug("Descr ["+descrizioneConCodice+"] L["+descrizioneConCodice.length()+"]");
						lst.add(new  org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(dipartimento.getCodice(),descrizioneConCodice.trim()));
					}
				}
			}

		}catch(Exception e ){

		}
		// Inserisco l'elemento nullo in cima
		lst.add(0, item0);

		return lst;
	}

	public void selezionaDipartimento(String codiceDipartimento){

		try{
			//1 Aggiungi alla lista di dx

			log.debug("Selezionato Dipartimento Codice["+codiceDipartimento+"]");
			
			DipartimentoBean findDipartimentoByCodice = this.dipartimentoService.findDipartimentoByCodice(codiceDipartimento);

//			List<Dipartimento> lstDipartimenti =this.getDipartimentiDisponibili(codiceDipartimento, this.form.getDipartimento().getValue());

			//	this.dipartimentoService.getDescrizioneAutoComplete((String)codiceDipartimento);
			Dipartimento d = null;
			if(findDipartimentoByCodice != null){
				d = findDipartimentoByCodice.getDTO(); //lstDipartimenti.get(0);


				String descrizioneConCodice = d.getDescrizione() + " ("+ d.getCodice() + ")";
//				log.debug("Descr ["+descrizioneConCodice+"] L["+descrizioneConCodice.length()+"]");
				org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem newItem = new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						d.getCodice(),
						descrizioneConCodice.trim());

				this.form.getDipartimento().getValue().add(newItem);

			}

			// Aggiorno lista sx
			this.form.getDipartimento().setElencoCustomTypeSelectItems(this.getListaDipartimentiEnte2());
			List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> itemCorrenti = this.form.getDipartimento().getValue();
			List<Integer> listaId = new ArrayList<Integer>();
			for (int i = 0 ; i < this.form.getDipartimento().getElencoCustomTypeSelectItems().size() ; i ++) {
				org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = this.form.getDipartimento().getElencoCustomTypeSelectItems().get(i);
				for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem oldItem : itemCorrenti) {
					if(item.getValue().equals(oldItem.getValue())){
						listaId.add(0,i);
					}
				}
			}

			for (Integer idToRemove : listaId) {
				this.form.getDipartimento().getElencoCustomTypeSelectItems().remove(idToRemove.intValue());
			}

			this.form.getDipartimento().checkDimensions();

		}catch(Exception e){
			log.error("Si e' verificato un errore durante la selezione del dipartimento: "+ e.getMessage(), e);
		}
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.selectedElement = null;
		this.selectedId = null;
		this.showForm = true;
		this.azione = "new";
		this.form.setValues(null);
		this.form.setRendered(this.showForm); 

		this.form.getRuolo().setElencoSelectItems(getListaRuoli());
		this.listaDipartimentiEnte2 = null;
		this.form.getDipartimento().setElencoCustomTypeSelectItems(this.getListaDipartimentiEnte2());


		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> itemCorrenti = this.form.getDipartimento().getDefaultValue();

		List<Integer> listaId = new ArrayList<Integer>();
		for (int i = 0 ; i < this.form.getDipartimento().getElencoCustomTypeSelectItems().size() ; i ++) {
			org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item = this.form.getDipartimento().getElencoCustomTypeSelectItems().get(i);
			for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem oldItem : itemCorrenti) {
				if(item.getValue().equals(oldItem.getValue())){
					listaId.add(0,i);
				}
			}
		}

		for (Integer idToRemove : listaId) {
			this.form.getDipartimento().getElencoCustomTypeSelectItems().remove(idToRemove.intValue());
		}

		this.form.reset();
		this.form.getDipartimento().checkDimensions();
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

	public UtenteForm getForm() {
		return form;
	}

	public void setForm(UtenteForm form) {
		this.form = form;
	}


	public List<SelectItem> getListaRuoli() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO,CostantiForm.NON_SELEZIONATO)));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserRole.ADMIN.getValue(),UserRole.ADMIN.toString())));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserRole.DEPT_ADMIN.getValue(),UserRole.DEPT_ADMIN.toString())));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(UserRole.USER.getValue(),UserRole.USER.toString())));

		return lista;
	}

	private List<Dipartimento> getDipartimentiDisponibili(String val, List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> listaSelezionati){
		List<Dipartimento> lista = new ArrayList<Dipartimento>();

		try{

			log.debug("Ricerca Dipartimenti disponibili. Input Utente["+val+"] in corso..."); 

			List<Dipartimento> lstDipartimenti = this.dipartimentoService.getCodiceDescrizioneAutoComplete(val);

			if(lstDipartimenti != null && lstDipartimenti.size() > 0){
				for (Dipartimento dipartimento : lstDipartimenti) {
					boolean found = false;
					for (org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem item : listaSelezionati) {
						String codiceDipartimento = item.getValue();
						if(codiceDipartimento.equals(dipartimento.getCodice())){
							found = true;
							break;
						}
					}

					if(!found)
						lista.add(dipartimento);
				}
			}
		}catch(Exception e){
			log.error("Si e' verificato un errore durante la selezione del dipartimento: "+ e.getMessage(), e);
		}
		log.debug("Ricerca Dipartimenti disponibili. Input Utente["+val+"] completata.");

		return lista;
	}

 

	@Override
	public String delete() {

		String deleteMsg = super.delete();

		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			log.debug("["+eliminati+"] Utenti eliminati con successo.");
			MessageUtils.addInfoMsg(
					Utils.getInstance().getMessageFromResourceBundle("utente.deleteOk"));
		}else {
			log.error("Si e' verificato un errore durante l'eliminazione degli utenti: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
	}
}
