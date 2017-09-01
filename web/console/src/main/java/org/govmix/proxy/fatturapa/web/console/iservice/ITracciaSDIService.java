package org.govmix.proxy.fatturapa.web.console.iservice;

import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.console.bean.TracciaSDIBean;
import org.openspcoop2.generic_project.web.dao.IService;

public interface ITracciaSDIService extends IService<TracciaSDIBean, Long>{
	
	public void setIdFattura(IdFattura idFattura);
	public IdFattura getIdFattura();

}
