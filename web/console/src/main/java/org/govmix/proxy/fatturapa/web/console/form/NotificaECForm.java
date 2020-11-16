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
package org.govmix.proxy.fatturapa.web.console.form;


import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.input.MultipleCheckBox;
import org.openspcoop2.generic_project.web.input.SelectList;

/**
 * NotificaECForm Bean per la gestione del form per l'invio della NotificaEC.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECForm extends BaseForm implements Form {

	private SelectList<SelectItem> esito= null;
	private MultipleCheckBox<SelectItem> motivoRifiuto = null; 
	
	public NotificaECForm()throws Exception{
		this.init();
	}
	
	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		
		this.setClosable(false);
		this.setId("iNEC");
		this.setNomeForm(null); 
		
		this.esito = factory.getInputFieldFactory().createSelectList("esito","notificaEsitoCommittente.esito",new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*","--"),true);

		this.esito.setFieldsToUpdate("iNEC_formPnl");
		this.esito.setForm(this);

		this.motivoRifiuto = factory.getInputFieldFactory().createMultipleCheckBox("motivoRifiuto","notificaEsitoCommittente.motivoRifiuto",null,true);
		this.motivoRifiuto.setDirezione(Costanti.CHOICE_ORIENTATION_VERTICAL);
		
		this._setEsito();
		
		this.setField(this.esito);
		this.setField(this.motivoRifiuto);
	}

	@Override
	public void reset() {
		this.esito.reset();
		this.motivoRifiuto.reset();
		
	}

	public SelectList<SelectItem> getEsito() {
		return this.esito;
	}

	public void setEsito(SelectList<SelectItem> esito) {
		this.esito = esito;
	}

	private void _setEsito() {
		this.motivoRifiuto.setRendered(false); 
		
		if(this.esito.getValue() != null){
			if(this.esito.getValue().getValue().equals(EsitoCommittenteType.EC02.getValue())){
				this.motivoRifiuto.setRendered(true); 
			}
		}
	}

	public MultipleCheckBox<SelectItem> getMotivoRifiuto() {
		this._setEsito();
		
		return this.motivoRifiuto;
	}

	public void setMotivoRifiuto(MultipleCheckBox<SelectItem> motivoRifiuto) {
		this.motivoRifiuto = motivoRifiuto;
	}
	
	public void esitoSelectListener(ActionEvent ae){
		this._setEsito();
	}

}
