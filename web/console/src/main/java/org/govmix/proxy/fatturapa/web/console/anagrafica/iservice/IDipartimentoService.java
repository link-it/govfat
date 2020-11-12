/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.govmix.proxy.fatturapa.web.console.anagrafica.iservice;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.DipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdDipartimentoProperty;
import org.govmix.proxy.fatturapa.orm.IdRegistro;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.DipartimentoBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.DipartimentoSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IDipartimentoService extends IBaseService<DipartimentoBean, Long, DipartimentoSearchForm>{

	public DipartimentoBean findDipartimentoByCodice(String codiceDipartimento) throws ServiceException;
	
	public List<Dipartimento> getDescrizioneAutoComplete(String val,String nomeEnte) throws ServiceException;
	public List<Dipartimento> getCodiceAutoComplete(String val,String nomeEnte) throws ServiceException;
	public List<Dipartimento> getCodiceDescrizioneAutoComplete(String val,String nomeEnte) throws ServiceException;
	
	public List<IdDipartimento> findAllIdDipartimento() throws ServiceException;
	
	public List<DipartimentoBean> getListaDipartimentiUtente(Utente utente, boolean ignoreRole) throws ServiceException;
	
	public List<DipartimentoProperty> getListaPropertiesProtocollo(IdRegistro idRegistro) throws ServiceException;
	
	public DipartimentoProperty findPropertyById(IdDipartimentoProperty idProperty) throws ServiceException;
	
	@Override
	public List<DipartimentoBean> findAll(DipartimentoSearchForm search) throws ServiceException;
	
	public List<PccOperazione> getListaOperazioniConsentiteUnitaOrganizzativa() throws ServiceException;
	
	public List<PccDipartimentoOperazione> getListaOperazioniAbilitateUnitaOrganizzativa(IdDipartimento idDipartimento) throws ServiceException;
	
	public void deleteOperazioniUnitaOrganizzativa(IdDipartimento idDipartimento)throws ServiceException;
	
	public void salvaOperazioniUnitaOrganizzativa(List<PccDipartimentoOperazione> listaOperazioni) throws ServiceException;
	
	public void salvaDipartimento(DipartimentoBean bean,IdDipartimento idDipartimento,List<PccDipartimentoOperazione> listaOperazioni) throws ServiceException;
}
