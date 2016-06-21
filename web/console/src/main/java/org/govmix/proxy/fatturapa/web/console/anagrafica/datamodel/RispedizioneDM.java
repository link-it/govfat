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
package org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel;

import org.govmix.proxy.fatturapa.orm.PccRispedizione;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RispedizioneBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RispedizioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRispedizioneService;
import org.openspcoop2.generic_project.web.impl.jsf1.datamodel.ParameterizedDataModel;

public class RispedizioneDM extends ParameterizedDataModel<Long, RispedizioneBean, IRispedizioneService, PccRispedizione, RispedizioneSearchForm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

}
