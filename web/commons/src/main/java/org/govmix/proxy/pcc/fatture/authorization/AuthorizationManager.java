/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.pcc.fatture.authorization;

import org.apache.log4j.Logger;
import org.govmix.pcc.fatture.AuthorizationFault;
import org.govmix.pcc.fatture.WSAuthorizationFault;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdOperazione;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.EnteBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccAutorizzazioneBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.pcc.fatture.utils.PccProperties;
import org.openspcoop2.generic_project.exception.NotFoundException;

public class AuthorizationManager {

	
	private PccAutorizzazioneBD authBD;
	private UtenteBD utenteBD;
	private DipartimentoBD dipartimentoBD;
	private EnteBD enteBD;
	private FatturaElettronicaBD fatturaBD;
	private Logger log;
	public AuthorizationManager(Logger log) throws Exception {
		this.log = log;
		this.authBD = new PccAutorizzazioneBD(log);
		this.utenteBD = new UtenteBD(log);
		this.fatturaBD = new FatturaElettronicaBD(log);
		this.dipartimentoBD = new DipartimentoBD(log);
		this.enteBD = new EnteBD(log);
	}
	
	public AuthorizationBeanResponse authorizeByIdFattura(AuthorizationBeanRequest bean) throws Exception {
		
		IdUtente idUtente = new IdUtente();

		IdUtente idUtentePrincipal = new IdUtente();
		idUtentePrincipal.setUsername(bean.getPrincipal());

		if(!this.utenteBD.exists(idUtentePrincipal)) {
			throw getWSAuthorizationFault("Utente applicativo["+idUtentePrincipal.getUsername()+"] non censito");
		}

		Utente utente = this.utenteBD.findByUsername(idUtentePrincipal.getUsername());
		Utente utenteRichiedente = null;
		if(!utente.isEsterno()) {
			throw getWSAuthorizationFault("Utente applicativo["+idUtentePrincipal.getUsername()+"] deve essere un utente di tipo esterno");
		}

		if(utente.getSistema().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
			idUtente.setUsername(bean.getUtenteRichiedente());
			
			if(!this.utenteBD.exists(idUtente)) {
				throw getWSAuthorizationFault("Utente richiedente["+idUtente.getUsername()+"] non censito");
			}
			utenteRichiedente = this.utenteBD.findByUsername(idUtente.getUsername());

		} else {
			idUtente.setUsername(bean.getPrincipal());
			utenteRichiedente = utente;
		}
		
		
		
		FatturaElettronica fattura = null;
		IdFattura idFattura = null;
		try {
			fattura = getFattura(bean);
			idFattura = this.fatturaBD.convertToId(fattura);
		} catch(NotFoundException e) {
			throw getWSAuthorizationFault("Fattura non presente nel sistema");
		}

		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(fattura.getCodiceDestinatario());
		Dipartimento dip = this.dipartimentoBD.get(idDipartimento); 

		Ente ente = this.enteBD.get(dip.getEnte());
		
		if(ente.getIdPccAmministrazione() == null) {
			throw getWSAuthorizationFault("Dipartimento ["+idDipartimento.getCodice()+"] appartiene all'ente["+ente.getNome()+"] non autorizzato ad eseguire alcuna operazione PCC");
		}

		IdOperazione operazione = new IdOperazione();
		operazione.setNome(bean.getOperazione());
		
		if(!this.authBD.isAuthorized(idDipartimento, operazione)) {
			throw getWSAuthorizationFault("Dipartimento ["+idDipartimento.getCodice()+"] non e' autorizzato ad eseguire l'operazione ["+operazione.getNome()+"]");
		}


		if(!utenteRichiedente.getRole().equals(UserRole.ADMIN)) {
			if(!this.utenteBD.belongsTo(idUtente, idDipartimento, true)) {
				throw getWSAuthorizationFault("Utente richiedente["+idUtente.getUsername()+"] non appartenente al dipartimento ["+idDipartimento.getCodice()+"] destinatario della fattura ["+bean.getIdentificativoSdi()+"]");
			}

			if(!this.authBD.isAuthorized(idUtente, operazione)) {
				throw getWSAuthorizationFault("Utente richiedente["+idUtente.getUsername()+"] non e' autorizzato ad eseguire l'operazione ["+operazione.getNome()+"]");
			}
		}
		
		AuthorizationBeanResponse response = new AuthorizationBeanResponse();
		response.setCfTrasmittente(ente.getCfAuth());
		response.setIdPccAmministrazione(ente.getIdPccAmministrazione());
		response.setCodiceDipartimento(idDipartimento.getCodice());
		response.setSistemaRichiedente(utente.getSistema());
		response.setUtenteRichiedente(bean.getUtenteRichiedente());
		response.setIdFattura(fattura.getId());
		response.setIdLogicoFattura(idFattura);
		return response;
	}
	
	public FatturaElettronica getFattura(AuthorizationBeanRequest bean) throws Exception {
		try {
			
			if(bean.getIdLogicoFattura() != null) {
				return this.fatturaBD.get(bean.getIdLogicoFattura());
			} else if(bean.getIdFattura() != null) {
				return this.fatturaBD.getById(bean.getIdFattura());
			} else if(bean.getIdentificativoPcc() != null) {
				return this.fatturaBD.findByIdPcc(bean.getIdentificativoPcc());
			} else if(bean.getIdentificativoSdi() != null) {
				return this.fatturaBD.findByIdSdiNumero(bean.getIdentificativoSdi().getLottoSDI().intValue(), bean.getIdentificativoSdi().getNumeroFattura());
			} else if(bean.getIdentificativoGenerale() != null) {
				return this.fatturaBD.findByIdFiscaleNumeroDataImporto(bean.getIdentificativoGenerale().getIdFiscaleIvaFornitore(), bean.getIdentificativoGenerale().getNumeroFattura(), bean.getIdentificativoGenerale().getDataEmissione(), bean.getIdentificativoGenerale().getImportoTotaleDocumento().doubleValue());
			} else {
				throw getWSAuthorizationFault("Fattura non identificabile");
			}
	
		} catch(NotFoundException e) {
			throw getWSAuthorizationFault("Fattura non presente nel sistema");
		}

	}
	public AuthorizationBeanResponse authorizeByCodiceDestinatario(AuthorizationBeanRequest bean) throws Exception {
		
		IdUtente idUtente = new IdUtente();
		idUtente.setUsername(bean.getUtenteRichiedente());

		IdUtente idUtentePrincipal = new IdUtente();
		idUtentePrincipal.setUsername(bean.getPrincipal());

		if(!this.utenteBD.exists(idUtentePrincipal)) {
			throw getWSAuthorizationFault("Utente applicativo["+idUtentePrincipal.getUsername()+"] non censito");
		}

		Utente utente = this.utenteBD.findByUsername(idUtentePrincipal.getUsername());
		Utente utenteRichiedente = null;

		if(!utente.isEsterno()) {
			throw getWSAuthorizationFault("Utente applicativo["+idUtentePrincipal.getUsername()+"] deve essere un utente di tipo esterno");
		}

		if(utente.getSistema().equals(PccProperties.getInstance().getSistemaRichiedenteCruscotto())) {
			idUtente.setUsername(bean.getUtenteRichiedente());
			
			if(!this.utenteBD.exists(idUtente)) {
				throw getWSAuthorizationFault("Utente richiedente["+idUtente.getUsername()+"] non censito");
			}
			utenteRichiedente = this.utenteBD.findByUsername(idUtente.getUsername());

		} else {
			idUtente.setUsername(bean.getPrincipal());
			utenteRichiedente = utente;
		}
		
		
		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(bean.getCodiceDipartimento());
		Dipartimento dip = this.dipartimentoBD.get(idDipartimento); 
		
		Ente ente = this.enteBD.get(dip.getEnte());
		
		if(ente.getIdPccAmministrazione() == null) {
			throw getWSAuthorizationFault("Dipartimento ["+idDipartimento.getCodice()+"] appartiene all'ente["+ente.getNome()+"], non e' autorizzato ad eseguire alcuna operazione PCC");
		}

		IdOperazione operazione = new IdOperazione();
		operazione.setNome(bean.getOperazione());

		if(!this.authBD.isAuthorized(idDipartimento, operazione)) {
			throw getWSAuthorizationFault("Dipartimento ["+idDipartimento.getCodice()+"] non e' autorizzato ad eseguire l'operazione ["+operazione.getNome()+"]");
		}

		if(!utenteRichiedente.getRole().equals(UserRole.ADMIN)) {
			if(!this.utenteBD.belongsTo(idUtente, idDipartimento, true)) {
				throw getWSAuthorizationFault("Utente ["+idUtente.getUsername()+"] non appartenente al dipartimento ["+idDipartimento.getCodice()+"] destinatario della fattura ["+bean.getIdentificativoSdi()+"]");
			}
	
			if(!this.authBD.isAuthorized(idUtente, operazione)) {
				throw getWSAuthorizationFault("Utente ["+idUtente.getUsername()+"] non e' autorizzato ad eseguire l'operazione ["+operazione.getNome()+"]");
			}
		}
		
		AuthorizationBeanResponse response = new AuthorizationBeanResponse();
		response.setIdPccAmministrazione(ente.getIdPccAmministrazione());
		response.setCodiceDipartimento(idDipartimento.getCodice());
		response.setCfTrasmittente(ente.getCfAuth());
		response.setSistemaRichiedente(utente.getSistema());
		response.setUtenteRichiedente(bean.getUtenteRichiedente());
		return response;
	}

	private WSAuthorizationFault getWSAuthorizationFault(String detail) {
		this.log.error("Errore di autorizzazione: " +detail);
		AuthorizationFault authorizationFault = new AuthorizationFault();
		authorizationFault.setDetail(detail);
		WSAuthorizationFault fault = new WSAuthorizationFault("Errore di autorizzazione", authorizationFault);
		return fault;
	}

}
