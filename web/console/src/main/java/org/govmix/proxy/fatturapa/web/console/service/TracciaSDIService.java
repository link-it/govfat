package org.govmix.proxy.fatturapa.web.console.service;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.console.bean.TracciaSDIBean;
import org.govmix.proxy.fatturapa.web.console.iservice.ITracciaSDIService;
import org.openspcoop2.generic_project.exception.ServiceException;

public class TracciaSDIService implements ITracciaSDIService{ 
	
	private IdFattura idFattura = null;

	@Override
	public List<TracciaSDIBean> findAll(int arg0, int arg1) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalCount() throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(TracciaSDIBean arg0) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long arg0) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(TracciaSDIBean arg0) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TracciaSDIBean> findAll() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TracciaSDIBean findById(Long arg0) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(TracciaSDIBean arg0) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIdFattura(IdFattura idFattura) {
		this.idFattura = idFattura;
	}

	@Override
	public IdFattura getIdFattura() {
		return this.idFattura;
	}
}
