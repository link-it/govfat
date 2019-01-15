package org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente;

import org.govmix.proxy.fatturapa.orm.TracciaSDI;

import it.gov.fatturapa.sdi.messaggi.v1_0.ScartoEsitoCommittenteType;

public class NotificaECResponse {

	private TracciaSDI tracciaNotifica;
	private TracciaSDI tracciaScarto;
	private int esitoChiamata;
	public TracciaSDI getTracciaNotifica() {
		return tracciaNotifica;
	}
	public void setTracciaNotifica(TracciaSDI tracciaNotifica) {
		this.tracciaNotifica = tracciaNotifica;
	}
	public TracciaSDI getTracciaScarto() {
		return tracciaScarto;
	}
	public void setTracciaScarto(TracciaSDI tracciaScarto) {
		this.tracciaScarto = tracciaScarto;
	}
	public int getEsitoChiamata() {
		return esitoChiamata;
	}
	public void setEsitoChiamata(int esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}
	public ScartoEsitoCommittenteType getScarto() {
		return scarto;
	}
	public void setScarto(ScartoEsitoCommittenteType scarto) {
		this.scarto = scarto;
	}
	private ScartoEsitoCommittenteType scarto;

}
