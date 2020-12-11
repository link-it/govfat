/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.notificaesitocommittente.EsitoCommittente;
import org.govmix.proxy.fatturapa.notificaesitocommittente.MotivoRifiuto;
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
			connection.commit();
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

	public static int getLunghezzaResiduaDescrizione(List<MotivoRifiuto> lstMotivi) throws Exception {
		
		int lunghezzaMax = 255; 
		if(lstMotivi == null || lstMotivi.isEmpty()) {
			return lunghezzaMax;
		}
		NotificaEC esito = new NotificaEC();
		
		esito.setEsito(EsitoCommittente.EC_02);
		esito.setMotivoRifiuto(lstMotivi);
		esito.setDescrizione("");
		String descrizione = new NotificaEsitoCommittenteConverter(esito, null).getDescrizione();
		return lunghezzaMax - descrizione.length();
	}

	public static int getLunghezzaRealeDescrizione(NotificaEC esito) throws Exception {
		return new NotificaEsitoCommittenteConverter(esito, null).getDescrizione().length();
	}
	
//	public static void main(String[] args) throws Exception {
//		List<MotivoRifiuto> m = Arrays.asList(MotivoRifiuto.MR_01,MotivoRifiuto.MR_02,MotivoRifiuto.MR_03);
//		
//		System.out.println(getLunghezzaResiduaDescrizione(m));
//		NotificaEC ec = new NotificaEC();
//		ec.setDescrizione("");
//		ec.setMotivoRifiuto(m);
//		System.out.println(getLunghezzaRealeDescrizione(ec));
//	}
//
	public void invia(NotificaEC esito, IdUtente idUtente) throws Exception {
		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			connection.setAutoCommit(false);
			this.invia(new NotificaEsitoCommittenteConverter(esito, idUtente), connection);
			connection.commit();
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
			throw new Exception("Impossibile inviare la Notifica di Esito Committente, in quanto e' stata gia' ricevuta una Notifica di Decorrenza Termini per la fattura con identificativo SdI ["+notificaEsitoCommittente.getIdFattura().getIdentificativoSdi()+"] e posizione ["+notificaEsitoCommittente.getIdFattura().getPosizione()+"]");
		}

		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(fattura.getCodiceDestinatario());

		IdUtente idUtente = converter.getIdUtente();


		//check utente appartenente a quel dipartimento
		if(!utenteBD.belongsTo(idUtente, idDipartimento)) {
			throw new Exception("L'utente ["+idUtente.getUsername()+"] non appartiene al dipartimento destinatario della fattura.");
		}

		if(!notificaEsitoCommittenteBD.canNotificaEsitoCommittenteBeSent(notificaEsitoCommittente.getIdFattura())) {
			throw new NotificaGiaInviataException("Impossibile inviare la Notifica di Esito Committente per la fattura con identificativo SdI ["+notificaEsitoCommittente.getIdFattura().getIdentificativoSdi()+"] e posizione ["+notificaEsitoCommittente.getIdFattura().getPosizione()+"] in quanto ne e' stata gia' inviata una");
		}

		notificaEsitoCommittenteBD.create(notificaEsitoCommittente);

		EsitoType esito = notificaEsitoCommittente.getEsito().equals(EsitoCommittenteType.EC01) ? EsitoType.IN_ELABORAZIONE_ACCETTATO : EsitoType.IN_ELABORAZIONE_RIFIUTATO;
		fatturaElettronicaBD.updateEsito(notificaEsitoCommittente.getIdFattura(), esito);
	}

}
