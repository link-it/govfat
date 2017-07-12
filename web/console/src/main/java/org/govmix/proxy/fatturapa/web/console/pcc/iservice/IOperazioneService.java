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
package org.govmix.proxy.fatturapa.web.console.pcc.iservice;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.TracciaPccEstesaBean;
import org.govmix.proxy.fatturapa.web.console.pcc.search.OperazioneSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IOperazioneService  extends IBaseService<TracciaPccEstesaBean, Long, OperazioneSearchForm>{

	
	public List<String> getUtenteRichiedenteAutoComplete(String val) throws ServiceException;
	
	public List<String> getSistemaRichiedenteAutoComplete(String val) throws ServiceException ;
	
	public List<String> getMittenteAutoComplete(String val) throws ServiceException;
	
	public List<String> getNumeroAutoComplete(String val) throws ServiceException;
	
	public void setIdFattura(IdFattura idFattura);
	public IdFattura getIdFattura();

}
