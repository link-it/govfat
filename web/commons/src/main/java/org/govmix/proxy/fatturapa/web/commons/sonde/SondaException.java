package org.govmix.proxy.fatturapa.web.commons.sonde;

public class SondaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SondaException(String msg) {
		super(msg);
	}
	
	public SondaException(String msg, Exception e) {
		super(msg, e);
	}
	

}
