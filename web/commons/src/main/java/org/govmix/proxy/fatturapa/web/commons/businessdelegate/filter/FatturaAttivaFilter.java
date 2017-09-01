package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.ArrayList;
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
	
	

}
