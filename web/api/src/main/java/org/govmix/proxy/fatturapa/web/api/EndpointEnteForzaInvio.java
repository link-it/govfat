/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.api;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/")
public interface EndpointEnteForzaInvio extends EndpointEnte {

	
	@GET
	@Produces("text/plain")
	@Path("elencoLottiRicevuti")
	Response elencoLottiRicevuti(@QueryParam("ProxyFatturaPA-codiceUfficio") String codiceUfficio, @QueryParam("ProxyFatturaPA-dataIniziale") Date dataIniziale, @QueryParam("ProxyFatturaPA-dataFinale") Date dataFinale);
	
	@GET
	@Produces("text/plain")
	@Path("forzaInvioLotto")
	Response forzaInvioLotto(@QueryParam("ProxyFatturaPA-IdSdI") String idSdI, @QueryParam("ProxyFatturaPA-ConsegnaDocumentiAccessori") String consegnaDocumentiAccessori);
	
	@GET
	@Produces("text/plain")
	@Path("elencoFattureRicevute")
	Response elencoFattureRicevute(@QueryParam("ProxyFatturaPA-IdSdI") String idSdI);
	
	@GET
	@Produces("text/plain")
	@Path("forzaInvioFattura")
	Response forzaInvioFattura(@QueryParam("ProxyFatturaPA-IdSdI") String idSdI, @QueryParam("ProxyFatturaPA-Posizione") Integer posizione, @QueryParam("ProxyFatturaPA-ConsegnaDocumentiAccessori") String consegnaDocumentiAccessori);

}