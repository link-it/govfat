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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.UtenteDipartimento;
import org.govmix.proxy.fatturapa.constants.EsitoType;
import org.govmix.proxy.fatturapa.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.dao.IExtendedFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.dao.IFatturaElettronicaService;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public class FatturaElettronicaBD extends BaseBD {

	private IExtendedFatturaElettronicaServiceSearch serviceSearch;
	private IFatturaElettronicaService service;

	public FatturaElettronicaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getFatturaElettronicaService();
		this.serviceSearch = this.serviceManager.getExtendedFatturaElettronicaServiceSearch();
	}

	public FatturaElettronicaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getFatturaElettronicaService();
		this.serviceSearch = this.serviceManager.getExtendedFatturaElettronicaServiceSearch();
	}

	public FatturaElettronicaBD() throws Exception {
		this(Logger.getLogger(FatturaElettronicaBD.class));
	}

	public IdFattura convertToId(FatturaElettronica fattura) throws Exception {
		try {
			return this.service.convertToId(fattura);
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void create(FatturaElettronica fattura) throws Exception {
		try {

			IdFattura idFattura = this.service.convertToId(fattura);
			if(this.service.exists(idFattura)) {
				throw new Exception("Fattura ["+idFattura.toJson()+"] gia' esistente nel sistema. Impossibile crearne un duplicato.");
			}

			this.service.create(fattura, this.validate);
		} catch (ServiceException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void validate(FatturaElettronica fattura) throws Exception {
		try {

			this.service.validate(fattura);
		} catch (ServiceException e) {
			this.log.error("Errore durante la validate: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la validate: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public FatturaElettronica get(IdFattura id) throws Exception {
		try {

			if(!this.service.exists(id)) {
				throw new Exception("Fattura ["+id.toJson()+"] non esistente nel sistema.");
			}

			return this.service.get(id);
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public boolean exists(IdFattura id) throws Exception {
		try {

			return this.service.exists(id);
		} catch (ServiceException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la exists: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void assegnaProtocolloAInteroLotto(IdLotto idLotto, String protocollo) throws Exception {
		try {

			this.serviceSearch.assegnaProtocolloAInteroLotto(idLotto, protocollo);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void erroreProtocolloAInteroLotto(IdLotto idLotto) throws Exception {
		try {

			this.serviceSearch.assegnaProtocolloAInteroLotto(idLotto, null);

		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateProtocollo(IdFattura idFattura, StatoProtocollazioneType statoProtocollazioneAttuale, String protocollo, boolean asincrono) throws Exception {
		try {
			
			List<UpdateField> lst = new ArrayList<UpdateField>();
			lst.add(new UpdateField(FatturaElettronica.model().STATO_CONSEGNA, StatoConsegnaType.CONSEGNATA));
			lst.add(new UpdateField(FatturaElettronica.model().DETTAGLIO_CONSEGNA, null));
			lst.add(new UpdateField(FatturaElettronica.model().DATA_CONSEGNA, new Date()));
			
			if(asincrono) {
				 // Lo stato potrebbe essere gia' stato aggiornato nel frattempo dal passaggio del batch di associazione protocollo
				// Se non e' stato aggiornato, lo aggiorno 
				if(!StatoProtocollazioneType.PROTOCOLLATA.equals(statoProtocollazioneAttuale))
					lst.add(new UpdateField(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA_IN_ELABORAZIONE));
			} else if(protocollo != null) {
				lst.add(new UpdateField(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA));
				lst.add(new UpdateField(FatturaElettronica.model().DATA_PROTOCOLLAZIONE, new Date()));
				lst.add(new UpdateField(FatturaElettronica.model().PROTOCOLLO, protocollo));
			} else {
				lst.add(new UpdateField(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.NON_PROTOCOLLATA));
			}

			this.service.updateFields(idFattura, lst.toArray(new UpdateField[1]));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoConsegna(IdFattura idFattura, String dettaglio) throws Exception {
		this.updateStatoConsegna(idFattura, StatoConsegnaType.ERRORE_CONSEGNA, dettaglio);
	}

	public void updateStatoConsegna(IdFattura idFattura, StatoConsegnaType statoConsegna, String dettaglio) throws Exception {
		try {
			List<UpdateField> lstFields = new ArrayList<UpdateField>();
			lstFields.add(new UpdateField(FatturaElettronica.model().STATO_CONSEGNA, statoConsegna));
			lstFields.add(new UpdateField(FatturaElettronica.model().DATA_CONSEGNA, new Date()));
			if(dettaglio != null)
				lstFields.add(new UpdateField(FatturaElettronica.model().DETTAGLIO_CONSEGNA, dettaglio));
			
			this.service.updateFields(idFattura, lstFields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateEsito(IdFattura idFattura, EsitoType esito) throws Exception {
		try {
			UpdateField esitoField = new UpdateField(FatturaElettronica.model().ESITO, esito);
			this.service.updateFields(idFattura, esitoField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<IdFattura> getIdFattureByUtente(Utente utente) throws Exception {
		
		try {
			IPaginatedExpression expression = this.serviceSearch.newPaginatedExpression();
			List<String> dipartimenti = new ArrayList<String>();
			
			for(UtenteDipartimento id: utente.getUtenteDipartimentoList()) {
				dipartimenti.add(id.getIdDipartimento().getCodice());
			}
			
			expression.in(FatturaElettronica.model().CODICE_DESTINATARIO, dipartimenti.toArray());
			return this.serviceSearch.findAllIds(expression);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (Exception e) {
			this.log.error("Errore durante la getIdFattureByUtente: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public List<FatturaElettronica> getIdFattureNonConsegnate(IdUtente idUtente, int limit) throws Exception {

		try {
			return this.serviceSearch.findAllFatturePullByUser(new Date(), idUtente, 0, limit);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getIdFattureNonConsegnate: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getIdFattureNonConsegnate: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (Exception e) {
			this.log.error("Errore durante la getIdFattureNonConsegnate: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<FatturaElettronica> getFattureDaSpedire(int offset, int limit, Date date) throws Exception {
		try {
			return this.serviceSearch.findAllFatturePush(date, offset, limit);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaSpedire(Date date) throws Exception {
		try {
			return this.serviceSearch.countFatturePush(date);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la countFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<FatturaElettronica> getFattureDaSpedireContestuale(int offset, int limit, Date date) throws Exception {
		try {
			return this.serviceSearch.findAllFattureContestualePush(date, offset, limit);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaSpedireContestuale(Date date) throws Exception {
		try {
			return this.serviceSearch.countFattureContestualePush(date);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la countFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<IdFattura> findAllIdFatturaByIdentificativoSdi(Integer identificativoSdI) throws Exception {
		try {
			IPaginatedExpression exp = this.serviceSearch.newPaginatedExpression();
			exp.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI, identificativoSdI);
			return this.serviceSearch.findAllIds(exp);
		} catch (ServiceException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la findAllIdFatturaByIdentificativoSdi: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateDecorrenzaTermini(IdFattura idF,
			IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws Exception {
		try {
			FatturaElettronica fattura = this.serviceSearch.get(idF);
			fattura.setIdDecorrenzaTermini(idNotificaDecorrenzaTermini);
			this.service.update(idF, fattura);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<FatturaElettronica> getFattureDaAccettare(int offset, int limit, Date date) throws Exception {
		try {
			return this.serviceSearch.findAllFattureDaAccettare(date, offset, limit);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaAccettare(Date date) throws Exception {
		try {
			return this.serviceSearch.countFattureDaAccettare(date);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la countFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

}
