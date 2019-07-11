package org.govmix.proxy.fatturapa.web.timers;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFattureAttiveBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.LottoFattureAnalyzer;
import org.govmix.proxy.fatturapa.web.commons.utils.CostantiProtocollazione;
import org.govmix.proxy.fatturapa.web.commons.utils.Endpoint;
import org.govmix.proxy.fatturapa.web.commons.utils.EndpointSelector;
import org.govmix.proxy.fatturapa.web.timers.policies.IPolicyRispedizione;
import org.govmix.proxy.fatturapa.web.timers.policies.PolicyRispedizioneFactory;

public class WorkFlow implements IWorkFlow<LottoFatture> {

	private Logger log;
	private int limit;
	private LottoFattureAttiveBD lottoBD;
	private Date limitDate;
	private EndpointSelector endpointSelector;

	@Override
	public void init(Logger log, Connection connection, int limit) throws Exception {
		this.log = log;
		this.limit = limit;
		this.limitDate = new Date();
		this.lottoBD = new LottoFattureAttiveBD(log, connection, false);
		this.endpointSelector = new EndpointSelector(log, connection, false);
	}

	@Override
	public long count() throws Exception {
		List<StatoElaborazioneType> lst = new ArrayList<StatoElaborazioneType>();
		lst.add(StatoElaborazioneType.PRESA_IN_CARICO);
		return this.lottoBD.countByStatiElaborazioneInUscita(lst, this.limitDate);
	}

	@Override
	public List<LottoFatture> getNextLista() throws Exception {
		List<StatoElaborazioneType> lst = new ArrayList<StatoElaborazioneType>();
		lst.add(StatoElaborazioneType.PRESA_IN_CARICO);
		return this.lottoBD.findAllByStatiElaborazioneInUscita(lst, this.limitDate, 0, this.limit);
	}

	@Override
	public void process(LottoFatture lotto) throws Exception {
		this.log.debug("Elaboro il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"]");
		
		StatoElaborazioneType nextStatoOK = null;
		StatoElaborazioneType nextStatoKO = null;
		
		LottoFattureAnalyzer lottoFattureAnalyzer = new LottoFattureAnalyzer(lotto, null, lotto.getCodiceDestinatario(), this.log);
		if(!lottoFattureAnalyzer.isFirmato() && lottoFattureAnalyzer.isFirmaNecessaria()) {
			nextStatoOK = StatoElaborazioneType.IN_CORSO_DI_FIRMA;
			nextStatoKO = StatoElaborazioneType.ERRORE_DI_FIRMA;
		} else {
			nextStatoOK = StatoElaborazioneType.IN_CORSO_DI_PROTOCOLLAZIONE;
			nextStatoKO = StatoElaborazioneType.ERRORE_DI_PROTOCOLLO;
		}
		
		if(nextStatoOK!= null) {
			IdLotto idLotto = this.lottoBD.convertToId(lotto);

			try{
				Endpoint endpoint = endpointSelector.findEndpoint(lotto);
				
				URL urlOriginale = endpoint.getEndpoint().toURL();
				
				this.log.debug("Spedisco il lotto di fatture ["+idLotto.toJson()+"] all'endpoint ["+urlOriginale.toString()+"]");
				
				URL url = new URL(urlOriginale.toString() + "/protocollazioneLottoFattureAttive");
	
				URLConnection conn = url.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection) conn;
				boolean esitoPositivo = false;

				String response = null;

				try {
				
					httpConn.setRequestProperty(CostantiProtocollazione.IDENTIFICATIVO_SDI_HEADER_PARAM, ""+lotto.getIdentificativoSdi());
					httpConn.setRequestProperty(CostantiProtocollazione.NOME_FILE_HEADER_PARAM, ""+lotto.getNomeFile());
					httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_HEADER_PARAM, lotto.getCodiceDestinatario());
					if(lotto.getDettaglioElaborazione() != null)
						httpConn.setRequestProperty(CostantiProtocollazione.DETTAGLIO_CONSEGNA_HEADER_PARAM, lotto.getDettaglioElaborazione());
					
					String ct = FormatoArchivioInvioFatturaType.XML.equals(lotto.getFormatoArchivioInvioFattura()) ? "text/xml" : "application/pkcs7-mime";  
					httpConn.setRequestProperty("Content-Type", ct);
	
					if(endpoint.getUsername() != null && endpoint.getPassword()!= null) {
						String auth = endpoint.getUsername() + ":" + endpoint.getPassword(); 
						String authentication = "Basic " + Base64.encode(auth.getBytes());
	
						httpConn.setRequestProperty("Authorization", authentication);
					}
	

					httpConn.setDoOutput(true);
					httpConn.setDoInput(true);
					
					httpConn.setRequestMethod("POST");								
	
					httpConn.getOutputStream().write(lotto.getXml());
					httpConn.getOutputStream().flush();
					httpConn.getOutputStream().close();
					
					esitoPositivo = httpConn.getResponseCode() < 299;
					
					this.log.debug("ResponseCode ["+httpConn.getResponseCode()+"]");
					response = IOUtils.readStringFromStream(esitoPositivo ? httpConn.getInputStream() : httpConn.getErrorStream());
					
					if(!esitoPositivo) {
						this.log.debug("Response: "+response);
					}
				} catch(Exception e) {
					this.log.error("Errore durante l'avvio del workflow per il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"]: " + e.getMessage(), e);
				}
				
				if(esitoPositivo) {
					this.log.debug("Elaboro il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"], stato ["+lotto.getStatoElaborazioneInUscita()+"] -> ["+nextStatoOK+"]...");
					this.lottoBD.updateStatoElaborazioneInUscitaOK(idLotto, nextStatoOK);
					this.log.debug("Elaboro il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"], stato ["+lotto.getStatoElaborazioneInUscita()+"] -> ["+nextStatoOK+"] OK");
				} else {
					IPolicyRispedizione policy = PolicyRispedizioneFactory.getPolicyRispedizioneWFM(lotto);

					long now = new Date().getTime();
					
					long offset = policy.getOffsetRispedizione();

					StatoElaborazioneType nextStato = policy.isRispedizioneAbilitata() ? StatoElaborazioneType.PRESA_IN_CARICO : nextStatoKO;
					this.log.debug("Elaboro il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"], stato ["+lotto.getStatoElaborazioneInUscita()+"] -> ["+nextStato+"]...");
					this.lottoBD.updateStatoElaborazioneInUscitaKO(idLotto, nextStato, new Date(now+offset), response, lotto.getTentativiConsegna() + 1);
					this.log.debug("Elaboro il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"], stato ["+lotto.getStatoElaborazioneInUscita()+"] -> ["+nextStato+"] OK");
				}
			} catch(Exception e) {
				this.log.error("Errore durante l'avvio del workflow per il lotto ["+idLotto.toJson()+"]: " + e.getMessage(), e);
				this.log.debug("Elaboro il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"], stato ["+lotto.getStatoElaborazioneInUscita()+"] -> ["+nextStatoKO+"]...");
				this.lottoBD.updateStatoElaborazioneInUscitaKO(idLotto, nextStatoKO, new Date(), lotto.getDettaglioElaborazione(), lotto.getTentativiConsegna() + 1);
				this.log.debug("Elaboro il lotto ["+this.lottoBD.convertToId(lotto).toJson()+"], stato ["+lotto.getStatoElaborazioneInUscita()+"] -> ["+nextStatoKO+"] OK");
			}
		}

	}

}
