package org.govmix.proxy.fatturapa.web.console.mbean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.ConservazioneBean;
import org.govmix.proxy.fatturapa.web.console.costanti.Costanti;
import org.govmix.proxy.fatturapa.web.console.datamodel.ConservazioneDM;
import org.govmix.proxy.fatturapa.web.console.form.FatturaForm;
import org.govmix.proxy.fatturapa.web.console.iservice.IConservazioneService;
import org.govmix.proxy.fatturapa.web.console.search.ConservazioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.ConservazioneService;
import org.govmix.proxy.fatturapa.web.console.util.ConservazioneThread;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.DataModelListView;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.FiltraException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.MenuActionException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;

public class ConservazioneMBean extends DataModelListView<ConservazioneBean, Long,
ConservazioneSearchForm, FatturaForm, ConservazioneDM, FatturaElettronica, 
IConservazioneService>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Anno
	private List<SelectItem> listaAnni = null;	
	
	// Tipo Fattura
	private List<SelectItem> listaTipiFattura = null;
	
	// Ente
	private List<SelectItem> listaEnti = null;
	
	// Stato Invio
	private List<SelectItem> listaStatiInvio = null;
	
	public ConservazioneMBean(){
		super(LoggerManager.getConsoleLogger());
		this.initTables();
		this.setOutcomes();

		this.log.debug("ConservazioneMBean");

	}
	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.table.setId("conservazioneTable"); 
			this.table.setEnableDelete(false);
			this.table.setShowAddButton(false);
			this.table.setShowDetailColumn(false);
			this.table.setShowSelectAll(true);
			this.table.setHeaderText("conservazione.label.ricercaConservazione.tabellaRisultati");
			this.table.setMBean(this);
			this.table.setMetadata(this.getMetadata()); 

		}catch (Exception e) {
			log.error("Errore durante la init ConservazioneMBean:" + e.getMessage(),e);  
		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome("listaConservazione?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome("listaConservazione");
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome("listaConservazione?faces-redirect=true");
		this.getNavigationManager().setRestoreSearchOutcome("listaConservazione");
	}


	@Override
	protected String _filtra() throws FiltraException {
		return super._filtra();
	}

	// Override del set della ricerca, popolo i field di tipo selectList.
	@Override
	public void setSearch(ConservazioneSearchForm search) {
		this.search = search;
		this.selectedElement = null;

		// Popolo le selectList Statiche
		List<SelectItem> listaAnni = this.getListaAnni(); 
		((SelectListImpl)this.search.getAnno()).setElencoSelectItems(listaAnni);
		((SelectListImpl)this.search.getTipoFattura()).setElencoSelectItems(this.getListaTipiFattura());
		
		List<SelectItem> listaEnti = this.getListaEnti();
		((SelectListImpl)this.search.getEnte()).setElencoSelectItems(listaEnti);
		if(!listaEnti.isEmpty() && this.search.getEnte().getValue() == null) {
			this.search.setDefaultEnte((org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem) listaEnti.get(0).getValue());
		}
		
		((SelectListImpl)this.search.getStatoInvio()).setElencoSelectItems(this.getListaStatiInvio());
		this.search.setmBean(this);
	}
	
	
	public List<SelectItem> getListaAnni() {
		if (this.listaAnni == null) {
			this.listaAnni = new ArrayList<SelectItem>();
			
			Calendar instance = Calendar.getInstance();
			instance.setTime(new Date());
			int year = instance.get(Calendar.YEAR);
			
			for (int i = year; i >= 2015; i--) {
				String valAnno = "" + i;
				this.listaAnni.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(valAnno, valAnno)));
				
			}
		}
		
		return listaAnni;
	}
	public void setListaAnni(List<SelectItem> listaAnni) {
		this.listaAnni = listaAnni;
	}
	public List<SelectItem> getListaTipiFattura() {
		if (this.listaTipiFattura == null) {
			this.listaTipiFattura = new ArrayList<SelectItem>();
			
			this.listaTipiFattura.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(Costanti.TIPO_FATTURA_ATTIVA_VALUE, ("conservazione.search.tipoFattura.attiva"))));
			this.listaTipiFattura.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(Costanti.TIPO_FATTURA_PASSIVA_VALUE, ("conservazione.search.tipoFattura.passiva"))));
		
		}
		return listaTipiFattura;
	}
	public void setListaTipiFattura(List<SelectItem> listaTipiFattura) {
		this.listaTipiFattura = listaTipiFattura;
	}
	public List<SelectItem> getListaEnti() {
		this.listaEnti = new ArrayList<SelectItem>();
		
		boolean fatturazioneAttiva = false;
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem tipoFatturaSelezionata = ((SelectListImpl)this.search.getTipoFattura()).getValue();
		if(tipoFatturaSelezionata != null) {
			fatturazioneAttiva = Costanti.TIPO_FATTURA_ATTIVA_VALUE.equals(tipoFatturaSelezionata.getValue());
		}
		
		this.listaEnti = _getEnti(false, fatturazioneAttiva);
		
		return listaEnti;
	}
	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}
	
	public List<SelectItem> _getEnti(boolean addQualsiasi, boolean fatturazioneAttiva) {
		List<SelectItem> listaDipartimenti = new ArrayList<SelectItem>();

		if(addQualsiasi)
			listaDipartimenti.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*",  ("commons.label.qualsiasi"))));

		List<Dipartimento> listaDipartimentiLoggedUtente = org.govmix.proxy.fatturapa.web.console.util.Utils.getListaDipartimentiLoggedUtente();
		List<String> listaAggiunti = new ArrayList<String>();
		if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0)
			for (Dipartimento dipartimento : listaDipartimentiLoggedUtente) {
				boolean add = true;

				if(fatturazioneAttiva) {
					add = dipartimento.isFatturazioneAttiva();
				}

				if(add) {
					IdEnte ente = dipartimento.getEnte();
					if(!listaAggiunti.contains(ente.getNome())){
						listaAggiunti.add(ente.getNome());
					}
				}
			}
		
		List<String> entiSelezionati = new ArrayList<String>();
		Map<String, Ente> mapEntiLoggedUtente = org.govmix.proxy.fatturapa.web.console.util.Utils.getMapEntiLoggedUtente();
		for (String nomeEnte : listaAggiunti) {
			if(mapEntiLoggedUtente.containsKey(nomeEnte)) {
				Ente ente = mapEntiLoggedUtente.get(nomeEnte);
			
				if(ente.getEnteVersatore() != null && ente.getStrutturaVersatore() != null) {
					entiSelezionati.add(nomeEnte);
				}
			}
		}
		
		// ordino i risultati in ordine alfabetico
		Collections.sort(entiSelezionati);
		
		for (String nomeEnte : entiSelezionati) {
			if(mapEntiLoggedUtente.containsKey(nomeEnte)) {
				Ente ente = mapEntiLoggedUtente.get(nomeEnte);
			
				if(ente.getEnteVersatore() != null && ente.getStrutturaVersatore() != null) {
					listaDipartimenti.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(ente.getNome(),ente.getNome())));
				}
			}
		}


		return listaDipartimenti;
	}
	
	/*
	 * Non inviata in conservazione
	   Presa in carico
       In riconsegna
	   Errore consegna
	   Conservazione competata
	   Conservazione fallita 
	 */
	public List<SelectItem> getListaStatiInvio() {
		if (this.listaStatiInvio == null) {
			this.listaStatiInvio = new ArrayList<SelectItem>();

			this.listaStatiInvio.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi"))));
			this.listaStatiInvio.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoConservazioneType.NON_INVIATA.getValue(), ("conservazione.search.statoInvio." + StatoConservazioneType.NON_INVIATA.getValue()))));
			this.listaStatiInvio.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoConservazioneType.PRESA_IN_CARICO.getValue(),  ("conservazione.search.statoInvio." + StatoConservazioneType.PRESA_IN_CARICO.getValue()))));
			this.listaStatiInvio.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoConservazioneType.IN_RICONSEGNA.getValue(),  ("conservazione.search.statoInvio." + StatoConservazioneType.IN_RICONSEGNA.getValue()))));
			this.listaStatiInvio.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoConservazioneType.ERRORE_CONSEGNA.getValue(),  ("conservazione.search.statoInvio." + StatoConservazioneType.ERRORE_CONSEGNA.getValue()))));
			this.listaStatiInvio.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoConservazioneType.CONSERVAZIONE_COMPLETATA.getValue(), ("conservazione.search.statoInvio." + StatoConservazioneType.CONSERVAZIONE_COMPLETATA.getValue()))));
			this.listaStatiInvio.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoConservazioneType.CONSERVAZIONE_FALLITA.getValue(),  ("conservazione.search.statoInvio." + StatoConservazioneType.CONSERVAZIONE_FALLITA.getValue()))));
		}
		
		return listaStatiInvio;
	}
	public void setListaStatiInvio(List<SelectItem> listaStatiInvio) {
		this.listaStatiInvio = listaStatiInvio;
	}
	
	@Override
	protected String _menuAction() throws MenuActionException {
		this.search.setRestoreSearch(true);
		return super._menuAction();
	}
	
	public String inviaFattureInConservazione() {
		try {

			// recupero lista diagnostici
			List<Long> idFatture = new ArrayList<Long>();

			Iterator<ConservazioneBean> it = this.selectedIds.keySet().iterator();
			while (it.hasNext()) {
				ConservazioneBean fatturaBean = it.next();
				if (this.selectedIds.get(fatturaBean).booleanValue()) {
					idFatture.add(fatturaBean.getDTO().getId());
					it.remove();
				}
			}
			
			if(idFatture.isEmpty()) {
				MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("conservazione.invio.nessunaFatturaSelezionataError"));
				return null;
			}
			
			FatturaBD fatturaBD = new FatturaBD(getLog());
			FatturaFilter fatturaFilter = ((ConservazioneService)this.service).getFilterFromSearch(fatturaBD , this.search);
			ConservazioneThread conservazioneThread = new ConservazioneThread();
			conservazioneThread.setAll(false);
			conservazioneThread.setIds(idFatture);
			conservazioneThread.setFatturaBD(fatturaBD);
			conservazioneThread.setFatturaFilter(fatturaFilter);
			
			// stato conservazione
			if(search.getStatoInvio().getValue() != null &&
					!StringUtils.isEmpty(search.getStatoInvio().getValue().getValue()) && !search.getStatoInvio().getValue().getValue().equals("*")){
				StatoConservazioneType statoConservazioneType = StatoConservazioneType.toEnumConstant(search.getStatoInvio().getValue().getValue());
				conservazioneThread.setStatoConservazione(statoConservazioneType);
			}
					
			Thread t = new Thread(conservazioneThread);
			t.start();
			
			if(idFatture.size() > 1)
				MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("conservazione.invioFatture.ok"));
			else 
				MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("conservazione.invioFattura.ok"));

			// End of the method
		} catch (Exception e) {
			FacesContext.getCurrentInstance().responseComplete();
			this.log.error(e, e);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("conservazione.invio.genericError"),null));
		}

		return null;
	}
	
	public String inviaTutteFattureInConservazione() {
		try {

			List<Long> idFatture = new ArrayList<Long>();

			FatturaBD fatturaBD = new FatturaBD(getLog());
			FatturaFilter fatturaFilter = ((ConservazioneService)this.service).getFilterFromSearch(fatturaBD , this.search);
			ConservazioneThread conservazioneThread = new ConservazioneThread();
			conservazioneThread.setAll(true);
			conservazioneThread.setIds(idFatture);
			conservazioneThread.setFatturaBD(fatturaBD);
			conservazioneThread.setFatturaFilter(fatturaFilter);
			
			// stato conservazione
			if(search.getStatoInvio().getValue() != null &&
					!StringUtils.isEmpty(search.getStatoInvio().getValue().getValue()) && !search.getStatoInvio().getValue().getValue().equals("*")){
				StatoConservazioneType statoConservazioneType = StatoConservazioneType.toEnumConstant(search.getStatoInvio().getValue().getValue());
				conservazioneThread.setStatoConservazione(statoConservazioneType);
			}
					
			Thread t = new Thread(conservazioneThread);
			t.start();
			
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("conservazione.invioFatture.ok"));

			// End of the method
		} catch (Exception e) {
			FacesContext.getCurrentInstance().responseComplete();
			this.log.error(e, e);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("conservazione.invio.genericError"),null));
		}

		return null;
	}
	
	
	public String getMessaggioSelezioneTutteFatture() {
		int sizeRicerca = this.search.getTotalCount() ; 
		String key = sizeRicerca > 1 ? "conservazione.confermaInvioTutteFatture.multipla.label": "conservazione.confermaInvioTutteFatture.singola.label";
		List<Object> params = new ArrayList<Object>();
		
		if(this.search.getAnno().getValue() != null)
			params.add(this.search.getAnno().getValue().getLabel());
		if(this.search.getEnte().getValue() != null)
			params.add(this.search.getEnte().getValue().getLabel());
		
		if(sizeRicerca > 1)
			params.add(0,sizeRicerca);
		
		return Utils.getInstance().getMessageWithParamsFromResourceBundle(key, params.toArray(new Object[params.size()])); 
	}

	public String getMessaggioSelezioneFattureMultipla() {
		String key = "conservazione.confermaInvioTutteFatture.multipla.label" ; //: "conservazione.confermaInvioTutteFatture.singola.label";
		return _getMessaggioSelezioneFatture(key,"@@numero@@"); 
	}
	public String getMessaggioSelezioneFattureSingola() {
		String key = "conservazione.confermaInvioTutteFatture.singola.label";
		return _getMessaggioSelezioneFatture(key,null); 
	}
	
	private String _getMessaggioSelezioneFatture(String key,String numero) {
		List<Object> params = new ArrayList<Object>();
		
		if(numero!=null)
			params.add(numero);
		
		if(this.search.getAnno().getValue() != null)
			params.add(this.search.getAnno().getValue().getLabel());
		if(this.search.getEnte().getValue() != null)
			params.add(this.search.getEnte().getValue().getLabel());
		
		return Utils.getInstance().getMessageWithParamsFromResourceBundle(key, params.toArray(new Object[params.size()]));
	}
}
