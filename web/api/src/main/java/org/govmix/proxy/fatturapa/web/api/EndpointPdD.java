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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/")
public interface EndpointPdD {

	@POST
	@Path("riceviLotto")
	Response postRiceviLotto(@HeaderParam("GovWay-SDI-FormatoFatturaPA") String X_SDI_FormatoFatturaPA, 
			@HeaderParam("GovWay-SDI-IdentificativoSdI") String X_SDI_IdentificativoSDIString, 
			@HeaderParam("GovWay-SDI-NomeFile") String X_SDI_NomeFile,
			@HeaderParam("GovWay-SDI-FormatoArchivioInvioFattura") String X_SDI_FormatoArchivioInvioFattura,
			@HeaderParam("GovWay-SDI-FormatoArchivioBase64") String X_SDI_FormatoArchivioBase64,
			@HeaderParam("GovWay-SDI-MessageId") String X_SDI_MessageId,
			@HeaderParam("GovWay-SDI-CodiceDestinatario") String X_SDI_CodiceDestinatario,
			@HeaderParam("GovWay-SDI-CedentePrestatore-Denominazione") String X_SDI_CedentePrestatore_Denominazione, 
			@HeaderParam("GovWay-SDI-CedentePrestatore-Nome") String X_SDI_CedentePrestatore_Nome,
			@HeaderParam("GovWay-SDI-CedentePrestatore-Cognome") String X_SDI_CedentePrestatore_Cognome,
			@HeaderParam("GovWay-SDI-CedentePrestatore-CodiceFiscale") String X_SDI_CedentePrestatore_CodiceFiscale,
			@HeaderParam("GovWay-SDI-CedentePrestatore-IdCodice") String X_SDI_CedentePrestatore_IdCodice,
			@HeaderParam("GovWay-SDI-CedentePrestatore-IdPaese") String X_SDI_CedentePrestatore_IdPaese,
			@HeaderParam("GovWay-SDI-CessionarioCommittente-Denominazione") String X_SDI_CessionarioCommittente_Denominazione, 
			@HeaderParam("GovWay-SDI-CessionarioCommittente-Nome") String X_SDI_CessionarioCommittente_Nome,
			@HeaderParam("GovWay-SDI-CessionarioCommittente-Cognome") String X_SDI_CessionarioCommittente_Cognome,
			@HeaderParam("GovWay-SDI-CessionarioCommittente-CodiceFiscale") String X_SDI_CessionarioCommittente_CodiceFiscale,
			@HeaderParam("GovWay-SDI-CessionarioCommittente-IdCodice") String X_SDI_CessionarioCommittente_IdCodice,
			@HeaderParam("GovWay-SDI-CessionarioCommittente-IdPaese") String X_SDI_CessionarioCommittente_IdPaese,
			@HeaderParam("GovWay-SDI-TerzoIntermediarioOSoggettoEmittente-Denominazione") String X_SDI_TerzoIntermediarioOSoggettoEmittente_Denominazione, 
			@HeaderParam("GovWay-SDI-TerzoIntermediarioOSoggettoEmittente-Nome") String X_SDI_TerzoIntermediarioOSoggettoEmittente_Nome,
			@HeaderParam("GovWay-SDI-TerzoIntermediarioOSoggettoEmittente-Cognome") String X_SDI_TerzoIntermediarioOSoggettoEmittente_Cognome,
			@HeaderParam("GovWay-SDI-TerzoIntermediarioOSoggettoEmittente-CodiceFiscale") String X_SDI_TerzoIntermediarioOSoggettoEmittente_CodiceFiscale,
			@HeaderParam("GovWay-SDI-TerzoIntermediarioOSoggettoEmittente-IdCodice") String X_SDI_TerzoIntermediarioOSoggettoEmittente_IdCodice,
			@HeaderParam("GovWay-SDI-TerzoIntermediarioOSoggettoEmittente-IdPaese") String X_SDI_TerzoIntermediarioOSoggettoEmittente_IdPaese,
			@Context HttpHeaders headers,
			InputStream fatturaStream);

	@POST
	@Consumes("text/xml")
	@Path("riceviNotificaDT")
	Response postConsegnaNotificaDT(InputStream notificaStream);
	
	@POST
	@Path("riceviComunicazione")
	Response riceviComunicazioniSdI(@HeaderParam("GovWay-SDI-IdentificativoSdI") String X_SDI_IdentificativoSDI,
			@HeaderParam("X-GovWay-Azione") String azione,
			@HeaderParam("GovWay-SDI-NomeFile") String X_SDI_NomeFile,
			@HeaderParam("Content-Type") String contentType,
			@Context HttpHeaders headers,
			InputStream comunicazioneStream);


}