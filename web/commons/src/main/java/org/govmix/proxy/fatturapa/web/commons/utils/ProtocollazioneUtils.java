package org.govmix.proxy.fatturapa.web.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFatturePassiveBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.TracciaSdIBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.TracciaSdIFilter;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.FatturaSingleFileExporter;
import org.govmix.proxy.fatturapa.web.commons.policies.IPolicyRispedizione;
import org.govmix.proxy.fatturapa.web.commons.policies.PolicyRispedizioneFactory;
import org.openspcoop2.utils.UtilsException;

public class ProtocollazioneUtils {

	private Logger log;


	public ProtocollazioneUtils(Logger log) throws Exception {
		this.log = log;
	}

	public boolean inviaLotto(LottoFatturePassiveBD lottoBD, EndpointSelector endpointSelector, LottoFatture lotto, String idLotto)
			throws Exception, MalformedURLException, UtilsException,
			IOException {
		Endpoint endpoint = endpointSelector.findEndpoint(lotto);
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyy HH:mm:ss Z");

		URL urlEndpoint = endpoint.getEndpoint().toURL();

		this.log.debug("Spedisco il lotto di fatture ["+idLotto+"] all'endpoint ["+urlEndpoint+"]");

		URL url = new URL(urlEndpoint.toString() + "/protocollazioneLotto");

		URLConnection conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;
		String errore = null;
		boolean esitoPositivo = false;
		ByteArrayOutputStream baos = null;
		String response = null;
		String protocollo = null;
		boolean asincrono = false;
		try{
			httpConn.setRequestProperty(CostantiProtocollazione.IDENTIFICATIVO_SDI_HEADER_PARAM, ""+lotto.getIdentificativoSdi());
			httpConn.setRequestProperty(CostantiProtocollazione.MSG_ID_HEADER_PARAM, ""+lotto.getMessageId());
			httpConn.setRequestProperty(CostantiProtocollazione.NOME_FILE_HEADER_PARAM, ""+lotto.getNomeFile());
			httpConn.setRequestProperty(CostantiProtocollazione.DATA_HEADER_PARAM, "" + sdf.format(lotto.getDataRicezione()));
			httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_HEADER_PARAM, lotto.getCodiceDestinatario());

			if(lotto.getIdEgov() != null) //solo se presente
				httpConn.setRequestProperty(CostantiProtocollazione.ID_EGOV_HEADER_PARAM, lotto.getIdEgov());

			if(lotto.getCedentePrestatoreCodiceFiscale() != null)
				httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_CF_HEADER_PARAM, lotto.getCedentePrestatoreCodiceFiscale());

			if(lotto.getCedentePrestatoreDenominazione() != null) {
				httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_RAGIONESOCIALE_HEADER_PARAM, lotto.getCedentePrestatoreDenominazione());
			} else if(lotto.getCedentePrestatoreNome() != null && lotto.getCedentePrestatoreCognome() != null) {
				httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_RAGIONESOCIALE_HEADER_PARAM, lotto.getCedentePrestatoreNome() + " " + lotto.getCedentePrestatoreCognome());
			}

			if(lotto.getCessionarioCommittenteCodiceFiscale() != null) {
				httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_CF_HEADER_PARAM, lotto.getCessionarioCommittenteCodiceFiscale());
			}

			if(lotto.getCessionarioCommittenteDenominazione() != null) {
				httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getCessionarioCommittenteDenominazione());
			} else if(lotto.getCessionarioCommittenteNome() != null && lotto.getCessionarioCommittenteCognome() != null) {
				httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getCessionarioCommittenteNome() + " " + lotto.getCessionarioCommittenteCognome());
			}

			if(lotto.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale() != null)
				httpConn.setRequestProperty(CostantiProtocollazione.TERZO_INTERMEDIARIO_CF_HEADER_PARAM, lotto.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale());

			if(lotto.getTerzoIntermediarioOSoggettoEmittenteDenominazione() != null) {
				httpConn.setRequestProperty(CostantiProtocollazione.TERZO_INTERMEDIARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getTerzoIntermediarioOSoggettoEmittenteDenominazione());
			} else if(lotto.getTerzoIntermediarioOSoggettoEmittenteNome() != null && lotto.getTerzoIntermediarioOSoggettoEmittenteCognome() != null) {
				httpConn.setRequestProperty(CostantiProtocollazione.TERZO_INTERMEDIARIO_RAGIONESOCIALE_HEADER_PARAM, lotto.getTerzoIntermediarioOSoggettoEmittenteNome() + " " + lotto.getTerzoIntermediarioOSoggettoEmittenteCognome());
			}

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
			baos = new ByteArrayOutputStream();

			httpConn.getOutputStream().write(lotto.getXml());
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();

			esitoPositivo = httpConn.getResponseCode() < 299;

			response = IOUtils.readStringFromStream(httpConn.getInputStream());
			if(httpConn.getHeaderFields().containsKey(CostantiProtocollazione.PROTOCOLLAZIONE_ASINCRONA_PARAM))
				asincrono = Boolean.parseBoolean(httpConn.getHeaderField(CostantiProtocollazione.PROTOCOLLAZIONE_ASINCRONA_PARAM));

			if(httpConn.getHeaderFields().containsKey(CostantiProtocollazione.PROTOCOLLO_PARAM))
				protocollo = httpConn.getHeaderField(CostantiProtocollazione.PROTOCOLLO_PARAM);
		} catch (Exception e) {
			errore = e.getMessage();
		} finally {
			if(baos != null) {
				try {
					baos.flush();
				} catch(Exception e) {}
				try {
					baos.close();
				} catch(Exception e) {}
			}
		}

		IdLotto idLottoSDI = lottoBD.convertToId(lotto);

		if(esitoPositivo) {
			this.log.debug("Lotto di Fatture ["+idLotto+"] inviata correttamente all'endpoint ["+url+"]. Response: ["+response+"]");
			lottoBD.updateProtocollo(idLottoSDI, protocollo, response, asincrono);
		} else {
			if(errore != null) {
				this.log.debug("Lotto di Fatture ["+idLotto+"] inviato con errore ["+errore+"] all'endpoint ["+url+"]. Response: ["+response+"]");
				lottoBD.updateStatoConsegna(idLottoSDI, response, "Errore durante la spedizione del lotto di fatture: " + errore);	
			} else {
				this.log.debug("Lotto di Fatture ["+idLotto+"] inviato con errore all'endpoint ["+url+"], codice di risposta ["+httpConn.getResponseCode()+"]. Response: ["+response+"]");
				lottoBD.updateStatoConsegna(idLottoSDI, response, "Errore durante la spedizione del lotto di fatture: risposta dal servizio con codice [" + httpConn.getResponseCode()+"]");
			}
		}

		return esitoPositivo;
	}

	public boolean inviaFattura(FatturaPassivaBD fatturaElettronicaBD, EndpointSelector endpointSelector, FatturaSingleFileExporter exp, FatturaElettronica fattura, IdFattura idFattura, boolean consegnaContestuale) throws Exception {

		Endpoint endpoint = endpointSelector.findEndpoint(fattura);

		URL urlEndpoint = endpoint.getEndpoint().toURL();
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyy HH:mm:ss Z");

		this.log.debug("Spedisco la fattura ["+idFattura.toJson()+"] all'endpoint ["+urlEndpoint+"]");

		if(consegnaContestuale) {
			this.log.debug("Identificativo di protocollo lotto ["+fattura.getLottoFatture().getDettaglioConsegna()+"] per la fattura ["+idFattura.toJson()+"]");
		}

		boolean esitoPositivo = false;
		ByteArrayOutputStream baos = null;
		String response = null;
		String protocollo = null;
		boolean asincrono = false;
		int responseCode = -1;
		URL url = new URL(urlEndpoint.toString() + "/protocollazioneFattura" + "?" + CostantiProtocollazione.URL_PARAM_ID_SDI+"="+idFattura.getIdentificativoSdi() + "&"+CostantiProtocollazione.URL_PARAM_POSIZIONE+"="+idFattura.getPosizione());

		try{

			URLConnection conn = url.openConnection();

			HttpURLConnection httpConn = (HttpURLConnection) conn;

			if(consegnaContestuale) {
				httpConn.setRequestProperty(CostantiProtocollazione.ID_PROTOCOLLO_HEADER_PARAM, fattura.getLottoFatture().getDettaglioConsegna());
			}
			httpConn.setRequestProperty(CostantiProtocollazione.NOME_FILE_HEADER_PARAM, fattura.getNomeFile());
			httpConn.setRequestProperty(CostantiProtocollazione.IDENTIFICATIVO_SDI_HEADER_PARAM, ""+idFattura.getIdentificativoSdi());
			httpConn.setRequestProperty(CostantiProtocollazione.POSIZIONE_HEADER_PARAM, ""+idFattura.getPosizione());
			httpConn.setRequestProperty(CostantiProtocollazione.NUMERO_HEADER_PARAM, fattura.getNumero());
			httpConn.setRequestProperty(CostantiProtocollazione.DATA_HEADER_PARAM, "" + sdf.format(fattura.getData()));
			httpConn.setRequestProperty(CostantiProtocollazione.IMPORTO_HEADER_PARAM, ""+ fattura.getImportoTotaleDocumento());
			httpConn.setRequestProperty(CostantiProtocollazione.VALUTA_HEADER_PARAM, fattura.getDivisa());
			httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_CF_HEADER_PARAM, fattura.getCedentePrestatoreCodiceFiscale());
			httpConn.setRequestProperty(CostantiProtocollazione.MITTENTE_RAGIONESOCIALE_HEADER_PARAM, fattura.getCedentePrestatoreDenominazione());

			if(consegnaContestuale) {
				if(fattura.getLottoFatture().getDettaglioConsegna() != null)
					httpConn.setRequestProperty(CostantiProtocollazione.DETTAGLIO_CONSEGNA_HEADER_PARAM, fattura.getLottoFatture().getDettaglioConsegna());
			} else {
				if(fattura.getDettaglioConsegna() != null)
					httpConn.setRequestProperty(CostantiProtocollazione.DETTAGLIO_CONSEGNA_HEADER_PARAM, fattura.getDettaglioConsegna());
			}

			httpConn.setRequestProperty(CostantiProtocollazione.DESTINATARIO_HEADER_PARAM, fattura.getCodiceDestinatario());
			httpConn.setRequestProperty("Content-Type", "application/zip");

			if(endpoint.getUsername() != null && endpoint.getPassword()!= null) {
				String auth = endpoint.getUsername() + ":" + endpoint.getPassword(); 
				String authentication = "Basic " + Base64.encode(auth.getBytes());

				httpConn.setRequestProperty("Authorization", authentication);
			}

			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			httpConn.setRequestMethod("POST");								
			baos = new ByteArrayOutputStream();
			exp.exportAsZip(Arrays.asList(fattura), baos);

			httpConn.getOutputStream().write(baos.toByteArray());
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();

			responseCode = httpConn.getResponseCode();
			esitoPositivo =  responseCode < 299;

			this.log.debug("Esito: "+ responseCode);

			response = IOUtils.readStringFromStream(esitoPositivo ? httpConn.getInputStream() : httpConn.getErrorStream());
			if(httpConn.getHeaderFields().containsKey(CostantiProtocollazione.PROTOCOLLAZIONE_ASINCRONA_PARAM))
				asincrono = Boolean.parseBoolean(httpConn.getHeaderField(CostantiProtocollazione.PROTOCOLLAZIONE_ASINCRONA_PARAM));
			if(httpConn.getHeaderFields().containsKey(CostantiProtocollazione.PROTOCOLLO_PARAM))
				protocollo = httpConn.getHeaderField(CostantiProtocollazione.PROTOCOLLO_PARAM);

		} catch (Exception e) {
			this.log.error("Errore durante la consegna della fattura ["+idFattura.toJson()+"]: "+e.getMessage(), e);
		} finally {
			if(baos != null) {
				try {
					baos.flush();
				} catch(Exception e) {}
				try {
					baos.close();
				} catch(Exception e) {}
			}
		}

		if(esitoPositivo) {
			this.log.debug("Fattura ["+idFattura.toJson()+"] inviata correttamente all'endpoint ["+url+"]. Response: ["+response+"]");
			fatturaElettronicaBD.updateProtocollo(idFattura, fattura.getStatoProtocollazione(), protocollo, asincrono);
		} else {
			this.log.debug("Fattura ["+idFattura.toJson()+"] inviata con errore all'endpoint ["+url+"], codice di risposta ["+responseCode+"]. Dettaglio: ["+response+"]");

			IPolicyRispedizione policy = PolicyRispedizioneFactory.getInstance().getPolicyRispedizione(fattura);

			long now = new Date().getTime();

			fattura.setTentativiConsegna(fattura.getTentativiConsegna() + 1);
			fattura.setDettaglioConsegna(response);
			fattura.setDataConsegna(new Date(now));

			long offset = policy.getOffsetRispedizione();

			if(policy.isRispedizioneAbilitata()) {
				fattura.setStatoConsegna(StatoConsegnaType.IN_RICONSEGNA);
			} else {
				fattura.setStatoConsegna(StatoConsegnaType.ERRORE_CONSEGNA);
			}
			fattura.setDataProssimaConsegna(new Date(now+offset));
			fatturaElettronicaBD.updateStatoConsegna(fattura);

		}

		return esitoPositivo;

	}
	
	public boolean consegnaNotifica(TracciaSDI tracciaSDI, EndpointSelector endpointSelector, TracciaSdIBD tracciaSdiBD) throws Exception {
		
		StatoProtocollazioneType nextStatoOK = StatoProtocollazioneType.PROTOCOLLATA;
		StatoProtocollazioneType nextStatoKO = StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE;
		
		this.log.debug("Elaboro la traccia con id ["+tracciaSDI.getId()+"]");
		
		Endpoint endpoint = endpointSelector.findEndpoint(tracciaSDI);
		
		URL urlOriginale = endpoint.getEndpoint().toURL();
		
		this.log.debug("Spedisco la traccia ["+tracciaSDI.getId()+"] all'endpoint ["+urlOriginale.toString()+"]");
		
		URL url = new URL(urlOriginale.toString() + "/protocollazioneRicevute");

		URLConnection conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;
		boolean esitoPositivo = false;
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
			
			esitoPositivo = httpConn.getResponseCode() < 299;
			
			response = IOUtils.readStringFromStream(esitoPositivo ? httpConn.getInputStream() : httpConn.getErrorStream());
		} catch(Exception e) {
			this.log.error("Errore durante la protocollazione della traccia: " + e.getMessage(), e);
		}
		
		
		if(esitoPositivo) {
			this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStatoOK+"]...");
			tracciaSdiBD.updateStatoProtocollazioneOK(tracciaSDI, nextStatoOK);
			this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStatoOK+"] OK");
		} else {
			
			IPolicyRispedizione policy = PolicyRispedizioneFactory.getInstance().getPolicyRispedizione(tracciaSDI);

			long now = new Date().getTime();
			
			long offset = policy.getOffsetRispedizione();

			StatoProtocollazioneType nextStato = policy.isRispedizioneAbilitata() ? StatoProtocollazioneType.NON_PROTOCOLLATA : nextStatoKO;

			this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStato+"]...");
			tracciaSdiBD.updateStatoProtocollazioneKO(tracciaSDI, nextStato, response, new Date(now+offset), tracciaSDI.getTentativiProtocollazione() + 1);
			this.log.debug("Elaboro la traccia ["+tracciaSDI.getId()+"], stato ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+nextStato+"] OK");
		}
		
		return esitoPositivo;

	}

	public boolean associaProtocollo(LottoFatturePassiveBD lottoBD, EndpointSelector endpointSelector, FatturaPassivaBD fatturaBD, LottoFatture lotto, IdLotto idLotto) throws Exception {

		Endpoint endpoint = endpointSelector.findEndpoint(lotto);

		URL urlEndpoint = endpoint.getEndpoint().toURL();

		this.log.debug("Associo il protocollo al lotto di fatture ["+idLotto.toJson()+"]");

		URL url = new URL(urlEndpoint.toString() + "/richiediProtocollo");

		URLConnection conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;
		boolean esitoPositivo = false;
		//		String response = null;
		String protocollo = null;
		try{
			httpConn.setRequestProperty(CostantiProtocollazione.ID_PROTOCOLLO_HEADER_PARAM, ""+lotto.getDettaglioConsegna());

			if(endpoint.getUsername() != null && endpoint.getPassword()!= null) {
				String auth = endpoint.getUsername() + ":" + endpoint.getPassword(); 
				String authentication = "Basic " + Base64.encode(auth.getBytes());

				httpConn.setRequestProperty("Authorization", authentication);
			}

			httpConn.setDoOutput(false);
			httpConn.setDoInput(true);

			httpConn.connect();
			esitoPositivo = httpConn.getResponseCode() < 299;

			if(httpConn.getHeaderFields().containsKey(CostantiProtocollazione.PROTOCOLLO_PARAM))
				protocollo = httpConn.getHeaderField(CostantiProtocollazione.PROTOCOLLO_PARAM);

		} catch (Exception e) {
			this.log.error("Errore durante l'associazione del protocollo al lotto ["+idLotto.toJson()+"]:" + e.getMessage(), e);
		}

		if(esitoPositivo) {
			if(protocollo != null && !"".equals(protocollo)) {
				this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviata correttamente all'endpoint ["+url+"]. Assegno il protocollo ["+protocollo+"] alle fatture del lotto.");
				lottoBD.updateStatoProtocollazioneOK(idLotto);
				fatturaBD.assegnaProtocolloAInteroLotto(idLotto, protocollo);
			} else {
				this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviata correttamente all'endpoint ["+url+"]. Protocollo non ancora disponibile");
			}

		} else {
			this.log.debug("Lotto di Fatture ["+idLotto.toJson()+"] inviata con errore");
			lottoBD.erroreProtocollo(idLotto);
			fatturaBD.erroreProtocolloAInteroLotto(idLotto);
		}

		return esitoPositivo;
	}

	public boolean forzaInvioLotto(String idSdI, boolean consegnaDocumentiAssociati) throws Exception {
		Connection conn = null;
		boolean esitoPositivo = false;
		try {
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);

			LottoFatturePassiveBD lottoBD = new LottoFatturePassiveBD(this.log, conn, false);
			FatturaPassivaBD fatturaElettronicaBD = new FatturaPassivaBD(this.log, conn, false);
			EndpointSelector endpointSelector = new EndpointSelector(this.log, conn, false);
			IdLotto idLotto = new IdLotto(false);
			idLotto.setIdentificativoSdi(idSdI);

			if(!lottoBD.exists(idLotto)) {
				throw new Exception("Lotto con id ["+idLotto.toJson()+"] non esiste");
			}

			LottoFatture lotto = lottoBD.get(idLotto);


			this.log.info("Invia lotto: " + idLotto.toJson());
			esitoPositivo = this.inviaLotto(lottoBD, endpointSelector, lotto, idLotto.toJson());

			if(!esitoPositivo)
				return false;

			List<IdFattura> idFatture = fatturaElettronicaBD.findAllIdFatturaByIdLotto(idLotto);
			for(IdFattura idFattura: idFatture) {
				boolean forzaInvioFattura = forzaInvioFattura(idFattura.getIdentificativoSdi(), idFattura.getPosizione(), consegnaDocumentiAssociati, conn);
				if(!forzaInvioFattura)
					return false;
			}

			this.log.info("Invia lotto: " + idLotto.toJson() + " completata con esitoPositivo: " + esitoPositivo);
			return esitoPositivo;

		} catch(Exception e) {
			this.log.error("Errore durante la forzaInvioLotto: " + e.getMessage(), e);
			if(conn != null){
				conn.rollback();
			}
			throw e;
		} finally {
			if(conn != null){
				conn.close();
			}
		}

	}


	public boolean forzaInvioFattura(String idSdI, Integer posizione, boolean consegnaDocumentiAssociati) throws Exception {
		Connection conn = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			return forzaInvioFattura(idSdI, posizione, consegnaDocumentiAssociati, conn);
		} catch(Exception e) {
			this.log.error("Errore durante la forzaInvioFattura: " + e.getMessage(), e);
			if(conn != null){
				conn.rollback();
			}
			throw e;
		} finally {
			if(conn != null){
				conn.close();
			}
		}
	}


	public boolean forzaInvioFattura(String idSdI, Integer posizione, boolean consegnaDocumentiAssociati, Connection conn) throws Exception {
		boolean esitoPositivo = false;
		boolean consegnaContestuale = true;

		LottoFatturePassiveBD lottoBD = new LottoFatturePassiveBD(this.log, conn, false);
		FatturaPassivaBD fatturaElettronicaBD = new FatturaPassivaBD(this.log, conn, false);
		EndpointSelector endpointSelector = new EndpointSelector(this.log, conn, false);
		FatturaSingleFileExporter exp = new FatturaSingleFileExporter(this.log, conn, false);
		IdLotto idLotto = lottoBD.newIdLotto();
		idLotto.setIdentificativoSdi(idSdI);

		if(!lottoBD.exists(idLotto)) {
			throw new Exception("Lotto con id ["+idLotto.toJson()+"] non esiste");
		}

		LottoFatture lotto = lottoBD.get(idLotto);

		IdFattura idFattura = fatturaElettronicaBD.newIdFattura();
		idFattura.setIdentificativoSdi(idSdI);
		idFattura.setPosizione(posizione);

		if(!fatturaElettronicaBD.exists(idFattura)) {
			throw new Exception("Fattura con id ["+idFattura.toJson()+"] non esiste");
		}

		FatturaElettronica fattura = fatturaElettronicaBD.get(idFattura);

		fattura.setDettaglioConsegna(lotto.getDettaglioConsegna());

		this.log.info("Invia fattura: " + idFattura.toJson());
		esitoPositivo = this.inviaFattura(fatturaElettronicaBD, endpointSelector, exp, fattura, idFattura, consegnaContestuale);

		if(!esitoPositivo)
			return false;

		this.log.info("Associa protocollo: " + idLotto.toJson());
		esitoPositivo = this.associaProtocollo(lottoBD, endpointSelector, fatturaElettronicaBD, lotto, idLotto);
		this.log.info("Associa protocollo: " + idLotto.toJson() + " completata con esitoPositivo: " + esitoPositivo);

		if(!esitoPositivo)
			return false;

		if(consegnaDocumentiAssociati) {
			TracciaSdIBD tracciaSdIBD = new TracciaSdIBD(this.log, conn, false);

			List<TipoComunicazioneType> lstT = Arrays.asList(TipoComunicazioneType.EC, TipoComunicazioneType.SE, TipoComunicazioneType.DT_PASS);
			for(TipoComunicazioneType type: lstT) {
				TracciaSdIFilter filter = tracciaSdIBD.newFilter();
				
				filter.setIdentificativoSdi(idSdI);
				
				if(!TipoComunicazioneType.DT_PASS.equals(type)) // la notifica DT non e' riferita a una fattura singola ma a un lotto 
					filter.setPosizione(posizione);
				filter.setTipoComunicazione(type.toString());
				
				this.log.info("Invia ["+type+"]: " + idFattura.toJson());
				List<TracciaSDI> lst = tracciaSdIBD.findAll(filter);
				for(TracciaSDI nec: lst) {
					esitoPositivo = this.consegnaNotifica(nec, endpointSelector, tracciaSdIBD);
					if(!esitoPositivo)
						return false;
				}

				this.log.info("Invia ["+type+"]: " + idFattura.toJson() + " completata con esitoPositivo: " + esitoPositivo);
			}

		}

		this.log.info("Invia fattura: " + idFattura.toJson() + " completata con esitoPositivo: " + esitoPositivo);
		return esitoPositivo;


	}

}
