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

import java.util.Map;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;

public class PolicyRispedizioneFactory {

	private Map<String, PolicyRispedizioneConfig> params;
	
	private PolicyRispedizioneFactory() {} //per usare solo il getinstance
	public static void init(Map<String, PolicyRispedizioneConfig> params) {
		instance = new PolicyRispedizioneFactory();
		instance.setParams(params);
	}

	private static PolicyRispedizioneFactory instance;
	public static PolicyRispedizioneFactory getInstance() throws Exception {
		if(instance == null) {
			instance = new PolicyRispedizioneFactory();
		}
//			throw new Exception("Instance not initialized");
		return instance;
	}
	
	public IPolicyRispedizione getPolicyRispedizione(FatturaElettronica fattura) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		
		PolicyRispedizioneConfig config = getConfig("default");
		
		policy.setFattore(config.getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(config.getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(fattura.getTentativiConsegna()+1);
		policy.setParams(params);
		return policy;

	}

	private PolicyRispedizioneConfig getConfig(String configName) {
		if(params == null || !params.containsKey(configName)) {
			
			if(params!=null && params.containsKey("default")) {
				return params.get("default");
			}
			
			PolicyRispedizioneConfig config = new PolicyRispedizioneConfig();
			config.setFattoreRispedizione(5);
			config.setMaxTentativiRispedizione(5);
			return config;
		} else {
			return params.get(configName);
		}
	}

	public IPolicyRispedizione getPolicyRispedizione(TracciaSDI tracciaSdI) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		
		PolicyRispedizioneConfig config = getConfig("default");

		policy.setFattore(config.getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(config.getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(tracciaSdI.getTentativiProtocollazione()+1);
		policy.setParams(params);
		return policy;

	}

	public IPolicyRispedizione getPolicyRispedizioneWFM(LottoFatture lotto) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		PolicyRispedizioneConfig config = getConfig("WFM");

		policy.setFattore(config.getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(config.getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(lotto.getTentativiConsegna()+1);
		policy.setParams(params);
		return policy;

	}

	public IPolicyRispedizione getPolicyRispedizioneSdI(LottoFatture lotto) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		PolicyRispedizioneConfig config = getConfig("SDI");

		policy.setFattore(config.getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(config.getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(lotto.getTentativiConsegna()+1);
		policy.setParams(params);
		return policy;

	}

	public IPolicyRispedizione getPolicyRispedizione(NotificaEsitoCommittente notifica) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		PolicyRispedizioneConfig config = getConfig("default");

		policy.setFattore(config.getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(config.getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(notifica.getTentativiConsegnaSdi()+1);
		policy.setParams(params);
		return policy;

	}

	public void setParams(Map<String, PolicyRispedizioneConfig> params) {
		this.params = params;
	}

}
