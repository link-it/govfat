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

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.notificaesitocommittente.EsitoCommittente;
import org.govmix.proxy.fatturapa.notificaesitocommittente.NotificaEC;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.InvioNotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.converter.NotificaEsitoCommittenteConverter;

/**
 * Implementazione dell'interfaccia {@link TimerConsegnaFattura}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerAccettazioneFatturaLib extends AbstractTimerLib {


	//Giorni che passano dall'accettazione della fattura al passaggio del batch
	private int giorniScarto = 14;
	
	public TimerAccettazioneFatturaLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance(log).getConnection();
			
			FatturaElettronicaBD fatturaElettronicaBD = new FatturaElettronicaBD(log, connection, false);
			UtenteBD utenteBD = new UtenteBD(log, connection, false);
			InvioNotificaEsitoCommittente invio = new InvioNotificaEsitoCommittente(this.log);
			
			Date currentDate = new Date();
			
			long millisecondsInHour = 60*60*1000;
			long millisecondsInDay = 24*millisecondsInHour;
			long millisecondsToday = currentDate.getTime() % millisecondsInDay;
			
			Date dataSenzaOra = new Date(currentDate.getTime() - millisecondsToday - millisecondsInHour); // TODO migliorare: per ora sottraggo un'ora per il fuso orario
			
			Date limitDate = new Date(dataSenzaOra.getTime() - ((this.giorniScarto-1)*millisecondsInDay));
			
			this.log.info("Cerco fatture da consegnare");
			long countFatture = fatturaElettronicaBD.countFattureDaAccettare(limitDate);
			this.log.info("Trovate ["+countFatture+"] fatture da accettare fino al giorno ["+limitDate+"]");
			long countFattureElaborate = 0; 

			if(countFatture > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countFatture+"] fatture da accettare, ["+this.limit+"] alla volta");
				List<FatturaElettronica> lstId = fatturaElettronicaBD.getFattureDaAccettare(0, this.limit, limitDate);

				while(countFattureElaborate < countFatture) {
					try {
						for(FatturaElettronica fattura : lstId) {
							
							IdFattura idFattura = new IdFattura();
							idFattura = fatturaElettronicaBD.convertToId(fattura);
							
							NotificaEC esito = new NotificaEC();
							esito.setEsito(EsitoCommittente.EC_01);
							esito.setIdentificativoSdi(new BigInteger(idFattura.getIdentificativoSdi() + ""));
							esito.setPosizione(new BigInteger(idFattura.getPosizione() + ""));
							IdUtente idUtente =utenteBD.getUtenteAccettatore();
							
							NotificaEsitoCommittenteConverter converter = new NotificaEsitoCommittenteConverter(esito, idUtente, true);
							invio.invia(converter, connection);

							countFattureElaborate++;
						}
						this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture da accettare");

						lstId = fatturaElettronicaBD.getFattureDaAccettare(0, this.limit, limitDate);

						connection.commit();
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch AccettazioneFattura: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] fatture da accettare. Fine.");
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
