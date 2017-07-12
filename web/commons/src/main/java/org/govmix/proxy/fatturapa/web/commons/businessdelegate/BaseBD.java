/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.dao.IDBServiceUtilities;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;


public abstract class BaseBD {

	protected JDBCServiceManager serviceManager;
	protected Logger log;
	protected boolean validate;
	protected List<FilterSortWrapper> filterSortList;
	
	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public BaseBD(Logger log) throws Exception {
		this.log = log;
		this.serviceManager = DAOFactory.getInstance().getServiceManager();
	}

	public BaseBD(Logger log, Connection conn, boolean autocommit) throws Exception {
		this.log = log;
		this.serviceManager = DAOFactory.getInstance().getServiceManager(conn, autocommit);
	}
	
	public List<FilterSortWrapper> getFilterSortList() {
		return filterSortList;
	}

	public void setFilterSortList(List<FilterSortWrapper> filterSortList) {
		this.filterSortList = filterSortList;
	}
	
	protected FilterSortWrapper getDefaultFilterSortWrapper(IExpressionConstructor expressionConstructor) throws ServiceException {
		try {
			CustomField baseField = new CustomField("id", Long.class, "id", getRootTable(expressionConstructor));
			FilterSortWrapper wrapper = new FilterSortWrapper();
			wrapper.setField(baseField);
			wrapper.setSortOrder(SortOrder.ASC);
			return wrapper;
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}
	
	protected String getRootTable(IExpressionConstructor expressionConstructor) throws ExpressionException {
		ISQLFieldConverter converter = ((IDBServiceUtilities<?>)expressionConstructor).getFieldConverter();
		IModel<?> model = converter.getRootModel();
		return converter.toTable(model);
	}
	public IPaginatedExpression toPaginatedExpression(IExpressionConstructor expressionConstructor, IExpression expression) throws ServiceException {
		return this.toPaginatedExpression(expressionConstructor, expression, null, null);
	}
	
	public IPaginatedExpression toPaginatedExpression(IExpressionConstructor expressionConstructor, IExpression expression, Integer start, Integer limit) throws ServiceException {
		return this.toPaginatedExpression(expressionConstructor, expression, start, limit, this.getFilterSortList());
	}
	
	public IPaginatedExpression toPaginatedExpression(IExpressionConstructor expressionConstructor, IExpression expression, Integer start, Integer limit,List<FilterSortWrapper> filterSortList) throws ServiceException {
		try {
			
			IPaginatedExpression pagExpression = expressionConstructor.toPaginatedExpression(expression);
			
			if(filterSortList != null && !filterSortList.isEmpty()){
			
				for(FilterSortWrapper filterSort: filterSortList) {
					pagExpression.addOrder(filterSort.getField(), filterSort.getSortOrder());
				}
			} else {
				FilterSortWrapper filterSort = getDefaultFilterSortWrapper(expressionConstructor);
				pagExpression.addOrder(filterSort.getField(), filterSort.getSortOrder());
			}
			
			if(start!= null)
				pagExpression.offset(start);
			
			if(limit != null)
				pagExpression.limit(limit);
			
			return pagExpression;
			
		}catch(ExpressionException e) {
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
		
	}
	
}
