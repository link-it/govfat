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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IExtendedFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.pcc.fatture.tracciamento.OperazioneNonPermessaException;
import org.openspcoop2.generic_project.beans.CustomField;
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
				throw new NotFoundException("Fattura ["+id.toJson()+"] non esistente nel sistema.");
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

	public FatturaElettronica findByIdSdiNumero(Integer identificativoSdi, String numero) throws Exception {
		try {

			IPaginatedExpression exp = this.service.newPaginatedExpression();
			exp.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI, identificativoSdi);
			exp.equals(FatturaElettronica.model().NUMERO, numero);
			
			List<FatturaElettronica> findAllIds = this.service.findAll(exp);
			
			if(findAllIds == null || findAllIds.isEmpty())
				throw new NotFoundException();
			if(findAllIds.size() > 1)
				throw new MultipleResultException();
			
			return findAllIds.get(0);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public IdFattura findId(long idFisico) throws Exception {
		try {
			return this.service.convertToId(this.getById(idFisico));

		} catch (ServiceException e) {
			this.log.error("Errore durante la findId: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la findId: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public FatturaElettronica getById(long idFisico) throws Exception {
		try {
			return ((IDBFatturaElettronicaServiceSearch)this.service).get(idFisico);

		} catch (ServiceException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public FatturaElettronica findByIdFiscaleNumeroDataImporto(String idFiscaleFornitore, String numero, Date date, Double importo) throws Exception {
		try {

			
			String codNazione = null;
			String idFiscale = null;
			IPaginatedExpression exp = this.service.newPaginatedExpression();
			
			if(idFiscaleFornitore.length() > 2) {
				codNazione = idFiscaleFornitore.substring(0, 2);
				idFiscale = idFiscaleFornitore.substring(2);
				exp.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE, codNazione);
			} else {
				idFiscale = idFiscaleFornitore;
			}
			
			exp.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE, idFiscale);
			exp.equals(FatturaElettronica.model().NUMERO, numero);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date.getTime());
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTimeInMillis(cal.getTimeInMillis());
			cal2.add(Calendar.DATE, 1);
			
			exp.between(FatturaElettronica.model().DATA, date, cal2.getTime());

			
			if(importo != null)
				exp.equals(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO, importo);

			List<FatturaElettronica> findAllIds = this.service.findAll(exp);
			
			if(findAllIds == null || findAllIds.isEmpty())
				throw new NotFoundException();
			if(findAllIds.size() > 1)
				throw new MultipleResultException();
			
			return findAllIds.get(0);
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la get: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public FatturaElettronica findByIdPcc(String idPcc) throws Exception {
//		try {
//
//			IPaginatedExpression exp = this.service.newPaginatedExpression();
//			exp.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI, identificativoSdi);
//			exp.equals(FatturaElettronica.model().NUMERO, numero);
//			
//			List<IdFattura> findAllIds = this.service.findAllIds(exp);
//			
//			if(findAllIds == null || findAllIds.isEmpty())
//				throw new NotFoundException();
//			if(findAllIds.size() > 1)
				throw new NotImplementedException();
//			
//			return findAllIds.get(0);
//			
//		} catch (ServiceException e) {
//			this.log.error("Errore durante la get: " + e.getMessage(), e);
//			throw new Exception(e);
//		} catch (NotImplementedException e) {
//			this.log.error("Errore durante la get: " + e.getMessage(), e);
//			throw new Exception(e);
//		}
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

	public boolean existFatture() throws Exception {
		try {

			return this.service.count(this.service.newExpression()).longValue() > 0;
		} catch (ServiceException e) {
			this.log.error("Errore durante la existFatture: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la existFatture: " + e.getMessage(), e);
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

	public void updateStatoConsegna(FatturaElettronica fattura) throws Exception {
		try {
			List<UpdateField> lstFields = new ArrayList<UpdateField>();
			lstFields.add(new UpdateField(FatturaElettronica.model().STATO_CONSEGNA, fattura.getStatoConsegna()));
			lstFields.add(new UpdateField(FatturaElettronica.model().DATA_CONSEGNA, new Date()));
			lstFields.add(new UpdateField(FatturaElettronica.model().DETTAGLIO_CONSEGNA, fattura.getDettaglioConsegna()));
			lstFields.add(new UpdateField(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA, fattura.getDataProssimaConsegna()));
			lstFields.add(new UpdateField(FatturaElettronica.model().TENTATIVI_CONSEGNA, fattura.getTentativiConsegna()));

			this.service.updateFields(this.service.convertToId(fattura), lstFields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateDaPagare(IdFattura idFattura, boolean daPagare) throws Exception {
		try {
			List<UpdateField> lstFields = new ArrayList<UpdateField>();
			lstFields.add(new UpdateField(FatturaElettronica.model().DA_PAGARE, daPagare));

			this.service.updateFields(idFattura, lstFields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateDaPagare: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateDaPagare: " + e.getMessage(), e);
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
	public void forzaRispedizioneFattura(FatturaElettronica fattura) throws Exception {
		try {
			IdFattura idFattura = this.serviceSearch.convertToId(fattura); 
			FatturaElettronica fatturaDaDB = this.serviceSearch.get(idFattura);
			if(StatoConsegnaType.ERRORE_CONSEGNA.equals(fatturaDaDB.getStatoConsegna()) || StatoConsegnaType.IN_RICONSEGNA.equals(fatturaDaDB.getStatoConsegna())) {
				fatturaDaDB.setStatoConsegna(StatoConsegnaType.IN_RICONSEGNA);
				fatturaDaDB.setDataProssimaConsegna(new Date());
				fatturaDaDB.setTentativiConsegna(new Integer(0));
				this.service.update(idFattura, fatturaDaDB);
			} else
				throw new Exception("La fattura ["+idFattura.toJson()+"] non puo' essere forzata in riconsegna perche' il suo stato attuale e' ["+fatturaDaDB.getStatoConsegna()+"]");
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la forzaRispedizioneFattura: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la forzaRispedizioneFattura: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateEsitoScadenza(IdFattura idFattura, PccTracciaTrasmissioneEsito esito, boolean existScadenze)  throws Exception {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField esitoCustomField = new CustomField("id_scadenza", Long.class, "id_scadenza", converter.toTable(FatturaElettronica.model()));
			UpdateField esitoField = null;
			if(esito != null) {
				esitoField = new UpdateField(esitoCustomField, esito.getId());
			}else {
				FatturaElettronica f = this.get(idFattura);
				if((f.getIdEsitoScadenza() == null || f.getIdEsitoScadenza().getIdTrasmissioneEsito() < 0) && existScadenze) {
					throw new OperazioneNonPermessaException("Fattura ["+idFattura.toJson()+"] in stato di elaborazione, impossibile inviare un piano di scadenze");
				} else {
					esitoField = new UpdateField(esitoCustomField, null);					
				}
			}
			this.service.updateFields(idFattura, esitoField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateEsitoContabilizzazione(IdFattura idFattura, PccTracciaTrasmissioneEsito esito, boolean existContabilizzazioni)  throws Exception {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField esitoCustomField = new CustomField("id_contabilizzazione", Long.class, "id_contabilizzazione", converter.toTable(FatturaElettronica.model()));
			UpdateField esitoField = null;
			if(esito != null) {
				esitoField = new UpdateField(esitoCustomField, esito.getId());
			}else {
				FatturaElettronica f = this.get(idFattura);
				if((f.getIdEsitoContabilizzazione() == null || f.getIdEsitoContabilizzazione().getIdTrasmissioneEsito() < 0) && existContabilizzazioni) {
					throw new OperazioneNonPermessaException("Fattura ["+idFattura.toJson()+"] in stato di elaborazione, impossibile inviare un piano di contabilizzazioni");
				} else {
					esitoField = new UpdateField(esitoCustomField, null);					
				}
			}

			this.service.updateFields(idFattura, esitoField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateEsito: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void aggiornaDataScadenza(IdFattura idFattura, Date dataScadenza, boolean daPagare) throws Exception {
		try {
			
			UpdateField updateField = new UpdateField(FatturaElettronica.model().DATA_SCADENZA, dataScadenza);
			UpdateField updateField2 = new UpdateField(FatturaElettronica.model().DA_PAGARE, daPagare);

			this.service.updateFields(idFattura, updateField, updateField2);
		} catch (ServiceException e) {
			this.log.error("Errore durante la aggiornaDataScadenza: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la aggiornaDataScadenza: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

}
