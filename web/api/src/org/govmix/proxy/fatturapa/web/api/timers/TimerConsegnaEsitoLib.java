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

import it.gov.fatturapa.sdi.messaggi.v1_0.NotificaEsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.ObjectFactory;
import it.gov.fatturapa.sdi.messaggi.v1_0.RiferimentoFatturaType;
import it.gov.fatturapa.sdi.messaggi.v1_0.ScartoEsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.constants.EsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbDeserializer;
import it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbSerializer;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.constants.EsitoType;
import org.govmix.proxy.fatturapa.constants.ScartoType;
import org.govmix.proxy.fatturapa.web.api.utils.WebApiProperties;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaEsitoCommittenteBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;

/**
 * Timer per la consegna degli esiti delle fatture alla PdD
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerConsegnaEsitoLib extends AbstractTimerLib {

	private static final String NOME_FILE_URL_PARAM = "NomeFile";

	private JaxbSerializer serializer;
	private ObjectFactory of;
	private JaxbDeserializer deserializer;


	public TimerConsegnaEsitoLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
		this.serializer = new JaxbSerializer();
		this.of = new ObjectFactory();
		this.deserializer = new JaxbDeserializer();

	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance(log).getConnection();
			NotificaEsitoCommittenteBD notificaEsitoCommittenteBD = new NotificaEsitoCommittenteBD(log, connection, false);
			FatturaElettronicaBD fatturaElettronicaBD = new FatturaElettronicaBD(log, connection, false);

			Date limitDate = new Date(System.currentTimeMillis());
			Date fakeDate = new Date(System.currentTimeMillis()+1000);

			this.log.info("Cerco NotificheEsitoCommittente da consegnare");
			long countNotifiche = notificaEsitoCommittenteBD.countNotifiche(limitDate);
			this.log.info("Trovate ["+countNotifiche+"] NotificheEsitoCommittente da consegnare");
			long countNotificheElaborate = 0; 

			if(countNotifiche > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countNotifiche+"] NotificheEsitoCommittente da consegnare, ["+limit+"] alla volta");
				List<NotificaEsitoCommittente> lstNotifiche = notificaEsitoCommittenteBD.findAllNotifiche(limitDate, 0, this.limit);

				WebApiProperties properties = WebApiProperties.getInstance();
				try {
					while(countNotificheElaborate < countNotifiche) {
						for(NotificaEsitoCommittente notifica : lstNotifiche) {


							NotificaEsitoCommittenteType nec = new NotificaEsitoCommittenteType();
							EsitoCommittenteType esito;
							switch(notifica.getEsito()) {
							case EC01: esito = EsitoCommittenteType.EC01;
							break;
							case EC02: esito = EsitoCommittenteType.EC02;
							break;
							default: esito = EsitoCommittenteType.EC02;
							break;

							}
							nec.setEsito(esito);

							nec.setDescrizione(notifica.getDescrizione());
							nec.setIdentificativoSdI(notifica.getIdFattura().getIdentificativoSdi());

							nec.setMessageIdCommittente(notifica.getMessageIdCommittente());
							nec.setVersione("1.0");

							RiferimentoFatturaType riferimentoFattura = new RiferimentoFatturaType();
							riferimentoFattura.setAnnoFattura(notifica.getAnno());
							riferimentoFattura.setNumeroFattura(notifica.getNumeroFattura());
							riferimentoFattura.setPosizioneFattura(notifica.getIdFattura().getPosizione());

							nec.setRiferimentoFattura(riferimentoFattura);


							String necXML = this.serializer.toString(this.of.createNotificaEsitoCommittente(nec));

							URL urlOriginale = properties.getRicezioneEsitoURL();
							URL url = new URL(urlOriginale.toString() + "?" + NOME_FILE_URL_PARAM + "=" + notifica.getNomeFile());
							URLConnection conn = url.openConnection();
							HttpURLConnection httpConn = (HttpURLConnection) conn;

							if(properties.getRicezioneEsitoUsername() != null && !"".equals(properties.getRicezioneEsitoUsername())
									&&
									properties.getRicezioneEsitoPassword() != null && !"".equals(properties.getRicezioneEsitoPassword())) {
								String auth =  properties.getRicezioneEsitoUsername() + ":" +  properties.getRicezioneEsitoPassword(); 
								String authentication = "Basic " + Base64.encode(auth.getBytes());
								httpConn.setRequestProperty("Authorization", authentication);
							}

							httpConn.setRequestProperty("Content-Type", "text/xml");

							httpConn.setDoOutput(true);
							httpConn.setDoInput(true);

							httpConn.setRequestMethod("POST");
							httpConn.getOutputStream().write(necXML.getBytes());
							httpConn.getOutputStream().close();

							int esitoChiamata = -1;
							//							try{
							esitoChiamata = httpConn.getResponseCode();
							//NOTA: se ricevo un'eccezione dalla PdD riprovero' la consegna nella prossima tornata del batch
							//							} catch (Exception e) {
							//esito negativo
							//							}

							if(esitoChiamata > 299) {
								this.log.info("Risposta dallo SdI con codice ["+esitoChiamata+"]. ritentero' l'invio alla prossima schedulazione del job");
								notifica.setDataInvioSdi(fakeDate);
							} else {
								this.log.info("Risposta dallo SdI con codice ["+esitoChiamata+"]. Aggiorno l'esito della fattura e inserisco lo scarto");
								notifica.setDataInvioSdi(new Date());
								notifica.setXml(necXML);

								if(esitoChiamata == 200) {

									String scartoXML = null;
									try {
										scartoXML = IOUtils.toString(httpConn.getInputStream());
									} finally {
										if(httpConn.getInputStream() != null)
											try {httpConn.getInputStream().close(); } catch(Exception e) {} //IOUtils non chiude sempre (TODO usare commons IO di apache) 
									}

									ScartoEsitoCommittenteType scarto = deserializer.readScartoEsitoCommittenteTypeFromString(scartoXML);
									ScartoType scartoT;
									switch(scarto.getScarto()) {
									case EN00: scartoT = ScartoType.EN00;
									break;
									case EN01: scartoT = ScartoType.EN01;
									break;
									default: scartoT = ScartoType.EN00;
									break;

									}

									notifica.setScarto(scartoT);
									notifica.setScartoNote(scarto.getNote());
									notifica.setScartoXml(scartoXML);
									
									EsitoType esitoFattura;
									switch(esito) {
									case EC01: esitoFattura = EsitoType.SCARTATA_ACCETTATO;
									break;
									case EC02: esitoFattura = EsitoType.SCARTATA_RIFIUTATO;
									break;
									default: esitoFattura = EsitoType.SCARTATA_ACCETTATO;
									break;

									}

									this.log.info("Esito della fattura ["+esitoFattura.toString()+"].");
									
									fatturaElettronicaBD.updateEsito(notifica.getIdFattura(), esitoFattura);

								} else if (esitoChiamata == 202) {
									this.log.info("Risposta dallo SdI con codice ["+esitoChiamata+"]. Aggiorno l'esito della fattura.");

									EsitoType esitoFattura;
									switch(esito) {
									case EC01: esitoFattura = EsitoType.INVIATA_ACCETTATO;
									break;
									case EC02: esitoFattura = EsitoType.INVIATA_RIFIUTATO;
									break;
									default: esitoFattura = EsitoType.INVIATA_ACCETTATO;
									break;

									}

									this.log.info("Esito della fattura ["+esitoFattura.toString()+"].");
									
									fatturaElettronicaBD.updateEsito(notifica.getIdFattura(), esitoFattura);
								}
							}
							notificaEsitoCommittenteBD.update(notifica);
							countNotificheElaborate++;
						}
						this.log.info("Gestite ["+countNotificheElaborate+"\\"+countNotifiche+"] NotificheEsitoCommittente da consegnare");

						lstNotifiche = notificaEsitoCommittenteBD.findAllNotifiche(limitDate, 0, this.limit);
					}
					connection.commit();
				} catch(Exception e) {
					log.error("Errore durante la consegnaEsito:"+e.getMessage(), e);
					connection.rollback();
				}

				this.log.info("Gestite ["+countNotificheElaborate+"\\"+countNotifiche+"] NotificheEsitoCommittente da consegnare. Fine");
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
