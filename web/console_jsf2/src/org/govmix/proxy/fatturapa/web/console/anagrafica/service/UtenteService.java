package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCUtenteService;
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
import org.openspcoop2.generic_project.web.input.SelectItem;
import org.openspcoop2.generic_project.web.service.BaseService;

@RequestScoped @ManagedBean(name="utenteService")
public class UtenteService extends BaseService<UtenteSearchForm> implements IUtenteService {

	public static final String SEPARATORE = "$$$$"; 

	private IUtenteServiceSearch utenteSearchDao = null;
	private org.govmix.proxy.fatturapa.dao.IUtenteService utenteDao = null;

	private static Logger log = LoggerManager.getConsoleDaoLogger();

	public UtenteService(){
		try{
			this.utenteDao = DAOFactory.getInstance(log).getServiceManager().getUtenteService();
			this.utenteSearchDao = DAOFactory.getInstance(log).getServiceManager().getUtenteServiceSearch();

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}
	
	@PostConstruct
	private void initManagedProperties(){
		this.form = (UtenteSearchForm) Utils.findBean("utenteSearchForm");
	}

	@Override
	public List<UtenteBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<UtenteBean> lst = new ArrayList<UtenteBean>();

		try{

			IExpression expr = getExpressionFromSearch(this.form);

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
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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

			NonNegativeNumber nnn = this.utenteSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(UtenteBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Utente utente = obj.getDTO();

			IdUtente idUtente = new IdUtente();
			idUtente.setUsername(utente.getUsername());

			if(this.utenteDao.exists(idUtente))
				this.utenteDao.update(idUtente, utente);
			else
				this.utenteDao.create(utente);
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		try{
			((JDBCUtenteService)this.utenteDao).deleteById(key.longValue());
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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

			this.utenteDao.deleteById(idUtente); 
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public UtenteBean findById(Long key) throws ServiceException {
		String methodName = "findById(id)";

		try{
			Utente d = ((JDBCUtenteService)this.utenteDao).get(key.longValue());
			UtenteBean ut = new UtenteBean();
			ut.setDTO(d);

			return ut;
		}catch(NotFoundException e){
			log.debug("["+methodName+"] Utente non trovato: "+ e.getMessage());
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<UtenteBean> findAll() throws ServiceException {
		String methodName = "findAll()";

		List<UtenteBean> lst = new ArrayList<UtenteBean>();

		try{

			IExpression expr = getExpressionFromSearch(this.form);

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
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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

					if(tokens != null && tokens.length > 0){

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
			expr.equals(Utente.model().ENTE.NOME, Utils.getIdEnte().getNome());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
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
			String nomeEnte = Utils.getLoggedUtente().getEnte().getNome();

			IPaginatedExpression pagExpr = this.utenteSearchDao.newPaginatedExpression();

			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(Utente.model().COGNOME).addOrder(Utente.model().NOME);
			IExpression orExpr = this.utenteSearchDao.newExpression();

			String[] tokens = val.split(" ");

			if(tokens != null && tokens.length > 0){
				for (int i = 0; i < tokens.length; i++) {
					orExpr.ilike(Utente.model().NOME,	tokens[i].toLowerCase(), LikeMode.ANYWHERE);
					orExpr.ilike(Utente.model().COGNOME,	tokens[i].toLowerCase(), LikeMode.ANYWHERE);
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
			pagExpr.and().equals(Utente.model().ENTE.NOME, nomeEnte);

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
			log.debug("Nessun risultato trovato! Metodo ["+methodName+"]: "+ e.getMessage());
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

			return utente;

		}catch (NotFoundException e) {
			log.debug("Metodo ["+methodName+"]: Nessun utente con username ["+username+"]trovato."+ e.getMessage());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
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
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;
	}

	@Override
	public List<UtenteBean> findAll(UtenteSearchForm form)
			throws ServiceException {
		return null;
	}
}
