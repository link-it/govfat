package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class FatturaAttivaFilter extends AbstractFatturaFilter {

	public FatturaAttivaFilter(IExpressionConstructor expressionConstructor) {
		super(expressionConstructor, true);
	}

	@Override
	public IExpression _toFatturaExpression() throws ServiceException {
		try {
			IExpression expression = this.newExpression();
			
			return expression;
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

}
