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
package org.govmix.proxy.fatturapa.web.console.iservice;

import org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.web.console.bean.NotificaDTBean;
import org.openspcoop2.generic_project.web.dao.IService;

/**
 * INotificaDTService Interfaccia del livello service di accesso al dao per le Notifiche Decorrenza Termini.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public interface INotificaDTService extends IService<NotificaDTBean, Long>{

	public void setIdNotificaDecorrenzaTermini(IdNotificaDecorrenzaTermini idNotifica);
	public IdNotificaDecorrenzaTermini getIdNotificaDecorrenzaTermini();
}

