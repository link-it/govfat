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


import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.ajax4jsf.xml.serializer.utils.Utils;
import org.govmix.proxy.fatturapa.notificaesitocommittente.MotivoRifiuto;
import org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.InvioNotificaEsitoCommittente;
import org.openspcoop2.generic_project.web.factory.Costanti;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.Form;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.input.MultipleCheckBox;
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

	private SelectList<SelectItem> esito= null;
	private MultipleCheckBox<SelectItem> motivoRifiuto = null; 
	private TextArea descrizione = null;
	
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
		this.motivoRifiuto.setFieldsToUpdate("iNEC_formPnl");
		this.motivoRifiuto.setForm(this);
		
		this.descrizione = factory.getInputFieldFactory().createTextArea("descrizione","notificaEsitoCommittente.descrizione", null, false);
		
		this._setEsito();
		this._setMotivoRifiuto();
		
		this.setField(this.esito);
		this.setField(this.motivoRifiuto);
		this.setField(this.descrizione);
	}

	@Override
	public void reset() {
		this.esito.reset();
		this.motivoRifiuto.reset();
		this.descrizione.reset();
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
			} else {
				this.motivoRifiuto.reset();
			}
		} else {
			this.motivoRifiuto.reset();
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
		this._setMotivoRifiuto();
	}

	private void _setMotivoRifiuto() {
		this.descrizione.setRendered(false); 
		this.descrizione.setNote(null);
		
		if(this.motivoRifiuto.isRendered() && this.motivoRifiuto.getValue() != null){
			List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> value = this.getMotivoRifiuto().getValue();
			if(value != null && value.size() > 0) {
				this.descrizione.setRendered(true); 
				
				try {
					int lunghezzaResiduaDescrizione = InvioNotificaEsitoCommittente.getLunghezzaResiduaDescrizione(this.getMotivoRifiutoValori()); 
					String notaLabel = org.govmix.proxy.fatturapa.web.console.util.Utils.getInstance().getMessageWithParamsFromResourceBundle("notificaEsitoCommittente.descrizione.nota", lunghezzaResiduaDescrizione);
					this.descrizione.setNote(notaLabel);
				}catch(Exception e) {
					
				}
				
				
			} else {
				this.descrizione.reset();
			}
		} else {
			this.descrizione.reset();
		}
	}
	
	public void motivoRifiutoOnChangeListener(ActionEvent ae){
		this._setMotivoRifiuto();
	}
	
	public TextArea getDescrizione() {
		this._setMotivoRifiuto();
		
		return descrizione;
	}
	
	public void setDescrizione(TextArea descrizione) {
		this.descrizione = descrizione;
	}
	
	public List<MotivoRifiuto> getMotivoRifiutoValori() {
		List<MotivoRifiuto> motivoRifiuto = new ArrayList<MotivoRifiuto>();
		
		List<org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem> value = this.getMotivoRifiuto().getValue();
		if(value != null && value.size() > 0) {
			for(org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem val : value) {
				motivoRifiuto.add(MotivoRifiuto.fromValue(val.getValue()));
			}
		}
		
		return motivoRifiuto;
	}
}
