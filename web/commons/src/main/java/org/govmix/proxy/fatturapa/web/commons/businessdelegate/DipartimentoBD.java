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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoService;
import org.govmix.proxy.fatturapa.orm.dao.IRegistroService;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.DipartimentoFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.RegistroFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.DipartimentoFetch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.TipiDatabase;

public class DipartimentoBD extends BaseBD {


	protected IDipartimentoService service;
	private IRegistroService registroService;
	private IUtenteService utenteService;

	public DipartimentoBD() throws Exception {
		this(Logger.getLogger(DipartimentoBD.class));
	}

	public DipartimentoBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getDipartimentoService();
		this.registroService = this.serviceManager.getRegistroService();
		this.utenteService = this.serviceManager.getUtenteService();
	}

	public DipartimentoBD(Logger log, Connection connection, boolean autoCommit) throws Exception {
		super(log, connection, autoCommit);
		this.service = this.serviceManager.getDipartimentoService();
		this.registroService = this.serviceManager.getRegistroService();
		this.utenteService = this.serviceManager.getUtenteService();
	}

	public Dipartimento get(IdDipartimento id) throws Exception {
		return this._get(id);
	}
	
	private Dipartimento _get(IdDipartimento id) throws Exception {
		try {
			
			if(!this.service.exists(id)) {
				throw new Exception("Dipartimento con id ["+id.toJson()+"] non esiste.");
			}
			
			return this.service.get(id);
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
	
	public List<Dipartimento> findAll() throws Exception {
		try {
			
			return this.service.findAll(this.service.newPaginatedExpression());
		} catch (ServiceException e) {
			this.log.error("Errore durante la findAll: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findAll: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public boolean isPull(IdDipartimento id) throws Exception {
		try {
			return !this._get(id).getModalitaPush();
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

	private Map<Long, IdRegistro> getRegistriMap() throws Exception {
		Map<Long, IdRegistro> registrimap = new HashMap<Long, IdRegistro>();
		TipiDatabase databaseType = DAOFactory.getInstance().getServiceManagerProperties().getDatabase();

		CustomField cf = new CustomField("id", Long.class, "id", new RegistroFieldConverter(databaseType).toTable(Registro.model()));

		List<Map<String,Object>> select = this.registroService.select(this.registroService.newPaginatedExpression(), Registro.model().NOME, cf);
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
	
	public List<Dipartimento> getListaDipartimentiUtente(String principal) {
		if(principal ==null){
			this.log.error("Principal non specificato");
			return null;
		}

		List<Dipartimento> listDipartimenti = new ArrayList<Dipartimento>();


		try {
			
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(principal);
			
			Utente utente = this.utenteService.get(idUtente);
			if(utente.getRole().equals(UserRole.ADMIN)){
				IPaginatedExpression pagExpr =  this.service.newPaginatedExpression();
				pagExpr.sortOrder(SortOrder.ASC);
				pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);

				TipiDatabase databaseType = DAOFactory.getInstance().getServiceManagerProperties().getDatabase();
				
				CustomField cf = new CustomField("id_registro", Long.class, "id_registro", new DipartimentoFieldConverter(databaseType).toTable(Dipartimento.model()));
				
				List<Map<String,Object>> select = this.service.select(pagExpr, true,
						Dipartimento.model().CODICE,Dipartimento.model().DESCRIZIONE,
						Dipartimento.model().ACCETTAZIONE_AUTOMATICA, Dipartimento.model().MODALITA_PUSH, Dipartimento.model().FATTURAZIONE_ATTIVA,
						Dipartimento.model().ID_PROCEDIMENTO,Dipartimento.model().ID_PROCEDIMENTO_B2B,
						Dipartimento.model().FIRMA_AUTOMATICA,Dipartimento.model().ENTE.NOME, Dipartimento.model().ENTE.NODO_CODICE_PAGAMENTO, Dipartimento.model().ENTE.PREFISSO_CODICE_PAGAMENTO, cf);

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

					Dipartimento dipartimento = this.service.get(idDipartimento);

					if(dipartimento != null)
						listDipartimenti.add(dipartimento);
				}
			}
		} catch (ServiceException e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+principal+"]:" +e.getMessage(), e);
		} catch (NotFoundException e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+principal+"]:" +e.getMessage(), e);
		} catch (MultipleResultException e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+principal+"]:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+principal+"]:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+principal+"]:" +e.getMessage(), e);

		} catch (ExpressionException e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+principal+"]:" +e.getMessage(), e);

		} catch (Exception e) {
			this.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+principal+"]:" +e.getMessage(), e);
		}

		return listDipartimenti;
	}


}
