package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.UtenteDipartimento;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.govmix.proxy.fatturapa.dao.IDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCDipartimentoService;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCDipartimentoServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.LikeMode;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.service.BaseService;

public class DipartimentoService extends BaseService<DipartimentoSearchForm> implements IDipartimentoService{


	private IDipartimentoServiceSearch dipartimentoSearchDao = null;
	private org.govmix.proxy.fatturapa.dao.IDipartimentoService dipartimentoDao = null;
	private IFatturaElettronicaServiceSearch fatturaSearchDao = null;
	private IUtenteServiceSearch utenteSearchDao = null;
	private org.govmix.proxy.fatturapa.dao.IDipartimentoPropertyServiceSearch dipartimentoPropertySearchDao = null;

	private static Logger log = LoggerManager.getConsoleDaoLogger();

	public DipartimentoService(){
		try{
			this.dipartimentoDao = DAOFactory.getInstance(log).getServiceManager().getDipartimentoService();
			this.dipartimentoSearchDao = DAOFactory.getInstance(log).getServiceManager().getDipartimentoServiceSearch();
			this.fatturaSearchDao = DAOFactory.getInstance(log).getServiceManager().getFatturaElettronicaServiceSearch();
			this.utenteSearchDao = DAOFactory.getInstance(log).getServiceManager().getUtenteServiceSearch();
			this.dipartimentoPropertySearchDao = DAOFactory.getInstance(log).getServiceManager().getDipartimentoPropertyServiceSearch();

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<DipartimentoBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try{

			IExpression expr = getExpressionFromSearch(this.form);



			//			//order by
//			expr.sortOrder(SortOrder.ASC);
//			expr.addOrder(Dipartimento.model().DESCRIZIONE);
//
//			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.toPaginatedExpression(expr);
//
//			pagExpr.offset(start);
//			pagExpr.limit(limit);


			lst = _findAll(expr,start,limit);
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

			IExpression expr = getExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.dipartimentoSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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
				throw new ServiceException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.fatturePresenti"));

			IExpression utExpr = this.utenteSearchDao.newExpression();

			utExpr.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE, dipartimento.getCodice());

			nnn = this.utenteSearchDao.count(utExpr);

			if(nnn.longValue() > 0)
				throw new ServiceException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.utentiPresenti"));

			((JDBCDipartimentoService)this.dipartimentoDao).deleteById(key.longValue());
		}catch (ServiceException e){
			throw e;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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
				throw new ServiceException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.fatturePresenti"));

			IExpression utExpr = this.utenteSearchDao.newExpression();

			utExpr.equals(Utente.model().UTENTE_DIPARTIMENTO.ID_DIPARTIMENTO.CODICE, dipartimento.getCodice());

			nnn = this.utenteSearchDao.count(utExpr);

			if(nnn.longValue() > 0)
				throw new ServiceException(Utils.getInstance().getMessageFromResourceBundle("dipartimento.deleteError.utentiPresenti"));

			this.dipartimentoDao.deleteById(idDipartimento); 
		}catch (ServiceException e){
			throw e;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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

			return dip;
		}catch(NotFoundException e){
			log.debug("["+methodName+"] Dipartimento non trovato: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DipartimentoBean> findAll() throws ServiceException {
		String methodName = "findAll()";

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try{

			IExpression expr = getExpressionFromSearch(this.form);

			lst = _findAll( expr, null, null);

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	@Override
	public List<DipartimentoBean> findAll(DipartimentoSearchForm search) throws ServiceException {
		String methodName = "findAll()";

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try{

			IExpression expr = getExpressionFromSearch(search);

			lst = _findAll( expr, null , null);

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}

	public List<DipartimentoBean> _findAll(IExpression expr, Integer start, Integer limit) throws Exception {

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();
		//order by
		expr.sortOrder(SortOrder.ASC);
		expr.addOrder(Dipartimento.model().DESCRIZIONE);

		IPaginatedExpression pagExpr = this.dipartimentoSearchDao.toPaginatedExpression(expr);

		if(start != null && limit != null){
			pagExpr.offset(start);
			pagExpr.limit(limit);
		}

		List<Dipartimento> findAll = this.dipartimentoSearchDao.findAll(pagExpr,false);

		if(findAll != null && findAll.size() > 0){
			for (Dipartimento dipartimento : findAll) {
				DipartimentoBean toRet = new DipartimentoBean();
				toRet.setDTO(dipartimento);

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

			IExpression expr = getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Dipartimento.model().DESCRIZIONE);

			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.toPaginatedExpression(expr);

			lst = this.dipartimentoSearchDao.findAllIds(pagExpr);

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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

			List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(d.getEnte());
			dip.setListaNomiProperties(listaPropertiesEnte);

			return dip;

		}catch (NotFoundException e) {
			log.debug("Metodo ["+methodName+"]: Nessun dipartimento con codice ["+codiceDipartimento+"]trovato."+ e.getMessage());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public List<Dipartimento> getDescrizioneAutoComplete(String val)
			throws ServiceException {
		String methodName = "getDescrizioneAutoComplete("+val+")";
		List<Dipartimento> listaDipartimenti = new ArrayList<Dipartimento>();


		try {
			String nomeEnte = Utils.getLoggedUtente().getEnte().getNome();

			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.newPaginatedExpression();
			pagExpr.equals(Dipartimento.model().ENTE.NOME, nomeEnte);

			IExpression orExpr = this.dipartimentoSearchDao.newExpression();
			orExpr.ilike(Dipartimento.model().DESCRIZIONE,	val.toLowerCase(), LikeMode.ANYWHERE);
			//			orExpr.or();
			//			orExpr.ilike(Dipartimento.model().CODICE,	val.toLowerCase(), LikeMode.ANYWHERE);

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);
			pagExpr.and(orExpr);

			listaDipartimenti = this.dipartimentoSearchDao.findAll(pagExpr,false);

			//			if(select != null && select.size() > 0){
			//				for (Object object : select) {
			//					listaDipartimenti.add((String) object);
			//				}
			//			}

		}
		//		catch (NotFoundException e) {
		//			log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		//		} 
		catch (NotImplementedException e) {
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

		return listaDipartimenti;
	}

	@Override
	public List<Dipartimento> getCodiceAutoComplete(String val)
			throws ServiceException {
		String methodName = "getDescrizioneAutoComplete("+val+")";
		List<Dipartimento> listaDipartimenti = new ArrayList<Dipartimento>();


		try {
			String nomeEnte = Utils.getLoggedUtente().getEnte().getNome();

			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.newPaginatedExpression();
			pagExpr.equals(Dipartimento.model().ENTE.NOME, nomeEnte);

			IExpression orExpr = this.dipartimentoSearchDao.newExpression();
			//			orExpr.ilike(Dipartimento.model().DESCRIZIONE,	val.toLowerCase(), LikeMode.ANYWHERE);
			//			orExpr.or();
			orExpr.ilike(Dipartimento.model().CODICE,	val.toLowerCase(), LikeMode.ANYWHERE);

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);
			pagExpr.and(orExpr);

			listaDipartimenti = this.dipartimentoSearchDao.findAll(pagExpr,false);

			//			if(select != null && select.size() > 0){
			//				for (Object object : select) {
			//					listaDipartimenti.add((String) object);
			//				}
			//			}

		}
		//		catch (NotFoundException e) {
		//			log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		//		} 
		catch (NotImplementedException e) {
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

		return listaDipartimenti;
	}

	@Override
	public List<Dipartimento> getCodiceDescrizioneAutoComplete(String val)
			throws ServiceException {
		String methodName = "getDescrizioneAutoComplete("+val+")";
		List<Dipartimento> listaDipartimenti = new ArrayList<Dipartimento>();


		try {
			String nomeEnte = Utils.getLoggedUtente().getEnte().getNome();

			IPaginatedExpression pagExpr = this.dipartimentoSearchDao.newPaginatedExpression();
			pagExpr.equals(Dipartimento.model().ENTE.NOME, nomeEnte);

			IExpression orExpr = this.dipartimentoSearchDao.newExpression();
			orExpr.ilike(Dipartimento.model().DESCRIZIONE,	val.toLowerCase(), LikeMode.ANYWHERE);
			orExpr.or();
			orExpr.ilike(Dipartimento.model().CODICE,	val.toLowerCase(), LikeMode.ANYWHERE);

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);
			pagExpr.and(orExpr);

			listaDipartimenti = this.dipartimentoSearchDao.findAll(pagExpr,false);

			//			if(select != null && select.size() > 0){
			//				for (Object object : select) {
			//					listaDipartimenti.add((String) object);
			//				}
			//			}

		}
		//		catch (NotFoundException e) {
		//			log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage(), e);
		//		} 
		catch (NotImplementedException e) {
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

		return listaDipartimenti;
	}

	private IExpression getExpressionFromSearch(DipartimentoSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.dipartimentoSearchDao.newExpression();
			IExpression descrOrExpr = null; // this.dipartimentoSearchDao.newExpression();
			IExpression codiceOrExpr = null; //this.dipartimentoSearchDao.newExpression();
			IExpression codDescrExpr = null; //this.dipartimentoSearchDao.newExpression();
			// Visualizzo solo i dipartimenti dell'ente che e' configurato
			expr.equals(Dipartimento.model().ENTE.NOME, Utils.getIdEnte().getNome());

			if(search != null){
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
			log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	@Override
	public List<DipartimentoBean> getListaDipartimentiUtente(Utente utente, Ente ente, boolean ignoreRole) throws ServiceException {
		if(utente ==null){
			log.error("parametro Utente non presente");
			return null;
		}

		List<DipartimentoBean> lst = new ArrayList<DipartimentoBean>();

		try {
			if(utente.getRole().equals(UserRole.ADMIN) && !ignoreRole){
				IPaginatedExpression pagExpr =  this.dipartimentoSearchDao.newPaginatedExpression();
				pagExpr.sortOrder(SortOrder.ASC);
				pagExpr.addOrder(Dipartimento.model().DESCRIZIONE);

				List<Dipartimento> findAll = this.dipartimentoSearchDao.findAll(pagExpr);
				if(findAll != null && findAll.size()  >0)
					for (Dipartimento dipartimento : findAll) {
						if(dipartimento != null){
							DipartimentoBean bean = new DipartimentoBean();
							bean.setDTO(dipartimento);

							List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(dipartimento.getEnte());
							bean.setListaNomiProperties(listaPropertiesEnte);

							lst.add(bean);
						}
					}
			} else {
				List<UtenteDipartimento> utenteDipartimentoList = utente.getUtenteDipartimentoList();
				for (UtenteDipartimento utenteDipartimento : utenteDipartimentoList) {
					IdDipartimento idDipartimento = utenteDipartimento.getIdDipartimento();

					Dipartimento dipartimento = this.dipartimentoSearchDao.get(idDipartimento);

					if(dipartimento != null){
						DipartimentoBean bean = new DipartimentoBean();
						bean.setDTO(dipartimento);

						List<DipartimentoProperty> listaPropertiesEnte = this.getListaPropertiesEnte(dipartimento.getEnte());
						bean.setListaNomiProperties(listaPropertiesEnte);

						lst.add(bean);
					}
				}
			}


			//			for (UtenteDipartimento utenteDipartimento : utenteDipartimentoList) {
			//				IdDipartimento idDipartimento = utenteDipartimento.getIdDipartimento();
			//
			//				Dipartimento dipartimento = this.dipartimentoSearchDao.get(idDipartimento);
			//				
			//								
			//				if(dipartimento != null){
			//					DipartimentoBean bean = new DipartimentoBean();
			//					bean.setDTO(dipartimento);
			//					lst.add(bean);
			//				}
			//			}
		} catch (ServiceException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (NotFoundException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (MultipleResultException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		} catch (ExpressionException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'utente ["+utente.getUsername()+"]:" +e.getMessage(), e);
		}

		return lst;
	}

	public DipartimentoProperty findPropertyById(IdProperty idProperty) throws ServiceException {
		String methodName = "findPropertyById(id)";

		try{
			DipartimentoProperty prop =  this.dipartimentoPropertySearchDao.get(idProperty);
			return prop;
		}catch (NotFoundException e) {
			log.debug("Metodo ["+methodName+"]: Nessuna property trovata."+ e.getMessage());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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
			pagExpr.equals(DipartimentoProperty.model().ID_ENTE.NOME, idEnte.getNome());

			lst = this.dipartimentoPropertySearchDao.findAll(pagExpr);

		} catch (ServiceException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
		} catch (NotImplementedException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
		} catch (ExpressionNotImplementedException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
		} catch (ExpressionException e) {
			log.error("Si e' verificato un errore durante il caricamento dei dipartimenti l'ente ["+idEnte.getNome()+"]:" +e.getMessage(), e);
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
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;
	}
}
