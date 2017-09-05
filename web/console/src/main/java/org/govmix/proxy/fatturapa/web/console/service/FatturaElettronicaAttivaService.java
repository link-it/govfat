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
package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLotti;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoRequest;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoSoloConservazioneRequest;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaAttivaBean;
import org.govmix.proxy.fatturapa.web.console.iservice.IFatturaElettronicaAttivaService;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaAttivaSearchForm;
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
public class FatturaElettronicaAttivaService extends BaseService<FatturaElettronicaAttivaSearchForm> implements IFatturaElettronicaAttivaService {

	private static Logger log = LoggerManager.getDaoLogger();

	private FatturaAttivaBD fatturaAttivaBD= null;
	private InserimentoLotti inserimentoLotti = null;
	
	
	public FatturaElettronicaAttivaService(){
		try{
			this.fatturaAttivaBD = new FatturaAttivaBD(log);
			this.inserimentoLotti = new InserimentoLotti(log);
		}catch(Exception e){
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<String> getDestinatarioAutoComplete(String val)
			throws ServiceException {
		String methodName = "getDestinatarioAutoComplete("+val+")";
		List<String> listaMittenti = new ArrayList<String>();


		try {
			
			FatturaAttivaFilter filter = this.fatturaAttivaBD.newFilter();
			
			filter.setUtente(Utils.getLoggedUtente());
			filter.getCcDenominazioneList().add(val.toLowerCase());
			
			listaMittenti = this.fatturaAttivaBD.getAutocompletamentoCessionarioCommittenteDenominazione(filter);

		}  catch(ServiceException e) {
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}  


		return listaMittenti;
	}

	@Override
	public List<FatturaElettronicaAttivaBean> findAll(int arg0, int arg1)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<FatturaElettronicaAttivaBean> lst = new ArrayList<FatturaElettronicaAttivaBean>();

		try{
			if(abilitaRicerca()){
				FatturaAttivaFilter filter = (FatturaAttivaFilter) this.getFilterFromSearch(fatturaAttivaBD, this.form);

				//order by
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.ASC);
				fsw.setField(FatturaElettronica.model().DATA_RICEZIONE);
				filter.getFilterSortList().add(fsw);

				filter.setOffset(arg0);
				filter.setLimit(arg1);
				
				List<FatturaElettronica> findAll = this.fatturaAttivaBD.findAll(filter);

				if(findAll != null && findAll.size() > 0){
					for (FatturaElettronica fatturaElettronica : findAll) {
						FatturaElettronicaAttivaBean toRet = new FatturaElettronicaAttivaBean();
						toRet.setDTO(fatturaElettronica);
						lst.add(toRet);
					}
				}
			}else return lst;

		}catch(Exception e){
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			if(abilitaRicerca()){
				FatturaAttivaFilter filter = (FatturaAttivaFilter) this.getFilterFromSearch(fatturaAttivaBD, this.form);
				cnt = (int)   this.fatturaAttivaBD.count(filter);						
			}
			else 
				return 0;
		}catch(Exception e){
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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
	public void delete(FatturaElettronicaAttivaBean arg0) throws ServiceException {

	}

	@Override
	public void deleteById(Long arg0) throws ServiceException {

	}

	@Override
	public List<FatturaElettronicaAttivaBean> findAll() throws ServiceException {
		String methodName = "findAll";

		List<FatturaElettronicaAttivaBean> lst = new ArrayList<FatturaElettronicaAttivaBean>();

		try{
			if(abilitaRicerca()){
				FatturaAttivaFilter filter = (FatturaAttivaFilter) this.getFilterFromSearch(fatturaAttivaBD, this.form);

				//order by
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.ASC);
				fsw.setField(FatturaElettronica.model().DATA_RICEZIONE);
				filter.getFilterSortList().add(fsw);

				List<FatturaElettronica> findAll = this.fatturaAttivaBD.findAll(filter);

				if(findAll != null && findAll.size() > 0){
					for (FatturaElettronica fatturaElettronica : findAll) {
						FatturaElettronicaAttivaBean toRet = new FatturaElettronicaAttivaBean();
						toRet.setDTO(fatturaElettronica);
						lst.add(toRet);
					}
				}
			}else 
				return lst;

		}catch(Exception e){
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;


	}

	@Override
	public FatturaElettronicaAttivaBean findById(Long arg0)
			throws ServiceException {
		String methodName = "findById(id)";


		try{
			FatturaElettronica f = this.fatturaAttivaBD.getById(arg0.longValue());
			FatturaElettronicaAttivaBean fat = new FatturaElettronicaAttivaBean();
			fat.setDTO(f);

			return fat;

		}catch(NotFoundException e){
			FatturaElettronicaAttivaService.log.debug("["+methodName+"] Fattura non trovata: "+ e.getMessage());
		} catch(Exception e){
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public void store(FatturaElettronicaAttivaBean arg0) throws ServiceException {}
	
	@Override
	public InserimentoLottoResponse salvaFatture(List<InserimentoLottoRequest> listaFatture) throws ServiceException {
		String methodName = "salvaFatture(listaFatture)";
		
		FatturaElettronicaAttivaService.log.debug("Esecuzione ["+methodName+"] in corso...");
		
		InserimentoLottoResponse inserimentoLottoResponse = this.inserimentoLotti.inserisciLotto(listaFatture);
		
		FatturaElettronicaAttivaService.log.debug("Esecuzione ["+methodName+"] completato.");
		
		return inserimentoLottoResponse;
	}
	
	@Override
	public InserimentoLottoResponse salvaFattureSoloConservazione(
			List<InserimentoLottoSoloConservazioneRequest> listaFatture) throws ServiceException {
		String methodName = "store(salvaFattureSoloConservazione)";
		
		FatturaElettronicaAttivaService.log.debug("Esecuzione ["+methodName+"] in corso...");
		
		InserimentoLottoResponse inserimentoLottoResponse = this.inserimentoLotti.inserisciLottoSoloConservazione(listaFatture);
		
		FatturaElettronicaAttivaService.log.debug("Esecuzione ["+methodName+"] completato.");
		
		return inserimentoLottoResponse;
	}

	public FatturaFilter getFilterFromSearch(FatturaBD fatturaBD, FatturaElettronicaAttivaSearchForm search) throws Exception{
		FatturaAttivaFilter filter = (FatturaAttivaFilter) this.getFilterDateFromSearch(fatturaBD, search);

		try{
			// selezione mittente, se non viene scelto dovrei cercare solo le fatture destinate all'ente dell'utente loggato
			// ma questo vincolo e' gia' rispettato dal filtro sul dipartimento.
			if(search.getCessionarioCommittente().getValue() != null && !StringUtils.isEmpty(search.getCessionarioCommittente().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getCessionarioCommittente().getValue())){
				List<SelectItem> cedPrestVals = this.form.getCessCommSelList();
				String trimSelCedPrest = search.getCessionarioCommittente().getValue().trim();

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
					filter.getCcDenominazioneList().add(search.getCessionarioCommittente().getValue());
					
				}else {
					filter.getCcDenominazioneList().addAll(valoriCedPret);
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

			// numero
			if(search.getNumero().getValue() != null && !StringUtils.isEmpty(search.getNumero().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getNumero().getValue())){
				filter.setNumero(search.getNumero().getValue());
			}

			// identificativoSDI
			if(search.getIdentificativoLotto().getValue() != null && !StringUtils.isEmpty(search.getIdentificativoLotto().getValue())){
				filter.setIdentificativoSdi(Integer.parseInt(search.getIdentificativoLotto().getValue()));
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
			if(search.getStatoElaborazione().getValue() != null &&
					!StringUtils.isEmpty(search.getStatoElaborazione().getValue().getValue()) && !search.getStatoElaborazione().getValue().getValue().equals("*")){
				StatoElaborazioneType statoElaborazioneType = StatoElaborazioneType.toEnumConstant(search.getStatoElaborazione().getValue().getValue());
				filter.getStatoElaborazioneList().add(statoElaborazioneType);
			}

		}catch(Exception e){
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return filter;
	}

	public FatturaFilter getFilterDateFromSearch(FatturaBD fatturaBD, FatturaElettronicaAttivaSearchForm search) throws Exception{
		FatturaAttivaFilter filter = this.fatturaAttivaBD.newFilter();

		try{
			SelectList<SelectItem> dataRicezionePeriodo = search.getDataInvioPeriodo();
			DateTime dataRicezione = search.getDataInvio();

			Date dataInizio = dataRicezione.getValue();
			Date dataFine = dataRicezione.getValue2();

			String periodo = dataRicezionePeriodo.getValue() != null ? dataRicezionePeriodo.getValue().getValue() : FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_ULTIMA_SETTIMANA;

			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 23);
			today.set(Calendar.MINUTE, 59);
			today.clear(Calendar.SECOND);
			today.clear(Calendar.MILLISECOND);

			//ultima settimana
			if ( FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_ULTIMA_SETTIMANA.equals(periodo)) {
				Calendar lastWeek = (Calendar) today.clone();
				Calendar c = Calendar.getInstance();
				dataFine = c.getTime();
				lastWeek.set(Calendar.HOUR_OF_DAY, 0);
				lastWeek.set(Calendar.MINUTE, 0);
				lastWeek.add(Calendar.DATE, -7);
				dataInizio = lastWeek.getTime();

			} else if ( FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_ULTIMO_MESE.equals( periodo)) {
				Calendar lastMonth = (Calendar) today.clone();

				// prendo la data corrente
				dataFine = Calendar.getInstance().getTime();

				// la data inizio rimane uguale sia per giornaliero che per orario
				lastMonth.set(Calendar.HOUR_OF_DAY, 0);
				lastMonth.set(Calendar.MINUTE, 0);
				lastMonth.add(Calendar.DATE, -30);
				dataInizio = lastMonth.getTime();

			} else if ( FatturaElettronicaAttivaSearchForm.DATA_INVIO_PERIODO_ULTIMI_TRE_MESI.equals( periodo)) {
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
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
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
			
			FatturaAttivaFilter filter = this.fatturaAttivaBD.newFilter();
			
			filter.setUtente(Utils.getLoggedUtente());
			filter.setNumeroLike(val.toLowerCase());
			
			listaID = this.fatturaAttivaBD.getAutocompletamentoNumero(filter);

		}  catch(ServiceException e) {
			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}  

		return listaID;
	}

	@Override
	public boolean exists(FatturaElettronicaAttivaBean obj) throws ServiceException {
		return false;
	}
	@Override
	public List<FatturaElettronicaAttivaBean> findAll(
			FatturaElettronicaAttivaSearchForm form) throws ServiceException {
		return null;
	}


	protected String getRootTable(IExpressionConstructor expressionConstructor) throws ExpressionException {
		ISQLFieldConverter converter = ((IDBServiceUtilities<?>)expressionConstructor).getFieldConverter();
		IModel<?> model = converter.getRootModel();
		return converter.toTable(model);
	}

//	@Override
//	public Date getDataUltimaOperazioneByIdFattura(IdFattura idFattura) throws ServiceException {
//		String methodName = "getDataUltimaOperazioneByIdFattura()";
//
//		try{
//			return this.operazioneContabileBD.getDataUltimaOperazioneByIdFattura(idFattura);
//		}catch(Exception e){
//			FatturaElettronicaAttivaService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
//			throw new ServiceException(e);
//		}
//	}
}
