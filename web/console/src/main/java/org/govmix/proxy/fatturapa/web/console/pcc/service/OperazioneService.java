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
package org.govmix.proxy.fatturapa.web.console.pcc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.constants.StatoType;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.TracciaPccEstesaBean;
import org.govmix.proxy.fatturapa.web.console.pcc.iservice.IOperazioneService;
import org.govmix.proxy.fatturapa.web.console.pcc.search.OperazioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.service.FatturaElettronicaService;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.LikeMode;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.input.DateTime;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.service.BaseService;

public class OperazioneService extends BaseService<OperazioneSearchForm> implements IOperazioneService{

	private IdFattura idFattura = null;
	private static Logger log = LoggerManager.getDaoLogger();

	private PccTracciamentoBD tracciamentoBD = null;
	private IFatturaElettronicaService fatturaService = null;
	private IPccTracciaServiceSearch tracciaSearchDAO = null;

	public OperazioneService() {
		try {
			this.tracciamentoBD = new PccTracciamentoBD(OperazioneService.log);
			this.fatturaService = new FatturaElettronicaService();
			this.tracciaSearchDAO = DAOFactory.getInstance().getServiceManager().getPccTracciaServiceSearch();
		} catch (Exception e) {
			OperazioneService.log.error("Errore durante la init di OperazioneService: " + e.getMessage(),e); 
		}
	}

	@Override
	public List<TracciaPccEstesaBean> findAll(int start, int limit) throws ServiceException {
		return this._findAll(this.getForm(), start, limit);
	}

	@Override
	public int totalCount() throws ServiceException {
		IExpression expr =  null;
		try{
			// Dettaglio Fattura
			if(this.idFattura != null){
				expr = this.tracciamentoBD.newExpression();
				expr.equals(PccTraccia.model().ID_FATTURA, idFattura.getId());
			} else {
				// ricerca libera
				expr = this.getExpressionFromSearch(this.tracciamentoBD, form);
			}

			return (int) this.tracciamentoBD.countTracce(expr);
		}catch(NotImplementedException e){
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (Exception e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
	}

	@Override
	public void store(TracciaPccEstesaBean obj) throws ServiceException {
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
	}

	@Override
	public void delete(TracciaPccEstesaBean obj) throws ServiceException {
	}

	@Override
	public TracciaPccEstesaBean findById(Long key) throws ServiceException {
		PccTraccia dto;
		try {
			dto = this.tracciamentoBD.getTracciaById(key.longValue());
			TracciaPccEstesaBean bean = new TracciaPccEstesaBean();
			bean.setDTO(dto);
			return bean;
		} catch(NotFoundException e){
			OperazioneService.log.debug("Nessuna traccia con ID ["+key+"] trovata.");
		}	catch (Exception e) {
			OperazioneService.log.error("Errore durante la findById ["+key+"]: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}
		return null;
	}

	@Override
	public List<TracciaPccEstesaBean> findAll() throws ServiceException {
		return this._findAll(this.getForm(), null, null);
	}

	@Override
	public boolean exists(TracciaPccEstesaBean obj) throws ServiceException {
		return false;
	}

	@Override
	public List<TracciaPccEstesaBean> findAll(OperazioneSearchForm arg0) throws ServiceException {
		return this._findAll(arg0, null,null);
	}

	@Override
	public List<String> getUtenteRichiedenteAutoComplete(String val) throws ServiceException {
		String methodName = "getUtenteRichiedenteAutoComplete("+val+")";
		List<String> listaMittenti = new ArrayList<String>();


		try {

			IPaginatedExpression pagExpr = this.tracciaSearchDAO.newPaginatedExpression();

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(PccTraccia.model().UTENTE_RICHIEDENTE);
			pagExpr.ilike(PccTraccia.model().UTENTE_RICHIEDENTE,	val.toLowerCase(), LikeMode.ANYWHERE);

			List<Object> select = this.tracciaSearchDAO.select(pagExpr, true, PccTraccia.model().UTENTE_RICHIEDENTE);

			if(select != null && select.size() > 0){
				for (Object object : select) {
					listaMittenti.add((String) object);
				}
			}

		} catch (NotFoundException e) {
			OperazioneService.log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}


		return listaMittenti;
	}

	@Override
	public List<String> getSistemaRichiedenteAutoComplete(String val) throws ServiceException {
		String methodName = "getSistemaRichiedenteAutoComplete("+val+")";
		List<String> listaMittenti = new ArrayList<String>();


		try {

			IPaginatedExpression pagExpr = this.tracciaSearchDAO.newPaginatedExpression();

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(PccTraccia.model().SISTEMA_RICHIEDENTE);
			pagExpr.ilike(PccTraccia.model().SISTEMA_RICHIEDENTE,	val.toLowerCase(), LikeMode.ANYWHERE);

			List<Object> select = this.tracciaSearchDAO.select(pagExpr, true, PccTraccia.model().SISTEMA_RICHIEDENTE);

			if(select != null && select.size() > 0){
				for (Object object : select) {
					listaMittenti.add((String) object);
				}
			}

		} catch (NotFoundException e) {
			OperazioneService.log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			OperazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}


		return listaMittenti;
	}

	@Override
	public List<String> getMittenteAutoComplete(String val) throws ServiceException {
		return this.fatturaService.getMittenteAutoComplete(val);
	}

	@Override
	public List<String> getNumeroAutoComplete(String val) throws ServiceException {
		return this.fatturaService.getNumeroAutoComplete(val);
	}

	private List<TracciaPccEstesaBean> _findAll(OperazioneSearchForm form,Integer start, Integer limit) throws ServiceException {
		List<TracciaPccEstesaBean> lst = new ArrayList<TracciaPccEstesaBean>();

		IExpression expr =  null;
		try{
			// Dettaglio Fattura
			if(this.idFattura != null){
				expr = this.tracciamentoBD.newExpression();
				expr.equals(PccTraccia.model().ID_FATTURA, idFattura.getId());
			} else {
				// ricerca libera
				expr = this.getExpressionFromSearch(this.tracciamentoBD, form);
			}

			FilterSortWrapper filtro = new FilterSortWrapper();
			filtro.setField(PccTraccia.model().DATA_ULTIMA_TRASMISSIONE);
			filtro.setSortOrder(SortOrder.DESC);
			List<FilterSortWrapper> listaFiltri = new ArrayList<FilterSortWrapper>();
			listaFiltri.add(filtro);
			IPaginatedExpression pagExpr = this.tracciamentoBD.toPaginatedExpression(this.tracciamentoBD.getTracciamentoService(), expr,start,limit,listaFiltri);

			List<PccTraccia> tracce = this.tracciamentoBD.getTracce(pagExpr,false );

			if(tracce != null && tracce.size() > 0 ){
				for (PccTraccia pccTraccia : tracce) {
					TracciaPccEstesaBean bean = new TracciaPccEstesaBean();
					bean.setDTO(pccTraccia);
					lst.add(bean);
				}
			}

		}catch(NotImplementedException e){
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (Exception e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}

		return lst;
	}

	public IExpression getExpressionFromSearch(PccTracciamentoBD tracciamentoBD, OperazioneSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr =  tracciamentoBD.newExpression();

			IExpression dateExpr = this.getExpressionDateFromSearch(tracciamentoBD, search);
			expr.and(dateExpr).and();

			// selezione sistema richiedente
			if(search.getSistemaRichiedente().getValue() != null && !StringUtils.isEmpty(search.getSistemaRichiedente().getValue()) &&
					!CostantiForm.NON_SELEZIONATO.equals(search.getSistemaRichiedente().getValue())){
				List<SelectItem> cedPrestVals = this.form.getSistemaRichiedenteSelList();
				String trimSelCedPrest = search.getSistemaRichiedente().getValue().trim();

				OperazioneService.log.debug("Confronto TRIM["+trimSelCedPrest+"]");
				List<String> valoriCedPret = new ArrayList<String>();
				for (SelectItem selectItem : cedPrestVals) {
					String val = selectItem.getValue();

					String noSpaceRes = val.trim().replace("  ", " ");
					while(noSpaceRes.contains("  ")) {
						noSpaceRes = noSpaceRes.replace("  ", " ");
					}

					OperazioneService.log.debug("Confronto NSRES["+noSpaceRes+"]");
					// Se il valore trimmato selezionato dall'utente corrisponde ad uno dei valori nella lista allora li uso per fare il confronto
					if(noSpaceRes.equals(trimSelCedPrest)){
						valoriCedPret.add(val);
					}
				}

				if(valoriCedPret.size() == 0)
					expr.like(PccTraccia.model().SISTEMA_RICHIEDENTE, search.getSistemaRichiedente().getValue(), LikeMode.ANYWHERE);
				else {
					IExpression orExpr = tracciamentoBD.newExpression();
					for (String val : valoriCedPret) {
						orExpr.like(PccTraccia.model().SISTEMA_RICHIEDENTE, val, LikeMode.ANYWHERE).or();
					}
					expr.and(orExpr);
				}

			}

			// selezione utente richiedente
			if(search.getUtenteRichiedente().getValue() != null && !StringUtils.isEmpty(search.getUtenteRichiedente().getValue()) &&
					!CostantiForm.NON_SELEZIONATO.equals(search.getUtenteRichiedente().getValue())){
				List<SelectItem> cedPrestVals = this.form.getUtenteRichiedenteSelList();
				String trimSelCedPrest = search.getUtenteRichiedente().getValue().trim();

				OperazioneService.log.debug("Confronto TRIM["+trimSelCedPrest+"]");
				List<String> valoriCedPret = new ArrayList<String>();
				for (SelectItem selectItem : cedPrestVals) {
					String val = selectItem.getValue();

					String noSpaceRes = val.trim().replace("  ", " ");
					while(noSpaceRes.contains("  ")) {
						noSpaceRes = noSpaceRes.replace("  ", " ");
					}

					OperazioneService.log.debug("Confronto NSRES["+noSpaceRes+"]");
					// Se il valore trimmato selezionato dall'utente corrisponde ad uno dei valori nella lista allora li uso per fare il confronto
					if(noSpaceRes.equals(trimSelCedPrest)){
						valoriCedPret.add(val);
					}
				}

				if(valoriCedPret.size() == 0)
					expr.ilike(PccTraccia.model().UTENTE_RICHIEDENTE, search.getUtenteRichiedente().getValue(), LikeMode.ANYWHERE);
				else {
					IExpression orExpr = tracciamentoBD.newExpression();
					for (String val : valoriCedPret) {
						orExpr.ilike(PccTraccia.model().UTENTE_RICHIEDENTE, val, LikeMode.ANYWHERE).or();
					}
					expr.and(orExpr);
				}
			}

			// stato consegna
			if(search.getEsito().getValue() != null &&
					!StringUtils.isEmpty(search.getEsito().getValue().getValue()) && !search.getEsito().getValue().getValue().equals("*")){
				StatoType statoType = StatoType.toEnumConstant(search.getEsito().getValue().getValue());
				expr.equals(PccTraccia.model().STATO,statoType);
			}


			// stato consegna
			if(search.getOperazione().getValue() != null &&
					!StringUtils.isEmpty(search.getOperazione().getValue().getValue())
						&& !search.getOperazione().getValue().getValue().equals("*")){
				String operazione = search.getOperazione().getValue().getValue();
//				OperazioneType operazioneType = OperazioneType.toEnumConstant(search.getOperazione().getValue().getValue());
				// [TODO] eliminare il tostring quando cambia il dao
				expr.equals(PccTraccia.model().OPERAZIONE,operazione);
			}


			// selezione cedente prestatore
			if(search.getCedentePrestatore().getValue() != null && !StringUtils.isEmpty(search.getCedentePrestatore().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getCedentePrestatore().getValue())){
				List<SelectItem> cedPrestVals = this.form.getCedPrestSelList();
				String trimSelCedPrest = search.getCedentePrestatore().getValue().trim();

				OperazioneService.log.debug("Confronto TRIM["+trimSelCedPrest+"]");
				List<String> valoriCedPret = new ArrayList<String>();
				for (SelectItem selectItem : cedPrestVals) {
					String val = selectItem.getValue();

					String noSpaceRes = val.trim().replace("  ", " ");
					while(noSpaceRes.contains("  ")) {
						noSpaceRes = noSpaceRes.replace("  ", " ");
					}

					OperazioneService.log.debug("Confronto NSRES["+noSpaceRes+"]");
					// Se il valore trimmato selezionato dall'utente corrisponde ad uno dei valori nella lista allora li uso per fare il confronto
					if(noSpaceRes.equals(trimSelCedPrest)){
						valoriCedPret.add(val);
					}
				}

				if(valoriCedPret.size() == 0)
					expr.ilike(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE, search.getCedentePrestatore().getValue(), LikeMode.ANYWHERE);
				else {
					IExpression orExpr = this.tracciamentoBD.newExpression();
					for (String val : valoriCedPret) {
						orExpr.ilike(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE, val, LikeMode.ANYWHERE).or();
					}
					expr.and(orExpr);
				}
				expr.equals(PccTraccia.model().FATTURA_ELETTRONICA.CEDENTE_PRESTATORE_DENOMINAZIONE, search.getCedentePrestatore().getValue());
			}

			//Numero
			if(search.getNumero().getValue() != null && !StringUtils.isEmpty(search.getNumero().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getNumero().getValue())){
				expr.ilike(PccTraccia.model().FATTURA_ELETTRONICA.NUMERO, search.getNumero().getValue(), LikeMode.START);
			}

			// identificativoSDI
			if(search.getIdentificativoLotto().getValue() != null && !StringUtils.isEmpty(search.getIdentificativoLotto().getValue())){
				expr.equals(PccTraccia.model().FATTURA_ELETTRONICA.IDENTIFICATIVO_SDI, Integer.parseInt(search.getIdentificativoLotto().getValue()));
			}

			// idPaTransazione
			if(search.getIdPaTransazione().getValue() != null && !StringUtils.isEmpty(search.getIdPaTransazione().getValue())){
				expr.equals(PccTraccia.model().ID_PA_TRANSAZIONE, search.getIdPaTransazione().getValue());
			}
			
			//codice errore pcc
			if(search.getCodiceErrore().getValue() != null &&
					!StringUtils.isEmpty(search.getCodiceErrore().getValue().getValue())
						&& !search.getCodiceErrore().getValue().getValue().equals("*")){
				String codiceErrorePCC = search.getCodiceErrore().getValue().getValue();
				expr.like(PccTraccia.model().CODICI_ERRORE, codiceErrorePCC, LikeMode.ANYWHERE);
			}

		}catch(Exception e){
			OperazioneService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	private IExpression getExpressionDateFromSearch(PccTracciamentoBD tracciamentoBD,OperazioneSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = tracciamentoBD.newExpression();

			SelectList<SelectItem> dataRicezionePeriodo = search.getDataPeriodo();
			DateTime dataRicezione = search.getData();

			Date dataInizio = dataRicezione.getValue();
			Date dataFine = dataRicezione.getValue2();

			String periodo = dataRicezionePeriodo.getValue() != null ? dataRicezionePeriodo.getValue().getValue() : org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA;

			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 23);
			today.set(Calendar.MINUTE, 59);
			today.clear(Calendar.SECOND);
			today.clear(Calendar.MILLISECOND);

			//ultima settimana
			if ( org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA.equals(periodo)) {
				Calendar lastWeek = (Calendar) today.clone();
				Calendar c = Calendar.getInstance();
				dataFine = c.getTime();
				lastWeek.set(Calendar.HOUR_OF_DAY, 0);
				lastWeek.set(Calendar.MINUTE, 0);
				lastWeek.add(Calendar.DATE, -7);
				dataInizio = lastWeek.getTime();

			} else if ( org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_RICEZIONE_PERIODO_ULTIMO_MESE.equals( periodo)) {
				Calendar lastMonth = (Calendar) today.clone();

				// prendo la data corrente
				dataFine = Calendar.getInstance().getTime();

				// la data inizio rimane uguale sia per giornaliero che per orario
				lastMonth.set(Calendar.HOUR_OF_DAY, 0);
				lastMonth.set(Calendar.MINUTE, 0);
				lastMonth.add(Calendar.DATE, -30);
				dataInizio = lastMonth.getTime();

			} else if ( org.govmix.proxy.fatturapa.web.console.costanti.Costanti.DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI.equals( periodo)) {
				Calendar lastyear = (Calendar) today.clone();

				dataFine = Calendar.getInstance().getTime();

				lastyear.set(Calendar.HOUR_OF_DAY, 0);
				lastyear.set(Calendar.MINUTE, 0);
				lastyear.add(Calendar.DATE, -90);
				dataInizio = lastyear.getTime();

			}  else {
				// personalizzato
				dataInizio = dataRicezione.getValue();
				dataFine = dataRicezione.getValue2();

				Calendar calendarFine = Calendar.getInstance();
				calendarFine.setTime(dataFine); 
				// forzo l'ora della data fine alle 23:59:59
				calendarFine.set(Calendar.HOUR_OF_DAY, 23);
				calendarFine.set(Calendar.MINUTE, 59);
				calendarFine.set(Calendar.SECOND, 59);

				dataFine = calendarFine.getTime();
			}


			if(dataInizio != null){
				expr.greaterEquals(PccTraccia.model().DATA_CREAZIONE, dataInizio);
				expr.and();
			}

			if(dataFine != null){
				expr.lessEquals(PccTraccia.model().DATA_CREAZIONE, dataFine);
				expr.and();
			}

		}catch(Exception e){
			OperazioneService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	@Override
	public IdFattura getIdFattura() {
		return idFattura;
	}

	@Override
	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}

	public int countTracce(StatoType stato) throws ServiceException {
		return countTracce(this.idFattura, stato);
	}
	
	public int countTracce(IdFattura idFattura,StatoType stato) throws ServiceException {
		IExpression expr =  null;
		try{
			// Dettaglio Fattura
			if(idFattura != null){
				expr = this.tracciamentoBD.newExpression();
				expr.equals(PccTraccia.model().ID_FATTURA, idFattura.getId());
				expr.and();
				expr.equals(PccTraccia.model().STATO, stato);

				return (int) this.tracciamentoBD.countTracce(expr);
			}
		}catch(NotImplementedException e){
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		} catch (Exception e) {
			OperazioneService.log.error("Errore durante la _findAll: " + e.getMessage(),e); 
			throw new ServiceException(e);
		}

		return 0;
	}
}
