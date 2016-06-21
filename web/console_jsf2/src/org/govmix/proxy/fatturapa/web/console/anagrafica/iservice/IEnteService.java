package org.govmix.proxy.fatturapa.web.console.anagrafica.iservice;

import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.EnteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.EnteSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IEnteService  extends IBaseService<EnteBean, Long, EnteSearchForm>{
	
	public EnteBean findEnteByNome(String nome) throws ServiceException;
}
