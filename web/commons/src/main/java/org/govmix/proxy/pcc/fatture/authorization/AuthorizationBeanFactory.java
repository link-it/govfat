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
package org.govmix.proxy.pcc.fatture.authorization;


import java.math.BigDecimal;

import org.govmix.pcc.fatture.DatiRichiestaDatiFatturaProxyTipo;
import org.govmix.pcc.fatture.DatiRichiestaOperazioneContabileProxyTipo;
import org.govmix.pcc.fatture.IdentificazioneSDITipo;
import org.govmix.pcc.fatture.TipoOperazioneTipo;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;

public class AuthorizationBeanFactory {

	public static AuthorizationBeanRequest getAuthorizationBeanByIdFattura(String principal, String utenteRichiedente, String idFattura, NomePccOperazioneType operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setIdentificativoPcc(idFattura);
		request.setOperazione(operazione);
		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanByIdFattura(String principal, String utenteRichiedente, Long idFattura, NomePccOperazioneType operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setIdFattura(idFattura);
		request.setOperazione(operazione);
		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanByIdFattura(String principal, String utenteRichiedente, IdFattura idFattura, NomePccOperazioneType operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setIdLogicoFattura(idFattura);
		request.setOperazione(operazione);
		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanByCodiceDestinatario(String principal, String utenteRichiedente, String codiceDestinatario, NomePccOperazioneType operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setCodiceDipartimento(codiceDestinatario);
		request.setOperazione(operazione);
		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanByIdPcc(String principal, String utenteRichiedente, String idPcc, NomePccOperazioneType operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setIdentificativoPcc(idPcc);
		request.setOperazione(operazione);
		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanByIdUtente(String principal, String utenteRichiedente, NomePccOperazioneType operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setOperazione(operazione);
		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanByIdFattura(String principal, String utenteRichiedente, DatiRichiestaDatiFatturaProxyTipo datiRichiesta, NomePccOperazioneType operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setIdentificativoPcc(datiRichiesta.getIdentificazionePCC());
		request.setIdentificativoSdi(datiRichiesta.getIdentificazioneSDI());
		request.setIdentificativoGenerale(datiRichiesta.getIdentificazioneGenerale());
		request.setOperazione(operazione);
		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanOperazioneContabileByIdFattura(String principal, String utenteRichiedente, DatiRichiestaOperazioneContabileProxyTipo datiRichiesta) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		request.setIdentificativoPcc(datiRichiesta.getIdentificazionePCC());
		request.setIdentificativoSdi(datiRichiesta.getIdentificazioneSDI());
		request.setIdentificativoGenerale(datiRichiesta.getIdentificazioneGenerale());
		request.setOperazione(getOperationName(datiRichiesta.getListaOperazione().getOperazione().getTipoOperazione()));

		return request;
	}

	public static AuthorizationBeanRequest getAuthorizationBeanOperazioneContabileByIdFattura(String principal, String utenteRichiedente, String identificativoSdi, String numero, TipoOperazioneTipo operazione) {
		AuthorizationBeanRequest request = new AuthorizationBeanRequest();
		request.setPrincipal(principal);
		request.setUtenteRichiedente(utenteRichiedente);
		IdentificazioneSDITipo idSdi = new IdentificazioneSDITipo();
		idSdi.setLottoSDI(new BigDecimal(identificativoSdi));
		idSdi.setNumeroFattura(numero);
		request.setIdentificativoSdi(idSdi);
		
		request.setOperazione(getOperationName(operazione));

		return request;
	}
	
	public static NomePccOperazioneType getOperationName(TipoOperazioneTipo operazione) {
		switch(operazione) {
		case CCS: return NomePccOperazioneType.OPERAZIONE_CONTABILE_CCS;
		case CO: return NomePccOperazioneType.OPERAZIONE_CONTABILE_CO;
		case CP:return NomePccOperazioneType.OPERAZIONE_CONTABILE_CP;
		case CS:return NomePccOperazioneType.OPERAZIONE_CONTABILE_CS;
		case RC:return NomePccOperazioneType.OPERAZIONE_CONTABILE_RC;
		case RF:return NomePccOperazioneType.OPERAZIONE_CONTABILE_RF;
		case SC:return NomePccOperazioneType.OPERAZIONE_CONTABILE_SC;
		case SP:return NomePccOperazioneType.OPERAZIONE_CONTABILE_SP;
		default: return null;
		}
		
	}

}
