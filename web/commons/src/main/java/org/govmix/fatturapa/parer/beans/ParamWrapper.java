package org.govmix.fatturapa.parer.beans;

public class ParamWrapper {

	private String key;
	private String value;
	
	public ParamWrapper(String key, String value) {
		this.key = key;
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
