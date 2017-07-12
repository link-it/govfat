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
package org.govmix.proxy.fatturapa.web.commons.sonde;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSondaBatch extends AbstractServletSonda {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServletSondaBatch() throws Exception {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try {
			this.log.debug("Stato Servizi");
			RisultatoSonda result = this.gather(Sonda.getInstance().getStatoBatch(this.log));
			this.log.debug("Stato Servizi " + result.getStato());
			resp.getOutputStream().write(result.toString().getBytes());
			resp.getOutputStream().close();
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
}
