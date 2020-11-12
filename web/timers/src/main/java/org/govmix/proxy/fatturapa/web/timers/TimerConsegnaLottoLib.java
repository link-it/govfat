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
package org.govmix.proxy.fatturapa.web.timers;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFatturePassiveBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.govmix.proxy.fatturapa.web.commons.utils.CostantiProtocollazione;
import org.govmix.proxy.fatturapa.web.commons.utils.Endpoint;
import org.govmix.proxy.fatturapa.web.commons.utils.EndpointSelector;

/**
 * Implementazione dell'interfaccia {@link TimerConsegnaLotto}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerConsegnaLottoLib extends AbstractTimerLib {

//	private static final String PROPERTY_ID_SDI = "X-ProxyFatturaPA-IdSdI";
//	private static final String PROPERTY_DATA = "X-ProxyFatturaPA-Data";
//	private static final String PROPERTY_MITTENTE_CF = "X-ProxyFatturaPA-Mittente-CF";
//	private static final String PROPERTY_MITTENTE_RAGIONE_SOCIALE = "X-ProxyFatturaPA-Mittente-RagioneSociale";
//	private static final String PROPERTY_DESTINATARIO = "X-ProxyFatturaPA-Destinatario";
																	

	public TimerConsegnaLottoLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);

	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			LottoFatturePassiveBD lottoBD = new LottoFatturePassiveBD(log, connection, false);

			Date limitDate = new Date();
			this.log.info("Cerco lotti di fatture da consegnare");
			long countFatture = lottoBD.countLottiDaConsegnare(limitDate);
			this.log.info("Trovati ["+countFatture+"] lotti di fatture da consegnare");
			long countFattureElaborate = 0; 

			EndpointSelector endpointSelector = new EndpointSelector(log);

			if(countFatture > 0) {
//				connection.setAutoCommit(false);
				DateFormat sdf = new SimpleDateFormat("MM/dd/yyy HH:mm:ss Z");

				this.log.info("Gestisco ["+countFatture+"] lotti di fatture da consegnare, ["+this.limit+"] alla volta");
				List<LottoFatture> lstId = lottoBD.getLottiDaConsegnare(limitDate, 0, this.limit);

				while(countFattureElaborate < countFatture) {
					try {
						for(LottoFatture lotto : lstId) {
							IdLotto idLotto = lottoBD.convertToId(lotto);
							try {
	
								Endpoint endpoint = endpointSelector.findEndpoint(lotto);
	
								URL urlOriginale = endpoint.getEndpoint().toURL();
								
								this.log.debug("Spedisco il lotto di fatture ["+idLotto.toJson()+"] all'endpoint ["+urlOriginale.toString()+"]");
								
								URL url = new URL(urlOriginale.toString() + "/protocollazioneLotto");
	
								URLConnection conn = url.openConnection();
								HttpURLConnection httpConn = (HttpURLConnection) conn;
								String errore = null;
								boolean esitoPositivo = false;
								ByteArrayOutputStream baos = null;
								String response = null;
								boolean asincrono = false;
								try{
									httpConn.setRequestProperty(CostantiProtocollazione.IDENTIFICATIVO_SDI_HEADER_PARAM, ""+idLotto.getIdentificativoSdi());
									httpConn.setRequestProperty(CostantiProtocollazione.MSG_ID_HEADER_PARAM, ""+lotto.getMessageId());
									httpConn.setRequestProperty(CostantiProtocollazione.NOME_FILE_HEADER_PARAM, ""+lotto.getNomeFile());
									httpConn.setRequestProperty(CostantiProtocollazione.DATA_HEADER_PARAM, "" + sdf.format(lotto.getDataRicezione()));
									httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_HEADER_PARAM, lotto.getCodiceDestinatario());
									
									if(lotto.getCedentePrestatoreCodiceFiscale() != null)
										httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_CF_HEADER_PARAM, lotto.getCedentePrestatoreCodiceFiscale());
									
									if(lotto.getCedentePrestatoreDenominazione() != null) {
										httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_RAGIONESOCIALE_HEADER_PARAM, lotto.getCedentePrestatoreDenominazione());
									} else if(lotto.getCedentePrestatoreNome() != null && lotto.getCedentePrestatoreCognome() != null) {
										httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_RAGIONESOCIALE_HEADER_PARAM, lotto.getCedentePrestatoreNome() + " " + lotto.getCedentePrestatoreCognome());
									}
									
									if(lotto.getCessionarioCommittenteCodiceFiscale() != null) {
										httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_CF_HEADER_PARAM, lotto.getCessionarioCommittenteCodiceFiscale());
									}
									
									if(lotto.getCessionarioCommittenteDenominazione() != null) {
										httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getCessionarioCommittenteDenominazione());
									} else if(lotto.getCessionarioCommittenteNome() != null && lotto.getCessionarioCommittenteCognome() != null) {
										httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getCessionarioCommittenteNome() + " " + lotto.getCessionarioCommittenteCognome());
									}
									
									if(lotto.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale() != null)
										httpConn.setRequestProperty(CostantiProtocollazione.TERZO_INTERMEDIARIO_CF_HEADER_PARAM, lotto.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale());
									
									if(lotto.getTerzoIntermediarioOSoggettoEmittenteDenominazione() != null) {
										httpConn.setRequestProperty(CostantiProtocollazione.TERZO_INTERMEDIARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getTerzoIntermediarioOSoggettoEmittenteDenominazione());
									} else if(lotto.getTerzoIntermediarioOSoggettoEmittenteNome() != null && lotto.getTerzoIntermediarioOSoggettoEmittenteCognome() != null) {
										httpConn.setRequestProperty(CostantiProtocollazione.TERZO_INTERMEDIARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getTerzoIntermediarioOSoggettoEmittenteNome() + " " + lotto.getTerzoIntermediarioOSoggettoEmittenteCognome());
									}
									
	//								
									String ct = FormatoArchivioInvioFatturaType.XML.equals(lotto.getFormatoArchivioInvioFattura()) ? "text/xml" : "application/pkcs7-mime";  
									httpConn.setRequestProperty("Content-Type", ct);
	
									if(endpoint.getUsername() != null && endpoint.getPassword()!= null) {
										String auth = endpoint.getUsername() + ":" + endpoint.getPassword(); 
										String authentication = "Basic " + Base64.encode(auth.getBytes());
	
										httpConn.setRequestProperty("Authorization", authentication);
									}
	
									httpConn.setDoOutput(true);
									httpConn.setDoInput(true);
									
									httpConn.setRequestMethod("POST");								
									baos = new ByteArrayOutputStream();
	
									httpConn.getOutputStream().write(lotto.getXml());
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
									this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviata correttamente all'endpoint ["+url+"]. Response: ["+response+"]");
									lottoBD.updateProtocollo(idLotto, response, asincrono);
								} else {
									if(errore != null) {
										this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviato con errore ["+errore+"] all'endpoint ["+url+"]. Response: ["+response+"]");
										lottoBD.updateStatoConsegna(idLotto, response, "Errore durante la spedizione del lotto di fatture: " + errore);	
									} else {
										this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviato con errore all'endpoint ["+url+"], codice di risposta ["+httpConn.getResponseCode()+"]. Response: ["+response+"]");
										lottoBD.updateStatoConsegna(idLotto, response, "Errore durante la spedizione del lotto di fatture: risposta dal servizio con codice [" + httpConn.getResponseCode()+"]");
									}
	
								}
							}catch (Exception e) {
								this.log.warn("Errore durante la consegna del lotto ["+idLotto.toJson()+"]:"+e.getMessage(), e);
							}

							countFattureElaborate++;
						}
						this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da consegnare");

						lstId = lottoBD.getLottiDaConsegnare(limitDate, 0, this.limit);

//						connection.commit();
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch ConsegnaLotto: "+e.getMessage(), e);
//						connection.rollback();
					}
				}
				this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da consegnare. Fine.");
//				connection.setAutoCommit(true);
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
