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
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.utils.ServiceManagerProperties;
import org.govmix.proxy.fatturapa.dao.IExtendedLottoFattureServiceSearch;
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
import org.govmix.proxy.fatturapa.dao.IServiceManager;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.dao.IExtendedFatturaElettronicaServiceSearch;

/**     
 * Manager that allows you to obtain the services of research and management of objects
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class JDBCServiceManager extends org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManager implements IServiceManager {

	protected Connection get_Connection() throws ServiceException {
		return this.connection;
	}
	protected DataSource get_Datasource() throws ServiceException {
		return this.datasource;
	}
	protected JDBCServiceManagerProperties get_JdbcProperties(){
		return this.jdbcProperties;
	}
	protected Logger get_Logger(){
		return this.log;
	}
	@Override
	protected Connection getConnection() throws ServiceException {
		return super.getConnection();
	}
	@Override
	protected void closeConnection(Connection connection) throws ServiceException {
		super.closeConnection(connection);
	}

	protected JDBCServiceManager(){}

	public JDBCServiceManager(String jndiName, Properties contextJNDI,
			ServiceManagerProperties smProperties) throws ServiceException {
		super(jndiName, contextJNDI, smProperties);
	}
	public JDBCServiceManager(String jndiName, Properties contextJNDI,
			JDBCServiceManagerProperties jdbcProperties) throws ServiceException {
		super(jndiName, contextJNDI, jdbcProperties);
	}
	public JDBCServiceManager(String jndiName, Properties contextJNDI,
			ServiceManagerProperties smProperties, Logger alog) throws ServiceException {
		super(jndiName, contextJNDI, smProperties, alog);
	}
	public JDBCServiceManager(String jndiName, Properties contextJNDI,
			JDBCServiceManagerProperties jdbcProperties, Logger alog) throws ServiceException {
		super(jndiName, contextJNDI, jdbcProperties, alog);
	}
	
	
	public JDBCServiceManager(DataSource ds, ServiceManagerProperties smProperties)
			throws ServiceException {
		super(ds, smProperties);
	}
	public JDBCServiceManager(DataSource ds, JDBCServiceManagerProperties jdbcProperties)
			throws ServiceException {
		super(ds, jdbcProperties);
	}
	public JDBCServiceManager(DataSource ds, ServiceManagerProperties smProperties, Logger alog)
			throws ServiceException {
		super(ds, smProperties, alog);
	}
	public JDBCServiceManager(DataSource ds, JDBCServiceManagerProperties jdbcProperties, Logger alog)
			throws ServiceException {
		super(ds, jdbcProperties, alog);
	}
	
	
	public JDBCServiceManager(String connectionUrl, String driverJDBC,
			String username, String password, ServiceManagerProperties smProperties)
			throws ServiceException {
		super(connectionUrl, driverJDBC, username, password, smProperties);
	}
	public JDBCServiceManager(String connectionUrl, String driverJDBC,
			String username, String password, JDBCServiceManagerProperties jdbcProperties)
			throws ServiceException {
		super(connectionUrl, driverJDBC, username, password, jdbcProperties);
	}
	public JDBCServiceManager(String connectionUrl, String driverJDBC,
			String username, String password, ServiceManagerProperties smProperties, Logger alog)
			throws ServiceException {
		super(connectionUrl, driverJDBC, username, password, smProperties, alog);
	}
	public JDBCServiceManager(String connectionUrl, String driverJDBC,
			String username, String password, JDBCServiceManagerProperties jdbcProperties, Logger alog)
			throws ServiceException {
		super(connectionUrl, driverJDBC, username, password, jdbcProperties, alog);
	}
	
	
	public JDBCServiceManager(Connection connection, ServiceManagerProperties smProperties)
			throws ServiceException {
		super(connection, smProperties);
	}
	public JDBCServiceManager(Connection connection, JDBCServiceManagerProperties jdbcProperties)
			throws ServiceException {
		super(connection, jdbcProperties);
	}
	public JDBCServiceManager(Connection connection, ServiceManagerProperties smProperties, Logger alog) throws ServiceException {
		super(connection, smProperties, alog);
	}
	public JDBCServiceManager(Connection connection, JDBCServiceManagerProperties jdbcProperties, Logger alog) throws ServiceException {
		super(connection, jdbcProperties, alog);
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
		return new JDBCLottoFattureServiceSearch(this);
	}
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.LottoFatture}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IExtendedLottoFattureServiceSearch getExtendedLottoFattureServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCExtendedLottoFattureServiceSearch(this);
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
		return new JDBCLottoFattureService(this);
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
		return new JDBCFatturaElettronicaServiceSearch(this);
	}
	
	/**
	 * Return a service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}
	 *
	 * @return Service used to research on the backend on objects of type {@link org.govmix.proxy.fatturapa.FatturaElettronica}	
	 * @throws ServiceException Exception thrown when an error occurs during processing of the request
	 * @throws NotImplementedException Exception thrown when the method is not implemented
	 */
	@Override
	public IExtendedFatturaElettronicaServiceSearch getExtendedFatturaElettronicaServiceSearch() throws ServiceException,NotImplementedException{
		return new JDBCExtendedFatturaElettronicaServiceSearch(this);
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
		return new JDBCFatturaElettronicaService(this);
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
		return new JDBCNotificaEsitoCommittenteServiceSearch(this);
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
		return new JDBCNotificaEsitoCommittenteService(this);
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
		return new JDBCNotificaDecorrenzaTerminiServiceSearch(this);
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
		return new JDBCNotificaDecorrenzaTerminiService(this);
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
		return new JDBCAllegatoFatturaServiceSearch(this);
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
		return new JDBCAllegatoFatturaService(this);
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
		return new JDBCEnteServiceSearch(this);
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
		return new JDBCEnteService(this);
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
		return new JDBCRegistroServiceSearch(this);
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
		return new JDBCRegistroService(this);
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
		return new JDBCRegistroPropertyServiceSearch(this);
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
		return new JDBCRegistroPropertyService(this);
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
		return new JDBCUtenteServiceSearch(this);
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
		return new JDBCUtenteService(this);
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
		return new JDBCDipartimentoServiceSearch(this);
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
		return new JDBCDipartimentoService(this);
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
		return new JDBCDipartimentoPropertyServiceSearch(this);
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
		return new JDBCDipartimentoPropertyService(this);
	}
	
	
	
	

}
