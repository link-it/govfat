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
package org.govmix.proxy.fatturapa.web.console.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.servlet.FatturaElettronicaAttivaUploadServlet;

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
		HttpServletResponse response = (HttpServletResponse) res;
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
		
		if(controlRequired) {
			this.log.debug("Richiesta risorsa protetta ["+requestPath+"]");
			String uri = request.getScheme() + "://" +
					request.getServerName() + 
		             ("http".equals(request.getScheme()) && request.getServerPort() == 80 ||
		             	"https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
		             request.getRequestURI() +
		            (request.getQueryString() != null ? "?" + request.getQueryString() : "");
			this.log.debug("Richiesta Url Esatta Invocata ["+uri+"]");
			this.log.debug("Content-Type Richiesta: ["+request.getContentType()+"]");
			this.log.debug("Content-Length Richiesta: ["+request.getContentLength()+"]");
		}
		
		chain.doFilter(request, response);
		
			this.log.debug("Exit");
	}



	@Override
	public void destroy() {
	}
}
