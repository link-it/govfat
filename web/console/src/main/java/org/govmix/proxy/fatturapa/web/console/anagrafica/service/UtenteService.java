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
package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.dao.IPccOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccUtenteOperazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccUtenteOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCUtenteService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.UtenteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.UtenteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IUtenteService;
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
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.service.BaseService;

public class UtenteService extends BaseService<UtenteSearchForm> implements IUtenteService {

	public static final String SEPARATORE = "$$$$"; 

	private IUtenteServiceSearch utenteSearchDao = null;
	private org.govmix.proxy.fatturapa.orm.dao.IUtenteService utenteDao = null;
	private IPccOperazioneServiceSearch pccOperazioniSearchDao = null;
	private IPccUtenteOperazioneServiceSearch pccUtenteOperazioniSearchDao  =null;
	private IPccUtenteOperazioneService pccUtenteOperazioniDao  =null;

	private static Logger log = LoggerManager.getDaoLogger();

	public UtenteService(){
		try{
			this.utenteDao = DAOFactory.getInstance().getServiceManager().getUtenteService();
			this.utenteSearchDao = DAOFactory.getInstance().getServiceManager().getUtenteServiceSearch();
			this.pccOperazioniSearchDao = DAOFactory.getInstance().getServiceManager().getPccOperazioneServiceSearch();
			this.pccUtenteOperazioniDao = DAOFactory.getInstance().getServiceManager().getPccUtenteOperazioneService();
			this.pccUtenteOperazioniSearchDao = DAOFactory.getInstance().getServiceManager().getPccUtenteOperazioneServiceSearch();


		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<UtenteBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<UtenteBean> lst = new ArrayList<UtenteBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Utente.model().COGNOME);
			expr.addOrder(Utente.model().NOME);

			IPaginatedExpression pagExpr = this.utenteSearchDao.toPaginatedExpression(expr);

			pagExpr.offset(start);
			pagExpr.limit(limit);

			List<Utente> findAll = this.utenteSearchDao.findAll(pagExpr,false);

			if(findAll != null && findAll.size() > 0){
				for (Utente utente : findAll) {
					UtenteBean toRet = new UtenteBean();
					toRet.setDTO(utente);
					lst.add(toRet);
				}
			}

		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.utenteSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(UtenteBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Utente utenteNew = obj.getDTO();

			Date dataUltimaModifica = new Date();
			IdUtente idResponsabile = new IdUtente();
			idResponsabile.setUsername(Utils.getLoggedUtente().getUsername());
			
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(utenteNew.getUsername());

			if(this.utenteDao.exists(idUtente)) {
				Utente utenteOriginale = this.utenteDao.get(idUtente);

				Map<String,UtenteDipartimento> map = new HashMap<String, UtenteDipartimento>();
				
				for(UtenteDipartimento dip: utenteOriginale.getUtenteDipartimentoList()) {
					map.put(dip.getIdDipartimento().getCodice(), dip);
				}
				
				List<UtenteDipartimento> lst = new ArrayList<UtenteDipartimento>();
				
				for(UtenteDipartimento dip: utenteNew.getUtenteDipartimentoList()) {
					if(map.containsKey(dip.getIdDipartimento().getCodice())) {
						lst.add(map.get(dip.getIdDipartimento().getCodice()));
					} else {
						dip.setIdResponsabile(idResponsabile);
						dip.setDataUltimaModifica(dataUltimaModifica);
						lst.add(dip);
					}
				}
				
				utenteNew.setUtenteDipartimentoList(lst);
				this.utenteDao.update(idUtente, utenteNew);
			}
			else {
				this.utenteDao.create(utenteNew);
				for(UtenteDipartimento dip: utenteNew.getUtenteDipartimentoList()) {
					dip.setIdResponsabile(idResponsabile);
					dip.setDataUltimaModifica(dataUltimaModifica);
				}
			}
		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		try{
			UtenteBean utente = this.findById(key);
			
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(utente.getDTO().getUsername());
			
			this.deleteOperazioniUtente(idUtente); 
			
			
			((JDBCUtenteService)this.utenteDao).deleteById(key.longValue());
		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void delete(UtenteBean obj) throws ServiceException {
		String methodName = "delete()";

		try{
			Utente utente = obj.getDTO();

			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(utente.getUsername());
			
			this.deleteOperazioniUtente(idUtente); 

			this.utenteDao.deleteById(idUtente); 
		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public UtenteBean findById(Long key) throws ServiceException {
		String methodName = "findById(id)";

		try{
			Utente u = ((JDBCUtenteService)this.utenteDao).get(key.longValue());
			UtenteBean ut = new UtenteBean();
			ut.setDTO(u);
			
			List<PccOperazione> listaOperazioniConsentiteUnitaOrganizzativa = this.getListaOperazioni();
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(u.getUsername()); 
			List<PccUtenteOperazione> listaOperazioniAbilitateUnitaOrganizzativa = this.getListaOperazioniAbilitateUtente(idUtente);
			ut.setProprietaPCC(listaOperazioniConsentiteUnitaOrganizzativa, listaOperazioniAbilitateUnitaOrganizzativa);

			return ut;
		}catch(NotFoundException e){
			UtenteService.log.debug("["+methodName+"] Utente non trovato: "+ e.getMessage());
		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<UtenteBean> findAll() throws ServiceException {
		String methodName = "findAll()";

		List<UtenteBean> lst = new ArrayList<UtenteBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Utente.model().COGNOME);
			expr.addOrder(Utente.model().NOME);

			IPaginatedExpression pagExpr = this.utenteSearchDao.toPaginatedExpression(expr);

			List<Utente> findAll = this.utenteSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (Utente utente : findAll) {
					UtenteBean toRet = new UtenteBean();
					toRet.setDTO(utente);
					lst.add(toRet);
				}
			}

		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	private IExpression getExpressionFromSearch(UtenteSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.utenteSearchDao.newExpression();
			if(search != null){
				if(search.getDenominazione().getValue() != null && 
						!StringUtils.isEmpty(search.getDenominazione().getValue()) 
						&& !CostantiForm.NON_SELEZIONATO.equals(search.getDenominazione().getValue())){
					String valore = search.getDenominazione().getValue();

					String[] tokens = valore.split(" ");

//					if(tokens != null && tokens.length > 0){
					if(tokens != null){
						if(tokens.length > 1){

							// Primo Cognome
							expr.ilike(Utente.model().COGNOME,	tokens[0].toLowerCase(), LikeMode.START);
							expr.and();
							// Ultimo Nome
							expr.ilike(Utente.model().NOME,	tokens[(tokens.length -1)].toLowerCase(), LikeMode.END);
	
							// Or di tutti le parole intermedie
							if(tokens.length > 2){
								IExpression orExpr = this.utenteSearchDao.newExpression();
								for (int i = 1; i < tokens.length-1; i++) {
									orExpr.ilike(Utente.model().NOME,	tokens[i].toLowerCase(), LikeMode.ANYWHERE);
									orExpr.ilike(Utente.model().COGNOME,	tokens[i].toLowerCase(), LikeMode.ANYWHERE);
									orExpr.or();
								}
								expr.and(orExpr);
							}
						} else if(tokens.length > 0){
							// Cerchiamo in or su cognome e nome
							expr.ilike(Utente.model().COGNOME,	tokens[0].toLowerCase(), LikeMode.ANYWHERE).or().ilike(Utente.model().NOME, tokens[0].toLowerCase(), LikeMode.ANYWHERE);
						}
					}
				}

				if(search.getDipartimento().getValue()!= null){
					SelectItem value = search.getDipartimento().getValue();

					if(value != null && !value.getValue().equals(CostantiForm.NON_SELEZIONATO)){
						expr.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE, value.getValue());
					}
				}
			}
			// Visualizzo solo i gli utenti dell'ente che e' configurato
//			expr.equals(Utente.model().ENTE.NOME, Utils.getIdEnte().getNome());
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}


	@Override
	public List<String> getDenominazioneAutoComplete(String val)
			throws ServiceException {
		String methodName = "getDenominazioneAutoComplete("+val+")";
		List<String> listaDipartimenti = new ArrayList<String>();


		try {
//			String nomeEnte = Utils.getLoggedUtente().getEnte().getNome();

			IPaginatedExpression pagExpr = this.utenteSearchDao.newPaginatedExpression();

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Utente.model().COGNOME).addOrder(Utente.model().NOME);
			IExpression orExpr = this.utenteSearchDao.newExpression();

			String[] tokens = val.split(" ");

			if(tokens != null && tokens.length > 0){
				for (int i = 0; i < tokens.length; i++) {
					orExpr.ilike(Utente.model().NOME, tokens[i].toLowerCase(), LikeMode.ANYWHERE).or().ilike(Utente.model().COGNOME, tokens[i].toLowerCase(), LikeMode.ANYWHERE);
					orExpr.or();
				}
			}

			//			if(!val.contains(SEPARATORE)){
			//				orExpr.ilike(Utente.model().NOME,	val.toLowerCase(), LikeMode.ANYWHERE);
			//				orExpr.ilike(Utente.model().COGNOME,	val.toLowerCase(), LikeMode.ANYWHERE);
			//				orExpr.or();
			//			}else {
			//				String[] tokens = val.split(SEPARATORE);
			//				orExpr.ilike(Utente.model().NOME,	tokens[0].toLowerCase(), LikeMode.ANYWHERE);
			//				orExpr.ilike(Utente.model().COGNOME,	tokens[1].toLowerCase(), LikeMode.ANYWHERE);
			//				orExpr.or();
			//			}
			pagExpr.and(orExpr);
//			pagExpr.and().equals(Utente.model().ENTE.NOME, nomeEnte);

			List<Map<String,Object>> select = this.utenteSearchDao.select(pagExpr, true, Utente.model().NOME,Utente.model().COGNOME);

			if(select != null && select.size() > 0){
				for (Map<String, Object> map : select) {
					String nome = (String) map.get(Utente.model().NOME.getFieldName());
					String cognome = (String) map.get(Utente.model().COGNOME.getFieldName());
					StringBuilder sb = new StringBuilder();
					sb.append(cognome).append(" ").append(nome);

					listaDipartimenti.add(sb.toString());
				}
			}

		} catch (NotFoundException e) {
			UtenteService.log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage());
		} catch (NotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

		return listaDipartimenti;
	}

	@Override
	public UtenteBean findUtenteByUsername(String username)
			throws ServiceException {
		String methodName = "findUtenteByUsername("+username+")";
		IExpression expr = null;

		try{
			expr = this.utenteSearchDao.newExpression();
			expr.equals(Utente.model().USERNAME, username);

			Utente u = this.utenteSearchDao.find(expr);
			UtenteBean utente = new UtenteBean();
			utente.setDTO(u);
			
			List<PccOperazione> listaOperazioniConsentiteUnitaOrganizzativa = this.getListaOperazioni();
			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(u.getUsername()); 
			List<PccUtenteOperazione> listaOperazioniAbilitateUnitaOrganizzativa = this.getListaOperazioniAbilitateUtente(idUtente);
			utente.setProprietaPCC(listaOperazioniConsentiteUnitaOrganizzativa, listaOperazioniAbilitateUnitaOrganizzativa);

			return utente;

		}catch (NotFoundException e) {
			UtenteService.log.debug("Metodo ["+methodName+"]: Nessun utente con username ["+username+"]trovato."+ e.getMessage());
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public boolean exists(UtenteBean obj) throws ServiceException {
		String methodName = "exists()";
		try{
			IdUtente idObj = new IdUtente();
			idObj.setUsername(obj.getDTO().getUsername());
			return this.utenteSearchDao.exists(idObj );

		}catch(ServiceException e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;
	}

	@Override
	public List<UtenteBean> findAll(UtenteSearchForm form)
			throws ServiceException {
		return null;
	}
	
	public List<PccOperazione> getListaOperazioni() throws ServiceException {
		String methodName = "getListaOperazioni()";
		
		List<PccOperazione> list =  new ArrayList<PccOperazione>();

		try {
			IExpression expr = this.pccOperazioniSearchDao.newExpression();

			expr.sortOrder(SortOrder.ASC).addOrder(PccOperazione.model().NOME);
			IPaginatedExpression pagExpr = this.pccOperazioniSearchDao.toPaginatedExpression(expr);

			 list = this.pccOperazioniSearchDao.findAll(pagExpr);

		}catch (ExpressionException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}


		return list;
	}
	
	public List<PccUtenteOperazione> getListaOperazioniAbilitateUtente(IdUtente idUtente) throws ServiceException {
		String methodName = "getListaOperazioniAbilitateUtente(idUtente)";
		List<PccUtenteOperazione> list =  new ArrayList<PccUtenteOperazione>();

		try {
			IExpression expr = this.pccUtenteOperazioniSearchDao.newExpression();

			expr.equals(PccUtenteOperazione.model().ID_UTENTE.USERNAME, idUtente.getUsername());

			IPaginatedExpression pagExpr = this.pccUtenteOperazioniSearchDao.toPaginatedExpression(expr);

			 list = this.pccUtenteOperazioniSearchDao.findAll(pagExpr);

		}catch (ExpressionException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}


		return list;
	}
	
	@Override
	public void deleteOperazioniUtente(IdUtente idUtente)throws ServiceException {
		String methodName = "deleteOperazioniUtente(idUtente)";
		try {
			IExpression expr = this.pccUtenteOperazioniDao.newExpression();

			expr.equals(PccUtenteOperazione.model().ID_UTENTE.USERNAME, idUtente.getUsername());

			this.pccUtenteOperazioniDao.deleteAll(expr);
		}catch (ExpressionException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
	}
	
	@Override
	public void salvaOperazioniUtente(List<PccUtenteOperazione> listaOperazioni) throws ServiceException{
		String methodName = "salvaOperazioniUtente(listaOperazioni)";
		try {
			
			for (PccUtenteOperazione operazione : listaOperazioni) {
				this.pccUtenteOperazioniDao.create(operazione);
			}
		} catch (NotImplementedException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} 
	}
	
	
	@Override
	public void salvaUtente(UtenteBean bean, IdUtente idUtente, List<PccUtenteOperazione> listaOperazioni)
			throws ServiceException {
		String methodName = "salvaUtente(bean,idUtente,listaOperazioniScelte)";

		try {
			this.store(bean);

			this.deleteOperazioniUtente(idUtente);

			this.salvaOperazioniUtente(listaOperazioni);
		} catch (ServiceException e) {
			UtenteService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}

	}
	
 
}
