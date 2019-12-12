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
package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccOperazioneContabileBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaService;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaSearchForm;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.IDBServiceUtilities;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.input.DateTime;
import org.openspcoop2.generic_project.web.input.SelectList;
import org.openspcoop2.generic_project.web.service.BaseService;

/**
 * FatturaElettronicaService classe di servizio per le entita' FatturaElettronica nel livello DAO.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class FatturaElettronicaService extends BaseService<FatturaElettronicaSearchForm> implements IFatturaElettronicaService {

	private static Logger log = LoggerManager.getDaoLogger();

	private PccOperazioneContabileBD operazioneContabileBD = null;
	private FatturaPassivaBD fatturaPassivaBD= null;

	public FatturaElettronicaService(){
		try{
			this.operazioneContabileBD = new PccOperazioneContabileBD(FatturaElettronicaService.log);
			this.fatturaPassivaBD = new FatturaPassivaBD(log);
		}catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<String> getMittenteAutoComplete(String val)
			throws ServiceException {
		String methodName = "getMittenteAutoComplete("+val+")";
		List<String> listaMittenti = new ArrayList<String>();


		try {
			
			FatturaPassivaFilter filter = this.fatturaPassivaBD.newFilter();
			
			filter.setUtente(Utils.getLoggedUtente());
			filter.getCpDenominazioneList().add(val.toLowerCase());
			
			listaMittenti = this.fatturaPassivaBD.getAutocompletamentoCedentePrestatoreDenominazione(filter);

		}  catch(ServiceException e) {
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}  


		return listaMittenti;
	}

	@Override
	public List<FatturaElettronicaBean> findAll(int arg0, int arg1)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<FatturaElettronicaBean> lst = new ArrayList<FatturaElettronicaBean>();

		try{
			if(abilitaRicerca()){
				FatturaPassivaFilter filter = (FatturaPassivaFilter) this.getFilterFromSearch(fatturaPassivaBD, this.form);

				//order by
				if(this.form.isUsaDataScadenza()){
					FilterSortWrapper fsw = new FilterSortWrapper();
					fsw.setSortOrder(SortOrder.ASC);
					fsw.setField(FatturaElettronica.model().DATA_SCADENZA);
					filter.getFilterSortList().add(fsw);
				}
				
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.DESC);
				fsw.setField(FatturaElettronica.model().DATA_RICEZIONE);
				filter.getFilterSortList().add(fsw);

				filter.setOffset(arg0);
				filter.setLimit(arg1);
				
				List<FatturaElettronica> findAll = this.fatturaPassivaBD.fatturaElettronicaSelectForListaFatture(filter);

				if(findAll != null && findAll.size() > 0){
					for (FatturaElettronica fatturaElettronica : findAll) {
						FatturaElettronicaBean toRet = new FatturaElettronicaBean();
						toRet.setDTO(fatturaElettronica);
						lst.add(toRet);
					}
				}
			}else return lst;

		}catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			if(abilitaRicerca()){
				FatturaPassivaFilter filter = (FatturaPassivaFilter) this.getFilterFromSearch(fatturaPassivaBD, this.form);
				cnt = (int)   this.fatturaPassivaBD.count(filter);						
			}
			else 
				return 0;
		}catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	private boolean abilitaRicerca(){
		boolean ricercaAbilitata = Utils.getLoginBean().isAdmin();

		if(!ricercaAbilitata){
			List<Dipartimento> listaDipartimentiLoggedUtente = Utils.getListaDipartimentiLoggedUtente();

			if(listaDipartimentiLoggedUtente != null && listaDipartimentiLoggedUtente.size() > 0){
				ricercaAbilitata = true;
			}
		}

		return ricercaAbilitata;
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
			if(abilitaRicerca()){
				FatturaPassivaFilter filter = (FatturaPassivaFilter) this.getFilterFromSearch(fatturaPassivaBD, this.form);

				//order by
				if(this.form.isUsaDataScadenza()){
					FilterSortWrapper fsw = new FilterSortWrapper();
					fsw.setSortOrder(SortOrder.ASC);
					fsw.setField(FatturaElettronica.model().DATA_SCADENZA);
					filter.getFilterSortList().add(fsw);
				}
				
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.DESC);
				fsw.setField(FatturaElettronica.model().DATA_RICEZIONE);
				filter.getFilterSortList().add(fsw);

				List<FatturaElettronica> findAll = this.fatturaPassivaBD.fatturaElettronicaSelectForListaFatture(filter);

				if(findAll != null && findAll.size() > 0){
					for (FatturaElettronica fatturaElettronica : findAll) {
						FatturaElettronicaBean toRet = new FatturaElettronicaBean();
						toRet.setDTO(fatturaElettronica);
						lst.add(toRet);
					}
				}
			}else 
				return lst;

		}catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;


	}

	@Override
	public FatturaElettronicaBean findById(Long arg0)
			throws ServiceException {
		String methodName = "findById(id)";


		try{
			FatturaElettronica f = this.fatturaPassivaBD.getById(arg0.longValue());
			FatturaElettronicaBean fat = new FatturaElettronicaBean();
			fat.setDTO(f);

			return fat;

		}catch(NotFoundException e){
			FatturaElettronicaService.log.debug("["+methodName+"] Fattura non trovata: "+ e.getMessage());
		} catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public void store(FatturaElettronicaBean arg0) throws ServiceException {

	}

	public FatturaFilter getFilterFromSearch(FatturaBD fatturaBD, FatturaElettronicaSearchForm search) throws Exception{
		FatturaPassivaFilter filter = (FatturaPassivaFilter) this.getFilterDateFromSearch(fatturaBD, search);

		try{
			// selezione mittente, se non viene scelto dovrei cercare solo le fatture destinate all'ente dell'utente loggato
			// ma questo vincolo e' gia' rispettato dal filtro sul dipartimento.
			if(search.getCedentePrestatore().getValue() != null && !StringUtils.isEmpty(search.getCedentePrestatore().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getCedentePrestatore().getValue())){
				List<SelectItem> cedPrestVals = this.form.getCedPrestSelList();
				String trimSelCedPrest = search.getCedentePrestatore().getValue().trim();

//				log.debug("Confronto TRIM["+trimSelCedPrest+"]");
				List<String> valoriCedPret = new ArrayList<String>();
				for (SelectItem selectItem : cedPrestVals) {
					String val = selectItem.getValue();

					String noSpaceRes = val.trim().replace("  ", " ");
					while(noSpaceRes.contains("  ")) {
						noSpaceRes = noSpaceRes.replace("  ", " ");
					}

//					log.debug("Confronto NSRES["+noSpaceRes+"]");
					// Se il valore trimmato selezionato dall'utente corrisponde ad uno dei valori nella lista allora li uso per fare il confronto
					if(noSpaceRes.equals(trimSelCedPrest)){
						valoriCedPret.add(val);
					}
				}

				if(valoriCedPret.size() == 0) {
					filter.getCpDenominazioneList().add(search.getCedentePrestatore().getValue());
					
				}else {
					filter.getCpDenominazioneList().addAll(valoriCedPret);
				}
			}

			// se l'utente seleziona un dipartimento utilizzo il codice esatto altrimenti la ricerca deve essere effettuata solo sui dipartimenti che puo' vedere
			if(search.getDipartimento().getValue() != null && !StringUtils.isEmpty(search.getDipartimento().getValue().getValue()) && !search.getDipartimento().getValue().getValue().equals("*")){
				filter.setCodiceDestinatario(search.getDipartimento().getValue().getValue());
			} else {
				filter.setUtente(Utils.getLoggedUtente());
			}

			if(search.getTipoDocumento().getValue() != null &&
					!StringUtils.isEmpty(search.getTipoDocumento().getValue().getValue()) && !search.getTipoDocumento().getValue().getValue().equals("*")){
				filter.setTipoDocumento(search.getTipoDocumento().getValue().getValue());
			}

			// Esito Committente
			if(search.getNotificaEsitoCommittente().getValue() != null &&
					!StringUtils.isEmpty(search.getNotificaEsitoCommittente().getValue().getValue()) && !search.getNotificaEsitoCommittente().getValue().getValue().equals("*")){

				if(!search.getNotificaEsitoCommittente().getValue().getValue().equals("E")){
					EsitoType esitoType = EsitoType.toEnumConstant(search.getNotificaEsitoCommittente().getValue().getValue());
					filter.setEsito(esitoType);
				} else {
					filter.setEsitoNull(true);
				}
			}

			// Decorrenza Termini
			if(search.getNotificaDecorrenzaTermini().getValue() != null &&
					!StringUtils.isEmpty(search.getNotificaDecorrenzaTermini().getValue().getValue()) && !search.getNotificaDecorrenzaTermini().getValue().getValue().equals("*")){
				if(search.getNotificaDecorrenzaTermini().getValue().getValue().equals("Y")){
					filter.setDecorrenzaTermini(true);
				} else {
					filter.setDecorrenzaTermini(false);
				}
			}

			// numero
			if(search.getNumero().getValue() != null && !StringUtils.isEmpty(search.getNumero().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getNumero().getValue())){
				filter.setNumero(search.getNumero().getValue());
			}

			// identificativoSDI
			if(search.getIdentificativoLotto().getValue() != null && !StringUtils.isEmpty(search.getIdentificativoLotto().getValue())){
				filter.setIdentificativoSdi(Long.parseLong(search.getIdentificativoLotto().getValue()));
			}

			// data Fattura
			if(search.getDataEsatta().getValue() != null){
				filter.setDataFatturaMin(search.getDataEsatta().getValue());
				filter.setDataFatturaMax(search.getDataEsatta().getValue());
			}

			// identificativo Protocollo
			if(search.getIdentificativoProtocollo().getValue() != null && !StringUtils.isEmpty(search.getIdentificativoProtocollo().getValue())){
				filter.setProtocollo(search.getIdentificativoProtocollo().getValue());
			}

			// stato consegna
			if(search.getStatoConsegna().getValue() != null &&
					!StringUtils.isEmpty(search.getStatoConsegna().getValue().getValue()) && !search.getStatoConsegna().getValue().getValue().equals("*")){
				StatoConsegnaType statoConsegnaType = StatoConsegnaType.toEnumConstant(search.getStatoConsegna().getValue().getValue());
				filter.getStatiConsegna().add(statoConsegnaType);
			}

			// filtro per le fatture in scadenza
			if(this.form.isUsaDataScadenza()){
				filter.setInScadenza(true);
			}


		}catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return filter;
	}

	public FatturaFilter getFilterDateFromSearch(FatturaBD fatturaBD, FatturaElettronicaSearchForm search) throws Exception{
		FatturaPassivaFilter filter = fatturaPassivaBD.newFilter();

		try{
			SelectList<SelectItem> dataRicezionePeriodo = search.getDataRicezionePeriodo();
			DateTime dataRicezione = search.getDataRicezione();

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
				filter.setDataRicezioneMin(dataInizio);
			}

			if(dataFine != null){
				filter.setDataRicezioneMax(dataFine);
			}

		}catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return filter;
	}

	@Override
	public List<String> getNumeroAutoComplete(String val)
			throws ServiceException {
		String methodName = "getNumeroAutoComplete("+val+")";
		List<String> listaID = new ArrayList<String>();


		try {
			
			FatturaPassivaFilter filter = this.fatturaPassivaBD.newFilter();
			
			filter.setUtente(Utils.getLoggedUtente());
			filter.setNumeroLike(val.toLowerCase());
			
			listaID = this.fatturaPassivaBD.getAutocompletamentoNumero(filter);

		}  catch(ServiceException e) {
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
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

	@Override
	public Long getIdEsitoScadenza(Long idFattura) throws ServiceException {
		String methodName = "getIdEsitoScadenza()";
		try {

			return this.fatturaPassivaBD.getIdEsitoScadenza(idFattura);
		} catch (NotFoundException e) {
			FatturaElettronicaService.log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch(ServiceException e) {
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} 

		return null;
	}

	@Override
	public Long getIdEsitoContabilizzazione(Long idFattura) throws ServiceException {
		String methodName = "getIdEsitoContabilizzazione()";
		try {
			
			return this.fatturaPassivaBD.getIdEsitoContabilizzazione(idFattura);

		} catch (NotFoundException e) {
			FatturaElettronicaService.log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch(ServiceException e) {
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} 

		return null;
	}

	protected String getRootTable(IExpressionConstructor expressionConstructor) throws ExpressionException {
		ISQLFieldConverter converter = ((IDBServiceUtilities<?>)expressionConstructor).getFieldConverter();
		IModel<?> model = converter.getRootModel();
		return converter.toTable(model);
	}

	@Override
	public Date getDataUltimaOperazioneByIdFattura(IdFattura idFattura) throws ServiceException {
		String methodName = "getDataUltimaOperazioneByIdFattura()";

		try{
			return this.operazioneContabileBD.getDataUltimaOperazioneByIdFattura(idFattura);
		}catch(Exception e){
			FatturaElettronicaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
}
