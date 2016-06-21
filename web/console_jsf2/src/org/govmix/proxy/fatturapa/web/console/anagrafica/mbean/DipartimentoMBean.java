package org.govmix.proxy.fatturapa.web.console.anagrafica.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel.DipartimentoDM;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.DipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.RegistroService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf2.mbean.DataModelListView;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.MessageUtils;
import org.openspcoop2.generic_project.web.mbean.exception.AnnullaException;
import org.openspcoop2.generic_project.web.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.mbean.exception.ModificaException;

@ViewScoped @ManagedBean(name="dipartimentoMBean")
public class DipartimentoMBean extends DataModelListView<Dipartimento,Long,DipartimentoBean,  DipartimentoSearchForm,DipartimentoForm,DipartimentoDM,IDipartimentoService>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showForm = false;

	private String azione = null;

	private IRegistroService registroService = null;

	public DipartimentoMBean() {
		super(LoggerManager.getConsoleLogger());
		this.registroService = new RegistroService();

	}

	@PostConstruct
	private void initManagedProperties(){
		setSearch((DipartimentoSearchForm) Utils.findBean("dipartimentoSearchForm"));
		this.service = (DipartimentoService) Utils.findBean("dipartimentoService");

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
		setSelectedIdParamName("selectedIdDipartimento"); 
	}

	public void init() {
		try{
			this.log.debug("Dipartimento MBean");
			this.form = new DipartimentoForm();
			this.form.setRendered(false);
			this.form.reset();

			this.showForm = false;
			this.azione = null;


			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("dipartimentiListView"); 
			this.table.setEnableDelete(true);
			this.table.setShowAddButton(true);
			this.table.setShowDetailColumn(true);
			this.table.setShowSelectAll(true);
			this.table.setHeaderText("dipartimento.tabellaRisultati.label");
			this.table.setDetailLinkText("dipartimento.dettaglioTitle"); 
			//	this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	@Override
	public void initNavigationManager() throws Exception {
		this.getNavigationManager().setAnnullaOutcome(null); //
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("dipartimento");
		this.getNavigationManager().setFiltraOutcome("listaDipartimenti?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaDipartimenti");
		this.getNavigationManager().setModificaOutcome(null); //"modifica");
		this.getNavigationManager().setNuovoOutcome("dipartimento");
		this.getNavigationManager().setResetOutcome("listaDipartimenti?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaDipartimenti");
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(DipartimentoSearchForm search) {
		this.search = search;
		this.selectedElement = null;
		this.selectedId = null;

		this.search.setmBean(this);
	}

	public void setSelectedIdParam(String selectedIdParValue) {
		this.selectedIdParam = selectedIdParValue;

		this.log.debug("Selected Id ["+selectedIdParValue+"]");

		if(selectedIdParValue!= null){
			this.setSelectedId(new Long(this.selectedIdParam)); 
		}
	}

	@Override
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
		this.selectedElement = null;

		if(this.selectedId != null){

			try {
				//carico dettaglio utente
				DipartimentoBean dettaglioDipartimento = this.service.findById(this.selectedId);
				setSelectedElement(dettaglioDipartimento); 
			} catch (ServiceException e) {
				this.log.error("Si e' verificato un errore durante il caricamento del dettaglio dipartimento: " + e.getMessage(), e);
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
	// Metodo Menu Action

	@Override
	public String azioneMenuAction() throws MenuActionException {
		this.search.setRestoreSearch(true);
		return super.azioneMenuAction();
	}

	@Override
	public String azioneModifica() throws ModificaException {
		try{
			this.showForm = true;
			this.azione = "update";
			this.form.setRendered(this.showForm);
			this.form.setObject(this.selectedElement);
			this.form.setListaNomiProperties(this.getListaDipartimentoProperties());
			((SelectListImpl)this.form.getRegistro()).setElencoSelectItems(this.getElencoRegistri());
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
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione")+": " + msg);
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione")+": " + msg);
			//			return null;
		}

		try{
			long oldId = -1;
			Dipartimento newDip = this.form.getObject();
			newDip.setEnte(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte()); 
			// Add
			if(!this.azione.equals("update")){
				DipartimentoBean bean = new DipartimentoBean();
				bean.setDTO(newDip);
				boolean exists = this.service.exists(bean);
				//				DipartimentoBean oldDip = ((IDipartimentoService) this.service).findDipartimentoByCodice(this.form.getCodice().getValue());

				if(exists){
					throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione") +
							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("dipartimento.form.dipartimentoEsistente",this.form.getCodice().getValue()));
					//					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreValidazione") +
					//							": " +Utils.getInstance().getMessageWithParamsFromResourceBundle("dipartimento.form.dipartimentoEsistente",this.form.getCodice().getValue()));
					//					return null;
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

			return this.getNavigationManager().getInviaOutcome(); //"invia";
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio del dipartimento: " + e.getMessage(), e);
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreGenerico"));
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("dipartimento.form.erroreGenerico"));
			//			return null;
		}
	}


	@Override
	public String azioneAnnulla() throws AnnullaException {
		this.getNavigationManager().setAnnullaOutcome("listaDipartimenti");

		if(this.azione.equals("update")){
			this.showForm = false;
			this.azione = null;
			this.getNavigationManager().setAnnullaOutcome("annulla");
		}
		this.form.setRendered(this.showForm); 
		this.form.reset();

		return super.azioneAnnulla();
	}

	public List<org.openspcoop2.generic_project.web.input.SelectItem> descrizioneAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();

		org.openspcoop2.generic_project.web.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();
			else{
				List<Dipartimento> lstDipartimenti = ((IDipartimentoService)this.service).getDescrizioneAutoComplete((String)val);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.input.SelectItem(
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
	public List<org.openspcoop2.generic_project.web.input.SelectItem> codiceAutoComplete(Object val) {
		List<org.openspcoop2.generic_project.web.input.SelectItem> lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();

		org.openspcoop2.generic_project.web.input.SelectItem item0 = new  org.openspcoop2.generic_project.web.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);

		try{
			if(val==null || StringUtils.isEmpty((String)val) || ((String)val).equals(CostantiForm.NON_SELEZIONATO))
				lst = new ArrayList<org.openspcoop2.generic_project.web.input.SelectItem>();
			else{
				List<Dipartimento> lstDipartimenti = ((IDipartimentoService)this.service).getCodiceAutoComplete((String)val);

				if(lstDipartimenti != null && lstDipartimenti.size() > 0){
					for (Dipartimento dipartimento : lstDipartimenti) {
						lst.add(new  org.openspcoop2.generic_project.web.input.SelectItem(
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
		this.form.setObject(null);
		this.form.setListaNomiProperties(this.getListaDipartimentoProperties());
		((SelectListImpl)this.form.getRegistro()).setElencoSelectItems(this.getElencoRegistri());
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

	//	public DipartimentoForm getForm() {
	//		return form;
	//	}
	//
	//	public void setForm(DipartimentoForm form) {
	//		this.form = form;
	//	}

	public List<DipartimentoProperty> getListaDipartimentoProperties(){
		List<DipartimentoProperty> lista = new ArrayList<DipartimentoProperty>();

		try{
			lista = ((IDipartimentoService)this.service).getListaPropertiesEnte(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte());
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il caricamento delle properties: " + e.getMessage(), e);
		}


		return lista;
	}

	private List<SelectItem> getElencoRegistri() {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		SelectItem elem0 = new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO));


		try {
			RegistroSearchForm registroForm = new RegistroSearchForm();
			registroForm.getEnte().setValue(org.govmix.proxy.fatturapa.web.console.util.Utils.getIdEnte().getNome());
			List<RegistroBean> findAllRegistri = this.registroService.findAll(registroForm);

			if(findAllRegistri != null && findAllRegistri.size() > 0){
				for (RegistroBean bean : findAllRegistri) {
					SelectItem item = new SelectItem(new org.openspcoop2.generic_project.web.input.SelectItem(
							bean.getNome().getValue(),bean.getNome().getValue()));
					lista.add(item);
				}

			}
		} catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei registri: " + e.getMessage(), e);
		}

		lista.add(0, elem0);

		return lista;
	}

	@Override
	public String delete() {

		String deleteMsg = null;

		try{
			deleteMsg = super.azioneDelete();
		}catch(DeleteException e){
			deleteMsg = e.getMessage();
		}

		// se delete msg == null allora e' andato tutto ok
		if(deleteMsg == null){
			int eliminati = this.toRemove != null ? this.toRemove.size() : 0;
			this.log.debug("["+eliminati+"] Dipartimenti eliminati con successo.");
			String deletemsgok = Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteOk");
			MessageUtils.addInfoMsg(deletemsgok);
		}else {
			this.log.error("Si e' verificato un errore durante l'eliminazione dei dipartimenti: " + deleteMsg);
			MessageUtils.addErrorMsg(deleteMsg);
		}

		return deleteMsg;
	}
}
