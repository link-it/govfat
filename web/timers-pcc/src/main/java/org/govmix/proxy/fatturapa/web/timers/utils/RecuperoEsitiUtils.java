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
package org.govmix.proxy.fatturapa.web.timers.utils;

import org.govmix.pcc.fatture.TipoOperazioneTipo;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;
import org.govmix.proxy.fatturapa.orm.constants.NomePccOperazioneType;
import org.govmix.proxy.pcc.wsclient.PccWsClient;

public class RecuperoEsitiUtils {

	public static void recuperaEsiti(PccTraccia traccia, PccWsClient client) throws Exception {
		PccTracciaTrasmissione tracciaTrasmissione = getTracciaTrasmissione(traccia);
		String oper = traccia.getOperazione();
		
		NomePccOperazioneType op = NomePccOperazioneType.toEnumConstant(oper);
		
		switch(op) {
		case DATI_FATTURA: client.wSQueryDatiFattura(traccia, tracciaTrasmissione);
			break;
		case INSERIMENTO_FATTURA: client.wSQueryInserimentoFattura(traccia, tracciaTrasmissione);
			break;
		case PAGAMENTO_IVA: client.wSQueryPagamentoIva(traccia, tracciaTrasmissione);
			break;
		case OPERAZIONE_CONTABILE_CCS: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.CCS);
		break;
		case OPERAZIONE_CONTABILE_CPS: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.CO);
		break;
		case OPERAZIONE_CONTABILE_CO: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.CO);
		break;
		case OPERAZIONE_CONTABILE_CP: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.CP);
		break;
		case OPERAZIONE_CONTABILE_CS: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.CS);
		break;
		case OPERAZIONE_CONTABILE_CSPC: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.CS);
		break;
		case OPERAZIONE_CONTABILE_RC: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.RC);
		break;
		case OPERAZIONE_CONTABILE_RF: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.RF);
		break;
		case OPERAZIONE_CONTABILE_SC: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.SC);
		break;
		case OPERAZIONE_CONTABILE_SP: client.wSQueryOperazioneContabile(traccia, tracciaTrasmissione, TipoOperazioneTipo.SP);
		break;
		default:
			break;
		}
	}
	
	public static void rispedisciProxy(PccTraccia traccia, PccWsClient client) throws Exception {
		String oper = traccia.getOperazione();
		NomePccOperazioneType op = NomePccOperazioneType.toEnumConstant(oper); 
		
		
		traccia.setIdPaTransazioneRispedizione(traccia.getIdPaTransazione() + "_" + System.currentTimeMillis());
		
		
		switch(op) {
		case DATI_FATTURA: client.wSProxyDatiFattura(traccia);
		break;
		case INSERIMENTO_FATTURA: client.wSProxyInserimentoFattura(traccia);
		break;
		case PAGAMENTO_IVA: client.wSProxyPagamentoIva(traccia);
		break;
		case OPERAZIONE_CONTABILE_CCS:
		case OPERAZIONE_CONTABILE_CPS:
		case OPERAZIONE_CONTABILE_CO:
		case OPERAZIONE_CONTABILE_CP:
		case OPERAZIONE_CONTABILE_CS:
		case OPERAZIONE_CONTABILE_CSPC:
		case OPERAZIONE_CONTABILE_RC:
		case OPERAZIONE_CONTABILE_RF:
		case OPERAZIONE_CONTABILE_SC:
		case OPERAZIONE_CONTABILE_SP: client.wSProxyOperazioneContabile(traccia);
		break;
		default:
			break;
		}
	}
	
	private static PccTracciaTrasmissione getTracciaTrasmissione(PccTraccia traccia) {
		PccTracciaTrasmissione trasmissione = null;
		if(traccia.getPccTracciaTrasmissioneList() != null) {
			for(PccTracciaTrasmissione trasm: traccia.getPccTracciaTrasmissioneList()) {
				if(trasmissione == null || trasm.getGdo().after(trasmissione.getGdo())){
					trasmissione = trasm;
				}
			}
		}
		return trasmissione;
	}
}
