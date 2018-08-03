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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.govmix.fatturapa.parer.utils.ConservazioneProperties;
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.AllegatoFatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaDecorrenzaTerminiBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.NotificaEsitoCommittenteBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.SIPBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;


/**
 * Implementazione dell'interfaccia {@link TimerReinvioConservazione}.
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

			List<LottoFatture> lottoList = lottoBD.getLottiDaConservare(new Date(), offset, LIMIT_STEP);

			while(lottoList != null && !lottoList.isEmpty()) {
				this.log.debug("Trovati ["+lottoList.size()+"] lotti di fatture da conservare");

				for(LottoFatture lotto: lottoList) {
					boolean spedizione = true;
					StatoConsegnaType statoConsegna = null;
					String rapportoVersamento = null;
					ChiaveType chiave = null;
					UnitaDocumentariaLottoInput input = null;
					UnitaDocumentariaBean request = null;

					// 1. Creo Unita DOC
					try {
						input = this.getUnitaDocumentariaLotto(props, lotto, fatturaElettronicaBD, allegatoBD, notificaDecorrenzaTerminiBD, notificaEsitoCommittenteBD);
						request = builderLotto.build(input);
						chiave = request.getUnitaDocumentaria().getIntestazione().getChiave();
						
						this.log.debug("Numero Fatture presenti nel lotto ["+input.getFattureLst().size()+"] .");
						if(input.getFattureLst().size() == 0) {
							spedizione = false;
							statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
						}
					}catch (ServiceException e) {
						log.error("impossibile creare l'UnitaDocumentaria per il lotto ["+lotto.getIdentificativoSdi()+"].",e); 
						statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
						spedizione = false;
					}

					if(spedizione) {
						// Spedizione verso parer
						this.log.debug("Invio lotto ["+lotto.getIdentificativoSdi()+"] in conservazione in corso...");
						ParERResponse response = client.invia(request);
						this.log.debug("Invio lotto ["+lotto.getIdentificativoSdi()+"] in conservazione completata con esito ["+response.getStato()+"].");


						if(response.getStato().equals(STATO.OK)) {
							statoConsegna = StatoConsegnaType.CONSEGNATA; 
						} else {
							statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
							// TODO gestire riconsegna
						}
						rapportoVersamento = response.getRapportoVersamento();
					}

					// aggiornamento sip
					this.log.debug("Aggiornamento entry SIP su db per il lotto ["+lotto.getIdentificativoSdi()+"] in corso...");
					sipBD.update(lotto.getIdSIP(), rapportoVersamento, statoConsegna, chiave.getNumero(), chiave.getAnno(), chiave.getTipoRegistro());
					this.log.debug("Aggiornamento entry SIP su db per il lotto ["+lotto.getIdentificativoSdi()+"] completata.");

					List<FatturaBean> fatturaList = input != null ? input.getFattureLst() : null;
					while(fatturaList != null && !fatturaList.isEmpty()) {
						
						this.log.debug("Invio in conservazione delle fatture associate al lotto ["+lotto.getIdentificativoSdi()+"] in corso...");
						for(FatturaBean fatturaBean: fatturaList) {
							FatturaElettronica fattura = fatturaBean.getFattura();

							String numero = null;
							String registro = null;
							Integer anno = null;
							String rapportoVersamentoFat = null;
							StatoConsegnaType statoConsegnaFat = null;
							StatoConservazioneType statoConservazioneFat = null;
							ChiaveType chiaveFat = null;
							UnitaDocumentariaFatturaInput inputFat = null;
							UnitaDocumentariaBean requestFat = null;

							try {
								// creare unita doc con builderFatturaPassivaMultipla
								inputFat = this.getUnitaDocumentariaFatturaPassiva(props, fatturaBean); 
								requestFat = builderFatturaPassivaMultipla.build(inputFat);
								chiaveFat = request.getUnitaDocumentaria().getIntestazione().getChiave();
							}catch (ServiceException e) {
								log.error("impossibile creare l'UnitaDocumentaria per la fattura ["+fattura.getIdentificativoSdi()+"].",e); 
								statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
								statoConservazioneFat = StatoConservazioneType.ERRORE_CONSEGNA;
								spedizione = false;
							}

							// informazioni sulla chiave
							if(chiaveFat != null) {
								numero = chiave.getNumero();
								anno = chiave.getAnno();
								registro = chiave.getTipoRegistro();
							}

							if(spedizione) {
								this.log.debug("Invio fattura ["+fattura.getIdentificativoSdi()+"] in conservazione in corso...");
								ParERResponse responseFattura = client.invia(requestFat);
								this.log.debug("Invio fattura ["+fattura.getIdentificativoSdi()+"] in conservazione completata con esito ["+responseFattura.getStato()+"].");
								
								if(responseFattura.getStato().equals(STATO.OK)) {
									statoConsegnaFat = StatoConsegnaType.CONSEGNATA; 
									statoConservazioneFat = StatoConservazioneType.CONSERVAZIONE_COMPLETATA;
								} else {
									statoConsegnaFat = StatoConsegnaType.ERRORE_CONSEGNA;
									statoConservazioneFat = StatoConservazioneType.CONSERVAZIONE_FALLITA;
									// TODO gestire riconsegna
								}

								rapportoVersamentoFat = responseFattura.getRapportoVersamento();
							}

							//aggiornare sip e fattura in trnasazione
							boolean oldAutoCommit = connection.getAutoCommit();
							try {
								this.log.debug("Aggiornamento entry SIP su db per la fattura ["+fattura.getIdentificativoSdi()+"] in corso...");
								connection.setAutoCommit(false);
								sipBD.update(fattura.getIdSIP(), rapportoVersamentoFat, statoConsegnaFat, numero, anno, registro);
								fatturaElettronicaBD.updateStatoConservazione(fattura, statoConservazioneFat);
								connection.commit();
								this.log.debug("Aggiornamento entry SIP su db per la fattura ["+fattura.getIdentificativoSdi()+"] completata.");
							}catch(Exception e) {
								log.error("impossibile aggiornare la entry SIP su db per la fattura ["+fattura.getIdentificativoSdi()+"].",e); 
								connection.rollback();
							} finally {
								connection.setAutoCommit(oldAutoCommit);
							}
						}
						
						this.log.debug("Invio in conservazione delle fatture associate al lotto ["+lotto.getIdentificativoSdi()+"] completato.");
					}

				}
				// sposto l'offset
				offset += LIMIT_STEP;
				lottoList = lottoBD.getLottiDaConservare(new Date(), offset, LIMIT_STEP);
			}

			offset = 0;
			FatturaFilter filter = fatturaElettronicaBD.newFilter();
			FilterSortWrapper fsw = new FilterSortWrapper();
			fsw.setSortOrder(SortOrder.ASC);
			fsw.setField(FatturaElettronica.model().DATA_RICEZIONE); 
			filter.getFilterSortList().add(fsw );
			filter.getStatiConservazione().add(StatoConservazioneType.PRESA_IN_CARICO);
			filter.setOffset(offset);
			filter.setLimit(LIMIT_STEP); 
			List<FatturaElettronica> fatturaList = fatturaElettronicaBD.findAll(filter);

			while(fatturaList != null && !fatturaList.isEmpty()) {
				this.log.debug("Trovate ["+fatturaList.size()+"] fatture da conservare");
				
				for(FatturaElettronica fattura: fatturaList) {
					boolean spedizione = true;
					String numero = null;
					String registro = null;
					Integer anno = null;
					String rapportoVersamento = null;
					StatoConsegnaType statoConsegna = null;
					StatoConservazioneType statoConservazione = null;
					UnitaDocumentariaFatturaInput input = null;
					UnitaDocumentariaBean request = null;
					ChiaveType chiave = null;

					if(fattura.isFatturazioneAttiva()) {
						// creare unita doc con builderFatturaAttivaSingola
						try {
							input = this.getUnitaDocumentariaFatturaAttiva(props, fatturaElettronicaBD, allegatoBD, fattura);
							request = builderFatturaAttivaSingola.build(input);
							chiave = request.getUnitaDocumentaria().getIntestazione().getChiave();
						}catch (ServiceException e) {
							log.error("impossibile creare l'UnitaDocumentaria per la fattura ["+fattura.getIdentificativoSdi()+"].",e); 
							statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
							statoConservazione = StatoConservazioneType.ERRORE_CONSEGNA;
							spedizione = false;
						}

						// se posizione > 2 mettere in stato ERRORE_CONSEGNA
						if(fattura.getPosizione() > 1) {
							statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
							statoConservazione = StatoConservazioneType.ERRORE_CONSEGNA;
							spedizione = false;
						}
					} else {
						// creare unita doc con builderFatturaPassivaSingola
						try {
							input = this.getUnitaDocumentariaFatturaPassiva(props, toFatturaBean(fatturaElettronicaBD, allegatoBD, notificaDecorrenzaTerminiBD, notificaEsitoCommittenteBD, fattura));  
							request = builderFatturaPassivaSingola.build(input);
							chiave = request.getUnitaDocumentaria().getIntestazione().getChiave();
						}catch (ServiceException e) {
							log.error("impossibile creare l'UnitaDocumentaria per la fattura ["+fattura.getIdentificativoSdi()+"].",e); 
							statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
							statoConservazione = StatoConservazioneType.ERRORE_CONSEGNA;
							spedizione = false;
						}
					}
					// informazioni sulla chiave
					if(chiave != null) {
						numero = chiave.getNumero();
						anno = chiave.getAnno();
						registro = chiave.getTipoRegistro();
					}

					//spedire
					if(spedizione) {
						this.log.debug("Invio fattura ["+fattura.getIdentificativoSdi()+"] in conservazione in corso...");
						ParERResponse response = client.invia(request);
						this.log.debug("Invio fattura ["+fattura.getIdentificativoSdi()+"] in conservazione completata con esito ["+response.getStato()+"].");
						if(response.getStato().equals(STATO.OK)) {
							statoConsegna = StatoConsegnaType.CONSEGNATA; 
							statoConservazione = StatoConservazioneType.CONSERVAZIONE_COMPLETATA;
						} else {
							statoConsegna = StatoConsegnaType.ERRORE_CONSEGNA;
							statoConservazione = StatoConservazioneType.CONSERVAZIONE_FALLITA;
							// TODO gestire riconsegna
						}

						rapportoVersamento = response.getRapportoVersamento();
					}

					//aggiornare sip e fattura in trnasazione
					boolean oldAutoCommit = connection.getAutoCommit();
					try {
						this.log.debug("Aggiornamento entry SIP su db per la fattura ["+fattura.getIdentificativoSdi()+"] in corso...");
						connection.setAutoCommit(false);
						sipBD.update(fattura.getIdSIP(), rapportoVersamento, statoConsegna, numero, anno, registro);
						fatturaElettronicaBD.updateStatoConservazione(fattura, statoConservazione);
						connection.commit();
						this.log.debug("Aggiornamento entry SIP su db per la fattura ["+fattura.getIdentificativoSdi()+"] completata.");
					}catch(Exception e) {
						log.error("impossibile aggiornare la entry SIP su db per la fattura ["+fattura.getIdentificativoSdi()+"].",e); 
						connection.rollback();
					} finally {
						connection.setAutoCommit(oldAutoCommit);
					}
				}

				// sposto l'offset
				offset += LIMIT_STEP;
				filter.setOffset(offset);
				fatturaList = fatturaElettronicaBD.findAll(filter);
			}
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

	private UnitaDocumentariaFatturaInput getUnitaDocumentariaFatturaPassiva(ConservazioneProperties props, FatturaBean fatturaBean) throws Exception {

		UnitaDocumentariaFatturaInput input = new UnitaDocumentariaFatturaInput();
		input.setProperties(props);
		FatturaElettronica fattura = fatturaBean.getFattura();
		input.setFattura(fattura);
		input.setLotto(fattura.getLottoFatture());
		input.getAllegati().addAll(fatturaBean.getAllegati());
		input.setNotificaDT(fatturaBean.getDecorrenzaTermini());
		input.setNotificaEC(fatturaBean.getEsitoCommittente());

		return input;

	}



	private UnitaDocumentariaLottoInput getUnitaDocumentariaLotto(ConservazioneProperties props, LottoFatture lotto, FatturaBD fatturaElettronicaBD, AllegatoFatturaBD allegatoBD, NotificaDecorrenzaTerminiBD notificaDecorrenzaTerminiBD,
			NotificaEsitoCommittenteBD notificaEsitoCommittenteBD) throws Exception {
		UnitaDocumentariaLottoInput input = new UnitaDocumentariaLottoInput();
		input.setProperties(props);
		input.setLotto(lotto);
		
		this.log.debug("Lettura fatture associate al lotto ["+lotto.getIdentificativoSdi() +"] in corso...");
		
		FatturaFilter filter = fatturaElettronicaBD.newFilter();
		filter.setIdentificativoSdi(lotto.getIdentificativoSdi());
		filter.setFatturazioneAttiva(lotto.isFatturazioneAttiva());
		FilterSortWrapper fsw = new FilterSortWrapper();
		fsw.setSortOrder(SortOrder.ASC);
		fsw.setField(FatturaElettronica.model().DATA_RICEZIONE); 
		filter.getFilterSortList().add(fsw );
		List<FatturaElettronica> findAllFattureByIdentificativoSdi = fatturaElettronicaBD.findAll(filter);
		List<FatturaBean> fat = new ArrayList<FatturaBean>();

		for(FatturaElettronica fattura: findAllFattureByIdentificativoSdi) {
			FatturaBean bean = toFatturaBean(fatturaElettronicaBD, allegatoBD, notificaDecorrenzaTerminiBD,
					notificaEsitoCommittenteBD, fattura);


			fat.add(bean);
		}

		input.setFattureLst(fat);
		
		this.log.debug("Lettura fatture associate al lotto ["+lotto.getIdentificativoSdi() +"] completata");

		return input;

	}

	private FatturaBean toFatturaBean(FatturaBD fatturaElettronicaBD, AllegatoFatturaBD allegatoBD,
			NotificaDecorrenzaTerminiBD notificaDecorrenzaTerminiBD,
			NotificaEsitoCommittenteBD notificaEsitoCommittenteBD, FatturaElettronica fattura) throws ServiceException {
		FatturaBean bean = new FatturaBean();
		bean.setFattura(fattura);
		IdFattura idFattura = fatturaElettronicaBD.convertToId(fattura);
		bean.setAllegati(allegatoBD.getAllegati(idFattura));

		if(fattura.getIdDecorrenzaTermini() != null) {
			try {
				bean.setDecorrenzaTermini(notificaDecorrenzaTerminiBD.getNotificaDT(idFattura));
			} catch (NotFoundException e) {
				throw new ServiceException(e);
			}
		}

		if(fattura.getEsito() != null) {
			bean.setEsitoCommittente(notificaEsitoCommittenteBD.getNotificaEsitoCommittente(idFattura));
		}
		return bean;
	}

	private UnitaDocumentariaFatturaInput getUnitaDocumentariaFatturaAttiva(ConservazioneProperties props, FatturaBD fatturaElettronicaBD, AllegatoFatturaBD allegatoBD, FatturaElettronica fattura) throws Exception {

		IdFattura idFattura = fatturaElettronicaBD.convertToId(fattura);
		List<AllegatoFattura> allegati = allegatoBD.getAllegati(idFattura);

		UnitaDocumentariaFatturaInput input = new UnitaDocumentariaFatturaInput();
		input.setProperties(props);
		input.setFattura(fattura);
		input.setLotto(fattura.getLottoFatture());
		input.getAllegati().addAll(allegati);
		return input;

	}



}
