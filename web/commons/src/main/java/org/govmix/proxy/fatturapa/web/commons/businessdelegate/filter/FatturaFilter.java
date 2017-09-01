package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.LikeMode;

public class FatturaFilter extends AbstractFilter {

	/** Identificativo fisico della fattura sul DB **/
	private Long id;

	/** Dati identificativi della fattura (logici) **/
	private Integer identificativoSdi;
	private Integer posizione;
	
	/** Utente autenticato (filtra sulle fatture indirizzate ai dipartimenti di quell'utente **/
	private Utente utente;
	
	/** Fatturazione attiva / passiva **/
	private Boolean fatturazioneAttiva;
	

	/** Metadati della fattura **/
	private Date dataRicezioneMin;
	private Date dataRicezioneMax;
	
	private Date dataFatturaMin;
	private Date dataFatturaMax;

	private String codiceDestinatario;

	private String numero;
	private String numeroLike;
	
	private List<String> cpDenominazioneList;
	private String cpCodiceFiscale;
	private String cpPaese;

	private Double importo;
	private String tipoDocumento;
	
	private String protocollo;
	
	public FatturaFilter(IExpressionConstructor expressionConstructor, Boolean fatturazioneAttiva) {
		super(expressionConstructor);
		this.fatturazioneAttiva = fatturazioneAttiva;
	}

	public FatturaFilter(IExpressionConstructor expressionConstructor) {
		this(expressionConstructor, null);
	}

	@Override
	public IExpression _toExpression() throws ServiceException {
		try {
			IExpression expression = this._toFatturaExpression();

			if(this.id != null) {
				expression.equals(new CustomField("id", Long.class, "id", this.getRootTable()), this.id);
			}
			
			if(this.identificativoSdi != null) {
				expression.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI, this.identificativoSdi);
			}
			
			if(this.posizione != null) {
				expression.equals(FatturaElettronica.model().POSIZIONE, this.posizione);
			}

			if(this.fatturazioneAttiva != null) {
				expression.equals(FatturaElettronica.model().FATTURAZIONE_ATTIVA, this.fatturazioneAttiva);
			}
			
			if(this.dataRicezioneMin != null) {
				expression.greaterEquals(FatturaElettronica.model().DATA_RICEZIONE, this.dataRicezioneMin);
			}
			
			if(this.dataRicezioneMax != null) {
				expression.lessEquals(FatturaElettronica.model().DATA_RICEZIONE, this.dataRicezioneMax);
			}
			
			if(this.dataFatturaMin != null) {
				expression.greaterEquals(FatturaElettronica.model().DATA, this.dataFatturaMin);
			}
			
			if(this.dataFatturaMax != null) {
				expression.lessEquals(FatturaElettronica.model().DATA, this.dataFatturaMax);
			}
			
			if(this.numero != null) {
				expression.equals(FatturaElettronica.model().NUMERO, this.numero);
			}
			
			if(this.numeroLike != null) {
				expression.ilike(FatturaElettronica.model().NUMERO, this.numeroLike, LikeMode.ANYWHERE);
			}
			
			if(this.tipoDocumento != null) {
				expression.equals(FatturaElettronica.model().TIPO_DOCUMENTO, this.tipoDocumento);
			}
			
			if(this.protocollo != null) {
				expression.ilike(FatturaElettronica.model().PROTOCOLLO, this.protocollo);
			}
			
			if(this.cpDenominazioneList != null) {
				if(cpDenominazioneList.size() == 1) {
					expression.ilike(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE, this.cpDenominazioneList.get(0), LikeMode.ANYWHERE);
				} else {
					IExpression newExpression = this.newExpression();
					for(String cpDenom: cpDenominazioneList) {
						newExpression.or().ilike(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE, cpDenom, LikeMode.ANYWHERE);
					}
					expression.and(newExpression);
				}
					
			}
			
			if(this.cpCodiceFiscale != null) {
				expression.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE, this.cpCodiceFiscale);
			}
			
			if(this.cpPaese != null) {
				expression.equals(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE, this.cpPaese);
			}
			
			if(this.codiceDestinatario != null) {
				expression.equals(FatturaElettronica.model().CODICE_DESTINATARIO, this.codiceDestinatario);
			}
			
			if(this.importo != null) {
				expression.equals(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO, this.importo);
			}
			
			if(this.utente != null && !this.utente.getRole().equals(UserRole.ADMIN)) {
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
		} catch(NotImplementedException e) {
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

	public Integer getIdentificativoSdi() {
		return identificativoSdi;
	}

	public void setIdentificativoSdi(Integer identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}

	public Integer getPosizione() {
		return posizione;
	}

	public void setPosizione(Integer posizione) {
		this.posizione = posizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDataFatturaMin() {
		return dataFatturaMin;
	}

	public void setDataFatturaMin(Date dataFatturaMin) {
		this.dataFatturaMin = dataFatturaMin;
	}

	public Date getDataFatturaMax() {
		return dataFatturaMax;
	}

	public void setDataFatturaMax(Date dataFatturaMax) {
		this.dataFatturaMax = dataFatturaMax;
	}

	public String getCpCodiceFiscale() {
		return cpCodiceFiscale;
	}

	public void setCpCodiceFiscale(String cpCodiceFiscale) {
		this.cpCodiceFiscale = cpCodiceFiscale;
	}

	public String getCpPaese() {
		return cpPaese;
	}

	public void setCpPaese(String cpPaese) {
		this.cpPaese = cpPaese;
	}

	public String getCodiceDestinatario() {
		return codiceDestinatario;
	}

	public void setCodiceDestinatario(String codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public List<String> getCpDenominazioneList() {
		if(this.cpDenominazioneList == null) this.cpDenominazioneList = new ArrayList<String>();
		
		return cpDenominazioneList;
	}

	public void setCpDenominazioneList(List<String> cpDenominazioneList) {
		this.cpDenominazioneList = cpDenominazioneList;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Date getDataRicezioneMax() {
		return dataRicezioneMax;
	}

	public void setDataRicezioneMax(Date dataRicezioneMax) {
		this.dataRicezioneMax = dataRicezioneMax;
	}

	public String getNumeroLike() {
		return numeroLike;
	}

	public void setNumeroLike(String numeroLike) {
		this.numeroLike = numeroLike;
	}

}
