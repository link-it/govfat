/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.timers.policies;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.web.timers.utils.BatchProperties;

public class PolicyRispedizioneFactory {

	public static IPolicyRispedizione getPolicyRispedizione(FatturaElettronica fattura) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		policy.setFattore(BatchProperties.getInstance().getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(BatchProperties.getInstance().getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(fattura.getTentativiConsegna()+1);
		policy.setParams(params);
		return policy;

	}

	public static IPolicyRispedizione getPolicyRispedizione(TracciaSDI tracciaSdI) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		policy.setFattore(BatchProperties.getInstance().getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(BatchProperties.getInstance().getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(tracciaSdI.getTentativiProtocollazione()+1);
		policy.setParams(params);
		return policy;

	}

	public static IPolicyRispedizione getPolicyRispedizioneWFM(LottoFatture lotto) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		policy.setFattore(BatchProperties.getInstance().getFattoreRispedizioneWFM());
		policy.setMaxTentativiRispedizione(BatchProperties.getInstance().getMaxTentativiRispedizioneWFM());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(lotto.getTentativiConsegna()+1);
		policy.setParams(params);
		return policy;

	}

	public static IPolicyRispedizione getPolicyRispedizioneSdI(LottoFatture lotto) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		policy.setFattore(BatchProperties.getInstance().getFattoreRispedizioneSdI());
		policy.setMaxTentativiRispedizione(BatchProperties.getInstance().getMaxTentativiRispedizioneSdI());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(lotto.getTentativiConsegna()+1);
		policy.setParams(params);
		return policy;

	}

	public static IPolicyRispedizione getPolicyRispedizione(NotificaEsitoCommittente notifica) throws Exception {
		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
		policy.setFattore(BatchProperties.getInstance().getFattoreRispedizione());
		policy.setMaxTentativiRispedizione(BatchProperties.getInstance().getMaxTentativiRispedizione());
		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
		params.setTentativi(notifica.getTentativiConsegnaSdi()+1);
		policy.setParams(params);
		return policy;

	}
//
//	public static IPolicyRispedizione getPolicyRispedizione(NotificaDecorrenzaTermini notifica) throws Exception {
//		PolicyRispedizioneRetry policy = new PolicyRispedizioneRetry();
//		policy.setFattore(BatchProperties.getInstance().getFattoreRispedizione());
//		policy.setMaxTentativiRispedizione(BatchProperties.getInstance().getMaxTentativiRispedizione());
//		PolicyRispedizioneParameters params = new PolicyRispedizioneParameters();
//		params.setTentativi(notifica.getTentativiConsegna()+1);
//		policy.setParams(params);
//		return policy;
//	}

}
