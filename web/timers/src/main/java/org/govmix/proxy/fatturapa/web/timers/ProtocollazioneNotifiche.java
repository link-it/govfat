package org.govmix.proxy.fatturapa.web.timers;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.TracciaSdIBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.TracciaSdIFilter;
import org.govmix.proxy.fatturapa.web.commons.policies.IPolicyRispedizione;
import org.govmix.proxy.fatturapa.web.commons.policies.PolicyRispedizioneFactory;
import org.govmix.proxy.fatturapa.web.commons.utils.CostantiProtocollazione;
import org.govmix.proxy.fatturapa.web.commons.utils.Endpoint;
import org.govmix.proxy.fatturapa.web.commons.utils.EndpointSelector;

public class ProtocollazioneNotifiche implements IWorkFlow<TracciaSDI> {

	private Logger log;
	private int limit;
	private TracciaSdIBD tracciaSdiBD;
	private Date limitDate;
	private EndpointSelector endpointSelector;

	@Override
	public void init(Logger log, Connection connection, int limit) throws Exception {
		this.log = log;
		this.limit = limit;
		this.limitDate = new Date();
		this.tracciaSdiBD = new TracciaSdIBD(log, connection, true);
		this.endpointSelector = new EndpointSelector(log, connection, true);
	}

	@Override
	public long count() throws Exception {
		return this.tracciaSdiBD.count(this.newFilter());
	}

	private TracciaSdIFilter newFilter() {
		TracciaSdIFilter filter = this.tracciaSdiBD.newFilter();
		filter.setDataProssimaProtocollazioneMax(this.limitDate);
		filter.setStatoProtocollazioneLst(Arrays.asList(StatoProtocollazioneType.NON_PROTOCOLLATA,StatoProtocollazioneType.IN_RICONSEGNA));
		filter.setDaProtocollare(true);
		filter.setOffset(0);
		filter.setLimit(this.limit);
		return filter;
	}

	@Override
	public List<TracciaSDI> getNextLista() throws Exception {
		return this.tracciaSdiBD.findAll(this.newFilter());
	}

	@Override
	public void process(TracciaSDI tracciaSDI) throws Exception {
		StatoProtocollazioneType nextStatoOK = StatoProtocollazioneType.PROTOCOLLATA;
		StatoProtocollazioneType nextStatoKO = StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE;
		this.log.debug("Elaboro la traccia con id ["+tracciaSDI.getId()+"]");
		
		Endpoint endpoint = this.endpointSelector.findEndpoint(tracciaSDI);
		
		URL urlOriginale = endpoint.getEndpoint().toURL();
		
		this.log.debug("Spedisco la traccia ["+tracciaSDI.getId()+"] all'endpoint ["+urlOriginale.toString()+"]");
		
		URL url = new URL(urlOriginale.toString() + "/protocollazioneRicevute");

		URLConnection conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;
//		boolean esitoPositivo = false;
		int responseCode = -1;
		String response = null;
		try{
			httpConn.setRequestProperty(CostantiProtocollazione.IDENTIFICATIVO_SDI_HEADER_PARAM, ""+tracciaSDI.getIdentificativoSdi());
			
			if(tracciaSDI.getPosizione() != null)
				httpConn.setRequestProperty(CostantiProtocollazione.POSIZIONE_HEADER_PARAM, ""+ tracciaSDI.getPosizione());

			httpConn.setRequestProperty(CostantiProtocollazione.NOME_FILE_HEADER_PARAM, ""+tracciaSDI.getNomeFile());
			httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_HEADER_PARAM, tracciaSDI.getCodiceDipartimento());
			httpConn.setRequestProperty(CostantiProtocollazione.TIPO_RICEVUTA_HEADER_PARAM, tracciaSDI.getTipoComunicazione().toString());
			
			httpConn.setRequestProperty("Content-Type", tracciaSDI.getContentType());
			

			if(endpoint.getUsername() != null && endpoint.getPassword()!= null) {
				String auth = endpoint.getUsername() + ":" + endpoint.getPassword(); 
				String authentication = "Basic " + Base64.encode(auth.getBytes());

				httpConn.setRequestProperty("Authorization", authentication);
			}

			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			
			httpConn.setRequestMethod("POST");								

			httpConn.getOutputStream().write(tracciaSDI.getRawData());
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();
			
			responseCode = httpConn.getResponseCode();
			
			response = IOUtils.readStringFromStream(responseCode < 299 ? httpConn.getInputStream() : httpConn.getErrorStream());
		} catch(Exception e) {
			this.log.error("Errore durante la protocollazione della traccia: " + e.getMessage(), e);
		}
		
		
		if(responseCode > 0 && responseCode < 299) {
			this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStatoOK+"]...");
			this.tracciaSdiBD.updateStatoProtocollazioneOK(tracciaSDI, nextStatoOK);
			this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStatoOK+"] OK");
		} else {
			
			if(responseCode == 503) { // protocollo della fattura non ancora disponibile, rimando semplicemente la data di prossima protocollazione senza aumentare il numero di tentativi
				long now = new Date().getTime();
				long offset = 1000*60*60; //rimando di un'ora

				StatoProtocollazioneType nextStato = tracciaSDI.getStatoProtocollazione();

				this.log.debug("Protocollo della fattura non ancora disponibile, rimando semplicemente la data di prossima protocollazione di un'ora senza aumentare il numero di tentativi...");
				this.tracciaSdiBD.updateStatoProtocollazioneKO(tracciaSDI, nextStato, response, new Date(now+offset), tracciaSDI.getTentativiProtocollazione());
				this.log.debug("Protocollo della fattura non ancora disponibile, rimando semplicemente la data di prossima protocollazione di un'ora senza aumentare il numero di tentativi OK");
			} else {
				IPolicyRispedizione policy = PolicyRispedizioneFactory.getInstance().getPolicyRispedizione(tracciaSDI);

				long now = new Date().getTime();
				
				long offset = policy.getOffsetRispedizione();

				StatoProtocollazioneType nextStato = policy.isRispedizioneAbilitata() ? StatoProtocollazioneType.IN_RICONSEGNA : nextStatoKO;

				this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStato+"]...");
				this.tracciaSdiBD.updateStatoProtocollazioneKO(tracciaSDI, nextStato, response, new Date(now+offset), tracciaSDI.getTentativiProtocollazione() + 1);
				this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStato+"] OK");
			}
		}

	}

}
