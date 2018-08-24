package org.govmix.fatturapa.parer.builder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.geronimo.mail.util.Hex;
import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.AbstractUnitaDocumentariaInput;
import org.govmix.fatturapa.parer.beans.DocumentoWrapper;
import org.govmix.fatturapa.parer.beans.ParamWrapper;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaBean;
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.fatturapa.parer.versamento.request.ConfigType;
import org.govmix.fatturapa.parer.versamento.request.DatiSpecificiType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.IntestazioneType;
import org.govmix.fatturapa.parer.versamento.request.ProfiloUnitaDocumentariaType;
import org.govmix.fatturapa.parer.versamento.request.UnitaDocumentaria;
import org.govmix.fatturapa.parer.versamento.request.UnitaDocumentaria.Allegati;
import org.govmix.fatturapa.parer.versamento.request.UnitaDocumentaria.Annessi;
import org.govmix.fatturapa.parer.versamento.request.VersatoreType;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.LottoFatture;

import it.gov.fatturapa.sdi.messaggi.v1_0.MetadatiInvioFileType;

public abstract class AbstractUnitaDocumentariaBuilder <T extends AbstractUnitaDocumentariaInput> {

	protected Logger log;
	protected static String FORMAT ="yyyy-MM-dd";
	protected static boolean INCLUDE_METADATI = false;
	private Map<String, byte[]> multiparts;
	

	private static DatatypeFactory dtf;
	protected static XMLGregorianCalendar toXMLGC(Date date) throws Exception {
		if(dtf == null)
			dtf = DatatypeFactory.newInstance();
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return dtf.newXMLGregorianCalendar(cal);
	}

	public AbstractUnitaDocumentariaBuilder(Logger log) {
		this.log = log;
		this.multiparts = new HashMap<String, byte[]>();
	}
	
	public UnitaDocumentariaBean build(T input) throws Exception {
		this.multiparts = new HashMap<String, byte[]>();
		UnitaDocumentariaBean bean = new UnitaDocumentariaBean();
		bean.setInput(input);
		UnitaDocumentaria unitaDocumentaria = new UnitaDocumentaria();
		unitaDocumentaria.setIntestazione(this.getIntestazione(input));
		
		unitaDocumentaria.setConfigurazione(this.getConfigurazione(input));
		
		unitaDocumentaria.setProfiloUnitaDocumentaria(this.getProfiloUnitaDocumentaria(input));

		unitaDocumentaria.setDatiSpecifici(this.getDatiSpecificiJAXB(input));
		
		this.setDocumentoPricipale(unitaDocumentaria, input);

		unitaDocumentaria.setDocumentiCollegati(this.getDocumentiCollegati(input));
		
		this.setAnnessi(unitaDocumentaria, input);
		this.setAllegati(unitaDocumentaria, input);
		unitaDocumentaria.setNumeroAnnotazioni(0);
		
		bean.setUnitaDocumentaria(unitaDocumentaria);
		bean.setMultiparts(this.multiparts);
		
		return bean;
	}

	private void setDocumentoPricipale(UnitaDocumentaria unitaDocumentaria, T input) throws Exception {
		DocumentoType documentoPrincipale = this.getDocumentoPrincipale(input);

		byte[] raw = this.getRawDocumentoPrincipale(input);
		this.setHash(documentoPrincipale, raw);
		unitaDocumentaria.setDocumentoPrincipale(documentoPrincipale);
		this.multiparts.put(documentoPrincipale.getIDDocumento(), raw);
	}

	private void setAnnessi(UnitaDocumentaria unitaDocumentaria, T input) throws Exception {
		List<DocumentoWrapper> annessiDoc = this.getAnnessi(input);
		if(annessiDoc != null) {
			Annessi annessi = new Annessi();		
			for(DocumentoWrapper annesso: annessiDoc) {
				this.setHash(annesso.getDoc(), annesso.getRaw());
				annessi.getAnnesso().add(annesso.getDoc());
				String key = annesso.getDoc().getIDDocumento();
				this.multiparts.put(key, annesso.getRaw());
			}
			unitaDocumentaria.setAnnessi(annessi);
			unitaDocumentaria.setNumeroAnnessi(annessi.getAnnesso().size());
		} else {
			unitaDocumentaria.setNumeroAnnessi(0);
		}
		
	}

	private void setAllegati(UnitaDocumentaria unitaDocumentaria, T input) throws Exception {
		List<DocumentoWrapper> allegatiDoc = this.getAllegati(input);
		if(allegatiDoc != null) {
			Allegati allegati = new Allegati();		
			for(DocumentoWrapper allegato: allegatiDoc) {
				this.setHash(allegato.getDoc(), allegato.getRaw());
				allegati.getAllegato().add(allegato.getDoc());
				String key = allegato.getDoc().getIDDocumento();
				this.multiparts.put(key, allegato.getRaw());
			}
			unitaDocumentaria.setAllegati(allegati);
			unitaDocumentaria.setNumeroAllegati(allegati.getAllegato().size());
		} else {
			unitaDocumentaria.setNumeroAllegati(0);
		}
		
	}
	
	private static final String ALGORITHM = "SHA-1";

	private void setHash(DocumentoType doc, byte[] raw) throws Exception {
		String hash = new String(Hex.encode(MessageDigest.getInstance(ALGORITHM).digest(raw)));
		doc.getStrutturaOriginale().getComponenti().getComponente().get(0).setHashVersato(hash);
	}
	private JAXBElement<DatiSpecificiType> getDatiSpecificiJAXB(T input) {
		DatiSpecificiType datiSpecifici = new DatiSpecificiType();
		datiSpecifici.setVersioneDatiSpecifici(getVersioneDatiSpecifici(input));
		
		List<ParamWrapper> param = this.getParams(input);
		
		if(param != null) {
			for(ParamWrapper wrap: param) {
				datiSpecifici.getAny().add(newElement(wrap.getKey(), wrap.getValue()));
			}
		}
		
		JAXBElement<DatiSpecificiType> datiSpecificiJAXB = new JAXBElement<DatiSpecificiType>(new QName("DatiSpecifici"), DatiSpecificiType.class, datiSpecifici);
		return datiSpecificiJAXB;
	}

	private JAXBElement<String> newElement(String name, String value) {
		JAXBElement<String> element = new JAXBElement<String>(new QName(name), String.class, value);
		return element;
	}

	protected abstract ConfigType getConfigurazione(T input);

	protected byte[] toXML(MetadatiInvioFileType input) throws JAXBException, IOException {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			JAXBElement<MetadatiInvioFileType> inputj = new JAXBElement<MetadatiInvioFileType>(new QName("http://www.fatturapa.gov.it/sdi/messaggi/v1.0", "MetadatiInvioFile"), MetadatiInvioFileType.class, input);

			JAXBContext jaxbContext = JAXBContext.newInstance(MetadatiInvioFileType.class.getPackage().getName());
			Marshaller marshaller = jaxbContext.createMarshaller(); 

			marshaller.setProperty("jaxb.encoding", Charset.forName("ISO-8859-1").toString());
			marshaller.marshal(inputj, baos);
			return baos.toByteArray();
		} finally {
			if(baos != null) {
				baos.flush();
				baos.close();
			} 
		}
	}
	
	protected byte[] getXMLMetadati(LottoFatture lotto) throws Exception {
		MetadatiInvioFileType metadati = new MetadatiInvioFileType();
		metadati.setCodiceDestinatario(lotto.getCodiceDestinatario());
		metadati.setFormato(lotto.getFormatoArchivioInvioFattura().getValue());
		metadati.setIdentificativoSdI(lotto.getIdentificativoSdi());
		metadati.setMessageId(lotto.getMessageId());
		metadati.setNomeFile(lotto.getNomeFile());
		metadati.setVersione(lotto.getFormatoTrasmissione().getValue());

		return toXML(metadati);
	}
	
	protected IntestazioneType getIntestazione(T input) throws Exception {
		IntestazioneType intestazione = new IntestazioneType();
		intestazione.setVersione(input.getProperties().getVersioneServizio());
		VersatoreType versatore = new VersatoreType();
		versatore.setAmbiente(input.getProperties().getAmbienteVersatore());
		Ente ente = this.getEnte(input);
		versatore.setEnte(ente.getEnteVersatore());
		versatore.setStruttura(ente.getStrutturaVersatore());
		versatore.setUserID(input.getProperties().getUserIDVersatore());
		intestazione.setVersatore(versatore);
		
		intestazione.setChiave(getChiave(input));
		intestazione.setTipologiaUnitaDocumentaria(getTipologiaUnitaDocumentaria(input));
		
		
		return intestazione;
	}


	protected abstract Ente getEnte(T input) throws Exception;
	protected abstract String getTipologiaUnitaDocumentaria(T input);
	protected abstract DocumentoCollegatoType getDocumentiCollegati(T input);
	protected abstract byte[] getRawDocumentoPrincipale(T input);
	protected abstract List<DocumentoWrapper> getAnnessi(T input) throws Exception;
	protected abstract List<DocumentoWrapper> getAllegati(T input) throws Exception;
	protected abstract DocumentoType getDocumentoPrincipale(T input);
	protected abstract List<ParamWrapper> getParams(T input);
	protected abstract ProfiloUnitaDocumentariaType getProfiloUnitaDocumentaria(T input) throws Exception;
	public abstract ChiaveType getChiave(T input);
	protected abstract String getVersioneDatiSpecifici(T input);
	
	
}
