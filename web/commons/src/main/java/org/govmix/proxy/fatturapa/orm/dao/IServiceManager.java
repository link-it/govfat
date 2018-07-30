/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm.dao;

import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.exception.NotImplementedException;


/**	
 * Manager with which 'can get the service for the management of the objects defined in the package org.govmix.proxy.fatturapa.orm 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ILottoFattureServiceSearch getLottoFattureServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ILottoFattureService getLottoFattureService() throws ServiceException,NotImplementedException;
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:TracciaSDI type:TracciaSDI
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ITracciaSDIServiceSearch getTracciaSDIServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.TracciaSDI}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ITracciaSDIService getTracciaSDIService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:FatturaElettronica type:FatturaElettronica
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IFatturaElettronicaServiceSearch getFatturaElettronicaServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IFatturaElettronicaService getFatturaElettronicaService() throws ServiceException,NotImplementedException;
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:NotificaEsitoCommittente type:NotificaEsitoCommittente
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public INotificaEsitoCommittenteServiceSearch getNotificaEsitoCommittenteServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public INotificaDecorrenzaTerminiServiceSearch getNotificaDecorrenzaTerminiServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IAllegatoFatturaServiceSearch getAllegatoFatturaServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Ente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IEnteServiceSearch getEnteServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Ente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IEnteService getEnteService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:Protocollo type:Protocollo
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IProtocolloServiceSearch getProtocolloServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IProtocolloService getProtocolloService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:Registro type:Registro
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Registro}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IRegistroServiceSearch getRegistroServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Registro}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IRegistroPropertyServiceSearch getRegistroPropertyServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IRegistroPropertyService getRegistroPropertyService() throws ServiceException,NotImplementedException;
	
	
		/*
	 =====================================================================================================================
	 Services relating to the object with name:SIP type:SIP
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.SIP}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ISIPServiceSearch getSIPServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.SIP}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.SIP}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public ISIPService getSIPService() throws ServiceException,NotImplementedException;
	
	
	

	/*
	 =====================================================================================================================
	 Services relating to the object with name:Utente type:Utente
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Utente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IUtenteServiceSearch getUtenteServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Utente}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IDipartimentoServiceSearch getDipartimentoServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IDipartimentoPropertyServiceSearch getDipartimentoPropertyServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IDipartimentoPropertyService getDipartimentoPropertyService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccOperazione type:PccOperazione
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccOperazioneServiceSearch getPccOperazioneServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccOperazioneService getPccOperazioneService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccDipartimentoOperazione type:PccDipartimentoOperazione
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccDipartimentoOperazioneServiceSearch getPccDipartimentoOperazioneServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccDipartimentoOperazioneService getPccDipartimentoOperazioneService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccUtenteOperazione type:PccUtenteOperazione
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccUtenteOperazioneServiceSearch getPccUtenteOperazioneServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccUtenteOperazioneService getPccUtenteOperazioneService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccContabilizzazione type:PccContabilizzazione
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccContabilizzazioneServiceSearch getPccContabilizzazioneServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccContabilizzazioneService getPccContabilizzazioneService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccScadenza type:PccScadenza
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccScadenzaServiceSearch getPccScadenzaServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccScadenzaService getPccScadenzaService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccPagamento type:PccPagamento
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccPagamentoServiceSearch getPccPagamentoServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccPagamentoService getPccPagamentoService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccTraccia type:PccTraccia
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccTracciaServiceSearch getPccTracciaServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccTracciaService getPccTracciaService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccTracciaTrasmissione type:PccTracciaTrasmissione
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccTracciaTrasmissioneServiceSearch getPccTracciaTrasmissioneServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccTracciaTrasmissioneService getPccTracciaTrasmissioneService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccTracciaTrasmissioneEsito type:PccTracciaTrasmissioneEsito
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccTracciaTrasmissioneEsitoServiceSearch getPccTracciaTrasmissioneEsitoServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccTracciaTrasmissioneEsitoService getPccTracciaTrasmissioneEsitoService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccErroreElaborazione type:PccErroreElaborazione
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccErroreElaborazioneServiceSearch getPccErroreElaborazioneServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccErroreElaborazioneService getPccErroreElaborazioneService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccRispedizione type:PccRispedizione
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccRispedizioneServiceSearch getPccRispedizioneServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccRispedizioneService getPccRispedizioneService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:PccNotifica type:PccNotifica
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccNotificaServiceSearch getPccNotificaServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IPccNotificaService getPccNotificaService() throws ServiceException,NotImplementedException;
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:Evento type:Evento
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Evento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IEventoServiceSearch getEventoServiceSearch() throws ServiceException,NotImplementedException;
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Evento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	public IEventoService getEventoService() throws ServiceException,NotImplementedException;
	
	
	
	
}
