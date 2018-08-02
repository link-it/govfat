/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.FatturaBean;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaBean;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaInput;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaLottoInput;
import org.govmix.fatturapa.parer.builder.FatturaAttivaSingolaUnitaDocumentariaBuilder;
import org.govmix.fatturapa.parer.builder.FatturaPassivaMultiplaUnitaDocumentariaBuilder;
import org.govmix.fatturapa.parer.builder.FatturaPassivaSingolaUnitaDocumentariaBuilder;
import org.govmix.fatturapa.parer.builder.LottoFattureUnitaDocumentariaBuilder;
import org.govmix.fatturapa.parer.client.ParERClient;
import org.govmix.fatturapa.parer.client.ParERResponse;
import org.govmix.fatturapa.parer.client.ParERResponse.STATO;
import org.govmix.fatturapa.parer.utils.ChiaveRapportoType;
import org.govmix.fatturapa.parer.utils.ConservazioneProperties;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdSip;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.AllegatoFatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaDecorrenzaTerminiBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaEsitoCommittenteBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.SIPBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;


/**
 * Implementazione dell'interfaccia {@link TimerConsegnaFattura}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerReinvioConservazioneLib extends AbstractTimerLib {


	public TimerReinvioConservazioneLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			LottoBD lottoBD = new LottoBD(log, connection, false);
			FatturaBD fatturaElettronicaBD = new FatturaBD(log, connection, false);
			AllegatoFatturaBD allegatoBD = new AllegatoFatturaBD(log, connection, false);
			SIPBD sipBD = new SIPBD(log, connection, false);
			NotificaEsitoCommittenteBD notificaEsitoCommittenteBD = new NotificaEsitoCommittenteBD(log, connection, false);
			NotificaDecorrenzaTerminiBD notificaDecorrenzaTerminiBD = new NotificaDecorrenzaTerminiBD(log, connection, false);

			ConservazioneProperties props = ConservazioneProperties.getInstance();
			ParERClient client = new ParERClient(this.log, props);
			LottoFattureUnitaDocumentariaBuilder builderLotto = new LottoFattureUnitaDocumentariaBuilder(this.log);
			FatturaPassivaSingolaUnitaDocumentariaBuilder builderFatturaPassivaSingola = new FatturaPassivaSingolaUnitaDocumentariaBuilder(this.log);
			FatturaPassivaMultiplaUnitaDocumentariaBuilder builderFatturaPassivaMultipla = new FatturaPassivaMultiplaUnitaDocumentariaBuilder(this.log);
			FatturaAttivaSingolaUnitaDocumentariaBuilder builderFatturaAttivaSingola = new FatturaAttivaSingolaUnitaDocumentariaBuilder(this.log);
			int offset = 0;
			
			int LIMIT_STEP = Math.min(this.limit, 500);
			
			List<LottoFatture> lottoList = null;//TODO lottoBD.getLottiDaConservare(new Date(), 0, LIMIT_STEP);
			
			for(LottoFatture lotto: lottoList) {
				//TODO creare unita doc con builderLotto
				
				//find su db fatture con quell'identificativo sdi e fatturazione attiva
				//foreach:
				// creare unita doc con builderFatturaPassivaMultipla
				//spedire
				//aggiornare sip e fatture


				
				//spedire
				//aggiornare sip
			}

			List<FatturaElettronica> fatturaList = null;//TODO search fatture da conservare (saranno sicuramente solo singole
			
			for(FatturaElettronica fattura: fatturaList) {
				
				//SOLO se attiva:
				// se posizione > 2 mettere in stato ERRORE_CONSEGNA
				// creare unita doc con builderFatturaAttivaSingola

				//SOLO se passiva:
				// creare unita doc con builderFatturaPassivaSingola
				
				
				//spedire
				//aggiornare sip e fattura

			}
//			
//			while(idFatturePassiveList != null && !idFatturePassiveList.isEmpty() && offset < this.limit) {
//				this.log.info("Trovati ["+idFatturePassiveList.size()+"] lotti di fatture da conservare");
//				for(LottoFatture id: idFatturePassiveList) {
//					UnitaDocumentariaLottoInput input = this.getUnitaDocumentariaLotto(props, id);
//					
//					if(input.getFattureLst() == null || input.getFattureLst().isEmpty()) {
//						throw new Exception("Errore durante la migrazione del lotto con identificativo SdI["+id.getIdentificativoSdi()+"]. Fatture non trovate");
//					}
//					
//					if(input.getFattureLst().size() > 1) {
//						UnitaDocumentariaBean build = builderLotto.build(input);
//						ParERResponse inviaLottoResponse = client.invia(build);
//						
//						ChiaveRapportoType chiaveSIPLotto = getChiaveRapporto(builderLotto.getChiave(input), input.getLotto().getCodiceDestinatario());
//						if(inviaLottoResponse.getStato().equals(STATO.OK)) {
//							sipBD.update(input.getLotto().getIdSIP(), inviaLottoResponse.getRapportoVersamento(), StatoConsegnaType.CONSEGNATA, chiaveSIPLotto.getNumero(), chiaveSIPLotto.getAnno(), chiaveSIPLotto.getTipoRegistro());
//						} else {
//							sipBD.update(input.getLotto().getIdSIP(), inviaLottoResponse.getRapportoVersamento(), StatoConsegnaType.ERRORE_CONSEGNA, chiaveSIPLotto.getNumero(), chiaveSIPLotto.getAnno(), chiaveSIPLotto.getTipoRegistro());
//						}
//
//						for(FatturaBean fattura: input.getFattureLst()) {
//							if(fattura.getFattura().getEsito() == null || fattura.getFattura().getEsito().equals(EsitoType.INVIATA_ACCETTATO)) {	
//								UnitaDocumentariaFatturaInput fatturaInput = this.getUnitaDocumentariaFattura(props, fattura, input.getLotto());
//								UnitaDocumentariaBean build2 = builderFatturaPassivaMultipla.build(fatturaInput);
//								ParERResponse response = client.invia(build2);
//								
//								ChiaveRapportoType chiaveSIP = getChiaveRapporto(builderFatturaPassivaMultipla.getChiave(fatturaInput), fatturaInput.getLotto().getCodiceDestinatario()));
//								if(response.getStato().equals(STATO.OK)) {
//									sipBD.update(fattura.getFattura().getIdSIP(), response.getRapportoVersamento(), StatoConsegnaType.CONSEGNATA, chiaveSIP.getNumero(), chiaveSIP.getAnno(), chiaveSIP.getTipoRegistro());
//								} else {
//									sipBD.update(fattura.getFattura().getIdSIP(), response.getRapportoVersamento(), StatoConsegnaType.ERRORE_CONSEGNA, chiaveSIP.getNumero(), chiaveSIP.getAnno(), chiaveSIP.getTipoRegistro());
//								}
//
//							}
//						}
//						
//					} else {
//						FatturaBean fattura = input.getFattureLst().get(0);
//						UnitaDocumentariaFatturaInput fatturaInput = this.getUnitaDocumentariaFattura(props, fattura, input.getLotto());
//						UnitaDocumentariaBean build2 = builderFatturaPassivaSingola.build(fatturaInput);
//						ParERResponse response = client.invia(build2);
//						
//						ChiaveRapportoType chiaveSIP = getChiaveRapporto(builderFatturaPassivaSingola.getChiave(fatturaInput), fatturaInput.getLotto().getCodiceDestinatario()));
//						if(response.getStato().equals(STATO.OK)) {
//							sipBD.update(fattura.getFattura().getIdSIP(), response.getRapportoVersamento(), StatoConsegnaType.CONSEGNATA, chiaveSIP.getNumero(), chiaveSIP.getAnno(), chiaveSIP.getTipoRegistro());
//						} else {
//							sipBD.update(fattura.getFattura().getIdSIP(), response.getRapportoVersamento(), StatoConsegnaType.ERRORE_CONSEGNA, chiaveSIP.getNumero(), chiaveSIP.getAnno(), chiaveSIP.getTipoRegistro());
//						}
//
//					}
//				}
//				offset += idFatturePassiveList.size();
//				idFatturePassiveList = lottoBD.getLottiDaConservare(new Date(), 0, LIMIT_STEP);
//			}

		}catch(Exception e){
			this.log.error("Errore durante l'esecuzione del batch ReinvioConservazione: "+e.getMessage(), e);
			connection.rollback();
			throw e;
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}

	}

//	private void aggiornaStatoParER(SIPBD sipBD, IdSip id, ParERResponse response, ChiaveRapportoType chiaveSIP) throws Exception {
//		if(response.getStato().equals(STATO.OK)) {
//			sipBD.update(id, response.getRapportoVersamento(), StatoConsegnaType.CONSEGNATA, chiaveSIP.getNumero(), chiaveSIP.getAnno(), chiaveSIP.getTipoRegistro());
//		} else {
//			sipBD.update(id, response.getRapportoVersamento(), StatoConsegnaType.ERRORE_CONSEGNA, chiaveSIP.getNumero(), chiaveSIP.getAnno(), chiaveSIP.getTipoRegistro());
//		}
//	}


}
