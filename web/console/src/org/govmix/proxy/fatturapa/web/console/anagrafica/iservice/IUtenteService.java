package org.govmix.proxy.fatturapa.web.console.anagrafica.iservice;

import java.util.List;

import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.UtenteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.UtenteSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IUtenteService extends IBaseService<UtenteBean, Long, UtenteSearchForm>{

	public List<String> getDenominazioneAutoComplete(String val) throws ServiceException;
	
	public UtenteBean findUtenteByUsername(String username) throws ServiceException;
}
