package org.govmix.proxy.fatturapa.web.console.listener;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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
			log.debug("Init FatturaPA WebConsole in corso...");
			

			String logPath = getLoggerProperties();
			if(!logPath.startsWith("/"))
				logPath = "/".concat(logPath);

			is = ConsoleStartupListener.class
					.getResourceAsStream(logPath);
			Properties prop = new Properties();
			prop.load(is);

			// inizializzo il logger
			PropertyConfigurator.configure(prop);


			log.debug("Init FatturaPA WebConsole completato.");
			
			log.debug("Check Graphics Environment: is HeadeLess ["+java.awt.GraphicsEnvironment.isHeadless()+"]");
			
			log.debug("Elenco Nomi Font disponibili: " + Arrays.asList(GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
			
			
			log.debug("Elenco Font disponibili: " + Arrays.asList(GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getAllFonts()));
			
		}catch(Throwable e ){
			ConsoleStartupListener.log.error(
					//					throw new ServletException(
					"Errore durante il caricamento del file di logging "
					+ getLoggerProperties());

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
