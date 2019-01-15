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
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.FatturaSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.govmix.proxy.fatturapa.web.commons.utils.EndpointSelector;
import org.govmix.proxy.fatturapa.web.commons.utils.ProtocollazioneUtils;
import org.govmix.proxy.fatturapa.web.timers.utils.BatchProperties;

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

	private ProtocollazioneUtils utils;
	
	public TimerConsegnaFatturaLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
		this.utils = new ProtocollazioneUtils(log);
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			FatturaPassivaBD fatturaElettronicaBD = new FatturaPassivaBD(log, connection, false);
			EndpointSelector endpointSelector = new EndpointSelector(this.log, connection, false);
			FatturaSingleFileExporter exp = new FatturaSingleFileExporter(this.log, connection, false);


			Date limitDate = new Date();
			
			boolean consegnaContestuale = BatchProperties.getInstance().isConsegnaFatturaContestuale();
			
			this.log.info("Consegna contestuale delle fatture impostata a ["+consegnaContestuale+"]");
			this.log.info("Cerco fatture da consegnare");
			long countFatture = 0;
			try {
				countFatture = consegnaContestuale ? fatturaElettronicaBD.countFattureDaSpedireContestuale(limitDate) : fatturaElettronicaBD.countFattureDaSpedire(limitDate);
			}catch (SQLException e) {
				this.log.warn("Errore SQL durante la count:"+e.getMessage(), e);
			}
			this.log.info("Trovate ["+countFatture+"] fatture da consegnare");
			long countFattureElaborate = 0; 

			if(countFatture > 0) {

				this.log.info("Gestisco ["+countFatture+"] fatture da consegnare, ["+this.limit+"] alla volta");
				List<FatturaElettronica> lstId = consegnaContestuale ? fatturaElettronicaBD.getFattureDaSpedireContestuale(0, this.limit, limitDate) : fatturaElettronicaBD.getFattureDaSpedire(0, this.limit, limitDate);

				while(countFattureElaborate < countFatture) {
					try {
						for(FatturaElettronica fattura : lstId) {
							IdFattura idFattura = fatturaElettronicaBD.convertToId(fattura);
							try {
								this.utils.inviaFattura(fatturaElettronicaBD, endpointSelector, exp, fattura, idFattura, consegnaContestuale);
							}catch (SQLException e) {
								this.log.warn("Errore SQL durante la consegna della fattura ["+idFattura.toJson()+"]:"+e.getMessage(), e);
							}catch (Exception e) {
								this.log.warn("Errore durante la consegna della fattura ["+idFattura.toJson()+"]:"+e.getMessage(), e);
							}

							countFattureElaborate++;
						}
						this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture da consegnare");

						lstId = consegnaContestuale ? fatturaElettronicaBD.getFattureDaSpedireContestuale(0, this.limit, limitDate) : fatturaElettronicaBD.getFattureDaSpedire(0, this.limit, limitDate);
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch ConsegnaFattura: "+e.getMessage(), e);
					}
				}
				this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture da consegnare. Fine.");
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
