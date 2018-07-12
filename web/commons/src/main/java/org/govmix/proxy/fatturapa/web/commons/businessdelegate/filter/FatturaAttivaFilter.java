package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.LikeMode;

public class FatturaAttivaFilter extends FatturaFilter {

	private List<StatoElaborazioneType> statoElaborazioneList;
	private TipoComunicazioneType tipoComunicazione;
	private Date dataUltimaElaborazioneMin;
	private Date dataUltimaElaborazioneMax;
	private Boolean soloConservazione;

	public Boolean getSoloConservazione() {
		return soloConservazione;
	}

	public void setSoloConservazione(Boolean soloConservazione) {
		this.soloConservazione = soloConservazione;
	}

	public FatturaAttivaFilter(IExpressionConstructor expressionConstructor) {
		this(expressionConstructor, true);
	}
	
	public FatturaAttivaFilter(IExpressionConstructor expressionConstructor, Boolean fatturazioneAttiva) {
		super(expressionConstructor, fatturazioneAttiva);
	}

	@Override
	public IExpression _toFatturaExpression() throws ServiceException {
		try {
			IExpression expression = this.newExpression();
			
			if(this.statoElaborazioneList != null) {
				expression.in(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, this.statoElaborazioneList);
			}
			
			if(this.tipoComunicazione != null) {
				expression.ilike(FatturaElettronica.model().LOTTO_FATTURE.TIPI_COMUNICAZIONE,  "#" + this.tipoComunicazione.name() + "#", LikeMode.ANYWHERE);
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
			
			if(this.decorrenzaTermini != null) {
				if(this.decorrenzaTermini)
					expression.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, StatoElaborazioneType.RICEVUTA_DECORRENZA_TERMINI.toString());
				else
					expression.notEquals(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, StatoElaborazioneType.RICEVUTA_DECORRENZA_TERMINI.toString());
			}
			
			if(this.soloConservazione != null) {
				if(this.soloConservazione)
					expression.equals(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, StatoElaborazioneType.SOLO_CONSERVAZIONE.toString());
				else
					expression.notEquals(FatturaElettronica.model().LOTTO_FATTURE.STATO_ELABORAZIONE_IN_USCITA, StatoElaborazioneType.SOLO_CONSERVAZIONE.toString());
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

	public TipoComunicazioneType getTipoComunicazione() {
		return tipoComunicazione;
	}

	public void setTipoComunicazione(TipoComunicazioneType tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}
	
	

}
