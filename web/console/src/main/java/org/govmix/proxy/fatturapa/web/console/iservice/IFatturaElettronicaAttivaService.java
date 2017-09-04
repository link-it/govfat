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
package org.govmix.proxy.fatturapa.web.console.iservice;

import java.util.List;

import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoRequest;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoSoloConservazioneRequest;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaAttivaBean;
import org.govmix.proxy.fatturapa.web.console.search.FatturaElettronicaAttivaSearchForm;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.web.iservice.IBaseService;

/**
 * IFatturaElettronicaService Interfaccia del livello service di accesso al dao per le fatture.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public interface IFatturaElettronicaAttivaService extends IBaseService<FatturaElettronicaAttivaBean, Long, FatturaElettronicaAttivaSearchForm>{

	
	public List<String> getDestinatarioAutoComplete(String val) throws ServiceException;
	
	public List<String> getNumeroAutoComplete(String val) throws ServiceException;
	
	public InserimentoLottoResponse salvaFatture(List<InserimentoLottoRequest> listaFatture) throws ServiceException;
	
	public InserimentoLottoResponse salvaFattureSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> listaFatture) throws ServiceException;

//	public Date getDataUltimaOperazioneByIdFattura(IdFattura idFattura) throws ServiceException;
}
