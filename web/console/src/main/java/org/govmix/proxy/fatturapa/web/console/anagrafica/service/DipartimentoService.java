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
package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.DipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.IdEnte;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccDipartimentoOperazioneService;
import org.govmix.proxy.fatturapa.orm.dao.IPccDipartimentoOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccOperazioneServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCDipartimentoService;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.DipartimentoFieldConverter;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.openspcoop2.generic_project.beans.CustomField;
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
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.service.BaseService;

public class DipartimentoService extends BaseService<DipartimentoSearchForm> implements IDipartimentoService{


	private IDipartimentoServiceSearch dipartimentoSearchDao = null;
	private org.govmix.proxy.fatturapa.orm.dao.IDipartimentoService dipartimentoDao = null;
	private IFatturaElettronicaServiceSearch fatturaSearchDao = null;
	private IUtenteServiceSearch utenteSearchDao = null;
	private org.govmix.proxy.fatturapa.orm.dao.IDipartimentoPropertyServiceSearch dipartimentoPropertySearchDao = null;
	private IPccOperazioneServiceSearch pccOperazioniSearchDao = null;
	private IPccDipartimentoOperazioneServiceSearch pccDipartimentoOperazioniSearchDao  =null;
	private IPccDipartimentoOperazioneService pccDipartimentoOperazioniDao  =null;

	private static Logger log = LoggerManager.getDaoLogger();

	public DipartimentoService(){
		try{
			this.dipartimentoDao = DAOFactory.getInstance().getServiceManager().getDipartimentoService();
			this.dipartimentoSearchDao = DAOFactory.getInstance().getServiceManager().getDipartimentoServiceSearch();
			this.fatturaSearchDao = DAOFactory.getInstance().getServiceManager().getFatturaElettronicaServiceSearch();
			this.utenteSearchDao = DAOFactory.getInstance().getServiceManager().getUtenteServiceSearch();
			this.dipartimentoPropertySearchDao = DAOFactory.getInstance().getServiceManager().getDipartimentoPropertyServiceSearch();
			this.pccOperazioniSearchDao = DAOFactory.getInstance().getServiceManager().getPccOperazioneServiceSearch();
			this.pccDipartimentoOperazioniDao = DAOFactory.getInstance().getServiceManager().getPccDipartimentoOperazioneService();
			this.pccDipartimentoOperazioniSearchDao = DAOFactory.getInstance().getServiceManager().getPccDipartimentoOperazioneServiceSearch();

		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<DipartimentoBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(this.form);



			//			//order by
			//			expr.sortOrder(SortOrder.ASC);
			//			expr.addOrder(Dipartimento.model().DESCRIZIONE);
			//
			//			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.toPaginatedExpression(expr);
			//
			//			pagExpr.offset(start);
			//			pagExpr.limit(limit);


			lst = this._findAll(expr,start,limit);
			//
			//			List<Dipartimento> findAll = this.dipartimentoSearchDao.findAll(pagExpr);
			//
			//			if(findAll != null && findAll.size() > 0){
			//				for (Dipartimento dipartimento : findAll) {
			//					DipartimentoBean toRet = new DipartimentoBean();
			//					toRet.setDTO(dipartimento);
			//
			//					List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(dipartimento.getEnte());
			//					toRet.setListaNomiProperties(listaPropertiesEnte);
			//
			//					lst.add(toRet);
			//				}
			//			}

		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public int totalCount() throws ServiceException {
		String methodName = "totalCount";
		int cnt = 0;
		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.dipartimentoSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(DipartimentoBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Dipartimento dipartimento = obj.getDTO();

			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(dipartimento.getCodice());

			if(this.dipartimentoDao.exists(idDipartimento))
				this.dipartimentoDao.update(idDipartimento, dipartimento);
			else
				this.dipartimentoDao.create(dipartimento);
		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		try{

			DipartimentoBean bean = this.findById(key);
			Dipartimento dipartimento = bean.getDTO();

			IExpression fatExpr = this.fatturaSearchDao.newExpression();

			fatExpr.equals(FatturaElettronica.model().CODICE_DESTINATARIO, dipartimento.getCodice());

			NonNegativeNumber nnn = this.fatturaSearchDao.count(fatExpr);

			if(nnn.longValue() > 0)
				throw new ServiceException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.fatturePresenti"));

			IExpression utExpr = this.utenteSearchDao.newExpression();

			utExpr.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE, dipartimento.getCodice());

			nnn = this.utenteSearchDao.count(utExpr);

			if(nnn.longValue() > 0)
				throw new ServiceException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.utentiPresenti"));

			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(dipartimento.getCodice());
			this.deleteOperazioniUnitaOrganizzativa(idDipartimento);

			((JDBCDipartimentoService)this.dipartimentoDao).deleteById(key.longValue());
		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(DipartimentoBean obj) throws ServiceException {
		String methodName = "delete()";

		try{
			Dipartimento dipartimento = obj.getDTO();

			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(dipartimento.getCodice());

			IExpression fatExpr = this.fatturaSearchDao.newExpression();

			fatExpr.equals(FatturaElettronica.model().CODICE_DESTINATARIO, dipartimento.getCodice());

			NonNegativeNumber nnn = this.fatturaSearchDao.count(fatExpr);

			if(nnn.longValue() > 0)
				throw new ServiceException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.fatturePresenti"));

			IExpression utExpr = this.utenteSearchDao.newExpression();

			utExpr.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE, dipartimento.getCodice());

			nnn = this.utenteSearchDao.count(utExpr);

			if(nnn.longValue() > 0)
				throw new ServiceException(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.utentiPresenti"));

			this.deleteOperazioniUnitaOrganizzativa(idDipartimento); 

			this.dipartimentoDao.deleteById(idDipartimento); 
		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
	public DipartimentoBean findById(Long key) throws ServiceException {
		String methodName = "findById(id)";

		try{
			Dipartimento d = ((JDBCDipartimentoServiceSearch)this.dipartimentoSearchDao).get(key.longValue());
			DipartimentoBean dip = new DipartimentoBean();
			dip.setDTO(d);
			List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(d.getEnte());
			dip.setListaNomiProperties(listaPropertiesEnte);

			List<PccOperazione> listaOperazioniConsentiteUnitaOrganizzativa = this.getListaOperazioniConsentiteUnitaOrganizzativa();
			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(d.getCodice()); 
			List<PccDipartimentoOperazione> listaOperazioniAbilitateUnitaOrganizzativa = this.getListaOperazioniAbilitateUnitaOrganizzativa(idDipartimento);
			dip.setProprietaPCC(listaOperazioniConsentiteUnitaOrganizzativa, listaOperazioniAbilitateUnitaOrganizzativa);

			return dip;
		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(NotFoundException e){
			DipartimentoService.log.debug("["+methodName+"] Dipartimento non trovato: "+ e.getMessage(), e);
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DipartimentoBean> findAll() throws ServiceException {
		String methodName = "findAll()";

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			lst = this._findAll( expr, null, null);

		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public List<DipartimentoBean> findAll(DipartimentoSearchForm search) throws ServiceException {
		String methodName = "findAll()";

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try{

			IExpression expr = this.getExpressionFromSearch(search);

			lst = this._findAll( expr, null , null);

		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	public List<DipartimentoBean> _findAll(IExpression expr, Integer start, Integer limit) throws Exception {

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();
		//order by
		expr.addOrder(Dipartimento.model().DESCRIZIONE,SortOrder.ASC);
		expr.addOrder(Dipartimento.model().CODICE, SortOrder.ASC);

		IPaginatedExpression pagExpr = this.dipartimentoSearchDao.toPaginatedExpression(expr);

		if(start != null && limit != null){
			pagExpr.offset(start);
			pagExpr.limit(limit);
		}

		List<Dipartimento> findAll = this.dipartimentoSearchDao.findAll(pagExpr,false);

		//		List<PccOperazione> listaOperazioniConsentiteUnitaOrganizzativa = this.getListaOperazioniConsentiteUnitaOrganizzativa();

		if(findAll != null && findAll.size() > 0){
			for (Dipartimento dipartimento : findAll) {
				//				IdDipartimento idDipartimento = new IdDipartimento();
				//				idDipartimento.setCodice(dipartimento.getCodice()); 
				//				List<PccDipartimentoOperazione> listaOperazioniAbilitateUnitaOrganizzativa = this.getListaOperazioniAbilitateUnitaOrganizzativa(idDipartimento);

				DipartimentoBean toRet = new DipartimentoBean();
				toRet.setDTO(dipartimento);
				//				toRet.setProprietaPCC(listaOperazioniConsentiteUnitaOrganizzativa, listaOperazioniAbilitateUnitaOrganizzativa);

				//				List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(dipartimento.getEnte());
				//				toRet.setListaNomiProperties(listaPropertiesEnte);
				lst.add(toRet);
			}
		}

		return lst;
	}

	@Override
	public List<IdDipartimento> findAllIdDipartimento() throws ServiceException {
		String methodName = "findAllIdDipartimento()";

		List<IdDipartimento> lst = new ArrayList<IdDipartimento>();

		try{

			IExpression expr = this.getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Dipartimento.model().DESCRIZIONE);

			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.toPaginatedExpression(expr);

			lst = this.dipartimentoSearchDao.findAllIds(pagExpr);

		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public DipartimentoBean findDipartimentoByCodice(String codiceDipartimento)
			throws ServiceException {
		String methodName = "findDipartimentoByCodice("+codiceDipartimento+")";
		IExpression expr = null;

		try{
			expr = this.dipartimentoSearchDao.newExpression();
			expr.equals(Dipartimento.model().CODICE, codiceDipartimento);

			Dipartimento d = this.dipartimentoSearchDao.find(expr);
			DipartimentoBean dip = new DipartimentoBean();
			dip.setDTO(d);

			List<PccOperazione> listaOperazioniConsentiteUnitaOrganizzativa = this.getListaOperazioniConsentiteUnitaOrganizzativa();
			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(d.getCodice()); 
			List<PccDipartimentoOperazione> listaOperazioniAbilitateUnitaOrganizzativa = this.getListaOperazioniAbilitateUnitaOrganizzativa(idDipartimento);
			dip.setProprietaPCC(listaOperazioniConsentiteUnitaOrganizzativa, listaOperazioniAbilitateUnitaOrganizzativa);

			List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(d.getEnte());
			dip.setListaNomiProperties(listaPropertiesEnte);

			return dip;

		}catch(ServiceException e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch (NotFoundException e) {
			DipartimentoService.log.debug("Metodo ["+methodName+"]: Nessun dipartimento con codice ["+codiceDipartimento+"]trovato."+ e.getMessage());
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public List<Dipartimento> getDescrizioneAutoComplete(String val,String nomeEnte)
			throws ServiceException {
		String methodName = "getDescrizioneAutoComplete("+val+")";
		List<Dipartimento> listaDipartimenti = new ArrayList<Dipartimento>();


		try {
			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.newPaginatedExpression();
			if(StringUtils.isNotEmpty(nomeEnte))
				pagExpr.equals(Dipartimento.model().ENTE.NOME, nomeEnte);
			
			IExpression orExpr = this.dipartimentoSearchDao.newExpression();
			orExpr.ilike(Dipartimento.model().DESCRIZIONE,	val.toLowerCase(), LikeMode.ANYWHERE);

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);
			pagExpr.and(orExpr);
			
			//se sei un DEPT_ADMIN vedi solo i tuoi dipartimenti
			//[Pintori 2016/06/10] Vincolo rilasciato il DeptAdmin gestisce l'anagrafica di tutti i dipartimenti, 
			// quindi e' abilitato a vedere tutti i dipartimenti nelle form di autocompletamento.
//			if(Utils.getLoginBean().isDeptAdmin()){
//				if(Utils.getLoggedUtente().getUtenteDipartimentoList() != null && 
//						Utils.getLoggedUtente().getUtenteDipartimentoList().size()>0){
//					List<Long> listaId = new ArrayList<Long>();
//					
//					for (UtenteDipartimento idUD : Utils.getLoggedUtente().getUtenteDipartimentoList()) {
//						listaId.add(idUD.getIdDipartimento().getId());
//					}
//					
//					DipartimentoFieldConverter fieldConverter = new DipartimentoFieldConverter(DAOFactory.getInstance().getServiceManagerProperties().getDatabaseType());
//					pagExpr.in(new CustomField("id", Long.class, "id", fieldConverter.toTable(Dipartimento.model())), listaId);
//				}
//			}

			listaDipartimenti = this.dipartimentoSearchDao.findAll(pagExpr,false);
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (Exception e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

		return listaDipartimenti;
	}

	@Override
	public List<Dipartimento> getCodiceAutoComplete(String val,String nomeEnte)
			throws ServiceException {
		String methodName = "getDescrizioneAutoComplete("+val+")";
		List<Dipartimento> listaDipartimenti = new ArrayList<Dipartimento>();


		try {
			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.newPaginatedExpression();
			if(StringUtils.isNotEmpty(nomeEnte))
				pagExpr.equals(Dipartimento.model().ENTE.NOME, nomeEnte);

			IExpression orExpr = this.dipartimentoSearchDao.newExpression();
			orExpr.ilike(Dipartimento.model().CODICE,	val.toLowerCase(), LikeMode.ANYWHERE);

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);
			pagExpr.and(orExpr);
			
			//se sei un DEPT_ADMIN vedi solo i tuoi dipartimenti
			//[Pintori 2016/06/10] Vincolo rilasciato il DeptAdmin gestisce l'anagrafica di tutti i dipartimenti, 
			// quindi e' abilitato a vedere tutti i dipartimenti nelle form di autocompletamento.
//			if(Utils.getLoginBean().isDeptAdmin()){
//				if(Utils.getLoggedUtente().getUtenteDipartimentoList() != null && 
//						Utils.getLoggedUtente().getUtenteDipartimentoList().size()>0){
//					List<Long> listaId = new ArrayList<Long>();
//					
//					for (UtenteDipartimento idUD : Utils.getLoggedUtente().getUtenteDipartimentoList()) {
//						listaId.add(idUD.getIdDipartimento().getId());
//					}
//					
//					DipartimentoFieldConverter fieldConverter = new DipartimentoFieldConverter(DAOFactory.getInstance().getServiceManagerProperties().getDatabaseType());
//					pagExpr.in(new CustomField("id", Long.class, "id", fieldConverter.toTable(Dipartimento.model())), listaId);
//				}
//			}

			listaDipartimenti = this.dipartimentoSearchDao.findAll(pagExpr,false);
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (Exception e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

		return listaDipartimenti;
	}

	@Override
	public List<Dipartimento> getCodiceDescrizioneAutoComplete(String val,String nomeEnte)
			throws ServiceException {
		String methodName = "getDescrizioneAutoComplete("+val+")";
		List<Dipartimento> listaDipartimenti = new ArrayList<Dipartimento>();


		try {
			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.newPaginatedExpression();
			if(StringUtils.isNotEmpty(nomeEnte))
				pagExpr.equals(Dipartimento.model().ENTE.NOME, nomeEnte);

			IExpression orExpr = this.dipartimentoSearchDao.newExpression();
			orExpr.ilike(Dipartimento.model().DESCRIZIONE,	val.toLowerCase(), LikeMode.ANYWHERE);
			orExpr.or();
			orExpr.ilike(Dipartimento.model().CODICE,	val.toLowerCase(), LikeMode.ANYWHERE);

			pagExpr.addOrder(Dipartimento.model().DESCRIZIONE,SortOrder.ASC);
			pagExpr.addOrder(Dipartimento.model().CODICE, SortOrder.ASC);
			pagExpr.and(orExpr);

			listaDipartimenti = this.dipartimentoSearchDao.findAll(pagExpr,false);

		}catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch(ServiceException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		} catch (Exception e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

		return listaDipartimenti;
	}

	private IExpression getExpressionFromSearch(DipartimentoSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.dipartimentoSearchDao.newExpression();
			IExpression descrOrExpr = null; // this.dipartimentoSearchDao.newExpression();
			IExpression codiceOrExpr = null; //this.dipartimentoSearchDao.newExpression();
			IExpression codDescrExpr = null; //this.dipartimentoSearchDao.newExpression();



			if(search != null){

				if(search.getListaIdDipartimentiUtente() != null && search.getListaIdDipartimentiUtente().size()>0){
					DipartimentoFieldConverter fieldConverter = new DipartimentoFieldConverter(DAOFactory.getInstance().getServiceManagerProperties().getDatabaseType());
					expr.in(new CustomField("id", Long.class, "id", fieldConverter.toTable(Dipartimento.model())), search.getListaIdDipartimentiUtente());
				}

				SelectItem value = search.getEnte().getValue();
				if(value != null && !StringUtils.isEmpty(value.getValue()) && !CostantiForm.NON_SELEZIONATO.equals(value.getValue())){
					expr.equals(Dipartimento.model().ENTE.NOME, value.getValue());
				}

				if(search.getDescrizione().getValue() != null && !StringUtils.isEmpty(search.getDescrizione().getValue()) && !CostantiForm.NON_SELEZIONATO.equals(search.getDescrizione().getValue())){
					descrOrExpr = this.dipartimentoSearchDao.newExpression();
					descrOrExpr.ilike(Dipartimento.model().DESCRIZIONE,	search.getDescrizione().getValue(), LikeMode.ANYWHERE);
					codDescrExpr = this.dipartimentoSearchDao.newExpression();
				}

				if(search.getCodice().getValue() != null && !StringUtils.isEmpty(search.getCodice().getValue()) 
						&& !CostantiForm.NON_SELEZIONATO.equals(search.getCodice().getValue())){

					codiceOrExpr = this.dipartimentoSearchDao.newExpression();
					codiceOrExpr.ilike(Dipartimento.model().CODICE,	search.getCodice().getValue(), LikeMode.ANYWHERE);
					codDescrExpr = this.dipartimentoSearchDao.newExpression();
				}

				if(codDescrExpr != null){
					// Ho impostato la descrizione 
					if(descrOrExpr != null){
						// se ho impostato il codice vado in  or
						if(codiceOrExpr != null){
							codDescrExpr.or(descrOrExpr, codiceOrExpr);
						}else{
							codDescrExpr.or(descrOrExpr);
						}

					}else {
						// non ho impostato la descrizione 
						if(codiceOrExpr != null)
							codDescrExpr.or(codiceOrExpr);
					}

					expr.and(codDescrExpr);
				}
			}

		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	@Override
	public List<DipartimentoBean> getListaDipartimentiUtente(Utente utente, boolean ignoreRole) throws ServiceException {
		if(utente ==null){
			DipartimentoService.log.error("parametro Utente non presente");
			return null;
		}

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try {
			IPaginatedExpression pagExpr =  this.dipartimentoSearchDao.newPaginatedExpression();

			if(utente.getRole().equals(UserRole.ADMIN) && !ignoreRole){
				pagExpr.addOrder(Dipartimento.model().DESCRIZIONE, SortOrder.ASC);
				pagExpr.addOrder(Dipartimento.model().CODICE, SortOrder.ASC);
				List<Dipartimento> findAll = this.dipartimentoSearchDao.findAll(pagExpr);
				if(findAll != null && findAll.size()  >0){
					for (Dipartimento dipartimento : findAll) {
						if(dipartimento != null){
							DipartimentoBean bean = new DipartimentoBean();
							bean.setDTO(dipartimento);

							List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(dipartimento.getEnte());
							bean.setListaNomiProperties(listaPropertiesEnte);

							lst.add(bean);
						}
					}
				}
			} else {
				List<UtenteDipartimento> utenteDipartimentoList = utente.getUtenteDipartimentoList();
				List<Long> idDipartimentiUtenti = new ArrayList<Long>();
				for (UtenteDipartimento utenteDipartimento : utenteDipartimentoList) {
					IdDipartimento idDipartimento = utenteDipartimento.getIdDipartimento();
					idDipartimentiUtenti.add(idDipartimento.getId());
				}
				
				if(idDipartimentiUtenti.size() > 0){
					DipartimentoFieldConverter fieldConverter = new DipartimentoFieldConverter(DAOFactory.getInstance().getServiceManagerProperties().getDatabaseType());
					pagExpr.in(new CustomField("id", Long.class, "id", fieldConverter.toTable(Dipartimento.model())), idDipartimentiUtenti);
					
					pagExpr.addOrder(Dipartimento.model().DESCRIZIONE, SortOrder.ASC);
					pagExpr.addOrder(Dipartimento.model().CODICE, SortOrder.ASC);
					
					List<Dipartimento> findAll = this.dipartimentoSearchDao.findAll(pagExpr);
					if(findAll != null && findAll.size()  >0){
						for (Dipartimento dipartimento : findAll) {
							if(dipartimento != null){
								DipartimentoBean bean = new DipartimentoBean();
								bean.setDTO(dipartimento);

								List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(dipartimento.getEnte());
								bean.setListaNomiProperties(listaPropertiesEnte);

								lst.add(bean);
							}
						}
					}
				}
			}
		} catch (ServiceException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (Exception e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public DipartimentoProperty findPropertyById(IdDipartimentoProperty idProperty) throws ServiceException {
		String methodName = "findPropertyById(id)";

		try{
			DipartimentoProperty prop =  this.dipartimentoPropertySearchDao.get(idProperty);
			return prop;
		}catch (NotFoundException e) {
			DipartimentoService.log.debug("Metodo ["+methodName+"]: Nessuna property trovata."+ e.getMessage());
		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;

	};

	@Override
	public List<DipartimentoProperty> getListaPropertiesEnte(IdEnte idEnte)
			throws ServiceException {
		List<DipartimentoProperty> lst = new ArrayList<DipartimentoProperty>();

		try {
			IPaginatedExpression pagExpr =  this.dipartimentoPropertySearchDao.newPaginatedExpression();
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(DipartimentoProperty.model().NOME);
			if(idEnte != null && idEnte.getNome() != null)
				pagExpr.equals(DipartimentoProperty.model().ID_ENTE.NOME, idEnte.getNome());

			lst = this.dipartimentoPropertySearchDao.findAll(pagExpr);

		} catch (ServiceException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
		} catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public boolean exists(DipartimentoBean obj) throws ServiceException {
		String methodName = "exists()";
		try{
			IdDipartimento idObj = new IdDipartimento();
			idObj.setCodice(obj.getDTO().getCodice());
			return this.dipartimentoSearchDao.exists(idObj );

		}catch(Exception e){
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;
	}

	@Override
	public List<PccDipartimentoOperazione> getListaOperazioniAbilitateUnitaOrganizzativa(IdDipartimento idDipartimento)
			throws ServiceException {
		String methodName = "getListaOperazioniAbilitateUnitaOrganizzativa(idDipartimento)";
		List<PccDipartimentoOperazione> list =  new ArrayList<PccDipartimentoOperazione>();

		try {
			IExpression expr = this.pccDipartimentoOperazioniSearchDao.newExpression();

			expr.equals(PccDipartimentoOperazione.model().ID_DIPARTIMENTO.CODICE, idDipartimento.getCodice());

			IPaginatedExpression pagExpr = this.pccDipartimentoOperazioniSearchDao.toPaginatedExpression(expr);

			list = this.pccDipartimentoOperazioniSearchDao.findAll(pagExpr);

		}catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}


		return list;
	}

	@Override
	public List<PccOperazione> getListaOperazioniConsentiteUnitaOrganizzativa() throws ServiceException {
		String methodName = "getListaOperazioniConsentiteUnitaOrganizzativa()";

		List<PccOperazione> list =  new ArrayList<PccOperazione>();

		try {
			IExpression expr = this.pccOperazioniSearchDao.newExpression();

			expr.sortOrder(SortOrder.ASC).addOrder(PccOperazione.model().NOME);

			// add filtro sul consentito per dipartimento.

			IPaginatedExpression pagExpr = this.pccOperazioniSearchDao.toPaginatedExpression(expr);

			list = this.pccOperazioniSearchDao.findAll(pagExpr);

		}catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}


		return list;
	}

	@Override
	public void deleteOperazioniUnitaOrganizzativa(IdDipartimento idDipartimento)
			throws ServiceException {
		String methodName = "deleteOperazioniUnitaOrganizzativa(idDipartimento)";
		try {
			IExpression expr = this.pccDipartimentoOperazioniDao.newExpression();

			expr.equals(PccDipartimentoOperazione.model().ID_DIPARTIMENTO.CODICE, idDipartimento.getCodice());

			this.pccDipartimentoOperazioniDao.deleteAll(expr);
		}catch (ExpressionException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
	}

	@Override
	public void salvaOperazioniUnitaOrganizzativa(List<PccDipartimentoOperazione> listaOperazioni) throws ServiceException{
		String methodName = "salvaOperazioniUnitaOrganizzativa(listaOperazioni)";
		try {

			for (PccDipartimentoOperazione operazione : listaOperazioni) {
				this.pccDipartimentoOperazioniDao.create(operazione);
			}
		} catch (NotImplementedException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		} 
	}

	@Override
	public void salvaDipartimento(DipartimentoBean bean, IdDipartimento idDipartimento,	List<PccDipartimentoOperazione> listaOperazioni) throws ServiceException {
		String methodName = "salvaDipartimento(bean,idDipartimento,listaOperazioniScelte)";

		try {
			this.store(bean);

			this.deleteOperazioniUnitaOrganizzativa(idDipartimento);

			this.salvaOperazioniUnitaOrganizzativa(listaOperazioni);
		} catch (ServiceException e) {
			DipartimentoService.log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}

	}
}
