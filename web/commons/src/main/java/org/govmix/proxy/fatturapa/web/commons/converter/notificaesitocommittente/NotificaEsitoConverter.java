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
package org.govmix.proxy.fatturapa.web.commons.converter.notificaesitocommittente;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;

import it.gov.fatturapa.sdi.messaggi.v1_0.NotificaEsitoCommittenteType;
import it.gov.fatturapa.sdi.messaggi.v1_0.NotificaEsitoType;
import it.gov.fatturapa.sdi.messaggi.v1_0.constants.EsitoCommittenteType;

public class NotificaEsitoConverter {

	private static Unmarshaller unmarshaller;
	private NotificaEsitoType notificaEsito;
	
	static {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NotificaEsitoCommittenteType.class.getPackage().getName());
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	@SuppressWarnings("unchecked")
	public NotificaEsitoConverter(byte[] raw) throws JAXBException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(raw);
			this.notificaEsito = ((JAXBElement<NotificaEsitoType>) unmarshaller.unmarshal(bais)).getValue();
		} finally {
			if(bais != null) {
				try {bais.close();} catch (IOException e) {}
			}
		}
	}
	
	public Integer getPosizione() {
		if(this.notificaEsito != null && this.notificaEsito.getEsitoCommittente() != null && 
			this.notificaEsito.getEsitoCommittente().getRiferimentoFattura() != null && 
			this.notificaEsito.getEsitoCommittente().getRiferimentoFattura().getPosizioneFattura()!= null)
			return this.notificaEsito.getEsitoCommittente().getRiferimentoFattura().getPosizioneFattura();
		return null;
	}

	public StatoElaborazioneType getNuovoStatoLotto() {
		if(this.notificaEsito.getEsitoCommittente() != null) {
			if(EsitoCommittenteType.EC01.equals(this.notificaEsito.getEsitoCommittente().getEsito())) {
				return StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE;
			} else {
				return StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO;
			}
		}
		return null;
	}

	public String getTipoComunicazione() {
		if(StatoElaborazioneType.RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE.equals(getNuovoStatoLotto())) {
			return "NE_ACC";
		} else {
			return "NE_RIF";
		}
	}

	
}
