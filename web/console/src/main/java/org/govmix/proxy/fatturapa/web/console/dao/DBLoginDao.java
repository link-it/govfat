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
package org.govmix.proxy.fatturapa.web.console.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.Evento;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IEnteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IEventoService;
import org.govmix.proxy.fatturapa.orm.dao.IProtocolloServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.DipartimentoFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.RegistroFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.DipartimentoFetch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.crypt.Password;

/**
 * DBLoginDao Implementa il login per l'utente della console.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class DBLoginDao implements ILoginDao{

	private IUtenteServiceSearch utenteDAO = null;
	private IDipartimentoServiceSearch dipartimentoDAO = null;
	private IRegistroServiceSearch registroDAO = null;
	private IEnteServiceSearch enteDAO = null;
	private IEventoService eventoDAO = null;
	private IProtocolloServiceSearch protocolloSearchDAO = null;

	private static Logger log = LoggerManager.getDaoLogger();

	public DBLoginDao() {
		try{
			this.utenteDAO = DAOFactory.getInstance().getServiceManager().getUtenteServiceSearch();
			this.dipartimentoDAO = DAOFactory.getInstance().getServiceManager().getDipartimentoServiceSearch();
			this.registroDAO = DAOFactory.getInstance().getServiceManager().getRegistroServiceSearch();
			this.enteDAO = DAOFactory.getInstance().getServiceManager().getEnteServiceSearch();
			this.eventoDAO = DAOFactory.getInstance().getServiceManager().getEventoService();
			this.protocolloSearchDAO = DAOFactory.getInstance().getServiceManager().getProtocolloServiceSearch();
		}catch(Exception e){
			DBLoginDao.log.error("Si e' verificato un errore durante la creazione del LoginDAO: "+e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean login(String username, String password) throws ServiceException  {
		if(username ==null && password ==null){
			DBLoginDao.log.error("Credenziali non presenti");
			return false;
		}

		DBLoginDao.log.debug("Login per l'utente["+username+"] in corso...");

		Utente u;
		try {
			u = this.utenteDAO.find(this.utenteDAO.newExpression()
					.equals(Utente.model().USERNAME, username).and().equals(Utente.model().ESTERNO, false)); 

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
			DBLoginDao.log.error("Credenziali non presenti");
			return null;
		}

		DBLoginDao.log.debug("Caricamento delle informazioni per l'utente["+username+"] in corso...");

		Utente u = null;
		try {
			u = this.utenteDAO.find(this.utenteDAO.newExpression()
					.equals(Utente.model().USERNAME, username).and().equals(Utente.model().ESTERNO, false));

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
			DBLoginDao.log.error("Credenziali non presenti");
			return null;
		}

		DBLoginDao.log.debug("Caricamento delle informazioni per l'utente["+username+"] in corso...");

		Utente u = null;
		try {
			u = this.utenteDAO.find(this.utenteDAO.newExpression()
					.equals(Utente.model().USERNAME, username).and().equals(Utente.model().ESTERNO, false));

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

	
	private Map<Long, IdRegistro> getRegistriMap() throws Exception {
		Map<Long, IdRegistro> registrimap = new HashMap<Long, IdRegistro>();
		TipiDatabase databaseType = DAOFactory.getInstance().getServiceManagerProperties().getDatabase();

		CustomField cf = new CustomField("id", Long.class, "id", new RegistroFieldConverter(databaseType).toTable(Registro.model()));

		List<Map<String,Object>> select = this.registroDAO.select(this.registroDAO.newPaginatedExpression(), Registro.model().NOME, cf);
		if(select != null && select.size()  >0) {
			for(Map<String,Object> record: select) {
				Long idRegistro = (Long) record.get("id");
				String name = (String) record.get(Registro.model().NOME.getFieldName());
				
				IdRegistro idRegistroObj = new IdRegistro();
				idRegistroObj.setNome(name);
				registrimap.put(idRegistro, idRegistroObj);

			}
		}
		return registrimap;
	}
	
	@Override
	public List<Dipartimento> getListaDipartimentiUtente(Utente utente) {
		if(utente ==null){
			DBLoginDao.log.error("parametro Utente non presente");
			return null;
		}

		List<Dipartimento> listDipartimenti = new ArrayList<Dipartimento>();


		try {
			if(utente.getRole().equals(UserRole.ADMIN)){
				IPaginatedExpression pagExpr =  this.dipartimentoDAO.newPaginatedExpression();
				pagExpr.sortOrder(SortOrder.ASC);
				pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);

				TipiDatabase databaseType = DAOFactory.getInstance().getServiceManagerProperties().getDatabase();
				
				CustomField cf = new CustomField("id_registro", Long.class, "id_registro", new DipartimentoFieldConverter(databaseType).toTable(Dipartimento.model()));
				
				List<Map<String,Object>> select = this.dipartimentoDAO.select(pagExpr, true,
						Dipartimento.model().CODICE,Dipartimento.model().DESCRIZIONE,
						Dipartimento.model().ACCETTAZIONE_AUTOMATICA, Dipartimento.model().MODALITA_PUSH, Dipartimento.model().FATTURAZIONE_ATTIVA,
						Dipartimento.model().ID_PROCEDIMENTO,Dipartimento.model().ID_PROCEDIMENTO_B2B,
						Dipartimento.model().FIRMA_AUTOMATICA,Dipartimento.model().ENTE.NOME,Dipartimento.model().ENTE.NODO_CODICE_PAGAMENTO,Dipartimento.model().ENTE.PREFISSO_CODICE_PAGAMENTO, cf);

				Map<Long, IdRegistro> registrimap = getRegistriMap();
				if(select != null && select.size()  >0)
					for (Map<String,Object> dipMap : select) {
						DipartimentoFetch dipFetch = new DipartimentoFetch();
						Dipartimento dipartimento = (Dipartimento) dipFetch.fetch(databaseType, Dipartimento.model(), dipMap);
						Object idRegistroObject = dipMap.get("id_registro");
						if(idRegistroObject instanceof Long) {
							Long idRegistro = (Long)idRegistroObject;
							if(!registrimap.containsKey(idRegistro)) {
								throw new ServiceException("Registro con id ["+idRegistro+"] riferito dal dipartimento ["+dipartimento.getCodice()+"] non esiste");
							}
							
							dipartimento.setRegistro(registrimap.get(idRegistro));
						}
						
						IdEnte ente = (IdEnte) dipFetch.fetch(databaseType, Dipartimento.model().ENTE, dipMap);
						dipartimento.setEnte(ente);
						
						listDipartimenti.add(dipartimento); 
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
	public Ente getEnte(String nomeEnte) {
		DBLoginDao.log.debug("Caricamento delle informazioni dell'ente in corso...");
		try {
			IExpression expr = this.enteDAO.newExpression();
			expr.equals(Ente.model().NOME, nomeEnte);

			Ente ente = this.enteDAO.find(expr);
			
			return ente;
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
	@Override
	public Protocollo getProtocollo(String nomeProtocollo) {
		DBLoginDao.log.debug("Caricamento delle informazioni del protocollo in corso...");
		try {
			IExpression expr = this.protocolloSearchDAO.newExpression();
			expr.equals(Protocollo.model().NOME, nomeProtocollo);
			return this.protocolloSearchDAO.find(expr);
		} catch (ServiceException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento del protocollo:" +e.getMessage(), e);
		} catch (NotFoundException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento del protocollo:" +e.getMessage(), e);
		} catch (MultipleResultException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento del protocollo:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento del protocollo:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento del protocollo:" +e.getMessage(), e);
		} catch (ExpressionException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante il caricamento del protocollo:" +e.getMessage(), e);
		}

		return null;
	}

	@Override
	public void registraEvento(Evento evento) throws ServiceException {
		log.debug("Registrazione Evento ["+evento.getTipo()+"] in corso...");

		try {
			this.eventoDAO.create(evento);
		}  catch (NotImplementedException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante Registrazione Evento ["+evento.getTipo()+"]:" +e.getMessage(), e);
			throw new ServiceException(e);
		}	catch (ServiceException e) {
			DBLoginDao.log.error("Si e' verificato un errore durante Registrazione Evento ["+evento.getTipo()+"]:" +e.getMessage(), e);
			throw e;
		}

	}
}
