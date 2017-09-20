package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.Date;

import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
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
	private StatoProtocollazioneType statoProtocollazione;
	private Date dataProssimaProtocollazioneMin;
	
	public TracciaSdIFilter(IExpressionConstructor expressionConstructor) {
		super(expressionConstructor);
	}

	@Override
	public IExpression _toExpression() throws ServiceException {
		try {
			IExpression expression = this.newExpression();

			if(this.id != null) {
				expression.equals(new CustomField("id", Long.class, "id", this.getRootTable()), this.id);
			}
			
			if(this.tipoComunicazione != null) {
				expression.equals(TracciaSDI.model().TIPO_COMUNICAZIONE, this.tipoComunicazione);
			}
			
			if(this.statoProtocollazione != null) {
				expression.equals(TracciaSDI.model().STATO_PROTOCOLLAZIONE, this.statoProtocollazione);
			}
			
			if(this.dataProssimaProtocollazioneMin != null) {
				expression.greaterEquals(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE, this.dataProssimaProtocollazioneMin);
			}

			
			return expression;
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
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

	public StatoProtocollazioneType getStatoProtocollazione() {
		return statoProtocollazione;
	}

	public void setStatoProtocollazione(StatoProtocollazioneType statoProtocollazione) {
		this.statoProtocollazione = statoProtocollazione;
	}

	public Date getDataProssimaProtocollazioneMin() {
		return dataProssimaProtocollazioneMin;
	}

	public void setDataProssimaProtocollazioneMin(Date dataProssimaProtocollazioneMin) {
		this.dataProssimaProtocollazioneMin = dataProssimaProtocollazioneMin;
	}

}
