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
package org.govmix.proxy.fatturapa.orm.dao.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

import org.govmix.proxy.fatturapa.orm.dao.IAllegatoFatturaService;
import org.govmix.proxy.fatturapa.orm.dao.IAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IComunicazioneSdiService;
import org.govmix.proxy.fatturapa.orm.dao.IComunicazioneSdiServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoPropertyService;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoPropertyServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoService;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IEnteService;
import org.govmix.proxy.fatturapa.orm.dao.IEnteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IEventoService;
import org.govmix.proxy.fatturapa.orm.dao.IEventoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureService;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.INotificaDecorrenzaTerminiService;
import org.govmix.proxy.fatturapa.orm.dao.INotificaDecorrenzaTerminiServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteService;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccContabilizzazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccContabilizzazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccDipartimentoOperazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccDipartimentoOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccErroreElaborazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccErroreElaborazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccNotificaService;
import org.govmix.proxy.fatturapa.orm.dao.IPccNotificaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccOperazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccPagamentoService;
import org.govmix.proxy.fatturapa.orm.dao.IPccPagamentoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccRispedizioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccRispedizioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccScadenzaService;
import org.govmix.proxy.fatturapa.orm.dao.IPccScadenzaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneEsitoService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneEsitoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccUtenteOperazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccUtenteOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IProtocolloService;
import org.govmix.proxy.fatturapa.orm.dao.IProtocolloServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroPropertyService;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroPropertyServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroService;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteService;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteServiceSearch;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

/**     
 * Manager that allows you to obtain the services of research and management of objects
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class JDBCLimitedServiceManager extends JDBCServiceManager {

	private JDBCServiceManager unlimitedJdbcServiceManager;

	public JDBCLimitedServiceManager(JDBCServiceManager jdbcServiceManager) throws ServiceException {
		this.datasource = jdbcServiceManager.get_Datasource();
		this.connection = jdbcServiceManager.get_Connection();
		this.log = jdbcServiceManager.get_Logger();
		this.jdbcProperties = jdbcServiceManager.get_JdbcProperties();
		this.unlimitedJdbcServiceManager = jdbcServiceManager;
	}
	
	
	@Override
	public Connection getConnection() throws ServiceException {
		throw new ServiceException("Connection managed from framework");
	}
	@Override
	public void closeConnection(Connection connection) throws ServiceException {
		throw new ServiceException("Connection managed from framework");
	}
	@Override
	protected Connection get_Connection() throws ServiceException {
		throw new ServiceException("Connection managed from framework");
	}
	@Override
	protected DataSource get_Datasource() throws ServiceException {
		throw new ServiceException("Connection managed from framework");
	}
	
	
	
	/*
	 =====================================================================================================================
	 Services relating to the object with name:ComunicazioneSdi type:ComunicazioneSdi
	 =====================================================================================================================
	*/
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.ComunicazioneSdi}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.ComunicazioneSdi}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IComunicazioneSdiServiceSearch getComunicazioneSdiServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCComunicazioneSdiServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.ComunicazioneSdi}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.ComunicazioneSdi}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IComunicazioneSdiService getComunicazioneSdiService() throws ServiceException,NotImplementedException{
		return new JDBCComunicazioneSdiService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public ILottoFattureServiceSearch getLottoFattureServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCLottoFattureServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.LottoFatture}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public ILottoFattureService getLottoFattureService() throws ServiceException,NotImplementedException{
		return new JDBCLottoFattureService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IFatturaElettronicaServiceSearch getFatturaElettronicaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCFatturaElettronicaServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.FatturaElettronica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IFatturaElettronicaService getFatturaElettronicaService() throws ServiceException,NotImplementedException{
		return new JDBCFatturaElettronicaService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public INotificaEsitoCommittenteServiceSearch getNotificaEsitoCommittenteServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCNotificaEsitoCommittenteServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public INotificaEsitoCommittenteService getNotificaEsitoCommittenteService() throws ServiceException,NotImplementedException{
		return new JDBCNotificaEsitoCommittenteService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public INotificaDecorrenzaTerminiServiceSearch getNotificaDecorrenzaTerminiServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCNotificaDecorrenzaTerminiServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public INotificaDecorrenzaTerminiService getNotificaDecorrenzaTerminiService() throws ServiceException,NotImplementedException{
		return new JDBCNotificaDecorrenzaTerminiService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IAllegatoFatturaServiceSearch getAllegatoFatturaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCAllegatoFatturaServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.AllegatoFattura}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IAllegatoFatturaService getAllegatoFatturaService() throws ServiceException,NotImplementedException{
		return new JDBCAllegatoFatturaService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IEnteServiceSearch getEnteServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCEnteServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Ente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Ente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IEnteService getEnteService() throws ServiceException,NotImplementedException{
		return new JDBCEnteService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IProtocolloServiceSearch getProtocolloServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCProtocolloServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Protocollo}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IProtocolloService getProtocolloService() throws ServiceException,NotImplementedException{
		return new JDBCProtocolloService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IRegistroServiceSearch getRegistroServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCRegistroServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Registro}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Registro}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IRegistroService getRegistroService() throws ServiceException,NotImplementedException{
		return new JDBCRegistroService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IRegistroPropertyServiceSearch getRegistroPropertyServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCRegistroPropertyServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.RegistroProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IRegistroPropertyService getRegistroPropertyService() throws ServiceException,NotImplementedException{
		return new JDBCRegistroPropertyService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IUtenteServiceSearch getUtenteServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCUtenteServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Utente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Utente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IUtenteService getUtenteService() throws ServiceException,NotImplementedException{
		return new JDBCUtenteService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IDipartimentoServiceSearch getDipartimentoServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCDipartimentoServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Dipartimento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IDipartimentoService getDipartimentoService() throws ServiceException,NotImplementedException{
		return new JDBCDipartimentoService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IDipartimentoPropertyServiceSearch getDipartimentoPropertyServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCDipartimentoPropertyServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.DipartimentoProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IDipartimentoPropertyService getDipartimentoPropertyService() throws ServiceException,NotImplementedException{
		return new JDBCDipartimentoPropertyService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccOperazioneServiceSearch getPccOperazioneServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccOperazioneServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccOperazioneService getPccOperazioneService() throws ServiceException,NotImplementedException{
		return new JDBCPccOperazioneService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccDipartimentoOperazioneServiceSearch getPccDipartimentoOperazioneServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccDipartimentoOperazioneServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccDipartimentoOperazioneService getPccDipartimentoOperazioneService() throws ServiceException,NotImplementedException{
		return new JDBCPccDipartimentoOperazioneService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccUtenteOperazioneServiceSearch getPccUtenteOperazioneServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccUtenteOperazioneServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccUtenteOperazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccUtenteOperazioneService getPccUtenteOperazioneService() throws ServiceException,NotImplementedException{
		return new JDBCPccUtenteOperazioneService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccContabilizzazioneServiceSearch getPccContabilizzazioneServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccContabilizzazioneServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccContabilizzazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccContabilizzazioneService getPccContabilizzazioneService() throws ServiceException,NotImplementedException{
		return new JDBCPccContabilizzazioneService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccScadenzaServiceSearch getPccScadenzaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccScadenzaServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccScadenza}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccScadenzaService getPccScadenzaService() throws ServiceException,NotImplementedException{
		return new JDBCPccScadenzaService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccPagamentoServiceSearch getPccPagamentoServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccPagamentoServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccPagamento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccPagamentoService getPccPagamentoService() throws ServiceException,NotImplementedException{
		return new JDBCPccPagamentoService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccTracciaServiceSearch getPccTracciaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccTracciaServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTraccia}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccTracciaService getPccTracciaService() throws ServiceException,NotImplementedException{
		return new JDBCPccTracciaService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccTracciaTrasmissioneServiceSearch getPccTracciaTrasmissioneServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccTracciaTrasmissioneServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccTracciaTrasmissioneService getPccTracciaTrasmissioneService() throws ServiceException,NotImplementedException{
		return new JDBCPccTracciaTrasmissioneService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccTracciaTrasmissioneEsitoServiceSearch getPccTracciaTrasmissioneEsitoServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccTracciaTrasmissioneEsitoServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccTracciaTrasmissioneEsitoService getPccTracciaTrasmissioneEsitoService() throws ServiceException,NotImplementedException{
		return new JDBCPccTracciaTrasmissioneEsitoService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccErroreElaborazioneServiceSearch getPccErroreElaborazioneServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccErroreElaborazioneServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccErroreElaborazione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccErroreElaborazioneService getPccErroreElaborazioneService() throws ServiceException,NotImplementedException{
		return new JDBCPccErroreElaborazioneService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccRispedizioneServiceSearch getPccRispedizioneServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccRispedizioneServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccRispedizione}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccRispedizioneService getPccRispedizioneService() throws ServiceException,NotImplementedException{
		return new JDBCPccRispedizioneService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IPccNotificaServiceSearch getPccNotificaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCPccNotificaServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.PccNotifica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IPccNotificaService getPccNotificaService() throws ServiceException,NotImplementedException{
		return new JDBCPccNotificaService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IEventoServiceSearch getEventoServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCEventoServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Evento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.orm.Evento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IEventoService getEventoService() throws ServiceException,NotImplementedException{
		return new JDBCEventoService(this.unlimitedJdbcServiceManager);
	}
	
	
	
}