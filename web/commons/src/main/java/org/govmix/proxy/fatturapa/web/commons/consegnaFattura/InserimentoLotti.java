package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.SottodominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFattureAttiveBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse.ESITO;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.openspcoop2.generic_project.exception.ValidationException;

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
			LottoFattureAttiveBD lottoBD = new LottoFattureAttiveBD(this.log, connection, false);
			ConsegnaFattura consegnaFattura = new ConsegnaFattura(this.log, false, connection, false);
			List<IdLotto> lstIdentificativoEfatt = new ArrayList<IdLotto>();
			
			for(InserimentoLottoRequest request: requestList) {
				
				Integer identificativo = generaIdentificativo(lottoBD);

				Dipartimento dipartimento = this.getDipartimento(request.getNomeFile(), request.getDipartimento());

				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), identificativo, dipartimento, dipartimento.getCodice(), this.log);
				
				if(!this.checkCodiceProcedimento(analizer.getLotto(), dipartimento)) {
					throw new InserimentoLottiException(CODICE.ERRORE_CODICE_PROCEDIMENTO, request.getNomeFile(), request.getDipartimento(), analizer.getLotto().getFormatoTrasmissione());
				}

				if(analizer.isFirmato()) {
					if(dipartimento.getFirmaAutomatica()){
						throw new InserimentoLottiException(analizer.getCodiceErroreFirmato(), request.getNomeFile(), request.getDipartimento());
					}
				} else {
					if(!dipartimento.getFirmaAutomatica() && analizer.isFirmaNecessaria()){
						throw new InserimentoLottiException(analizer.getCodiceErroreNonFirmato(), request.getNomeFile(), request.getDipartimento());
					}
				}

				LottoFatture lotto = analizer.getLotto();
				if(dipartimento.getModalitaPush()) {
					lotto.setStatoElaborazioneInUscita(StatoElaborazioneType.PRESA_IN_CARICO);
				} else {
					if(!analizer.isFirmato()  && analizer.isFirmaNecessaria()) {
						throw new InserimentoLottiException(analizer.getCodiceErroreNonFirmato(), request.getNomeFile(), request.getDipartimento());
					}
					
					lotto.setStatoElaborazioneInUscita(StatoElaborazioneType.DA_INVIARE_ALLO_SDI);
				}

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
			try {
				ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(lotto, (i+1), nomeFile, fattureLst.get(i));
				consegnaFattura.consegnaFattura(params);
			} catch(ValidationException e) {
				throw new InserimentoLottiException(CODICE.PARAMETRI_NON_VALIDI, nomeFile);
			}
		}

	}

	private boolean checkCodiceProcedimento(LottoFatture lotto, Dipartimento dipartimento) {
		if(!dipartimento.isModalitaPush())
			return true; //senza modalita push i codici procedimento sono ininfluenti
		
		if(lotto.getDominio().toString().equals(DominioType.PA.toString()) || (lotto.getSottodominio()!= null && lotto.getSottodominio().toString().equals(SottodominioType.ESTERO.toString()))) {
			return dipartimento.getIdProcedimento() != null;
		} else {
			return dipartimento.getIdProcedimentoB2B() != null;
		}
					
	}


	public void checkLotto(List<InserimentoLottoRequest> requestList) throws InserimentoLottiException {
		for(InserimentoLottoRequest request: requestList) {
			Dipartimento dipartimento = this.getDipartimento(request.getNomeFile(), request.getDipartimento());
			
			LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), 1, dipartimento, dipartimento.getCodice(), this.log);
			
			if(!this.checkCodiceProcedimento(analizer.getLotto(), this.getDipartimento(request.getNomeFile(), request.getDipartimento()))) {
				throw new InserimentoLottiException(CODICE.ERRORE_CODICE_PROCEDIMENTO, request.getNomeFile(), request.getDipartimento(), analizer.getLotto().getFormatoTrasmissione());
			}
			
			if(analizer.isFirmato()) {
				if(this.getDipartimento(request.getNomeFile(), request.getDipartimento()).getFirmaAutomatica()){
					throw new InserimentoLottiException(analizer.getCodiceErroreFirmato(), request.getNomeFile(), request.getDipartimento());
				}
			} else {
				if(!this.getDipartimento(request.getNomeFile(), request.getDipartimento()).getFirmaAutomatica() && analizer.isFirmaNecessaria()){
					throw new InserimentoLottiException(analizer.getCodiceErroreNonFirmato(), request.getNomeFile(), request.getDipartimento());
				}
			}
		}
	}

	public void checkLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> requestList) throws InserimentoLottiException {
		for(InserimentoLottoSoloConservazioneRequest request: requestList) {

			LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), 1, null, request.getDipartimento(), this.log);
			
			if(!analizer.isFirmato()) {
				throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO_CONSERVAZIONE, request.getNomeFile(), request.getDipartimento());
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
				Integer identificativo = generaIdentificativo(lottoBD);

				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), identificativo, null, request.getDipartimento(), this.log);
				
				if(!analizer.isFirmato()) {
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO_CONSERVAZIONE, request.getNomeFile(), request.getDipartimento());
				}


				LottoFatture lotto = analizer.getLotto();
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
			Dipartimento dipartimento = this.dipartimenti.get(codice);
			if(!dipartimento.getFatturazioneAttiva())
				throw new InserimentoLottiException(CODICE.ERRORE_DIPARTIMENTO_NON_ABILITATO, nomeFile, codice);
			
			return dipartimento;
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
