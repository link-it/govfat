package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

public class InserimentoLottoSoloConservazioneRequest extends InserimentoLottoRequest {

	private String numeroProtocollo;
	private String annoProtocollo;
	private String registroProtocollo;
	
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}
	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public String getRegistroProtocollo() {
		return registroProtocollo;
	}
	public void setRegistroProtocollo(String registroProtocollo) {
		this.registroProtocollo = registroProtocollo;
	}
	
	
}
