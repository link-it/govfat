package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class TracciaSdIFilter extends AbstractFilter {

	/** Identificativo fisico della traccia sul DB **/
	private Long id;

	private Integer identificativoSdi;
	private String tipoComunicazione;
		
	public TracciaSdIFilter(IExpressionConstructor expressionConstructor) {
		super(expressionConstructor);
	}

	@Override
	public IExpression _toExpression() throws ServiceException {
		try {
			IExpression expression = this._toFatturaExpression();

			if(this.id != null) {
				expression.equals(new CustomField("id", Long.class, "id", this.getRootTable()), this.id);
			}
			
			if(this.tipoComunicazione != null) {
				expression.equals(TracciaSDI.model().TIPO_COMUNICAZIONE, this.tipoComunicazione);
			}
			return expression;
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}
	
	protected IExpression _toFatturaExpression() throws ServiceException {
		try {
			return this.newExpression();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public Integer getIdentificativoSdi() {
		return identificativoSdi;
	}

	public void setIdentificativoSdi(Integer identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoComunicazione() {
		return tipoComunicazione;
	}

	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}

}
