package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class FatturaPassivaFilter extends AbstractFatturaFilter {

	private Boolean daAccettareAutomaticamente; 
	private Boolean modalitaPush; 
	private boolean consegnaContestuale;
	private List<StatoConsegnaType> statiConsegna;
	
	private EsitoType esito;
	private boolean esitoNull;
	
	private Boolean decorrenzaTermini;
	private Boolean inScadenza;
	
	public FatturaPassivaFilter(IExpressionConstructor expressionConstructor) {
		super(expressionConstructor, false);
	}

	@Override
	public IExpression _toFatturaExpression() throws ServiceException {
		try {
			IExpression expression = this.newExpression();
			
			if(modalitaPush != null) {
				expression.equals(FatturaElettronica.model().DIPARTIMENTO.MODALITA_PUSH, this.modalitaPush);
			}
			
			if(this.statiConsegna != null && !this.statiConsegna.isEmpty()) {
				IExpression expression2 = this.newExpression();

				for(StatoConsegnaType stato: this.statiConsegna){
					expression2.equals(FatturaElettronica.model().STATO_CONSEGNA, stato);
					expression2.or();
				}
				expression.and(expression2);

				if(this.consegnaContestuale) {
					expression.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_CONSEGNA, StatoConsegnaType.CONSEGNATA);
				}
			}
			
			if(this.daAccettareAutomaticamente != null && this.daAccettareAutomaticamente) {
				expression.equals(FatturaElettronica.model().DIPARTIMENTO.ACCETTAZIONE_AUTOMATICA, true);
				expression.isNull(FatturaElettronica.model().ESITO);
				expression.isNotNull(new CustomField("id_notifica_decorrenza_termini", Long.class, "id_notifica_decorrenza_termini", this.getRootTable()));
			}
			
			if(this.esitoNull) {
				expression.isNull(FatturaElettronica.model().ESITO);
			} else if(this.esito != null) {
				expression.equals(FatturaElettronica.model().ESITO, this.esito);
			}

			if(this.decorrenzaTermini != null) {
				if(this.decorrenzaTermini) {
					expression.isNotNull(new CustomField("id_notifica_decorrenza_termini", Long.class, "id_notifica_decorrenza_termini", this.getRootTable()));	
				} else {
					expression.isNull(new CustomField("id_notifica_decorrenza_termini", Long.class, "id_notifica_decorrenza_termini", this.getRootTable()));
				}
			}

			if(this.inScadenza) {
				expression.equals(FatturaElettronica.model().DA_PAGARE, this.inScadenza);
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

	public Boolean getModalitaPush() {
		return modalitaPush;
	}

	public void setModalitaPush(Boolean modalitaPush) {
		this.modalitaPush = modalitaPush;
	}

	public List<StatoConsegnaType> getStatiConsegna() {
		return statiConsegna;
	}

	public void setStatiConsegna(List<StatoConsegnaType> statiConsegna) {
		this.statiConsegna = statiConsegna;
	}

	public boolean isConsegnaContestuale() {
		return consegnaContestuale;
	}

	public void setConsegnaContestuale(boolean consegnaContestuale) {
		this.consegnaContestuale = consegnaContestuale;
	}

	public Boolean getDaAccettareAutomaticamente() {
		return daAccettareAutomaticamente;
	}

	public void setDaAccettareAutomaticamente(Boolean daAccettareAutomaticamente) {
		this.daAccettareAutomaticamente = daAccettareAutomaticamente;
	}

	public EsitoType getEsito() {
		return esito;
	}

	public void setEsito(EsitoType esito) {
		this.esito = esito;
	}

	public boolean isEsitoNull() {
		return esitoNull;
	}

	public void setEsitoNull(boolean esitoNull) {
		this.esitoNull = esitoNull;
	}

	public Boolean getDecorrenzaTermini() {
		return decorrenzaTermini;
	}

	public void setDecorrenzaTermini(Boolean decorrenzaTermini) {
		this.decorrenzaTermini = decorrenzaTermini;
	}

	public Boolean getInScadenza() {
		return inScadenza;
	}

	public void setInScadenza(Boolean inScadenza) {
		this.inScadenza = inScadenza;
	}

}
