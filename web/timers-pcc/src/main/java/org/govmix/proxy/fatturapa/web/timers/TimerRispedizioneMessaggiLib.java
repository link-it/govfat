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
package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.govmix.proxy.fatturapa.web.timers.utils.RecuperoEsitiUtils;
import org.govmix.proxy.pcc.wsclient.PccWsClient;

/**
 * Implementazione dell'interfaccia {@link TimerRispedizioneMessaggi}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerRispedizioneMessaggiLib extends AbstractTimerLib {


	public TimerRispedizioneMessaggiLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			PccTracciamentoBD pccTracciamentoBD = new PccTracciamentoBD(log, connection, false);

			Date limitDate = new Date();
			this.log.info("Cerco messaggi da spedire");
			long countMessagggiDaRispedire = pccTracciamentoBD.countTraccePerRispedizione(limitDate);
			this.log.info("Trovati ["+countMessagggiDaRispedire+"] messaggi da spedire");
			long countEsitiElaborati = 0;

			if(countMessagggiDaRispedire > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countMessagggiDaRispedire+"] messaggi da spedire, ["+this.limit+"] alla volta");
				List<PccTraccia> lstId = pccTracciamentoBD.getTraccePerRispedizione(0, this.limit, limitDate);

				PccWsClient client = new PccWsClient(log);
				while(countEsitiElaborati < countMessagggiDaRispedire) {
					try {
						for(PccTraccia traccia: lstId) {

							try {
								RecuperoEsitiUtils.rispedisciProxy(traccia, client);
								
							} catch(Exception e) {
								this.log.error("Errore durante la gestione della traccia ["+traccia.getId()+"]. " + e.getMessage(), e);
							}
								
							countEsitiElaborati++;
						}
						this.log.info("Gestiti ["+countEsitiElaborati+"\\"+countMessagggiDaRispedire+"] messaggi da spedire");

						lstId = pccTracciamentoBD.getTraccePerRispedizione(0, this.limit, limitDate);

						connection.commit();
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch SpedizioneMessaggi: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestite ["+countEsitiElaborati+"\\"+countMessagggiDaRispedire+"] messaggi da spedire. Fine.");
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
