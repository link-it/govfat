/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.console.datamodel;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.console.bean.ConservazioneBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IConservazioneService;
import org.govmix.proxy.fatturapa.web.console.search.ConservazioneSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.datamodel.ParameterizedDataModel;

/**
 * ConservazioneDM classe di supporto alla visualizzazione delle fatture nella console.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class ConservazioneDM extends

ParameterizedDataModel<Long, ConservazioneBean, IConservazioneService, FatturaElettronica, ConservazioneSearchForm>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3498902673441284275L;

}
