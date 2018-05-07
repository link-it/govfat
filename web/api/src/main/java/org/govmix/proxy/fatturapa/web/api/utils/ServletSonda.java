package org.govmix.proxy.fatturapa.web.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.pcc.fatture.utils.PccProperties;

public class ServletSonda extends org.govmix.proxy.fatturapa.web.commons.sonde.ServletSonda {

	public ServletSonda() throws Exception {
		super(getUrlList());
	}

	private static List<String> getUrlList() {
		List<String> urls = new ArrayList<String>();
//		urls.add(PccProperties.getInstance().getUrlWadlEndpointEnte()); TODO enable
		urls.add(PccProperties.getInstance().getUrlWadlEndpointPdd());
		return urls;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
