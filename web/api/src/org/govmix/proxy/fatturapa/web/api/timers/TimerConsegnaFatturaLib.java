/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.api.timers;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.web.api.business.consegnaFattura.Endpoint;
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.SingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.utils.CostantiProtocollazione;

/**
 * Implementazione dell'interfaccia {@link TimerConsegnaFattura}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerConsegnaFatturaLib extends AbstractTimerLib {


	private static final String URL_PARAM_ID_SDI = "ProxyFatturaPA-IdSDI";
	private static final String URL_PARAM_POSIZIONE = "ProxyFatturaPA-Posizione";

	private SingleFileExporter sfe;

	public TimerConsegnaFatturaLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
		this.sfe = new SingleFileExporter(log);

	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance(log).getConnection();
			FatturaElettronicaBD fatturaElettronicaBD = new FatturaElettronicaBD(log, connection, false);

			Date limitDate = new Date();
			
			boolean consegnaContestuale = WebApiProperties.getInstance().isConsegnaFatturaContestuale();
			
			this.log.info("Consegna contestuale delle fatture impostata a ["+consegnaContestuale+"]");
			this.log.info("Cerco fatture da consegnare");
			long countFatture = consegnaContestuale ? fatturaElettronicaBD.countFattureDaSpedireContestuale(limitDate) : fatturaElettronicaBD.countFattureDaSpedire(limitDate);
			this.log.info("Trovate ["+countFatture+"] fatture da consegnare");
			long countFattureElaborate = 0; 

			EndpointSelector endpointSelector = new EndpointSelector(log);

			DateFormat sdf = new SimpleDateFormat("MM/dd/yyy HH:MM:ss Z");
			if(countFatture > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countFatture+"] fatture da consegnare, ["+this.limit+"] alla volta");
				List<FatturaElettronica> lstId = consegnaContestuale ? fatturaElettronicaBD.getFattureDaSpedireContestuale(0, this.limit, limitDate) : fatturaElettronicaBD.getFattureDaSpedire(0, this.limit, limitDate);

				while(countFattureElaborate < countFatture) {
					try {
						for(FatturaElettronica fattura : lstId) {
							IdFattura idFattura = fatturaElettronicaBD.convertToId(fattura);


							Endpoint endpoint = endpointSelector.findEndpoint(fattura);

							URL urlOriginale = endpoint.getEndpoint().toURL();
							
							this.log.debug("Spedisco la fattura ["+idFattura.toJson()+"] all'endpoint ["+urlOriginale.toString()+"]");
							
							if(consegnaContestuale) {
								this.log.debug("Identificativo di protocollo lotto ["+fattura.getProtocollo()+"] per la fattura ["+idFattura.toJson()+"]");
							}
							
							URL url = new URL(urlOriginale.toString() + "?" + URL_PARAM_ID_SDI+"="+idFattura.getIdentificativoSdi() + "&"+URL_PARAM_POSIZIONE+"="+idFattura.getPosizione());

							URLConnection conn = url.openConnection();
							HttpURLConnection httpConn = (HttpURLConnection) conn;
							String errore = null;
							boolean esitoPositivo = false;
							ByteArrayOutputStream baos = null;
							String response = null;
							boolean asincrono = false;
							try{

								if(consegnaContestuale) {
									httpConn.setRequestProperty(CostantiProtocollazione.ID_PROTOCOLLO_HEADER_PARAM, fattura.getProtocollo());
								}
								httpConn.setRequestProperty(CostantiProtocollazione.NOME_FILE_HEADER_PARAM, ""+fattura.getNomeFile());
								httpConn.setRequestProperty(CostantiProtocollazione.IDENTIFICATIVO_SDI_HEADER_PARAM, ""+idFattura.getIdentificativoSdi());
								httpConn.setRequestProperty(CostantiProtocollazione.POSIZIONE_HEADER_PARAM, ""+idFattura.getPosizione());
								httpConn.setRequestProperty(CostantiProtocollazione.NUMERO_HEADER_PARAM, "" + fattura.getNumero());
								httpConn.setRequestProperty(CostantiProtocollazione.DATA_HEADER_PARAM, "" + sdf.format(fattura.getDataRicezione()));
								httpConn.setRequestProperty(CostantiProtocollazione.IMPORTO_HEADER_PARAM, ""+ fattura.getImportoTotaleDocumento());
								httpConn.setRequestProperty(CostantiProtocollazione.VALUTA_HEADER_PARAM, "" + fattura.getDivisa());
								httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_CF_HEADER_PARAM, "" + fattura.getCedentePrestatoreCodiceFiscale());
								httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_RAGIONESOCIALE_HEADER_PARAM, "" + fattura.getCedentePrestatoreDenominazione());
								httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_HEADER_PARAM, "" + fattura.getCodiceDestinatario());
								httpConn.setRequestProperty("Content-Type", "application/zip");

								if(endpoint.getUsername() != null && endpoint.getPassword()!= null) {
									String auth = endpoint.getUsername() + ":" + endpoint.getPassword(); 
									String authentication = "Basic " + Base64.encode(auth.getBytes());

									httpConn.setRequestProperty("Authorization", authentication);
								}

								httpConn.setDoOutput(true);
								httpConn.setDoInput(true);
								
								httpConn.setRequestMethod("POST");								
								baos = new ByteArrayOutputStream();
								sfe.exportAsZip(Arrays.asList(idFattura), baos, true); //esporta anche l'xml del lotto di fatture
								
								httpConn.getOutputStream().write(baos.toByteArray());
								httpConn.getOutputStream().flush();
								httpConn.getOutputStream().close();
								
								esitoPositivo = httpConn.getResponseCode() < 299;
								
								response = IOUtils.readStringFromStream(httpConn.getInputStream());
								if(httpConn.getHeaderFields().containsKey(CostantiProtocollazione.PROTOCOLLAZIONE_ASINCRONA_PARAM))
									asincrono = Boolean.parseBoolean(httpConn.getHeaderField(CostantiProtocollazione.PROTOCOLLAZIONE_ASINCRONA_PARAM));
								
							} catch (Exception e) {
								errore = e.getMessage();
							} finally {
								if(baos != null) {
									try {
										baos.flush();
									} catch(Exception e) {}
									try {
										baos.close();
									} catch(Exception e) {}
								}
							}

							if(esitoPositivo) {
								this.log.debug("Fattura ["+idFattura.toJson()+"] inviata correttamente all'endpoint ["+url+"]. Response: ["+response+"]");
								fatturaElettronicaBD.updateProtocollo(idFattura, fattura.getStatoProtocollazione(), response, asincrono);
							} else {
								if(errore != null) {
									this.log.debug("Fattura ["+idFattura.toJson()+"] inviata con errore ["+errore+"] all'endpoint ["+url+"]. Response: ["+response+"]");
									fatturaElettronicaBD.updateStatoConsegna(idFattura, response, "Errore durante la spedizione della fattura: " + errore);	
								} else {
									this.log.debug("Fattura ["+idFattura.toJson()+"] inviata con errore all'endpoint ["+url+"], codice di risposta ["+httpConn.getResponseCode()+"]. Response: ["+response+"]");
									fatturaElettronicaBD.updateStatoConsegna(idFattura, response, "Errore durante la spedizione della fattura: risposta dal servizio con codice [" + httpConn.getResponseCode()+"]");
								}

							}
							countFattureElaborate++;
						}
						this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture da consegnare");

						lstId = consegnaContestuale ? fatturaElettronicaBD.getFattureDaSpedireContestuale(0, this.limit, limitDate) : fatturaElettronicaBD.getFattureDaSpedire(0, this.limit, limitDate);
						connection.commit();
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch ConsegnaFattura: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture da consegnare. Fine.");
				connection.setAutoCommit(true);
			}

		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}

	}

}
