/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package org.govmix.proxy.fatturapa.web.api.timers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSSignedData;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.web.api.EndpointPdDImpl;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.openspcoop2.protocol.sdi.constants.SDICostanti;

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
			connection = DAOFactory.getInstance(log).getConnection();
			LottoBD lottoBD = new LottoBD(log, connection, false);

			Date limitDate = new Date();
			this.log.info("Cerco lotti di fatture da inserire");
			long countFatture = lottoBD.countLottiDaInserire(limitDate);
			this.log.info("Trovati ["+countFatture+"] lotti di fatture da inserire");
			long countFattureElaborate = 0; 

			if(countFatture > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countFatture+"] lotti di fatture da inserire, ["+this.limit+"] alla volta");
				List<LottoFatture> lstLotti = lottoBD.getLottiDaInserire(limitDate, 0, this.limit);

				it.gov.fatturapa.sdi.fatturapa.v1_0.utils.serializer.JaxbDeserializer deserializer10 = new it.gov.fatturapa.sdi.fatturapa.v1_0.utils.serializer.JaxbDeserializer();
				it.gov.fatturapa.sdi.fatturapa.v1_0.utils.serializer.JaxbSerializer serializer10 = new it.gov.fatturapa.sdi.fatturapa.v1_0.utils.serializer.JaxbSerializer();
				it.gov.fatturapa.sdi.fatturapa.v1_0.ObjectFactory of10 = new it.gov.fatturapa.sdi.fatturapa.v1_0.ObjectFactory();

				it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbDeserializer deserializer11 = new it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbDeserializer();
				it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbSerializer serializer11 = new it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbSerializer();
				it.gov.fatturapa.sdi.fatturapa.v1_1.ObjectFactory of11 = new it.gov.fatturapa.sdi.fatturapa.v1_1.ObjectFactory();

				EndpointPdDImpl end = new EndpointPdDImpl();

				while(countFattureElaborate < countFatture) {
					try {
						for(LottoFatture lotto : lstLotti) {
							
							this.log.info("Lotto XML [Inserimento fattura]: " + (new String(lotto.getXml())));
							
							byte[] lottoXML = null;
							String nomeFile = null;
							if(FormatoArchivioInvioFatturaType.P7M.equals(lotto.getFormatoArchivioInvioFattura())) {
								ByteArrayInputStream is = null;
								try {
									is =new ByteArrayInputStream(lotto.getXml());
									this.log.info("Estrazione del lotto di fatture, ora in formato P7M.");
									CMSSignedData cmsSignedData = new CMSSignedData(is);
									lottoXML = (byte[]) cmsSignedData.getSignedContent().getContent();
									this.log.info("Estrazione del lotto di fatture, ora in formato P7M, completata.");
									
									this.log.info("Lotto XML dopo lo sbustamento della firma. [Inserimento fattura]: " + (new String(lottoXML)));
								} finally {
									if(is != null){
										try {is.close();} catch(Exception e) {}
									}
								}
								
								nomeFile = lotto.getNomeFile().replace(SDICostanti.SDI_FATTURA_ESTENSIONE_P7M, SDICostanti.SDI_FATTURA_ESTENSIONE_XML);
							} else {
								lottoXML = lotto.getXml();
								nomeFile = lotto.getNomeFile();
							}

							
							if(lotto.getFormatoTrasmissione().equals(FormatoTrasmissioneType.SDI10)) {
								
								
								it.gov.fatturapa.sdi.fatturapa.v1_0.FatturaElettronicaType fattura = deserializer10.readFatturaElettronicaType(lottoXML);
								
								for (int i = 0; i < fattura.sizeFatturaElettronicaBodyList(); i++) {
									
									it.gov.fatturapa.sdi.fatturapa.v1_0.FatturaElettronicaType fatturaSingola = 
											new it.gov.fatturapa.sdi.fatturapa.v1_0.FatturaElettronicaType();
									fatturaSingola.setVersione(fattura.getVersione());
									fatturaSingola.setFatturaElettronicaHeader(fattura.getFatturaElettronicaHeader());
									fatturaSingola.addFatturaElettronicaBody(fattura.getFatturaElettronicaBody(i));
									
									
									byte[] xml = serializer10.toByteArray(of10.createFatturaElettronica(fatturaSingola));
									
									inserisciFattura(end, lotto, (i+1), nomeFile, xml);
								}
							} else {
								it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaType fattura = deserializer11.readFatturaElettronicaType(lottoXML);
								
								for (int i = 0; i < fattura.sizeFatturaElettronicaBodyList(); i++) {
									
									it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaType fatturaSingola = 
											new it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaType();
									fatturaSingola.setVersione(fattura.getVersione());
									fatturaSingola.setFatturaElettronicaHeader(fattura.getFatturaElettronicaHeader());
									fatturaSingola.addFatturaElettronicaBody(fattura.getFatturaElettronicaBody(i));
									
									byte[] xml = serializer11.toByteArray(of11.createFatturaElettronica(fatturaSingola));
									
									inserisciFattura(end, lotto, (i+1), nomeFile, xml);

								}
							}
							IdLotto idLotto = new IdLotto();
							idLotto.setIdentificativoSdi(lotto.getIdentificativoSdi());
							
							lottoBD.setProcessato(idLotto);

							countFattureElaborate++;
						}
						this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da inserire");

						lstLotti = lottoBD.getLottiDaInserire(limitDate, 0, this.limit);

						connection.commit();
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch InserimentoFattura: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestiti ["+countFattureElaborate+"\\"+countFatture+"] lotti di fatture da inserire. Fine.");
				connection.setAutoCommit(true);
			}

		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}

	}

	public void inserisciFattura(EndpointPdDImpl end, LottoFatture lotto,
			int posizione, String nomeFile, byte[] xml) {
		InputStream stream = null;
		try {
			stream = new ByteArrayInputStream(xml);
			Response response = end.postConsegnaFattura(lotto.getFormatoTrasmissione().getValue(), 
					posizione, 
					lotto.getIdentificativoSdi(),
					nomeFile, 
					lotto.getMessageId(), 
					lotto.getCodiceDestinatario(), 
					lotto.getCedentePrestatoreDenominazione(), 
					lotto.getCedentePrestatoreNome(), 
					lotto.getCedentePrestatoreCognome(), 
					lotto.getCedentePrestatoreCodiceFiscale(), 
					lotto.getCedentePrestatoreCodice(), 
					lotto.getCedentePrestatorePaese(), 
					lotto.getCessionarioCommittenteDenominazione(), 
					lotto.getCessionarioCommittenteNome(), 
					lotto.getCessionarioCommittenteCognome(), 
					lotto.getCessionarioCommittenteCodiceFiscale(), 
					lotto.getCessionarioCommittenteCodice(), 
					lotto.getCessionarioCommittentePaese(), 
					lotto.getTerzoIntermediarioOSoggettoEmittenteDenominazione(), 
					lotto.getTerzoIntermediarioOSoggettoEmittenteNome(), 
					lotto.getTerzoIntermediarioOSoggettoEmittenteCognome(), 
					lotto.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(), 
					lotto.getTerzoIntermediarioOSoggettoEmittenteCodice(), 
					lotto.getTerzoIntermediarioOSoggettoEmittentePaese(), 
					stream);
			if(response.getStatus() >=299) {
				this.log.error("Errore durante l'inserimento della fattura con idSdI ["+lotto.getIdentificativoSdi()+"] e posizione ["+posizione+"]:" + response.getStatus());
			}
		} finally {
			if(stream != null) {
				try {stream.close();} catch (Exception e) {}
			}
		}
	}

}
