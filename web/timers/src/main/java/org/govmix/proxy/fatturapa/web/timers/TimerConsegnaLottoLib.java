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
package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFatturePassiveBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.govmix.proxy.fatturapa.web.commons.utils.EndpointSelector;
import org.govmix.proxy.fatturapa.web.commons.utils.ProtocollazioneUtils;

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
									
	private ProtocollazioneUtils utils;

	public TimerConsegnaLottoLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
		this.utils = new ProtocollazioneUtils(log);
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

				this.log.info("Gestisco ["+countFatture+"] lotti di fatture da consegnare, ["+this.limit+"] alla volta");
				List<LottoFatture> lstId = lottoBD.getLottiDaConsegnare(limitDate, 0, this.limit);

				while(countFattureElaborate < countFatture) {
					try {
						for(LottoFatture lotto : lstId) {
							String idLotto = lottoBD.convertToId(lotto).toJson();
							try {
								this.utils.inviaLotto(lottoBD, endpointSelector, lotto, idLotto);
							}catch (Exception e) {
								this.log.warn("Errore durante la consegna del lotto ["+idLotto+"]:"+e.getMessage(), e);
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
