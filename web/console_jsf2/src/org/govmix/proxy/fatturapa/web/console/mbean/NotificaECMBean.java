/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.mbean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.notificaesitocommittente.EsitoCommittente;
import org.govmix.proxy.fatturapa.notificaesitocommittente.NotificaEC;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.InvioNotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.bean.NotificaECBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.search.NotificaECForm;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.service.NotificaECService;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.impl.jsf2.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf2.mbean.BaseFormMBean;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.MessageUtils;
import org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils;
import org.openspcoop2.generic_project.web.input.TextArea;
import org.openspcoop2.generic_project.web.mbean.exception.InviaException;

/**
 * NotificaECMBean MBean per la gestione della schermata di invio Notifica EC.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
@ManagedBean(name="notificaECMBean") @ViewScoped
public class NotificaECMBean extends BaseFormMBean<NotificaECBean, Long, NotificaECForm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private String fromPage= null;

	private FatturaElettronicaBean fattura = null;

	private List<SelectItem> listaEsiti = null;

	private boolean showForm = true;

	private IFatturaElettronicaService fatturaService= null;
	
	public NotificaECMBean(){
		super(LoggerManager.getConsoleLogger());
	}
	
	@PostConstruct
	private void initManagedProperties(){
		this.fatturaService = new FatturaElettronicaService();
		setForm((NotificaECForm) Utils.findBean("notificaECForm"));
		this.service = (NotificaECService) Utils.findBean("notificaECService");
		
		String reqParameterValue = Utils.getRequestParameter(this.getSelectedIdParamName());
		
		// In teoria risolve il problema della navigazione
		if(reqParameterValue != null){
			setSelectedIdParam(reqParameterValue);
		}
	}
	
	@Override
	public void initSelectedIdParamName() throws Exception {
		setSelectedIdParamName("selectedIdFattura"); 
	}
	
	@Override
	public void init() throws Exception {
		this.selectedElement = null;
		this.selectedIdParam = null;
	}
	
	@Override
	public void initNavigationManager() throws Exception {
		this.getNavigationManager().setAnnullaOutcome("annulla");
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome(null);
		this.getNavigationManager().setInviaOutcome("invia");
		this.getNavigationManager().setMenuActionOutcome(null);
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome(null);
		this.getNavigationManager().setRestoreSearchOutcome(null);
	}
	
	@Override
	public void setSelectedIdParam(String arg0) {
		this.selectedIdParam = arg0;
	 
		this.log.debug("Selected Id ["+selectedIdParam+"]");

		if(selectedIdParam!= null){
			Long selectedIdFat =  null;
			try {
				selectedIdFat = new Long(this.selectedIdParam);
				FatturaElettronicaBean findById = this.fatturaService.findById( selectedIdFat);
				this.setFattura(findById); 
			} catch (ServiceException e) {
				log.error("Errore durante la lettura della fattura con Id ["+ selectedIdFat+"]: "+ e.getMessage(), e); 
			}
		}
	}

	@Override
	public NotificaECBean getSelectedElement() {
		if(this.selectedElement == null)
			this.selectedElement = new NotificaECBean();

		return this.selectedElement;
	}

	@Override
	public void setForm(NotificaECForm form) {
		this.form = form;
		this.form.reset();

		((SelectListImpl)this.form.getEsito()).setElencoSelectItems(this.getListaEsiti());

		this.showForm = true;
	}

	public String getFromPage() {
		return this.fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public FatturaElettronicaBean getFattura() {
		return this.fattura;
	}

	public void setFattura(FatturaElettronicaBean fattura) {
		this.fattura = fattura;

		if(fattura != null){
			if(this.selectedElement == null)
				this.selectedElement = new NotificaECBean();

			NotificaEsitoCommittente notificaECDTO = this.selectedElement.getDTO();
			FatturaElettronica fatturaDTO = fattura.getDTO();

			notificaECDTO.setAnno(fatturaDTO.getAnno());
			notificaECDTO.setIdentificativoSdi(fatturaDTO.getIdentificativoSdi());
			notificaECDTO.setPosizione(fatturaDTO.getPosizione());
			notificaECDTO.setMessageIdCommittente(fatturaDTO.getMessageId());
			notificaECDTO.setNumeroFattura(fatturaDTO.getNumero());
			IdFattura idFattura = new IdFattura();

			idFattura.setPosizione(fatturaDTO.getPosizione());
			idFattura.setIdentificativoSdi(fatturaDTO.getIdentificativoSdi());

			notificaECDTO.setIdFattura(idFattura );
		}
	}

	public List<SelectItem> getListaEsiti() {

		this.listaEsiti = new ArrayList<SelectItem>();

		this.listaEsiti.add(new SelectItem(
				new org.openspcoop2.generic_project.web.input.SelectItem("*","--")));

		this.listaEsiti.add(new SelectItem(
				new org.openspcoop2.generic_project.web.input.SelectItem(
						EsitoCommittenteType.EC01.getValue(), ("notificaEsitoCommittente.esito.EC01"))));
		this.listaEsiti.add(new SelectItem(
				new org.openspcoop2.generic_project.web.input.SelectItem(
						EsitoCommittenteType.EC02.getValue(),   ("notificaEsitoCommittente.esito.EC02"))));


		return this.listaEsiti;
	}

	public void setListaEsiti(List<SelectItem> listaEsiti) {
		this.listaEsiti = listaEsiti;
	}


	@Override
	public String azioneInvia() throws InviaException {
		this.log.debug("Inserimento della notifica in corso...");

		try{
			String msg = validaInput();

			// Non faccio la submit
			if(msg != null){
				throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.operazioneNonCompletata") + " " + msg);
				//				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.operazioneNonCompletata") + " " + msg);
				//				return null;
			}

			NotificaEC notificaECToSend = new NotificaEC();
			notificaECToSend.setDescrizione(this.form.getDescrizione().getValue());
			notificaECToSend.setEsito(EsitoCommittente.fromValue(this.form.getEsito().getValue().getValue()));
			notificaECToSend.setIdentificativoSdi(BigInteger.valueOf(this.fattura.getDTO().getIdentificativoSdi()));
			notificaECToSend.setPosizione(BigInteger.valueOf(this.fattura.getDTO().getPosizione()));

			InvioNotificaEsitoCommittente sender = new InvioNotificaEsitoCommittente(this.log);

			Utente utente = org.govmix.proxy.fatturapa.web.console.util.Utils.getLoggedUtente();
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(utente.getUsername());

			sender.invia(notificaECToSend, idUtente );

			MessageUtils.addInfoMsg(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.invioNotificaOk"));

			this.showForm = false;
			this.form.setRendered(this.showForm); 


			// ricarico la fattura con lo stato aggiornato
			FatturaElettronicaBean findById = this.fatturaService.findById(this.fattura.getDTO().getId());
			this.setFattura(findById);

		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante l'invio della notifica: " + e.getMessage(), e);
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.invioNonRiuscito"));
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.invioNonRiuscito"));

			//			return null;
		}
		return this.getNavigationManager().getInviaOutcome();
	}


	public String validaInput(){
		String msg = null;
		org.openspcoop2.generic_project.web.input.SelectItem selectedEsito = this.form.getEsito().getValue();

		if(selectedEsito.getValue().equals("*"))
			return Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.esitoVuoto");

		TextArea descrizione = this.form.getDescrizione();
		if(selectedEsito.getValue().equalsIgnoreCase(EsitoCommittenteType.EC02.getValue())){
			if(StringUtils.isEmpty(descrizione.getValue()))
				return Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.descrizioneVuota");

			if(descrizione.getValue() != null){
				if(descrizione.getValue().length() > 255)
					return Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.descrizioneTroppoLunga");
			}
		}

		return msg;
	}

	public boolean isShowForm() {
		return this.showForm;
	}

	public void setShowForm(boolean showForm) {
		this.showForm = showForm;
	}
}
