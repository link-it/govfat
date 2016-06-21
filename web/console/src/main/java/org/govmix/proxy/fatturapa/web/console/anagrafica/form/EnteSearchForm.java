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

import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.form.SearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.form.BaseSearchForm;
import org.openspcoop2.generic_project.web.input.Text;

public class EnteSearchForm extends BaseSearchForm implements SearchForm {
	
	
	private Text nome = null;
	
	public EnteSearchForm() throws Exception{
		this.init();
	}

	@Override
	public void init() throws Exception {
		
		WebGenericProjectFactory factory = this.getWebGenericProjectFactory();
		
		this.nome = factory.getInputFieldFactory().createText("nome","ente.search.nome",null,false);
		
		this.setField(this.nome); 
	}

	@Override
	public void reset() {
		this.resetParametriPaginazione();
		this.nome.reset();
	}

	public Text getNome() {
		return this.nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}
}
