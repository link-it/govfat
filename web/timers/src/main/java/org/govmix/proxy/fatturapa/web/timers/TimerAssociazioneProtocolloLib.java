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
package org.govmix.proxy.fatturapa.web.timers;

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
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
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

public class TimerAssociazioneProtocolloLib extends AbstractTimerLib {

	public TimerAssociazioneProtocolloLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);

	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			LottoBD lottoBD = new LottoBD(log, connection, false);
			FatturaElettronicaBD fatturaBD = new FatturaElettronicaBD(log, connection, false);

			Date limitDate = new Date();
			this.log.info("Cerco lotti di fatture da consegnare");
			long countFatture = lottoBD.countLottiDaAssociare(limitDate);
			this.log.info("Trovati ["+countFatture+"] lotti di fatture da consegnare");
			long countFattureElaborate = 0; 

			EndpointSelector endpointSelector = new EndpointSelector(log, connection, false);

			if(countFatture > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countFatture+"] lotti di fatture da consegnare, ["+this.limit+"] alla volta");
				List<LottoFatture> lstId = lottoBD.getLottiDaAssociare(limitDate, 0, this.limit);

				while(countFattureElaborate < countFatture) {
					try {
						for(LottoFatture lotto : lstId) {
							IdLotto idLotto = lottoBD.convertToId(lotto);
							try {
	
								Endpoint endpoint = endpointSelector.findEndpoint(lotto);
	
								URL urlOriginale = endpoint.getEndpointAssociazioneLotto().toURL();
								
								this.log.debug("Associo il protocollo al lotto di fatture ["+idLotto.toJson()+"]");
								
								URL url = new URL(urlOriginale.toString());
	
								URLConnection conn = url.openConnection();
								HttpURLConnection httpConn = (HttpURLConnection) conn;
								boolean esitoPositivo = false;
								String response = null;
								try{
									httpConn.setRequestProperty(CostantiProtocollazione.ID_PROTOCOLLO_HEADER_PARAM, ""+lotto.getProtocollo());
	
									if(endpoint.getUsername() != null && endpoint.getPassword()!= null) {
										String auth = endpoint.getUsername() + ":" + endpoint.getPassword(); 
										String authentication = "Basic " + Base64.encode(auth.getBytes());
	
										httpConn.setRequestProperty("Authorization", authentication);
									}
	
									httpConn.setDoOutput(false);
									httpConn.setDoInput(true);
									
									httpConn.connect();
									esitoPositivo = httpConn.getResponseCode() < 299;
									
									response = IOUtils.readStringFromStream(httpConn.getInputStream());
								} catch (Exception e) {
									this.log.error("Errore durante l'associazione del protocollo al lotto ["+idLotto.toJson()+"]:" + e.getMessage(), e);
								}
	
								if(esitoPositivo) {
									if(response != null && !"".equals(response)) {
										this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviata correttamente all'endpoint ["+url+"]. Assegno il protocollo ["+response+"] alle fatture del lotto.");
										lottoBD.updateStatoProtocollazioneOK(idLotto);
										fatturaBD.assegnaProtocolloAInteroLotto(idLotto, response);
									} else {
										this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviata correttamente all'endpoint ["+url+"]. Protocollo non ancora disponibile");
									}
									
								} else {
									this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviata con errore");
									lottoBD.erroreProtocollo(idLotto);
									fatturaBD.erroreProtocolloAInteroLotto(idLotto);
								}
							}catch (Exception e) {
								this.log.warn("Errore durante l'associazione protocollo del lotto ["+idLotto.toJson()+"]:"+e.getMessage(), e);
							}
							countFattureElaborate++;
						}
						this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da consegnare");

						lstId = lottoBD.getLottiDaAssociare(limitDate, 0, this.limit);

						connection.commit();
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch ConsegnaLotto: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da consegnare. Fine.");
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
