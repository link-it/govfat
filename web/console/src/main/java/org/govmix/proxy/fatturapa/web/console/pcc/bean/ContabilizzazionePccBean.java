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

import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.constants.CausaleType;
import org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoDebitoType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.OutputNumber;
import org.openspcoop2.generic_project.web.output.Text;

public class ContabilizzazionePccBean  extends BaseBean<PccContabilizzazione, Long> implements IBean<PccContabilizzazione, Long>{

	private OutputNumber importoMovimento;
	private Text naturaSpesa;
	private Text capitoliSpesa;
	private Text statoDebito;
	private Text causale;
	private Text descrizione;
	private Text estremiImpiego;
	private Text codiceCig;
	private Text codiceCup;
	private Text idImporto;
	private DateTime dataRichiesta;
	private EsitoPccBean esito;
	private DateTime dataQuery;
	private Text sistemaRichiedente;
	private Text utenteRichiedente;
	
	private boolean editabile = false;
	private boolean nonInviata = true;

	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;


	public ContabilizzazionePccBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.importoMovimento = this.getWebGenericProjectFactory().getOutputFieldFactory().createNumber("importoMovimento","contabilizzazione.importoMovimento");
		this.importoMovimento.setConverterType(Costanti.CONVERT_TYPE_CURRENCY);
		this.importoMovimento.setCurrencySymbol(Costanti.CURRENCY_SYMBOL_EURO);
		this.importoMovimento.setTableColumnStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_ALLINATO_DX);
		this.naturaSpesa = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("naturaSpesa","contabilizzazione.naturaSpesa");
		this.capitoliSpesa = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("capitoliSpesa","contabilizzazione.capitoliSpesa");
		this.statoDebito = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("statoDebito","contabilizzazione.statoDebito");
		this.causale = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("causale","contabilizzazione.causale");
		this.descrizione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("descrizione","contabilizzazione.descrizione");
		this.estremiImpiego = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("estremiImpiego","contabilizzazione.estremiImpiego");
		this.codiceCig = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceCig","contabilizzazione.codiceCig"); 
		this.codiceCup = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceCup","contabilizzazione.codiceCup");
		this.idImporto = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idImporto","contabilizzazione.idImporto");
//		this.stato = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("stato","contabilizzazione.stato");
		this.dataRichiesta = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataRichiesta","contabilizzazione.dataRichiesta",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS);
		this.dataQuery = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataQuery","contabilizzazione.dataQuery",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY_HH_MM_SS); 
		this.sistemaRichiedente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("sistemaRichiedente","contabilizzazione.sistemaRichiedente");
		this.utenteRichiedente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("utenteRichiedente","contabilizzazione.utenteRichiedente");
	
		
		this.setField(this.importoMovimento);
		this.setField(this.naturaSpesa);
		this.setField(this.capitoliSpesa);
		this.setField(this.statoDebito);
		this.setField(this.causale);
		this.setField(this.descrizione);
		this.setField(this.estremiImpiego);
		this.setField(this.codiceCig);
		this.setField(this.codiceCup);
		this.setField(this.idImporto);
//		this.setField(this.stato);
		this.setField(this.dataRichiesta);
		this.setField(this.dataQuery);
		this.setField(this.sistemaRichiedente);
		this.setField(this.utenteRichiedente);

		this.fieldsDatiGenerali = this.getWebGenericProjectFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.addField(this.importoMovimento);
		this.fieldsDatiGenerali.addField(this.naturaSpesa);
		this.fieldsDatiGenerali.addField(this.capitoliSpesa);
		this.fieldsDatiGenerali.addField(this.statoDebito);
		this.fieldsDatiGenerali.addField(this.causale);
		this.fieldsDatiGenerali.addField(this.descrizione);
		this.fieldsDatiGenerali.addField(this.estremiImpiego);
		this.fieldsDatiGenerali.addField(this.codiceCig);
		this.fieldsDatiGenerali.addField(this.codiceCup);
		this.fieldsDatiGenerali.addField(this.idImporto);
//		this.fieldsDatiGenerali.addField(this.stato);
		this.fieldsDatiGenerali.addField(this.dataRichiesta);
		this.fieldsDatiGenerali.addField(this.dataQuery);
		this.fieldsDatiGenerali.addField(this.sistemaRichiedente);
		this.fieldsDatiGenerali.addField(this.utenteRichiedente);

		this.fieldsDatiGenerali.setStyleClass(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_TRASMISSIONE_TABLE); 
		this.fieldsDatiGenerali.setColumnClasses(org.govmix.proxy.fatturapa.web.console.costanti.Costanti.CSS_CLASS_DATI_DETTAGLIO_DUE_COLONNE); 
	}

	@Override
	public Long getId() {
		return this.getDTO().getId();
	}

	@Override
	public void setDTO(PccContabilizzazione dto) {
		super.setDTO(dto);
		
		this.importoMovimento.setValue(this.getDTO().getImportoMovimento());
		NaturaSpesaType naturaSpesa2 = this.getDTO().getNaturaSpesa();
		if(naturaSpesa2 != null){
			String val = "pccNaturaSpesa." + naturaSpesa2.getValue();
			this.naturaSpesa.setValue(val);
		} else 
			this.naturaSpesa.setValue(null);
		
		this.capitoliSpesa.setValue(this.getDTO().getCapitoliSpesa());
		StatoDebitoType statoDebito2 = this.getDTO().getStatoDebito();
		if(statoDebito2 !=  null){
			String val = "pccStatoDebito." + statoDebito2.getValue() ;
			this.statoDebito.setValue(val);
		} else 
			this.statoDebito.setValue(null);
		
		CausaleType causale2 = this.getDTO().getCausale();
		if(causale2 !=  null){
			String val = "pccCausale." + causale2.getValue();
			this.causale.setValue(val);
		} else 
			this.causale.setValue(null);
			
		this.descrizione.setValue(this.getDTO().getDescrizione());
		this.estremiImpiego.setValue(this.getDTO().getEstremiImpegno());
		this.codiceCig.setValue(this.getDTO().getCodiceCig());
		this.codiceCup.setValue(this.getDTO().getCodiceCup());
		this.idImporto.setValue(this.getDTO().getIdImporto());
		this.nonInviata = true;
		this.dataRichiesta.setValue(this.getDTO().getDataRichiesta());
		this.dataQuery.setValue(this.getDTO().getDataQuery());
		this.sistemaRichiedente.setValue(this.getDTO().getSistemaRichiedente());
		this.utenteRichiedente.setValue(this.getDTO().getUtenteRichiedente());
		
		
		// la contabilizzazione si puo' modificare se ha come sistema richiedente quello impostato nelle properties
		// e se il suo stato non e' liquidato.
		this.editabile = false;
		try {
			if(this.getDTO().getSistemaRichiedente().equalsIgnoreCase(ConsoleProperties.getInstance(LoggerManager.getConsoleLogger()).getSistemaRichiedente())){
				this.editabile = true;
			}
			
			boolean isLiquidato = this.getDTO() != null && this.getDTO().getStatoDebito().equals(StatoDebitoType.LIQ);
			
			this.editabile = this.editabile && !isLiquidato;
		} catch (Exception e) {
			this.editabile = false;
		}

	}

	public OutputNumber getImportoMovimento() {
		return importoMovimento;
	}

	public void setImportoMovimento(OutputNumber importoMovimento) {
		this.importoMovimento = importoMovimento;
	}

	public Text getNaturaSpesa() {
		return naturaSpesa;
	}

	public void setNaturaSpesa(Text naturaSpesa) {
		this.naturaSpesa = naturaSpesa;
	}

	public Text getCapitoliSpesa() {
		return capitoliSpesa;
	}

	public void setCapitoliSpesa(Text capitoliSpesa) {
		this.capitoliSpesa = capitoliSpesa;
	}

	public Text getStatoDebito() {
		return statoDebito;
	}

	public void setStatoDebito(Text statoDebito) {
		this.statoDebito = statoDebito;
	}

	public Text getCausale() {
		return causale;
	}

	public void setCausale(Text causale) {
		this.causale = causale;
	}

	public Text getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getEstremiImpiego() {
		return estremiImpiego;
	}

	public void setEstremiImpiego(Text estremiImpiego) {
		this.estremiImpiego = estremiImpiego;
	}

	public Text getCodiceCig() {
		return codiceCig;
	}

	public void setCodiceCig(Text codiceCig) {
		this.codiceCig = codiceCig;
	}

	public Text getCodiceCup() {
		return codiceCup;
	}

	public void setCodiceCup(Text codiceCup) {
		this.codiceCup = codiceCup;
	}

	public Text getIdImporto() {
		return idImporto;
	}

	public void setIdImporto(Text idImporto) {
		this.idImporto = idImporto;
	}

//	public Text getStato() {
//		return stato;
//	}
//
//	public void setStato(Text stato) {
//		this.stato = stato;
//	}

	public DateTime getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(DateTime dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public EsitoPccBean getEsito() {
		return esito;
	}

	public void setEsito(EsitoPccBean esito) {
		this.esito = esito;
	}

	public DateTime getDataQuery() {
		return dataQuery;
	}

	public void setDataQuery(DateTime dataQuery) {
		this.dataQuery = dataQuery;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
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

	public boolean isEditabile() {
		return editabile;
	}

	public void setEditabile(boolean editabile) {
		this.editabile = editabile;
	}

	public boolean isNonInviata() {
		return nonInviata;
	}

	public void setNonInviata(boolean nonInviata) {
		this.nonInviata = nonInviata;
	}
	
	
}
