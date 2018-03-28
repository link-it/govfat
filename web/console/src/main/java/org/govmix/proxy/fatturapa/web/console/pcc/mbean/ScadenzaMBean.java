/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.pcc.fatture.ComunicaScadenzaTipo;
import org.govmix.pcc.fatture.ComunicazioneScadenzaTipo;
import org.govmix.pcc.fatture.DatiRichiestaOperazioneContabileProxyTipo;
import org.govmix.pcc.fatture.EsitoOkKoTipo;
import org.govmix.pcc.fatture.FattureWS;
import org.govmix.pcc.fatture.IdentificazioneSDITipo;
import org.govmix.pcc.fatture.ListaOperazioneTipo;
import org.govmix.pcc.fatture.OperazioneTipo;
import org.govmix.pcc.fatture.OrigineTipo;
import org.govmix.pcc.fatture.ProxyOperazioneContabileRichiestaTipo;
import org.govmix.pcc.fatture.ProxyRispostaTipo;
import org.govmix.pcc.fatture.StrutturaDatiOperazioneTipo;
import org.govmix.pcc.fatture.TestataAsyncRichiestaTipo;
import org.govmix.pcc.fatture.TestataRispTipo;
import org.govmix.pcc.fatture.TipoOperazioneTipo;
import org.govmix.pcc.fatture.WSAuthorizationFault;
import org.govmix.pcc.fatture.WSGenericFault;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.orm.constants.EsitoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.EsitoPccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ScadenzaPccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.form.OperazioneForm;
import org.govmix.proxy.fatturapa.web.console.pcc.form.ScadenzaForm;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IEsitoService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.ScadenzaSearchForm;
import org.govmix.proxy.fatturapa.web.console.pcc.service.EsitoService;
import org.govmix.proxy.fatturapa.web.console.pcc.service.OperazioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.service.ScadenzaService;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.DeleteException;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.iservice.IBaseService;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.table.PagedDataTable;

public class ScadenzaMBean  extends BaseMBean<ScadenzaPccBean, Long, ScadenzaSearchForm> {

	private ScadenzaForm form;
	private String scadenzaAction = "add";
	private boolean checkScadenza = false;

	private IdFattura idFattura;
	private List<ScadenzaPccBean> statoOriginale = null; 
	private List<ScadenzaPccBean> listaScadenze = null;

	private PagedDataTable<List<ScadenzaPccBean>, OperazioneForm, ScadenzaSearchForm> table;

	private boolean operazioneAsincrona = false;

	private boolean abilitaOperazioni = true;

	private FatturaElettronicaBean fattura= null;

	private IEsitoService esitoService = null;
	private IFatturaElettronicaService fatturaService = null;
	private OperazioneService operazioneService = null;

	private EsitoPccBean esitoScadenza = null;
	private Text esitoScadenze = null;
	private Button linkErroreScadenze = null;
	private OutputGroup fieldsScadenze = null;
	private Text pianoScadenze = null;
	private Text dataPianoScadenze = null;
	private Date dataUltimaOperazione;

	private ConsoleProperties properties = null;

	private SimpleDateFormat sdf = new SimpleDateFormat(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_MM_YYYY);

	private ContabilizzazioneMBean contabilizzazioneMBean = null;


	private boolean editMode = false;

	public ScadenzaMBean(){
		super(LoggerManager.getConsoleLogger());
		this.log.debug("Scadenza MBean");

		this.setOutcomes();
		this.init();

		this.esitoService = new EsitoService();
		this.fatturaService = new FatturaElettronicaService();
		this.operazioneService = new OperazioneService();
	}

	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.getTable().setId("panelScadenze"); 
			this.getTable().setEnableDelete(false);
			this.getTable().setShowAddButton(false);
			this.getTable().setShowDetailColumn(false);
			this.getTable().setShowSelectAll(true);
			this.getTable().setHeaderText("scadenza.label.ricercaScadenze.tabellaRisultati");
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

			this.form = new ScadenzaForm();

			this.esitoScadenze = this.getFactory().getOutputFieldFactory().createText("scad_esito","scadenza.stato");
			this.linkErroreScadenze = this.getFactory().getOutputFieldFactory().createButton("scad_link_esito","scadenza.stato.link.iconTitle",null,null,
					"scadenza.stato.link.iconTitle","scadenza.stato.link.iconTitle");

			this.pianoScadenze = this.getFactory().getOutputFieldFactory().createText("scad_piano","scadenza.piano");
			this.dataPianoScadenze = this.getFactory().getOutputFieldFactory().createText("scad_piano_data","scadenza.piano.data");

			this.fieldsScadenze = this.getFactory().getOutputFieldFactory().createOutputGroup("infoScadenze",3);
			this.fieldsScadenze.setStyleClass("datiTrasmissioneTable"); 
			this.fieldsScadenze.setColumnClasses("labelAllineataDx,valueAllineataSx,valueAllineataSx");

			this.fieldsScadenze.addField(this.esitoScadenze);
			this.fieldsScadenze.addField(this.linkErroreScadenze);
		}catch (Exception e){
			this.log.error("Errore durante la init di scadenzaMBean: " + e.getMessage(),e);
		}
	}

	public IBaseService<ScadenzaPccBean, Long, ScadenzaSearchForm> getService(){
		return this.service;
	}

	@Override
	public void addNewListener(ActionEvent ae) {
		super.addNewListener(ae);
		this.setEditMode(true);
		this.selectedElement = null;
		this.selectedId = null;
		this.checkScadenza = false;
		this.scadenzaAction = "add";
		this.form.setValues(this.selectedElement);
	}


	@Override
	public void setSelectedElement(ScadenzaPccBean selectedElement) {
		super.setSelectedElement(selectedElement);
		this.setEditMode(true);
		this.checkScadenza = false;
		this.scadenzaAction = "edit";
		this.form.setValues(this.selectedElement);
	}

	@Override
	protected String _delete() throws DeleteException {
		try{
			this.setEditMode(true);
			if(this.scadenzaAction.equals("delete")){
				this.service.delete(this.selectedElement);
			} 
			return this.getNavigationManager().getDeleteOutcome();
		}catch (Exception e) {
			throw new DeleteException(e.getMessage());
		}
	}

	@Override
	protected String _invia() throws InviaException {
		this.setEditMode(true);
		String msg = this.form.valida();

		if(msg!= null){
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.erroreValidazione")+": " + msg);
		}

		try{
			long oldId = -1;
			PccScadenza nuovaScadenza = this.form.getScadenza();
			// Add
			if(!this.scadenzaAction.equals("edit")){
			} else {
				oldId = this.selectedElement.getDTO().getId();
				nuovaScadenza.setId(oldId); 
			}

			ScadenzaPccBean bean = new ScadenzaPccBean();
			bean.setDTO(nuovaScadenza);

			this.service.store(bean);

			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.salvataggioOk"));

			this.checkScadenza = true;
			//	this.setSelectedElement(bean);
			return this.getNavigationManager().getInviaOutcome();//"invia";
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il salvataggio della scadenza: " + e.getMessage(), e);
			throw new InviaException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.erroreGenerico"));
		} finally {
			this.abilitaOperazioni = this.listaScadenze != null ? this.listaScadenze.size() > 0 : false;
		}
	}

	public String inviaAggiornamentoScadenze (){
		// non si puo' invocare il servizio se la lista scadenze e' vuota
		if(this.listaScadenze.size() == 0){
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneInviaScadenze.listaVuota"));
			return null;
		}

		this.operazioneAsincrona = false;
		this.contabilizzazioneMBean.setOperazioneAsincrona(this.operazioneAsincrona);
		ProxyOperazioneContabileRichiestaTipo richiesta = new ProxyOperazioneContabileRichiestaTipo();
		try {
			Date dataUltimaOperazioneAttuale = this.getDataUltimaOperazione();
			log.debug("Data Ultima Operazione Attuale: " + dataUltimaOperazioneAttuale);
			log.debug("Data Ultima Operazione Precedente: " + this.dataUltimaOperazione);
			if(dataUltimaOperazioneAttuale != null && this.dataUltimaOperazione != null){
				if(dataUltimaOperazioneAttuale.getTime() > this.dataUltimaOperazione.getTime()){
					// c'e' stata una modifica segnalo all'utente.
					MessageUtils.addWarnMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("scadenza.form.operazioneInviaScadenze.fatturaAggiornata"));
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

			ListaOperazioneTipo listaOperazione = creaBodyRichiestaScadenze();

			datiRichiesta.setListaOperazione(listaOperazione );
			richiesta.setDatiRichiesta(datiRichiesta); 

			ProxyRispostaTipo wSProxyOperazioneContabile = clientProxy.wSProxyOperazioneContabile(richiesta);

			TestataRispTipo testataRisposta = wSProxyOperazioneContabile.getTestataRisposta();
			EsitoOkKoTipo esito = testataRisposta.getEsito();
			@SuppressWarnings("unused")
			String detail = testataRisposta.getDetail();
			OrigineTipo origine = testataRisposta.getOrigine();

			if(esito.equals(EsitoOkKoTipo.OK)){
				((ScadenzaService) this.service).setIdFattura(this.idFattura); 

				try {
					this.listaScadenze = this.service.findAll();
					this.statoOriginale = this.service.findAll();
					this.dataUltimaOperazione = this.getDataUltimaOperazione();
				} catch (ServiceException e) {
					log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
				}
				this.setEditMode(false);
				if(this.contabilizzazioneMBean != null)
					this.contabilizzazioneMBean.setDataUltimaOperazione(this.dataUltimaOperazione);

				MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("scadenza.form.operazioneInviaScadenzePresaInCaricoOK.parametri",origine.toString()));
			}			else {
				MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("scadenza.form.operazioneInviaScadenzePresaInCaricoKO.parametri",origine.toString()));
			}


		} catch (WSGenericFault e) {
			this.log.error("Si e' verificato un errore durante l'invio della scadenze: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneInviaScadenzeKO"));
		} catch (WSAuthorizationFault e) {
			this.log.error("Si e' verificato un errore durante l'invio della scadenze: " + e.getMessage(), e);
			String authDetail = e.getFaultInfo().getDetail();

			// Utente non autorizzato all'operazione
			if(authDetail.startsWith("Utente")) 
				MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneInviaScadenzeKO.utenteNonAutorizzato"));
			else{ // Dipartimento non autorizzata 
				MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneInviaScadenzeKO.dipartimentoNonAutorizzato"));
			}
		}
		catch (Exception e) {
			this.log.error("Si e' verificato un errore durante l'invio della scadenze: " + e.getMessage(), e);

			if(e instanceof javax.xml.ws.soap.SOAPFaultException){
				if(e.getMessage() != null && e.getMessage().contains("Could not send Message")){
					MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneInviaScadenzeErroreConnessione"));
					return null;
				}
				
				MessageUtils.addErrorMsg(e.getMessage());	
				return null;
			}

			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneInviaScadenzeErroreGenerico"));
		}finally {
			if(!this.isEditMode())
				this.abilitaOperazioni();
		}

		return null;
	}

	private ListaOperazioneTipo creaBodyRichiestaScadenze(){
		ListaOperazioneTipo listaOperazione = new ListaOperazioneTipo();

		OperazioneTipo operazione = new OperazioneTipo();
		operazione.setProgressivoOperazione(1);
		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();

		strutturaDatiOperazione.getComunicazioneScadenza().addAll(getListaScadenzeDaSpedire());

		operazione.setStrutturaDatiOperazione(strutturaDatiOperazione );
		operazione.setTipoOperazione(TipoOperazioneTipo.CS);

		listaOperazione.setOperazione(operazione);

		return listaOperazione;
	}

	private ListaOperazioneTipo creaBodyRichiestaCancellazioneScadenze(){
		ListaOperazioneTipo listaOperazione = new ListaOperazioneTipo();

		OperazioneTipo operazione = new OperazioneTipo();
		operazione.setProgressivoOperazione(1);
		operazione.setTipoOperazione(TipoOperazioneTipo.CCS);

		StrutturaDatiOperazioneTipo strutturaDatiOperazione = new StrutturaDatiOperazioneTipo();

		operazione.setStrutturaDatiOperazione(strutturaDatiOperazione); 

		listaOperazione.setOperazione(operazione);

		return listaOperazione;
	}

	private List<ComunicazioneScadenzaTipo> getListaScadenzeDaSpedire(){
		List<ComunicazioneScadenzaTipo> lst = new ArrayList<ComunicazioneScadenzaTipo>();

		for (ScadenzaPccBean bean : this.listaScadenze) {
			// le scadenze con pagatoRicontabilizzato > 0 non devo tornare al proxy

			if(bean.getDTO().getPagatoRicontabilizzato()  == null || bean.getDTO().getPagatoRicontabilizzato().doubleValue()  == 0){

				//if(bean.getDTO().getSistemaRichiedente().equals(this.sistemaRichiedente)){
				ComunicazioneScadenzaTipo tipo = new ComunicazioneScadenzaTipo();

				tipo.setDataScadenza(bean.getDataScadenza().getValue());
				if(bean.getDTO().getImportoInScadenza()!= null)
					tipo.setImporto(new BigDecimal(bean.getDTO().getImportoInScadenza()));
				tipo.setComunicaScadenza(ComunicaScadenzaTipo.SI);

				lst.add(tipo);
			}
		}

		return lst;
	}

	public String annullaModificheScadenze (){
		this.setEditMode(true);
		try {
			((ScadenzaService) this.service).setIdFattura(this.idFattura); 
			this.listaScadenze = this.service.findAll();
			this.statoOriginale = this.service.findAll();
			MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.annullaModificheScadenzeOk"));
		} catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il salvataggio della scadenza: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.erroreGenerico"));
		} finally {
			this.abilitaOperazioni();
		}


		return null;
	}

	public String cancellaScadenze (){

		// non si puo' invocare il servizio se la lista scadenze e' vuota
		//		if(this.listaScadenze.size() == 0){
		//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneCancellazioneScadenze.listaVuota"));
		//			return null;
		//		}

		this.operazioneAsincrona = false;
		this.contabilizzazioneMBean.setOperazioneAsincrona(this.operazioneAsincrona);
		ProxyOperazioneContabileRichiestaTipo richiesta = new ProxyOperazioneContabileRichiestaTipo();
		try {
			Date dataUltimaOperazioneAttuale = this.getDataUltimaOperazione();
			log.debug("Data Ultima Operazione Attuale: " + dataUltimaOperazioneAttuale);
			log.debug("Data Ultima Operazione Precedente: " + this.dataUltimaOperazione);
			if(dataUltimaOperazioneAttuale != null && this.dataUltimaOperazione != null){
				if(dataUltimaOperazioneAttuale.getTime() > this.dataUltimaOperazione.getTime()){
					// c'e' stata una modifica segnalo all'utente.
					MessageUtils.addWarnMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("scadenza.form.operazioneCancellazioneScadenze.fatturaAggiornata"));
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

			ListaOperazioneTipo listaOperazione = creaBodyRichiestaCancellazioneScadenze();

			datiRichiesta.setListaOperazione(listaOperazione );
			richiesta.setDatiRichiesta(datiRichiesta); 

			ProxyRispostaTipo wSProxyOperazioneContabile = clientProxy.wSProxyOperazioneContabile(richiesta);

			TestataRispTipo testataRisposta = wSProxyOperazioneContabile.getTestataRisposta();
			EsitoOkKoTipo esito = testataRisposta.getEsito();
			@SuppressWarnings("unused")
			String detail = testataRisposta.getDetail();
			OrigineTipo origine = testataRisposta.getOrigine();

			if(esito.equals(EsitoOkKoTipo.OK)){
				((ScadenzaService) this.service).setIdFattura(this.idFattura); 

				try {
					this.listaScadenze = this.service.findAll();
					this.statoOriginale = this.service.findAll();
					this.dataUltimaOperazione = this.getDataUltimaOperazione();
				} catch (ServiceException e) {
					log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
				}
				this.setEditMode(false);
				if(this.contabilizzazioneMBean != null)
					this.contabilizzazioneMBean.setDataUltimaOperazione(this.dataUltimaOperazione); 

				MessageUtils.addInfoMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("scadenza.form.operazioneCancellazioneScadenzePresaInCaricoOK.parametri",origine.toString()));
			}else {
				MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("scadenza.form.operazioneCancellazioneScadenzePresaInCaricoKO.parametri",origine.toString()));
			}


		} catch (WSGenericFault e) {
			this.log.error("Si e' verificato un errore durante l'invio della scadenze: " + e.getMessage(), e);
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneCancellazioneScadenzeKO"));
		} catch (WSAuthorizationFault e) {
			this.log.error("Si e' verificato un errore durante l'invio della scadenze: " + e.getMessage(), e);
			String authDetail = e.getFaultInfo().getDetail();

			// Utente non autorizzato all'operazione
			if(authDetail.startsWith("Utente")) 
				MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneCancellazioneScadenzeKO.utenteNonAutorizzato"));
			else{ // Dipartimento non autorizzata 
				MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneCancellazioneScadenzeKO.dipartimentoNonAutorizzato"));
			}
		}
		catch (Exception e) {
			this.log.error("Si e' verificato un errore durante l'invio della scadenze: " + e.getMessage(), e);
			
			if(e instanceof javax.xml.ws.soap.SOAPFaultException){
				if(e.getMessage() != null && e.getMessage().contains("Could not send Message")){
					MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneCancellazioneScadenzeErroreConnessione"));
					return null;
				}
				
				MessageUtils.addErrorMsg(e.getMessage());	
				return null;
			}
			
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("scadenza.form.operazioneCancellazioneScadenzeErroreGenerico"));
		} finally {
			if(!this.isEditMode())
				this.abilitaOperazioni();
		}

		return null;
	}


	public ScadenzaForm getForm() {
		return this.form;
	}


	public void setForm(ScadenzaForm form) {
		this.form = form;
	}

	public PagedDataTable<List<ScadenzaPccBean>, OperazioneForm, ScadenzaSearchForm> getTable() {
		return this.table;
	}

	public void setTable(PagedDataTable<List<ScadenzaPccBean>, OperazioneForm, ScadenzaSearchForm> table) {
		this.table = table;
	}

	public String getScadenzaAction() {
		return scadenzaAction;
	}

	public void setScadenzaAction(String scadenzaAction) {
		this.scadenzaAction = scadenzaAction;
	}

	public boolean isCheckScadenza() {
		return checkScadenza;
	}

	public void setCheckScadenza(boolean checkScadenza) {
		this.checkScadenza = checkScadenza;
	}

	public IdFattura getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;

		this.log.debug("Set Id Fattura: " + idFattura);  

		if(this.idFattura != null){
			if(this.service != null){
				((ScadenzaService) this.service).setIdFattura(this.idFattura); 

				try {
					this.listaScadenze = this.service.findAll();
					this.statoOriginale = this.service.findAll();
				} catch (ServiceException e) {
					log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
				}
			}

			// carico la fattura
			try {
				this.fattura = this.fatturaService.findById(this.idFattura.getId());
				this.dataUltimaOperazione = this.getDataUltimaOperazione();
			} catch (ServiceException e) {
				this.log.debug("Si e' verificato un errore durante il caricamento del dettaglio fattura: "+ e.getMessage(), e);
			}

			this.abilitaOperazioni();
		}
	}

	private Date getDataUltimaOperazione(){
		// carico la fattura
		try {
			return this.fatturaService.getDataUltimaOperazioneByIdFattura(this.idFattura); 
		} catch (ServiceException e) {
			this.log.debug("Si e' verificato un errore durante il caricamento della data ultima operazione fattura: "+ e.getMessage(), e);
			return null;
		}
	}

	public void abilitaOperazioni(){


		this.esitoScadenza = null;
		try {
			Long idEsito = this.fatturaService.getIdEsitoScadenza(this.idFattura.getId());

			if(idEsito != null){
				this.esitoScadenza = this.esitoService.findById(idEsito); 
			}
		} catch (ServiceException e) {
			this.log.debug("Si e' verificato un errore durante il caricamento dell'esito scadenza: "+ e.getMessage(), e);
		}


		this.abilitaOperazioni = true;
		this.linkErroreScadenze.setRendered(false);
		this.pianoScadenze.setRendered(false);
		this.dataPianoScadenze.setRendered(false); 

		boolean scadenzePresenti = false;
		String pianoString = null;
		String dataPianoString = null;

		if(this.listaScadenze != null && this.listaScadenze.size() > 0){
			scadenzePresenti = true;
			ScadenzaPccBean scadenzaPccBean = listaScadenze.get(0);
			String sistemaRichiedente2 = scadenzaPccBean.getDTO().getSistemaRichiedente();
			dataPianoString = scadenzaPccBean.getDTO().getDataRichiesta() != null ? sdf.format(scadenzaPccBean.getDTO().getDataRichiesta()) : org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.dataScadenza.sconosciuta");
			pianoString = sistemaRichiedente2;
		}



		// 1. presenza esito
		if(this.esitoScadenza != null){
			EsitoTrasmissioneType esitoTrasmissione = this.esitoScadenza.getDTO().getEsitoTrasmissione();

			switch (esitoTrasmissione) {
			case KO:
				this.esitoScadenze.setValue("scadenza.stato.erroreTrasmissione");
				this.linkErroreScadenze.setRendered(true);
				break;
			case OK:
			default:
				this.esitoScadenze.setValue("scadenza.stato.ok");

				// controllo l'esito dei elaborazione
				String esitoElaborazione = this.esitoScadenza.getDTO().getEsitoElaborazione();
				// controllo errore elaborazione
				if(esitoElaborazione == null || !"OK".equalsIgnoreCase(esitoElaborazione)){
					this.esitoScadenze.setValue("scadenza.stato.erroreElaborazione");
					this.linkErroreScadenze.setRendered(true);
				}
				break;
			}
		}else {
			// sono in stato non calcolabile
			this.esitoScadenze.setValue("scadenza.stato.nuovo");

			// No esito
			// sono presenti delle entries allora sono in stato in Elaborazione
			if(scadenzePresenti){
				this.abilitaOperazioni = false;
				this.esitoScadenze.setValue("scadenza.stato.inElaborazione");
			}
		}

		this.pianoScadenze.setValue(pianoString);   
		this.dataPianoScadenze.setValue(dataPianoString);
		if(StringUtils.isNotEmpty(pianoString )){
			this.pianoScadenze.setRendered(true);
			this.dataPianoScadenze.setRendered(true);  
		}
	}

	public List<ScadenzaPccBean> getStatoOriginale() {
		return statoOriginale;
	}

	public void setStatoOriginale(List<ScadenzaPccBean> statoOriginale) {
		this.statoOriginale = statoOriginale;
	}

	public List<ScadenzaPccBean> getListaScadenze() {
		return listaScadenze;
	}

	public void setListaScadenze(List<ScadenzaPccBean> listaScadenze) {
		this.listaScadenze = listaScadenze;
	}

	public boolean isAbilitaOperazioni() {
		return this.abilitaOperazioni || this.isEditMode();
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

	public Text getEsitoScadenze() {
		return esitoScadenze;
	}

	public void setEsitoScadenze(Text esitoScadenze) {
		this.esitoScadenze = esitoScadenze;
	}

	public Button getLinkErroreScadenze() {
		return linkErroreScadenze;
	}

	public void setLinkErroreScadenze(Button linkErroreScadenze) {
		this.linkErroreScadenze = linkErroreScadenze;
	}

	public OutputGroup getFieldsScadenze() {
		return fieldsScadenze;
	}

	public void setFieldsScadenze(OutputGroup fieldsScadenze) {
		this.fieldsScadenze = fieldsScadenze;
	}

	public EsitoPccBean getEsitoScadenza() {
		return esitoScadenza;
	}

	public void setEsitoScadenza(EsitoPccBean esitoScadenza) {
		this.esitoScadenza = esitoScadenza;
	}

	public boolean isVisualizzaLinkErroreScadenze() {
		if(!this.isEditMode()){
			if(this.operazioneAsincrona){
				try {
					int val = this.operazioneService.countTracce(this.idFattura, StatoType.AS_PRESA_IN_CARICO);

					if(val == 0){
						((ScadenzaService) this.service).setIdFattura(this.idFattura); 
						try {
							this.listaScadenze = this.service.findAll();
							this.statoOriginale = this.service.findAll();
							this.dataUltimaOperazione = this.getDataUltimaOperazione();
						} catch (ServiceException e) {
							log.error("Errore durante la lettura delle contabilizzazioni:" + e.getMessage() , e);
						}

						this.operazioneAsincrona = false;
						if(this.contabilizzazioneMBean != null){
							//							this.contabilizzazioneMBean.setOperazioneAsincrona(this.operazioneAsincrona);
							this.contabilizzazioneMBean.setDataUltimaOperazione(this.dataUltimaOperazione);
						}
					}
				} catch (ServiceException e) {	}
			}

			this.abilitaOperazioni();
		}
		return this.linkErroreScadenze.isRendered();
	}

	public void setVisualizzaLinkErroreScadenze(boolean visualizzaErroreLinkScadenze) {
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public Text getPianoScadenze() {
		return pianoScadenze;
	}

	public void setPianoScadenze(Text pianoScadenze) {
		this.pianoScadenze = pianoScadenze;
	}

	public Text getDataPianoScadenze() {
		return dataPianoScadenze;
	}

	public void setDataPianoScadenze(Text dataPianoScadenze) {
		this.dataPianoScadenze = dataPianoScadenze;
	}

	public void setDataUltimaOperazione(Date dataUltimaOperazione) {
		this.dataUltimaOperazione = dataUltimaOperazione;
	}

	public ContabilizzazioneMBean getContabilizzazioneMBean() {
		return contabilizzazioneMBean;
	}

	public void setContabilizzazioneMBean(ContabilizzazioneMBean contabilizzazioneMBean) {
		this.contabilizzazioneMBean = contabilizzazioneMBean;
	}

	public boolean isOperazioneAsincrona() {
		return operazioneAsincrona;
	}

	public void setOperazioneAsincrona(boolean operazioneAsincrona) {
		this.operazioneAsincrona = operazioneAsincrona;
	}


}

