package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class LottoFattureAttiveBD extends LottoBD {

	public LottoFattureAttiveBD(Logger log) throws Exception {
		super(log);
	}

	public LottoFattureAttiveBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
	}
	
	public List<LottoFatture> getLottiDaSpedire(Date dataRicezione, int offset, int limit) throws Exception {
		try {
			IPaginatedExpression expression = this.service.toPaginatedExpression(getExpressionLottiDaSpedire(dataRicezione));

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

	public long countLottiDaSpedire(Date dataRicezione) throws Exception {
		try {
			IExpression expression = getExpressionLottiDaSpedire(dataRicezione);
			
			return this.service.count(expression).longValue();
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	private IExpression getExpressionLottiDaSpedire(Date dataRicezione)
			throws ServiceException, NotImplementedException, ExpressionNotImplementedException, ExpressionException {
		IExpression expression = this.service.newExpression();
		expression.lessEquals(LottoFatture.model().DATA_PROSSIMA_ELABORAZIONE, dataRicezione);
		expression.equals(LottoFatture.model().STATO_ELABORAZIONE_IN_USCITA, StatoElaborazioneType.PROTOCOLLATA);
		expression.equals(LottoFatture.model().FATTURAZIONE_ATTIVA, true);
		return expression;
	}
	
	public void updateIdentificativoSdI(LottoFatture lotto, Integer identificativoSdI) throws ServiceException, NotFoundException {
		try {
			this.service.updateFields(this.service.convertToId(lotto), new UpdateField(LottoFatture.model().IDENTIFICATIVO_SDI, identificativoSdI), new UpdateField(LottoFatture.model().TIPI_COMUNICAZIONE, "#"+TipoComunicazioneType.FAT_OUT.name()+"#"));
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
		
	}




}
