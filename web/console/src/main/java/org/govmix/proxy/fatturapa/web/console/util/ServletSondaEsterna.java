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
package org.govmix.proxy.fatturapa.web.console.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.govmix.proxy.fatturapa.web.commons.sonde.AbstractServletSonda;
import org.govmix.proxy.fatturapa.web.commons.sonde.ClientSondaInterna;
import org.govmix.proxy.fatturapa.web.commons.sonde.RisultatoSonda;
import org.govmix.proxy.fatturapa.web.commons.sonde.RisultatoSonda.STATO;

public class ServletSondaEsterna extends AbstractServletSonda {

	private static final long serialVersionUID = 1L;
	
	private List<ClientSondaInterna> lstSondeInterne;
	
	public ServletSondaEsterna() throws Exception {
		super();
		
		this.lstSondeInterne = new ArrayList<ClientSondaInterna>();
		ConsoleProperties props = ConsoleProperties.getInstance(this.log);
		this.lstSondeInterne.add(new ClientSondaInterna(props.getProxyPccSondaUrl(),props.getProxyPccSondaUser(),props.getProxyPccSondaPassword(), "API_PCC", this.log));
		this.lstSondeInterne.add(new ClientSondaInterna(props.getProxyFatturaPASondaUrl(),props.getProxyFatturaPASondaUser(),props.getProxyFatturaPASondaPassword(), "API", this.log));
		this.lstSondeInterne.add(new ClientSondaInterna(props.getTimersProxyPccSondaUrl(), props.getTimersProxyPccSondaUser(), props.getTimersProxyPccSondaPassword(), "TIMERS_PCC", this.log));
		this.lstSondeInterne.add(new ClientSondaInterna(props.getTimersProxyFatturaPASondaUrl(), props.getTimersProxyFatturaPASondaUser(), props.getTimersProxyFatturaPASondaPassword(), "TIMERS", this.log));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.log.info("Invocazione sonda esterna");
		RisultatoSonda result = sonda(req);
		List<RisultatoSonda> lst =new ArrayList<RisultatoSonda>();
		lst.add(result);

		if(result.getStato().equals(STATO.OK)) {
			for(ClientSondaInterna client: this.lstSondeInterne) {
				lst.add(client.invoke(req));
			}
		}

		RisultatoSonda gather = this.gather(lst);
		
		this.log.info("Invocazione sonda esterna Response ["+gather.getStato()+"]");
		resp.getOutputStream().write(gather.toString().getBytes());
		resp.getOutputStream().close();
	}

}
