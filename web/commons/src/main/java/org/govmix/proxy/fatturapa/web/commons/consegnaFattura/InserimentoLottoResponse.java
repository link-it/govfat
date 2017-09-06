package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

public class InserimentoLottoResponse {

	public enum ESITO {OK, KO}
	
	private ESITO esito;
	private InserimentoLottiException eccezione;
	
	public ESITO getEsito() {
		return esito;
	}
	public void setEsito(ESITO esito) {
		this.esito = esito;
	}
	public InserimentoLottiException getEccezione() {
		return eccezione;
	}
	public void setEccezione(InserimentoLottiException eccezione) {
		this.eccezione = eccezione;
	}
}
