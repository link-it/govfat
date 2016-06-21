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
package org.govmix.proxy.fatturapa.web.console.anagrafica.form;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.web.console.anagrafica.mbean.DipartimentoMBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;

public class DipartimentoSearchForm extends BaseSearchForm implements SearchForm{

	private Text descrizione = null;

	private Text codice = null;
	
	private SelectList<SelectItem> ente = null;

	private DipartimentoMBean mBean = null;
	
	private List<UtenteDipartimento> listaUtenteDipartimenti= null;

	public DipartimentoSearchForm(){
		this.init();
	}

	@Override
	public void init() {
		this.setId("fDips");
		this.setNomeForm(Utils.getInstance().getMessageFromResourceBundle("dipartimento.label.ricercaDipartimenti"));
		this.setClosable(false);

		try{
			WebGenericProjectFactory factory =  this.getWebGenericProjectFactory();

			// Init dei FormField
			this.descrizione = factory.getInputFieldFactory().createText("descrizione","dipartimento.search.descrizione",null,false);

			this.descrizione.setAutoComplete(true);
			this.descrizione.setEnableManualInput(true);
			this.descrizione.setFieldsToUpdate(this.getId() + "_searchPnl");
			this.descrizione.setForm(this);

			this.codice = factory.getInputFieldFactory().createText("codice","dipartimento.search.codice",null,false);

			this.codice.setAutoComplete(true);
			this.codice.setEnableManualInput(true);
			this.codice.setFieldsToUpdate(this.getId() + "_searchPnl");
			this.codice.setForm(this);
			
			this.ente = factory.getInputFieldFactory().createSelectList("ente", "dipartimento.search.ente", null, false);
			((SelectListImpl)this.ente).setCheckItemWidth(true); 
			this.ente.setFontName("Arial");
			
			this.setField(this.descrizione);
			this.setField(this.codice);
			this.setField(this.ente);
			
		}catch(FactoryException e){

		}
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.descrizione.reset();
		this.codice.reset();
		this.ente.reset();
	}

	public Text getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public DipartimentoMBean getmBean() {
		return this.mBean;
	}

	public void setmBean(DipartimentoMBean mBean) {
		this.mBean = mBean;
	}

	public List<SelectItem> descrizioneAutoComplete(Object val){
		SelectItem enteSI = this.ente.getValue();
		String _ente  = null;
		if(enteSI!= null){
			_ente = enteSI.getValue();
		}
		
		return this.mBean.descrizioneAutoComplete(val,_ente);
	}

	public List<SelectItem> codiceAutoComplete(Object val){
		SelectItem enteSI = this.ente.getValue();
		String _ente  = null;
		if(enteSI!= null){
			_ente = enteSI.getValue();
		}
		return this.mBean.codiceAutoComplete(val,_ente);
	}


	public Text getCodice() {
		return this.codice;
	}

	public void setCodice(Text codice) {
		this.codice = codice;
	}

	public void descrizioneSelectListener(ActionEvent ae){
		//do something
	}

	public void codiceSelectListener(ActionEvent ae){
		//do something
	}

	public SelectList<SelectItem> getEnte() {
		return ente;
	}

	public void setEnte(SelectList<SelectItem> ente) {
		this.ente = ente;
	}

	public List<UtenteDipartimento> getListaUtenteDipartimenti() {
		return listaUtenteDipartimenti;
	}

	public void setListaUtenteDipartimenti(List<UtenteDipartimento> listaUtenteDipartimenti) {
		this.listaUtenteDipartimenti = listaUtenteDipartimenti;
	}
	
	public List<Long> getListaIdDipartimentiUtente(){
		if(this.listaUtenteDipartimenti != null && this.listaUtenteDipartimenti.size() > 0){
			List<Long> listaId = new ArrayList<Long>();
			
			for (UtenteDipartimento idUD : this.listaUtenteDipartimenti) {
				listaId.add(idUD.getIdDipartimento().getId());
			}
			
			return listaId;
		}
		return null;
	}
}
