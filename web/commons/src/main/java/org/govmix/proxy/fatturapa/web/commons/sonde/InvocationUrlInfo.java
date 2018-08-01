/**
 * 
 */
package org.govmix.proxy.fatturapa.web.commons.sonde;

/**
 * @author Bussu Giovanni (bussu@link.it)
 * @author  $Author: bussu $
 * @version $ Rev: 12563 $, $Date: 07 mag 2018 $
 * 
 */
public class InvocationUrlInfo {

	private String url;
	private String username;
	private String password;

	public InvocationUrlInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
