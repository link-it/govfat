package org.govmix.proxy.fatturapa.web.console.anagrafica.iservice;

import java.util.List;

import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.RegistroProperty;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.RegistroBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.RegistroSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IRegistroService extends IBaseService<RegistroBean, Long, RegistroSearchForm>{
	
	public RegistroBean findRegistroByNome(String nome) throws ServiceException;
	public List<RegistroProperty> getListaRegistroPropertiesEnte(IdEnte idEnte) throws ServiceException;
}
