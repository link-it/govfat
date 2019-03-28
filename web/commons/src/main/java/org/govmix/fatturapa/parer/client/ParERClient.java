package org.govmix.fatturapa.parer.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaBean;
import org.govmix.fatturapa.parer.client.ParERResponse.STATO;
import org.govmix.fatturapa.parer.utils.ConservazioneProperties;
import org.govmix.fatturapa.parer.versamento.request.UnitaDocumentaria;
import org.govmix.fatturapa.parer.versamento.response.ECEsitoExtType;
import org.govmix.fatturapa.parer.versamento.response.EsitoVersamentoType;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

@SuppressWarnings("deprecation")
public class ParERClient {

	protected Logger log;
	protected Logger logDump;

	protected Marshaller marshaller;
	protected Unmarshaller evUnmarshaller;
	protected ConservazioneProperties props;
	
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

			
			CloseableHttpClient client = null;
			if(ud.getInput().getProperties().getTrustStorePath() != null) {
				KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		        FileInputStream instream = new FileInputStream(new File(ud.getInput().getProperties().getTrustStorePath()));
		        try {
		            trustStore.load(instream, ud.getInput().getProperties().getTrustStorePassword().toCharArray());
		        } finally {
		            instream.close();
		        }

		        // Trust own CA and all self-signed certs
		        SSLContext sslcontext = SSLContexts.custom()
		                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
		                .build();
		        // Allow TLSv1 protocol only
		        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		                sslcontext,
		                new String[] { "TLSv1" },
		                null,
		                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		        client = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(10)
		                .setCookieSpec(CookieSpecs.STANDARD).build())
		                .build();
			} else {
				client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(10)
		                .setCookieSpec(CookieSpecs.STANDARD).build()).build();
			}
			
			
			//invoca il web service
			HttpResponse response = client.execute(httppost);

			
			ParERResponse parerResp = getParerResponse(response, chiaveString);
			

			this.log.info("Invio in conservazione l'UD con chiave " + chiaveString + " completato con esito " + parerResp.getStato());

			if(!STATO.OK.equals(parerResp.getStato())) {
				this.log.warn("Response "+parerResp.getStato()+" per la chiave "+chiaveString);
			}
			
			client.close();
			return parerResp;
		} catch(ClientProtocolException e) {
			this.log.error("Errore durante l'invocazione del WS ParER: " + e.getMessage(), e);
			ParERResponse parERResponse = new ParERResponse();
			parERResponse.setStato(STATO.ERRORE_CONNESSIONE);
			
			return parERResponse;
		} catch(ConnectTimeoutException e) {
			this.log.error("Connect timeout durante l'invocazione del WS ParER: " + e.getMessage(), e);
			ParERResponse parERResponse = new ParERResponse();
			parERResponse.setStato(STATO.ERRORE_TIMEOUT);
			
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
			throw new IOException("Response code restituito ["+response.getStatusLine().getStatusCode()+"] diverso da [200]");
		}
		
		ByteArrayOutputStream baos = leggiBody(response);
		EsitoVersamentoType esito = toEsito(baos.toByteArray(), chiave);
		
		ParERResponse parERResponse = new ParERResponse();
		
		
		parERResponse.setStato(getErrori(esito, chiave));
		parERResponse.setEsitoVersamento(new String(baos.toByteArray())); 
		if(esito.getRapportoVersamento() != null) {
			parERResponse.setRapportoVersamento(esito.getRapportoVersamento());
		}					
		
		return parERResponse;
	}

	private ByteArrayOutputStream leggiBody(HttpResponse response) throws IOException {
		ByteArrayOutputStream baos = null;
		InputStream is = null;
		try {
			is = response.getEntity().getContent();
			baos = new ByteArrayOutputStream();
			IOUtils.copy(is, baos);
			return baos;
		} finally {
			if(baos!= null) {
				try {
					baos.flush();
					baos.close();
				} catch(Exception e) {}
			}
			if(is!= null) {
				try{is.close();} catch(Exception e) {}
			}
		}
	} 
	
	private STATO getErrori(EsitoVersamentoType esito, String chiave) {
		ECEsitoExtType codiceEsito = esito.getEsitoGenerale().getCodiceEsito() != null ? esito.getEsitoGenerale().getCodiceEsito() : ECEsitoExtType.NEGATIVO;
		if(ECEsitoExtType.NEGATIVO.toString().equals(codiceEsito.toString())) {
			if(esito.getEsitoGenerale().getCodiceErrore() != null) {
				if("UD-002-001".equals(esito.getEsitoGenerale().getCodiceErrore())) {
					return STATO.DUPLICATO;				
				} else {
					return STATO.KO;
				}
			} else {
				return STATO.OK;
			}
		} else {
			return STATO.OK;
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
	
	@SuppressWarnings("unchecked")
	private EsitoVersamentoType toEsito(byte content[], String chiave) throws JAXBException, IOException {
		InputStream iss = null;
		try {
			this.logDump.info("Response con chiave "+chiave+":");
			this.logDump.info(new String(content));
			iss = new ByteArrayInputStream(content);
	    	JAXBElement<EsitoVersamentoType> unmarshal = (JAXBElement<EsitoVersamentoType>) this.evUnmarshaller.unmarshal(iss);
			return unmarshal.getValue();
		} finally {
			if(iss!= null) {
				try{iss.close();} catch(Exception e) {}
			}
		}
	}
	
}
