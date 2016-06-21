package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.dao.IEnteServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCEnteService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IEnteService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;


@RequestScoped @ManagedBean(name="enteService")
public class EnteService extends BaseService<EnteSearchForm> implements IEnteService {

	private IEnteServiceSearch enteSearchDao = null;
	private org.govmix.proxy.fatturapa.dao.IEnteService enteDao = null;

	private static Logger log = LoggerManager.getConsoleDaoLogger();

	public EnteService(){
		try{
			this.enteDao = DAOFactory.getInstance(log).getServiceManager().getEnteService();
			this.enteSearchDao = DAOFactory.getInstance(log).getServiceManager().getEnteServiceSearch();
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}
	
	@PostConstruct
	private void initManagedProperties(){
		this.form = (EnteSearchForm) Utils.findBean("enteSearchForm");
	}

	@Override
	public List<EnteBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<EnteBean> lst = new ArrayList<EnteBean>();

		try{

			IExpression expr = getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Ente.model().NOME);

			IPaginatedExpression pagExpr = this.enteSearchDao.toPaginatedExpression(expr);

			pagExpr.offset(start);
			pagExpr.limit(limit);

			List<Ente> findAll = this.enteSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (Ente ente : findAll) {
					EnteBean toRet = new EnteBean();
					toRet.setDTO(ente);
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

			NonNegativeNumber nnn = this.enteSearchDao.count(expr);

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
	public void store(EnteBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Ente ente = obj.getDTO();

			IdEnte idEnte = new IdEnte();
			idEnte.setNome(ente.getNome());

			if(this.enteDao.exists(idEnte))
				this.enteDao.update(idEnte, ente);
			else
				this.enteDao.create(ente);
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		try{
			((JDBCEnteService)this.enteDao).deleteById(key.longValue());
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public void delete(EnteBean obj) throws ServiceException {
		String methodName = "delete()";

		try{
			Ente ente = obj.getDTO();

			IdEnte idEnte = new IdEnte();
			idEnte.setNome(ente.getNome());

			this.enteDao.deleteById(idEnte); 
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw e;
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	@Override
	public EnteBean findById(Long key) throws ServiceException {
		String methodName = "findById(id)";

		try{
			Ente d = ((JDBCEnteService)this.enteDao).get(key.longValue());
			EnteBean ut = new EnteBean();
			ut.setDTO(d);

			return ut;
		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(NotFoundException e){
			log.debug("["+methodName+"] Ente non trovato: "+ e.getMessage());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<EnteBean> findAll() throws ServiceException {
		String methodName = "findAll()";

		List<EnteBean> lst = new ArrayList<EnteBean>();

		try{

			IExpression expr = getExpressionFromSearch(this.form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Ente.model().NOME);

			IPaginatedExpression pagExpr = this.enteSearchDao.toPaginatedExpression(expr);

			List<Ente> findAll = this.enteSearchDao.findAll(pagExpr);

			if(findAll != null && findAll.size() > 0){
				for (Ente ente : findAll) {
					EnteBean toRet = new EnteBean();
					toRet.setDTO(ente);
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

	private IExpression getExpressionFromSearch(EnteSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.enteSearchDao.newExpression();
			if(search != null){
				if(search.getNome().getValue()!= null){
					expr.equals(Ente.model().NOME, search.getNome().getValue());
				}
			}
		}catch(Exception e){
			log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}

	@Override
	public EnteBean findEnteByNome(String nome) throws ServiceException {
		String methodName = "findEnteByNome("+nome+")";
		IExpression expr = null;

		try{
			expr = this.enteSearchDao.newExpression();
			expr.equals(Ente.model().NOME, nome);

			Ente u = this.enteSearchDao.find(expr);
			EnteBean ente = new EnteBean();
			ente.setDTO(u);

			return ente;

		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public boolean exists(EnteBean obj) throws ServiceException {
		String methodName = "exists()";
		try{
			IdEnte idObj = new IdEnte();
			idObj.setNome(obj.getDTO().getNome());
			return this.enteSearchDao.exists(idObj );

		}catch(ServiceException e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;
	}

	@Override
	public List<EnteBean> findAll(EnteSearchForm form) throws ServiceException {
		return null;
	}

}

