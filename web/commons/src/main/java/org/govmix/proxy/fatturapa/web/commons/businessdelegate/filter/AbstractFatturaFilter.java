package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public abstract class AbstractFatturaFilter extends AbstractFilter {

	private Boolean fatturazioneAttiva;
	private Utente utente;
	private Date dataRicezioneMin;
	
	public AbstractFatturaFilter(IExpressionConstructor expressionConstructor, Boolean fatturazioneAttiva) {
		super(expressionConstructor);
		this.fatturazioneAttiva = fatturazioneAttiva;
	}

	@Override
	public IExpression _toExpression() throws ServiceException {
		try {
			IExpression expression = this._toFatturaExpression();

			if(this.fatturazioneAttiva != null) {
				//solo fatture attive / passive
				expression.equals(FatturaElettronica.model().FATTURAZIONE_ATTIVA, this.fatturazioneAttiva);
			}
			
			if(this.dataRicezioneMin != null) {
				expression.greaterEquals(FatturaElettronica.model().DATA_RICEZIONE, this.dataRicezioneMin);
			}
			
			if(this.utente != null) {
				List<String> dipartimenti = new ArrayList<String>(); 
				if(utente.getUtenteDipartimentoList()!=null && !utente.getUtenteDipartimentoList().isEmpty()) {
					for(UtenteDipartimento ud: utente.getUtenteDipartimentoList()) {
						dipartimenti.add(ud.getIdDipartimento().getCodice());
					}
				}
				expression.in(FatturaElettronica.model().CODICE_DESTINATARIO, dipartimenti);
			}
			return expression;
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}
	
	protected abstract IExpression _toFatturaExpression() throws ServiceException;

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Date getDataRicezioneMin() {
		return dataRicezioneMin;
	}

	public void setDataRicezioneMin(Date dataRicezioneMin) {
		this.dataRicezioneMin = dataRicezioneMin;
	}

}
