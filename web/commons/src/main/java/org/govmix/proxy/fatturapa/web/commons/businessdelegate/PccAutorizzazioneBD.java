/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdOperazione;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccDipartimentoOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccUtenteOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteServiceSearch;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public class PccAutorizzazioneBD extends BaseBD {

	private IUtenteServiceSearch utenteSearch;
	private IDipartimentoServiceSearch dipartimentoSearch;
	private IPccUtenteOperazioneServiceSearch utenteOperazioneSearch;
	private IPccDipartimentoOperazioneServiceSearch dipartimentoOperazioneSearch;
	
	public PccAutorizzazioneBD() throws Exception {
		this(Logger.getLogger(PccAutorizzazioneBD.class));
	}

	public PccAutorizzazioneBD(Logger log) throws Exception {
		super(log);
		this.utenteSearch = this.serviceManager.getUtenteServiceSearch();
		this.dipartimentoSearch = this.serviceManager.getDipartimentoServiceSearch();
		this.utenteOperazioneSearch = this.serviceManager.getPccUtenteOperazioneServiceSearch();
		this.dipartimentoOperazioneSearch= this.serviceManager.getPccDipartimentoOperazioneServiceSearch();
	}

	public PccAutorizzazioneBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.utenteSearch = this.serviceManager.getUtenteServiceSearch();
		this.dipartimentoSearch = this.serviceManager.getDipartimentoServiceSearch();
		this.utenteOperazioneSearch = this.serviceManager.getPccUtenteOperazioneServiceSearch();
		this.dipartimentoOperazioneSearch= this.serviceManager.getPccDipartimentoOperazioneServiceSearch();
	}

	public boolean isAuthorized(IdUtente idUtente, IdOperazione operazione) throws Exception {
		try {
			
			if(!this.utenteSearch.exists(idUtente)) {
				throw new Exception("Utente con id ["+idUtente.toJson()+"] non esiste.");
			}
			
			IPaginatedExpression exp = this.utenteOperazioneSearch.newPaginatedExpression();
			exp.equals(PccUtenteOperazione.model().ID_UTENTE.USERNAME, idUtente.getUsername());
			
			List<PccUtenteOperazione> utOp = this.utenteOperazioneSearch.findAll(exp); 
			
			for(PccUtenteOperazione utenteOperazione: utOp) {
				if(utenteOperazione.getIdOperazione().getNome().equals(operazione.getNome())) {
					return true;
				}
			}
			
			return false;
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la belongsTo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la belongsTo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la belongsTo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la belongsTo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public boolean isAuthorized(IdDipartimento idDipartimento, IdOperazione operazione) throws Exception {
		try {
			
			if(!this.dipartimentoSearch.exists(idDipartimento)) {
				throw new Exception("Utente con id ["+idDipartimento.toJson()+"] non esiste.");
			}
			
			IPaginatedExpression exp = this.dipartimentoOperazioneSearch.newPaginatedExpression();
			exp.equals(PccDipartimentoOperazione.model().ID_DIPARTIMENTO.CODICE, idDipartimento.getCodice());
			
			List<PccDipartimentoOperazione> dipOp = this.dipartimentoOperazioneSearch.findAll(exp); 
			
			for(PccDipartimentoOperazione utenteOperazione: dipOp) {
				if(utenteOperazione.getIdOperazione().getNome().equals(operazione.getNome())) {
					return true;
				}
			}
			
			return false;
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la isAuthorized: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la isAuthorized: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la isAuthorized: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la isAuthorized: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
}
