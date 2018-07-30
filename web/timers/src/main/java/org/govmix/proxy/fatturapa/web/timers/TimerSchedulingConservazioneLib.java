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
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.SIP;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.SIPBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;


/**
 * Implementazione dell'interfaccia {@link TimerConsegnaFattura}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerSchedulingConservazioneLib extends AbstractTimerLib {


	public TimerSchedulingConservazioneLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();

			FatturaPassivaBD fatturaElettronicaBD = new FatturaPassivaBD(log, connection, false);
			LottoBD lottoBD = new LottoBD(log, connection, false);
			SIPBD sipBD = new SIPBD(log, connection, false);

			FatturaFilter filter = fatturaElettronicaBD.newFilter();
			filter.getStatiConservazione().add(StatoConservazioneType.PRESA_IN_CARICO);
			filter.setIdSipNull(true);
			List<FatturaElettronica> fatturePerAnno = fatturaElettronicaBD.findAll(filter);

			while(fatturePerAnno != null && !fatturePerAnno.isEmpty()) {


				for(FatturaElettronica fattura: fatturePerAnno) {

					SIP sipFattura = new SIP();
					ChiaveType chiaveFattura = getChiave(fattura);
					sipFattura.setNumero(chiaveFattura.getNumero());
					sipFattura.setAnno(chiaveFattura.getAnno());
					sipFattura.setRegistro(chiaveFattura.getTipoRegistro());
					sipFattura.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
					sipFattura.setDataUltimaConsegna(new Date());
					sipBD.create(sipFattura);
					fatturaElettronicaBD.assegnaIdSip(fattura,sipFattura.getId());


					if(fattura.getLottoFatture().getIdSIP() == null) {

						SIP sipLotto = new SIP();
						ChiaveType chiaveLotto = getChiaveLotto(fattura);
						sipLotto.setNumero(chiaveLotto.getNumero());
						sipLotto.setAnno(chiaveLotto.getAnno());
						sipLotto.setRegistro(chiaveLotto.getTipoRegistro());
						sipLotto.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
						sipLotto.setDataUltimaConsegna(new Date());
						sipBD.create(sipLotto);

						lottoBD.assegnaIdSip(fattura.getLottoFatture(),sipLotto.getId());
					}
				}
				fatturePerAnno = fatturaElettronicaBD.findAll(filter);
			}

		}catch(Exception e){
			this.log.error("Errore durante l'esecuzione del batch AccettazioneFattura: "+e.getMessage(), e);
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
	
	private ChiaveType getChiave(FatturaElettronica fattura) {
		ChiaveType chiave = new ChiaveType();
		chiave.setNumero(fattura.getNumero() + "_" + fattura.getCedentePrestatoreCodiceFiscale());
		chiave.setAnno(fattura.getAnno());
		chiave.setTipoRegistro("FATTURE PASSIVE");
		return chiave;
	}

	private ChiaveType getChiaveLotto(FatturaElettronica fattura) {
		ChiaveType chiave = new ChiaveType();
		chiave.setNumero(""+fattura.getLottoFatture().getIdentificativoSdi());
		chiave.setAnno(fattura.getAnno());
		chiave.setTipoRegistro("LOTTI_FATTURE");
		return chiave;
	}


}
