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
import java.util.List;

import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.slf4j.Logger;

/**
 * Implementazione dell'interfaccia {@link TimerConsegnaLotto}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerProtocollazioneRicevutaLib extends AbstractTimerLib {

	public TimerProtocollazioneRicevutaLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);

	}

	@Override
	public void execute() throws Exception {
		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			connection.setAutoCommit(false);

			this.log.info("Cerco fatture");
			
			ProtocollazioneNotifiche workflow = new ProtocollazioneNotifiche();
			
			workflow.init(this.log, connection, this.limit);
			long countFatture = workflow.count();
			this.log.info("Trovate ["+countFatture+"] fatture");
			long countFattureElaborate = 0; 

			if(countFatture > 0) {
				this.log.info("Gestisco ["+countFatture+"] fatture, ["+this.limit+"] alla volta");
				List<TracciaSDI> lst = workflow.getNextLista();

				while(lst != null && !lst.isEmpty()) {
					try {
						for(TracciaSDI traccia : lst) {
							workflow.process(traccia);
							countFattureElaborate++;
						}
						this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture");

						lst = workflow.getNextLista();
						
						connection.commit();
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante la spedizione dell'esito: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture. Fine.");
				connection.setAutoCommit(true);
			}
		}catch(Exception e){
			this.log.error("Errore durante l'esecuzione del batch TimerWorkFlowFatturaLib: "+e.getMessage(), e);
			connection.rollback();
			throw e;
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}

	}

}
