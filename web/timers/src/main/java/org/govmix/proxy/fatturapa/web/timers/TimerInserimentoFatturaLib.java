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
package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFatturePassiveBD;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFattura;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaUtils;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.InvioNotifica;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.NotificaECRequest;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.NotificaECResponse;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.govmix.proxy.fatturapa.web.timers.utils.BatchProperties;
import org.openspcoop2.generic_project.exception.ValidationException;

/**
 * Implementazione dell'interfaccia {@link TimerInserimentoFattura}
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerInserimentoFatturaLib extends AbstractTimerLib {


	public TimerInserimentoFatturaLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
	}

	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();
			BatchProperties properties = BatchProperties.getInstance(); 

			ConsegnaFattura consegnaFattura = new ConsegnaFattura(log, properties.isValidazioneDAOAbilitata(), connection, false);
			LottoFatturePassiveBD lottoBD = new LottoFatturePassiveBD(log, connection, false);

			Date limitDate = new Date();
			this.log.info("Cerco lotti di fatture da inserire");
			long countFatture = lottoBD.countLottiDaInserire(limitDate);
			this.log.info("Trovati ["+countFatture+"] lotti di fatture da inserire");
			long countFattureElaborate = 0; 

			if(countFatture > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countFatture+"] lotti di fatture da inserire, ["+this.limit+"] alla volta");
				List<LottoFatture> lstLotti = lottoBD.getLottiDaInserire(limitDate, 0, this.limit);

				while(countFattureElaborate < countFatture) {
					try {
						for(LottoFatture lotto : lstLotti) {
							
							IdLotto idLotto =  lottoBD.convertToId(lotto);

							try{ 
								byte[] lottoXML = ConsegnaFatturaUtils.getLottoXml(lotto, this.log);
								String nomeFile = ConsegnaFatturaUtils.getNomeLottoXml(lotto, this.log);

								
								
								List<byte[]> fattureLst =ConsegnaFatturaUtils.getXmlWithSDIUtils(lottoXML); //ConsegnaFatturaUtils.getXmlWithSerializer(lotto.getFormatoTrasmissione(), lottoXML, ...);
								
								for (int i = 0; i < fattureLst.size(); i++) {
									inserisciFattura(consegnaFattura, lotto, (i+1), nomeFile, fattureLst.get(i));
								}
								
								lottoBD.setProcessato(idLotto);
								connection.commit();

							} catch(ValidationException e) {
								//NOTA: in caso di errore oltre che effettuare il rollback il lotto viene marcato come in errore, e viene inviata una notifica di rifiuto d'ufficio al fornitore
								connection.rollback();
								this.log.error("Errore di validazione durante l'inserimento del lotto con identificativo SdI ["+lotto.getIdentificativoSdi()+"]: "+e.getMessage(), e);

								if(properties.isRifiutoAutomaticoAbilitato()) {
									
									try {
										InvioNotifica invioNotifica = new InvioNotifica(properties.getRicezioneEsitoURL(), properties.getRicezioneEsitoUsername(), properties.getRicezioneEsitoPassword());
										this.log.info("Invio della notifica di rifiuto automatico per il lotto ["+lotto.getIdentificativoSdi()+"]...");
										
										NotificaECRequest request = new NotificaECRequest();

										NotificaEsitoCommittente notifica = new NotificaEsitoCommittente();
										notifica.setIdentificativoSdi(lotto.getIdentificativoSdi());
										notifica.setMessageIdCommittente(lotto.getMessageId());
										notifica.setEsito(org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType.EC02);
										notifica.setDescrizione("Lotto rifiutato d'ufficio in quanto non conforme alle specifiche");
										request.setNotifica(notifica);
										NotificaECResponse invioNotificaResponse = invioNotifica.invia(request);
										
										this.log.info("Invio della notifica di rifiuto automatico per il lotto ["+lotto.getIdentificativoSdi()+"] completato. Return code ["+invioNotificaResponse.getEsitoChiamata()+"]");
										
										if(invioNotificaResponse.getEsitoChiamata() == 202 || invioNotificaResponse.getEsitoChiamata() == 200) { //solo se lo SdI accetta la notifica (o se c'e' uno scarto)
											lottoBD.setProcessato(idLotto, true); //processiamo il lotto con errore
										}
									} catch(Exception eInterna) {
										this.log.warn("Errore durante la spedizione dell'esito negativo per il lotto ["+lotto.getIdentificativoSdi()+"]: "+eInterna.getMessage()+". Non aggiorno lo stato del lotto", eInterna);
										//Se ci sono eccezioni nell'invio dell'esito non aggiorno il lotto, ma alla prossima tornata del batch verra' ritentato l'inserimento e l'eventuale esito
										try {connection.rollback();} catch(Exception ex) {}
									}
								} else {
									lottoBD.setProcessato(idLotto, true); //processiamo il lotto con errore
								}
							} catch(Exception e) {
								connection.rollback();
								this.log.error("Errore durante l'inserimento del lotto con identificativo SdI ["+lotto.getIdentificativoSdi()+"]: "+e.getMessage(), e);

								try{
									lottoBD.setProcessato(idLotto, true); //processiamo il lotto con errore
								} catch(Exception ex) {
									this.log.warn("Errore durante l'aggiornamento del lotto con identificativo SdI ["+lotto.getIdentificativoSdi()+"]: "+ex.getMessage(), e);
								}
							}
							countFattureElaborate++;
						}
						this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da inserire");

						lstLotti = lottoBD.getLottiDaInserire(limitDate, 0, this.limit);
						
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch InserimentoFattura: "+e.getMessage(), e);
//						connection.rollback();
					}
				}
				this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da inserire. Fine.");
//				connection.setAutoCommit(true);
			}

		}catch (Throwable e) {
			log.error(e);
			throw new Exception(e);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}

	}

	public void inserisciFattura(ConsegnaFattura consegnaFattura, LottoFatture lotto,
			int posizione, String nomeFile, byte[] xml) throws ValidationException, Exception {
		
		try {

			ConsegnaFatturaParameters params = ConsegnaFatturaUtils.getParameters(lotto, posizione, nomeFile, xml);
			if(xml == null) {
				throw new ValidationException("La fattura ricevuta in ingresso e' null");
			}

			consegnaFattura.consegnaFattura(params);
		} catch(Exception e) {
			this.log.error("riceviFattura completata con errore per il lotto["+lotto.getIdentificativoSdi()+"] posizione ["+posizione+"]:"+ e.getMessage(), e);
			throw e;
		}
	}

}
