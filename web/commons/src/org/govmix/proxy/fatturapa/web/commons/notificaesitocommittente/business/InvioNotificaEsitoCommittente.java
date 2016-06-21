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
package org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business;



import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.IdUtente;
import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.constants.EsitoType;
import org.govmix.proxy.fatturapa.notificaesitocommittente.NotificaEC;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaEsitoCommittenteBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.converter.NotificaEsitoCommittenteConverter;

public class InvioNotificaEsitoCommittente {

	private Logger log;
	public InvioNotificaEsitoCommittente(Logger log) throws Exception {
		this.log = log;
	}

	public void invia(InputStream is, IdUtente idUtente) throws Exception {
		Connection connection = null;
		try {
			connection = DAOFactory.getInstance(this.log).getConnection();
			connection.setAutoCommit(false);
			this.invia(new NotificaEsitoCommittenteConverter(is, idUtente), connection);
		} catch(Exception e) {
			this.log.error("Errore durante l'invio della Notifica di Esito Committente: "+e.getMessage(), e);
			if(connection != null){
				connection.rollback();
			}
			throw e;
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}
	}

	public void invia(NotificaEC esito, IdUtente idUtente) throws Exception {
		Connection connection = null;
		try {
			connection = DAOFactory.getInstance(this.log).getConnection();
			connection.setAutoCommit(false);
			this.invia(new NotificaEsitoCommittenteConverter(esito, idUtente), connection);
		} catch(Exception e) {
			this.log.error("Errore durante l'invio della Notifica di Esito Committente: "+e.getMessage(), e);
			if(connection != null){
				connection.rollback();
			}
			throw e;
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}
	}

	public void invia(NotificaEsitoCommittenteConverter converter, Connection connection) throws Exception {

		NotificaEsitoCommittenteBD notificaEsitoCommittenteBD = new NotificaEsitoCommittenteBD(this.log, connection, false);
		UtenteBD utenteBD = new UtenteBD(this.log, connection, false);

		NotificaEsitoCommittente notificaEsitoCommittente = converter.getNotificaEsitoCommittente();
		
		FatturaElettronicaBD fatturaElettronicaBD = new FatturaElettronicaBD(this.log, connection, false);
		FatturaElettronica fattura = fatturaElettronicaBD.get(notificaEsitoCommittente.getIdFattura());
		if(fattura.getIdDecorrenzaTermini() != null) {
			throw new Exception("Impossibile inviare la Notifica di Esito Committente, in quanto e' stata gia' ricevuta una Notifica di Decorrenza Termini per la fattura con id ["+notificaEsitoCommittente.getIdFattura().toJson()+"]");
		}

		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(fattura.getCodiceDestinatario());

		IdUtente idUtente = converter.getIdUtente();


		//check utente appartenente a quel dipartimento
		if(!utenteBD.belongsTo(idUtente, idDipartimento)) {
			throw new Exception("L'utente ["+idUtente.toJson()+"] non appartiene al dipartimento destinatario della fattura.");
		}

		notificaEsitoCommittenteBD.create(notificaEsitoCommittente);

		EsitoType esito = notificaEsitoCommittente.getEsito().equals(EsitoCommittenteType.EC01) ? EsitoType.IN_ELABORAZIONE_ACCETTATO : EsitoType.IN_ELABORAZIONE_RIFIUTATO;
		fatturaElettronicaBD.updateEsito(notificaEsitoCommittente.getIdFattura(), esito);
		connection.commit();
	}

}
