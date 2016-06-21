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
package org.govmix.proxy.fatturapa.web.api.timers;

import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdDipartimento;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.Registro;
import org.govmix.proxy.fatturapa.web.api.business.consegnaFattura.Endpoint;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.EnteBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.RegistroBD;

public class EndpointSelector {

	private EnteBD enteBD;
	private DipartimentoBD dipartimentoBD;
	private RegistroBD registroBD;
	private Logger log;
	
	public EndpointSelector(Logger log) throws Exception {
		this.enteBD = new EnteBD(log);
		this.dipartimentoBD = new DipartimentoBD(log);
		this.registroBD = new RegistroBD(log);
		this.log = log;
	}
	public Endpoint findEndpoint(FatturaElettronica fattura) throws Exception {
		this.log.debug("Cerco endpoint per fattura Id-SdI["+fattura.getIdentificativoSdi()+"] posizione["+fattura.getPosizione()+"] con destinatario ["+fattura.getCodiceDestinatario()+"]");
		return findEndpoint(fattura.getCodiceDestinatario());
	}
	
	public Endpoint findEndpoint(LottoFatture lotto) throws Exception {
		this.log.debug("Cerco endpoint per lotto con Id-SdI["+lotto.getIdentificativoSdi()+"] con destinatario ["+lotto.getCodiceDestinatario()+"]");
		return findEndpoint(lotto.getCodiceDestinatario());
	}
	
	private Endpoint findEndpoint(String codiceDestinatario) throws Exception {

		Endpoint endpoint = new Endpoint();
		IdDipartimento id = new IdDipartimento();
		id.setCodice(codiceDestinatario);

		Dipartimento dipartimento = this.dipartimentoBD.get(id);
		Ente ente = this.enteBD.get(dipartimento.getEnte());
		
		this.log.debug("Trovato ente ["+ente.getNome()+"] con endpoint consegna fattura ["+ente.getEndpoint()+"] endpoint consegna lotto ["+ente.getEndpointConsegnaLotto()+"] endpoint richiesta protocollo ["+ente.getEndpointRichiediProtocollo()+"] per codice destinatario ["+codiceDestinatario+"]");
		

		endpoint.setEndpoint(ente.getEndpoint());
		endpoint.setEndpointAssociazioneLotto(ente.getEndpointRichiediProtocollo());
		endpoint.setEndpointConsegnaLotto(ente.getEndpointConsegnaLotto());

		List<Registro> lstRegistri = this.registroBD.findByIdEnte(dipartimento.getEnte());

		if(lstRegistri.size() > 0 && dipartimento.getRegistro() != null) {
			for(Registro registro : lstRegistri) {
				if(registro.getNome().equals(dipartimento.getRegistro().getNome())) {
					this.log.debug("Trovato Registro ["+registro.getNome()+"] per ente ["+ente.getNome()+"] e dipartimento ["+dipartimento.getCodice()+"]");
					endpoint.setUsername(registro.getUsername());
					endpoint.setPassword(registro.getPassword());
				}
			}
		}

		return endpoint;
	}

}
