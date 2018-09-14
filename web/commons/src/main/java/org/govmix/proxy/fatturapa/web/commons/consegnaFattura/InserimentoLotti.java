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

				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), identificativo, request.getDipartimento(), this.log);
				
				Dipartimento dipartimento = this.getDipartimento(request.getNomeFile(), request.getDipartimento());
				
				if(analizer.isFirmato()) {
					if(dipartimento.getFirmaAutomatica()){
						throw new InserimentoLottiException(CODICE.ERRORE_FILE_FIRMATO, request.getNomeFile(), request.getDipartimento());
					}
				} else {
					if(!dipartimento.getFirmaAutomatica()){
						throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO, request.getNomeFile(), request.getDipartimento());
					}
				}

				LottoFatture lotto = analizer.getLotto();
				if(dipartimento.getModalitaPush()) {
					lotto.setStatoElaborazioneInUscita(StatoElaborazioneType.PRESA_IN_CARICO);
				} else {
					if(!analizer.isFirmato()) {
						throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO, request.getNomeFile(), request.getDipartimento());
					}
					
					lotto.setStatoElaborazioneInUscita(StatoElaborazioneType.PROTOCOLLATA);
				}
				checkCodiceProcedimento(lotto, dipartimento);
				
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
	
	private void checkCodiceProcedimento(LottoFatture lotto, Dipartimento dipartimento) throws InserimentoLottiException {
		if(lotto.getDominio().toString().equals(DominioType.PA.toString()) || (lotto.getSottodominio()!= null && lotto.getSottodominio().toString().equals(SottodominioType.ESTERO.toString()))) {
			if(dipartimento.getIdProcedimento() == null)
				throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO);
			
			return;
		} else if(lotto.getDominio().toString().equals(DominioType.B2B.toString()) && (lotto.getSottodominio()== null || !lotto.getSottodominio().toString().equals(SottodominioType.ESTERO.toString()))) {
			if(dipartimento.getIdProcedimentoB2B() == null)
				return;
			else 
				return;
		} else {
			throw new InserimentoLottiException(CODICE.ERRORE_IDENTIFICAZIONE_CODICE_PROCEDIMENTO);
		}
					
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

			try {
				
				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), 1, request.getDipartimento(), this.log);
				
				if(analizer.isFirmato()) {
					if(this.getDipartimento(request.getNomeFile(), request.getDipartimento()).getFirmaAutomatica()){
						throw new InserimentoLottiException(CODICE.ERRORE_FILE_FIRMATO, request.getNomeFile(), request.getDipartimento());
					}
				} else {
					if(!this.getDipartimento(request.getNomeFile(), request.getDipartimento()).getFirmaAutomatica()){
						throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO, request.getNomeFile(), request.getDipartimento());
					}
				}
				
				checkCodiceProcedimento(analizer.getLotto(), dipartimento);

			} catch(Exception e) {
				throw new InserimentoLottiException(CODICE.ERRORE_FORMATO_FILE, request.getNomeFile());
			}
		}
	}

	public void checkLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> requestList) throws InserimentoLottiException {
		for(InserimentoLottoSoloConservazioneRequest request: requestList) {

			try {
				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), 1, request.getDipartimento(), this.log);
				
				if(!analizer.isFirmato()) {
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO, request.getNomeFile(), request.getDipartimento());
				}
				
				Dipartimento dipartimento = this.getDipartimento(request.getNomeFile(), request.getDipartimento()); //check esistenza del dipartimento
				checkCodiceProcedimento(analizer.getLotto(), dipartimento);

			} catch(Exception e) {
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

				Integer identificativo = generaIdentificativo(lottoBD);

				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), identificativo, request.getDipartimento(), this.log);
				
				if(!analizer.isFirmato()) {
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO, request.getNomeFile(), request.getDipartimento());
				}

				Dipartimento dipartimento = this.getDipartimento(request.getNomeFile(), request.getDipartimento()); //check esistenza del dipartimento

				LottoFatture lotto = analizer.getLotto();
				lotto.setStatoElaborazioneInUscita(StatoElaborazioneType.SOLO_CONSERVAZIONE);
				lotto.setProtocollo(request.getNumeroProtocollo() + "/" + request.getAnnoProtocollo() + "/" + request.getRegistroProtocollo());
				
				checkCodiceProcedimento(analizer.getLotto(), dipartimento);

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
