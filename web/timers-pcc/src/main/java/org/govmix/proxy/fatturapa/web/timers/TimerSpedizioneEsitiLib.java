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
package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccTracciamentoBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.govmix.proxy.fatturapa.web.timers.utils.RecuperoEsitiUtils;
import org.govmix.proxy.pcc.wsclient.PccWsClient;

/**
 * Implementazione dell'interfaccia {@link TimerSpedizioneEsiti}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerSpedizioneEsitiLib extends AbstractTimerLib {

	private PccWsClient client;
	
	public TimerSpedizioneEsitiLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
		this.client = new PccWsClient(log);
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			PccTracciamentoBD pccTracciamentoBD = new PccTracciamentoBD(log, connection, false);

			Date limitDate = new Date();
			this.log.info("Cerco esiti da spedire");
			long countEsiti = pccTracciamentoBD.countTraccePerEsiti(limitDate);
			this.log.info("Trovati ["+countEsiti+"] esiti da spedire fino al giorno ["+limitDate+"]");
			long countEsitiElaborati = 0; 

			if(countEsiti > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countEsiti+"] esiti da spedire, ["+this.limit+"] alla volta");
				List<PccTraccia> lstId = pccTracciamentoBD.getTraccePerEsiti(0, this.limit, limitDate);

				while(countEsitiElaborati < countEsiti) {
					try {
						for(PccTraccia traccia: lstId) {

							try {
								RecuperoEsitiUtils.recuperaEsiti(traccia, this.client);
								
							} catch(Exception e) {
								this.log.error("Errore durante la gestione della traccia ["+traccia.getId()+"]. " + e.getMessage(), e);
							}
								
							countEsitiElaborati++;
						}
						this.log.info("Gestiti ["+countEsitiElaborati+"\\"+countEsiti+"] esiti da spedire");

						lstId = pccTracciamentoBD.getTraccePerEsiti(0, this.limit, limitDate);

						connection.commit();
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
				} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch SpedizioneEsiti: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestite ["+countEsitiElaborati+"\\"+countEsiti+"] esiti da spedire. Fine.");
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
