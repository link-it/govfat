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
package org.govmix.proxy.fatturapa.web.commons.riceviNotificaDT;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaDecorrenzaTerminiBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.notificadecorrenzatermini.NotificaDecorrenzaTerminiConverter;

public class RiceviNotifica {

	private Logger log;

	public RiceviNotifica(Logger log) throws Exception {
		this.log = log;
	}


	public void ricevi(byte[] raw) throws Exception {
		NotificaDecorrenzaTerminiConverter converter = new NotificaDecorrenzaTerminiConverter(raw);
		NotificaDecorrenzaTermini notificaDecorrenzaTermini = converter.getNotificaDecorrenzaTermini();

		Connection connection = null;
		try {
			
			connection = DAOFactory.getInstance().getConnection();
			connection.setAutoCommit(false);

			FatturaElettronicaBD fatturaElettronicaBD = new FatturaElettronicaBD(log, connection, false);

			List<IdFattura> lstIdFattura = new ArrayList<IdFattura>();
	
			Integer posizioneFattura = converter.getPosizioneFattura();
			if(posizioneFattura != null) {
				
				IdFattura idFattura = new IdFattura();
				idFattura.setIdentificativoSdi(notificaDecorrenzaTermini.getIdentificativoSdi());
				idFattura.setPosizione(posizioneFattura);
				
				if(!fatturaElettronicaBD.exists(idFattura)) {
					throw new Exception("Ricevuta NotificaDecorrenzaTermini relativa a fattura ["+idFattura.toJson()+"] inesistente");	
				}
				
				lstIdFattura.add(idFattura);
				
			} else {
				lstIdFattura.addAll(fatturaElettronicaBD.findAllIdFatturaByIdentificativoSdi(notificaDecorrenzaTermini.getIdentificativoSdi()));
			}
			
			if(lstIdFattura.size() <= 0) {
				throw new Exception("Ricevuta NotificaDecorrenzaTermini relativa a fattura [ Identificativo SdI ["+notificaDecorrenzaTermini.getIdentificativoSdi()+"] "+(posizioneFattura != null ? "Posizione ["+posizioneFattura+"]" : "")+"] inesistente");
			}

			NotificaDecorrenzaTerminiBD notificaDecorrenzaTerminiBD = new NotificaDecorrenzaTerminiBD(log, connection, false);
			notificaDecorrenzaTerminiBD.create(notificaDecorrenzaTermini);

			for(IdFattura idF: lstIdFattura) {
				IdNotificaDecorrenzaTermini idNotificaDecorrenzaTermini = new IdNotificaDecorrenzaTermini();
				idNotificaDecorrenzaTermini.setIdentificativoSdi(notificaDecorrenzaTermini.getIdentificativoSdi());
				
				fatturaElettronicaBD.updateDecorrenzaTermini(idF, idNotificaDecorrenzaTermini);
			}
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
