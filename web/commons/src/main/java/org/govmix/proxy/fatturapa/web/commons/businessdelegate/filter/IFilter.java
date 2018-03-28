package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;

public interface IFilter {

	public IExpression toExpression() throws ServiceException;
	public IPaginatedExpression toPaginatedExpression() throws ServiceException;
}
