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
package org.govmix.proxy.fatturapa.dao;

import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.IdUtente;
import org.openspcoop2.generic_project.dao.IServiceSearchWithId;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;


/** 
* Service can be used for research objects on the backend of type org.govmix.proxy.fatturapa.FatturaElettronica  
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public interface IExtendedFatturaElettronicaServiceSearch extends IServiceSearchWithId<FatturaElettronica, IdFattura> {

	long countFatturePush(Date date) throws NotImplementedException, ServiceException, Exception;
	List<FatturaElettronica> findAllFatturePush(Date date, int offset, int limit) throws NotImplementedException, ServiceException, Exception;
	long countFattureContestualePush(Date date) throws NotImplementedException, ServiceException, Exception;
	List<FatturaElettronica> findAllFattureContestualePush(Date date, int offset, int limit) throws NotImplementedException, ServiceException, Exception;
	public long countFatturePullByUser(Date date, IdUtente idUtente) throws NotImplementedException, ServiceException,Exception;
	public List<FatturaElettronica> findAllFatturePullByUser(Date date, IdUtente idUtente, int offset, int limit) throws NotImplementedException, ServiceException,Exception;
	public long countFattureDaAccettare(Date date) throws NotImplementedException, ServiceException,Exception;	
	public List<FatturaElettronica> findAllFattureDaAccettare(Date date, int offset, int limit) throws NotImplementedException, ServiceException,Exception;

	public void assegnaProtocolloAInteroLotto(IdLotto idLotto, String protocollo) throws NotImplementedException, ServiceException,Exception;

}
