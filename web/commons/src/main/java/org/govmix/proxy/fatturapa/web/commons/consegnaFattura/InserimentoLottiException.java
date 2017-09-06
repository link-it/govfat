package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

public class InserimentoLottiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum CODICE {ERRORE_GENERICO, PARAMETRI_NON_VALIDI, ERRORE_FILE_FIRMATO, ERRORE_FILE_NON_FIRMATO, ERRORE_FORMATO_FILE, ERRORE_FILE_NON_FIRMATO_CONSERVAZIONE}
	
	private CODICE codice;
	private Object[] params;
	
	public InserimentoLottiException(CODICE codice, Object ... params) {
		this.codice = codice;
		this.setParams(params);
	}
	
	public InserimentoLottiException(CODICE codice, String messaggio, Object ... params) {
		super(messaggio);
		this.codice = codice;
		this.setParams(params);
	}

	public CODICE getCodice() {
		return codice;
	}

	public void setCodice(CODICE codice) {
		this.codice = codice;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

}
