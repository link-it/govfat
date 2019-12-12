package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.UtenteDipartimento;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
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
	private Long identificativoSdi;
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

	private Date dataFattura;

	private String codiceDestinatario;

	private String numero;
	private String numeroLike;
	
	private List<String> ccDenominazioneList;

	private List<String> cpDenominazioneList;
	private String cpCodiceFiscale;
	private String cpPaese;

	private Double importo;
	private String tipoDocumento;
	
	private String protocollo;
	protected Boolean decorrenzaTermini;
	private Boolean idSipNull;
	
	private String formatoArchivioInvioFattura;
	private String formatoTrasmissione;
	private String ente;
	private Integer anno;
	
	private List<StatoConservazioneType> statiConservazione;
	
	protected Boolean filtroConservazione = null;

	public FatturaFilter(IExpressionConstructor expressionConstructor, Boolean fatturazioneAttiva) {
		super(expressionConstructor);
		this.setFatturazioneAttiva(fatturazioneAttiva);
	}

	public FatturaFilter(IExpressionConstructor expressionConstructor) {
		this(expressionConstructor, null);
	}

	@Override
	public IExpression _toExpression() throws ServiceException {
		try {
			IExpression expression = this._toFatturaExpression();
			
			IExpression conservazioneExpression = this._toConservazioneExpression();
			if(this.filtroConservazione != null && this.filtroConservazione.booleanValue() && conservazioneExpression != null)
				expression.and(conservazioneExpression);

			if(this.id != null) {
				expression.equals(new CustomField("id", Long.class, "id", this.getRootTable()), this.id);
			}
			
			if(this.identificativoSdi != null) {
				expression.equals(FatturaElettronica.model().IDENTIFICATIVO_SDI, this.identificativoSdi);
			}
			
			if(this.posizione != null) {
				expression.equals(FatturaElettronica.model().POSIZIONE, this.posizione);
			}

			if(this.getFatturazioneAttiva() != null) {
				expression.equals(FatturaElettronica.model().LOTTO_FATTURE.FATTURAZIONE_ATTIVA, this.getFatturazioneAttiva());
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
			
			if(this.dataFattura != null) {
				expression.equals(FatturaElettronica.model().DATA, this.dataFattura);
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
			
			if(this.formatoArchivioInvioFattura != null) {
				expression.equals(FatturaElettronica.model().LOTTO_FATTURE.FORMATO_ARCHIVIO_INVIO_FATTURA, this.formatoArchivioInvioFattura);
			}
			
			if(this.formatoTrasmissione != null) {
				expression.equals(FatturaElettronica.model().FORMATO_TRASMISSIONE, this.formatoTrasmissione);
			}
			
			if(this.protocollo != null) {
				expression.ilike(FatturaElettronica.model().PROTOCOLLO, this.protocollo);
			}
			
			if(this.idSipNull!= null) {
				CustomField customField = new CustomField("id_sip", Long.class, "id_sip", this.getRootTable());
				
				if(this.idSipNull) {
					expression.isNull(customField);
				} else {
					expression.isNotNull(customField);
				}
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
			
			if(this.ccDenominazioneList != null) {
				if(ccDenominazioneList.size() == 1) {
					expression.ilike(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE, this.ccDenominazioneList.get(0), LikeMode.ANYWHERE);
				} else {
					IExpression newExpression = this.newExpression();
					for(String cpDenom: ccDenominazioneList) {
						newExpression.or().ilike(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE, cpDenom, LikeMode.ANYWHERE);
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
			
			
			if(this.ente!= null) {
				expression.equals(FatturaElettronica.model().DIPARTIMENTO.ENTE.NOME, this.ente);
			}
			
			if(this.anno!= null) {
				expression.equals(FatturaElettronica.model().ANNO, this.anno);
			}
			
			if(this.statiConservazione != null && !this.statiConservazione.isEmpty()) {
				IExpression expression2 = this.newExpression();

				for(StatoConservazioneType stato: this.statiConservazione){
					expression2.equals(FatturaElettronica.model().STATO_CONSERVAZIONE, stato);
					expression2.or();
				}
				expression.and(expression2);
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
	
	protected IExpression _toConservazioneExpression() throws ServiceException {
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

	public Long getIdentificativoSdi() {
		return identificativoSdi;
	}

	public void setIdentificativoSdi(Long identificativoSdi) {
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

	public List<String> getCcDenominazioneList() {
		if(this.ccDenominazioneList == null) this.ccDenominazioneList = new ArrayList<String>();

		return ccDenominazioneList;
	}

	public void setCcDenominazioneList(List<String> ccDenominazioneList) {
		this.ccDenominazioneList = ccDenominazioneList;
	}

	public Boolean getDecorrenzaTermini() {
		return decorrenzaTermini;
	}

	public void setDecorrenzaTermini(Boolean decorrenzaTermini) {
		this.decorrenzaTermini = decorrenzaTermini;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public List<StatoConservazioneType> getStatiConservazione() {
		if(this.statiConservazione == null) this.statiConservazione = new ArrayList<StatoConservazioneType>();
		return statiConservazione;
	}

	public void setStatiConservazione(List<StatoConservazioneType> statiConservazione) {
		this.statiConservazione = statiConservazione;
	}

	
	public Boolean getFatturazioneAttiva() {
		return fatturazioneAttiva;
	}

	public void setFatturazioneAttiva(Boolean fatturazioneAttiva) {
		this.fatturazioneAttiva = fatturazioneAttiva;
	}

	public Boolean getIdSipNull() {
		return idSipNull;
	}

	public void setIdSipNull(Boolean idSipNull) {
		this.idSipNull = idSipNull;
	}

	public Boolean getFiltroConservazione() {
		return filtroConservazione;
	}

	public void setFiltroConservazione(Boolean filtroConservazione) {
		this.filtroConservazione = filtroConservazione;
	}

	public Date getDataFattura() {
		return dataFattura;
	}

	public void setDataFattura(Date dataFattura) {
		this.dataFattura = dataFattura;
	}

	public String getFormatoArchivioInvioFattura() {
		return formatoArchivioInvioFattura;
	}

	public void setFormatoArchivioInvioFattura(String formatoArchivioInvioFattura) {
		this.formatoArchivioInvioFattura = formatoArchivioInvioFattura;
	}

	public String getFormatoTrasmissione() {
		return formatoTrasmissione;
	}

	public void setFormatoTrasmissione(String formatoTrasmissione) {
		this.formatoTrasmissione = formatoTrasmissione;
	}
	
}
