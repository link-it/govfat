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
package org.govmix.proxy.fatturapa.web.console.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.UtenteDipartimento;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.govmix.proxy.fatturapa.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.dao.IEnteServiceSearch;
import org.govmix.proxy.fatturapa.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.fetch.DipartimentoFetch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.crypt.Password;

/**
 * DBLoginDao Implementa il login per l'utente della console.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
@RequestScoped @ManagedBean(name="loginDao")
public class DBLoginDao implements ILoginDao{

	private IUtenteServiceSearch utenteDAO = null;
	private IDipartimentoServiceSearch dipartimentoDAO = null;
	private IEnteServiceSearch enteDAO = null;

	private static Logger log = LoggerManager.getConsoleDaoLogger();

	public DBLoginDao() {
		try{
			this.utenteDAO = DAOFactory.getInstance(log).getServiceManager().getUtenteServiceSearch();
			this.dipartimentoDAO = DAOFactory.getInstance(log).getServiceManager().getDipartimentoServiceSearch();
			this.enteDAO = DAOFactory.getInstance(log).getServiceManager().getEnteServiceSearch();
		}catch(Exception e){
			log.error("Si e' verificato un errore durante la creazione del LoginDAO: "+e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean login(String username, String password) throws ServiceException  {
		if(username ==null && password ==null){
			log.error("Credenziali non presenti");
			return false;
		}

		log.debug("Login per l'utente["+username+"] in corso...");

		Utente u;
		try {
			u = this.utenteDAO.find(this.utenteDAO.newExpression()
					.equals(Utente.model().USERNAME, username));

			Password passwordManager = new Password();

			return passwordManager.checkPw(password, u.getPassword());
		}catch (ServiceException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante l'esecuzione del login:" +e.getMessage(), e);
			throw e;
		} catch (NotFoundException e1) {
			return false;
		} catch (MultipleResultException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante l'esecuzione del login:" +e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante l'esecuzione del login:" +e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante l'esecuzione del login:" +e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante l'esecuzione del login:" +e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public Utente getLoggedUtente(String username, String password) throws ServiceException{
		if(username ==null && password ==null){
			log.error("Credenziali non presenti");
			return null;
		}

		log.debug("Caricamento delle informazioni per l'utente["+username+"] in corso...");

		Utente u = null;
		try {
			u = this.utenteDAO.find(this.utenteDAO.newExpression()
					.equals(Utente.model().USERNAME, username));

			Password passwordManager = new Password();

			if( passwordManager.checkPw(password, u.getPassword()))
				return u;
			else return null;
		}catch (ServiceException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (NotFoundException e1) {

			return null;
		} catch (MultipleResultException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (NotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (ExpressionNotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (ExpressionException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public Utente getLoggedUtente(String username) throws ServiceException{
		if(username ==null ){
			log.error("Credenziali non presenti");
			return null;
		}

		log.debug("Caricamento delle informazioni per l'utente["+username+"] in corso...");

		Utente u = null;
		try {
			u = this.utenteDAO.find(this.utenteDAO.newExpression()
					.equals(Utente.model().USERNAME, username));

//			Password passwordManager = new Password();
//
//			if( passwordManager.checkPw(password, u.getPassword()))
				return u;
//			else return null;
		}catch (ServiceException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (NotFoundException e1) {

			return null;
		} catch (MultipleResultException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (NotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (ExpressionNotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		} catch (ExpressionException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento delle informazioni per l'utente ["+username+"]:" +e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<Dipartimento> getListaDipartimentiUtente(Utente utente, Ente ente) {
		if(utente ==null){
			log.error("parametro Utente non presente");
			return null;
		}

		List<Dipartimento> listDipartimenti = new ArrayList<Dipartimento>();


		try {
			if(utente.getRole().equals(UserRole.ADMIN)){
				IPaginatedExpression pagExpr =  this.dipartimentoDAO.newPaginatedExpression();
				pagExpr.sortOrder(SortOrder.ASC);
				pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);
				
				List<Map<String,Object>> select = this.dipartimentoDAO.select(pagExpr, true,
						Dipartimento.model().CODICE,Dipartimento.model().DESCRIZIONE,
						Dipartimento.model().ACCETTAZIONE_AUTOMATICA, Dipartimento.model().MODALITA_PUSH);
				
				if(select != null && select.size()  >0)
				for (Map<String,Object> dipMap : select) {
					DipartimentoFetch dipFetch = new DipartimentoFetch();
					listDipartimenti.add((Dipartimento) dipFetch.fetch(DAOFactory.getInstance(log).getServiceManagerProperties().getDatabase(), Dipartimento.model(), dipMap)); 
				}
			} else {
				List<UtenteDipartimento> utenteDipartimentoList = utente.getUtenteDipartimentoList();
				for (UtenteDipartimento utenteDipartimento : utenteDipartimentoList) {
					IdDipartimento idDipartimento = utenteDipartimento.getIdDipartimento();

					Dipartimento dipartimento = this.dipartimentoDAO.get(idDipartimento);

					if(dipartimento != null)
						listDipartimenti.add(dipartimento);
				}
			}
		} catch (ServiceException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (NotFoundException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (MultipleResultException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
			
		} catch (ExpressionException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
			
		} catch (Exception e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		}

		return listDipartimenti;
	}

	@Override
	public Ente getEnte() {
		log.debug("Caricamento delle informazioni dell'ente in corso...");
		try {
			IPaginatedExpression pagExpr = this.enteDAO.newPaginatedExpression();

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Ente.model().NOME);

			List<Ente> findAll = this.enteDAO.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				// Devo avere solo un ente configurato nel db
				if(findAll.size() > 1)
					throw new MultipleResultException("E' stato trovato piu' di un ente.");

				return findAll.get(0);
			}

			throw new NotFoundException ("Ente non trovato.");
		} catch (ServiceException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dell'ente:" +e.getMessage(), e);
		} catch (NotFoundException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dell'ente:" +e.getMessage(), e);
		} catch (MultipleResultException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dell'ente:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dell'ente:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dell'ente:" +e.getMessage(), e);
		} catch (ExpressionException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento dell'ente:" +e.getMessage(), e);
		}

		return null;
	}

}
