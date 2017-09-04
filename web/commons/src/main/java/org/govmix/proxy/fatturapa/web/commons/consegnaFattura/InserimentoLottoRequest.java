package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

public class InserimentoLottoRequest {

	private String nomeFile;
	private byte[] xml;
	private String dipartimento;
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public byte[] getXml() {
		return xml;
	}
	public void setXml(byte[] xml) {
		this.xml = xml;
	}
	public String getDipartimento() {
		return dipartimento;
	}
	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}
	
}
