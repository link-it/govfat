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
package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.constants.EsitoType;
import org.govmix.proxy.fatturapa.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaSearchForm;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
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
import org.openspcoop2.generic_project.web.input.DateTime;
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.service.BaseService;

/**
 * FatturaElettronicaService classe di servizio per le entita' FatturaElettronica nel livello DAO.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
@RequestScoped @ManagedBean(name="fatturaElettronicaService")
public class FatturaElettronicaService extends BaseService<FatturaElettronicaSearchForm> implements IFatturaElettronicaService {

	private IFatturaElettronicaServiceSearch fatturaSearchDao = null;

	private static Logger log = LoggerManager.getConsoleDaoLogger();


	public FatturaElettronicaService(){
		try{
			this.fatturaSearchDao = DAOFactory.getInstance(log).getServiceManager().getFatturaElettronicaServiceSearch();

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}
	
	@PostConstruct
	private void initManagedProperties(){
		this.form = (FatturaElettronicaSearchForm) Utils.findBean("fatturaElettronicaSearchForm");
	}

	@Override
	public List<String> getMittenteAutoComplete(String val)
			throws ServiceException {
		String methodName = "getMittenteAutoComplete("+val+")";
		List<String> listaMittenti = new ArrayList<String>();


		try {

			IPaginatedExpression pagExpr = this.fatturaSearchDao.newPaginatedExpression();

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);
			pagExpr.ilike(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE,	val.toLowerCase(), LikeMode.ANYWHERE);

			List<Dipartimento> listaDipartimentiLoggedUtente = Utils.getListaDipartimentiLoggedUtente();

			if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0){
				Object values []= new Object[listaDipartimentiLoggedUtente.size()];
				for (int i = 0; i < listaDipartimentiLoggedUtente.size(); i++) {
					values[i] = listaDipartimentiLoggedUtente.get(i).getCodice();
				}

				pagExpr.in(FatturaElettronica.model().CODICE_DESTINATARIO, values);
			} else { // Se la lista dei dipartimenti e' vuota restituisco una lista vuota
				return listaMittenti;
			}


			List<Object> select = this.fatturaSearchDao.select(pagExpr, true, FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE);

			if(select != null && select.size() > 0){
				for (Object object : select) {
					listaMittenti.add((String) object);
				}
			}

		} catch (NotFoundException e) {
			log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}


		return listaMittenti;
	}

	@Override
	public List<FatturaElettronicaBean> findAll(int arg0, int arg1)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<FatturaElettronicaBean> lst = new ArrayList<FatturaElettronicaBean>();

		try{

			IExpression expr = getExpressionFromSearch( this.fatturaSearchDao,this.form);

			//order by
			expr.sortOrder(SortOrder.DESC);
			expr.addOrder(FatturaElettronica.model().DATA_RICEZIONE);

			IPaginatedExpression pagExpr = this.fatturaSearchDao.toPaginatedExpression(expr);

			pagExpr.offset(arg0);
			pagExpr.limit(arg1);

			List<FatturaElettronica> findAll = this.fatturaSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (FatturaElettronica fatturaElettronica : findAll) {
					FatturaElettronicaBean toRet = new FatturaElettronicaBean();
					toRet.setDTO(fatturaElettronica);
					lst.add(toRet);
				}
			}

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			IExpression expr = getExpressionFromSearch( this.fatturaSearchDao,this.form);

			NonNegativeNumber nnn = this.fatturaSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void delete(FatturaElettronicaBean arg0) throws ServiceException {

	}

	@Override
	public void deleteById(Long arg0) throws ServiceException {

	}

	@Override
	public List<FatturaElettronicaBean> findAll() throws ServiceException {
		String methodName = "findAll";

		List<FatturaElettronicaBean> lst = new ArrayList<FatturaElettronicaBean>();

		try{

			IExpression expr = getExpressionFromSearch( this.fatturaSearchDao,this.form);

			//order by
			expr.sortOrder(SortOrder.DESC);
			expr.addOrder(FatturaElettronica.model().DATA_RICEZIONE);

			IPaginatedExpression pagExpr = this.fatturaSearchDao.toPaginatedExpression(expr);

			List<FatturaElettronica> findAll = this.fatturaSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (FatturaElettronica fatturaElettronica : findAll) {
					FatturaElettronicaBean toRet = new FatturaElettronicaBean();
					toRet.setDTO(fatturaElettronica);
					lst.add(toRet);
				}
			}

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;


	}

	@Override
	public FatturaElettronicaBean findById(Long arg0)
			throws ServiceException {
		String methodName = "findById(id)";


		try{
			FatturaElettronica f = ((JDBCFatturaElettronicaServiceSearch)this.fatturaSearchDao).get(arg0.longValue());
			FatturaElettronicaBean fat = new FatturaElettronicaBean();
			fat.setDTO(f);

			return fat;

		}catch(NotFoundException e){
			log.debug("["+methodName+"] Fattura non trovata: "+ e.getMessage());
		} catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public void store(FatturaElettronicaBean arg0) throws ServiceException {

	}

	public IExpression getExpressionFromSearch(IFatturaElettronicaServiceSearch fatturaSearchDao, FatturaElettronicaSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr =  fatturaSearchDao.newExpression();

			// selezione mittente, se non viene scelto dovrei cercare solo le fatture destinate all'ente dell'utente loggato
			// ma questo vincolo e' gia' rispettato dal filtro sul dipartimento.
			if(search.getCedentePrestatore().getValue() != null && !StringUtils.isEmpty(search.getCedentePrestatore().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getCedentePrestatore().getValue())){
				List<SelectItem> cedPrestVals = this.form.getCedPrestSelList();
				
				List<String> valoriCedPret = new ArrayList<String>();
				for (SelectItem selectItem : cedPrestVals) {
					String val = selectItem.getValue();
					
					// Se il valore trimmato selezionato dall'utente corrisponde ad uno dei valori nella lista allora li uso per fare il confronto
					if(val.replace("  "," ").equals(search.getCedentePrestatore().getValue())){
						valoriCedPret.add(val);
					}
				}
				
				if(valoriCedPret.size() == 0)
					expr.ilike(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE, search.getCedentePrestatore().getValue(), LikeMode.ANYWHERE);
				else {
					IExpression orExpr = fatturaSearchDao.newExpression();
					for (String val : valoriCedPret) {
						orExpr.ilike(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE, val, LikeMode.ANYWHERE).or();
					}
					expr.and(orExpr);
				}
				//				expr.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE, search.getCedentePrestatore().getValue());
			}

			// se l'utente seleziona un dipartimento utilizzo il codice esatto altrimenti la ricerca deve essere effettuata solo sui dipartimenti che puo' vedere
			if(search.getDipartimento().getValue() != null && !StringUtils.isEmpty(search.getDipartimento().getValue().getValue()) && !search.getDipartimento().getValue().getValue().equals("*")){
				expr.equals(FatturaElettronica.model().CODICE_DESTINATARIO, search.getDipartimento().getValue().getValue());
			} else {
				List<Dipartimento> listaDipartimentiLoggedUtente = Utils.getListaDipartimentiLoggedUtente();

				if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0){
					Object values []= new Object[listaDipartimentiLoggedUtente.size()];
					for (int i = 0; i < listaDipartimentiLoggedUtente.size(); i++) {
						values[i] = listaDipartimentiLoggedUtente.get(i).getCodice();
					}

					expr.in(FatturaElettronica.model().CODICE_DESTINATARIO, values);
				}
			}

			expr.and(getExpressionDateFromSearch(fatturaSearchDao,search)).and();

			if(search.getTipoDocumento().getValue() != null &&
					!StringUtils.isEmpty(search.getTipoDocumento().getValue().getValue()) && !search.getTipoDocumento().getValue().getValue().equals("*")){
				TipoDocumentoType tipoDoc = TipoDocumentoType.toEnumConstant(search.getTipoDocumento().getValue().getValue());

				expr.equals(FatturaElettronica.model().TIPO_DOCUMENTO, tipoDoc);
			}

			// Esito Committente
			if(search.getNotificaEsitoCommittente().getValue() != null &&
					!StringUtils.isEmpty(search.getNotificaEsitoCommittente().getValue().getValue()) && !search.getNotificaEsitoCommittente().getValue().getValue().equals("*")){

				if(!search.getNotificaEsitoCommittente().getValue().getValue().equals("E")){
					EsitoType esitoType = EsitoType.toEnumConstant(search.getNotificaEsitoCommittente().getValue().getValue());
					IExpression esitoExpr = this.fatturaSearchDao.newExpression();

					//					if(esitoType.equals(EsitoType.ACCETTATO)){
					//						esitoExpr.equals(FatturaElettronica.model().ESITO, EsitoType.IN_ELABORAZIONE_ACCETTATO);
					//						esitoExpr.equals(FatturaElettronica.model().ESITO, esitoType);
					//						esitoExpr.or();
					//					} else if(esitoType.equals(EsitoType.RIFIUTATO)){
					//						esitoExpr.equals(FatturaElettronica.model().ESITO, EsitoType.IN_ELABORAZIONE_RIFIUTATO);
					//						esitoExpr.equals(FatturaElettronica.model().ESITO, esitoType);
					//						esitoExpr.or();
					//					} else if(esitoType.equals(EsitoType.IN_ELABORAZIONE_RIFIUTATO) || esitoType.equals(EsitoType.IN_ELABORAZIONE_ACCETTATO)){
					//						esitoExpr.equals(FatturaElettronica.model().ESITO, EsitoType.IN_ELABORAZIONE_RIFIUTATO);
					//						esitoExpr.equals(FatturaElettronica.model().ESITO, EsitoType.IN_ELABORAZIONE_ACCETTATO);
					//						esitoExpr.or();
					//					} else 
					esitoExpr.equals(FatturaElettronica.model().ESITO, esitoType);

					expr.and(esitoExpr);
				} else {
					expr.isNull(FatturaElettronica.model().ESITO);
				}
			}

			// Decorrenza Termini
			if(search.getNotificaDecorrenzaTermini().getValue() != null &&
					!StringUtils.isEmpty(search.getNotificaDecorrenzaTermini().getValue().getValue()) && !search.getNotificaDecorrenzaTermini().getValue().getValue().equals("*")){

				if(search.getNotificaDecorrenzaTermini().getValue().getValue().equals("Y")){
					expr.isNotNull(FatturaElettronica.model().ID_DECORRENZA_TERMINI.IDENTIFICATIVO_SDI);
				} else {
					expr.isNull(FatturaElettronica.model().ID_DECORRENZA_TERMINI.IDENTIFICATIVO_SDI);
				}
			}

			// numero
			//			if(search.getNumero().getValue() != null && !StringUtils.isEmpty(search.getNumero().getValue())){
			//				expr.equals(FatturaElettronica.model().NUMERO, search.getNumero().getValue());
			//			}
			//			
			if(search.getNumero().getValue() != null && !StringUtils.isEmpty(search.getNumero().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getNumero().getValue())){
				expr.ilike(FatturaElettronica.model().NUMERO, search.getNumero().getValue(), LikeMode.START);
				//				expr.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE, search.getCedentePrestatore().getValue());
			}

			// identificativoSDI
			if(search.getIdentificativoLotto().getValue() != null && !StringUtils.isEmpty(search.getIdentificativoLotto().getValue())){
				expr.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI, Integer.parseInt(search.getIdentificativoLotto().getValue()));
			}

			// data Fattura
			if(search.getDataEsatta().getValue() != null){
				expr.equals(FatturaElettronica.model().DATA, search.getDataEsatta().getValue());
			}

		}catch(Exception e){
			log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}


	private IExpression getExpressionDateFromSearch(IFatturaElettronicaServiceSearch fatturaSearchDao,FatturaElettronicaSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = fatturaSearchDao.newExpression();

			SelectList dataRicezionePeriodo = search.getDataRicezionePeriodo();
			DateTime dataRicezione = search.getDataRicezione();

			Date dataInizio = dataRicezione.getValue();
			Date dataFine = dataRicezione.getValue2();

			String periodo = dataRicezionePeriodo.getValue() != null ? dataRicezionePeriodo.getValue().getValue() : FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA;

			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 23);
			today.set(Calendar.MINUTE, 59);
			today.clear(Calendar.SECOND);
			today.clear(Calendar.MILLISECOND);

			//ultima settimana
			if ( FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMA_SETTIMANA.equals(periodo)) {
				Calendar lastWeek = (Calendar) today.clone();
				Calendar c = Calendar.getInstance();
				dataFine = c.getTime();
				lastWeek.set(Calendar.HOUR_OF_DAY, 0);
				lastWeek.set(Calendar.MINUTE, 0);
				lastWeek.add(Calendar.DATE, -7);
				dataInizio = lastWeek.getTime();

			} else if ( FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMO_MESE.equals( periodo)) {
				Calendar lastMonth = (Calendar) today.clone();

				// prendo la data corrente
				dataFine = Calendar.getInstance().getTime();

				// la data inizio rimane uguale sia per giornaliero che per orario
				lastMonth.set(Calendar.HOUR_OF_DAY, 0);
				lastMonth.set(Calendar.MINUTE, 0);
				lastMonth.add(Calendar.DATE, -30);
				dataInizio = lastMonth.getTime();

			} else if ( FatturaElettronicaSearchForm.DATA_RICEZIONE_PERIODO_ULTIMI_TRE_MESI.equals( periodo)) {
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
				expr.greaterEquals(FatturaElettronica.model().DATA_RICEZIONE, dataInizio);
				expr.and();
			}

			if(dataFine != null){
				expr.lessEquals(FatturaElettronica.model().DATA_RICEZIONE, dataFine);
				expr.and();
			}

		}catch(Exception e){
			log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	@Override
	public List<String> getNumeroAutoComplete(String val)
			throws ServiceException {
		String methodName = "getNumeroAutoComplete("+val+")";
		List<String> listaID = new ArrayList<String>();


		try {

			IPaginatedExpression pagExpr = this.fatturaSearchDao.newPaginatedExpression();

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(FatturaElettronica.model().NUMERO);
			pagExpr.ilike(FatturaElettronica.model().NUMERO,	val.toLowerCase(), LikeMode.START);

			List<Dipartimento> listaDipartimentiLoggedUtente = Utils.getListaDipartimentiLoggedUtente();

			if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0){
				Object values []= new Object[listaDipartimentiLoggedUtente.size()];
				for (int i = 0; i < listaDipartimentiLoggedUtente.size(); i++) {
					values[i] = listaDipartimentiLoggedUtente.get(i).getCodice();
				}

				pagExpr.in(FatturaElettronica.model().CODICE_DESTINATARIO, values);
			} else { // Se la lista dei dipartimenti e' vuota restituisco una lista vuota
				return listaID;
			}

			List<Object> select = this.fatturaSearchDao.select(pagExpr, true, FatturaElettronica.model().NUMERO);

			if(select != null && select.size() > 0){
				for (Object object : select) {
					listaID.add((String) object);
				}
			}

		} catch (NotFoundException e) {
			log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}


		return listaID;
	}

	@Override
	public boolean exists(FatturaElettronicaBean obj) throws ServiceException {
		return false;
	}
	@Override
	public List<FatturaElettronicaBean> findAll(
			FatturaElettronicaSearchForm form) throws ServiceException {
		return null;
	}
}
