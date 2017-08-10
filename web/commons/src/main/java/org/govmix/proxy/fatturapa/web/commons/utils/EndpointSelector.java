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
package org.govmix.proxy.fatturapa.web.commons.utils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.Registro;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.ProtocolloBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.RegistroBD;

public class EndpointSelector {

	private ProtocolloBD protocolloBD;
	private DipartimentoBD dipartimentoBD;
	private RegistroBD registroBD;
	private Logger log;
	
	private Map<String, Endpoint> endpoints;
	public EndpointSelector(Logger log) throws Exception {
		this.protocolloBD = new ProtocolloBD(log);
		this.dipartimentoBD = new DipartimentoBD(log);
		this.registroBD = new RegistroBD(log);
		this.log = log;
		this.endpoints = new HashMap<String, Endpoint>();
	}
	
	public EndpointSelector(Logger log, Connection connection, boolean autoCommit) throws Exception {
		this.protocolloBD = new ProtocolloBD(log, connection, autoCommit);
		this.dipartimentoBD = new DipartimentoBD(log, connection, autoCommit);
		this.registroBD = new RegistroBD(log, connection, autoCommit);
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

		if(this.endpoints.containsKey(codiceDestinatario)) {
			return this.endpoints.get(codiceDestinatario);
		} else{
			Endpoint endpoint = new Endpoint();
			IdDipartimento id = new IdDipartimento();
			id.setCodice(codiceDestinatario);
		
			Dipartimento dipartimento = this.dipartimentoBD.get(id);
		
			Registro registro = this.registroBD.findById(dipartimento.getRegistro());
			Protocollo protocollo = this.protocolloBD.get(registro.getIdProtocollo());
			this.log.debug("Trovato ente ["+protocollo.getNome()+"] con endpoint["+protocollo.getEndpoint()+"]");
			
		
			endpoint.setEndpoint(protocollo.getEndpoint());
		
			endpoint.setUsername(registro.getUsername());
			endpoint.setPassword(registro.getPassword());
			
			this.endpoints.put(codiceDestinatario, endpoint);
			
			return endpoint;
		}
	}

}
