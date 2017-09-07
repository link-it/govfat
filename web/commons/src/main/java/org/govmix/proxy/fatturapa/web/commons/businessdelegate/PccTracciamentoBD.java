/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.util.Map;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.IdTrasmissione;
import org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.PccErroreElaborazione;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.orm.constants.TipoOperazionePccType;
import org.govmix.proxy.fatturapa.orm.dao.IDBFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.orm.dao.IPccErroreElaborazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneEsitoService;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaTrasmissioneService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccErroreElaborazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccTracciaTrasmissioneEsitoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccTracciaTrasmissioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.PccTracciaFieldConverter;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.fetch.PccTracciaFetch;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.TipiDatabase;

public class PccTracciamentoBD extends BaseBD {

	private IPccTracciaService tracciamentoService;
	private IPccTracciaTrasmissioneService trasmissioniService;
	private IPccTracciaTrasmissioneEsitoService esitoService;
	private IPccErroreElaborazioneService erroreService;
	private IFatturaElettronicaService fatturaService;

	public PccTracciamentoBD() throws Exception {
		this(LoggerWrapperFactory.getLogger(PccTracciamentoBD.class));
	}

	public PccTracciamentoBD(Logger log) throws Exception {
		super(log);
		this.tracciamentoService = this.serviceManager.getPccTracciaService();
		this.trasmissioniService = this.serviceManager.getPccTracciaTrasmissioneService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
		this.erroreService = this.serviceManager.getPccErroreElaborazioneService();
		this.fatturaService = this.serviceManager.getFatturaElettronicaService();
	}

	public PccTracciamentoBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.tracciamentoService = this.serviceManager.getPccTracciaService();
		this.trasmissioniService = this.serviceManager.getPccTracciaTrasmissioneService();
		this.esitoService = this.serviceManager.getPccTracciaTrasmissioneEsitoService();
		this.erroreService = this.serviceManager.getPccErroreElaborazioneService();
		this.fatturaService = this.serviceManager.getFatturaElettronicaService();
	}

	public void create(PccTraccia traccia) throws Exception {
		try {

			this.tracciamentoService.create(traccia);

		} catch (Exception e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void update(PccTraccia traccia) throws Exception {
		try {


			IdTraccia idTraccia = this.tracciamentoService.convertToId(traccia);
			this.tracciamentoService.update(idTraccia, traccia);

		} catch (Exception e) {
			this.log.error("Errore durante la update traccia: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void forzaRispedizione(IdTraccia idTraccia) throws Exception {
		try {

			UpdateField rispedizioneField = new UpdateField(PccTraccia.model().RISPEDIZIONE, true);
			UpdateField intervalloRispedizioneField = new UpdateField(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO, new Date());
			UpdateField tentativiRispedizioneField = new UpdateField(PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI, 0);;
			UpdateField maxTentativiRispedizioneField = new UpdateField(PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI, 1);
			UpdateField statoTracciaField = new UpdateField(PccTraccia.model().STATO, StatoType.AS_PRESA_IN_CARICO);
			this.tracciamentoService.updateFields(idTraccia, rispedizioneField, intervalloRispedizioneField, tentativiRispedizioneField, maxTentativiRispedizioneField, statoTracciaField);

		} catch (Exception e) {
			this.log.error("Errore durante la update traccia: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void update(PccTracciaTrasmissione tracciaTrasmissione) throws Exception {
		try {


			IdTrasmissione idTracciaTrasmissione = this.trasmissioniService.convertToId(tracciaTrasmissione);
			this.trasmissioniService.update(idTracciaTrasmissione, tracciaTrasmissione);

		} catch (Exception e) {
			this.log.error("Errore durante la update trasmissione: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void associaATraccia(PccTracciaTrasmissione trasmissione, PccTraccia traccia) throws Exception {
		try {

			IdTraccia idTraccia = this.tracciamentoService.convertToId(traccia);

			trasmissione.setIdTraccia(idTraccia);
			this.trasmissioniService.create(trasmissione);

		} catch (Exception e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void associaATrasmissione(PccTracciaTrasmissione trasmissione, PccTracciaTrasmissioneEsito esito) throws Exception {
		try {

			esito.setIdTrasmissione(this.trasmissioniService.convertToId(trasmissione));
			this.esitoService.create(esito);

		} catch (Exception e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void associaAEsito(PccTracciaTrasmissioneEsito esito, PccErroreElaborazione errore) throws Exception {
		try {

			IdTrasmissioneEsito idEsito = this.esitoService.convertToId(esito);
			errore.setIdEsito(idEsito);
			this.erroreService.create(errore);

		} catch (Exception e) {
			this.log.error("Errore durante la create: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<PccTraccia> getTraccePerEsiti(int offset, int limit, Date date) throws Exception {
		IPaginatedExpression exp1 = this.tracciamentoService.newPaginatedExpression();
		exp1.equals(PccTraccia.model().STATO, StatoType.AS_PRESA_IN_CARICO);
		exp1.equals(PccTraccia.model().TIPO_OPERAZIONE, TipoOperazionePccType.PROXY);


		IPaginatedExpression exp2 = this.tracciamentoService.newPaginatedExpression();

		exp2.isNull(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO).or().lessEquals(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO, date);

		IPaginatedExpression exp = this.tracciamentoService.newPaginatedExpression();

		exp.and(exp1, exp2);

		PccTracciaFieldConverter fc = new PccTracciaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 

		exp.sortOrder(SortOrder.ASC);
		exp.addOrder(new CustomField("id", Long.class, "id", fc.toTable(PccTraccia.model())));
		exp.offset(offset);
		exp.limit(limit);

		return this.getTracce(exp, true);
	}

	public PccTraccia getTracciaByIdPa(String idPa) throws Exception {
		IExpression exp = this.tracciamentoService.newExpression();
		exp.equals(PccTraccia.model().ID_PA_TRANSAZIONE, idPa);

		return this.tracciamentoService.find(exp);
	}

	public boolean existsTracciaByIdPa(String idPa) throws Exception {
		IExpression exp = this.tracciamentoService.newExpression();
		exp.equals(PccTraccia.model().ID_PA_TRANSAZIONE, idPa);

		return this.tracciamentoService.count(exp).longValue() > 0;
	}

	public PccTraccia getTracciaById(Long id) throws NotFoundException,Exception {
		IdTraccia idTraccia = new IdTraccia();
		idTraccia.setIdTraccia(id);
		PccTraccia traccia = this.tracciamentoService.get(idTraccia);

		List<PccTracciaTrasmissione> trasmissioniByIdTraccia = getTrasmissioniByIdTraccia(idTraccia);
		if(trasmissioniByIdTraccia != null) {
			for(PccTracciaTrasmissione pccTracciaTrasmissione: trasmissioniByIdTraccia) {
				traccia.addPccTracciaTrasmissione(pccTracciaTrasmissione);
			}
		}
		
		if(traccia.getIdFattura() > 0) {
			traccia.setFatturaElettronica(((IDBFatturaElettronicaServiceSearch)fatturaService).get(traccia.getIdFattura()));
		}
		
		return traccia;

	}

	public IPaginatedExpression newExp() throws ServiceException, NotImplementedException {
		return this.tracciamentoService.newPaginatedExpression();
	}

	public IExpression newExpression() throws ServiceException, NotImplementedException {
		return this.tracciamentoService.newExpression();
	}

	public long countTracce(IExpression expr) throws Exception{
		NonNegativeNumber nnn = this.tracciamentoService.count(expr);

		if(nnn != null)
			return nnn.longValue();


		return 0;
	}

	public List<PccTraccia> getTracce(IPaginatedExpression exp, boolean deep) throws ServiceException {
		List<PccTraccia> lstTracce = null;

		try{
			if(deep) {
				lstTracce = this.tracciamentoService.findAll(exp);
				if(lstTracce != null) {
					for(PccTraccia traccia: lstTracce) {
						IdTraccia idTraccia = new IdTraccia();
						idTraccia.setIdTraccia(traccia.getId());
						List<PccTracciaTrasmissione> trasmissioniByIdTraccia = null;
						try{
							trasmissioniByIdTraccia = getTrasmissioniByIdTraccia(idTraccia);
						}catch(Exception e){

						}
						if(trasmissioniByIdTraccia != null && trasmissioniByIdTraccia.size() >0) {
							for(PccTracciaTrasmissione pccTracciaTrasmissione: trasmissioniByIdTraccia) {
								traccia.addPccTracciaTrasmissione(pccTracciaTrasmissione);
							}
						}
					}
				}
			} else {
				lstTracce = new ArrayList<PccTraccia>();

				List<IField> fields = new ArrayList<IField>();
				TipiDatabase databaseType = this.serviceManager.getJdbcProperties().getDatabase();
				PccTracciaFieldConverter converter = new PccTracciaFieldConverter(databaseType);

				PccTracciaFetch tracciaFetch = new PccTracciaFetch();
				fields.add(new CustomField("id", Long.class, "id", converter.toTable(PccTraccia.model())));
				fields.add(PccTraccia.model().CF_TRASMITTENTE);
				fields.add(PccTraccia.model().VERSIONE_APPLICATIVA);
				fields.add(PccTraccia.model().ID_PCC_AMMINISTRAZIONE);
				fields.add(PccTraccia.model().ID_PA_TRANSAZIONE);
				fields.add(PccTraccia.model().SISTEMA_RICHIEDENTE);
				fields.add(PccTraccia.model().UTENTE_RICHIEDENTE);
				fields.add(PccTraccia.model().ID_FATTURA);
				fields.add(PccTraccia.model().CODICE_DIPARTIMENTO);
				fields.add(PccTraccia.model().OPERAZIONE);
				fields.add(PccTraccia.model().TIPO_OPERAZIONE);
				fields.add(PccTraccia.model().STATO);
				fields.add(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO);
				fields.add(PccTraccia.model().DATA_ULTIMA_TRASMISSIONE);
				fields.add(PccTraccia.model().RISPEDIZIONE);
				fields.add(PccTraccia.model().RISPEDIZIONE_MAX_TENTATIVI);
				fields.add(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO);
				fields.add(PccTraccia.model().RISPEDIZIONE_NUMERO_TENTATIVI);
				fields.add(PccTraccia.model().RISPEDIZIONE_ULTIMO_TENTATIVO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.FORMATO_TRASMISSIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.IDENTIFICATIVO_SDI);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_RICEZIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.NOME_FILE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.MESSAGE_ID);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_PAESE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_CODICE_FISCALE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_DENOMINAZIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_PAESE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CESSIONARIO_COMMITTENTE_CODICE_FISCALE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.POSIZIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CODICE_DESTINATARIO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TIPO_DOCUMENTO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DIVISA);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.ANNO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.NUMERO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.ESITO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.IN_RIALLINEAMENTO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.IMPORTO_TOTALE_DOCUMENTO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.IMPORTO_TOTALE_RIEPILOGO);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.CAUSALE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.STATO_CONSEGNA);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_CONSEGNA);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_PROSSIMA_CONSEGNA);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.TENTATIVI_CONSEGNA);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DETTAGLIO_CONSEGNA);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.STATO_PROTOCOLLAZIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.DATA_PROTOCOLLAZIONE);
//				fields.add(PccTraccia.model().FATTURA_ELETTRONICA.PROTOCOLLO);

				List<Map<String, Object>> returnMap = null;
				try{
					returnMap = this.tracciamentoService.select(exp, fields.toArray(new IField[1]));
				}catch (NotFoundException e) {
					return new ArrayList<PccTraccia>();
				}

				for(Map<String, Object> map: returnMap) {
					PccTraccia traccia = (PccTraccia)tracciaFetch.fetch(databaseType, PccTraccia.model(), map);
					
					if(traccia.getIdFattura() > 0) {
						traccia.setFatturaElettronica(((IDBFatturaElettronicaServiceSearch)fatturaService).get(traccia.getIdFattura()));
					}
					lstTracce.add(traccia);
				}
			}

		}catch(ServiceException e){
			throw e;
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} 

		return lstTracce;

	}

	public PccTracciaTrasmissione getTrasmissioneById(long idTrasmissione) throws NotFoundException,Exception {
		return ((JDBCPccTracciaTrasmissioneServiceSearch)this.trasmissioniService).get(idTrasmissione);
	}

	public List<PccTracciaTrasmissione> getTrasmissioniByIdTraccia(IdTraccia idTraccia) throws Exception {
		return this.getTrasmissioniByIdTraccia(idTraccia, null, null);
	}

	public long countTrasmissioniByIdTraccia(IdTraccia idTraccia) throws Exception{
		IExpression exp = this.trasmissioniService.newExpression();
		exp.equals(PccTracciaTrasmissione.model().ID_TRACCIA.ID_TRACCIA, idTraccia.getIdTraccia());
		NonNegativeNumber nnn = this.trasmissioniService.count(exp);

		if(nnn != null)
			return nnn.longValue();

		return 0;
	}

	public List<PccTracciaTrasmissione> getTrasmissioniByIdTraccia(IdTraccia idTraccia,Integer start,Integer limit) throws Exception {
		return this.getTrasmissioniByIdTraccia(idTraccia, start, limit, getFilterSortList());
	}
	public List<PccTracciaTrasmissione> getTrasmissioniByIdTraccia(IdTraccia idTraccia,Integer start,Integer limit,List<FilterSortWrapper> filterSortList ) throws Exception {

		IExpression exp = this.trasmissioniService.newExpression();
		exp.equals(PccTracciaTrasmissione.model().ID_TRACCIA.ID_TRACCIA, idTraccia.getIdTraccia());

		IPaginatedExpression pagExpr = this.toPaginatedExpression(trasmissioniService, exp,start,limit,filterSortList);

		List<PccTracciaTrasmissione> trasmissioniByIdTraccia = this.trasmissioniService.findAll(pagExpr);

		if(trasmissioniByIdTraccia != null) {
			for(PccTracciaTrasmissione pccTracciaTrasmissione: trasmissioniByIdTraccia) {
				List<PccTracciaTrasmissioneEsito> esitiByIdTrasmissione = getEsitiByIdTrasmissione(pccTracciaTrasmissione.getId());
				if(esitiByIdTrasmissione != null) {
					for(PccTracciaTrasmissioneEsito esito: esitiByIdTrasmissione) {
						pccTracciaTrasmissione.addPccTracciaTrasmissioneEsito(esito);

					}
				}
			}
		}

		return trasmissioniByIdTraccia;
	}

	public List<PccTracciaTrasmissioneEsito> getEsitiByIdTrasmissione(Long idTrasmissione) throws Exception {
		return this.getEsitiByIdTrasmissione(idTrasmissione, null, null);
	}

	public List<PccTracciaTrasmissioneEsito> getEsitiByIdTrasmissione(Long idTrasmissione,Integer start,Integer limit) throws Exception {
		return this.getEsitiByIdTrasmissione(idTrasmissione, start, limit, getFilterSortList());
	}

	public List<PccTracciaTrasmissioneEsito> getEsitiByIdTrasmissione(Long idTrasmissione,Integer start,Integer limit,List<FilterSortWrapper> filterSortList ) throws Exception {
		IExpression exp1 = this.esitoService.newExpression();
		exp1.equals(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE.ID_TRASMISSIONE, idTrasmissione);

		IPaginatedExpression pagExpr = this.toPaginatedExpression(esitoService, exp1,start,limit,filterSortList);

		List<PccTracciaTrasmissioneEsito> esitiByIdTrasmissione = this.esitoService.findAll(pagExpr);

		if(esitiByIdTrasmissione != null) {
			for(PccTracciaTrasmissioneEsito pccTracciaTrasmissioneEsito: esitiByIdTrasmissione) {
				List<PccErroreElaborazione> erroreByIdEsito = getErroriByIdEsito(pccTracciaTrasmissioneEsito.getId());
				if(erroreByIdEsito != null) {
					for(PccErroreElaborazione pccErroreElaborazione: erroreByIdEsito) {
						pccTracciaTrasmissioneEsito.addPccErroreElaborazione(pccErroreElaborazione);

					}
				}
			}
		}

		return esitiByIdTrasmissione;
	}

	public long countEsitiByIdTrasmissione(Long idTrasmissione) throws Exception{
		IExpression exp = this.esitoService.newExpression();
		exp.equals(PccTracciaTrasmissioneEsito.model().ID_TRASMISSIONE.ID_TRASMISSIONE, idTrasmissione);
		NonNegativeNumber nnn = this.esitoService.count(exp);

		if(nnn != null)
			return nnn.longValue();

		return 0;
	}

	public PccTracciaTrasmissioneEsito getEsitoById(long idEsito) throws NotFoundException,Exception {
		return ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)this.esitoService).get(idEsito);
	}

	public List<PccErroreElaborazione> getErroriByIdEsito(Long idEsito) throws Exception {
		return this.getErroriByIdEsito(idEsito, null, null);
	}

	public List<PccErroreElaborazione> getErroriByIdEsito(Long idEsito,Integer start, Integer limit) throws Exception {
		return this.getErroriByIdEsito(idEsito, start, limit, getFilterSortList());
	}

	public List<PccErroreElaborazione> getErroriByIdEsito(Long idEsito,Integer start, Integer limit, List<FilterSortWrapper> filterSortList ) throws Exception {
		IExpression exp = this.erroreService.newExpression();
		exp.equals(PccErroreElaborazione.model().ID_ESITO.ID_TRASMISSIONE_ESITO, idEsito);

		IPaginatedExpression pagExpr = this.toPaginatedExpression(erroreService, exp,start,limit,filterSortList);

		return this.erroreService.findAll(pagExpr);
	}

	public long countErroriByIdEsito(Long idEsito) throws Exception{
		IExpression exp = this.erroreService.newExpression();
		exp.equals(PccErroreElaborazione.model().ID_ESITO.ID_TRASMISSIONE_ESITO, idEsito);
		NonNegativeNumber nnn = this.erroreService.count(exp);

		if(nnn != null)
			return nnn.longValue();

		return 0;
	}

	public PccErroreElaborazione getErroreById(long idErrore) throws NotFoundException,Exception {
		return ((JDBCPccErroreElaborazioneServiceSearch)this.erroreService).get(idErrore);
	}

	public long countTraccePerEsiti(Date date) throws Exception {
		IExpression exp1 = this.tracciamentoService.newExpression();
		exp1.equals(PccTraccia.model().STATO, StatoType.AS_PRESA_IN_CARICO);
		exp1.equals(PccTraccia.model().TIPO_OPERAZIONE, TipoOperazionePccType.PROXY);


		IExpression exp2 = this.tracciamentoService.newExpression();
		exp2.isNull(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO).or().lessEquals(PccTraccia.model().DATA_ULTIMO_TENTATIVO_ESITO, date);

		IExpression exp = this.tracciamentoService.newExpression();
		exp.and(exp1, exp2);

		return this.tracciamentoService.count(exp).longValue();
	}


	public List<PccTraccia> getTraccePerRispedizione(int offset, int limit, Date date) throws Exception {

		IPaginatedExpression exp = this.tracciamentoService.toPaginatedExpression(getExpPerRispedizione(date));

		PccTracciaFieldConverter fc = new PccTracciaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase()); 

		exp.sortOrder(SortOrder.ASC);
		exp.addOrder(new CustomField("id", Long.class, "id", fc.toTable(PccTraccia.model())));
		exp.offset(offset);
		exp.limit(limit);

		return this.getTracce(exp, true);
	}

	public long countTraccePerRispedizione(Date date) throws Exception {
		return this.tracciamentoService.count(getExpPerRispedizione(date)).longValue();
	}

	private IExpression getExpPerRispedizione(Date date) throws Exception {
		IExpression exp1 = this.tracciamentoService.newExpression();
		exp1.lessEquals(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO, date).or().isNull(PccTraccia.model().RISPEDIZIONE_PROSSIMO_TENTATIVO);

		IExpression exp = this.tracciamentoService.newExpression();
		exp.equals(PccTraccia.model().RISPEDIZIONE, true);

		IExpression exp2 = this.tracciamentoService.newExpression();
		exp2.and(exp, exp1);

		return exp2;
	}

	public IPccTracciaService getTracciamentoService() {
		return tracciamentoService;
	}

	public IPccTracciaTrasmissioneService getTrasmissioniService() {
		return trasmissioniService;
	}

	public IPccTracciaTrasmissioneEsitoService getEsitoService() {
		return esitoService;
	}

	public IPccErroreElaborazioneService getErroreService() {
		return erroreService;
	}


}
