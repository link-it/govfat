package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.List;

import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;

public class InserimentoLotti {

	private FatturaAttivaBD fatturaAttivaBD;
	
	public InserimentoLotti(FatturaAttivaBD fatturaAttivaBD) {
		this.fatturaAttivaBD = fatturaAttivaBD;
	}
	
	public InserimentoLottoResponse inserisciLotto(List<InserimentoLottoRequest> request) {
		//TODO
		return null;
	}

	public InserimentoLottoResponse inserisciLottoSoloConservazione(List<InserimentoLottoSoloConservazioneRequest> request) {
		//TODO
		return null;
	}
}
