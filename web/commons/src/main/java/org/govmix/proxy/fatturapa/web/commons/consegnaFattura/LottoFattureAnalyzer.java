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
import org.openspcoop2.utils.resources.FileSystemUtilities;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class LottoFattureAnalyzer {

	private boolean isP7M;
	private boolean isFirmato;
	private byte[] original;
	private byte[] decoded;
	private LottoFatture lotto;


	public LottoFattureAnalyzer(LottoFatture lotto, Dipartimento dipartimento, String codiceDipartimento, Logger log) throws Exception {
		this(lotto.getXml(), lotto.getNomeFile(), lotto.getIdentificativoSdi(), dipartimento, codiceDipartimento, log);
	}
	
	public LottoFattureAnalyzer(byte[] lottoFatture, String nomeFile, Integer identificativo, Dipartimento dipartimento, String codiceDipartimento, Logger log) throws Exception {
		this.original = lottoFatture;
		try {
			P7MInfo info = new P7MInfo(lottoFatture, log);
			this.decoded = info.getXmlDecoded();
			this.isP7M = true;
			this.isFirmato = true;
		} catch(Throwable t) {
			log.debug("Acquisizione lotto P7M non riuscita...provo ad acquisire lotto XML. Motivo della mancata acquisizione:" + t.getMessage());
			this.isP7M = false;
			try {
				this.decoded = extractContentFromXadesSignedFile(lottoFatture);
				if(this.decoded != null) {
					this.isFirmato = true;
				} else {
					this.decoded = lottoFatture;
				}

			} catch(Exception e) {
				log.error("Errore durante l'acquisizione del lotto xml:" + e.getMessage(), e);
				throw e;
			}
		}
		String type = this.isP7M ? "P7M" : "XML"; 
		this.lotto  = getLotto(this.original, nomeFile, identificativo, type, dipartimento, codiceDipartimento, log);
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

	private static DominioType getDominio(String codiceDipartimento) throws Exception {
		if(codiceDipartimento==null)
			throw new Exception("Impossibile determinare il dominio. Codice dipartimento null");
		if(codiceDipartimento.length() == 6)
			return DominioType.PA;
		if(codiceDipartimento.length() == 7)
			return DominioType.B2B;
		
		throw new Exception("Lunghezza del codice dipartimento ["+codiceDipartimento.length()+"]. Impossibile determinare il dominio");
	}
	
	
	private static SottodominioType getSottodominio(String codiceDipartimento) throws Exception {
		
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
	
	private LottoFatture getLotto(byte[] xml, String nomeFile, Integer identificativo, String type, Dipartimento dipartimento, String codiceDipartimento, Logger log) throws Exception {
		
		
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
			log.error("Errore durante il caricamento del lotto con nome file ["+nomeFile+"]: " + e.getMessage(), e);
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
		
		if(dipartimento!=null && dipartimento.getEnte().getNodoCodicePagamento()!=null && dipartimento.getEnte().getPrefissoCodicePagamento() != null)
			lotto.setPagoPA(getPagoPA(params.getXml(), dipartimento.getEnte().getNodoCodicePagamento(), dipartimento.getEnte().getPrefissoCodicePagamento()));
		
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

	public static String getPagoPA(byte[] xml, String xPath, String prefix) throws Exception {

		ByteArrayInputStream is = null;
		try {
			is = new ByteArrayInputStream(xml);
			XPath xpath = XPathFactory.newInstance().newXPath();

			try {
				XPathExpression expr = xpath.compile(xPath);
				NodeList nodeset = (NodeList) expr.evaluate(new InputSource(is), XPathConstants.NODESET);


				int size = 0;
				String napp = null;
				for(int i =0; i < nodeset.getLength(); i++) {
					Node item = nodeset.item(i);

					if(item.getTextContent().startsWith(prefix)) {
						napp = item.getTextContent().substring(prefix.length());
						size++;
					}
				}

				if(size > 1)
					throw new Exception("Trovati ["+size+"] numero avviso PagoPA. Atteso al piu' uno");

				return napp;
			} catch(XPathExpressionException e) {
				throw new Exception("L'xPath ["+xPath+"] fornito per l'identificazione del numero avviso PagoPA non e' sintatticamente valido");
			}

		} finally {
			if(is != null)
				try {is.close();} catch (IOException e) {}
		}

	}
}

