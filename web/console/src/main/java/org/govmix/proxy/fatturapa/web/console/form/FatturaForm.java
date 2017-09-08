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
package org.govmix.proxy.fatturapa.web.console.form;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.web.console.costanti.Costanti;
import org.govmix.proxy.fatturapa.web.console.mbean.FatturaElettronicaAttivaMBean;
import org.govmix.proxy.fatturapa.web.console.mbean.FileUploadBean;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.impl.SelectListImpl;
import org.openspcoop2.generic_project.web.input.BooleanCheckBox;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.Text;
import org.richfaces.model.UploadItem;

public class FatturaForm extends BaseForm implements Form {

	private SelectList dipartimento = null;

	// BEAN per gestire upload file descrittoreFattura
	private Text descrittoreFattura = null;
	private FileUploadBean fatturaFile= null;
	private BooleanCheckBox conservazione = null;
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
		
		WebGenericProjectFactory factory = this.getFactory();
		// Properties del form
		this.setId("formCaricaFattura"); 
		this.setClosable(false);
		this.setNomeForm("");

		this.dipartimento = factory.getInputFieldFactory().createSelectList("cf_dipartimento","fattura.form.dipartimento",null,true);
		((SelectListImpl)this.dipartimento).setCheckItemWidth(true); 
		this.dipartimento.setFontName("Arial"); //"Arial,Verdana,sans-serif" 

		this.descrittoreFattura = factory.getInputFieldFactory().createText("descrittoreFattura","fattura.form.descrittoreFattura",null,true);
		this.descrittoreFattura.setStyleClass(Costanti.INPUT_LONG_STYLE_CLASS); 
		this.conservazione = factory.getInputFieldFactory().createBooleanCheckBox("conservazione","fattura.form.conservazione",null,false);
		this.conservazione.setFieldsToUpdate(this.getId() + "_btnPnl"); 
		this.conservazione.setForm(this);
		this.conservazione.setNote("Informativa che spiega il significato di questo flag.");
		
		this.setField(this.dipartimento);
		this.setField(this.descrittoreFattura);
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
		this.conservazione.reset();
		
		this.mostraFormCorservazione= false;
		
		this._setMostraConservazione();
	}
	
	@Override
	public void setObject(Object arg0) throws Exception {
		this.fatturaFile.setDto(true, null, null); 
		this._setMostraConservazione();
	}
	
	@Override
	public Object getObject() throws Exception {
		return null;
	}
	
	public SelectList getDipartimento() {
		return dipartimento;
	}
	public void setDipartimento(SelectList dipartimento) {
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

	@Override
	public String valida() throws Exception {
		String toRet = null;

		List<UploadItem> files = this.getFatturaFile().getFilesCache();

		if(files == null || (files != null && files.size() == 0)) {
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.salvaFattura.nessunFileSelezionato");
		}		


		// Dipartimento
		org.openspcoop2.generic_project.web.input.SelectItem _dipartimento = this.getDipartimento().getValue();

		String valueCodiceDipartimento = null; 

		if(_dipartimento != null)
			valueCodiceDipartimento = _dipartimento.getValue();

		if( valueCodiceDipartimento == null || (valueCodiceDipartimento != null && valueCodiceDipartimento.equals(CostantiForm.ALL)))
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromCommonsResourceBundle(CostantiForm.SELECT_VALORE_NON_VALIDO,this.getDipartimento().getLabel());


		return toRet;
	}
}
