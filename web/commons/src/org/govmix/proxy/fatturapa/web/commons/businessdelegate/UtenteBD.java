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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.UtenteDipartimento;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.govmix.proxy.fatturapa.dao.IUtenteService;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.utils.crypt.Password;

public class UtenteBD extends BaseBD {


	private IUtenteService service;

	public UtenteBD() throws Exception {
		this(Logger.getLogger(UtenteBD.class));
	}

	public UtenteBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getUtenteService();
	}

	public UtenteBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getUtenteService();
	}


	public boolean belongsTo(IdUtente idUtente, IdDipartimento idDipartimento) throws Exception {
		try {
			
			if(!this.service.exists(idUtente)) {
				throw new Exception("Utente con id ["+idUtente.toJson()+"] non esiste.");
			}
			
			Utente utente = this.service.get(idUtente);
			
			if(utente.getRole().equals(UserRole.ADMIN)) { // L'utente admin viene trattato come appartenente a qualsiasi dipartimento
				return true;
			}
			boolean belongs = false;
			
			for(int i = 0; i < utente.sizeUtenteDipartimentoList() && !belongs; i++) {
				UtenteDipartimento dipartimento = utente.getUtenteDipartimento(i);
				if(dipartimento.getIdDipartimento().equals(idDipartimento)) {
					belongs = true;
				}
			}
			
			return belongs;
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public IdUtente getUtenteAccettatore() throws Exception {
		try {
			
			IPaginatedExpression expression = this.service.newPaginatedExpression();
			expression.equals(Utente.model().ROLE, UserRole.ADMIN);
			List<IdUtente> lstUtenti = this.service.findAllIds(expression);

			if(lstUtenti == null || lstUtenti.size() <=0) {
				throw new Exception("Nessun utente Accettatore definito");
			}
			
			return lstUtenti.get(0);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public boolean checkPw(String username, String password) throws Exception {
		try {
			IdUtente id = new IdUtente();
			id.setUsername(username);
			
			if(!this.service.exists(id)) {
				throw new Exception("Utente ["+username+"] non trovato");
			}
			
			Utente ut = this.service.get(id);
			
			Password pwCheck = new Password();
			
			return pwCheck.checkPw(password, ut.getPassword());
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	

}
