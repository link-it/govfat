package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

public class InserimentoLottoResponse {

	public enum ESITO {OK, KO}
	
	private ESITO esito;
	private String dettaglio;
	
	public ESITO getEsito() {
		return esito;
	}
	public void setEsito(ESITO esito) {
		this.esito = esito;
	}
	public String getDettaglio() {
		return dettaglio;
	}
	public void setDettaglio(String dettaglio) {
		this.dettaglio = dettaglio;
	}
}
