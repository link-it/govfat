package org.govmix.proxy.fatturapa.web.console.form;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.mbean.FatturaElettronicaAttivaMBean;
import org.govmix.proxy.fatturapa.web.console.mbean.FileUploadBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.BooleanCheckBox;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class FatturaForm extends BaseForm implements Form {

	private SelectList<SelectItem> dipartimento = null;

	// BEAN per gestire upload file descrittoreServizio
	private Text descrittoreFattura = null;
	private FileUploadBean fatturaFile= null;
//	private BooleanCheckBox firmato = null;
//	private BooleanCheckBox fascicoloEsistente = null;
//	private SelectList<SelectItem> fascicolo = null;
	
	private BooleanCheckBox conservazione = null;
	
//	private boolean mostraConferma = false;
	private boolean mostraFormCorservazione = false;

	private FatturaElettronicaAttivaMBean mBean =null; 

	public FatturaForm() {
		try{
			this.init();
		}catch(Exception e){

		}
	}

	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		// Properties del form
		this.setId("formCaricaFattura"); 
		this.setClosable(false);
		this.setNomeForm("");

		this.dipartimento = factory.getInputFieldFactory().createSelectList("cf_dipartimento","fattura.form.dipartimento",new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*",  ("commons.label.qualsiasi")),true);
		((SelectListImpl)this.dipartimento).setCheckItemWidth(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		this.dipartimento.setFieldsToUpdate(this.getId() + "_formPnl"); 
		this.dipartimento.setForm(this); 
		
		this.descrittoreFattura = factory.getInputFieldFactory().createText("descrittoreFattura","fattura.form.descrittoreFattura",null,true);
		
//		this.firmato = factory.getInputFieldFactory().createBooleanCheckBox("firmato","fattura.form.firmato",null,false);
//		this.firmato.setFieldsToUpdate(this.getId() + "_formPnl"); 
//		this.firmato.setForm(this); 
//		
//		this.fascicoloEsistente = factory.getInputFieldFactory().createBooleanCheckBox("fascicoloEsistente","fattura.form.fascicoloEsistente",null,false);
//		this.fascicoloEsistente.setFieldsToUpdate(this.getId() + "_formPnl"); 
//		this.fascicoloEsistente.setForm(this); 
//		
//		this.fascicolo = factory.getInputFieldFactory().createSelectList("fascicolo","fattura.form.fascicolo",new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*",  ("commons.label.qualsiasi")),true);
//		((SelectListImpl)this.fascicolo).setCheckItemWidth(true); 
//		this.fascicolo.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		
		this.conservazione = factory.getInputFieldFactory().createBooleanCheckBox("conservazione","fattura.form.conservazione",null,false);
		this.conservazione.setFieldsToUpdate(this.getId() + "_btnPnl"); 
		this.conservazione.setForm(this);
		this.conservazione.setNote("Informativa che spiega il significato di questo flag.");
		
//		this._setFascioloEsistente();
//		this._setFirmato();
		
		this.setField(this.dipartimento);
		this.setField(this.descrittoreFattura);
//		this.setField(this.firmato);
//		this.setField(this.fascicoloEsistente);
//		this.setField(this.fascicolo);
		this.setField(this.conservazione);
		
		this._setMostraConservazione();
		
		this.fatturaFile = new FileUploadBean();
		this.fatturaFile.setNumeroFile(10);
		this.fatturaFile.setAcceptedTypes("xml,p7m"); 
		
		this.reset();
	}
	@Override
	public void reset() {
		this.dipartimento.reset();
		this.descrittoreFattura.reset();
//		this.firmato.reset();
//		this.fascicoloEsistente.reset();
//		this.fascicolo.reset();
		this.conservazione.reset();
		
//		this.mostraConferma = false;
		this.mostraFormCorservazione= false;
		
//		this._setFascioloEsistente();
//		this._setFirmato();

		this._setMostraConservazione();
	}
	
	public void setValues(Object object ){
		this.fatturaFile.setDto(true, null, null); 
		this._setMostraConservazione();
//		this._setFascioloEsistente();
//		this._setFirmato();
	}
	
	public SelectList<SelectItem> getDipartimento() {
		return dipartimento;
	}
	public void setDipartimento(SelectList<SelectItem> dipartimento) {
		this.dipartimento = dipartimento;
	}
	public Text getDescrittoreFattura() {
		return descrittoreFattura;
	}
	public void setDescrittoreFattura(Text descrittoreFattura) {
		this.descrittoreFattura = descrittoreFattura;
	}

	public FileUploadBean getFatturaFile() {
		return fatturaFile;
	}
	public void setFatturaFile(FileUploadBean fatturaFile) {
		this.fatturaFile = fatturaFile;
	}
	public FatturaElettronicaAttivaMBean getmBean() {
		return mBean;
	}
	public void setmBean(FatturaElettronicaAttivaMBean mBean) {
		this.mBean = mBean;
	}

//	public BooleanCheckBox getFirmato() {
//		return firmato;
//	}
//
//	public void setFirmato(BooleanCheckBox firmato) {
//		this.firmato = firmato;
//	}
//
//	public BooleanCheckBox getFascicoloEsistente() {
//		return fascicoloEsistente;
//	}
//
//	public void setFascicoloEsistente(BooleanCheckBox fascicoloEsistente) {
//		this.fascicoloEsistente = fascicoloEsistente;
//	}
//
//	public SelectList<SelectItem> getFascicolo() {
//		this.fascicolo.setRendered(false);
//
//		boolean mod = this.fascicoloEsistente.getValue() != null ? (this.fascicoloEsistente.getValue() ? true : false) : false;
//
//		if(mod)
//			this.fascicolo.setRendered(true);
//
//		return this.fascicolo;
//		
//	}
//
//	public void setFascicolo(SelectList<SelectItem> fascicolo) {
//		this.fascicolo = fascicolo;
//	}
//	
//	public void fascicoloEsistenteOnChangeListener(ActionEvent ae){
//		this._setFascioloEsistente();
//	}
//	
//	public void firmatoOnChangeListener(ActionEvent ae){
//		this._setFirmato();
//	}
//	
//	private void _setFirmato() {
//		this.mostraConferma = ! (this.firmato.getValue() != null ? (this.firmato.getValue() ? true : false) : false);
//	}
//
//	private void _setFascioloEsistente() {
//		this.fascicolo.setRendered(false);
//
//		boolean mod = this.fascicoloEsistente.getValue() != null ? (this.fascicoloEsistente.getValue() ? true : false) : false;
//
//		if(mod){
//			this.fascicolo.setRendered(true);
//		}
//	}

//	public boolean isMostraConferma() {
//		return mostraConferma;
//	}
//
//	public void setMostraConferma(boolean mostraConferma) {
//		this.mostraConferma = mostraConferma;
//	}
	
	private void _setMostraConservazione() {
		this.mostraFormCorservazione = (this.conservazione.getValue() != null ? (this.conservazione.getValue() ? true : false) : false);
	}
	
	public void conservazioneOnChangeListener(ActionEvent ae){
		this._setMostraConservazione();
	}

	public BooleanCheckBox getConservazione() {
		return conservazione;
	}

	public void setConservazione(BooleanCheckBox conservazione) {
		this.conservazione = conservazione;
	}

	public boolean isMostraFormCorservazione() {
		return mostraFormCorservazione;
	}

	public void setMostraFormCorservazione(boolean mostraFormCorservazione) {
		this.mostraFormCorservazione = mostraFormCorservazione;
	}
	
	public void cf_dipartimentoSelectListener(ActionEvent ae){
		 
	}
}
