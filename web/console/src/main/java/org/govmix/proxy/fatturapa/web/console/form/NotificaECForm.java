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


import javax.faces.event.ActionEvent;

import org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.input.TextArea;

/**
 * NotificaECForm Bean per la gestione del form per l'invio della NotificaEC.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECForm extends BaseForm implements Form {

	private SelectList esito= null;
	private TextArea descrizione = null;
	
	public NotificaECForm()throws Exception{
		this.init();
	}
	
	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getFactory();
		
		this.setClosable(false);
		this.setId("iNEC");
		this.setNomeForm(null); 
		
		this.esito = factory.getInputFieldFactory().createSelectList("esito","notificaEsitoCommittente.esito",new org.openspcoop2.generic_project.web.input.SelectItem("*","--"),true);

		this.esito.setFieldsToUpdate("iNEC_formPnl");
		this.esito.setForm(this);

		this.descrizione = factory.getInputFieldFactory().createTextArea("descrizione","notificaEsitoCommittente.descrizione",null,true);
		
		
		this._setEsito();
		
		this.setField(this.esito);
		this.setField(this.descrizione);
	}

	@Override
	public void reset() {
		this.esito.reset();
		this.descrizione.reset();
		
	}

	public SelectList getEsito() {
		return this.esito;
	}

	public void setEsito(SelectList esito) {
		this.esito = esito;
	}

	public TextArea getDescrizione() {
		this._setEsito();
		
		return this.descrizione;
	}

	private void _setEsito() {
		this.descrizione.setRendered(false); 
		
		if(this.esito.getValue() != null){
			if(this.esito.getValue().getValue().equals(EsitoCommittenteType.EC02.getValue())){
				this.descrizione.setRendered(true); 
			}
		}
	}

	public void setDescrizione(TextArea descrizione) {
		this.descrizione = descrizione;
	}
	
	public void esitoSelectListener(ActionEvent ae){
		this._setEsito();
	}

}
