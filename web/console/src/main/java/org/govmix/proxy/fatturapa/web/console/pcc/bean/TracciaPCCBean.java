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
package org.govmix.proxy.fatturapa.web.console.pcc.bean;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.orm.constants.TipoOperazionePccType;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.OutputNumber;
import org.openspcoop2.generic_project.web.output.Text;

public class TracciaPCCBean extends BaseBean<PccTraccia, Long> implements IBean<PccTraccia, Long>{


	private DateTime dataCreazione;
	private DateTime dataUltimaTrasmissione;
	private Text cfTrasmittente;
	private Text versioneApplicativa;
	private Text idPccAmministrazione;
	private Text idPaTransazione;
	private Text sistemaRichiedente;
	private Text utenteRichiedente;
	private Text operazione;
	private Text tipoOperazione;
	private Text stato;
	private DateTime dataUltimoTentativoEsito;
	private Text rispedizione;
	private OutputNumber rispedizioneMaxTentativi;
	private DateTime rispedizioneProssimoTentativo;
	private OutputNumber rispedizioneNumeroTentativi;
	private DateTime rispedizioneUltimoTentativo;
	private Text rifFattura;
	private FatturaElettronicaBean fattura = null;

	private List<TracciaTrasmissionePCCBean> listaTrasmissioni = null;

	private Button richiesta = null;
	private Button risposta = null;
	private Button pdfRiallineamento = null;
	
	private SimpleDateFormat sdf= null;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	public TracciaPCCBean(){
		try{
			this.init();
			this.sdf = new  SimpleDateFormat(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_YYYY_M_MDD_H_HMMSS);
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.listaTrasmissioni = new ArrayList<TracciaTrasmissionePCCBean>();

		this.rifFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("rifFattura","tracciaPcc.rifFattura");
		this.cfTrasmittente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cfTrasmittente","tracciaPcc.cfTrasmittente");
		this.versioneApplicativa = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("versioneApplicativa","tracciaPcc.versioneApplicativa");
		this.idPccAmministrazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idPccAmministrazione","tracciaPcc.idPccAmministrazione");
		this.idPaTransazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idPaTransazione","tracciaPcc.idPaTransazione");
		this.sistemaRichiedente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("sistemaRichiedente","tracciaPcc.sistemaRichiedente");
		this.utenteRichiedente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("utenteRichiedente","tracciaPcc.utenteRichiedente");
		this.operazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("operazione","tracciaPcc.operazione");
		this.tipoOperazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("tipoOperazione","tracciaPcc.tipoOperazione");
		this.stato = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("stato","tracciaPcc.stato");
		this.dataUltimoTentativoEsito = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataUltimoTentativoEsito","tracciaPcc.dataUltimoTentativoEsito",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.rispedizione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("rispedizione","tracciaPcc.rispedizione");
		this.rispedizioneMaxTentativi = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("rispedizioneMaxTentativi","tracciaPcc.rispedizioneMaxTentativi");
		this.rispedizioneMaxTentativi.setConverterType(Costanti.CONVERT_TYPE_NUMBER);
		this.rispedizioneMaxTentativi.setTableColumnStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_ALLINATO_DX);
		this.dataUltimaTrasmissione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataUltimaTrasmissione","tracciaPcc.dataUltimaTrasmissione",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.dataCreazione = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataCreazione","tracciaPcc.dataCreazione",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);


		this.rispedizioneProssimoTentativo = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("rispedizioneProssimoTentativo","tracciaPcc.rispedizioneProssimoTentativo",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.rispedizioneNumeroTentativi = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("rispedizioneNumeroTentativi","tracciaPcc.rispedizioneNumeroTentativi");
		this.rispedizioneNumeroTentativi.setConverterType(Costanti.CONVERT_TYPE_NUMBER);
		this.rispedizioneNumeroTentativi.setTableColumnStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_ALLINATO_DX);
		this.rispedizioneUltimoTentativo = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("rispedizioneUltimoTentativo","tracciaPcc.rispedizioneUltimoTentativo",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);

		this.richiesta = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("tracciaRichiesta","tracciaPcc.tracciaRichiesta",null,
				org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_XML,"tracciaPcc.tracciaRichiesta.iconTitle","tracciaPcc.tracciaRichiesta.iconTitle");
		this.risposta = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("tracciaRisposta","tracciaPcc.tracciaRisposta",null,
				org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_XML,"tracciaPcc.tracciaRisposta.iconTitle","tracciaPcc.tracciaRisposta.iconTitle");
		
		this.pdfRiallineamento = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("pdfRiallineamento","tracciaPcc.pdfRiallineamento",null,
				org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_PDF,"tracciaPcc.pdfRiallineamento.iconTitle","tracciaPcc.pdfRiallineamento.iconTitle");


		this.setField(this.cfTrasmittente);
		this.setField(this.rifFattura);
		this.setField(this.versioneApplicativa);
		this.setField(this.idPccAmministrazione);
		this.setField(this.idPaTransazione);
		this.setField(this.sistemaRichiedente);
		this.setField(this.utenteRichiedente);
		this.setField(this.operazione);
		this.setField(this.tipoOperazione);
		this.setField(this.stato);
		this.setField(this.dataUltimoTentativoEsito);
		this.setField(this.dataUltimaTrasmissione);
		this.setField(this.dataCreazione);
		this.setField(this.rispedizione);
		this.setField(this.rispedizioneMaxTentativi);
		this.setField(this.rispedizioneProssimoTentativo);
		this.setField(this.rispedizioneNumeroTentativi);
		this.setField(this.rispedizioneUltimoTentativo);
		this.setField(this.richiesta);
		this.setField(this.risposta);
		this.setField(this.pdfRiallineamento);

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",3);

		this.fieldsDatiGenerali.addField(this.dataCreazione);
		this.fieldsDatiGenerali.addField(this.dataUltimaTrasmissione);
		this.fieldsDatiGenerali.addField(this.cfTrasmittente);
		this.fieldsDatiGenerali.addField(this.versioneApplicativa);
		this.fieldsDatiGenerali.addField(this.idPccAmministrazione);
		this.fieldsDatiGenerali.addField(this.idPaTransazione);
		this.fieldsDatiGenerali.addField(this.sistemaRichiedente);
		this.fieldsDatiGenerali.addField(this.utenteRichiedente);
		this.fieldsDatiGenerali.addField(this.operazione);
		this.fieldsDatiGenerali.addField(this.tipoOperazione);
		this.fieldsDatiGenerali.addField(this.stato);
		//		this.fieldsDatiGenerali.addField(this.rifFattura);
		this.fieldsDatiGenerali.addField(this.dataUltimoTentativoEsito);
		this.fieldsDatiGenerali.addField(this.rispedizione);
		//		this.fieldsDatiGenerali.addField(this.rispedizioneMaxTentativi);
		this.fieldsDatiGenerali.addField(this.rispedizioneProssimoTentativo);
		//		this.fieldsDatiGenerali.addField(this.rispedizioneNumeroTentativi);
		//		this.fieldsDatiGenerali.addField(this.rispedizioneUltimoTentativo);

		this.fieldsDatiGenerali.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiGenerali.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_TRE_COLONNE); 
	}

	@Override
	public Long getId() {
		return this.getDTO().getId();
	}

	@Override
	public void setDTO(PccTraccia dto) {
		super.setDTO(dto);

		this.cfTrasmittente.setValue(this.getDTO().getCfTrasmittente());
		this.versioneApplicativa.setValue(this.getDTO().getVersioneApplicativa());
		this.idPccAmministrazione.setValue(this.getDTO().getIdPccAmministrazione() + "");
		this.idPaTransazione.setValue(this.getDTO().getIdPaTransazione());
		this.sistemaRichiedente.setValue(this.getDTO().getSistemaRichiedente());
		this.utenteRichiedente.setValue(this.getDTO().getUtenteRichiedente());
		this.operazione.setValue(this.getDTO().getOperazione());
		TipoOperazionePccType tipoOperazione2 = this.getDTO().getTipoOperazione();
		if(tipoOperazione2 != null){
			String val = "pccTipoOperazione." + tipoOperazione2.getValue();
			this.tipoOperazione.setValue(val);
		}
		StatoType stato2 = this.getDTO().getStato();
		if(stato2 != null ){
			String val = "pccStato." + stato2.getValue();
			this.stato.setValue(val);
		}
		this.dataUltimoTentativoEsito.setValue(this.getDTO().getDataUltimoTentativoEsito());
		this.dataUltimaTrasmissione.setValue(this.getDTO().getDataUltimaTrasmissione());
		this.dataCreazione.setValue(this.getDTO().getDataCreazione());
		
		boolean rispedizione2 = this.getDTO().getRispedizione();
		String val = Utils.getBooleanAsLabel(rispedizione2, "commons.label.abilitata", "commons.label.nonAbilitata");
		this.rispedizione.setValue(val);
		this.rispedizioneProssimoTentativo.setValue(this.getDTO().getRispedizioneProssimoTentativo());
		if(rispedizione2)
			this.rispedizioneProssimoTentativo.setRendered(false); 

		this.rispedizioneMaxTentativi.setValue(this.getDTO().getRispedizioneMaxTentativi());

		this.rispedizioneNumeroTentativi.setValue(this.getDTO().getRispedizioneNumeroTentativi());
		this.rispedizioneUltimoTentativo.setValue(this.getDTO().getRispedizioneUltimoTentativo());

		this.listaTrasmissioni = new ArrayList<TracciaTrasmissionePCCBean>();
		if(this.getDTO().getPccTracciaTrasmissioneList() != null && this.getDTO().getPccTracciaTrasmissioneList().size() > 0){
			for (PccTracciaTrasmissione trasmissione : this.getDTO().getPccTracciaTrasmissioneList()) {
				TracciaTrasmissionePCCBean trasmissioneBean = new TracciaTrasmissionePCCBean();
				trasmissioneBean.setDTO(trasmissione);
				this.listaTrasmissioni.add(trasmissioneBean);
			}
		}

		if(this.getDTO().getFatturaElettronica() != null){
			this.fattura = new FatturaElettronicaBean();
			this.fattura.setDTO(this.getDTO().getFatturaElettronica()); 
			String value = null;

			if(this.fattura != null){
				value = this.fattura.getNumero().getValue() + "/" + this.fattura.getAnno().getValue();
			}

			this.rifFattura.setValue(value);
		}

		
		this.richiesta.setHref(this.getDTO().getRichiestaXml() != null ?  "downloadOk" : null);
		this.risposta.setHref(this.getDTO().getRispostaXml() != null ?  "downloadOk" : null);
		
		this.pdfRiallineamento.setHref(null);
		if(NomePccOperazioneType.DATI_FATTURA.getValue().equals(this.getDTO().getOperazione()) && this.getDTO().getRispostaXml() != null){
			FacesContext context = FacesContext.getCurrentInstance();

			String url = context.getExternalContext().getRequestContextPath() 
					+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
					+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
					+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_PDF
					+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_PCC_RIALLINEAMENTO;
			this.pdfRiallineamento.setHref(url);
		}  
			
	}
	
	public String visualizzaTracciaRichiesta()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)fc.getExternalContext().getResponse();
		response.reset();
		response.setContentType("text/xml");
		String filename = (new StringBuilder()).append("traccia_richiesta_").append(this.sdf.format(this.getDTO().getDataUltimaTrasmissione())).append(".xml").toString(); 
		response.setHeader("Content-Disposition", (new StringBuilder()).append("attachment; filename=\"").append(filename).append("\"").toString());
		response.addHeader("Cache-Control", "no-cache");
		response.setStatus(200);
		response.setBufferSize(1024);
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(this.getDTO().getRichiestaXml());
			Utils.copy(bais, response.getOutputStream());
			response.flushBuffer();
		}
		catch(Exception e1)
		{
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("tracciaPcc.tracciaRichiesta.erroreDownload"));
		}
		fc.responseComplete();
		return null;
	}

	public String visualizzaTracciaRisposta()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)fc.getExternalContext().getResponse();
		response.reset();
		response.setContentType("text/xml");
		String filename = (new StringBuilder()).append("traccia_risposta_").append(this.sdf.format(this.getDTO().getDataUltimaTrasmissione())).append(".xml").toString();
		response.setHeader("Content-Disposition", (new StringBuilder()).append("attachment; filename=\"").append(filename).append("\"").toString());
		response.addHeader("Cache-Control", "no-cache");
		response.setStatus(200);
		response.setBufferSize(1024);
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(this.getDTO().getRispostaXml());
			Utils.copy(bais, response.getOutputStream());
			response.flushBuffer();
		}
		catch(Exception e1)
		{
			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("tracciaPcc.tracciaRisposta.erroreDownload"));
		}
		fc.responseComplete();
		return null;
	}


	public Text getCfTrasmittente() {
		return cfTrasmittente;
	}

	public void setCfTrasmittente(Text cfTrasmittente) {
		this.cfTrasmittente = cfTrasmittente;
	}

	public Text getVersioneApplicativa() {
		return versioneApplicativa;
	}

	public void setVersioneApplicativa(Text versioneApplicativa) {
		this.versioneApplicativa = versioneApplicativa;
	}

	public Text getIdPccAmministrazione() {
		return idPccAmministrazione;
	}

	public void setIdPccAmministrazione(Text idPccAmministrazione) {
		this.idPccAmministrazione = idPccAmministrazione;
	}

	public Text getIdPaTransazione() {
		return idPaTransazione;
	}

	public void setIdPaTransazione(Text idPaTransazione) {
		this.idPaTransazione = idPaTransazione;
	}

	public Text getSistemaRichiedente() {
		return sistemaRichiedente;
	}

	public void setSistemaRichiedente(Text sistemaRichiedente) {
		this.sistemaRichiedente = sistemaRichiedente;
	}

	public Text getUtenteRichiedente() {
		return utenteRichiedente;
	}

	public void setUtenteRichiedente(Text utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}

	public Text getOperazione() {
		return operazione;
	}

	public void setOperazione(Text operazione) {
		this.operazione = operazione;
	}

	public Text getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(Text tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public Text getStato() {
		return stato;
	}

	public void setStato(Text stato) {
		this.stato = stato;
	}

	public DateTime getDataUltimoTentativoEsito() {
		return dataUltimoTentativoEsito;
	}

	public void setDataUltimoTentativoEsito(DateTime dataUltimoTentativoEsito) {
		this.dataUltimoTentativoEsito = dataUltimoTentativoEsito;
	}

	public Text getRispedizione() {
		return rispedizione;
	}

	public void setRispedizione(Text rispedizione) {
		this.rispedizione = rispedizione;
	}

	public OutputNumber getRispedizioneMaxTentativi() {
		return rispedizioneMaxTentativi;
	}

	public void setRispedizioneMaxTentativi(OutputNumber rispedizioneMaxTentativi) {
		this.rispedizioneMaxTentativi = rispedizioneMaxTentativi;
	}

	public DateTime getRispedizioneProssimoTentativo() {
		return rispedizioneProssimoTentativo;
	}

	public void setRispedizioneProssimoTentativo(DateTime rispedizioneProssimoTentativo) {
		this.rispedizioneProssimoTentativo = rispedizioneProssimoTentativo;
	}

	public OutputNumber getRispedizioneNumeroTentativi() {
		return rispedizioneNumeroTentativi;
	}

	public void setRispedizioneNumeroTentativi(OutputNumber rispedizioneNumeroTentativi) {
		this.rispedizioneNumeroTentativi = rispedizioneNumeroTentativi;
	}

	public DateTime getRispedizioneUltimoTentativo() {
		return rispedizioneUltimoTentativo;
	}

	public void setRispedizioneUltimoTentativo(DateTime rispedizioneUltimoTentativo) {
		this.rispedizioneUltimoTentativo = rispedizioneUltimoTentativo;
	}

	public List<TracciaTrasmissionePCCBean> getListaTrasmissioni() {
		return listaTrasmissioni;
	}

	public void setListaTrasmissioni(List<TracciaTrasmissionePCCBean> listaTrasmissioni) {
		this.listaTrasmissioni = listaTrasmissioni;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}

	public Text getRifFattura() {
		return rifFattura;
	}

	public void setRifFattura(Text rifFattura) {
		this.rifFattura = rifFattura;
	}

	public FatturaElettronicaBean getFattura() {
		return fattura;
	}

	public void setFattura(FatturaElettronicaBean fattura) {
		this.fattura = fattura;
	}

	public DateTime getDataUltimaTrasmissione() {
		return dataUltimaTrasmissione;
	}

	public void setDataUltimaTrasmissione(DateTime dataUltimaTrasmissione) {
		this.dataUltimaTrasmissione = dataUltimaTrasmissione;
	}

	public boolean isvisualizzaLinkRispedizione(){
		if(this.getDTO() != null && this.getDTO().getStato() != null){
			boolean isAdmin = true; // Utils.getLoginBean().isAdmin();
			StatoType statoType =  this.getDTO().getStato();

			if((statoType.equals(StatoType.AS_ERRORE) || (statoType.equals(StatoType.AS_ERRORE_PRESA_IN_CARICO))) && isAdmin)
				return true;
		}

		return false;
	}

	public Button getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(Button richiesta) {
		this.richiesta = richiesta;
	}

	public Button getRisposta() {
		return risposta;
	}

	public void setRisposta(Button risposta) {
		this.risposta = risposta;
	}

	public DateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(DateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Button getPdfRiallineamento() {
		return pdfRiallineamento;
	}

	public void setPdfRiallineamento(Button pdfRiallineamento) {
		this.pdfRiallineamento = pdfRiallineamento;
	}

	
}
