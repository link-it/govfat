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
package org.govmix.proxy.fatturapa.web.commons.ricevicomunicazionesdi;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.TracciaSdIBD;

public class RiceviComunicazioneSdI {

	private Logger log;
	private TracciaSdIBD tracciaBD;
	private LottoBD lottoBD;

	public RiceviComunicazioneSdI(Logger log) throws Exception {
		this.log = log;
		this.tracciaBD = new TracciaSdIBD(this.log);
		this.lottoBD = new LottoBD(this.log);

	}

	public RiceviComunicazioneSdI(Logger log, Connection connection, boolean autocommit) throws Exception {
		this.log = log;
		this.tracciaBD = new TracciaSdIBD(this.log, connection, false);
		this.lottoBD = new LottoBD(this.log, connection, false);
	}

	public static TipoComunicazioneType getTipoComunicazione(String tipo) throws Exception {
		
		if("RicevutaConsegna".equals(tipo)) {
			return TipoComunicazioneType.RC;
		} else if("NotificaMancataConsegna".equals(tipo)) {
			return TipoComunicazioneType.MC;
		} else if("NotificaScarto".equals(tipo)) {
			return TipoComunicazioneType.NS;
		} else if("NotificaEsito".equals(tipo)) {
			return TipoComunicazioneType.NE;
		} else if("NotificaDecorrenzaTermini".equals(tipo)) {
			return TipoComunicazioneType.DT;
		} else if("AttestazioneTrasmissioneFattura".equals(tipo)) {
			return TipoComunicazioneType.AT;
		} 

		throw new Exception("Tipo ["+tipo+"] non e' un tipo valido per il tipo di comunicazione");
	}

	public void ricevi(TracciaSDI tracciaSdI) throws Exception {
		this.tracciaBD.insert(tracciaSdI);
		StatoElaborazioneType nuovoStatoLotto = null;
		switch(tracciaSdI.getTipoComunicazione()) {
			case AT: nuovoStatoLotto = StatoElaborazioneType.IMPOSSIBILITA_DI_RECAPITO; 
				break;
			case DT: nuovoStatoLotto = StatoElaborazioneType.RICEVUTA_DECORRENZA_TERMINI;
				break;
			case EC: // solo fatturazione passiva, non gestita
				break;
			case FAT_IN: // solo fatturazione passiva, non gestita
				break;
			case FAT_OUT: // solo fatturazione passiva, non gestita
				break;
			case MC: nuovoStatoLotto = StatoElaborazioneType.MANCATA_CONSEGNA;
				break;
			case MT:  // non gestita
				break;
			case NE: nuovoStatoLotto = StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE;
				break;
			case NS: nuovoStatoLotto = StatoElaborazioneType.RICEVUTO_SCARTO_SDI;
				break;
			case RC: nuovoStatoLotto = StatoElaborazioneType.RICEVUTA_DAL_DESTINATARIO;
				break;
			case SE:  // solo fatturazione passiva, non gestita
				break;
			default:
				break;
		}
		
		if(nuovoStatoLotto != null) {
			IdLotto idLotto = new IdLotto();
			idLotto.setIdentificativoSdi(tracciaSdI.getIdentificativoSdi());
			this.lottoBD.updateStatoElaborazioneInUscitaOK(idLotto, nuovoStatoLotto);
		}
	}
}
