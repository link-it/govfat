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
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.govmix.pcc.fatture.FattureWS;
import org.govmix.pcc.fatture.FattureWS_Service;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdOperazione;
import org.govmix.proxy.fatturapa.orm.IdProtocollo;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.PccDipartimentoOperazione;
import org.govmix.proxy.fatturapa.orm.PccOperazione;
import org.govmix.proxy.fatturapa.orm.PccUtenteOperazione;
import org.govmix.proxy.fatturapa.orm.Protocollo;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.mbean.LoginMBean;
import org.openspcoop2.generic_project.web.form.CostantiForm;
import org.openspcoop2.generic_project.web.impl.jsf1.input.SelectItem;
import org.openspcoop2.generic_project.web.input.FormField;

/**
 * Utils classe di utilies.
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author: pintori $
 *
 */
public class Utils extends org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils{

	public static String getSistemaRichiedente(){
		try {
			return ConsoleProperties.getInstance(LoggerManager.getConsoleLogger()).getSistemaRichiedente();
		} catch (Exception e) {
			
		}
		
		return null;
	}
	
	public static Number getNumber(Object o){
		if(o == null)
			return null;
		
		Number n = null;
		if(o instanceof String){
			NumberFormat numberFormat = NumberFormat.getInstance(Utils.getCurrentLocale());

			try {
				n= numberFormat.parse((String) o);
			} catch (ParseException e) {
				
			}  
//			BigDecimal bd = new BigDecimal((String) o);
//			n = bd;
		}
		
		if(o instanceof Number){
			n = (Number) o;
		}
		
		return n;	
	}
	
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
	
	public static Map<String,Ente> getMapEntiLoggedUtente() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				return lb.getMapEnti();
			}
		}
		return null;
	}
	
//	public static Ente getEnte() {
//		FacesContext fc = FacesContext.getCurrentInstance();
//		if(fc!= null){
//			ExternalContext ec = fc.getExternalContext();
//			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");
//
//			if(lb!= null && lb.getIsLoggedIn()){
//				return lb.getEnte();
//			}
//		}
//		return null;
//	}
//	
//	public static IdEnte getIdEnte() {
//		FacesContext fc = FacesContext.getCurrentInstance();
//		if(fc!= null){
//			ExternalContext ec = fc.getExternalContext();
//			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");
//
//			if(lb!= null && lb.getIsLoggedIn()){
//				IdEnte idEnte = new IdEnte();
//				
//				idEnte.setNome(lb.getEnte().getNome());
//				
//				return idEnte;
//			}
//		}
//		return null;
//	}
	
	
	public static Protocollo getProtocollo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				return lb.getProtocollo();
			}
		}
		return null;
	}
	
	public static IdProtocollo getIdProtocollo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc!= null){
			ExternalContext ec = fc.getExternalContext();
			LoginMBean lb = (LoginMBean)ec.getSessionMap().get("loginBean");

			if(lb!= null && lb.getIsLoggedIn()){
				IdProtocollo idProtocollo = new IdProtocollo();
				
				idProtocollo.setNome(lb.getProtocollo().getNome());
				
				return idProtocollo;
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
	
	public static String getAbilitataAsLabel(boolean abilitata){
		return getBooleanAsLabel(abilitata, "commons.label.abilitata","commons.label.nonAbilitata");
	}
	
	public static String getAbilitatoAsLabel(boolean abilitato){
		return getBooleanAsLabel(abilitato, "commons.label.abilitato","commons.label.nonAbilitato");
	}
	
	public static String getBooleanAsLabel(boolean flag, String yesPropertyName, String noPropertyName ){
		if(flag)
			return getInstance().getMessageFromResourceBundle(yesPropertyName);
		else 
			return getInstance().getMessageFromResourceBundle(noPropertyName);
	}
	
	public static void impostaValoreProprietaPCCUtente(List<PccOperazione> listaProprietaUtente,
			List<PccUtenteOperazione> listaProprietaAbilitate,NomePccOperazioneType nomeProperty, org.openspcoop2.generic_project.web.output.Text proprietaPCC) {
		proprietaPCC.setRendered(false); 
		PccOperazione operazione =  getOperazioneByName(nomeProperty, listaProprietaUtente);
		if(operazione != null){
			
			proprietaPCC.setLabel(operazione.getLabel());
			
			PccUtenteOperazione opDip =  getProprietaUtenteAbilitatoByOperazione(operazione, listaProprietaAbilitate);
			
			boolean abilitato = opDip != null;
			
			proprietaPCC.setValue(Utils.getAbilitatoAsLabel(abilitato));
			
			proprietaPCC.setRendered(true); 
		}
	}
	
	public static PccUtenteOperazione getProprietaUtenteAbilitatoByOperazione(PccOperazione operazione,  List<PccUtenteOperazione> listaProprietaAbilitate){
		for (PccUtenteOperazione pccOperazione : listaProprietaAbilitate) {
			if(pccOperazione.getIdOperazione().getNome().equals(operazione.getNome()))
				return pccOperazione;
		}
		
		return null;
	}
	
	public static void impostaValoreProprietaPCCUtenteForm(List<PccOperazione> listaProprietaUtente,
			List<PccUtenteOperazione> listaProprietaAbilitate,NomePccOperazioneType nomeProperty, FormField<String> proprietaPCC, boolean setDefaultValue) {
		proprietaPCC.setRendered(false);  
		PccOperazione operazione =  getOperazioneByName(nomeProperty, listaProprietaUtente);
		if(operazione != null){
			proprietaPCC.setLabel(operazione.getLabel());
			proprietaPCC.setRendered(true);
			
			PccUtenteOperazione opDip =  getProprietaUtenteAbilitatoByOperazione(operazione, listaProprietaAbilitate);
			
			String value = opDip != null ? "commons.label.abilitata": "commons.label.nonAbilitata";

			if(setDefaultValue)
				proprietaPCC.setDefaultValue(value);
			else 
				proprietaPCC.setValue(value);
		}
	}
	
	public static void impostaValoreProprietaPCCDipartimento(List<PccOperazione> listaProprietaConsentiteAiDipartimenti,
			List<PccDipartimentoOperazione> listaProprietaAbilitate,NomePccOperazioneType nomeProperty, org.openspcoop2.generic_project.web.output.Text proprietaPCC) {
		proprietaPCC.setRendered(false); 
		PccOperazione operazione =  getOperazioneByName(nomeProperty, listaProprietaConsentiteAiDipartimenti);
		if(operazione != null){
			
			proprietaPCC.setLabel(operazione.getLabel()); 
			
			PccDipartimentoOperazione opDip =  getProprietaDipartimentoAbilitatoByOperazione(operazione, listaProprietaAbilitate);
			
			boolean abilitato = opDip != null;
			
			proprietaPCC.setValue(Utils.getAbilitatoAsLabel(abilitato));
			
			proprietaPCC.setRendered(true); 
		}
	}
	
	public static PccDipartimentoOperazione getProprietaDipartimentoAbilitatoByOperazione(PccOperazione operazione,  List<PccDipartimentoOperazione> listaProprietaAbilitate){
		for (PccDipartimentoOperazione pccOperazione : listaProprietaAbilitate) {
			if(pccOperazione.getIdOperazione().getNome().equals(operazione.getNome()))
				return pccOperazione;
		}
		
		return null;
	}
	
	
	public static PccOperazione getOperazioneByName(NomePccOperazioneType name, List<PccOperazione> listaProprietaConsentiteAiDipartimenti){
		for (PccOperazione pccOperazione : listaProprietaConsentiteAiDipartimenti) {
			if(pccOperazione.getNome().equals(name))
				return pccOperazione;
		}
		
		return null;
	}
	
	public static void impostaValoreProprietaPCCDipartimentoForm(List<PccOperazione> listaProprietaConsentiteAiDipartimenti,
			List<PccDipartimentoOperazione> listaProprietaAbilitate, NomePccOperazioneType nomeProperty, FormField<String> proprietaPCC, boolean setDefaultValue) {
		proprietaPCC.setRendered(false); 
		PccOperazione operazione =  getOperazioneByName(nomeProperty, listaProprietaConsentiteAiDipartimenti);
		if(operazione != null){
			proprietaPCC.setLabel(operazione.getLabel());
			proprietaPCC.setRendered(true);
			
			PccDipartimentoOperazione opDip =  getProprietaDipartimentoAbilitatoByOperazione(operazione, listaProprietaAbilitate);
			
			String value = opDip != null ? "commons.label.abilitata": "commons.label.nonAbilitata";

			if(setDefaultValue)
				proprietaPCC.setDefaultValue(value);
			else 
				proprietaPCC.setValue(value);
		}
	}
	
	public static void addSceltaUtente(List<PccUtenteOperazione> list,IdUtente idUtente, NomePccOperazioneType nomeOperazione, FormField<String> proprietaPCC){
		PccUtenteOperazione operazione = null;
		
		String value = proprietaPCC.getValue();
		
		if(value!= null){
			boolean scelta = value.equals("commons.label.abilitata");
			
			if(scelta){
				IdOperazione idOperazione = new IdOperazione();
				idOperazione.setNome(nomeOperazione);
				
				operazione = new  PccUtenteOperazione();
				operazione.setIdOperazione(idOperazione);
				operazione.setIdUtente(idUtente);
				
				list.add(operazione);
			}
				
		}
	}
	
	public static void addSceltaUnitaOrganizzativa(List<PccDipartimentoOperazione> list, IdDipartimento idDipartimento, NomePccOperazioneType nomeOperazione, FormField<String> proprietaPCC){
		PccDipartimentoOperazione operazione = null;
		
		String value = proprietaPCC.getValue();
		
		if(value!= null){
			boolean scelta = value.equals("commons.label.abilitata");
			
			if(scelta){
				IdOperazione idOperazione = new IdOperazione();
				idOperazione.setNome(nomeOperazione);
				
				operazione = new  PccDipartimentoOperazione();
				operazione.setIdOperazione(idOperazione);
				operazione.setIdDipartimento(idDipartimento);
				
				list.add(operazione);
			}
				
		}
	}
	
	
	public static SelectItem getNonSelezionatoOption(){
		return new SelectItem(CostantiForm.NON_SELEZIONATO, CostantiForm.NON_SELEZIONATO);
	}
	
	public static SelectItem getNoOption(String label){
		return new SelectItem(Boolean.toString(false),label);
	}
	
	public static SelectItem getSiOption(String label){
		return new SelectItem(Boolean.toString(true),label);
	}
	
	public static SelectItem getSelectItem(String value,String label){
		return new SelectItem(value,label); 
	}
	
	public static String generaIdentificativoTransazionePA(){
		UUID randomUUID = UUID.randomUUID();
		
		return randomUUID.toString().replace("-", ""); 
	}
	
	public static String generaIdentificativoImporto(){
		UUID randomUUID = UUID.randomUUID();
		
		return randomUUID.toString().replace("-", ""); 
	}
	public static final String HEADER_PRINCIPAL = "X-ProxyFatturaPA-Principal"; 

	@SuppressWarnings("unchecked")
	public static FattureWS creaClientProxyPcc(ConsoleProperties properties,Logger log){
		FattureWS clientProxy = null;
		FattureWS_Service service = new FattureWS_Service(Utils.class.getResource("classpath:/wsdl/PCC_WSFATT_v2.0.wsdl")); 
		clientProxy = service.getFattureWSSOAP11Port();

		((BindingProvider)clientProxy).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,  properties.getProxyPccWsFattureUrl());

		((BindingProvider)clientProxy).getRequestContext().put("schema-validation-enabled", "true"); 
		if(properties.getProxyPccWsFattureUsername() != null && !properties.getProxyPccWsFattureUsername().isEmpty()) {
			((BindingProvider)clientProxy).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, properties.getProxyPccWsFattureUsername());
			((BindingProvider)clientProxy).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, properties.getProxyPccWsFatturePassword());
		}
		
		 Map<String, List<String>> headers = (Map<String, List<String>>) ((BindingProvider)clientProxy).getRequestContext().get(MessageContext.HTTP_REQUEST_HEADERS);
		 
		 if(headers == null) {
			 headers = new TreeMap<String, List<String>>();
		 }
		 
		 //Header Autenticazione.		 
		 headers.put(HEADER_PRINCIPAL, Arrays.asList(properties.getProxyPccWsFattureUsername()));

		 ((BindingProvider)clientProxy).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);

		return clientProxy;
	}
	
	public static boolean isDataScadenzaSconosciuta(Date dataScadenza) {
		Date d = new Date(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(dataScadenza).equals(sdf.format(d));
	}
	
}
