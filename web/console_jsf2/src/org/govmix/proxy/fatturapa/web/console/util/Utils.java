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
package org.govmix.proxy.fatturapa.web.console.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.govmix.proxy.fatturapa.Dipartimento;
import org.govmix.proxy.fatturapa.Ente;
import org.govmix.proxy.fatturapa.IdEnte;
import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.web.console.mbean.LoginMBean;

/**
 * Utils classe di utilies.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class Utils extends org.openspcoop2.generic_project.web.impl.jsf2.utils.Utils{

	public static Utente getLoggedUtente() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				return lb.getLoggedUtente();
			}
		}
		return null;
	}
	
	public static LoginMBean getLoginBean() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");
				return lb;
		}
		return null;
	}
	
	public static List<Dipartimento> getListaDipartimentiLoggedUtente() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				return lb.getListDipartimenti();
			}
		}
		return null;
	}
	
	public static Ente getEnte() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				return lb.getEnte();
			}
		}
		return null;
	}
	
	public static IdEnte getIdEnte() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				IdEnte idEnte = new IdEnte();
				
				idEnte.setNome(lb.getEnte().getNome());
				
				return idEnte;
			}
		}
		return null;
	}
	
	public static Locale getCurrentLocale() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				return lb.getCurrentLocal();
			}
		}
		return null;
	}
	
	public static void copy(InputStream in, OutputStream out) 
			throws IOException {

		// do not allow other threads to read from the
		// input or write to the output while copying is
		// taking place

		synchronized (in) {
			synchronized (out) {

				byte[] buffer = new byte[256];
				while (true) {
					int bytesRead = in.read(buffer);
					if (bytesRead == -1) break;
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	}
	
}
