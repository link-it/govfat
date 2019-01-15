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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.govmix.proxy.pcc.fatture.tracciamento.OperazioneNonPermessaException;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class FatturaPassivaBD extends FatturaBD {

	public FatturaPassivaBD(Logger log) throws Exception {
		super(log);
	}

	public FatturaPassivaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
	}

	public FatturaPassivaBD() throws Exception {
		this(Logger.getLogger(FatturaPassivaBD.class));
	}

	public FatturaPassivaFilter newFilter() {
		return new FatturaPassivaFilter(this.service);
	}
	
	public IdFattura newIdFattura() {
		return new IdFattura(false);
	}
	
	public List<FatturaElettronica> getFattureDaSpedireContestuale(int offset, int limit, Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, true);
			filter.setOffset(offset);
			filter.setLimit(limit);
			return this.findAll(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaSpedireContestuale(Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, true);
			return this.count(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaSpedireContestuale: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	public List<FatturaElettronica> getFattureDaSpedire(int offset, int limit, Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, false);
			return this.findAll(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaSpedire(Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaSpedireFilter(date, false);
			return this.count(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaSpedire: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private FatturaPassivaFilter getFattureDaSpedireFilter(Date date, boolean contestuale) {
		FatturaPassivaFilter filter = this.newFilter();
		filter.setModalitaPush(true);
		filter.setConsegnaContestuale(contestuale);
		filter.setDataRicezioneMax(date);
		List<StatoConsegnaType> statiConsegna = new ArrayList<StatoConsegnaType>();
		statiConsegna.add(StatoConsegnaType.NON_CONSEGNATA);
		statiConsegna.add(StatoConsegnaType.IN_RICONSEGNA);
		filter.setStatiConsegna(statiConsegna);
		return filter;
	}

	public List<FatturaElettronica> getFattureDaAccettare(int offset, int limit, Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaAccettareFilter(date);
			filter.setOffset(offset);
			filter.setLimit(limit);
			return this.findAll(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la getFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public long countFattureDaAccettare(Date date) throws Exception {
		try {
			FatturaPassivaFilter filter = getFattureDaAccettareFilter(date);
			return this.count(filter);
		} catch (ServiceException e) {
			this.log.error("Errore durante la countFattureDaAccettare: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	private FatturaPassivaFilter getFattureDaAccettareFilter(Date date) {
		FatturaPassivaFilter filter = this.newFilter();
		filter.setDataRicezioneMax(date);
		filter.setDaAccettareAutomaticamente(true);
		return filter;
	}
	

	public List<IdFattura> getIdFattureRicevute(Integer idSdI) throws Exception {
		try {
			FatturaPassivaFilter filter = this.newFilter();
			filter.setIdentificativoSdi(idSdI);

			return this.service.findAllIds(filter.toPaginatedExpression());
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

	public void updateDecorrenzaTermini(IdFattura idF,
			IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini) throws Exception {
		try {
			FatturaElettronica fattura = this.get(idF);
			fattura.setIdDecorrenzaTermini(idNotificaDecorrenzaTermini);
			this.service.update(idF, fattura);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateDecorrenzaTermini: " + e.getMessage(), e);
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

	public void forzaRispedizioneFattura(FatturaElettronica fattura) throws Exception {
		try {
			IdFattura idFattura = this.convertToId(fattura); 
			FatturaElettronica fatturaDaDB = this.get(idFattura);
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


	public void updateEsitoScadenza(IdFattura idFattura, PccTracciaTrasmissioneEsito esito)  throws Exception {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField esitoCustomField = new CustomField("id_scadenza", Long.class, "id_scadenza", converter.toTable(FatturaElettronica.model()));
			UpdateField esitoField = null;
			if(esito != null) {
				esitoField = new UpdateField(esitoCustomField, esito.getId());
			}else {
				esitoField = new UpdateField(esitoCustomField, null);					
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

	public void updateEsitoContabilizzazione(IdFattura idFattura, PccTracciaTrasmissioneEsito esito)  throws Exception {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField esitoCustomField = new CustomField("id_contabilizzazione", Long.class, "id_contabilizzazione", converter.toTable(FatturaElettronica.model()));
			UpdateField esitoField = null;
			if(esito != null) {
				esitoField = new UpdateField(esitoCustomField, esito.getId());
			}else {
				esitoField = new UpdateField(esitoCustomField, null);					
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

	public void checkEsitoScadenza(IdFattura idFattura)  throws Exception {
		try {
			FatturaElettronica f = this.get(idFattura);
			if((f.getIdEsitoScadenza() == null || f.getIdEsitoScadenza().getIdTrasmissioneEsito() < 0)) {
				throw new OperazioneNonPermessaException("Fattura ["+idFattura.toJson()+"] in stato di elaborazione, impossibile inviare un piano di scadenze");
			}
		} catch (ServiceException e) {
			this.log.error("Errore durante la checkEsitoScadenza: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void checkEsitoContabilizzazione(IdFattura idFattura)  throws Exception {
		try {
			FatturaElettronica f = this.get(idFattura);
			if((f.getIdEsitoContabilizzazione() == null || f.getIdEsitoContabilizzazione().getIdTrasmissioneEsito() < 0)) {
				throw new OperazioneNonPermessaException("Fattura ["+idFattura.toJson()+"] in stato di elaborazione, impossibile inviare un piano di contabilizzazioni");
			}

		} catch (ServiceException e) {
			this.log.error("Errore durante la checkEsitoContabilizzazione: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void assegnaIdSip(FatturaElettronica fattura, Long id) throws ServiceException {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 
			CustomField idSipCustomField = new CustomField("id_sip", Long.class, "id_sip", converter.toTable(FatturaElettronica.model()));
			UpdateField sipUpdateField = new UpdateField(idSipCustomField, id);

			((IDBFatturaElettronicaService)this.service).updateFields(fattura.getId(), sipUpdateField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la assegnaIdSip: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
}
