package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.Date;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.SottodominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.ILottoConverter;
import org.openspcoop2.protocol.sdi.utils.P7MInfo;

public class LottoFattureAnalyzer {

	private boolean isP7M;
	private boolean isFirmato;
	private byte[] original;
	private byte[] decoded;
	private ILottoConverter lottoConverter;
	private Logger log;

	public LottoFattureAnalyzer(LottoFatture lotto, Dipartimento dipartimento, String codiceDipartimento, StatoElaborazioneType stato, String protocollo, Logger log) throws Exception {
		this(lotto.getXml(), lotto.getNomeFile(), lotto.getIdentificativoSdi(), dipartimento, codiceDipartimento, stato, protocollo, log);
	}
	
	public LottoFattureAnalyzer(byte[] lottoFatture, String nomeFile, Long identificativo, Dipartimento dipartimento, String codiceDipartimento, StatoElaborazioneType stato, String protocollo, Logger log) throws InserimentoLottiException {
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
				this.decoded = XPathUtils.extractContentFromXadesSignedFile(lottoFatture);
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
		
		if(stato == null) {
			
			if(this.isFirmato) {
				if(dipartimento.getFirmaAutomatica()){
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_FIRMATO, nomeFile, dipartimento);
				}
			} else {
				if(!dipartimento.getFirmaAutomatica() && isFirmaNecessaria(codiceDipartimento)) {
					throw new InserimentoLottiException(getCodiceErroreNonFirmato(codiceDipartimento), nomeFile, dipartimento);
				}
			}

			 if(dipartimento.getModalitaPush()) {
					stato = StatoElaborazioneType.PRESA_IN_CARICO;
			 } else {
			         if(!this.isFirmato  && isFirmaNecessaria(codiceDipartimento)) {
			                 throw new InserimentoLottiException(getCodiceErroreNonFirmato(codiceDipartimento), nomeFile, dipartimento);
			         }
					stato = StatoElaborazioneType.DA_INVIARE_ALLO_SDI;
			 }
		}
		ConsegnaFatturaParameters params = new ConsegnaFatturaParameters();
		params.setCodiceDestinatario(codiceDipartimento);
		params.setDataRicezione(new Date());
		params.setDominio(getDominio(codiceDipartimento));
		params.setSottodominio(getSottodominio(codiceDipartimento));
		params.setStato(stato);
		params.setProtocollo(protocollo);
		if(dipartimento!=null) {
			params.setPrefissoCodicePagamento(dipartimento.getEnte().getPrefissoCodicePagamento());
			params.setNodoCodicePagamento(dipartimento.getEnte().getNodoCodicePagamento());
		}
		params.setFatturazioneAttiva(true);
		params.setFormatoArchivioInvioFattura(this.isP7M ? FormatoArchivioInvioFatturaType.P7M : FormatoArchivioInvioFatturaType.XML);
		params.setIdentificativoSdI(identificativo);
		params.setMessageId(identificativo+"");
		params.setNomeFile(nomeFile);
		params.setRaw(this.original);
		params.setXml(this.decoded);

		this.lottoConverter = FatturaDeserializerUtils.getLottoConverter(params);
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
	
	public boolean isFirmaNecessaria() throws InserimentoLottiException {
		return this.isFirmaNecessaria(this.lottoConverter.getLottoFatture().getCodiceDestinatario());
	}
	
	private boolean isFirmaNecessaria(String codiceDestinatario) throws InserimentoLottiException {
		
		DominioType dominio = getDominio(codiceDestinatario);
		SottodominioType sottodominio = getSottodominio(codiceDestinatario);
		return dominio.toString().equals(DominioType.PA.toString()) || (sottodominio != null && sottodominio.toString().equals(SottodominioType.ESTERO.toString()));
	}

	private CODICE getCodiceErroreNonFirmato(String codiceDestinatario) throws InserimentoLottiException {
		SottodominioType sottodominio = getSottodominio(codiceDestinatario);
		if(sottodominio != null && sottodominio.equals(SottodominioType.ESTERO)) {
			return CODICE.ERRORE_FILE_ESTERO_NON_FIRMATO;				
		}
		return CODICE.ERRORE_FILE_NON_FIRMATO;
	}

	public ILottoConverter getLottoConverter() {
		return lottoConverter;
	}

}

