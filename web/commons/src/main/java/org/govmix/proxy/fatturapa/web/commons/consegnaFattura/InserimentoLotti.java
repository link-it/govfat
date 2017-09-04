package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse.ESITO;

public class InserimentoLotti {

	private LottoBD lottoBD;
	
	public InserimentoLotti(Logger log) throws Exception {
		this.lottoBD = new LottoBD(log);
	}
	
	public InserimentoLottoResponse inserisciLotto(List<InserimentoLottoRequest> request) {
		InserimentoLottoResponse inserimentoLottoResponse = new InserimentoLottoResponse();
		inserimentoLottoResponse.setEsito(ESITO.OK);

		//TODO
		return inserimentoLottoResponse;
	}

	public InserimentoLottoResponse inserisciLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> request) {
		InserimentoLottoResponse inserimentoLottoResponse = new InserimentoLottoResponse();
		inserimentoLottoResponse.setEsito(ESITO.OK);
		//TODO
		return inserimentoLottoResponse;
	}
}
