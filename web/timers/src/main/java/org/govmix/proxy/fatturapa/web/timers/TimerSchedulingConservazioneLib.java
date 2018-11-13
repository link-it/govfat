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
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.SIP;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.SIPBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.openspcoop2.generic_project.beans.AliasField;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ServiceException;


/**
 * Implementazione dell'interfaccia {@link TimerSchedulingConservazione}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerSchedulingConservazioneLib extends AbstractTimerLib {


	public TimerSchedulingConservazioneLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
	}

	private IField[] getFieldsConservazione(FatturaBD fatturaBD) throws ServiceException {
		List<IField> fields = new ArrayList<IField>();

		FatturaElettronicaFieldConverter fieldConverter = new FatturaElettronicaFieldConverter(fatturaBD.getDatabaseType());

		String id = "id";
		try {
			fields.add(new CustomField(id, Long.class, id, fieldConverter.toTable(FatturaElettronica.model())));
			fields.add(FatturaElettronica.model().IDENTIFICATIVO_SDI);
			fields.add(FatturaElettronica.model().POSIZIONE);
			fields.add(FatturaElettronica.model().FATTURAZIONE_ATTIVA);
			fields.add(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE);
			fields.add(FatturaElettronica.model().CODICE_DESTINATARIO);
			fields.add(FatturaElettronica.model().ANNO);
			fields.add(FatturaElettronica.model().NUMERO);
			fields.add(FatturaElettronica.model().DATA_RICEZIONE);
			fields.add(FatturaElettronica.model().DATA);
			fields.add(FatturaElettronica.model().STATO_CONSERVAZIONE);
			fields.add(FatturaElettronica.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI);
			
			String idSipField = "id_sip";
			fields.add(new CustomField(idSipField, Long.class, idSipField, fieldConverter.toTable(FatturaElettronica.model())));
			
			String lottoTable = "LottoFatture";
			String lottoId = lottoTable + ".id";
			fields.add(new AliasField(new CustomField(lottoId, Long.class, "id", fieldConverter.toTable(FatturaElettronica.model().LOTTO_FATTURE)), "l_id"));
			String idSipLottoField = lottoTable+".id_sip";
			fields.add(new AliasField(new CustomField(idSipLottoField, Long.class, "id_sip", fieldConverter.toTable(FatturaElettronica.model().LOTTO_FATTURE)), "l_id_sip"));
			
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}

		return fields.toArray(new IField[]{});
	}

	private IField[] getFieldsRiconsegna(FatturaBD fatturaBD) throws ServiceException {
		List<IField> fields = new ArrayList<IField>();

		FatturaElettronicaFieldConverter fieldConverter = new FatturaElettronicaFieldConverter(fatturaBD.getDatabaseType());

		try {
			String idSipField = "id_sip";
			fields.add(FatturaElettronica.model().DATA_RICEZIONE);
			fields.add(FatturaElettronica.model().LOTTO_FATTURE.IDENTIFICATIVO_SDI);
			fields.add(new CustomField(idSipField, Long.class, idSipField, fieldConverter.toTable(FatturaElettronica.model())));
			
			String lottoTable = "LottoFatture";
			String idSipLottoField = lottoTable+".id_sip";
			fields.add(new AliasField(new CustomField(idSipLottoField, Long.class, "id_sip", fieldConverter.toTable(FatturaElettronica.model().LOTTO_FATTURE)), "l_id_sip"));
			
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}

		return fields.toArray(new IField[]{});
	}

	@Override
	public void execute() throws Exception {
		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();

			FatturaBD fatturaElettronicaBD = new FatturaBD(log, connection, true);
			LottoBD lottoBD = new LottoBD(log, connection, true);
			SIPBD sipBD = new SIPBD(log, connection, true);

			int offset = 0;
			int LIMIT_STEP = Math.min(this.limit, 500);

			FatturaFilter filter = fatturaElettronicaBD.newFilter();
			filter.getStatiConservazione().add(StatoConservazioneType.PRESA_IN_CARICO);
			filter.setIdSipNull(true);
			filter.setOffset(offset);
			filter.setLimit(LIMIT_STEP);
//			List<FatturaElettronica> fattureDaSchedulare = fatturaElettronicaBD.findAll(filter);
			
			List<FatturaElettronica> fattureDaSchedulare = fatturaElettronicaBD.fatturaElettronicaSelect(filter, this.getFieldsConservazione(fatturaElettronicaBD));

			while(fattureDaSchedulare != null && !fattureDaSchedulare.isEmpty()) {
				this.log.debug("Trovate ["+fattureDaSchedulare.size()+"] fatture da mandare in scheduling per la conservazione...");

				for(FatturaElettronica fattura: fattureDaSchedulare) {

					SIP sipFattura = new SIP();
					ChiaveType chiaveFattura = ConservazioneUtils.getChiave(fattura);
					sipFattura.setNumero(chiaveFattura.getNumero());
					sipFattura.setAnno(chiaveFattura.getAnno());
					sipFattura.setRegistro(chiaveFattura.getTipoRegistro());
					sipFattura.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
					sipFattura.setDataUltimaConsegna(new Date());
					sipBD.create(sipFattura);
					fatturaElettronicaBD.assegnaIdSip(fattura,sipFattura.getId());


					
					FatturaFilter idSdiFilter = fatturaElettronicaBD.newFilter();
					idSdiFilter.setIdentificativoSdi(fattura.getIdentificativoSdi());
					idSdiFilter.setFatturazioneAttiva(fattura.getFatturazioneAttiva());
					long count = fatturaElettronicaBD.count(idSdiFilter);
					
					//controlli da fare per abilitare la spedizione del lotto:
					// 1) fatturazione passiva
					// 2) lotto di piu' fatture
					// 3) il lotto no ndeve avere gia' associato il sip (verificato dalla sipBD.exist)
					if(!fattura.getFatturazioneAttiva() && count > 1) {
						this.log.debug("Inserisco in scheduling il lotto della fattura passiva ["+fattura.getIdentificativoSdi()+"]...");
						ChiaveType chiaveLotto = ConservazioneUtils.getChiaveLotto(fattura);
						if(!sipBD.exists(chiaveLotto.getNumero(), chiaveLotto.getAnno(), chiaveLotto.getTipoRegistro())) {
							SIP sipLotto = new SIP();
							sipLotto.setNumero(chiaveLotto.getNumero());
							sipLotto.setAnno(chiaveLotto.getAnno());
							sipLotto.setRegistro(chiaveLotto.getTipoRegistro());
							sipLotto.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
							sipLotto.setDataUltimaConsegna(new Date());
							sipBD.create(sipLotto);
							lottoBD.assegnaIdSip(fattura.getLottoFatture(),sipLotto.getId());
						}

					}

				}

				// sposto l'offset
				offset += LIMIT_STEP;
				filter.setOffset(offset);
				fattureDaSchedulare = fatturaElettronicaBD.fatturaElettronicaSelect(filter, this.getFieldsConservazione(fatturaElettronicaBD));
			}


			offset = 0;
			FatturaFilter filter2 = fatturaElettronicaBD.newFilter();
			filter2.getStatiConservazione().add(StatoConservazioneType.IN_RICONSEGNA);
			filter2.setOffset(offset);
			filter2.setLimit(LIMIT_STEP);
			List<FatturaElettronica> fattureInRiconsegna = fatturaElettronicaBD.fatturaElettronicaSelect(filter2, this.getFieldsRiconsegna(fatturaElettronicaBD));

			while(fattureInRiconsegna != null && !fattureInRiconsegna.isEmpty()) {
				this.log.debug("Trovate ["+fattureInRiconsegna.size()+"] fatture da reinviare in conservazione...");

				for(FatturaElettronica fattura: fattureInRiconsegna) {

					sipBD.updateStatoConsegna(fattura.getIdSIP(), StatoConsegnaType.IN_RICONSEGNA);

					

					//controlli da fare per abilitare la rispedizione del lotto:
					// 1) fatturazione passiva
					// 2) il lotto deve avere associato il sip
					
					this.log.debug("Identificativo sdi ["+fattura.getIdentificativoSdi()+"] posizione ["+fattura.getPosizione()+"] id sip lotto ["+fattura.getLottoFatture().getIdSIP()+"]");
					
					if(!fattura.getFatturazioneAttiva() && fattura.getLottoFatture().getIdSIP() != null) {
						sipBD.updateStatoConsegna(fattura.getLottoFatture().getIdSIP(), StatoConsegnaType.IN_RICONSEGNA);
					}

				}

				// sposto l'offset
				offset += LIMIT_STEP;
				filter2.setOffset(offset);
				fattureInRiconsegna = fatturaElettronicaBD.fatturaElettronicaSelect(filter2, this.getFieldsRiconsegna(fatturaElettronicaBD));


			}

		}catch(Exception e){
			this.log.error("Errore durante l'esecuzione del batch SchedulingConservazione: "+e.getMessage(), e);
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


}
