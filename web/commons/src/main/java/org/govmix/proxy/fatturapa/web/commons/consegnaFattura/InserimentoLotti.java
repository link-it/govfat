package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.List;

import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse.ESITO;

public class InserimentoLotti {

	private FatturaAttivaBD fatturaAttivaBD;
	
	public InserimentoLotti(FatturaAttivaBD fatturaAttivaBD) {
		this.fatturaAttivaBD = fatturaAttivaBD;
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
