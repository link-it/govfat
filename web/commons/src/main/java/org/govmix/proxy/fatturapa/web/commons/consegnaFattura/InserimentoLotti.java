package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.SottodominioType;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.AllegatoFatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFattureAttiveBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse.ESITO;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.ILottoConverter;
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
			FatturaAttivaBD fatturaBD = new FatturaAttivaBD(this.log, connection, false);
			AllegatoFatturaBD allegatoBD = new AllegatoFatturaBD(this.log, connection, false);
			List<IdLotto> lstIdentificativoEfatt = new ArrayList<IdLotto>();
			
			for(InserimentoLottoRequest request: requestList) {
				
				Long identificativo = generaIdentificativo(lottoBD);

				Dipartimento dipartimento = this.getDipartimento(request.getNomeFile(), request.getDipartimento());

				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), identificativo, dipartimento, dipartimento.getCodice(), null, null, this.log);
				ILottoConverter lottoConverter = analizer.getLottoConverter();
				LottoFatture lotto = lottoConverter.getLottoFatture();
				if(!this.checkCodiceProcedimento(lotto, dipartimento)) {
					throw new InserimentoLottiException(CODICE.ERRORE_CODICE_PROCEDIMENTO, request.getNomeFile(), request.getDipartimento(), lotto.getFormatoTrasmissione());
				}

				insertLotto(lottoConverter, lottoBD, fatturaBD, allegatoBD);
				
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

	private void insertLotto(ILottoConverter lottoConverter, LottoBD lottoBD, FatturaAttivaBD fatturaBD, AllegatoFatturaBD allegatoBD) throws Exception {
		
		LottoFatture lotto = lottoConverter.getLottoFatture();
		this.log.info("Inserimento del Lotto con identificativo ["+lotto.getIdentificativoSdi()+"]...");
		lottoBD.create(lotto);	
		this.log.info("Inserimento del Lotto con identificativo ["+lotto.getIdentificativoSdi()+"] completato");
		
		List<String> ids = lottoConverter.getIdentificativiInterniFatture();
		for(String key: ids) {
			FatturaElettronica fatturaElettronica = lottoConverter.getFatturaElettronica(key);
			List<AllegatoFattura> allegatiLst = lottoConverter.getAllegati(key);

			IdFattura idFattura = fatturaBD.convertToId(fatturaElettronica);
			fatturaBD.create(fatturaElettronica);

			if(allegatiLst != null) {
				for(AllegatoFattura allegato: allegatiLst) {
					allegato.setIdFattura(idFattura);
					allegatoBD.create(allegato);
				}
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
			
			LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), 1l, dipartimento, dipartimento.getCodice(), null, null, this.log);
			LottoFatture lotto = analizer.getLottoConverter().getLottoFatture();
			if(!this.checkCodiceProcedimento(lotto, this.getDipartimento(request.getNomeFile(), request.getDipartimento()))) {
				throw new InserimentoLottiException(CODICE.ERRORE_CODICE_PROCEDIMENTO, request.getNomeFile(), request.getDipartimento(), lotto.getFormatoTrasmissione());
			}
			
		}
	}

	public void checkLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> requestList) throws InserimentoLottiException {
		for(InserimentoLottoSoloConservazioneRequest request: requestList) {

			LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), 1l, null, request.getDipartimento(), StatoElaborazioneType.SOLO_CONSERVAZIONE, null, this.log);
			
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

			LottoFattureAttiveBD lottoBD = new LottoFattureAttiveBD(this.log, connection, false);
			FatturaAttivaBD fatturaBD = new FatturaAttivaBD(this.log, connection, false);
			AllegatoFatturaBD allegatoBD = new AllegatoFatturaBD(this.log, connection, false);
			List<IdLotto> lstIdentificativoEfatt = new ArrayList<IdLotto>();

			for(InserimentoLottoSoloConservazioneRequest request: requestList) {
				Long identificativo = generaIdentificativo(lottoBD);

				String protocollo = request.getNumeroProtocollo() + "/" + request.getAnnoProtocollo() + "/" + request.getRegistroProtocollo();
				LottoFattureAnalyzer analizer = new LottoFattureAnalyzer(request.getXml(), request.getNomeFile(), identificativo, null, request.getDipartimento(), StatoElaborazioneType.SOLO_CONSERVAZIONE, protocollo, this.log);
				ILottoConverter lottoConverter = analizer.getLottoConverter();
				LottoFatture lotto = lottoConverter.getLottoFatture();

				if(!analizer.isFirmato()) {
					throw new InserimentoLottiException(CODICE.ERRORE_FILE_NON_FIRMATO_CONSERVAZIONE, request.getNomeFile(), request.getDipartimento());
				}

				insertLotto(lottoConverter, lottoBD, fatturaBD, allegatoBD);
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
	
	private static synchronized Long generaIdentificativo(LottoFattureAttiveBD lottoBD) throws Exception {
		
		Integer idSdI = Math.abs(new BigInteger(UUID.randomUUID().toString().replaceAll("-", ""), 16).intValue());
		IdLotto idLotto = lottoBD.newIdLotto();
		idLotto.setIdentificativoSdi(idSdI.longValue());
		while(lottoBD.exists(idLotto)) {
			idSdI = Math.abs(new BigInteger(UUID.randomUUID().toString().replaceAll("-", ""), 16).intValue());
			idLotto = lottoBD.newIdLotto();
			idLotto.setIdentificativoSdi(idSdI.longValue());
		}
		return idSdI.longValue();

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
