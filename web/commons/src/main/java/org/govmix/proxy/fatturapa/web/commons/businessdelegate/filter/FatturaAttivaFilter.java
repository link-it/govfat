package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class FatturaAttivaFilter extends FatturaFilter {

	private List<StatoElaborazioneType> statoElaborazioneList;
	private Date dataUltimaElaborazioneMin;
	private Date dataUltimaElaborazioneMax;
	private String protocollo;
	
	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public FatturaAttivaFilter(IExpressionConstructor expressionConstructor) {
		super(expressionConstructor, true);
	}

	@Override
	public IExpression _toFatturaExpression() throws ServiceException {
		try {
			IExpression expression = this.newExpression();
			
			if(this.statoElaborazioneList != null) {
				expression.in(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, this.statoElaborazioneList);
			}
			
			if(this.protocollo != null) {
				expression.equals(FatturaElettronica.model().PROTOCOLLO, this.protocollo);
			}
			
			if(this.dataUltimaElaborazioneMin != null) {
				IExpression exp2 = this.newExpression();
				exp2.isNull(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE).or().greaterThan(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE, this.dataUltimaElaborazioneMin);
				expression.and(exp2);
				
			}
			
			if(this.dataUltimaElaborazioneMax != null) {
				IExpression exp2 = this.newExpression();
				exp2.isNull(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE).or().lessThan(FatturaElettronica.model().LOTTO_FATTURE.DATA_ULTIMA_ELABORAZIONE, this.dataUltimaElaborazioneMax);
				expression.and(exp2);
			}
			
			return expression;
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}

	public List<StatoElaborazioneType> getStatoElaborazioneList() {
		if(this.statoElaborazioneList == null) this.statoElaborazioneList = new ArrayList<StatoElaborazioneType>();
		return this.statoElaborazioneList;
	}

	public void setStatoElaborazioneList(List<StatoElaborazioneType> statoElaborazioneList) {
		this.statoElaborazioneList = statoElaborazioneList;
	}

	public Date getDataUltimaElaborazioneMin() {
		return dataUltimaElaborazioneMin;
	}

	public void setDataUltimaElaborazioneMin(Date dataUltimaElaborazioneMin) {
		this.dataUltimaElaborazioneMin = dataUltimaElaborazioneMin;
	}

	public Date getDataUltimaElaborazioneMax() {
		return dataUltimaElaborazioneMax;
	}

	public void setDataUltimaElaborazioneMax(Date dataUltimaElaborazioneMax) {
		this.dataUltimaElaborazioneMax = dataUltimaElaborazioneMax;
	}
	
	

}
