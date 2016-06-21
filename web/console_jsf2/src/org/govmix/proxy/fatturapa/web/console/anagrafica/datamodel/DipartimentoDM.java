package org.govmix.proxy.fatturapa.web.console.anagrafica.datamodel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.govmix.proxy.fatturapa.web.console.anagrafica.iservice.IDipartimentoService;
import org.govmix.proxy.fatturapa.web.console.anagrafica.service.DipartimentoService;
import org.govmix.proxy.fatturapa.web.console.util.Utils;
import org.openspcoop2.generic_project.web.impl.jsf2.datamodel.ParameterizedDataModel;


@RequestScoped @ManagedBean(name="dipartimentoDM")
public class DipartimentoDM extends ParameterizedDataModel<Dipartimento,Long, DipartimentoBean, 
	IDipartimentoService,  DipartimentoSearchForm>{
	
	
	@PostConstruct
	private void initManagedProperties(){
		this.form = (DipartimentoSearchForm) Utils.findBean("dipartimentoSearchForm");
		this.setDataProvider((DipartimentoService) Utils.findBean("dipartimentoService"));
	}
	
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
 
}
