package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class LottoFatturePassiveBD extends LottoBD {

	public LottoFatturePassiveBD(Logger log) throws Exception {
		super(log);
	}

	public LottoFatturePassiveBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
	}
	
	public List<LottoFatture> getLottiDaInserire(Date dataRicezione, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.toPaginatedExpression(getExpressionLottiDaInserire(dataRicezione));

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(LottoFatture.model().DATA_RICEZIONE);
			
			expression.offset(offset);
			expression.limit(limit);
			
			return this.service.findAll(expression);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public long countLottiDaInserire(Date dataRicezione) throws Exception {
		try {
			IExpression expression = getExpressionLottiDaInserire(dataRicezione);
			
			return this.service.count(expression).longValue();
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	private IExpression getExpressionLottiDaInserire(Date dataRicezione)
			throws ServiceException, NotImplementedException, ExpressionNotImplementedException, ExpressionException {
		IExpression expression = this.service.newExpression();
		expression.lessEquals(LottoFatture.model().DATA_RICEZIONE, dataRicezione);
		expression.equals(LottoFatture.model().STATO_INSERIMENTO, StatoInserimentoType.NON_INSERITO);
		expression.equals(LottoFatture.model().FATTURAZIONE_ATTIVA, false);
		return expression;
	}

	private IExpression getExpressionLottiDaConsegnare(Date dataRicezione)
			throws ServiceException, NotImplementedException, ExpressionNotImplementedException, ExpressionException {
		IExpression expression = this.service.newExpression();
		
		expression.equals(LottoFatture.model().DIPARTIMENTO.MODALITA_PUSH, true);
		expression.equals(LottoFatture.model().FATTURAZIONE_ATTIVA, false);
		expression.equals(LottoFatture.model().STATO_INSERIMENTO, StatoInserimentoType.INSERITO);
		
		expression.lessEquals(LottoFatture.model().DATA_RICEZIONE, dataRicezione);
		
		IExpression expression2 = this.service.newExpression();
		expression2.lessEquals(LottoFatture.model().DATA_CONSEGNA, dataRicezione).or().isNull(LottoFatture.model().DATA_CONSEGNA);
		expression.and(expression2);
		
		
		IExpression expression3 = this.service.newExpression();
		expression3.equals(LottoFatture.model().STATO_CONSEGNA, StatoConsegnaType.NON_CONSEGNATA).or().equals(LottoFatture.model().STATO_CONSEGNA, StatoConsegnaType.ERRORE_CONSEGNA);
		expression.and(expression3);
		
		return expression;
	}

	public List<LottoFatture> getLottiDaConsegnare(Date dataRicezione, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.toPaginatedExpression(this.getExpressionLottiDaConsegnare(dataRicezione));

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(LottoFatture.model().DATA_RICEZIONE);
			
			expression.offset(offset);
			expression.limit(limit);
			
			return this.service.findAll(expression);
			
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}


	public List<LottoFatture> getLottiDaAssociare(Date dataRicezione, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.toPaginatedExpression(this.getExpressionLottiDaAssociare(dataRicezione));

			expression.sortOrder(SortOrder.ASC);
			expression.addOrder(LottoFatture.model().DATA_PROTOCOLLAZIONE);
			
			expression.offset(offset);
			expression.limit(limit);
			
			return this.service.findAll(expression);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}
	
	public long countLottiDaAssociare(Date dataRicezione) throws Exception {
		try {
			IExpression expression = getExpressionLottiDaAssociare(dataRicezione);
			
			return this.service.count(expression).longValue();
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	private IExpression getExpressionLottiDaAssociare(Date dataRicezione)
			throws ServiceException, NotImplementedException, ExpressionNotImplementedException, ExpressionException {
		IExpression expression = this.service.newExpression();
		
		IExpression ricezioneNull = this.service.newPaginatedExpression();
		ricezioneNull.isNull(LottoFatture.model().DATA_PROTOCOLLAZIONE);

		IExpression ricezioneLessEquals = this.service.newPaginatedExpression();
		ricezioneLessEquals.lessEquals(LottoFatture.model().DATA_PROTOCOLLAZIONE, dataRicezione);

		expression.or(ricezioneNull, ricezioneLessEquals);
		
		IExpression protocollataInElaborazione = this.service.newPaginatedExpression();
		protocollataInElaborazione.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA_IN_ELABORAZIONE);
		
		IExpression erroreProtocollazione = this.service.newPaginatedExpression();
		erroreProtocollazione.equals(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE);
		
		expression.or(protocollataInElaborazione, erroreProtocollazione);
		return expression;
	}

	public long countLottiDaConsegnare(Date dataRicezione) throws Exception {
		try {
			IExpression expression = getExpressionLottiDaConsegnare(dataRicezione);
			
			return this.service.count(expression).longValue();
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}


	public void setProcessato(IdLotto idLotto) throws Exception {
		this.setProcessato(idLotto, false);
	}
	
	public void setProcessato(IdLotto idLotto, boolean errore) throws Exception {
		try {
			StatoInserimentoType stato = errore ? StatoInserimentoType.ERRORE_INSERIMENTO :StatoInserimentoType.INSERITO;
			UpdateField updateField = new UpdateField(LottoFatture.model().STATO_INSERIMENTO, stato);
			this.service.updateFields(idLotto, updateField);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public void erroreProtocollo(IdLotto idLotto) throws Exception {
		try {

			List<UpdateField> lst = new ArrayList<UpdateField>();
			
			lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.ERRORE_PROTOCOLLAZIONE));
			lst.add(new UpdateField(LottoFatture.model().DATA_PROTOCOLLAZIONE, new Date()));
			
			this.service.updateFields(idLotto, lst.toArray(new UpdateField[1]));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void updateProtocollo(IdLotto idLotto, String protocollo,boolean asincrono) throws Exception {
		try {

			List<UpdateField> lst = new ArrayList<UpdateField>();
			lst.add(new UpdateField(LottoFatture.model().STATO_CONSEGNA, StatoConsegnaType.CONSEGNATA));
			lst.add(new UpdateField(LottoFatture.model().DATA_CONSEGNA, new Date()));
			
			if(asincrono) {
				lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA_IN_ELABORAZIONE));
				lst.add(new UpdateField(LottoFatture.model().PROTOCOLLO, protocollo));
			} else if(protocollo != null) {
				lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA));
				lst.add(new UpdateField(LottoFatture.model().DATA_PROTOCOLLAZIONE, new Date()));
				lst.add(new UpdateField(LottoFatture.model().PROTOCOLLO, protocollo));
			} else {
				lst.add(new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.NON_PROTOCOLLATA));
			}
			
			this.service.updateFields(idLotto, lst.toArray(new UpdateField[1]));
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoConsegna(IdLotto idLotto, String response,String detail) throws Exception {
		try {
			UpdateField statoConsegnaField = new UpdateField(LottoFatture.model().STATO_CONSEGNA, StatoConsegnaType.ERRORE_CONSEGNA);
			UpdateField dataConsegnaField = new UpdateField(LottoFatture.model().DATA_CONSEGNA, new Date());
			UpdateField dettaglioConsegnaField = new UpdateField(LottoFatture.model().DETTAGLIO_CONSEGNA, detail + ": " + response);
			this.service.updateFields(idLotto, statoConsegnaField, dataConsegnaField, dettaglioConsegnaField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoConsegna: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void updateStatoProtocollazioneOK(IdLotto idLotto) throws Exception {
		try {
			UpdateField statoProtocollazioneField = new UpdateField(LottoFatture.model().STATO_PROTOCOLLAZIONE, StatoProtocollazioneType.PROTOCOLLATA);
			UpdateField dataProtocollazioneField = new UpdateField(LottoFatture.model().DATA_PROTOCOLLAZIONE, new Date());
			this.service.updateFields(idLotto, statoProtocollazioneField, dataProtocollazioneField);
		} catch (ServiceException e) {
			this.log.error("Errore durante la updateStatoProtocollazioneOK: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la updateStatoProtocollazioneOK: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}



}
