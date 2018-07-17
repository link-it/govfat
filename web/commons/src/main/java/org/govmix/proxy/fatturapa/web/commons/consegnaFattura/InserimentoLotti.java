package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFattureAttiveBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse.ESITO;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;

public class InserimentoLotti {

	private Logger log;
	private Map<String, Dipartimento> dipartimenti;
	
	public InserimentoLotti(Logger log) throws Exception {
		this.log = log;
	}
	
	public InserimentoLottoResponse inserisciLotto(List<InserimentoLottoRequest> requestList) {
		InserimentoLottoResponse inserimentoLottoResponse = new InserimentoLottoResponse();

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();

			connection.setAutoCommit(false);
			LottoFattureAttiveBD lottoBD = new LottoFattureAttiveBD(log, connection, false);
			ConsegnaFattura consegnaFattura = new ConsegnaFattura(log, false, connection, false);
			List<IdLotto> lstIdentificativoEfatt = new ArrayList<IdLotto>();
			
			for(InserimentoLottoRequest request: requestList) {
				String type = null;
				if(request.getNomeFile().toLowerCase().endsWith("xml")) {
					if(!this.getDipartimento(request.getNomeFile(), request.getDipartimento()).getFirmaAutomatica()){
						throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO, request.getNomeFile(), request.getDipartimento());
					}
					type= "XML";
				} else if(request.getNomeFile().toLowerCase().endsWith("p7m")) {
					if(this.getDipartimento(request.getNomeFile(), request.getDipartimento()).getFirmaAutomatica()){
						throw new InserimentoLottiException(CODICE.ERRORE_FILE_FIRMATO, request.getNomeFile(), request.getDipartimento());
					}
					type= "P7M";
				} else {
					throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, request.getNomeFile());
				}

				LottoFatture lotto = getLotto(request, lottoBD, type);
				lotto.setStatoElaborazioneInUscita(StatoElaborazioneType.PRESA_IN_CARICO);
				
				insertLotto(lotto, lottoBD, consegnaFattura);
				IdLotto idLotto = new IdLotto(lotto.isFatturazioneAttiva());
				idLotto.setIdentificativoSdi(lotto.getIdentificativoSdi());
				lstIdentificativoEfatt.add(idLotto);
			}
			
			connection.commit();
			
			inserimentoLottoResponse.setEsito(ESITO.OK);
			inserimentoLottoResponse.setLstIdentificativoEfatt(lstIdentificativoEfatt);
			
		} catch(InserimentoLottiException e) {
			if(connection != null) try {connection.rollback();} catch(Exception ex) {}
			
			this.log.error("Errore durante il caricamento dei lotti: " + e.getMessage(), e);
			inserimentoLottoResponse.setEsito(ESITO.KO);
			inserimentoLottoResponse.setEccezione(e);
		} catch(Exception e) {
			if(connection != null) try {connection.rollback();} catch(Exception ex) {}
			
			this.log.error("Errore durante il caricamento dei lotti: " + e.getMessage(), e);
			inserimentoLottoResponse.setEsito(ESITO.KO);
			inserimentoLottoResponse.setEccezione(new InserimentoLottiException(CODICE.ERRORE_GENERICO, e.getMessage()));
		} finally {
			if(connection != null) try {connection.close();} catch(Exception ex) {}
		}

		return inserimentoLottoResponse;
	}

	private void insertLotto(LottoFatture lotto, LottoBD lottoBD, ConsegnaFattura consegnaFattura) throws Exception {
		this.log.info("Inserimento del Lotto con identificativo ["+lotto.getIdentificativoSdi()+"]...");
		lottoBD.create(lotto);	
		this.log.info("Inserimento del Lotto con identificativo ["+lotto.getIdentificativoSdi()+"] completato");
		
		
		byte[] lottoXML = ConsegnaFatturaUtils.getLottoXml(lotto, this.log);
		String nomeFile = ConsegnaFatturaUtils.getNomeLottoXml(lotto, this.log);

		
		
		List<byte[]> fattureLst =ConsegnaFatturaUtils.getXmlWithSDIUtils(lottoXML);
		
		for (int i = 0; i < fattureLst.size(); i++) {

			if(fattureLst.get(i) == null) {
				throw new Exception("La fattura ricevuta in ingresso e' null");
			}
			
			ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(lotto, (i+1), nomeFile, fattureLst.get(i));
			consegnaFattura.consegnaFattura(params);
		}

	}

	public void checkLotto(List<InserimentoLottoRequest> requestList) throws InserimentoLottiException {
		for(InserimentoLottoRequest request: requestList) {
			Dipartimento dipartimento = null;
			
			dipartimento = this.getDipartimento(request.getNomeFile(), request.getDipartimento());
			
			if(!dipartimento.getFatturazioneAttiva()) {
				throw new InserimentoLottiException(CODICE.ERRORE_DIPARTIMENTO_NON_ABILITATO);
			}
			
			if(request.getNomeFile().toLowerCase().endsWith("xml")) {
				if(!dipartimento.getFirmaAutomatica()){
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO, request.getNomeFile(), request.getDipartimento());
				}
			} else if(request.getNomeFile().toLowerCase().endsWith("p7m")) {
				if(dipartimento.getFirmaAutomatica()){
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_FIRMATO, request.getNomeFile(), request.getDipartimento());
				}
			} else {
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, request.getNomeFile());
			}
		}
	}

	public void checkLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> requestList) throws InserimentoLottiException {
		for(InserimentoLottoSoloConservazioneRequest request: requestList) {
			if(request.getNomeFile().toLowerCase().endsWith("xml")) {
				throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO_CONSERVAZIONE, request.getNomeFile());
			} else if(request.getNomeFile().toLowerCase().endsWith("p7m")) {
				this.getDipartimento(request.getNomeFile(), request.getDipartimento()); //check esistenza del dipartimento
			} else {
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, request.getNomeFile());
			}
		}
	}

	public InserimentoLottoResponse inserisciLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> requestList) {
		InserimentoLottoResponse inserimentoLottoResponse = new InserimentoLottoResponse();
		
		Connection connection = null;
		
		try {
			connection = DAOFactory.getInstance().getConnection();
			connection.setAutoCommit(false);

			LottoFattureAttiveBD lottoBD = new LottoFattureAttiveBD(log, connection, false);
			ConsegnaFattura consegnaFattura = new ConsegnaFattura(log, false, connection, false);
			List<IdLotto> lstIdentificativoEfatt = new ArrayList<IdLotto>();

			for(InserimentoLottoSoloConservazioneRequest request: requestList) {
				String type = "P7M";
				if(request.getNomeFile().toLowerCase().endsWith("xml")) {
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO_CONSERVAZIONE, request.getNomeFile());
				} else if(request.getNomeFile().toLowerCase().endsWith("p7m")) {
					this.getDipartimento(request.getNomeFile(), request.getDipartimento()); //check esistenza del dipartimento
				} else {
					throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, request.getNomeFile());
				}

				LottoFatture lotto = getLotto(request, lottoBD, type);
				lotto.setStatoElaborazioneInUscita(StatoElaborazioneType.SOLO_CONSERVAZIONE);
				lotto.setProtocollo(request.getNumeroProtocollo() + "/" + request.getAnnoProtocollo() + "/" + request.getRegistroProtocollo());
				
				insertLotto(lotto, lottoBD, consegnaFattura);
				IdLotto idLotto = new IdLotto(lotto.isFatturazioneAttiva());
				idLotto.setIdentificativoSdi(lotto.getIdentificativoSdi());
				lstIdentificativoEfatt.add(idLotto);
			}
			connection.commit();
			inserimentoLottoResponse.setEsito(ESITO.OK);
			inserimentoLottoResponse.setLstIdentificativoEfatt(lstIdentificativoEfatt);

		} catch(InserimentoLottiException e) {
			if(connection != null) try {connection.rollback();} catch(Exception ex) {}

			this.log.error("Errore durante il caricamento dei lotti: " + e.getMessage(), e);
			inserimentoLottoResponse.setEsito(ESITO.KO);
			inserimentoLottoResponse.setEccezione(e);
		} catch(Exception e) {
			if(connection != null) try {connection.rollback();} catch(Exception ex) {}
			
			this.log.error("Errore durante il caricamento dei lotti: " + e.getMessage(), e);
			inserimentoLottoResponse.setEsito(ESITO.KO);
			inserimentoLottoResponse.setEccezione(new InserimentoLottiException(CODICE.ERRORE_GENERICO, e.getMessage()));
		} finally {
			if(connection != null) try {connection.close();} catch(Exception ex) {}
		}
		
		return inserimentoLottoResponse;
	}
	
	private LottoFatture getLotto(InserimentoLottoRequest req, LottoFattureAttiveBD lottoBD, String type) throws Exception {
		
		
		ConsegnaFatturaParameters params = null;
		Integer identificativo = generaIdentificativo(lottoBD);
		String messageId = identificativo + "";

		try {

			try {
				params = ConsegnaFatturaUtils.getParameters(identificativo, req.getNomeFile(),
								type, null,
								messageId,
								true,
								req.getXml());
			} catch(Exception e) {
				if(req instanceof InserimentoLottoSoloConservazioneRequest) {
					params = ConsegnaFatturaUtils.getParameters(identificativo, req.getNomeFile(),
							"XML", null,
							messageId,
							true,
							req.getXml());
				} else {
					throw e;
				}
			}
			
			params.validate(true);
		} catch(Exception e) {
			this.log.error("Errore durante il caricamento del lotto con nome file ["+req.getNomeFile()+"]: " + e.getMessage(), e);
			throw new InserimentoLottiException(CODICE.PARAMETRI_NON_VALIDI, req.getNomeFile());
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

		lotto.setCodiceDestinatario(req.getDipartimento());
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

		return lotto;
	}

	private static synchronized Integer generaIdentificativo(LottoFattureAttiveBD lottoBD) throws Exception {
		
		Integer idSdI = Math.abs(new BigInteger(UUID.randomUUID().toString().replaceAll("-", ""), 16).intValue());
		IdLotto idLotto = lottoBD.newIdLotto();
		idLotto.setIdentificativoSdi(idSdI);
		while(lottoBD.exists(idLotto)) {
			idSdI = Math.abs(new BigInteger(UUID.randomUUID().toString().replaceAll("-", ""), 16).intValue());
			idLotto = lottoBD.newIdLotto();
			idLotto.setIdentificativoSdi(idSdI);
		}
		return idSdI;

	}
	
	private Dipartimento getDipartimento(String nomeFile, String codice) throws InserimentoLottiException {
		if(this.dipartimenti != null && this.dipartimenti.containsKey(codice)) {
			return this.dipartimenti.get(codice);
		}
		throw new InserimentoLottiException(CODICE.ERRORE_DIPARTIMENTO_NON_TROVATO, nomeFile, codice);
	}
	
	public void setDipartimenti(List<Dipartimento> dipartimenti) {
		this.dipartimenti = new HashMap<String, Dipartimento>();
		if(dipartimenti != null) {
			for(Dipartimento dipartimento: dipartimenti) {
				this.dipartimenti.put(dipartimento.getCodice(), dipartimento);
			}
		}
	}
}
