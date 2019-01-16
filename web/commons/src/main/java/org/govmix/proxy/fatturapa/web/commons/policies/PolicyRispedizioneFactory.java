/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.govmix.proxy.fatturapa.web.commons.policies;

import java.util.HashMap;
import java.util.Map;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class PolicyRispedizioneFactory {

	private Map<String, PolicyRispedizioneConfig> params;
	
	private static final String DEFAULT_K = "default";
	private static final String WFM_K = "WFM";
	private static final String SDI_K = "SDI";
	private PolicyRispedizioneFactory() {
		try {
			CommonsProperties properties = CommonsProperties.getInstance(LoggerManager.getBatchStartupLogger());
			
			this.params = new HashMap<String, PolicyRispedizioneConfig>();
			PolicyRispedizioneConfig defaultConfig = new PolicyRispedizioneConfig();
			defaultConfig.setFattoreRispedizione(properties.getFattoreRispedizione());
			defaultConfig.setMaxTentativiRispedizione(properties.getMaxTentativiRispedizione());
			this.params.put(DEFAULT_K, defaultConfig);
			PolicyRispedizioneConfig wfmConfig = new PolicyRispedizioneConfig();
			wfmConfig.setFattoreRispedizione(properties.getFattoreRispedizioneWFM());
			wfmConfig.setMaxTentativiRispedizione(properties.getMaxTentativiRispedizioneWFM());
			this.params.put(WFM_K, wfmConfig);
			PolicyRispedizioneConfig sdiConfig = new PolicyRispedizioneConfig();
			sdiConfig.setFattoreRispedizione(properties.getFattoreRispedizioneSdI());
			sdiConfig.setMaxTentativiRispedizione(properties.getMaxTentativiRispedizioneSdI());
			this.params.put(SDI_K, sdiConfig);
		}catch(Exception e) {}
	}

	private static PolicyRispedizioneFactory instance;
	public static synchronized PolicyRispedizioneFactory getInstance() throws Exception {
		if(instance == null) {
			instance = new PolicyRispedizioneFactory();
		}
		return instance;
	}
	
	public IPolicyRispedizione getPolicyRispedizione(FatturaElettronica fattura) throws Exception {
		return getPolicyRispedizione(fattura.getTentativiConsegna(), DEFAULT_K);
	}

	public IPolicyRispedizione getPolicyRispedizione(TracciaSDI tracciaSdI) throws Exception {
		return getPolicyRispedizione(tracciaSdI.getTentativiProtocollazione(), DEFAULT_K);
	}

	public IPolicyRispedizione getPolicyRispedizione(NotificaEsitoCommittente notifica) throws Exception {
		return getPolicyRispedizione(notifica.getTentativiConsegnaSdi(), DEFAULT_K);
	}

	public IPolicyRispedizione getPolicyRispedizioneWFM(LottoFatture lotto) throws Exception {
		return getPolicyRispedizione(lotto.getTentativiConsegna(), WFM_K);
	}

	public IPolicyRispedizione getPolicyRispedizioneSdI(LottoFatture lotto) throws Exception {
		return getPolicyRispedizione(lotto.getTentativiConsegna(), SDI_K);
	}
	
	private IPolicyRispedizione getPolicyRispedizione(int tentativiConsegna, String configK) {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		PolicyRispedizioneConfig config = getConfig(configK);

		policy.setFattore(config.getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(config.getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(tentativiConsegna+1);
		policy.setParams(params);
		return policy;
	}


	private PolicyRispedizioneConfig getConfig(String configName) {
		if(this.params == null || !this.params.containsKey(configName)) {
			
			if(this.params!=null && this.params.containsKey("default")) {
				return this.params.get("default");
			}
			
			PolicyRispedizioneConfig config = new PolicyRispedizioneConfig();
			config.setFattoreRispedizione(5);
			config.setMaxTentativiRispedizione(5);
			return config;
		} else {
			return this.params.get(configName);
		}
	}

}
