package org.govmix.fatturapa.parer.client;

public class ParERResponse {

	public enum STATO {OK, KO, ERRORE_CONNESSIONE};
	
	private STATO stato;
	private String rapportoVersamento;
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
}
