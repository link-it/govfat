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
package org.govmix.proxy.fatturapa.web.console.dao;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.Evento;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.dao.ILoginDAO;


/**
 * ILoginDao Interfaccia di Login.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public interface ILoginDao extends ILoginDAO{

	public Utente getLoggedUtente(String username, String password) throws ServiceException;
	
	public Utente getLoggedUtente(String username) throws ServiceException;
	
	public Ente getEnte(String nomeEnte);
	
	public Protocollo getProtocollo(String nomeProtocollo);
	
	public List<Dipartimento> getListaDipartimentiUtente(Utente utente);
	
	public void registraEvento(Evento evento) throws ServiceException;
}
