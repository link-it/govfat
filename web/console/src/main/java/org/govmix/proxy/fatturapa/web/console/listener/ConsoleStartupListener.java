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
package org.govmix.proxy.fatturapa.web.console.listener;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.web.listener.ApplicationStartupListener;

public class ConsoleStartupListener extends ApplicationStartupListener {
	static{
		 System.setProperty("java.awt.headless", "true");
	}

	private static Logger log = Logger.getLogger(ConsoleStartupListener.class); 

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		super.contextInitialized(evt);

		InputStream is = null;
		try{
			ConsoleStartupListener.log.debug("Init FatturaPA WebConsole in corso...");

			String logPath = this.getLoggerProperties();
			if(!logPath.startsWith("/"))
				logPath = "/".concat(logPath);

			is = ConsoleStartupListener.class
					.getResourceAsStream(logPath);
			Properties prop = new Properties();
			prop.load(is);

			// inizializzo il logger
			PropertyConfigurator.configure(prop);


			ConsoleStartupListener.log.debug("Init FatturaPA WebConsole completato.");
			ConsoleStartupListener.log.info("Info versione: " + CommonsProperties.getInstance(ConsoleStartupListener.log).getInfoVersione());
			
			ConsoleStartupListener.log.debug("Check Graphics Environment: is HeadeLess ["+java.awt.GraphicsEnvironment.isHeadless()+"]");
			
			ConsoleStartupListener.log.debug("Elenco Nomi Font disponibili: " + Arrays.asList(GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
			
			
//			log.debug("Elenco Font disponibili: " + Arrays.asList(GraphicsEnvironment
//                    .getLocalGraphicsEnvironment().getAllFonts()));
			
		}catch(Throwable e ){
			ConsoleStartupListener.log.error(
					//					throw new ServletException(
					"Errore durante il caricamento del file di logging "
					+ this.getLoggerProperties());

		}finally{
			try{
				if(is!=null)
					is.close();
			}catch(Exception er){}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if(ConsoleStartupListener.log!=null)
			ConsoleStartupListener.log.info("Undeploy FatturaPA WebConsole in corso...");

		if(ConsoleStartupListener.log!=null)
			ConsoleStartupListener.log.info("Undeploy FatturaPA WebConsole effettuato.");
		
		if(ConsoleStartupListener.log!=null)
			try {ConsoleStartupListener.log.info("Info versione: " + CommonsProperties.getInstance(ConsoleStartupListener.log).getInfoVersione());} catch (Exception e) {}


	}
}
