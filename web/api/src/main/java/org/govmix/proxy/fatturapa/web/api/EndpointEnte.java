/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/")
public interface EndpointEnte {

	@POST
	@Consumes("text/xml")
	@Path("riceviNotificaEC")
	Response postRiceviNotificaEC(InputStream esitoStream);

	@GET
	@Produces("text/plain")
	@Path("listaFattureNonConsegnate")
	Response cercaFattureNonConsegnate(@QueryParam("ProxyFatturaPA-Limit") Integer limit);
	
	@GET
	@Produces("application/zip")
	@Path("consegnaFattura")
	Response recuperaFatturaNonConsegnata(@QueryParam("ProxyFatturaPA-IdSdI") Integer idSdI, @QueryParam("ProxyFatturaPA-Posizione") Integer posizione);

}