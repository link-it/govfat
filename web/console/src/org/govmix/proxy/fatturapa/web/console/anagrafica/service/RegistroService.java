package org.govmix.proxy.fatturapa.web.console.anagrafica.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.IdRegistro;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.dao.IRegistroPropertyServiceSearch;
import org.govmix.proxy.fatturapa.dao.IRegistroServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCRegistroService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IRegistroService;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.web.service.BaseService;

public class RegistroService extends BaseService<RegistroSearchForm> implements IRegistroService{

	private IRegistroServiceSearch registroSearchDao = null;
	private IRegistroPropertyServiceSearch registroPropertySearchDao = null;
	private org.govmix.proxy.fatturapa.dao.IRegistroService registroDao = null;
	private static Logger log = LoggerManager.getConsoleDaoLogger();

	public RegistroService(){
		try{
			this.registroDao = DAOFactory.getInstance(log).getServiceManager().getRegistroService();
			this.registroSearchDao = DAOFactory.getInstance(log).getServiceManager().getRegistroServiceSearch();
			this.registroPropertySearchDao = DAOFactory.getInstance(log).getServiceManager().getRegistroPropertyServiceSearch();

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'inizializzazione del service:" + e.getMessage(), e);
		}
	}

	@Override
	public List<RegistroBean> findAll(int start, int limit)
			throws ServiceException {
		String methodName = "findAll(start,limit)";

		List<RegistroBean> lst = new ArrayList<RegistroBean>();

		try{

			lst = _findAllRegistri(this.form);


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

			IExpression expr = getRegistroExpressionFromSearch(this.form);

			NonNegativeNumber nnn = this.registroSearchDao.count(expr);

			if(nnn != null)
				cnt =(int) nnn.longValue();
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return cnt;
	}

	@Override
	public void store(RegistroBean obj) throws ServiceException {
		String methodName = "store()";

		try{
			Registro registro = obj.getDTO();

			IdRegistro idRegistro = new IdRegistro();
			idRegistro.setNome(registro.getNome());

			if(this.registroDao.exists(idRegistro))
				this.registroDao.update(idRegistro, registro);
			else
				this.registroDao.create(registro);
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteById(Long key) throws ServiceException {
		String methodName = "deleteById(id)";

		try{
			((JDBCRegistroService)this.registroDao).deleteById(key.longValue());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
	}

	@Override
	public void delete(RegistroBean obj) throws ServiceException {
		String methodName = "delete(Registro)";

		try{
			Registro registro = obj.getDTO();

			IdRegistro idRegistro = new IdRegistro();
			idRegistro.setNome(registro.getNome());

			this.registroDao.deleteById(idRegistro); 
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}
	}

	@Override
	public RegistroBean findById(Long key) throws ServiceException {
			String methodName = "findById(id)";

			try{
				Registro registro = ((JDBCRegistroService)this.registroDao).get(key.longValue());
				RegistroBean bean = new RegistroBean();
				bean.setDTO(registro);
				List<RegistroProperty> listaPropertiesEnte = this.getListaRegistroPropertiesEnte(registro.getIdEnte()); 
				bean.setListaNomiProperties(listaPropertiesEnte);

				return bean;
			}catch (NotFoundException e) {
				log.debug("Metodo ["+methodName+"]: Nessun registro trovato."+ e.getMessage());
			}catch(Exception e){
				log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			}
			return null;
	}

	@Override
	public List<RegistroBean> findAll() throws ServiceException {
		String methodName = "findAll()";

			List<RegistroBean> lst = new ArrayList<RegistroBean>();

			try{

				lst = _findAllRegistri(this.form);

			}catch(Exception e){
				log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
			}

			return lst;
	}

	@Override
	public boolean exists(RegistroBean obj) throws ServiceException {
		String methodName = "exists()";
		try{
			IdRegistro idObj = new IdRegistro();
			idObj.setNome(obj.getDTO().getNome());
			return this.registroSearchDao.exists(idObj);

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return false;		
	}

	@Override
	public RegistroBean findRegistroByNome(String nome) throws ServiceException {
		String methodName = "findRegistroByNome("+nome+")";
		IExpression expr = null;

		try{
			expr = this.registroSearchDao.newExpression();
			expr.equals(Registro.model().NOME, nome);

			Registro u = this.registroSearchDao.find(expr);
			RegistroBean bean = new RegistroBean();
			bean.setDTO(u);
			List<RegistroProperty> listaPropertiesEnte = this.getListaRegistroPropertiesEnte(u.getIdEnte()); 
			bean.setListaNomiProperties(listaPropertiesEnte);

			return bean;

		}catch (NotFoundException e) {
			log.debug("Metodo ["+methodName+"]: Nessun registro con nome ["+nome+"]trovato."+ e.getMessage());
		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return null;
	}

	@Override
	public List<RegistroProperty> getListaRegistroPropertiesEnte(IdEnte idEnte)
			throws ServiceException {
		List<RegistroProperty> lst = new ArrayList<RegistroProperty>();

		try {
			IPaginatedExpression pagExpr =  this.registroPropertySearchDao.newPaginatedExpression();
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(RegistroProperty.model().NOME);
			pagExpr.equals(RegistroProperty.model().ID_ENTE.NOME, idEnte.getNome());

			lst = this.registroPropertySearchDao.findAll(pagExpr);

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
	public List<RegistroBean> findAll(RegistroSearchForm form) throws ServiceException {
		String methodName = "findAll(RegistroSearchForm form)";

		List<RegistroBean> lst = new ArrayList<RegistroBean>();

		try{

			lst = _findAllRegistri( form);

		}catch(Exception e){
			log.error("Si e' verificato un errore durante l'esecuzione del metodo ["+methodName+"]: "+ e.getMessage(), e);
		}

		return lst;
	}
	
	private List<RegistroBean> _findAllRegistri(RegistroSearchForm form) throws ServiceException {
		List<RegistroBean> lst = new ArrayList<RegistroBean>();

		try{

			IExpression expr = getRegistroExpressionFromSearch( form);

			//order by
			expr.sortOrder(SortOrder.ASC);
			expr.addOrder(Registro.model().NOME);

			IPaginatedExpression pagExpr = this.registroSearchDao.toPaginatedExpression(expr);

			List<Registro> findAll = this.registroSearchDao.findAll(pagExpr,false);

			if(findAll != null && findAll.size() > 0){
				for (Registro registro : findAll) {
					RegistroBean bean = new RegistroBean();
					bean.setDTO(registro);
//					List<RegistroProperty> listaPropertiesEnte = this.getListaRegistroPropertiesEnte(registro.getIdEnte()); 
//					bean.setListaNomiProperties(listaPropertiesEnte);
					lst.add(bean);
				}
			}

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return lst;
	}

	private IExpression getRegistroExpressionFromSearch(RegistroSearchForm search) throws Exception{
		IExpression expr = null;

		try{
			expr = this.registroSearchDao.newExpression();
			if(search != null){
				if(search.getEnte().getValue()!= null){
					expr.equals(Registro.model().ID_ENTE.NOME, search.getEnte().getValue());
				}
			}
		}catch(Exception e){
			log.error("Si e' verificato un errore durante la conversione del filtro di ricerca: " + e.getMessage(), e);
			throw e;
		}

		return expr;
	}
}
