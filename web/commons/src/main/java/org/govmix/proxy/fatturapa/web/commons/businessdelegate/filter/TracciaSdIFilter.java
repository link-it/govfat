package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
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
	private Integer posizione;
	private Boolean consentiPosizioneNull;
	private String tipoComunicazione;
	private StatoProtocollazioneType statoProtocollazione;
	private Date dataProssimaProtocollazioneMax;
	private Boolean daProtocollare;
	private Boolean fatturazioneAttiva;
	
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

			if(this.identificativoSdi != null) {
				expression.equals(TracciaSDI.model().IDENTIFICATIVO_SDI, this.identificativoSdi);
			}
			
			if(this.posizione != null) {
				if(this.consentiPosizioneNull !=null && this.consentiPosizioneNull) {
					IExpression expression2 = this.newExpression();
					expression2.equals(TracciaSDI.model().POSIZIONE, this.posizione).or().isNull(TracciaSDI.model().POSIZIONE);
					expression.and(expression2);
				} else {
					expression.equals(TracciaSDI.model().POSIZIONE, this.posizione);
				}
			}
			
			if(this.tipoComunicazione != null) {
				expression.equals(TracciaSDI.model().TIPO_COMUNICAZIONE, this.tipoComunicazione);
			}
			
			if(this.statoProtocollazione != null) {
				expression.equals(TracciaSDI.model().STATO_PROTOCOLLAZIONE, this.statoProtocollazione);
			}
			
			if(this.dataProssimaProtocollazioneMax != null) {
				expression.lessEquals(TracciaSDI.model().DATA_PROSSIMA_PROTOCOLLAZIONE, this.dataProssimaProtocollazioneMax);
			}
			
			if(this.daProtocollare != null) {
				expression.equals(TracciaSDI.model().DIPARTIMENTO.MODALITA_PUSH, this.daProtocollare);
			}
			
			if(this.fatturazioneAttiva != null) {
				List<String> listaTipiComunicazione = null;

				if(this.fatturazioneAttiva) {
					listaTipiComunicazione = Arrays.asList(TipoComunicazioneType.AT.toString(),TipoComunicazioneType.DT_ATT.toString(),TipoComunicazioneType.FAT_OUT.toString(),TipoComunicazioneType.MC.toString(),TipoComunicazioneType.MT.toString(),TipoComunicazioneType.NE.toString(),TipoComunicazioneType.NS.toString(),TipoComunicazioneType.RC.toString());
				} else {
					listaTipiComunicazione = Arrays.asList(TipoComunicazioneType.DT_PASS.toString(),TipoComunicazioneType.FAT_IN.toString(),TipoComunicazioneType.EC.toString(),TipoComunicazioneType.SE.toString());
				}
				
				expression.in(TracciaSDI.model().TIPO_COMUNICAZIONE, listaTipiComunicazione);
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

	public Date getDataProssimaProtocollazioneMax() {
		return dataProssimaProtocollazioneMax;
	}

	public void setDataProssimaProtocollazioneMax(Date dataProssimaProtocollazioneMax) {
		this.dataProssimaProtocollazioneMax = dataProssimaProtocollazioneMax;
	}

	public Boolean getDaProtocollare() {
		return daProtocollare;
	}

	public void setDaProtocollare(Boolean daProtocollare) {
		this.daProtocollare = daProtocollare;
	}

	public Integer getPosizione() {
		return posizione;
	}

	public void setPosizione(Integer posizione) {
		this.posizione = posizione;
	}

	public boolean isConsentiPosizioneNull() {
		return consentiPosizioneNull;
	}

	public void setConsentiPosizioneNull(boolean consentiPosizioneNull) {
		this.consentiPosizioneNull = consentiPosizioneNull;
	}

	public Boolean getFatturazioneAttiva() {
		return fatturazioneAttiva;
	}

	public void setFatturazioneAttiva(Boolean fatturazioneAttiva) {
		this.fatturazioneAttiva = fatturazioneAttiva;
	}

}
