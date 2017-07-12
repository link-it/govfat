/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.web.console.anagrafica.bean.UtenteBean;
import org.govmix.proxy.fatturapa.web.console.anagrafica.form.UtenteSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

public interface IUtenteService extends IBaseService<UtenteBean, Long, UtenteSearchForm>{

	public List<String> getDenominazioneAutoComplete(String val) throws ServiceException;

	public UtenteBean findUtenteByUsername(String username) throws ServiceException;

	public List<PccOperazione> getListaOperazioni() throws ServiceException;

	public List<PccUtenteOperazione> getListaOperazioniAbilitateUtente(IdUtente idUtente) throws ServiceException;
	
	public void deleteOperazioniUtente(IdUtente idUtente)	throws ServiceException;
	
	public void salvaOperazioniUtente(List<PccUtenteOperazione> listaOperazioni) throws ServiceException;
	
	public void salvaUtente(UtenteBean bean,IdUtente idUtente,List<PccUtenteOperazione> listaOperazioni) throws ServiceException;
	
}
