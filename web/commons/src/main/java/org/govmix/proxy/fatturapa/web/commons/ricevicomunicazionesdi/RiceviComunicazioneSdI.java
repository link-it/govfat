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
import java.util.Date;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.ComunicazioneSdi;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.ComunicazioneSdiBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;

public class RiceviComunicazioneSdI {

	private Logger log;

	public RiceviComunicazioneSdI(Logger log) throws Exception {
		this.log = log;
	}

	public static TipoComunicazioneType getTipoComunicazione(String tipo) throws Exception {
		
		if("RicevutaConsegna".equals(tipo)) {
			return TipoComunicazioneType.RICEVUTA_CONSEGNA;
		} else if("NotificaMancataConsegna".equals(tipo)) {
			return TipoComunicazioneType.NOTIFICA_MANCATA_CONSEGNA;
		} else if("NotificaScarto".equals(tipo)) {
			return TipoComunicazioneType.NOTIFICA_SCARTO;
		} else if("NotificaEsito".equals(tipo)) {
			return TipoComunicazioneType.NOTIFICA_ESITO_COMMITTENTE;
		} else if("NotificaDecorrenzaTermini".equals(tipo)) {
			return TipoComunicazioneType.NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE;
		} else if("AttestazioneTrasmissioneFattura".equals(tipo)) {
			return TipoComunicazioneType.ATTESTAZIONE_TRASMISSIONE_FATTURA;
		} 

		throw new Exception("Tipo ["+tipo+"] non e' un tipo valido per il tipo di comunicazione");
	}


	public void ricevi(Integer identificativoSDI, Integer x_SDI_IdentificativoSDIFattura, TipoComunicazioneType tipo, String contentType, String nomeFile, byte[] raw) throws Exception {
		
		ComunicazioneSdi comunicazioneSdI = new ComunicazioneSdi();
		
		comunicazioneSdI.setIdentificativoSdi(identificativoSDI);
		comunicazioneSdI.setTipoComunicazione(tipo);
		comunicazioneSdI.setDataRicezione(new Date());
		comunicazioneSdI.setContentType(contentType);
		comunicazioneSdI.setNomeFile(nomeFile);
		comunicazioneSdI.setRawData(raw);
		comunicazioneSdI.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
		
		this.ricevi(comunicazioneSdI);

	}
	public void ricevi(ComunicazioneSdi comunicazioneSdI) throws Exception {
	
		
		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			connection.setAutoCommit(false);

			ComunicazioneSdiBD comunicazioneBD = new ComunicazioneSdiBD(this.log, connection, false);

			int progressivo = comunicazioneBD.getNextProgressivo(comunicazioneSdI.getIdentificativoSdi(), comunicazioneSdI.getTipoComunicazione());
			comunicazioneSdI.setProgressivo(progressivo);


			comunicazioneBD.create(comunicazioneSdI);
			
			connection.commit();
		} catch(Exception e) {
			connection.rollback();
			throw e;
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch(Exception e) {}
			}
		}
	}
}
