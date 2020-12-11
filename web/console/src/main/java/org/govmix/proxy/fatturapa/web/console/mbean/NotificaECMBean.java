/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.mbean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.govmix.proxy.fatturapa.notificaesitocommittente.EsitoCommittente;
import org.govmix.proxy.fatturapa.notificaesitocommittente.MotivoRifiuto;
import org.govmix.proxy.fatturapa.notificaesitocommittente.NotificaEC;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.InvioNotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.exception.NotificaGiaInviataException;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.bean.NotificaECBean;
import org.govmix.proxy.fatturapa.web.console.form.NotificaECForm;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.MultipleCheckBoxImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseFormMBean;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.exception.InviaException;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.input.TextArea;

/**
 * NotificaECMBean MBean per la gestione della schermata di invio Notifica EC.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECMBean extends BaseFormMBean<NotificaECBean, Long, NotificaECForm> {

	private String fromPage= null;

	private FatturaElettronicaBean fattura = null;

	private List<SelectItem> listaEsiti = null;
	private List<SelectItem> listaMotivi = null;

	private boolean showForm = true;

	private IFatturaElettronicaService fatturaService= null;

	public NotificaECMBean(){
		super(LoggerManager.getConsoleLogger());
		this.fatturaService = new FatturaElettronicaService();

		this.selectedElement = null;
		this.setOutcomes();
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome("annulla");
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome(null);
		this.getNavigationManager().setFiltraOutcome(null);
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome(null);
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome(null);
		this.getNavigationManager().setRestoreSearchOutcome(null);
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
		((MultipleCheckBoxImpl)this.form.getMotivoRifiuto()).setElencoSelectItems(this.getListaMotivi());

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
			IdFattura idFattura = new IdFattura(fatturaDTO.isFatturazioneAttiva());

			idFattura.setPosizione(fatturaDTO.getPosizione());
			idFattura.setIdentificativoSdi(fatturaDTO.getIdentificativoSdi());

			notificaECDTO.setIdFattura(idFattura );
		}
	}

	public List<SelectItem> getListaEsiti() {

		this.listaEsiti = new ArrayList<SelectItem>();

		this.listaEsiti.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*","--")));

		this.listaEsiti.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						EsitoCommittenteType.EC01.getValue(), ("notificaEsitoCommittente.esito.EC01"))));
		this.listaEsiti.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						EsitoCommittenteType.EC02.getValue(),   ("notificaEsitoCommittente.esito.EC02"))));


		return this.listaEsiti;
	}

	public void setListaEsiti(List<SelectItem> listaEsiti) {
		this.listaEsiti = listaEsiti;
	}


	public List<SelectItem> getListaMotivi() {
		this.listaMotivi = new ArrayList<SelectItem>();
		
		this.listaMotivi.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						MotivoRifiuto.MR_01.value(), Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.motivoRifiuto.MR01"))));
		this.listaMotivi.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						MotivoRifiuto.MR_02.value(), Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.motivoRifiuto.MR02"))));
		this.listaMotivi.add(new SelectItem(
				new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(
						MotivoRifiuto.MR_03.value(), Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.motivoRifiuto.MR03"))));
		
		return listaMotivi;
	}

	public void setListaMotivi(List<SelectItem> listaMotivi) {
		this.listaMotivi = listaMotivi;
	}
	
	@Override
	protected String _invia() throws InviaException {
		this.log.debug("Inserimento della notifica in corso...");

		try{
			String msg = this.validaInput();

			// Non faccio la submit
			if(msg != null){
//				throw new InviaException(msg);
//				throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.operazioneNonCompletata") + " " + msg);
				MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.operazioneNonEseguita") + " " + msg);
				return null;
			}

			NotificaEC notificaECToSend = new NotificaEC();
			
			notificaECToSend.setMotivoRifiuto(this.getMotivoRifiutoValori());
			notificaECToSend.setEsito(EsitoCommittente.fromValue(this.form.getEsito().getValue().getValue()));
			notificaECToSend.setIdentificativoSdi(BigInteger.valueOf(this.fattura.getDTO().getIdentificativoSdi()));
			notificaECToSend.setPosizione(BigInteger.valueOf(this.fattura.getDTO().getPosizione()));
			notificaECToSend.setDescrizione(this.form.getDescrizione().getValue());

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
			
		}catch(InviaException e){
			throw e;
		}catch(NotificaGiaInviataException e){
			log.error("Si e' verificato un errore durante l'invio della notifica: " + e.getMessage(), e);

			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.notificaGiaInviata"));

			return null;
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante l'invio della notifica: " + e.getMessage(), e);
			throw new InviaException(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.invioNonRiuscito"));
			//			MessageUtils.addErrorMsg(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.invioNonRiuscito"));

			//			return null;
		}
		return this.getNavigationManager().getInviaOutcome();
	}


	private List<MotivoRifiuto> getMotivoRifiutoValori() {
		return this.form.getMotivoRifiutoValori();
	}

	public String validaInput(){
		String msg = null;
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem selectedEsito = this.form.getEsito().getValue();

		if(selectedEsito.getValue().equals("*"))
			return Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.esitoVuoto");

		if(selectedEsito.getValue().equalsIgnoreCase(EsitoCommittenteType.EC02.getValue())){
			List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> value = this.form.getMotivoRifiuto().getValue();
			if(value == null || (value != null && value.size() <= 0)) {
				return Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.formInvia.error.motivoRifiutoVuoto");
			}
			
			// validazione lunghezza stringa se ho selezionato un motivo
			if(value != null && value.size() > 0) {
				TextArea descrizione = this.form.getDescrizione();
				
				if(descrizione.getValue() != null){
					NotificaEC esito = new NotificaEC();
					esito.setEsito(EsitoCommittente.EC_02);
					esito.setDescrizione(descrizione.getValue());
					esito.setMotivoRifiuto(getMotivoRifiutoValori());
					try {
						int lunghezzaRealeDescrizione = InvioNotificaEsitoCommittente.getLunghezzaRealeDescrizione(esito);
						if(lunghezzaRealeDescrizione > 255)
							return Utils.getInstance().getMessageWithParamsFromResourceBundle("notificaEsitoCommittente.formInvia.error.descrizioneTroppoLunga", lunghezzaRealeDescrizione);
					} catch (Exception e) {
					}
				}
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
