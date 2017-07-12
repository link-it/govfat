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

package org.govmix.proxy.fatturapa.web.timers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



/**
 * Implementazione del punto di Startup dell'applicazione WEB
 * 
 * @author Giovanni Bussu
 * @author $Author: gbussu $
 * @version 
 */

public class TimerStartupServlet implements ServletContextListener {


	/**
	 * Startup dell'applicazione WEB
	 *
	 * @param sce Servlet Context Event
	 * 
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		TimerStartup.getInstance().contextInitialized(sce);
	}

	/**
	 * Undeploy dell'applicazione WEB
	 *
	 * @param sce Servlet Context Event
	 * 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		TimerStartup.getInstance().contextDestroyed(sce);
	}
}
