/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.DipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.view.IViewBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;


public class DipartimentoBean extends BaseBean<Dipartimento, Long> implements IViewBean<Dipartimento, Long>{ 


	private Text codice = null;
	private Text ente = null;
	private Text descrizione = null;
	private Text modalitaPush = null;
	private Text endpoint = null;
	private Text username = null;
	private Text password = null;
	private Text notificaAutomatica = null;
	private Text registro = null;
	
	private Text indirizziNotifica = null;
	private Text pagamentoIVA = null;
	private Text datiFattura = null;
	private Text inserimentoFattura = null;
	private Text statoFattura = null;
	private Text movimentiErarioIVA = null;
	private Text downloadDocumento = null;
	private Text consultazioneTracce = null;
	private Text pagamento = null;
	private Text stornoPagamento = null;
	private Text comunicazioneScadenza = null;
	private Text cancellazioneScadenze = null;
	private Text contabilizzazione = null;
	private Text stornoContabilizzazione = null;
	private Text ricezioneFattura = null;
	private Text rifiutoFattura = null;
	
	private Text fatturazioneAttiva = null;
	private Text firmaAutomatica = null;
	private Text codiceProcedimento = null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	// Gruppo Informazioni Proprieta
	private OutputGroup fieldsProperties = null;
	
	
	private EnteBean enteBean = null;
	
	// Gruppo Informazioni PCC
	private OutputGroup fieldsDatiPCC = null;	
	
	 private List<PccDipartimentoOperazione> listaProprietaAbilitate = null; 
	 
	 private boolean showPCC = false;

	public DipartimentoBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}
	
	public void init() throws FactoryException{
		this.codice = this.getFactory().getOutputFieldFactory().createText("codice","dipartimento.codice");
		this.ente = this.getFactory().getOutputFieldFactory().createText("ente","dipartimento.ente");
		this.descrizione = this.getFactory().getOutputFieldFactory().createText("descrizione","dipartimento.descrizione");
		this.modalitaPush = this.getFactory().getOutputFieldFactory().createText("modalitaPush","dipartimento.modalitaPush");
		this.endpoint = this.getFactory().getOutputFieldFactory().createText("endpoint","dipartimento.endpoint");
		this.username = this.getFactory().getOutputFieldFactory().createText("username","dipartimento.username");
		this.registro = this.getFactory().getOutputFieldFactory().createText("registro","dipartimento.registro");
		this.password = this.getFactory().getOutputFieldFactory().createText("password","dipartimento.password");
		this.password.setSecret(true); 

		this.notificaAutomatica = this.getFactory().getOutputFieldFactory().createText("notificaAutomatica","dipartimento.notificaAutomatica");
		
		this.indirizziNotifica = this.getFactory().getOutputFieldFactory().createText("indirizziNotifica","dipartimento.pcc.indirizziNotifica");
		this.pagamentoIVA = this.getFactory().getOutputFieldFactory().createText("pagamentoIVA","utente.pcc.pagamentoIVA");
		this.datiFattura = this.getFactory().getOutputFieldFactory().createText("datiFattura","utente.pcc.datiFattura");
		this.inserimentoFattura = this.getFactory().getOutputFieldFactory().createText("inserimentoFattura","utente.pcc.inserimentoFattura");
		this.statoFattura = this.getFactory().getOutputFieldFactory().createText("statoFattura","utente.pcc.statoFattura");
		this.movimentiErarioIVA = this.getFactory().getOutputFieldFactory().createText("movimentiErarioIVA","utente.pcc.movimentiErarioIVA");
		this.downloadDocumento = this.getFactory().getOutputFieldFactory().createText("downloadDocumento","utente.pcc.downloadDocumento");
		this.consultazioneTracce = this.getFactory().getOutputFieldFactory().createText("consultazioneTracce","dipartimento.pcc.consultazioneTracce");
		this.pagamento = this.getFactory().getOutputFieldFactory().createText("pagamento","dipartimento.pcc.pagamento");
		this.stornoPagamento = this.getFactory().getOutputFieldFactory().createText("stornoPagamento","dipartimento.pcc.stornoPagamento");
		this.comunicazioneScadenza = this.getFactory().getOutputFieldFactory().createText("comunicazioneScadenza","dipartimento.pcc.comunicazioneScadenza");
		this.cancellazioneScadenze = this.getFactory().getOutputFieldFactory().createText("cancellazioneScadenze","dipartimento.pcc.cancellazioneScadenze");
		this.contabilizzazione = this.getFactory().getOutputFieldFactory().createText("contabilizzazione","dipartimento.pcc.contabilizzazione");
		this.stornoContabilizzazione = this.getFactory().getOutputFieldFactory().createText("stornoContabilizzazione","dipartimento.pcc.stornoContabilizzazione");
		this.ricezioneFattura = this.getFactory().getOutputFieldFactory().createText("ricezioneFattura","dipartimento.pcc.ricezioneFattura");
		this.rifiutoFattura = this.getFactory().getOutputFieldFactory().createText("rifiutoFattura","dipartimento.pcc.rifiutoFattura");
		
		this.fatturazioneAttiva = this.getFactory().getOutputFieldFactory().createText("fatturazioneAttiva","dipartimento.fatturazioneAttiva");
		this.firmaAutomatica = this.getFactory().getOutputFieldFactory().createText("firmaAutomatica","dipartimento.firmaAutomatica");
		this.codiceProcedimento = this.getFactory().getOutputFieldFactory().createText("codiceProcedimento","dipartimento.codiceProcedimento");

		this.setField(this.codice);
		this.setField(this.descrizione);
		this.setField(this.ente);
		this.setField(this.registro);
		this.setField(this.notificaAutomatica);
		this.setField(this.modalitaPush);
		
		this.setField(this.indirizziNotifica);
		this.setField(this.pagamentoIVA);
		this.setField(this.datiFattura);
		this.setField(this.inserimentoFattura);
		this.setField(this.statoFattura);
		this.setField(this.movimentiErarioIVA);
		this.setField(this.downloadDocumento);
		this.setField(this.consultazioneTracce);
		this.setField(this.pagamento);
		this.setField(this.stornoPagamento);
		this.setField(this.comunicazioneScadenza);
		this.setField(this.cancellazioneScadenze);
		this.setField(this.contabilizzazione);
		this.setField(this.stornoContabilizzazione);
		this.setField(this.ricezioneFattura);
		this.setField(this.rifiutoFattura);
		
		this.setField(this.fatturazioneAttiva);
		this.setField(this.firmaAutomatica);
		this.setField(this.codiceProcedimento);
		
		this.fieldsDatiGenerali = this.getFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.codice);
		this.fieldsDatiGenerali.addField(this.descrizione);
		this.fieldsDatiGenerali.addField(this.ente);
		this.fieldsDatiGenerali.addField(this.registro);
		this.fieldsDatiGenerali.addField(this.notificaAutomatica);
		this.fieldsDatiGenerali.addField(this.modalitaPush);
		
		this.fieldsDatiGenerali.addField(this.fatturazioneAttiva);
		this.fieldsDatiGenerali.addField(this.firmaAutomatica);
		this.fieldsDatiGenerali.addField(this.codiceProcedimento);
		
		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");

		this.fieldsProperties = this.getFactory().getOutputFieldFactory().createOutputGroup("dipartimentoProperties",2);
		this.fieldsProperties.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsProperties.setColumnClasses("labelAllineataDx,valueAllineataSx");
		
		
		this.fieldsDatiPCC = this.getFactory().getOutputFieldFactory().createOutputGroup("datiPCC",2);
		this.fieldsDatiPCC.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiPCC.setColumnClasses("labelAllineataDx,valueAllineataSx");
		
		this.fieldsDatiPCC.addField(this.indirizziNotifica);
		this.fieldsDatiPCC.addField(this.pagamentoIVA);
		this.fieldsDatiPCC.addField(this.datiFattura);
		this.fieldsDatiPCC.addField(this.inserimentoFattura);
		this.fieldsDatiPCC.addField(this.statoFattura);
		this.fieldsDatiPCC.addField(this.movimentiErarioIVA);
		this.fieldsDatiPCC.addField(this.downloadDocumento);
		this.fieldsDatiPCC.addField(this.consultazioneTracce);
		this.fieldsDatiPCC.addField(this.pagamento);
		this.fieldsDatiPCC.addField(this.stornoPagamento);
		this.fieldsDatiPCC.addField(this.comunicazioneScadenza);
		this.fieldsDatiPCC.addField(this.cancellazioneScadenze);
		this.fieldsDatiPCC.addField(this.contabilizzazione);
		this.fieldsDatiPCC.addField(this.stornoContabilizzazione);
		this.fieldsDatiPCC.addField(this.ricezioneFattura);
		this.fieldsDatiPCC.addField(this.rifiutoFattura);
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(Dipartimento dto) {
		super.setDTO(dto);

		this.codice.setValue(this.getDTO().getCodice());
		this.ente.setValue(this.getDTO().getEnte().getNome());
		this.descrizione.setValue(this.getDTO().getDescrizione());
		if(this.getDTO().getRegistro() != null)
			this.registro.setValue( this.getDTO().getRegistro().getNome());

		boolean modalitaPush2 = this.getDTO().getModalitaPush();
		this.modalitaPush.setValue(Utils.getBooleanAsLabel(modalitaPush2,"commons.label.si", "commons.label.no"));
		//		URI endpoint2 = this.getDTO().getEndpoint();
		//		if(endpoint2 != null)
		//			this.endpoint.setValue(endpoint2.toString());
		//		this.username.setValue(this.getDTO().getUsername());
		//		this.password.setValue(this.getDTO().getPassword());
		boolean notificaAutomatica2 = this.getDTO().getAccettazioneAutomatica();
		this.notificaAutomatica.setValue(Utils.getBooleanAsLabel(notificaAutomatica2,"commons.label.si", "commons.label.no"));

		this.indirizziNotifica.setValue(this.getDTO().getListaEmailNotifiche());
		
		this.fatturazioneAttiva.setValue(Utils.getBooleanAsLabel(this.getDTO().isFatturazioneAttiva(),"commons.label.si", "commons.label.no"));
		this.firmaAutomatica.setValue(Utils.getBooleanAsLabel(this.getDTO().isFirmaAutomatica(),"commons.label.si", "commons.label.no"));
		this.codiceProcedimento.setValue(this.getDTO().getIdProcedimento());
	}
	
	public void setProprietaPCC(List<PccOperazione> listaProprietaConsentiteAiDipartimenti, List<PccDipartimentoOperazione> listaProprietaAbilitate){
		this.listaProprietaAbilitate = listaProprietaAbilitate;
		
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.DATI_FATTURA, this.datiFattura);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.INSERIMENTO_FATTURA, this.inserimentoFattura);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.STATO_FATTURA, this.statoFattura);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CP,this.pagamento);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SP,this.stornoPagamento);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CS,this.comunicazioneScadenza);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS,this.cancellazioneScadenze);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_CO,this.contabilizzazione);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC,this.stornoContabilizzazione);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RC,this.ricezioneFattura);
		Utils.impostaValoreProprietaPCCDipartimento(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_RF,this.rifiutoFattura);
	}

 

	public void setListaNomiProperties(List<DipartimentoProperty> listaProperties){
		// svuoto la lista per sicurezza
		this.fieldsProperties.getFields().clear();

		if(this.getDTO() != null && listaProperties != null){
			try{
				Text proprieta = null;
				int i = 0;
				for (DipartimentoProperty dipartimentoProperty : listaProperties) {
					boolean found = false;
					proprieta  = this.getFactory().getOutputFieldFactory().createText(("prop_" + (i++)),dipartimentoProperty.getLabel());

					for (DipartimentoPropertyValue dipartimentoPropertyValue : this.getDTO().getDipartimentoPropertyValueList()) {
						if(dipartimentoProperty.getNome().equals(dipartimentoPropertyValue.getIdProperty().getNome())){
							proprieta.setValue(dipartimentoPropertyValue.getValore());
							found = true;
							break;
						}
					}

					if(!found){
						proprieta.setValue(null);
					}

					this.fieldsProperties.addField(proprieta);
				}
			}catch(Exception e){

			}
		}
	}

	public Text getNotificaAutomatica() {
		return this.notificaAutomatica;
	}

	public void setNotificaAutomatica(Text notificaAutomatica) {
		this.notificaAutomatica = notificaAutomatica;
	}

	public Text getCodice() {
		return this.codice;
	}

	public void setCodice(Text codice) {
		this.codice = codice;
	}

	public Text getEnte() {
		return this.ente;
	}

	public void setEnte(Text ente) {
		this.ente = ente;
	}

	public Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getModalitaPush() {
		return this.modalitaPush;
	}

	public void setModalitaPush(Text modalitaPush) {
		this.modalitaPush = modalitaPush;
	}

	public Text getEndpoint() {
		return this.endpoint;
	}

	public void setEndpoint(Text endpoint) {
		this.endpoint = endpoint;
	}

	public Text getUsername() {
		return this.username;
	}

	public void setUsername(Text username) {
		this.username = username;
	}

	public Text getPassword() {
		return this.password;
	}

	public void setPassword(Text password) {
		this.password = password;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return this.fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}

	public OutputGroup getFieldsProperties() {
		return this.fieldsProperties;
	}

	public void setFieldsProperties(OutputGroup fieldsProperties) {
		this.fieldsProperties = fieldsProperties;
	}

	public Text getRegistro() {
		return this.registro;
	}

	public void setRegistro(Text registro) {
		this.registro = registro;
	}

	public Text getIndirizziNotifica() {
		return indirizziNotifica;
	}

	public void setIndirizziNotifica(Text indirizziNotifica) {
		this.indirizziNotifica = indirizziNotifica;
	}

	public Text getDatiFattura() {
		return datiFattura;
	}

	public void setDatiFattura(Text datiFattura) {
		this.datiFattura = datiFattura;
	}

	public Text getInserimentoFattura() {
		return inserimentoFattura;
	}

	public void setInserimentoFattura(Text inserimentoFattura) {
		this.inserimentoFattura = inserimentoFattura;
	}

	public Text getStatoFattura() {
		return statoFattura;
	}

	public void setStatoFattura(Text statoFattura) {
		this.statoFattura = statoFattura;
	}

	public Text getConsultazioneTracce() {
		return consultazioneTracce;
	}

	public void setConsultazioneTracce(Text consultazioneTracce) {
		this.consultazioneTracce = consultazioneTracce;
	}

	public Text getPagamento() {
		return pagamento;
	}

	public void setPagamento(Text pagamento) {
		this.pagamento = pagamento;
	}

	public Text getStornoPagamento() {
		return stornoPagamento;
	}

	public void setStornoPagamento(Text stornoPagamento) {
		this.stornoPagamento = stornoPagamento;
	}

	public Text getComunicazioneScadenza() {
		return comunicazioneScadenza;
	}

	public void setComunicazioneScadenza(Text comunicazioneScadenza) {
		this.comunicazioneScadenza = comunicazioneScadenza;
	}

	public Text getCancellazioneScadenze() {
		return cancellazioneScadenze;
	}

	public void setCancellazioneScadenze(Text cancellazioneScadenze) {
		this.cancellazioneScadenze = cancellazioneScadenze;
	}

	public Text getContabilizzazione() {
		return contabilizzazione;
	}

	public void setContabilizzazione(Text contabilizzazione) {
		this.contabilizzazione = contabilizzazione;
	}

	public Text getStornoContabilizzazione() {
		return stornoContabilizzazione;
	}

	public void setStornoContabilizzazione(Text stornoContabilizzazione) {
		this.stornoContabilizzazione = stornoContabilizzazione;
	}

	public Text getRicezioneFattura() {
		return ricezioneFattura;
	}

	public void setRicezioneFattura(Text ricezioneFattura) {
		this.ricezioneFattura = ricezioneFattura;
	}

	public Text getRifiutoFattura() {
		return rifiutoFattura;
	}

	public void setRifiutoFattura(Text rifiutoFattura) {
		this.rifiutoFattura = rifiutoFattura;
	}

	public OutputGroup getFieldsDatiPCC() {
		return fieldsDatiPCC;
	}

	public void setFieldsDatiPCC(OutputGroup fieldsDatiPCC) {
		this.fieldsDatiPCC = fieldsDatiPCC;
	}

	public List<PccDipartimentoOperazione> getListaProprietaAbilitate() {
		return listaProprietaAbilitate;
	}

	public void setListaProprietaAbilitate(List<PccDipartimentoOperazione> listaProprietaAbilitate) {
		this.listaProprietaAbilitate = listaProprietaAbilitate;
	}

	public EnteBean getEnteBean() {
		return enteBean;
	}

	public void setEnteBean(EnteBean enteBean) {
		this.enteBean = enteBean;
		this.showPCC = this.enteBean != null ? (this.enteBean.getDTO().getIdPccAmministrazione() != null) : false;
	}

	public boolean isShowPCC() {
		return showPCC;
	}

	public void setShowPCC(boolean showPCC) {
		this.showPCC = showPCC;
	}

	public Text getPagamentoIVA() {
		return pagamentoIVA;
	}

	public void setPagamentoIVA(Text pagamentoIVA) {
		this.pagamentoIVA = pagamentoIVA;
	}

	public Text getMovimentiErarioIVA() {
		return movimentiErarioIVA;
	}

	public void setMovimentiErarioIVA(Text movimentiErarioIVA) {
		this.movimentiErarioIVA = movimentiErarioIVA;
	}

	public Text getDownloadDocumento() {
		return downloadDocumento;
	}

	public void setDownloadDocumento(Text downloadDocumento) {
		this.downloadDocumento = downloadDocumento;
	}

	public Text getFatturazioneAttiva() {
		return fatturazioneAttiva;
	}

	public void setFatturazioneAttiva(Text fatturazioneAttiva) {
		this.fatturazioneAttiva = fatturazioneAttiva;
	}

	public Text getFirmaAutomatica() {
		return firmaAutomatica;
	}

	public void setFirmaAutomatica(Text firmaAutomatica) {
		this.firmaAutomatica = firmaAutomatica;
	}

	public Text getCodiceProcedimento() {
		return codiceProcedimento;
	}

	public void setCodiceProcedimento(Text codiceProcedimento) {
		this.codiceProcedimento = codiceProcedimento;
	}

}
