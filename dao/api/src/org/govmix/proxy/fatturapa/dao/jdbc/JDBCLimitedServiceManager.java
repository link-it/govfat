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
package org.govmix.proxy.fatturapa.dao.jdbc;

import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

import java.sql.Connection;

import javax.sql.DataSource;

import org.govmix.proxy.fatturapa.dao.ILottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.dao.ILottoFattureService;
import org.govmix.proxy.fatturapa.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.dao.INotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.dao.INotificaEsitoCommittenteService;
import org.govmix.proxy.fatturapa.dao.INotificaDecorrenzaTerminiServiceSearch;
import org.govmix.proxy.fatturapa.dao.INotificaDecorrenzaTerminiService;
import org.govmix.proxy.fatturapa.dao.IAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.dao.IAllegatoFatturaService;
import org.govmix.proxy.fatturapa.dao.IEnteServiceSearch;
import org.govmix.proxy.fatturapa.dao.IEnteService;
import org.govmix.proxy.fatturapa.dao.IRegistroServiceSearch;
import org.govmix.proxy.fatturapa.dao.IRegistroService;
import org.govmix.proxy.fatturapa.dao.IRegistroPropertyServiceSearch;
import org.govmix.proxy.fatturapa.dao.IRegistroPropertyService;
import org.govmix.proxy.fatturapa.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.dao.IUtenteService;
import org.govmix.proxy.fatturapa.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.dao.IDipartimentoService;
import org.govmix.proxy.fatturapa.dao.IDipartimentoPropertyServiceSearch;
import org.govmix.proxy.fatturapa.dao.IDipartimentoPropertyService;
/**     
 * Manager that allows you to obtain the services of research and management of objects
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
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
	@Override
	public ILottoFattureServiceSearch getLottoFattureServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCLottoFattureServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IFatturaElettronicaServiceSearch getFatturaElettronicaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCFatturaElettronicaServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public INotificaEsitoCommittenteServiceSearch getNotificaEsitoCommittenteServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCNotificaEsitoCommittenteServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaEsitoCommittente}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public INotificaDecorrenzaTerminiServiceSearch getNotificaDecorrenzaTerminiServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCNotificaDecorrenzaTerminiServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IAllegatoFatturaServiceSearch getAllegatoFatturaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCAllegatoFatturaServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.AllegatoFattura}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IEnteServiceSearch getEnteServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCEnteServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Ente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IEnteService getEnteService() throws ServiceException,NotImplementedException{
		return new JDBCEnteService(this.unlimitedJdbcServiceManager);
	}
	
	
	
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
	@Override
	public IRegistroServiceSearch getRegistroServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCRegistroServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Registro}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Registro}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IRegistroPropertyServiceSearch getRegistroPropertyServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCRegistroPropertyServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.RegistroProperty}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IUtenteServiceSearch getUtenteServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCUtenteServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Utente}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IDipartimentoServiceSearch getDipartimentoServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCDipartimentoServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.Dipartimento}	
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
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IDipartimentoPropertyServiceSearch getDipartimentoPropertyServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCDipartimentoPropertyServiceSearch(this.unlimitedJdbcServiceManager);
	}
	
	/**
	 * Return a service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}
	 *
	 * @return Service used to research and manage on the backend on objects of type {@link org.govmix.proxy.fatturapa.DipartimentoProperty}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IDipartimentoPropertyService getDipartimentoPropertyService() throws ServiceException,NotImplementedException{
		return new JDBCDipartimentoPropertyService(this.unlimitedJdbcServiceManager);
	}
	
	
	
}