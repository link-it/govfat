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
package org.govmix.proxy.fatturapa.web.api;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class EndpointTrasmittenteImpl implements EndpointTrasmittente {

//	@Context 
//	private MessageContext context;

//	private static final String HEADER_IDENTIFICATIVO_SDI = "X-SDI-IdentificativoSdI";
//	private static final String HEADER_NOME_FILE = "X-SDI-NomeFile";
//	private RiceviComunicazioneSdI riceviComunicazioneSdi;
	private Logger log;

	public EndpointTrasmittenteImpl() throws Exception {
		this.log = LoggerManager.getEndpointTrasmittenteLogger();
		this.log.info("Inizializzazione endpoint Trasmittente...");
//		this.riceviComunicazioneSdi = new RiceviComunicazioneSdI(this.log);
		this.log.info("Inizializzazione endpoint Trasmittente completata");
	}

//	private IdUtente getIdUtente() throws Exception {
//		HttpServletRequest req = context.getHttpServletRequest();
//		
//		if(req == null) {
//			throw new Exception("HttpServletRequest is null");
//		}
//		
//		List<String> principals = context.getHttpHeaders().getRequestHeader("PRINCIPAL_PROXY");
//		
//		if(principals != null && principals.size() > 0) {
//			
//			String principal = principals.get(0);
//			this.log.info("Principal utente: " + principal);
//			IdUtente idUtente = new IdUtente();
//			idUtente.setUsername(principal);			
//			return idUtente;			
//		} else {
//			throw new Exception("Principal utente non trovato");
//		}
//
//	}


	@Override
	public Response postInviaFattura(String tipoFattura, String versione, InputStream lottoStream) {
//		this.log.info("Invoke inviaFattura");
//
//		try {
//			
//			if(lottoStream == null) {
//				throw new Exception("La fattura ricevuta in ingresso e' null");
//			}
//
//			byte[] lottoBytes = IOUtils.readBytesFromStream(lottoStream);
//			
//			URL urlOriginale = WebApiProperties.getInstance().getInvioFatturaURL();
//			
//			this.log.debug("Spedisco il lotto di fatture all'endpoint ["+urlOriginale.toString()+"]");
//
//			String ct = null;
//			
//			if("XML".equals(tipoFattura)) {
//				ct = "text/xml";
//			} else if("ZIP".equals(tipoFattura)) {
//				ct = "application/zip";
//			} else if("P7M".equals(tipoFattura)) {
//				ct = "application/pkcs7-mime";
//			} else {
//				throw new Exception("Tipo Fattura ["+tipoFattura+"] non corretto");
//			}
//
//			if(versione == null) {
//				versione = "FPA12";
//			}
//			
//			URL url = new URL(urlOriginale.toString() + "?TipoFile="+tipoFattura+"&Versione="+versione+"&IdPaese="+WebApiProperties.getInstance().getInvioFatturaIdPaese()+"&IdCodice="+WebApiProperties.getInstance().getInvioFatturaIdCodice());
//
//			URLConnection conn = url.openConnection();
//			HttpURLConnection httpConn = (HttpURLConnection) conn;
//			boolean esitoPositivo = false;
//			ByteArrayOutputStream baos = null;
//			Integer idSdi = null;
//			String nomeFile = null;
//			try{
//
//				httpConn.setRequestProperty("Content-Type", ct);
//
//				if(WebApiProperties.getInstance().getInvioFatturaUsername() != null && WebApiProperties.getInstance().getInvioFatturaPassword()!= null) {
//					String auth = WebApiProperties.getInstance().getInvioFatturaUsername() + ":" + WebApiProperties.getInstance().getInvioFatturaPassword(); 
//					String authentication = "Basic " + Base64.encode(auth.getBytes());
//
//					httpConn.setRequestProperty("Authorization", authentication);
//				}
//
//				httpConn.setDoOutput(true);
//				httpConn.setDoInput(true);
//				
//				httpConn.setRequestMethod("POST");								
//				baos = new ByteArrayOutputStream();
//
//				httpConn.getOutputStream().write(lottoBytes);
//				httpConn.getOutputStream().flush();
//				httpConn.getOutputStream().close();
//				
//				esitoPositivo = httpConn.getResponseCode() < 299;
//
//				if(!esitoPositivo) {
//					String error =IOUtils.readStringFromStream(httpConn.getErrorStream());
//					this.log.error("Errore durante la spedizione del lotto allo Sdi:" + error);
//					throw new Exception(error);
//				}
//				
//				if(httpConn.getHeaderFields().containsKey(HEADER_IDENTIFICATIVO_SDI)) {
//					idSdi = Integer.parseInt(httpConn.getHeaderField(HEADER_IDENTIFICATIVO_SDI));
//				} else {
//					String error = "Errore durante la spedizione del lotto allo Sdi: nessun identificativo Sdi restituito";
//					this.log.error(error);
//					throw new Exception(error);
//				}
//				 
//				if(httpConn.getHeaderFields().containsKey(HEADER_NOME_FILE)) {
//					nomeFile = httpConn.getHeaderField(HEADER_NOME_FILE);
//				}
//				
//			} catch (Exception e) {
//				this.log.error("Errore durante la spedizione del lotto allo Sdi:" + e.getMessage(), e);
//				throw e;
//			} finally {
//				if(baos != null) {
//					try {
//						baos.flush();
//					} catch(Exception e) {}
//					try {
//						baos.close();
//					} catch(Exception e) {}
//				}
//			}
//			
//
//			ComunicazioneSdi comunicazioneSdi = new ComunicazioneSdi();
//			
//			comunicazioneSdi.setIdentificativoSdi(idSdi);
//			comunicazioneSdi.setTipoComunicazione(TipoComunicazioneType.FATTURA_USCITA);
//			comunicazioneSdi.setDataRicezione(new Date());
//			comunicazioneSdi.setContentType(ct);
//			comunicazioneSdi.setNomeFile(nomeFile);
//			comunicazioneSdi.setRawData(lottoBytes);
//			comunicazioneSdi.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
//
//			this.riceviComunicazioneSdi.ricevi(comunicazioneSdi);
//		} catch(Exception e) {
//			this.log.error("inviaFattura completata con errore: "+ e.getMessage(), e);
//			return Response.status(500).entity(e.getMessage()).build();
//		}
//
//		this.log.info("inviaFattura completata con successo");
//		return Response.ok().build();
		return Response.status(500).entity("NOT IMPLEMENTED").build();

	}

	@Override
	public Response riceviComunicazioniSdI(String tipo, Integer X_SDI_IdentificativoSDI, Integer X_SDI_IdentificativoSDIFattura, String X_SDI_NomeFile, String contentType, InputStream comunicazioneStream) {
//		this.log.info("Invoke riceviComunicazioniSdi");
//
//		try {
//			if(comunicazioneStream == null) {
//				throw new Exception("La comunicazione ricevuta in ingresso e' null");
//			}
//			
//			TipoComunicazioneType tipoComunicazione = RiceviComunicazioneSdI.getTipoComunicazione(tipo);
//
//			ComunicazioneSdi comunicazioneSdi = new ComunicazioneSdi();
//			
//			comunicazioneSdi.setIdentificativoSdi(X_SDI_IdentificativoSDIFattura);
//			comunicazioneSdi.setTipoComunicazione(tipoComunicazione);
//			comunicazioneSdi.setDataRicezione(new Date());
//			comunicazioneSdi.setContentType(contentType);
//			comunicazioneSdi.setNomeFile(X_SDI_NomeFile);
//			comunicazioneSdi.setRawData(IOUtils.readBytesFromStream(comunicazioneStream));
//			comunicazioneSdi.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
//			Metadato metadato = new Metadato();
//			metadato.setNome(HEADER_IDENTIFICATIVO_SDI);
//			metadato.setValore(""+X_SDI_IdentificativoSDI);
//			comunicazioneSdi.addMetadato(metadato);
//
//			this.riceviComunicazioneSdi.ricevi(comunicazioneSdi);
//		} catch(Exception e) {
//			this.log.error("riceviComunicazioniSdi completata con errore:"+ e.getMessage(), e);
//			return Response.status(500).build();
//		}
//
//		this.log.info("riceviComunicazioniSdi completata con successo");
//		return Response.ok().build();
		return Response.status(500).entity("NOT IMPLEMENTED").build();
	}

}
