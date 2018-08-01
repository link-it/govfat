package org.govmix.pcc.utils;

import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.fatturapa.web.commons.sonde.InvocationUrlInfo;
import org.govmix.proxy.pcc.fatture.utils.PccProperties;

public class ServletSonda extends org.govmix.proxy.fatturapa.web.commons.sonde.ServletSonda {

	public ServletSonda() throws Exception {
		super(getUrlList());
	}

	private static List<InvocationUrlInfo> getUrlList() {
		List<InvocationUrlInfo> urls = new ArrayList<InvocationUrlInfo>();
		urls.add(new InvocationUrlInfo(PccProperties.getInstance().getUrlWsdlFattureWS(), PccProperties.getInstance().getUserWsdlFattureWS(), PccProperties.getInstance().getPasswordWsdlFattureWS()));
		urls.add(new InvocationUrlInfo(PccProperties.getInstance().getUrlWsdlTracceWS(), PccProperties.getInstance().getUserWsdlTracceWS(), PccProperties.getInstance().getPasswordWsdlTracceWS()));
		return urls;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
