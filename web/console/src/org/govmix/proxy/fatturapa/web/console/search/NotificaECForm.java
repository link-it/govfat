/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.search;


import org.govmix.proxy.fatturapa.constants.EsitoCommittenteType;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;
import org.openspcoop2.generic_project.web.impl.jsf1.form.Form;
import org.openspcoop2.generic_project.web.input.FieldType;
import org.openspcoop2.generic_project.web.impl.jsf1.input.FormField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectListField;
import org.openspcoop2.generic_project.web.impl.jsf1.input.TextField;

/**
 * NotificaECForm Bean per la gestione del form per l'invio della NotificaEC.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class NotificaECForm extends Form {

	private FormField<SelectItem> esito= null;
	private FormField<String> descrizione = null;
	
	public NotificaECForm(){
		init();
	}
	
	@Override
	protected void init() {
		this.setClosable(false);
		this.setIdForm("inviaNotificaEC");
		this.setNomeForm(null); 
		
		this.esito = new SelectListField();
		this.esito.setRequired(true);
		this.esito.setLabel(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.esito"));
		this.esito.setName("esito");
		this.esito.setValue(null);
		this.esito.setDefaultValue(new org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem("*","--"));
		this.esito.setFieldsToUpdate("inviaNotificaEC_formPnl");

		this.descrizione = new TextField();
		this.descrizione.setType(FieldType.TEXT_AREA);
		this.descrizione.setRequired(true);
		this.descrizione.setLabel(Utils.getInstance().getMessageFromResourceBundle("notificaEsitoCommittente.descrizione"));
		this.descrizione.setName("descrizione");
		this.descrizione.setValue(null);
	}

	@Override
	public void reset() {
		this.esito.reset();
		this.descrizione.reset();
		
	}

	public FormField<SelectItem> getEsito() {
		return this.esito;
	}

	public void setEsito(FormField<SelectItem> esito) {
		this.esito = esito;
	}

	public FormField<String> getDescrizione() {
		this.descrizione.setRendered(false); 
		
		if(this.esito.getValue() != null){
			if(this.esito.getValue().getValue().equals(EsitoCommittenteType.EC02.getValue())){
				this.descrizione.setRendered(true); 
			}
		}
		
		return this.descrizione;
	}

	public void setDescrizione(FormField<String> descrizione) {
		this.descrizione = descrizione;
	}
	
	

}
