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
package org.govmix.proxy.fatturapa.web.console.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.ConservazioneBean;
import org.govmix.proxy.fatturapa.web.console.costanti.Costanti;
import org.govmix.proxy.fatturapa.web.console.iservice.IConservazioneService;
import org.govmix.proxy.fatturapa.web.console.search.ConservazioneSearchForm;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.IDBServiceUtilities;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.web.service.BaseService;

/**
 * ConservazioneService classe di servizio per le entita' FatturaElettronica nel livello DAO.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class ConservazioneService extends BaseService<ConservazioneSearchForm> implements IConservazioneService {

	private static Logger log = LoggerManager.getDaoLogger();

	private FatturaBD fatturaBD= null;


	public ConservazioneService(){
		try{
			this.fatturaBD = new FatturaBD(log);
		}catch(Exception e){
			ConservazioneService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}



	@Override
	public List<ConservazioneBean> findAll(int arg0, int arg1)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<ConservazioneBean> lst = new ArrayList<ConservazioneBean>();

		try{
			if(abilitaRicerca()){
				FatturaFilter filter = this.getFilterFromSearch(fatturaBD, this.form);

				//order by
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.DESC);
				fsw.setField(FatturaElettronica.model().DATA_RICEZIONE);
				filter.getFilterSortList().add(fsw);

				filter.setOffset(arg0);
				filter.setLimit(arg1);

				List<FatturaElettronica> findAll = this.fatturaBD.findAll(filter);

				if(findAll != null && findAll.size() > 0){
					for (FatturaElettronica fatturaElettronica : findAll) {
						ConservazioneBean toRet = new ConservazioneBean();
						toRet.setDTO(fatturaElettronica);
						lst.add(toRet);
					}
				}
			}else return lst;

		}catch(Exception e){
			ConservazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			if(abilitaRicerca()){
				FatturaFilter filter = this.getFilterFromSearch(fatturaBD, this.form);
				cnt = (int)   this.fatturaBD.count(filter);						
			}
			else 
				return 0;
		}catch(Exception e){
			ConservazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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
	public void delete(ConservazioneBean arg0) throws ServiceException {

	}

	@Override
	public void deleteById(Long arg0) throws ServiceException {

	}

	@Override
	public List<ConservazioneBean> findAll() throws ServiceException {
		String methodName = "findAll";

		List<ConservazioneBean> lst = new ArrayList<ConservazioneBean>();

		try{
			if(abilitaRicerca()){
				FatturaFilter filter = this.getFilterFromSearch(fatturaBD, this.form);

				//order by
				FilterSortWrapper fsw = new FilterSortWrapper();
				fsw.setSortOrder(SortOrder.DESC);
				fsw.setField(FatturaElettronica.model().DATA_RICEZIONE);
				filter.getFilterSortList().add(fsw);

				List<FatturaElettronica> findAll = this.fatturaBD.findAll(filter);

				if(findAll != null && findAll.size() > 0){
					for (FatturaElettronica fatturaElettronica : findAll) {
						ConservazioneBean toRet = new ConservazioneBean();
						toRet.setDTO(fatturaElettronica);
						lst.add(toRet);
					}
				}
			}else 
				return lst;

		}catch(Exception e){
			ConservazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;


	}

	@Override
	public ConservazioneBean findById(Long arg0)
			throws ServiceException {
		String methodName = "findById(id)";


		try{
			FatturaElettronica f = this.fatturaBD.getById(arg0.longValue());
			ConservazioneBean fat = new ConservazioneBean();
			fat.setDTO(f);

			return fat;

		}catch(NotFoundException e){
			ConservazioneService.log.debug("["+methodName+"] Fattura non trovata: "+ e.getMessage());
		} catch(Exception e){
			ConservazioneService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public void store(ConservazioneBean arg0) throws ServiceException {}

	//	@Override
	//	public InserimentoLottoResponse salvaFatture(List<InserimentoLottoRequest> listaFatture) throws ServiceException {
	//		String methodName = "salvaFatture(listaFatture)";
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] in corso...");
	//		
	//		InserimentoLottoResponse inserimentoLottoResponse = this.inserimentoLotti.inserisciLotto(listaFatture);
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] completato.");
	//		
	//		return inserimentoLottoResponse;
	//	}
	//	
	//	@Override
	//	public InserimentoLottoResponse salvaFattureSoloConservazione(
	//			List<InserimentoLottoSoloConservazioneRequest> listaFatture) throws ServiceException {
	//		String methodName = "store(salvaFattureSoloConservazione)";
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] in corso...");
	//		
	//		InserimentoLottoResponse inserimentoLottoResponse = this.inserimentoLotti.inserisciLottoSoloConservazione(listaFatture);
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] completato.");
	//		
	//		return inserimentoLottoResponse;
	//	}
	//	
	//	@Override
	//	public void checkLotto(List<InserimentoLottoRequest> requestList) throws InserimentoLottiException {
	//		String methodName = "checkLotto";
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] in corso...");
	//		
	//		this.inserimentoLotti.checkLotto(requestList);
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] completato.");
	//	}
	//
	//	@Override
	//	public void checkLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> requestList)
	//			throws InserimentoLottiException {
	//		String methodName = "checkLottoSoloConservazione";
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] in corso...");
	//		
	//		this.inserimentoLotti.checkLottoSoloConservazione(requestList);
	//		
	//		ConservazioneService.log.debug("Esecuzione ["+methodName+"] completato.");
	//	}

	public FatturaFilter getFilterFromSearch(FatturaBD fatturaBD, ConservazioneSearchForm search) throws Exception{
		FatturaFilter filter = null;
		try{	
			boolean attiva =false;
			// tipo fattura
			if(search.getTipoFattura().getValue() != null && !StringUtils.isEmpty(search.getTipoFattura().getValue().getValue())){
				attiva = Costanti.TIPO_FATTURA_ATTIVA_VALUE.equals(search.getTipoFattura().getValue().getValue());
			}

			filter = fatturaBD.newFilter(attiva);

			// anno
			if(search.getAnno().getValue() != null && !StringUtils.isEmpty(search.getAnno().getValue().getValue())){
				String annoS = search.getAnno().getValue().getValue();
				int anno = Integer.parseInt(annoS);
				filter.setAnno(anno); 
			}

			// stato invio
			if(search.getStatoInvio().getValue() != null &&
					!StringUtils.isEmpty(search.getStatoInvio().getValue().getValue()) && !search.getStatoInvio().getValue().getValue().equals("*")){
				// TODO agganciare nuovo filtro per stato invio conservazione
//				if(attiva) {
//					StatoElaborazioneType statoElaborazioneType = StatoElaborazioneType.toEnumConstant(search.getStatoInvio().getValue().getValue());
//					filter.getStatoElaborazioneList().add(statoElaborazioneType);
//				} else {
//					//					if(!search.getStatoInvio().getValue().getValue().equals("E")){
//					//						EsitoType esitoType = EsitoType.toEnumConstant(search.getStatoInvio().getValue().getValue());
//					//						filter.setEsito(esitoType);
//					//					} else {
//					//						filter.setEsitoNull(true);
//					//					}
//				}
			}

			// ente
			if(search.getEnte().getValue() != null &&
					!StringUtils.isEmpty(search.getEnte().getValue().getValue()) && !search.getEnte().getValue().getValue().equals("*")){
				filter.setEnte(search.getEnte().getValue().getValue());
			}

		}catch(Exception e){
			ConservazioneService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return filter;
	}


	@Override
	public boolean exists(ConservazioneBean obj) throws ServiceException {
		return false;
	}
	@Override
	public List<ConservazioneBean> findAll(
			ConservazioneSearchForm form) throws ServiceException {
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
