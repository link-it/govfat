package org.govmix.fatturapa.parer.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaBean;
import org.govmix.fatturapa.parer.client.ParERResponse.STATO;
import org.govmix.fatturapa.parer.utils.ConservazioneProperties;
import org.govmix.fatturapa.parer.versamento.request.UnitaDocumentaria;
import org.govmix.fatturapa.parer.versamento.response.ECEsitoExtType;
import org.govmix.fatturapa.parer.versamento.response.EsitoVersamentoType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class ParERClient {

	private Logger log;
	private Logger logDump;

	private Marshaller marshaller;
	private Unmarshaller evUnmarshaller;
	private ConservazioneProperties props;
	
	public ParERClient(Logger log, ConservazioneProperties props) throws Exception {
		this(log, LoggerManager.getDumpLogger(), props);
	}
	
	public ParERClient(Logger log, Logger logDump, ConservazioneProperties props) throws Exception {

		this.props = props;
		this.log = log;
		this.logDump = logDump;
		
		JAXBContext udJaxbContext = JAXBContext.newInstance(UnitaDocumentaria.class.getPackage().getName());
		JAXBContext evJaxbContext = JAXBContext.newInstance(EsitoVersamentoType.class.getPackage().getName());
		this.marshaller = udJaxbContext.createMarshaller(); 
		this.evUnmarshaller = evJaxbContext.createUnmarshaller(); 

	}
	
	public ParERResponse invia(UnitaDocumentariaBean ud) {
		try {
			// crea una nuova istanza di HttpClient, predisponendo la chiamata del metodo POST
			HttpPost httppost = new HttpPost(this.props.getUrlParER());
			
			//Inizializza la request come multipart, nella modalità browser compatible che consente di inviare i dati come campi di una form web
			MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
			reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			
			//aggiunge alla request il campo testuale VERSIONE
			reqEntity.addPart("VERSIONE", new StringBody(ud.getInput().getProperties().getVersioneServizio(),
					ContentType.MULTIPART_FORM_DATA));
			
			//aggiunge alla request il campo testuale LOGINNAME
			reqEntity.addPart("LOGINNAME", new StringBody(ud.getInput().getProperties().getUsername(),
					ContentType.MULTIPART_FORM_DATA));
			
			//aggiunge alla request il campo testuale PASSWORD
			reqEntity.addPart("PASSWORD", new StringBody(ud.getInput().getProperties().getPassword(),
					ContentType.MULTIPART_FORM_DATA));
			
			//aggiunge alla request il campo testuale XMLSIP, con il documento XML dei metadati
			reqEntity.addPart("XMLSIP", new StringBody(new String(toXML(ud.getUnitaDocumentaria()), Charset.forName("ISO-8859-1")), ContentType.MULTIPART_FORM_DATA));
	
			if(ud.getMultiparts() != null) {
				for(String key: ud.getMultiparts().keySet()) {
					reqEntity.addPart(key, new ByteArrayBody(ud.getMultiparts().get(key), ContentType.MULTIPART_FORM_DATA, key));
				}
			}
			
//			imposta la chiamata del metodo POST con i dati appena caricati
			httppost.setEntity(reqEntity.build());
			
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			httppost.getEntity().writeTo(out);

			String chiaveString ="numero["+ud.getUnitaDocumentaria().getIntestazione().getChiave().getNumero()+"] anno["+ud.getUnitaDocumentaria().getIntestazione().getChiave().getAnno()+"] registro["+ud.getUnitaDocumentaria().getIntestazione().getChiave().getTipoRegistro()+"]";
			this.log.info("Invio in conservazione l'UD con chiave " + chiaveString);
			this.logDump.info("Invio in conservazione l'UD con chiave " + chiaveString);
			this.logDump.info("Request: " + out.toString());

			//invoca il web service
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(httppost);

			
			ParERResponse parerResp = getParerResponse(response, chiaveString);
			

			this.log.info("Invio in conservazione l'UD con chiave " + chiaveString + " completato con esito " + parerResp.getStato());

			if(STATO.KO.equals(parerResp.getStato())) {
				this.log.warn("Response KO per la chiave "+chiaveString);
			}
			
			client.close();
			return parerResp;
		} catch(ClientProtocolException e) {
			this.log.error("Errore durante l'invocazione del WS ParER: " + e.getMessage(), e);
			ParERResponse parERResponse = new ParERResponse();
			parERResponse.setStato(STATO.ERRORE_CONNESSIONE);
			
			return parERResponse;
		} catch(IOException e) {
			this.log.error("Errore durante l'invocazione del WS ParER: " + e.getMessage(), e);
			ParERResponse parERResponse = new ParERResponse();
			parERResponse.setStato(STATO.ERRORE_CONNESSIONE);
			
			return parERResponse;
		} catch(Exception e) {
			this.log.error("Errore durante l'invocazione del WS ParER: " + e.getMessage(), e);
			ParERResponse parERResponse = new ParERResponse();
			parERResponse.setStato(STATO.KO);
			
			return parERResponse;
		}

	}
	
	private ParERResponse getParerResponse(HttpResponse response, String chiave) throws Exception {
		
		this.log.info("Response code: " + response.getStatusLine().getStatusCode());
		
		if(response.getStatusLine().getStatusCode() != 200) {
			throw new Exception("Response code restituito ["+response.getStatusLine().getStatusCode()+"] diverso da [200]");
		}
		EsitoVersamentoType esito = toEsito(response.getEntity().getContent(), chiave);
		ParERResponse parERResponse = new ParERResponse();
		
		if(ECEsitoExtType.NEGATIVO.equals(esito.getEsitoGenerale().getCodiceEsito())) {

			boolean errore = getErrori(esito, chiave);
			if(errore) {
				parERResponse.setStato(STATO.KO);
			} else {
				parERResponse.setStato(STATO.OK);
				if(esito.getRapportoVersamento() != null) {
					parERResponse.setRapportoVersamento(esito.getRapportoVersamento());
				}					
			}

		} else {
			parERResponse.setStato(STATO.OK);
			if(esito.getRapportoVersamento() != null) {
				parERResponse.setRapportoVersamento(esito.getRapportoVersamento());
			}					

		}
		
		return parERResponse;
	} 
	
	private boolean getErrori(EsitoVersamentoType esito, String chiave) {

		if(esito.getEsitoGenerale().getCodiceErrore() != null) {
			
			if("UD-002-001".equals(esito.getEsitoGenerale().getCodiceErrore())) { // Fattura gia' presente nel sistema, considero caso ok
				this.log.warn("Fattura con chiave "+chiave+" gia' presente nel sistema");
				return false;				
			} 
			
			
			if(esito.getErroriUlteriori() != null && esito.getErroriUlteriori().getErrore() != null && !esito.getErroriUlteriori().getErrore().isEmpty()) {
				return true;
			}
			return false;
		} else {
			return false;	
		}
	}

	private byte[] toXML(UnitaDocumentaria input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<UnitaDocumentaria> inputj = new JAXBElement<UnitaDocumentaria>(new QName("UnitaDocumentaria"), UnitaDocumentaria.class, input);
	        

			this.marshaller.setProperty("jaxb.encoding", Charset.forName("ISO-8859-1").toString());
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}
	
	private EsitoVersamentoType toEsito(InputStream is, String chiave) throws JAXBException, IOException {
		
		ByteArrayOutputStream out = null;
		InputStream iss = null;
		try {
			out = new ByteArrayOutputStream();
			IOUtils.copy(is, out);
			this.logDump.info("Response con chiave "+chiave+":");
			this.logDump.info(out.toString());
	
			iss = new ByteArrayInputStream(out.toByteArray());
	    	JAXBElement<EsitoVersamentoType> unmarshal = (JAXBElement<EsitoVersamentoType>) this.evUnmarshaller.unmarshal(iss);
			return unmarshal.getValue();
		} finally {
			if(out!= null) {
				try {
					out.flush();
					out.close();
				} catch(Exception e) {}
			}
			if(iss!= null) {
				try{iss.close();} catch(Exception e) {}
			}
		}
	}
	
}
