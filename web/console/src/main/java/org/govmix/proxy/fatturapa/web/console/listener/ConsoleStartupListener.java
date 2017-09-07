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
package org.govmix.proxy.fatturapa.web.console.listener;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties;
import org.openspcoop2.generic_project.web.listener.ApplicationStartupListener;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.slf4j.Logger;

public class ConsoleStartupListener extends ApplicationStartupListener {
	static{
		System.setProperty("java.awt.headless", "true");
	}

	private static Logger log = LoggerWrapperFactory.getLogger(ConsoleStartupListener.class); 

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		super.contextInitialized(evt);

		InputStream is = null;
		try{
			ServletContext servletContext = evt.getServletContext();
			ConsoleStartupListener.log.debug("Init FatturaPA WebConsole in corso...");


			String logPath = this.getLoggerProperties();
			if(!logPath.startsWith("/"))
				logPath = "/".concat(logPath);

			is = ConsoleStartupListener.class
					.getResourceAsStream(logPath);
			Properties prop = new Properties();
			prop.load(is);

			// inizializzo il logger
			LoggerWrapperFactory.setLogConfiguration(prop);
			ConsoleStartupListener.log = LoggerWrapperFactory.getLogger(ConsoleStartupListener.class);

			ConsoleStartupListener.log.debug("Init FatturaPA WebConsole completato.");

			ConsoleStartupListener.log.debug("Check Graphics Environment: is HeadeLess ["+java.awt.GraphicsEnvironment.isHeadless()+"]");

			ConsoleStartupListener.log.debug("Elenco Nomi Font disponibili: " + Arrays.asList(GraphicsEnvironment
					.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));


			//			log.debug("Elenco Font disponibili: " + Arrays.asList(GraphicsEnvironment
			//                    .getLocalGraphicsEnvironment().getAllFonts()));

			InputStream isFont = null;

			try{
				String fontFileName = ConsoleProperties.getInstance(log).getConsoleFont();

				if(StringUtils.isNotEmpty(fontFileName)) {
					log.debug("Caricato Font dal file: ["+fontFileName+"] in corso... ");

					isFont = servletContext.getResourceAsStream("/fonts/"+ fontFileName);

					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					Font fontCaricato = Font.createFont(Font.PLAIN, isFont);

					log.debug("Caricato Font: ["+fontCaricato.getName()+"] FontName: ["+fontCaricato.getFontName()+"] FontFamily: ["+fontCaricato.getFamily()+"] FontStyle: ["+fontCaricato.getStyle()+"]");

					ge.registerFont(fontCaricato);

					log.debug("Check Graphics Environment: is HeadeLess ["+java.awt.GraphicsEnvironment.isHeadless()+"]");

					log.debug("Elenco Nomi Font disponibili: " + Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));

					FatturaElettronicaBean richiestaBean = new FatturaElettronicaBean();
					richiestaBean.getFactory().setFontName(fontCaricato.getFamily());
					richiestaBean.getFactory().setFontStyle(fontCaricato.getStyle());
					richiestaBean.getFactory().setFontSize(12);

					log.debug("Caricato Font dal file: ["+fontFileName+"] completato.");
				}
			}catch (Exception e) {
				log.error(e.getMessage(),e);
			} finally {
				if(isFont != null){
					try {   isFont.close(); } catch (IOException e) {       }
				}
			}


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

	}
}
