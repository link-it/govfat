/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/")
public interface EndpointTrasmittente {

	@POST
	@Path("inviaFattura/{tipo}")
	Response postInviaFattura(@PathParam("tipo") String tipoFattura, InputStream fatturaStream);

	@POST
	@Path("riceviComunicazione/{tipo}")
	Response riceviComunicazioniSdI(@PathParam("tipo") String tipo,
			@HeaderParam("X-SDI-IdentificativoSDI") Integer X_SDI_IdentificativoSDI,
			@HeaderParam("X-SDI-IdentificativoSDIFattura") Integer X_SDI_IdentificativoSDIFattura,
									@HeaderParam("X-SDI-NomeFile") String X_SDI_NomeFile,
			 						@HeaderParam("Content-Type") String contentType,
			 						InputStream comunicazioneStream);

}