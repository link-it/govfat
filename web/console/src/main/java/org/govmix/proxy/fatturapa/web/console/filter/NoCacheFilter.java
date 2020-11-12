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
package org.govmix.proxy.fatturapa.web.console.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.costanti.Costanti;
import org.govmix.proxy.fatturapa.web.console.servlet.FatturaElettronicaAttivaUploadServlet;
import org.openspcoop2.generic_project.web.utils.BrowserUtils;

public class NoCacheFilter implements Filter {


	private List<String> excludedPages = null;
	private Logger log = LoggerManager.getConsoleLogger();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		this.excludedPages = new ArrayList<String>();
		this.excludedPages.add("a4j");
		this.excludedPages.add("images");
		this.excludedPages.add("css");
		this.excludedPages.add("scripts");
		this.excludedPages.add("fileupload");
		this.excludedPages.add(FatturaElettronicaAttivaUploadServlet.FATTURA_ELETTRONICA_ATTIVA_UPLOAD_SERVLET_PATH);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponseWrapper response = new HttpServletResponseWrapper((HttpServletResponse) res);
		String requestPath = request.getRequestURI();

		boolean controlRequired = true;

		if(this.excludedPages.size() > 0)
			for (String page : this.excludedPages) {
				if(StringUtils.contains(requestPath, page)){
					controlRequired = false;
					break;
				}
			}
		else
			controlRequired = true;

		if (controlRequired) { // Skip JSF resources (CSS/JS/Images/etc)
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0); // Proxies.
		}
		
		String idRichiesta = UUID.randomUUID().toString();
		request.setAttribute(Costanti.UUID_INFO_PARAMETER_NAME, idRichiesta);

		//if(controlRequired) {
			this.log.debug("["+idRichiesta+"] Richiesta risorsa protetta ["+requestPath+"]");
			String uri = request.getScheme() + "://" +
					request.getServerName() + 
					("http".equals(request.getScheme()) && request.getServerPort() == 80 ||
					"https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
					request.getRequestURI() +
					(request.getQueryString() != null ? "?" + request.getQueryString() : "");
			this.log.debug("["+idRichiesta+"] Richiesta Url Esatta Invocata ["+uri+"]");
			this.log.debug("["+idRichiesta+"] HTTP-Method Richiesta: ["+request.getMethod()+"]");
			this.log.debug("["+idRichiesta+"] Content-Type Richiesta: ["+request.getContentType()+"]");
			this.log.debug("["+idRichiesta+"] Content-Length Richiesta: ["+request.getContentLength()+"]");
		//}

		// Analisi dello user agent
		String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");

		if(userAgent != null) {
			try{

				//log.info("Decodifica Browser da Header UserAgent ["+userAgent+"]");
				String info[] = BrowserUtils.getBrowserInfo(userAgent);
				
				request.setAttribute(Costanti.BROWSER_INFO_PARAMETER_NAME, info);
				String browsername = info[0];
				String browserversion = info[1];

				this.log.debug("["+idRichiesta+"] BrowserName ["+browsername+"] + Version ["+browserversion+"]");

				if(browsername.equalsIgnoreCase("MSIE") || browsername.equalsIgnoreCase("rv")|| browsername.equalsIgnoreCase("Trident")){
					//Imposto l'header http necessario per forzare la visualizzazione.
					((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=edge,chrome=1");

					// Risolvo anche il problema di ie9 che non visualizza il contenuto dei file css della libreria Richfaces.
					// Esso invia solo "text/css" all'interno dell' Accept header.
					// la classe HtmlRenderUtils lancia un eccezione poiche' non gestisce questo tipo di Accept
					// allora la soluzione e' una patch del codice oppure aggiornare il valore dell'header Accept per far si che non
					// venga sollevata l'eccezione: applico questa soluzione impostando "text/css, */*" al posto di "text/css".

					String accept = ((HttpServletRequest) request).getHeader("Accept");

					if ("text/css".equals(accept)) {

						chain.doFilter(new IE9HttpServletRequestWrapper((HttpServletRequest) request), response);

					}
				}
			}catch(Exception e){
				//log.info("Browser non riconosciuto.");
			}
		}



		chain.doFilter(request, response);

		this.log.debug("["+idRichiesta+"] Exit");
		this.log.debug("["+idRichiesta+"] StatusCode Response: ["+response.getSc()+"]");
		this.log.debug("["+idRichiesta+"] Content-Type Response: ["+response.getContentType()+"]");
	}



	@Override
	public void destroy() {
	}

	// Request Wrapper. Catches the getHeader for Accept. When it is text/css we will return simply "text/css, */*"

	private class IE9HttpServletRequestWrapper extends HttpServletRequestWrapper {
		public IE9HttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override

		public String getHeader(String name) {
			String header = super.getHeader(name);

			if ("text/css".equalsIgnoreCase(header)) {
				header = "text/css, */*";
			}

			return header;
		}
	}
	
	// Response Wrapper. Catches the status code

	private class HttpServletResponseWrapper extends javax.servlet.http.HttpServletResponseWrapper{
		
		private int sc = SC_OK;
		
		public HttpServletResponseWrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public void setStatus(int sc) {
			this.sc = sc;
			super.setStatus(sc);
		}
		
		@Override
		public void setStatus(int sc, String sm) {
			this.sc = sc;
			super.setStatus(sc, sm);
		}
		
		@Override
		public void sendError(int sc) throws IOException {
			this.sc = sc;
			super.sendError(sc);
		}
		
		@Override
		public void sendRedirect(String location) throws IOException {
			this.sc = SC_MOVED_TEMPORARILY;
			super.sendRedirect(location);
		}
		
		@Override
		public void reset() {
		    super.reset();
		    this.sc = SC_OK;
		}
		
		public int getSc() {
			return sc;
		}
		
	}

}
