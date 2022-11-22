package org.govmix.proxy.fatturapa.web.commons.recuperaFatture;

public class ProtocolloKey {
	private String numero;
	private Integer anno;
	private String registro;
	public static ProtocolloKey fromString(String protocolloString) throws Exception {
		String[] split = protocolloString.split("/");
		if(split.length != 3) {
			throw new Exception("Protocollo non valido, atteso formato numero / anno / registro");
		}
		
		return new ProtocolloKey(split[0], Integer.parseInt(split[1]), split[2]);
	}
	
	public ProtocolloKey(String numero, Integer anno, String registro) {
		this.numero = numero;
		this.anno = anno;
		this.registro = registro;
	}
	
	@Override
	public String toString() {
		return this.numero + "/" + this.anno + "/" + this.registro;

	}

	public String getNumero() {
		return numero;
	}

	public Integer getAnno() {
		return anno;
	}

	public String getRegistro() {
		return registro;
	}
	
}
