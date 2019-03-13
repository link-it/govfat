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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.IdSip;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.SIP;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.ISIPServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.SIPFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.FatturaElettronicaFetch;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.openspcoop2.generic_project.beans.AliasField;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBC_SQLObjectFactory;
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
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLQueryObjectException;

public class FatturaBD extends BaseBD {

	protected IFatturaElettronicaService service;

	public FatturaBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getFatturaElettronicaService();
	}

	public FatturaBD() throws Exception {
		this(Logger.getLogger(FatturaBD.class));
	}

	public FatturaFilter newFilter(Boolean fatturazioneAttiva) {
		return new FatturaFilter(this.service,fatturazioneAttiva);
	}

	public void create(FatturaElettronica fattura) throws ServiceException {
		try {
			this.service.create(fattura);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
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

	public FatturaElettronica getById(long idFisico) throws ServiceException, NotFoundException {
		try {
			return ((IDBFatturaElettronicaService)this.service).get(idFisico);

		} catch (NotImplementedException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			this.log.error("Errore durante la getById: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}


	public FatturaElettronica get(IdFattura id) throws NotFoundException, ServiceException {
		FatturaFilter filter = this.newFilter();
		filter.setIdentificativoSdi(id.getIdentificativoSdi());
		filter.setPosizione(id.getPosizione());

		if(this.count(filter) == 0) {
			throw new NotFoundException();
		}

		return this.findAll(filter).get(0);
	}

	public FatturaElettronica findByIdSdiNumero(Integer identificativoSdi, String numero) throws Exception {
		try {

			FatturaFilter filter = this.newFilter();
			filter.setIdentificativoSdi(identificativoSdi);
			filter.setNumero(numero);

			if(this.count(filter) == 0) {
				throw new NotFoundException();
			}

			return this.findAll(filter).get(0);

		} catch (ServiceException e) {
			throw new Exception(e);
		}
	}

	public FatturaElettronica findByMessageId(String messageId) throws Exception {
		try {

			FatturaFilter filter = this.newFilter();
			filter.setMessageId(messageId);

			if(this.count(filter) == 0) {
				throw new NotFoundException();
			}

			return this.findAll(filter).get(0);

		} catch (ServiceException e) {
			throw new Exception(e);
		}
	}

	public FatturaElettronica findByCodDipartimentoNumeroData(String codiceDipartimento, String numero, Date dataFattura) throws Exception {
		try {
			List<StatoElaborazioneType> fatturaInviataSdi = new ArrayList<StatoElaborazioneType>();
			fatturaInviataSdi = new ArrayList<StatoElaborazioneType>();
			fatturaInviataSdi.add(StatoElaborazioneType.RICEVUTA_DALLO_SDI);
			fatturaInviataSdi.add(StatoElaborazioneType.RICEVUTA_DAL_DESTINATARIO);
			fatturaInviataSdi.add(StatoElaborazioneType.IMPOSSIBILITA_DI_RECAPITO);
			fatturaInviataSdi.add(StatoElaborazioneType.MANCATA_CONSEGNA);
			fatturaInviataSdi.add(StatoElaborazioneType.RICEVUTA_DECORRENZA_TERMINI);
			fatturaInviataSdi.add(StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE);
			fatturaInviataSdi.add(StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO);

			FatturaFilter filter = this.newFilter();
			filter.setCodiceDestinatario(codiceDipartimento);
			filter.setNumero(numero);
			
			filter.setDataFattura(dataFattura);

			FilterSortWrapper sort = new FilterSortWrapper();
			sort.setField(FatturaElettronica.model().DATA_RICEZIONE);
			sort.setSortOrder(SortOrder.DESC);
			filter.getFilterSortList().add(sort);
			
			if(this.count(filter) == 0) {
				throw new NotFoundException();
			}

			List<FatturaElettronica> findAll = this.findAll(filter);
			for(FatturaElettronica fat: findAll) {
				if(fatturaInviataSdi.contains(fat.getLottoFatture().getStatoElaborazioneInUscita())) {
					return fat;
				}
			}
			
			return findAll.get(0);

		} catch (ServiceException e) {
			throw new Exception(e);
		}
	}

	public IdFattura findId(long idFisico) throws ServiceException, NotFoundException {
		return this.convertToId(this.getById(idFisico));
	}

	public FatturaElettronica findByIdFiscaleNumeroDataImporto(String idFiscaleFornitore, String numero, Date date, Double importo) throws ServiceException, NotFoundException {
		try {


			String codNazione = null;
			String idFiscale = null;

			FatturaFilter filter = this.newFilter();

			if(idFiscaleFornitore.length() > 2) {
				codNazione = idFiscaleFornitore.substring(0, 2);
				idFiscale = idFiscaleFornitore.substring(2);
				filter.setCpPaese(codNazione);
			} else {
				idFiscale = idFiscaleFornitore;
			}

			filter.setCpCodiceFiscale(idFiscale);
			filter.setNumero(numero);

			Calendar dataMin = Calendar.getInstance();
			dataMin.setTimeInMillis(date.getTime());
			dataMin.set(Calendar.HOUR, 0);
			dataMin.set(Calendar.MINUTE, 0);
			dataMin.set(Calendar.SECOND, 0);

			Calendar dataMax = Calendar.getInstance();
			dataMax.setTimeInMillis(dataMin.getTimeInMillis());
			dataMax.add(Calendar.DATE, 1);

			filter.setDataFatturaMin(dataMin.getTime());
			filter.setDataFatturaMax(dataMax.getTime());

			if(importo != null)
				filter.setImporto(importo);

			List<FatturaElettronica> findAllIds = this.findAll(filter);

			if(findAllIds == null || findAllIds.isEmpty())
				throw new NotFoundException();
			if(findAllIds.size() > 1)
				throw new MultipleResultException();

			return findAllIds.get(0);

		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		}
	}

	public FatturaElettronica findByIdPcc(String idPcc) throws Exception {
		throw new NotImplementedException();
	}

	public boolean exists(IdFattura id) throws ServiceException {
		FatturaFilter filter = this.newFilter();
		filter.setIdentificativoSdi(id.getIdentificativoSdi());
		filter.setPosizione(id.getPosizione());
		return this.count(filter) > 0;
	}

	public List<IdFattura> findAllIdFatturaByIdLotto(IdLotto idLotto) throws ServiceException {
		FatturaFilter filter = this.newFilter();
		filter.setIdentificativoSdi(idLotto.getIdentificativoSdi());
		filter.setFatturazioneAttiva(idLotto.getFatturazioneAttiva());
		List<FatturaElettronica> findAll = this.findAll(filter);
		List<IdFattura> findAllIds = new ArrayList<IdFattura>();
		for(FatturaElettronica fatt: findAll) {
			findAllIds.add(this.convertToId(fatt));
		}

		return findAllIds;
	}

	public IdFattura convertToId(FatturaElettronica fattura) throws ServiceException {
		try {
			return this.service.convertToId(fattura);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new ServiceException(e);
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

	public void aggiornaProtocollo(IdFattura idFattura, String protocollo) throws Exception {
		try {
			this.service.updateFields(idFattura, new UpdateField(FatturaElettronica.model().PROTOCOLLO, protocollo));
		} catch (ServiceException e) {
			this.log.error("Errore durante la aggiornaProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la aggiornaProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<IdFattura> getIdFattureByUtente(Utente utente) throws Exception {

		try {

			FatturaFilter newFilter = newFilter();
			newFilter.setUtente(utente);

			return this.service.findAllIds(newFilter.toPaginatedExpression());
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

	public long count(FatturaFilter filter)throws ServiceException {
		try {
			return this.service.count(filter.toExpression()).longValue();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<FatturaElettronica> findAll(FatturaFilter filter)throws ServiceException {
		try {
			return this.service.findAll(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<IdFattura> findAllIds(FatturaFilter filter)throws ServiceException {
		try {
			return this.service.findAllIds(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<Long> findAllTableIds(FatturaFilter filter)throws ServiceException {
		try {
			return ((IDBFatturaElettronicaService)this.service).findAllTableIds(filter.toPaginatedExpression());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<Map<String,Object>> select(FatturaFilter filter, IField... fields) throws ServiceException {
		try {
			return this.service.select(filter.toPaginatedExpression(), fields);
		} catch (NotFoundException e) {
			return new ArrayList<Map<String,Object>>();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	private IField getCustomField(IField field, String table) throws ExpressionException, ServiceException {
		
		
		String columnName = new FatturaElettronicaFieldConverter(this.getDatabaseType()).toColumn(field, false);
		String aliasTableName = table.substring(0,  1);
		String aliasColumnName = aliasTableName + columnName;
		
		return new AliasField(field, aliasColumnName);
	}
	
	public List<FatturaElettronica> fatturaElettronicaSelectForListaFatture(FatturaFilter filter) throws ServiceException {
		List<IField> fields = new ArrayList<IField>();
		
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.getDatabaseType());
			String id = "id";
			fields.add(new CustomField(id, Long.class, id, converter.toTable(FatturaElettronica.model())));
			fields.add(FatturaElettronica.model().FORMATO_TRASMISSIONE);
			fields.add(FatturaElettronica.model().IDENTIFICATIVO_SDI);
			fields.add(FatturaElettronica.model().POSIZIONE);
			fields.add(FatturaElettronica.model().FATTURAZIONE_ATTIVA);
			fields.add(FatturaElettronica.model().DATA_RICEZIONE);
			fields.add(FatturaElettronica.model().NOME_FILE);
			fields.add(FatturaElettronica.model().MESSAGE_ID);
			fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
			fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE);
			fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE);
			fields.add(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE);
			fields.add(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE);
			fields.add(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE);
			fields.add(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE);
			fields.add(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE);
			fields.add(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE);
			fields.add(FatturaElettronica.model().CODICE_DESTINATARIO);
			fields.add(FatturaElettronica.model().TIPO_DOCUMENTO);
			fields.add(FatturaElettronica.model().DIVISA);
			fields.add(FatturaElettronica.model().DATA);
			fields.add(FatturaElettronica.model().ANNO);
			fields.add(FatturaElettronica.model().NUMERO);
			fields.add(FatturaElettronica.model().ESITO);
			fields.add(FatturaElettronica.model().DA_PAGARE);
			fields.add(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO);
			fields.add(FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO);
			fields.add(FatturaElettronica.model().CAUSALE);
			fields.add(FatturaElettronica.model().STATO_CONSEGNA);
			fields.add(FatturaElettronica.model().DATA_CONSEGNA);
			fields.add(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA);
			fields.add(FatturaElettronica.model().DATA_SCADENZA);
			fields.add(FatturaElettronica.model().TENTATIVI_CONSEGNA);
			fields.add(FatturaElettronica.model().DETTAGLIO_CONSEGNA);
			fields.add(FatturaElettronica.model().STATO_PROTOCOLLAZIONE);
			fields.add(FatturaElettronica.model().STATO_CONSERVAZIONE);
			fields.add(FatturaElettronica.model().DATA_PROTOCOLLAZIONE);
			fields.add(FatturaElettronica.model().PROTOCOLLO);
			String idDecorrenzaTerminiField = "id_notifica_decorrenza_termini";
			String idEsitoContabilizzazioneField = "id_contabilizzazione";
			String idEsitoScadenzaField = "id_scadenza";
			fields.add(new CustomField(idDecorrenzaTerminiField, Long.class, idDecorrenzaTerminiField, converter.toTable(FatturaElettronica.model())));
			fields.add(new CustomField(idEsitoContabilizzazioneField, Long.class, idEsitoContabilizzazioneField, converter.toTable(FatturaElettronica.model())));
			fields.add(new CustomField(idEsitoScadenzaField, Long.class, idEsitoScadenzaField, converter.toTable(FatturaElettronica.model())));
			
			String lottoTable = "LottoFatture";
			String lottoId = lottoTable + ".id";
			fields.add(new AliasField(new CustomField(lottoId, Long.class, "id", converter.toTable(FatturaElettronica.model().LOTTO_FATTURE)), "l_id"));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_TRASMISSIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.NOME_FILE, lottoTable));
			fields.add(new AliasField(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA, "l_formatoArchivio"));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.MESSAGE_ID, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_DENOMINAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_NOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_COGNOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_PAESE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CEDENTE_PRESTATORE_CODICE_FISCALE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_DENOMINAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_NOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_COGNOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_PAESE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CESSIONARIO_COMMITTENTE_CODICE_FISCALE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_NOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_COGNOME, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.CODICE_DESTINATARIO, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_RICEZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_INSERIMENTO, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_CONSEGNA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_CONSEGNA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DETTAGLIO_CONSEGNA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.STATO_PROTOCOLLAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DATA_PROTOCOLLAZIONE, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.PROTOCOLLO, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.ID_EGOV, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.PAGO_PA, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.DOMINIO, lottoTable));
			fields.add(this.getCustomField(FatturaElettronica.model().LOTTO_FATTURE.SOTTODOMINIO, lottoTable));
	
			return this.fatturaElettronicaSelect(filter, fields.toArray(new IField[]{}));
		} catch(ExpressionException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<FatturaElettronica> fatturaElettronicaSelect(FatturaFilter filter, IField... fields) throws ServiceException {

		ISIPServiceSearch sipServiceSearch = null;
		try {
			sipServiceSearch = this.serviceManager.getSIPServiceSearch();
		} catch(NotImplementedException e) {
			throw new ServiceException(e);
		}

		List<FatturaElettronica> fatLst = new ArrayList<FatturaElettronica>();
			
		List<Map<String,Object>> select = this.select(filter, fields);
		FatturaElettronicaFetch fetch = new FatturaElettronicaFetch();
		
		String idDecorrenzaTerminiField = "id_notifica_decorrenza_termini";
		String idEsitoContabilizzazioneField = "id_contabilizzazione";
		String idEsitoScadenzaField = "id_scadenza";

		for(Map<String, Object> map: select) {
			Object idFK_fatturaElettronica_notificaDecorrenzaTermini = map.remove(idDecorrenzaTerminiField);
			Object idFK_fatturaElettronica_esitoContabilizzazione = map.remove(idEsitoContabilizzazioneField);
			Object idFK_fatturaElettronica_esitoScadenza = map.remove(idEsitoScadenzaField);
			Object idFK_fatturaElettronica_sipOBJ = map.remove("id_sip");
			Object idFK_fatturaElettronica_LottosipOBJ = map.remove("l_id_sip");

			FatturaElettronica fatturaElettronica = (FatturaElettronica)fetch.fetch(getDatabaseType(), FatturaElettronica.model(), map);
			LottoFatture lottoFatture = (LottoFatture)fetch.fetch(getDatabaseType(), FatturaElettronica.model().LOTTO_FATTURE, map);

			lottoFatture.setIdentificativoSdi(fatturaElettronica.getIdentificativoSdi());
			lottoFatture.setFatturazioneAttiva(fatturaElettronica.getFatturazioneAttiva());
			if(idFK_fatturaElettronica_LottosipOBJ != null && idFK_fatturaElettronica_LottosipOBJ instanceof Long) {
				IdSip idSIP = new IdSip();
				idSIP.setIdSip((Long) idFK_fatturaElettronica_LottosipOBJ);
				lottoFatture.setIdSIP(idSIP);
			}
			fatturaElettronica.setLottoFatture(lottoFatture);

			if(idFK_fatturaElettronica_sipOBJ != null && idFK_fatturaElettronica_sipOBJ instanceof Long) {
				IdSip idSIP = new IdSip();
				idSIP.setIdSip((Long) idFK_fatturaElettronica_sipOBJ);
//				if(StatoConservazioneType.CONSERVAZIONE_COMPLETATA.equals(fatturaElettronica.getStatoConservazione())) {
					try {
						IPaginatedExpression expSIP = sipServiceSearch.newPaginatedExpression();
						expSIP.equals(new CustomField("id", Long.class, "id", new SIPFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()).toAliasTable(SIP.model())), idSIP.getIdSip());
						List<Object> selectDataConsegna = sipServiceSearch.select(expSIP, SIP.model().DATA_ULTIMA_CONSEGNA);
						
						if(selectDataConsegna !=null && !selectDataConsegna.isEmpty() && selectDataConsegna.get(0) != null)
							idSIP.setDataUltimaConsegna((Date) selectDataConsegna.get(0));
						
					} catch(Exception e) {
						this.log.error("Errore durante il recupero della data ultima consegna per id sip ["+idFK_fatturaElettronica_sipOBJ+"]:" + e.getMessage(),e);
					}
//				}
				fatturaElettronica.setIdSIP(idSIP);
			}
			
			if(idFK_fatturaElettronica_notificaDecorrenzaTermini != null && idFK_fatturaElettronica_notificaDecorrenzaTermini instanceof Long) {
				org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini id_fatturaElettronica_notificaDecorrenzaTermini = new org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini();
				id_fatturaElettronica_notificaDecorrenzaTermini.setId((Long) idFK_fatturaElettronica_notificaDecorrenzaTermini);
				fatturaElettronica.setIdDecorrenzaTermini(id_fatturaElettronica_notificaDecorrenzaTermini);
			}
			
			if(idFK_fatturaElettronica_esitoContabilizzazione != null && idFK_fatturaElettronica_esitoContabilizzazione instanceof Long) {
				org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito id_fatturaElettronica_pccTracciaTrasmissioneEsito = new org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito();
				id_fatturaElettronica_pccTracciaTrasmissioneEsito.setId((Long) idFK_fatturaElettronica_esitoContabilizzazione);
				fatturaElettronica.setIdEsitoContabilizzazione(id_fatturaElettronica_pccTracciaTrasmissioneEsito);
			}

			if(idFK_fatturaElettronica_esitoScadenza != null && idFK_fatturaElettronica_esitoScadenza instanceof Long) {
				org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito id_fatturaElettronica_pccTracciaTrasmissioneEsito = new org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito();
				id_fatturaElettronica_pccTracciaTrasmissioneEsito.setId((Long) idFK_fatturaElettronica_esitoScadenza);
				fatturaElettronica.setIdEsitoScadenza(id_fatturaElettronica_pccTracciaTrasmissioneEsito);
			}

			
			fatLst.add(fatturaElettronica);
		}

		return fatLst;
	}

	public TipiDatabase getDatabaseType() throws ServiceException {
		return this.serviceManager.getJdbcProperties().getDatabase();
	}

	public List<String> getListAutocomplete(FatturaFilter filter, IField field) throws ServiceException {

		List<Map<String,Object>> select = null;
		select = this.select(filter, field);
		List<String> cpValues = new ArrayList<String>();
		if(select.size() > 0) {
			for(Map<String, Object> record: select) {
				cpValues.add((String)record.get(field.getFieldName()));
			}
		}


		return cpValues;
	}

	public void assegnaIdentificativoSDIAInteroLotto(IdLotto idLotto, Integer identificativoSDI) throws Exception {
		try {

			StringBuffer update = new StringBuffer();

			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(getDatabaseType());

			update.append("update "+converter.toTable(FatturaElettronica.model())+" set ");
			update.append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ");
			listObjects.add(identificativoSDI);

			update.append(" where ").append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ");
			update.append(" AND ").append(converter.toColumn(FatturaElettronica.model().FATTURAZIONE_ATTIVA, false)).append(" = ? ");
			listObjects.add(idLotto.getIdentificativoSdi());
			listObjects.add(idLotto.isFatturazioneAttiva());

			this.service.nativeUpdate(update.toString(), listObjects.toArray(new Object[]{}));

		} catch (ServiceException e) {
			this.log.error("Errore durante la assegnaIdentificativoSDIAInteroLotto: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la assegnaIdentificativoSDIAInteroLotto: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	public void assegnaProtocolloAInteroLotto(IdLotto idLotto, String protocollo) throws Exception {
		try {

			StringBuffer update = new StringBuffer();

			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(getDatabaseType());

			update.append("update "+converter.toTable(FatturaElettronica.model())+" set ");
			if(protocollo != null) {
				update.append(converter.toColumn(FatturaElettronica.model().PROTOCOLLO, false)).append(" = ? , ");
				listObjects.add(protocollo);
			}

			update.append(converter.toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE, false)).append(" = ? , ");
			listObjects.add(protocollo != null ? StatoProtocollazioneType.PROTOCOLLATA : StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE);

			update.append(converter.toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE, false)).append(" = ? ");
			listObjects.add(new Date());

			update.append(" where ")
			.append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ")
			.append(" AND ").append(converter.toColumn(FatturaElettronica.model().FATTURAZIONE_ATTIVA, false)).append(" = ? ");

			listObjects.add(idLotto.getIdentificativoSdi());
			listObjects.add(idLotto.getFatturazioneAttiva());

			this.service.nativeUpdate(update.toString(), listObjects.toArray(new Object[]{}));

		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void erroreProtocolloAInteroLotto(IdLotto idLotto) throws Exception {
		this.assegnaProtocolloAInteroLotto(idLotto, null);
	}

	public Long getIdEsitoScadenza(long idFattura) throws ServiceException, NotFoundException {
		try {
			String idEsito = "id_scadenza";
			CustomField esitoField = new CustomField(idEsito, Long.class, idEsito, this.getRootTable(this.service));
			FatturaFilter newFilter = this.newFilter();
			newFilter.setId(idFattura);

			List<Map<String,Object>> select = this.select(newFilter, esitoField);

			if(select.size() > 1) {
				throw new MultipleResultException();
			} else if(select.size() > 0) {
				Object idEsitoObj = select.get(0).get(idEsito);
				if(idEsitoObj instanceof Long)
					return (Long) idEsitoObj;
				else
					return null;
			} else {
				throw new NotFoundException();
			}
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}

	public Long getIdEsitoContabilizzazione(long idFattura) throws ServiceException, NotFoundException {
		try {
			String idEsito = "id_contabilizzazione";
			CustomField esitoField = new CustomField(idEsito, Long.class, idEsito, this.getRootTable(this.service));
			FatturaFilter newFilter = this.newFilter();
			newFilter.setId(idFattura);

			List<Map<String,Object>> select = this.select(newFilter, esitoField);

			if(select.size() > 1) {
				throw new MultipleResultException();
			} else if(select.size() > 0) {
				Object idEsitoObj = select.get(0).get(idEsito);
				if(idEsitoObj instanceof Long)
					return (Long) idEsitoObj;
				else
					return null;
			} else {
				throw new NotFoundException();
			}

		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}


	public List<String> getAutocompletamentoCedentePrestatoreDenominazione(FatturaPassivaFilter filter) throws ServiceException {
		return this.getListAutocomplete(filter, FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
	}

	public List<String> getAutocompletamentoCessionarioCommittenteDenominazione(FatturaFilter filter) throws ServiceException {
		return this.getListAutocomplete(filter, FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE);
	}

	public List<String> getAutocompletamentoNumero(FatturaFilter filter) throws ServiceException {
		return this.getListAutocomplete(filter, FatturaElettronica.model().NUMERO);
	}

	public void inviaInConservazione(FatturaFilter filter, StatoConservazioneType statoConservazione) throws ServiceException {
		this.log.info("invio in conservazione fatture");

		try {

			StatoConservazioneType newStatoConservazione = StatoConservazioneType.PRESA_IN_CARICO;
			switch (statoConservazione) {
			case CONSERVAZIONE_COMPLETATA: throw new ServiceException("Operazione non valida");
			case CONSERVAZIONE_FALLITA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case ERRORE_CONSEGNA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case IN_RICONSEGNA: throw new ServiceException("Operazione non valida");
			case NON_INVIATA: newStatoConservazione= StatoConservazioneType.PRESA_IN_CARICO; break;
			case PRESA_IN_CARICO: throw new ServiceException("Operazione non valida");
			}

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(getDatabaseType());

			ArrayList<Object> params = new ArrayList<Object>();

			ISQLQueryObject sqlQueryObject = new JDBC_SQLObjectFactory().createSQLQueryObject(getDatabaseType());

			String fatturaTable = converter.toTable(FatturaElettronica.model());
			String lottiTable = converter.toTable(FatturaElettronica.model().LOTTO_FATTURE);
			String dipartimentiTable = converter.toTable(FatturaElettronica.model().DIPARTIMENTO);
			String entiTable = converter.toTable(FatturaElettronica.model().DIPARTIMENTO.ENTE);
			((JDBCExpression)filter._toExpression()).toSqlForPreparedStatementWithFromCondition(sqlQueryObject, params, fatturaTable);
			sqlQueryObject.addSelectField(fatturaTable+".id");
			sqlQueryObject.addWhereCondition(fatturaTable+".identificativo_sdi = "+lottiTable+".identificativo_sdi and "+fatturaTable+".fatturazione_attiva = "+lottiTable+".fatturazione_attiva");

			sqlQueryObject.addFromTable(dipartimentiTable);
			
			sqlQueryObject.setANDLogicOperator(true);
			sqlQueryObject.addWhereCondition(fatturaTable+".codice_destinatario="+dipartimentiTable+".codice");
			
			sqlQueryObject.addWhereCondition(entiTable+".id="+dipartimentiTable+".id_ente");
			
			ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
			
			sqlQueryObjectUpdate.addUpdateTable(fatturaTable);
			sqlQueryObjectUpdate.addUpdateField(converter.toColumn(FatturaElettronica.model().STATO_CONSERVAZIONE, false), "?");
			
			sqlQueryObjectUpdate.setANDLogicOperator(true);
			sqlQueryObjectUpdate.addWhereINSelectSQLCondition(false, "id", sqlQueryObject);

			ArrayList<Object> realParams = new ArrayList<Object>();
			realParams.add(newStatoConservazione.toString());
			realParams.addAll(params);
			this.service.nativeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), realParams.toArray());
		} catch (ServiceException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw e;
		} catch (ExpressionException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (SQLQueryObjectException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	public void inviaInConservazione(List<Long> ids, StatoConservazioneType statoConservazione) throws ServiceException {
		this.log.info(String.format("invio in conservazione %d fatture", ids.size()));

		try {

			StringBuffer update = new StringBuffer();

			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(getDatabaseType());

			update.append("update ").append(converter.toTable(FatturaElettronica.model())).append(" set ");
			update.append(converter.toColumn(FatturaElettronica.model().STATO_CONSERVAZIONE, false)).append(" = ? ");

			StatoConservazioneType newStatoConservazione = StatoConservazioneType.PRESA_IN_CARICO;
			switch (statoConservazione) {
			case CONSERVAZIONE_COMPLETATA: throw new ServiceException("Operazione non valida");
			case CONSERVAZIONE_FALLITA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case ERRORE_CONSEGNA: newStatoConservazione= StatoConservazioneType.IN_RICONSEGNA; break;
			case IN_RICONSEGNA: throw new ServiceException("Operazione non valida");
			case NON_INVIATA: newStatoConservazione= StatoConservazioneType.PRESA_IN_CARICO; break;
			case PRESA_IN_CARICO: throw new ServiceException("Operazione non valida");
			}
			listObjects.add(newStatoConservazione .toString());

			StringBuffer questionMarks = new StringBuffer();
			for(Long id: ids) {
				if(questionMarks.length() > 0) {
					questionMarks.append(",");
				}
				questionMarks.append("?");

				listObjects.add(id);
			}
			update.append("where id in (").append(questionMarks.toString()).append(")");

			this.service.nativeUpdate(update.toString(), listObjects.toArray(new Object[]{}));

		} catch (ServiceException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw e;
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			this.log.error("Errore durante la inviaInConservazione: " + e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	public FatturaFilter newFilter() {
		return new FatturaFilter(this.service);
	}
	public FatturaFilter newFatturaPassivaFilter() {
		return new FatturaPassivaFilter(this.service);
	}
	public FatturaFilter newFatturaAttivaFilter() {
		return new FatturaAttivaFilter(this.service);
	}


	public void assegnaIdSip(FatturaElettronica fattura, Long id) throws ServiceException {
		try {
			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(getDatabaseType()); 
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

	public void updateStatoConservazione(FatturaElettronica fattura, StatoConservazioneType statoConsegna) throws Exception {
		try {
			List<UpdateField> fields = new ArrayList<UpdateField>();
			fields.add(new UpdateField(FatturaElettronica.model().STATO_CONSERVAZIONE, statoConsegna));
			IdFattura idFAttura = this.service.convertToId(fattura);
			this.service.updateFields(idFAttura, fields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}
	public List<FatturaElettronica> getFattureDaConservare(Date dataLimite, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.toPaginatedExpression(getFattureDaConservareExpression(dataLimite));

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(FatturaElettronica.model().DATA_RICEZIONE);

			expression.offset(offset);
			expression.limit(limit);

			return this.service.findAll(expression);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	private IExpression getFattureDaConservareExpression(Date dataLimite) throws Exception {
		IExpression expression = this.service.newExpression();
		expression.in(FatturaElettronica.model().ID_SIP.STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA, StatoConsegnaType.IN_RICONSEGNA);
		expression.lessEquals(FatturaElettronica.model().ID_SIP.DATA_ULTIMA_CONSEGNA, dataLimite);
		return expression;
	}

}
