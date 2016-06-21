/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.pcc.mbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.govmix.pcc.fatture.ContabilizzazioneTipo;
import org.govmix.pcc.fatture.DatiRichiestaDatiFatturaProxyTipo;
import org.govmix.pcc.fatture.DatiRichiestaOperazioneContabileProxyTipo;
import org.govmix.pcc.fatture.DatiRispostaProxyTipo;
import org.govmix.pcc.fatture.DatiRispostaStatoFatturaReadTipo;
import org.govmix.pcc.fatture.EsitoOkKoTipo;
import org.govmix.pcc.fatture.FattureWS;
import org.govmix.pcc.fatture.IdentificazioneSDITipo;
import org.govmix.pcc.fatture.ListaOperazioneTipo;
import org.govmix.pcc.fatture.NaturaSpesaContabiliTipo;
import org.govmix.pcc.fatture.OperazioneContabilizzazioneTipo;
import org.govmix.pcc.fatture.OperazioneTipo;
import org.govmix.pcc.fatture.OrigineTipo;
import org.govmix.pcc.fatture.ProxyDatiFatturaRichiestaTipo;
import org.govmix.pcc.fatture.ProxyOperazioneContabileRichiestaTipo;
import org.govmix.pcc.fatture.ProxyRispostaTipo;
import org.govmix.pcc.fatture.ReadStatoFatturaRichiestaTipo;
import org.govmix.pcc.fatture.ReadStatoFatturaRispostaTipo;
import org.govmix.pcc.fatture.StatoDebitoTipo;
import org.govmix.pcc.fatture.StrutturaDatiOperazioneTipo;
import org.govmix.pcc.fatture.TestataAsyncRichiestaTipo;
import org.govmix.pcc.fatture.TestataBaseRichiestaTipo;
import org.govmix.pcc.fatture.TestataRispTipo;
import org.govmix.pcc.fatture.TipoOperazioneTipo;
import org.govmix.pcc.fatture.WSAuthorizationFault;
import org.govmix.pcc.fatture.WSGenericFault;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.constants.CausaleType;
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoDebitoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.commons.utils.TransformUtils;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ContabilizzazionePccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.EsitoPccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.form.ContabilizzazioneForm;
import org.govmix.proxy.fatturapa.web.console.pcc.form.OperazioneForm;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IEsitoService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.ContabilizzazioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.pcc.service.ContabilizzazioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.service.EsitoService;
import org.govmix.proxy.fatturapa.web.console.pcc.service.OperazioneService;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.table.PagedDataTable;

public class ContabilizzazioneMBean  extends BaseMBean<ContabilizzazionePccBean, Long, ContabilizzazioneSearchForm> {

	private ContabilizzazioneForm form;
	private PagedDataTable<List<ContabilizzazionePccBean>, OperazioneForm, ContabilizzazioneSearchForm> table;
	private String contabilizzazioneAction = "add";
	private boolean checkContabilizzazione = false;

	private IdFattura idFattura;
	private List<ContabilizzazionePccBean> statoOriginale = null; 
	private List<ContabilizzazionePccBean> listaContabilizzazioni = null;

	private String sistemaRichiedente = null;
	private boolean abilitaOperazioni = true;

	private FatturaElettronicaBean fattura= null;

	private IEsitoService esitoService = null;
	private OperazioneService operazioneService = null;

	private EsitoPccBean esitoContabilizzazione = null;
	private Text esitoContabilizzazioni = null;
	private Button linkErroreContabilizzazioni = null;
	private OutputGroup fieldsContabilizzazioni= null;	
	private ConsoleProperties properties = null;
	private IFatturaElettronicaService fatturaService = null;

	private ScadenzaMBean scadenzaMBean = null;

	private boolean editMode = false;
	private boolean operazioneAsincrona = false;
	private Date dataUltimaOperazione;
	private boolean visualizzaRiallineamentoSincrono = false;

	public ContabilizzazioneMBean(){
		super(LoggerManager.getConsoleLogger());
		this.log.debug("ContabilizzazioneMBean");

		this.setOutcomes();
		this.init();

		this.esitoService = new EsitoService();
		this.fatturaService = new FatturaElettronicaService();
		this.operazioneService = new OperazioneService();
	}

	@Override
	public void setSearch(ContabilizzazioneSearchForm search) {
		super.setSearch(search);
	}

	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.getTable().setId("panelContabilizzazione"); 
			this.getTable().setEnableDelete(false);
			this.getTable().setShowAddButton(false);
			this.getTable().setShowDetailColumn(false);
			this.getTable().setShowSelectAll(true);
			this.getTable().setHeaderText("contabilizzazione.label.ricercaContabilizzazioni.tabellaRisultati");
			//	this.table.setMBean(this);
			this.getTable().setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		//this.getNavigationManager().setDettaglioOutcome("utente");
		//this.getNavigationManager().setFiltraOutcome("listaUtenti?faces-redirect=true");
		this.getNavigationManager().setInviaOutcome(null);
		//		this.getNavigationManager().setMenuActionOutcome("listaUtenti");
		//		this.getNavigationManager().setModificaOutcome("modifica");
		//		this.getNavigationManager().setNuovoOutcome("utente");
		//		this.getNavigationManager().setResetOutcome("listaUtenti?faces-redirect=true");
		//		this.getNavigationManager().setRestoreSearchOutcome("listaUtenti");
	}

	private void init (){
		try{
			this.properties = ConsoleProperties.getInstance(log);
			this.form = new ContabilizzazioneForm();
			this.form.setmBean(this); 
			((SelectListImpl)this.form.getStato()).setElencoSelectItems(this.getListaStati());
			org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem statoSI = this.form.getStato().getValue();

			String valueStato = null; 

			if(statoSI != null)
				valueStato = statoSI.getValue();
			((SelectListImpl)this.form.getCausale()).setElencoSelectItems(this.getListaCausali(valueStato));

			this.sistemaRichiedente = ConsoleProperties.getInstance(log).getSistemaRichiedente();

			this.esitoContabilizzazioni = this.getFactory().getOutputFieldFactory().createText("cont_esito","contabilizzazione.stato");
			this.linkErroreContabilizzazioni = this.getFactory().getOutputFieldFactory().createButton("cont_link_esito","contabilizzazione.stato.link.iconTitle",null,null,
					"contabilizzazione.stato.link.iconTitle","contabilizzazione.stato.link.iconTitle");

			this.fieldsContabilizzazioni = this.getFactory().getOutputFieldFactory().createOutputGroup("infoContabilizzazione",3);
			this.fieldsContabilizzazioni.setStyleClass("datiTrasmissioneTable"); 
			this.fieldsContabilizzazioni.setColumnClasses("labelAllineataDx,valueAllineataSx,valueAllineataSx");

			this.fieldsContabilizzazioni.addField(this.esitoContabilizzazioni);
			this.fieldsContabilizzazioni.addField(this.linkErroreContabilizzazioni);
		}catch (Exception e){
			this.log.error("Errore durante la init delle contabilizzazioneMBean: " + e.getMessage(),e);
		}
	}

	@Override
	public void setService(IBaseService<ContabilizzazionePccBean, Long, ContabilizzazioneSearchForm> service) {
		super.setService(service);
	}

	public IBaseService<ContabilizzazionePccBean, Long, ContabilizzazioneSearchForm> getService(){
		return this.service;
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.editMode = true;
		this.selectedElement = null;
		this.selectedId = null;
		this.checkContabilizzazione = false;
		this.contabilizzazioneAction = "add";
		this.form.setValues(this.selectedElement);

		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem statoSI = this.form.getStato().getValue();

		String valueStato = null; 

		if(statoSI != null)
			valueStato = statoSI.getValue();

		((SelectListImpl)this.form.getCausale()).setElencoSelectItems(this.getListaCausali(valueStato));
	}


	@Override
	public void setSelectedElement(ContabilizzazionePccBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.editMode = true;
		this.checkContabilizzazione = false;
		this.contabilizzazioneAction = "edit";
		this.form.setValues(this.selectedElement);
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem statoSI = this.form.getStato().getValue();

		String valueStato = null; 

		if(statoSI != null)
			valueStato = statoSI.getValue();

		((SelectListImpl)this.form.getCausale()).setElencoSelectItems(this.getListaCausali(valueStato));
	}

	@Override
	protected String _invia() throws InviaException {
		this.editMode = true;
		String msg = this.form.valida();

		if(msg!= null){
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.erroreValidazione")+": " + msg);
		}

		try{
			long oldId = -1;
			String idImporto = null;
			PccContabilizzazione nuovaContabilizzazione = this.form.getContabilizzazione();
			// Add
			if(!this.contabilizzazioneAction.equals("edit")){
				//generaId importo
				idImporto = getNextIdImporto();

				nuovaContabilizzazione.setIdImporto(idImporto); 
			} else {
				oldId = this.selectedElement.getDTO().getId();
				nuovaContabilizzazione.setId(oldId); 
			}

			ContabilizzazionePccBean bean = new ContabilizzazionePccBean();
			bean.setDTO(nuovaContabilizzazione);

			bean.setNonInviata(true);

			this.service.store(bean);
			this.checkContabilizzazione = true;

			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.salvataggioOk"));

			return this.getNavigationManager().getInviaOutcome();//"invia";
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.erroreGenerico"));
		}finally {
			this.abilitaOperazioni = this.listaContabilizzazioni != null ? this.listaContabilizzazioni.size() > 0 : false;
		}
	}


	@Override
	protected String _delete() throws DeleteException {
		try{
			this.editMode = true;
			if(this.contabilizzazioneAction.equals("delete")){
				this.service.delete(this.selectedElement);
			} 
			return this.getNavigationManager().getDeleteOutcome();
		}catch (Exception e) {
			throw new DeleteException(e.getMessage());
		}
	}

	public String inviaOperazioneContabile(){
		this.operazioneAsincrona = false;
		this.scadenzaMBean.setOperazioneAsincrona(this.operazioneAsincrona);
		
		ProxyOperazioneContabileRichiestaTipo richiesta = new ProxyOperazioneContabileRichiestaTipo();
		try {
			Date dataUltimaOperazioneAttuale = this.calcolaDataUltimaOperazione();
			log.debug("Data Ultima Operazione Attuale: " + dataUltimaOperazioneAttuale);
			log.debug("Data Ultima Operazione Precedente: " + this.dataUltimaOperazione);
			if(dataUltimaOperazioneAttuale != null && this.dataUltimaOperazione != null){
				if(dataUltimaOperazioneAttuale.getTime() > this.dataUltimaOperazione.getTime()){
					// c'e' stata una modifica segnalo all'utente.
					MessageUtils.addWarnMsg(Utils.getInstance().getMessageWithParamsFromResourceBundle("contabilizzazione.form.operazioneContabile.fatturaAggiornata"));
					return null;
				}
			}

			FattureWS clientProxy = Utils.creaClientProxyPcc(this.properties,this.log);

			TestataAsyncRichiestaTipo value = new TestataAsyncRichiestaTipo();
			value.setIdentificativoTransazionePA(Utils.generaIdentificativoTransazionePA());
			value.setUtenteRichiedente(Utils.getLoggedUtente().getUsername()); 
			richiesta.setTestataRichiesta(value);
			DatiRichiestaOperazioneContabileProxyTipo datiRichiesta = new DatiRichiestaOperazioneContabileProxyTipo();
			IdentificazioneSDITipo idSDI = new IdentificazioneSDITipo();

			idSDI.setLottoSDI(new BigDecimal(this.fattura.getIdentificativoSdi().getValue()));
			idSDI.setNumeroFattura(this.fattura.getNumero().getValue());
			datiRichiesta.setIdentificazioneSDI(idSDI);

			ListaOperazioneTipo listaOperazione = creaBodyRichiestaContabilizzazione();

			// non si puo' invocare il servizio se la lista contabilizzazioni efatt e' vuota
			//			if(listaOperazione.getOperazione().getStrutturaDatiOperazione().getListaContabilizzazione().size() == 0){
			//				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneContabile.listaVuota"));
			//				return null;
			//			}

			datiRichiesta.setListaOperazione(listaOperazione );
			richiesta.setDatiRichiesta(datiRichiesta); 

			ProxyRispostaTipo wSProxyOperazioneContabile = clientProxy.wSProxyOperazioneContabile(richiesta);

			TestataRispTipo testataRisposta = wSProxyOperazioneContabile.getTestataRisposta();
			EsitoOkKoTipo esito = testataRisposta.getEsito();
			String detail = testataRisposta.getDetail();
			OrigineTipo origine = testataRisposta.getOrigine();

			if(esito.equals(EsitoOkKoTipo.OK)){
				MessageUtils.addInfoMsg(Utils.getInstance().getMessageWithParamsFromResourceBundle("contabilizzazione.form.operazioneContabilePresaInCaricoOK.parametri",origine.toString()));

				// Ricarico lo stato solo nel caso OK
				((ContabilizzazioneService) this.service).setIdFattura(this.idFattura); 
				try {
					this.listaContabilizzazioni = this.service.findAll();
					this.statoOriginale = this.service.findAll();
					this.dataUltimaOperazione = this.calcolaDataUltimaOperazione();
				} catch (ServiceException e) {
					log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
				}
				this.editMode = false;	
				this.scadenzaMBean.setDataUltimaOperazione(this.dataUltimaOperazione); 
				
			}else {
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageWithParamsFromResourceBundle("contabilizzazione.form.operazioneContabilePresaInCaricoKO.parametri",origine.toString()));
			}
		} catch (WSGenericFault e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneContabileKO"));
		} catch (WSAuthorizationFault e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			String authDetail = e.getFaultInfo().getDetail();
			
			// Utente non autorizzato all'operazione
			if(authDetail.startsWith("Utente")) 
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneContabileKO.utenteNonAutorizzato"));
			else{ // Dipartimento non autorizzata 
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneContabileKO.dipartimentoNonAutorizzato"));
			}
			
		}
		catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			
			if(e instanceof javax.xml.ws.soap.SOAPFaultException){
				if(e.getMessage() != null && e.getMessage().contains("Could not send Message")){
					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneContabileErroreConnessione"));
					return null;
				}
				
				MessageUtils.addErrorMsg(e.getMessage());	
				return null;
			}
			
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneContabileErroreGenerico"));
		}finally {
			if(!this.editMode)
				this.abilitaOperazioni();
		}

		return null;
	}


	private ListaOperazioneTipo creaBodyRichiestaContabilizzazione(){
		ListaOperazioneTipo listaOperazione = new ListaOperazioneTipo();

		OperazioneTipo operazione = new OperazioneTipo();
		operazione.setProgressivoOperazione(1);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();

		strutturaDatiOperazione.getListaContabilizzazione().addAll(getListaContabilizzazioniDaSpedire());

		operazione.setStrutturaDatiOperazione(strutturaDatiOperazione );
		operazione.setTipoOperazione(TipoOperazioneTipo.CO);

		listaOperazione.setOperazione(operazione);

		return listaOperazione;
	}



	private List<ContabilizzazioneTipo> getListaContabilizzazioniDaSpedire(){
		List<ContabilizzazioneTipo> lst = new ArrayList<ContabilizzazioneTipo>();

		//	int i = 0;
		for (ContabilizzazionePccBean bean : this.listaContabilizzazioni) {
			if(bean.getDTO().getSistemaRichiedente().equals(this.sistemaRichiedente)){
				ContabilizzazioneTipo tipo = new ContabilizzazioneTipo();


				String descr = bean.getDescrizione().getValue();

				descr =	TransformUtils.toStringDescrizioneImportoContabilizzazione(descr);

				tipo.setDescrizione(descr);
				tipo.setImportoMovimento(new BigDecimal(bean.getDTO().getImportoMovimento()));
				OperazioneContabilizzazioneTipo operazione = new OperazioneContabilizzazioneTipo();

				CausaleType causale = bean.getDTO().getCausale();
				if(causale != null)
					operazione.setCausale(causale.toString());

				operazione.setStatoDebito(StatoDebitoTipo.fromValue(bean.getDTO().getStatoDebito().toString()));
				tipo.setOperazione(operazione);
				tipo.setIdentificativoMovimento(bean.getDTO().getIdImporto());

				// N/A come da specifica
				tipo.setNaturaSpesa(NaturaSpesaContabiliTipo.NA);
				tipo.setCodiceCIG("NA");
				tipo.setCodiceCUP("NA");


				lst.add(tipo);
			}
		}

		return lst;
	}

	public String annullaModifiche (){
		this.editMode = true;
		try {
			((ContabilizzazioneService) this.service).setIdFattura(this.idFattura); 
			this.listaContabilizzazioni = this.service.findAll();
			this.statoOriginale = this.service.findAll();
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.annullaModificheOk"));
		} catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.erroreGenerico"));
		}finally {
			this.abilitaOperazioni();
		}


		return null;
	}

	public String inviaRichiestaRiallineamento (){
		this.operazioneAsincrona = false;
		this.scadenzaMBean.setOperazioneAsincrona(this.operazioneAsincrona);
		ReadStatoFatturaRichiestaTipo richiesta = new ReadStatoFatturaRichiestaTipo();

		try {
			Date dataUltimaOperazioneAttuale = this.calcolaDataUltimaOperazione();
			log.debug("Data Ultima Operazione Attuale: " + dataUltimaOperazioneAttuale);
			log.debug("Data Ultima Operazione Precedente: " + this.dataUltimaOperazione);
			if(dataUltimaOperazioneAttuale != null && this.dataUltimaOperazione != null){
				if(dataUltimaOperazioneAttuale.getTime() > this.dataUltimaOperazione.getTime()){
					// c'e' stata una modifica segnalo all'utente.
					MessageUtils.addWarnMsg(Utils.getInstance().getMessageWithParamsFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamento.fatturaAggiornata"));
					return null;
				}
			}

			FattureWS clientProxy = Utils.creaClientProxyPcc(this.properties,this.log);

			TestataBaseRichiestaTipo value = new TestataBaseRichiestaTipo();
			//value.setIdentificativoTransazionePA(Utils.generaIdentificativoTransazionePA());
			value.setUtenteRichiedente(Utils.getLoggedUtente().getUsername()); 
			richiesta.setTestataRichiesta(value);
			DatiRichiestaDatiFatturaProxyTipo datiRichiesta = new DatiRichiestaDatiFatturaProxyTipo();
			IdentificazioneSDITipo idSDI = new IdentificazioneSDITipo();

			idSDI.setLottoSDI(new BigDecimal(this.fattura.getIdentificativoSdi().getValue()));
			idSDI.setNumeroFattura(this.fattura.getNumero().getValue());
			datiRichiesta.setIdentificazioneSDI(idSDI);
			richiesta.setDatiRichiesta(datiRichiesta); 

			ReadStatoFatturaRispostaTipo wSProxyOperazioneContabile = clientProxy.wSReadStatoFattura(richiesta);

			DatiRispostaStatoFatturaReadTipo datiRisposta = wSProxyOperazioneContabile.getDatiRisposta();

			EsitoOkKoTipo esito = datiRisposta.getEsitoTrasmissione(); 

			if(esito.equals(EsitoOkKoTipo.OK)){
				((ContabilizzazioneService) this.service).setIdFattura(this.idFattura); 
				try {
					this.listaContabilizzazioni = this.service.findAll();
					this.statoOriginale = this.service.findAll();
					this.dataUltimaOperazione = this.calcolaDataUltimaOperazione();
				} catch (ServiceException e) {
					log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
				}
				this.editMode = false;
				this.scadenzaMBean.setDataUltimaOperazione(this.dataUltimaOperazione); 

				MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoPresaInCaricoOK"));
			}else {
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoPresaInCaricoKO.noRispedizione"));
			}
		} catch (WSGenericFault e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoKO"));
		} catch (WSAuthorizationFault e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			String authDetail = e.getFaultInfo().getDetail();
			
			// Utente non autorizzato all'operazione
			if(authDetail.startsWith("Utente")) 
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoKO.utenteNonAutorizzato"));
			else{ // Dipartimento non autorizzata 
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoKO.dipartimentoNonAutorizzato"));
			}
		} 
		catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			
			if(e instanceof javax.xml.ws.soap.SOAPFaultException){
				if(e.getMessage() != null && e.getMessage().contains("Could not send Message")){
					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoErroreConnessione"));
					return null;
				}
				
				MessageUtils.addErrorMsg(e.getMessage());	
				return null;
			}
			
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoErroreGenerico"));
		}finally {
			if(!this.editMode)
				this.abilitaOperazioni();
		}

		return null;
	}


	public List<SelectItem> getListaStati() {
		List<SelectItem> lista = new ArrayList<SelectItem>(); 

		//lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO)));
		//lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoDebitoType.LIQ.getValue(), "pccStatoDebito."+StatoDebitoType.LIQ.getValue())));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoDebitoType.NOLIQ.getValue(), "pccStatoDebito."+StatoDebitoType.NOLIQ.getValue())));
		lista.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(StatoDebitoType.SOSP.getValue(), "pccStatoDebito."+StatoDebitoType.SOSP.getValue())));


		return lista;
	}

	public List<SelectItem> getListaCausali(String valueStato) {
		List<SelectItem> lista = new ArrayList<SelectItem>();

		StatoDebitoType statoDebitoType = valueStato != null ?  StatoDebitoType.toEnumConstant(valueStato) : StatoDebitoType.NOLIQ;
				
		TipoDocumentoType tipoDocumento = this.fattura != null ? this.fattura.getDTO().getTipoDocumento() : TipoDocumentoType.TD01;
		
		TipoDocumentoType notadicredito = TipoDocumentoType.TD04; // nota di credito 

		switch (statoDebitoType) {
		case NOLIQ:
			lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO)));
			lista.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.CONT.getValue(), "pccCausale."+CausaleType.CONT.getValue())));
			if(!notadicredito.equals(tipoDocumento))
				lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.ATTNC.getValue(), "pccCausale."+CausaleType.ATTNC.getValue())));
			if(notadicredito.equals(tipoDocumento))
				lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.NCRED.getValue(), "pccCausale."+CausaleType.NCRED.getValue())));
			
			lista.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.PAGTERZI.getValue(), "pccCausale."+CausaleType.PAGTERZI.getValue())));
			lista.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.IVARC.getValue(), "pccCausale."+CausaleType.IVARC.getValue())));
			break;
		case SOSP: 
			lista.add(new SelectItem(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO)));
			lista.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.ATTLIQ.getValue(), "pccCausale."+CausaleType.ATTLIQ.getValue())));
			lista.add(new SelectItem(
					new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(CausaleType.CONT.getValue(), "pccCausale."+CausaleType.CONT.getValue())));
			break;
		case LIQ:
		default:
			// non possibile
			break;
		}

		return lista;
	}

	public ContabilizzazioneForm getForm() {
		return this.form;
	}


	public void setForm(ContabilizzazioneForm form) {
		this.form = form;
	}

	public PagedDataTable<List<ContabilizzazionePccBean>, OperazioneForm, ContabilizzazioneSearchForm> getTable() {
		return this.table;
	}

	public void setTable(PagedDataTable<List<ContabilizzazionePccBean>, OperazioneForm, ContabilizzazioneSearchForm> table) {
		this.table = table;
	}


	public String getContabilizzazioneAction() {
		return contabilizzazioneAction;
	}

	public void setContabilizzazioneAction(String contabilizzazioneAction) {
		this.contabilizzazioneAction = contabilizzazioneAction;
	}

	public boolean isCheckContabilizzazione() {
		return checkContabilizzazione;
	}

	public void setCheckContabilizzazione(boolean checkContabilizzazione) {
		this.checkContabilizzazione = checkContabilizzazione;
	}

	public IdFattura getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;

		this.log.debug("Set Id Fattura: " + idFattura);  

		if(this.idFattura != null){
			if(this.service != null){
				((ContabilizzazioneService) this.service).setIdFattura(this.idFattura); 
				try {
					this.listaContabilizzazioni = this.service.findAll();
					this.statoOriginale = this.service.findAll();
				} catch (ServiceException e) {
					log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
				}
			}
	
			// carico la fattura
			try {
				this.fattura = this.fatturaService.findById(this.idFattura.getId());
				this.dataUltimaOperazione = this.calcolaDataUltimaOperazione();
			} catch (ServiceException e) {
				this.log.debug("Si e' verificato un errore durante il caricamento del dettaglio fattura: "+ e.getMessage(), e);
			}
	
			this.abilitaOperazioni();
		}
	}

	private Date calcolaDataUltimaOperazione(){
		// carico la fattura
		try {
			return this.fatturaService.getDataUltimaOperazioneByIdFattura(this.idFattura); 
		} catch (ServiceException e) {
			this.log.debug("Si e' verificato un errore durante il caricamento della data ultima operazione fattura: "+ e.getMessage(), e);
			return null;
		}
	}

	private void abilitaOperazioni(){
		this.esitoContabilizzazione = null;
		// carico l'esito
		try {
			Long idEsito = this.fatturaService.getIdEsitoContabilizzazione(this.idFattura.getId());

			if(idEsito!= null){
				this.esitoContabilizzazione =  this.esitoService.findById(idEsito);
			}
		} catch (ServiceException e) {
			this.log.debug("Si e' verificato un errore durante il caricamento dell'esito contabilizzazione: "+ e.getMessage(), e);
		}


		this.abilitaOperazioni = true;
		this.linkErroreContabilizzazioni.setRendered(false);

		// 1. presenza esito
		if(this.esitoContabilizzazione != null){
			EsitoTrasmissioneType esitoTrasmissione = this.esitoContabilizzazione.getDTO().getEsitoTrasmissione();

			switch (esitoTrasmissione) {
			case KO:
				this.esitoContabilizzazioni.setValue("contabilizzazione.stato.erroreTrasmissione");
				this.linkErroreContabilizzazioni.setRendered(true);
				break;
			case OK:
			default:
				this.esitoContabilizzazioni.setValue("contabilizzazione.stato.ok");

				// controllo l'esito dei elaborazione
				String esitoElaborazione = this.esitoContabilizzazione.getDTO().getEsitoElaborazione();
				// controllo errore elaborazione
				if(esitoElaborazione == null || !"OK".equalsIgnoreCase(esitoElaborazione)){
					this.esitoContabilizzazioni.setValue("contabilizzazione.stato.erroreElaborazione");
					this.linkErroreContabilizzazioni.setRendered(true);
				}
				break;
			}
		}else {
			// sono in stato non calcolabile
			this.esitoContabilizzazioni.setValue("contabilizzazione.stato.nuovo");

			// No esito
			// sono presenti delle entries allora sono in stato in Elaborazione
			if(this.listaContabilizzazioni != null && this.listaContabilizzazioni.size() > 0){
				this.abilitaOperazioni = false;
				this.esitoContabilizzazioni.setValue("contabilizzazione.stato.inElaborazione");
			}
		}
	}

	public List<ContabilizzazionePccBean> getStatoOriginale() {
		return statoOriginale;
	}

	public void setStatoOriginale(List<ContabilizzazionePccBean> statoOriginale) {
		this.statoOriginale = statoOriginale;
	}

	public List<ContabilizzazionePccBean> getListaContabilizzazioni() {
		return listaContabilizzazioni;
	}

	public void setListaContabilizzazioni(List<ContabilizzazionePccBean> listaContabilizzazioni) {
		this.listaContabilizzazioni = listaContabilizzazioni;
	}

	public boolean isAbilitaOperazioni() {
		return abilitaOperazioni || this.editMode;
	}

	public void setAbilitaOperazioni(boolean abilitaOperazioni) {
		this.abilitaOperazioni = abilitaOperazioni;
	}

	public FatturaElettronicaBean getFattura() {
		return fattura;
	}

	public void setFattura(FatturaElettronicaBean fattura) {
		this.fattura = fattura;
	}

	public Text getEsitoContabilizzazioni() {
		return esitoContabilizzazioni;
	}

	public void setEsitoContabilizzazioni(Text esitoContabilizzazioni) {
		this.esitoContabilizzazioni = esitoContabilizzazioni;
	}

	public Button getLinkErroreContabilizzazioni() {
		return linkErroreContabilizzazioni;
	}

	public void setLinkErroreContabilizzazioni(Button linkErroreContabilizzazioni) {
		this.linkErroreContabilizzazioni = linkErroreContabilizzazioni;
	}

	public OutputGroup getFieldsContabilizzazioni() {
		return fieldsContabilizzazioni;
	}

	public void setFieldsContabilizzazioni(OutputGroup fieldsContabilizzazioni) {
		this.fieldsContabilizzazioni = fieldsContabilizzazioni;
	}

	public EsitoPccBean getEsitoContabilizzazione() {
		return esitoContabilizzazione;
	}

	public void setEsitoContabilizzazione(EsitoPccBean esitoContabilizzazione) {
		this.esitoContabilizzazione = esitoContabilizzazione;
	}

	public boolean isVisualizzaLinkErroreContabilizzazioni() {
		if(!this.editMode){
			if(this.operazioneAsincrona){
				try {
					int val = this.operazioneService.countTracce(this.idFattura, StatoType.AS_PRESA_IN_CARICO);
					
					if(val == 0){
						((ContabilizzazioneService) this.service).setIdFattura(this.idFattura); 
						try {
							this.listaContabilizzazioni = this.service.findAll();
							this.statoOriginale = this.service.findAll();
							this.dataUltimaOperazione = this.calcolaDataUltimaOperazione();
						} catch (ServiceException e) {
							log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
						}
						this.scadenzaMBean.setDataUltimaOperazione(this.dataUltimaOperazione); 
						this.operazioneAsincrona = false;
//						this.scadenzaMBean.setOperazioneAsincrona(this.operazioneAsincrona);
					}
				} catch (ServiceException e) {	}
			}
			
			this.abilitaOperazioni();
			
		}
		
		return this.linkErroreContabilizzazioni.isRendered();
	}

	public void setVisualizzaLinkErroreContabilizzazioni(boolean visualizzaErroreLinkContabilizzazioni) {
	}

	public String inviaRichiestaRiallineamentoAsincrono (){
		ProxyDatiFatturaRichiestaTipo richiesta = new ProxyDatiFatturaRichiestaTipo();
		this.operazioneAsincrona = true;
		this.scadenzaMBean.setOperazioneAsincrona(this.operazioneAsincrona);
		try {
			Date dataUltimaOperazioneAttuale = this.calcolaDataUltimaOperazione();
			log.debug("Data Ultima Operazione Attuale: " + dataUltimaOperazioneAttuale);
			log.debug("Data Ultima Operazione Precedente: " + this.dataUltimaOperazione);
			if(dataUltimaOperazioneAttuale != null && this.dataUltimaOperazione != null){
				if(dataUltimaOperazioneAttuale.getTime() > this.dataUltimaOperazione.getTime()){
					// c'e' stata una modifica segnalo all'utente.
					MessageUtils.addWarnMsg(Utils.getInstance().getMessageWithParamsFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamento.fatturaAggiornata"));
					return null;
				}
			}

			FattureWS clientProxy = Utils.creaClientProxyPcc(this.properties,this.log);

			TestataAsyncRichiestaTipo value = new TestataAsyncRichiestaTipo();
			value.setIdentificativoTransazionePA(Utils.generaIdentificativoTransazionePA());
			value.setUtenteRichiedente(Utils.getLoggedUtente().getUsername()); 
			richiesta.setTestataRichiesta(value);
			DatiRichiestaDatiFatturaProxyTipo datiRichiesta = new DatiRichiestaDatiFatturaProxyTipo();
			IdentificazioneSDITipo idSDI = new IdentificazioneSDITipo();

			idSDI.setLottoSDI(new BigDecimal(this.fattura.getIdentificativoSdi().getValue()));
			idSDI.setNumeroFattura(this.fattura.getNumero().getValue());
			datiRichiesta.setIdentificazioneSDI(idSDI);
			richiesta.setDatiRichiesta(datiRichiesta); 

			ProxyRispostaTipo wSProxyOperazioneContabile = clientProxy.wSProxyDatiFattura(richiesta);

			DatiRispostaProxyTipo datiRisposta = wSProxyOperazioneContabile.getDatiRisposta();

			EsitoOkKoTipo esito = datiRisposta.getEsitoTrasmissione();

			if(esito.equals(EsitoOkKoTipo.OK)){
				((ContabilizzazioneService) this.service).setIdFattura(this.idFattura); 
				try {
					this.listaContabilizzazioni = this.service.findAll();
					this.statoOriginale = this.service.findAll();
					this.dataUltimaOperazione = this.calcolaDataUltimaOperazione();
				} catch (ServiceException e) {
					log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
				}
				this.editMode = false;
				this.scadenzaMBean.setEditMode(this.editMode); 
				this.scadenzaMBean.setDataUltimaOperazione(this.dataUltimaOperazione); 

				MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoAsincronaPresaInCaricoOK"));
			}else {
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoAsincronaPresaInCaricoKO.noRispedizione"));
			}
		} catch (WSGenericFault e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoAsincronaKO"));
		} catch (WSAuthorizationFault e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			String authDetail = e.getFaultInfo().getDetail();
			
			// Utente non autorizzato all'operazione
			if(authDetail.startsWith("Utente")) 
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoAsincronaKO.utenteNonAutorizzato"));
			else{ // Dipartimento non autorizzata 
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoAsincronaKO.dipartimentoNonAutorizzato"));
			}
		} 
		catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della contabilizzazione: " + e.getMessage(), e);
			
			if(e instanceof javax.xml.ws.soap.SOAPFaultException){
				if(e.getMessage() != null && e.getMessage().contains("Could not send Message")){
					MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoAsincronaErroreConnessione"));
					return null;
				}
					
				MessageUtils.addErrorMsg(e.getMessage());	
				return null;
			}
			
			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("contabilizzazione.form.operazioneRichiestaRiallineamentoAsincronaErroreGenerico"));
		}finally {
			if(!this.editMode){
				if(this.operazioneAsincrona){
					try {
						int val = this.operazioneService.countTracce(this.idFattura, StatoType.AS_PRESA_IN_CARICO);
						
						if(val == 0){
							((ContabilizzazioneService) this.service).setIdFattura(this.idFattura); 
							try {
								this.listaContabilizzazioni = this.service.findAll();
								this.statoOriginale = this.service.findAll();
								this.dataUltimaOperazione = this.calcolaDataUltimaOperazione();
							} catch (ServiceException e) {
								log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
							}
							this.scadenzaMBean.setDataUltimaOperazione(this.dataUltimaOperazione); 
							this.operazioneAsincrona = false;
//							this.scadenzaMBean.setOperazioneAsincrona(this.operazioneAsincrona);
						}
					} catch (ServiceException e) {	}
				}
				
				this.abilitaOperazioni();
				this.scadenzaMBean.abilitaOperazioni();
			}
		}

		return null;
	}

	public boolean isAbilitaOperazioneAsincrona() {
		return abilitaOperazioni || this.editMode || ( this.scadenzaMBean != null ? this.scadenzaMBean.isAbilitaOperazioni() : true); 
	}

	public void setAbilitaOperazioneAsincrona(boolean abilitaOperazioni) {
		this.abilitaOperazioni = abilitaOperazioni;
	}

	public ScadenzaMBean getScadenzaMBean() {
		return scadenzaMBean;
	}

	public void setScadenzaMBean(ScadenzaMBean scadenzaMBean) {
		this.scadenzaMBean = scadenzaMBean;
		if(this.scadenzaMBean != null)
			this.scadenzaMBean.setContabilizzazioneMBean(this); 
	}

	public void setDataUltimaOperazione(Date dataUltimaOperazione) {
		this.dataUltimaOperazione = dataUltimaOperazione;
	}

	public Date getDataUltimaOperazione() {
		return dataUltimaOperazione;
	}

	private String getNextIdImporto() throws Exception{
		String nextId = null;
		boolean exit = false;
		boolean found = false;

		if(this.listaContabilizzazioni != null && this.listaContabilizzazioni.size() > 0){
			while(!exit){
				found = false;
				nextId = Utils.generaIdentificativoImporto().substring(0,3); 
				for (ContabilizzazionePccBean bean : this.listaContabilizzazioni) {
					if(bean.getDTO().getSistemaRichiedente().equals(ConsoleProperties.getInstance(log).getSistemaRichiedente())){
						String idImporto = bean.getDTO().getIdImporto();
						if(idImporto.equals(nextId)){
							found = true;
							break;
						}
					}
				}

				exit = !found;
			}
		} else 
			nextId = Utils.generaIdentificativoImporto().substring(0,3);

		return nextId; 
	}

	public boolean isOperazioneAsincrona() {
		return operazioneAsincrona;
	}

	public void setOperazioneAsincrona(boolean operazioneAsincrona) {
		this.operazioneAsincrona = operazioneAsincrona;
	}

	public boolean isVisualizzaRiallineamentoSincrono() {
		this.visualizzaRiallineamentoSincrono = Utils.getLoginBean().isAdmin();
		return visualizzaRiallineamentoSincrono;
	}

	public void setVisualizzaRiallineamentoSincrono(boolean visualizzaRiallineamentoSincrono) {
		this.visualizzaRiallineamentoSincrono = visualizzaRiallineamentoSincrono;
	}
	
	
}
