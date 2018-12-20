package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.SottodominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.openspcoop2.protocol.sdi.utils.P7MInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LottoFattureAnalyzer {

	private boolean isP7M;
	private boolean isFirmato;
	private byte[] original;
	private byte[] decoded;
	private LottoFatture lotto;
	private Logger log;

	public LottoFattureAnalyzer(LottoFatture lotto, Dipartimento dipartimento, String codiceDipartimento, Logger log) throws Exception {
		this(lotto.getXml(), lotto.getNomeFile(), lotto.getIdentificativoSdi(), dipartimento, codiceDipartimento, log);
	}
	
	public LottoFattureAnalyzer(byte[] lottoFatture, String nomeFile, Integer identificativo, Dipartimento dipartimento, String codiceDipartimento, Logger log) throws InserimentoLottiException {
		this.log = log;
		this.original = lottoFatture;
		try {
			P7MInfo info = new P7MInfo(lottoFatture, this.log);
			this.decoded = info.getXmlDecoded();
			this.isP7M = true;
			this.isFirmato = true;
		} catch(Throwable t) {
			this.log.debug("Acquisizione lotto P7M non riuscita...provo ad acquisire lotto XML. Motivo della mancata acquisizione:" + t.getMessage());
			this.isP7M = false;
			try {
				this.decoded = extractContentFromXadesSignedFile(lottoFatture);
				if(this.decoded != null) {
					this.isFirmato = true;
				} else {
					this.decoded = lottoFatture;
				}

			} catch(Exception e) {
				this.log.error("Errore durante l'acquisizione del lotto xml:" + e.getMessage(), e);
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, e.getMessage());
			}
		}
		String type = this.isP7M ? "P7M" : "XML"; 
		this.lotto  = getLotto(this.original, nomeFile, identificativo, type, dipartimento, codiceDipartimento);
	}

	private static DocumentBuilderFactory dbf;
	private static XPathFactory xPathfactory;
	private static boolean init;

	private static synchronized void init() {
		if(!init) {
			dbf = DocumentBuilderFactory.newInstance();
			xPathfactory = XPathFactory.newInstance();
		}
		init = true;
	}

	private byte[] extractContentFromXadesSignedFile(byte[] xmlIn) throws Exception {
		InputStream xadesIn = null;
		ByteArrayOutputStream bos = null;
		try {
			init();
			xadesIn = new ByteArrayInputStream(xmlIn);
			dbf.setNamespaceAware(true);
			Document doc = dbf.newDocumentBuilder().parse(xadesIn);

			XPath xpath = xPathfactory.newXPath();

			Map<String, String> map = new HashMap<String, String>();
			map.put("ds", "http://www.w3.org/2000/09/xmldsig#");

			xpath.setNamespaceContext(new MapNamespaceContext(map));

			XPathExpression expr = xpath.compile("//ds:Signature");
			NodeList referenceNodes = (NodeList) expr.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);

			if(referenceNodes.getLength() > 0) {
				for(int i=0;i<referenceNodes.getLength();i++){
					Node referenceNode = referenceNodes.item(i);
					referenceNode.getParentNode().removeChild(referenceNode);
				}

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();

				bos=new ByteArrayOutputStream();
				StreamResult result=new StreamResult(bos);
				transformer.transform(new DOMSource(doc), result);
				return bos.toByteArray();
			} else {
				return null;
			}
		} finally {
			if(xadesIn!=null) try {xadesIn.close();} catch(IOException e) {}                
			if(bos!=null) try {bos.flush();bos.close();} catch(IOException e) {}                
		}
	}

	public boolean isP7M() {
		return isP7M;
	}

	public void setP7M(boolean isP7M) {
		this.isP7M = isP7M;
	}

	public boolean isFirmato() {
		return isFirmato;
	}

	public void setFirmato(boolean isFirmato) {
		this.isFirmato = isFirmato;
	}

	public byte[] getOriginal() {
		return original;
	}

	public void setOriginal(byte[] original) {
		this.original = original;
	}

	public byte[] getDecoded() {
		return decoded;
	}

	public void setDecoded(byte[] decoded) {
		this.decoded = decoded;
	}

	private static DominioType getDominio(String codiceDipartimento) throws InserimentoLottiException {
		if(codiceDipartimento==null)
			throw new InserimentoLottiException(CODICE.ERRORE_GENERICO, "Impossibile determinare il dominio. Codice dipartimento null");
		if(codiceDipartimento.length() == 6)
			return DominioType.PA;
		if(codiceDipartimento.length() == 7)
			return DominioType.B2B;
		
		throw new InserimentoLottiException(CODICE.ERRORE_GENERICO, "Lunghezza del codice dipartimento ["+codiceDipartimento.length()+"]. Impossibile determinare il dominio");
	}
	
	
	private static SottodominioType getSottodominio(String codiceDipartimento) throws InserimentoLottiException {
		
		DominioType dominio = getDominio(codiceDipartimento);
		if(dominio.toString().equals(DominioType.B2B.toString())) {
			if("XXXXXXX".equals(codiceDipartimento)) {
				return SottodominioType.ESTERO;
			} else if("0000000".equals(codiceDipartimento)) {
				return SottodominioType.PEC;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}
	
	private LottoFatture getLotto(byte[] xml, String nomeFile, Integer identificativo, String type, Dipartimento dipartimento, String codiceDipartimento) throws InserimentoLottiException {
		
		
		ConsegnaFatturaParameters params = null;
		String messageId = identificativo + "";

		try {

			params = ConsegnaFatturaUtils.getParameters(identificativo, nomeFile,
							type, null,
							messageId,
							false,
							xml);
			
			params.validate(true);
		} catch(Exception e) {
			this.log.error("Errore durante il caricamento del lotto con nome file ["+nomeFile+"]: " + e.getMessage(), e);
			throw new InserimentoLottiException(CODICE.PARAMETRI_NON_VALIDI, nomeFile);
		}

		LottoFatture lotto = new LottoFatture();

		lotto.setFormatoArchivioInvioFattura(params.getFormatoArchivioInvioFattura());
		lotto.setCedentePrestatoreCodice(params.getCedentePrestatore().getIdCodice());
		lotto.setCedentePrestatorePaese(params.getCedentePrestatore().getIdPaese());
		lotto.setCedentePrestatoreCodiceFiscale(params.getCedentePrestatore().getCodiceFiscale());
		lotto.setCedentePrestatoreCognome(params.getCedentePrestatore().getCognome());
		lotto.setCedentePrestatoreNome(params.getCedentePrestatore().getNome());
		lotto.setCedentePrestatoreDenominazione(params.getCedentePrestatore().getDenominazione());

		lotto.setCessionarioCommittenteCodice(params.getCessionarioCommittente().getIdCodice());
		lotto.setCessionarioCommittentePaese(params.getCessionarioCommittente().getIdPaese());
		lotto.setCessionarioCommittenteCodiceFiscale(params.getCessionarioCommittente().getCodiceFiscale());
		lotto.setCessionarioCommittenteCognome(params.getCessionarioCommittente().getCognome());
		lotto.setCessionarioCommittenteNome(params.getCessionarioCommittente().getNome());
		lotto.setCessionarioCommittenteDenominazione(params.getCessionarioCommittente().getDenominazione());

		if(params.getTerzoIntermediarioOSoggettoEmittente() != null) {
			lotto.setTerzoIntermediarioOSoggettoEmittenteCodice(params.getTerzoIntermediarioOSoggettoEmittente().getIdCodice());
			lotto.setTerzoIntermediarioOSoggettoEmittentePaese(params.getTerzoIntermediarioOSoggettoEmittente().getIdPaese());
			lotto.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(params.getTerzoIntermediarioOSoggettoEmittente().getCodiceFiscale());
			lotto.setTerzoIntermediarioOSoggettoEmittenteCognome(params.getTerzoIntermediarioOSoggettoEmittente().getCognome());
			lotto.setTerzoIntermediarioOSoggettoEmittenteNome(params.getTerzoIntermediarioOSoggettoEmittente().getNome());
			lotto.setTerzoIntermediarioOSoggettoEmittenteDenominazione(params.getTerzoIntermediarioOSoggettoEmittente().getDenominazione());
		}

		lotto.setIdentificativoSdi(params.getIdentificativoSdI());

		lotto.setCodiceDestinatario(codiceDipartimento);
		lotto.setFormatoTrasmissione(FormatoTrasmissioneType.valueOf(params.getFormatoFatturaPA()));

		lotto.setNomeFile(params.getNomeFile());
		lotto.setMessageId(params.getMessageId());

		lotto.setXml(params.getXml());
		lotto.setFatturazioneAttiva(true);
		
		lotto.setDataRicezione(new Date());
		lotto.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
		lotto.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
		lotto.setStatoInserimento(StatoInserimentoType.NON_INSERITO);
		lotto.setDataUltimaElaborazione(new Date());
		lotto.setDataProssimaElaborazione(new Date());
		lotto.setTentativiConsegna(0);
		lotto.setDominio(getDominio(params.getCodiceDestinatario()));
		lotto.setSottodominio(getSottodominio(params.getCodiceDestinatario()));
		
		
		if(dipartimento!=null) {
			if(dipartimento.getEnte().getNodoCodicePagamento()!=null && dipartimento.getEnte().getPrefissoCodicePagamento() != null) {
				this.log.debug("Dipartimento ["+dipartimento.getCodice()+"]. Nodo ["+dipartimento.getEnte().getNodoCodicePagamento()+"]. Prefisso ["+dipartimento.getEnte().getPrefissoCodicePagamento()+"]");
				lotto.setPagoPA(this.getPagoPA(this.decoded, dipartimento.getEnte().getNodoCodicePagamento(), dipartimento.getEnte().getPrefissoCodicePagamento(), lotto.getNomeFile()));
			} else {
				this.log.debug("Ente ["+dipartimento.getEnte().getNome()+"] non abilitato a invio PagoPA");
			}
		} else {
			this.log.debug("Dipartimento non valorizzato. Non cerco numero avviso PagoPA"); 
		}
		
		return lotto;
	}

	public LottoFatture getLotto() {
		return lotto;
	}

	public void setLotto(LottoFatture lotto) {
		this.lotto = lotto;
	}
	
	public boolean isFirmaNecessaria() {
		return this.lotto.getDominio().toString().equals(DominioType.PA.toString()) || (this.getLotto().getSottodominio() != null && this.getLotto().getSottodominio().toString().equals(SottodominioType.ESTERO.toString()));
	}

	public String getPagoPA(byte[] xml, String xPathExpression, String prefix, String nomeFile) throws InserimentoLottiException {

		ByteArrayInputStream is = null;
		try {
			init();
			is = new ByteArrayInputStream(xml);
			dbf.setNamespaceAware(true);

			XPath xpath = xPathfactory.newXPath();

			XPathExpression expr = null; 
			try {
				this.log.debug("Compilazione dell'espressione ["+xPathExpression+"]...");
				expr = xpath.compile(xPathExpression);
				this.log.debug("Compilazione dell'espressione ["+xPathExpression+"] completata con successo");
			} catch(XPathExpressionException e) {
				this.log.error("Errore durante la valutazione dell'xPath:" + e.getMessage(), e);
				throw new InserimentoLottiException(CODICE.ERRORE_NODO_PAGOPA_NON_VALIDO, nomeFile, xPathExpression);
			}				
			Document doc = dbf.newDocumentBuilder().parse(is);
			this.log.debug("Valutazione dell'espressione ["+xPathExpression+"]...");
			NodeList nodeset = (NodeList) expr.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);
			this.log.debug("Valutazione dell'espressione ["+xPathExpression+"] completata. Trovati ["+nodeset.getLength()+"] risultati");


			int size = 0;
			String napp = null;
			for(int i =0; i < nodeset.getLength(); i++) {
				Node item = nodeset.item(i);

				if(item.getTextContent().startsWith(prefix)) {
					this.log.debug("MATCH risultato ["+i+"]:" + item.getTextContent());
					napp = item.getTextContent().substring(prefix.length());
					size++;
				} else {
					this.log.debug("NO MATCH risultato ["+i+"]:" + item.getTextContent());
				}
			}

			if(size > 1) {
				this.log.error("Trovato ["+size+"] numero avviso con prefisso ["+prefix+"]");
				throw new InserimentoLottiException(CODICE.ERRORE_IDENTIFICAZIONE_PAGOPA, nomeFile, size, prefix);
			}

			return napp;
		} catch (InserimentoLottiException e) {
			throw e;
		} catch (Exception e) {
			this.log.error("Errore durante la valutazione dell'xPath:" + e.getMessage(), e);
			throw new InserimentoLottiException(CODICE.ERRORE_GENERICO);
		} finally {
			if(is != null)
				try {is.close();} catch (IOException e) {}
		}

	}
	
	public CODICE getCodiceErroreNonFirmato() {
		if(this.lotto.getSottodominio() != null && this.lotto.getSottodominio().equals(SottodominioType.ESTERO)) {
			return CODICE.ERRORE_FILE_ESTERO_NON_FIRMATO;				
		}
		return CODICE.ERRORE_FILE_NON_FIRMATO;
	}

	public CODICE getCodiceErroreFirmato() {
		return CODICE.ERRORE_FILE_FIRMATO;
	}

}

