package org.govmix.proxy.fatturapa.web.console.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
//import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter;
import org.govmix.proxy.fatturapa.web.console.exporter.FattureExporter;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.Button;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.Image;
import org.openspcoop2.generic_project.web.output.Text;

public class ConservazioneBean extends BaseBean<FatturaElettronica, Long> implements IBean<FatturaElettronica, Long>{

	// Field Che visualizzo nella maschera di ricerca
	private Text tipoFattura = null;
	private Text cedentePrestatore = null;
	private Text dipartimento = null;
	private Text identificativoSdi = null;
	private Text annoNumero = null;
	private DateTime dataInvio = null;
	private Image statoInvio = null;
	private Button xml = null;
	private Button xmlLotto = null;
	private Text cessionarioCommittente = null;

	public ConservazioneBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.cedentePrestatore = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cedentePrestatore","fattura.cedentePrestatoreDenominazione");
		this.dipartimento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("dipartimento","fattura.dipartimento");
		this.annoNumero = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("annoNumero","fattura.annoNumero");
		this.dataInvio = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("dataInvio","fattura.dataInvio",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY);

		this.xml = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("xml","conservazione.rapportoVersamento",
				null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_XML,"conservazione.rapportoVersamento.iconTitle","conservazione.rapportoVersamento.iconTitle");
		
		this.xmlLotto = this.getWebGenericProjectFactory().getOutputFieldFactory().createButton("xmlLotto","conservazione.rapportoVersamentoLotto",
				null,org.govmix.proxy.fatturapa.web.console.costanti.Costanti.PATH_ICONA_XML,"conservazione.rapportoVersamentoLotto.iconTitle","conservazione.rapportoVersamentoLotto.iconTitle");

		this.identificativoSdi = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("identificativoSdi","fattura.identificativoSdi");
		this.cessionarioCommittente = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("cessionarioCommittente","fattura.cessionarioCommittenteDenominazione");
		this.statoInvio = this.getWebGenericProjectFactory().getOutputFieldFactory().createImage("statoInvio","fattura.statoInvio");
		this.tipoFattura = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("tipoFattura","fattura.tipoFattura");

		this.setField(this.cedentePrestatore);
		this.setField(this.dipartimento);
		this.setField(this.annoNumero);
		this.setField(this.dataInvio);
		this.setField(this.xml);
		this.setField(this.xmlLotto);
		this.setField(this.identificativoSdi);
		this.setField(this.cessionarioCommittente);
		this.setField(this.statoInvio);
		this.setField(this.tipoFattura);
	}

	@Override
	public void setDTO(FatturaElettronica dto) {
		super.setDTO(dto);

		this.cedentePrestatore.setValue(this.getDTO().getCedentePrestatoreDenominazione());

		List<Dipartimento> listaDipartimentiLoggedUtente = org.govmix.proxy.fatturapa.web.console.util.Utils.getListaDipartimentiLoggedUtente();

		for (Dipartimento dipartimento : listaDipartimentiLoggedUtente) {
			if(dipartimento.getCodice().equals(this.getDTO().getCodiceDestinatario())){
				this.dipartimento.setValue(dipartimento.getDescrizione());
				break;
			}
		}

		this.annoNumero.setValue(this.getDTO().getNumero());
		this.dataInvio.setValue(this.getDTO().getDataRicezione());
		this.tipoFattura.setValue(this.getDTO().isFatturazioneAttiva() ? "fattura.tipoFattura.attiva" : "fattura.tipoFattura.passiva");
		

		StatoConservazioneType statoConservazione = this.getDTO().getStatoConservazione();
		
		if(statoConservazione != null) {
			switch (statoConservazione) {
			case CONSERVAZIONE_FALLITA:  // Conservazione fallita 
				this.prepareUrls();
				this.statoInvio.setImage("/images/fatturapa/icons/accept_circle-red.png");
				this.statoInvio.setTitle("conservazione.search.statoInvio." + StatoConservazioneType.CONSERVAZIONE_FALLITA.getValue());
				this.statoInvio.setAlt("conservazione.search.statoInvio." + StatoConservazioneType.CONSERVAZIONE_FALLITA.getValue());
				break;
			case CONSERVAZIONE_COMPLETATA: // Conservazione competata
				this.prepareUrls();
				this.statoInvio.setImage("/images/fatturapa/icons/accept_circle-green.png");
				this.statoInvio.setTitle("conservazione.search.statoInvio." + StatoConservazioneType.CONSERVAZIONE_COMPLETATA.getValue());
				this.statoInvio.setAlt("conservazione.search.statoInvio." + StatoConservazioneType.CONSERVAZIONE_COMPLETATA.getValue());
				break;
			case ERRORE_CONSEGNA: // Errore consegna
				this.statoInvio.setImage("/images/fatturapa/icons/no_accept-yellow.png");
				this.statoInvio.setTitle("conservazione.search.statoInvio." + StatoConservazioneType.ERRORE_CONSEGNA.getValue());
				this.statoInvio.setAlt("conservazione.search.statoInvio." + StatoConservazioneType.ERRORE_CONSEGNA.getValue());
				break;
			case IN_RICONSEGNA: // In riconsegna
				this.statoInvio.setImage("/images/fatturapa/icons/accept_circle-yellow.png");
				this.statoInvio.setTitle("conservazione.search.statoInvio." + StatoConservazioneType.IN_RICONSEGNA.getValue());
				this.statoInvio.setAlt("conservazione.search.statoInvio." + StatoConservazioneType.IN_RICONSEGNA.getValue());
				break;
			case PRESA_IN_CARICO: // Presa in carico
				this.statoInvio.setImage("/images/fatturapa/icons/accept_circle-yellow.png");
				this.statoInvio.setTitle("conservazione.search.statoInvio." + StatoConservazioneType.PRESA_IN_CARICO.getValue());
				this.statoInvio.setAlt("conservazione.search.statoInvio." + StatoConservazioneType.PRESA_IN_CARICO.getValue());
				break;
			case NON_INVIATA: // Non inviata in conservazione
			default:
				this.statoInvio.setImage("/images/fatturapa/icons/plus-grey.png");
				this.statoInvio.setTitle("conservazione.search.statoInvio." + StatoConservazioneType.NON_INVIATA.getValue());
				this.statoInvio.setAlt("conservazione.search.statoInvio." + StatoConservazioneType.NON_INVIATA.getValue());
				break;
			}
		} else {
			this.statoInvio.setImage("/images/fatturapa/icons/plus-grey.png");
			this.statoInvio.setTitle(("conservazione.search.statoInvio." + StatoConservazioneType.NON_INVIATA.getValue()));
			this.statoInvio.setAlt("conservazione.search.statoInvio." + StatoConservazioneType.NON_INVIATA.getValue());
		}
		

		this.identificativoSdi.setValue(this.getDTO().getIdentificativoSdi() + "");
		this.cessionarioCommittente.setValue(this.getDTO().getCessionarioCommittenteDenominazione());
	}


	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	public Text getTipoFattura() {
		return tipoFattura;
	}

	public void setTipoFattura(Text tipoFattura) {
		this.tipoFattura = tipoFattura;
	}

	public Text getCedentePrestatore() {
		return cedentePrestatore;
	}

	public void setCedentePrestatore(Text cedentePrestatore) {
		this.cedentePrestatore = cedentePrestatore;
	}

	public Text getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(Text dipartimento) {
		this.dipartimento = dipartimento;
	}

	public Text getIdentificativoSdi() {
		return identificativoSdi;
	}

	public void setIdentificativoSdi(Text identificativoSdi) {
		this.identificativoSdi = identificativoSdi;
	}

	public Text getAnnoNumero() {
		return annoNumero;
	}

	public void setAnnoNumero(Text annoNumero) {
		this.annoNumero = annoNumero;
	}

	public DateTime getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(DateTime dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Image getStatoInvio() {
		return statoInvio;
	}

	public void setStatoInvio(Image statoInvio) {
		this.statoInvio = statoInvio;
	}

	public Button getXml() {
		return xml;
	}

	public void setXml(Button xml) {
		this.xml = xml;
	}

	public Button getXmlLotto() {
		return xmlLotto;
	}

	public void setXmlLotto(Button xmlLotto) {
		this.xmlLotto = xmlLotto;
	}

	public Text getCessionarioCommittente() {
		return cessionarioCommittente;
	}

	public void setCessionarioCommittente(Text cessionarioCommittente) {
		this.cessionarioCommittente = cessionarioCommittente;
	}

	private void prepareUrls(){
		FacesContext context = FacesContext.getCurrentInstance();

		String url = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_RAPPORTO_VERSAMENTO;

		this.xml.setHref((this.getDTO().getIdSIP() != null && this.getDTO().getIdSIP().getIdSip() > 0) ?  url : null);

		String urlLotto = context.getExternalContext().getRequestContextPath() 
				+ "/"+FattureExporter.FATTURE_EXPORTER+"?"
				+FattureExporter.PARAMETRO_IDS+"=" + this.getDTO().getId()
				+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
				+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_RAPPORTO_VERSAMENTO_LOTTO;

		this.xmlLotto.setHref((this.getDTO().getLottoFatture().getIdSIP() != null && this.getDTO().getLottoFatture().getIdSIP().getIdSip() > 0) ?  urlLotto : null);
	}

	public String visualizzaXml() {
		try {

			// recupero lista diagnostici
			List<Long> idFatture = new ArrayList<Long>();

			idFatture.add(this.getDTO().getId());

			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.sendRedirect(context.getExternalContext()
					.getRequestContextPath()
					+"/" + FattureExporter.FATTURE_EXPORTER+"?"
					+ FattureExporter.PARAMETRO_IDS+"="
					+ StringUtils.join(idFatture, ",")
					+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
					+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_RAPPORTO_VERSAMENTO);

			context.responseComplete();

			// End of the method
		} catch (Exception e) {
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
		}

		return null;
	}
	
	public String visualizzaXmlLotto() {
		try {

			// recupero lista diagnostici
			List<Long> idFatture = new ArrayList<Long>();

			idFatture.add(this.getDTO().getId());

			// We must get first our context
			FacesContext context = FacesContext.getCurrentInstance();

			// Then we have to get the Response where to write our file
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.sendRedirect(context.getExternalContext()
					.getRequestContextPath()
					+"/" + FattureExporter.FATTURE_EXPORTER+"?"
					+ FattureExporter.PARAMETRO_IDS+"="
					+ StringUtils.join(idFatture, ",")
					+ "&"+FattureExporter.PARAMETRO_FORMATO+"="+ AbstractSingleFileExporter.FORMATO_XML
					+ "&"+FattureExporter.PARAMETRO_ACTION+"="+ FattureExporter.PARAMETRO_ACTION_RAPPORTO_VERSAMENTO_LOTTO);

			context.responseComplete();

			// End of the method
		} catch (Exception e) {
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fattura.export.genericError"),null));
		}

		return null;
	}
}
