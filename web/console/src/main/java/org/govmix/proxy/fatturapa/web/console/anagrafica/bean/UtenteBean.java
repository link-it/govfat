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
package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.constants.UserType;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;

public class UtenteBean extends BaseBean<Utente, Long> implements IBean<Utente, Long>{

	private Text username = null;
	private Text password = null;
	private Text nome = null;
	private Text cognome = null;
//	private Text ente = null;
	private Text ruolo = null;
	private Text profilo = null;

	private Text utenteEsterno = null;
	private Text sistemaEsterno = null;
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

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	// Gruppo Informazioni PCC
	private OutputGroup fieldsDatiPCC = null;

	// Lista Dipartimenti associati all'utente
	private List<DipartimentoBean> listaDipartimenti = null;

	private List<PccUtenteOperazione> listaProprietaAbilitate =null;

	private DipartimentoBean metadataDipartimento = null;

//	private EnteBean enteBean =null;

	private boolean showPCC =  true;

	public UtenteBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{

		this.username = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("username","utente.username");
		this.password = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("password","utente.password");
		this.password.setSecret(true); 
		this.cognome = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cognome","utente.cognome");
		this.nome = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("nome","utente.nome");
//		this.ente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("ente","utente.ente");
		this.ruolo = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("ruolo","utente.ruolo");
		this.profilo = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("profilo","utente.profilo"); 

		this.utenteEsterno = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("utenteEsterno","utente.pcc.utenteEsterno");
		this.sistemaEsterno = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("sistemaEsterno","utente.pcc.sistemaEsterno");
		this.pagamentoIVA = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("pagamentoIVA","utente.pcc.pagamentoIVA");
		this.datiFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("datiFattura","utente.pcc.datiFattura");
		this.inserimentoFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("inserimentoFattura","utente.pcc.inserimentoFattura");
		this.statoFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("statoFattura","utente.pcc.statoFattura");
		this.movimentiErarioIVA = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("movimentiErarioIVA","utente.pcc.movimentiErarioIVA");
		this.downloadDocumento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("downloadDocumento","utente.pcc.downloadDocumento");
		this.consultazioneTracce = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("consultazioneTracce","utente.pcc.consultazioneTracce");
		this.pagamento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("pagamento","utente.pcc.pagamento");
		this.stornoPagamento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("stornoPagamento","utente.pcc.stornoPagamento");
		this.comunicazioneScadenza = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("comunicazioneScadenza","utente.pcc.comunicazioneScadenza");
		this.cancellazioneScadenze = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cancellazioneScadenze","utente.pcc.cancellazioneScadenze");
		this.contabilizzazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("contabilizzazione","utente.pcc.contabilizzazione");
		this.stornoContabilizzazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("stornoContabilizzazione","utente.pcc.stornoContabilizzazione");
		this.ricezioneFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("ricezioneFattura","utente.pcc.ricezioneFattura");
		this.rifiutoFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("rifiutoFattura","utente.pcc.rifiutoFattura");


		this.setField(this.cognome);
		this.setField(this.nome);
		this.setField(this.username);
		this.setField(this.ruolo);
		this.setField(this.profilo);

		this.setField(this.utenteEsterno);
		this.setField(this.sistemaEsterno);
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

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");

		this.fieldsDatiGenerali.addField(this.cognome);
		this.fieldsDatiGenerali.addField(this.nome);
		this.fieldsDatiGenerali.addField(this.username);
		this.fieldsDatiGenerali.addField(this.ruolo);
		this.fieldsDatiGenerali.addField(this.profilo);
//		this.fieldsDatiGenerali.addField(this.ente);

		this.fieldsDatiPCC = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiPCC",2);
		this.fieldsDatiPCC.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiPCC.setColumnClasses("labelAllineataDx,valueAllineataSx");

		this.fieldsDatiPCC.addField(this.utenteEsterno);
		this.fieldsDatiPCC.addField(this.sistemaEsterno);
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

		this.metadataDipartimento = new DipartimentoBean();
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(Utente dto) {
		super.setDTO(dto);

		this.cognome.setValue(this.getDTO().getCognome());
		this.nome.setValue(this.getDTO().getNome());
//		this.ente.setValue(this.getDTO().getEnte().getNome());
		this.username.setValue(this.getDTO().getUsername());
		this.password.setValue(this.getDTO().getPassword());

		UserRole role = this.getDTO().getRole();

		if(role!= null){
			this.ruolo.setValue(role.getValue());
		}else 
			this.ruolo.setValue(null);

		// profilo
		UserType tipo = this.getDTO().getTipo();

		if(tipo!= null){
			if(tipo.equals(UserType.ESTERNO))
				this.profilo.setValue(("utente.form.profilo.esterno"));
			else 
				this.profilo.setValue(("utente.form.profilo.interno"));
		}else 
			this.profilo.setValue(("utente.form.profilo.interno"));

		boolean esterno = this.getDTO().isEsterno();
		this.utenteEsterno.setValue(Utils.getBooleanAsLabel(esterno,"commons.label.si", "commons.label.no"));
		this.sistemaEsterno.setValue(this.getDTO().getSistema()); 
		if(esterno)
			this.sistemaEsterno.setRendered(true);
		else 
			this.sistemaEsterno.setRendered(false);
		
	}

	public void setProprietaPCC(List<PccOperazione> listaProprietaConsentiteAiDipartimenti, List<PccUtenteOperazione> listaProprietaAbilitate){

		this.listaProprietaAbilitate = listaProprietaAbilitate;
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.PAGAMENTO_IVA, this.pagamentoIVA);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.DATI_FATTURA, this.datiFattura);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.INSERIMENTO_FATTURA, this.inserimentoFattura);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.STATO_FATTURA, this.statoFattura);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.ELENCO_MOVIMENTI_ERARIO_IVA , this.movimentiErarioIVA);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.DOWNLOAD_DOCUMENTO, this.downloadDocumento);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.CONSULTAZIONE_TRACCE,this.consultazioneTracce);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate,NomePccOperazioneType.OPERAZIONE_CONTABILE_CP, this.pagamento);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate,NomePccOperazioneType.OPERAZIONE_CONTABILE_SP, this.stornoPagamento);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate,NomePccOperazioneType.OPERAZIONE_CONTABILE_CS, this.comunicazioneScadenza);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate,NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS, this.cancellazioneScadenze);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate,NomePccOperazioneType.OPERAZIONE_CONTABILE_CO, this.contabilizzazione);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate, NomePccOperazioneType.OPERAZIONE_CONTABILE_SC, this.stornoContabilizzazione);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate,NomePccOperazioneType.OPERAZIONE_CONTABILE_RC, this.ricezioneFattura);
		Utils.impostaValoreProprietaPCCUtente(listaProprietaConsentiteAiDipartimenti, this.listaProprietaAbilitate,NomePccOperazioneType.OPERAZIONE_CONTABILE_RF, this.rifiutoFattura);
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


	public Text getNome() {
		return this.nome;
	}


	public void setNome(Text nome) {
		this.nome = nome;
	}


	public Text getCognome() {
		return this.cognome;
	}


	public void setCognome(Text cognome) {
		this.cognome = cognome;
	}


//	public Text getEnte() {
//		return this.ente;
//	}
//	public void setEnte(Text ente) {
//		this.ente = ente;
//	}

	public OutputGroup getFieldsDatiGenerali() {
		return this.fieldsDatiGenerali;
	}


	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}


	public List<DipartimentoBean> getListaDipartimenti() {
		return this.listaDipartimenti;
	}


	public void setListaDipartimenti(List<DipartimentoBean> listaDipartimenti) {
		this.listaDipartimenti = listaDipartimenti;
	}


	public DipartimentoBean getMetadataDipartimento() {
		return this.metadataDipartimento;
	}


	public void setMetadataDipartimento(DipartimentoBean metadataDipartimento) {
		this.metadataDipartimento = metadataDipartimento;
	}


	public Text getRuolo() {
		return this.ruolo;
	}


	public void setRuolo(Text ruolo) {
		this.ruolo = ruolo;
	}


	public Text getProfilo() {
		return profilo;
	}


	public void setProfilo(Text profilo) {
		this.profilo = profilo;
	}

	public Text getUtenteEsterno() {
		return utenteEsterno;
	}

	public void setUtenteEsterno(Text utenteEsterno) {
		this.utenteEsterno = utenteEsterno;
	}

	public Text getSistemaEsterno() {
		return sistemaEsterno;
	}

	public void setSistemaEsterno(Text sistemaEsterno) {
		this.sistemaEsterno = sistemaEsterno;
	}

	public Text getPagamentoIVA() {
		return pagamentoIVA;
	}

	public void setPagamentoIVA(Text pagamentoIVA) {
		this.pagamentoIVA = pagamentoIVA;
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

	public List<PccUtenteOperazione> getListaProprietaAbilitate() {
		return listaProprietaAbilitate;
	}

	public void setListaProprietaAbilitate(List<PccUtenteOperazione> listaProprietaAbilitate) {
		this.listaProprietaAbilitate = listaProprietaAbilitate;
	}

//	public EnteBean getEnteBean() {
//		return enteBean;
//	}
//	public void setEnteBean(EnteBean enteBean) {
//		this.enteBean = enteBean;
//
//		// prendere la info dall'ente
//		this.showPCC = false;
//	}

	public boolean isShowPCC() {
		return showPCC;
	}

	public void setShowPCC(boolean showPCC) {
		this.showPCC = showPCC;
	}



}
