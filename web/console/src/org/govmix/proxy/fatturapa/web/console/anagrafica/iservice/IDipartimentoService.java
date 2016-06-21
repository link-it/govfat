package org.govmix.proxy.fatturapa.web.console.anagrafica.iservice;

import java.util.List;

import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.DipartimentoProperty;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.IdProperty;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IDipartimentoService extends IBaseService<DipartimentoBean, Long, DipartimentoSearchForm>{

	public DipartimentoBean findDipartimentoByCodice(String codiceDipartimento) throws ServiceException;
	
	public List<Dipartimento> getDescrizioneAutoComplete(String val) throws ServiceException;
	public List<Dipartimento> getCodiceAutoComplete(String val) throws ServiceException;
	public List<Dipartimento> getCodiceDescrizioneAutoComplete(String val) throws ServiceException;
	
	public List<IdDipartimento> findAllIdDipartimento() throws ServiceException;
	
	public List<DipartimentoBean> getListaDipartimentiUtente(Utente utente, Ente ente, boolean ignoreRole) throws ServiceException;
	
	public List<DipartimentoProperty> getListaPropertiesEnte(IdEnte idEnte) throws ServiceException;
	
	public DipartimentoProperty findPropertyById(IdProperty idProperty) throws ServiceException;
	
	public List<DipartimentoBean> findAll(DipartimentoSearchForm search) throws ServiceException;
}
