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
package org.govmix.proxy.fatturapa.web.console.search;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.web.console.costanti.Costanti;
import org.govmix.proxy.fatturapa.web.console.mbean.ConservazioneMBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.SelectList;

/**
 * ConservazioneSearchForm Form di ricerca delle Fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class ConservazioneSearchForm extends BaseSearchForm implements SearchForm, Cloneable{

	private SelectList<SelectItem> anno = null;
	private SelectList<SelectItem> tipoFattura = null;
	private SelectList<SelectItem> ente = null;
	private SelectList<SelectItem> statoInvio = null;
	private ConservazioneMBean mBean = null;
	
	public ConservazioneSearchForm()throws Exception{
		this.init();
	}
	
	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		// Properties del form
		this.setId("formFattura");
		this.setNomeForm("conservazione.label.ricercaConservazione");
		this.setClosable(false);
		
		// anno
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date());
		String valAnno = "" + instance.get(Calendar.YEAR);
		this.anno = factory.getInputFieldFactory().createSelectList("anno","conservazione.search.anno",new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem(valAnno,valAnno),false);
		((SelectListImpl)this.anno).setCheckItemWidth(true); 
		this.anno.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		
		// tipoFattura
		this.tipoFattura = factory.getInputFieldFactory().createSelectList("tipoFattura","conservazione.search.tipoFattura",new SelectItem(Costanti.TIPO_FATTURA_ATTIVA_VALUE, ("conservazione.search.tipoFattura.attiva")),false);
		((SelectListImpl)this.tipoFattura).setCheckItemWidth(true); 
		this.tipoFattura.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		this.tipoFattura.setFieldsToUpdate(this.getId () + "_searchPnl");
		this.tipoFattura.setForm(this);
		
		// ente
		this.ente = factory.getInputFieldFactory().createSelectList("ente","conservazione.search.ente",null,false);
		((SelectListImpl)this.ente).setCheckItemWidth(true); 
		this.ente.setFontName("Arial"); //"Arial,Verdana,sans-serif" 
		
		// statoInvio
		this.statoInvio = factory.getInputFieldFactory().createSelectList("statoInvio","conservazione.search.statoInvio",new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*", ("commons.label.qualsiasi")),false);
		((SelectListImpl)this.statoInvio).setCheckItemWidth(true); 
		this.statoInvio.setFontName("Arial"); //"Arial,Verdana,sans-serif" 

		this.setField(this.anno);
		this.setField(this.tipoFattura);
		this.setField(this.ente);
		this.setField(this.statoInvio);
		
		this.reset();
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();

		// reset search fields
		this.anno.reset();
		this.tipoFattura.reset();
		this.ente.reset();
		this.statoInvio.reset();

	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public SelectList<SelectItem> getAnno() {
		return anno;
	}

	public void setAnno(SelectList<SelectItem> anno) {
		this.anno = anno;
	}
	
	public void setDefaultAnno(SelectItem anno) {
		this.anno.setDefaultValue(anno);
		this.anno.reset();
	}
	
	public void setDefaultEnte(SelectItem ente) {
		this.ente.setDefaultValue(ente);
		this.ente.reset();
	}

	public SelectList<SelectItem> getTipoFattura() {
		return tipoFattura;
	}

	public void setTipoFattura(SelectList<SelectItem> tipoFattura) {
		this.tipoFattura = tipoFattura;
	}

	public SelectList<SelectItem> getEnte() {
		return ente;
	}

	public void setEnte(SelectList<SelectItem> ente) {
		this.ente = ente;
	}

	public SelectList<SelectItem> getStatoInvio() {
		return statoInvio;
	}

	public void setStatoInvio(SelectList<SelectItem> statoInvio) {
		this.statoInvio = statoInvio;
	}

	public ConservazioneMBean getmBean() {
		return mBean;
	}

	public void setmBean(ConservazioneMBean mBean) {
		this.mBean = mBean;
	}
 
	public void tipoFatturaSelectListener(ActionEvent ae){
		boolean fatturazioneAttiva = this.isRicercaFattureAttive();
		
		List<javax.faces.model.SelectItem> listaEnti = this.mBean._getEnti(false, fatturazioneAttiva);
		if(!listaEnti.isEmpty()) {
			this.setDefaultEnte((org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem) listaEnti.get(0).getValue());
			this.getEnte().setValue((org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem) listaEnti.get(0).getValue()); 
		}
	}
	
	public boolean isRicercaFattureAttive() {
		boolean fatturazioneAttiva = false;
		org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem tipoFatturaSelezionata = 
				((SelectListImpl)this.getTipoFattura()).getValue();
		if(tipoFatturaSelezionata != null) {
			fatturazioneAttiva = Costanti.TIPO_FATTURA_ATTIVA_VALUE.equals(tipoFatturaSelezionata.getValue());
		}
		
		return fatturazioneAttiva;
	}

	public void setRicercaFattureAttive(boolean ricercaFattureAttive) {
	}
	
	public boolean isVisualizzaTastiInvioConservazione() {
		if(this.getStatoInvio().getValue() != null &&
				!StringUtils.isEmpty(this.getStatoInvio().getValue().getValue()) && !this.getStatoInvio().getValue().getValue().equals("*")){
			StatoConservazioneType statoConservazioneType = StatoConservazioneType.toEnumConstant(this.getStatoInvio().getValue().getValue());
			
			switch (statoConservazioneType) {
			case CONSERVAZIONE_COMPLETATA:
			case PRESA_IN_CARICO:
			case IN_RICONSEGNA:
				return false;
				
			case CONSERVAZIONE_FALLITA:
			case ERRORE_CONSEGNA:
			case NON_INVIATA:
				return true;
			}
		}
		
		return false;
	}

	public void setVisualizzaTastiInvioConservazione(boolean visualizzaTastiInvioConservazione) {
	}
}

