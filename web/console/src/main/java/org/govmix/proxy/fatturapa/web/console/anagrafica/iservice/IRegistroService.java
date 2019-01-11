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
package org.govmix.proxy.fatturapa.web.console.anagrafica.iservice;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.IdProtocollo;
import org.govmix.proxy.fatturapa.orm.RegistroProperty;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IRegistroService extends IBaseService<RegistroBean, Long, RegistroSearchForm>{
	
	public RegistroBean findRegistroByNome(String nome) throws ServiceException;
	public List<RegistroProperty> getListaRegistroPropertiesProtocollo(IdProtocollo idProtocollo) throws ServiceException;
}
