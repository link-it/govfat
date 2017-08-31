package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class FatturaFilter extends AbstractFatturaFilter {

	private IExpression exp;
	
	public FatturaFilter(IExpressionConstructor expressionConstructor) {
		super(expressionConstructor, null);
	}

	public FatturaFilter(IExpressionConstructor expressionConstructor, IExpression exp) {
		super(expressionConstructor, null);
		this.exp = exp;
	}

	@Override
	public IExpression _toFatturaExpression() throws ServiceException {
		try {
			if(this.exp == null) {
				return this.newExpression();
			} else {
				return this.exp;
			}
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

}
