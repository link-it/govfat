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
package org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business;



import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.notificaesitocommittente.NotificaEC;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaEsitoCommittenteBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.fatturapa.web.commons.converter.notificaesitocommittente.NotificaEsitoCommittenteConverter;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.business.exception.NotificaGiaInviataException;

public class InvioNotificaEsitoCommittente {

	private Logger log;
	public InvioNotificaEsitoCommittente(Logger log) throws Exception {
		this.log = log;
	}

	public void invia(InputStream is, IdUtente idUtente) throws Exception {
		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
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
			connection = DAOFactory.getInstance().getConnection();
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
		
		FatturaPassivaBD fatturaElettronicaBD = new FatturaPassivaBD(this.log, connection, false);
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

		if(notificaEsitoCommittenteBD.canNotificaEsitoCommittenteBeSent(notificaEsitoCommittente.getIdFattura()))
		notificaEsitoCommittenteBD.create(notificaEsitoCommittente);
		else
			throw new NotificaGiaInviataException("Impossibile inviare la Notifica di Esito Committente per la fattura con id ["+notificaEsitoCommittente.getIdFattura().toJson()+"] in quanto ne e' stata gia' inviata una");

		EsitoType esito = notificaEsitoCommittente.getEsito().equals(EsitoCommittenteType.EC01) ? EsitoType.IN_ELABORAZIONE_ACCETTATO : EsitoType.IN_ELABORAZIONE_RIFIUTATO;
		fatturaElettronicaBD.updateEsito(notificaEsitoCommittente.getIdFattura(), esito);
		connection.commit();
	}

}
