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
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;

/**
 * Implementazione dell'interfaccia {@link TimerConsegnaFattura}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerWorkFlowFatturaLib extends AbstractTimerLib {

	public TimerWorkFlowFatturaLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();

			this.log.info("Cerco fatture");
			
			WorkFlow workflow = new WorkFlow();
			
			workflow.init(this.log, connection, this.limit);
			long countFatture = workflow.count();
			this.log.info("Trovate ["+countFatture+"] lotti");
			long countFattureElaborate = 0; 

			if(countFatture > 0) {
				this.log.info("Gestisco ["+countFatture+"] lotti, ["+this.limit+"] alla volta");
				List<LottoFatture> lst = workflow.getNextLista();

				while(lst != null && !lst.isEmpty()) {
					try {
						for(LottoFatture fattura : lst) {
							workflow.process(fattura);
							countFattureElaborate++;
						}
						this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] lotti");

						lst = workflow.getNextLista();
						
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante l'avvio del workflow del lotto: "+e.getMessage(), e);
					}
				}
				this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] lotti. Fine.");
			}
		}catch(Exception e){
			this.log.error("Errore durante l'esecuzione del batch TimerWorkFlowFatturaLib: "+e.getMessage(), e);
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
