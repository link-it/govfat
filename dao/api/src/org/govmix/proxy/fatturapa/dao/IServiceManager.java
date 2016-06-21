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

import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.exception.NotImplementedException;


/**	
 * Manager with which 'can get the service for the management of the objects defined in the package org.govmix.proxy.fatturapa 
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public interface IServiceManager {

	/*
	 =====================================================================================================================
	 Services relating to the object with name:LottoFatture type:LottoFatture
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ILottoFattureServiceSearch getLottoFattureServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IExtendedLottoFattureServiceSearch getExtendedLottoFattureServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ILottoFattureService getLottoFattureService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:FatturaElettronica type:FatturaElettronica
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IFatturaElettronicaServiceSearch getFatturaElettronicaServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IFatturaElettronicaService getFatturaElettronicaService() throws ServiceException,NotImplementedException;
	
	public IExtendedFatturaElettronicaServiceSearch getExtendedFatturaElettronicaServiceSearch() throws ServiceException, NotImplementedException;

	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:NotificaEsitoCommittente type:NotificaEsitoCommittente
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public INotificaEsitoCommittenteServiceSearch getNotificaEsitoCommittenteServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public INotificaEsitoCommittenteService getNotificaEsitoCommittenteService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:NotificaDecorrenzaTermini type:NotificaDecorrenzaTermini
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public INotificaDecorrenzaTerminiServiceSearch getNotificaDecorrenzaTerminiServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public INotificaDecorrenzaTerminiService getNotificaDecorrenzaTerminiService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:AllegatoFattura type:AllegatoFattura
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IAllegatoFatturaServiceSearch getAllegatoFatturaServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IAllegatoFatturaService getAllegatoFatturaService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:Ente type:Ente
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IEnteServiceSearch getEnteServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IEnteService getEnteService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:Registro type:Registro
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Registro}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Registro}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IRegistroServiceSearch getRegistroServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Registro}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Registro}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IRegistroService getRegistroService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:RegistroProperty type:RegistroProperty
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IRegistroPropertyServiceSearch getRegistroPropertyServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IRegistroPropertyService getRegistroPropertyService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:Utente type:Utente
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IUtenteServiceSearch getUtenteServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IUtenteService getUtenteService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:Dipartimento type:Dipartimento
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IDipartimentoServiceSearch getDipartimentoServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IDipartimentoService getDipartimentoService() throws ServiceException,NotImplementedException;
	/*
	 =====================================================================================================================
	 Services relating to the object with name:DipartimentoProperty type:DipartimentoProperty
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IDipartimentoPropertyServiceSearch getDipartimentoPropertyServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IDipartimentoPropertyService getDipartimentoPropertyService() throws ServiceException,NotImplementedException;
	
}
