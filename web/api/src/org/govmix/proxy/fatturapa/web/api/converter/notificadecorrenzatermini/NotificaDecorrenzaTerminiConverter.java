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
package org.govmix.proxy.fatturapa.web.api.converter.notificadecorrenzatermini;

import it.gov.fatturapa.sdi.messaggi.v1_0.NotificaDecorrenzaTerminiType;
import it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbDeserializer;

import java.util.Date;

import org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini;

public class NotificaDecorrenzaTerminiConverter {

	private static JaxbDeserializer deserializer;
	
	static {
		try {
			deserializer = new JaxbDeserializer();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private NotificaDecorrenzaTerminiType esito;
	private String raw;
	
	public NotificaDecorrenzaTerminiConverter(String raw) throws Exception {
		this.raw = raw;
		this.esito =  deserializer.readNotificaDecorrenzaTerminiTypeFromString(raw);
	}
	
	public NotificaDecorrenzaTermini getNotificaDecorrenzaTermini() throws Exception {

		NotificaDecorrenzaTermini notifica = new NotificaDecorrenzaTermini();
		
		notifica.setIdentificativoSdi(this.esito.getIdentificativoSdI());
		notifica.setNomeFile(this.esito.getNomeFile());
		notifica.setDescrizione(this.esito.getDescrizione());
		notifica.setMessageId(this.esito.getMessageId());
		notifica.setNote(this.esito.getNote());
		notifica.setDataRicezione(new Date());
		notifica.setXml(this.raw);
		
		return notifica;
	}

	public Integer getPosizioneFattura() {
		if(this.esito.getRiferimentoFattura() != null) {
			return this.esito.getRiferimentoFattura().getPosizioneFattura();
		} else {
			return null;
		}
		
		
	}
}
