package org.govmix.fatturapa.parer.client;

public class ParERResponse {

	public enum STATO {OK, KO, DUPLICATO, ERRORE_CONNESSIONE, ERRORE_TIMEOUT};
	
	private STATO stato;
	private String rapportoVersamento;
	private String esitoVersamento;
	
	public String getRapportoVersamento() {
		return rapportoVersamento;
	}
	public void setRapportoVersamento(String rapportoVersamento) {
		this.rapportoVersamento = rapportoVersamento;
	}
	public STATO getStato() {
		return stato;
	}
	public void setStato(STATO stato) {
		this.stato = stato;
	}
	public String getEsitoVersamento() {
		return esitoVersamento;
	}
	public void setEsitoVersamento(String esitoVersamento) {
		this.esitoVersamento = esitoVersamento;
	}
}
